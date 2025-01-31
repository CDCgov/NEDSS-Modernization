
--drop table rdb.dbo.TMP_F_INV_CLINICAL;
  
--go
USE [RDB];
 
GO
SET ANSI_NULLS ON;
 
GO
SET QUOTED_IDENTIFIER ON;
 
GO
IF OBJECT_ID('rdb.[dbo].[sp_HEPATITIS_DATAMART]', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE [dbo].[sp_HEPATITIS_DATAMART];
 
END;
 
GO

CREATE  PROCEDURE [dbo].[sp_HEPATITIS_DATAMART] 
				 @batch_id bigint
AS
BEGIN
	-----EXEC  sp_HEPATITIS_DATAMART 12345
	--Delete from rdb.[dbo].[job_flow_log] where batch_id =12345
	--Select * from rdb.[dbo].[job_flow_log] where batch_id =12345
	BEGIN TRY



    -- DECLARE @batch_id BIGINT = 9999;




	declare @sql nvarchar(100);
 
	DECLARE @RowCount_no int;
 
	DECLARE @Proc_Step_no float= 0;
 
	DECLARE @Proc_Step_Name varchar(200)= '';
 
	DECLARE @batch_start_time datetime2(7)= NULL;
 
	DECLARE @batch_end_time datetime2(7)= NULL;
 
	DECLARE @COUNT_PB_HEP AS int;
 
	DECLARE @date_last_run datetime2(7)= NULL;
 
 
	       BEGIN TRANSACTION;
 
			SET @Proc_Step_no = 1;
 
			SET @Proc_Step_Name = 'SP_Start';
 
 
			INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, ---------------@batch_id 
			[Dataflow_Name], --------------'Hepatitis_Case_DATAMART' 
			[package_Name], --------------'RDB.Hepatitis_Case_DATAMART' 
			[Status_Type], ---------------START 
			[step_number], ---------------@Proc_Step_no 
			[step_name], ------------------@Proc_Step_Name=sp_start 
			[row_count] --------------------0
			)
			VALUES( @batch_id, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );
 
			COMMIT TRANSACTION;
 
			SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
			FROM rdb.[dbo].[job_batch_log]
			WHERE status_type = 'start';
 


	
	SET @COUNT_PB_HEP =
	(
		SELECT COUNT(*)
		FROM NBS_ODSE.[dbo].[NBS_ui_metadata] WITH(NOLOCK)
		WHERE investigation_form_cd IN
		(
			SELECT investigation_form_cd
			FROM NBS_SRTE.[dbo].[Condition_code] WITH(NOLOCK)
			WHERE CONDITION_CD  IN( '10110', '10104', '10100', '10106', '10101', '10102', '10103', '10105', '10481', '50248', '999999' )
			
			
		)
	);
 
		
		IF(@COUNT_PB_HEP <= 0)
		BEGIN
			select  'No data';
			--COMMIT TRANSACTION;
		    return;
 
		END;
 

			---------------------------------------------------------------------2 Create Table HEPATITIS_DATAMART_LAST-----------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  HEPATITIS_DATAMART_LAST ';
 
			SET @Proc_Step_no = 2;
 
			IF OBJECT_ID('rdb.dbo.HEPATITIS_DATAMART_LAST', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.HEPATITIS_DATAMART_LAST;
 
			END;
 
			CREATE TABLE rdb.dbo.HEPATITIS_DATAMART_LAST
			( 
						 date_last_ran date, START_DATE date
			);
 
			INSERT INTO rdb.dbo.HEPATITIS_DATAMART_LAST( date_last_ran, START_DATE )
			VALUES( '01jun1900', '01jun1900' );
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			UPDATE rdb.dbo.HEPATITIS_DATAMART_LAST
			  SET date_last_ran =
			(
				SELECT CAST(MAX([REFRESH_DATETIME]) AS date)
				FROM rdb.[dbo].[HEPATITIS_DATAMART]
			), START_DATE = GETDATE();
 
			UPDATE rdb.dbo.HEPATITIS_DATAMART_LAST
			  SET date_last_ran = '01jun1900'
			WHERE date_last_ran IS NULL;
 
			
			SET @date_last_run =
			(
				SELECT date_last_ran
				FROM rdb.dbo.HEPATITIS_DATAMART_LAST
			);
 
			COMMIT TRANSACTION;
 
								
			-----------------------------------------------------------3. Create Table rdb.dbo.Update_Patient_Cases-------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating Update_Patient_Cases';
 
			SET @Proc_Step_no = 3;
 
			IF OBJECT_ID('rdb.dbo.Update_Patient_Cases', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.Update_Patient_Cases;
 
			END;
 
			SELECT P.PATIENT_LAST_CHANGE_TIME
			INTO rdb.dbo.Update_Patient_Cases
			FROM rdb.[dbo].[D_PATIENT] AS p WITH(NOLOCK), rdb.[dbo].[F_PAGE_CASE] WITH(NOLOCK)
			WHERE P.PATIENT_KEY = F_PAGE_CASE.PATIENT_KEY AND 
				  p.PATIENT_LAST_CHANGE_TIME > @date_last_run;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 

			-----------------------------------------------------------4. Create Table rdb.dbo.Updated_Hep_Patient-------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating Updated_Hep_Patient';
 
			SET @Proc_Step_no = 4;
 
			
			IF OBJECT_ID('rdb.dbo.Updated_Hep_Patient', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.Updated_Hep_Patient;
 
			END;
 
			SELECT DISTINCT 
				   P.PATIENT_UID
			INTO rdb.dbo.Updated_Hep_Patient
			FROM rdb.dbo.D_PATIENT AS p WITH(NOLOCK), RDB.dbo.HEPATITIS_DATAMART WITH(NOLOCK)
			WHERE P.PATIENT_UID = HEPATITIS_DATAMART.PATIENT_UID AND 
				  PATIENT_LAST_CHANGE_TIME > @date_last_run;
 

			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 

			-----------------------------------------------------------5. Create Table rdb.dbo.Updated_Hep_PHYSICIAN-------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating Updated_Hep_PHYSICIAN';
 
			SET @Proc_Step_no = 5;
 
			IF OBJECT_ID('rdb.dbo.Updated_Hep_PHYSICIAN', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.Updated_Hep_PHYSICIAN;
 
			END;
 
			SELECT DISTINCT 
				   P.PROVIDER_UID AS PHYSICIAN_UID
			INTO rdb.dbo.Updated_Hep_PHYSICIAN
			FROM rdb.[dbo].[D_PROVIDER] AS p WITH(NOLOCK), rdb.[dbo].[HEPATITIS_DATAMART] WITH(NOLOCK)
			WHERE P.PROVIDER_UID = HEPATITIS_DATAMART.PHYSICIAN_UID AND 
				  CAST(PROVIDER_LAST_CHANGE_TIME AS date) > @date_last_run ;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 

			--------------------------------------------------------6. Create Table rdb.dbo.Updated_Hep_INVESTIGATOR-------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating rdb.dbo.Updated_Hep_INVESTIGATOR';
 
			SET @Proc_Step_no = 6;
 
			IF OBJECT_ID('rdb.dbo.Updated_Hep_INVESTIGATOR', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.Updated_Hep_INVESTIGATOR;
 
			END;
 
			SELECT DISTINCT 
				   p.PROVIDER_UID AS INVESTIGATOR_UID
			INTO rdb.dbo.Updated_Hep_INVESTIGATOR
			FROM rdb.[dbo].[D_PROVIDER] AS p WITH(NOLOCK), rdb.[dbo].[HEPATITIS_DATAMART] WITH(NOLOCK)
			WHERE P.PROVIDER_UID = HEPATITIS_DATAMART.INVESTIGATOR_UID AND 
				  PROVIDER_LAST_CHANGE_TIME > @date_last_run ;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			-----------------------------------------------------------7. Create Table rdb.dbo.Updated_Hep_REPORTING-------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.Updated_Hep_REPORTING';
 
			SET @Proc_Step_no = 7;
 
			IF OBJECT_ID('rdb.dbo.Updated_Hep_REPORTING', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.Updated_Hep_REPORTING;
 
			END;
 
			-----modified below on 9/29/2021
			/*SELECT P.ORGANIZATION_UID AS REPORTING_SOURCE_UID, ORGANIZATION_NAME, ORGANIZATION_COUNTY_CODE, ORGANIZATION_COUNTY, ORGANIZATION_CITY
			INTO rdb.dbo.Updated_Hep_REPORTING
			FROM rdb.[dbo].[D_ORGANIZATION] AS p WITH(NOLOCK), rdb.[dbo].[HEPATITIS_DATAMART] WITH(NOLOCK)
			WHERE P.ORGANIZATION_UID = HEPATITIS_DATAMART.REPORTING_SOURCE_UID AND 
				  CAST(p.ORGANIZATION_LAST_CHANGE_TIME AS date) > @date_last_run ;
 
*/
			SELECT P.ORGANIZATION_UID AS REPORTING_SOURCE_UID, ORGANIZATION_NAME, ORGANIZATION_COUNTY_CODE, ORGANIZATION_COUNTY, ORGANIZATION_CITY
			INTO rdb.dbo.Updated_Hep_REPORTING
			FROM rdb.[dbo].[D_ORGANIZATION] AS p WITH(NOLOCK), rdb.[dbo].[HEPATITIS_DATAMART] WITH(NOLOCK)
			WHERE P.ORGANIZATION_UID = HEPATITIS_DATAMART.REPORTING_SOURCE_UID AND 
				  ORGANIZATION_LAST_CHANGE_TIME > @date_last_run ;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 

			---------------------------------------------------------------------------------------------------------------------------------------------
			--XX
			BEGIN TRANSACTION;
				DELETE FROM rdb.dbo.HEPATITIS_DATAMART
				WHERE PATIENT_UID IN
				(
					SELECT DISTINCT 
						   PATIENT_UID
					FROM rdb.dbo.updated_hep_PATIENT
				);
 
				DELETE FROM rdb.dbo.HEPATITIS_DATAMART
				WHERE PHYSICIAN_UID IN
				(
					SELECT DISTINCT 
						   PHYSICIAN_UID
					FROM rdb.dbo.updated_hep_PHYSICIAN
				);
 
				DELETE FROM rdb.dbo.HEPATITIS_DATAMART
				WHERE INVESTIGATOR_UID IN
				(
					SELECT DISTINCT 
						   INVESTIGATOR_UID
					FROM rdb.dbo.updated_hep_INVESTIGATOR
				);
 
				DELETE FROM rdb.dbo.HEPATITIS_DATAMART
				WHERE REPORTING_SOURCE_UID IN
				(
					SELECT DISTINCT 
						   REPORTING_SOURCE_UID
					FROM rdb.dbo.updated_hep_REPORTING
				);
 
			COMMIT TRANSACTION;

 
			-----------------------------------------------------------8. Create Table rdb.dbo.EXISTING_HEPATITIS_DATAMART-------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.EXISTING_HEPATITIS_DATAMART';
 
			SET @Proc_Step_no = 8;
 
			IF OBJECT_ID('rdb.dbo.EXISTING_HEPATITIS_DATAMART', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.EXISTING_HEPATITIS_DATAMART;
 
			END;
 
			SELECT DISTINCT 
				   investigation.investigation_key
			INTO rdb.dbo.EXISTING_HEPATITIS_DATAMART
			FROM rdb.dbo.HEPATITIS_DATAMART WITH(NOLOCK), rdb.dbo.INVESTIGATION WITH(NOLOCK)
			WHERE rdb.dbo.HEPATITIS_DATAMART.[CASE_UID] = INVESTIGATION.[CASE_UID];
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 

			-----------------------------------------------------------9. Create Table rdb.dbo.TMP_CONDITION---------------------------------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_CONDITION';
 
			SET @Proc_Step_no = 9;
 
			IF OBJECT_ID('rdb.dbo.TMP_CONDITION', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_CONDITION;
 
			END;
 
			SELECT CONDITION_CD, CONDITION_DESC, DISEASE_GRP_DESC, CONDITION_KEY
			INTO rdb.dbo.TMP_CONDITION
			FROM rdb.[dbo].[CONDITION] WITH(NOLOCK)
			WHERE CONDITION_CD IN( '10110', '10104', '10100', '10106', '10101', '10102', '10103', '10105', '10481', '50248', '999999' );
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 

			---------------------------------------------------------------------10. CREATE TABLE rdb.dbo.TMP_F_PAGE_CASE-------------------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_F_PAGE_CASE';
 
			SET @Proc_Step_no = 10;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_PAGE_CASE', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_PAGE_CASE;
 
			END;
 
			SELECT F_PAGE_CASE.INVESTIGATION_KEY, T.CONDITION_KEY, F_PAGE_CASE.PATIENT_KEY
			INTO rdb..TMP_F_PAGE_CASE
			FROM rdb.[dbo].[F_PAGE_CASE] WITH(NOLOCK)---Original table 
				 INNER JOIN	 rdb.dbo.TMP_CONDITION AS T WITH(NOLOCK) ON F_PAGE_CASE.CONDITION_KEY = T.CONDITION_KEY  ------(my table comdition)
				 INNER JOIN	 rdb.[dbo].[D_PATIENT] WITH(NOLOCK)		 ON F_PAGE_CASE.PATIENT_KEY = D_PATIENT.PATIENT_KEY
				 INNER JOIN	 rdb.dbo.INVESTIGATION WITH(NOLOCK)		 ON INVESTIGATION.INVESTIGATION_KEY = F_PAGE_CASE.INVESTIGATION_KEY
				 LEFT JOIN	 rdb.dbo.EXISTING_HEPATITIS_DATAMART WITH(NOLOCK) ON F_PAGE_CASE.INVESTIGATION_KEY = EXISTING_HEPATITIS_DATAMART.INVESTIGATION_KEY
			WHERE EXISTING_HEPATITIS_DATAMART.INVESTIGATION_KEY IS NULL AND 
				  INVESTIGATION.RECORD_STATUS_CD = 'ACTIVE'
			ORDER BY F_PAGE_CASE.INVESTIGATION_KEY;
 

			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 

			---------------------------------------------------------------------11. CREATE TABLE rdb.dbo.TMP_D_INV_ADMINISTRATIVE
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_D_INV_ADMINISTRATIVE';
 
			SET @Proc_Step_no = 11;
 
			
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_ADMINISTRATIVE', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_INV_ADMINISTRATIVE;
 
			END;
 

			SELECT page_case.D_INV_ADMINISTRATIVE_KEY, page_case.INVESTIGATION_KEY
			INTO rdb.dbo.TMP_F_INV_ADMINISTRATIVE
			FROM rdb.dbo.F_PAGE_CASE AS page_case WITH(NOLOCK) ----Original table
				 INNER JOIN	 rdb.dbo.TMP_F_PAGE_CASE AS T
				 ON T.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY  ---(My Table)--Should it be F-Page or tmp_F_Page
				 ORDER BY D_INV_ADMINISTRATIVE_KEY;
 

				 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_ADMINISTRATIVE', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_ADMINISTRATIVE;
 
			END;
 

         -- CREATE TABLE D_INV_ADMINISTRATIVE AS  
		SELECT F.D_INV_ADMINISTRATIVE_KEY, F.INVESTIGATION_KEY AS ADMIN_INV_KEY, 
			ADM_INNC_NOTIFICATION_DT AS INIT_NND_NOT_DT, ADM_FIRST_RPT_TO_PHD_DT AS FIRST_RPT_PHD_DT, 
			ADM_BINATIONAL_RPTNG_CRIT AS BINATIONAL_RPTNG_CRIT
			INTO rdb.dbo.TMP_D_INV_ADMINISTRATIVE
			FROM rdb.dbo.TMP_F_INV_ADMINISTRATIVE AS F
				 LEFT JOIN	 rdb.dbo.D_INV_ADMINISTRATIVE AS D WITH(NOLOCK) ON F.D_INV_ADMINISTRATIVE_KEY = D.D_INV_ADMINISTRATIVE_KEY
				 ;
 


			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			-----------------------------------------------------------------------------12. CREATE TABLE TMP_D_INV_CLINICAL----------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_D_INV_CLINICAL';
 
			
			SET @Proc_Step_no = 12;
 
			
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_CLINICAL', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_INV_CLINICAL;
 
			END;
 
			
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_CLINICAL', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_CLINICAL;
 
			END;
 
			
			SELECT F_PAGE_CASE.D_INV_CLINICAL_KEY, F_PAGE_CASE.INVESTIGATION_KEY
			INTO rdb.dbo.TMP_F_INV_CLINICAL
			FROM rdb.dbo.F_PAGE_CASE WITH(NOLOCK)
				 INNER JOIN	 rdb..TMP_F_PAGE_CASE AS PAGE_CASE ON F_PAGE_CASE.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY ---(my Table)
				 ORDER BY D_INV_CLINICAL_KEY;
 
			
			
			SELECT F.D_INV_CLINICAL_KEY as D_INV_CLINICAL_KEY_1 , F.INVESTIGATION_KEY AS CLINICAL_INV_KEY, D.*,
			cast( null as varchar(4000)) as HEP_D_INFECTION_IND,
			cast( null as varchar(4000)) as HEP_MEDS_RECVD_IND
						--CAST(CLN_HepDInfection AS varchar(300)) AS HEP_D_INFECTION_IND, CAST(CLN_MedsforHep AS varchar(300)) AS HEP_MEDS_RECVD_IND
			INTO rdb.dbo.TMP_D_INV_CLINICAL
			FROM rdb.dbo.TMP_F_INV_CLINICAL AS F
				 LEFT JOIN [dbo].[D_INV_CLINICAL] AS D WITH(NOLOCK)  ON F.D_INV_CLINICAL_KEY = D.D_INV_CLINICAL_KEY
				 ORDER BY F.INVESTIGATION_KEY;
 

			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'CLN_HepDInfection'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_CLINICAL'))
			  BEGIN
				--declare @sql nvarchar(100)
			    SET @sql = N'update rdb.dbo.TMP_D_INV_CLINICAL set HEP_D_INFECTION_IND=trim(CLN_HepDInfection);'
			    execute sp_executesql  @sql
			  END;
 

			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'CLN_MedsforHep'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_CLINICAL'))
			  BEGIN
				--declare @sql nvarchar(100)
			    SET @sql = N'update rdb.dbo.TMP_D_INV_CLINICAL set HEP_MEDS_RECVD_IND=trim(CLN_MedsforHep);'
			    execute sp_executesql  @sql
			  END;
 

			/*UPDATE rdb.dbo.TMP_D_INV_CLINICAL
			  SET HEP_D_INFECTION_IND = ( CASE
										  WHEN HEP_D_INFECTION_IND IS NULL THEN NULL
										  ELSE RTRIM(LTRIM(HEP_D_INFECTION_IND))
										  END );
 
			UPDATE rdb.dbo.TMP_D_INV_CLINICAL
			  SET HEP_MEDS_RECVD_IND = ( CASE
										 WHEN HEP_MEDS_RECVD_IND IS NULL THEN NULL
										 ELSE RTRIM(LTRIM(HEP_MEDS_RECVD_IND))
										 END );
 
										 */
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			--------------------------------------------------------------------------------13. CREATE TABLE TMP_D_INV_PATIENT_OBS----------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_D_INV_PATIENT_OBS';
 
			SET @Proc_Step_no = 13;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_PATIENT_OBS', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_PATIENT_OBS;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_PATIENT_OBS', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_INV_PATIENT_OBS;
 
			END;
 

			SELECT F_PAGE_CASE.D_INV_PATIENT_OBS_KEY, F_PAGE_CASE.INVESTIGATION_KEY
			INTO rdb.dbo.TMP_F_INV_PATIENT_OBS
			FROM rdb.dbo.F_PAGE_CASE WITH(NOLOCK)
				 INNER JOIN  rdb..TMP_F_PAGE_CASE AS PAGE_CASE
				 ON F_PAGE_CASE.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY	---(my table)
				 ORDER BY D_INV_PATIENT_OBS_KEY;
 
			
			
			SELECT F.D_INV_PATIENT_OBS_KEY, F.INVESTIGATION_KEY AS PATIENT_OBS_INV_KEY, CAST(null AS varchar(300)) AS SEX_PREF
			INTO rdb.dbo.TMP_D_INV_PATIENT_OBS
			FROM rdb.dbo.TMP_F_INV_PATIENT_OBS AS F
				 LEFT JOIN
				 rdb.[dbo].[D_INV_PATIENT_OBS] AS D WITH(NOLOCK)
				 ON F.D_INV_PATIENT_OBS_KEY = D.D_INV_PATIENT_OBS_KEY
				 ORDER BY F.INVESTIGATION_KEY;
 

 	IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'IPO_SEXUAL_PREF'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_PATIENT_OBS'))
			  BEGIN
				--declare @sql nvarchar(100)
			    SET @sql = N'update rdb.dbo.TMP_D_INV_PATIENT_OBS set SEX_PREF=trim(IPO_SEXUAL_PREF);'
			    execute sp_executesql  @sql
			  END;
 

			/*UPDATE rdb.dbo.TMP_D_INV_PATIENT_OBS
			  SET SEX_PREF = ( CASE
							   WHEN SEX_PREF IS NULL THEN NULL
							   ELSE RTRIM(LTRIM(SEX_PREF))
							   END );
 
*/
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			--------------------------------------------------------------------------------14. CREATE TABLE TMP_D_INV_EPIDEMIOLOGY----------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_D_INV_EPIDEMIOLOGY';
 
			SET @Proc_Step_no = 14;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_INV_EPIDEMIOLOGY;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_EPIDEMIOLOGY;
 
			END;
 
			SELECT F_PAGE_CASE.[D_INV_EPIDEMIOLOGY_KEY], F_PAGE_CASE.[INVESTIGATION_KEY]
			INTO rdb.dbo.TMP_F_INV_EPIDEMIOLOGY
			FROM rdb.dbo.F_PAGE_CASE WITH(NOLOCK)
				 INNER JOIN rdb..TMP_F_PAGE_CASE AS PAGE_CASE
				 ON F_PAGE_CASE.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY	 ---(my table)
				 ORDER BY D_INV_EPIDEMIOLOGY_KEY;
 
			
			SELECT F.D_INV_EPIDEMIOLOGY_KEY as D_INV_EPIDEMIOLOGY_KEY1, F.INVESTIGATION_KEY AS EPIDEMIOLOGY_INV_KEY, D.*,
			CAST( null AS varchar(2000)) AS CHILDCARE_CASE_IND, ----1
			CAST( null  AS varchar(2000)) AS CNTRY_USUAL_RESIDENCE, --2
			CAST( null  AS varchar(2000)) AS CT_BABYSITTER_IND, ----3
			CAST( null  AS varchar(2000)) AS CT_CHILDCARE_IND, --4
			CAST( null  AS varchar(2000)) AS CT_HOUSEHOLD_IND, --5
			CAST( null  AS varchar(2000)) AS HEP_CONTACT_IND, ----6
			CAST( null  AS varchar(2000)) AS OTHER_CONTACT_IND, -----7
			CAST( null  AS varchar(2000)) AS CONTACT_TYPE_OTH, --8
			CAST( null  AS varchar(2000)) AS CT_PLAYMATE_IND, ----9
			CAST( null  AS varchar(2000)) AS SEXUAL_PARTNER_IND, ---10
			CAST( null  AS varchar(2000)) AS DNP_HOUSEHOLD_CT_IND, -----11
			CAST( null  AS varchar(2000)) AS HEP_A_EPLINK_IND, ----------12
			CAST( null  AS varchar(2000)) AS FEMALE_SEX_PRTNR_NBR, ------13
			CAST( null  AS varchar(2000)) AS FOODHNDLR_PRIOR_IND, ------14
			CAST( null  AS varchar(2000)) AS DNP_EMPLOYEE_IND, ----------15
			CAST( null  AS varchar(2000)) AS STREET_DRUG_INJECTED, ------16
			CAST( null  AS varchar(2000)) AS MALE_SEX_PRTNR_NBR, -------17
			--- EPI_OutbreakAssoc,	   ---OUTBREAK_IND-----18
			CAST( null  AS varchar(2000)) AS OBRK_FOODHNDLR_IND, -------19
			CAST( null  AS varchar(2000)) AS FOOD_OBRK_FOOD_ITEM, ------20
			CAST( null  AS varchar(2000)) AS OBRK_NOFOODHNDLR_IND, -----21
			CAST( null  AS varchar(2000)) AS OBRK_UNIDENTIFIED_IND, ----22
			CAST( null  AS varchar(2000)) AS OBRK_WATERBORNE_IND, -----23
			CAST( null  AS varchar(2000)) AS STREET_DRUG_USED, ----24
			CAST( null  AS varchar(2000)) AS COM_SRC_OUTBREAK_IND ----25
			INTO rdb.dbo.TMP_D_INV_EPIDEMIOLOGY
			FROM rdb.dbo.TMP_F_INV_EPIDEMIOLOGY AS F
				 LEFT JOIN
				 rdb.[dbo].[D_INV_EPIDEMIOLOGY] AS D WITH(NOLOCK)
				 ON F.D_INV_EPIDEMIOLOGY_KEY = D.D_INV_EPIDEMIOLOGY_KEY
				 ORDER BY F.INVESTIGATION_KEY;
 

--				declare @sql nvarchar(100)
				 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_OutbreakAssoc'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set COM_SRC_OUTBREAK_IND=trim(EPI_OutbreakAssoc);'
			    execute sp_executesql  @sql
			  END;
 

			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_ChildCareCase'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set CHILDCARE_CASE_IND = trim(EPI_ChildCareCase);'
			    execute sp_executesql  @sql
			  END;
 

			  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_CNTRY_USUAL_RESID'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set CNTRY_USUAL_RESIDENCE = trim(EPI_CNTRY_USUAL_RESID);'
			    execute sp_executesql  @sql
			  END;
 

			  
			  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_ContactBabysitter'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set CT_BABYSITTER_IND = trim(EPI_ContactBabysitter);'
			    execute sp_executesql  @sql
			  END;
 

			
			  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_ContactHousehold'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set CT_HOUSEHOLD_IND = trim(EPI_ContactHousehold);'
			    execute sp_executesql  @sql
			  END;
 

			  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_ContactOfCase'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set HEP_CONTACT_IND = trim(EPI_ContactOfCase);'
			    execute sp_executesql  @sql
			  END;
 

			  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_ContactOther'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set OTHER_CONTACT_IND = trim(EPI_ContactOther);'
			    execute sp_executesql  @sql
			  END;
 

			  		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_ContactOthSpecify'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set CONTACT_TYPE_OTH = trim(EPI_ContactOthSpecify);'
			    execute sp_executesql  @sql
			  END;
 

			  
			  		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_ContactPlaymate'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set CONTACT_TYPE_OTH = trim(EPI_ContactPlaymate);'
			    execute sp_executesql  @sql
			  END;
 

			  		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_ContactSexPartner'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set SEXUAL_PARTNER_IND = trim(EPI_ContactSexPartner);'
			    execute sp_executesql  @sql
			  END;
 

			    		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_FoodHandler'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set FOODHNDLR_PRIOR_IND = trim(EPI_FoodHandler);'
			    execute sp_executesql  @sql
			  END;
 

			    		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_InDayCare'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set DNP_EMPLOYEE_IND = trim(EPI_InDayCare);'
			    execute sp_executesql  @sql
			  END;
 

			      		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_IVDrugUse'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set STREET_DRUG_INJECTED = trim(EPI_IVDrugUse);'
			    execute sp_executesql  @sql
			  END;
 

			      		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_MaleSexPartner'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set MALE_SEX_PRTNR_NBR = trim(EPI_MaleSexPartner);'
			    execute sp_executesql  @sql
			  END;
 

			      		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_OutbreakFoodHndlr'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set OBRK_FOODHNDLR_IND = trim(EPI_OutbreakFoodHndlr);'
			    execute sp_executesql  @sql
			  END;
 

			      		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_OutbreakFoodItem'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set FOOD_OBRK_FOOD_ITEM = trim(EPI_OutbreakFoodItem);'
			    execute sp_executesql  @sql
			  END;
 

			        		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_outbreakNonFoodHndlr'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set OBRK_NOFOODHNDLR_IND = trim(EPI_outbreakNonFoodHndlr);'
			    execute sp_executesql  @sql
			  END;
 

        		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_OutbreakUnidentified'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set OBRK_UNIDENTIFIED_IND = trim(EPI_OutbreakUnidentified);'
			    execute sp_executesql  @sql
			  END;
 
    		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_OutbreakWaterborne'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set OBRK_WATERBORNE_IND = trim(EPI_OutbreakWaterborne);'
			    execute sp_executesql  @sql
			  END;
 
			  		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_RecDrugUse'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set STREET_DRUG_USED = trim(EPI_RecDrugUse);'
			    execute sp_executesql  @sql
			  END;
 
		  		  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'EPI_OutbreakAssoc'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_EPIDEMIOLOGY'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_EPIDEMIOLOGY set COM_SRC_OUTBREAK_IND = trim(EPI_OutbreakAssoc);'
			    execute sp_executesql  @sql
			  END;
 

			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			--------------------------------------------------------------------------------------15. CREATE TABLE rdb.dbo.D_INV_LAB_FINDING_TMP----------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_D_INV_LAB_FINDING';
 
			SET @Proc_Step_no = 15;
 
			
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_LAB_FINDING', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_INV_LAB_FINDING;
 
			END;
 
			
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_LAB_FINDING', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_LAB_FINDING;
 
			END;
 

			SELECT F_PAGE_CASE.[D_INV_LAB_FINDING_KEY], F_PAGE_CASE.[INVESTIGATION_KEY]
			INTO rdb.dbo.TMP_F_INV_LAB_FINDING
			FROM rdb.dbo.F_PAGE_CASE WITH(NOLOCK)
				 INNER JOIN
				 rdb..TMP_F_PAGE_CASE AS PAGE_CASE
				 ON F_PAGE_CASE.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY---(my table)
			;
 

			SELECT F.[D_INV_LAB_FINDING_KEY] as D_INV_LAB_FINDING_KEY1, F.[INVESTIGATION_KEY] AS LAB_INV_KEY,D.*,
			CAST( null  AS varchar(2000)) AS HEP_C_TOTAL_ANTIBODY, ---1
			CAST( null  AS date) AS SUPP_ANTI_HCV_DT, ------2
			CAST( null  AS varchar(2000)) AS ALT_SGPT_RESULT, ---3
			CAST( null  AS varchar(2000)) AS ANTI_HBS_POS_REAC_IND, ---4
			CAST( null  AS varchar(2000)) AS ANTI_HBSAG_TESTED_IND, ----5
			CAST( null  AS varchar(2000)) AS AST_SGOT_RESULT, ---6
			CAST( null  AS varchar(2000)) AS HEP_E_ANTIGEN, ---7
			CAST( null  AS date) AS HBE_AG_DT, ---8
			CAST( null  AS varchar(2000)) AS HEP_B_SURFACE_ANTIGEN, --9
			CAST( null  AS date) AS HBS_AG_DT, --10
			CAST( null  AS varchar(2000)) AS HEP_B_DNA, -----11
			CAST( null  AS date) AS HBV_NAT_DT, ---12
			CAST( null  AS varchar(2000)) AS HCV_RNA, ---13
			CAST( null  AS date) AS HCV_RNA_DT, ---14
			CAST( null  AS varchar(2000)) AS HEP_D_TEST_IND, -----15
			CAST( null  AS varchar(2000)) AS HEP_A_IGM_ANTIBODY, ---16
			CAST( null  AS date) AS IGM_ANTI_HAV_DT, ---17
			CAST( null  AS varchar(2000)) AS HEP_B_IGM_ANTIBODY, ---18
			CAST( null  AS date) AS IGM_ANTI_HBC_DT, ---19
			CAST( null  AS varchar(2000)) AS PREV_NEG_HEP_TEST_IND, --20
			CAST( null  AS varchar(2000)) AS ANTIHCV_SIGCUT_RATIO, ---21
			CAST( null  AS varchar(2000)) AS ANTIHCV_SUPP_ASSAY, ---22
			CAST( null  AS date) AS ALT_RESULT_DT, -----23
			CAST( null  AS date) AS AST_RESULT_DT, ---24
			CAST( null  AS varchar(2000)) AS ALT_SGPT_RSLT_UP_LMT, ---25
			CAST( null  AS varchar(2000)) AS AST_SGOT_RSLT_UP_LMT, ---26
			CAST( null  AS varchar(2000)) AS HEP_A_TOTAL_ANTIBODY, ---27
			CAST( null  AS date) AS TOTAL_ANTI_HAV_DT, ---28
			CAST( null  AS varchar(2000)) AS HEP_B_TOTAL_ANTIBODY, ---29
			CAST( null  AS date) AS TOTAL_ANTI_HBC_DT, ----30
			CAST( null  AS date) AS TOTAL_ANTI_HCV_DT, --31
			CAST( null  AS varchar(2000)) AS HEP_D_TOTAL_ANTIBODY, ---32
			CAST( null  AS date) AS TOTAL_ANTI_HDV_DT, ---33
			CAST( null  AS varchar(2000)) AS HEP_E_TOTAL_ANTIBODY, ---34
			CAST( null  AS date) AS TOTAL_ANTI_HEV_DT, ---35
			CAST( null  AS date) AS VERIFIED_TEST_DT ---36
			INTO rdb.dbo.TMP_D_INV_LAB_FINDING
			FROM rdb.dbo.TMP_F_INV_LAB_FINDING AS F
				 LEFT JOIN
				 rdb.[dbo].[D_INV_LAB_FINDING] AS D WITH(NOLOCK)
				 ON F.D_INV_LAB_FINDING_KEY = D.D_INV_LAB_FINDING_KEY
				 ;
 

				 	  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TotalAntiHCV'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HEP_C_TOTAL_ANTIBODY = trim(LAB_TotalAntiHCV);'
			    execute sp_executesql  @sql
			  END;
 

			 	  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_Supplem_antiHCV_Date'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set SUPP_ANTI_HCV_DT = (LAB_Supplem_antiHCV_Date);'
			    execute sp_executesql  @sql
			  END;
 
			  		 	  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_ALT_Result'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set ALT_SGPT_RESULT = trim(LAB_ALT_Result);'
			    execute sp_executesql  @sql
			  END;
 

			  		 	  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_AntiHBsPositive'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set ANTI_HBS_POS_REAC_IND = trim(LAB_AntiHBsPositive);'
			    execute sp_executesql  @sql
			  END;
 
			  		 	  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_AntiHBsTested'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set ANTI_HBSAG_TESTED_IND = trim(LAB_AntiHBsTested);'
			    execute sp_executesql  @sql
			  END;
 

			  		 	  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_AST_Result'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set AST_SGOT_RESULT = trim(LAB_AST_Result);'
			    execute sp_executesql  @sql
			  END;
 

			  		 	  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_HBeAg'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HEP_E_ANTIGEN = trim(LAB_HBeAg);'
			    execute sp_executesql  @sql
			  END;
 

			  		 	  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_HBeAg_Date'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HBE_AG_DT = (LAB_HBeAg_Date);'
			    execute sp_executesql  @sql
			  END;
 

			  		 	  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_HBsAg'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HEP_B_SURFACE_ANTIGEN = trim(LAB_HBsAg);'
			    execute sp_executesql  @sql
			  END;
 

			  		 	  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_HBsAg_Date'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HBS_AG_DT = (LAB_HBsAg_Date);'
			    execute sp_executesql  @sql
			  END;
 

			  		 	  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_HBV_NAT'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HEP_B_DNA = trim(LAB_HBV_NAT);'
			    execute sp_executesql  @sql
			  END;
 
	  		 	  			  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_HBV_NAT_Date'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HBV_NAT_DT = (LAB_HBV_NAT_Date);'
			    execute sp_executesql  @sql
			  END;
 

			  	  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_HCVRNA'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HCV_RNA = trim(LAB_HCVRNA);'
			    execute sp_executesql  @sql
			  END;
 

			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_HCVRNA_Date'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HCV_RNA_DT = (LAB_HCVRNA_Date);'
			    execute sp_executesql  @sql
			  END;
 
			  	  	 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_HepDTest'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HEP_D_TEST_IND = trim(LAB_HepDTest);'
			    execute sp_executesql  @sql
			  END;
 
			  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_IgM_AntiHAV'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HEP_A_IGM_ANTIBODY = trim(LAB_IgM_AntiHAV);'
			    execute sp_executesql  @sql
			  END;
 

			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_IgMAntiHAVDate'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set IGM_ANTI_HAV_DT = (LAB_IgMAntiHAVDate);'
			    execute sp_executesql  @sql
			  END;
 
			  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_IgMAntiHBc'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HEP_B_IGM_ANTIBODY = trim(LAB_IgMAntiHBc);'
			    execute sp_executesql  @sql
			  END;
 

			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_IgMAntiHBcDate'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set IGM_ANTI_HBC_DT = (LAB_IgMAntiHBcDate);'
			    execute sp_executesql  @sql
			  END;
 
			  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_PrevNegHepTest'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set PREV_NEG_HEP_TEST_IND = trim(LAB_PrevNegHepTest);'
			    execute sp_executesql  @sql
			  END;
 
	  	 
		 			  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_SignalToCutoff'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set ANTIHCV_SIGCUT_RATIO = trim(LAB_SignalToCutoff);'
			    execute sp_executesql  @sql
			  END;
 
	  	 
			 			  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_Supplem_antiHCV'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set ANTIHCV_SUPP_ASSAY = trim(LAB_Supplem_antiHCV);'
			    execute sp_executesql  @sql
			  END;
 
	 		 			  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TestDate'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set ALT_RESULT_DT = (LAB_TestDate);'
			    execute sp_executesql  @sql
			  END;
 
	 	 		 			  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TestDate2'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set AST_RESULT_DT = (LAB_TestDate2);'
			    execute sp_executesql  @sql
			  END;
 
	  		 			  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TestResultUpperLimit'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set ALT_SGPT_RSLT_UP_LMT = trim(LAB_TestResultUpperLimit);'
			    execute sp_executesql  @sql
			  END;
 
	  		 			  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TestResultUpperLimit2'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set AST_SGOT_RSLT_UP_LMT = trim(LAB_TestResultUpperLimit2);'
			    execute sp_executesql  @sql
			  END;
 
	 			  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TotalAntiHAV'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HEP_A_TOTAL_ANTIBODY = trim(LAB_TotalAntiHAV);'
			    execute sp_executesql  @sql
			  END;
 
			  	  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TotalAntiHAVDate'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set TOTAL_ANTI_HAV_DT = (LAB_TotalAntiHAVDate);'
			    execute sp_executesql  @sql
			  END;
 
			  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TotalAntiHBc'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HEP_B_TOTAL_ANTIBODY = trim(LAB_TotalAntiHBc);'
			    execute sp_executesql  @sql
			  END;
 
			  		  	  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TotalAntiHBcDate'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set TOTAL_ANTI_HBC_DT = (LAB_TotalAntiHBcDate);'
			    execute sp_executesql  @sql
			  END;
 
			  	  
  		  	  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TotalAntiHCV_Date'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set TOTAL_ANTI_HCV_DT = (LAB_TotalAntiHCV_Date);'
			    execute sp_executesql  @sql
			  END;
   		  	  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TotalAntiHDV'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HEP_D_TOTAL_ANTIBODY = trim(LAB_TotalAntiHDV);'
			    execute sp_executesql  @sql
			  END;
   		  	  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TotalAntiHDV_Date'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set TOTAL_ANTI_HDV_DT = (LAB_TotalAntiHDV_Date);'
			    execute sp_executesql  @sql
			  END;
   		  	  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TotalAntiHEV'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set HEP_E_TOTAL_ANTIBODY = trim(LAB_TotalAntiHEV);'
			    execute sp_executesql  @sql
			  END;
   		  	  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_TotalAntiHEV_Date'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set TOTAL_ANTI_HEV_DT = (LAB_TotalAntiHEV_Date);'
			    execute sp_executesql  @sql
			  END;
    		  	  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'LAB_VerifiedTestDate'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_LAB_FINDING'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_LAB_FINDING set VERIFIED_TEST_DT = (LAB_VerifiedTestDate);'
			    execute sp_executesql  @sql
			  END;
 
	 
	 /*
			CAST(LAB_TotalAntiHCV_Date AS date) AS TOTAL_ANTI_HCV_DT, --31
			CAST(LAB_TotalAntiHDV AS varchar(300)) AS HEP_D_TOTAL_ANTIBODY, ---32
			CAST(LAB_TotalAntiHDV_Date AS date) AS TOTAL_ANTI_HDV_DT, ---33
			CAST(LAB_TotalAntiHEV AS varchar(300)) AS HEP_E_TOTAL_ANTIBODY, ---34
			CAST(LAB_TotalAntiHEV_Date AS date) AS TOTAL_ANTI_HEV_DT, ---35
			CAST(LAB_VerifiedTestDate AS date) AS VERIFIED_TEST_DT ---36
			
			*/


/*	UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HEP_C_TOTAL_ANTIBODY  = Case when  HEP_C_TOTAL_ANTIBODY is null then null Else RTRIM(LTRIM(HEP_C_TOTAL_ANTIBODY)) end;
 
----1
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET ANTI_HBS_POS_REAC_IND = Case when  ANTI_HBS_POS_REAC_IND is null then null Else RTRIM(LTRIM(ANTI_HBS_POS_REAC_IND)) end;
 
----4
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET ANTI_HBSAG_TESTED_IND = Case when  ANTI_HBSAG_TESTED_IND is null then null Else RTRIM(LTRIM(ANTI_HBSAG_TESTED_IND)) end;
 
---5
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HEP_E_ANTIGEN  = Case when  HEP_E_ANTIGEN  is null then null Else RTRIM(LTRIM(HEP_E_ANTIGEN)) end;
 
----7
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HEP_B_SURFACE_ANTIGEN = Case when HEP_B_SURFACE_ANTIGEN   is null then null Else RTRIM(LTRIM(HEP_B_SURFACE_ANTIGEN )) end;
 
----9
																			----UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HBS_AG_DT = LAB_HBsAg_Date;
 
-----------------------10
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HEP_B_DNA   = Case when  HEP_B_DNA  is null then null Else RTRIM(LTRIM(HEP_B_DNA )) end;
 
---11
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HCV_RNA     = Case when  HCV_RNA    is null then null Else RTRIM(LTRIM(HCV_RNA)) end;
 
------13
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HEP_D_TEST_IND  = Case when  HEP_D_TEST_IND is null then null Else RTRIM(LTRIM(HEP_D_TEST_IND)) end;
 
----15
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HEP_A_IGM_ANTIBODY  = Case when HEP_A_IGM_ANTIBODY is null then null Else RTRIM(LTRIM(HEP_A_IGM_ANTIBODY)) end;
 
---16
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HEP_B_IGM_ANTIBODY  = Case when HEP_B_IGM_ANTIBODY is null then null Else RTRIM(LTRIM(HEP_B_IGM_ANTIBODY)) end;
 
----18
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET PREV_NEG_HEP_TEST_IND= Case when  PREV_NEG_HEP_TEST_IND is null then null Else RTRIM(LTRIM(PREV_NEG_HEP_TEST_IND)) end;
 
---20
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET ANTIHCV_SIGCUT_RATIO = Case when  ANTIHCV_SIGCUT_RATIO is null then null Else RTRIM(LTRIM(ANTIHCV_SIGCUT_RATIO)) end;
 
----21
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET ANTIHCV_SUPP_ASSAY   = Case when  ANTIHCV_SUPP_ASSAY  is null then null Else RTRIM(LTRIM(ANTIHCV_SUPP_ASSAY )) end;
 
----22
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HEP_A_TOTAL_ANTIBODY  = Case when  HEP_A_TOTAL_ANTIBODY is null then null Else RTRIM(LTRIM(HEP_A_TOTAL_ANTIBODY)) end;
 
----27
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HEP_B_TOTAL_ANTIBODY = Case when HEP_B_TOTAL_ANTIBODY  is null then null Else RTRIM(LTRIM(HEP_B_TOTAL_ANTIBODY )) end;
 
----29
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HEP_D_TOTAL_ANTIBODY  = Case when  HEP_D_TOTAL_ANTIBODY is null then null Else RTRIM(LTRIM(HEP_D_TOTAL_ANTIBODY)) end;
 
----32
																			UPDATE rdb.dbo.TMP_D_INV_LAB_FINDING SET HEP_E_TOTAL_ANTIBODY  = Case when  HEP_E_TOTAL_ANTIBODY is null then null Else RTRIM(LTRIM(HEP_E_TOTAL_ANTIBODY)) end;
 
----34
																		*/
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			---------------------------------------------------------------------------------------16. CREATE TABLE TMP_D_INV_MEDICAL_HISTORY----------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_D_INV_MEDICAL_HISTORY';
 
			SET @Proc_Step_no = 16;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_INV_MEDICAL_HISTORY;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_MEDICAL_HISTORY;
 
			END;
 
			SELECT F_PAGE_CASE.D_INV_MEDICAL_HISTORY_KEY, F_PAGE_CASE.[INVESTIGATION_KEY]
			INTO TMP_F_INV_MEDICAL_HISTORY
			FROM rdb.dbo.F_PAGE_CASE WITH(NOLOCK)
				 INNER JOIN
				 rdb..TMP_F_PAGE_CASE AS PAGE_CASE
				 ON F_PAGE_CASE.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY---(my table)
				 ORDER BY D_INV_MEDICAL_HISTORY_KEY;
 

			SELECT F.D_INV_MEDICAL_HISTORY_KEY, F.INVESTIGATION_KEY AS MEDHistory_INV_KEY, 
			CAST(MDH_DiabetesDxDate AS date) AS DIABETES_DX_DT, CAST(MDH_Diabetes AS [varchar](300)) AS DIABETES_IND, CAST(MDH_Jaundiced AS [varchar](300)) AS PAT_JUNDICED_IND, CAST(MDH_PrevAwareInfection AS [varchar](300)) AS PAT_PREV_AWARE_IND, CAST(MDH_ProviderOfCare AS [varchar](300)) AS HEP_CARE_PROVIDER, CAST(MDH_ReasonForTest AS [varchar](300)) AS TEST_REASON, CAST(MDH_ReasonForTestingOth AS [varchar](300)) AS TEST_REASON_OTH, CAST(MDH_Symptomatic AS [varchar](300)) AS SYMPTOMATIC_IND, CAST(MDH_DueDate AS date) AS PREGNANCY_DUE_DT
			INTO rdb.dbo.TMP_D_INV_MEDICAL_HISTORY
			FROM rdb.dbo.TMP_F_INV_MEDICAL_HISTORY AS F
				 LEFT JOIN
				 D_INV_MEDICAL_HISTORY AS D WITH(NOLOCK)
				 ON F.D_INV_MEDICAL_HISTORY_KEY = D.D_INV_MEDICAL_HISTORY_KEY
				 ORDER BY F.INVESTIGATION_KEY;
 

			--UPDATE rdb.dbo.TMP_D_INV_MEDICAL_HISTORY SET DIABETES_DX_DT = MDH_DiabetesDxDate	;
 
----1
																		/*UPDATE rdb.dbo.TMP_D_INV_MEDICAL_HISTORY SET DIABETES_IND  = CASE WHEN DIABETES_IND is null then null else RTRIM(LTRIM(DIABETES_IND)) END;
 
----2
																		UPDATE rdb.dbo.TMP_D_INV_MEDICAL_HISTORY SET PAT_JUNDICED_IND  = CASE WHEN PAT_JUNDICED_IND is null then null else RTRIM(LTRIM(PAT_JUNDICED_IND)) END;
 
---3														 
																		UPDATE rdb.dbo.TMP_D_INV_MEDICAL_HISTORY SET PAT_PREV_AWARE_IND = CASE WHEN PAT_PREV_AWARE_IND is null then null else RTRIM(LTRIM(PAT_PREV_AWARE_IND)) END;
 
---4														 
																		UPDATE rdb.dbo.TMP_D_INV_MEDICAL_HISTORY SET HEP_CARE_PROVIDER  = CASE WHEN HEP_CARE_PROVIDER  is null then null else RTRIM(LTRIM(HEP_CARE_PROVIDER)) END;
 
---5
	
																		UPDATE rdb.dbo.TMP_D_INV_MEDICAL_HISTORY SET TEST_REASON = CASE WHEN TEST_REASON is null then Null else RTRIM(LTRIM(TEST_REASON)) END;
 
---6
																		UPDATE rdb.dbo.TMP_D_INV_MEDICAL_HISTORY SET TEST_REASON_OTH = CASE WHEN TEST_REASON_OTH  is null then null else RTRIM(LTRIM( TEST_REASON_OTH)) END;
 
---7
																		UPDATE rdb.dbo.TMP_D_INV_MEDICAL_HISTORY SET SYMPTOMATIC_IND = CASE WHEN SYMPTOMATIC_IND is null  then null else RTRIM(LTRIM(SYMPTOMATIC_IND )) END;
 
---8
																		*/
			--UPDATE rdb.dbo.TMP_D_INV_MEDICAL_HISTORY SET PREGNANCY_DUE_DT=MDH_DueDate
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
	
			---------------------------------------------------------------------------------17. CREATE TABLE TMP.D_INV_MOTHER-------------------------------------------------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_D_INV_MOTHER';
 
			SET @Proc_Step_no = 17;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_MOTHER', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_INV_MOTHER;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_MOTHER', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_MOTHER;
 
			END;
 

			SELECT F_PAGE_CASE.D_INV_MOTHER_KEY, F_PAGE_CASE.INVESTIGATION_KEY
			INTO rdb.dbo.TMP_F_INV_MOTHER
			FROM rdb.dbo.F_PAGE_CASE WITH(NOLOCK)
				 INNER JOIN
				 TMP_F_PAGE_CASE AS PAGE_CASE
				 ON F_PAGE_CASE.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY---(my table)
				 ORDER BY D_INV_MOTHER_KEY;
 

				---changed it like this on 10/1/2021
				declare @SQL1 varchar(4000)
				set @SQL1='SELECT F.D_INV_MOTHER_KEY D_INV_MOTHER_KEY1, F.INVESTIGATION_KEY AS MOTHER_INV_KEY, D.*,
				CAST( null AS varchar(2000)) AS MTH_BORN_OUTSIDE_US, 
				CAST( null  AS varchar(2000)) AS MTH_ETHNICITY, 
				CAST( null  AS varchar(2000)) AS MTH_HBS_AG_PRIOR_POS, 
				CAST( null  AS varchar(2000)) AS MTH_POS_AFTER, 
				CAST( null  AS varchar(2000)) AS MTH_RACE,
				CAST( null AS varchar(2000)) AS MTH_BIRTH_COUNTRY, 
				CAST( null  AS date) AS MTH_POS_TEST_DT
			INTO rdb.dbo.TMP_D_INV_MOTHER
			FROM rdb.dbo.TMP_F_INV_MOTHER AS F
				 LEFT JOIN
				 [dbo].[D_INV_MOTHER] AS D WITH(NOLOCK)
				 ON F.D_INV_MOTHER_KEY = D.D_INV_MOTHER_KEY
				 ORDER BY F.INVESTIGATION_KEY';
 
				 
				  exec (@SQL1);
 

/*
				CAST(MTH_MotherBornOutsideUS AS varchar(300)) AS MTH_BORN_OUTSIDE_US, 
				CAST(MTH_MotherEthnicity AS varchar(300)) AS MTH_ETHNICITY, 
				CAST(MTH_MotherHBsAgPosPrior AS varchar(300)) AS MTH_HBS_AG_PRIOR_POS, 
				CAST(MTH_MotherPositiveAfter AS varchar(300)) AS MTH_POS_AFTER, 
				CAST(MTH_MotherRace AS varchar(300)) AS MTH_RACE, 
				CAST(MTH_MothersBirthCountry AS varchar(300)) AS MTH_BIRTH_COUNTRY, 
				CAST(MTH_MotherPosTestDate AS date) AS MTH_POS_TEST_DT
*/

	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'MTH_MotherBornOutsideUS'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_MOTHER'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_MOTHER set MTH_BORN_OUTSIDE_US = trim(MTH_MotherBornOutsideUS);'
			    execute sp_executesql  @sql
			  END;
 
			  		  	  
		  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'MTH_MotherEthnicity'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_MOTHER'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_MOTHER set MTH_ETHNICITY = trim(MTH_MotherEthnicity);'
			    execute sp_executesql  @sql
			  END;
 
			  		  	  
	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'MTH_MotherHBsAgPosPrior'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_MOTHER'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_MOTHER set MTH_HBS_AG_PRIOR_POS = trim(MTH_MotherHBsAgPosPrior);'
			    execute sp_executesql  @sql
			  END;
 
			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'MTH_MotherPositiveAfter'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_MOTHER'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_MOTHER set MTH_POS_AFTER = trim(MTH_MotherPositiveAfter);'
			    execute sp_executesql  @sql
			  END;
 
			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'MTH_MotherRace'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_MOTHER'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_MOTHER set MTH_RACE = trim(MTH_MotherRace);'
			    execute sp_executesql  @sql
			  END;
 
			  		  	  
			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'MTH_MothersBirthCountry'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_MOTHER'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_MOTHER set MTH_BIRTH_COUNTRY = trim(MTH_MothersBirthCountry);'
			    execute sp_executesql  @sql
			  END;
 
			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'MTH_MotherPosTestDate'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_MOTHER'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_MOTHER set MTH_POS_TEST_DT = (MTH_MotherPosTestDate);'
			    execute sp_executesql  @sql
			  END;
 

			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			----------------------------------------------------------------------------------18. CREATE TABLE TMP_D_INV_RISK_FACTOR---------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_D_INV_RISK_FACTOR';
 
			SET @Proc_Step_no = 18;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_RISK_FACTOR', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_INV_RISK_FACTOR;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_RISK_FACTOR', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_RISK_FACTOR;
 
			END;
 
			SELECT F_PAGE_CASE.D_INV_RISK_FACTOR_KEY, F_PAGE_CASE.INVESTIGATION_KEY
			INTO rdb.dbo.TMP_F_INV_RISK_FACTOR
			FROM rdb.dbo.F_PAGE_CASE WITH(NOLOCK)
				 INNER JOIN
				 TMP_F_PAGE_CASE AS PAGE_CASE
				 ON F_PAGE_CASE.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY	---(my table)
				 ORDER BY D_INV_RISK_FACTOR_KEY;
 
			SELECT F.D_INV_RISK_FACTOR_KEY D_INV_RISK_FACTOR_KEY1, F.INVESTIGATION_KEY AS RISK_INV_KEY, D.*,
			CAST( null AS varchar(300)) AS BLD_EXPOSURE_IND, ---1
			CAST( NULL  AS varchar(1000)) AS BLD_RECVD_IND, ----2
			CAST( null  AS date) AS BLD_RECVD_DT, ---3
			CAST( NULL  AS varchar(1000)) AS MED_DEN_BLD_CT_FRQ, ---4
			CAST( NULL  AS varchar(1000)) AS MED_DEN_EMP_EVER_IND, ---5
			CAST( NULL  AS varchar(1000)) AS MED_DEN_EMPLOYEE_IND, ---6
			CAST( NULL  AS varchar(1000)) AS CLOTFACTOR_PRIOR_1987, ----7
			CAST( NULL  AS varchar(1000)) AS BLD_CONTAM_IND, ----8
			CAST( NULL  AS varchar(1000)) AS DEN_WORK_OR_SURG_IND, ----9
			CAST( NULL  AS varchar(1000)) AS HEMODIALYSIS_IND, --10
			CAST( NULL  AS varchar(1000)) AS LT_HEMODIALYSIS_IND, --11
			CAST( NULL  AS varchar(1000)) AS HSPTL_PRIOR_ONSET_IND, --12
			CAST( NULL  AS varchar(1000)) AS EVER_INJCT_NOPRSC_DRG, ------13
			CAST( NULL  AS varchar(1000)) AS INCAR_24PLUSHRS_IND, ---14
			CAST( NULL  AS varchar(1000)) AS INCAR_6PLUS_MO_IND, ----15
			CAST( NULL  AS varchar(1000)) AS EVER_INCAR_IND, ---16
			CAST( NULL  AS varchar(1000)) AS INCAR_TYPE_JAIL_IND, -- -17
			CAST( NULL  AS varchar(1000)) AS INCAR_TYPE_PRISON_IND, ---18
			CAST( NULL  AS varchar(1000)) AS INCAR_TYPE_JUV_IND, ---19
			CAST( NULL  AS varchar(1000)) AS LAST6PLUSMO_INCAR_PER, ------20
			CAST( NULL  AS varchar(1000)) AS LAST6PLUSMO_INCAR_YR, ---21
			CAST( NULL  AS varchar(1000)) AS OUTPAT_IV_INF_IND, ---22
			CAST( NULL  AS varchar(1000)) AS LTCARE_RESIDENT_IND, ---23
			CAST( NULL  AS varchar(1000)) AS LIFE_SEX_PRTNR_NBR, ---24
			CAST( NULL  AS varchar(1000)) AS BLD_EXPOSURE_OTH, ---25
			CAST( NULL  AS varchar(1000)) AS PIERC_PRIOR_ONSET_IND, ---26
			CAST( NULL  AS varchar(1000)) AS PIERC_PERF_LOC_OTH, ----27                                                                                                            
			CAST( NULL  AS varchar(1000)) AS PIERC_PERF_LOC, -------28
			CAST( NULL  AS varchar(1000)) AS PUB_SAFETY_BLD_CT_FRQ, ---29
			CAST( NULL  AS varchar(1000)) AS PUB_SAFETY_WORKER_IND, ---30
			CAST( NULL  AS varchar(1000)) AS STD_TREATED_IND, ---31
			CAST( NULL  AS varchar(1000)) AS STD_LAST_TREATMENT_YR, ---32
			CAST( NULL  AS varchar(1000)) AS NON_ORAL_SURGERY_IND, -----33
			CAST( NULL  AS varchar(1000)) AS TATT_PRIOR_ONSET_IND, ----34
			CAST( NULL  AS varchar(1000)) AS TATTOO_PERF_LOC, ----35
			CAST( NULL  AS varchar(1000)) AS TATT_PRIOR_LOC_OTH, ---36
			CAST( NULL  AS varchar(1000)) AS BLD_TRANSF_PRIOR_1992, ---37
			CAST( NULL  AS varchar(1000)) AS ORGN_TRNSP_PRIOR_1992, ---38
			CAST( NULL  AS varchar(1000)) AS HEP_CONTACT_EVER_IND     ----39
			INTO rdb.dbo.TMP_D_INV_RISK_FACTOR
			FROM rdb.dbo.TMP_F_INV_RISK_FACTOR AS F
				 LEFT JOIN
				 [dbo].[D_INV_RISK_FACTOR] AS D WITH(NOLOCK)
				 ON F.D_INV_RISK_FACTOR_KEY = D.D_INV_RISK_FACTOR_KEY
				 ORDER BY RISK_INV_KEY;
 
 /*
 CAST(RSK_BloodExpOther AS varchar(300)) AS BLD_EXPOSURE_IND, ---1
			CAST(RSK_BloodTransfusion AS varchar(300)) AS BLD_RECVD_IND, ----2
			CAST(RSK_BloodTransfusionDate AS date) AS BLD_RECVD_DT, ---3
			CAST(RSK_BloodWorkerCnctFreq AS varchar(300)) AS MED_DEN_BLD_CT_FRQ, ---4
			CAST(RSK_BloodWorkerEver AS varchar(300)) AS MED_DEN_EMP_EVER_IND, ---5
			CAST(RSK_BloodWorkerOnset AS varchar(300)) AS MED_DEN_EMPLOYEE_IND, ---6
			CAST(RSK_ClottingPrior87 AS varchar(300)) AS CLOTFACTOR_PRIOR_1987, ----7
			CAST(RSK_ContaminatedStick AS varchar(300)) AS BLD_CONTAM_IND, ----8
			CAST(RSK_DentalOralSx AS varchar(300)) AS DEN_WORK_OR_SURG_IND, ----9
			CAST(RSK_HEMODIALYSIS_BEFORE_ONSET AS varchar(300)) AS HEMODIALYSIS_IND, --10
			CAST(RSK_HemodialysisLongTerm AS varchar(300)) AS LT_HEMODIALYSIS_IND, --11
			CAST(RSK_HospitalizedPrior AS varchar(300)) AS HSPTL_PRIOR_ONSET_IND, --12
			CAST(RSK_IDU AS varchar(300)) AS EVER_INJCT_NOPRSC_DRG, ------13
*/


			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_BloodExpOther'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set BLD_EXPOSURE_IND = trim(RSK_BloodExpOther);'
			    execute sp_executesql  @sql
			  END;
 

			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_BloodTransfusion'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set BLD_RECVD_IND = trim(RSK_BloodTransfusion);'
			    execute sp_executesql  @sql
			  END;
 

			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_BloodTransfusionDate'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set BLD_RECVD_DT = (RSK_BloodTransfusionDate);'
			    execute sp_executesql  @sql
			  END;
 

			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_BloodWorkerCnctFreq'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set MED_DEN_BLD_CT_FRQ = trim(RSK_BloodWorkerCnctFreq);'
			    execute sp_executesql  @sql
			  END;
 

			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_BloodWorkerEver'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set MED_DEN_EMP_EVER_IND = trim(RSK_BloodWorkerEver);'
			    execute sp_executesql  @sql
			  END;
 

			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_BloodWorkerOnset'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set MED_DEN_EMPLOYEE_IND = trim(RSK_BloodWorkerOnset);'
			    execute sp_executesql  @sql
			  END;
 

			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_ClottingPrior87'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set CLOTFACTOR_PRIOR_1987 = trim(RSK_ClottingPrior87);'
			    execute sp_executesql  @sql
			  END;
 

			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_ContaminatedStick'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set BLD_CONTAM_IND = trim(RSK_ContaminatedStick);'
			    execute sp_executesql  @sql
			  END;
 

			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_DentalOralSx'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set DEN_WORK_OR_SURG_IND = trim(RSK_DentalOralSx);'
			    execute sp_executesql  @sql
			  END;
 

			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_HEMODIALYSIS_BEFORE_ONSET'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set HEMODIALYSIS_IND = trim(RSK_HEMODIALYSIS_BEFORE_ONSET);'
			    execute sp_executesql  @sql
			  END;
 

			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_HemodialysisLongTerm'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set LT_HEMODIALYSIS_IND = trim(RSK_HemodialysisLongTerm);'
			    execute sp_executesql  @sql
			  END;
 

			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_HospitalizedPrior'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set HSPTL_PRIOR_ONSET_IND = trim(RSK_HospitalizedPrior);'
			    execute sp_executesql  @sql
			  END;
 

			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_IDU'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set EVER_INJCT_NOPRSC_DRG = trim(RSK_IDU);'
			    execute sp_executesql  @sql
			  END;
 



/*          CAST(RSK_Incarcerated24Hrs AS varchar(300)) AS INCAR_24PLUSHRS_IND, ---14
			CAST(RSK_Incarcerated6months AS varchar(300)) AS INCAR_6PLUS_MO_IND, ----15
			CAST(RSK_IncarceratedEver AS varchar(300)) AS EVER_INCAR_IND, ---16
			CAST(RSK_IncarceratedJail AS varchar(300)) AS INCAR_TYPE_JAIL_IND, -- -17
			CAST(RSK_IncarcerationPrison AS varchar(300)) AS INCAR_TYPE_PRISON_IND, ---18
			CAST(RSK_IncarcJuvenileFacilit AS varchar(300)) AS INCAR_TYPE_JUV_IND, ---19
			CAST(RSK_IncarcTimeMonths AS varchar(300)) AS LAST6PLUSMO_INCAR_PER, ------20
			CAST(RSK_IncarcYear6Mos AS varchar(300)) AS LAST6PLUSMO_INCAR_YR, ---21
*/


			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_Incarcerated24Hrs'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set INCAR_24PLUSHRS_IND = trim(RSK_Incarcerated24Hrs);'
			    execute sp_executesql  @sql
			  END;
   		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_Incarcerated6months'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set INCAR_6PLUS_MO_IND = trim(RSK_Incarcerated6months);'
			    execute sp_executesql  @sql
			  END;
		  	
			  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_IncarceratedEver'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set EVER_INCAR_IND = trim(RSK_IncarceratedEver);'
			    execute sp_executesql  @sql
			  END;
		  		  	  	


			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_IncarceratedJail'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set INCAR_TYPE_JAIL_IND = trim(RSK_IncarceratedJail);'
			    execute sp_executesql  @sql
			  END;
 			  		  	  	  		  
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_IncarcerationPrison'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set INCAR_TYPE_PRISON_IND = trim(RSK_IncarcerationPrison);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_IncarcJuvenileFacilit'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set INCAR_TYPE_JUV_IND = trim(RSK_IncarcJuvenileFacilit);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_IncarcTimeMonths'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set LAST6PLUSMO_INCAR_PER = trim(RSK_IncarcTimeMonths);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_IncarcYear6Mos'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set LAST6PLUSMO_INCAR_YR = trim(RSK_IncarcYear6Mos);'
			    execute sp_executesql  @sql
			  END;
 


/*
CAST(RSK_IVInjectInfuseOutpt AS varchar(300)) AS OUTPAT_IV_INF_IND, ---22
			CAST(RSK_LongTermCareRes AS varchar(300)) AS LTCARE_RESIDENT_IND, ---23
			CAST(RSK_NumSexPrtners AS varchar(300)) AS LIFE_SEX_PRTNR_NBR, ---24
			CAST(RSK_OtherBldExpSpec AS varchar(300)) AS BLD_EXPOSURE_OTH, ---25
			CAST(RSK_Piercing AS varchar(300)) AS PIERC_PRIOR_ONSET_IND, ---26
			CAST(RSK_PiercingOthLocSpec AS varchar(300)) AS PIERC_PERF_LOC_OTH, ----27                                                                                                            
			CAST(RSK_PiercingRcvdFrom AS varchar(300)) AS PIERC_PERF_LOC, -------28
			CAST(RSK_PSWrkrBldCnctFreq AS varchar(300)) AS PUB_SAFETY_BLD_CT_FRQ, ---29
			CAST(RSK_PublicSafetyWorker AS varchar(300)) AS PUB_SAFETY_WORKER_IND, ---30
			CAST(RSK_STDTxEver AS varchar(300)) AS STD_TREATED_IND, ---31
			CAST(RSK_STDTxYr AS varchar(300)) AS STD_LAST_TREATMENT_YR, ---32
			CAST(RSK_SurgeryOther AS varchar(300)) AS NON_ORAL_SURGERY_IND, -----33
			CAST(RSK_Tattoo AS varchar(300)) AS TATT_PRIOR_ONSET_IND, ----34
			CAST(RSK_TattooLocation AS varchar(300)) AS TATTOO_PERF_LOC, ----35
			CAST(RSK_TattooLocOthSpec AS varchar(300)) AS TATT_PRIOR_LOC_OTH, ---36
			CAST(RSK_TransfusionPrior92 AS varchar(300)) AS BLD_TRANSF_PRIOR_1992, ---37
			CAST(RSK_TransplantPrior92 AS varchar(300)) AS ORGN_TRNSP_PRIOR_1992, ---38
			CAST(RSK_HepContactEver AS varchar(300)) AS HEP_CONTACT_EVER_IND     ----39
		*/



			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_IVInjectInfuseOutpt'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set OUTPAT_IV_INF_IND = trim(RSK_IVInjectInfuseOutpt);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_NumSexPrtners'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set LIFE_SEX_PRTNR_NBR = trim(RSK_NumSexPrtners);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_OtherBldExpSpec'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set BLD_EXPOSURE_OTH = trim(RSK_OtherBldExpSpec);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_Piercing'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set PIERC_PRIOR_ONSET_IND = trim(RSK_Piercing);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_PiercingOthLocSpec'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set PIERC_PERF_LOC = trim(RSK_PiercingOthLocSpec);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_PSWrkrBldCnctFreq'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set PUB_SAFETY_BLD_CT_FRQ = trim(RSK_PSWrkrBldCnctFreq);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_PublicSafetyWorker'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set PUB_SAFETY_WORKER_IND = trim(RSK_PublicSafetyWorker);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_STDTxEver'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set STD_TREATED_IND = trim(RSK_STDTxEver);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_STDTxYr'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set STD_LAST_TREATMENT_YR = trim(RSK_STDTxYr);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_SurgeryOther'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set NON_ORAL_SURGERY_IND = trim(RSK_SurgeryOther);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_Tattoo'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set TATT_PRIOR_ONSET_IND = trim(RSK_Tattoo);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_TattooLocation'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set TATTOO_PERF_LOC = trim(RSK_TattooLocation);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_TattooLocOthSpec'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set TATT_PRIOR_LOC_OTH = trim(RSK_TattooLocOthSpec);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_TransfusionPrior92'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set BLD_TRANSF_PRIOR_1992 = trim(RSK_TransfusionPrior92);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_TransplantPrior92'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set ORGN_TRNSP_PRIOR_1992 = trim(RSK_TransplantPrior92);'
			    execute sp_executesql  @sql
			  END;
 
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_HepContactEver'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_RISK_FACTOR'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_RISK_FACTOR set HEP_CONTACT_EVER_IND = trim(RSK_HepContactEver);'
			    execute sp_executesql  @sql
			  END;
 

       	SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			---------------------------------------------------19. CREATE TABLE rdb.dbo.TMP_D_INV_TRAVEL---------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_D_INV_TRAVEL';
 
			SET @Proc_Step_no = 19;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_TRAVEL', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_INV_TRAVEL;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_TRAVEL', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_TRAVEL;
 
			END;
 
			SELECT F_PAGE_CASE.D_INV_TRAVEL_KEY, F_PAGE_CASE.INVESTIGATION_KEY
			INTO rdb.dbo.TMP_F_INV_TRAVEL
			FROM rdb.dbo.F_PAGE_CASE WITH(NOLOCK)
				 INNER JOIN
				 rdb.dbo.TMP_F_PAGE_CASE AS PAGE_CASE
				 ON F_PAGE_CASE.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY---(my table)
				 ORDER BY D_INV_TRAVEL_KEY;
 
			SELECT F.D_INV_TRAVEL_KEY D_INV_TRAVEL_KEY1, F.INVESTIGATION_KEY AS Travel_INV_KEY, d.*,
			CAST( NULL  AS varchar(1000)) AS HOUSEHOLD_TRAVEL_IND, 
			CAST( NULL  AS varchar(1000)) AS TRAVEL_OUT_USACAN_IND, 
			CAST( NULL  AS varchar(1000)) AS TRAVEL_OUT_USACAN_LOC, 
			CAST( NULL  AS varchar(1000)) AS HOUSEHOLD_TRAVEL_LOC,
			CAST( NULL  AS varchar(1000)) AS TRAVEL_REASON
			INTO rdb.dbo.TMP_D_INV_TRAVEL
			FROM rdb.dbo.TMP_F_INV_TRAVEL AS F
				 LEFT JOIN
				 [dbo].[D_INV_TRAVEL] AS D WITH(NOLOCK)
				 ON F.D_INV_TRAVEL_KEY = D.D_INV_TRAVEL_KEY
				 ORDER BY F.INVESTIGATION_KEY;
            
			
			
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'TRV_HouseholdTravel'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_TRAVEL'))
			  BEGIN
			      
			    SET @sql = N'update rdb.dbo.TMP_D_INV_TRAVEL set HOUSEHOLD_TRAVEL_IND = trim(TRV_HouseholdTravel);'
			    execute sp_executesql  @sql
			  END;
 
 
			
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'TRV_PatientTravel'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_TRAVEL'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_TRAVEL set HOUSEHOLD_TRAVEL_IND = trim(TRV_PatientTravel);'
			    execute sp_executesql  @sql
			  END;
 
			
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'TRV_PtTravelCountries'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_TRAVEL'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_TRAVEL set TRAVEL_OUT_USACAN_IND = trim(TRV_PtTravelCountries);'
			    execute sp_executesql  @sql
			  END;
 
			
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'TRV_VHF_TRAVEL_REASON'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_TRAVEL'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_TRAVEL set TRAVEL_REASON = trim(TRV_VHF_TRAVEL_REASON);'
			    execute sp_executesql  @sql
			  END;
 
			


			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
	
			-------------------------------------------------------------------20. CREATE TABLE TMP_D_INV_VACCINATION--------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  TMP_D_INV_VACCINATION';
 
			SET @Proc_Step_no = 20;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INV_VACCINATION', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_INV_VACCINATION;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INV_VACCINATION', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INV_VACCINATION;
 
			END;
 
			SELECT F_PAGE_CASE.D_INV_VACCINATION_KEY, F_PAGE_CASE.INVESTIGATION_KEY
			INTO rdb.dbo.TMP_F_INV_VACCINATION
			FROM rdb.dbo.F_PAGE_CASE WITH(NOLOCK)
				 INNER JOIN
				 TMP_F_PAGE_CASE AS PAGE_CASE
				 ON F_PAGE_CASE.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY	---(my table)
				 ORDER BY D_INV_VACCINATION_KEY;
 

			SELECT F.D_INV_VACCINATION_KEY D_INV_VACCINATION_KEY1, F.INVESTIGATION_KEY AS VACCINATION_INV_KEY, V.*,
			CAST( NULL  AS varchar(1000)) AS IMM_GLOB_RECVD_IND, ----EXT_IMM_GLOB_RECVD_IND,
			CAST( NULL  AS varchar(1000)) AS STD_LAST_TREATMENT_YR, -----EXT_STD_LAST_TREATMENT_YR,
			CAST( NULL  AS varchar(1000)) AS GLOB_LAST_RECVD_YR, -----EXT_GLOB_LAST_RECVD_YR,
			CAST( NULL  AS varchar(1000)) AS VACC_RECVD_IND, ---- ---EXT_VACC_RECVD_IND--------------5-13-2021
			CAST( NULL  AS varchar(1000)) AS VACC_DOSE_RECVD_NBR, ---EXT_VACC_DOSE_RECVD_NBR,-----INPUT(VAC_VaccineDoses, comma20.);                                     
			CAST( NULL  AS varchar(1000)) AS VACC_LAST_RECVD_YR ,-----EXT_VACC_LAST_RECVD_YR,-------INPUT(VAC_YearofLastDose, comma20.);
 			Cast( null   as varchar(2000)) as VAC_VaccinationDate  ---EXT_VACC_RECVD_DT,------------VAC_VaccinationDate---could not find
			INTO rdb.dbo.TMP_D_INV_VACCINATION
			FROM rdb.dbo.TMP_F_INV_VACCINATION AS F
				 LEFT JOIN
				 [dbo].[D_INV_VACCINATION] AS V WITH(NOLOCK)
				 ON F.D_INV_VACCINATION_KEY = V.D_INV_VACCINATION_KEY
				 ORDER BY F.INVESTIGATION_KEY;
 
 /*

 	        CAST(VAC_ImmuneGlobulin AS varchar(300)) AS IMM_GLOB_RECVD_IND, ----EXT_IMM_GLOB_RECVD_IND,
			CAST(RSK_STDTxYr AS varchar(300)) AS STD_LAST_TREATMENT_YR, -----EXT_STD_LAST_TREATMENT_YR,
			CAST(VAC_LastIGDose AS varchar(300)) AS GLOB_LAST_RECVD_YR, -----EXT_GLOB_LAST_RECVD_YR,
			CAST(VAC_Vacc_Rcvd AS varchar(10)) AS VACC_RECVD_IND, ---- ---EXT_VACC_RECVD_IND--------------5-13-2021
			CAST(VAC_VaccineDoses AS varchar(300)) AS VACC_DOSE_RECVD_NBR, ---EXT_VACC_DOSE_RECVD_NBR,-----INPUT(VAC_VaccineDoses, comma20.);                                     
			CAST(VAC_YearofLastDose AS varchar(300)) AS VACC_LAST_RECVD_YR ,-----EXT_VACC_LAST_RECVD_YR,-------INPUT(VAC_YearofLastDose, comma20.);
 			Cast(VAC_VaccinationDate  as varchar(2000)) as VAC_VaccinationDate  ---EXT_VACC_RECVD_DT,------------VAC_VaccinationDate---could not find
		*/

		
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'VAC_ImmuneGlobulin'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_VACCINATION'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_VACCINATION set IMM_GLOB_RECVD_IND = trim(VAC_ImmuneGlobulin);'
			    execute sp_executesql  @sql
			  END;
 	
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'RSK_STDTxYr'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_VACCINATION'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_VACCINATION set STD_LAST_TREATMENT_YR = (RSK_STDTxYr);'
			    execute sp_executesql  @sql
			  END;
 	
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'VAC_LastIGDose'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_VACCINATION'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_VACCINATION set GLOB_LAST_RECVD_YR = (VAC_LastIGDose);'
			    execute sp_executesql  @sql
			  END;
 	
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'VAC_Vacc_Rcvd'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_VACCINATION'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_VACCINATION set VACC_RECVD_IND = trim(VAC_Vacc_Rcvd);'
			    execute sp_executesql  @sql
			  END;
 	
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'VAC_VaccineDoses'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_VACCINATION'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_VACCINATION set VACC_LAST_RECVD_YR = (VAC_VaccineDoses);'
			    execute sp_executesql  @sql
			  END;
 	
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'VAC_VaccinationDate'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INV_VACCINATION'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INV_VACCINATION set VAC_VaccinationDate = trim(VAC_VaccinationDate);'
			    execute sp_executesql  @sql
			  END;
 
			--------Could Not find
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			----------------------------------------------------------21. CREATE TABLE rdb.dbo.TMP_D_Patient---------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_D_Patient';
 
			SET @Proc_Step_no = 21;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_Patient', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_Patient;
 
			END;
 
			SELECT F_PAGE_CASE.INVESTIGATION_KEY AS Patient_INV_KEY, D_PATIENT.PATIENT_UID, D_PATIENT.PATIENT_ETHNICITY AS PAT_ETHNICITY, D_PATIENT.PATIENT_AGE_REPORTED AS PAT_REPORTED_AGE, D_PATIENT.PATIENT_AGE_REPORTED_UNIT AS PAT_REPORTED_AGE_UNIT, D_PATIENT.PATIENT_CITY AS PAT_CITY, D_PATIENT.PATIENT_COUNTRY AS PAT_COUNTRY, D_PATIENT.PATIENT_BIRTH_COUNTRY AS PAT_BIRTH_COUNTRY, D_PATIENT.PATIENT_COUNTY AS PAT_COUNTY, D_PATIENT.PATIENT_CURRENT_SEX AS PAT_CURR_GENDER, D_PATIENT.PATIENT_DOB AS PAT_DOB, D_PATIENT.PATIENT_FIRST_NAME AS PAT_FIRST_NM, D_PATIENT.PATIENT_LAST_NAME AS PAT_LAST_NM, SUBSTRING(LTRIM(RTRIM(D_PATIENT.PATIENT_LOCAL_ID)), 1, 25) AS PAT_LOCAL_ID, ---added 9/9/2021		
			D_PATIENT.PATIENT_MIDDLE_NAME AS PAT_MIDDLE_NM, D_PATIENT.PATIENT_RACE_CALCULATED AS PAT_RACE, D_PATIENT.PATIENT_STATE AS PAT_STATE, D_PATIENT.PATIENT_STREET_ADDRESS_1 AS PAT_STREET_ADDR_1, D_PATIENT.PATIENT_STREET_ADDRESS_2 AS PAT_STREET_ADDR_2, SUBSTRING(LTRIM(RTRIM(D_PATIENT.PATIENT_ZIP)), 1, 10) AS PAT_ZIP_CODE, ---added 9/9/2021
			D_PATIENT.PATIENT_ENTRY_METHOD AS PAT_ELECTRONIC_IND, D_PATIENT.PATIENT_ADD_TIME AS INV_ADD_TIME
			INTO rdb.dbo.TMP_D_Patient
			----FROM F_PAGE_CASE
			FROM rdb.dbo.TMP_F_PAGE_CASE AS F_PAGE_CASE---(my table)
				 INNER JOIN
				 TMP_CONDITION AS T
				 ON F_PAGE_CASE.CONDITION_KEY = T.CONDITION_KEY---(my table)
				 INNER JOIN
				 rdb.dbo.D_PATIENT WITH(NOLOCK)
				 ON D_PATIENT.PATIENT_KEY = F_PAGE_CASE.PATIENT_KEY
				 ORDER BY INVESTIGATION_KEY;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			---------------------------------------------------22. CREATE TABLE TMP_INVESTIGATION---------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_Investigation';
 
			SET @Proc_Step_no = 22;
 
			IF OBJECT_ID('rdb.dbo.TMP_Investigation', 'U') IS NOT NULL
			BEGIN
				DROP TABLE TMP_Investigation; 
 
			END;
 
			SELECT PAGE_CASE.INVESTIGATION_KEY, INVESTIGATION.CASE_UID, T.CONDITION_CD AS CONDITION_CD, INVESTIGATION.CASE_OID AS PROGRAM_JURISDICTION_OID, CAST([CASE_RPT_MMWR_WK] AS varchar(100)) AS CASE_RPT_MMWR_WEEK, CAST([CASE_RPT_MMWR_YR] AS varchar(100)) AS CASE_RPT_MMWR_YEAR, INVESTIGATION.DIAGNOSIS_DT AS DIAGNOSIS_DT, INVESTIGATION.DIE_FRM_THIS_ILLNESS_IND AS DIE_FRM_THIS_ILLNESS_IND, INVESTIGATION.DISEASE_IMPORTED_IND AS DISEASE_IMPORTED_IND, INVESTIGATION.EARLIEST_RPT_TO_CNTY_DT AS EARLIEST_RPT_TO_CNTY, INVESTIGATION.EARLIEST_RPT_TO_STATE_DT AS EARLIEST_RPT_TO_STATE_DT, 
			INVESTIGATION.HSPTL_ADMISSION_DT AS HSPTL_ADMISSION_DT, INVESTIGATION.HSPTL_DISCHARGE_DT AS HSPTL_DISCHARGE_DT, 
			INVESTIGATION.HSPTL_DURATION_DAYS AS HSPTL_DURATION_DAYS, INVESTIGATION.HSPTLIZD_IND AS HSPTLIZD_IND, INVESTIGATION.ILLNESS_ONSET_DT AS ILLNESS_ONSET_DT, INVESTIGATION.IMPORT_FRM_CITY AS IMPORT_FROM_CITY, INVESTIGATION.IMPORT_FRM_CNTRY AS IMPORT_FROM_COUNTRY, INVESTIGATION.IMPORT_FRM_CNTY AS IMPORT_FROM_COUNTY, INVESTIGATION.IMPORT_FRM_STATE AS IMPORT_FROM_STATE, INVESTIGATION.INV_CASE_STATUS AS INV_CASE_STATUS, LTRIM(RTRIM(INVESTIGATION.INV_COMMENTS)) AS INV_COMMENTS, ---added 7/21/2021	
			INVESTIGATION.INV_LOCAL_ID AS INV_LOCAL_ID, INVESTIGATION.INV_RPT_DT AS INV_RPT_DT, INVESTIGATION.INV_START_DT AS INV_START_DT,		
			----	INVESTIGATION.INVESTIGATION_KEY	AS 	INVESTIGATION_KEY,		
			INVESTIGATION.INVESTIGATION_STATUS AS INVESTIGATION_STATUS, INVESTIGATION.JURISDICTION_NM AS JURISDICTION_NM, INVESTIGATION.OUTBREAK_IND AS OUTBREAK_IND, INVESTIGATION.PATIENT_PREGNANT_IND AS PAT_PREGNANT_IND, INVESTIGATION.RPT_SRC_CD_DESC AS RPT_SRC_CD_DESC, INVESTIGATION.TRANSMISSION_MODE AS TRANSMISSION_MODE, SUBSTRING(LTRIM(RTRIM(INVESTIGATION.LEGACY_CASE_ID)), 1, 15) AS LEGACY_CASE_ID, ----added on 9/8/2021
			DATE_MM_DD_YYYY AS NOT_SUBMIT_DT
			INTO rdb.dbo.TMP_Investigation
			FROM rdb.dbo.F_PAGE_CASE AS PAGE_CASE WITH(NOLOCK)---(original table)
				 INNER JOIN  rdb.dbo.TMP_F_PAGE_CASE AS F_PAGE_CASE  ON F_PAGE_CASE.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY--(myTable)
				 INNER JOIN	 TMP_CONDITION AS T		 ON F_PAGE_CASE.CONDITION_KEY = T.CONDITION_KEY---(my table)
				 INNER JOIN	 rdb.dbo.D_PATIENT WITH(NOLOCK)	 ON F_PAGE_CASE.patient_key = D_PATIENT.patient_key
				 INNER JOIN	 rdb.dbo.INVESTIGATION WITH(NOLOCK)	 ON F_PAGE_CASE.INVESTIGATION_KEY = INVESTIGATION.INVESTIGATION_KEY
				 LEFT OUTER JOIN	 rdb.dbo.NOTIFICATION_EVENT WITH(NOLOCK) 	 ON NOTIFICATION_EVENT.PATIENT_KEY = F_PAGE_CASE.PATIENT_KEY
				 LEFT OUTER JOIN	 rdb.dbo.RDB_DATE WITH(NOLOCK)		 ON NOTIFICATION_EVENT.NOTIFICATION_SUBMIT_DT_KEY = DATE_KEY
				 ORDER BY PAGE_CASE.INVESTIGATION_key;
 

/*
																							CASE_RPT_MMWR_WEEK= INPUT(CASE_RPT_MMWR_WK, comma20.);
 
																							CASE_RPT_MMWR_YEAR= INPUT(CASE_RPT_MMWR_YR, comma20.);
 
*/
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			---------------------------------------------------23. CREATE TABLE TMP_HEP_PAT_PROV ---------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_HEP_PAT_PROV';
 
			SET @Proc_Step_no = 23;
 
			IF OBJECT_ID('rdb.dbo.TMP_HEP_PAT_PROV', 'U') IS NOT NULL
			BEGIN
				DROP TABLE TMP_HEP_PAT_PROV;
 
			END;
 
			SELECT DISTINCT 
				   TMP_F_PAGE_CASE.INVESTIGATION_KEY AS HEP_PAT_PROV_INV_KEY, P.PROVIDER_LOCAL_ID, P.PROVIDER_FIRST_NAME AS PHYSICIAN_FIRST_NM, P.PROVIDER_MIDDLE_NAME AS PHYSICIAN_MIDDLE_NM, P.PROVIDER_LAST_NAME AS PHYSICIAN_LAST_NM, CAST(NULL AS varchar(300)) AS PHYS_NAME, P.PROVIDER_CITY AS PHYS_CITY, P.PROVIDER_STATE AS PHYS_STATE, P.PROVIDER_COUNTY AS PHYS_COUNTY, CAST(NULL AS varchar(300)) AS PHYSICIAN_ADDRESS_USE_DESC, CAST(NULL AS varchar(300)) AS PHYSICIAN_ADDRESS_TYPE_DESC, P.PROVIDER_ADD_TIME, P.PROVIDER_LAST_CHANGE_TIME, P.PROVIDER_UID AS PHYSICIAN_UID, INVGTR.PROVIDER_FIRST_NAME AS INVESTIGATOR_FIRST_NM, INVGTR.PROVIDER_MIDDLE_NAME AS INVESTIGATOR_MIDDLE_NM, INVGTR.PROVIDER_LAST_NAME AS INVESTIGATOR_LAST_NM, CAST(NULL AS varchar(300)) AS INVESTIGATOR_NAME, INVGTR.PROVIDER_UID AS INVESTIGATOR_UID, REPTORG.ORGANIZATION_NAME AS RPT_SRC_SOURCE_NM, REPTORG.ORGANIZATION_COUNTY_CODE AS RPT_SRC_COUNTY_CD, REPTORG.ORGANIZATION_COUNTY AS RPT_SRC_COUNTY, REPTORG.ORGANIZATION_CITY AS RPT_SRC_CITY, REPTORG.ORGANIZATION_STATE AS RPT_SRC_STATE, CAST(NULL AS varchar(300)) AS REPORTING_SOURCE_ADDRESS_USE, CAST(NULL AS varchar(300)) AS REPORTING_SOURCE_ADDRESS_TYPE, REPTORG.ORGANIZATION_UID AS REPORTING_SOURCE_UID
			INTO rdb.dbo.TMP_HEP_PAT_PROV
			FROM rdb.dbo.F_PAGE_CASE AS PAGE_CASE WITH(NOLOCK)
				 INNER JOIN
				 TMP_F_PAGE_CASE
				 ON TMP_F_PAGE_CASE.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY------ (My table)
				 INNER JOIN
				 TMP_CONDITION AS T
				 ON TMP_F_PAGE_CASE.CONDITION_KEY = T.CONDITION_KEY------ (My table)
				 LEFT OUTER JOIN
				 rdb.dbo.D_PROVIDER AS P WITH(NOLOCK)
				 ON PAGE_CASE.PHYSICIAN_KEY = P.PROVIDER_KEY
				 LEFT OUTER JOIN
				 rdb.dbo.D_PROVIDER AS INVGTR WITH(NOLOCK)
				 ON PAGE_CASE.INVESTIGATOR_KEY = INVGTR.PROVIDER_KEY
				 LEFT OUTER JOIN
				 rdb.dbo.D_ORGANIZATION AS REPTORG WITH(NOLOCK)
				 ON PAGE_CASE.ORG_AS_REPORTER_KEY = REPTORG.ORGANIZATION_KEY
				 ORDER BY HEP_PAT_PROV_INV_KEY;
 
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			  SET PHYS_NAME = RTRIM(LTRIM(PHYSICIAN_first_nm));
 
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			  SET PHYS_NAME = CASE
							  WHEN PHYS_NAME IS NOT NULL THEN CONCAT(PHYSICIAN_Last_nm, ', ', RTRIM(LTRIM(PHYSICIAN_first_nm)), ' ', PHYSICIAN_middle_nm)
							  ELSE PHYS_NAME
							  END;
 
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			  SET PHYS_NAME = CASE
							  WHEN LEN(PHYSICIAN_middle_nm) > 0 THEN PHYS_NAME
							  ELSE RTRIM(LTRIM(PHYS_NAME))
							  END;
 
 ----5-17-2021
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			  SET INVESTIGATOR_NAME = RTRIM(LTRIM(INVESTIGATOR_FIRST_NM));
 
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			  SET INVESTIGATOR_NAME = CASE
									  WHEN INVESTIGATOR_NAME IS NOT NULL THEN CONCAT(INVESTIGATOR_Last_nm, ', ', RTRIM(LTRIM(INVESTIGATOR_FIRST_NM)), ' ', INVESTIGATOR_MIDDLE_NM)
									  ELSE INVESTIGATOR_NAME
									  END;
 
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			  SET INVESTIGATOR_NAME = CASE
									  WHEN LEN(INVESTIGATOR_middle_nm) > 0 THEN INVESTIGATOR_NAME
									  ELSE RTRIM(LTRIM(INVESTIGATOR_NAME))
									  END;
 
 ----5-21-2021
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			--SET PHYSICIAN_ADDRESS_USE_DESC =concat_ws(' ',RTRIM(LTRIM(PHYS_CITY)),RTRIM(LTRIM(PHYS_STATE)),RTRIM(LTRIM(PHYS_COUNTY)))
			  SET PHYSICIAN_ADDRESS_USE_DESC = concat(RTRIM(LTRIM(ISNULL(PHYS_CITY, ''))), ' ', RTRIM(LTRIM(ISNULL(PHYS_STATE, ''))), ' ', RTRIM(LTRIM(ISNULL(PHYS_COUNTY, ''))));
 
---8-31-2021
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			  SET PHYSICIAN_ADDRESS_USE_DESC = CASE
											   WHEN LEN(PHYSICIAN_ADDRESS_USE_DESC) > 0 THEN 'Primary Work Place'
											   ELSE NULL
											   END;
 
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			--SET PHYSICIAN_ADDRESS_TYPE_DESC =concat_ws(' ',RTRIM(LTRIM(RPT_SRC_COUNTY)),RTRIM(LTRIM(RPT_SRC_STATE)),RTRIM(LTRIM(RPT_SRC_CITY)))
			  SET PHYSICIAN_ADDRESS_TYPE_DESC = concat(RTRIM(LTRIM(ISNULL(RPT_SRC_COUNTY, ''))), ' ', RTRIM(LTRIM(ISNULL(RPT_SRC_STATE, ''))), ' ', RTRIM(LTRIM(ISNULL(RPT_SRC_CITY, ''))));
 
---8-31-2021
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			  SET PHYSICIAN_ADDRESS_TYPE_DESC = CASE
												WHEN LEN(PHYSICIAN_ADDRESS_TYPE_DESC) > 0 THEN 'Office'
												ELSE NULL
												END;
 
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			---SET REPORTING_SOURCE_ADDRESS_USE =concat_ws(' ',RTRIM(LTRIM(PHYS_CITY)),RTRIM(LTRIM(PHYS_STATE)),RTRIM(LTRIM(PHYS_COUNTY)))
			  SET REPORTING_SOURCE_ADDRESS_USE = concat(RTRIM(LTRIM(ISNULL(PHYS_CITY, ''))), ' ', RTRIM(LTRIM(ISNULL(PHYS_STATE, ''))), ' ', RTRIM(LTRIM(ISNULL(PHYS_COUNTY, ''))));
 
---8-31-2021
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			  SET REPORTING_SOURCE_ADDRESS_USE = CASE
												 WHEN LEN(REPORTING_SOURCE_ADDRESS_USE) > 0 THEN 'Primary Work Place'
												 ELSE NULL
												 END;
 
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			---SET REPORTING_SOURCE_ADDRESS_TYPE =concat_ws(' ',RTRIM(LTRIM(RPT_SRC_COUNTY)),RTRIM(LTRIM(RPT_SRC_STATE)),RTRIM(LTRIM(RPT_SRC_CITY)))
			  SET REPORTING_SOURCE_ADDRESS_TYPE = concat(RTRIM(LTRIM(ISNULL(RPT_SRC_COUNTY, ''))), ' ', RTRIM(LTRIM(ISNULL(RPT_SRC_STATE, ''))), ' ', RTRIM(LTRIM(ISNULL(RPT_SRC_CITY, ''))));
 
---8-31-2021
			UPDATE rdb.dbo.TMP_HEP_PAT_PROV
			  SET REPORTING_SOURCE_ADDRESS_TYPE = CASE
												  WHEN LEN(REPORTING_SOURCE_ADDRESS_TYPE) > 0 THEN 'office'
												  ELSE NULL
												  END;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			-------------------------------------------------24. CREATE TABLE TMP_D_INVESTIGATION_REPEAT ---------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating  rdb.dbo.TMP_D_INVESTIGATION_REPEAT';
 
------is same as dataset- D_INVESTIGATION_REPEAT
			SET @Proc_Step_no = 24;
 
			IF OBJECT_ID('rdb.dbo.TMP_F_INVESTIGATION_REPEAT', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_F_INVESTIGATION_REPEAT;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_D_INVESTIGATION_REPEAT', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_D_INVESTIGATION_REPEAT;
 
			END;
 
			SELECT PAGE_CASE.D_INVESTIGATION_REPEAT_KEY, PAGE_CASE.INVESTIGATION_KEY, TMP_F_PAGE_CASE.CONDITION_KEY
			INTO rdb.dbo.TMP_F_INVESTIGATION_REPEAT
			FROM rdb.dbo.F_PAGE_CASE AS PAGE_CASE WITH(NOLOCK)
				 INNER JOIN
				 TMP_F_PAGE_CASE
				 ON TMP_F_PAGE_CASE.INVESTIGATION_KEY = PAGE_CASE.INVESTIGATION_KEY   ----(my table) since in sas it is rdb.dbo
				 ORDER BY D_INVESTIGATION_REPEAT_KEY;
 
			SELECT DISTINCT 
				   F.D_INVESTIGATION_REPEAT_KEY D_INVESTIGATION_REPEAT_KEY1, F.INVESTIGATION_KEY, F.CONDITION_KEY,D.*, 
				   	CAST( NULL  AS varchar(1000)) AS VAC_VaccineDoseNum1 ,
 			        Cast( null   as varchar(2000)) as VAC_VaccinationDate1 , 
				   PAGE_CASE_UID PAGE_CASE_UID1, BLOCK_NM BLOCK_NM1, ANSWER_GROUP_SEQ_NBR ANSWER_GROUP_SEQ_NBR1 
			INTO rdb.dbo.TMP_D_INVESTIGATION_REPEAT
			FROM rdb.dbo.TMP_F_INVESTIGATION_REPEAT AS F
				 LEFT JOIN
				 [dbo].[D_INVESTIGATION_REPEAT] AS D WITH(NOLOCK)
				 ON D.D_INVESTIGATION_REPEAT_KEY = F.D_INVESTIGATION_REPEAT_KEY
				 ORDER BY F.INVESTIGATION_KEY;
 
   
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'VAC_VaccineDoseNum'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INVESTIGATION_REPEAT'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INVESTIGATION_REPEAT set VAC_VaccineDoseNum1 = trim(VAC_VaccineDoseNum);'
			    execute sp_executesql  @sql
			  END;
 	
			IF EXISTS(SELECT 1 FROM sys.columns   WHERE Name = N'VAC_VaccinationDate'   AND Object_ID = Object_ID(N'rdb.dbo.TMP_D_INVESTIGATION_REPEAT'))
			  BEGIN
			    SET @sql = N'update rdb.dbo.TMP_D_INVESTIGATION_REPEAT set VAC_VaccinationDate1 = (VAC_VaccinationDate);'
			    execute sp_executesql  @sql
			  END;
 	



			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 

			--------------------------------------------------25. CREATE TABLE TMP_METADATA_TEST---------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating rdb.dbo.TMP_METADATA_TEST';
 
			SET @Proc_Step_no = 25;
 
			IF OBJECT_ID('rdb.dbo.TMP_METADATA_TEST', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_METADATA_TEST;
 
			END;
 
			SELECT C.CONDITION_KEY, M.block_nm, M.investigation_form_cd
			INTO rdb.dbo.TMP_METADATA_TEST
			FROM NBS_ODSE.dbo.NBS_UI_METADATA AS M WITH(NOLOCK)
				 INNER JOIN
				 TMP_CONDITION AS C
				 ON M.INVESTIGATION_FORM_CD = C.DISEASE_GRP_DESC ----(My table)
			WHERE M.question_identifier IN( 'VAC103', 'VAC120' ) AND 
				  M.[block_nm] IS NOT NULL;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 

			--------------------------------------------------26a. CREATE TABLE TMP_VAC_REPEAT------------------------------------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating rdb.dbo.TMP_VAC_REPEAT';
 
			SET @Proc_Step_no = 26;
 
-----a
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT;
 
			END;
 
			SELECT DISTINCT 
				   D.D_INVESTIGATION_REPEAT_KEY, D.INVESTIGATION_KEY,
				   VAC_VaccinationDate1 VAC_VaccinationDate ,
				   VAC_VaccineDoseNum1 VAC_VaccineDoseNum,
				   D.PAGE_CASE_UID, D.ANSWER_GROUP_SEQ_NBR AS SEQNBR, D.BLOCK_NM, CAST(NULL  AS varchar(1000)) AS VAC_GT_4_IND, -----Date Indicator
				   CAST(NULL  AS varchar(1000)) AS VAC_DOSE_4_IND, ---Dose Indicator
				   CAST(NULL  AS varchar(1000)) AS VACC_GT_4_IND   ---------FinalIndicator
			INTO rdb.dbo.TMP_VAC_REPEAT
			FROM rdb.dbo.TMP_D_INVESTIGATION_REPEAT AS D
				 INNER JOIN	 rdb.[dbo].TMP_CONDITION AS C  ON C.CONDITION_KEY = D.CONDITION_KEY----My Table
				 INNER JOIN	 rdb.dbo.TMP_METADATA_TEST AS M ON M.CONDITION_KEY = D.CONDITION_KEY
			WHERE M.block_nm = D.BLOCK_NM 
			AND   M.block_nm IN
			(
				SELECT DISTINCT 
					   BLOCK_NM
				FROM NBS_ODSE.dbo.NBS_UI_METADATA WITH(NOLOCK)
				WHERE QUESTION_IDENTIFIER IN( 'VAC103', 'VAC120' ) AND 
					  BLOCK_NM IS NOT NULL
			)
			ORDER BY PAGE_CASE_UID;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 

			---------------------------------------------------26b. CREATE TABLE TMP_VAC_REPEAT_OUT_DATE_Pivot---------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating TMP_VAC_REPEAT_OUT_DATE_Pivot';
 
			SET @Proc_Step_no = 26;
 
----b
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_DATE_Pivot', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_DATE_Pivot;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_DATE', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_DATE;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_DATE_Final', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_DATE_Final;
 
			END;
 
			DECLARE @cols AS nvarchar(max)= STUFF(
			(
				SELECT DISTINCT 
					   ',[' + CAST(NUM AS varchar) + ']'
				FROM
				(
					SELECT ROW_NUMBER() OVER(
					ORDER BY(SELECT NULL)) AS num
					FROM Sys.Objects
				) AS X
				WHERE num <= 5 FOR XML PATH('')
			), 1, 1, '');
 
			PRINT @cols;
 
			DECLARE @SqlCmd nvarchar(max)= '';
 
			SET @SqlCmd = '
																						SELECT 
																						 D_INVESTIGATION_REPEAT_KEY,INVESTIGATION_KEY,Page_Case_UId, 
																							[1] as VACC_RECVD_DT_1,
																							[2] as VACC_RECVD_DT_2,
																							[3] as VACC_RECVD_DT_3,
																							[4] as VACC_RECVD_DT_4,
																							[5] as VACC_RECVD_DT_5
												
																						 Into rdb.dbo.TMP_VAC_REPEAT_OUT_DATE_Pivot  FROM 
																						(
																						SELECT p.*
																						 FROM
																						 (
																							SELECT D_INVESTIGATION_REPEAT_KEY,VAC_VaccinationDate,SEQNBR,INVESTIGATION_KEY, Page_Case_UId
																							FROM rdb.dbo.TMP_VAC_REPEAT
																						 ) AS tbl
																						 PIVOT
																						 (
																							MAX(VAC_VaccinationDate) FOR SEQNBR IN(' + @cols + ')
																						 ) AS p)
																						 as c
																						 ';
 
			PRINT @SqlCmd;
 
			EXEC sp_executesql @SqlCmd;
 
			SELECT *, CAST(NULL  AS varchar(1000)) AS VAC_GT_4_IND      -----Date Indicator---change the length to 300 on 5-13-2021
			INTO TMP_VAC_REPEAT_OUT_DATE
			FROM TMP_VAC_REPEAT_OUT_DATE_Pivot
			WHERE LEN(VACC_RECVD_DT_1) > 0 OR 
				  LEN(VACC_RECVD_DT_2) > 0 OR 
				  LEN(VACC_RECVD_DT_3) > 0 OR 
				  LEN(VACC_RECVD_DT_4) > 0 OR 
				  LEN(VACC_RECVD_DT_5) > 0 AND 
				  PAGE_CASE_UID > 0;
 
			UPDATE TMP_VAC_REPEAT_OUT_DATE
			  SET VAC_GT_4_IND = CASE
								 WHEN VACC_RECVD_DT_5 IS NULL THEN NULL
								 ELSE 'True'
								 END;
 
			SELECT DISTINCT 
				   Page_Case_UId, D_INVESTIGATION_REPEAT_KEY, INVESTIGATION_KEY, VACC_RECVD_DT_1, VACC_RECVD_DT_2, VACC_RECVD_DT_3, VACC_RECVD_DT_4, VAC_GT_4_IND
			INTO TMP_VAC_REPEAT_OUT_DATE_Final
			FROM TMP_VAC_REPEAT_OUT_DATE
			ORDER BY D_INVESTIGATION_REPEAT_KEY;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			---------------------------------------26c. CREATE TABLE TMP_VAC_REPEAT_OUT_NUM_Pivot-------------------------------------------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating TMP_VAC_REPEAT_OUT_NUM_Pivot';
 
			SET @Proc_Step_no = 26;
 
----c
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_NUM_Pivot', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_NUM_Pivot;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_NUM', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_NUM;
 
			END;
 
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_NUM_final', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_NUM_Final;
 
			END;
 
			DECLARE @col AS nvarchar(max)= STUFF(
			(
				SELECT DISTINCT 
					   ',[' + CAST(NUM AS varchar) + ']'
				FROM
				(
					SELECT ROW_NUMBER() OVER(
					ORDER BY(SELECT NULL)) AS num
					FROM Sys.Objects
				) AS X
				WHERE num <= 5 FOR XML PATH('')
			), 1, 1, '');
 
			PRINT @col;
 
			DECLARE @SqlCmds nvarchar(max)= '';
 
			SET @SqlCmds = '
																						SELECT 
																						 D_INVESTIGATION_REPEAT_KEY,INVESTIGATION_KEY,Page_Case_UId, 
																							[1] as VACC_DOSE_NBR_1,
																							[2] as VACC_DOSE_NBR_2,
																							[3] as VACC_DOSE_NBR_3,
																							[4] as VACC_DOSE_NBR_4,
																							[5] as VACC_DOSE_NBR_5
												
																						 Into rdb.dbo.TMP_VAC_REPEAT_OUT_NUM_Pivot  FROM 
																						(
																						SELECT p.*
																						 FROM
																						 (
																							SELECT  D_INVESTIGATION_REPEAT_KEY,VAC_VaccineDoseNum,SEQNBR,INVESTIGATION_KEY, Page_Case_UId
																							FROM rdb.dbo.TMP_VAC_REPEAT
																						 ) AS tbl
																						 PIVOT
																						 (
																							MAX(VAC_VaccineDoseNum) FOR SEQNBR IN(' + @col + ')
																						 ) AS p)
																						 as c
																						 ';
 
			PRINT @SqlCmds;
 
			EXEC sp_executesql @SqlCmds;
 
			SELECT *, CAST(NULL AS [varchar](2000)) AS VAC_DOSE_4_IND      ---Dose Indicator
			INTO TMP_VAC_REPEAT_OUT_NUM
			FROM TMP_VAC_REPEAT_OUT_NUM_Pivot
			WHERE LEN(VACC_DOSE_NBR_1) > 0 OR 
				  LEN(VACC_DOSE_NBR_2) > 0 OR 
				  LEN(VACC_DOSE_NBR_3) > 0 OR 
				  LEN(VACC_DOSE_NBR_4) > 0 OR 
				  LEN(VACC_DOSE_NBR_5) > 0 AND 
				  PAGE_CASE_UID > 0;
 
			UPDATE TMP_VAC_REPEAT_OUT_NUM
			  SET VAC_DOSE_4_IND = CASE
								   WHEN VACC_DOSE_NBR_5 IS NULL THEN NULL
								   ELSE 'True'
								   END;
 
			SELECT DISTINCT 
				   Page_Case_UId, D_INVESTIGATION_REPEAT_KEY, INVESTIGATION_KEY, VACC_DOSE_NBR_1, VACC_DOSE_NBR_2, VACC_DOSE_NBR_3, VACC_DOSE_NBR_4, VAC_DOSE_4_IND
			INTO TMP_VAC_REPEAT_OUT_NUM_Final
			FROM TMP_VAC_REPEAT_OUT_NUM
			ORDER BY D_INVESTIGATION_REPEAT_KEY;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 

			-------------------------------------------------------26d. CREATE TABLE TMP_VAC_REPEAT_OUT_FINAL1--------------------------------------------------------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating TMP_VAC_REPEAT_OUT_FINAL1';
 
			SET @Proc_Step_no = 26;
 
----d
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_FINAL1', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_FINAL1;
 
			END;
 

/*
																				DATA VAC_REPEAT_OUT_FINAL;
 
																				MERGE  VAC_REPEAT_OUT_DATE(IN=in1) VAC_REPEAT_OUT_NUM(IN=in2);
 
																				BY D_INVESTIGATION_REPEAT_KEY;
 
																				*/
			SELECT DISTINCT 
				   D.Page_Case_UId, D.D_INVESTIGATION_REPEAT_KEY, D.INVESTIGATION_KEY, VACC_DOSE_NBR_1, VACC_RECVD_DT_1, VACC_DOSE_NBR_2, VACC_RECVD_DT_2, VACC_DOSE_NBR_3, VACC_RECVD_DT_3, VACC_DOSE_NBR_4, VACC_RECVD_DT_4, VAC_GT_4_IND, VAC_DOSE_4_IND, CAST(NULL  AS varchar(1000)) AS VACC_GT_4_IND   ---------FinalIndicator
			INTO TMP_VAC_REPEAT_OUT_FINAL1
			FROM TMP_VAC_REPEAT_OUT_Date_Final AS D
				 INNER JOIN
				 TMP_VAC_REPEAT_OUT_NUM_Final AS N
				 ON D.D_INVESTIGATION_REPEAT_KEY = N.D_INVESTIGATION_REPEAT_KEY
				 ORDER BY D.D_INVESTIGATION_REPEAT_KEY;
 
			UPDATE TMP_VAC_REPEAT_OUT_FINAL1
			  SET VACC_GT_4_IND = CASE
								  WHEN LEN(RTRIM(VAC_DOSE_4_IND)) > 0 OR 
									   LEN(RTRIM(VAC_GT_4_IND)) > 0 THEN 'True'
								  ELSE NULL
								  END;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			--------------------------------------------------26e. CREATE TABLE TMP_VAC_REPEAT_OUT_FINAL--------------------------------------------------------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating TMP_VAC_REPEAT_OUT_FINAL';
 
			SET @Proc_Step_no = 26;
 
----e
			IF OBJECT_ID('rdb.dbo.TMP_VAC_REPEAT_OUT_FINAL', 'U') IS NOT NULL
			BEGIN
				DROP TABLE rdb.dbo.TMP_VAC_REPEAT_OUT_FINAL;
 
			END;
 
			SELECT Page_Case_UId, D_INVESTIGATION_REPEAT_KEY, INVESTIGATION_KEY, VACC_DOSE_NBR_1, VACC_RECVD_DT_1, VACC_DOSE_NBR_2, VACC_RECVD_DT_2, VACC_DOSE_NBR_3, VACC_RECVD_DT_3, VACC_DOSE_NBR_4, VACC_RECVD_DT_4, VACC_GT_4_IND   ---------FinalIndicator
			INTO rdb.dbo.TMP_VAC_REPEAT_OUT_FINAL
			FROM rdb.dbo.TMP_VAC_REPEAT_OUT_FINAL1;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
	
			---------------------------------------------------------Final Table 27 TMP_HEPATITIS_CASE_BASE------------------------------------------------------------------------------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Generating TMP_HEPATITIS_CASE_BASE';
 
			SET @Proc_Step_no = 27;
 
			IF OBJECT_ID('rdb.dbo.TMP_HEPATITIS_CASE_BASE', 'U') IS NOT NULL
			BEGIN
				DROP TABLE TMP_HEPATITIS_CASE_BASE;
 
			END;
 
			SELECT DISTINCT 
				   A.INIT_NND_NOT_DT, ---1
				   I.CASE_RPT_MMWR_WEEK, ---2
				   I.CASE_RPT_MMWR_YEAR, ----3
				   C.HEP_D_INFECTION_IND, ----4
				   C.HEP_MEDS_RECVD_IND, ----5
				   L.HEP_C_TOTAL_ANTIBODY, ----6
				   I.DIAGNOSIS_DT, ------------7
				   I.DIE_FRM_THIS_ILLNESS_IND, ----8
				   I.DISEASE_IMPORTED_IND, -----9
				   I.EARLIEST_RPT_TO_CNTY, ----10
				   I.EARLIEST_RPT_TO_STATE_DT, ---11
				   RTRIM(LTRIM(SUBSTRING(A.BINATIONAL_RPTNG_CRIT,1,300))) as BINATIONAL_RPTNG_CRIT, ---12
				   E.CHILDCARE_CASE_IND, ---13
				   E.CNTRY_USUAL_RESIDENCE, ---14
				   E.CT_BABYSITTER_IND, ---15
				   E.CT_CHILDCARE_IND, ----16
				   E.CT_HOUSEHOLD_IND, ---17
				   E.HEP_CONTACT_IND, ----18
				   R.HEP_CONTACT_EVER_IND, ----19
				   E.OTHER_CONTACT_IND, ---20
				   ISNULL(NULLIF(E.COM_SRC_OUTBREAK_IND, ''), NULL) AS COM_SRC_OUTBREAK_IND, ---21
				   E.CONTACT_TYPE_OTH, ----22
				   E.CT_PLAYMATE_IND, ----23
				   E.SEXUAL_PARTNER_IND, ----24
				   E.DNP_HOUSEHOLD_CT_IND, ---25
				   E.HEP_A_EPLINK_IND, ----25
				   E.FEMALE_SEX_PRTNR_NBR, ---27
				   E.FOODHNDLR_PRIOR_IND, ----28
				   ISNULL(NULLIF(E.DNP_EMPLOYEE_IND, ''), NULL) AS DNP_EMPLOYEE_IND, -----29
				   ISNULL(NULLIF(E.STREET_DRUG_INJECTED, ''), NULL) AS STREET_DRUG_INJECTED, -----30
				   ISNULL(NULLIF(E.MALE_SEX_PRTNR_NBR, ''), NULL) AS MALE_SEX_PRTNR_NBR, ----31
				   I.OUTBREAK_IND, -----32
				   ISNULL(NULLIF(E.OBRK_FOODHNDLR_IND, ''), NULL) AS OBRK_FOODHNDLR_IND, ----33
				   ISNULL(NULLIF(E.FOOD_OBRK_FOOD_ITEM, ''), NULL) AS FOOD_OBRK_FOOD_ITEM, ----34
				   ISNULL(NULLIF(E.OBRK_NOFOODHNDLR_IND, ''), NULL) AS OBRK_NOFOODHNDLR_IND, ----35
				   ISNULL(NULLIF(E.OBRK_UNIDENTIFIED_IND, ''), NULL) AS OBRK_UNIDENTIFIED_IND, ----36
				   ISNULL(NULLIF(E.OBRK_WATERBORNE_IND, ''), NULL) AS OBRK_WATERBORNE_IND, ----37
				   ISNULL(NULLIF(E.STREET_DRUG_USED, ''), NULL) AS STREET_DRUG_USED, ---38
				   PO.SEX_PREF, ----39
				   I.HSPTL_ADMISSION_DT, ----40
				   I.HSPTL_DISCHARGE_DT, ----41
				   I.HSPTL_DURATION_DAYS, ----42
				   I.HSPTLIZD_IND, ---43
				   I.ILLNESS_ONSET_DT, ----44
				   I.INV_CASE_STATUS, ----45
				   ----	ltrim(rtrim(I.INV_COMMENTS)) as INV_COMMENTS,----46--------7/21/2021
				   LTRIM(RTRIM(REPLACE(REPLACE(REPLACE(REPLACE([INV_COMMENTS], CHAR(10), CHAR(32)), CHAR(13), CHAR(32)), CHAR(160), CHAR(32)), CHAR(9), CHAR(32)))) AS INV_COMMENTS, ----46--------7/21/2021
				   I.INV_LOCAL_ID, ---47
				   I.INV_RPT_DT, ---48
				   I.INV_START_DT, ---49
				   I.INVESTIGATION_STATUS, ---50
				   I.JURISDICTION_NM, ---51
				   L.ALT_SGPT_RESULT, ----52
				   L.ANTI_HBS_POS_REAC_IND, -----53
				   L.AST_SGOT_RESULT, ---54
				   L.HEP_E_ANTIGEN, ----55
				   L.HBE_AG_DT, ---56
				   L.HEP_B_SURFACE_ANTIGEN, ----57
				   L.HBS_AG_DT, ----58
				   L.HEP_B_DNA, -----59
				   L.HBV_NAT_DT, ----60	
				   L.HCV_RNA, ----61
				   L.HCV_RNA_DT, ----62
				   L.HEP_D_TEST_IND, ----63
				   L.HEP_A_IGM_ANTIBODY, ----64
				   L.IGM_ANTI_HAV_DT, ----65
				   L.HEP_B_IGM_ANTIBODY, ----66
				   L.IGM_ANTI_HBC_DT, ----67
				   L.PREV_NEG_HEP_TEST_IND, ------68
				   L.ANTIHCV_SIGCUT_RATIO, ----69
				   L.ANTIHCV_SUPP_ASSAY, -----70
				   L.SUPP_ANTI_HCV_DT, -----71
				   L.ALT_RESULT_DT, ----72
				   L.AST_RESULT_DT, -----73
				   L.ALT_SGPT_RSLT_UP_LMT, ----74
				   L.AST_SGOT_RSLT_UP_LMT, ----75
				   L.HEP_A_TOTAL_ANTIBODY, ----76
				   L.TOTAL_ANTI_HAV_DT, ----77
				   L.HEP_B_TOTAL_ANTIBODY, ----78
				   L.TOTAL_ANTI_HBC_DT, ----79
				   L.TOTAL_ANTI_HCV_DT, ----80
				   L.HEP_D_TOTAL_ANTIBODY, ----81
				   L.TOTAL_ANTI_HDV_DT, ----82
				   L.HEP_E_TOTAL_ANTIBODY, ----83
				   L.TOTAL_ANTI_HEV_DT, ----84
				   L.VERIFIED_TEST_DT, ----85
				   I.LEGACY_CASE_ID, ---86
				   MH.DIABETES_IND, ----87
				   MH.DIABETES_DX_DT, ----88
				   MH.PREGNANCY_DUE_DT, ----89
				   MH.PAT_JUNDICED_IND, -----90
				   MH.PAT_PREV_AWARE_IND, ----91
				   MH.HEP_CARE_PROVIDER, ----92
				   MH.TEST_REASON, ------93
				   RTRIM(LTRIM(SUBSTRING(MH.TEST_REASON_OTH,1,150))) as TEST_REASON_OTH, -----94----10/18/2021 added the trim since the dest has only 150 length
				   MH.SYMPTOMATIC_IND, -----95
				   M.MTH_BORN_OUTSIDE_US, ---96
				   M.MTH_ETHNICITY, ------97
				   M.MTH_HBS_AG_PRIOR_POS, ----98
				   M.MTH_POS_AFTER, ------99
				   M.MTH_POS_TEST_DT, ----100
				   M.MTH_RACE, -----101
				   M.MTH_BIRTH_COUNTRY, ----102
				   I.NOT_SUBMIT_DT, ----103
				   P.PAT_REPORTED_AGE, ----104
				   P.PAT_REPORTED_AGE_UNIT, ----105	
				   P.PAT_CITY, ----106
				   P.PAT_COUNTRY, ----107
				   P.PAT_COUNTY, ----108
				   P.PAT_CURR_GENDER, ---109
				   P.PAT_DOB, ---110
				   LTRIM(RTRIM(P.PAT_ETHNICITY)) AS PAT_ETHNICITY, ---111
				   P.PAT_FIRST_NM, ---112
				   P.PAT_LAST_NM, ---113
				   P.PAT_LOCAL_ID, ---114
				   P.PAT_MIDDLE_NM, ---115
				   I.PAT_PREGNANT_IND, ---116
				   P.PAT_RACE, ----117
				   P.PAT_STATE, ----118
				   LTRIM(RTRIM(P.PAT_STREET_ADDR_1)) AS PAT_STREET_ADDR_1, ---119-------------------7/14/2021
				   P.PAT_STREET_ADDR_2, ----120	
				   P.PAT_ZIP_CODE, ----121
				   HP.RPT_SRC_SOURCE_NM, ----122
				   HP.RPT_SRC_STATE, ----123
				   I.RPT_SRC_CD_DESC, ---124
				   R.BLD_EXPOSURE_IND, ----125
				   R.BLD_RECVD_IND, ----126	
				   R.BLD_RECVD_DT, ----127
				   R.MED_DEN_BLD_CT_FRQ, ----128
				   R.MED_DEN_EMP_EVER_IND, ----129
				   R.MED_DEN_EMPLOYEE_IND, ---130
				   R.CLOTFACTOR_PRIOR_1987, ----131	
				   R.BLD_CONTAM_IND, ------132
				   R.DEN_WORK_OR_SURG_IND, ----133
				   R.HEMODIALYSIS_IND, ----134
				   R.LT_HEMODIALYSIS_IND, -----135
				   R.HSPTL_PRIOR_ONSET_IND, ----136
				   R.EVER_INJCT_NOPRSC_DRG, -----137	
				   R.INCAR_24PLUSHRS_IND, ---138
				   R.INCAR_6PLUS_MO_IND, ---139
				   R.EVER_INCAR_IND, ----140
				   R.INCAR_TYPE_JAIL_IND, ----141
				   R.INCAR_TYPE_PRISON_IND, ----142
				   R.INCAR_TYPE_JUV_IND, ----143
				   R.LAST6PLUSMO_INCAR_PER, ----144	
				   R.LAST6PLUSMO_INCAR_YR, ----145
				   R.OUTPAT_IV_INF_IND, ----146
				   R.LTCARE_RESIDENT_IND, ----147
				   R.LIFE_SEX_PRTNR_NBR, ----148
				   R.BLD_EXPOSURE_OTH, ----149
				   R.PIERC_PRIOR_ONSET_IND, ----150	
				   R.PIERC_PERF_LOC_OTH, ----151	
				   R.PIERC_PERF_LOC, -----152
				   R.PUB_SAFETY_BLD_CT_FRQ, ----153	
				   R.PUB_SAFETY_WORKER_IND, ----154	
				   R.STD_TREATED_IND, ----155
				   R.STD_LAST_TREATMENT_YR, ---156	
				   R.NON_ORAL_SURGERY_IND, ---157
				   R.TATT_PRIOR_ONSET_IND, ---158
				   R.TATTOO_PERF_LOC, ----158
				   R.TATT_PRIOR_LOC_OTH, ----160
				   R.BLD_TRANSF_PRIOR_1992, ---161
				   R.ORGN_TRNSP_PRIOR_1992, ----162	
				   I.TRANSMISSION_MODE, ----163
				   T.HOUSEHOLD_TRAVEL_IND, ----164
				   T.TRAVEL_OUT_USACAN_IND, ----165
				   T.TRAVEL_OUT_USACAN_LOC, -----166
				   T.HOUSEHOLD_TRAVEL_LOC, ----167
				   T.TRAVEL_REASON, -----168
				   V.IMM_GLOB_RECVD_IND, ----169
				   V.GLOB_LAST_RECVD_YR, ----170
				   V.VACC_RECVD_IND, ----171-------Has to be 10 digits long
				   VAC.VACC_DOSE_NBR_1, ----172
				   VAC.VACC_RECVD_DT_1, ----173
				   VAC.VACC_DOSE_NBR_2, ----174
				   VAC.VACC_RECVD_DT_2, ----175
				   VAC.VACC_DOSE_NBR_3, ----176
				   VAC.VACC_RECVD_DT_3, -----177
				   VAC.VACC_DOSE_NBR_4, -----178
				   VAC.VACC_RECVD_DT_4, ---179
				   VAC.VACC_GT_4_IND, -----180
				   V.VACC_DOSE_RECVD_NBR, ----181
				   V.VACC_LAST_RECVD_YR, -----182
				   L.ANTI_HBSAG_TESTED_IND, ----183
				   I.CONDITION_CD, -----184
				   CAST(NULL AS datetime) AS EVENT_DATE, ----185
				   I.IMPORT_FROM_CITY, ----186
				   I.IMPORT_FROM_COUNTRY, ----187
				   I.IMPORT_FROM_COUNTY, ----188
				   I.IMPORT_FROM_STATE, ----189
				   I.INVESTIGATION_KEY, -----190
				   HP.INVESTIGATOR_NAME, -----191
				   P.PAT_ELECTRONIC_IND, ----192
				   HP.PHYS_CITY, ----193
				   HP.PHYS_COUNTY, ----194
				   HP.PHYS_NAME, ----195
				   HP.PHYS_STATE, ----196
				   I.PROGRAM_JURISDICTION_OID, ----197
				   HP.RPT_SRC_CITY, ---198
				   HP.RPT_SRC_COUNTY, ----199
				   HP.RPT_SRC_COUNTY_CD, ---200
				   HP.PHYSICIAN_UID, ----201
				   P.PATIENT_UID, ----202
				   I.CASE_UID, ---203
				   HP.INVESTIGATOR_UID, ---204
				   HP.REPORTING_SOURCE_UID, ---205
				   CAST(NULL AS datetime) AS REFRESH_DATETIME, ---206
				   P.PAT_BIRTH_COUNTRY----207
			INTO rdb.dbo.TMP_HEPATITIS_CASE_BASE
			FROM rdb.dbo.TMP_Investigation AS I WITH(NOLOCK)
				 FULL OUTER JOIN
				 rdb.dbo.TMP_D_INV_LAB_FINDING AS L WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = L.LAB_INV_KEY
				 FULL OUTER JOIN
				 rdb.dbo.TMP_D_INV_RISK_FACTOR AS R WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = R.RISK_INV_KEY
				 FULL OUTER JOIN
				 rdb.dbo.TMP_D_INV_EPIDEMIOLOGY AS E WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = E.EPIDEMIOLOGY_INV_KEY
				 FULL OUTER JOIN
				 rdb.dbo.TMP_D_Patient AS P WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = P.Patient_INV_KEY
				 FULL OUTER JOIN
				 rdb.dbo.TMP_D_INV_VACCINATION AS V WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = V.VACCINATION_INV_KEY
				 FULL OUTER JOIN
				 rdb.dbo.TMP_D_INV_TRAVEL AS T WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = T.Travel_INV_KEY
				 FULL OUTER JOIN
				 rdb.dbo.TMP_D_INV_MOTHER AS M WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = M.Mother_INV_KEY
				 FULL OUTER JOIN
				 rdb.dbo.TMP_D_INV_MEDICAL_HISTORY AS MH WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = MH.MEDHistory_INV_KEY
				 FULL OUTER JOIN
				 rdb.dbo.TMP_D_INV_ADMINISTRATIVE AS A WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = A.ADMIN_INV_KEY
				 FULL OUTER JOIN
				 rdb.dbo.TMP_D_INV_PATIENT_OBS AS PO WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = PO.PATIENT_OBS_INV_KEY
				 FULL OUTER JOIN
				 rdb.dbo.TMP_HEP_PAT_PROV AS HP WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = HP.HEP_PAT_PROV_INV_KEY
				 FULL OUTER JOIN
				 rdb.dbo.TMP_D_INV_CLINICAL AS C WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = C.CLINICAL_INV_KEY
				 FULL OUTER JOIN
				 rdb.dbo.TMP_VAC_REPEAT_OUT_FINAL AS VAC WITH(NOLOCK)
				 ON I.INVESTIGATION_KEY = VAC.INVESTIGATION_KEY;
 
-------(--Actually Should be inner Join)
/*data HEPATITIS_CASE_BASE;
 
																						set HEPATITIS_CASE_BASE;
 
																						if trim(LENGTHN(VACC_GT_4_IND))<1 then do;
 
																						VACC_GT_4_IND="FALSE";
 
 end;
 
  
																						run;
 
*/
			---7/14/2021(since getting error converting varchar to int)
			UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE
			  SET LAST6PLUSMO_INCAR_PER = CASE
										  WHEN LAST6PLUSMO_INCAR_PER NOT LIKE N'%[^0-9.,-]%' AND 
											   LAST6PLUSMO_INCAR_PER NOT LIKE '.' AND 
											   ISNUMERIC(LAST6PLUSMO_INCAR_PER) = 1 THEN LAST6PLUSMO_INCAR_PER
										  ELSE 0
										  END
				where LAST6PLUSMO_INCAR_PER is not null
				;

 
------------  /*EXT_LAST6PLUSMO_INCAR_PER= INPUT(RSK_IncarcTimeMonths, comma20.)*/----20
			UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE
			  SET LAST6PLUSMO_INCAR_YR = CASE
										 WHEN LAST6PLUSMO_INCAR_YR NOT LIKE N'%[^0-9.,-]%' AND 
											  LAST6PLUSMO_INCAR_YR NOT LIKE '.' AND 
											  ISNUMERIC(LAST6PLUSMO_INCAR_YR) = 1 THEN LAST6PLUSMO_INCAR_YR
										 ELSE 0
										 END
           	where LAST6PLUSMO_INCAR_YR is not null
				;
 
------------  /*EXT_LAST6PLUSMO_INCAR_PER= INPUT(RSK_IncarcTimeMonths, comma20.)*/----20
			UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE
			  SET VACC_GT_4_IND = CASE
								  WHEN LEN(ISNULL(RTRIM(LTRIM(VACC_GT_4_IND)), '')) < 1 THEN 'False'
								  ELSE VACC_GT_4_IND
								  END;
 

/*proc sql;
 
																					DELETE from HEPATITIS_CASE_BASE where patient_uid is null;
 
																					quit;
 
*/
			DELETE FROM rdb.dbo.TMP_HEPATITIS_CASE_BASE
			WHERE PATIENT_UID IS NULL;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			------------------------------------------------------------------------------------------------------------------------------------------------------
			UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE
			  SET REFRESH_DATETIME = GETDATE();
 

			/* Note = FIRST_RPT_TO_CNTY_DT   is EARLIEST_RPT_TO_CNTY */
			----1.---HSPTL_DISCHARGE_DT
			UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE
			  SET EVENT_DATE = HSPTL_DISCHARGE_DT
			WHERE EVENT_DATE IS NULL OR 
				  EVENT_DATE > HSPTL_DISCHARGE_DT AND 
				  HSPTL_DISCHARGE_DT IS NOT NULL;
 

			----2.---- HSPTL_ADMISSION_DT
			UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE
			  SET EVENT_DATE = HSPTL_ADMISSION_DT
			WHERE EVENT_DATE IS NULL OR 
				  EVENT_DATE > HSPTL_ADMISSION_DT AND 
				  HSPTL_ADMISSION_DT IS NOT NULL;
 

			----3. ---AST_RESULT_DT
			UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE
			  SET EVENT_DATE = AST_RESULT_DT
			WHERE EVENT_DATE IS NULL OR 
				  EVENT_DATE > AST_RESULT_DT AND 
				  AST_RESULT_DT IS NOT NULL;
 

			----4.---ALT_RESULT_DT
			UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE
			  SET EVENT_DATE = ALT_RESULT_DT
			WHERE EVENT_DATE IS NULL OR 
				  EVENT_DATE > ALT_RESULT_DT AND 
				  ALT_RESULT_DT IS NOT NULL;
 

			----5.--- INV_START_DT
			UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE
			  SET EVENT_DATE = INV_START_DT
			WHERE EVENT_DATE IS NULL OR 
				  EVENT_DATE > INV_START_DT AND 
				  INV_START_DT IS NOT NULL;
 

			---6.  ---EARLIEST_RPT_TO_STATE_DT
			UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE
			  SET EVENT_DATE = EARLIEST_RPT_TO_STATE_DT
			WHERE EVENT_DATE IS NULL OR 
				  EVENT_DATE > EARLIEST_RPT_TO_STATE_DT AND 
				  EARLIEST_RPT_TO_STATE_DT IS NOT NULL;
 

			----7.----EARLIEST_RPT_TO_CNTY
			UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE
			  SET EVENT_DATE = EARLIEST_RPT_TO_CNTY
			WHERE EVENT_DATE IS NULL OR 
				  EVENT_DATE > EARLIEST_RPT_TO_CNTY AND 
				  EARLIEST_RPT_TO_CNTY IS NOT NULL;
 

/*
																								----8---INV_RPT_DT 
																								UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE 
																								SET EVENT_DATE = INV_RPT_DT
																								Where  EVENT_DATE iS NULL or
																								EVENT_DATE > INV_RPT_DT AND  INV_RPT_DT IS NOT NULL 
																								*/
			----8.----ILLNESS_ONSET_DT---
			UPDATE TMP_HEPATITIS_CASE_BASE
			  SET EVENT_DATE = DIAGNOSIS_DT
			WHERE DIAGNOSIS_DT IS NOT NULL;
 

			----9.----ILLNESS_ONSET_DT---
			UPDATE TMP_HEPATITIS_CASE_BASE
			  SET EVENT_DATE = ILLNESS_ONSET_DT
			WHERE ILLNESS_ONSET_DT IS NOT NULL;
 

			----10 If all Dates Are Null get the addTime from Investigation Table ---5-19-2021
			UPDATE TMP_HEPATITIS_CASE_BASE
			  SET EVENT_DATE = ADD_TIME
			FROM [dbo].[INVESTIGATION] I
			WHERE Event_Date IS NULL AND 
				  I.INV_LOCAL_ID = TMP_HEPATITIS_CASE_BASE.INV_LOCAL_ID AND 
				  I.[INVESTIGATION_KEY] = TMP_HEPATITIS_CASE_BASE.INVESTIGATION_KEY;
 

			----HEP_D_TEST_IND  to appear as N,Y U or Null--added on 5-19-2021
			UPDATE rdb.dbo.TMP_HEPATITIS_CASE_BASE ----5/20-2021
			  SET HEP_D_TEST_IND = CASE
								   WHEN HEP_D_TEST_IND IS NULL THEN NULL
								   WHEN HEP_D_TEST_IND = 'Yes' THEN 'Y'
								   WHEN HEP_D_TEST_IND = 'No' THEN 'N'
								   ELSE 'U'
								   END;
 

			---------------------------------------------------------Final Table 28 TMP_HEPATITIS_CASE_BASE------------------------------------------------------------------------------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_name = 'Updating HEPATITIS_CASE_BASE';
 
			SET @Proc_Step_no = 28;
 
			UPDATE rdb.[dbo].[HEPATITIS_DATAMART]
			  SET INIT_NND_NOT_DT = H.[INIT_NND_NOT_DT], CASE_RPT_MMWR_WEEK = H.[CASE_RPT_MMWR_WEEK], CASE_RPT_MMWR_YEAR = H.[CASE_RPT_MMWR_YEAR], HEP_D_INFECTION_IND = H.[HEP_D_INFECTION_IND], HEP_MEDS_RECVD_IND = H.[HEP_MEDS_RECVD_IND], HEP_C_TOTAL_ANTIBODY = H.[HEP_C_TOTAL_ANTIBODY], DIAGNOSIS_DT = H.[DIAGNOSIS_DT], DIE_FRM_THIS_ILLNESS_IND = H.[DIE_FRM_THIS_ILLNESS_IND], DISEASE_IMPORTED_IND = H.[DISEASE_IMPORTED_IND], EARLIEST_RPT_TO_CNTY = H.[EARLIEST_RPT_TO_CNTY], EARLIEST_RPT_TO_STATE_DT = H.[EARLIEST_RPT_TO_STATE_DT], BINATIONAL_RPTNG_CRIT = H.[BINATIONAL_RPTNG_CRIT], CHILDCARE_CASE_IND = H.[CHILDCARE_CASE_IND], CNTRY_USUAL_RESIDENCE = H.[CNTRY_USUAL_RESIDENCE], CT_BABYSITTER_IND = H.[CT_BABYSITTER_IND], CT_CHILDCARE_IND = H.[CT_CHILDCARE_IND], CT_HOUSEHOLD_IND = H.[CT_HOUSEHOLD_IND], HEP_CONTACT_IND = H.[HEP_CONTACT_IND], HEP_CONTACT_EVER_IND = H.[HEP_CONTACT_EVER_IND], OTHER_CONTACT_IND = H.[OTHER_CONTACT_IND], COM_SRC_OUTBREAK_IND = H.[COM_SRC_OUTBREAK_IND], CONTACT_TYPE_OTH = H.[CONTACT_TYPE_OTH], CT_PLAYMATE_IND = H.[CT_PLAYMATE_IND], SEXUAL_PARTNER_IND = H.[SEXUAL_PARTNER_IND], DNP_HOUSEHOLD_CT_IND = H.[DNP_HOUSEHOLD_CT_IND], HEP_A_EPLINK_IND = H.[HEP_A_EPLINK_IND], FEMALE_SEX_PRTNR_NBR = H.[FEMALE_SEX_PRTNR_NBR], FOODHNDLR_PRIOR_IND = H.[FOODHNDLR_PRIOR_IND], DNP_EMPLOYEE_IND = H.[DNP_EMPLOYEE_IND], STREET_DRUG_INJECTED = H.[STREET_DRUG_INJECTED], MALE_SEX_PRTNR_NBR = H.[MALE_SEX_PRTNR_NBR], OUTBREAK_IND = H.[OUTBREAK_IND], OBRK_FOODHNDLR_IND = H.[OBRK_FOODHNDLR_IND], FOOD_OBRK_FOOD_ITEM = H.[FOOD_OBRK_FOOD_ITEM], OBRK_NOFOODHNDLR_IND = H.[OBRK_NOFOODHNDLR_IND], OBRK_UNIDENTIFIED_IND = H.[OBRK_UNIDENTIFIED_IND], OBRK_WATERBORNE_IND = H.[OBRK_WATERBORNE_IND], STREET_DRUG_USED = H.[STREET_DRUG_USED], 
			  SEX_PREF = H.[SEX_PREF], HSPTL_ADMISSION_DT = H.[HSPTL_ADMISSION_DT], HSPTL_DISCHARGE_DT = H.[HSPTL_DISCHARGE_DT], HSPTL_DURATION_DAYS = H.[HSPTL_DURATION_DAYS], HSPTLIZD_IND = H.[HSPTLIZD_IND], ILLNESS_ONSET_DT = H.[ILLNESS_ONSET_DT], INV_CASE_STATUS = H.[INV_CASE_STATUS], INV_COMMENTS = H.[INV_COMMENTS], INV_LOCAL_ID = H.[INV_LOCAL_ID], INV_RPT_DT = H.[INV_RPT_DT], INV_START_DT = H.[INV_START_DT], INVESTIGATION_STATUS = H.[INVESTIGATION_STATUS], JURISDICTION_NM = H.[JURISDICTION_NM], ALT_SGPT_RESULT = H.[ALT_SGPT_RESULT], ANTI_HBS_POS_REAC_IND = H.[ANTI_HBS_POS_REAC_IND], AST_SGOT_RESULT = H.[AST_SGOT_RESULT], HEP_E_ANTIGEN = H.[HEP_E_ANTIGEN], HBE_AG_DT = H.[HBE_AG_DT], HEP_B_SURFACE_ANTIGEN = H.[HEP_B_SURFACE_ANTIGEN], HBS_AG_DT = H.[HBS_AG_DT], HBV_NAT_DT = H.[HBV_NAT_DT], HCV_RNA = H.[HCV_RNA],
			  HCV_RNA_DT = H.[HCV_RNA_DT], HEP_D_TEST_IND = H.[HEP_D_TEST_IND], HEP_A_IGM_ANTIBODY = H.[HEP_A_IGM_ANTIBODY], IGM_ANTI_HAV_DT = H.[IGM_ANTI_HAV_DT], HEP_B_IGM_ANTIBODY = H.[HEP_B_IGM_ANTIBODY], IGM_ANTI_HBC_DT = H.[IGM_ANTI_HBC_DT], PREV_NEG_HEP_TEST_IND = H.[PREV_NEG_HEP_TEST_IND], ANTIHCV_SIGCUT_RATIO = H.[ANTIHCV_SIGCUT_RATIO], ANTIHCV_SUPP_ASSAY = H.[ANTIHCV_SUPP_ASSAY], SUPP_ANTI_HCV_DT = H.[SUPP_ANTI_HCV_DT], ALT_RESULT_DT = H.[ALT_RESULT_DT], AST_RESULT_DT = H.[AST_RESULT_DT], ALT_SGPT_RSLT_UP_LMT = H.[ALT_SGPT_RSLT_UP_LMT], AST_SGOT_RSLT_UP_LMT = H.[AST_SGOT_RSLT_UP_LMT], HEP_A_TOTAL_ANTIBODY = H.[HEP_A_TOTAL_ANTIBODY], TOTAL_ANTI_HAV_DT = H.[TOTAL_ANTI_HAV_DT], HEP_B_TOTAL_ANTIBODY = H.[HEP_B_TOTAL_ANTIBODY], TOTAL_ANTI_HBC_DT = H.[TOTAL_ANTI_HBC_DT], TOTAL_ANTI_HCV_DT = H.[TOTAL_ANTI_HCV_DT], 
			  HEP_D_TOTAL_ANTIBODY = H.[HEP_D_TOTAL_ANTIBODY], TOTAL_ANTI_HDV_DT = H.[TOTAL_ANTI_HDV_DT], HEP_E_TOTAL_ANTIBODY = H.[HEP_E_TOTAL_ANTIBODY], TOTAL_ANTI_HEV_DT = H.[TOTAL_ANTI_HEV_DT], VERIFIED_TEST_DT = H.[VERIFIED_TEST_DT], LEGACY_CASE_ID = H.[LEGACY_CASE_ID], DIABETES_IND = H.[DIABETES_IND], DIABETES_DX_DT = H.[DIABETES_DX_DT], PREGNANCY_DUE_DT = H.[PREGNANCY_DUE_DT], PAT_JUNDICED_IND = H.[PAT_JUNDICED_IND], PAT_PREV_AWARE_IND = H.[PAT_PREV_AWARE_IND], HEP_CARE_PROVIDER = H.[HEP_CARE_PROVIDER], TEST_REASON = H.[TEST_REASON], TEST_REASON_OTH = H.[TEST_REASON_OTH], SYMPTOMATIC_IND = H.[SYMPTOMATIC_IND], MTH_BORN_OUTSIDE_US = H.[MTH_BORN_OUTSIDE_US], MTH_ETHNICITY = H.[MTH_ETHNICITY], MTH_HBS_AG_PRIOR_POS = H.[MTH_HBS_AG_PRIOR_POS], MTH_POS_AFTER = H.[MTH_POS_AFTER], MTH_POS_TEST_DT = H.[MTH_POS_TEST_DT], MTH_RACE = H.[MTH_RACE], MTH_BIRTH_COUNTRY = H.[MTH_BIRTH_COUNTRY], NOT_SUBMIT_DT = H.[NOT_SUBMIT_DT], PAT_REPORTED_AGE = H.[PAT_REPORTED_AGE], PAT_REPORTED_AGE_UNIT = H.[PAT_REPORTED_AGE_UNIT], PAT_CITY = H.[PAT_CITY], PAT_COUNTRY = H.[PAT_COUNTRY], PAT_COUNTY = H.[PAT_COUNTY], PAT_CURR_GENDER = H.[PAT_CURR_GENDER], PAT_DOB = H.[PAT_DOB], PAT_ETHNICITY = H.[PAT_ETHNICITY], PAT_FIRST_NM = H.[PAT_FIRST_NM], PAT_LAST_NM = H.[PAT_LAST_NM], PAT_LOCAL_ID = H.[PAT_LOCAL_ID], PAT_MIDDLE_NM = H.[PAT_MIDDLE_NM], PAT_PREGNANT_IND = H.[PAT_PREGNANT_IND], PAT_RACE = H.[PAT_RACE], PAT_STATE = H.[PAT_STATE], PAT_STREET_ADDR_1 = H.[PAT_STREET_ADDR_1], PAT_STREET_ADDR_2 = H.[PAT_STREET_ADDR_2], PAT_ZIP_CODE = H.[PAT_ZIP_CODE], RPT_SRC_SOURCE_NM = H.[RPT_SRC_SOURCE_NM], RPT_SRC_STATE = H.[RPT_SRC_STATE], RPT_SRC_CD_DESC = H.[RPT_SRC_CD_DESC], BLD_EXPOSURE_IND = H.[BLD_EXPOSURE_IND], BLD_RECVD_IND = H.[BLD_RECVD_IND], BLD_RECVD_DT = H.[BLD_RECVD_DT], MED_DEN_BLD_CT_FRQ = H.[MED_DEN_BLD_CT_FRQ], MED_DEN_EMPLOYEE_IND = H.[MED_DEN_EMPLOYEE_IND], MED_DEN_EMP_EVER_IND = H.[MED_DEN_EMP_EVER_IND], CLOTFACTOR_PRIOR_1987 = H.[CLOTFACTOR_PRIOR_1987], BLD_CONTAM_IND = H.[BLD_CONTAM_IND], DEN_WORK_OR_SURG_IND = H.[DEN_WORK_OR_SURG_IND], HEMODIALYSIS_IND = H.[HEMODIALYSIS_IND], LT_HEMODIALYSIS_IND = H.[LT_HEMODIALYSIS_IND], HSPTL_PRIOR_ONSET_IND = H.[HSPTL_PRIOR_ONSET_IND], EVER_INJCT_NOPRSC_DRG = H.[EVER_INJCT_NOPRSC_DRG], INCAR_24PLUSHRS_IND = H.[INCAR_24PLUSHRS_IND], INCAR_6PLUS_MO_IND = H.[INCAR_6PLUS_MO_IND], EVER_INCAR_IND = H.[EVER_INCAR_IND], INCAR_TYPE_JAIL_IND = H.[INCAR_TYPE_JAIL_IND], INCAR_TYPE_PRISON_IND = H.[INCAR_TYPE_PRISON_IND],
			  INCAR_TYPE_JUV_IND = H.[INCAR_TYPE_JUV_IND], LAST6PLUSMO_INCAR_PER = H.[LAST6PLUSMO_INCAR_PER], LAST6PLUSMO_INCAR_YR = H.[LAST6PLUSMO_INCAR_YR], OUTPAT_IV_INF_IND = H.[OUTPAT_IV_INF_IND], LTCARE_RESIDENT_IND = H.[LTCARE_RESIDENT_IND], LIFE_SEX_PRTNR_NBR = H.[LIFE_SEX_PRTNR_NBR], BLD_EXPOSURE_OTH = H.[BLD_EXPOSURE_OTH], PIERC_PRIOR_ONSET_INd = H.[PIERC_PRIOR_ONSET_IND], PIERC_PERF_LOC_OTH = H.[PIERC_PERF_LOC_OTH], PIERC_PERF_LOC = H.[PIERC_PERF_LOC], PUB_SAFETY_BLD_CT_FRQ = H.[PUB_SAFETY_BLD_CT_FRQ], PUB_SAFETY_WORKER_IND = H.[PUB_SAFETY_WORKER_IND], STD_TREATED_IND = H.[STD_TREATED_IND], STD_LAST_TREATMENT_YR = H.[STD_LAST_TREATMENT_YR], NON_ORAL_SURGERY_IND = H.[NON_ORAL_SURGERY_IND], TATT_PRIOR_ONSET_IND = H.[TATT_PRIOR_ONSET_IND], TATTOO_PERF_LOC = H.[TATTOO_PERF_LOC], TATT_PRIOR_LOC_OTH = H.[TATT_PRIOR_LOC_OTH], BLD_TRANSF_PRIOR_1992 = H.[BLD_TRANSF_PRIOR_1992], ORGN_TRNSP_PRIOR_1992 = H.[ORGN_TRNSP_PRIOR_1992], TRANSMISSION_MODE = H.[TRANSMISSION_MODE], HOUSEHOLD_TRAVEL_IND = H.[HOUSEHOLD_TRAVEL_IND], TRAVEL_OUT_USACAN_IND = H.[TRAVEL_OUT_USACAN_IND], TRAVEL_OUT_USACAN_LOC = H.[TRAVEL_OUT_USACAN_LOC], HOUSEHOLD_TRAVEL_LOC = H.[HOUSEHOLD_TRAVEL_LOC], TRAVEL_REASON = H.[TRAVEL_REASON], IMM_GLOB_RECVD_IND = H.[IMM_GLOB_RECVD_IND], GLOB_LAST_RECVD_YR = H.[GLOB_LAST_RECVD_YR], VACC_RECVD_IND = H.[VACC_RECVD_IND], VACC_DOSE_NBR_1 = H.[VACC_DOSE_NBR_1], VACC_RECVD_DT_1 = H.[VACC_RECVD_DT_1], VACC_DOSE_NBR_2 = H.[VACC_DOSE_NBR_2], VACC_RECVD_DT_2 = H.[VACC_RECVD_DT_2], VACC_DOSE_NBR_3 = H.[VACC_DOSE_NBR_3], VACC_RECVD_DT_3 = H.[VACC_RECVD_DT_3], VACC_DOSE_NBR_4 = H.[VACC_DOSE_NBR_4], VACC_RECVD_DT_4 = H.[VACC_RECVD_DT_4], VACC_GT_4_IND = H.[VACC_GT_4_IND], VACC_DOSE_RECVD_NBR = H.[VACC_DOSE_RECVD_NBR], VACC_LAST_RECVD_YR = H.[VACC_LAST_RECVD_YR], ANTI_HBSAG_TESTED_IND = H.[ANTI_HBSAG_TESTED_IND], CONDITION_CD = H.[CONDITION_CD], EVENT_DATE = H.[EVENT_DATE], IMPORT_FROM_CITY = H.[IMPORT_FROM_CITY], IMPORT_FROM_COUNTRY = H.[IMPORT_FROM_COUNTRY], IMPORT_FROM_COUNTY = H.[IMPORT_FROM_COUNTY], IMPORT_FROM_STATE = H.[IMPORT_FROM_STATE], INVESTIGATION_KEY = H.[INVESTIGATION_KEY], INVESTIGATOR_NAME = H.[INVESTIGATOR_NAME], PAT_ELECTRONIC_IND = H.[PAT_ELECTRONIC_IND], PHYS_CITY = H.[PHYS_CITY], PHYS_COUNTY = H.[PHYS_COUNTY], PHYS_NAME = H.[PHYS_NAME], PHYS_STATE = H.[PHYS_STATE], PROGRAM_JURISDICTION_OID = H.[PROGRAM_JURISDICTION_OID], RPT_SRC_CITY = H.[RPT_SRC_CITY], RPT_SRC_COUNTY = H.[RPT_SRC_COUNTY], RPT_SRC_COUNTY_CD = H.[RPT_SRC_COUNTY_CD], PHYSICIAN_UID = H.[PHYSICIAN_UID], PATIENT_UID = H.[PATIENT_UID], CASE_UID = H.[CASE_UID], INVESTIGATOR_UID = H.[INVESTIGATOR_UID], REPORTING_SOURCE_UID = H.[REPORTING_SOURCE_UID], REFRESH_DATETIME = H.[REFRESH_DATETIME], PAT_BIRTH_COUNTRY = H.[PAT_BIRTH_COUNTRY]
			FROM rdb.dbo.TMP_HEPATITIS_CASE_BASE H WITH(NOLOCK)
			WHERE H.[CASE_UID] = [dbo].[HEPATITIS_DATAMART].[CASE_UID] AND 
				  H.[PATIENT_UID] = [dbo].[HEPATITIS_DATAMART].[PATIENT_UID] AND 
				  H.[INVESTIGATION_KEY] = [dbo].[HEPATITIS_DATAMART].[INVESTIGATION_KEY];
 
			COMMIT TRANSACTION;
 

			--------------------------------------29.-----Final ---Inserting into rdb.dbo.HEPATITIS_DATAMART----------------------------------------------
			BEGIN TRANSACTION;
 
			SET @PROC_STEP_NO = 29;
 
			SET @PROC_STEP_NAME = 'Inserting new entries rdb.dbo.HEPATITIS_DATAMART';
 
			INSERT INTO rdb.dbo.[HEPATITIS_DATAMART]( INIT_NND_NOT_DT, ---1
			CASE_RPT_MMWR_WEEK, ---2
			CASE_RPT_MMWR_YEAR, ----3
			HEP_D_INFECTION_IND, ----4
			HEP_MEDS_RECVD_IND, ----5
			HEP_C_TOTAL_ANTIBODY, ----6
			DIAGNOSIS_DT, ------------7
			DIE_FRM_THIS_ILLNESS_IND, ----8
			DISEASE_IMPORTED_IND, -----9
			EARLIEST_RPT_TO_CNTY, ----10
			EARLIEST_RPT_TO_STATE_DT, ---11
			BINATIONAL_RPTNG_CRIT, ---12
			CHILDCARE_CASE_IND, ---13
			CNTRY_USUAL_RESIDENCE, ---14
			CT_BABYSITTER_IND, ---15
			CT_CHILDCARE_IND, ----16
			CT_HOUSEHOLD_IND, ---17
			HEP_CONTACT_IND, ----18
			HEP_CONTACT_EVER_IND, ----19
			OTHER_CONTACT_IND, ---20
			COM_SRC_OUTBREAK_IND, ---21
			CONTACT_TYPE_OTH, ----22
			CT_PLAYMATE_IND, ----23
			SEXUAL_PARTNER_IND, ----24
			DNP_HOUSEHOLD_CT_IND, ---25
			HEP_A_EPLINK_IND, ----25
			FEMALE_SEX_PRTNR_NBR, ---27
			FOODHNDLR_PRIOR_IND, ----28
			DNP_EMPLOYEE_IND, -----29
			STREET_DRUG_INJECTED, -----30
			MALE_SEX_PRTNR_NBR, ----31
			OUTBREAK_IND, -----32
			OBRK_FOODHNDLR_IND, ----33
			FOOD_OBRK_FOOD_ITEM, ----34
			OBRK_NOFOODHNDLR_IND, ----35
			OBRK_UNIDENTIFIED_IND, ----36
			OBRK_WATERBORNE_IND, ----37
			STREET_DRUG_USED, ---38
			SEX_PREF, ----39
			HSPTL_ADMISSION_DT, ----40
			HSPTL_DISCHARGE_DT, ----41
			HSPTL_DURATION_DAYS, ----42
			HSPTLIZD_IND, ---43
			ILLNESS_ONSET_DT, ----44
			INV_CASE_STATUS, ----45
			INV_COMMENTS, ----46
			INV_LOCAL_ID, ---47
			INV_RPT_DT, ---48
			INV_START_DT, ---49
			INVESTIGATION_STATUS, ---50
			JURISDICTION_NM, ---51
			ALT_SGPT_RESULT, ----52
			ANTI_HBS_POS_REAC_IND, -----53
			AST_SGOT_RESULT, ---54
			HEP_E_ANTIGEN, ----55
			HBE_AG_DT, ---56
			HEP_B_SURFACE_ANTIGEN, ----57
			HBS_AG_DT, ----58
			HEP_B_DNA, -----59
			HBV_NAT_DT, ----60	
			HCV_RNA, ----61
			HCV_RNA_DT, ----62
			HEP_D_TEST_IND, ----63
			HEP_A_IGM_ANTIBODY, ----64
			IGM_ANTI_HAV_DT, ----65
			HEP_B_IGM_ANTIBODY, ----66
			IGM_ANTI_HBC_DT, ----67
			PREV_NEG_HEP_TEST_IND, ------68
			ANTIHCV_SIGCUT_RATIO, ----69
			ANTIHCV_SUPP_ASSAY, -----70
			SUPP_ANTI_HCV_DT, -----71
			ALT_RESULT_DT, ----72
			AST_RESULT_DT, -----73
			ALT_SGPT_RSLT_UP_LMT, ----74
			AST_SGOT_RSLT_UP_LMT, ----75
			HEP_A_TOTAL_ANTIBODY, ----76
			TOTAL_ANTI_HAV_DT, ----77
			HEP_B_TOTAL_ANTIBODY, ----78
			TOTAL_ANTI_HBC_DT, ----79
			TOTAL_ANTI_HCV_DT, ----80
			HEP_D_TOTAL_ANTIBODY, ----81
			TOTAL_ANTI_HDV_DT, ----82
			HEP_E_TOTAL_ANTIBODY, ----83
			TOTAL_ANTI_HEV_DT, ----84
			VERIFIED_TEST_DT, ----85
			LEGACY_CASE_ID, ---86
			DIABETES_IND, ----87
			DIABETES_DX_DT, ----88
			PREGNANCY_DUE_DT, ----89
			PAT_JUNDICED_IND, -----90
			PAT_PREV_AWARE_IND, ----91
			HEP_CARE_PROVIDER, ----92
			TEST_REASON, ------93
			TEST_REASON_OTH, -----94
			SYMPTOMATIC_IND, -----95
			MTH_BORN_OUTSIDE_US, ---96
			MTH_ETHNICITY, ------97
			MTH_HBS_AG_PRIOR_POS, ----98
			MTH_POS_AFTER, ------99
			MTH_POS_TEST_DT, ----100
			MTH_RACE, -----101
			MTH_BIRTH_COUNTRY, ----102
			NOT_SUBMIT_DT, ----103
			PAT_REPORTED_AGE, ----104
			PAT_REPORTED_AGE_UNIT, ----105	
			PAT_CITY, ----106
			PAT_COUNTRY, ----107
			PAT_COUNTY, ----108
			PAT_CURR_GENDER, ---109
			PAT_DOB, ---110
			PAT_ETHNICITY, ---111
			PAT_FIRST_NM, ---112
			PAT_LAST_NM, ---113
			PAT_LOCAL_ID, ---114
			PAT_MIDDLE_NM, ---115
			PAT_PREGNANT_IND, ---116
			PAT_RACE, ----117
			PAT_STATE, ----118
			PAT_STREET_ADDR_1, ---119
			PAT_STREET_ADDR_2, ----120	
			PAT_ZIP_CODE, ----121
			RPT_SRC_SOURCE_NM, ----122
			RPT_SRC_STATE, ----123
			RPT_SRC_CD_DESC, ---124
			BLD_EXPOSURE_IND, ----125
			BLD_RECVD_IND, ----126	
			BLD_RECVD_DT, ----127
			MED_DEN_BLD_CT_FRQ, ----128
			MED_DEN_EMP_EVER_IND, ----129
			MED_DEN_EMPLOYEE_IND, ---130
			CLOTFACTOR_PRIOR_1987, ----131	
			BLD_CONTAM_IND, ------132
			DEN_WORK_OR_SURG_IND, ----133
			HEMODIALYSIS_IND, ----134
			LT_HEMODIALYSIS_IND, -----135
			HSPTL_PRIOR_ONSET_IND, ----136
			EVER_INJCT_NOPRSC_DRG, -----137	
			INCAR_24PLUSHRS_IND, ---138
			INCAR_6PLUS_MO_IND, ---139
			EVER_INCAR_IND, ----140
			INCAR_TYPE_JAIL_IND, ----141
			INCAR_TYPE_PRISON_IND, ----142
			INCAR_TYPE_JUV_IND, ----143
			LAST6PLUSMO_INCAR_PER, ----144	
			LAST6PLUSMO_INCAR_YR, ----145
			OUTPAT_IV_INF_IND, ----146
			LTCARE_RESIDENT_IND, ----147
			LIFE_SEX_PRTNR_NBR, ----148
			BLD_EXPOSURE_OTH, ----149
			PIERC_PRIOR_ONSET_IND, ----150	
			PIERC_PERF_LOC_OTH, ----151	
			PIERC_PERF_LOC, -----152
			PUB_SAFETY_BLD_CT_FRQ, ----153	
			PUB_SAFETY_WORKER_IND, ----154	
			STD_TREATED_IND, ----155
			STD_LAST_TREATMENT_YR, ---156	
			NON_ORAL_SURGERY_IND, ---157
			TATT_PRIOR_ONSET_IND, ---158
			TATTOO_PERF_LOC, ----158
			TATT_PRIOR_LOC_OTH, ----160
			BLD_TRANSF_PRIOR_1992, ---161
			ORGN_TRNSP_PRIOR_1992, ----162	
			TRANSMISSION_MODE, ----163
			HOUSEHOLD_TRAVEL_IND, ----164
			TRAVEL_OUT_USACAN_IND, ----165
			TRAVEL_OUT_USACAN_LOC, -----166
			HOUSEHOLD_TRAVEL_LOC, ----167
			TRAVEL_REASON, -----168
			IMM_GLOB_RECVD_IND, ----169
			GLOB_LAST_RECVD_YR, ----170
			VACC_RECVD_IND, ----171
			VACC_DOSE_NBR_1, ----172
			VACC_RECVD_DT_1, ----173
			VACC_DOSE_NBR_2, ----174
			VACC_RECVD_DT_2, ----175
			VACC_DOSE_NBR_3, ----176
			VACC_RECVD_DT_3, -----177
			VACC_DOSE_NBR_4, -----178
			VACC_RECVD_DT_4, ---179
			VACC_GT_4_IND, -----180
			VACC_DOSE_RECVD_NBR, ----181
			VACC_LAST_RECVD_YR, -----182
			ANTI_HBSAG_TESTED_IND, ----183
			CONDITION_CD, -----184
			EVENT_DATE, ----185
			IMPORT_FROM_CITY, ----186
			IMPORT_FROM_COUNTRY, ----187
			IMPORT_FROM_COUNTY, ----188
			IMPORT_FROM_STATE, ----189
			INVESTIGATION_KEY, -----190
			INVESTIGATOR_NAME, -----191
			PAT_ELECTRONIC_IND, ----192
			PHYS_CITY, ----193
			PHYS_COUNTY, ----194
			PHYS_NAME, ----195
			PHYS_STATE, ----196
			PROGRAM_JURISDICTION_OID, ----197
			RPT_SRC_CITY, ---198
			RPT_SRC_COUNTY, ----199
			RPT_SRC_COUNTY_CD, ---200
			PHYSICIAN_UID, ----201
			PATIENT_UID, ----202
			CASE_UID, ---203
			INVESTIGATOR_UID, ---204
			REPORTING_SOURCE_UID, ---205
			REFRESH_DATETIME, ---206
			PAT_BIRTH_COUNTRY ----207
			)
				   SELECT  INIT_NND_NOT_DT,
					CASE_RPT_MMWR_WEEK,
					CASE_RPT_MMWR_YEAR,
					cast(HEP_D_INFECTION_IND as varchar(300)),
					cast(HEP_MEDS_RECVD_IND as varchar(300)),
					cast(HEP_C_TOTAL_ANTIBODY as varchar(300)),
					DIAGNOSIS_DT,
					cast(DIE_FRM_THIS_ILLNESS_IND as varchar(300)),
					cast(DISEASE_IMPORTED_IND as varchar(300)),
					EARLIEST_RPT_TO_CNTY,
					EARLIEST_RPT_TO_STATE_DT,
					cast(BINATIONAL_RPTNG_CRIT as varchar(300)),
					cast(CHILDCARE_CASE_IND as varchar(300)),
					cast(CNTRY_USUAL_RESIDENCE as varchar(300)),
					cast(CT_BABYSITTER_IND as varchar(300)),
					cast(CT_CHILDCARE_IND as varchar(300)),
					cast(CT_HOUSEHOLD_IND as varchar(300)),
					cast(HEP_CONTACT_IND as varchar(300)),
					cast(HEP_CONTACT_EVER_IND as varchar(300)),
					cast(OTHER_CONTACT_IND as varchar(300)),
					cast(COM_SRC_OUTBREAK_IND as varchar(300)),
					cast(CONTACT_TYPE_OTH as varchar(100)),
					cast(CT_PLAYMATE_IND as varchar(300)),
					cast(SEXUAL_PARTNER_IND as varchar(300)),
					cast(DNP_HOUSEHOLD_CT_IND as varchar(300)),
					cast(HEP_A_EPLINK_IND as varchar(300)),
					FEMALE_SEX_PRTNR_NBR,
					cast(FOODHNDLR_PRIOR_IND as varchar(300)),
					cast(DNP_EMPLOYEE_IND as varchar(300)),
					cast(STREET_DRUG_INJECTED as varchar(300)),
					MALE_SEX_PRTNR_NBR,
					cast(OUTBREAK_IND as varchar(300)),
					cast(OBRK_FOODHNDLR_IND as varchar(300)),
					cast(FOOD_OBRK_FOOD_ITEM as varchar(100)),
					cast(OBRK_NOFOODHNDLR_IND as varchar(300)),
					cast(OBRK_UNIDENTIFIED_IND as varchar(300)),
					cast(OBRK_WATERBORNE_IND as varchar(300)),
					cast(STREET_DRUG_USED as varchar(300)),
					cast(SEX_PREF as varchar(300)),
					HSPTL_ADMISSION_DT,
					HSPTL_DISCHARGE_DT,
					HSPTL_DURATION_DAYS,
					cast(HSPTLIZD_IND as varchar(300)),
					ILLNESS_ONSET_DT,
					cast(INV_CASE_STATUS as varchar(300)),
					cast(INV_COMMENTS as varchar(2000)),
					cast(INV_LOCAL_ID as varchar(25)),
					INV_RPT_DT,
					INV_START_DT,
					cast(INVESTIGATION_STATUS as varchar(300)),
					cast(JURISDICTION_NM as varchar(300)),
					ALT_SGPT_RESULT,
					cast(ANTI_HBS_POS_REAC_IND as varchar(300)),
					AST_SGOT_RESULT,
					cast(HEP_E_ANTIGEN as varchar(300)),
					HBE_AG_DT,
					cast(HEP_B_SURFACE_ANTIGEN as varchar(300)),
					HBS_AG_DT,
					cast(HEP_B_DNA as varchar(300)),
					HBV_NAT_DT,
					cast(HCV_RNA as varchar(300)),
					HCV_RNA_DT,
					cast(HEP_D_TEST_IND as varchar(300)),
					cast(HEP_A_IGM_ANTIBODY as varchar(300)),
					IGM_ANTI_HAV_DT,
					cast(HEP_B_IGM_ANTIBODY as varchar(300)),
					IGM_ANTI_HBC_DT,
					cast(PREV_NEG_HEP_TEST_IND as varchar(300)),
					cast(ANTIHCV_SIGCUT_RATIO as varchar(25)),
					cast(ANTIHCV_SUPP_ASSAY as varchar(300)),
					SUPP_ANTI_HCV_DT,
					ALT_RESULT_DT,
					AST_RESULT_DT,
					ALT_SGPT_RSLT_UP_LMT,
					AST_SGOT_RSLT_UP_LMT,
					cast(HEP_A_TOTAL_ANTIBODY as varchar(300)),
					TOTAL_ANTI_HAV_DT,
					cast(HEP_B_TOTAL_ANTIBODY as varchar(300)),
					TOTAL_ANTI_HBC_DT,
					TOTAL_ANTI_HCV_DT,
					cast(HEP_D_TOTAL_ANTIBODY as varchar(300)),
					TOTAL_ANTI_HDV_DT,
					cast(HEP_E_TOTAL_ANTIBODY as varchar(300)),
					TOTAL_ANTI_HEV_DT,
					VERIFIED_TEST_DT,
					cast(LEGACY_CASE_ID as varchar(15)),
					cast(DIABETES_IND as varchar(300)),
					DIABETES_DX_DT,
					PREGNANCY_DUE_DT,
					cast(PAT_JUNDICED_IND as varchar(300)),
					cast(PAT_PREV_AWARE_IND as varchar(300)),
					cast(HEP_CARE_PROVIDER as varchar(300)),
					cast(TEST_REASON as varchar(300)),
					cast(TEST_REASON_OTH as varchar(150)),
					cast(SYMPTOMATIC_IND as varchar(300)),
					cast(MTH_BORN_OUTSIDE_US as varchar(300)),
					cast(MTH_ETHNICITY as varchar(300)),
					cast(MTH_HBS_AG_PRIOR_POS as varchar(300)),
					cast(MTH_POS_AFTER as varchar(300)),
					MTH_POS_TEST_DT,
					cast(MTH_RACE as varchar(300)),
					cast(MTH_BIRTH_COUNTRY as varchar(300)),
					NOT_SUBMIT_DT,
					PAT_REPORTED_AGE,
					cast(PAT_REPORTED_AGE_UNIT as varchar(300)),
					cast(PAT_CITY as varchar(50)),
					cast(PAT_COUNTRY as varchar(300)),
					cast(PAT_COUNTY as varchar(300)),
					cast(PAT_CURR_GENDER as varchar(300)),
					PAT_DOB,
					cast(PAT_ETHNICITY as varchar(300)),
					cast(PAT_FIRST_NM as varchar(50)),
					cast(PAT_LAST_NM as varchar(50)),
					cast(PAT_LOCAL_ID as varchar(25)),
					cast(PAT_MIDDLE_NM as varchar(50)),
					cast(PAT_PREGNANT_IND as varchar(300)),
					cast(PAT_RACE as varchar(300)),
					cast(PAT_STATE as varchar(300)),
					cast(PAT_STREET_ADDR_1 as varchar(50)),
					cast(PAT_STREET_ADDR_2 as varchar(50)),
					cast(PAT_ZIP_CODE as varchar(10)),
					cast(RPT_SRC_SOURCE_NM as varchar(300)),
					cast(RPT_SRC_STATE as varchar(300)),
					cast(RPT_SRC_CD_DESC as varchar(300)),
					cast(BLD_EXPOSURE_IND as varchar(300)),
					cast(BLD_RECVD_IND as varchar(300)),
					BLD_RECVD_DT,
					cast(MED_DEN_BLD_CT_FRQ as varchar(300)),
					cast(MED_DEN_EMP_EVER_IND as varchar(300)),
					cast(MED_DEN_EMPLOYEE_IND as varchar(300)),
					cast(CLOTFACTOR_PRIOR_1987 as varchar(300)),
					cast(BLD_CONTAM_IND as varchar(300)),
					cast(DEN_WORK_OR_SURG_IND as varchar(300)),
					cast(HEMODIALYSIS_IND as varchar(300)),
					cast(LT_HEMODIALYSIS_IND as varchar(300)),
					cast(HSPTL_PRIOR_ONSET_IND as varchar(300)),
					cast(EVER_INJCT_NOPRSC_DRG as varchar(300)),
					cast(INCAR_24PLUSHRS_IND as varchar(300)),
					cast(INCAR_6PLUS_MO_IND as varchar(300)),
					cast(EVER_INCAR_IND as varchar(300)),
					cast(INCAR_TYPE_JAIL_IND as varchar(300)),
					cast(INCAR_TYPE_PRISON_IND as varchar(300)),
					cast(INCAR_TYPE_JUV_IND as varchar(300)),
					LAST6PLUSMO_INCAR_PER,
					LAST6PLUSMO_INCAR_YR,
					cast(OUTPAT_IV_INF_IND as varchar(300)),
					cast(LTCARE_RESIDENT_IND as varchar(300)),
					LIFE_SEX_PRTNR_NBR,
					cast(BLD_EXPOSURE_OTH as varchar(200)),
					cast(PIERC_PRIOR_ONSET_IND as varchar(300)),
					cast(PIERC_PERF_LOC_OTH as varchar(150)),
					cast(PIERC_PERF_LOC as varchar(300)),
					cast(PUB_SAFETY_BLD_CT_FRQ as varchar(300)),
					cast(PUB_SAFETY_WORKER_IND as varchar(300)),
					cast(STD_TREATED_IND as varchar(300)),
					STD_LAST_TREATMENT_YR,
					cast(NON_ORAL_SURGERY_IND as varchar(300)),
					cast(TATT_PRIOR_ONSET_IND as varchar(300)),
					cast(TATTOO_PERF_LOC as varchar(300)),
					cast(TATT_PRIOR_LOC_OTH as varchar(150)),
					cast(BLD_TRANSF_PRIOR_1992 as varchar(300)),
					cast(ORGN_TRNSP_PRIOR_1992 as varchar(300)),
					cast(TRANSMISSION_MODE as varchar(300)),
					cast(HOUSEHOLD_TRAVEL_IND as varchar(300)),
					cast(TRAVEL_OUT_USACAN_IND as varchar(300)),
					cast(TRAVEL_OUT_USACAN_LOC as varchar(300)),
					cast(HOUSEHOLD_TRAVEL_LOC as varchar(300)),
					cast(TRAVEL_REASON as varchar(300)),
					cast(IMM_GLOB_RECVD_IND as varchar(300)),
					GLOB_LAST_RECVD_YR,
					cast(cast(VACC_RECVD_IND as varchar(10)) as varchar(10)),
					VACC_DOSE_NBR_1,
					VACC_RECVD_DT_1,
					VACC_DOSE_NBR_2,
					VACC_RECVD_DT_2,
					VACC_DOSE_NBR_3,
					VACC_RECVD_DT_3,
					VACC_DOSE_NBR_4,
					VACC_RECVD_DT_4,
					cast(VACC_GT_4_IND as varchar(300)),
					VACC_DOSE_RECVD_NBR,
					VACC_LAST_RECVD_YR,
					cast(ANTI_HBSAG_TESTED_IND as varchar(300)),
					cast(CONDITION_CD as varchar(300)),
					EVENT_DATE,
					cast(IMPORT_FROM_CITY as varchar(300)),
					cast(IMPORT_FROM_COUNTRY as varchar(300)),
					cast(IMPORT_FROM_COUNTY as varchar(300)),
					cast(IMPORT_FROM_STATE as varchar(300)),
					INVESTIGATION_KEY,
					cast(INVESTIGATOR_NAME as varchar(300)),
					cast(PAT_ELECTRONIC_IND as varchar(300)),
					cast(PHYS_CITY as varchar(300)),
					cast(PHYS_COUNTY as varchar(300)),
					cast(PHYS_NAME as varchar(300)),
					cast(PHYS_STATE as varchar(300)),
					PROGRAM_JURISDICTION_OID,
					cast(RPT_SRC_CITY as varchar(300)),
					cast(RPT_SRC_COUNTY as varchar(300)),
					cast(RPT_SRC_COUNTY_CD as varchar(300)),
					PHYSICIAN_UID,
					PATIENT_UID,
					CASE_UID,
					INVESTIGATOR_UID,
					REPORTING_SOURCE_UID,
					REFRESH_DATETIME,
					cast(PAT_BIRTH_COUNTRY as varchar(50))	
				   FROM rdb.dbo.TMP_HEPATITIS_CASE_BASE  TH WITH(NOLOCK)
				   WHERE NOT EXISTS
				   (
					   SELECT *
					   FROM rdb.[dbo].[HEPATITIS_DATAMART] WITH(NOLOCK)
					   WHERE [CASE_UID] = TH.[CASE_UID] AND 
							 [PATIENT_UID] = TH.[PATIENT_UID] AND 
							 [INVESTIGATION_KEY] = TH.[INVESTIGATION_KEY]
				   )
				   ORDER BY INVESTIGATION_KEY;
 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
 
			INSERT INTO RDB.[DBO].[JOB_FLOW_LOG]( BATCH_ID, [DATAFLOW_NAME], [PACKAGE_NAME], [STATUS_TYPE], [STEP_NUMBER], [STEP_NAME], [ROW_COUNT] )
			VALUES( @BATCH_ID, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'START', @PROC_STEP_NO, @PROC_STEP_NAME, @ROWCOUNT_NO );
 
			COMMIT TRANSACTION;
 
			---------------------------------------------------------------------------Dropping All TMP Tables------------------------------------------------------------

        select 'Transaction_count',@@TRANCOUNT
		;

		IF @@TRANCOUNT > 0
		BEGIN
		    select 'COMMIT 1';

			COMMIT TRANSACTION;
		END;
		
		--IF @@TRANCOUNT > 0
		--BEGIN
		--    select 'COMMIT 2';
		--	COMMIT TRANSACTION;
		--END;
		

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
 
 
			-------------------------------------------------------------------------------------------------------------------------
			BEGIN TRANSACTION;
 
			SET @Proc_Step_no = 20;
 
			SET @Proc_Step_Name = 'SP_COMPLETE';
 
			INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
			VALUES( @batch_id, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );
 
			

 	COMMIT TRANSACTION;
		
 

	END TRY
	BEGIN CATCH
		IF @@TRANCOUNT > 0
		BEGIN
			ROLLBACK TRANSACTION;
 
		END;
 
		DECLARE @ErrorNumber int= ERROR_NUMBER();
 
		DECLARE @ErrorLine int= ERROR_LINE();
 
		DECLARE @ErrorMessage nvarchar(4000)= ERROR_MESSAGE();
 
		DECLARE @ErrorSeverity int= ERROR_SEVERITY();
 
		DECLARE @ErrorState int= ERROR_STATE();
 
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [Error_Description], [row_count] )
		VALUES( @Batch_id, 'Hepatitis_Case_DATAMART', 'RDB.Hepatitis_Case_DATAMART', 'ERROR', @Proc_Step_no, 'ERROR - ' + @Proc_Step_name, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );
 
		RETURN -1;
 
	END CATCH;
 
END;
 
---First begin

GO
