#!/bin/bash

mkdir -p dependence
cd dependence/
wget https://repo1.maven.org/maven2/software/amazon/awssdk/bundle/2.17.178/bundle-2.17.178.jar

wget https://repo1.maven.org/maven2/org/apache/flink/flink-connector-kafka/1.17.0/flink-connector-kafka-1.17.0.jar

wget https://repo1.maven.org/maven2/org/apache/flink/flink-csv/1.17.0/flink-csv-1.17.0.jar

wget https://repo1.maven.org/maven2/org/apache/flink/flink-json/1.17.0/flink-json-1.17.0.jar

wget https://repo.maven.apache.org/maven2/org/apache/flink/flink-shaded-hadoop-2-uber/2.8.3-10.0/flink-shaded-hadoop-2-uber-2.8.3-10.0.jar

wget https://repo1.maven.org/maven2/org/apache/flink/flink-sql-avro-confluent-registry/1.17.0/flink-sql-avro-confluent-registry-1.17.0.jar

wget https://repo1.maven.org/maven2/org/apache/flink/flink-sql-connector-hive-2.3.9_2.12/1.17.0/flink-sql-connector-hive-2.3.9_2.12-1.17.0.jar

wget https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-common/2.8.3/hadoop-common-2.8.3.jar

wget https://repo1.maven.org/maven2/org/apache/iceberg/iceberg-flink-runtime-1.17/1.5.2/iceberg-flink-runtime-1.17-1.5.2.jar

wget https://repo1.maven.org/maven2/org/apache/iceberg/iceberg-nessie/1.5.2/iceberg-nessie-1.5.2.jar

wget https://repo1.maven.org/maven2/org/apache/kafka/kafka-clients/3.2.3/kafka-clients-3.2.3.jar

wget https://repo1.maven.org/maven2/software/amazon/awssdk/url-connection-client/2.17.178/url-connection-client-2.17.178.jar