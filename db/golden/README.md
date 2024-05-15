# Golden Database De-identification

## Summary
This project aims to provide an environment to scan, detect and replace all fields containing PII/PHI data in the Golden Database.

## Requirements
EC2 instance type: `m5.2xlarge` (minimal) or `m5.4xlarge` \
OS: Ubuntu, 22.04 LTS, amd64 \
SDK: Java JDK 17

## Setup

#### 1. Install SQL Server command-line tools

The `sqlcmd` tool is required for performing data extraction and restoration operations. 
Instructions for installing `sqlcmd` on Ubuntu can be found [here](https://learn.microsoft.com/en-us/sql/linux/sql-server-linux-setup-tools?view=sql-server-ver16&tabs=ubuntu-install#ubuntu)

#### 2. CliniDeID

Choose a directory (e.g. `/opt`) and clone the CliniDeID repository:
```
cd /opt
git clone https://github.com/Clinacuity/CliniDeID
```

Build the project:
```
mvn clean package -DskipTests

./scripts/makeDeployGenericZip.sh

./scripts/makePlatformZip.sh Ubuntu

unzip CliniDeID-Ubuntu.zip
```
This will create a `CliniDeID-Ubuntu` folder under main CLiniDeID directory.

Download and place the trained models for the machine learning modules in the CliniDeID `/data/models` directory:

| Model | File name | Download link |
| ------ | ------ | ------ |
| RNN | rnn-U3-FullSplit.h5 | https://e.pcloud.link/publink/show?code=XZWr7jZ7a6Kn8TVRnRLeE1HQ5mjtS6uuW8y |
| SVM map | svmMap-U3-FullSplit | https://e.pcloud.link/publink/show?code=XZlr7jZhAtGksespVS7FP6mY9niAFbopAUV |
| SVM model | svmModel-U3-FullSplit | https://e.pcloud.link/publink/show?code=XZtr7jZs3pm2OR7PgmqCd4A8w2mLm2RlQM7 |
| MIRA | mira-U3-FullSplit | https://e.pcloud.link/publink/show?code=XZar7jZjNXjjyH1rBkr6nQ3kdE5iRNquOLV |

Complete installation instructions are available in the [Installation](https://github.com/Clinacuity/CliniDeID?tab=readme-ov-file#installation) section of the CliniDeID repository.

#### 3. Get GoldenDB scripts
Choose a directory for cloning the GoldenDB project scripts, e.g. `/opt/db` 
```
cd /opt
mkdir /opt/db
cd /opt/db
git clone https://github.com/CDCgov/NEDSS-Modernization/tree/main/db/golden
```
The SQL scripts in this repository should be executed on the attached database instance. They will create necessary procedures for data extraction and restoration. 
The command files will provide the data ETL pipeline.

#### 4. Environment variables
Set the following environment variables:
```
export CLINIDEID_HOME=/opt/CliniDeID/CliniDeID-Ubuntu
export GOLDEN_DB_HOME=/opt/db/golden
```

### 5. Final setup and run
Make necessary adjustments in `export-data.sh` and `restore-data.sh`:
```
SERVER_NAME=
USERNAME="sa"
PASSWORD="fake.fake.fake.1234"
```
Do not change the `DATABASE_NAME` variable.

#### Extract PII/PHI data
Navigate to the GoldenDB home directory and create a data directory:
```
cd /opt/db/golden
mkdir data
./export-data.sh odse
./export-data.sh msgoute
```
This will create CSV files for each Golden DB table containing potentially sensitive information for de-identification.

#### Run CliniDeID
Execute CliniDeID tool on the extracted data:
```
./runCliniDeID.sh
```

#### Restore data
Repopulate Golden DB with anonymized data:
```
./restore-data.sh odse
./restore-data.sh msgoute
```
