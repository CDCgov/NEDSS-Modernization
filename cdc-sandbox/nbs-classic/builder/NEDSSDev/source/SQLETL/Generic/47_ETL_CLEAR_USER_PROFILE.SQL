USE [RDB]
GO


IF OBJECT_ID('rdb.dbo.sp_CLEAR_USER_PROFILE', 'P') IS NOT NULL
BEGIN
DROP PROCEDURE [sp_CLEAR_USER_PROFILE];
END;
GO


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE  PROCEDURE [dbo].[sp_CLEAR_USER_PROFILE]
@Batch_id BIGINT
as


BEGIN
    DECLARE @RowCount_no INT ;
    DECLARE @Proc_Step_no FLOAT = 0 ;
    DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
	DECLARE @batch_start_time datetime2(7) = null ;
	DECLARE @batch_end_time datetime2(7) = null ;
  
  BEGIN TRY
    
	SET @Proc_Step_no = 1;
	SET @Proc_Step_Name = 'SP_Start';

	
	BEGIN TRANSACTION;
	
    INSERT INTO rdb.[dbo].[job_flow_log] (
	        batch_id
		   ,[Dataflow_Name]
		   ,[package_Name]
		    ,[Status_Type] 
           ,[step_number]
           ,[step_name]
           ,[row_count]
           )
		   VALUES
           (
		   @Batch_id
           ,'USER_PROFILE'
           ,'RDB.CLEAR_USER_PROFILE'
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
    COMMIT TRANSACTION;
	
	
	select @batch_start_time = batch_start_dttm,@batch_end_time = batch_end_dttm
	from [dbo].[job_batch_log]
	 where type_code='MasterETL'
		 and status_type = 'start'
     ;


BEGIN TRANSACTION ;
--------------------------------------Dropping all tmp tables.------------------------------
	
IF OBJECT_ID('rdb.dbo.TMP_PROVIDER_USER_DIMENSION', 'U') IS NOT NULL  ---Step1 
 	drop table rdb.dbo.TMP_PROVIDER_USER_DIMENSION;
IF OBJECT_ID('rdb.dbo.TMP_USER_PROVIDER', 'U') IS NOT NULL   ------------Step2
 	 drop table rdb.dbo.TMP_USER_PROVIDER;
IF OBJECT_ID('rdb.dbo.TMP_USER_PROVIDER_KEY', 'U') IS NOT NULL --------Step3  
 	drop table rdb.dbo.TMP_USER_PROVIDER_KEY;
IF OBJECT_ID('rdb.dbo.TMP_USER_PROFILE', 'U') IS NOT NULL   ------------Step4
 	drop table rdb.dbo.TMP_USER_PROFILE;
IF OBJECT_ID('rdb.dbo.USER_PROFILE_FINAL', 'U') IS NOT NULL   
 	drop table rdb.dbo.USER_PROFILE_FINAL;
----------------------------------------------------------------------------------------------------------------		   
COMMIT TRANSACTION;
BEGIN TRANSACTION ;
	SET @Proc_Step_no = 999;
	SET @Proc_Step_Name = 'SP_COMPLETE'; 


	INSERT INTO rdb.[dbo].[job_flow_log] (
		    batch_id
			,[Dataflow_Name]
		   ,[package_Name]
		    ,[Status_Type] 
           ,[step_number]
           ,[step_name]
           ,[row_count]
           )
		   VALUES
           (
		   @Batch_id,
           'USER_PROFILE'
           ,'RDB.CLEAR_USER_PROFILE'
		   ,'COMPLETE'
		   ,@Proc_Step_no
		   ,@Proc_Step_name
           ,@RowCount_no
		   );
  
	
	COMMIT TRANSACTION;
  END TRY

  BEGIN CATCH
  
     
     IF @@TRANCOUNT > 0   ROLLBACK TRANSACTION;
 
  
	
	DECLARE @ErrorNumber INT = ERROR_NUMBER();
    DECLARE @ErrorLine INT = ERROR_LINE();
    DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
    DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
    DECLARE @ErrorState INT = ERROR_STATE();
 
	
    INSERT INTO rdb.[dbo].[job_flow_log] (
		    batch_id
		   ,[Dataflow_Name]
		   ,[package_Name]
		    ,[Status_Type] 
           ,[step_number]
           ,[step_name]
           ,[Error_Description]
		   ,[row_count]
           )
		   VALUES
           (
           @Batch_id
           ,'USER_PROFILE'
           ,'RDB.S_USER_PROFILE'
		   ,'ERROR'
		   ,@Proc_Step_no
		   ,'ERROR - '+ @Proc_Step_name
           , 'Step -' +CAST(@Proc_Step_no AS VARCHAR(3))+' -' +CAST(@ErrorMessage AS VARCHAR(500))
           ,0
		   );
  

      return -1 ;

	END CATCH
	
END
	
