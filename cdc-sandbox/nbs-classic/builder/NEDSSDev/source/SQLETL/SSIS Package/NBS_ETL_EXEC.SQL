Declare @execution_id bigint
EXEC [SSISDB].[catalog].[create_execution] 
 @package_name=N'NBS_PageCase_SSIS.dtsx', 
 @execution_id=@execution_id OUTPUT, 
 @folder_name=N'NBS_ETL', 
 @project_name=N'NBS_ETL', 
 @use32bitruntime=False, 
 @reference_id=Null, 
 @runinscaleout=False
 ;


Select @execution_id;

DECLARE @var0 smallint = 1;

EXEC [SSISDB].[catalog].[set_execution_parameter_value] @execution_id, 
  @object_type=50 
  , @parameter_name = N'SYNCHRONIZED'
  , @parameter_value = 1
  ;
 
 
 --@parameter_name=N'LOGGING_LEVEL', 
 --@parameter_value=@var0
 -- ;

EXEC [SSISDB].[catalog].[start_execution] @execution_id

GO



