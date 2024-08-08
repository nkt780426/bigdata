#!/bin/bash

cd ../../1-lakehouse
docker compose down -v

cd ../2-spark
docker compose down -v

cd ../3-trino
if [ -d "data" ]; then
    sudo rm -r data/
fi
docker compose down -v

echo "Sucess!"