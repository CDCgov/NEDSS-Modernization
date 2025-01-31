

USE [RDB]
GO


IF OBJECT_ID('rdb.dbo.sp_CLEAR_INV_SUMMARY_DATAMART', 'P') IS NOT NULL
BEGIN
DROP PROCEDURE [sp_CLEAR_INV_SUMMARY_DATAMART];
END;
GO


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE  PROCEDURE [dbo].[sp_CLEAR_INV_SUMMARY_DATAMART]
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
           ,'INV_SUMMARY_DATAMART'
           ,'RDB.sp_CLEAR_INV_SUMMARY_DATAMART'
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
    COMMIT TRANSACTION;
	
	
	select @batch_start_time = batch_start_dttm,@batch_end_time = batch_end_dttm
	from [dbo].[job_batch_log]
	 where status_type = 'start'
     ;


BEGIN TRANSACTION ;

				IF OBJECT_ID('[dbo].[TMP_UPDATED_INV_WITH_NOTIF]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[TMP_UPDATED_INV_WITH_NOTIF]
				;

				IF OBJECT_ID('[dbo].[TMP_PATIENT_LOCATION_KEYS_INIT]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[TMP_PATIENT_LOCATION_KEYS_INIT]
				;

				IF OBJECT_ID('[dbo].[TMP_CONFIRMATION_METHOD_BASE]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[TMP_CONFIRMATION_METHOD_BASE]
				;

				IF OBJECT_ID('[dbo].[TMP_CONFIRMATION_METHOD_PIVOT]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[TMP_CONFIRMATION_METHOD_PIVOT]
				;

				IF OBJECT_ID('[dbo].[tmp_PATIENT_LOCATION_KEYS]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[tmp_PATIENT_LOCATION_KEYS]
				;

				IF OBJECT_ID('[dbo].[tmp_PATIENT_INFO]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[tmp_PATIENT_INFO]
				;

				IF OBJECT_ID('[dbo].[tmp_PHYSICIAN_INFO]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[tmp_PHYSICIAN_INFO]
				;

				IF OBJECT_ID('[dbo].[tmp_PATIENT_DETAILS]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[tmp_PATIENT_DETAILS]
				;

				IF OBJECT_ID('[dbo].[TMP_S_INV_SUMM_DATAMART_INIT]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[TMP_S_INV_SUMM_DATAMART_INIT]
				;

				IF OBJECT_ID('[dbo].[TMP_InvLab]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[TMP_InvLab]
				;

				IF OBJECT_ID('[dbo].[TMP_Lab]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[TMP_Lab]
				;

				IF OBJECT_ID('[dbo].[TMP_BothTable]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[TMP_BothTable]
				;

				IF OBJECT_ID('[dbo].[TMP_Inv2Labs]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[TMP_Inv2Labs]
				;

				IF OBJECT_ID('[dbo].[TMP_SPECIMEN_COLLECTION]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[TMP_SPECIMEN_COLLECTION]
				;

				IF OBJECT_ID('[dbo].[TMP_CASE_LAB_DATAMART_MODIFIED]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[TMP_CASE_LAB_DATAMART_MODIFIED]
				;

				IF OBJECT_ID('[dbo].[TMP_INV_SUMM_DATAMART]', 'U') IS NOT NULL 
				DROP TABLE rdb.[dbo].[TMP_INV_SUMM_DATAMART]
				;



COMMIT TRANSACTION ;

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
           'INV_SUMMARY_DATAMART'
           ,'RDB.sp_CLEAR_INV_SUMMARY_DATAMART'
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
           ,'INV_SUMMARY_DATAMART'
           ,'RDB.sp_CLEAR_INV_SUMMARY_DATAMART'
		   ,'ERROR'
		   ,@Proc_Step_no
		   ,'ERROR - '+ @Proc_Step_name
           , 'Step -' +CAST(@Proc_Step_no AS VARCHAR(3))+' -' +CAST(@ErrorMessage AS VARCHAR(500))
           ,0
		   );
  

      return -1 ;

	END CATCH
	
END

;






