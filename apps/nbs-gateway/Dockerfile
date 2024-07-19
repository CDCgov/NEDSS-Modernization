FROM gradle:8-jdk21 AS builder

# Copy project configuration
COPY gradle /usr/src/nbs/gradle
COPY build.gradle /usr/src/nbs/build.gradle
COPY settings.gradle /usr/src/nbs/settings.gradle

# Copy required sources
COPY apps/nbs-gateway /usr/src/nbs/apps/nbs-gateway

WORKDIR /usr/src/nbs

RUN gradle :nbs-gateway:build -x test --no-daemon

RUN jar xf apps/nbs-gateway/build/libs/nbs-gateway.jar
RUN jdeps --ignore-missing-deps -q  \
  --recursive  \
  --multi-release 21  \
  --print-module-deps  \
  --class-path 'BOOT-INF/lib/*'  \
  apps/nbs-gateway/build/libs/nbs-gateway.jar > deps.info

RUN jlink \
  --add-modules $(cat deps.info),jdk.crypto.cryptoki \
  --strip-debug \
  --compress 2 \
  --no-header-files \
  --no-man-pages \
  --output gateway/runtime

FROM redhat/ubi9-micro:latest

COPY --from=builder /usr/src/nbs/gateway/runtime /deployment/runtime

COPY --from=builder /usr/src/nbs/apps/nbs-gateway/build/libs/nbs-gateway.jar /deployment/application/api.jar

ENV PATH /deployment/runtime/bin:$PATH

WORKDIR /deployment/application

CMD ["java", "-jar", "api.jar"]
