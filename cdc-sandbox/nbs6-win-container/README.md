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
  - Run:  ``` docker run -p 7001:7001 -e "GITHUB_RELEASE_TAG=latest" -e "DATABASE_ENDPOINT=<ENDPOINT>" -e "odse_user=<username>" -e "odse_pass=<password>" -e "rdb_user=<username>" -e "rdb_pass=<password>" -e "srte_user=<username>" -e "srte_pass=<password>"  -t <CONTAINER-NAME> ```
    - GITHUB_RELEASE_TAG - Creates URL, downloads Release Package from GitHub Releases and configure User Guide. Default is always latest or Null
    - DATABASE_ENDPOINT - Provides Database Endpoint
    - odse_user - Provides odse Database user
    - odse_pass - Provides odse Database password
    - rdb_user - Provides rdb Database user
    - rdb_pass - Provides rdb Database pasword
    - srte_user - Provides srte Database user
    - srte_pass - Provides srte Database password
- Optional: Push Image to Quay.io Repository
  - Tag Image:  ``` docker tag <CONTAINER-NAME>:<TAG> <DOCKER-REPOSITORY-URL>/<CONTAINER-NAME>:<TAG> ``` 
  - Authenticate to Quay.io:  ``` docker login -u=<USERNAME> -p=<PASSWORD> quay.io ```
  - Push to Quay.io with latest Tag:  ``` docker push <DOCKER-REPOSITORY-URL>/<CONTAINER-NAME>:<TAG>  ```

## Deployment
This docker container makes use of environment variables that are explicitly defined in the Dockerfile and some that can be set for adhoc use cases.

### Explicit Environment Variables
| Key | Default | Description |
| --- | --- | --- |
| JAVA_HOME | "D:\wildfly-10.0.0.Final\Java\jdk8u412b08" | Location of JAVA for NBS6 wildfly server |
| JBOSS_HOME | "D:\wildfly-10.0.0.Final" | Location of and including Wildfly directory for NBS6 |
| JAVA_TOOL_OPTIONS | "-Dsun.stdout.encoding=cp437 -Dsun.stderr.encoding=cp437" | JAVA tools option for NBS6 |
| GITHUB_RELEASE_TAG | "" | Release Tag from Modernization-API Github repository, default picks up latest tag |
| FINAL_NBS_USER_GUIDE_NAME  | "NBS User Training Guide.pdf" | Name of NBS6 user guide, changing this requires a DB update |
| GITHUB_ZIP_FILE_NAME | "<version>.NEDSS.NBS.Modernized.Documentation.zip" | Name of zip Modernization-API Github repository |
| JAVA_MEMORY | "4096M"  | Memory allocated to NBS6 wildfly server |
| DISABLED_SCHEDULED_TASKS | "" | Comma separated list of a task's filename to disable from windows scheduled tasks (available filenames for tasks listed in [tasks.csv](./tasks.csv)) | 


### Implicit Environment Variables
| Key | Default | Description |
| --- | --- | --- |
| DATABASE_ENDPOINT | `null` | Database server endpoint for NBS6 wildfly |
| updateScheduledTask_* | `null` | Multiple type allowed due to * qualifier see [Supplemental](#supplemental) Section for details on this use|

### Supplemental
1. The ability to update any scheduled task on runtime for a container has been added. This comes in the form of adding environment variables starting with updateScheduledTask_. To update a scheduled task simply provide a uniquely named environment variable starting with updateScheduledTask_ (ex. updateScheduledTask_ELRImporter or updateScheduledTask_MsgOutProcessor). The format of the variables should follow what is present in [tasks.csv](./tasks.csv) and **ENDING** with a semicolon.
    - EXAMPLE: updating ELRImporter.bat tasks to run at 8 am, instead of 6 am, and every 5 minutes instead of every 2 minutes.
      ```
        updateScheduledTask_ELRImporter="ELRImporter.bat,, 8am, 0, 0, 5;"
      ```