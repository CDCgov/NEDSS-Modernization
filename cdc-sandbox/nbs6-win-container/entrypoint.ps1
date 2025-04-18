# ./entrypoint.ps1
# Prepare NBS Configuration and Start NBS 6.0

# Update files needing inputs
if ($null -ne $env:PHCRImporter_user -and $env:PHCRImporter_user -ne "") {
    (Get-Content -Path "$env:JBOSS_HOME\nedssdomain\Nedss\BatchFiles\PHCRImporter.bat" -Raw) -replace "%1  %2", "$env:PHCRImporter_user" | Set-Content -Path "$env:JBOSS_HOME\nedssdomain\Nedss\BatchFiles\PHCRImporter.bat"
}

# Get current csv columns and create new file
$csvRows = Get-Content -Path "C:\tasks.csv" -TotalCount 1
Add-Content -Path "C:\updatedTasks.csv" -Value $csvRows

# Update schedule for environment 
$env:updatedTasks=(Get-ChildItem env:updateScheduledTask_*).Value
if ($null -ne $env:updatedTasks -and $env:updatedTasks -ne "") {
    Add-Content -Path "C:\updatedTasks.csv" -Value ($env:updatedTasks.split(';').replace('"','').replace('''','') | ForEach-Object {$_.Trim()})
}

# Import new csv file and update
$csvDataUpdated = Import-Csv -Path "C:\updatedTasks.csv"

# Modify Triggers
foreach ($row in $csvDataUpdated) { 
    Write-Output "Updating Task: $row.filename"

    $days=[int]$row.frequencyDays
    $hours=[int]$row.frequencyHours
    $minutes=[int]$row.frequencyMinutes
    $jobName = $row.filename
    $repeat = (New-TimeSpan -Days $days -Hours $hours -Minutes $minutes)

    $currentDate= ([DateTime]::Now)
    $duration = $currentDate.AddYears(25) -$currentDate

    $trigger = New-ScheduledTaskTrigger -Once -At $row.startTime -RepetitionInterval $repeat -RepetitionDuration $duration
    Set-ScheduledTask -TaskName $jobName -Trigger $trigger
}

#Disable specific scheduled tasks (all enabled by default)
if ($null -ne $env:DISABLED_SCHEDULED_TASKS -and $env:DISABLED_SCHEDULED_TASKS -ne "") {
    $disabledTasksArray= $env:DISABLED_SCHEDULED_TASKS.split(',') | ForEach-Object {$_.Trim()}
}

foreach ($item in $disabledTasksArray) {
    Write-Output "Disabling TaskName: $item"
    Disable-ScheduledTask -TaskName "$item"
}

# Set environment memory allocation (override standalone.conf.bat)
$env:JAVA_OPTS="-Xms$env:JAVA_MEMORY -Xmx$env:JAVA_MEMORY -XX:MetaspaceSize=96M"
$env:JAVA_OPTS="$env:JAVA_OPTS -XX:MaxMetaspaceSize=$env:MAX_META_SPACE_SIZE -Xss4m -XX:+UseG1GC -XX:+AggressiveOpts"
$env:JAVA_OPTS="$env:JAVA_OPTS -Djava.net.preferIPv4Stack=true"
$env:JAVA_OPTS="$env:JAVA_OPTS -Djboss.modules.system.pkgs=org.jboss.byteman"

# Set global variables and paths
[Environment]::SetEnvironmentVariable("JAVA_HOME", $env:JAVA_HOME, "Machine")
[Environment]::SetEnvironmentVariable("JBOSS_HOME", $env:JBOSS_HOME, "Machine")
[Environment]::SetEnvironmentVariable("JAVA_TOOL_OPTIONS", $env:JAVA_TOOL_OPTIONS, "Machine")
[Environment]::SetEnvironmentVariable("DATABASE_ENDPOINT", $env:DATABASE_ENDPOINT, "Machine")
[Environment]::SetEnvironmentVariable("odse_user", $env:odse_user, "Machine")
[Environment]::SetEnvironmentVariable("odse_pass", $env:odse_pass, "Machine")
[Environment]::SetEnvironmentVariable("rdb_user", $env:rdb_user, "Machine")
[Environment]::SetEnvironmentVariable("rdb_pass", $env:rdb_pass, "Machine")
[Environment]::SetEnvironmentVariable("srte_user", $env:srte_user, "Machine")
[Environment]::SetEnvironmentVariable("srte_pass", $env:srte_pass, "Machine")
[Environment]::SetEnvironmentVariable("JAVA_OPTS", $env:JAVA_OPTS, "Machine")
[Environment]::SetEnvironmentVariable("Path", "$env:Path;C:\executables\sqlcmd", "Machine")

# Initialize hastable for data sources
# NOTE: Provide DATABASE_ENDPOINT when running Container

$connectionURLs = @{
    "NedssDS" = "jdbc:sqlserver://DATABASE_ENDPOINT:1433;SelectMethod=direct;DatabaseName=nbs_odse";
    "MsgOutDS" = "jdbc:sqlserver://DATABASE_ENDPOINT:1433;SelectMethod=direct;DatabaseName=nbs_msgoute";
    "ElrXrefDS" = "jdbc:sqlserver://DATABASE_ENDPOINT:1433;SelectMethod=direct;DatabaseName=nbs_msgoute";
    "RdbDS" = "jdbc:sqlserver://DATABASE_ENDPOINT:1433;SelectMethod=direct;DatabaseName=rdb";
    "SrtDS" = "jdbc:sqlserver://DATABASE_ENDPOINT:1433;SelectMethod=direct;DatabaseName=nbs_srte"
}

$connectionURLs_user = @{ 
    "NedssDS" = "odse_user";                     
    "MsgOutDS" = "odse_user";                    
    "ElrXrefDS" = "odse_user";                     
    "RdbDS" = "rdb_user";                     
    "SrtDS" = "srte_user"
}

$connectionURLs_pass = @{ 
    "NedssDS" = "odse_pass";                     
    "MsgOutDS" = "odse_pass";                     
    "ElrXrefDS" = "odse_pass";                     
    "RdbDS" = "rdb_pass";                     
    "SrtDS" = "srte_pass"
}

$keys = $connectionURLs.Keys.Clone()
foreach ($key in $keys) {
    $connectionURLs[$key] = $connectionURLs[$key] -replace "DATABASE_ENDPOINT", $env:DATABASE_ENDPOINT
}

$keys_user = $connectionURLs_user.Keys.Clone()
foreach ($key in $keys_user) {
    $connectionURLs_user[$key] = $connectionURLs_user[$key] -replace "odse_user", $env:odse_user
    $connectionURLs_user[$key] = $connectionURLs_user[$key] -replace "rdb_user", $env:rdb_user
    $connectionURLs_user[$key] = $connectionURLs_user[$key] -replace "srte_user", $env:srte_user
}

$keys_pass = $connectionURLs_pass.Keys.Clone()
foreach ($key in $keys_pass) {
    $connectionURLs_pass[$key] = $connectionURLs_pass[$key] -replace "odse_pass", $env:odse_pass
    $connectionURLs_pass[$key] = $connectionURLs_pass[$key] -replace "rdb_pass", $env:rdb_pass
    $connectionURLs_pass[$key] = $connectionURLs_pass[$key] -replace "srte_pass", $env:srte_pass
}


# Replace datasources in standalone.xml file
$xmlFileName = "D:\wildfly-10.0.0.Final\nedssdomain\configuration\standalone.xml"

# Create a XML document
[xml]$xmlDoc = New-Object system.Xml.XmlDocument

# Read the existing XML file
[xml]$xmlDoc = Get-Content $xmlFileName

# Search and replace db host name in connection URL
$subsystems = $xmlDoc.server.profile.subsystem
$subsystems | ForEach-Object {
    if ($_.xmlns -eq "urn:jboss:domain:datasources:4.0") {
        $datsources = $_.datasources.datasource
        $datsources | ForEach-Object {
            if ( $connectionURLs.ContainsKey($_.'pool-name')) {
                $_.'connection-url' =  $connectionURLs[$_.'pool-name']
                $_.security.'user-name' = $connectionURLs_user[$_.'pool-name']
                $_.security.password = $connectionURLs_pass[$_.'pool-name']
            }
        }
    }
}

# Save XML file after connection url replacement
$xmlDoc.Save($xmlFileName)

# Update files needing inputs
# update static file nndmConfig.properties
Write-Output "Updating $env:JBOSS_HOME\nedssdomain\Nedss\Properties\nndmConfig.properties"
(Get-Content -Path "$env:JBOSS_HOME\nedssdomain\Nedss\Properties\nndmConfig.properties" -Raw) -replace "PUBLIC_KEY_LDAP_ADDRESS=.*", "$env:NNDM_CONFIG_PUBLIC_KEY_LDAP_ADDRESS" | Set-Content -Path "$env:JBOSS_HOME\nedssdomain\Nedss\Properties\nndmConfig.properties"
(Get-Content -Path "$env:JBOSS_HOME\nedssdomain\Nedss\Properties\nndmConfig.properties" -Raw) -replace "PUBLIC_KEY_LDAP_BASE_DN=.*", "$env:NNDM_CONFIG_PUBLIC_KEY_LDAP_BASE_DN" | Set-Content -Path "$env:JBOSS_HOME\nedssdomain\Nedss\Properties\nndmConfig.properties"
(Get-Content -Path "$env:JBOSS_HOME\nedssdomain\Nedss\Properties\nndmConfig.properties" -Raw) -replace "PUBLIC_KEY_LDAP_DN=.*", "$env:NNDM_CONFIG_PUBLIC_KEY_LDAP_DN" | Set-Content -Path "$env:JBOSS_HOME\nedssdomain\Nedss\Properties\nndmConfig.properties"
(Get-Content -Path "$env:JBOSS_HOME\nedssdomain\Nedss\Properties\nndmConfig.properties" -Raw) -replace "CERTIFICATE_URL=.*", "$env:NNDM_CONFIG_CERTIFICATE_URL" | Set-Content -Path "$env:JBOSS_HOME\nedssdomain\Nedss\Properties\nndmConfig.properties"

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

Start-Process "D:\\wildfly-10.0.0.Final\\bin\\standalone.bat" -Wait -NoNewWindow -PassThru | Out-Host
