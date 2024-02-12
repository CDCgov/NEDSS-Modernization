# This Dockerfile is used to build NBS 6.0 Image locally and pushed to a ECR Repository

# Use Windows Server Core as the base image
FROM mcr.microsoft.com/windows/servercore:ltsc2019

#Setting Docker Build Arguments
ARG ZIP_NAME="<ADD-FULL-ZIP-FILE-NAME>"
ARG USER_GUIDE_NAME="<ADD-GUIDE-NAME>"

# Run Shell as Powershell
SHELL ["powershell", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

# Set environment variables and AWS credentials and region
ENV JAVA_HOME="D:\wildfly-10.0.0.Final\Java\jdk1.8.0_181" \
    JBOSS_HOME="D:\wildfly-10.0.0.Final" \
    JAVA_TOOL_OPTIONS="-Dsun.stdout.encoding=cp437 -Dsun.stderr.encoding=cp437" \
    USER_GUIDE_PATH="C:\nbs\wildfly-10.0.0.Final\nedssdomain\Nedss\UserGuide"

# Set symbolic link as D drive using HKEY_LOCAL_MACHINE (HKLM)
RUN New-Item -ItemType directory -Path C:\nbs ; \
    New-ItemProperty -Path 'HKLM:\SYSTEM\CurrentControlSet\Control\Session Manager\DOS Devices' -Name 'D:'  -Value '\??\C:\nbs' -PropertyType String

# Configure NBS from Local Directory
# NOTE: 
# 1) Verify Zip File is downloaded from S3 and place in same directory, example: wildfly-10.0.0.Final-6.0.15.1.zip
# 2) Delete wildfly*/bin/hs_err_pid2604.mdmp and/or any file name containing "err"
WORKDIR /nbs
COPY $ZIP_NAME.zip $ZIP_NAME.zip

#Unzip and Wildfly Zip
RUN Expand-Archive -Path C:\nbs\$env:ZIP_NAME.zip; mv C:\nbs\$env:ZIP_NAME\wildfly-10.0.0.Final C:\nbs; rm C:\nbs\$env:ZIP_NAME.zip; rm C:\nbs\$env:ZIP_NAME

#Update User Guide
RUN ls $USER_GUIDE_PATH
RUN ls $USER_GUIDE_PATH\*
COPY $USER_GUIDE_NAME $USER_GUIDE_PATH\$USER_GUIDE_NAME

RUN ls $USER_GUIDE_PATH

# Replace datasources in standalone.xml file
COPY entrypoint.ps1 entrypoint.ps1

# Expose necessary ports
EXPOSE 7001

# Start WildFly
CMD ["powershell","C:\\nbs\\entrypoint.ps1"]
