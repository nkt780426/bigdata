#!/bin/bash

# ...
docker network create --driver bridge bigdata

# ...
cd ../../1-lakehouse

mkdir -p minio/minio1/data1
mkdir -p minio/minio1/data2
mkdir -p minio/minio2/data1
mkdir -p minio/minio2/data2
mkdir -p minio/minio3/data1
mkdir -p minio/minio3/data2
sudo chmod -R 777 minio/

docker compose up -d

# ...
cd ../4-kafka

if [ -d "data" ]; then
    sudo rm -r data/
fi

echo "Success remove kafka data/"

mkdir -p data/kafka1
mkdir -p data/kafka2
mkdir -p data/kafka3
sudo chmod -R 777 data

docker compose up -d

# ...
cd ../5-flink

docker compose up -d

echo "success"