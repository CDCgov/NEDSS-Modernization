# Use official PostgreSQL image from the Docker Hub
FROM postgres:13

# Set the environment variables to configure PostgreSQL
ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=mypassword
ENV POSTGRES_DB=configuration_db

# Copy the schema SQL file to Docker's entrypoint directory for initialization
COPY config_schema.sql /docker-entrypoint-initdb.d/
