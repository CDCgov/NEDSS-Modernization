@echo on
set date2=%date:~10,4%%date:~4,2%%date:~7,2%

@rem SQL SERVER SETTINGS
sqlcmd -S localhost -U nbs_rdb -P rdb -d RDB -Q "Exec RDB.dbo.DynDM_Parent_Main_sp"  > D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\DynamicDatamart.log
sqlcmd -S localhost -U nbs_rdb -P rdb -d RDB -Q "Exec RDB.dbo.DynDM_CLEAR_sp" >> D:\wildfly-10.0.0.Final\nedssdomain\Nedss\report\log\DynamicDatamart.log














