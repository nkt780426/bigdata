#!/bin/bash

cd ../../4-kafka
docker compose down -v
sudo rm -r data/

cd ../5-flink
docker compose down -v

cd ../6-source
docker compose down -v

echo "Sucess!"