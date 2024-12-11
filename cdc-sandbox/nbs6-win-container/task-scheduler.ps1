# ./task-scheduler.ps1
# DI app required task schedule

# Following commands are required to run ELRImporter.bat in Docker Container Windows ServerCore.
# This will add the variables in setenvJBOSS.cmd, allowing Instance Scheduler user to view them
# Path to setenvJBOSS.cmd
$setenvFilePath = "$env:JBOSS_HOME\nedssdomain\Nedss\BatchFiles\setenvJBOSS.cmd"
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

# Following commands are required to run batch jobs in Docker Container Windows ServerCore.
# This will add the variables in setenvJBOSS.cmd, allowing Instance Scheduler user to view them
# The following batch jobs are scheduled in this script. All Batch jobs are presumed to be under D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles
# 1. AHSLogRotate, 2. Mark as Reviewed, 3. Covid-Case, 4. DCIPER, 5. Covid-Celrlab, 6. Covid_Celrlab Rhapsody Report
# 7. USER_PROFILE, 8. nbs_odse nbs_odse_Jurisdiction_Code SP, 9. ELRImporter, 10. UserProfileUpdateProcess
# 11. ALERT_EMAIL, 12. DEDUPLICATION_SIMILAR, 13. MSGOUT (MSGoutProcessor), 14. COVID_Case_DATAMART_REPORT, 15. COVID_LAB_DATAMART_REPORT
# 16. COVID CELR_Repot

# $batchJobFiles = "ELRImporter.bat","AHSLogRotate.bat", "Mark as Reviewed.bat", "Covid-Case.bat", "DCIPER.bat", "Covid-Celrlab.bat", "Covid_Celrlab Rhapsody Report.bat", "USER_PROFILE.bat", "nbs_odse nbs_odse_Jurisdiction_Code SP.bat", "UserProfileUpdateProcess.bat", "ALERT_EMAIL.bat", "DEDUPLICATION_SIMILAR.bat", "MSGOUT.bat", "COVID_Case_DATAMART_REPORT.bat", "COVID_LAB_DATAMART_REPORT.bat", "COVID CELR_Repot.bat" 

#TODO modify DOCKERFILE AND PLACE PATH HERE
$csvData = Import-Csv -Path "C:\tasks.csv"
$WorkingDirectory = "$env:JBOSS_HOME\nedssdomain\Nedss\BatchFiles"

foreach ($row in $csvData) {
    $days=[int]$row.frequencyDays
    $hours=[int]$row.frequencyHours
    $minutes=[int]$row.frequencyMinutes
    $jobName = $row.filename + " Task"
    $repeat = (New-TimeSpan -Days $days -Hours $hours -Minutes $minutes)
    $currentDate= ([DateTime]::Now)
    $duration = $currentDate.AddYears(25) -$currentDate

    # Define the file path
    $relativeScriptPath = "$env:JBOSS_HOME\" + $row.dirpath
    $filename = $row.filename
    $filename_noext = $filename.split('.')[0]
    $scriptPathFromWorkDir = ".\" + $row.scriptPathFromWorkDir + $row.filename
    $argument = "> " + $filename_noext + ".output 2>&1"
    
    $principal = New-ScheduledTaskPrincipal -UserId "NT AUTHORITY\SYSTEM" -LogonType S4U
    # Action to run the specified batch file
    $action = New-ScheduledTaskAction -Execute "$scriptPathFromWorkDir" -Argument "$argument" -WorkingDirectory "$WorkingDirectory"
    # Trigger for daily execution once, repeating every 2 minutes
    $trigger = New-ScheduledTaskTrigger -Once -At $row.startTime -RepetitionInterval $repeat -RepetitionDuration $duration
    # Create scheduled task
    $settings = New-ScheduledTaskSettingsSet -AllowStartIfOnBatteries -DontStopIfGoingOnBatteries -StartWhenAvailable -RunOnlyIfNetworkAvailable -DontStopOnIdleEnd
    # Register the scheduled task
    Register-ScheduledTask -TaskName $jobName -Action $action -Trigger $trigger -Principal $principal -Settings $settings

    Write-Output "Scheduled task $WorkingDirectory\" + $row.scriptPathFromWorkDir + $row.filename
}

# Batch Files Name
# MasterETL ---
# AHSLogRotate
# Mark As Reviewed
# Covid-Case
# Covid-Lab
# DCIPER
# Covid-Celrlab
# Covid_Celrlab Rhapsody Report
# USER_PROFILE
# nbs_odse nbs_odse_Jurisdiction_Code SP 
# PHCMartETL.bat ---
# DynamicDataMart.bat ---
# ELRImporter.bat - ELRImporter.bat

# UserProfileUpdateProcess - retired\UserProfileUpdateProcess.bat

# ALERT_EMAIL  - retired\SendAlertEMail.bat

# DEDUPLICATION_SIMILAR - retired\DeDuplicationSimilarBatchProcess.bat

# MSGOUT - MsgOutProcessor.bat

# COVID_Case_DATAMART_REPORT
# COVID_LAB_DATAMART_REPORT
# COVID CELR Repot
# covid19ETL.bat













################################################################
# $jobName = "ELRImporter Task"
# $repeat = (New-TimeSpan -Minutes 2)
# $currentDate= ([DateTime]::Now)
# $duration = $currentDate.AddYears(25) -$currentDate
# # Define the file path
# $scriptDirPath = "D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles"
# $scriptPath = ".\ELRImporter.bat"
# $argument = "> ElrImporter.output 2>&1"
# $principal = New-ScheduledTaskPrincipal -UserId "NT AUTHORITY\SYSTEM" -LogonType S4U
# # Action to run the specified batch file
# $action = New-ScheduledTaskAction -Execute "$scriptPath" -Argument "$argument" -WorkingDirectory "$scriptDirPath"
# # Trigger for daily execution once, repeating every 2 minutes
# $trigger = New-ScheduledTaskTrigger -Once -At (Get-Date) -RepetitionInterval $repeat -RepetitionDuration $duration
# # Create scheduled task
# $settings = New-ScheduledTaskSettingsSet -AllowStartIfOnBatteries -DontStopIfGoingOnBatteries -StartWhenAvailable -RunOnlyIfNetworkAvailable -DontStopOnIdleEnd
# # Register the scheduled task
# Register-ScheduledTask -TaskName $jobName -Action $action -Trigger $trigger -Principal $principal -Settings $settings
################################################################