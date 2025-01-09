# Creating and Running NB6 Docker Image Manually

## Description
Below are manual NBS6 creation and run process steps. Foundation image used is Microsoft Server Core ltsc2019. Additional details, inclusive of licensing information, are available at https://hub.docker.com/_/microsoft-windows-servercore/. 

## Files
- Dockerfile - Configuration file to build docker image
- entrypoint.ps1 - Docker entrypoint script to run each time a container runs
- tasks.csv - A csv container tasks that are scheduled within the container, all tasks are on by default. Tasks can be disabled through an environment variable called DISABLED_SCHEDULED_TASKS which is a comma separated list.
  - CSV expected columns: filename, scriptPathFromWorkDir, startTime, frequencyDays, frequencyHours, frequencyMinutes
- task-scheduler.ps1 - Script to configure windows scheduled jobs

## Prerequisites
- Docker engine installed locally or on an instance with Docker engine installed
- Access to S3 bucket containing NBS6 installation package
- Optional: Access to a Quay.io Docker Repository if pushing image

## Steps
- Build Docker Container
  - Verify you're in cdc-sandbox -> nbs6-win-container directory
  - Run:  ``` docker build --build-arg S3_ZIP_NAME=wildfly-10.0.0.Final-6.0.15.1 -t <CONTAINER-NAME>:<TAG> . ```
    - S3_ZIP_NAME - Zip file name pulling NBS6 Wildfly installation zip from S3.
  - Verify container was built successfully
- Run NBS6 Docker Container
  - Run:  ``` docker run -p 7001:7001 -e "GITHUB_RELEASE_TAG=latest" -e "DATABASE_ENDPOINT=<ENDPOINT>" -t <CONTAINER-NAME> ```
    - GITHUB_RELEASE_TAG - Creates URL, downloads Release Package from GitHub Releases and configure User Guide. Default is always latest or Null
    - DATABASE_ENDPOINT - Provides Database Endpoint
- Optional: Push Image to Quay.io Repository
  - Tag Image:  ``` docker tag <CONTAINER-NAME>:<TAG> <DOCKER-REPOSITORY-URL>/<CONTAINER-NAME>:<TAG> ``` 
  - Authenticate to Quay.io:  ``` docker login -u=<USERNAME> -p=<PASSWORD> quay.io ```
  - Push to Quay.io with latest Tag:  ``` docker push <DOCKER-REPOSITORY-URL>/<CONTAINER-NAME>:<TAG>  ```
