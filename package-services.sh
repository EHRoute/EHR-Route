#!/bin/sh
set -ex

# Package all projects
mvn clean install

# Build images and containers
docker-compose up

# Using a maven docker image building plugin
#mvn -f ./config-server/pom.xml  -Pdockerimage docker:build
#mvn -f ./discovery-service/pom.xml  -Pdockerimage docker:build
#mvn -f ./gateway-service/pom.xml  -Pdockerimage docker:build
#mvn -f ./auth-service/pom.xml -Pdockerimage docker:build
#mvn -f ./patient-service/pom.xml  -Pdockerimage docker:build
#mvn -f ./provider-service/pom.xml  -Pdockerimage docker:build
