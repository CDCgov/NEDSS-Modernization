@echo on
set date2=%date:~10,4%%date:~4,2%%date:~7,2%
@rem execute the SAS PHDC Source data population program 
%SAS_HOME%\sas.exe -sysin D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\phdc\src\PHDC_Source.sas -nosyntaxcheck -print D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\PHDC_Source.lst -log D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\PHDC_Source.log -config  %SAS_HOME%\SASV9.CFG -autoexec D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\autoexec.sas


















