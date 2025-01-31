USE [RDB]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

IF OBJECT_ID('rdb.dbo.[sp_SLD_ALL]', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_SLD_ALL;
END;
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE  PROCEDURE [dbo].[sp_SLD_ALL]as

BEGIN
    DECLARE @RowCount_no INT ;
    DECLARE @Proc_Step_no FLOAT = 0 ;
    DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
    DECLARE @batch_id BIGINT ;
	DECLARE @batch_start_time datetime2(7) = null ;
	DECLARE @batch_end_time datetime2(7) = null ;
	DECLARE @type_code varchar(100)='MasterETL';
  
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
		   @batch_id
           ,'SLD_ALL'
           ,'RDB.SLD_ALL'
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
    COMMIT TRANSACTION;
	
	DECLARE	@return_value int = 0 ;
	DECLARE	@return_value_ret int;
	declare @ret int;


    -- EXEC	@return_value = rdb.[dbo].[sp_nbs_batch_start];

 
      SELECT	'Return Value TEST ' = @return_value;

	  set @return_value_ret = @return_value;

	  if ( @return_value_ret <> 0 ) 
	    begin
		   select 'no more processing'
	       return @return_value_ret ;
        end
	
	BEGIN TRANSACTION;
		

		EXEC	@return_value = [dbo].[addMissingColumn_SP]
				@tableName = N'VACCINATION'
				SELECT	'Return Value' = @return_value
    COMMIT TRANSACTION;

	
		select @batch_id = batch_id,@batch_start_time = batch_start_dttm,@batch_end_time = batch_end_dttm
		from [dbo].[job_batch_log] where type_code=@type_code
		 and status_type = 'start'
		
		/*LDF */
		print @batch_id
		EXEC  RDB.[dbo].sp_LDF_DIMENSIONAL_DATA @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_LDF_DIMENSIONAL_DATA' ;
		print @batch_id
		EXEC  RDB.[dbo].sp_LDF_GENERIC_DATAMART @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.LDF_GENERIC' ;
		print @batch_id
		EXEC  RDB.[dbo].sp_LDF_HEPATITIS_DATAMART @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.LDF_HEPATITIS' ;
		print @batch_id
		EXEC  RDB.[dbo].sp_LDF_BMIRD_DATAMART @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.LDF_BMIRD' ;
		print @batch_id
		EXEC  RDB.[dbo].sp_LDF_FOODBORNE_DATAMART @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.LDF_FOODBORNE' ;
		print @batch_id
		EXEC  RDB.[dbo].sp_LDF_MUMPS_DATAMART @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.LDF_MUMPS' ;
		print @batch_id
		EXEC  RDB.[dbo].sp_LDF_TETANUS_DATAMART @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.LDF_TETANUS' ;
		print @batch_id
		EXEC  RDB.[dbo].sp_LDF_VACCINE_PREVENT_DISEASES_DATAMART @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.LDF_VACCINE_PREVENT_DISEASES' ;

		/*LAB */
		EXEC  RDB.[dbo].sp_CLEAR_D_Lab_Test @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_D_LabTest_Results @batch_id;
		
		print @batch_id
		EXEC  RDB.[dbo].sp_D_LAB_TEST @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.LAB_TEST' ;
		
		/*MORB */
		EXEC  RDB.[dbo].sp_CLEAR_Morbidity_Report @batch_id;

		print @batch_id
		EXEC  RDB.[dbo].sp_D_Morbidity_Report @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_MORBIDITY_REPORT' ;

		/*LAB_TEST_RESULT*/
		print @batch_id
		EXEC  RDB.[dbo].sp_D_LABTEST_RESULTS @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.LABTEST_RESULTS' ;

		
		/*Vaccination*/
		EXEC RDB.[dbo].sp_VACCINATION_RECORD @batch_id;
		EXEC RDB.[dbo].[sp_nbs_batch_log_activity] @batch_id, @package_Name = N'RDB.sp_VACCINATION_RECORD';
		
		/*lab00*/
		EXEC  RDB.[dbo].sp_CLEAR_Lab100 @batch_id;
		EXEC RDB.[dbo].sp_D_LAB100 @batch_id;
		EXEC RDB.[dbo].[sp_nbs_batch_log_activity] @batch_id, @package_Name = N'RDB.sp_D_LAB100';
		/*lab01*/
		EXEC RDB.[dbo].sp_D_LAB101 @batch_id;
		EXEC RDB.[dbo].[sp_nbs_batch_log_activity] @batch_id, @package_Name = N'RDB.sp_D_LAB101';


		/*Page case*/
		--EXEC  RDB.[dbo].[sp_nbs_batch_start_activity] @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_ADMINISTRATIVE @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_CLINICAL @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_COMPLICATION @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_CONTACT @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_DEATH @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_EPIDEMIOLOGY @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_HIV @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_ISOLATE_TRACKING @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_LAB_FINDING @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_MEDICAL_HISTORY @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_MOTHER @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_OTHER @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_PATIENT_OBS @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_PREGNANCY_BIRTH @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_RESIDENCY @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_RISK_FACTOR @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_SOCIAL_HISTORY @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_SYMPTOM @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_TRAVEL @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_TREATMENT @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_UNDER_CONDITION @batch_id;
		EXEC  RDB.[dbo].sp_CLEAR_INV_VACCINATION @batch_id;

      
		EXEC  RDB.[dbo].sp_S_INV_ADMINISTRATIVE @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_ADMINISTRATIVE';
		EXEC  RDB.[dbo].sp_S_INV_CLINICAL @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_CLINICAL' ;
		EXEC  RDB.[dbo].sp_S_INV_COMPLICATION @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_COMPLICATION' ;
		EXEC  RDB.[dbo].sp_S_INV_CONTACT @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_CONTACT' ;
		EXEC  RDB.[dbo].sp_S_INV_DEATH @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_DEATH' ;
		EXEC  RDB.[dbo].sp_S_INV_EPIDEMIOLOGY @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_EPIDEMIOLOGY' ;
		EXEC  RDB.[dbo].sp_S_INV_HIV @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_HIV' ;
		EXEC  RDB.[dbo].sp_S_INV_ISOLATE_TRACKING @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_ISOLATE_TRACKING' ;
		EXEC  RDB.[dbo].sp_S_INV_LAB_FINDING @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_LAB_FINDING' ;
		EXEC  RDB.[dbo].sp_S_INV_MEDICAL_HISTORY @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_MEDICAL_HISTORY' ;
		EXEC  RDB.[dbo].sp_S_INV_MOTHER @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_MOTHER' ;
		EXEC  RDB.[dbo].sp_S_INV_OTHER @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_OTHER' ;
		EXEC  RDB.[dbo].sp_S_INV_PATIENT_OBS @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_PATIENT_OBS' ;
		EXEC  RDB.[dbo].sp_S_INV_PREGNANCY_BIRTH @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_PREGNANCY_BIRTH' ;
		EXEC  RDB.[dbo].sp_S_INV_RESIDENCY @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_RESIDENCY' ;
		EXEC  RDB.[dbo].sp_S_INV_RISK_FACTOR @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_RISK_FACTOR' ;
		EXEC  RDB.[dbo].sp_S_INV_SOCIAL_HISTORY @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_SOCIAL_HISTORY' ;
		EXEC  RDB.[dbo].sp_S_INV_SYMPTOM @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_SYMPTOM' ;
		EXEC  RDB.[dbo].sp_S_INV_TRAVEL @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_TRAVEL' ;
		EXEC  RDB.[dbo].sp_S_INV_TREATMENT @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_TREATMENT' ;
		EXEC  RDB.[dbo].sp_S_INV_UNDER_CONDITION @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_UNDER_CONDITION' ;
		EXEC  RDB.[dbo].sp_S_INV_VACCINATION @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.S_INV_VACCINATION' ;

 
		EXEC  RDB.[dbo].sp_L_INV_ADMINISTRATIVE @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_ADMINISTRATIVE';
		EXEC  RDB.[dbo].sp_L_INV_CLINICAL @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_CLINICAL' ;
		EXEC  RDB.[dbo].sp_L_INV_COMPLICATION @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_COMPLICATION' ;
		EXEC  RDB.[dbo].sp_L_INV_CONTACT @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_CONTACT' ;
		EXEC  RDB.[dbo].sp_L_INV_DEATH @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_DEATH' ;
		EXEC  RDB.[dbo].sp_L_INV_EPIDEMIOLOGY @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_EPIDEMIOLOGY' ;
		EXEC  RDB.[dbo].sp_L_INV_HIV @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_HIV' ;
		EXEC  RDB.[dbo].sp_L_INV_ISOLATE_TRACKING @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_ISOLATE_TRACKING' ;
		EXEC  RDB.[dbo].sp_L_INV_LAB_FINDING @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_LAB_FINDING' ;
		EXEC  RDB.[dbo].sp_L_INV_MEDICAL_HISTORY @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_MEDICAL_HISTORY' ;
		EXEC  RDB.[dbo].sp_L_INV_MOTHER @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_MOTHER' ;
		EXEC  RDB.[dbo].sp_L_INV_OTHER @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_OTHER' ;
		EXEC  RDB.[dbo].sp_L_INV_PATIENT_OBS @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_PATIENT_OBS' ;
		EXEC  RDB.[dbo].sp_L_INV_PREGNANCY_BIRTH @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_PREGNANCY_BIRTH' ;
		EXEC  RDB.[dbo].sp_L_INV_RESIDENCY @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_RESIDENCY' ;
		EXEC  RDB.[dbo].sp_L_INV_RISK_FACTOR @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_RISK_FACTOR' ;
		EXEC  RDB.[dbo].sp_L_INV_SOCIAL_HISTORY @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_SOCIAL_HISTORY' ;
		EXEC  RDB.[dbo].sp_L_INV_SYMPTOM @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_SYMPTOM' ;
		EXEC  RDB.[dbo].sp_L_INV_TRAVEL @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_TRAVEL' ;
		EXEC  RDB.[dbo].sp_L_INV_TREATMENT @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_TREATMENT' ;
		EXEC  RDB.[dbo].sp_L_INV_UNDER_CONDITION @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_UNDER_CONDITION' ;
		EXEC  RDB.[dbo].sp_L_INV_VACCINATION @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.L_INV_VACCINATION' ;


 
		EXEC  RDB.[dbo].sp_D_INV_ADMINISTRATIVE @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_ADMINISTRATIVE';
		EXEC  RDB.[dbo].sp_D_INV_CLINICAL @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_CLINICAL' ;
		EXEC  RDB.[dbo].sp_D_INV_COMPLICATION @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_COMPLICATION' ;
		EXEC  RDB.[dbo].sp_D_INV_CONTACT @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_CONTACT' ;
		EXEC  RDB.[dbo].sp_D_INV_DEATH @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_DEATH' ;
		EXEC  RDB.[dbo].sp_D_INV_EPIDEMIOLOGY @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_EPIDEMIOLOGY' ;
		EXEC  RDB.[dbo].sp_D_INV_HIV @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_HIV' ;
		EXEC  RDB.[dbo].sp_D_INV_ISOLATE_TRACKING @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_ISOLATE_TRACKING' ;
		EXEC  RDB.[dbo].sp_D_INV_LAB_FINDING @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_LAB_FINDING' ;
		EXEC  RDB.[dbo].sp_D_INV_MEDICAL_HISTORY @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_MEDICAL_HISTORY' ;
		EXEC  RDB.[dbo].sp_D_INV_MOTHER @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_MOTHER' ;
		EXEC  RDB.[dbo].sp_D_INV_OTHER @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_OTHER' ;
		EXEC  RDB.[dbo].sp_D_INV_PATIENT_OBS @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_PATIENT_OBS' ;
		EXEC  RDB.[dbo].sp_D_INV_PREGNANCY_BIRTH @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_PREGNANCY_BIRTH' ;
		EXEC  RDB.[dbo].sp_D_INV_RESIDENCY @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_RESIDENCY' ;
		EXEC  RDB.[dbo].sp_D_INV_RISK_FACTOR @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_RISK_FACTOR' ;
		EXEC  RDB.[dbo].sp_D_INV_SOCIAL_HISTORY @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_SOCIAL_HISTORY' ;
		EXEC  RDB.[dbo].sp_D_INV_SYMPTOM @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_SYMPTOM' ;
		EXEC  RDB.[dbo].sp_D_INV_TRAVEL @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_TRAVEL' ;
		EXEC  RDB.[dbo].sp_D_INV_TREATMENT @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_TREATMENT' ;
		EXEC  RDB.[dbo].sp_D_INV_UNDER_CONDITION @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_UNDER_CONDITION' ;
		EXEC  RDB.[dbo].sp_D_INV_VACCINATION @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.D_INV_VACCINATION' ;

        EXEC  RDB.[dbo].[sp_SLD_INVESTIGATION_REPEAT] @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.SLD_INVESTIGATION_REPEAT' ;
  
 	    EXEC  RDB.[dbo].[sp_F_PAGE_CASE] @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.F_PAGE_CASE' ;

	    EXEC  RDB.[dbo].[sp_F_STD_PAGE_CASE] @batch_id;
		EXEC  RDB.[dbo].[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.F_STD_PAGE_CASE' ;
		
	
		--Only execute sp_STD_HIV_DATAMART stored proc if there is a published STD page 
		DECLARE @COUNTSTD INT;
		SET @COUNTSTD =
		(
			SELECT COUNT(*) AS INT
			FROM NBS_SRTE..Condition_code
			WHERE nnd_entity_identifier = 'STD_Case_Map_v1.0'
				  AND port_req_ind_cd = 'F'
		);
		IF(@COUNTSTD > 0)
			BEGIN
				EXEC RDB.[dbo].[sp_STD_HIV_DATAMART] 
					 @batch_id;
				EXEC RDB.[dbo].[sp_nbs_batch_log_activity] 
					 @batch_id, 
					 @package_Name = N'RDB.sp_STD_HIV_DATAMART';
			END;
			
				   EXEC RDB.[dbo].[sp_CLEAR_CASE_LAB_DATAMART] @batch_id;

		EXEC RDB.[dbo].[sp_CASE_LAB_DATAMART] 
				 @batch_id;
		EXEC RDB.[dbo].[sp_nbs_batch_log_activity] 
				 @batch_id, 
				 @package_Name = N'RDB.CASE_LAB_DATAMART';


	   EXEC RDB.[dbo].[sp_CLEAR_INV_SUMMARY_DATAMART] @batch_id;

		EXEC RDB.[dbo].[sp_INV_SUMMARY_DATAMART] 
				 @batch_id;
		EXEC RDB.[dbo].[sp_nbs_batch_log_activity] 
				 @batch_id, 
				 @package_Name = N'RDB.INV_SUMMARY_DATAMART';
	
	   
		BEGIN
			EXEC RDB.[dbo].[sp_CLEAR_HEPATITIS_CASE_DATAMART] @batch_id;

			EXEC RDB.[dbo].[sp_HEPATITIS_DATAMART] 
				 @batch_id;
			EXEC RDB.[dbo].[sp_nbs_batch_log_activity] 
				 @batch_id, 
				 @package_Name = N'RDB.sp_HEPATITIS_DATAMART';
		END;
		EXEC  RDB.[dbo].sp_CLEAR_ETL_TEMP_TABLES @batch_id;

	   EXEC RDB.[dbo].[sp_nbs_batch_complete] @type_code;
	   	BEGIN
			EXEC RDB.[dbo].[SP_FIX_MORBIDITY_CONDITION] ;
		END;
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
           @batch_id
           ,'SLD_ALL'
           ,'RDB.SLD_ALL'
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


