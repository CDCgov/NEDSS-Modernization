FROM amazoncorretto:17-alpine-jdk as builder

# Copy project configuration
COPY gradle /usr/src/nbs/gradle
COPY gradlew /usr/src/nbs/gradlew
COPY build.gradle /usr/src/nbs/build.gradle
COPY settings.gradle /usr/src/nbs/settings.gradle

# Copy required sources
COPY apps/nbs-gateway /usr/src/nbs/apps/nbs-gateway

WORKDIR /usr/src/nbs

RUN ./gradlew :nbs-gateway:build -x test --no-daemon

FROM amazoncorretto:17-alpine-jdk

COPY --from=builder /usr/src/nbs/apps/nbs-gateway/build/libs/nbs-gateway*.jar api.jar

# Run jar
CMD ["java", "-jar", "api.jar"]
