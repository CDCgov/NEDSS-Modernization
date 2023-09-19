#
# NEDSS_HOME env variable must be set to the full path of the
# NEDSSDev source tree downloaded from https://github.com/cdcent/NEDSSDev
#

if [[ -z "$NEDSS_HOME"  ]]; then
    echo "NEDSS_HOME must be set to the NEDSSDev source downloaded from https://github.com/cdcent/NEDSSDev"
    exit 1
fi

docker build -t nedssdev --force-rm -f Dockerfile-antbuild .

NEDSS_DIST=$(pwd)/dist/nedss
mkdir -p "${NEDSS_DIST}"
docker run --rm -v "${NEDSS_HOME}":/mnt/src:ro -v "${NEDSS_DIST}":/mnt/dist -v m2:/root/.m2 nedssdev

docker-compose build nbs-mssql
docker-compose build wildfly
