# build all script, ported from .sh files

$BUILD_DB = $false
$BUILD_CLASSIC = $false
$BUILD_MODERNIZED = $true

# load env
.\check_env.ps1

# build DB

if ($BUILD_DB) {
    .\db\build_db.ps1
}

# build classic

if ($BUILD_CLASSIC) {
    $CLASSIC_PATH = ".\nbs-classic\builder\NEDSSDev"
    $CLASSIC_VERSION = "NBS_6.0.16"

    Write-Host "Building NBS6 Application"

    if (Test-Path $CLASSIC_PATH) {
      Remove-Item -Recurse -Force $CLASSIC_PATH
    }
    try {
      # create NEDSSDev folder
      New-Item -ItemType Directory -Force -Path $CLASSIC_PATH
      # clone repo into NEDSSDev folder
      git clone -b $CLASSIC_VERSION git@github.com:cdcent/NEDSSDev.git $CLASSIC_PATH
      # build the classic application image
      docker compose -f ./docker-compose.yml up wildfly --build -d
      # Remove-Item -Recurse -Force $CLASSIC_PATH
    } catch {
      Write-Host "An error occurred during the classic build process: $_"
      exit 1
    }

    Write-Host "**** Classic build complete ****"
    Write-Host "http://localhost:7001/nbs/login"
    Write-Host ""
    Write-Host "**** Available users ****"
    Write-Host "*`tmsa"
    Write-Host "*`tsuperuser"
    Write-Host ""
}

# build modernized

if ($BUILD_MODERNIZED) {
    Write-Host "Building modernized services"

    # Start ES and the proxy
    Write-Host "Starting elasticsearch reverse-proxy"
    docker compose -f ./docker-compose.yml up elasticsearch --build -d

    # Start of modernization-api initializes the elasticserach indices 
    Write-Host "Starting modernized application services"
    docker compose -f ../docker-compose.yml up --build -d

    # NiFi handles synchronizing data between nbs-mssql and elasticsearch
    Write-Host "Starting NiFi"
    docker compose -f ./docker-compose.yml up nifi --build -d

    # Process complete
    Write-Host "**** Modernized application startup complete ****"
    Write-Host "http://localhost:8080/nbs/login"
    Write-Host ""
    Write-Host "**** Available users ****"
    Write-Host "*\tmsa"
    Write-Host "*\tsuperuser"
    Write-Host ""
}
