# Creating NB6 Docker Image

## Description

Below are manual creation process. Foundation image used is Microsoft Server Core ltsc2019. Additional details, inclusive of licensing information, are available at https://hub.docker.com/_/microsoft-windows-servercore/. 

## Files
- Dockerfile - Configuration file to build docker image
- entrypoint.ps1 - Docker entrypoint script to run each time a container runs

## Prerequisites
- Docker engine installed locally or on an instance with docker engine installed
- Access to ECR Repository  

## Steps

- Build Docker Container
  - Verify you're in nbs-legacy -> Docker directory
  - Run:  ``` docker build . -t nbs6  ```
  - Verify container was built successfully
- Push Image to Private ECR Repository
  - Tag Image:  ``` docker tag nbs6:latest <shared-account-id>.dkr.ecr.us-east-1.amazonaws.com/cdc-nbs-legacy/nbs6:latest  ``` 
  - Login ECR:  ``` aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <shared-account-id>.dkr.ecr.us-east-1.amazonaws.com  ```
  - Push to ECR with latest Tag:  ``` docker push <shared-account-id>.dkr.ecr.us-east-1.amazonaws.com/cdc-nbs-legacy/nbs6:latest  ```
  - Push to ECR with NBS Version Tag:  ``` docker push <shared-account-id>.dkr.ecr.us-east-1.amazonaws.com/cdc-nbs-legacy/nbs6:6.0.15.1  ```
