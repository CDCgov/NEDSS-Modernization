# Dockerfile to build nifi image
FROM apache/nifi:1.19.0

# Set environment variables
ENV NIFI_SENSITIVE_PROPS_KEY='12345678901234567890A'
ENV SINGLE_USER_CREDENTIALS_USERNAME=admin
ENV JDBC_CONNECTION_STRING='jdbc:sqlserver://nbs-mssql:1433;integratedSecurity=false;encrypt=false;databaseName=NBS_ODSE;user=sa;password=fake.fake.fake.1234;authenticationScheme=nativeAuthentication;'
ENV JDBC_DRIVER=/var/opt/nifi_drivers/mssql-jdbc-11.2.0.jre8.jar
ENV ELASTICSEARCH_HTTP_HOSTS=http://elasticsearch:9200

COPY ./nifi_drivers/ /var/opt/nifi_drivers/
COPY ./nifi_conf/conf/ /opt/nifi/nifi-current/conf/

USER root

RUN chown -R nifi:nifi /var/opt/nifi_drivers/
RUN chown -R nifi:nifi /opt/nifi/

USER nifi