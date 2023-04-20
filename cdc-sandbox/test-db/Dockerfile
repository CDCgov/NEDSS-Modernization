FROM mcr.microsoft.com/azure-sql-edge:1.0.7
USER root

# Install sqlcmd, see https://docs.microsoft.com/en-us/sql/tools/go-sqlcmd-utility?view=sql-server-ver16
RUN apt-get update
RUN apt-get install -y software-properties-common tar
RUN add-apt-repository "$(wget -qO- https://packages.microsoft.com/config/ubuntu/20.04/prod.list)"
RUN apt-get install -y sqlcmd

# Copy restore files and unzip
ADD ./restore /var/opt/db-restore/
RUN tar -xzf /var/opt/db-restore/restore.d/restore.tar.gz -C /var/opt/db-restore/restore.d/

USER mssql

# Set default passwords
ENV ACCEPT_EULA=1
ENV SQLCMDPASSWORD=fake.fake.fake.1234
ENV SA_PASSWORD=fake.fake.fake.1234

# Start the database and execute restore scripts
RUN /opt/mssql/bin/sqlservr & /var/opt/db-restore/restore.sh

ENTRYPOINT [ "/opt/mssql/bin/sqlservr" ]