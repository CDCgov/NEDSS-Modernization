

USE [RDB]
GO


	IF OBJECT_ID('rdb.dbo.tmp_RESULTED_TEST_DETAILS_final', 'U') IS NOT NULL 
		drop table rdb..tmp_RESULTED_TEST_DETAILS_final ;
     go




SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


			 IF OBJECT_ID('rdb.dbo.sp_D_LAB101', 'P') IS NOT NULL
				BEGIN
				DROP PROCEDURE dbo.sp_D_LAB101;
				END;

GO






CREATE PROCEDURE [dbo].[sp_D_LAB101]
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
				VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;


			SELECT @batch_start_time = batch_start_dttm, 
						   @batch_end_time = batch_end_dttm
					FROM [dbo].[job_batch_log]
					WHERE type_code = 'MasterETL'
						  AND status_type = 'start';

				

			with lst as (select top 2 LAB_RPT_LOCAL_ID
				from rdb..LAB101)
				select @Table_RowCount_no = count(*) from lst
				;

        
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = @PROC_STEP_NO+1;
			SET @PROC_STEP_NAME = ' GENERATING tmp_UPDATED_LAB101'; 

	
			IF OBJECT_ID('rdb.dbo.tmp_UPDATED_LAB101', 'U') IS NOT NULL 
			drop table rdb..tmp_UPDATED_LAB101 ;


			SELECT RESULTED_LAB_TEST_KEY
			into rdb..tmp_UPDATED_LAB101 
			FROM RDB..LAB101
			WHERE RESULTED_LAB_TEST_KEY IN (SELECT LAB_TEST_KEY FROM RDB..updated_lab_test_list)
			;

			delete 
			from RDB..lab101
			where RESULTED_LAB_TEST_KEY in (select RESULTED_LAB_TEST_KEY from rdb..tmp_UPDATED_LAB101)
			;


--CREATE TABLE ISOLATE_TRACKING_INIT AS

  		     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
		   if @Table_RowCount_no > 0 
		   BEGIN
		
			   BEGIN TRANSACTION;

					SET @PROC_STEP_NO = @PROC_STEP_NO+1;
					SET @PROC_STEP_NAME = ' GENERATING tmp_ISOLATE_TRACKING_INIT'; 

	
    				IF OBJECT_ID('rdb.dbo.tmp_ISOLATE_TRACKING_INIT', 'U') IS NOT NULL 
							drop table rdb..tmp_ISOLATE_TRACKING_INIT ;
		
					SELECT  TMP_D_LAB_TEST_N.LAB_TEST_KEY, TMP_LAB_RESULT_VAL_final.TEST_RESULT_GRP_KEY, TMP_D_LAB_TEST_N.LAB_TEST_CD, TMP_LAB_RESULT_VAL_final.TEST_RESULT_VAL_CD, 
										  TMP_LAB_RESULT_VAL_final.TEST_RESULT_VAL_CD_DESC, TMP_LAB_RESULT_VAL_final.FROM_TIME, 
										  TMP_LAB_RESULT_VAL_final.LAB_RESULT_TXT_VAL, TMP_D_LAB_TEST_N.PARENT_TEST_PNTR,  --TMP_D_LAB_TEST_N.PARENT_TEST_PNTR,
										  TMP_D_LAB_TEST_N.RECORD_STATUS_CD, TMP_D_LAB_TEST_N.ORDER_OID as oid, TMP_D_LAB_TEST_N.LAB_RPT_LOCAL_ID, TMP_D_LAB_TEST_N.LAB_RPT_UID
					into rdb..tmp_ISOLATE_TRACKING_INIT
					FROM         RDB..TMP_LAB_RESULT_VAL_final,
								 RDB..TMP_TEST_RESULT_GROUPING,
								 RDB..TMP_D_LAB_TEST_N,
								 RDB..TMP_LAB_TEST_RESULT    
					WHERE      
						  TMP_LAB_RESULT_VAL_final.TEST_RESULT_GRP_KEY = TMP_TEST_RESULT_GROUPING.TEST_RESULT_GRP_KEY 
						 and  TMP_D_LAB_TEST_N.LAB_TEST_KEY = TMP_LAB_TEST_RESULT.LAB_TEST_KEY 
						 and  TMP_TEST_RESULT_GROUPING.TEST_RESULT_GRP_KEY = TMP_LAB_TEST_RESULT.TEST_RESULT_GRP_KEY
    
					and lab_test_type = 'I_Result'
					order by LAB_RPT_UID
					;



					/*TO GET RESULTED TEST DETAILS(ORGANISM NAME)
					*/

					--CREATE TABLE RESULTED_TEST_DETAIL1 AS

  					 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

					 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
						(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
						VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		   END;


		   
		   if @Table_RowCount_no = 0 
		   BEGIN
		
			   BEGIN TRANSACTION;

					SET @PROC_STEP_NO = @PROC_STEP_NO+1;
					SET @PROC_STEP_NAME = ' GENERATING ENTIRE tmp_ISOLATE_TRACKING_INIT'; 

	
    				IF OBJECT_ID('rdb.dbo.tmp_ISOLATE_TRACKING_INIT', 'U') IS NOT NULL 
							drop table rdb..tmp_ISOLATE_TRACKING_INIT ;
		

					SELECT  LAB_TEST.LAB_TEST_KEY, LAB_RESULT_VAL.TEST_RESULT_GRP_KEY, LAB_TEST.LAB_TEST_CD, LAB_RESULT_VAL.TEST_RESULT_VAL_CD, 
										  LAB_RESULT_VAL.TEST_RESULT_VAL_CD_DESC, LAB_RESULT_VAL.FROM_TIME, 
										  LAB_RESULT_VAL.LAB_RESULT_TXT_VAL, LAB_TEST.PARENT_TEST_PNTR,  --LAB_TEST.PARENT_TEST_PNTR,
										  LAB_TEST.RECORD_STATUS_CD, LAB_TEST.OID as oid, LAB_TEST.LAB_RPT_LOCAL_ID, LAB_TEST.LAB_RPT_UID
					into rdb..tmp_ISOLATE_TRACKING_INIT
						FROM     RDB..LAB_RESULT_VAL,
								 RDB..TEST_RESULT_GROUPING,
								 RDB..LAB_TEST,
								 RDB..LAB_TEST_RESULT    
					WHERE      
						  LAB_RESULT_VAL.TEST_RESULT_GRP_KEY = TEST_RESULT_GROUPING.TEST_RESULT_GRP_KEY 
						 and  LAB_TEST.LAB_TEST_KEY = LAB_TEST_RESULT.LAB_TEST_KEY 
						 and  TEST_RESULT_GROUPING.TEST_RESULT_GRP_KEY = LAB_TEST_RESULT.TEST_RESULT_GRP_KEY
        				and lab_test_type = 'I_Result'
					order by LAB_RPT_UID
					;



					/*TO GET RESULTED TEST DETAILS(ORGANISM NAME)
					*/

					--CREATE TABLE RESULTED_TEST_DETAIL1 AS

  					 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

					 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
						(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
						VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		   END;



		   if @Table_RowCount_no > 0 
		   BEGIN

			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = @PROC_STEP_NO+1;
			SET @PROC_STEP_NAME = ' GENERATING tmp_RESULTED_TEST_DETAIL1'; 

	
				IF OBJECT_ID('rdb.dbo.tmp_RESULTED_TEST_DETAIL1', 'U') IS NOT NULL 
				drop table rdb..tmp_RESULTED_TEST_DETAIL1 ;


				select LAB_TEST_I_RESULT.LAB_RPT_UID, 
					resulted_test.lab_test_cd_desc, 
					resulted_test.SPECIMEN_SRC as SPECIMEN_SRC_CD,
					resulted_test.SPECIMEN_DESC as SPECIMEN_SRC_DESC,
					resulted_test.SPECIMEN_COLLECTION_DT as SPECIMEN_COLLECTION_DT,
					resulted_test.LAB_TEST_DT as LAB_TEST_DT, 
					resulted_test.LAB_RPT_RECEIVED_BY_PH_DT as LAB_RPT_RECEIVED_BY_PH_DT,
					resulted_test.LAB_RPT_CREATED_DT as LAB_RPT_CREATED_DT, 
					resulted_test.record_status_cd as record_status_cd_resulted_test,
					resulted_test.LAB_TEST_KEY as RESULTED_LAB_TEST_KEY, 
					LAB_TEST_I_result.LAB_RPT_UID as LAB_RPT_UID_result, 
					LAB_TEST_I_result.LAB_RPT_LOCAL_ID
				into rdb..tmp_RESULTED_TEST_DETAIL1
				from RDB..TMP_D_LAB_TEST_N resulted_test 
					left join RDB..LAB_TEST AS LAB_TEST_I_ORDER ON  resulted_test.LAB_RPT_UID=LAB_TEST_I_ORDER.PARENT_TEST_PNTR
					left  join RDB..LAB_TEST AS LAB_TEST_I_RESULT ON LAB_TEST_I_ORDER.LAB_RPT_UID=LAB_TEST_I_RESULT.PARENT_TEST_PNTR
				WHERE LAB_TEST_I_RESULT.LAB_TEST_TYPE = 'I_Result'
					AND  LAB_TEST_I_ORDER.LAB_TEST_TYPE = 'I_Order'
					and resulted_test.LAB_TEST_TYPE = 'Result'
				--VS --AND resulted_test.RDB_LAST_REFRESH_TIME >= @batch_start_time	AND resulted_test.RDB_LAST_REFRESH_TIME <  @batch_end_time
				ORDER BY LAB_RPT_UID
				;

          

				--CREATE TABLE RESULTED_TEST_DETAILS AS


  				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

	        COMMIT TRANSACTION;
		END;


		
		   if @Table_RowCount_no = 0 
		   BEGIN

			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = @PROC_STEP_NO+1;
			SET @PROC_STEP_NAME = ' GENERATING ENTIRE tmp_RESULTED_TEST_DETAIL1'; 

	
				IF OBJECT_ID('rdb.dbo.tmp_RESULTED_TEST_DETAIL1', 'U') IS NOT NULL 
				drop table rdb..tmp_RESULTED_TEST_DETAIL1 ;


				select LAB_TEST_I_RESULT.LAB_RPT_UID, 
					resulted_test.lab_test_cd_desc, 
					resulted_test.SPECIMEN_SRC as SPECIMEN_SRC_CD,
					resulted_test.SPECIMEN_DESC as SPECIMEN_SRC_DESC,
					resulted_test.SPECIMEN_COLLECTION_DT as SPECIMEN_COLLECTION_DT,
					resulted_test.LAB_TEST_DT as LAB_TEST_DT, 
					resulted_test.LAB_RPT_RECEIVED_BY_PH_DT as LAB_RPT_RECEIVED_BY_PH_DT,
					resulted_test.LAB_RPT_CREATED_DT as LAB_RPT_CREATED_DT, 
					resulted_test.record_status_cd as record_status_cd_resulted_test,
					resulted_test.LAB_TEST_KEY as RESULTED_LAB_TEST_KEY, 
					LAB_TEST_I_result.LAB_RPT_UID as LAB_RPT_UID_result, 
					LAB_TEST_I_result.LAB_RPT_LOCAL_ID
				into rdb..tmp_RESULTED_TEST_DETAIL1
				from RDB..LAB_TEST resulted_test 
					left join RDB..LAB_TEST AS LAB_TEST_I_ORDER ON  resulted_test.LAB_RPT_UID=LAB_TEST_I_ORDER.PARENT_TEST_PNTR
					left  join RDB..LAB_TEST AS LAB_TEST_I_RESULT ON LAB_TEST_I_ORDER.LAB_RPT_UID=LAB_TEST_I_RESULT.PARENT_TEST_PNTR
				WHERE LAB_TEST_I_RESULT.LAB_TEST_TYPE = 'I_Result'
					AND  LAB_TEST_I_ORDER.LAB_TEST_TYPE = 'I_Order'
					and resulted_test.LAB_TEST_TYPE = 'Result'
				--VS --AND resulted_test.RDB_LAST_REFRESH_TIME >= @batch_start_time	AND resulted_test.RDB_LAST_REFRESH_TIME <  @batch_end_time
				ORDER BY LAB_RPT_UID
				;

          

				--CREATE TABLE RESULTED_TEST_DETAILS AS


  				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

	        COMMIT TRANSACTION;
		END;



			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = @PROC_STEP_NO+1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_RESULTED_TEST_DETAILS'; 

	
		IF OBJECT_ID('rdb.dbo.TMP_RESULTED_TEST_DETAILS', 'U') IS NOT NULL 
		drop table rdb..TMP_RESULTED_TEST_DETAILS ;

			  SELECT TRACK.*,
				    RESULTED_TEST_DETAIL1.lab_test_cd_desc,
				    RESULTED_TEST_DETAIL1.RESULTED_LAB_TEST_KEY,
				    RESULTED_TEST_DETAIL1.SPECIMEN_COLLECTION_DT,
					RESULTED_TEST_DETAIL1.LAB_TEST_DT, 
					RESULTED_TEST_DETAIL1.LAB_RPT_RECEIVED_BY_PH_DT,
					RESULTED_TEST_DETAIL1.LAB_RPT_CREATED_DT, 
					RESULTED_TEST_DETAIL1.SPECIMEN_SRC_CD, 
					RESULTED_TEST_DETAIL1.SPECIMEN_SRC_DESC, 
 				    RESULTED_TEST_DETAIL1.record_status_cd_resulted_test,
					cast ( null as varchar(50)) as LAB1	,
					cast ( null as varchar(50)) as LAB2	,
					cast ( null as varchar(50)) as LAB3	,
					cast ( null as varchar(50)) as LAB4	,
					cast ( null as varchar(50)) as LAB5	,
					cast ( null as varchar(50)) as LAB6	,
					cast ( null as varchar(50)) as LAB7	,
					cast ( null as varchar(50)) as LAB8	,
					cast ( null as varchar(50)) as LAB9	,
					cast ( null as varchar(50)) as LAB10	,
					cast ( null as varchar(100)) as LAB11	,
					cast ( null as varchar(50)) as LAB12	,
					cast ( null as varchar(50)) as LAB13	,
					cast ( null as varchar(50)) as LAB14	,
					cast ( null as varchar(50)) as LAB15	,
					cast ( null as varchar(50)) as LAB16	,
					cast ( null as varchar(50)) as LAB17	,
					cast ( null as varchar(50)) as LAB18	,
					cast ( null as varchar(50)) as LAB19	,
					cast ( null as varchar(100)) as LAB20	,
					cast ( null as varchar(50)) as LAB21	,
					cast ( null as varchar(50)) as LAB22	,
					cast ( null as varchar(50)) as LAB23	,
					cast ( null as varchar(50)) as LAB24	,
					cast ( null as varchar(50)) as LAB25	,
					cast ( null as varchar(50)) as LAB26	,
					cast ( null as varchar(50)) as LAB27	,
					cast ( null as varchar(50)) as LAB28	,
					cast ( null as varchar(50)) as LAB29	,
					cast ( null as varchar(50)) as LAB30	,
					cast ( null as varchar(50)) as LAB31	,
					cast ( null as varchar(50)) as LAB32	,
					cast ( null as varchar(50)) as LAB33	,
					cast ( null as varchar(50)) as LAB34	,
					cast ( null as varchar(50)) as LAB35	
			into rdb..tmp_RESULTED_TEST_DETAILS
			FROM rdb..tmp_ISOLATE_TRACKING_INIT AS TRACK
			LEFT JOIN rdb..tmp_RESULTED_TEST_DETAIL1 AS RESULTED_TEST_DETAIL1 ON TRACK.LAB_RPT_UID=RESULTED_TEST_DETAIL1.LAB_RPT_UID
		   ;


/*
PROC SORT DATA=RESULTED_TEST_DETAILS;
BY PARENT_TEST_PNTR;
RUN;
*/


--CREATE TABLE ISOLATE_TRACKING_LAB330_INIT AS


  		     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		

		
	   if @Table_RowCount_no > 0 
		   BEGIN
		   
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = @PROC_STEP_NO+1;
			SET @PROC_STEP_NAME = ' GENERATING tmp_ISOLATE_TRACKING_LAB330_INIT'; 

	
			IF OBJECT_ID('rdb.dbo.tmp_ISOLATE_TRACKING_LAB330_INIT', 'U') IS NOT NULL 
			drop table rdb..tmp_ISOLATE_TRACKING_LAB330_INIT ;



					SELECT       TMP_LAB_RESULT_VAL.TEST_RESULT_VAL_CD_DESC AS LAB330, 
								 TMP_D_LAB_TEST_N.LAB_RPT_LOCAL_ID
					into rdb..tmp_ISOLATE_TRACKING_LAB330_INIT
					FROM         RDB..TMP_LAB_RESULT_VAL,
								 RDB..TMP_TEST_RESULT_GROUPING,
								 RDB..TMP_D_LAB_TEST_N,
								 RDB..TMP_LAB_TEST_RESULT    
			     
					WHERE     
										  TMP_LAB_RESULT_VAL.TEST_RESULT_GRP_KEY = TMP_TEST_RESULT_GROUPING.TEST_RESULT_GRP_KEY 
					and                      TMP_D_LAB_TEST_N.LAB_TEST_KEY = TMP_LAB_TEST_RESULT.LAB_TEST_KEY 
					and                      TMP_TEST_RESULT_GROUPING.TEST_RESULT_GRP_KEY =TMP_LAB_TEST_RESULT.TEST_RESULT_GRP_KEY
					and TMP_D_LAB_TEST_N.LAB_TEST_CD ='LAB330'
					order by LAB_RPT_LOCAL_ID
					;

					/*   --VS
					DATA RESULTED_TEST_DpdETAILS;
					SET RESULTED_TEST_DETAILS;
					by PARENT_TEST_PNTR;
					format LAB1-LAB34 $50.;
					array LAB(35) LAB1-LAB35;
					retain LAB1-LAB35 ' ' i 0;

					if first.PARENT_TEST_PNTR then do;
					do j=1 to 35; LAB(j) = ''; end;
					i = 0; 
					end;
					i+1;
					if i <= 35 then do; 
					if LAB_TEST_CD = 'LAB329a' then LAB(1) = TEST_RESULT_VAL_CD_DESC;
					if LAB_TEST_CD = 'LAB330' then LAB(2) = TEST_RESULT_VAL_CD_DESC;
					if LAB_TEST_CD = 'LAB331'  then LAB(3) = TEST_RESULT_VAL_CD_DESC;
					if LAB_TEST_CD = 'LAB332'  then LAB(4) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB333'  then LAB(5) = LAB_RESULT_TXT_VAL; 
					if LAB_TEST_CD = 'LAB334'  then LAB(6) = FROM_TIME; 
					if LAB_TEST_CD = 'LAB335'  then LAB(7) = LAB_RESULT_TXT_VAL; 
					if LAB_TEST_CD = 'LAB336'  then LAB(8) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB337'  then LAB(9) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB338'  then LAB(10) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB339'  then LAB(11) = LAB_RESULT_TXT_VAL; 
					if LAB_TEST_CD = 'LAB340'  then LAB(12) = LAB_RESULT_TXT_VAL; 
					if LAB_TEST_CD = 'LAB341'  then LAB(13) = LAB_RESULT_TXT_VAL; 
					if LAB_TEST_CD = 'LAB342'  then LAB(14) = LAB_RESULT_TXT_VAL; 
					if LAB_TEST_CD = 'LAB343'  then LAB(15) = LAB_RESULT_TXT_VAL; 
					if LAB_TEST_CD = 'LAB344'  then LAB(16) = LAB_RESULT_TXT_VAL; 
					if LAB_TEST_CD = 'LAB345'  then LAB(17) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB346'  then LAB(18) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB347'  then LAB(19) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB348'  then LAB(20) = LAB_RESULT_TXT_VAL; 
					if LAB_TEST_CD = 'LAB349'  then LAB(21) = FROM_TIME; 
					if LAB_TEST_CD = 'LAB350'  then LAB(22) = FROM_TIME; 
					if LAB_TEST_CD = 'LAB351'  then LAB(23) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB352'  then LAB(24) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB353'  then LAB(25) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB354'  then LAB(26) = LAB_RESULT_TXT_VAL; 
					if LAB_TEST_CD = 'LAB355'  then LAB(27) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB356'  then LAB(28) = FROM_TIME;  
					if LAB_TEST_CD = 'LAB357'  then LAB(29) = FROM_TIME;  
					if LAB_TEST_CD = 'LAB358'  then LAB(30) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB359'  then LAB(31) = TEST_RESULT_VAL_CD_DESC; 
					if LAB_TEST_CD = 'LAB360'  then LAB(32) = LAB_RESULT_TXT_VAL; 
					if LAB_TEST_CD = 'LAB361'  then LAB(33) = FROM_TIME; 
					if LAB_TEST_CD = 'LAB362'  then LAB(34) = FROM_TIME; 
					if LAB_TEST_CD = 'LAB363'  then LAB(35) = TEST_RESULT_VAL_CD_DESC;
					end;
					if last.PARENT_TEST_PNTR then output;
					run;

					PROC SORT DATA=RESULTED_TEST_DETAILS;
					BY LAB_RPT_LOCAL_ID;
					RUN;

					*/


  		     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		END;

		

		
	   if @Table_RowCount_no = 0 
		   BEGIN
		   
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = @PROC_STEP_NO+1;
			SET @PROC_STEP_NAME = ' GENERATING tmp_ISOLATE_TRACKING_LAB330_INIT'; 

	
			IF OBJECT_ID('rdb.dbo.tmp_ISOLATE_TRACKING_LAB330_INIT', 'U') IS NOT NULL 
			drop table rdb..tmp_ISOLATE_TRACKING_LAB330_INIT ;



					SELECT       LAB_RESULT_VAL.TEST_RESULT_VAL_CD_DESC AS LAB330, 
								 LAB_TEST.LAB_RPT_LOCAL_ID
					into rdb..tmp_ISOLATE_TRACKING_LAB330_INIT
					FROM         RDB..LAB_RESULT_VAL,
								 RDB..TEST_RESULT_GROUPING,
								 RDB..LAB_TEST,
								 RDB..LAB_TEST_RESULT    
			     
					WHERE     
										  LAB_RESULT_VAL.TEST_RESULT_GRP_KEY = TEST_RESULT_GROUPING.TEST_RESULT_GRP_KEY 
					and                   LAB_TEST.LAB_TEST_KEY = LAB_TEST_RESULT.LAB_TEST_KEY 
					and                   TEST_RESULT_GROUPING.TEST_RESULT_GRP_KEY =LAB_TEST_RESULT.TEST_RESULT_GRP_KEY
					and LAB_TEST.LAB_TEST_CD ='LAB330'
					order by LAB_RPT_LOCAL_ID
					;


  		     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		END;





			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = @PROC_STEP_NO+1;
			SET @PROC_STEP_NAME = ' GENERATING tmp_RESULTED_TEST_DETAILS_final'; 

	
		IF OBJECT_ID('rdb.dbo.tmp_RESULTED_TEST_DETAILS_final', 'U') IS NOT NULL 
		drop table rdb..tmp_RESULTED_TEST_DETAILS_final ;



					CREATE TABLE rdb.[dbo].[tmp_RESULTED_TEST_DETAILS_final](
						[LAB_TEST_KEY] [bigint] NULL,
						[TEST_RESULT_GRP_KEY] [bigint]  NULL,
						[LAB_TEST_CD] [varchar](1000) NULL,
						[TEST_RESULT_VAL_CD] [varchar](20) NULL,
						[TEST_RESULT_VAL_CD_DESC] [varchar](300) NULL,
						[FROM_TIME] [datetime] NULL,
						[LAB_RESULT_TXT_VAL] [varchar](2000) NULL,
						[PARENT_TEST_PNTR] [bigint] NULL,
						[RECORD_STATUS_CD] [varchar](8) NULL,
						[OID] [bigint] NULL,
						[LAB_RPT_LOCAL_ID] [varchar](50) NULL,
						[LAB_RPT_UID] [bigint] NULL,
						[lab_test_cd_desc] [varchar](2000) NULL,
						[RESULTED_LAB_TEST_KEY] [bigint] NULL,
						[SPECIMEN_COLLECTION_DT] [datetime] NULL,
						[LAB_TEST_DT] [datetime] NULL,
						[LAB_RPT_RECEIVED_BY_PH_DT] [datetime] NULL,
						[LAB_RPT_CREATED_DT] [datetime] NULL,
						[SPECIMEN_SRC_CD] [varchar](50) NULL,
						[SPECIMEN_SRC_DESC] [varchar](1000) NULL,
						[record_status_cd_resulted_test] [varchar](8) NULL,
						[LAB1] [varchar](50) NULL,
						[LAB2] [varchar](50) NULL,
						[LAB3] [varchar](50) NULL,
						[LAB4] [varchar](50) NULL,
						[LAB5] [varchar](100) NULL,
						[LAB6] [varchar](50) NULL,
						[LAB7] [varchar](50) NULL,
						[LAB8] [varchar](50) NULL,
						[LAB9] [varchar](50) NULL,
						[LAB10] [varchar](50) NULL,
						[LAB11] [varchar](100) NULL,
						[LAB12] [varchar](50) NULL,
						[LAB13] [varchar](50) NULL,
						[LAB14] [varchar](50) NULL,
						[LAB15] [varchar](50) NULL,
						[LAB16] [varchar](50) NULL,
						[LAB17] [varchar](50) NULL,
						[LAB18] [varchar](50) NULL,
						[LAB19] [varchar](50) NULL,
						[LAB20] [varchar](100) NULL,
						[LAB21] [varchar](50) NULL,
						[LAB22] [varchar](50) NULL,
						[LAB23] [varchar](50) NULL,
						[LAB24] [varchar](50) NULL,
						[LAB25] [varchar](50) NULL,
						[LAB26] [varchar](100) NULL,
						[LAB27] [varchar](50) NULL,
						[LAB28] [varchar](50) NULL,
						[LAB29] [varchar](50) NULL,
						[LAB30] [varchar](50) NULL,
						[LAB31] [varchar](50) NULL,
						[LAB32] [varchar](50) NULL,
						[LAB33] [varchar](50) NULL,
						[LAB34] [varchar](50) NULL,
						[LAB35] [varchar](50) NULL
					) ON [PRIMARY]
					;


					insert into  rdb..tmp_RESULTED_TEST_DETAILS_final (lab_rpt_local_id,PARENT_TEST_PNTR,[RESULTED_LAB_TEST_KEY],record_status_cd,oid,        
					lab_test_cd_desc, SPECIMEN_SRC_DESC, SPECIMEN_SRC_CD, SPECIMEN_COLLECTION_DT
					, LAB_TEST_DT
					 , LAB_RPT_RECEIVED_BY_PH_DT
					 , LAB_RPT_CREATED_DT)
					select lab_rpt_local_id,PARENT_TEST_PNTR,[RESULTED_LAB_TEST_KEY],record_status_cd_resulted_test,oid,
						   lab_test_cd_desc, SPECIMEN_SRC_DESC, SPECIMEN_SRC_CD, SPECIMEN_COLLECTION_DT
					 , LAB_TEST_DT
					 , LAB_RPT_RECEIVED_BY_PH_DT
					 , LAB_RPT_CREATED_DT
					from rdb..tmp_RESULTED_TEST_DETAILS
					group by lab_rpt_local_id,PARENT_TEST_PNTR,[RESULTED_LAB_TEST_KEY],record_status_cd_resulted_test,oid,
						   lab_test_cd_desc, SPECIMEN_SRC_DESC, SPECIMEN_SRC_CD, SPECIMEN_COLLECTION_DT
					 , LAB_TEST_DT
					 , LAB_RPT_RECEIVED_BY_PH_DT
					 , LAB_RPT_CREATED_DT
					;




					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB1  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB329a'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB2  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB330'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB3  = (  select top 1   substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)    from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB331'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB4  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB332'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB5  = (  select top 1    substring(trtd.LAB_RESULT_TXT_VAL,1,100)  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB333'   and   LAB_RESULT_TXT_VAL  is not null   and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY  ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB6  = (  select top 1    trtd.FROM_TIME  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB334'  and   FROM_TIME  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY   ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB7  = (  select top 1    trtd.LAB_RESULT_TXT_VAL  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB335'   and   LAB_RESULT_TXT_VAL  is not null   and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY  ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB8  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB336'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB9  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB337'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB10  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB338'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB11  = (  select top 1    substring(trtd.LAB_RESULT_TXT_VAL,1,100)  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB339'   and   LAB_RESULT_TXT_VAL  is not null   and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY  ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB12  = (  select top 1    trtd.LAB_RESULT_TXT_VAL  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB340'   and   LAB_RESULT_TXT_VAL  is not null   and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY  ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB13  = (  select top 1    trtd.LAB_RESULT_TXT_VAL  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB341'   and   LAB_RESULT_TXT_VAL  is not null   and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY  ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB14  = (  select top 1    trtd.LAB_RESULT_TXT_VAL  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB342'   and   LAB_RESULT_TXT_VAL  is not null   and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY  ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB15  = (  select top 1    trtd.LAB_RESULT_TXT_VAL  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB343'   and   LAB_RESULT_TXT_VAL  is not null   and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY  ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB16  = (  select top 1    trtd.LAB_RESULT_TXT_VAL  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB344'   and   LAB_RESULT_TXT_VAL  is not null   and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY  ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB17  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB345'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB18  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB346'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB19  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB347'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB20  = (  select top 1    substring(trtd.LAB_RESULT_TXT_VAL,1,100)  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB348'   and   LAB_RESULT_TXT_VAL  is not null   and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY  ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB21  = (  select top 1    trtd.FROM_TIME  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB349'  and   FROM_TIME  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY   ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB22  = (  select top 1    trtd.FROM_TIME  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB350'  and   FROM_TIME  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY   ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB23  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB351'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB24  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB352'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB25  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB353'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB26  = (  select top 1    substring(trtd.LAB_RESULT_TXT_VAL,1,100)  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB354'   and   LAB_RESULT_TXT_VAL  is not null   and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY  ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB27  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB355'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB28  = (  select top 1    trtd.FROM_TIME  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB356'  and   FROM_TIME  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY   ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB29  = (  select top 1    trtd.FROM_TIME  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB357'  and   FROM_TIME  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY   ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB30  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB358'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB31  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB359'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB32  = (  select top 1    trtd.LAB_RESULT_TXT_VAL  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB360'   and   LAB_RESULT_TXT_VAL  is not null   and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY  ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB33  = (  select top 1    trtd.FROM_TIME  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB361'  and   FROM_TIME  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY   ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB34  = (  select top 1    trtd.FROM_TIME  from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB362'  and   FROM_TIME  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY   ) ;
					update rdb..tmp_RESULTED_TEST_DETAILS_FINAL set   LAB35  = (  select top 1    substring(trtd.TEST_RESULT_VAL_CD_DESC,1,50)   from rdb..tmp_RESULTED_TEST_DETAILS trtd where rdb..tmp_RESULTED_TEST_DETAILS_final.lab_rpt_local_id  = trtd.lab_rpt_local_id    and   LAB_TEST_CD  =  'LAB363'   and    TEST_RESULT_VAL_CD_DESC  is not null  and  rdb..tmp_RESULTED_TEST_DETAILS_final.RESULTED_LAB_TEST_KEY =  trtd.RESULTED_LAB_TEST_KEY ) ;




					/*
					CREATE TABLE ISOLATE_TRACKING_WITH_LAB330 AS
						SELECT TRACK_INFO.*,
							   LAB330.LAB330
						FROM rdb..tmp_RESULTED_TEST_DETAILS AS TRACK_INFO
						LEFT outer JOIN rdb..tmp_ISOLATE_TRACKING_LAB330_INIT AS LAB330
						ON TRACK_INFO.LAB_RPT_LOCAL_ID=LAB330.LAB_RPT_LOCAL_ID;
					QUIT;

					*/


  		     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = @PROC_STEP_NO+1;
			SET @PROC_STEP_NAME = ' GENERATING tmp_ISOLATE_TRACKING_WITH_LAB330'; 

	
		IF OBJECT_ID('rdb.dbo.tmp_ISOLATE_TRACKING_WITH_LAB330', 'U') IS NOT NULL 
		drop table rdb..tmp_ISOLATE_TRACKING_WITH_LAB330 ;



	SELECT TRACK_INFO.*,
		   LAB330.LAB330
    into rdb..tmp_ISOLATE_TRACKING_WITH_LAB330
	FROM rdb..tmp_RESULTED_TEST_DETAILS_FINAL AS TRACK_INFO
	LEFT outer JOIN rdb..tmp_ISOLATE_TRACKING_LAB330_INIT AS LAB330 	ON TRACK_INFO.LAB_RPT_LOCAL_ID=LAB330.LAB_RPT_LOCAL_ID
	;



--CREATE TABLE LAB101_INIT AS
	

  		     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = @PROC_STEP_NO+1;
			SET @PROC_STEP_NAME = ' GENERATING tmp_LAB101_INIT'; 

	
		IF OBJECT_ID('rdb.dbo.tmp_LAB101_INIT', 'U') IS NOT NULL 
		drop table rdb..tmp_LAB101_INIT ;
	
	SELECT 	OID as  'PROGRAM_JURISDICTION_OID',
			lab_test_cd_desc as  'RESULTED_LAB_TEST_CD_DESC',
			SPECIMEN_SRC_DESC,
			PARENT_TEST_PNTR,
			SPECIMEN_SRC_CD,
			RECORD_STATUS_CD,
			LAB_RPT_LOCAL_ID,
			RESULTED_LAB_TEST_KEY as  'RESULTED_LAB_TEST_KEY',
			LAB_RPT_CREATED_DT,
			SPECIMEN_COLLECTION_DT as  'SPECIMEN_COLLECTION_DT',
			LAB_RPT_RECEIVED_BY_PH_DT,
			LAB_TEST_DT,
			LAB_RPT_CREATED_DT as  LAB_RPT_CREATED_DT2,
			LAB.LAB330 AS  'PATIENT_STATUS',
			--LAB.LAB1 AS   'TRACK_ISO_IND',
			'Yes' AS   'TRACK_ISO_IND',
			/*LAB.LAB2 AS ISO_RECEIVED_IND 'ISO_RECEIVED_IND',*/
			rtrim(LAB.LAB3) AS  'ISO_RECEIVED_IND',
			LAB.LAB4  AS  'ISO_NO_RECEIVED_REASON',
			LAB.LAB5  AS  'ISO_NO_RECEIVED_REASON_OTH',
			LAB.LAB6 as  'ISO_RECEIVED_DT',
			LAB.LAB7  AS  'ISO_STATEID_NUM',
			LAB.LAB8  AS  'CASE_LAB_CONFIRMED_IND',
			LAB.LAB9  AS  'PULSENET_ISO_IND',
			LAB.LAB10  AS  'PFGE_PULSENET_SENT',
			LAB.LAB11 AS   'PFGE_PULSENET_ENZYME1',
			LAB.LAB12 AS   'PFGE_STATELAB_ENZYME1',
			LAB.LAB13 AS   'PFGE_PULSENET_ENZYME2',
			LAB.LAB14 AS   'PFGE_STATELAB_ENZYME2',
			LAB.LAB15 AS   'PFGE_PULSENET_ENZYME3',
			LAB.LAB16 AS   'PFGE_STATELAB_ENZYME3',
			LAB.LAB17 AS   'NARMS_ISO_IND',
			LAB.LAB18 AS   'NARMS_ISO_SENT_IND',
			LAB.LAB19 AS   'NARMS_NO_SENT_REASON',
			LAB.LAB20 AS   'NARMS_STATEID_NUM',
			LAB.LAB21 AS   'NARMS_EXPECTED_SHIP_DT',
			LAB.LAB22 AS   'NARMS_ACTUAL_SHIP_DT',
			LAB.LAB23 AS   'EIP_ISO_IND',
			LAB.LAB24 AS   'EIP_SPEC_AVAIL_IND',
			LAB.LAB25 AS   'EIP_SPEC_NO_REASON',
			LAB.LAB26 AS   'EIP_SPEC_NO_REASON_OTH',
			LAB.LAB27 AS   'EIP_SHIP_LOCATION',
			LAB.LAB28 AS   'EIP_EXPECTED_SHIP_DT',
			LAB.LAB29 AS   'EIP_ACTUAL_SHIP_DT',
			LAB.LAB30 AS   'EIP_SPEC_RESHIP_IND',
			LAB.LAB31 AS   'EIP_SPEC_RESHIP_REASON',
			LAB.LAB32 AS   'EIP_SPEC_RESHIP_REASON_OTH',
			LAB.LAB33 AS   'EIP_SPEC_EXPECTED_RESHIP_DT',
			LAB.LAB34 AS   'EIP_SPEC_ACTUAL_RESHIP_DT',
			LAB.LAB35 AS   'ISO_SENT_CDC_IND',
			 convert(datetime, replace(  LAB.LAB6, '-', ' '), 0) as ISO_RECEIVED_DATE	,
			 convert(datetime, replace( LAB.LAB21, '-', ' '), 0) as NARMS_EXPECTED_SHIP_DATE	,
			 convert(datetime, replace( LAB.LAB22, '-', ' '), 0) as NARMS_ACTUAL_SHIP_DATE	,
			 convert(datetime, replace( LAB.LAB28, '-', ' '), 0) as EIP_EXPECTED_SHIP_DATE	,
			 convert(datetime, replace( LAB.LAB29, '-', ' '), 0) as EIP_ACTUAL_SHIP_DATE	,
			 convert(datetime, replace( LAB.LAB33, '-', ' '), 0) as EIP_SPEC_EXPECTED_RESHIP_DATE	,
			 convert(datetime, replace( LAB.LAB34, '-', ' '), 0) as EIP_SPEC_ACTUAL_RESHIP_DATE,
			 cast(null as datetime) as EVENT_DATE			
    into rdb..tmp_LAB101_INIT
	FROM rdb..tmp_ISOLATE_TRACKING_WITH_LAB330 AS LAB
	ORDER BY LAB_RPT_LOCAL_ID
	;


	


/*
data LAB101_INIT;
  set LAB101_INIT;
  ISO_RECEIVED_DATE= input(put(trim(ISO_RECEIVED_DT),8.), mmddyy8.);
  NARMS_EXPECTED_SHIP_DATE= input(put(trim(NARMS_EXPECTED_SHIP_DT),8.), mmddyy8.);
  NARMS_ACTUAL_SHIP_DATE= input(put(trim(NARMS_ACTUAL_SHIP_DT),8.), mmddyy8.);
  EIP_EXPECTED_SHIP_DATE= input(put(trim(EIP_EXPECTED_SHIP_DT),8.), mmddyy8.);
  EIP_ACTUAL_SHIP_DATE= input(put(trim(EIP_ACTUAL_SHIP_DT),8.), mmddyy8.);
  EIP_SPEC_EXPECTED_RESHIP_DATE= input(put(trim(EIP_SPEC_EXPECTED_RESHIP_DT),8.), mmddyy8.);
  EIP_SPEC_ACTUAL_RESHIP_DATE= input(put(trim(EIP_SPEC_ACTUAL_RESHIP_DT),8.), mmddyy8.);
  run;

data LAB101_INIT;
  set LAB101_INIT;
ISO_RECEIVED_DATE = ISO_RECEIVED_DT;
NARMS_EXPECTED_SHIP_DATE=NARMS_EXPECTED_SHIP_DT;
NARMS_ACTUAL_SHIP_DATE=NARMS_ACTUAL_SHIP_DT;
EIP_EXPECTED_SHIP_DATE=EIP_EXPECTED_SHIP_DT;
EIP_ACTUAL_SHIP_DATE= EIP_ACTUAL_SHIP_DT;
EIP_SPEC_EXPECTED_RESHIP_DATE=EIP_SPEC_EXPECTED_RESHIP_DT;
EIP_SPEC_ACTUAL_RESHIP_DATE=EIP_SPEC_ACTUAL_RESHIP_DT;
run;
*/


/*
DATA LAB101_INIT; 
SET LAB101_INIT;
EVENT_DATE = SPECIMEN_COLLECTION_DT;

IF SPECIMEN_COLLECTION_DT ~= .  THEN 
		EVENT_DATE = SPECIMEN_COLLECTION_DT;
ELSE IF LAB_TEST_DT ~= . THEN 
		EVENT_DATE = LAB_TEST_DT;
ELSE IF LAB_RPT_RECEIVED_BY_PH_DT ~=. THEN 
		EVENT_DATE =LAB_RPT_RECEIVED_BY_PH_DT;
ELSE IF LAB_RPT_CREATED_DT ~=. THEN 
		EVENT_DATE =LAB_RPT_CREATED_DT;
RUN;

*/


update rdb..tmp_LAB101_INIT
set EVENT_DATE =  
CASE 
 WHEN SPECIMEN_COLLECTION_DT is not null    THEN SPECIMEN_COLLECTION_DT
 WHEN LAB_TEST_DT  is not  null               THEN LAB_TEST_DT
 WHEN LAB_RPT_RECEIVED_BY_PH_DT  is not  null THEN LAB_RPT_RECEIVED_BY_PH_DT
 WHEN LAB_RPT_CREATED_DT  is not  null        THEN LAB_RPT_CREATED_DT
ELSE NULL
END
;


/* Populate REPORTING_FACILITY_UID by joining with LAB100 on RESULTED_LAB_TEST_KEY */


--CREATE TABLE tmp_LAB101 AS 
 
  		     SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = @PROC_STEP_NO+1;
			SET @PROC_STEP_NAME = ' GENERATING TMP_LAB101_INIT2'; 

	
		IF OBJECT_ID('rdb.dbo.TMP_LAB101_INIT2', 'U') IS NOT NULL 
		drop table rdb..TMP_LAB101_INIT2 ;
	

  SELECT L101.*,
   L100.REPORTING_FACILITY_UID ,
   getdate() as  RDB_LAST_REFRESH_TIME
  INTO RDB..TMP_LAB101_INIT2 
  FROM rdb..tmp_LAB101_INIT AS L101, 
       RDB..LAB100 AS L100
  WHERE L101.RESULTED_LAB_TEST_KEY = L100.RESULTED_LAB_TEST_KEY
  ;


insert into rdb..LAB101
( [CASE_LAB_CONFIRMED_IND]
      ,[EIP_ACTUAL_SHIP_DATE]
      ,[EIP_EXPECTED_SHIP_DATE]
      ,[EIP_ISO_IND]
      ,[EIP_SHIP_LOCATION]
      ,[EIP_SPEC_ACTUAL_RESHIP_DATE]
      ,[EIP_SPEC_AVAIL_IND]
      ,[EIP_SPEC_EXPECTED_RESHIP_DATE]
      ,[EIP_SPEC_NO_REASON]
      ,[EIP_SPEC_NO_REASON_OTH]
      ,[EIP_SPEC_RESHIP_IND]
      ,[EIP_SPEC_RESHIP_REASON]
      ,[EIP_SPEC_RESHIP_REASON_OTH]
      ,[EVENT_DATE]
      ,[ISO_NO_RECEIVED_REASON]
      ,[ISO_NO_RECEIVED_REASON_OTH]
      ,[ISO_RECEIVED_DATE]
      ,[ISO_RECEIVED_IND]
      ,[ISO_STATEID_NUM]
      ,[LAB_RPT_LOCAL_ID]
      ,[NARMS_ACTUAL_SHIP_DATE]
      ,[NARMS_EXPECTED_SHIP_DATE]
      ,[NARMS_ISO_IND]
      ,[NARMS_ISO_SENT_IND]
      ,[NARMS_NO_SENT_REASON]
      ,[NARMS_STATEID_NUM]
      ,[PATIENT_STATUS]
      ,[PFGE_PULSENET_ENZYME1]
      ,[PFGE_PULSENET_ENZYME2]
      ,[PFGE_PULSENET_ENZYME3]
      ,[PFGE_PULSENET_SENT]
      ,[PFGE_STATELAB_ENZYME1]
      ,[PFGE_STATELAB_ENZYME2]
      ,[PFGE_STATELAB_ENZYME3]
      ,[PROGRAM_JURISDICTION_OID]
      ,[PULSENET_ISO_IND]
      ,[RECORD_STATUS_CD]
      ,[RESULTED_LAB_TEST_CD_DESC]
      ,[RESULTED_LAB_TEST_KEY]
      ,[SPECIMEN_COLLECTION_DT]
      ,[SPECIMEN_SRC_DESC]
      ,[SPECIMEN_SRC_CD]
      ,[TRACK_ISO_IND]
      ,[ISO_SENT_CDC_IND]
      ,[REPORTING_FACILITY_UID]
      ,[RDB_LAST_REFRESH_TIME]
	  )
   
SELECT  rtrim(substring(CASE_LAB_CONFIRMED_IND ,1,8)) 
			,EIP_ACTUAL_SHIP_DATE
			,EIP_EXPECTED_SHIP_DATE
			, rtrim(substring(EIP_ISO_IND ,1,8)) 
			, rtrim(substring(EIP_SHIP_LOCATION ,1,100)) 
			,EIP_SPEC_ACTUAL_RESHIP_DATE
			, rtrim(substring(EIP_SPEC_AVAIL_IND ,1,50)) 
			,EIP_SPEC_EXPECTED_RESHIP_DATE
			, rtrim(substring(EIP_SPEC_NO_REASON ,1,100)) 
			, rtrim(substring(EIP_SPEC_NO_REASON_OTH ,1,100)) 
			, rtrim(substring(EIP_SPEC_RESHIP_IND ,1,8)) 
			, rtrim(substring(EIP_SPEC_RESHIP_REASON ,1,100)) 
			, rtrim(substring(EIP_SPEC_RESHIP_REASON_OTH ,1,100)) 
			,EVENT_DATE
			, rtrim(substring(ISO_NO_RECEIVED_REASON ,1,100)) 
			, rtrim(substring(ISO_NO_RECEIVED_REASON_OTH ,1,100)) 
			,ISO_RECEIVED_DATE
			, rtrim(substring(ltrim(rtrim(ISO_RECEIVED_IND) ),1,8)) 
			, rtrim(substring(ISO_STATEID_NUM ,1,100)) 
			, rtrim(substring(LAB_RPT_LOCAL_ID ,1,50)) 
			,NARMS_ACTUAL_SHIP_DATE
			,NARMS_EXPECTED_SHIP_DATE
			, rtrim(substring(NARMS_ISO_IND ,1,8)) 
			, rtrim(substring(NARMS_ISO_SENT_IND ,1,8)) 
			, rtrim(substring(NARMS_NO_SENT_REASON ,1,100)) 
			, rtrim(substring(NARMS_STATEID_NUM ,1,100)) 
			, rtrim(substring(PATIENT_STATUS ,1,100)) 
			, rtrim(substring(PFGE_PULSENET_ENZYME1 ,1,100)) 
			, rtrim(substring(PFGE_PULSENET_ENZYME2 ,1,100)) 
			, rtrim(substring(PFGE_PULSENET_ENZYME3 ,1,100)) 
			, rtrim(substring(PFGE_PULSENET_SENT ,1,8)) 
			, rtrim(substring(PFGE_STATELAB_ENZYME1 ,1,100)) 
			, rtrim(substring(PFGE_STATELAB_ENZYME2 ,1,100)) 
			, rtrim(substring(PFGE_STATELAB_ENZYME3 ,1,100)) 
			,PROGRAM_JURISDICTION_OID
			, rtrim(substring(PULSENET_ISO_IND ,1,8)) 
			, rtrim(substring(RECORD_STATUS_CD ,1,8)) 
			, rtrim(substring(RESULTED_LAB_TEST_CD_DESC ,1,100)) 
			,RESULTED_LAB_TEST_KEY
			,SPECIMEN_COLLECTION_DT
			, rtrim(substring(SPECIMEN_SRC_DESC ,1,100)) 
			, rtrim(substring(SPECIMEN_SRC_CD ,1,100)) 
			, rtrim(substring(TRACK_ISO_IND ,1,8)) 
			, rtrim(substring(ltrim(rtrim(ISO_SENT_CDC_IND)) ,1,8)) 
			,REPORTING_FACILITY_UID
            ,RDB_LAST_REFRESH_TIME
  FROM [RDB].[dbo].tmp_LAB101_INIT2 
 ;



 
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB101_DATAMART','RDB.LAB101_DATAMART','START',  @PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);


			COMMIT TRANSACTION;

			 --IF OBJECT_ID('rdb.dbo.TMP_lab_test_resultInit', 'U') IS NOT NULL 
			 --drop table   rdb.dbo.TMP_lab_test_resultInit ;

        		IF OBJECT_ID('rdb..tmp_UPDATED_LAB101', 'U') IS NOT NULL  drop table    	rdb..tmp_UPDATED_LAB101	;
				IF OBJECT_ID('rdb..tmp_ISOLATE_TRACKING_INIT', 'U') IS NOT NULL  drop table    	rdb..tmp_ISOLATE_TRACKING_INIT	;
				IF OBJECT_ID('rdb..tmp_RESULTED_TEST_DETAIL1', 'U') IS NOT NULL  drop table    	rdb..tmp_RESULTED_TEST_DETAIL1	;
				IF OBJECT_ID('rdb..TMP_RESULTED_TEST_DETAILS', 'U') IS NOT NULL  drop table    	rdb..TMP_RESULTED_TEST_DETAILS	;
				IF OBJECT_ID('rdb..tmp_ISOLATE_TRACKING_LAB330_INIT', 'U') IS NOT NULL  drop table    	rdb..tmp_ISOLATE_TRACKING_LAB330_INIT	;
				IF OBJECT_ID('rdb..tmp_RESULTED_TEST_DETAILS_final', 'U') IS NOT NULL  drop table    	rdb..tmp_RESULTED_TEST_DETAILS_final	;
				IF OBJECT_ID('rdb..tmp_ISOLATE_TRACKING_WITH_LAB330', 'U') IS NOT NULL  drop table    	rdb..tmp_ISOLATE_TRACKING_WITH_LAB330	;
				IF OBJECT_ID('rdb..tmp_LAB101_INIT', 'U') IS NOT NULL  drop table    	rdb..tmp_LAB101_INIT	;
				IF OBJECT_ID('rdb..TMP_LAB101_INIT2', 'U') IS NOT NULL  drop table    	rdb..TMP_LAB101_INIT2	;


			   IF OBJECT_ID('rdb.dbo.TMP_LAB_TEST_final', 'U') IS NOT NULL   drop table  rdb.dbo.TMP_LAB_TEST_final ;
 			   IF OBJECT_ID('rdb.dbo.TMP_LAB_TEST', 'U') IS NOT NULL           drop table  rdb.dbo.TMP_LAB_TEST ;
			   IF OBJECT_ID('rdb.dbo.TMP_Lab_Test_Result', 'U') IS NOT NULL 	 drop table     rdb.dbo.TMP_Lab_Test_Result;
			   IF OBJECT_ID('rdb.dbo.TMP_TEST_RESULT_GROUPING', 'U') IS NOT NULL   drop table   [RDB].[dbo].[TMP_TEST_RESULT_GROUPING];

			   IF OBJECT_ID('rdb.dbo.TMP_Lab_Result_Val', 'U') IS NOT NULL 
    			 drop table   rdb..TMP_Lab_Result_Val;

    		   IF OBJECT_ID('rdb.dbo.TMP_New_Lab_Result_Comment_FINAL', 'U') IS NOT NULL 
	    		 drop table   [RDB].[dbo].[TMP_New_Lab_Result_Comment_FINAL];


                IF OBJECT_ID('rdb..TMP_LAB_RESULT_VAL_final', 'U') IS NOT NULL  drop table    	RDB..TMP_LAB_RESULT_VAL_final	;
				IF OBJECT_ID('rdb..TMP_TEST_RESULT_GROUPING', 'U') IS NOT NULL  drop table    	RDB..TMP_TEST_RESULT_GROUPING	;
				IF OBJECT_ID('rdb..TMP_D_LAB_TEST_N', 'U') IS NOT NULL  drop table    	RDB..TMP_D_LAB_TEST_N	;
				IF OBJECT_ID('rdb..TMP_LAB_TEST_RESULT', 'U') IS NOT NULL  drop table    	RDB..TMP_LAB_TEST_RESULT	;
				
			    IF OBJECT_ID('rdb.dbo.TMP_updated_participant', 'U') IS NOT NULL 
			         drop table  rdb..TMP_updated_participant;

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
						   'D_LAB101'
						   ,'RDB.D_LAB101'
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
           ,'D_LAB101'	
           ,'RDB.D_LAB101'
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









