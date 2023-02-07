FROM amazoncorretto:17

COPY . /usr/src/nbs

# Install node
WORKDIR /usr/src/nbs/ui
RUN yum update -y
RUN curl --silent --location https://rpm.nodesource.com/setup_16.x | bash -
RUN yum install tar gzip nodejs -y

# Install node modules
RUN npm install

# Update database connections
WORKDIR /usr/src/nbs
RUN sed -i 's/localhost:1433/nbs-mssql:1433/' api/src/main/resources/application.yml
RUN sed -i 's/localhost:1433/nbs-mssql:1433/' api/src/test/resources/application-test.yml
RUN sed -i 's/localhost:7001/wildfly:7001/' api/src/main/resources/application.yml
RUN sed -i 's/localhost:9200/elasticsearch:9200/' api/src/main/resources/application.yml


# Build app
RUN ./gradlew clean build -x test --no-daemon

RUN mv api/build/libs/api*-plain.jar api/build/libs/plain.jar 
RUN mv api/build/libs/api*.jar api/build/libs/api.jar 

# Run jar
CMD ["java", "-jar", "api/build/libs/api.jar"]