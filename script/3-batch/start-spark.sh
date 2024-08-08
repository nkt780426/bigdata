#!/bin/bash

cd ../../3-trino

if [ -d "data" ]; then
    sudo rm -r data/
fi

docker compose down -v

cd ../2-spark
docker compose up -d
