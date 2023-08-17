FROM node:16-alpine as ui-builder

# Copy required sources
COPY apps/modernization-ui /usr/modernization-ui

# Install node modules
WORKDIR /usr/modernization-ui
RUN npm install && npm run build

FROM amazoncorretto:17-alpine as api-builder

# Copy project configuration
COPY gradle /usr/src/nbs/gradle
COPY gradlew /usr/src/nbs/gradlew
COPY build.gradle /usr/src/nbs/build.gradle
COPY settings.gradle /usr/src/nbs/settings.gradle

COPY lombok.config /usr/src/nbs/lombok.config

# Copy required sources
COPY apps/modernization-api /usr/src/nbs/apps/modernization-api
COPY libs /usr/src/nbs/libs


# Copy the UI
copy --from=ui-builder /usr/modernization-ui/build /usr/src/nbs/apps/modernization-ui/build

WORKDIR /usr/src/nbs

# Build just the modernization-api and any required library
RUN ./gradlew :modernization-api:buildNeeded -x test --no-daemon

FROM amazoncorretto:17-alpine

COPY --from=api-builder /usr/src/nbs/apps/modernization-api/build/libs/modernization-api*.jar api.jar 

ENV NBS_DATASOURCE_SERVER=nbs-mssql
ENV NBS_ELASTICSEARCH_SERVER=elasticsearch
ENV NBS_WILDFLY_SERVER=wildfly

# Run jar
CMD ["java", "-jar", "api.jar"]
