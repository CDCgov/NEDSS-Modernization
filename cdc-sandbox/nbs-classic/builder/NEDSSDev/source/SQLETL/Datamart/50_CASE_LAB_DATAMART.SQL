USE [RDB]
GO

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_All_Case', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_All_Case; 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PATIENT_ADD', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PATIENT_ADD ;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PAT_ADD_INV', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PAT_ADD_INV 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_INVESTIGATOR', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_INVESTIGATOR 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_invlab', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_invlab ;

  	    IF OBJECT_ID('rdb.dbo.TMP_CLDM_lab', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_lab ;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_both', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_both 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_inv2labs', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_inv2labs 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_invmorb', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_invmorb 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_morbResults', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_morbResults 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_morbLabResults', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_morbLabResults ;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_Inv2labs_final', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_Inv2labs_final ;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample1', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample1;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample2', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample2;

		
		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample21', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample21;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample3', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample3 ;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample4', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample4 ;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample5', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample5 ;

		--IF OBJECT_ID('rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART_FINAL', 'U') IS NOT NULL 
		--						 drop table  rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART_FINAL ;

		IF OBJECT_ID('rdb.dbo.TMP_SPECIMEN_COLLECTION_TABLE', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_SPECIMEN_COLLECTION_TABLE ;

		IF OBJECT_ID('rdb.dbo.TMP_CASE_LAB_DATAMART_MODIFIED', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CASE_LAB_DATAMART_MODIFIED;

								 	
		IF OBJECT_ID('rdb.dbo.TEMP_UPDATED_LAB_INV_MAP', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TEMP_UPDATED_LAB_INV_MAP;			
						


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


			 IF OBJECT_ID('rdb.dbo.sp_CASE_LAB_DATAMART', 'P') IS NOT NULL
				BEGIN
				DROP PROCEDURE dbo.sp_CASE_LAB_DATAMART;
				END;

GO

USE [RDB]
GO
/****** Object:  StoredProcedure [dbo].[sp_CASE_LAB_DATAMART]    Script Date: 3/17/2023 12:37:37 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE   [dbo].[sp_CASE_LAB_DATAMART] 
  @batch_id BIGINT
 as

  BEGIN
 
  --
--UPDATE ACTIVITY_LOG_DETAIL SET 
--START_DATE=DATETIME();
-- dec
    DECLARE @RowCount_no INT ;
	DECLARE @Table_RowCount_no INT ;
    DECLARE @Proc_Step_no FLOAT = 0 ;
    DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
	DECLARE @batch_start_time datetime2(7) = null ;
	DECLARE @batch_end_time datetime2(7) = null ;
 
 BEGIN TRY
    
	SET @Proc_Step_no = 1;
	SET @Proc_Step_Name = 'SP_Start';

	
		   BEGIN TRANSACTION;

             SELECT @ROWCOUNT_NO = 0;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;


			SELECT @batch_start_time = batch_start_dttm, 
						   @batch_end_time = batch_end_dttm
					FROM rdb.[dbo].[job_batch_log]
					WHERE type_code = 'MasterETL'
						  AND status_type = 'start';

						  
			--select  @batch_start_time,@batch_end_time;
                
             with lst as (select top 2 INVESTIGATION_KEY
				from rdb.dbo.CASE_LAB_DATAMART)
				select @Table_RowCount_no = count(*) from lst
				;

          
				     SELECT @RowCount_no = @@ROWCOUNT;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_All_Case', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_All_Case; 


		  if @Table_RowCount_no > 0 
		   BEGIN
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING INCREMENTAL TMP_CLDM_All_Case'; 

		
					SELECT INVESTIGATION.INVESTIGATION_KEY, RPT_SRC_ORG_KEY,INV_LOCAL_ID AS INVESTIGATION_LOCAL_ID,CONDITION_KEY,
							JURISDICTION_NM AS JURISDICTION_NAME, PATIENT_key, PHYSICIAN_KEY 
						into RDB.dbo.TMP_CLDM_All_Case  
							FROM RDB.dbo.INVESTIGATION  with ( nolock) 
								LEFT OUTER JOIN RDB.dbo.CASE_COUNT with ( nolock) ON INVESTIGATION.INVESTIGATION_KEY =CASE_COUNT.INVESTIGATION_KEY 
							 WHERE CASE_TYPE='I'
							   AND INVESTIGATION.[LAST_CHG_TIME]  >= @batch_start_time   AND INVESTIGATION.[LAST_CHG_TIME]  <  @batch_end_time
     			          UNION
                           SELECT inv.INVESTIGATION_KEY, RPT_SRC_ORG_KEY,INV_LOCAL_ID AS INVESTIGATION_LOCAL_ID,CONDITION_KEY,
							JURISDICTION_NM AS JURISDICTION_NAME, PATIENT_key, PHYSICIAN_KEY 
							
							FROM RDB.dbo.INVESTIGATION inv    with ( nolock) 
							LEFT OUTER JOIN RDB.dbo.CASE_COUNT  cc with ( nolock) ON inv.INVESTIGATION_KEY =cc.INVESTIGATION_KEY 
							 WHERE CASE_TYPE='I'
							 and inv.INVESTIGATION_KEY in ( select [INVESTIGATION_KEY] 		FROM rdb.dbo.TEMP_UPDATED_LAB_INV_MAP   with ( nolock))
						union
                           SELECT inv.INVESTIGATION_KEY, RPT_SRC_ORG_KEY,INV_LOCAL_ID AS INVESTIGATION_LOCAL_ID,CONDITION_KEY,
							JURISDICTION_NM AS JURISDICTION_NAME, PATIENT_key, PHYSICIAN_KEY 
							
							FROM RDB.dbo.INVESTIGATION inv    with ( nolock) 
							LEFT OUTER JOIN RDB.dbo.CASE_COUNT  cc with ( nolock) ON inv.INVESTIGATION_KEY =cc.INVESTIGATION_KEY 
							 WHERE CASE_TYPE='I'
							 and inv.INVESTIGATION_KEY in (
							     select distinct(INVESTIGATION_KEY)
								 FROM RDB.dbo.LAB_TEST_RESULT
								where  LAB_TEST_KEY in ( select lab_test_key
									FROM RDB.dbo.LAB_TEST
									where LAB_RPT_LAST_UPDATE_DT >= @batch_start_time
									  and LAB_RPT_LAST_UPDATE_DT <   @batch_end_time
									)
									and INVESTIGATION_KEY <> 1
                            )
						UNION
						SELECT inv.INVESTIGATION_KEY, RPT_SRC_ORG_KEY,INV_LOCAL_ID AS INVESTIGATION_LOCAL_ID,CONDITION_KEY,
							JURISDICTION_NM AS JURISDICTION_NAME, PATIENT_key, PHYSICIAN_KEY 
							
							FROM RDB.dbo.INVESTIGATION inv    with ( nolock) 
							LEFT OUTER JOIN RDB.dbo.CASE_COUNT  cc with ( nolock) ON inv.INVESTIGATION_KEY =cc.INVESTIGATION_KEY 
							 WHERE CASE_TYPE='I'
							 and inv.INVESTIGATION_KEY in
							 (
								select  INVESTIGATION_KEY
								from rdb.dbo.MORBIDITY_REPORT 
								inner join rdb.dbo.MORBIDITY_REPORT_EVENT on MORBIDITY_REPORT.MORB_RPT_KEY= MORBIDITY_REPORT_EVENT.MORB_RPT_KEY 
								where MORB_RPT_LAST_UPDATE_DT >= @batch_start_time
								  and MORB_RPT_LAST_UPDATE_DT <   @batch_end_time
							)

						union
							SELECT inv.INVESTIGATION_KEY, RPT_SRC_ORG_KEY,INV_LOCAL_ID AS INVESTIGATION_LOCAL_ID,CONDITION_KEY,
							JURISDICTION_NM AS JURISDICTION_NAME, PATIENT_key, PHYSICIAN_KEY 
							
							FROM RDB.dbo.INVESTIGATION inv    with ( nolock) 
							LEFT OUTER JOIN RDB.dbo.CASE_COUNT  cc with ( nolock) ON inv.INVESTIGATION_KEY =cc.INVESTIGATION_KEY 
							 WHERE CASE_TYPE='I'
							 and cc.patient_key in
							 (
							select patient_key
								from rdb.dbo.d_patient
								where PATIENT_LAST_CHANGE_TIME >= @batch_start_time
								 and PATIENT_LAST_CHANGE_TIME <   @batch_end_time
								 group by PATIENT_LOCAL_ID,patient_key
								
							)
						
						;

                SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		       INSERT INTO RDB.dbo.[JOB_FLOW_LOG] 
			  	(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				 VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  
				 
			    COMMIT TRANSACTION;
		  END ;

		  
		  if @Table_RowCount_no = 0 
		   BEGIN
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING  REBUILD TMP_CLDM_All_Case'; 

		
					SELECT INVESTIGATION.INVESTIGATION_KEY, RPT_SRC_ORG_KEY,INV_LOCAL_ID AS INVESTIGATION_LOCAL_ID,CONDITION_KEY,
							JURISDICTION_NM AS JURISDICTION_NAME, PATIENT_key, PHYSICIAN_KEY 
						into RDB.dbo.TMP_CLDM_All_Case  
							FROM RDB.dbo.INVESTIGATION  with ( nolock) 
								LEFT OUTER JOIN RDB.dbo.CASE_COUNT with ( nolock) ON INVESTIGATION.INVESTIGATION_KEY =CASE_COUNT.INVESTIGATION_KEY 
							 WHERE CASE_TYPE='I'
							   ;

                        SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  		              INSERT INTO RDB.dbo.[JOB_FLOW_LOG] 
			  	           (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				            VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  
				 
			             COMMIT TRANSACTION;
		               END ;

		
							/*=================================================== ALL_CASE Load Complete ==================================*/
								
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_GEN_PATIENT_ADD'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PATIENT_ADD', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PATIENT_ADD ;
	  
								select  GC.*,
										C.CONDITION_CD AS CONDITION_CD ,
										p.PATIENT_local_id AS PATIENT_LOCAL_ID ,
										P.PATIENT_FIRST_NAME AS PATIENT_FIRST_NM ,
										P.PATIENT_MIDDLE_NAME AS PATIENT_MIDDLE_NM ,
										P.PATIENT_LAST_NAME AS PATIENT_LAST_NM ,         
										P.PATIENT_PHONE_HOME AS PATIENT_HOME_PHONE ,
										P.PATIENT_PHONE_EXT_HOME,
										P.PATIENT_STREET_ADDRESS_1,
										P.PATIENT_STREET_ADDRESS_2,
										P.PATIENT_CITY,
										P.PATIENT_STATE,
										P.PATIENT_ZIP,
										p.PATIENT_RACE_CALCULATED AS RACE ,
										P.PATIENT_COUNTY,
										P.PATIENT_DOB AS PATIENT_DOB ,   
										P.PATIENT_AGE_REPORTED AS PATIENT_REPORTED_AGE, 
										P.PATIENT_AGE_REPORTED_UNIT AS PATIENT_REPORTED_AGE_UNITS , 
										P.PATIENT_CURRENT_SEX AS PATIENT_CURR_GENDER,      
 										P.PATIENT_ENTRY_METHOD AS PATIENT_ELECTRONIC_IND,
 										P.PATIENT_UID AS PATIENT_UID 
								into rdb.dbo.TMP_CLDM_GEN_PATIENT_ADD 
								from rdb.dbo.TMP_CLDM_ALL_CASE  as GC with ( nolock) 
								left join RDB.dbo.D_PATIENT as p with ( nolock) ON GC.PATIENT_KEY = p.PATIENT_key
								left join RDB.dbo.CONDITION as C with ( nolock) ON C.CONDITION_KEY = GC.CONDITION_KEY
								AND P.PATIENT_KEY <> 1
								;
 

							/*
							DATA GEN_PATIENT_ADD;
							SET GEN_PATIENT_ADD;
							if(PATIENT_HOME_PHONE <>'' && PATIENT_PHONE_EXT_HOME <>'') then PATIENT_HOME_PHONE=rtrim(PATIENT_HOME_PHONE)||' ext. '||trim(PATIENT_PHONE_EXT_HOME);
								if(PATIENT_HOME_PHONE <>'' && PATIENT_PHONE_EXT_HOME ='') then PATIENT_HOME_PHONE=rtrim(PATIENT_HOME_PHONE);
								if(PATIENT_HOME_PHONE ='' && PATIENT_PHONE_EXT_HOME <>'') then PATIENT_HOME_PHONE='ext. '||trim(PATIENT_PHONE_EXT_HOME);
							RUN;
							*/

							  update rdb.dbo.TMP_CLDM_GEN_PATIENT_ADD
								set  PATIENT_HOME_PHONE=rtrim(PATIENT_HOME_PHONE)+' ext. '+rtrim(PATIENT_PHONE_EXT_HOME) 
								where   (PATIENT_HOME_PHONE <>'' and PATIENT_PHONE_EXT_HOME <>'') 
							   ;

							  update  rdb.dbo.TMP_CLDM_GEN_PATIENT_ADD
								set PATIENT_HOME_PHONE=rtrim(PATIENT_HOME_PHONE)
								where (PATIENT_HOME_PHONE <>'' and PATIENT_PHONE_EXT_HOME ='') 
								;


								 update  rdb.dbo.TMP_CLDM_GEN_PATIENT_ADD
								  set PATIENT_HOME_PHONE='ext. '+rtrim(PATIENT_PHONE_EXT_HOME)
								 where (PATIENT_HOME_PHONE =''  and  PATIENT_PHONE_EXT_HOME <>'')
								  ;



								     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_GEN_PAT_ADD_INV'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PAT_ADD_INV', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PAT_ADD_INV 

								 ;


								select GPA.*,
										i.INV_LOCAL_ID 'INV_LOCAL_ID',	
										i.INVESTIGATION_STATUS 'INVESTIGATION_STATUS',	
										i.INV_CASE_STATUS 'INV_CASE_STATUS',	
										i.JURISDICTION_NM AS INV_JURISDICTION_NM ,	
										i.ILLNESS_ONSET_DT 'ILLNESS_ONSET_DT',	
										i.INV_START_DT 'INV_START_DT',
										i.INV_RPT_DT 'INV_RPT_DT',	
										i.RPT_SRC_CD_DESC 'RPT_SRC_CD_DESC',	
										i.EARLIEST_RPT_TO_CNTY_DT 'EARLIEST_RPT_TO_CNTY_DT',	
										i.EARLIEST_RPT_TO_STATE_DT 'EARLIEST_RPT_TO_STATE_DT',	
										i.DIE_FRM_THIS_ILLNESS_IND 'DIE_FRM_THIS_ILLNESS_IND',	
										I.outbreak_ind 'OUTBREAK_IND',
    									I.DISEASE_IMPORTED_IND,
										I.Import_Frm_Cntry AS IMPORT_FROM_COUNTRY ,
										I.Import_Frm_State AS IMPORT_FROM_STATE ,
										I.Import_Frm_Cnty AS IMPORT_FROM_COUNTY ,
										I.Import_Frm_City AS IMPORT_FROM_CITY ,
										i.CASE_RPT_MMWR_WK ,	
										i.CASE_RPT_MMWR_YR 'CASE_RPT_MMWR_YR',	
										i.DIAGNOSIS_DT 'DIAGNOSIS_DT',	
										i.HSPTLIZD_IND 'HSPTLIZD_IND',
										i.HSPTL_ADMISSION_DT 'HSPTL_ADMISSION_DT',
										I.HSPTL_DISCHARGE_DT,
										I.HSPTL_DURATION_DAYS,
										I.Transmission_mode,
										I.CASE_OID,
										i.INV_COMMENTS 'INV_COMMENTS',
										em.ADD_TIME AS INV_ADD_TIME ,
										em.LAST_CHG_TIME AS PHC_LAST_CHG_TIME ,
										em.PROG_AREA_DESC_TXT AS PROGRAM_AREA_DESCRIPTION ,
										i.record_status_cd
								into rdb.dbo.TMP_CLDM_GEN_PAT_ADD_INV 
								from rdb.dbo.TMP_CLDM_GEN_PATIENT_ADD as GPA with ( nolock) 
								  left join RDB.dbo.investigation as i with ( nolock) ON GPA.investigation_key=i.investigation_key
								  left join RDB.dbo.EVENT_METRIC_INC as em with ( nolock) ON em.event_uid = i.case_uid and i.investigation_key <> 1
								WHERE     (I.RECORD_STATUS_CD <> 'INACTIVE') 
								  AND (I.CASE_TYPE <> 'S')
								  ;
  

							/*
							proc datasets memtype=DATA;
							   delete GEN_PATIENT_ADD;
							run;
							*/


								     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER 
								 ;


								SELECT GPI. *,
									   PP.PROVIDER_FIRST_NAME,
									   PP.PROVIDER_LAST_NAME,
									   PP.PROVIDER_MIDDLE_NAME,
									   PP.PROVIDER_PHONE_WORK,
									   PP.PROVIDER_PHONE_EXT_WORK,
									   cast ( null as varchar(100)) as PHYSICIAN_NAME,
									   cast ( null as varchar(100)) as PHYSICIAN_PHONE 
								into rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER
								FROM rdb.dbo.TMP_CLDM_GEN_PAT_ADD_INV AS GPI with ( nolock) 
								   LEFT JOIN RDB.dbo.D_PROVIDER AS PP with ( nolock) ON GPI.PHYSICIAN_KEY=PP.PROVIDER_KEY
								;
 

  
							update  rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER 
							 set PHYSICIAN_NAME= cast(rtrim(PROVIDER_LAST_NAME) + ', ' + rtrim(PROVIDER_FIRST_NAME) as varchar(100));

  
							update rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER 
							  set PHYSICIAN_PHONE=rtrim(PROVIDER_PHONE_WORK) + ' ext. ' + rtrim(PROVIDER_PHONE_EXT_WORK)
							  where (PROVIDER_PHONE_WORK <>'' and PROVIDER_PHONE_EXT_WORK <>'') ;
  
							update  rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER 
							 set PHYSICIAN_PHONE=rtrim(PROVIDER_PHONE_WORK)
							 where (PROVIDER_PHONE_WORK <>'' and PROVIDER_PHONE_EXT_WORK ='') ;
  
							update  rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER 
							 set  PHYSICIAN_PHONE='ext. ' + rtrim(PROVIDER_PHONE_EXT_WORK)
							 where(PROVIDER_PHONE_WORK ='' and PROVIDER_PHONE_EXT_WORK <>'') ;







							--CREATE TABLE GEN_PATCOMPL_INV_INVESTIGATOR AS

								     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_GEN_PATCOMPL_INV_INVESTIGATOR'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_INVESTIGATOR', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_INVESTIGATOR 
								 ;
	
	
								SELECT GPI. *,
									   O.ORGANIZATION_NAME AS REPORTING_SOURCE 
								INTO rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_INVESTIGATOR 
								FROM rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER AS GPI  with ( nolock) 
								  LEFT JOIN RDB.dbo.D_ORGANIZATION AS O with ( nolock) ON GPI.RPT_SRC_ORG_KEY=O.ORGANIZATION_KEY
								;
 

							/* GET THE CONDITION - JOIN WITH THE CONDITION TABLE */



	
								     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND 
								 ;

					SELECT GPIPR.*,
									   C.CONDITION_SHORT_NM,
									   C.PROGRAM_AREA_DESC,
									   cast( null as datetime) as CONFIRMATION_DT,
									   cast( null as datetime) as EVENT_DATE,
									    cast( null as varchar(200)) as EVENT_DATE_TYPE
								INTO rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND 
								FROM rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_INVESTIGATOR AS GPIPR  with ( nolock) 
								 LEFT JOIN RDB.dbo.CONDITION AS C with ( nolock) ON GPIPR.CONDITION_KEY=C.CONDITION_KEY
								;
 

                             update rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND
                                   set CONFIRMATION_DT = (SELECT min(CMG.[CONFIRMATION_DT] )
						           FROM  rdb.dbo.[CONFIRMATION_METHOD_GROUP]CMG with (nolock)
					             	WHERE CMG.[INVESTIGATION_KEY] = rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND.investigation_key )
							;
 
							/*
							proc datasets memtype=DATA;
							   delete GEN_PATINFO_INV_PHY_RPTSRC;
							run;
							*/

							/*
							Derive the event date using following algorithm
							1. Illness_onset_dt
							2. Diagnosis_Dt
							3. The earliest of the following dates:
								Earliest_rpt_to_cnty_dt,
								Earliest_rpt_to_state_dt,
								Inv_rpt_dt. Inv_rpt_dt,
								Inv_start_dt,
								ALT_Result_dt,
								AST_result_dt,
								HSPTL_Admission_dt,
								Hsptl_discharge_dt
							*/

							/*
							data GENERIC_DBASE_WITH_EVENT_DATE;

							set GEN_PATINFO_INV_PHY_RPTSRC_COND;

							if ILLNESS_ONSET_DT <> . then 
									EVENT_DATE = ILLNESS_ONSET_DT;

							else if DIAGNOSIS_DT <> . then 
									EVENT_DATE = DIAGNOSIS_DT;

							if EVENT_DATE = . then
								do; 
									EVENT_DATE = EARLIEST_RPT_TO_CNTY_DT;
									if EVENT_DATE <> . then
										do; 
											if EARLIEST_RPT_TO_STATE_DT <> . AND EARLIEST_RPT_TO_STATE_DT < EVENT_DATE then
											EVENT_DATE=EARLIEST_RPT_TO_STATE_DT;
										end;
									else EVENT_DATE = EARLIEST_RPT_TO_STATE_DT;

									if EVENT_DATE <> . then
										do; 
											if INV_RPT_DT <> . AND INV_RPT_DT < EVENT_DATE then
											EVENT_DATE=INV_RPT_DT;
										end;
									else EVENT_DATE = INV_RPT_DT;

									if EVENT_DATE <> . then
										do; 
											if INV_START_DT <> . AND INV_START_DT < EVENT_DATE then
											EVENT_DATE=INV_START_DT;
										end;
									else EVENT_DATE = INV_START_DT;

									if EVENT_DATE <> . then
										do; 
											if ALT_RESULT_DT <> . AND ALT_RESULT_DT < EVENT_DATE then
											EVENT_DATE=ALT_RESULT_DT;
										end;
									else EVENT_DATE = ALT_RESULT_DT;

									if EVENT_DATE <> . then
										do; 
											if AST_RESULT_DT <> . AND AST_RESULT_DT < EVENT_DATE then
											EVENT_DATE=AST_RESULT_DT;
										end;
									else EVENT_DATE = AST_RESULT_DT;

									if EVENT_DATE <> . then
										do; 
											if HSPTL_ADMISSION_DT <> . AND HSPTL_ADMISSION_DT < EVENT_DATE then
											EVENT_DATE=HSPTL_ADMISSION_DT;
										end;
									else EVENT_DATE = HSPTL_ADMISSION_DT;

									if EVENT_DATE <> . then
										do; 
											if HSPTL_DISCHARGE_DT <> . AND HSPTL_DISCHARGE_DT < EVENT_DATE then
											EVENT_DATE=HSPTL_DISCHARGE_DT;
										end;
									else EVENT_DATE = HSPTL_DISCHARGE_DT;

									if EVENT_DATE <> . then
										do; 
											if INV_ADD_TIME <> . AND INV_ADD_TIME < EVENT_DATE then
											EVENT_DATE=INV_ADD_TIME;
										end;
									else EVENT_DATE = INV_ADD_TIME;

							end;
							run; 


							*/



							/*
							 update rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND
							   set 	EVENT_DATE = ILLNESS_ONSET_DT
							   where  ILLNESS_ONSET_DT <> null
								;

							 update rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND
								set EVENT_DATE = DIAGNOSIS_DT
								where  DIAGNOSIS_DT <> null
								and  EVENT_DATE is null
							;


							 update rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND
								set EVENT_DATE =  ( select min(min_dt) from ( values   
											(EARLIEST_RPT_TO_STATE_DT) ,
											(INV_RPT_DT) ,
											(INV_START_DT) ,
											--(ALT_RESULT_DT) ,
											--(AST_RESULT_DT) ,
											(HSPTL_ADMISSION_DT) ,
											(HSPTL_DISCHARGE_DT) ,
											(INV_ADD_TIME) ) as Fields(min_dt))  
								where  EVENT_DATE is null
								;
                         */

						 DECLARE	@return_value int
						 DECLARE	@ERROR_MSG_BACK varchar(100)


							EXEC	@return_value = [dbo].SP_UPDATE_EVENT_DATE
									@batch_id = 999,
									@Dataflow_Name = N'CASE_LAB_DATAMART',
									@vTableName = N'rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND',
								    @ERROR_MSG = @ERROR_MSG_BACK;


							if @return_value = -1
							  begin

					  			   
         				 IF @@TRANCOUNT > 0   ROLLBACK TRANSACTION;

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
							   ,'CASE_LAB_DATAMART'	
							   ,'RDB.CASE_LAB_DATAMART'
							   ,'ERROR'
							   ,@Proc_Step_no
							   ,'ERROR - '+  @ERROR_MSG_BACK
							   , 'Step -' + ' Table not found'
							   ,0
							   );

 							      return -1;

							  end 
							;
						
							/*
							DATA GENERIC_DBASE (DROP= PATIENT_REPORTED_AGE Case_Rpt_MMWR_Wk CASE_RPT_MMWR_YR);
							SET GENERIC_DBASE_WITH_EVENT_DATE;
							rename
							Case_Rpt_MMWR_Wk = Case_Rpt_MMWR_WEEK
							CASE_RPT_MMWR_YR = CASE_RPT_MMWR_YEAR
							PATIENT_REPORTED_AGE=PATIENT_REPORTEDAGE
							;

							PROC SORT DATA=GENERIC_DBASE OUT=GENERIC_DBASE NODUPKEY;
							BY INVESTIGATION_KEY;
							RUN;

							*/


								     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_CASE_LAB_DATAMART'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART
								 ;

	
									SELECT  distinct	
											INVESTIGATION_KEY,
											PATIENT_LOCAL_ID,
											INV_LOCAL_ID AS INVESTIGATION_LOCAL_ID,
											PATIENT_FIRST_NM,
											PATIENT_MIDDLE_NM ,
											PATIENT_LAST_NM,    
											PATIENT_STREET_ADDRESS_1,
											PATIENT_STREET_ADDRESS_2,
											/*PATIENT_ADDRESS,  */
											PATIENT_CITY,
											PATIENT_STATE,
											PATIENT_ZIP, 
											PATIENT_COUNTY,
											PATIENT_HOME_PHONE,
											PATIENT_DOB,
											PATIENT_REPORTED_AGE AS AGE_REPORTED,  
											PATIENT_REPORTED_AGE_UNITS AS AGE_REPORTED_UNIT,
											PATIENT_CURR_GENDER AS PATIENT_CURRENT_SEX,      
											RACE,
											INV_JURISDICTION_NM AS JURISDICTION_NAME,
	   										PROGRAM_AREA_DESCRIPTION,
											INV_START_DT AS INVESTIGATION_START_DATE,
											INV_CASE_STATUS AS CASE_STATUS,
											condition_short_nm AS DISEASE,
											CONDITION_CD AS DISEASE_CD,
											REPORTING_SOURCE,
											INV_COMMENTS AS GENERAL_COMMENTS,
											PHYSICIAN_NAME,
	   										PHYSICIAN_PHONE,
	   										--LABORATORY_INFORMATION,
	   										CASE_OID AS PROGRAM_JURISDICTION_OID,
	   										INV_ADD_TIME AS PHC_ADD_TIME,
	   										PHC_LAST_CHG_TIME,
											EVENT_DATE,
											EVENT_DATE_TYPE
								INTO RDB.dbo.TMP_CLDM_CASE_LAB_DATAMART
								FROM rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND with ( nolock) 
								;


 
							/************ LAB INFORMATION STUFF STARTS FROM HERE *******************************************/


								     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_invlab'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_invlab', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_invlab ;

								SELECT l.INVESTIGATION_KEY, l.LAB_TEST_KEY
								into rdb.dbo.TMP_CLDM_invlab
								FROM RDB.dbo.LAB_TEST_RESULT l   with ( nolock) 
									 INNER JOIN RDB.dbo.INVESTIGATION I with ( nolock) ON l.INVESTIGATION_KEY = I.INVESTIGATION_KEY
								WHERE (l.LAB_TEST_KEY IN(SELECT  LAB_TEST_KEY FROM RDB.dbo.LAB_TEST)) 
								  AND (l.INVESTIGATION_KEY <> 1) 	AND (I.RECORD_STATUS_CD = 'ACTIVE')
								  AND l.INVESTIGATION_KEY in ( select INVESTIGATION_KEY from rdb.dbo.TMP_CLDM_All_Case )
	
								;



							 IF OBJECT_ID('rdb.dbo.TMP_CLDM_lab', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_lab ;

								 /*
								select lab_test_key,lab_rpt_local_id 
								 into rdb.dbo.TMP_CLDM_lab
								 from RDB.dbo.lab_test  with ( nolock) 
								 ;
                               */

							/*
							data both;
							   merge invlab lab;
							   by lab_test_key;
							run;
							*/

									     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_both'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_both', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_both 
								 ;


								SELECT til.INVESTIGATION_KEY, til.LAB_TEST_KEY,lab_rpt_local_id 
								into rdb.dbo.TMP_CLDM_both
								from  rdb.dbo.TMP_CLDM_invlab til,
									  rdb.dbo.lab_test tl with ( nolock)
								where til.LAB_TEST_KEY = tl.LAB_TEST_KEY
								and til.INVESTIGATION_KEY is not null
									;







							/*
							data both;
								set both;
								if investigation_key <> .;
							run;
							*/


								-- create table inv2labs as
	
	
									     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_inv2labs'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_inv2labs', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_inv2labs 
								 ;

	
									SELECT distinct b.investigation_key, b.lab_test_key,
									   l.lab_rpt_LOCAL_ID, l.LAB_RPT_RECEIVED_BY_PH_DT, l.SPECIMEN_COLLECTION_DT,
									   l.RESULTED_LAB_TEST_CD_DESC, l.RESULTEDTEST_VAL_CD_DESC, l.NUMERIC_RESULT_WITHUNITS, l.LAB_RESULT_TXT_VAL, l.LAB_RESULT_COMMENTS,
									   l.ELR_IND
									into rdb.dbo.TMP_CLDM_inv2labs
	  								FROM rdb.dbo.TMP_CLDM_both b 
									inner join RDB.dbo.lab100 l  with ( nolock) ON 	l.LAB_RPT_LOCAL_ID = b.LAB_RPT_LOCAL_ID
									 ;
 
							/* Retrieving LabResults for the Morbs (associated to INVs) starts here */

								     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_invmorb'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_invmorb', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_invmorb 
								 ;


								SELECT ME.MORB_RPT_KEY, I.INVESTIGATION_KEY, I.INV_LOCAL_ID, MR.MORB_RPT_LOCAL_ID
								into rdb.dbo.TMP_CLDM_invmorb
								FROM RDB.dbo.MORBIDITY_REPORT_EVENT ME with ( nolock) 
								  INNER JOIN	rdb.dbo.INVESTIGATION I with ( nolock) ON ME.INVESTIGATION_KEY = I.INVESTIGATION_KEY 
						                     		  AND I.INVESTIGATION_KEY in ( select INVESTIGATION_KEY from rdb.dbo.TMP_CLDM_All_Case )
								  INNER JOIN	rdb.dbo.MORBIDITY_REPORT MR with ( nolock) ON ME.MORB_RPT_KEY = MR.MORB_RPT_KEY
								 WHERE     (I.RECORD_STATUS_CD = 'ACTIVE')
								;

								     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_morbResults'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_morbResults', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_morbResults 
								 ;



								select * 
								into rdb.dbo.TMP_CLDM_morbResults
								from RDB.dbo.lab100   with ( nolock) 
								where morb_rpt_key in(
								  SELECT ME.MORB_RPT_KEY
								   FROM RDB.dbo.MORBIDITY_REPORT_EVENT ME  with ( nolock) 
									 INNER JOIN	rdb.dbo.INVESTIGATION I with ( nolock) ON ME.INVESTIGATION_KEY = I.INVESTIGATION_KEY
									           		  AND I.INVESTIGATION_KEY in ( select INVESTIGATION_KEY from rdb.dbo.TMP_CLDM_All_Case )
									  WHERE (I.RECORD_STATUS_CD = 'ACTIVE')
								) 
								;

 

									     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_morbLabResults'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_morbLabResults', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_morbLabResults ;


		
								SELECT distinct a.investigation_key, 
									b.resulted_lab_test_key,
									a.MORB_RPT_LOCAL_ID as lab_rpt_LOCAL_ID , 
									b.LAB_RPT_RECEIVED_BY_PH_DT, b.SPECIMEN_COLLECTION_DT,
									b.RESULTED_LAB_TEST_CD_DESC, 
									b.RESULTEDTEST_VAL_CD_DESC, 
									b.NUMERIC_RESULT_WITHUNITS, 
									b.LAB_RESULT_TXT_VAL, 
									b.LAB_RESULT_COMMENTS
								into rdb.dbo.TMP_CLDM_morbLabResults
 								FROM rdb.dbo.TMP_CLDM_invmorb a 
								inner join rdb.dbo.TMP_CLDM_morbResults b  on	a.MORB_RPT_KEY = b.MORB_RPT_KEY
									;
 
							/* Retrieving LabResults for the Morbs (associated to INVs) ends here */

							/* APPEND both Labs(associated to INVs) and Morbs(with L/R Info associated to INVs now)*/
							/*
							DATA Inv2labs;
								SET Inv2labs Morblabresults; 
							RUN;
							*/



									     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_Inv2labs_final'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_Inv2labs_final', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_Inv2labs_final ;


								select  * 
								into rdb.dbo.TMP_CLDM_Inv2labs_final
								from rdb.dbo.TMP_CLDM_Inv2labs
								union
								select  *,null 
								from  rdb.dbo.TMP_CLDM_Morblabresults
								; 



							/* Displaying Lab Test-Result Information starts below */

									     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_sample1'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample1', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample1
	
								 select investigation_key as [KEY] ,
	 								'' as Bigchunk 
								 into rdb.dbo.TMP_CLDM_sample1
								 from rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART
								 ;
 


									     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_sample2'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample2', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample2
    
								select 	investigation_key as 'KEY',
									lab_test_key as  'SUBKEY',
									--put(datepart((LAB_RPT_RECEIVED_BY_PH_DT)),mmddyy10.) as c1 'C1',
									--put(datepart((SPECIMEN_COLLECTION_DT)),mmddyy10.) as c2 'C2',
									FORMAT (LAB_RPT_RECEIVED_BY_PH_DT, 'MM/dd/yyyy ') as C1,
									FORMAT (SPECIMEN_COLLECTION_DT, 'MM/dd/yyyy ') as C2,
									rtrim(RESULTED_LAB_TEST_CD_DESC) as 'C3',
									rtrim(RESULTEDTEST_VAL_CD_DESC) as  'C4',
									rtrim(NUMERIC_RESULT_WITHUNITS) as  'C5',
									substring(rtrim(LAB_RESULT_TXT_VAL),1,200) as  'C6',
									substring(rtrim(LAB_RESULT_COMMENTS),1,200) as  'C7',
									rtrim(LAB_RPT_LOCAL_ID) as  'C8',
  									rtrim(ELR_IND) as  'c9'
								into rdb.dbo.TMP_CLDM_sample2
								from rdb.dbo.TMP_CLDM_inv2labs_final
								;
 
								 CREATE NONCLUSTERED INDEX [idx_tmp_sample2_key] ON rdb.[dbo].[TMP_CLDM_sample2]
								(
									[KEY] ASC
								);


									     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;

			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_sample21'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample21', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample21
								 ;
    

												WITH lst AS (
										SELECT *, ROW_NUMBER() 
										over (
											PARTITION BY [key] 
											order by subkey
										) AS RowNo 
										FROM rdb.dbo.TMP_CLDM_sample2
										
									)
									SELECT * 
									into rdb.dbo.TMP_CLDM_sample21
									FROM lst WHERE RowNo <= 9
									;

		
 

									     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;


			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_sample3'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample3', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample3 ;

	
								select distinct [key], subkey,
									(
					                '<b>Local ID:</b> '  +  rtrim (coalesce(C8,''))  +  '<br>'  + 
									'<b>Date Received by PH:</b> '  + rtrim (coalesce(C1,''))  +  '<br>'  + 
									'<b>Specimen Collection Date:</b> '  +  rtrim (coalesce(C2,''))  +  '<br>'  + 
									'<b>ELR Indicator:</b>' +  rtrim (coalesce(c9,'')) +  '<br>'  + 
									'<b>Resulted Test:</b> '  +  (case when rtrim(coalesce(C3,''))='' THEN '' else rtrim(C3) END)  +  '<br>'  + 
									'<b>Coded Result:</b> '   +  (case when rtrim(coalesce(C4,''))='' THEN '' else rtrim(C4) END)  +  '<br>'  + 
									'<b>Numeric Result:</b> ' +  (case when rtrim(coalesce(C5,''))='' THEN '' else rtrim(C5) END)  +  '<br>'  + 
									'<b>Text Result:</b> '    +  (case when rtrim(coalesce(C6,''))='' THEN '' else rtrim(C6) END)  +  '<br>'  + 
									'<b>Comments:</b> '       +    (case when rtrim(coalesce(C7,''))='' THEN '' else rtrim(C7) END)
													 )	as bigChunk
								into rdb.dbo.TMP_CLDM_sample3
								from rdb.dbo.TMP_CLDM_sample21
								;


								/* --VS

							   data sample4
							   (rename=(
										lab9=lab_concatenated_desc_txt key=investigation_key)
										);
	
								set sample3;
								by	key;
	
	
								format lab1-lab8 $2000. lab9 $4000.;
								array lab(8) lab1-lab8;
								retain lab1-lab9 ' ' i 0;

								if first.key then do;
									do j=1 to 8; lab(j) = ' ';	end;
									i = 0; lab9 = ''; 
									end;
								i+1;
								if i <= 8 then do;
									lab(i) = bigChunk;
									lab9 =left(trim(bigChunk))||'<br><br>'|| left(trim(lab9)) ;
								end;
								if last.key then output;
							run;
							*/


									     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_sample4'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample4', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample4 ;


							   SELECT [key], bigChunk = 
								STUFF((SELECT DISTINCT '  <br><br>' + bigChunk
									   FROM rdb.dbo.TMP_CLDM_sample3 b 
									   WHERE b.[key] = a.[key] 
									  FOR XML PATH('')), 1, 2, '')
							   into rdb.dbo.TMP_CLDM_sample4
								FROM rdb.dbo.TMP_CLDM_sample3 a
								GROUP BY [key]
								;

							



	
									     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_sample5'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample5', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample5 ;

							--	create table sample5 as 
								select [key] as investigation_key, bigChunk as LABORATORY_INFORMATION 
								into rdb.dbo.TMP_CLDM_sample5
								 from rdb.dbo.TMP_CLDM_sample4;



							/*
							data rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART;
							   merge rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART sample5;
							   by investigation_key;
							run;
							*/

									     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_SPECIMEN_COLLECTION_TABLE'; 


		IF OBJECT_ID('rdb.dbo.TMP_SPECIMEN_COLLECTION_TABLE', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_SPECIMEN_COLLECTION_TABLE 
								 ;
	 
	  
							select  investigation_key as 'KEY', 
							  min(SPECIMEN_COLLECTION_DT) as SPECIMEN_COLLECTION_DT 
							into rdb.dbo.TMP_SPECIMEN_COLLECTION_TABLE
							from rdb.dbo.TMP_CLDM_Inv2labs_final
							group by investigation_key
							;



							 SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
		



			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CLDM_CASE_LAB_DATAMART_FINAL'; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART_FINAL', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART_FINAL ;


							select tcld.*,
							 replace(replace(replace(replace(ts5.Laboratory_Information, '&lt;', '<'), '&gt;', '>'), '&amp;', '&') ,'™', '&trade;')as Laboratory_Information,
				            	SPECIMEN_COLLECTION_DT as EARLIEST_SPECIMEN_COLLECTION_DT
							into rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART_FINAL
							from rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART tcld
							  left join  rdb.dbo.TMP_CLDM_sample5  ts5 with ( nolock) ON tcld.investigation_key = ts5.investigation_key 
  							LEFT OUTER JOIN  rdb.dbo.TMP_SPECIMEN_COLLECTION_TABLE tspt with ( nolock) ON   tcld.INVESTIGATION_KEY = tspt.[KEY]
						;

						/*
							   update rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART_FINAL
							   set Laboratory_Information = replace(replace(replace(Laboratory_Information, '&lt;', '<'), '&gt;', '>'), '&amp;', '&')
							   ;
                        */
							   	update rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART_FINAL
								set Laboratory_Information = substring(Laboratory_Information,9,len(Laboratory_Information))+'<br><br>'
								;


								
								update rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART_FINAL
								set Laboratory_Information = cast([LABORATORY_INFORMATION] as varchar(3996))+'<br>'
                                 where len(Laboratory_Information) >= 4000
								 ;


								 
								update TMP_CLDM_CASE_LAB_DATAMART_FINAL
								set EVENT_DATE = EARLIEST_SPECIMEN_COLLECTION_DT
								, EVENT_DATE_TYPE	='Specimen Collection Date of Earliest Associated Lab'
								where EARLIEST_SPECIMEN_COLLECTION_DT is not null
								;


							/************ LAB INFORMATION STUFF ENDS HERE **************************************************/



							/*
										     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING CASE_LAB_DATAMART'; 


		IF OBJECT_ID('rdb.dbo.CASE_LAB_DATAMART', 'U') IS NOT  NULL 
		                                      DROP TABLE rdb.dbo.CASE_LAB_DATAMART;
                           */


							-- VS  %dbload (CASE_LAB_DATAMART, rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART);
										     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING CASE_LAB_DATAMART'; 


		IF OBJECT_ID('rdb.dbo.CASE_LAB_DATAMART', 'U') IS  NULL 
		  
		  
										CREATE TABLE rdb.[dbo].[CASE_LAB_DATAMART](
											[INVESTIGATION_KEY] [bigint] NOT NULL,
											[PATIENT_LOCAL_ID] [varchar](50) NULL,
											[INVESTIGATION_LOCAL_ID] [varchar](50) NULL,
											[PATIENT_FIRST_NM] [varchar](50) NULL,
											[PATIENT_MIDDLE_NM] [varchar](50) NULL,
											[PATIENT_LAST_NM] [varchar](50) NULL,
											[PATIENT_STREET_ADDRESS_1] [varchar](100) NULL,
											[PATIENT_STREET_ADDRESS_2] [varchar](100) NULL,
											[PATIENT_CITY] [varchar](100) NULL,
											[PATIENT_STATE] [varchar](100) NULL,
											[PATIENT_ZIP] [varchar](20) NULL,
											[PATIENT_COUNTY] [varchar](300) NULL,
											[PATIENT_HOME_PHONE] [varchar](50) NULL,
											[PATIENT_DOB] [datetime] NULL,
											[AGE_REPORTED] [numeric](18, 0) NULL,
											[AGE_REPORTED_UNIT] [varchar](50) NULL,
											[PATIENT_CURRENT_SEX] [varchar](50) NULL,
											[RACE] [varchar](500) NULL,
											[JURISDICTION_NAME] [varchar](100) NULL,
											[PROGRAM_AREA_DESCRIPTION] [varchar](50) NULL,
											[INVESTIGATION_START_DATE] [datetime] NULL,
											[CASE_STATUS] [varchar](50) NULL,
											[DISEASE] [varchar](50) NULL,
											[DISEASE_CD] [varchar](50) NULL,
											[REPORTING_SOURCE] [varchar](100) NULL,
											[GENERAL_COMMENTS] [varchar](2000) NULL,
											[PHYSICIAN_NAME] [varchar](102) NULL,
											[PHYSICIAN_PHONE] [varchar](46) NULL,
											[LABORATORY_INFORMATION] [varchar](4000) NULL,
											[PROGRAM_JURISDICTION_OID] [numeric](18, 0) NULL,
											[PHC_ADD_TIME] [datetime] NULL,
											[PHC_LAST_CHG_TIME] [datetime] NULL,
											[EVENT_DATE] [datetime] NULL,
											 EARLIEST_SPECIMEN_COLLECT_DATE datetime,
											[EVENT_DATE_TYPE] [varchar](200) NULL,
										)  ON [PRIMARY]

										;


                                   Delete cld from  rdb.dbo.CASE_LAB_DATAMART cld
									 Inner Join rdb.[dbo].TMP_CLDM_CASE_LAB_DATAMART_FINAL  tcld
											  ON   tcld.[INVESTIGATION_KEY]=cld.INVESTIGATION_KEY
		                              ;


									delete
										from rdb.dbo.case_lab_datamart
										where investigation_key in (
										SELECT li.[INVESTIGATION_KEY]
											FROM [RDB].[dbo].[S_INVESTIGATION] si
											, [RDB].[dbo].[L_INVESTIGATION]li
											where RECORD_STATUS_CD = 'INACTIVE'
											and si.CASE_UID = li.CASE_UID
										)
										;

							insert into    [RDB].[dbo].CASE_LAB_DATAMART
							   ([INVESTIGATION_KEY]
								  ,[PATIENT_LOCAL_ID]
								  ,[INVESTIGATION_LOCAL_ID]
								  ,[PATIENT_FIRST_NM]
								  ,[PATIENT_MIDDLE_NM]
								  ,[PATIENT_LAST_NM]
								  ,[PATIENT_STREET_ADDRESS_1]
								  ,[PATIENT_STREET_ADDRESS_2]
								  ,[PATIENT_CITY]
								  ,[PATIENT_STATE]
								  ,[PATIENT_ZIP]
								  ,[PATIENT_COUNTY]
								  ,[PATIENT_HOME_PHONE]
								  ,[PATIENT_DOB]
								  ,[AGE_REPORTED]
								  ,[AGE_REPORTED_UNIT]
								  ,[PATIENT_CURRENT_SEX]
								  ,[RACE]
								  ,[JURISDICTION_NAME]
								  ,[PROGRAM_AREA_DESCRIPTION]
								  ,[INVESTIGATION_START_DATE]
								  ,[CASE_STATUS]
								  ,[DISEASE]
								  ,[DISEASE_CD]
								  ,[REPORTING_SOURCE]
								  ,[GENERAL_COMMENTS]
								  ,[PHYSICIAN_NAME]
								  ,[PHYSICIAN_PHONE]
								  ,[LABORATORY_INFORMATION]
								  ,[PROGRAM_JURISDICTION_OID]
								  ,[PHC_ADD_TIME]
								  ,[PHC_LAST_CHG_TIME]
								  ,[EVENT_DATE]
								  ,EARLIEST_SPECIMEN_COLLECT_DATE
								  ,EVENT_DATE_TYPE
							  )
							  SELECT distinct [INVESTIGATION_KEY]
								  ,[PATIENT_LOCAL_ID]
								  ,[INVESTIGATION_LOCAL_ID]
								  ,[PATIENT_FIRST_NM]
								  ,[PATIENT_MIDDLE_NM]
								  ,[PATIENT_LAST_NM]
								  ,[PATIENT_STREET_ADDRESS_1]
								  ,[PATIENT_STREET_ADDRESS_2]
								  ,[PATIENT_CITY]
								  ,[PATIENT_STATE]
								  ,[PATIENT_ZIP]
								  ,[PATIENT_COUNTY]
								  ,[PATIENT_HOME_PHONE]
								  ,[PATIENT_DOB]
								  ,[AGE_REPORTED]
								  ,[AGE_REPORTED_UNIT]
								  ,[PATIENT_CURRENT_SEX]
								  ,[RACE]
								  ,[JURISDICTION_NAME]
								  ,[PROGRAM_AREA_DESCRIPTION]
								  ,[INVESTIGATION_START_DATE]
								  ,[CASE_STATUS]
								  ,[DISEASE]
								  ,[DISEASE_CD]
								  ,[REPORTING_SOURCE]
								  ,[GENERAL_COMMENTS]
								  ,[PHYSICIAN_NAME]
								  ,[PHYSICIAN_PHONE]
								  ,cast([LABORATORY_INFORMATION] as varchar(4000))
								  ,[PROGRAM_JURISDICTION_OID]
								  ,[PHC_ADD_TIME]
								  ,[PHC_LAST_CHG_TIME]
								  ,[EVENT_DATE]
								  ,EARLIEST_SPECIMEN_COLLECTION_DT
								  ,EVENT_DATE_TYPE
								 FROM [RDB].[dbo].TMP_CLDM_CASE_LAB_DATAMART_FINAL
							  ;

 

								     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES(@BATCH_ID,'CASE_LAB_DATAMART','RDB.CASE_LAB_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_CASE_LAB_DATAMART_MODIFIED'; 


		IF OBJECT_ID('rdb.dbo.TMP_CASE_LAB_DATAMART_MODIFIED', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CASE_LAB_DATAMART_MODIFIED 
								 ;


							 SELECT  *,SPECIMEN.SPECIMEN_COLLECTION_DT as SPECIMEN_COLLECTION_DT_2
							   into rdb.dbo.TMP_CASE_LAB_DATAMART_MODIFIED
							   FROM RDB.dbo.TMP_CLDM_CASE_LAB_DATAMART  case1
								LEFT OUTER JOIN  rdb.dbo.TMP_SPECIMEN_COLLECTION_TABLE SPECIMEN with ( nolock) ON   CASE1.INVESTIGATION_KEY = SPECIMEN.[KEY]
							;


             COMMIT TRANSACTION;

			 
            BEGIN TRANSACTION; 


		IF OBJECT_ID('rdb.dbo.TMP_CLDM_All_Case', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_All_Case; 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PATIENT_ADD', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PATIENT_ADD ;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PAT_ADD_INV', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PAT_ADD_INV 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_PROVIDER 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_INVESTIGATOR', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PATCOMPL_INV_INVESTIGATOR 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_GEN_PATINFO_INV_PHY_RPTSRC_COND 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_invlab', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_invlab ;

  	    IF OBJECT_ID('rdb.dbo.TMP_CLDM_lab', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_lab ;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_both', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_both 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_inv2labs', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_inv2labs 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_invmorb', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_invmorb 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_morbResults', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_morbResults 

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_morbLabResults', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_morbLabResults ;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_Inv2labs_final', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_Inv2labs_final ;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample1', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample1;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample2', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample2;

		
		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample21', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample21;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample3', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample3 ;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample4', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample4 ;

		IF OBJECT_ID('rdb.dbo.TMP_CLDM_sample5', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CLDM_sample5 ;

		--IF OBJECT_ID('rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART_FINAL', 'U') IS NOT NULL   -- NEEDED FOR INV_SUMM_DATAMART
		--						 drop table  rdb.dbo.TMP_CLDM_CASE_LAB_DATAMART_FINAL ;

		IF OBJECT_ID('rdb.dbo.TMP_SPECIMEN_COLLECTION_TABLE', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_SPECIMEN_COLLECTION_TABLE ;

		IF OBJECT_ID('rdb.dbo.TMP_CASE_LAB_DATAMART_MODIFIED', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TMP_CASE_LAB_DATAMART_MODIFIED;
		
			
		IF OBJECT_ID('rdb.dbo.TEMP_UPDATED_LAB_INV_MAP', 'U') IS NOT NULL 
								 drop table  rdb.dbo.TEMP_UPDATED_LAB_INV_MAP;			


			COMMIT TRANSACTION;
 
            BEGIN TRANSACTION; 

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;

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
						   'CASE_LAB_DATAMART'
						   ,'RDB.CASE_LAB_DATAMART'
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
					   @batch_id
					   ,'CASE_LAB_DATAMART'	
					   ,'RDB.CASE_LAB_DATAMART'
					   ,'ERROR'
					   ,@Proc_Step_no
					   ,'ERROR - '+ @Proc_Step_name
					   , 'Step -' +CAST(@Proc_Step_no AS VARCHAR(3))+' -' +CAST(@ErrorMessage AS VARCHAR(500))
					   ,0
					   );
  

				  return -1 ;

				END CATCH
	
END


