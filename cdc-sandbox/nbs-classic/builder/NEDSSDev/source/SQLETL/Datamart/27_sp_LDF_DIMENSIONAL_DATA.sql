/*
 This stored procedure creates and insert incremental data into staging table S_LDF_DIMENSIONAL_DATA, which used by following stored procedures,
 
 sp_LDF_GENERIC_DATAMART
 sp_LDF_HEPATITIS_DATAMART
 sp_LDF_BMIRD_DATAMART
 sp_LDF_TETANUS_DATAMART
 sp_LDF_FOODBORNE_DATAMART
 sp_LDF_MUMPS_DATAMART
 sp_LDF_VACCINE_PREVENT_DISEASES_DATAMART
 
 It has depedency on function RDB.dbo.NBS_Strings_Split
 
 */
USE [RDB]
GO

	IF OBJECT_ID('rdb.dbo.TMP_LDF_DATA', 'U') IS NOT NULL  
			 drop table rdb.dbo.TMP_LDF_DATA;

	IF OBJECT_ID('RDB.dbo.TMP_LDF_DATA_WITH_SOURCE', 'U') IS NOT NULL  
			 drop table rdb.dbo.TMP_LDF_DATA_WITH_SOURCE;

	IF OBJECT_ID('RDB.dbo.TMP_LDF_DATA_TRANSLATED_ROWS_NE', 'U') IS NOT NULL  
			 drop table RDB.dbo.TMP_LDF_DATA_TRANSLATED_ROWS_NE;

	IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_CODED_TRANSLATED', 'U') IS NOT NULL  
			drop table rdb.dbo.TMP_LDF_BASE_CODED_TRANSLATED;

	IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_CLINICAL_TRANSLATED', 'U') IS NOT NULL  
			 drop table rdb.dbo.TMP_LDF_BASE_CLINICAL_TRANSLATED;

	IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_STATE_TRANSLATED', 'U') IS NOT NULL  
			 drop table rdb.dbo.TMP_LDF_BASE_STATE_TRANSLATED;

	IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED', 'U') IS NOT NULL  
			 drop table rdb.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED;

	IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED_FINAL', 'U') IS NOT NULL  
			 drop table rdb.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED_FINAL;

	IF OBJECT_ID('rdb.dbo.TMP_LDF', 'U') IS NOT NULL  
			 drop table rdb.dbo.TMP_LDF; 

	IF OBJECT_ID('rdb.dbo.TMP_LDF_TRANSLATED_DATA', 'U') IS NOT NULL  
			 drop table rdb.dbo.TMP_LDF_TRANSLATED_DATA;

	IF OBJECT_ID('rdb.dbo.TMP_LDF_METADATA', 'U') IS NOT NULL  
			 drop table rdb.dbo.TMP_LDF_METADATA;

	IF OBJECT_ID('rdb.dbo.TMP_LDF_METADATA_N', 'U') IS NOT NULL  
			 drop table rdb.dbo.TMP_LDF_METADATA_N;
					 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF OBJECT_ID('dbo.sp_LDF_DIMENSIONAL_DATA', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_LDF_DIMENSIONAL_DATA;
GO

CREATE PROCEDURE [dbo].sp_LDF_DIMENSIONAL_DATA
  @batch_id BIGINT
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
		   @batch_id
           ,'S_LDF_DIMENSIONAL_DATA'
           ,'RDB.S_LDF_DIMENSIONAL_DATA'
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


		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'INCREMENTAL PROCESS';
		BEGIN TRANSACTION;

			SELECT @ROWCOUNT_NO = 0;
			
			IF OBJECT_ID ('LDF_BMIRD') IS NULL AND OBJECT_ID ('LDF_FOODBORNE') IS NULL AND OBJECT_ID ('LDF_GENERIC') IS NULL AND OBJECT_ID ('LDF_HEPATITIS') IS NULL AND OBJECT_ID ('LDF_MUMPS') IS NULL  AND OBJECT_ID ('LDF_TETANUS') IS NULL  AND OBJECT_ID ('LDF_VACCINE_PREVENT_DISEASES') IS NULL 
			BEGIN
				SET @Proc_Step_Name = 'DELETING DATA FROM LDF_DATAMART_COLUMN_REF AND D_LDF_META_DATA TABLES, FULL REFRESH PROCESS FOR LDF DIMENSIONS';
				
				SET @batch_start_time='01/01/1999';
				DELETE FROM RDB.dbo.D_LDF_META_DATA;
				
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;
			END

		    INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  
				
		COMMIT TRANSACTION;
	
		BEGIN TRANSACTION;
		
		
		SET @PROC_STEP_NO = 3;
		SET @PROC_STEP_NAME = ' GENERATING TMP_LDF_DATA'; 

		-- Create table ldf_data
		IF OBJECT_ID('rdb.dbo.TMP_LDF_DATA', 'U') IS NOT NULL  
				drop table rdb.dbo.TMP_LDF_DATA;

				SELECT 
					a.ldf_uid, 
					a.active_ind, 
					a.business_object_nm, 
					a.cdc_national_id, 
					a.class_cd, 
					a.code_set_nm, 
					a.condition_cd, 
					a.label_txt, 
					a.state_cd, 
					a.custom_subform_metadata_uid, 
					b.business_object_uid, 
					b.ldf_value,
					page_set.code_short_desc_txt 'page_set',
					phc.cd 'phc_cd',
					a.data_type, a.Field_size, codeset.class_cd 'data_source'
				INTO RDB.dbo.TMP_LDF_DATA
				FROM NBS_ODSE.dbo.STATE_DEFINED_FIELD_METADATA a WITH ( NOLOCK)
				INNER JOIN
					NBS_ODSE.dbo.STATE_DEFINED_FIELD_DATA b WITH ( NOLOCK)
				ON 
					a.ldf_uid = b.ldf_uid
				INNER JOIN
					NBS_SRTE.dbo.LDF_PAGE_SET page_set WITH ( NOLOCK)
				ON 
					page_set.ldf_page_id =a.ldf_page_id
				INNER JOIN
					NBS_ODSE.dbo.PUBLIC_HEALTH_CASE phc WITH ( NOLOCK)
				ON 
					phc.public_health_case_uid=b.business_object_uid
				INNER JOIN NBS_ODSE.DBO.PARTICIPATION WITH(NOLOCK) 
					ON PARTICIPATION.ACT_UID = phc.public_health_case_uid AND PARTICIPATION.TYPE_CD = 'SUBJOFPHC'
                INNER JOIN NBS_ODSE.DBO.PERSON WITH(NOLOCK) 
					ON PARTICIPATION.SUBJECT_ENTITY_UID = PERSON.PERSON_UID
				inner join RDB.dbo.LDF_DATAMART_TABLE_REF WITH ( NOLOCK) 
					on phc.cd= LDF_DATAMART_TABLE_REF.condition_cd
				LEFT OUTER JOIN  
					NBS_SRTE.dbo.codeset codeset  WITH ( NOLOCK)
				ON 
					a.code_set_nm=codeset.code_set_nm
				WHERE a.business_object_nm IN ('PHC','BMD','NIP', 'HEP')
				AND a.data_type IN ('ST', 'CV','LIST_ST')
				--AND phc.cd IN (SELECT condition_cd FROM RDB.dbo.LDF_DATAMART_TABLE_REF WITH ( NOLOCK))
				AND (b.add_time >= @batch_start_time OR PERSON.LAST_CHG_TIME>=@batch_start_time)	AND (b.add_time <  @batch_end_time OR PERSON.LAST_CHG_TIME<  @batch_end_time);

      		    
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		    INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 4;
			SET @PROC_STEP_NAME = 'D_LDF_META_DATA'; 
			
		

				-- Incrementally populate D_LDF_META_DATA
				-- newly added LDF
				INSERT INTO RDB.dbo.D_LDF_META_DATA
				SELECT     
					sdfm.ldf_uid, 
					sdfm.active_ind, 
					sdfm.business_object_nm, 
					sdfm.cdc_national_id, 
					sdfm.class_cd, 
					sdfm.code_set_nm, 
					sdfm.condition_cd, 
					LTRIM(RTRIM(sdfm.label_txt)) 'label_txt', 
					sdfm.state_cd, 
					sdfm.custom_subform_metadata_uid, 
					page_set.code_short_desc_txt 'page_set',
					sdfm.data_type, 
					sdfm.Field_size,
					Cast (null as  [varchar](50))  as 'LDF_PAGE_SET'  
				FROM          
					NBS_ODSE.dbo.state_defined_field_metadata sdfm WITH ( NOLOCK)
				LEFT OUTER JOIN
					NBS_SRTE.dbo.ldf_page_set page_set WITH ( NOLOCK)
				ON 
					page_set.ldf_page_id = sdfm.ldf_page_id
				WHERE sdfm.business_object_nm IN ('PHC','BMD','NIP', 'HEP')
				AND sdfm.data_type IN ('ST', 'CV', 'LIST_ST')
				AND (sdfm.condition_cd IN (SELECT condition_cd FROM RDB.dbo.ldf_datamart_table_ref WITH ( NOLOCK))
				OR sdfm.condition_cd IS NULL )
				AND sdfm.add_time >= @batch_start_time	AND sdfm.add_time <  @batch_end_time			-- Added for incremental process
				ORDER BY sdfm.business_object_nm;

				-- updated LDF
				MERGE INTO RDB.dbo.D_LDF_META_DATA a
				   USING NBS_ODSE.dbo.state_defined_field_metadata b
					  ON a.ldf_uid = b.ldf_uid
					  AND b.record_status_time >= @batch_start_time	AND b.record_status_time <  @batch_end_time
				WHEN MATCHED THEN
				   UPDATE 
					  SET a.active_ind = b.active_ind,
					  a.business_object_nm=b.business_object_nm,
					  a.cdc_national_id=b.cdc_national_id,
					  a.class_cd=b.class_cd,
					  a.code_set_nm=b.code_set_nm,
					  a.condition_cd=b.condition_cd,
					  a.label_txt=LTRIM(RTRIM(b.label_txt)),
					  a.state_cd=b.state_cd,
					  a.custom_subform_metadata_uid=b.custom_subform_metadata_uid,
					  a.data_type=b.data_type,
					  a.field_size=b.field_size;

				UPDATE rdb.dbo.D_LDF_META_DATA SET  LDF_PAGE_SET = 'BMIRD'   WHERE  business_object_nm= 'BMD' AND LEN(RTRIM(ISNULL(condition_cd, 0)))<2 ;
				UPDATE rdb.dbo.D_LDF_META_DATA SET  LDF_PAGE_SET = 'VPD'   WHERE  business_object_nm= 'NIP' AND (LEN(RTRIM(ISNULL(condition_cd, 0)))<2);
				UPDATE rdb.dbo.D_LDF_META_DATA SET  LDF_PAGE_SET = 'OTHER'   WHERE  business_object_nm= 'PHC' AND LEN(RTRIM(ISNULL(condition_cd, 0)))<2 ;
				UPDATE rdb.dbo.D_LDF_META_DATA SET  LDF_PAGE_SET = 'HEP'   WHERE  business_object_nm= 'HEP' AND LEN(RTRIM(ISNULL(condition_cd, 0)))<2 ;
				    
						     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
	
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 5;
			SET @PROC_STEP_NAME = ' GENERATING TMP_LDF_DATA_TRANSLATED_ROWS_NE'; 

			---- data ldf_data_translated;
			---- ldf_data_translated_rows_ne
			---- line 92 to 160
				
		IF OBJECT_ID('RDB.dbo.TMP_LDF_DATA_TRANSLATED_ROWS_NE', 'U') IS NOT NULL  
				 drop table RDB.dbo.TMP_LDF_DATA_TRANSLATED_ROWS_NE;

				SELECT  data_source,
						business_object_uid, 
						ldf_uid,
						code_set_nm,
						label_txt,
						phc_cd,
						splitted_ldf.item COL1
				INTO RDB.dbo.TMP_LDF_DATA_TRANSLATED_ROWS_NE
				FROM RDB.dbo.TMP_LDF_DATA f OUTER APPLY 
				rdb.[dbo].[NBS_Strings_Split] (f.LDF_VALUE, '|') splitted_ldf
				WHERE DATALENGTH(splitted_ldf.item) > 0
				ORDER BY business_object_uid, ldf_uid, code_set_nm, data_source; 
				
				
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
		
			--- line 161, create table ldf_base_coded_translated as 
			
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 6;
			SET @PROC_STEP_NAME = ' GENERATING TMP_LDF_BASE_CODED_TRANSLATED'; 

		IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_CODED_TRANSLATED', 'U') IS NOT NULL  
				drop table rdb.dbo.TMP_LDF_BASE_CODED_TRANSLATED;

				SELECT 	
					col1,   
					LTRIM(RTRIM(cvg.code_desc_txt)) as code_short_desc_txt, 
					ldf.code_set_nm,
					business_object_uid, 
					ldf_uid,
					data_source,
					phc_cd,
					label_txt
					
				INTO RDB.dbo.TMP_LDF_BASE_CODED_TRANSLATED
				FROM
					RDB.dbo.TMP_LDF_DATA_TRANSLATED_ROWS_NE ldf
				LEFT JOIN
					nbs_srte.dbo.code_value_general cvg
				ON
					cvg.code_set_nm=ldf.code_set_nm
				AND 
					cvg.code=ldf.col1
				AND 
					ldf.data_source='code_value_general'
				ORDER BY
					business_object_uid asc, ldf_uid asc, col1 COLLATE Latin1_General_bin asc;

				
				--- col1= code_short_desc_txt;
				CREATE NONCLUSTERED INDEX [IDX_LDF_DATA_01] ON [dbo].[TMP_LDF_BASE_CODED_TRANSLATED] ([code_short_desc_txt])
				UPDATE RDB.dbo.TMP_LDF_BASE_CODED_TRANSLATED
				SET col1 = code_short_desc_txt
				WHERE code_short_desc_txt is not null AND DATALENGTH(code_short_desc_txt)>0;
		
		    SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		    INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			---- create table ldf_base_clinical_translated
			
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 7;
			SET @PROC_STEP_NAME = ' GENERATING TMP_LDF_BASE_CLINICAL_TRANSLATED'; 

		IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_CLINICAL_TRANSLATED', 'U') IS NOT NULL  
				 drop table rdb.dbo.TMP_LDF_BASE_CLINICAL_TRANSLATED;

				
				SELECT 	
					col1, 
					cvg.code_desc_txt as code_short_desc_txt, 
					ldf.code_set_nm,
					business_object_uid, 
					data_source,
					label_txt,
					phc_cd,
					ldf_uid
				INTO RDB.dbo.TMP_LDF_BASE_CLINICAL_TRANSLATED
				FROM	
					RDB.dbo.TMP_LDF_BASE_CODED_TRANSLATED ldf
					LEFT JOIN NBS_SRTE.dbo.Code_value_clinical cvg
					ON cvg.code_set_nm=ldf.code_set_nm
					AND cvg.code=ldf.col1
					AND ldf.data_source='code_value_clinical'
				ORDER BY business_object_uid asc, ldf_uid asc, col1 COLLATE Latin1_General_bin asc;
				
				
				----  col1= code_short_desc_txt, line 233
				
				UPDATE RDB.dbo.TMP_LDF_BASE_CLINICAL_TRANSLATED
				SET col1 = code_short_desc_txt
				WHERE code_short_desc_txt is not null AND DATALENGTH(code_short_desc_txt)>0;
		
					     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			-- create table ldf_base_state_translated as
			
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 8;
			SET @PROC_STEP_NAME = ' GENERATING TMP_LDF_BASE_STATE_TRANSLATED'; 

		IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_STATE_TRANSLATED', 'U') IS NOT NULL  
				 drop table rdb.dbo.TMP_LDF_BASE_STATE_TRANSLATED;

			
				SELECT 	
					col1,  
					business_object_uid, 
					cvg.code_desc_txt AS code_short_desc_txt, 
					ldf_uid,
					ldf.code_set_nm,
					label_txt,
					phc_cd,
					data_source
				INTO RDB.dbo.TMP_LDF_BASE_STATE_TRANSLATED
				FROM	
					RDB.dbo.TMP_LDF_BASE_CLINICAL_TRANSLATED ldf
					LEFT OUTER JOIN nbs_srte.dbo.v_state_code cvg
				ON 
					cvg.code_set_nm=ldf.code_set_nm
					AND cvg.state_cd=ldf.col1
					AND ldf.data_source 
				IN ('STATE_CCD', 'V_state_code')
				ORDER BY 
					business_object_uid asc, ldf_uid asc, col1 COLLATE Latin1_General_bin asc;
				
				
				--- col1= code_short_desc_txt, line 268
				CREATE NONCLUSTERED INDEX [IDX_LDF_DATA_02] ON [dbo].[TMP_LDF_BASE_STATE_TRANSLATED] ([code_short_desc_txt])
				UPDATE RDB.dbo.TMP_LDF_BASE_STATE_TRANSLATED
				SET col1 = code_short_desc_txt
				WHERE code_short_desc_txt is not null AND DATALENGTH(code_short_desc_txt)>0;
		
		
					     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			---------create table ldf_base_country_translated as 
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 9;
			SET @PROC_STEP_NAME = ' GENERATING TMP_LDF_BASE_COUNTRY_TRANSLATED'; 

		IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED', 'U') IS NOT NULL  
				 drop table rdb.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED;

		
		
				SELECT
					col1,  
					business_object_uid, 
					cvg.code_desc_txt as code_short_desc_txt, 
					ldf_uid,
					ldf.code_set_nm,
					label_txt,
					phc_cd,
					data_source
					INTO RDB.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED
					FROM
						RDB.dbo.TMP_LDF_BASE_STATE_TRANSLATED ldf
						LEFT OUTER JOIN NBS_SRTE.dbo.country_code cvg
					ON cvg.code_set_nm=ldf.code_set_nm
					AND cvg.code=ldf.col1
					AND ldf.data_source in ('COUNTRY_CODE')
				ORDER BY 
						business_object_uid asc, ldf_uid asc, col1 COLLATE Latin1_General_bin asc;
				
				
				--- col1= code_short_desc_txt, line 301
				CREATE NONCLUSTERED INDEX [IDX_LDF_DATA_03] ON [dbo].[TMP_LDF_BASE_COUNTRY_TRANSLATED] ([code_short_desc_txt])
				UPDATE RDB.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED
				SET col1 = code_short_desc_txt
				WHERE code_short_desc_txt is not null AND DATALENGTH(code_short_desc_txt)>0;
				
				
				--- proc sort data= ldf_base_country_translated; by business_object_uid ldf_uid;  line 306

					     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			--- data  ldf_base_country_translated; length x $4000; length col1 $4000; 
			--- line 308 to 316

			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 10;
			SET @PROC_STEP_NAME = ' GENERATING TMP_LDF_BASE_COUNTRY_TRANSLATED_FINAL'; 

		IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED_FINAL', 'U') IS NOT NULL  
				 drop table rdb.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED_FINAL;

				SELECT DISTINCT 
			   business_object_uid, 
			   code_short_desc_txt, 
			   ldf_uid, 
			   code_set_nm, 
			   label_txt, 
			   phc_cd,
			   data_source, 
			   LTRIM(STUFF(
		(
			SELECT '| ' + Col1
			FROM TMP_LDF_BASE_COUNTRY_TRANSLATED a WITH(NOLOCK)
			WHERE a.business_object_uid = b.business_object_uid
				  AND a.ldf_uid = b.ldf_uid FOR XML PATH(''), TYPE
		).value('.', 'VARCHAR(MAX)'), 1, 2, '')) Col1
		INTO RDB.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED_FINAL
		FROM TMP_LDF_BASE_COUNTRY_TRANSLATED b WITH(NOLOCK)
		GROUP BY business_object_uid, 
				 code_short_desc_txt, 
				 ldf_uid, 
				 code_set_nm, 
				 label_txt, 
				 phc_cd,
				 data_source
		ORDER BY label_txt, 
				 ldf_uid;
	
				--- sort data= ldf_base_country_translated; by label_txt  cdc_national_id ldf_uid class_cd; 
					     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			--- create table ldf
			
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 11;
			SET @PROC_STEP_NAME = ' GENERATING TMP_LDF'; 

		IF OBJECT_ID('rdb.dbo.TMP_LDF', 'U') IS NOT NULL  
				 drop table rdb.dbo.TMP_LDF;

		CREATE NONCLUSTERED INDEX [IDX_LDF_DATA_04]
ON [dbo].[TMP_LDF_BASE_COUNTRY_TRANSLATED_FINAL] ([ldf_uid])
INCLUDE ([Col1],[business_object_uid],[code_short_desc_txt],[code_set_nm],[label_txt],[data_source])
				
				SELECT lbct.*,
					ldcr.DATAMART_COLUMN_NM,
					ldcr.LDF_DATAMART_COLUMN_REF_UID,
					ldcr.LDF_LABEL,
					ldcr.LDF_PAGE_SET
				INTO RDB.dbo.TMP_LDF
				FROM RDB.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED_FINAL lbct,
				RDB.dbo.ldf_datamart_column_ref ldcr
				WHERE lbct.ldf_uid= ldcr.ldf_uid;
				
					     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			--- create table ldf_translated_data as
			
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 12;
			SET @PROC_STEP_NAME = ' GENERATING TMP_LDF_TRANSLATED_DATA'; 

		IF OBJECT_ID('rdb.dbo.TMP_LDF_TRANSLATED_DATA', 'U') IS NOT NULL  
				 drop table rdb.dbo.TMP_LDF_TRANSLATED_DATA;

		
				
				SELECT 
					col1,  
					a.business_object_uid, 
					a.code_short_desc_txt, 
					a.code_set_nm,
					a.data_source,
					a.phc_cd,
					b.cdc_national_id,
					b.business_object_nm,
					b.condition_cd,
					b.custom_subform_metadata_uid,
					b.page_set,
					b.ldf_uid,
					b.label_txt,
					LDF_PAGE_SET,
					B.data_type, 
					B.Field_size
				INTO RDB.dbo.TMP_LDF_TRANSLATED_DATA 
				FROM 
					RDB.dbo.D_LDF_META_DATA b RIGHT OUTER JOIN RDB.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED_FINAL a  
				ON a.ldf_uid= b.ldf_uid
				AND b.ldf_uid IS NOT NULL
				ORDER BY
					label_txt,page_set, business_object_uid, ldf_uid, code_set_nm,  data_source;
				
					
					     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			----- create table ldf_metadata as 
			
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 13;
			SET @PROC_STEP_NAME = ' GENERATING TMP_LDF_METADATA'; 

		IF OBJECT_ID('rdb.dbo.TMP_LDF_METADATA', 'U') IS NOT NULL  
				 drop table rdb.dbo.TMP_LDF_METADATA;

		
				SELECT
					ldf_uid, 
					label_txt, 
					cdc_national_id, 
					condition_cd, 
					class_cd,
					custom_subform_metadata_uid,
					LDF_PAGE_SET, 
					data_type,
					Field_size
				INTO RDB.dbo.TMP_LDF_METADATA
				FROM RDB.dbo.D_LDF_META_DATA
				ORDER BY cdc_national_id, ldf_uid, label_txt;
						
		    			     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			---- create table ldf_metadata_n as 
			
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 14;
			SET @PROC_STEP_NAME = ' GENERATING TMP_LDF_METADATA_N'; 

		IF OBJECT_ID('rdb.dbo.TMP_LDF_METADATA_N', 'U') IS NOT NULL  
				 drop table rdb.dbo.TMP_LDF_METADATA_N;

		
				SELECT distinct
					ISNULL(a.cdc_national_id,'') 'cdc_national_id',
					SUBSTRING(label_txt, 1, 50) 'ldf_label',
					a.ldf_uid,
					ISNULL(a.condition_cd,'') 'condition_cd',
					a.class_cd,
					custom_subform_metadata_uid,
					LDF_PAGE_SET
				INTO RDB.dbo.TMP_LDF_METADATA_N
				FROM RDB.dbo.D_LDF_META_DATA a
				WHERE ldf_uid NOT IN (SELECT ldf_uid FROM RDB.dbo.ldf_datamart_column_ref);

					     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			--- TODO from line 383 to 465
			
			
			-------- %macro assign_nozero_key (ds, key);
			
			ALTER TABLE RDB.dbo.TMP_LDF_METADATA_N
			ADD ldf_datamart_column_ref_uid BIGINT;
	
			ALTER TABLE RDB.dbo.TMP_LDF_METADATA_N
			ADD datamart_column_nm  varchar(350);

			DECLARE @i As bigint
			SET @i = (SELECT CASE WHEN COUNT(1) > 0 
				THEN 
					Min(REF.LDF_DATAMART_COLUMN_REF_UID) 
				ELSE 
					1 
				END
			FROM rdb.dbo.LDF_DATAMART_COLUMN_REF REF);

			UPDATE RDB.dbo.TMP_LDF_METADATA_N
			SET @i= LDF_DATAMART_COLUMN_REF_UID  = @i+1;
		
		
			--- CREATE table data ldf_metadata_new;
			
			UPDATE RDB.dbo.TMP_LDF_METADATA_N
			SET  datamart_column_nm  =  (Rtrim(Replace(ldf_label , ' ','_')));
								

			Update RDB.dbo.TMP_LDF_METADATA_N
			SET	   datamart_column_nm =  
				Rtrim(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace
																(datamart_column_nm , '/','_') 
																					, ',','_')
																					, '@','_')
																					, '\','_')
																					, '%','_')
																					, '[','_')
																					, ']','_')
																					, '#','_')
																					, ';','_')
																					, '$','_')
																					, '-','_')
																					, '.','_')
																					, '>','_')
																					, '<','_')
																					, '=','_')
																					, ':','_')
																					, '?','_')	
																					, '(','_')
																					, ')','_')
																					, '{','_')
																					, '}','_')
																					, '"','_')
																					, '&','_')
																					, '*','_')
																					, '+','_')
																					, '!','_')
																					, '`','_')
																					, '''','_'));

			/*
			DECLARE @len bigint
			DECLARE @lencdcnatid bigint
			DECLARE @lenLDFid bigint
			DECLARE @lenCSid bigint
			DECLARE @ldfchar varchar(8)

			select @len = DATALENGTH(REPLACE(datamart_column_nm,' ','')) from   RDB.dbo.TMP_LDF_METADATA_N;

			select @lencdcnatid = DATALENGTH(REPLACE(cdc_national_id, ' ','')) from	RDB.dbo.TMP_LDF_METADATA_N;
				   
			select @lenCSid = DATALENGTH(REPLACE(cast(custom_subform_metadata_uid as varchar (max)), ' ','')) from	RDB.dbo.TMP_LDF_METADATA_N;

			select @ldfchar = REPLACE(ldf_uid, ' ','') from	RDB.dbo.TMP_LDF_METADATA_N;
			*/


			UPDATE RDB.dbo.TMP_LDF_METADATA_N
			SET datamart_column_nm =
						case when class_cd = 'State' then  ('L_' + rtrim(REPLACE(ldf_uid, ' ','')) + rtrim(datamart_column_nm) ) 
						 when (DATALENGTH(REPLACE(cdc_national_id, ' ',''))>1 and (DATALENGTH(REPLACE(cdc_national_id, ' ',''))+ DATALENGTH(REPLACE(datamart_column_nm,' ','')))>0 and DATALENGTH(REPLACE(cast(custom_subform_metadata_uid as varchar (max)), ' ',''))>1 ) then ('C_' + cdc_national_id + datamart_column_nm )
						 when  class_cd='CDC' then rtrim(ltrim('C_'+ rtrim((REPLACE(ldf_uid, ' ',''))))+ datamart_column_nm) end;

			UPDATE RDB.dbo.TMP_LDF_METADATA_N
			SET datamart_column_nm = SUBSTRING(datamart_column_nm, 1, 29);
			
			--- did not create TMP_LDF_METADATA_NEW table, using TMP_LDF_METADATA_N table
			---- %dbload (ldf_datamart_column_ref, ldf_metadata_new);

			INSERT INTO RDB.dbo.LDF_DATAMART_COLUMN_REF (LDF_DATAMART_COLUMN_REF_UID, CONDITION_CD, LDF_LABEL, DATAMART_COLUMN_NM, LDF_UID, CDC_NATIONAL_ID, LDF_PAGE_SET)
			SELECT 
				LDF_DATAMART_COLUMN_REF_UID, 
				CONDITION_CD, 
				LTRIM(RTRIM(LDF_LABEL)) 'LDF_LABEL', 
				DATAMART_COLUMN_NM, 
				LDF_UID, 
				CDC_NATIONAL_ID, 
				LDF_PAGE_SET
			FROM 
				RDB.dbo.TMP_LDF_METADATA_N

			Update RDB.dbo.LDF_DATAMART_COLUMN_REF
					SET DATAMART_COLUMN_NM =  Rtrim(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace(Replace
																(DATAMART_COLUMN_NM , '/','_') 
																					, ',','_')
																					, '@','_')
																					, '\','_')
																					, '%','_')
																					, '[','_')
																					, ']','_')
																					, '#','_')
																					, ';','_')
																					, '$','_')
																					, '-','_')
																					, '.','_')
																					, '>','_')
																					, '<','_')
																					, '=','_')
																					, ':','_')
																					, '?','_')	
																					, '(','_')
																					, ')','_')
																					, '{','_')
																					, '}','_')
																					, '"','_')
																					, '&','_')
																					, '*','_')
																					, '+','_')
																					, '!','_')
																					, '`','_')
																					, '''','_'));
			---- Incrementally update table ldf_dimensional_data as
			
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 15;
			SET @PROC_STEP_NAME = ' GENERATING LDF_DIMENSIONAL_DATA'; 

					-- S_LDF_DIMENSIONAL_DATA is staging incremental table.
					
					IF OBJECT_ID('RDB.dbo.S_LDF_DIMENSIONAL_DATA', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.S_LDF_DIMENSIONAL_DATA;
				 
					SELECT 
						col1,  
						business_object_uid 'INVESTIGATION_UID', 
						dim.code_short_desc_txt, 
						dim.ldf_uid,
						dim.code_set_nm,
						dim.label_txt,
						dim.data_source,
						dim.cdc_national_id,
						dim.business_object_nm,
						dim.condition_cd,
						dim.custom_subform_metadata_uid,
						dim.page_set,
						ref.datamart_column_nm 'datamart_column_nm1',
						ref2.datamart_column_nm 'datamart_column_nm2',
						dim.phc_cd,
						data_type, 
						Field_size,
						ref.datamart_column_nm 'datamart_column_nm'
					INTO RDB.dbo.S_LDF_DIMENSIONAL_DATA
					FROM 
						RDB.dbo.TMP_ldf_translated_data dim 
					LEFT OUTER JOIN
						RDB.dbo.ldf_datamart_column_ref  ref 
					ON 
						dim.ldf_uid  =ref.ldf_uid
					LEFT OUTER JOIN
						RDB.dbo.ldf_datamart_column_ref  ref2 
					ON
						dim.cdc_national_id  =ref2.cdc_national_id
					AND 
						ref2.ldf_uid is null
					AND 
						ref.cdc_national_id is null

						group by 

						col1,  
						business_object_uid , 
						dim.code_short_desc_txt, 
						dim.ldf_uid,
						dim.code_set_nm,
						dim.label_txt,
						dim.data_source,
						dim.cdc_national_id,
						dim.business_object_nm,
						dim.condition_cd,
						dim.custom_subform_metadata_uid,
						dim.page_set,
						ref.datamart_column_nm ,
						ref2.datamart_column_nm ,
						dim.phc_cd,
						data_type, 
						Field_size,
						ref.datamart_column_nm
					ORDER BY business_object_uid;

					SELECT @ROWCOUNT_NO = @@ROWCOUNT;

					UPDATE RDB.dbo.S_LDF_DIMENSIONAL_DATA
					SET datamart_column_nm = datamart_column_nm2
					WHERE DATALENGTH(datamart_column_nm1)<2;

					UPDATE RDB.dbo.S_LDF_DIMENSIONAL_DATA
					SET datamart_column_nm = datamart_column_nm1
					WHERE DATALENGTH(datamart_column_nm1)>=2;


					CREATE NONCLUSTERED INDEX [IDX_LDF_DATA_05]
ON [dbo].[S_LDF_DIMENSIONAL_DATA] ([phc_cd])

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'S_LDF_DIMENSIONAL_DATA','RDB.S_LDF_DIMENSIONAL_DATA','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
		
			--- DELETE Temp tables.
			
			IF OBJECT_ID('rdb.dbo.TMP_LDF_DATA', 'U') IS NOT NULL  
					 drop table rdb.dbo.TMP_LDF_DATA;

			IF OBJECT_ID('RDB.dbo.TMP_LDF_DATA_WITH_SOURCE', 'U') IS NOT NULL  
					 drop table rdb.dbo.TMP_LDF_DATA_WITH_SOURCE;

			IF OBJECT_ID('RDB.dbo.TMP_LDF_DATA_TRANSLATED_ROWS_NE', 'U') IS NOT NULL  
					 drop table RDB.dbo.TMP_LDF_DATA_TRANSLATED_ROWS_NE;

			IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_CODED_TRANSLATED', 'U') IS NOT NULL  
					drop table rdb.dbo.TMP_LDF_BASE_CODED_TRANSLATED;

			IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_CLINICAL_TRANSLATED', 'U') IS NOT NULL  
					 drop table rdb.dbo.TMP_LDF_BASE_CLINICAL_TRANSLATED;

			IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_STATE_TRANSLATED', 'U') IS NOT NULL  
					 drop table rdb.dbo.TMP_LDF_BASE_STATE_TRANSLATED;

			IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED', 'U') IS NOT NULL  
					 drop table rdb.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED;

			IF OBJECT_ID('rdb.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED_FINAL', 'U') IS NOT NULL  
					 drop table rdb.dbo.TMP_LDF_BASE_COUNTRY_TRANSLATED_FINAL;

			IF OBJECT_ID('rdb.dbo.TMP_LDF', 'U') IS NOT NULL  
					 drop table rdb.dbo.TMP_LDF; 

			IF OBJECT_ID('rdb.dbo.TMP_LDF_TRANSLATED_DATA', 'U') IS NOT NULL  
					 drop table rdb.dbo.TMP_LDF_TRANSLATED_DATA;

			IF OBJECT_ID('rdb.dbo.TMP_LDF_METADATA', 'U') IS NOT NULL  
					 drop table rdb.dbo.TMP_LDF_METADATA;

			IF OBJECT_ID('rdb.dbo.TMP_LDF_METADATA_N', 'U') IS NOT NULL  
					 drop table rdb.dbo.TMP_LDF_METADATA_N;




    BEGIN TRANSACTION ;
	
	SET @Proc_Step_no = 16;
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
           'S_LDF_DIMENSIONAL_DATA'
           ,'RDB.S_LDF_DIMENSIONAL_DATA'
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
           ,'S_LDF_DIMENSIONAL_DATA'
           ,'RDB.S_LDF_DIMENSIONAL_DATA'
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