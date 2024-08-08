FROM mcr.microsoft.com/mssql/server:2022-latest

ARG DB_PW

ENV ACCEPT_EULA=1 \
    SQLCMDPASSWORD=${DB_PW} \
    SA_PASSWORD=${DB_PW} 
USER root

RUN apt-get update \
    && ACCEPT_EULA=Y apt-get install mssql-tools18 unixodbc-dev -y 

COPY --chown=mssql ./restore /var/opt/database/restore/

USER mssql

# Start the database and execute restore scripts
RUN /opt/mssql/bin/sqlservr & /var/opt/database/restore/restore.sh


ENTRYPOINT [ "/opt/mssql/bin/sqlservr" ]