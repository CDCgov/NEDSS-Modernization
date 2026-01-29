FROM node:24 AS ui-builder

WORKDIR /usr/modernization-ui

COPY apps/modernization-ui/package.json package.json
COPY apps/modernization-ui/package-lock.json package-lock.json

RUN npm install

# Copy required sources
COPY apps/modernization-ui .

RUN npm run build

FROM gradle:9-jdk21 AS api-builder

# Copy project configuration
COPY gradle /usr/src/nbs/gradle

COPY build.gradle /usr/src/nbs/build.gradle
COPY settings.gradle /usr/src/nbs/settings.gradle

COPY lombok.config /usr/src/nbs/lombok.config

# Copy required sources
COPY apps/modernization-api /usr/src/nbs/apps/modernization-api
COPY libs /usr/src/nbs/libs

# Copy the UI
COPY --from=ui-builder /usr/modernization-ui/build /usr/src/nbs/apps/modernization-ui/build/

WORKDIR /usr/src/nbs

# Build just the modernization-api and any required library
RUN gradle :modernization-api:buildNeeded -x test --no-daemon

RUN jar xf apps/modernization-api/build/libs/modernization-api.jar

RUN jdeps --ignore-missing-deps -q  \
  --recursive  \
  --multi-release 21  \
  --print-module-deps  \
  --class-path 'BOOT-INF/lib/*'  \
  apps/modernization-api/build/libs/modernization-api.jar > deps.info

RUN jlink \
  --add-modules $(cat deps.info),jdk.crypto.cryptoki \
  --strip-debug \
  --no-header-files \
  --no-man-pages \
  --output modernization-api/runtime

FROM redhat/ubi10-micro:latest

ENV NBS_DATASOURCE_SERVER=nbs-mssql
ENV NBS_ELASTICSEARCH_SERVER=elasticsearch
ENV NBS_WILDFLY_SERVER=wildfly

COPY --from=api-builder /usr/src/nbs/modernization-api/runtime /deployment/runtime

COPY --from=api-builder /usr/src/nbs/apps/modernization-api/build/libs/modernization-api.jar /deployment/application/api.jar

ENV PATH /deployment/runtime/bin:$PATH

WORKDIR /deployment/application

CMD ["java", "-jar", "api.jar"]
