# ./entrypoint.ps1
# Prepare NBS Configuration and Start NBS 6.0

# Set environment memory allocation (override standalone.conf.bat)
$setJAVA_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n -Dprogram.name=standalone.bat"
$setJAVA_OPTS="%JAVA_OPTS% -Xms%JAVA_MEMORY% -Xmx%JAVA_MEMORY% -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Xss4m"
$setJAVA_OPTS="%JAVA_OPTS% -Djava.net.preferIPv4Stack=true"
$setJAVA_OPTS="%JAVA_OPTS% -Djboss.modules.system.pkgs=org.jboss.byteman"


# Initialize hastable for data sources
# NOTE: Provide DATABASE_ENDPOINT when running Container

$connectionURLs = @{
    "NedssDS" = "jdbc:sqlserver://DATABASE_ENDPOINT:1433;SelectMethod=direct;DatabaseName=nbs_odse";
    "MsgOutDS" = "jdbc:sqlserver://DATABASE_ENDPOINT:1433;SelectMethod=direct;DatabaseName=nbs_msgoute";
    "ElrXrefDS" = "jdbc:sqlserver://DATABASE_ENDPOINT:1433;SelectMethod=direct;DatabaseName=nbs_msgoute";
    "RdbDS" = "jdbc:sqlserver://DATABASE_ENDPOINT:1433;SelectMethod=direct;DatabaseName=rdb";
    "SrtDS" = "jdbc:sqlserver://DATABASE_ENDPOINT:1433;SelectMethod=direct;DatabaseName=nbs_srte"
}


$keys = $connectionURLs.Keys.Clone()

foreach ($key in $keys) {
    $connectionURLs[$key] = $connectionURLs[$key] -replace "DATABASE_ENDPOINT", $env:DATABASE_ENDPOINT
}

# Replace datasources in standalone.xml file
$xmlFileName = "D:\wildfly-10.0.0.Final\nedssdomain\configuration\standalone.xml"

# Create a XML document
[xml]$xmlDoc = New-Object system.Xml.XmlDocument

# Read the existing XML file
[xml]$xmlDoc = Get-Content $xmlFileName

# Search and replace db host name in connection URL
$subsystems = $xmlDoc.server.profile.subsystem
$subsystems | % {
    if ($_.xmlns -eq "urn:jboss:domain:datasources:4.0") {
        $datsources = $_.datasources.datasource
        $datsources | % {
            if ( $connectionURLs.ContainsKey($_.'pool-name')) {
                $_.'connection-url' =  $connectionURLs[$_.'pool-name']
            }
        }
    }
}

# Save XML file after connection url replacement
$xmlDoc.Save($xmlFileName)

############# Configure User Guide #############
# NOTE: Verify NBS User Training Guide.pdf is located in release zip file

# If $env:GITHUB_RELEASE_TAG null or latest, then choose ../releases/latest URL
if (-not $env:GITHUB_RELEASE_TAG -or $env:GITHUB_RELEASE_TAG -eq "latest") {
    Write-Output "Getting $env:GITHUB_RELEASE_TAG Release URL"
    # Fetch the latest release
    $githubRelease = Invoke-RestMethod -Uri "https://api.github.com/repos/CDCgov/NEDSS-Modernization/releases/latest"
    # Output the latest tag name
    $releaseTag = $githubRelease.tag_name
  } else {
    "Getting $env:GITHUB_RELEASE_TAG Release URL"
    # Fetch the latest release
    $githubRelease = Invoke-RestMethod -Uri "https://api.github.com/repos/CDCgov/NEDSS-Modernization/releases/tags/$env:GITHUB_RELEASE_TAG"
    # Output the latest tag name
    $releaseTag = $githubRelease.tag_name
  }


Write-Output "Release Tag to Download Zip From: $releaseTag"

# Update Variables
$zip_file_name = $env:GITHUB_ZIP_FILE_NAME -replace "<version>", $releaseTag
# Set Zip File Name
$zip_url = ($githubRelease.assets | Where-Object { $_.name -like "$zip_file_name" }).browser_download_url
# Download Zip File. System.Net.WebClient is faster then using Invoke-WebRequest
$webClient = New-Object System.Net.WebClient
$webClient.DownloadFile($zip_url, $zip_file_name)
Write-Output "Downloaded Zip File: $zip_file_name"
# Extract ZIP to temporary directory
Expand-Archive -LiteralPath "$zip_file_name" -Force
$zip_folder = $zip_file_name.Trim(".zip")
# Get User Guide Name
$user_guide_name = $(Get-ChildItem -Path $zip_folder\$zip_folder\*"User Guide.pdf" -Recurse).Name
# Move zip file the final destination
$zip_user_guide_path = Join-Path -Path $zip_folder -ChildPath "$zip_folder\$user_guide_name"
$user_guide_directory = "D:\wildfly-10.0.0.Final\nedssdomain\Nedss\UserGuide\$env:FINAL_NBS_USER_GUIDE_NAME"
Write-Output "Moving and Renaming User Guide '$user_guide_name' to '$env:FINAL_NBS_USER_GUIDE_NAME'"
Copy-Item -Path "$zip_user_guide_path" -Destination "$user_guide_directory" -Force
# Cleanup
Remove-Item $zip_file_name
Remove-Item $zip_folder -Recurse -Force -Confirm:$false
#### END OF Configure User Guide ####

# AWS Implementation *********************************************************
# $string1 = (ipconfig | Where-Object {$_ -match 'taskbr'})
# $string2 = ($string1 -split '\(',2)[-1]
# $string3 = ($string2 -split '\)',2)[0]
# $ethernet_name = "vEthernet ($string3)"
# Set-NetIPInterface $ethernet_name -InterfaceMetric 10
# ****************************************************************************

Start-Process "D:\\wildfly-10.0.0.Final\\bin\\standalone.bat" -Wait -NoNewWindow -PassThru | Out-Host
