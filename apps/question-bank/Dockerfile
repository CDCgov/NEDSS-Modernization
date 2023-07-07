FROM amazoncorretto:17 as builder

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

FROM amazoncorretto:17

COPY --from=builder /usr/src/nbs/apps/question-bank/build/libs/question-bank*.jar api.jar 

ENV NBS_DATASOURCE_SERVER=nbs-mssql

# Run jar
CMD ["java", "-jar", "api.jar"]