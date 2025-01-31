

USE [RDB]
GO


IF OBJECT_ID('rdb.dbo.sp_CLEAR_ETL_TEMP_TABLES', 'P') IS NOT NULL
BEGIN
DROP PROCEDURE [sp_CLEAR_ETL_TEMP_TABLES];
END;
GO


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE  PROCEDURE [dbo].[sp_CLEAR_ETL_TEMP_TABLES]
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
           ,'CLEAR_ETL_TEMP_TABLES'
           ,'RDB.sp_CLEAR_ETL_TEMP_TABLES'
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

				IF OBJECT_ID('[dbo].[ACTIVITY_LOG_MASTER_LAST_SAS]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[ACTIVITY_LOG_MASTER_LAST_SAS]	
					
				IF OBJECT_ID('[dbo].[DIMENSION_KEYS_PAGECASEID]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[DIMENSION_KEYS_PAGECASEID]	

				IF OBJECT_ID('[dbo].[DIMENSIONAL_KEYS]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[DIMENSIONAL_KEYS]	

				IF OBJECT_ID('[dbo].[ENTITY_KEYSTORE_INC]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[ENTITY_KEYSTORE_INC]	

				IF OBJECT_ID('[dbo].[ENTITY_KEYSTORE_STD]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[ENTITY_KEYSTORE_STD]	

				IF OBJECT_ID('[dbo].[F_PAGE_CASE_TEMP_INC]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[F_PAGE_CASE_TEMP_INC]	

				IF OBJECT_ID('[dbo].[F_STD_PAGE_CASE_TEMP_INC]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[F_STD_PAGE_CASE_TEMP_INC]	

				IF OBJECT_ID('[dbo].[INIT]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[INIT]	

				IF OBJECT_ID('[dbo].[NBS_CASE_ANSWER_REPT]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[NBS_CASE_ANSWER_REPT]	

				IF OBJECT_ID('[dbo].[PAGE_DATE_TABLE_REPT]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[PAGE_DATE_TABLE_REPT]	

				IF OBJECT_ID('[dbo].[Phc_keys]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[Phc_keys]	

				IF OBJECT_ID('[dbo].[PHC_UIDS]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[PHC_UIDS]	

				IF OBJECT_ID('[dbo].[phc_uids_REPT]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[phc_uids_REPT]	

				IF OBJECT_ID('[dbo].[RDB_UI_METADATA_REPT]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[RDB_UI_METADATA_REPT]	

				IF OBJECT_ID('[dbo].[rdb_ui_metadata_rept_temp]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[rdb_ui_metadata_rept_temp]	

				IF OBJECT_ID('[dbo].[SAS_PLACE_UID_COLL]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[SAS_PLACE_UID_COLL]	

				IF OBJECT_ID('[dbo].[SAS_S_PLACE_POSTALA]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[SAS_S_PLACE_POSTALA]	

				IF OBJECT_ID('[dbo].[SAS_S_PLACE_TELE]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[SAS_S_PLACE_TELE]	

				IF OBJECT_ID('[dbo].[TABLE_NAME]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[TABLE_NAME]	

				IF OBJECT_ID('[dbo].[TEMP_UPDATED_LAB_INV_MAP]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[TEMP_UPDATED_LAB_INV_MAP]	

				IF OBJECT_ID('[dbo].[TMP_ALL_VAC_PREVENT_DIS]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[TMP_ALL_VAC_PREVENT_DIS]	

				IF OBJECT_ID('[dbo].[TMP_BothTable]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[TMP_BothTable]	

				IF OBJECT_ID('[dbo].[TMP_CASE_LAB_DATAMART_MODIFIED]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[TMP_CASE_LAB_DATAMART_MODIFIED]	

				IF OBJECT_ID('[dbo].[UPDATED_LAB_TEST_LIST]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[UPDATED_LAB_TEST_LIST]	

				IF OBJECT_ID('[dbo].[UPDATED_OBSERVATION_LIST]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[UPDATED_OBSERVATION_LIST]	
				
				IF OBJECT_ID('[dbo].[F_S_STD_HIV_CASE]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[F_S_STD_HIV_CASE]	

				IF OBJECT_ID('[dbo].[PHC_CASE_UIDS_ALL]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[PHC_CASE_UIDS_ALL]	
				
				IF OBJECT_ID('[dbo].[PHC_UIDS_ALL]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[PHC_UIDS_ALL]	
				
				IF OBJECT_ID('[dbo].[F_S_INV_CASE]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[F_S_INV_CASE]	
				
				IF OBJECT_ID('[dbo].[STAGING_KEY_REPT_FINAL]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[STAGING_KEY_REPT_FINAL]	
				
				IF OBJECT_ID('[dbo].[PHC_CASE_UIDS]', 'U') IS NOT NULL
					DROP TABLE rdb.[dbo].[PHC_CASE_UIDS]	
				

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
           'CLEAR_ETL_TEMP_TABLES'
           ,'RDB.sp_CLEAR_ETL_TEMP_TABLES'
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
           ,'CLEAR_ETL_TEMP_TABLES'
           ,'RDB.sp_CLEAR_ETL_TEMP_TABLES'
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






