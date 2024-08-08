#!/bin/bash

mkdir -p notebooks/jars
cd dependence/

wget https://repo1.maven.org/maven2/software/amazon/awssdk/bundle/2.17.178/bundle-2.17.178.jar

wget https://repo1.maven.org/maven2/software/amazon/awssdk/url-connection-client/2.17.178/url-connection-client-2.17.178.jar

wget https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-common/3.3.6/hadoop-common-3.3.6.jar

wget https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-aws/3.3.6/hadoop-aws-3.3.6.jar

wget https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-client/3.3.6/hadoop-client-3.3.6.jar

wget https://repo1.maven.org/maven2/com/amazonaws/aws-java-sdk-bundle/1.12.765/aws-java-sdk-bundle-1.12.765.jar