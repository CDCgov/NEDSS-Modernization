# Local development db
A near empty NBS database for use in local development and test execution. 

## Pushing a new test db image

### Prerequisites
* Install [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
* Configure [SSO](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-sso.html)
* Build the test-db image

1. Log in to AWS CLI
    ```
    aws sso login --sso-session <session-name>
    ```
2. Connect docker to AWS ECR (See push commands for more info within the AWS ECR UI)
   ```
   aws ecr get-login-password --region us-east-1 --profile shared | docker login --username AWS --password-stdin <ecr url: ...dkr.ecr.us-east-1.amazonaws.com>
   ```
3. Tag the image
   ```
   docker tag cdc-sandbox-nbs-mssql:latest <...dkr.ecr.us-east-1.amazonaws.com/cdc-nbs-modernization/modernization-test-db:latest>
   ```
4. Push the image to ECR
   ```
   docker push <image to push: ...dkr.ecr.us-east-1.amazonaws.com/cdc-nbs-modernization/modernization-test-db:latest>
   ```