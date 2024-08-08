#!/bin/bash

cd ../../2-spark
docker compose down -v

cd ../3-trino

if [ -d "data" ]; then
    sudo rm -r data/
fi

echo "Success remove data/"

mkdir -p data/coordinator
mkdir -p data/worker1
mkdir -p data/worker2
mkdir -p data/worker3
sudo chmod -R 777 data/

docker compose up -d