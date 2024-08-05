FROM mcr.microsoft.com/mssql/server:2022-latest

ARG DB_PW

ENV ACCEPT_EULA=1 \
    SQLCMDPASSWORD=${DB_PW} \
    SA_PASSWORD=${DB_PW} \
    INITIALIZE_SCRIPTS=/var/opt/database/restore

USER root

RUN apt-get update \
    && ACCEPT_EULA=Y apt-get install mssql-tools18 unixodbc-dev -y 

USER mssql

COPY entrypoint.sh /var/opt/database/entrypoint.sh
COPY ./restore /var/opt/database/restore/

ENTRYPOINT ["/bin/bash", "/var/opt/database/entrypoint.sh"]