# Dockerfile to build nifi image
FROM apache/nifi:2.7.2

COPY ./nifi_drivers/ /var/opt/nifi_drivers/
COPY ./nifi_conf/conf/ /opt/nifi/nifi-current/conf/

USER root

RUN chown -R nifi:nifi /var/opt/nifi_drivers/
RUN chown -R nifi:nifi /opt/nifi/

ENV JDBC_DRIVER=/var/opt/nifi_drivers/mssql-jdbc-11.2.0.jre8.jar

USER nifi
