#!/bin/bash

set -euo pipefail

cd "$(dirname "${0}")"

mvn clean package -e -U -DskipTests

rm -rf output && mkdir output

cp control.sh output/
mv target/*.jar output/

rm -rf target