/*


IF object_id('LAB_RESULT_VAL_LARGE_TXT', 'U') is  null
  BEGIN 
	CREATE TABLE rdb.[dbo].LAB_RESULT_VAL_LARGE_TXT(
		 LAB_RESULT_VAL_LARGE_TXT_KEY bigint NULL,
		 LAB_RPT_LOCAL_ID [varchar](50) NULL,
		 LAB_RPT_UID [bigint]  NULL,
		LAB_RESULT_LARGE_TXT_VAL [varchar](max) NULL
		)
		;
  END



	

	IF NOT EXISTS(SELECT 1 FROM sys.columns 
				  WHERE Name = N'LAB_RESULT_VAL_LARGE_TXT_KEY'
				  AND Object_ID = Object_ID(N'dbo.LAB_TEST_RESULT'))
	BEGIN
		ALTER TABLE rdb.dbo.LAB_TEST_RESULT 
				Add LAB_RESULT_VAL_LARGE_TXT_KEY  bigint NULL;
	END


	
	IF NOT EXISTS(SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Sequence_LAB_RESULT_VAL_LARGE_TXT]') AND type = 'SO')
    BEGIN
	   CREATE SEQUENCE Sequence_LAB_RESULT_VAL_LARGE_TXT
			AS INT
			START WITH 1000
			INCREMENT BY 1;
     END

	-- exec rdb.[dbo].Lab_Result_Val_Large_Txt_sp 999;

	 
*/


USE [RDB]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



IF OBJECT_ID('rdb.dbo.Lab_Result_Val_Large_Txt_sp', 'P') IS NOT NULL
				BEGIN
				DROP PROCEDURE dbo.Lab_Result_Val_Large_Txt_sp;
				END;

GO



CREATE PROCEDURE [dbo].Lab_Result_Val_Large_Txt_sp
  @batch_id BIGINT
 as

  BEGIN

  --
--UPDATE ACTIVITY_LOG_DETAIL SET 
--START_DATE=DATETIME();
-- dec
    DECLARE @RowCount_no INT ;
    DECLARE @Proc_Step_no FLOAT = 0 ;
    DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
	DECLARE @batch_start_time datetime2(7) = null ;
	DECLARE @batch_end_time datetime2(7) = null ;
 
 BEGIN TRY
    
	SET @Proc_Step_no = 1;
	SET @Proc_Step_Name = 'SP_Start';

	

	
	BEGIN TRANSACTION;
				--create table Lab_Test_Result1 as 

			 SELECT @ROWCOUNT_NO = @@ROWCOUNT;
		
		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB_RESULT_VAL_LARGE_TXT','RDB.LAB_RESULT_VAL_LARGE_TXT','START', @PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);
	COMMIT TRANSACTION;

   	BEGIN TRANSACTION;

--create table updated_observation_List as

 
     		SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' Deleting  LAB_RESULT_VAL_LARGE_TXT '; 
 


         delete from  rdb.[dbo].LAB_RESULT_VAL_LARGE_TXT ;


			 SELECT @ROWCOUNT_NO = @@ROWCOUNT;
		
		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB_RESULT_VAL_LARGE_TXT','RDB.LAB_RESULT_VAL_LARGE_TXT','START', @PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);
	COMMIT TRANSACTION;

   	BEGIN TRANSACTION;

--create table updated_observation_List as

 
     		SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' Reset   Sequence Counter '; 


			ALTER SEQUENCE Sequence_LAB_RESULT_VAL_LARGE_TXT RESTART WITH 1000;

			 SELECT @ROWCOUNT_NO = @@ROWCOUNT;
		
		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB_RESULT_VAL_LARGE_TXT','RDB.LAB_RESULT_VAL_LARGE_TXT','START', @PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);
	COMMIT TRANSACTION;

   	BEGIN TRANSACTION;

--create table updated_observation_List as

 
     		SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' Generating  tmp_LAB_RESULT_VAL_LARGE_TXT_key1 '; 
 
 
						
		  IF OBJECT_ID('rdb.dbo.tmp_LAB_RESULT_VAL_LARGE_TXT_key1', 'U') IS NOT NULL   
 						drop table rdb.dbo.tmp_LAB_RESULT_VAL_LARGE_TXT_key1;




 	        select distinct 
				  -- NEXT VALUE FOR Sequence_LAB_RESULT_VAL_LARGE_TXT as LAB_RESULT_VAL_LARGE_TXT_KEY,
				    rootObs.local_id        as  LAB_RPT_LOCAL_ID,
				   rootObs.observation_uid as LAB_RPT_UID  
            into rdb.dbo.tmp_LAB_RESULT_VAL_LARGE_TXT_key1
			from nbs_odse..observation rootObs with (nolock) 
				inner join nbs_odse..Act_relationship ar with (nolock)  on ar.target_act_uid =rootObs.observation_uid
				inner join nbs_odse..Obs_value_txt Res  with (nolock)  on ar.source_act_uid =Res.observation_uid
			 where obs_domain_cd_st_1='Order' and ctrl_cd_display_form='LabReport'
				and res.value_large_txt is not null
				group by rootObs.local_id ,
				   rootObs.observation_uid   
				;


			 SELECT @ROWCOUNT_NO = @@ROWCOUNT;
		
		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB_RESULT_VAL_LARGE_TXT','RDB.LAB_RESULT_VAL_LARGE_TXT','START', @PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);

     	COMMIT TRANSACTION;

   	BEGIN TRANSACTION;

--create table updated_observation_List as

 
     		SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' Generating  tmp_LAB_RESULT_VAL_LARGE_TXT_key '; 
 
 
						
		  IF OBJECT_ID('rdb.dbo.tmp_LAB_RESULT_VAL_LARGE_TXT_key', 'U') IS NOT NULL   
 						drop table rdb.dbo.tmp_LAB_RESULT_VAL_LARGE_TXT_key;




 	        select
				    NEXT VALUE FOR Sequence_LAB_RESULT_VAL_LARGE_TXT as LAB_RESULT_VAL_LARGE_TXT_KEY,
					*  
            into rdb.dbo.tmp_LAB_RESULT_VAL_LARGE_TXT_key
			from rdb.dbo.tmp_LAB_RESULT_VAL_LARGE_TXT_key1
			;


			
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
		
		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB_RESULT_VAL_LARGE_TXT','RDB.LAB_RESULT_VAL_LARGE_TXT','START', @PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);

     	COMMIT TRANSACTION;

   	   BEGIN TRANSACTION;

--create table updated_observation_List as

 
     		SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
			SET @PROC_STEP_NAME = ' Inserting  LAB_RESULT_VAL_LARGE_TXT '; 
 


			insert into rdb.[dbo].LAB_RESULT_VAL_LARGE_TXT (
			[LAB_RESULT_VAL_LARGE_TXT_KEY]
				  ,[LAB_RPT_LOCAL_ID]
				  ,[LAB_RPT_UID]
				  ,[LAB_RESULT_LARGE_TXT_VAL]
				  )
			select distinct
				   sqkey.LAB_RESULT_VAL_LARGE_TXT_KEY                    as LAB_RESULT_VAL_LARGE_TXT_KEY,
				   rootObs.local_id        as  LAB_RPT_LOCAL_ID,
				   rootObs.observation_uid as LAB_RPT_UID,  
				   Res.value_large_txt    as LAB_RESULT_LARGE_TXT_VAL
			from nbs_odse..observation rootObs  with (nolock) 
				inner join nbs_odse..Act_relationship ar  with (nolock) on ar.target_act_uid =rootObs.observation_uid
				inner join nbs_odse..Obs_value_txt Res  with (nolock) on ar.source_act_uid =Res.observation_uid
				inner join rdb.dbo.tmp_LAB_RESULT_VAL_LARGE_TXT_key sqkey on sqkey.LAB_RPT_LOCAL_ID = rootObs.local_id and sqkey.LAB_RPT_UID = rootObs.observation_uid
			 where obs_domain_cd_st_1='Order' and ctrl_cd_display_form='LabReport'
				and value_large_txt is not null
				;


				--select * from  rdb.[dbo].LAB_RESULT_VAL_LARGE_TXT;



			SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB_RESULT_VAL_LARGE_TXT','RDB.LAB_RESULT_VAL_LARGE_TXT','START',  @PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);


			COMMIT TRANSACTION;


			
    	    BEGIN TRANSACTION;


     			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
				SET @PROC_STEP_NAME = ' Creating  TMP_LAB_RESULT_VAL_LARGE_TXT_UNQ keys '; 
 



			
				 IF OBJECT_ID('rdb.dbo.TMP_LAB_RESULT_VAL_LARGE_TXT_UNQ', 'U') IS NOT NULL   
 										drop table rdb.dbo.TMP_LAB_RESULT_VAL_LARGE_TXT_UNQ;



				select distinct LAB_RESULT_VAL_LARGE_TXT_KEY,LAB_RPT_LOCAL_ID,LAB_RPT_UID
					into  rdb.[dbo].TMP_LAB_RESULT_VAL_LARGE_TXT_UNQ
					from  rdb.[dbo].LAB_RESULT_VAL_LARGE_TXT ;




			SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB_RESULT_VAL_LARGE_TXT','RDB.LAB_RESULT_VAL_LARGE_TXT','START',  @PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);


			COMMIT TRANSACTION;


			
    	    BEGIN TRANSACTION;


     			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
				SET @PROC_STEP_NAME = ' Updating   LAB_TEST_RESULT LAB_RESULT_VAL_LARGE_TXT_KEY keys to null  '; 
 

			   UPDATE ltr
					 set  ltr.LAB_RESULT_VAL_LARGE_TXT_KEY = null
				  from  rdb.dbo.LAB_TEST_RESULT ltr
				  where LAB_RESULT_VAL_LARGE_TXT_KEY is not null;



			SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'LAB_RESULT_VAL_LARGE_TXT','RDB.LAB_RESULT_VAL_LARGE_TXT','START',  @PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);


			COMMIT TRANSACTION;


			
    	    BEGIN TRANSACTION;


     			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1;
				SET @PROC_STEP_NAME = ' Updating   LAB_TEST_RESULT LAB_RESULT_VAL_LARGE_TXT_KEYs  '; 
 


				  UPDATE ltr
					 set  ltr.LAB_RESULT_VAL_LARGE_TXT_KEY = llt.LAB_RESULT_VAL_LARGE_TXT_KEY
				  from  rdb.dbo.LAB_TEST_RESULT ltr
					 right outer join  rdb.dbo.LAB_TEST  lt on ltr.lab_test_key = lt.lab_test_key 
					 right outer join  rdb.[dbo].TMP_LAB_RESULT_VAL_LARGE_TXT_UNQ  llt on llt.LAB_RPT_LOCAL_ID = lt.LAB_RPT_LOCAL_ID and llt.LAB_RPT_UID = lt.LAB_RPT_UID
			  
				;



				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LAB_RESULT_VAL_LARGE_TXT','RDB.LAB_RESULT_VAL_LARGE_TXT','START',  @PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);


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
						   'LAB_RESULT_VAL_LARGE_TXT'
						   ,'RDB.LAB_RESULT_VAL_LARGE_TXT'
						   ,'COMPLETE'
						   ,@Proc_Step_no
						   ,@Proc_Step_name
						   ,0
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
           ,'LAB_RESULT_VAL_LARGE_TXT'	
           ,'RDB.LAB_RESULT_VAL_LARGE_TXT'
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













