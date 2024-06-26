# This Dockerfile is used to build NBS 6.0 Image locally and pushed to a ECR Repository
# Run command docker run -p 7001:7001 -e "GITHUB_RELEASE_TAG=latest" -e "DATABASE_ENDPOINT=<ENDPOINT>" -t <CONTAINER-NAME>
# DATABASE_ENDPOINT provided on RUN to provided Database Endpoint

# Use Windows Server Core as the base image
FROM mcr.microsoft.com/windows/servercore:ltsc2019

### Setting Docker Build Arguments ###
# NBS Zip File to download from S3
ARG S3_ZIP_NAME 

# Run Shell as Powershell
SHELL ["powershell", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

# Set environment variables and AWS credentials and region
ENV JAVA_HOME="D:\wildfly-10.0.0.Final\Java\jdk1.8.0_181" \
    JBOSS_HOME="D:\wildfly-10.0.0.Final" \
    JAVA_TOOL_OPTIONS="-Dsun.stdout.encoding=cp437 -Dsun.stderr.encoding=cp437" \
    # GITHUB_RELEASE_TAG provided on RUN to create URL and download Release Package from. Default is always latest or Null
    GITHUB_RELEASE_TAG="" \
    # Userguide file name 
    FINAL_NBS_USER_GUIDE_NAME="NBS User Training Guide.pdf" \
    # Full zip file name and file format to be downloaded from latest Release Assets in Modernization Repository. <version> will be replaced in entrypoint script.
    GITHUB_ZIP_FILE_NAME="<version>.NEDSS.NBS.Modernized.Documentation.zip"

# Set symbolic link as D drive using HKEY_LOCAL_MACHINE (HKLM)
RUN New-Item -ItemType directory -Path C:\nbs ; \
    New-ItemProperty -Path 'HKLM:\SYSTEM\CurrentControlSet\Control\Session Manager\DOS Devices' -Name 'D:'  -Value '\??\C:\nbs' -PropertyType String

# Configure NBS from Local Directory
# NOTE: 
# 1) Verify Zip File is downloaded from S3 and place in same directory, example: wildfly-10.0.0.Final-6.0.15.1.zip
# 2) Delete wildfly*/bin/hs_err_pid2604.mdmp and/or any file name containing "err"
WORKDIR /nbs
COPY $S3_ZIP_NAME.zip $S3_ZIP_NAME.zip
# Unzip and Wildfly Zip as ContainerUser to set permissions
USER ContainerUser
RUN Expand-Archive -Path C:\nbs\$env:S3_ZIP_NAME.zip; mv C:\nbs\$env:S3_ZIP_NAME\wildfly-10.0.0.Final C:\nbs

# Cleanup
USER ContainerAdministrator
RUN rm C:\nbs\$env:S3_ZIP_NAME.zip; rm C:\nbs\$env:S3_ZIP_NAME

# Configure Task Schedular. Requires Administrator Permissions
COPY task-scheduler.ps1 task-scheduler.ps1
RUN C:\nbs\task-scheduler.ps1

# Setup Entrypoint Script
COPY entrypoint.ps1 entrypoint.ps1

# Expose necessary ports
EXPOSE 7001

# ContainerUser used for running NBS6, following best practacies
USER ContainerUser

# Start WildFly
CMD ["powershell","C:\\nbs\\entrypoint.ps1"]
