#!/usr/bin/env bash

import_test_data() {
    cd test_data
    mongoimport --host mongo --db demo --drop -c subject --file ./subject.json
    mongoimport --host mongo --db demo --drop -c knowledge --file ./knowledge.json
    mongoimport --host mongo --db demo --drop -c question --file ./question.json
    mongoimport --host mongo --db demo --drop -c unit --file ./unit.json
}

import_test_data