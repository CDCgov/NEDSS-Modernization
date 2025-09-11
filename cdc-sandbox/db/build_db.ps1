# load and validate envorinment variables from .env file
Write-Host "Building Database"
$clean = $false

if ($clean) {
    Write-Host "Cleaning up"
    docker compose stop nbs-mssql
    docker compose rm -f nbs-mssql
    docker volume rm -f cdc-sandbox-nbs-mssql-data
}

Write-Host "Building SQL Server"
Write-Host "DATABASE_USER=$env:DATABASE_USER"
Write-Host "DATABASE_PASSWORD=$env:DATABASE_PASSWORD"

$env:DOCKER_DEFAULT_PLATFORM = "linux/amd64"
docker compose -f ./docker-compose.yml up nbs-mssql --build -d
