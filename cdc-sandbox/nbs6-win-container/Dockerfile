# This Dockerfile is used to build NBS 6.0 Image locally and pushed to a ECR Repository
# Run command docker run -p 7001:7001 -e "GITHUB_RELEASE_TAG=latest" -e "DATABASE_ENDPOINT=<ENDPOINT>" -t <CONTAINER-NAME>
# DATABASE_ENDPOINT provided on RUN to provided Database Endpoint

# Use Windows Server Core as the base image and builder
FROM mcr.microsoft.com/windows/servercore:ltsc2019 AS builder

# Run Shell as Powershell
SHELL ["powershell", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

### Setting Docker Build Arguments ###
# NBS Zip File to download from S3
ARG S3_ZIP_NAME 

COPY $S3_ZIP_NAME.zip $S3_ZIP_NAME.zip
USER ContainerAdministrator
RUN Expand-Archive -Path C:\$env:S3_ZIP_NAME.zip; mv C:\$env:S3_ZIP_NAME\wildfly-10.0.0.Final\* C:\wildfly-10.0.0.Final\/

# Downloads
RUN mkdir C:\executables\sqlcmd
RUN Invoke-WebRequest -Uri https://github.com/microsoft/go-sqlcmd/releases/download/v1.8.2/sqlcmd-windows-amd64.zip -OutFile C:\executables\sqlcmd\sqlcmd-windows-amd64.zip
RUN Expand-Archive C:\executables\sqlcmd\sqlcmd-windows-amd64.zip C:\executables\sqlcmd\/

# Use Windows Server Core as the base image
FROM mcr.microsoft.com/windows/servercore:ltsc2019

### Setting Docker Build Arguments ###
# NBS Zip File to download from S3
ARG S3_ZIP_NAME 

# Run Shell as Powershell
SHELL ["powershell", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

# Set environment variables and AWS credentials and region
ENV JAVA_HOME="D:\wildfly-10.0.0.Final\Java\jdk8u412b08" \
    JBOSS_HOME="D:\wildfly-10.0.0.Final" \
    # JBOSS_BASE_DIR="D:\wildfly-10.0.0.Final\nedssdomain" \
    JAVA_TOOL_OPTIONS="-Dsun.stdout.encoding=cp437 -Dsun.stderr.encoding=cp437" \
    # GITHUB_RELEASE_TAG provided on RUN to create URL and download Release Package from. Default is always latest or Null
    GITHUB_RELEASE_TAG="" \
    # Userguide file name 
    FINAL_NBS_USER_GUIDE_NAME="NBS User Training Guide.pdf" \
    # Full zip file name and file format to be downloaded from latest Release Assets in Modernization Repository. <version> will be replaced in entrypoint script.
    GITHUB_ZIP_FILE_NAME="<version>.NEDSS.NBS.Modernized.Documentation.zip" \
    JAVA_MEMORY="4096M" \
    # Task names for tasks to be disabled correspond to filename in tasks.csv (suggested removal SendAlertEMail.bat)
    DISABLED_SCHEDULED_TASKS=""

# Configure NBS from Local Directory
# NOTE: 
# 1) Verify Zip File is downloaded from S3 and place in same directory, example: wildfly-10.0.0.Final-6.0.15.1.zip
# 2) Delete wildfly*/bin/hs_err_pid2604.mdmp and/or any file name containing "err"

USER ContainerAdministrator

# Set symbolic link as D drive using HKEY_LOCAL_MACHINE (HKLM)
RUN New-Item -ItemType directory -Path C:\wildfly-10.0.0.Final ; \
    New-ItemProperty -Path 'HKLM:\SYSTEM\CurrentControlSet\Control\Session Manager\DOS Devices' -Name 'D:'  -Value '\??\C:\' -PropertyType String

    
COPY --from=builder C:\\wildfly-10.0.0.Final C:\\wildfly-10.0.0.Final
COPY --from=builder C:\\executables\\sqlcmd\\/ C:\\Users\\ContainerAdministrator\\AppData\\Local\\Microsoft\\WindowsApps\\/

# Configure Task Schedular. Requires Administrator Permissions
COPY task-scheduler.ps1 task-scheduler.ps1
COPY tasks.csv tasks.csv
RUN C:\task-scheduler.ps1

# Setup Entrypoint Script
COPY entrypoint.ps1 entrypoint.ps1

# Expose necessary ports
EXPOSE 7001

# ContainerUser used for running NBS6, following best practacies
# USER ContainerUser

# Start WildFly
CMD ["powershell","C:\\entrypoint.ps1"]
