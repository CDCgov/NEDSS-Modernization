FROM amazoncorretto:17

COPY . /usr/src/nbs

# Install node
WORKDIR /usr/src/nbs/apps/modernization-ui
RUN yum update -y
RUN curl --silent --location https://rpm.nodesource.com/setup_16.x | bash -
RUN yum install tar gzip nodejs -y

# Install node modules
RUN npm install

# Update database connections
WORKDIR /usr/src/nbs
RUN sed -i 's/localhost:1433/nbs-mssql:1433/' apps/modernization-api/src/main/resources/application.yml
RUN sed -i 's/localhost:1433/nbs-mssql:1433/' apps/modernization-api/src/test/resources/application-test.yml
RUN sed -i 's/localhost:7001/wildfly:7001/' apps/modernization-api/src/main/resources/application.yml
RUN sed -i 's/localhost:9200/elasticsearch:9200/' apps/modernization-api/src/main/resources/application.yml


# Build app
RUN ./gradlew clean build -x test --no-daemon

RUN mv apps/modernization-api/build/libs/modernization-api*-plain.jar apps/modernization-api/build/libs/plain.jar 
RUN mv apps/modernization-api/build/libs/modernization-api*.jar apps/modernization-api/build/libs/api.jar 

# Run jar
CMD ["java", "-jar", "apps/modernization-api/build/libs/api.jar"]
