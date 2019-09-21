#!/usr/bin/env bash

start_mongodb_docker() {
    echo "Starting MongoDB in Docker on port 27017..."
    docker run --rm -d -p 27017:27017 --name mongo mongo:latest
    sleep 3
    echo "Done."
}

import_test_data() {
    cd test_data
    mongoimport --db demo --drop -c subject --file ./subject.json
    mongoimport --db demo --drop -c knowledge --file ./knowledge.json
    mongoimport --db demo --drop -c question --file ./question.json
    mongoimport --db demo --drop -c unit --file ./unit.json
    cd ..
}

start_mongodb_docker
import_test_data