#!/bin/sh
set -e

BASE="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

NBS6_PATH=$BASE/builder/NEDSSDev/
NBS6_VERSION=lts/6.0.18
clean=false


while getopts ":p:v:" opt; do
  case $opt in
    p)
      NBS6_PATH=$OPTARG
      ;;
    v)
      NBS6_VERSION=$OPTARG
      ;;    
    \?)
      echo "Invalid option: -$OPTARG" >&2
      ;;
  esac
done

if [ ! -d $NBS6_PATH ]; then
  echo "Downloading NEDSSDev:[$NBS6_VERSION]"
  git clone -b $NBS6_VERSION git@github.com:cdcent/NEDSSDev.git $NBS6_PATH
   clean=true
fi

echo "Building NBS6 Application [$NBS6_VERSION]"

docker compose -f $BASE/../docker-compose.yml up wildfly --build -d  

if $clean; then
  rm -rf $NBS6_PATH
fi
