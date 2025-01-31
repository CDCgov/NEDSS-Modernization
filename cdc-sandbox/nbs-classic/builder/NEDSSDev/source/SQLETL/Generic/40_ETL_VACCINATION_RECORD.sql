USE [RDB]
GO
IF NOT EXISTS (SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='D_VACCINATION'
										AND COLUMN_NAME ='VACCINATION_UID' ) 
		BEGIN
		ALTER TABLE rdb.dbo.D_VACCINATION
		ADD  [VACCINATION_UID] bigint NOT NULL DEFAULT '1'
		END 
		
		----Dropping all Tmp tables
IF OBJECT_ID('rdb.dbo.TMP_S_VACCINATION_UIDS', 'U') IS NOT NULL   -------------Step1
  drop table rdb.dbo.TMP_S_VACCINATION_UIDS;
IF OBJECT_ID('rdb.dbo.TMP_VACCINATION_INIT', 'U') IS NOT NULL ------------------Step2  
 drop table rdb.dbo.TMP_VACCINATION_INIT;
 
 IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_TEXT', 'U') IS NOT NULL ----------------Step3 
	drop table rdb.dbo.RDB_UI_METADATA_TEXT;
IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_TEXT_FINAL', 'U') IS NOT NULL  -----------Step3
	drop table rdb.dbo.RDB_UI_METADATA_TEXT_FINAL

IF OBJECT_ID('rdb.dbo.text_data_VR', 'U') IS NOT NULL  --------------------------Step4
drop table rdb.dbo.text_data_VR
IF OBJECT_ID('rdb.dbo.Text_data_VR_out', 'U') IS NOT NULL  
drop table rdb.dbo.Text_data_VR_out--------------------------------------------Step5

IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_CODED', 'U') IS NOT NULL------------------Step6  
 drop table rdb.dbo.RDB_UI_METADATA_CODED ;
IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_CODED_FINAL', 'U') IS NOT NULL--------------Step6  
drop table rdb.dbo.RDB_UI_METADATA_CODED_FINAL;

IF OBJECT_ID('rdb.dbo.CODED_TABLE_VR', 'U') IS NOT NULL  -------------------------Step7
 drop table rdb.dbo.CODED_TABLE_VR;
IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNM', 'U') IS NOT NULL  -----------------------Step8
 drop table rdb.dbo.CODED_TABLE_SNM;

IF OBJECT_ID('rdb.dbo.CODED_TABLE_NONSNM', 'U') IS NOT NULL  --------------------Step9
  drop table rdb.dbo.CODED_TABLE_NONSNM;
IF OBJECT_ID('rdb.dbo.Combined_TABLE_NONSNM_SNM', 'U') IS NOT NULL  ---added 9/9/2021
  drop table rdb.dbo.Combined_TABLE_NONSNM_SNM;


IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_NUMERIC', 'U') IS NOT NULL ----------------Step10 
drop table  rdb.dbo.RDB_UI_METADATA_NUMERIC;
IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP', 'U') IS NOT NULL ---------------------Step11 
 drop table  rdb.dbo.CODED_TABLE_SNTEMP;
 
IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS', 'U') IS NOT NULL  --------------Step12
drop table  rdb.dbo.CODED_TABLE_SNTEMP_TRANS;
IF OBJECT_ID('rdb.dbo.CODED_TABLE_VR1', 'U') IS NOT NULL ------------------------Step12 
drop table  rdb.dbo.CODED_TABLE_VR1;

IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_TEMP', 'U') IS NOT NULL  ------------------Step13
drop table rdb.dbo.CODED_TABLE_DESC_TEMP ;

IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC', 'U') IS NOT NULL ------------------------Step13 
drop table rdb.dbo.CODED_TABLE_DESC ;
										
IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE', 'U') IS NOT NULL  ---------------------Step14
drop table rdb.dbo.CODED_COUNTY_TABLE  ;
IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_TEMP', 'U') IS NOT NULL ------------Step14 
drop table rdb.dbo.CODED_COUNTY_TABLE_DESC_TEMP ;
IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC', 'U') IS NOT NULL  ---------------Step14
drop table rdb.dbo.CODED_COUNTY_TABLE_DESC ;

 IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTH', 'U') IS NOT NULL  ----------------------Step15
drop table rdb.dbo.CODED_TABLE_OTH ;
IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED', 'U') IS NOT NULL ----------------------Step15 
drop table rdb.dbo.CODED_TABLE_MERGED

IF OBJECT_ID('rdb.dbo.CODED_DATA_OUT', 'U') IS NOT NULL---------------------------Step16
drop table rdb.dbo.CODED_DATA_OUT

IF OBJECT_ID('rdb.dbo.rdb_ui_metadata_DATE', 'U') IS NOT NULL  -------------------Step17
drop table rdb.dbo.rdb_ui_metadata_DATE
												  
IF OBJECT_ID('RDB.dbo.RDB_UI_METADATA_DateFinal', 'U') IS NOT NULL  -------------Step17
drop table rdb.dbo.RDB_UI_METADATA_DateFinal;

IF OBJECT_ID('rdb.dbo.DATE_DATA_VR', 'U') IS NOT NULL ---------------------------Step18 
drop table rdb.dbo.DATE_DATA_VR;

IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_VR', 'U') IS NOT NULL  --------------------Step19
drop table rdb.dbo.PAGE_DATE_TABLE_VR;
IF OBJECT_ID('rdb.dbo.DATE_DATA_VR_out', 'U') IS NOT NULL  ----------------------Step19
drop table   rdb.dbo.DATE_DATA_VR_out;

IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_Numeric_all', 'U') IS NOT NULL ------------Step20 
drop table rdb.dbo.RDB_UI_METADATA_Numeric_all ;
IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_NumericFinal', 'U') IS NOT NULL  ----------Step20
drop table rdb.dbo.RDB_UI_METADATA_NumericFinal;

IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_VR', 'U') IS NOT NULL--------------------Step21  
drop table rdb.dbo.NUMERIC_BASE_DATA_VR ;
IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_VR', 'U') IS NOT NULL ---------------------Step22 
drop table NUMERIC_DATA_2_VR ;
IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_VR', 'U') IS NOT NULL  ---------------Step23
drop table rdb.dbo.NUMERIC_DATA_MERGED_VR;
IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_VR', 'U') IS NOT NULL  ----------------Step24
drop table rdb.dbo.NUMERIC_DATA_TRANS_VR
 IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_out', 'U') IS NOT NULL  ----------------------Step25
drop table rdb.dbo.NUMERIC_DATA_out;

IF OBJECT_ID('rdb.dbo.tmp_NUMERIC_DATA_out', 'U') IS NOT NULL  -----------------Step26
drop table rdb.dbo.tmp_NUMERIC_DATA_out;

IF OBJECT_ID('rdb.dbo.tmp_DATE_DATA_VR_out', 'U') IS NOT NULL  -----------------Step26
drop table   rdb.dbo.tmp_DATE_DATA_VR_out;
												
IF OBJECT_ID('rdb.dbo.tmp_CODED_DATA_OUT', 'U') IS NOT NULL  -------------------Step26
drop table rdb.dbo.tmp_CODED_DATA_OUT;
												
IF OBJECT_ID('rdb.dbo.tmp_Text_data_VR_out', 'U') IS NOT NULL  -----------------Step26
drop table rdb.dbo.tmp_Text_data_VR_out;

/*IF OBJECT_ID('rdb.dbo.[S_VACCINATION]', 'U') IS NOT NULL  -----------------------Step27---commented on 8-23-2021
 drop table rdb.[dbo].[S_VACCINATION];*/
IF OBJECT_ID('rdb.dbo.[TMP_S_VACCINATION]', 'U') IS NOT NULL---------------------Step27  
drop table rdb.[dbo].[TMP_S_VACCINATION];
	
IF OBJECT_ID('rdb.dbo.TMP_L_VACCINATION_N', 'U') IS NOT NULL  -------------------Step28 
drop table rdb.dbo.TMP_L_VACCINATION_N ;
IF OBJECT_ID('rdb.dbo.TMP_L_VACCINATION_E', 'U') IS NOT NULL---------------------Step29  
DROP TABLE rdb.dbo.TMP_L_VACCINATION_E;
									
IF OBJECT_ID('rdb.dbo.TMP_PARTICIPATION_INIT', 'U') IS NOT NULL  ----------------Step32
DROP TABLE rdb.dbo.TMP_PARTICIPATION_INIT;

IF OBJECT_ID('rdb.dbo.TMP_PARTICIPATION_PAT', 'U') IS NOT NULL-------------------Step33  
DROP TABLE rdb.dbo.TMP_PARTICIPATION_PAT;
IF OBJECT_ID('rdb.dbo.TMP_PARTICIPATION_PRV', 'U') IS NOT NULL  ----------------Step33
DROP TABLE rdb.dbo.TMP_PARTICIPATION_PRV;

IF OBJECT_ID('rdb.dbo.TMP_PARTICIPATION_ORG', 'U') IS NOT NULL -----------------Step34 
DROP TABLE rdb.dbo.TMP_PARTICIPATION_ORG;
IF OBJECT_ID('rdb.dbo.TMP_VACCINATION_FACT_INIT', 'U') IS NOT NULL --------------Step35 
DROP TABLE rdb.dbo.TMP_VACCINATION_FACT_INIT;

IF OBJECT_ID('rdb.dbo.TMP_VACCINATION_PHC_COLL', 'U') IS NOT NULL --------------Step36 
DROP TABLE rdb.dbo.TMP_VACCINATION_PHC_COLL;
IF OBJECT_ID('rdb.dbo.PHC_INVESTIGATION_KEY', 'U') IS NOT NULL  ----------------Step36
 DROP TABLE rdb.dbo.PHC_INVESTIGATION_KEY;

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

IF OBJECT_ID('rdb.dbo.[sp_VACCINATION_RECORD]', 'P') IS NOT NULL
BEGIN
DROP PROCEDURE dbo.sp_VACCINATION_RECORD
END;
GO

CREATE PROCEDURE [dbo].[sp_VACCINATION_RECORD]
 @batch_id BIGINT
as
BEGIN 

           ---EXEC sp_VACCINATION_RECORD 12345
           -- Delete from rdb.[dbo].[job_flow_log] where batch_id =12345
           -- Select * from rdb.[dbo].[job_flow_log] where batch_id =12345
          ----  DECLARE @batch_id BIGINT = 12345
            DECLARE @RowCount_no INT ;
			DECLARE @Proc_Step_no FLOAT = 0 ;
			DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
			DECLARE @batch_start_time datetime2(7) = null ;
			DECLARE @batch_end_time datetime2(7) = null ;
/*
BEGIN
IF NOT EXISTS (SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='D_VACCINATION'
										AND COLUMN_NAME ='VACCINATION_UID' ) 
		BEGIN
		ALTER TABLE rdb.dbo.D_VACCINATION
		ADD  [VACCINATION_UID] bigint NOT NULL DEFAULT '1'
		END 
END
*/			
BEGIN TRY
			SET @Proc_Step_no = 1;
			SET @Proc_Step_Name = 'SP_Start';

		    BEGIN TRANSACTION;
								INSERT INTO rdb.[dbo].[job_flow_log] (
												batch_id         ---------------@batch_id
											   ,[Dataflow_Name]  --------------'VACCINATION_RECORD'
											   ,[package_Name]   --------------'RDB.VaccinationRecord'
											   ,[Status_Type]    ---------------START
											   ,[step_number]    ---------------@Proc_Step_no
											   ,[step_name]   ------------------@Proc_Step_Name=sp_start
											   ,[row_count] --------------------0
											   )
											   VALUES
											   (
											   @batch_id
											   ,'Vaccination_Record'
											   ,'RDB.VaccinationRecord'
											   ,'START'
											   ,@Proc_Step_no
											   ,@Proc_Step_Name
											   ,0  );
            COMMIT TRANSACTION;
		
								SELECT @batch_start_time = batch_start_dttm,@batch_end_time = batch_end_dttm
								FROM rdb.[dbo].[job_batch_log]
								WHERE status_type = 'start' ;

-------------------------------------------------1. CREATE TABLE TMP_S_VACCINATION_UIDS----------------------------------------------------------------------------

			BEGIN TRANSACTION;
								SET @Proc_Step_name='TMP_S_VACCINATION_UIDS';
								SET @Proc_Step_no = 1;
						
									IF OBJECT_ID('rdb.dbo.TMP_S_VACCINATION_UIDS', 'U') IS NOT NULL   
 									   drop table rdb.dbo.TMP_S_VACCINATION_UIDS;

									SELECT 
												INTERVENTION_UID AS PAGE_UID,
												INTERVENTION.LAST_CHG_TIME
								    INTO rdb.dbo.TMP_S_VACCINATION_UIDS 
									FROM NBS_ODSE.dbo.INTERVENTION with (nolock)
	                                INNER JOIN NBS_ODSE.dbo.NBS_ACT_ENTITY with (nolock)ON NBS_ACT_ENTITY.ACT_UID = INTERVENTION.INTERVENTION_UID
									WHERE NBS_ACT_ENTITY.TYPE_CD='SubOfVacc'
                                    AND INTERVENTION.LAST_CHG_TIME>= @batch_start_time	AND INTERVENTION.LAST_CHG_TIME <  @batch_end_time---- Incremental 
										  
									SELECT @ROWCOUNT_NO = @@ROWCOUNT;

									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;
----Select * from rdb.dbo.TMP_S_VACCINATION_UIDS 
---------------------------------------------------2. CREATE TABLE TMP_VACCINATION_INIT--------------------------------------------------------------------------

			BEGIN TRANSACTION;
								SET @Proc_Step_name='TMP_VACCINATION_INIT';
								SET @Proc_Step_no = 2;
						
												IF OBJECT_ID('rdb.dbo.TMP_VACCINATION_INIT', 'U') IS NOT NULL   
 												   drop table rdb.dbo.TMP_VACCINATION_INIT;
									SELECT 
									       I.ADD_TIME,
										   I.ADD_USER_ID, 
										   I.AGE_AT_VACC	AS	AGE_AT_VACCINATION ,
									       I.AGE_AT_VACC_UNIT_CD,--- AS AGE_AT_VACCINATION_UNIT---3,
										   I.LAST_CHG_TIME  ,
										   I.LAST_CHG_USER_ID ,
										   I.LOCAL_ID, 
										   I.RECORD_STATUS_CD,
										   I.RECORD_STATUS_TIME , 
										   I.ACTIVITY_FROM_TIME	AS	VACCINE_ADMINISTERED_DATE ,
										   I.VACC_DOSE_NBR	AS	VACCINE_DOSE_NBR ,
										   I.MATERIAL_CD ,--AS VACCINATION_ADMINISTERED_NM,----1
										   I.TARGET_SITE_CD ,---AS VACCINATION_ANATOMICAL_SITE---2,
										   I.INTERVENTION_UID AS VACCINATION_UID ,
									       I.MATERIAL_EXPIRATION_TIME AS VACCINE_EXPIRATION_DT ,
									       I.VACC_INFO_SOURCE_CD ,-----AS VACCINE_INFO_SOURCE,----5
									       I.MATERIAL_LOT_NM	AS	VACCINE_LOT_NUMBER_TXT ,
										   I.VACC_MFGR_CD, ---AS VACCINE_MANUFACTURER_NM,---4
										   I.VERSION_CTRL_NBR ,
										   I.ELECTRONIC_IND,
										   Cast (null as  [varchar](100))  VACCINATION_ADMINISTERED_NM ,----1
										   Cast (null as  [varchar](100))  VACCINATION_ANATOMICAL_SITE ,----2
										   Cast (null as  [varchar](100))  AGE_AT_VACCINATION_UNIT ,-----3
										   Cast (null as  [varchar](100))  VACCINE_MANUFACTURER_NM ,-----4
						                   Cast (null as  [varchar](100))  VACCINE_INFO_SOURCE-----5

									INTO rdb.dbo.TMP_VACCINATION_INIT
								    FROM NBS_ODSE.dbo.INTERVENTION I with (nolock)
									INNER JOIN rdb.dbo.TMP_S_VACCINATION_UIDS S ON I.INTERVENTION_UID =S.PAGE_UID;

									SELECT @ROWCOUNT_NO = @@ROWCOUNT;

									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
								 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
								 VALUES(@BATCH_ID,'Vaccination_Record','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;

-----------------------------
CREATE NONCLUSTERED INDEX [IDX_VACC_01]
ON rdb.[dbo].[TMP_VACCINATION_INIT] ([MATERIAL_CD]);

UPDATE rdb.dbo.TMP_VACCINATION_INIT---------------1
SET rdb.dbo.TMP_VACCINATION_INIT.VACCINATION_ADMINISTERED_NM = cvg.code_short_desc_txt
FROM
 NBS_ODSE.[dbo].[NBS_question]  nq with (nolock),
 [NBS_SRTE].[dbo].[Codeset] cd with (nolock),
 [NBS_SRTE].[dbo].[Code_value_general] cvg with (nolock),
 rdb.dbo.TMP_VACCINATION_INIT  vac
  where nq.question_identifier = ('VAC101')
	and   cd.code_set_group_id = nq.code_set_group_id
	and   cvg.code_set_nm = cd.code_set_nm
	and   vac.Material_cd = cvg.code
	and   vac.Material_cd is not null 

CREATE NONCLUSTERED INDEX [IDX_VACC_02]
ON RDB.[dbo].[TMP_VACCINATION_INIT] ([TARGET_SITE_CD])
UPDATE rdb.dbo.TMP_VACCINATION_INIT-------------------2
SET rdb.dbo.TMP_VACCINATION_INIT.VACCINATION_ANATOMICAL_SITE =cvg.code_short_desc_txt
from 
NBS_ODSE.[dbo].[NBS_question] nq with (nolock),
 [NBS_SRTE].[dbo].[Codeset] cd with (nolock),
 [NBS_SRTE].[dbo].[Code_value_general] cvg with (nolock),
 rdb.dbo.TMP_VACCINATION_INIT  vac
  where nq.question_identifier = ('VAC104')
	and   cd.code_set_group_id = nq.code_set_group_id
	and   cvg.code_set_nm = cd.code_set_nm
	and   vac.TARGET_SITE_CD = cvg.code
	and   vac.TARGET_SITE_CD is not null 

CREATE NONCLUSTERED INDEX [IDX_VACC_03]
ON RDB.[dbo].[TMP_VACCINATION_INIT] ([AGE_AT_VACC_UNIT_CD])
UPDATE rdb.dbo.TMP_VACCINATION_INIT----------------3
SET rdb.dbo.TMP_VACCINATION_INIT.AGE_AT_VACCINATION_UNIT = cvg.code_short_desc_txt
FROM
 NBS_ODSE.[dbo].[NBS_question] nq with (nolock),
 [NBS_SRTE].[dbo].[Codeset] cd with (nolock) ,
 [NBS_SRTE].[dbo].[Code_value_general] cvg with (nolock),
 rdb.dbo.TMP_VACCINATION_INIT  vac
  where nq.question_identifier = ('VAC106')
	and   cd.code_set_group_id = nq.code_set_group_id
	and   cvg.code_set_nm = cd.code_set_nm
	and   vac.AGE_AT_VACC_UNIT_CD = cvg.code
	and   vac.AGE_AT_VACC_UNIT_CD is not null 
			 ;
CREATE NONCLUSTERED INDEX [IDX_VACC_04]
ON RDB.[dbo].[TMP_VACCINATION_INIT] ([VACC_MFGR_CD])
UPDATE rdb.dbo.TMP_VACCINATION_INIT----------------4
SET rdb.dbo.TMP_VACCINATION_INIT.VACCINE_MANUFACTURER_NM = cvg.code_short_desc_txt
FROM
 NBS_ODSE.[dbo].[NBS_question] nq with (nolock),
 [NBS_SRTE].[dbo].[Codeset] cd with (nolock),
 [NBS_SRTE].[dbo].[Code_value_general] cvg with (nolock),
 rdb.dbo.TMP_VACCINATION_INIT  vac
  where nq.question_identifier = ('VAC107')
	and   cd.code_set_group_id = nq.code_set_group_id
	and   cvg.code_set_nm = cd.code_set_nm
	and   vac.VACC_MFGR_CD = cvg.code
	and   vac.VACC_MFGR_CD is not null 


CREATE NONCLUSTERED INDEX [IDX_VACC_05]
ON RDB.[dbo].[TMP_VACCINATION_INIT] ([VACC_INFO_SOURCE_CD])
UPDATE rdb.dbo.TMP_VACCINATION_INIT----------------5
SET rdb.dbo.TMP_VACCINATION_INIT.VACCINE_INFO_SOURCE = cvg.code_short_desc_txt
FROM
 NBS_ODSE.[dbo].[NBS_question] nq with (nolock),
 [NBS_SRTE].[dbo].[Codeset] cd with (nolock),
 [NBS_SRTE].[dbo].[Code_value_general] cvg with (nolock),
 rdb.dbo.TMP_VACCINATION_INIT  vac
  where nq.question_identifier = ('VAC147')
	and   cd.code_set_group_id = nq.code_set_group_id
	and   cvg.code_set_nm = cd.code_set_nm
	and   vac.VACC_INFO_SOURCE_CD  = cvg.code
	and   vac.VACC_INFO_SOURCE_CD  is not null 
								
---%MACRO PROCESS_INCR_STAGING_DATA(ODS_TABLE, TABLE_NM,      STAGING_UIDS,        UID,       DATA_LOCATION,          ACT_UID,    answer_UID);---from main proc
---%PROCESS_INCR_STAGING_DATA      (NBS_ANSWER,'D_VACCINATION',S_VACCINATION_UIDS, PAGE_UID, 'NBS_ANSWER.ANSWER_TXT', ACT_UID,    NBS_ANSWER_UID);-----MACRO USE

-----------------------------------------------below are from Macro Tables (ETL MACRO)Populating------------------

--------------------------------------------------3. CREATE TABLE rdb_ui_metadata_TEXT-------------------------to get text data
	
			BEGIN TRANSACTION;

								SET @Proc_Step_no = 3;----(macro table 1)---------------rdb.dbo.RDB_UI_METADATA_VR1--rdb_ui_metadata----Table1---First rdb_ui_metadata
								SET @Proc_Step_Name = 'Generating rdb_ui_metadata_TEXT'; 

									IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_TEXT', 'U') IS NOT NULL  
									    drop table rdb.dbo.RDB_UI_METADATA_TEXT;
								    IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_TEXT_FINAL', 'U') IS NOT NULL  
								        drop table rdb.dbo.RDB_UI_METADATA_TEXT_FINAL

										SELECT
													 NUIM.NBS_QUESTION_UID, 
													 NUIM.CODE_SET_GROUP_ID,
													 NRDBM.RDB_COLUMN_NM,
													 NUIM.INVESTIGATION_FORM_CD,
													 NUIM.QUESTION_GROUP_SEQ_NBR,
													 NUIM.DATA_TYPE
										INTO rdb.dbo.RDB_UI_METADATA_TEXT
										FROM NBS_ODSE.[dbo].[NBS_rdb_metadata] NRDBM with ( nolock ) ,
													NBS_ODSE.[dbo].[NBS_ui_metadata] NUIM   with ( nolock ) 
										WHERE NRDBM.NBS_UI_METADATA_UID=NUIM.NBS_UI_METADATA_UID
									    AND NRDBM.RDB_TABLE_NM='D_VACCINATION'
									    AND QUESTION_GROUP_SEQ_NBR IS NULL
									    AND DATA_TYPE='TEXT'
									    AND NUIM.DATA_LOCATION ='NBS_ANSWER.ANSWER_TXT'
										--AND NUIM.LAST_CHG_TIME>= @batch_start_time	AND NUIM.LAST_CHG_TIME <  @batch_end_time---- Incremental 	
										  
									   SELECT * 
									   into [RDB].[dbo].RDB_UI_METADATA_TEXT_FINAL
									   FROM
									   (
									   SELECT *, 
									   ROW_NUMBER () OVER (PARTITION BY NBS_QUESTION_UID  order by NBS_QUESTION_UID ) rowid
									   FROM rdb.dbo.RDB_UI_METADATA_TEXT
									   ) AS CTE WHERE rowid=1;
                                         
										
										SELECT @RowCount_no = @@ROWCOUNT;

										INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
										 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
										 VALUES(@BATCH_ID,'Vaccination_Record','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 
			COMMIT TRANSACTION;
--------------------------------------------------4. CREATE TABLE text_data_VR-----------------------------------------------to get text data
  
		    BEGIN TRANSACTION;
                                    SET @Proc_Step_no = 4;---(macro table 2)------------------------------------TEXT_DATA----Table 2
									SET @Proc_Step_Name = 'Generating text_data_VR'; 

										IF OBJECT_ID('rdb.dbo.text_data_VR', 'U') IS NOT NULL  
										 drop table rdb.dbo.text_data_VR

											SELECT  DISTINCT
													meta.NBS_QUESTION_UID,
													meta.CODE_SET_GROUP_ID,
													meta.RDB_COLUMN_NM,
													PA.NBS_ANSWER_UID, ----&answer_UID, 
													cast (REPLACE(PA.ANSWER_TXT, CHAR(13)+CHAR(10), ' ') as varchar(2000)) as ANSWER_TXT,
													coalesce(PA.ACT_UID,1)  AS PAGE_UID ,-------&UID 
													PA.RECORD_STATUS_CD 
													
											INTO rdb.dbo.TEXT_DATA_VR
											FROM  rdb.dbo.RDB_UI_METADATA_TEXT_FINAL meta with (nolock) 
											        LEFT OUTER JOIN NBS_ODSE.dbo.NBS_ANSWER PA  with (nolock) on meta.nbs_question_uid=PA.nbs_question_uid AND pa.ANSWER_GROUP_SEQ_NBR IS NULL
													LEFT OUTER JOIN rdb.dbo.TMP_S_VACCINATION_UIDS STG with (nolock) on STG.PAGE_UID=PA.act_uid
													INNER JOIN NBS_SRTE.dbo.CODE_VALUE_GENERAL CVG  with (nolock)  ON 	CVG.CODE=meta.DATA_TYPE
												WHERE CVG.CODE_SET_NM = 'NBS_DATA_TYPE' AND CODE = 'TEXT'
											  
												ORDER  BY NBS_ANSWER_UID, 
												meta.CODE_SET_GROUP_ID,
												RDB_COLUMN_NM,
												cast (REPLACE(ANSWER_TXT, CHAR(13)+CHAR(10), ' ') as varchar(2000)) ,
												coalesce(ACT_UID,1),
												PA.RECORD_STATUS_CD,
												meta.NBS_QUESTION_UID;

												---To delete the rdb_column_nm when Answer_TXt is null
												SELECT @RowCount_no = @@ROWCOUNT

												;WITH CTE  as (
												select VR.rdb_column_nm, CASE WHEN VR.answer_txt is not null  THEN 1 ELSE 0 END as Ans_null
												FROM  rdb.dbo.TEXT_DATA_VR VR
												 where RDB_COLUMN_NM  in ( select RDB_COLUMN_NM from rdb.dbo.RDB_UI_METADATA_TEXT_FINAL group by rdb_column_nm)
												 group by rdb_column_nm, CASE WHEN answer_txt is not null  THEN 1 ELSE 0 END
												 )
												 DELETE FROM  rdb.dbo.TEXT_DATA_VR
												 where rdb_column_nm in (select rdb_column_nm
												 from CTE
												 where ans_null = 0 
												 except
												 select rdb_column_nm
												 from CTE 
												 where ans_null = 1
												 );
												
                                           INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
										   (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
										   VALUES(@BATCH_ID,'Vaccination_Record','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

                                         ---Print @RowCount_no
										 ---Introduce dummy row
										 IF  @RowCount_no=0 
										 BEGIN
										 INSERT INTO rdb.dbo.TEXT_DATA_VR Values(0,0,'Text',0,'Text',0,'NULL')
										 END

			COMMIT TRANSACTION;
------------------------------------------------------------5.CREATE TABLE Text_data_VR_out----------------------------------to get text data  

 
		    BEGIN TRANSACTION;
										SET @Proc_Step_no = 5;---(macro Table 3)
										SET @Proc_Step_Name = 'Generating Text_data_VR_out'; -------------------TEXT_DATA_OUT---Table3---table in main to join


												IF OBJECT_ID('rdb.dbo.Text_data_VR_out', 'U') IS NOT NULL  
												drop table rdb.dbo.Text_data_VR_out

												DECLARE @columns1 NVARCHAR(MAX);
												DECLARE @sql1 NVARCHAR(MAX);

												SET @columns1 = N'';

												SELECT @columns1+=N', p.'+QUOTENAME(LTRIM(RTRIM([RDB_COLUMN_NM])))
													FROM
													(
														SELECT RDB_COLUMN_NM 
														FROM rdb.dbo.Text_data_VR AS p
													   GROUP BY [RDB_COLUMN_NM]
													   ) as X;
												--print @columns1 
			                           			SET @sql1 = N'
													SELECT [PAGE_UID] as PAGE_UID_text, '+STUFF(@columns1, 1, 2, '')+
													' into rdb.dbo.Text_data_VR_out ' +
			                                    		'FROM (
													SELECT [PAGE_UID],[answer_txt],[RDB_COLUMN_NM] 
													FROM rdb.[dbo].TEXT_DATA_VR
													group by [PAGE_UID],[answer_txt],[RDB_COLUMN_NM]
														) AS j PIVOT (max(answer_txt) FOR [RDB_COLUMN_NM] in 
															('+STUFF(REPLACE(@columns1, ', p.[', ',['), 1, 1, '')+')) AS p;';

													---print @sql1;
													EXEC sp_executesql @sql1;

												

												   SELECT @RowCount_no = @@ROWCOUNT
													 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
													 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
													 VALUES(@BATCH_ID,'Vaccination_Record','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 
													
			COMMIT TRANSACTION;
---Select * from  rdb.dbo.Text_data_VR_out 
-----------------------------------------------------------6.CREATE TABLE rdb.dbo.RDB_UI_METADATA_CODED-----------------to get Coded data------------------------------------------------
  
			BEGIN TRANSACTION;
												SET @Proc_Step_no = 6;---(macro Table 4)
												SET @Proc_Step_Name = 'Generating rdb.dbo.RDB_UI_METADATA_CODED'; ---------second -rdb_ui_metadata---table4
										
												IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_CODED', 'U') IS NOT NULL  
												 drop table rdb.dbo.RDB_UI_METADATA_CODED ;

													 SELECT  
														NUIM.NBS_QUESTION_UID,
														NUIM.CODE_SET_GROUP_ID,  
														NRDBM.RDB_COLUMN_NM,
														NUIM.INVESTIGATION_FORM_CD,
												        QUESTION_GROUP_SEQ_NBR,
														NUIM.DATA_TYPE,
														NUIM.unit_value
													INTO rdb.dbo.RDB_UI_METADATA_CODED
													FROM
														NBS_ODSE.dbo.NBS_RDB_METADATA NRDBM with (nolock),
														NBS_ODSE.dbo.NBS_UI_METADATA NUIM with (nolock)
													WHERE NRDBM.NBS_UI_METADATA_UID=NUIM.NBS_UI_METADATA_UID
														AND NRDBM.RDB_TABLE_NM='D_VACCINATION'
														AND QUESTION_GROUP_SEQ_NBR IS NULL
														AND (DATA_TYPE='CODED' or UNIT_TYPE_CD='CODED')
														AND NUIM.DATA_LOCATION ='NBS_ANSWER.ANSWER_TXT'
													   --- AND NUIM.LAST_CHG_TIME>= @batch_start_time	AND NUIM.LAST_CHG_TIME <  @batch_end_time---- Incremental 


														BEGIN
														  UPDATE rdb.dbo.RDB_UI_METADATA_CODED
																SET
																  DATA_TYPE = 'CODED',
																  CODE_SET_GROUP_ID = unit_value
																where CODE_SET_GROUP_ID is null;
                                                         END

															IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_CODED_FINAL', 'U') IS NOT NULL  
															 drop table rdb.dbo.RDB_UI_METADATA_CODED_FINAL;

															  SELECT * 
															  into rdb.dbo.RDB_UI_METADATA_CODED_FINAL
															  FROM
															  (
															SELECT *,
															ROW_NUMBER () OVER (PARTITION BY NBS_QUESTION_UID order by NBS_QUESTION_UID) rowid
															 FROM  rdb.dbo.RDB_UI_METADATA_CODED
															 ) AS Der WHERE rowid=1
															 ;

															SELECT @RowCount_no = @@ROWCOUNT
																INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name],[Status_Type]  ,[step_number] ,[step_name]  ,[row_count] )
															    VALUES(@Batch_id  ,'VaccinationRecord'  ,'RDB.VaccinationRecord' ,'START' ,@Proc_Step_no  ,@Proc_Step_Name ,@ROWCOUNT_NO  );
  
			COMMIT TRANSACTION;
--------------------------------------------------7.CREATE TABLE rdb.dbo.CODED_TABLE_VR-------------to get Coded data----------

		    BEGIN TRANSACTION;
												SET @Proc_Step_no = 7;---(macro Table 5)------------------------------------CODED_TABLE--Table5
												SET @Proc_Step_Name = 'Generating rdb.dbo.CODED_TABLE_VR'; 

												IF OBJECT_ID('rdb.dbo.CODED_TABLE_VR', 'U') IS NOT NULL  
												 drop table rdb.dbo.CODED_TABLE_VR;
															
															 SELECT  DISTINCT
																 rmeta.NBS_QUESTION_UID,
																 rmeta.CODE_SET_GROUP_ID,
																 rmeta.RDB_COLUMN_NM,
																 PA.NBS_ANSWER_UID, ------&answer_UID, 
																 cast (PA.ANSWER_TXT as varchar(2000)) as ANSWER_TXT,
																 PA.ACT_UID as  'PAGE_UID',---------------------------&UID
																 PA.RECORD_STATUS_CD,
																 Cast (null as [varchar](256))  as ANSWER_OTH,
																 Cast (null as [varchar](300)) RDB_COLUMN_NM2
														   INTO rdb.dbo.CODED_TABLE_VR--------------------------------CODED_TABLE
														    FROM rdb.dbo.RDB_UI_METADATA_CODED_FINAL rmeta
														    LEFT OUTER JOIN  NBS_ODSE.[dbo].NBS_ANSWER PA   with (nolock)ON rmeta.nbs_question_uid=PA.nbs_question_uid 
															                                                           and pa.ANSWER_GROUP_SEQ_NBR IS NULL
															LEFT OUTER JOIN  rdb.dbo.TMP_S_VACCINATION_UIDS STG  with (nolock)ON STG.PAGE_UID=PA.act_uid
															INNER JOIN       NBS_SRTE.dbo.CODE_VALUE_GENERAL CVG with (nolock)ON CVG.CODE=rmeta.DATA_TYPE
														   WHERE CVG.CODE_SET_NM = 'NBS_DATA_TYPE' AND data_type = 'CODED'
													       ORDER BY ACT_UID,NBS_ANSWER_UID, rmeta.CODE_SET_GROUP_ID
														 
														  SELECT @RowCount_no = @@ROWCOUNT

														INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name],[Status_Type]  ,[step_number] ,[step_name]  ,[row_count] )
													   VALUES(@Batch_id  ,'VaccinationRecord'  ,'RDB.VaccinationRecord' ,'START' ,@Proc_Step_no  ,@Proc_Step_Name , @RowCount_no );
  
            COMMIT TRANSACTION;

                                                    
														 	/*
																DATA CODED_TABLE;
																SET CODED_TABLE;
															   X = INDEX(ANSWER_TXT, '^');
															   LENGTH=LENGTHN(ANSWER_TXT);
															   IF X> 0 THEN ANSWER_OTH=SUBSTR(ANSWER_TXT, x+1, LENGTH);
															   IF X> 0 THEN ANSWER_TXT=SUBSTR(ANSWER_TXT, 1, (X-1));
															   Y=LENGTHN(ANSWER_OTH);
															   IF(Y>0) THEN RDB_COLUMN_NM2= TRIM(RDB_COLUMN_NM) || '_OTH';
														   IF UPCASE (ANSWER_TXT)='OTH' THEN ANSWER_TXT='OTH';*/

													    UPDATE rdb.dbo.CODED_TABLE_VR
														 SET  ANSWER_OTH=SUBSTRING(ANSWER_TXT, CHARINDEX( '^',ANSWER_TXT)+1, LEN(RTRIM(LTRIM(ANSWER_TXT))))
															where  CHARINDEX( '^',ANSWER_TXT) > 0	;
												
														UPDATE  rdb.dbo.CODED_TABLE_VR
														 SET   ANSWER_TXT = SUBSTRING(ANSWER_TXT, 1, (CHARINDEX( '^',ANSWER_TXT)-1))
														   where  CHARINDEX( '^',ANSWER_TXT) > 0	;
												
														Update rdb.dbo.CODED_TABLE_VR
														SET ANSWER_TXT='OTH'
														where  upper (ANSWER_TXT)= 'OTH';

														UPDATE rdb.dbo.CODED_TABLE_VR
														SET RDB_COLUMN_NM2=RTRIM(LTRIM(RDB_COLUMN_NM)) + '_OTH'
															where LEN(ANSWER_OTH)>0
													 
											  
--------------------------------------------------------8.CREATE TABLE rdb.dbo.CODED_TABLE_SNM----------------------------to get Coded data not other(Structerd Numerical)-----------------------------------------------------------------------

		    BEGIN TRANSACTION;
												SET @Proc_Step_no = 8;---(macro Table 6)
												SET @Proc_Step_Name = 'Generating rdb.dbo.CODED_TABLE_SNM'; --------------------CODED_TABLE_SNM---Table 6

												IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNM', 'U') IS NOT NULL  
												 drop table rdb.dbo.CODED_TABLE_SNM;

														SELECT
														     C.nbs_question_uid,
															 C.code_set_group_id,
															 C.rdb_column_nm,
															 C.nbs_answer_uid,------&answer_UID
															 C.ANSWER_TXT,
														     C.PAGE_UID,------&UID
															 C.ANSWER_OTH,
															 CVG.code_set_nm,
															 CVG.code_short_desc_txt as ANSWER_TXT2
												    	INTO rdb.dbo.CODED_TABLE_SNM---not other tecords
														FROM rdb.dbo.CODED_TABLE_VR C
														LEFT JOIN NBS_SRTE.dbo.CODESET_GROUP_METADATA METADATA ON  METADATA.CODE_SET_GROUP_ID=C.CODE_SET_GROUP_ID
														LEFT JOIN NBS_SRTE.dbo.CODE_VALUE_GENERAL CVG ON  CVG.CODE_SET_NM=METADATA.CODE_SET_NM
														                                              AND CVG.CODE=C.ANSWER_OTH
														WHERE answer_OTH is not null and  C.answer_txt<>'OTH'
														ORDER BY C.nbs_answer_uid, C.RDB_COLUMN_NM;

														/*DATA CODED_TABLE_SNM; 
															SET CODED_TABLE_SNM; 
															DROP answer_oth; 
															ANSWER_TXT=  compress(ANSWER_TXT) ||' '||compress(ANSWER_TXT2);
															RUN;*/
														
														 SELECT @RowCount_no = @@ROWCOUNT
														INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name],[Status_Type]  ,[step_number] ,[step_name]  ,[row_count] )
													   VALUES(@Batch_id  ,'VaccinationRecord'  ,'RDB.VaccinationRecord' ,'START' ,@Proc_Step_no  ,@Proc_Step_Name ,@RowCount_no );
			COMMIT TRANSACTION

														UPDATE rdb.dbo.CODED_TABLE_SNM
														 SET ANSWER_TXT = ANSWER_TXT +' '+ ANSWER_TXT2
														 
														 ALTER TABLE  rdb.dbo.CODED_TABLE_SNM
														 Drop COLUMN Answer_OTH
------------------------------------------------------9. CREATE TABLE rdb.dbo.CODED_TABLE_NONSNM-----------------------------to get Coded data (Non StructuredNumerical) ---------------------------------------------------------
        BEGIN TRANSACTION;

													SET @Proc_Step_no = 9;---(macro Table 7)
													SET @Proc_Step_Name = 'Generating rdb.dbo.CODED_TABLE_NONSNM'; --------------------CODED_TABLE_NONSNM   Table 7

													IF OBJECT_ID('rdb.dbo.CODED_TABLE_NONSNM', 'U') IS NOT NULL  
													 drop table rdb.dbo.CODED_TABLE_NONSNM;

													 IF OBJECT_ID('rdb.dbo.Combined_TABLE_NONSNM_SNM', 'U') IS NOT NULL  ---added 9/9/2021
													 drop table rdb.dbo.Combined_TABLE_NONSNM_SNM;

														SELECT 
															   C.NBS_QUESTION_UID, 
															   C.CODE_SET_GROUP_ID, 
															   C.RDB_COLUMN_NM,
															   C.nbs_answer_uid,---- &answer_UID,
															   C.ANSWER_TXT, 
															   C.PAGE_UID,------&UID
															   C.ANSWER_OTH, 
															   CVG.CODE_SET_NM, 
															   CVG.CODE_SHORT_DESC_TXT AS ANSWER_TXT1,
															   C.RDB_COLUMN_NM2, 
															   CVG.CODE
															 
															INTO rdb.dbo.CODED_TABLE_NONSNM
															FROM rdb.dbo.CODED_TABLE_VR C    --------------CODED_TABLE
															LEFT JOIN NBS_SRTE.dbo.CODESET_GROUP_METADATA METADATA ON  METADATA.CODE_SET_GROUP_ID=C.CODE_SET_GROUP_ID 
															LEFT JOIN NBS_SRTE.dbo.CODE_VALUE_GENERAL CVG ON  CVG.CODE_SET_NM=METADATA.CODE_SET_NM 
															AND CVG.CODE=C.ANSWER_TXT
															WHERE  C.nbs_answer_uid not in (select nbs_answer_uid from rdb.dbo.CODED_TABLE_SNM) 
															ORDER BY C.nbs_answer_uid, C.RDB_COLUMN_NM; 


															Select ---STEPS 8 AND 9 COMBINED  to get all records--Merge
															COALESCE(non.NBS_QUESTION_UID,snm.NBS_QUESTION_UID)   as NBS_QUESTION_UID,	
															COALESCE(non.CODE_SET_GROUP_ID,snm.CODE_SET_GROUP_ID) as CODE_SET_GROUP_ID ,	
															COALESCE(non.RDB_COLUMN_NM	,snm.RDB_COLUMN_NM)       as RDB_COLUMN_NM,
															COALESCE(non.nbs_answer_uid	,snm.nbs_answer_uid)      as nbs_answer_uid,
															COALESCE(non.ANSWER_TXT	,snm.ANSWER_TXT)              as ANSWER_TXT,
															COALESCE(non.PAGE_UID	,snm.PAGE_UID)                as PAGE_UID,
															COALESCE(non.CODE_SET_NM,snm.CODE_SET_NM)             as CODE_SET_NM,
															non.RDB_COLUMN_NM2	,
															non.ANSWER_OTH,
															non.ANSWER_TXT1,
															non.code,
															snm.ANSWER_TXT2	
															INTO rdb.dbo.Combined_TABLE_NONSNM_SNM
															from rdb.dbo.CODED_TABLE_NONSNM non
															Full outer JOIN rdb.dbo.CODED_TABLE_SNM snm ON NON.nbs_answer_uid= SNM.nbs_answer_uid  and SNM.rdb_column_nm= NON.rdb_column_nm


												             SELECT @RowCount_no = @@ROWCOUNT

														    INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
															 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
															 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

            COMMIT TRANSACTION
-----------------------------------------------------------10. CREATE TABLE rdb.dbo.RDB_UI_METADATA_NUMERIC---------------------to get Coded data +Numeric(unitType cd) data-----------------------------------------------

			BEGIN TRANSACTION;

												SET @Proc_Step_no = 10;---(macro Table 8)
												SET @Proc_Step_Name = 'Generating  rdb.dbo.RDB_UI_METADATA_NUMERIC'; ----------third rdb_ui_metadata  Table 8

												IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_NUMERIC', 'U') IS NOT NULL  
												 drop table  rdb.dbo.RDB_UI_METADATA_NUMERIC;

													SELECT DISTINCT 
														NUIM.NBS_QUESTION_UID,
														NUIM.CODE_SET_GROUP_ID, 
														NRDBM.RDB_COLUMN_NM, 
														NUIM.INVESTIGATION_FORM_CD,
														NUIM.QUESTION_GROUP_SEQ_NBR,
														NUIM.DATA_TYPE ,
														NUIM.unit_value
													
												     INTO rdb.dbo.RDB_UI_METADATA_NUMERIC
													FROM
														NBS_ODSE.dbo.NBS_RDB_METADATA NRDBM with (nolock) ,
														NBS_ODSE.dbo.NBS_UI_METADATA NUIM with (nolock)
														where NRDBM.NBS_UI_METADATA_UID=NUIM.NBS_UI_METADATA_UID
														AND NRDBM.RDB_TABLE_NM='D_VACCINATION'
														AND QUESTION_GROUP_SEQ_NBR IS NULL
														AND NUIM.DATA_LOCATION ='NBS_ANSWER.ANSWER_TXT'
														AND  ((NUIM.DATA_TYPE='NUMERIC' and  NUIM.mask='NUM')------and  upcase(mask)='NUM_TEMP')
														OR    (NUIM.DATA_TYPE='NUMERIC' and  NUIM.mask='NUM_SN' and NUIM.unit_type_cd='CODED'))
														AND RDB_COLUMN_NM not like '%_CD'
													   --- AND NUIM.LAST_CHG_TIME>= @batch_start_time	AND NUIM.LAST_CHG_TIME <  @batch_end_time---- Incremental 

														UPDATE rdb.dbo.RDB_UI_METADATA_NUMERIC
														SET CODE_SET_GROUP_ID = CASE WHEN CODE_SET_GROUP_ID is NULL then unit_value Else CODE_SET_GROUP_ID END
													
														SELECT @RowCount_no = @@ROWCOUNT

														INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

            COMMIT TRANSACTION
			
-------------------------------------------------------------------11. CREATE TABLE rdb.dbo.CODED_TABLE_SNTEMP--------------to get Coded data-----------------------------------------------


		  BEGIN TRANSACTION;

												SET @Proc_Step_no = 11;---(macro Table 9)
												SET @Proc_Step_Name = 'Generating  rdb.dbo.CODED_TABLE_SNTEMP'; --------------------CODED_TABLE_SNTEMP  Table 9

												IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP', 'U') IS NOT NULL  
												 drop table  rdb.dbo.CODED_TABLE_SNTEMP;
CREATE NONCLUSTERED INDEX [IDX_VACC_07]
ON RDB.[dbo].[TMP_S_VACCINATION_UIDS] ([PAGE_UID])
												SELECT 
												meta.nbs_question_uid,
												meta.CODE_SET_GROUP_ID, 
												meta.RDB_COLUMN_NM,
												PA.nbs_answer_uid, -------------------------------&answer_UID, 
											    cast (REPLACE(pa.answer_txt, CHAR(13)+CHAR(10), ' ') as varchar(2000)) as ANSWER_TXT,
												coalesce(PA.ACT_UID,1)  AS PAGE_UID,-------------------  &UID,
												PA.RECORD_STATUS_CD, 
												cast (Null as varchar(2000)) as ANSWER_TXT_CODE,
												cast (Null as varchar(2000)) as ANSWER_VALUE
										
										     INTO  rdb.dbo.CODED_TABLE_SNTEMP
												FROM rdb.dbo.RDB_UI_METADATA_NUMERIC meta
												LEFT OUTER JOIN NBS_ODSE.dbo.NBS_ANSWER PA  with ( nolock ) on meta.nbs_question_uid=PA.nbs_question_uid AND pa.ANSWER_GROUP_SEQ_NBR IS NULL
											    LEFT OUTER JOIN rdb.dbo.TMP_S_VACCINATION_UIDS S on S.PAGE_UID=PA.act_uid
												INNER JOIN NBS_SRTE.dbo.CODE_VALUE_GENERAL CVG  ON  CVG.CODE=meta.DATA_TYPE
												WHERE CVG.CODE_SET_NM = 'NBS_DATA_TYPE'
											    AND data_type = 'NUMERIC'
											---	AND UPPER(data_type) = 'CODED'  ----I Commented Out on 9/8/2021 and added the above one
												ORDER BY 
												PAGE_UID,NBS_ANSWER_UID, meta.CODE_SET_GROUP_ID;
      
												UPDATE  rdb.dbo.CODED_TABLE_SNTEMP
													SET ANSWER_TXT_CODE=SUBSTRING(ANSWER_TXT, CHARINDEX( '^',ANSWER_TXT)+1, LEN(RTRIM(LTRIM(ANSWER_TXT)))),
													    ANSWER_VALUE = SUBSTRING(ANSWER_TXT, 1, (CHARINDEX( '^',ANSWER_TXT)-1))
													  where CHARINDEX( '^',ANSWER_TXT) > 0	;
												
												             SELECT @RowCount_no = @@ROWCOUNT

														INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

            COMMIT TRANSACTION	
-----------------------------------------------------------12. CREATE TABLE rdb.dbo.CODED_TABLE_VR1---Merge------------------------------------------------------------------

   
			BEGIN TRANSACTION

												SET @Proc_Step_no = 12;---(macro Table 10)
												SET @Proc_Step_Name = 'Generating rdb.dbo.CODED_TABLE_SNTEMP_TRANS'; ------------CODED_TABLE_SNTEMP_TRANS,CODED_TABLE_VR1   Table 10


												IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS', 'U') IS NOT NULL  
												 drop table  rdb.dbo.CODED_TABLE_SNTEMP_TRANS;

												 IF OBJECT_ID('rdb.dbo.CODED_TABLE_VR1', 'U') IS NOT NULL  
												 drop table  rdb.dbo.CODED_TABLE_VR1;
			
												   SELECT 
														SNT.CODE_SET_GROUP_ID,
														SNT.RDB_COLUMN_NM,  
														SNT.NBS_ANSWER_UID, ----&answer_UID, 
														CAST ( null as varchar(2000)) as ANSWER_TXT, -----SNT.ANSWER_TXT,
														SNT.PAGE_UID,-----------------&UID, 
														SNT.ANSWER_TXT_CODE , 
														SNT.ANSWER_VALUE,
													    CVG.CODE,
														CVG.CODE_SET_NM, 
														CVG.CODE_SHORT_DESC_TXT AS ANSWER_TXT2
												
												INTO rdb.dbo.CODED_TABLE_SNTEMP_TRANS
												  FROM  rdb.dbo.CODED_TABLE_SNTEMP SNT   
														LEFT JOIN NBS_SRTE.dbo.CODESET_GROUP_METADATA METADATA ON  METADATA.CODE_SET_GROUP_ID=SNT.CODE_SET_GROUP_ID
														LEFT JOIN NBS_SRTE.dbo.CODE_VALUE_GENERAL CVG ON  CVG.CODE_SET_NM=METADATA.CODE_SET_NM
														AND CVG.CODE=SNT.ANSWER_TXT_CODE
														ORDER BY NBS_ANSWER_UID, RDB_COLUMN_NM;

														/*DATA CODED_TABLE_SNTEMP_TRANS;
															SET  CODED_TABLE_SNTEMP_TRANS;
															ANSWER_TXT=  compress(ANSWER_VALUE) ||' '||compress(ANSWER_TXT2);
															DROP CODE_SET_GROUP_ID;
														 */
													 


													  UPDATE rdb.dbo.CODED_TABLE_SNTEMP_TRANS
													  SET ANSWER_TXT = replace(ANSWER_VALUE,' ','') +' '+replace(ANSWER_TXT2,' ','')
														
	                                                   ALTER TABLE  rdb.dbo.CODED_TABLE_SNTEMP_TRANS
														DROP Column CODE_SET_GROUP_ID;
														
														/*
														  DATA CODED_TABLE ; ------------------------------------------------I names it as rdb.dbo.CODED_TABLE_VR1
														  MERGE 
														  CODED_TABLE_NONSNM 
														  CODED_TABLE_SNM 
														  CODED_TABLE_SNTEMP_TRANS; 
														  BY &answer_UID RDB_COLUMN_NM; 
														  */

														---MERGE

														SELECT 
															com.nbs_question_uid,
															com.code_set_group_id,
															COALESCE(com.rdb_column_nm  ,trans.rdb_column_nm)    as rdb_column_nm,
															COALESCE(com.nbs_answer_uid	,trans.nbs_answer_uid)   as nbs_answer_uid,
															COALESCE(com.ANSWER_TXT     ,trans.ANSWER_TXT)       as ANSWER_TXT,
															COALESCE(com.PAGE_UID       ,trans.PAGE_UID)         as PAGE_UID,
														    COALESCE(com.code_set_nm    ,trans.code_set_nm)      as code_set_nm,
															CAST(NULL as Varchar(2000))                          as RDB_COLUMN_NM2,   
															com.ANSWER_OTH,	 
															com.ANSWER_TXT1 ,
															COALESCE(com.code    ,trans.code)                    as code, 
															TRANS.ANSWER_TXT_Code,
                                                   			TRANS.ANSWER_VALUE
														INTO rdb.dbo.CODED_TABLE_VR1 -------------is the CODED_TABLE
													   FROM  rdb.dbo.Combined_TABLE_NONSNM_SNM com
												       FULL OUTER JOIN  rdb.dbo.CODED_TABLE_SNTEMP_TRANS TRANS ON com.nbs_answer_uid= TRANS.nbs_answer_uid and com.rdb_column_nm = TRANS.rdb_column_nm---step 12
													     Order by nbs_answer_uid,rdb_column_nm

														UPDATE rdb.dbo.CODED_TABLE_VR1
														set  ANSWER_TXT1=ANSWER_TXT
                                                         where  LTRIM(RTRIM(ANSWER_TXT1))='' or ANSWER_TXT1 is null
															
                                                        Begin
														Delete from rdb.dbo.CODED_TABLE_VR1 where Page_UID is null----I added to eliminate Null row---9/8/2021
														End

														SELECT @RowCount_no = @@ROWCOUNT

														INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

            COMMIT TRANSACTION	

 ---------------------------------------------------------------13. CREATE TABLE  rdb.dbo.CODED_TABLE_DESC--------------------------------------------------------------------

			
			BEGIN TRANSACTION												
								
											SET @Proc_Step_no = 13;---(macro Table 10)
											SET @Proc_Step_Name = 'Generating rdb.dbo.CODED_TABLE_DESC'; --------------------CODED_TABLE_DESC  Table 10

								
											IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_TEMP', 'U') IS NOT NULL  
												drop table rdb.dbo.CODED_TABLE_DESC_TEMP ;

											 IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC', 'U') IS NOT NULL  
												drop table rdb.dbo.CODED_TABLE_DESC ;
										
												  SELECT p1.PAGE_UID, p1.NBS_QUESTION_UID,
													   stuff( (SELECT top 10 ' | '+ANSWER_TXT1 
															   FROM [RDB].[dbo].CODED_TABLE_VR1 p2
															   WHERE p2.PAGE_UID    = p1.PAGE_UID
																 and p2.nbs_question_uid = p1.NBS_QUESTION_UID
															   ORDER BY PAGE_UID, NBS_QUESTION_UID
															   FOR XML PATH(''), TYPE).value('.', 'varchar(2000)')
															,1,3,'')
													   AS ANSWER_DESC11
													into  [RDB].[dbo].[CODED_TABLE_DESC_TEMP]
													  FROM [RDB].[dbo].CODED_TABLE_VR1 p1    ---- is the CODED_TABLE
													  where  nbs_question_uid is not null
													  GROUP BY PAGE_UID,NBS_QUESTION_UID   ;

														SELECT ct.*, coalesce(ctt.answer_desc11,ct.answer_txt1) as answer_desc11
														into rdb.dbo.CODED_TABLE_DESC
														FROM [RDB].[dbo].CODED_TABLE_VR1 ct
														LEFT OUTER JOIN  [CODED_TABLE_DESC_TEMP] ctt on ct.PAGE_UID = ctt.PAGE_UID
														AND ct.NBS_QUESTION_UID = ctt.NBS_QUESTION_UID ;
												
																SELECT @RowCount_no = @@ROWCOUNT

																INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														      (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														      VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

            COMMIT TRANSACTION	
-------------------------------------------------------------14. CREATE TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC----------------------------------------------------------------------------------------------------

			BEGIN TRANSACTION												
								
											SET @Proc_Step_no = 14;---(macro Table 11)
											SET @Proc_Step_Name = 'Generating rdb.dbo.CODED_COUNTY_TABLE '; --------------------CODED_COUNTY_TABLE  Table 11

								
											 IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE', 'U') IS NOT NULL  
											 drop table rdb.dbo.CODED_COUNTY_TABLE  ;
											 
											 IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_TEMP', 'U') IS NOT NULL  
													drop table rdb.dbo.CODED_COUNTY_TABLE_DESC_TEMP ;

											 IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC', 'U') IS NOT NULL  
													drop table rdb.dbo.CODED_COUNTY_TABLE_DESC ;

											 SELECT 
												  C.NBS_QUESTION_UID, 
												  C.CODE_SET_GROUP_ID, 
												  C.RDB_COLUMN_NM,
												  C.NBS_ANSWER_UID, ----&answer_UID,
												  C.ANSWER_TXT,
												  C.PAGE_UID,-----------------&UID, ,
												  C.ANSWER_OTH, 
												  CVG.CODE_SET_NM, 
												  CVG.CODE,
												  CVG.CODE_SHORT_DESC_TXT AS ANSWER_TXT1
											    INTO rdb.dbo.CODED_COUNTY_TABLE
												 FROM  rdb.dbo.CODED_TABLE_VR1 C  -----is the coded_table
												 LEFT JOIN NBS_SRTE.dbo.CODESET_GROUP_METADATA METADATA ON   METADATA.CODE_SET_GROUP_ID=C.CODE_SET_GROUP_ID
												 LEFT JOIN NBS_SRTE.dbo.V_STATE_COUNTY_CODE_VALUE CVG ON    CVG.CODE_SET_NM=METADATA.CODE_SET_NM AND CVG.CODE=C.ANSWER_TXT
												 WHERE METADATA.CODE_SET_NM= 'COUNTY_CCD'
												 Order by NBS_ANSWER_UID,RDB_COLUMN_NM

											    SELECT p1.PAGE_UID, p1.NBS_QUESTION_UID,
												   stuff( (SELECT top 10 ' |'+ANSWER_TXT1 
														   FROM rdb.dbo.CODED_COUNTY_TABLE p2
														   WHERE p2.PAGE_UID    = p1.PAGE_UID
															 and p2.nbs_question_uid = p1.NBS_QUESTION_UID
														   ORDER BY PAGE_UID, NBS_QUESTION_UID,NBS_ANSWER_UID
														   FOR XML PATH(''), TYPE).value('.', 'varchar(2000)')
														,1,2,'') AS ANSWER_DESC11

											  INTO rdb.dbo.CODED_COUNTY_TABLE_DESC_TEMP
												  FROM rdb.dbo.CODED_COUNTY_TABLE p1
												  GROUP BY PAGE_UID, NBS_QUESTION_UID 
										
													SELECT cct.*,cctt.answer_desc11
													INTO rdb.dbo.CODED_COUNTY_TABLE_DESC-----------Step_no = 14;---(macro Table 11)
													FROM rdb.dbo.CODED_COUNTY_TABLE cct
													LEFT OUTER JOIN  rdb.dbo.CODED_COUNTY_TABLE_DESC_TEMP cctt on
													cct.PAGE_UID = cctt.PAGE_UID  and cct.NBS_QUESTION_UID = cctt.NBS_QUESTION_UID 
													Order by NBS_ANSWER_UID,RDB_COLUMN_NM

													SELECT @RowCount_no = @@ROWCOUNT

													     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

            COMMIT TRANSACTION
	
---------------------------------------------------------15. CREATE TABLE rdb.dbo.CODED_TABLE_MERGED---Merge table ---------------------------------------------------------------

	  BEGIN TRANSACTION												
								
											SET @Proc_Step_no = 15;---(macro Table 12)
											SET @Proc_Step_Name = 'Generating CODED_TABLE_OTH'; --------------CODED_TABLE_OTH  -----Table 12


											 IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTH', 'U') IS NOT NULL  
											 drop table rdb.dbo.CODED_TABLE_OTH ;

											 IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED', 'U') IS NOT NULL  
											 drop table rdb.dbo.CODED_TABLE_MERGED

		                                      Select DISTINCT
													nbs_question_uid,
													code_set_group_id,	
													rdb_column_nm	,
													nbs_answer_uid	,
													ANSWER_TXT,
													Page_UID,
													code_set_nm	,
													RDB_COLUMN_NM2 ,
													ANSWER_OTH	,
													ANSWER_TXT1	,
													code,	
													ANSWER_TXT_Code	,
													ANSWER_VALUE	,
													ANSWER_DESC11
													---cast (NULL as VARCHAR(200)) as  ANSWER_DESC11
										      INTO rdb.dbo.CODED_TABLE_OTH-------------------------Step_no = 15;---(macro Table 12)
											  FROM rdb.dbo.CODED_TABLE_DESC -------------rdb.dbo.CODED_TABLE_VR1------CODED_TABLE
											  Order by NBS_ANSWER_UID,RDB_COLUMN_NM
											   
												/*DATA CODED_TABLE_OTH;
												SET CODED_TABLE;
												IF LENGTHN(TRIM(RDB_COLUMN_NM2))>0;
												IF(LENGTHN(RDB_COLUMN_NM2)>0) 
												   THEN RDB_COLUMN_NM=RDB_COLUMN_NM2;
												IF(LENGTHN(TRIM(RDB_COLUMN_NM2))>0) 
												   THEN ANSWER_DESC11=ANSWER_OTH;*/

														update CODED_TABLE_OTH
														set RDB_COLUMN_NM=RDB_COLUMN_NM2
														where LEN(rtrim(RDB_COLUMN_NM2)) > 0;
											
														update CODED_TABLE_OTH
														set ANSWER_DESC11=ANSWER_OTH
														where LEN(rtrim(RDB_COLUMN_NM2)) > 0;

												Select 
												  COALESCE(CTD.nbs_question_uid, CD.nbs_question_uid)  as nbs_question_uid	,
                                                  COALESCE(CTD.code_set_group_id,CD.code_set_group_id) as code_set_group_id,
										          COALESCE(CTD.rdb_column_nm	,CD.rdb_column_nm)     as rdb_column_nm,
												  COALESCE(CTD.nbs_answer_uid	,CD.nbs_ANSWER_UID)    as nbs_ANSWER_UID,
												  COALESCE(CTD.ANSWER_TXT	    ,CD.ANSWER_TXT)        as ANSWER_TXT,
											      COALESCE(CTD.Page_UID         ,CD.Page_uid )         as Page_uid,------------&UID 
												  COALESCE(CTD.code_set_nm      ,CD.code_set_nm)       as code_set_nm,
												  CTD.RDB_COLUMN_NM2,	 
												  COALESCE(CTD.ANSWER_OTH	    ,CD.ANSWER_OTH)        as ANSWER_OTH,
												  COALESCE(CTD.ANSWER_TXT1      ,CD.ANSWER_TXT1)       as ANSWER_TXT1,
												  COALESCE(CTD.code             ,CD.code)              as code,
												  CTD.ANSWER_TXT_Code,
												  CTD.ANSWER_VALUE ,
												  COALESCE(CTD.answer_desc11    ,CD.answer_desc11)     as answer_desc11
										       INTO rdb.dbo.CODED_TABLE_MERGED
											   FROM CODED_TABLE_OTH CTD -------CODED_TABLE_DESC CTD---Alll from CODED_TABLE_DESC is in CODED_TABLE_OTH 
											   Full OUTER JOIN CODED_COUNTY_TABLE_DESC CD  ON CTD.PAGE_UID = CD.PAGE_UID and CTD.rdb_column_nm = CD.rdb_column_nm  --and Cd.PAGE_UID is not null
											  ---   FULL OUTER JOIN CODED_TABLE_OTH         CO  ON CTD.PAGE_UID = CO.PAGE_UID and CTD.rdb_column_nm = CO.rdb_column_nm ---and CO.PAGE_UID is not null
											   --- Where CTD.Page_UID is not null----Added on 8/12/2021
												Order by CTD.NBS_ANSWER_UID,RDB_COLUMN_NM
		
												/*UPDATE rdb.dbo.CODED_TABLE_MERGED
												SET Page_UID = Case When Page_UID is null then 1 else Page_UID end*/
		
												SELECT @RowCount_no = @@ROWCOUNT
													
												;WITH CTE  as (
												select VR.rdb_column_nm, CASE WHEN VR.answer_txt is not null  THEN 1 ELSE 0 END as Ans_null
												FROM rdb.dbo.CODED_TABLE_MERGED VR
												 where RDB_COLUMN_NM  in ( select RDB_COLUMN_NM from CODED_TABLE_OTH group by rdb_column_nm)
												 group by rdb_column_nm, CASE WHEN answer_txt is not null  THEN 1 ELSE 0 END
												 )
												 DELETE FROM rdb.dbo.CODED_TABLE_MERGED
												 where rdb_column_nm in (select rdb_column_nm
												 from CTE
												 where ans_null = 0 
												 except
												 select rdb_column_nm
												 from CTE 
												 where ans_null = 1
												 );
												 
												    INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
													(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
													VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 
																
													IF @RowCount_no=0 
													Begin
												    print 'CODED_DATA_OUT'
													INSERT INTO  rdb.dbo.CODED_TABLE_MERGED  Values(0,0,'Coded',0,'NULL',0,'NULL','NULL','NULL','NULL','NULL','NULL',0,'Coded')
													END	
														
           COMMIT TRANSACTION

-----------------------------------------------------------16. CREATE TABLE rdb.dbo.CODED_DATA_OUT----------------------------------------

   
		     BEGIN TRANSACTION;
										SET @Proc_Step_no = 16;---(macro Table 13)
										SET @Proc_Step_Name = 'Generating CODED_DATA_OUT'; -------------------CODED_DATA_OUT---------third table in the main to Left join

												 IF OBJECT_ID('rdb.dbo.CODED_DATA_OUT', 'U') IS NOT NULL  
												 drop table rdb.dbo.CODED_DATA_OUT
													
													DECLARE @columns2 NVARCHAR(MAX);
													SET @columns2 = N'';

												  SELECT @columns2+=N', p.'+QUOTENAME(LTRIM(RTRIM(RDB_COLUMN_NM)))
														FROM
														(SELECT RDB_COLUMN_NM 
															FROM rdb.dbo.CODED_TABLE_MERGED  AS p
															 WHERE RDB_COLUMN_NM is not null
															GROUP BY RDB_COLUMN_NM
														) AS x;
													
													---print @columns2
													
													DECLARE @sql2 NVARCHAR(MAX);
			                           				SET @sql2 = N'
														 SELECT [PAGE_UID] as PAGE_UID_coded, '+STUFF(@columns2, 1, 2, '')+
															' into  rdb.dbo.CODED_DATA_OUT ' +
			                                    			'FROM (
															      SELECT [PAGE_UID],[RDB_COLUMN_NM],ANSWER_DESC11
																  FROM rdb.dbo.CODED_TABLE_MERGED
																  group by [PAGE_UID],[RDB_COLUMN_NM],ANSWER_DESC11 
															     ) AS j PIVOT (max(ANSWER_DESC11) FOR [RDB_COLUMN_NM] in 
																  ('+STUFF(REPLACE(@columns2, ', p.[', ',['), 1, 1, '')+')) AS p;';

													--  print @sql2;
													  EXEC sp_executesql @sql2;
												
										            SELECT @RowCount_no = @@ROWCOUNT
													INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;
---Select * from rdb.dbo.CODED_DATA_OUT
-----------------------------------------------------17. CREATE TABLE [RDB].[dbo].RDB_UI_METADATA_DATE----------------to get Date data---------------------------------

			BEGIN TRANSACTION;
										SET @Proc_Step_no = 17;---(macro Table 14)
										SET @Proc_Step_Name = 'Generating rdb_ui_metadata_DATE'; ----------------------------Fourth  rdb_ui_metadata table 
                                                 
												 IF OBJECT_ID('rdb.dbo.rdb_ui_metadata_DATE', 'U') IS NOT NULL  
												 drop table rdb.dbo.rdb_ui_metadata_DATE
												  
												  IF OBJECT_ID('RDB.dbo.RDB_UI_METADATA_DateFinal', 'U') IS NOT NULL  
												  drop table rdb.dbo.RDB_UI_METADATA_DateFinal;

														SELECT  
															NUIM.NBS_QUESTION_UID,
															NUIM.CODE_SET_GROUP_ID,
															NRDBM.RDB_COLUMN_NM,
															NUIM.INVESTIGATION_FORM_CD,
															QUESTION_GROUP_SEQ_NBR,
															DATA_TYPE
														INTO rdb.dbo.rdb_ui_metadata_DATE
														from
															NBS_ODSE.dbo.NBS_RDB_METADATA NRDBM with (nolock),
															NBS_ODSE.dbo.NBS_UI_METADATA NUIM with (nolock)
															where NRDBM.NBS_UI_METADATA_UID=NUIM.NBS_UI_METADATA_UID
														AND NRDBM.RDB_TABLE_NM='D_VACCINATION'
														AND QUESTION_GROUP_SEQ_NBR IS NULL
													    AND NUIM.DATA_LOCATION ='NBS_ANSWER.ANSWER_TXT'
														AND DATA_TYPE in ('Date/Time','Date', 'DATETIME','DATE')
													  ---  AND NUIM.LAST_CHG_TIME>= @batch_start_time	AND NUIM.LAST_CHG_TIME <  @batch_end_time---- Incremental 

															  SELECT * 
															  into rdb.dbo.RDB_UI_METADATA_DateFinal
															  FROM
															  ( SELECT *, 
																ROW_NUMBER () OVER (PARTITION BY NBS_QUESTION_UID order by NBS_QUESTION_UID) rowid
																 FROM rdb.dbo.rdb_ui_metadata_DATE
															  ) AS Der WHERE rowid=1;

													     SELECT @RowCount_no = @@ROWCOUNT

													   INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;

----------------------------------------------------------18. CREATE TABLE rdb.dbo.DATE_DATA_VR-----------------------------to get Date data-----------------------------------------
            BEGIN TRANSACTION;

										SET @Proc_Step_no = 18;---(macro Table 15)
										SET @Proc_Step_Name = ' CREATE TABLE DATE_DATA_VR'; 

										IF OBJECT_ID('rdb.dbo.DATE_DATA_VR', 'U') IS NOT NULL  
										 drop table rdb.dbo.DATE_DATA_VR;



										 SELECT DISTINCT
										        rmeta.NBS_QUESTION_UID,
												rmeta.CODE_SET_GROUP_ID, 
												rmeta.RDB_COLUMN_NM, 
												PA.NBS_ANSWER_UID, 
												PA.ANSWER_TXT, 
												PA.ACT_UID as PAGE_UID, ---@UID
												PA.RECORD_STATUS_CD, 
												CAST(NULL as VARCHAR(2000)) as ANSWER_TXT1
												into rdb.dbo.DATE_DATA_VR
										FROM  [RDB].[dbo].RDB_UI_METADATA_DateFinal rmeta with ( nolock ) 
										LEFT OUTER JOIN NBS_ODSE.[dbo].NBS_ANSWER PA  with ( nolock ) on rmeta.nbs_question_uid=PA.nbs_question_uid AND pa.ANSWER_GROUP_SEQ_NBR IS NULL
										LEFT OUTER JOIN rdb.dbo.TMP_S_VACCINATION_UIDS StG on STG.PAGE_UID=PA.act_uid
										INNER JOIN NBS_SRTE.dbo.CODE_VALUE_GENERAL CVG ON   CVG.CODE=rmeta.DATA_TYPE
										WHERE CVG.CODE_SET_NM = 'NBS_DATA_TYPE' AND CODE IN('Date/Time','Date', 'DATETIME','DATE') 
										ORDER BY 	ACT_UID,NBS_ANSWER_UID, rmeta.CODE_SET_GROUP_ID;

															/*
														   DATA DATE_DATA;
														   SET DATE_DATA;
														   ANSWER_TXT1=input(ANSWER_TXT,anydtdtm20.);
														   informat ANSWER_TXT1 LAST_CHG_TIME  dateTIME22.3 ;
														   format ANSWER_TXT1 LAST_CHG_TIME dateTIME22.3;*/
										
												UPDATE rdb.dbo.DATE_DATA_VR
													 SET ANSWER_TXT1 = FORMAT(CAST([ANSWER_TXT] AS DATE),'MM/dd/yy') + ' 00:00:00'

												/*DATA DATE_DATA; set DATE_DATA;IF  &UID= . THEN  &UID=1;RUN;
												UPDATE rdb.dbo.DATE_DATA_VR
													 SET PAGE_UID = CASE WHEN PAGE_UID is NULL then 1 else PAGE_UID end;*/
												
												
													 ---To delete the rdb_column_nm when Answer_TXt is null
								               SELECT @RowCount_no = @@ROWCOUNT;
												
												;WITH CTE  as (
												select VR.rdb_column_nm, CASE WHEN VR.answer_txt is not null  THEN 1 ELSE 0 END as Ans_null
												FROM rdb.dbo.DATE_DATA_VR VR
												 where RDB_COLUMN_NM  in ( select RDB_COLUMN_NM from[RDB].[dbo].RDB_UI_METADATA_DateFinal group by rdb_column_nm)
												 group by rdb_column_nm, CASE WHEN answer_txt is not null  THEN 1 ELSE 0 END
												 )
												 DELETE FROM rdb.dbo.DATE_DATA_VR
												 where rdb_column_nm in (select rdb_column_nm
												 from CTE
												 where ans_null = 0 
												 except
												 select rdb_column_nm
												 from CTE 
												 where ans_null = 1
												 );

											
												INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 
													 --- Print @RowCount_no
													 ---Introduce dummy row
													 IF  @RowCount_no=0 
													 BEGIN
													 INSERT INTO rdb.dbo.DATE_DATA_VR Values(0,0,'Datee',0,'Datee',0,'NULL','Datee')
													 END
            COMMIT TRANSACTION;

 -----------------------------------------------19. CREATE TABLE rdb.dbo.DATE_DATA_VR_out-----------------------to get Date data-------------------------
     
			BEGIN TRANSACTION;

								SET @Proc_Step_no = 19;---(macro Table 16)
								SET @Proc_Step_Name = ' CREATE TABLE rdb.dbo.PAGE_DATE_TABLE_VR'; 

							     IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_VR', 'U') IS NOT NULL  
								    drop table rdb.dbo.PAGE_DATE_TABLE_VR;
                                
								 IF OBJECT_ID('rdb.dbo.DATE_DATA_VR_out', 'U') IS NOT NULL  
								    drop table   rdb.dbo.DATE_DATA_VR_out;


										 Select * into rdb.dbo.PAGE_DATE_TABLE_VR
										 From rdb.dbo.DATE_DATA_VR

											DECLARE @columns3 NVARCHAR(MAX);
											DECLARE @sql3 NVARCHAR(MAX);
												 SET @columns3 = N'';

											SELECT @columns3+=N', p.'+QUOTENAME(LTRIM(RTRIM([RDB_COLUMN_NM])))
											FROM
											(
												SELECT RDB_COLUMN_NM
												FROM rdb.dbo.PAGE_DATE_TABLE_VR AS p
												---where RDB_COLUMN_NM is not null
												GROUP BY [RDB_COLUMN_NM]
											) AS x;
										---print @columns3;
									   SET @sql3 = N'SELECT PAGE_UID as PAGE_UID_date, '+STUFF(@columns3, 1, 2, '')+
											        ' into rdb.dbo.DATE_DATA_VR_out ' +
													'FROM (SELECT PAGE_UID, answer_txt1 ,RDB_COLUMN_NM
																FROM  rdb.dbo.PAGE_DATE_TABLE_VR
																group by PAGE_UID,answer_txt1,RDB_COLUMN_NM
															) AS j PIVOT (max(answer_txt1) FOR RDB_COLUMN_NM in 
														   ('+STUFF(REPLACE(@columns3, ', p.[', ',['), 1, 1, '')+')) AS p;';

															---print @sql3;
															EXEC sp_executesql @sql3;
								
															  SELECT @RowCount_no = @@ROWCOUNT;

									                    INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

 
			COMMIT TRANSACTION;
--Select * from rdb.dbo.DATE_DATA_VR_out
---------------------------------------------------20. CREATE TABLE rdb.dbo.RDB_UI_METADATA_VR1------------------------to get Numeric data-------------------------------------------------------
          BEGIN TRANSACTION;
										SET @Proc_Step_no = 20;---(macro Table 17)
										SET @Proc_Step_Name = 'Generating rdb.dbo.RDB_UI_METADATA_Numeric_all'; ----------------------------Fifth  rdb_ui_metadata table 
										

											 IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_Numeric_all', 'U') IS NOT NULL  
													drop table rdb.dbo.RDB_UI_METADATA_Numeric_all ;

													SELECT 
													NUIM.NBS_QUESTION_UID,
												    NUIM.CODE_SET_GROUP_ID,  
													NRDBM.RDB_COLUMN_NM,
													NUIM.INVESTIGATION_FORM_CD,
													QUESTION_GROUP_SEQ_NBR,
													DATA_TYPE
													INTO rdb.dbo.RDB_UI_METADATA_Numeric_all
													FROM
													NBS_ODSE.[dbo].NBS_RDB_METADATA NRDBM with ( nolock ) ,
													NBS_ODSE.[dbo].NBS_UI_METADATA NUIM with ( nolock ) 
													WHERE NRDBM.NBS_UI_METADATA_UID=NUIM.NBS_UI_METADATA_UID
													AND NRDBM.RDB_TABLE_NM='D_VACCINATION'
													AND NUIM.DATA_LOCATION ='NBS_ANSWER.ANSWER_TXT'
													AND nuim.QUESTION_GROUP_SEQ_NBR IS NULL
													AND nuim.DATA_TYPE in ('Numeric', 'NUMERIC')
													---AND NUIM.LAST_CHG_TIME>= @batch_start_time	AND NUIM.LAST_CHG_TIME <  @batch_end_time---- Incremental 
													
													IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_NumericFinal', 'U') IS NOT NULL  
													 drop table rdb.dbo.RDB_UI_METADATA_NumericFinal;

													  SELECT * 
													  into [RDB].[dbo].RDB_UI_METADATA_NumericFinal
													  FROM
													  (
														SELECT *, ROW_NUMBER () OVER (PARTITION BY NBS_QUESTION_UID 
														order by NBS_QUESTION_UID) rowid
														FROM  rdb.dbo.RDB_UI_METADATA_Numeric_all
													  ) AS Der WHERE rowid=1;

													SELECT @RowCount_no = @@ROWCOUNT;

													    INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 
            COMMIT TRANSACTION;
-------------------------------------------------------21. CREATE TABLE rdb.dbo.NUMERIC_BASE_DATA_VR------------------to get Numeric data-------------------------------------------------------
            BEGIN TRANSACTION;

										SET @Proc_Step_no = 21;---(macro Table 18)
										SET @Proc_Step_Name = 'Generating NUMERIC_BASE_DATA_VR'; 

											 IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_VR', 'U') IS NOT NULL  
											 drop table rdb.dbo.NUMERIC_BASE_DATA_VR ;

											 SELECT DISTINCT 
											         rmeta.NBS_QUESTION_UID,
													 rmeta.CODE_SET_GROUP_ID,
													 rmeta.RDB_COLUMN_NM,
													 PA.nbs_answer_uid, 
													 PA.ANSWER_TXT, 
													 PA.ACT_UID  as PAGE_UID ,---------&UID
													 PA.RECORD_STATUS_CD, 
													 LEN(RTRIM(ANSWER_TXT)) TXT_LEN,
													 Cast (null as  [varchar](2000)) ANSWER_UNIT, 
													 Cast (null as  int) LENCODED,
													 Cast (null as  [varchar](2000)) ANSWER_CODED, 
													 Cast (null as  [varchar](2000)) UNIT_VALUE1 ,
													 Cast (null as  [varchar](300)) RDB_COLUMN_NM2 
											   INTO rdb.dbo.NUMERIC_BASE_DATA_VR
											   FROM  [RDB].[dbo].RDB_UI_METADATA_NumericFinal rmeta with ( nolock ) 
											     LEFT OUTER JOIN NBS_ODSE.[dbo].NBS_ANSWER PA  with ( nolock ) on rmeta.nbs_question_uid=PA.nbs_question_uid AND pa.ANSWER_GROUP_SEQ_NBR IS NULL
											     LEFT OUTER JOIN rdb.dbo.TMP_S_VACCINATION_UIDS STG on STG.PAGE_UID=PA.act_uid
										  	     INNER JOIN NBS_SRTE.dbo.CODE_VALUE_GENERAL CVG   with ( nolock ) ON 	CVG.CODE=rmeta.DATA_TYPE
											   WHERE CVG.CODE_SET_NM = 'NBS_DATA_TYPE' AND CODE in ('Numeric', 'NUMERIC')
											   ORDER BY 	ACT_UID,NBS_ANSWER_UID, rmeta.CODE_SET_GROUP_ID;
											 
												update rdb.dbo.NUMERIC_BASE_DATA_VR set ANSWER_UNIT=SUBSTRING(ANSWER_TXT, 1, (CHARINDEX( '^',ANSWER_TXT)-1)) where CHARINDEX( '^',ANSWER_TXT) > 0 ;
	
												update rdb.dbo.NUMERIC_BASE_DATA_VR set LENCODED=LEN(RTRIM(ANSWER_UNIT)) where CHARINDEX( '^',ANSWER_TXT) > 0 ;

												update rdb.dbo.NUMERIC_BASE_DATA_VR set ANSWER_CODED=SUBSTRING(ANSWER_TXT, (LENCODED+2), TXT_LEN)  where CHARINDEX( '^',ANSWER_TXT) > 0 ;
	
												update rdb.dbo.NUMERIC_BASE_DATA_VR set UNIT_VALUE1 = replace(ANSWER_UNIT, ',','')  where CHARINDEX( '^',ANSWER_TXT) > 0 ;

												update rdb.dbo.NUMERIC_BASE_DATA_VR set RDB_COLUMN_NM2= substring(RTRIM(RDB_COLUMN_NM),1,25) + ' UNIT'
												where LEN(RTRIM(ANSWER_CODED)) > 0;

											      SELECT @RowCount_no = @@ROWCOUNT;

													     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;
--------------------------------------------------------22. CREATE TABLE rdb.dbo.NUMERIC_DATA_2_VR-------------to get Numeric data------------------------------------------
            BEGIN TRANSACTION;

										SET @Proc_Step_no = 22;---(macro Table 19)
										SET @Proc_Step_Name = 'Generating rdb.dbo.NUMERIC_DATA_2_VR'; 

										 IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_VR', 'U') IS NOT NULL  
										 drop table NUMERIC_DATA_2_VR ;

											select *
											into rdb.dbo.NUMERIC_DATA_2_VR
											from rdb.dbo.NUMERIC_BASE_DATA_VR;
											/*
											DATA NUMERIC_DATA2;------------------------------------@Proc_Step_no = 22;---(macro Table 19)
											SET NUMERIC_DATA1;
											IF(LENGTHN(RDB_COLUMN_NM2)>0) THEN RDB_COLUMN_NM=RDB_COLUMN_NM2;
											RUN
											*/

											update rdb.dbo.NUMERIC_DATA_2_VR
											set RDB_COLUMN_NM=RDB_COLUMN_NM2
											where LEN(rtrim(RDB_COLUMN_NM2)) > 0;
											       
													 SELECT @RowCount_no = @@ROWCOUNT;

										                  INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;
----------------------------------------------------23 CREATE TABLE rdb.dbo.NUMERIC_DATA_MERGED_VR-----------------------to get Numeric data-------------------------------------------- 
	   
		    BEGIN TRANSACTION;

									SET @Proc_Step_no = 23;---(macro Table 20)
									SET @Proc_Step_Name = 'CREATE TABLE  NUMERIC_DATA_MERGED_VR'; 

									IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_VR', 'U') IS NOT NULL  
									 drop table rdb.dbo.NUMERIC_DATA_MERGED_VR;

									  select DISTINCT
									  	   coalesce ( nbd.[NBS_QUESTION_UID],  nd2.[NBS_QUESTION_UID]) [NBS_QUESTION_UID],
										   coalesce ( nbd.[CODE_SET_GROUP_ID],  nd2.[CODE_SET_GROUP_ID]) [CODE_SET_GROUP_ID],
										   coalesce ( nbd.[RDB_COLUMN_NM],  nd2.[RDB_COLUMN_NM]) [RDB_COLUMN_NM],
                                           coalesce ( nbd.NBS_ANSWER_UID,  nd2.NBS_ANSWER_UID) NBS_ANSWER_UID,
										   coalesce ( nbd.[ANSWER_TXT],  nd2.[ANSWER_TXT]) [ANSWER_TXT],
										   coalesce ( nbd.[PAGE_UID],  nd2.[PAGE_UID]) [PAGE_UID],
										   coalesce ( nbd.[RECORD_STATUS_CD],  nd2.[RECORD_STATUS_CD]) [RECORD_STATUS_CD],
										   coalesce ( nbd.[TXT_LEN],  nd2.[TXT_LEN]) [TXT_LEN],
										   coalesce ( nbd.[ANSWER_UNIT],  nd2.[ANSWER_UNIT]) [ANSWER_UNIT],
										   coalesce ( nbd.[LENCODED],  nd2.[LENCODED]) [LENCODED],
										   coalesce ( nbd.[ANSWER_CODED],  nd2.[ANSWER_CODED]) [ANSWER_CODED],
										   coalesce ( nbd.[UNIT_VALUE1],  nd2.[UNIT_VALUE1]) [UNIT_VALUE1],
										   coalesce ( nbd.[RDB_COLUMN_NM2],  nd2.[RDB_COLUMN_NM2]) [RDB_COLUMN_NM2]
											into rdb.dbo.NUMERIC_DATA_MERGED_VR
											FROM [RDB].[dbo].NUMERIC_BASE_DATA_VR nbd
											full outer join  [RDB].[dbo].NUMERIC_DATA_2_VR nd2
											on nbd.NBS_ANSWER_UID = nd2.NBS_ANSWER_UID
											and nbd.[RDB_COLUMN_NM] = nd2.[RDB_COLUMN_NM]

											SELECT @RowCount_no = @@ROWCOUNT;

											              INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 


			COMMIT TRANSACTION;
---------------------------------------------------24. CREATE TABLE rdb.dbo.NUMERIC_DATA_TRANS_VR------------------------to get Numeric data-------------------------------------

     
			BEGIN TRANSACTION;

											SET @Proc_Step_no = 24;---(macro Table 21)
											SET @Proc_Step_Name = 'CREATE TABLE  NUMERIC_DATA_TRANS_VR'; 


											IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_VR', 'U') IS NOT NULL  
											 drop table rdb.dbo.NUMERIC_DATA_TRANS_VR

												SELECT DISTINCT
                                                 CODED.NBS_QUESTION_UID,
												 CODED.RDB_COLUMN_NM , 
												 CODED.NBS_ANSWER_UID,-----&answer_UID,
												 CODED.ANSWER_TXT ,
												 CODED.PAGE_UID, 	-----  &UID,
												 CVG.CODE_SET_NM,	 
												 CVG.CODE,	
												 CODED.ANSWER_UNIT,
												 CODED.ANSWER_CODED,
												 CVG.code_short_desc_txt as UNIT
											  into rdb.dbo.NUMERIC_DATA_TRANS_VR
												FROM 
													rdb.dbo.NUMERIC_DATA_MERGED_VR CODED  with ( nolock ) 
													LEFT JOIN NBS_SRTE.dbo.CODESET_GROUP_METADATA METADATA  with ( nolock ) ON 	METADATA.CODE_SET_GROUP_ID=CODED.CODE_SET_GROUP_ID
													LEFT JOIN NBS_SRTE.dbo.CODE_VALUE_GENERAL CVG  with ( nolock ) ON CVG.CODE_SET_NM=METADATA.CODE_SET_NM
												 WHERE 	CVG.CODE=CODED.ANSWER_CODED or ANSWER_CODED is null
												ORDER BY PAGE_UID;
												/*
												DATA NUMERIC_DATA_TRANS;
												SET NUMERIC_DATA_TRANS;
													X=INDEX(RDB_COLUMN_NM,' UNIT');
													IF TRIM(UNIT)=''  THEN ANSWER_TXT=ANSWER_TXT;
													ELSE IF X>0 THEN ANSWER_TXT=UNIT;
													ELSE ANSWER_TXT=ANSWER_UNIT;
												RUN;*/
										
												UPDATE rdb.dbo.NUMERIC_DATA_TRANS_VR
												SET PAGE_UID = coalesce(PAGE_UID,1),
													ANSWER_TXT = Case
																  when coalesce(RTRIM(UNIT),'')=''  THEN ANSWER_TXT
																  when CHARINDEX(' UNIT',RDB_COLUMN_NM) > 0 then UNIT
																  ELSE ANSWER_UNIT
												                  END;

												SELECT @RowCount_no = @@ROWCOUNT

												;WITH CTE  as (
												select VR.rdb_column_nm, CASE WHEN VR.answer_txt is not null  THEN 1 ELSE 0 END as Ans_null
												FROM  rdb.dbo.NUMERIC_DATA_TRANS_VR  VR
												 where RDB_COLUMN_NM  in ( select RDB_COLUMN_NM from rdb.dbo.NUMERIC_DATA_MERGED_VR group by rdb_column_nm)
												 group by rdb_column_nm, CASE WHEN answer_txt is not null  THEN 1 ELSE 0 END
												 )
												 DELETE FROM rdb.dbo.NUMERIC_DATA_TRANS_VR 
												 where rdb_column_nm in (select rdb_column_nm
												 from CTE
												 where ans_null = 0 
												 except
												 select rdb_column_nm
												 from CTE 
												 where ans_null = 1
												 );
                                                
											
												    INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

												-- Print @RowCount_no
												-- Introduce dummy row
												 IF  @RowCount_no=0 
												 BEGIN
												 INSERT INTO rdb.dbo.NUMERIC_DATA_TRANS_VR values(0,'NUM',0,'NUM',0,'NUM',0,0,0,0)
												 END

												     
			COMMIT TRANSACTION;
--Select * from  rdb.dbo.NUMERIC_DATA_TRANS_VR
--------------------------------------------------25. CREATE TABLE rdb.dbo.NUMERIC_DATA_PIVOT_VR---------rdb.dbo.NUMERIC_DATA_out-----------------to get Numeric data-------------------------------------------------
 
			BEGIN TRANSACTION;
									SET @Proc_Step_no =25;---(macro Table 22)
									SET @Proc_Step_Name = ' CREATE TABLE  NUMERIC_DATA_PIVOT_VR'; 

											  IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_out', 'U') IS NOT NULL  
												 drop table rdb.dbo.NUMERIC_DATA_out;
							 				  
											   DECLARE @columns4 NVARCHAR(MAX);
							                   DECLARE @sql4 NVARCHAR(MAX);
						
												SET @columns4 = N'';

												SELECT @columns4+=N', p.'+QUOTENAME(LTRIM(RTRIM(RDB_COLUMN_NM)))
												FROM
												(
													SELECT RDB_COLUMN_NM
													FROM rdb.dbo.NUMERIC_DATA_TRANS_VR AS p
												    GROUP BY [RDB_COLUMN_NM]
												) AS x;
												--print @columns4

												SET @sql4 = N'
												SELECT PAGE_UID as PAGE_UID_NUMERIC, '+isnull(STUFF(@columns4, 1, 2, ''),'TEST')+
												' into rdb.dbo.NUMERIC_DATA_out ' +
												'FROM (
												SELECT PAGE_UID,answer_txt,RDB_COLUMN_NM
												 FROM rdb.[dbo].NUMERIC_DATA_TRANS_VR
													group by PAGE_UID, answer_txt , RDB_COLUMN_NM
														) AS j PIVOT (max(answer_txt) FOR RDB_COLUMN_NM in 
													   ('+STUFF(REPLACE(@columns4, ', p.[', ',['), 1, 1, '')+')) AS p;';

												---print @sql4;
												   EXEC sp_executesql @sql4;
												
								                       SELECT @RowCount_no = @@ROWCOUNT;

														INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 
	      COMMIT TRANSACTION;
---Select * from rdb.dbo.NUMERIC_DATA_out 
-------------------------------------------------------26. CREATE TABLE rdb.dbo.NUMERIC_DATA_VR_out------------------------to get Numeric data----------------------------------------
   
  /*Select * from  rdb.dbo.Text_data_VR_out
  Select * from  rdb.dbo.CODED_DATA_OUT
  Select * from  rdb.dbo.DATE_DATA_VR_out
  Select * from  rdb.dbo.NUMERIC_DATA_out*/

 /* Select * from tmp_DATE_DATA_VR_out
	Select * from tmp_CODED_DATA_OUT
	Select * from tmp_Text_data_VR_out
	Select * From tmp_NUMERIC_DATA_out
	Select * from rdb.dbo.TMP_VACCINATION_INIT	*/
	 
	BEGIN TRANSACTION;
	
											SET @Proc_Step_no =26;---(macro Table 23)
											SET @Proc_Step_Name = 'ALL_Table_out'; 

												IF OBJECT_ID('rdb.dbo.tmp_NUMERIC_DATA_out', 'U') IS NOT NULL  
												   drop table rdb.dbo.tmp_NUMERIC_DATA_out;

												 IF OBJECT_ID('rdb.dbo.tmp_DATE_DATA_VR_out', 'U') IS NOT NULL  
												  drop table   rdb.dbo.tmp_DATE_DATA_VR_out;
												
												IF OBJECT_ID('rdb.dbo.tmp_CODED_DATA_OUT', 'U') IS NOT NULL  
												   drop table rdb.dbo.tmp_CODED_DATA_OUT;
												
												IF OBJECT_ID('rdb.dbo.tmp_Text_data_VR_out', 'U') IS NOT NULL  
												   drop table rdb.dbo.tmp_Text_data_VR_out;

												IF OBJECT_ID('rdb.dbo.tmp_Text_data_VR_out', 'U') IS NOT NULL  
												   drop table rdb.dbo.tmp_Text_data_VR_out;

												IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_out', 'U') IS  NULL  
												 BEGIN
												 SELECT PAGE_UID as Page_UID_Numeric,RDB_COLUMN_NM
												into rdb.dbo.NUMERIC_DATA_out
												 FROM rdb.[dbo].NUMERIC_DATA_TRANS_VR
												 where 1=2;
												 END;

												IF OBJECT_ID('rdb.dbo.DATE_DATA_VR_out', 'U') IS  NULL  
												 BEGIN
												 SELECT PAGE_UID as Page_Uid_Date,RDB_COLUMN_NM
												into rdb.dbo.DATE_DATA_VR_out
												 FROM rdb.[dbo].PAGE_DATE_TABLE_VR
												 where 1=2;
												 END;

												IF OBJECT_ID('rdb.dbo.CODED_DATA_OUT', 'U') IS  NULL  
												 BEGIN
												 SELECT PAGE_UID as Page_Uid_Coded,RDB_COLUMN_NM
												into rdb.dbo.CODED_DATA_OUT
												 FROM rdb.[dbo].CODED_TABLE_MERGED
												 where 1=2;
												 END;

												IF OBJECT_ID('rdb.dbo.Text_data_VR_out', 'U') IS  NULL  
												 BEGIN
												 SELECT PAGE_UID as Page_Uid_text,RDB_COLUMN_NM
												into rdb.dbo.Text_data_VR_out
												 FROM rdb.[dbo].TEXT_DATA_VR
												 where 1=2;
												 END;

														Select * into tmp_NUMERIC_DATA_out 
														From  rdb.dbo.NUMERIC_DATA_out Where Len(Page_UID_Numeric) >1 
														SELECT @RowCount_no = @@ROWCOUNT;
														  If @RowCount_no=0
														  Alter table tmp_NUMERIC_DATA_out 
														  Drop Column Num

														Select * into tmp_DATE_DATA_VR_out
														from rdb.dbo.DATE_DATA_VR_out Where Len(Page_Uid_Date)>1 
														 SELECT @RowCount_no = @@ROWCOUNT;
														  If @RowCount_no=0
														  Alter table tmp_DATE_DATA_VR_out
														  Drop Column Datee

														Select * into tmp_CODED_DATA_OUT
														from rdb.dbo.CODED_DATA_OUT Where Len(Page_Uid_Coded)>1
														 SELECT @RowCount_no = @@ROWCOUNT;
														  If @RowCount_no=0
														  Alter table tmp_CODED_DATA_OUT
														  Drop Column Coded

														Select * into tmp_Text_data_VR_out
														from rdb.dbo.Text_data_VR_out Where Len(Page_Uid_text)>1  
													     SELECT @RowCount_no = @@ROWCOUNT;
														  If @RowCount_no=0
														  Alter table tmp_Text_data_VR_out
														  Drop Column text

														   SELECT @RowCount_no = @@ROWCOUNT;

														INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO)

			COMMIT TRANSACTION
--------------------------------------------------------------27. CREATE TABLE rdb.[dbo].[S_VACCINATION]-------------------------------------------------------------------

		   
		    BEGIN TRANSACTION;
						
													 SET @PROC_STEP_NO =27;
													 SET @PROC_STEP_NAME = 'GENERATING rdb.dbo.S_VACCINATION'; 

													  IF OBJECT_ID('rdb.dbo.[S_VACCINATION]', 'U') IS NOT NULL  
													 drop table rdb.[dbo].[S_VACCINATION];
													   
													   IF OBJECT_ID('rdb.dbo.[TMP_S_VACCINATION]', 'U') IS NOT NULL  
													 drop table rdb.[dbo].[TMP_S_VACCINATION];

													  SELECT  DISTINCT
													    sk.[ADD_TIME] ,
														sk.[ADD_USER_ID],
														sk.[AGE_AT_VACCINATION] ,
														sk.[AGE_AT_VACCINATION_UNIT] ,--------3
														sk.[LAST_CHG_TIME],-----------------------------Intervention Table ODSE
														sk.[LAST_CHG_USER_ID],
														sk.[LOCAL_ID],
														sk.[RECORD_STATUS_CD],
														sk.[RECORD_STATUS_TIME] ,
														sk.[VACCINE_ADMINISTERED_DATE],
														sk.[VACCINE_DOSE_NBR] ,
														sk.[VACCINATION_ADMINISTERED_NM] ,---------1
														sk.[VACCINATION_ANATOMICAL_SITE] ,---------2
														sk.[VACCINATION_UID] ,-------------------------Intervention UID
														sk.[VACCINE_EXPIRATION_DT] ,
														sk.[VACCINE_INFO_SOURCE] ,---------------------5
														sk.[VACCINE_LOT_NUMBER_TXT] ,
														sk.[VACCINE_MANUFACTURER_NM] ,-------4
														sk.[VERSION_CTRL_NBR], 
													    sk.[ELECTRONIC_IND],
													    ndo.*,ddo.*,cdo.*,tdo.*
												   into rdb.[dbo].[TMP_S_VACCINATION]
													  from	rdb.dbo.TMP_VACCINATION_INIT sk
														LEFT OUTER JOIN rdb.dbo.tmp_NUMERIC_DATA_out  ndo ON ndo.PAGE_UID_NUMERIC = sk.VACCINATION_UID 
														LEFT OUTER JOIN rdb.dbo.tmp_DATE_DATA_VR_out  ddo ON ddo.PAGE_UID_date    = sk.VACCINATION_UID 
														LEFT OUTER JOIN rdb.dbo.tmp_CODED_DATA_out    cdo ON cdo.PAGE_UID_coded   = sk.VACCINATION_UID 
														LEFT OUTER JOIN rdb.dbo.tmp_text_data_VR_out  tdo ON tdo.PAGE_UID_text    = sk.VACCINATION_UID 

														
															Begin
														    Alter table  rdb.[dbo].[TMP_S_VACCINATION]
															Drop Column PAGE_UID_NUMERIC,PAGE_UID_date ,PAGE_UID_coded,PAGE_UID_text
															END
														
														------to do all the dynamic actions
														 SELECT  DISTINCT
													    sk.[ADD_TIME] ,
														sk.[ADD_USER_ID],
														sk.[AGE_AT_VACCINATION] ,
														sk.[AGE_AT_VACCINATION_UNIT] ,--------3
														sk.[LAST_CHG_TIME],-----------------------------Intervention Table ODSE
														sk.[LAST_CHG_USER_ID],
														sk.[LOCAL_ID],
														sk.[RECORD_STATUS_CD],
														sk.[RECORD_STATUS_TIME] ,
														sk.[VACCINE_ADMINISTERED_DATE],
														sk.[VACCINE_DOSE_NBR] ,
														sk.[VACCINATION_ADMINISTERED_NM] ,---------1
														sk.[VACCINATION_ANATOMICAL_SITE] ,---------2
														sk.[VACCINATION_UID] ,-------------------------Intervention UID
														sk.[VACCINE_EXPIRATION_DT] ,
														sk.[VACCINE_INFO_SOURCE] ,---------------------5
														sk.[VACCINE_LOT_NUMBER_TXT] ,
														sk.[VACCINE_MANUFACTURER_NM] ,-------4
														sk.[VERSION_CTRL_NBR], 
													    sk.[ELECTRONIC_IND],
													    ndo.*,ddo.*,cdo.*,tdo.*
												     into rdb.[dbo].[S_VACCINATION]
													 from	rdb.dbo.TMP_VACCINATION_INIT sk
														LEFT OUTER JOIN rdb.dbo.tmp_NUMERIC_DATA_out  ndo ON ndo.PAGE_UID_NUMERIC = sk.VACCINATION_UID 
														LEFT OUTER JOIN rdb.dbo.tmp_DATE_DATA_VR_out  ddo ON ddo.PAGE_UID_date    = sk.VACCINATION_UID 
														LEFT OUTER JOIN rdb.dbo.tmp_CODED_DATA_out    cdo ON cdo.PAGE_UID_coded  = sk.VACCINATION_UID 
														LEFT OUTER JOIN rdb.dbo.tmp_text_data_VR_out  tdo ON tdo.PAGE_UID_text    = sk.VACCINATION_UID 

															Begin
														    Alter table  rdb.[dbo].[S_VACCINATION]
															Drop Column PAGE_UID_NUMERIC,PAGE_UID_date ,PAGE_UID_coded,PAGE_UID_text
															END
                                               
													   SELECT @RowCount_no = @@ROWCOUNT;

														INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;
/*
Select * from S_Vaccination
Select * from TMP_S_VACCINATION
*/
----------------------------------------------------------28. CREATE TABLE TMP_L_VACCINATION_N ----------------------------------------------

	  BEGIN TRANSACTION;
											SET @PROC_STEP_NO =28;
											SET @PROC_STEP_NAME = ' GENERATING TMP_L_VACCINATION_N'; 

														IF OBJECT_ID('rdb.dbo.TMP_L_VACCINATION_N', 'U') IS NOT NULL   
 															 drop table rdb.dbo.TMP_L_VACCINATION_N ;

													CREATE TABLE rdb.[dbo].[TMP_L_VACCINATION_N]
													(
													[VACCINATION_id]  [int] IDENTITY(1,1) NOT NULL,
													[VACCINATION_UID_N] [bigint] NOT NULL,  
													[D_VACCINATION_KEY] [numeric](18, 0) NULL
													 ) ON [PRIMARY]
													 ;

													insert into rdb.dbo.TMP_L_VACCINATION_N([VACCINATION_UID_N],[D_VACCINATION_KEY])
													 
													 SELECT DISTINCT VACCINATION_UID ,null
													  FROM rdb.dbo.[S_VACCINATION]
													 EXCEPT 
													 SELECT [VACCINATION_UID],null
													   FROM rdb.dbo.[L_VACCINATION]
													 ;
													 ----New VACCINATION_UID  10101282 Select * from L_Vaccination order by 1
	 										
												 UPDATE rdb.dbo.TMP_L_VACCINATION_N 
												   SET [D_VACCINATION_KEY]= VACCINATION_id + coalesce((SELECT MAX([D_VACCINATION_KEY]) FROM RDB.dbo.[L_VACCINATION]),0)
												   ;
												SELECT @ROWCOUNT_NO = @@ROWCOUNT;

													INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;

 ----Select * from rdb.dbo.TMP_L_VACCINATION_N
--------------------------------------------------------------29. CREATE TABLE L_VACCINATION------------------------------------------------------------

		    BEGIN TRANSACTION;

															SET @PROC_STEP_NO = 29;
															SET @PROC_STEP_NAME = ' GENERATING TMP_L_VACCINATION'; 

																IF OBJECT_ID('rdb.dbo.TMP_L_VACCINATION_E', 'U') IS NOT NULL  
																DROP TABLE rdb.dbo.TMP_L_VACCINATION_E;

															IF NOT EXISTS (SELECT * FROM rdb.[dbo].[L_VACCINATION] WHERE D_VACCINATION_KEY=1) 
														BEGIN
														   INSERT INTO rdb.dbo.L_VACCINATION (D_VACCINATION_KEY,VACCINATION_UID) VALUES (1,0);
														END

																---To get all the exsisting records from L_Vaccination

															SELECT  LO.D_VACCINATION_KEY,
														            SO.VACCINATION_UID as VACCINATION_UID 
													    	INTO rdb.dbo.TMP_L_VACCINATION_E
															FROM rdb.dbo.S_VACCINATION SO ,---Staging
																 rdb.dbo.L_VACCINATION LO  ---LookUp
															WHERE SO.VACCINATION_UID = LO.VACCINATION_UID
															 Order by LO.D_VACCINATION_KEY
															
                                                     ----Merge the existing  records From  TMP_L_VACCINATION_E and L_VACCINATION so that it does not duplicate records

															  MERGE rdb.dbo.L_VACCINATION as Target
															  USING rdb.dbo.TMP_L_VACCINATION_E as Source
															  ON (Target.Vaccination_UID = Source.Vaccination_UID)
															  WHEN Matched THEN UPDATE SET
															  Target.Vaccination_UID = Source.Vaccination_UID,
															  Target.D_Vaccination_Key = Source.D_Vaccination_Key;

                                                        
														---Below code is where I add the New Vaccination_Uid to L_Vaccination
                                                            INSERT INTO rdb.dbo.L_VACCINATION----here L_VACCINATION Contains all UID
															SELECT DISTINCT  D_VACCINATION_KEY,VACCINATION_UID_N
															FROM  rdb.dbo.TMP_L_VACCINATION_N WHERE VACCINATION_UID_N is not null ;---9/2/2021	

														---IF Vaccination_UID is not there in D_VACCINATION ----8/20/21
                                                      /*  
														BEGIN
														IF NOT EXISTS (SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='D_VACCINATION'
														                                      AND COLUMN_NAME ='VACCINATION_UID' ) 
														     BEGIN
																ALTER TABLE rdb.dbo.D_VACCINATION
																ADD  [VACCINATION_UID] bigint NOT NULL DEFAULT '1'
														     END 
														END
														*/
														IF NOT EXISTS (SELECT * FROM rdb.[dbo].[D_VACCINATION] WHERE D_VACCINATION_KEY=1) 
														BEGIN
														INSERT INTO rdb.dbo.D_VACCINATION (D_VACCINATION_KEY,VACCINATION_UID,[VERSION_CTRL_NBR]) VALUES (1,1,1);
														END
												       SELECT @ROWCOUNT_NO = @@ROWCOUNT;

													 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;

----------------------------------------------------30 Delete from D_Vaccination All records and then Insert from TMP_S_Vaccination(UPDATE Equivalent) and Then Add new columns-------------------------------------------------------------------------

           BEGIN TRANSACTION;
									DECLARE @Temp_Query_Table TABLE (ID INT IDENTITY(1,1), QUERY_stmt VARCHAR(5000))
									DECLARE @column_query VARCHAR(5000)
									DECLARE @Max_Query_No INT
									DECLARE @Curr_Query_No INT
									DECLARE @ColumnList varchar(5000)
															
															SET @PROC_STEP_NO = 30;
															SET @PROC_STEP_NAME = 'ALTERING TABLE D_VACCINATION'; 

												  
													---Delete and Insert == Update for D_Vaccination
												 
												   BEGIN
													Delete from D_VACCINATION
													Where exists(Select * from rdb.dbo.TMP_S_VACCINATION S WHERE S.Vaccination_UID = D_VACCINATION.VACCINATION_UID )
													END
			
													------Select * from D_Vaccination

												INSERT INTO @Temp_Query_Table

												SELECT  'ALTER TABLE rdb.dbo.D_VACCINATION ADD [' + COLUMN_NAME + '] ' + DATA_TYPE +
												 CASE 
													WHEN DATA_TYPE IN ('char','varchar','nchar','nvarchar') THEN 
													' ('  + COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH,-1) AS varchar(10)),CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10))) + ')'
													ELSE '' END
													 + 
													 CASE WHEN IS_NULLABLE ='NO' THEN ' NOT NULL' ELSE ' NULL'  END
												FROM INFORMATION_SCHEMA.COLUMNS c
												WHERE TABLE_NAME = 'TMP_S_VACCINATION'
													  AND NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS
																	WHERE TABLE_NAME = 'D_VACCINATION'
																	  AND COLUMN_NAME = c.COLUMN_NAME )
													  AND lower(COLUMN_NAME) not in (lower('VACCINATION_UID'),'last_chg_time'  )
													;

												SET @Max_Query_No =(SELECT MAX(ID) FROM @Temp_Query_Table t);

												SET @Curr_Query_No =0;

												WHILE @Max_Query_No > @Curr_Query_No

												BEGIN
													SET @Curr_Query_No = @Curr_Query_No +1 ;
    
													SET @column_query = (SELECT QUERY_stmt FROM @Temp_Query_Table t WHERE ID = @Curr_Query_No) ;
	
													select @column_query;

													EXEC(@column_query) ;
    
												END
												SELECT @ROWCOUNT_NO = @@ROWCOUNT;

															INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;	
------------------------------------------------------------31 Now Doing an Insert -------------------------------------------
            BEGIN TRANSACTION;
															SET @PROC_STEP_NO = 31;
															SET @PROC_STEP_NAME = ' INSERTING in TABLE D_VACCINATION'; 


													  if not exists(select  D_VACCINATION_key
																				 FROM [RDB].[dbo].[D_VACCINATION]
																				 where d_VACCINATION_key = 1)
														INSERT INTO  RDB.[dbo].[D_VACCINATION]( [D_VACCINATION_key])
														   values (1); 

														DECLARE @insert_query NVARCHAR(MAX)

														SET @insert_query = ( SELECT  'INSERT INTO  RDB.[dbo].[D_VACCINATION]( [D_VACCINATION_KEY] ,' 
														+
														  STUFF ((
															SELECT ', [' + name + ']'
															FROM syscolumns
															WHERE id = OBJECT_ID('TMP_S_VACCINATION') 
															---  AND lower(NAME) not in (lower('VACCINATION_UID'),'last_chg_time'  )
															  AND lower(NAME) not in (lower('VACCINATION_UID') )
															FOR XML PATH('')), 1, 1, '')
														 +
														   ', VACCINATION_UID' +') select D_VACCINATION_KEY , '
														 +
														   STUFF ((
															SELECT ', [' + name + ']'
															FROM syscolumns
															WHERE id = OBJECT_ID('TMP_S_VACCINATION') 
															 --- AND lower(NAME) not in (lower('VACCINATION_UID'),'last_chg_time'  )
															  AND lower(NAME) not in (lower('VACCINATION_UID'))
															FOR XML PATH('')), 1, 1, '') +', LINV.VACCINATION_UID'
														+
														   ' 
														   FROM  RDB.dbo.L_VACCINATION LINV 
														   INNER JOIN RDB.dbo.TMP_S_VACCINATION SINV ON SINV.VACCINATION_UID=LINV.VACCINATION_UID 
															where linv.D_VACCINATION_KEY != 1'
	   														);

													-----Select @insert_query;
														EXEC sp_executesql @insert_query ;
														SELECT @ROWCOUNT_NO = @@ROWCOUNT;

															INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;	

----Select * from D_VACCINATION
----------------------------------------------------------32. CREATE TABLE TMP_PARTICIPATION_INIT-----------------------------------------------------------

		   BEGIN TRANSACTION;

			  											SET @PROC_STEP_NO =32;
														SET @PROC_STEP_NAME = ' GENERATING TMP_PARTICIPATION_INIT'; 

														IF OBJECT_ID('rdb.dbo.TMP_PARTICIPATION_INIT', 'U') IS NOT NULL  
														DROP TABLE rdb.dbo.TMP_PARTICIPATION_INIT;

														SELECT Distinct
														L_VACCINATION.D_VACCINATION_KEY,
														NBS_ACT_ENTITY.ACT_UID, 
														NBS_ACT_ENTITY.ENTITY_UID, 
														TYPE_CD 
													     INTO rdb.dbo.TMP_PARTICIPATION_INIT
														FROM rdb.dbo.L_VACCINATION with (nolock)
														LEFT OUTER JOIN NBS_ODSE.dbo.NBS_ACT_ENTITY with (nolock) ON L_VACCINATION.VACCINATION_UID=NBS_ACT_ENTITY.ACT_UID
													 	---where NBS_ACT_ENTITY.LAST_CHG_TIME>= @batch_start_time	AND NBS_ACT_ENTITY.LAST_CHG_TIME <  @batch_end_time---- Incremental 
														
														SELECT @ROWCOUNT_NO = @@ROWCOUNT;

														INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

            COMMIT TRANSACTION;
---SELECT * FROM rdb.dbo.TMP_PARTICIPATION_INIT
------------------------------------------------------33. CREATE TABLE rdb.dbo.TMP_PARTICIPATION_PAT,rdb.dbo.TMP_PARTICIPATION_PRV-----------------------------------------------------------------------------

			BEGIN TRANSACTION;

												SET @PROC_STEP_NO =33;
												SET @PROC_STEP_NAME = ' GENERATING TMP_PARTICIPATION_PAT'; 

												IF OBJECT_ID('rdb.dbo.TMP_PARTICIPATION_PAT', 'U') IS NOT NULL  
												DROP TABLE rdb.dbo.TMP_PARTICIPATION_PAT;

														 SELECT D_VACCINATION_KEY, 
														        PATIENT_KEY, 
																I.ACT_UID
														INTO rdb.dbo.TMP_PARTICIPATION_PAT		 
														FROM rdb.dbo.TMP_PARTICIPATION_INIT I 
														INNER JOIN rdb.dbo.L_PATIENT with (nolock) ON L_PATIENT.PATIENT_UID=I.ENTITY_UID WHERE TYPE_CD='SubOfVacc'
														Order by D_VACCINATION_KEY

												IF OBJECT_ID('rdb.dbo.TMP_PARTICIPATION_PRV', 'U') IS NOT NULL  
												DROP TABLE rdb.dbo.TMP_PARTICIPATION_PRV;

														 SELECT D_VACCINATION_KEY, 
														 PROVIDER_KEY AS VACCINE_GIVEN_BY_KEY,
														 ACT_UID  
														 INTO rdb.dbo.TMP_PARTICIPATION_PRV
														 FROM rdb.dbo.TMP_PARTICIPATION_INIT I
														 INNER JOIN rdb.dbo.L_PROVIDER ON I.ENTITY_UID=L_PROVIDER.PROVIDER_UID WHERE TYPE_CD='PerformerOfVacc';

														  SELECT @ROWCOUNT_NO = @@ROWCOUNT;

														INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

            COMMIT TRANSACTION;

------------------------------------------------------34. CREATE TABLE rdb.dbo.TMP_PARTICIPATION_PAT,rdb.dbo.TMP_PARTICIPATION_PRV-----------------------------------------------------------------------------
 
			BEGIN TRANSACTION;

												SET @PROC_STEP_NO =34;
												SET @PROC_STEP_NAME = ' GENERATING TMP_PARTICIPATION_ORG'; 

												IF OBJECT_ID('rdb.dbo.TMP_PARTICIPATION_ORG', 'U') IS NOT NULL  
												DROP TABLE rdb.dbo.TMP_PARTICIPATION_ORG;

													 SELECT D_VACCINATION_KEY, 
													 ORGANIZATION_KEY AS VACCINE_GIVEN_BY_ORG_KEY , 
													 ACT_UID 
													 into rdb.dbo.TMP_PARTICIPATION_ORG 
													 FROM rdb.dbo.TMP_PARTICIPATION_INIT I
													 INNER JOIN rdb.dbo.L_ORGANIZATION with (nolock) ON I.ENTITY_UID=L_ORGANIZATION.ORGANIZATION_UID WHERE TYPE_CD='PerformerOfVacc'
													 ORDER  BY D_VACCINATION_KEY; 
													  
													SELECT @ROWCOUNT_NO = @@ROWCOUNT;

													INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

            COMMIT TRANSACTION;
------------------------------------------------------35. CREATE TABLE rdb.dbo.TMP_VACCINATION_FACT_INIT-----------------------------------------------------------------------------

			 BEGIN TRANSACTION;

												SET @PROC_STEP_NO =35;
												SET @PROC_STEP_NAME = ' GENERATING rdb.dbo.TMP_VACCINATION_FACT_INIT'; 
												
												IF OBJECT_ID('rdb.dbo.TMP_VACCINATION_FACT_INIT', 'U') IS NOT NULL  
												DROP TABLE rdb.dbo.TMP_VACCINATION_FACT_INIT;
												
												SELECT 
												PAT.ACT_UID, 
											    PAT.PATIENT_KEY,
												PAT.D_VACCINATION_KEY, 
												PRV.VACCINE_GIVEN_BY_KEY, 
												ORG.VACCINE_GIVEN_BY_ORG_KEY
												INTO rdb.dbo.TMP_VACCINATION_FACT_INIT
												FROM rdb.dbo.TMP_PARTICIPATION_PAT pat
												LEFT OUTER JOIN rdb.dbo.TMP_PARTICIPATION_PRV  PRV ON PAT.D_VACCINATION_KEY = PRV.D_VACCINATION_KEY
												LEFT OUTER JOIN rdb.dbo.TMP_PARTICIPATION_ORG org  ON PAT.D_VACCINATION_KEY = ORG.D_VACCINATION_KEY
                                                ORDER BY D_VACCINATION_KEY; 

												UPDATE rdb.dbo.TMP_VACCINATION_FACT_INIT
												SET 
												VACCINE_GIVEN_BY_KEY     = CASE WHEN VACCINE_GIVEN_BY_KEY     is NULL THEN 1 ELSE VACCINE_GIVEN_BY_KEY END,
												VACCINE_GIVEN_BY_ORG_KEY = CASE WHEN VACCINE_GIVEN_BY_ORG_KEY is NULL THEN 1 ELSE VACCINE_GIVEN_BY_ORG_KEY END,
											 ---D_VACCINATION_REPEAT_KEY = CASE WHEN D_VACCINATION_REPEAT_KEY is NULL THEN 1 ELSE D_VACCINATION_REPEAT_KEY END,
											    PATIENT_KEY              = CASE WHEN PATIENT_KEY              is NULL THEN 1 ELSE PATIENT_KEY END

												  SELECT @ROWCOUNT_NO = @@ROWCOUNT;

												INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

            COMMIT TRANSACTION;
------------------------------------------------------36. CREATE TABLE rdb.dbo.TMP_VACCINATION_PHC_COLL-----------------------------------------------------------------------------

			BEGIN TRANSACTION;

												SET @PROC_STEP_NO =36;
												SET @PROC_STEP_NAME = ' GENERATING rdb.dbo.TMP_VACCINATION_PHC_COLL'; 
												
												IF OBJECT_ID('rdb.dbo.TMP_VACCINATION_PHC_COLL', 'U') IS NOT NULL  
												DROP TABLE rdb.dbo.TMP_VACCINATION_PHC_COLL;
									
												 SELECT TARGET_ACT_UID AS PHC_UID, 
												 	    D_VACCINATION_KEY
														INTO rdb.dbo.TMP_VACCINATION_PHC_COLL
														FROM NBS_ODSE.dbo.ACT_RELATIONSHIP A  with (nolock)
														INNER JOIN rdb.dbo.L_VACCINATION L ON A.SOURCE_ACT_UID =L.VACCINATION_UID WHERE  A.TYPE_CD='1180'
														--AND A.LAST_CHG_TIME>= @batch_start_time	AND A.LAST_CHG_TIME <  @batch_end_time---- Incremental 

												IF OBJECT_ID('rdb.dbo.PHC_INVESTIGATION_KEY', 'U') IS NOT NULL  
												DROP TABLE rdb.dbo.PHC_INVESTIGATION_KEY;

												SELECT P.PHC_UID,
												       P.D_VACCINATION_KEY,
													   L.INVESTIGATION_KEY
											    INTO rdb.dbo.PHC_INVESTIGATION_KEY
												FROM rdb.dbo.TMP_VACCINATION_PHC_COLL P
												INNER JOIN rdb.dbo.L_INVESTIGATION L ON P.PHC_UID= L.CASE_UID;

												 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

												INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
														 (BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
														 VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

            COMMIT TRANSACTION;
------------------------------------------------------37. CREATE TABLE rdb.dbo.F_VACCINATION -----------------------------------------------------------------------------

			BEGIN TRANSACTION;

												SET @PROC_STEP_NO =37;
												SET @PROC_STEP_NAME = ' GENERATING rdb.dbo.TMP_F_VACCINATION '; 

												IF OBJECT_ID('rdb.dbo.TMP_F_VACCINATION', 'U') IS NOT NULL  
												DROP TABLE rdb.dbo.TMP_F_VACCINATION;

												SELECT 
											    F.PATIENT_KEY,
												F.D_VACCINATION_KEY, 
												F.VACCINE_GIVEN_BY_KEY, 
												F.VACCINE_GIVEN_BY_ORG_KEY,
												1 as D_VACCINATION_REPEAT_KEY,
											    P.INVESTIGATION_KEY
											     INTO rdb.dbo.TMP_F_VACCINATION
												 FROM rdb.dbo.TMP_VACCINATION_FACT_INIT F
												 LEFT OUTER JOIN rdb.dbo.PHC_INVESTIGATION_KEY P ON F.D_VACCINATION_KEY = P.D_VACCINATION_KEY;

												UPDATE rdb.dbo.TMP_F_VACCINATION
												SET  INVESTIGATION_KEY  = CASE WHEN INVESTIGATION_KEY is NULL THEN 1 ELSE INVESTIGATION_KEY END

											    ---Delete existing rows from F_Vaccination
												
												BEGIN
													Delete from F_VACCINATION
													Where exists(Select * from rdb.dbo.TMP_F_VACCINATION T WHERE T.D_VACCINATION_KEY=  F_VACCINATION.D_VACCINATION_KEY and T.INVESTIGATION_KEY=F_VACCINATION.INVESTIGATION_KEY )
												 END
			

										---Insert Only New Records to F_Vaccination Table

                                             INSERT INTO rdb.dbo.F_VACCINATION
												   ( 
													PATIENT_KEY,
													D_VACCINATION_KEY, 
													VACCINE_GIVEN_BY_KEY, 
													VACCINE_GIVEN_BY_ORG_KEY,
													D_VACCINATION_REPEAT_KEY,
													INVESTIGATION_KEY
													   )
										 
												SELECT 
													PATIENT_KEY,
													D_VACCINATION_KEY, 
													VACCINE_GIVEN_BY_KEY, 
													VACCINE_GIVEN_BY_ORG_KEY,
													D_VACCINATION_REPEAT_KEY,
													INVESTIGATION_KEY
									            FROM rdb.dbo.TMP_F_VACCINATION T
												
												 WHERE NOT EXISTS
												(
												  SELECT * 
													FROM rdb.dbo.F_Vaccination with (nolock)
												   WHERE D_VACCINATION_KEY = T.D_VACCINATION_KEY and  INVESTIGATION_KEY=T.INVESTIGATION_KEY 
												)order by INVESTIGATION_KEY

										            SELECT @ROWCOUNT_NO = @@ROWCOUNT;

										           INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
													(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
													VALUES(@BATCH_ID,'VaccinationRecord','RDB.VaccinationRecord','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

										
												
            COMMIT TRANSACTION;
----------------------------------------------Dropping all tmp tables.----------------------------------------------------------
IF OBJECT_ID('rdb.dbo.TMP_F_VACCINATION', 'U') IS NOT NULL   -------------base step
  drop table rdb.dbo.TMP_F_VACCINATION;
IF OBJECT_ID('rdb.dbo.TMP_S_VACCINATION_UIDS', 'U') IS NOT NULL   -------------Step1
  drop table rdb.dbo.TMP_S_VACCINATION_UIDS;
IF OBJECT_ID('rdb.dbo.TMP_VACCINATION_INIT', 'U') IS NOT NULL ------------------Step2  
 drop table rdb.dbo.TMP_VACCINATION_INIT;
 
 IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_TEXT', 'U') IS NOT NULL ----------------Step3 
	drop table rdb.dbo.RDB_UI_METADATA_TEXT;
IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_TEXT_FINAL', 'U') IS NOT NULL  -----------Step3
	drop table rdb.dbo.RDB_UI_METADATA_TEXT_FINAL

IF OBJECT_ID('rdb.dbo.text_data_VR', 'U') IS NOT NULL  --------------------------Step4
drop table rdb.dbo.text_data_VR
IF OBJECT_ID('rdb.dbo.Text_data_VR_out', 'U') IS NOT NULL  
drop table rdb.dbo.Text_data_VR_out--------------------------------------------Step5

IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_CODED', 'U') IS NOT NULL------------------Step6  
 drop table rdb.dbo.RDB_UI_METADATA_CODED ;
IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_CODED_FINAL', 'U') IS NOT NULL--------------Step6  
drop table rdb.dbo.RDB_UI_METADATA_CODED_FINAL;

IF OBJECT_ID('rdb.dbo.CODED_TABLE_VR', 'U') IS NOT NULL  -------------------------Step7
 drop table rdb.dbo.CODED_TABLE_VR;
IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNM', 'U') IS NOT NULL  -----------------------Step8
 drop table rdb.dbo.CODED_TABLE_SNM;

IF OBJECT_ID('rdb.dbo.CODED_TABLE_NONSNM', 'U') IS NOT NULL  --------------------Step9
  drop table rdb.dbo.CODED_TABLE_NONSNM;
IF OBJECT_ID('rdb.dbo.Combined_TABLE_NONSNM_SNM', 'U') IS NOT NULL  ---added 9/9/2021
  drop table rdb.dbo.Combined_TABLE_NONSNM_SNM;


IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_NUMERIC', 'U') IS NOT NULL ----------------Step10 
drop table  rdb.dbo.RDB_UI_METADATA_NUMERIC;
IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP', 'U') IS NOT NULL ---------------------Step11 
 drop table  rdb.dbo.CODED_TABLE_SNTEMP;
 
IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS', 'U') IS NOT NULL  --------------Step12
drop table  rdb.dbo.CODED_TABLE_SNTEMP_TRANS;
IF OBJECT_ID('rdb.dbo.CODED_TABLE_VR1', 'U') IS NOT NULL ------------------------Step12 
drop table  rdb.dbo.CODED_TABLE_VR1;

IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_TEMP', 'U') IS NOT NULL  ------------------Step13
drop table rdb.dbo.CODED_TABLE_DESC_TEMP ;

IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC', 'U') IS NOT NULL ------------------------Step13 
drop table rdb.dbo.CODED_TABLE_DESC ;
										
IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE', 'U') IS NOT NULL  ---------------------Step14
drop table rdb.dbo.CODED_COUNTY_TABLE  ;
IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_TEMP', 'U') IS NOT NULL ------------Step14 
drop table rdb.dbo.CODED_COUNTY_TABLE_DESC_TEMP ;
IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC', 'U') IS NOT NULL  ---------------Step14
drop table rdb.dbo.CODED_COUNTY_TABLE_DESC ;

 IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTH', 'U') IS NOT NULL  ----------------------Step15
drop table rdb.dbo.CODED_TABLE_OTH ;
IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED', 'U') IS NOT NULL ----------------------Step15 
drop table rdb.dbo.CODED_TABLE_MERGED

IF OBJECT_ID('rdb.dbo.CODED_DATA_OUT', 'U') IS NOT NULL---------------------------Step16
drop table rdb.dbo.CODED_DATA_OUT

IF OBJECT_ID('rdb.dbo.rdb_ui_metadata_DATE', 'U') IS NOT NULL  -------------------Step17
drop table rdb.dbo.rdb_ui_metadata_DATE
												  
IF OBJECT_ID('RDB.dbo.RDB_UI_METADATA_DateFinal', 'U') IS NOT NULL  -------------Step17
drop table rdb.dbo.RDB_UI_METADATA_DateFinal;

IF OBJECT_ID('rdb.dbo.DATE_DATA_VR', 'U') IS NOT NULL ---------------------------Step18 
drop table rdb.dbo.DATE_DATA_VR;

IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_VR', 'U') IS NOT NULL  --------------------Step19
drop table rdb.dbo.PAGE_DATE_TABLE_VR;
IF OBJECT_ID('rdb.dbo.DATE_DATA_VR_out', 'U') IS NOT NULL  ----------------------Step19
drop table   rdb.dbo.DATE_DATA_VR_out;

IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_Numeric_all', 'U') IS NOT NULL ------------Step20 
drop table rdb.dbo.RDB_UI_METADATA_Numeric_all ;
IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_NumericFinal', 'U') IS NOT NULL  ----------Step20
drop table rdb.dbo.RDB_UI_METADATA_NumericFinal;

IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_VR', 'U') IS NOT NULL--------------------Step21  
drop table rdb.dbo.NUMERIC_BASE_DATA_VR ;
IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_VR', 'U') IS NOT NULL ---------------------Step22 
drop table NUMERIC_DATA_2_VR ;
IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_VR', 'U') IS NOT NULL  ---------------Step23
drop table rdb.dbo.NUMERIC_DATA_MERGED_VR;
IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_VR', 'U') IS NOT NULL  ----------------Step24
drop table rdb.dbo.NUMERIC_DATA_TRANS_VR
 IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_out', 'U') IS NOT NULL  ----------------------Step25
drop table rdb.dbo.NUMERIC_DATA_out;

IF OBJECT_ID('rdb.dbo.tmp_NUMERIC_DATA_out', 'U') IS NOT NULL  -----------------Step26
drop table rdb.dbo.tmp_NUMERIC_DATA_out;

IF OBJECT_ID('rdb.dbo.tmp_DATE_DATA_VR_out', 'U') IS NOT NULL  -----------------Step26
drop table   rdb.dbo.tmp_DATE_DATA_VR_out;
												
IF OBJECT_ID('rdb.dbo.tmp_CODED_DATA_OUT', 'U') IS NOT NULL  -------------------Step26
drop table rdb.dbo.tmp_CODED_DATA_OUT;
												
IF OBJECT_ID('rdb.dbo.tmp_Text_data_VR_out', 'U') IS NOT NULL  -----------------Step26
drop table rdb.dbo.tmp_Text_data_VR_out;

/*IF OBJECT_ID('rdb.dbo.[S_VACCINATION]', 'U') IS NOT NULL  -----------------------Step27---commented on 8-23-2021
 drop table rdb.[dbo].[S_VACCINATION];*/
IF OBJECT_ID('rdb.dbo.[TMP_S_VACCINATION]', 'U') IS NOT NULL---------------------Step27  
drop table rdb.[dbo].[TMP_S_VACCINATION];
	
IF OBJECT_ID('rdb.dbo.TMP_L_VACCINATION_N', 'U') IS NOT NULL  -------------------Step28 
drop table rdb.dbo.TMP_L_VACCINATION_N ;
IF OBJECT_ID('rdb.dbo.TMP_L_VACCINATION_E', 'U') IS NOT NULL---------------------Step29  
DROP TABLE rdb.dbo.TMP_L_VACCINATION_E;
									
IF OBJECT_ID('rdb.dbo.TMP_PARTICIPATION_INIT', 'U') IS NOT NULL  ----------------Step32
DROP TABLE rdb.dbo.TMP_PARTICIPATION_INIT;

IF OBJECT_ID('rdb.dbo.TMP_PARTICIPATION_PAT', 'U') IS NOT NULL-------------------Step33  
DROP TABLE rdb.dbo.TMP_PARTICIPATION_PAT;
IF OBJECT_ID('rdb.dbo.TMP_PARTICIPATION_PRV', 'U') IS NOT NULL  ----------------Step33
DROP TABLE rdb.dbo.TMP_PARTICIPATION_PRV;

IF OBJECT_ID('rdb.dbo.TMP_PARTICIPATION_ORG', 'U') IS NOT NULL -----------------Step34 
DROP TABLE rdb.dbo.TMP_PARTICIPATION_ORG;
IF OBJECT_ID('rdb.dbo.TMP_VACCINATION_FACT_INIT', 'U') IS NOT NULL --------------Step35 
DROP TABLE rdb.dbo.TMP_VACCINATION_FACT_INIT;

IF OBJECT_ID('rdb.dbo.TMP_VACCINATION_PHC_COLL', 'U') IS NOT NULL --------------Step36 
DROP TABLE rdb.dbo.TMP_VACCINATION_PHC_COLL;
IF OBJECT_ID('rdb.dbo.PHC_INVESTIGATION_KEY', 'U') IS NOT NULL  ----------------Step36
 DROP TABLE rdb.dbo.PHC_INVESTIGATION_KEY;
/*
IF OBJECT_ID('rdb.dbo.F_VACCINATION ', 'U') IS NOT NULL -------------------------Step37 
DROP TABLE rdb.dbo.F_VACCINATION ;*/
----------------------------------------------------------------------------------------------------------------------------------

								BEGIN TRANSACTION ;
			
									SET @Proc_Step_no = 40;
									SET @Proc_Step_Name = 'SP_COMPLETE'; 


									INSERT INTO rdb.[dbo].[job_flow_log] 
											(
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
										   'VaccinationRecord'
										   ,'RDB.VaccinationRecord'
										   ,'COMPLETE'
										   ,@Proc_Step_no
										   ,@Proc_Step_name
										   ,@RowCount_no
										   );
		  
			
								COMMIT TRANSACTION;
					 END TRY
--------------------------------------------------------------------------------------------------------------------------------------------------------------
BEGIN CATCH
  
     
				IF @@TRANCOUNT > 0   ROLLBACK TRANSACTION;
 
				DECLARE @ErrorNumber INT = ERROR_NUMBER();
				DECLARE @ErrorLine INT = ERROR_LINE();
				DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
				DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
				DECLARE @ErrorState INT = ERROR_STATE();
			 
	
				INSERT INTO rdb.[dbo].[job_flow_log] 
					  (
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
					   ,'D_VACCINATION'
					   ,'RDB.D_VACCINATION'
					   ,'ERROR'
					   ,@Proc_Step_no
					   ,'ERROR - '+ @Proc_Step_name
					   , 'Step -' +CAST(@Proc_Step_no AS VARCHAR(3))+' -' +CAST(@ErrorMessage AS VARCHAR(500))
					   ,0
					   );
  

			return -1 ;

	END CATCH
	
END;

GO


