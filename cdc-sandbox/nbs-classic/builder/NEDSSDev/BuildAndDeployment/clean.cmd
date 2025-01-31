@echo off
cls
net stop jboss_nedssdomain
call ant nbs.clean
if exist C:\wildfly-10.0.0.Final\nedssdomain\log  rd /s /q C:\wildfly-10.0.0.Final\nedssdomain\log
if exist C:\wildfly-10.0.0.Final\nedssdomain\tmp  rd /s /q C:\wildfly-10.0.0.Final\nedssdomain\tmp
if exist C:\wildfly-10.0.0.Final\nedssdomain\work rd /s /q C:\wildfly-10.0.0.Final\nedssdomain\work
copy /y NEDSS.properties C:\wildfly-10.0.0.Final\nedssdomain\Nedss\Properties\NEDSS.properties
rem net start jboss_nedssdomain
