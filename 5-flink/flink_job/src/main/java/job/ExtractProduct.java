package job;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.api.Table;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import static org.apache.flink.table.api.Expressions.$;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableResult;

import dto.OrderDTO;
import dto.OrderDetailDTO;
import dto.ProductDTO;

import models.Order;
import models.OrderDetail;
import models.Product;

import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;
import java.lang.RuntimeException;

public class ExtractProduct {
    public static void main(String[] args) throws Exception {
        // Load properties
        Properties minioConfig = new Properties();
        try (InputStream stream = ExtractProduct.class.getClassLoader().getResourceAsStream("minio.properties")) {
            minioConfig.load(stream);
        }
        Properties consumerConfig = new Properties();
        try (InputStream stream = ExtractProduct.class.getClassLoader().getResourceAsStream("consumer.properties")) {
            consumerConfig.load(stream);
        }

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Defile kafka source to consumer log
        KafkaSource<String> orderDataSource = KafkaSource.<String>builder()
                .setProperties(consumerConfig)
                .setTopics("orders")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();

        // Khởi tạo ObjectMapper và cấu hình để cho phép số không chuẩn
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        objectMapper.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);

        // dataflow
        DataStream<Product> productStream = env
            .fromSource(orderDataSource, WatermarkStrategy.noWatermarks(), "order_source")
            .map(orderJson -> {
                try {
                    return objectMapper.readValue(orderJson, OrderDTO.class);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to parse JSON", e);
                }
            })
            .flatMap(new FlatMapFunction<OrderDTO, Product>() {
                @Override
                public void flatMap(OrderDTO orderDTO, Collector<Product> out) {
                    for (OrderDetailDTO orderDetailDTO : orderDTO.getOrderDetailsDTO()) {
                        if (orderDetailDTO.getProductDTO() != null) {
                            out.collect(orderDetailDTO.getProductDTO().toProduct());
                        }
                    }
                }
            });

        
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, EnvironmentSettings.newInstance().inStreamingMode().build());

        // Define catalog
        tableEnv.executeSql(
        "CREATE CATALOG iceberg WITH ("
                + "'type'='iceberg',"
                + "'catalog-impl'='org.apache.iceberg.nessie.NessieCatalog',"
                + "'io-impl'='org.apache.iceberg.aws.s3.S3FileIO',"
                + "'uri'='http://nessie:19120/api/v2',"
                + "'authentication.type'='none',"
                + "'ref'='main',"
                + "'client.assume-role.region'='us-east-1',"
                + "'warehouse' = 's3://warehouse',"
                + "'s3.endpoint'='http://nginx:9000'"
                + ")");

        // List all catalogs in nessie
        TableResult result = tableEnv.executeSql("SHOW CATALOGS");
        result.print();
        
        // Set the current catalog to the new catalog
        tableEnv.useCatalog("iceberg");
        
        // Create a database in the current catalog
        tableEnv.executeSql("CREATE DATABASE IF NOT EXISTS db");

        // Create table in that database
        tableEnv.executeSql(
            "CREATE TABLE IF NOT EXISTS db.products ("
                    + "name STRING,"
                    + "category STRING,"
                    + "sub_category STRING,"
                    + "container STRING,"
                    + "base_margin FLOAT"
                    + ")");

        // Registable as a Temporary view
        Table productTable = tableEnv.fromDataStream(productStream,
            $("name").as("name"),
            $("category").as("category"),
            $("subCategory").as("sub_category"),
            $("container").as("container"),
            $("baseMargin").as("base_margin")
        );
        
        productTable.executeInsert("db.products");
    }
}
