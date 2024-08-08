package job;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.api.Table;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import static org.apache.flink.table.api.Expressions.$;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.kafka.common.TopicPartition;

import dto.OrderDTO;
import models.Order;

import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;
import java.lang.RuntimeException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ExtractOrder0 {
    public static void main(String[] args) throws Exception {
        // Load properties
        Properties minioConfig = new Properties();
        try (InputStream stream = ExtractOrder0.class.getClassLoader().getResourceAsStream("minio.properties")) {
            minioConfig.load(stream);
        }
        Properties consumerConfig = new Properties();
        try (InputStream stream = ExtractOrder0.class.getClassLoader().getResourceAsStream("consumer.properties")) {
            consumerConfig.load(stream);
            // Set the consumer group ID
            consumerConfig.setProperty("group.id", "order_group");
        }

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Tạo đối tượng TopicPartition cho partition 0
        Set<TopicPartition> partitionSet = new HashSet<>();
        partitionSet.add(new TopicPartition("orders", 0));

        // Define kafka source to consume logs
        KafkaSource<String> orderDataSource = KafkaSource.<String>builder()
                .setProperties(consumerConfig)
                .setPartitions(partitionSet)
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();

        // Initialize ObjectMapper and configure to allow non-standard numbers
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        objectMapper.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);

        DataStream<Order> orderStream = env
            .fromSource(orderDataSource, WatermarkStrategy.noWatermarks(), "order_source")
            .map(orderDTOJson -> {
                return objectMapper.readValue(orderDTOJson, OrderDTO.class);
            })
            .map(OrderDTO::toOrder);

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
        
        // Generate a unique table name using timestamp
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String tableName = "orders_" + timestamp;

        // Create table in that database
        tableEnv.executeSql(
                "CREATE TABLE IF NOT EXISTS db." + tableName + " ("
                        + "order_id BIGINT,"
                        + "order_date STRING,"
                        + "customer_id BIGINT"
                        + ")");

        Table orderTable = tableEnv.fromDataStream(orderStream, 
            $("orderId").as("order_id"),
            $("orderDate").as("order_date"),
            $("customerID").as("customer_id")
        );
        
        orderTable.executeInsert("db." + tableName);
    }
}
