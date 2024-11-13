# ./task-scheduler.ps1
# DI app required task schedule

# Following commands are required to run ELRImporter.bat in Docker Container Windows ServerCore.
# This will add the variables in setenvJBOSS.cmd, allowing Instance Scheduler user to view them
# Path to setenvJBOSS.cmd
$setenvFilePath = "D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\setenvJBOSS.cmd"
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