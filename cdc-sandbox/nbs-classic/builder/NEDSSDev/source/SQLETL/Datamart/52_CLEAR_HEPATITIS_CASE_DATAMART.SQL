USE [RDB];
 
GO

   
IF OBJECT_ID('rdb.dbo.sp_CLEAR_HEPATITIS_CASE_DATAMART', 'P') IS NOT NULL
BEGIN
DROP PROCEDURE [sp_CLEAR_HEPATITIS_CASE_DATAMART];
END;
GO


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE  PROCEDURE [dbo].[sp_CLEAR_HEPATITIS_CASE_DATAMART]
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
           ,'HEPATITIS_CASE_DATAMART'
           ,'RDB.sp_CLEAR_HEPATITIS_CASE_DATAMART'
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

			IF OBJECT_ID('rdb.dbo.HEPATITIS_DATAMART_LAST', 'U') IS NOT NULL
			BEGIN  -------------2 
				DROP TABLE rdb.dbo.HEPATITIS_DATAMART_LAST;
			END;
 
			IF OBJECT_ID('rdb.dbo.Update_Patient_Cases', 'U') IS NOT NULL
			BEGIN   -----------3
				DROP TABLE rdb.dbo.Update_Patient_Cases;
			END;
 
			IF OBJECT_ID('rdb.dbo.Updated_Hep_Patient', 'U') IS NOT NULL
			BEGIN   -------------4
				DROP TABLE rdb.dbo.Updated_Hep_Patient;
			END;
 
			IF OBJECT_ID('rdb.dbo.Updated_Hep_PHYSICIAN', 'U') IS NOT NULL
			BEGIN   -----------5
				DROP TABLE rdb.dbo.Updated_Hep_PHYSICIAN;
			END;
 
			IF OBJECT_ID('rdb.dbo.Updated_Hep_INVESTIGATOR', 'U') IS NOT NULL
			BEGIN --------6  
				DROP TABLE rdb.dbo.Updated_Hep_INVESTIGATOR;
			END;
 
			IF OBJECT_ID('rdb.dbo.Updated_Hep_REPORTING', 'U') IS NOT NULL
			BEGIN   ---------7
				DROP TABLE rdb.dbo.Updated_Hep_REPORTING;
			END;
 
			IF OBJECT_ID('rdb.dbo.EXISTING_HEPATITIS_DATAMART', 'U') IS NOT NULL
			BEGIN   -----8
				DROP TABLE rdb.dbo.EXISTING_HEPATITIS_DATAMART;
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_CONDITION', 'U') IS NOT NULL
			BEGIN   -------------------9
				DROP TABLE rdb.dbo.TMP_CONDITION;
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_PAGE_CASE', 'U') IS NOT NULL
			BEGIN  -------------10 
				DROP TABLE rdb.dbo.TMP_F_PAGE_CASE;
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_ADMINISTRATIVE', 'U') IS NOT NULL
			BEGIN   -----11
				DROP TABLE rdb.dbo.TMP_F_INV_ADMINISTRATIVE;
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_ADMINISTRATIVE', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_ADMINISTRATIVE;
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_CLINICAL', 'U') IS NOT NULL
			BEGIN   ------------12
				DROP TABLE rdb.dbo.TMP_F_INV_CLINICAL;
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_CLINICAL', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_CLINICAL;
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_PATIENT_OBS', 'U') IS NOT NULL
			BEGIN   -----------13
				DROP TABLE rdb.dbo.TMP_D_INV_PATIENT_OBS;
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_PATIENT_OBS', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_INV_PATIENT_OBS;
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
			BEGIN   ---------14
				DROP TABLE rdb.dbo.TMP_F_INV_EPIDEMIOLOGY;
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_EPIDEMIOLOGY;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_LAB_FINDING', 'U') IS NOT NULL
			BEGIN   --------15
				DROP TABLE rdb.dbo.TMP_F_INV_LAB_FINDING;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_LAB_FINDING', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_LAB_FINDING;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
			BEGIN   -----16
				DROP TABLE rdb.dbo.TMP_F_INV_MEDICAL_HISTORY;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_MEDICAL_HISTORY;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_MOTHER', 'U') IS NOT NULL
			BEGIN  -----------17 
				DROP TABLE rdb.dbo.TMP_F_INV_MOTHER;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_MOTHER', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_MOTHER;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_RISK_FACTOR', 'U') IS NOT NULL
			BEGIN   -----18
				DROP TABLE rdb.dbo.TMP_F_INV_RISK_FACTOR;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_RISK_FACTOR', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_RISK_FACTOR;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_TRAVEL', 'U') IS NOT NULL
			BEGIN   ----------------19
				DROP TABLE rdb.dbo.TMP_F_INV_TRAVEL;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_TRAVEL', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_TRAVEL;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_VACCINATION', 'U') IS NOT NULL
			BEGIN   ----20
				DROP TABLE rdb.dbo.TMP_F_INV_VACCINATION;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_VACCINATION', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_VACCINATION;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_Patient', 'U') IS NOT NULL
			BEGIN  ----21 
				DROP TABLE rdb.dbo.TMP_D_Patient;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_Investigation', 'U') IS NOT NULL
			BEGIN   ---22
				DROP TABLE TMP_Investigation;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_HEP_PAT_PROV', 'U') IS NOT NULL
			BEGIN ----23  
				DROP TABLE TMP_HEP_PAT_PROV;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INVESTIGATION_REPEAT', 'U') IS NOT NULL
			BEGIN   -----24
				DROP TABLE rdb.dbo.TMP_F_INVESTIGATION_REPEAT;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INVESTIGATION_REPEAT', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INVESTIGATION_REPEAT;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_METADATA_TEST', 'U') IS NOT NULL
			BEGIN   ---25
				DROP TABLE rdb.dbo.TMP_METADATA_TEST;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT', 'U') IS NOT NULL
			BEGIN  -----------------26A
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT;
 			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_DATE_Pivot', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_DATE_Pivot;
 			END;
 
----------------------26b
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_DATE', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_DATE;
 			END;
 
		----------------------26b	
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_DATE_Final', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_DATE_Final;
 			END;
 
----------------------26b
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_NUM_Pivot', 'U') IS NOT NULL
			BEGIN   -----26C
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_NUM_Pivot;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_NUM', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_NUM;
			END;
 
		---------------------------------26C
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_NUM_final', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_NUM_Final;
			END;
 
	-------------------------------26C
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_FINAL1', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_FINAL1;
			END;
 
--------------------------------------26D
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_FINAL', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_FINAL;
			END;
 
-----------------------------------------------------26e
			IF OBJECT_ID('rdb.dbo.TMP_HEPATITIS_CASE_BASE', 'U') IS NOT NULL
			BEGIN    ------27
				DROP TABLE TMP_HEPATITIS_CASE_BASE;
			END;
 
 
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
           'HEPATITIS_CASE_DATAMART'
           ,'RDB.sp_CLEAR_HEPATITIS_CASE_DATAMART'
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
           ,'HEPATITIS_CASE_DATAMART'
           ,'RDB.sp_CLEAR_HEPATITIS_CASE_DATAMART'
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







	