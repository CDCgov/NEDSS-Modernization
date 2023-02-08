FROM amazoncorretto:17

COPY ./modernization-api /usr/src/nbs
COPY ./modernization-ui /usr/src/nbs

# Install node
WORKDIR /usr/src/nbs/ui
RUN yum update -y
RUN curl --silent --location https://rpm.nodesource.com/setup_16.x | bash -
RUN yum install tar gzip nodejs -y

# Install node modules
RUN npm install

# Update database connections
WORKDIR /usr/src/nbs
RUN sed -i 's/localhost:1433/nbs-mssql:1433/' modernization-api/src/main/resources/application.yml
RUN sed -i 's/localhost:1433/nbs-mssql:1433/' modernization-api/src/test/resources/application-test.yml
RUN sed -i 's/localhost:7001/wildfly:7001/' modernization-api/src/main/resources/application.yml
RUN sed -i 's/localhost:9200/elasticsearch:9200/' modernization-api/src/main/resources/application.yml


# Build app
RUN ./gradlew clean build -x test --no-daemon

RUN mv modernization-api/build/libs/api*-plain.jar modernization-api/build/libs/plain.jar 
RUN mv modernization-api/build/libs/api*.jar modernization-api/build/libs/api.jar 

# Run jar
CMD ["java", "-jar", "modernization-api/build/libs/api.jar"]