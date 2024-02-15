# ./entrypoint.ps1
# Prepare NBS Configuration and Start NBS 6.0

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
$xmlFileName = "C:\nbs\wildfly-10.0.0.Final\nedssdomain\configuration\standalone.xml"

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

if ($env:GITHUB_RELEASE_TAG -eq 'latest') {
    $github_api_Url = "https://api.github.com/repos/CDCgov/NEDSS-Modernization/releases/latest"
  } else {
    $github_api_Url = "https://api.github.com/repos/CDCgov/NEDSS-Modernization/releases/tags/$env:GITHUB_RELEASE_TAG"
  }

# Fetch the latest release
$githubRelease = Invoke-RestMethod -Uri $env:github_api_Url
# Output the latest tag name
$releaseTag = $githubRelease.tag_name
Write-Output "Release Tag to Download Zip From: $releaseTag"
# Set Zip File Name
$zip_url = ($githubRelease.assets | Where-Object { $_.name -eq $env:GITHUB_ZIP_FILE_NAME }).browser_download_url
# Download Zip File. System.Net.WebClient is faster then using Invoke-WebRequest
$webClient = New-Object System.Net.WebClient
$webClient.DownloadFile($zip_url, $env:GITHUB_ZIP_FILE_NAME)
Write-Output "Downloaded Zip File: $env:GITHUB_ZIP_FILE_NAME"
# Extract ZIP to temporary directory
Expand-Archive -LiteralPath "$env:GITHUB_ZIP_FILE_NAME" -Force
$zip_folder = $env:GITHUB_ZIP_FILE_NAME.Trim(".zip")
# Move zip file the final destination
$zip_user_guide_path = Join-Path -Path $zip_folder -ChildPath "$zip_folder\$env:USER_GUIDE_DOC_NAME"
$user_guide_directory = "C:\nbs\wildfly-10.0.0.Final\nedssdomain\Nedss\UserGuide\$env:FINAL_NBS_USER_GUIDE_NAME"
Copy-Item -Path "$zip_user_guide_path" -Destination "$user_guide_directory" -Force
# Cleanup
Remove-Item $env:GITHUB_ZIP_FILE_NAME
Remove-Item $zip_folder -Recurse -Force -Confirm:$false
#### END OF Configure User Guide ####

#### DI app required task schedule ####
# Following commands are required to run ELRImporter.bat in Docker Container Windows ServerCore.
# This will add the variables in setenvJBOSS.cmd, allowing Instance Scheduler user to view them
# Path to setenvJBOSS.cmd
$setenvFilePath = "C:\nbs\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\setenvJBOSS.cmd"
# Read the current content
$currentContent = Get-Content -Path $setenvFilePath
# String to prepend
$setEchoOff = "@echo off"
$setJAVA_HOME = "set JAVA_HOME=$env:JAVA_HOME"
$setJBOSS_HOME = "set JBOSS_HOME=$env:JBOSS_HOME"
$setJAVA_TOOL_OPTIONS = "set JAVA_TOOL_OPTIONS=$env:JAVA_TOOL_OPTIONS"
# Combine the new strings with the current content, keeping variables at the top of setenvJBOSS.cmd
$newContent = $setEchoOff, $setJAVA_HOME, $setJBOSS_HOME, $setJAVA_TOOL_OPTIONS, $currentContent
# Write the new content back to the file
$newContent | Set-Content -Path $setenvFilePath
$jobName = "ELRImporter Task"
$repeat = (New-TimeSpan -Minutes 2)
$currentDate= ([DateTime]::Now)
$duration = $currentDate.AddYears(25) -$currentDate
# Define the file path
$scriptDirPath = "D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles"
$scriptPath = ".\ELRImporter.bat"
$argument = "> ElrImporter.output 2>&1"
$principal = New-ScheduledTaskPrincipal -UserId "NT AUTHORITY\SYSTEM" -LogonType S4U
# Action to run the specified batch file
$action = New-ScheduledTaskAction -Execute "$scriptPath" -Argument "$argument" -WorkingDirectory "$scriptDirPath"
# Trigger for daily execution once, repeating every 2 minutes
$trigger = New-ScheduledTaskTrigger -Once -At (Get-Date) -RepetitionInterval $repeat -RepetitionDuration $duration
# Create scheduled task
$settings = New-ScheduledTaskSettingsSet -AllowStartIfOnBatteries -DontStopIfGoingOnBatteries -StartWhenAvailable -RunOnlyIfNetworkAvailable -DontStopOnIdleEnd
# Register the scheduled task
Register-ScheduledTask -TaskName $jobName -Action $action -Trigger $trigger -Principal $principal -Settings $settings
#### END OF TASK SCHEDULES ####


Start-Process "C:\\nbs\\wildfly-10.0.0.Final\\bin\\standalone.bat" -Wait -NoNewWindow -PassThru | Out-Host