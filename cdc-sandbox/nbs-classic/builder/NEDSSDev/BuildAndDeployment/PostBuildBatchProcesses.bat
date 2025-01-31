@ECHO OFF



:: Move two batch files in the retired folder into the BatchFiles folder.
move /y D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\retired\UserProfileUpdateProcess.bat D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\UserProfileUpdateProcess.bat
move /y D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\retired\DeDuplicationSimilarBatchProcess.bat D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\DeDuplicationSimilarBatchProcess.bat


::Create temporary batch files (to be deleted later)
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
 echo.>"C:\RunMasterETL.bat"
@echo D: >> "C:\RunMasterETL.bat"
@echo cd "D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles" >> "C:\RunMasterETL.bat"
@echo call MasterEtl.bat pks >> "C:\RunMasterETL.bat"


echo.>"C:\UserProfile.bat"
@echo D: >> "C:\UserProfile.bat"
@echo cd "D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles" >> "C:\UserProfile.bat"
@echo call UserProfileUpdateProcess.bat >> "C:\UserProfile.bat"



echo.>"C:\RunDeDuplication.bat"
@echo D: >> "C:\RunDeDuplication.bat"
@echo cd "D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles" >> "C:\RunDeDuplication.bat"
@echo call DeDuplicationSimilarBatchProcess.bat >> "C:\RunDeDuplication.bat"
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

:: Create a scheduled tasks
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
SchTasks /Create /SC DAILY /TN UserProfile /TR C:\UserProfile.bat /ST 09:00
SchTasks /Create /SC DAILY /TN RunDeDuplication /TR C:\RunDeDuplication.bat /ST 09:00
SchTasks /Create /SC DAILY /TN RunMasterETL /TR C:\RunMasterETL.bat /ST 09:00
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::


::Run schtasks and loop to query if complete
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
schtasks /run /tn UserProfile

	:loop1
	for /f "tokens=2 delims=: " %%f in ('schtasks /query /tn UserProfile /fo list ^| find "Status:"' ) do (
	    if "%%f"=="Running" (
	        ping -n 6 localhost >nul 2>nul
	        goto loop1
	    )
	)


schtasks /run /tn RunDeDuplication

	:loop2
	for /f "tokens=2 delims=: " %%f in ('schtasks /query /tn RunDeDuplication /fo list ^| find "Status:"' ) do (
	    if "%%f"=="Running" (
	        ping -n 6 localhost >nul 2>nul
	        goto loop2
	    )
	)


schtasks /run /tn RunMasterETL

	:loop3
	for /f "tokens=2 delims=: " %%f in ('schtasks /query /tn RunMasterETL /fo list ^| find "Status:"' ) do (
	    if "%%f"=="Running" (
	        ping -n 6 localhost >nul 2>nul
	        goto loop3
	    )
	)
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::



::Delete the scheduled tasks
:::::::::::::::::::::::::::::::::::::::::::::
SchTasks /Delete /TN UserProfile /f
SchTasks /Delete /TN RunDeDuplication /f
SchTasks /Delete /TN RunMasterETL /f
:::::::::::::::::::::::::::::::::::::::::::::
::Delete temp bat files
:::::::::::::::::::::::::::::::::::::::::::::
del /F C:\RunMasterETL.bat
del /F C:\UserProfile.bat
del /F C:\RunDeDuplication.bat
:::::::::::::::::::::::::::::::::::::::::::::

::Move the batch files back to the retired folder
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
move /y D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\UserProfileUpdateProcess.bat D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\retired\UserProfileUpdateProcess.bat
move /y D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\DeDuplicationSimilarBatchProcess.bat D:\wildfly-10.0.0.Final\nedssdomain\Nedss\BatchFiles\retired\DeDuplicationSimilarBatchProcess.bat

