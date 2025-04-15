#!/bin/sh
set -e

BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"


NBS6_PATH=$BASE/builder/NEDSSDev
NBS6_VERSION=NBS_6.0.16

echo "Building NBS6 Application"

rm -rf $NBS6_PATH && \
  git clone -b $NBS6_VERSION git@github.com:cdcent/NEDSSDev.git $NBS6_PATH && \
  docker compose -f $BASE/../docker-compose.yml up wildfly --build -d && \
  rm -rf $NBS6_PATH
