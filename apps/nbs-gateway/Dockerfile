FROM eclipse-temurin:17 AS builder

# Copy project configuration
COPY gradle /usr/src/nbs/gradle
COPY gradlew /usr/src/nbs/gradlew
COPY build.gradle /usr/src/nbs/build.gradle
COPY settings.gradle /usr/src/nbs/settings.gradle

# Copy required sources
COPY apps/nbs-gateway /usr/src/nbs/apps/nbs-gateway

WORKDIR /usr/src/nbs

RUN ./gradlew :nbs-gateway:build -x test --no-daemon



RUN jar xf apps/nbs-gateway/build/libs/nbs-gateway.jar
RUN jdeps --ignore-missing-deps -q  \
    --recursive  \
    --multi-release 17  \
    --print-module-deps  \
    --class-path 'BOOT-INF/lib/*'  \
    apps/nbs-gateway/build/libs/nbs-gateway.jar > deps.info

RUN jlink \
    --add-modules $(cat deps.info) \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output gateway/runtime

FROM debian:12-slim

COPY --from=builder /usr/src/nbs/gateway/runtime /deployment/runtime

COPY --from=builder /usr/src/nbs/apps/nbs-gateway/build/libs/nbs-gateway.jar /deployment/application/

WORKDIR /deployment

ENTRYPOINT runtime/bin/java -jar application/nbs-gateway.jar
