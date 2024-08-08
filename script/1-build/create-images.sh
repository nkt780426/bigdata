#!/bin/bash

cd ../../2-spark
docker build -t jupyter-sparksubmit:v01 .

cd ../5-flink
docker build -t flink_pro:v10 .

cd ../6-source
docker build -t kafka-producer:v56 .