# load and validate environment variables from .env file

$BaseEnvPath = ".\.env"
$RootEnvPath = "..\.env"

# load env
function Load-EnvFile {
  param (
    [string]$path
  )

  if (Test-Path -Path $path) {
    Get-Content $path | ForEach-Object {
      # "^\s*([^#][^=]*)\s*=\s*(.*)\s*$"
      # '^\s*([^#][^=]*)\s*=\s*"?([^"]*)"?\s*$'
      if ($_ -match '^\s*([^#][^=]*)\s*=\s*"?([^"]*)"?\s*$') {
        $name = $matches[1]
        $value = $matches[2]
        [System.Environment]::SetEnvironmentVariable($name, $value)
      }
    }
  } else {
    Write-Host "$path file not found"
    exit 1
  }
}

Load-EnvFile -path $RootEnvPath
Load-EnvFile -path $BaseEnvPath

# validate env
if (-not $env:DATABASE_PASSWORD -or -not $env:NIFI_PASSWORD -or -not $env:KEYCLOAK_ADMIN_PASSWORD -or -not $env:TOKEN_SECRET -or -not $env:PARAMETER_SECRET) {
  Write-Host "DATABASE_PASSWORD, NIFI_PASSWORD, KEYCLOAK_ADMIN_PASSWORD, TOKEN_SECRET, and PARAMETER_SECRET are required"
  Write-Host "DATABASE_PASSWORD: $env:DATABASE_PASSWORD"
  Write-Host "NIFI_PASSWORD: $env:NIFI_PASSWORD"
  Write-Host "KEYCLOAK_ADMIN_PASSWORD: $env:KEYCLOAK_ADMIN_PASSWORD"
  Write-Host "PARAMETER_SECRET: <<$env:PARAMETER_SECRET>>"
  Write-Host "TOKEN_SECRET: <<$env:TOKEN_SECRET>>"
  exit 1
} else {
  Write-Host "DATABASE_PASSWORD: <<$env:DATABASE_PASSWORD>>"
  Write-Host "NIFI_PASSWORD: <<$env:NIFI_PASSWORD>>"
  Write-Host "KEYCLOAK_ADMIN_PASSWORD: <<$env:KEYCLOAK_ADMIN_PASSWORD>>"
  Write-Host "PARAMETER_SECRET: <<$env:PARAMETER_SECRET>>"
  Write-Host "TOKEN_SECRET: <<$env:TOKEN_SECRET>>"

  # supplemental env
  Write-Host "DATABASE_USER=$env:DATABASE_USER"
  Write-Host "NBS_DATASOURCE_SERVER=$env:NBS_DATASOURCE_SERVER"
  Write-Host "CLASSIC_SERVICE=$env:CLASSIC_SERVICE"
  Write-Host "MODERNIZATION_SERVICE=$env:MODERNIZATION_SERVICE"
  Write-Host "UI_SERVICE=$env:UI_SERVICE"
  Write-Host "PAGEBUILDER_SERVICE=$env:PAGEBUILDER_SERVICE"
  Write-Host "NBS_SECURITY_OIDC_ENABLED=$env:NBS_SECURITY_OIDC_ENABLED"
  Write-Host "NBS_SECURITY_OIDC_URI=$env:NBS_SECURITY_OIDC_URI"
  Write-Host "PAGEBUILDER_ENABLED=$env:PAGEBUILDER_ENABLED"
}