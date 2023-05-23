#!/bin/bash
failures=0
trap 'failures=$((failures+1))' ERR
./gradlew iam-integration-task1
./gradlew iam-integration-task2
./gradlew iam-integration-task3
./gradlew iam-integration-task4
./gradlew iam-integration-task5
./gradlew iam-integration-task6
./gradlew iam-integration-task7
./gradlew iam-preparation-taskuml
if ((failures == 0)); then
  echo "Success"
else
  echo "$failures failures"
  exit 1
fi
