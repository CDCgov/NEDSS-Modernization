FROM eclipse-temurin:17 as builder

# Copy project configuration
COPY gradle /usr/src/nbs/gradle
COPY gradlew /usr/src/nbs/gradlew
COPY build.gradle /usr/src/nbs/build.gradle
COPY settings.gradle /usr/src/nbs/settings.gradle

COPY lombok.config /usr/src/nbs/lombok.config

# Copy required sources
COPY apps/question-bank /usr/src/nbs/apps/question-bank
COPY libs /usr/src/nbs/libs

WORKDIR /usr/src/nbs

# Build just the pagebuilder-api and any required library
RUN ./gradlew :question-bank:buildNeeded -x test --no-daemon

RUN jar xf apps/question-bank/build/libs/question-bank.jar

RUN jdeps --ignore-missing-deps -q  \
    --recursive  \
    --multi-release 17  \
    --print-module-deps  \
    --class-path 'BOOT-INF/lib/*'  \
    apps/question-bank/build/libs/question-bank.jar > deps.info

RUN jlink \
    --add-modules $(cat deps.info) \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output page-builder/runtime

FROM debian:12-slim

ENV NBS_DATASOURCE_SERVER=nbs-mssql

COPY --from=builder /usr/src/nbs/page-builder/runtime /deployment/runtime

COPY --from=builder /usr/src/nbs/apps/question-bank/build/libs/question-bank.jar /deployment/application/

WORKDIR /deployment

ENTRYPOINT runtime/bin/java -jar application/question-bank.jar
