FROM amazoncorretto:17 as builder

# Copy project configuration
COPY gradle /usr/src/nbs/gradle
COPY gradlew /usr/src/nbs/gradlew
COPY build.gradle /usr/src/nbs/build.gradle
COPY settings.gradle /usr/src/nbs/settings.gradle

# Copy required sources
COPY apps /usr/src/nbs/apps
COPY libs /usr/src/nbs/libs

COPY . /usr/src/nbs

# Preparation for the modernization-ui build
# Install node
RUN yum update -y \
    && curl --silent --location https://rpm.nodesource.com/setup_16.x | bash - \
    && yum install tar gzip nodejs -y

# Install node modules
WORKDIR /usr/src/nbs/apps/modernization-ui
RUN npm install

WORKDIR /usr/src/nbs

# Build the UI
RUN ./gradlew :modernization-ui:build -x test --no-daemon 

# Build just the modernization-api and any required library
RUN ./gradlew :modernization-api:buildNeeded -x test --no-daemon

FROM amazoncorretto:17

COPY --from=builder /usr/src/nbs/apps/modernization-api/build/libs/modernization-api*.jar api.jar 

ENV NBS_DATASOURCE_SERVER=nbs-mssql
ENV NBS_ELASTICSEARCH_SERVER=elasticsearch
ENV NBS_WILDFLY_SERVER=wildfly

# Run jar
CMD ["java", "-jar", "api.jar"]
