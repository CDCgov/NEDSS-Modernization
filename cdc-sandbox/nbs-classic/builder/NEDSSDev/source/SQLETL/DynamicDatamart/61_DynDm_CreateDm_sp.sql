
 IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_VARCHAR_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_VARCHAR_ALL;



USE rdb;
GO

--		exec rdb.dbo.DynDm_CreateDm_sp 999,'HIV';

--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_TABLE_NAME  VARCHAR(100) = 'GENERIC_V2';


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


			 IF OBJECT_ID('rdb.dbo.DynDm_CreateDm_sp', 'P') IS NOT NULL
				BEGIN
				DROP PROCEDURE dbo.DynDm_CreateDm_sp;
				END;

GO



CREATE PROCEDURE [dbo].DynDm_CreateDm_sp
 
            @batch_id BIGINT,
			@DATAMART_TABLE_NAME VARCHAR(100)
	AS
BEGIN  
	 BEGIN TRY
	


--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_TABLE_NAME  VARCHAR(100) = 'CONG_SYPHILIS';

		

		
		DECLARE @RowCount_no INT ;
		DECLARE @Proc_Step_no FLOAT = 0 ;
		DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
		DECLARE @batch_start_time datetime = null ;
		DECLARE @batch_end_time datetime = null ;



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
           ,'DYNAMIC_DATAMART'
           ,'RDB.DBO.DynDm_CreateDm_sp '+ @DATAMART_TABLE_NAME
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  


  
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
		SET @Proc_Step_no = @Proc_Step_no + 1;
		SET @Proc_Step_Name = 'CREATING INDEXES';
	
	
			CREATE INDEX idx_tmp_DynDm_INV_SUMM_DATAMART_key  ON rdb.dbo.tmp_DynDm_INV_SUMM_DATAMART (INVESTIGATION_KEY);
		
			CREATE INDEX idx_tmp_DynDm_Investigation_Data_key  ON rdb.dbo.tmp_DynDm_Investigation_Data	 (	INVESTIGATION_KEY_INVESTIGATION_DATA  	);
			CREATE INDEX idx_tmp_DynDm_Patient_Data_key  ON rdb.dbo.tmp_DynDm_Patient_Data	 (	INVESTIGATION_KEY_PATIENT_DATA  	);
			CREATE INDEX idx_tmp_DynDm_Case_Management_Data_key  ON rdb.dbo.tmp_DynDm_Case_Management_Data	 (	INVESTIGATION_KEY_CASE_MANAGEMENT_DATA  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_Administrative_key  ON rdb.dbo.tmp_DynDm_D_INV_Administrative	 (	INVESTIGATION_KEY_D_INV_ADMINISTRATIVE  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_CLINICAL_key  ON rdb.dbo.tmp_DynDm_D_INV_CLINICAL	 (	INVESTIGATION_KEY_D_INV_CLINICAL  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_COMPLICATION_key  ON rdb.dbo. tmp_DynDm_D_INV_COMPLICATION	 (	INVESTIGATION_KEY_D_INV_COMPLICATION  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_CONTACT_key  ON rdb.dbo.tmp_DynDm_D_INV_CONTACT	 (	INVESTIGATION_KEY_D_INV_CONTACT  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_DEATH_key  ON rdb.dbo.tmp_DynDm_D_INV_DEATH	 (	INVESTIGATION_KEY_D_INV_DEATH  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_EPIDEMIOLOGY_key  ON rdb.dbo.tmp_DynDm_D_INV_EPIDEMIOLOGY	 (	INVESTIGATION_KEY_D_INV_EPIDEMIOLOGY  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_HIV_key  ON rdb.dbo.tmp_DynDm_D_INV_HIV	 (	INVESTIGATION_KEY_D_INV_HIV  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_PATIENT_OBS_key  ON rdb.dbo.tmp_DynDm_D_INV_PATIENT_OBS	 (	INVESTIGATION_KEY_D_INV_PATIENT_OBS  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_ISOLATE_TRACKING_key  ON rdb.dbo.tmp_DynDm_D_INV_ISOLATE_TRACKING	 (	INVESTIGATION_KEY_D_INV_ISOLATE_TRACKING  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_LAB_FINDING_key  ON rdb.dbo.tmp_DynDm_D_INV_LAB_FINDING	 (	INVESTIGATION_KEY_D_INV_LAB_FINDING  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_MEDICAL_HISTORY_key  ON rdb.dbo.tmp_DynDm_D_INV_MEDICAL_HISTORY	 (	INVESTIGATION_KEY_D_INV_MEDICAL_HISTORY  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_MOTHER_key  ON rdb.dbo.tmp_DynDm_D_INV_MOTHER	 (	INVESTIGATION_KEY_D_INV_MOTHER  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_OTHER_key  ON rdb.dbo.tmp_DynDm_D_INV_OTHER	 (	INVESTIGATION_KEY_D_INV_OTHER  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_PREGNANCY_BIRTH_key  ON rdb.dbo.tmp_DynDm_D_INV_PREGNANCY_BIRTH	 (	INVESTIGATION_KEY_D_INV_PREGNANCY_BIRTH  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_RESIDENCY_key  ON rdb.dbo.tmp_DynDm_D_INV_RESIDENCY	 (	INVESTIGATION_KEY_D_INV_RESIDENCY  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_RISK_FACTOR_key  ON rdb.dbo.tmp_DynDm_D_INV_RISK_FACTOR	 (	INVESTIGATION_KEY_D_INV_RISK_FACTOR  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_SOCIAL_HISTORY_key  ON rdb.dbo.tmp_DynDm_D_INV_SOCIAL_HISTORY	 (	INVESTIGATION_KEY_D_INV_SOCIAL_HISTORY  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_SYMPTOM_key  ON rdb.dbo.tmp_DynDm_D_INV_SYMPTOM	 (	INVESTIGATION_KEY_D_INV_SYMPTOM  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_TREATMENT_key  ON rdb.dbo.tmp_DynDm_D_INV_TREATMENT	 (	INVESTIGATION_KEY_D_INV_TREATMENT  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_TRAVEL_key  ON rdb.dbo.tmp_DynDm_D_INV_TRAVEL	 (	INVESTIGATION_KEY_D_INV_TRAVEL  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_UNDER_CONDITION_key  ON rdb.dbo.tmp_DynDm_D_INV_UNDER_CONDITION	 (	INVESTIGATION_KEY_D_INV_UNDER_CONDITION  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_VACCINATION_key  ON rdb.dbo. tmp_DynDm_D_INV_VACCINATION	 (	INVESTIGATION_KEY_D_INV_VACCINATION  	);
			CREATE INDEX idx_tmp_DynDm_D_INV_STD_key  ON rdb.dbo.tmp_DynDm_D_INV_STD	 (	INVESTIGATION_KEY_D_INV_STD  	);
			CREATE INDEX idx_tmp_DynDm_Organization_key  ON rdb.dbo. tmp_DynDm_Organization	 (	INVESTIGATION_KEY_ORGANIZATION   	);
			CREATE INDEX idx_tmp_DynDm_PROVIDER_key  ON rdb.dbo.tmp_DynDm_PROVIDER	 (	INVESTIGATION_KEY_PROVIDER  	);




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
		 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
		 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_CreateDm_sp '+@DATAMART_TABLE_NAME  ,'START' ,@Proc_Step_no , @Proc_Step_Name , @ROWCOUNT_NO  );
  
		
	
    COMMIT TRANSACTION;
	

	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_DISCRETE_ALL';
	  

  IF OBJECT_ID('rdb.dbo.tmp_DynDm_fixcols', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_fixcols;



	
  IF OBJECT_ID('rdb.dbo.tmp_DynDm_DISCRETE_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_DISCRETE_ALL;

SELECT *
into rdb.dbo.tmp_DynDm_DISCRETE_ALL
FROM rdb.dbo.tmp_DynDm_INV_SUMM_DATAMART disumdt
LEFT JOIN rdb.dbo.tmp_DynDm_Investigation_Data with (nolock) ON tmp_DynDm_Investigation_Data.INVESTIGATION_KEY_INVESTIGATION_DATA =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_Patient_Data with (nolock) ON tmp_DynDm_Patient_Data.INVESTIGATION_KEY_PATIENT_DATA =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_Case_Management_Data with (nolock) ON tmp_DynDm_Case_Management_Data.INVESTIGATION_KEY_CASE_MANAGEMENT_DATA =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_Administrative with (nolock) ON tmp_DynDm_D_INV_Administrative.INVESTIGATION_KEY_D_INV_ADMINISTRATIVE =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_CLINICAL with (nolock) ON tmp_DynDm_D_INV_CLINICAL.INVESTIGATION_KEY_D_INV_CLINICAL =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_COMPLICATION with (nolock) ON  tmp_DynDm_D_INV_COMPLICATION.INVESTIGATION_KEY_D_INV_COMPLICATION =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_CONTACT with (nolock) ON tmp_DynDm_D_INV_CONTACT.INVESTIGATION_KEY_D_INV_CONTACT =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_DEATH with (nolock) ON tmp_DynDm_D_INV_DEATH.INVESTIGATION_KEY_D_INV_DEATH =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_EPIDEMIOLOGY with (nolock) ON tmp_DynDm_D_INV_EPIDEMIOLOGY.INVESTIGATION_KEY_D_INV_EPIDEMIOLOGY =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_HIV with (nolock) ON tmp_DynDm_D_INV_HIV.INVESTIGATION_KEY_D_INV_HIV =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_PATIENT_OBS with (nolock) ON tmp_DynDm_D_INV_PATIENT_OBS.INVESTIGATION_KEY_D_INV_PATIENT_OBS =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_ISOLATE_TRACKING with (nolock) ON tmp_DynDm_D_INV_ISOLATE_TRACKING.INVESTIGATION_KEY_D_INV_ISOLATE_TRACKING =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_LAB_FINDING with (nolock) ON tmp_DynDm_D_INV_LAB_FINDING.INVESTIGATION_KEY_D_INV_LAB_FINDING =disumdt.INVESTIGATION_KEY

LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_MEDICAL_HISTORY with (nolock) ON tmp_DynDm_D_INV_MEDICAL_HISTORY.INVESTIGATION_KEY_D_INV_MEDICAL_HISTORY =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_MOTHER with (nolock) ON tmp_DynDm_D_INV_MOTHER.INVESTIGATION_KEY_D_INV_MOTHER =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_OTHER with (nolock) ON tmp_DynDm_D_INV_OTHER.INVESTIGATION_KEY_D_INV_OTHER =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_PREGNANCY_BIRTH with (nolock) ON tmp_DynDm_D_INV_PREGNANCY_BIRTH.INVESTIGATION_KEY_D_INV_PREGNANCY_BIRTH =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_RESIDENCY with (nolock) ON tmp_DynDm_D_INV_RESIDENCY.INVESTIGATION_KEY_D_INV_RESIDENCY =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_RISK_FACTOR with (nolock) ON tmp_DynDm_D_INV_RISK_FACTOR.INVESTIGATION_KEY_D_INV_RISK_FACTOR =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_SOCIAL_HISTORY with (nolock) ON tmp_DynDm_D_INV_SOCIAL_HISTORY.INVESTIGATION_KEY_D_INV_SOCIAL_HISTORY =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_SYMPTOM ON tmp_DynDm_D_INV_SYMPTOM.INVESTIGATION_KEY_D_INV_SYMPTOM =disumdt.INVESTIGATION_KEY


LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_TREATMENT with (nolock) ON tmp_DynDm_D_INV_TREATMENT.INVESTIGATION_KEY_D_INV_TREATMENT =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_TRAVEL with (nolock) ON tmp_DynDm_D_INV_TRAVEL.INVESTIGATION_KEY_D_INV_TRAVEL =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_UNDER_CONDITION with (nolock) ON tmp_DynDm_D_INV_UNDER_CONDITION.INVESTIGATION_KEY_D_INV_UNDER_CONDITION =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_VACCINATION with (nolock)  ON  tmp_DynDm_D_INV_VACCINATION.INVESTIGATION_KEY_D_INV_VACCINATION =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_D_INV_STD with (nolock) ON tmp_DynDm_D_INV_STD.INVESTIGATION_KEY_D_INV_STD =disumdt.INVESTIGATION_KEY
LEFT JOIN rdb.dbo.tmp_DynDm_Organization with (nolock)  ON  tmp_DynDm_Organization.INVESTIGATION_KEY_ORGANIZATION =disumdt.INVESTIGATION_KEY 
LEFT JOIN rdb.dbo.tmp_DynDm_PROVIDER with (nolock) ON tmp_DynDm_PROVIDER.INVESTIGATION_KEY_PROVIDER =disumdt.INVESTIGATION_KEY
;





				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
		 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
		 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_CreateDm_sp '+@DATAMART_TABLE_NAME  ,'START' ,@Proc_Step_no , @Proc_Step_Name , @ROWCOUNT_NO  );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_REPEAT_ALL';
	  

    
  IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_ALL;


--CREATE TABLE REPEAT_ALL AS

SELECT * 
into rdb.dbo.tmp_DynDm_REPEAT_ALL
FROM rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_VARCHAR irv  with (nolock)
LEFT JOIN rdb.dbo.tmp_DynDm_REPEAT_BLOCK_VARCHAR_ALL rbva  with (nolock)  ON rbva.INVESTIGATION_KEY_REPEAT_BLOCK_VARCHAR_ALL =irv.INVESTIGATION_KEY_INVESTIGATION_REPEAT_VARCHAR
LEFT JOIN rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_DATE  ird  with (nolock) ON ird.INVESTIGATION_KEY_REPEAT_DATE =irv.INVESTIGATION_KEY_INVESTIGATION_REPEAT_VARCHAR
LEFT JOIN rdb.dbo.tmp_DynDm_REPEAT_BLOCK_DATE_ALL rda   with (nolock)     ON rda.INVESTIGATION_KEY_REPEAT_BLOCK_DATE_ALL =irv.INVESTIGATION_KEY_INVESTIGATION_REPEAT_VARCHAR
LEFT JOIN rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC rn  with (nolock) ON rn.INVESTIGATION_KEY_REPEAT_NUMERIC =irv.INVESTIGATION_KEY_INVESTIGATION_REPEAT_VARCHAR
LEFT JOIN rdb.dbo.tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL rna   with (nolock)  ON rna.INVESTIGATION_KEY_REPEAT_BLOCK_NUMERIC_ALL =irv.INVESTIGATION_KEY_INVESTIGATION_REPEAT_VARCHAR;

;




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
		 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
		 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_CreateDm_sp '+@DATAMART_TABLE_NAME  ,'START' ,@Proc_Step_no , @Proc_Step_Name , @ROWCOUNT_NO  );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  Dynamic Datamart';
	  

    

/*
PROC SQL;
CREATE TABLE &DATAMART_TABLE_NAME AS 
SELECT * 
FROM DISCRETE_ALL 
LEFT OUTER JOIN REPEAT_ALL ON DISCRETE_ALL.INVESTIGATION_KEY = REPEAT_ALL.INVESTIGATION_KEY;
QUIT;

*/
    declare @SQL varchar(max);

		 SET @SQL =   ' IF OBJECT_ID(''rdb.dbo.'+@DATAMART_TABLE_NAME+''', ''U'') IS NOT NULL     drop table RDB.dbo.'+@DATAMART_TABLE_NAME +'  ;';

     --DX     select 'DROP DM',@SQL;
     EXEC (@SQL);




	 SET @SQL = '   SELECT *  ' +
	            '    into RDB.dbo.'+@DATAMART_TABLE_NAME +' '+
				'    from rdb.dbo.tmp_DynDm_DISCRETE_ALL  da ' +
				'    LEFT OUTER JOIN rdb.dbo.tmp_DynDm_REPEAT_ALL ra ON da.INVESTIGATION_KEY = ra.INVESTIGATION_KEY_INVESTIGATION_REPEAT_VARCHAR  ; '
				;

--DX select 'CREATE INTO DM',@SQL;
      EXEC (@SQL);




	  

				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
		 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
		 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_CreateDm_sp '+@DATAMART_TABLE_NAME  ,'START' ,@Proc_Step_no , @Proc_Step_Name , @ROWCOUNT_NO  );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  ALTER TABLE ';
	  

    

/*



IF OBJECT_ID('rdb.dbo.tmp_DynDm_Investigation_Data', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_Investigation_Data;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_Patient_Data', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_Patient_Data;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_Case_Management_Data', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_Case_Management_Data;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_Administrative', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_Administrative;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_CLINICAL', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_CLINICAL;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_COMPLICATION', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_COMPLICATION;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_CONTACT', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_CONTACT;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_DEATH', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_DEATH;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_EPIDEMIOLOGY', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_EPIDEMIOLOGY;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_HIV', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_HIV;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_PATIENT_OBS', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_PATIENT_OBS;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_ISOLATE_TRACKING', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_ISOLATE_TRACKING;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_LAB_FINDING', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_LAB_FINDING;

IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_MEDICAL_HISTORY', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_MEDICAL_HISTORY;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_MOTHER', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_MOTHER;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_OTHER', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_OTHER;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_PREGNANCY_BIRTH;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_RESIDENCY', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_RESIDENCY;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_RISK_FACTOR', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_RISK_FACTOR;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_SOCIAL_HISTORY', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_SOCIAL_HISTORY;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_SYMPTOM', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_SYMPTOM;


IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_TREATMENT', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_TREATMENT;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_TRAVEL', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_TRAVEL;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_UNDER_CONDITION', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_UNDER_CONDITION;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_VACCINATION', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_VACCINATION;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_STD', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_STD;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_Organization', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_Organization;
IF OBJECT_ID('rdb.dbo.tmp_DynDm_PROVIDER', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_PROVIDER;


*/




 SET @SQL =   '    alter  table RDB.dbo.'+@DATAMART_TABLE_NAME +' ' +
     '  drop column ' +
    -- ' INVESTIGATION_KEY_INVESTIGATION_DATA , ' + 
	-- ' INVESTIGATION_KEY_PATIENT_DATA , ' + 
	-- ' INVESTIGATION_KEY_CASE_MANAGEMENT_DATA , ' + 
	 ' INVESTIGATION_KEY_D_INV_ADMINISTRATIVE , ' + 
	 ' INVESTIGATION_KEY_D_INV_CLINICAL , ' + 
	 ' INVESTIGATION_KEY_D_INV_COMPLICATION , ' + 
	 ' INVESTIGATION_KEY_D_INV_CONTACT , ' + 
	 ' INVESTIGATION_KEY_D_INV_DEATH , ' + 
	 ' INVESTIGATION_KEY_D_INV_EPIDEMIOLOGY , ' + 
	 ' INVESTIGATION_KEY_D_INV_HIV , ' + 
	 ' INVESTIGATION_KEY_D_INV_PATIENT_OBS , ' + 
	 ' INVESTIGATION_KEY_D_INV_ISOLATE_TRACKING , ' + 
	 ' INVESTIGATION_KEY_D_INV_LAB_FINDING , ' + 
	 ' INVESTIGATION_KEY_D_INV_MEDICAL_HISTORY , ' + 
	 ' INVESTIGATION_KEY_D_INV_MOTHER , ' + 
	 ' INVESTIGATION_KEY_D_INV_OTHER , ' + 
	 ' INVESTIGATION_KEY_D_INV_PREGNANCY_BIRTH , ' + 
	 ' INVESTIGATION_KEY_D_INV_RESIDENCY , ' + 
	 ' INVESTIGATION_KEY_D_INV_RISK_FACTOR , ' + 
	 ' INVESTIGATION_KEY_D_INV_SOCIAL_HISTORY , ' + 
	 ' INVESTIGATION_KEY_D_INV_SYMPTOM , ' + 
	 ' INVESTIGATION_KEY_D_INV_TREATMENT , ' + 
	 ' INVESTIGATION_KEY_D_INV_TRAVEL , ' + 
	 ' INVESTIGATION_KEY_D_INV_UNDER_CONDITION , ' + 
	 ' INVESTIGATION_KEY_D_INV_VACCINATION , ' + 
	 ' INVESTIGATION_KEY_D_INV_STD , ' + 
	 ' INVESTIGATION_KEY_ORGANIZATION , ' + 
	 ' INVESTIGATION_KEY_PROVIDER , ' +
	 ' ORGANIZATION_UID , ' +
	 ' PROVIDER_UID , ' +
      ' INVESTIGATION_KEY_CASE_MANAGEMENT_DATA , ' +
      ' INVESTIGATION_KEY_INVESTIGATION_DATA , ' +
      ' INVESTIGATION_KEY_INVESTIGATION_REPEAT_VARCHAR , ' +
      ' INVESTIGATION_KEY_PATIENT_DATA , ' +
      ' INVESTIGATION_KEY_REPEAT_BLOCK_DATE_ALL , ' +
      ' INVESTIGATION_KEY_REPEAT_BLOCK_NUMERIC_ALL , ' +
      ' INVESTIGATION_KEY_REPEAT_BLOCK_VARCHAR_ALL , ' +
      ' INVESTIGATION_KEY_REPEAT_DATE , ' +
      ' INVESTIGATION_KEY_REPEAT_NUMERIC , ' +
	 'PATIENT_LOCAL_ID_PATIENT_DATA ;'
      ;

	  

      EXEC (@SQL);

	  

		SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
		 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
		 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_CreateDm_sp '+@DATAMART_TABLE_NAME  ,'START' ,@Proc_Step_no , @Proc_Step_Name , @ROWCOUNT_NO  );
  
		
	
    COMMIT TRANSACTION;
	
 	 BEGIN TRANSACTION;

               	SET @Proc_Step_no = @Proc_Step_no + 1;
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
						   @batch_id,
						   'DYNAMIC_DATAMART'
						   ,'RDB.DBO.DynDm_CreateDm_sp ' + @DATAMART_TABLE_NAME
						   ,'COMPLETE'
						   ,@Proc_Step_no
						   ,@Proc_Step_name
						   ,@RowCount_no
						   );
  
	
	 COMMIT TRANSACTION ;

    END TRY
            BEGIN CATCH
						IF @@TRANCOUNT > 0
						BEGIN
							COMMIT TRANSACTION;
						END;
						DECLARE @ErrorNumber int= ERROR_NUMBER();
						DECLARE @ErrorLine int= ERROR_LINE();
						DECLARE @ErrorMessage nvarchar(4000)= ERROR_MESSAGE();
						DECLARE @ErrorSeverity int= ERROR_SEVERITY();
						DECLARE @ErrorState int= ERROR_STATE();

                        select @ErrorMessage;

						INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [Error_Description], [row_count] )
						VALUES( @Batch_id, 'DYNAMIC_DATAMART', 'RDB.DBO.DynDm_CreateDm_sp ' + @DATAMART_TABLE_NAME, 'ERROR', @Proc_Step_no, 'ERROR - '+@Proc_Step_name, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );
						RETURN -1;
	        END CATCH;
END;

GO


