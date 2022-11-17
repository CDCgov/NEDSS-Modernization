FROM mcr.microsoft.com/azure-sql-edge:latest
USER root

COPY entrypoint.sh /entrypoint.sh
RUN chmod 0755 /entrypoint.sh

# Install sqlcmd, see https://docs.microsoft.com/en-us/sql/tools/go-sqlcmd-utility?view=sql-server-ver16
RUN apt-get update
RUN apt-get install -y software-properties-common
RUN add-apt-repository "$(wget -qO- https://packages.microsoft.com/config/ubuntu/20.04/prod.list)"
RUN apt-get install -y sqlcmd

USER mssql

ADD ./restore /var/opt/db-restore/

ENTRYPOINT [ "/entrypoint.sh" ]