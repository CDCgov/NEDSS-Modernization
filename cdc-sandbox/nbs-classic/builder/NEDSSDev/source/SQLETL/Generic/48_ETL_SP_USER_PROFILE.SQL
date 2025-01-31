USE RDB
GO
IF OBJECT_ID ('USER_PROFILE') IS NOT NULL 
	DROP TABLE USER_PROFILE;
CREATE TABLE dbo.USER_PROFILE
(
NEDSS_ENTRY_ID  BIGINT    NOT NULL,
  FIRST_NM        VARCHAR(50),
  LAST_UPD_TIME   DATETIME,
  LAST_NM         VARCHAR(50),
  USER_NM VARCHAR(100),
  PROVIDER_UID   BIGINT,
  PROVIDER_QUICK_CODE VARCHAR(50),
  PROVIDER_KEY BIGINT NOT NULL
CONSTRAINT pk_user_profile PRIMARY KEY CLUSTERED (nedss_entry_id)
) on [PRIMARY]


IF OBJECT_ID('rdb.dbo.TMP_PROVIDER_USER_DIMENSION', 'U') IS NOT NULL  ---Step1 
 	drop table rdb.dbo.TMP_PROVIDER_USER_DIMENSION;
IF OBJECT_ID('rdb.dbo.TMP_USER_PROVIDER', 'U') IS NOT NULL   ------------Step2
 	 drop table rdb.dbo.TMP_USER_PROVIDER;
IF OBJECT_ID('rdb.dbo.TMP_USER_PROVIDER_KEY', 'U') IS NOT NULL --------Step3  
 	drop table rdb.dbo.TMP_USER_PROVIDER_KEY;
IF OBJECT_ID('rdb.dbo.TMP_USER_PROFILE', 'U') IS NOT NULL   ------------Step4
 	drop table rdb.dbo.TMP_USER_PROFILE;
IF OBJECT_ID('rdb.dbo.USER_PROFILE_FINAL', 'U') IS NOT NULL   
 	drop table rdb.dbo.USER_PROFILE_FINAL;

USE [RDB]
GO

/****** Object:  StoredProcedure [dbo].[sp_USER_PROFILE]    Script Date: 2/22/2022 10:31:47 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


IF OBJECT_ID('rdb.[dbo].[sp_USER_PROFILE]', 'P') IS NOT NULL
BEGIN
DROP PROCEDURE [dbo].[sp_USER_PROFILE];
END;
Go

CREATE PROCEDURE [dbo].[sp_USER_PROFILE]
 @batch_id BIGINT
as
BEGIN 

/*
Select * from USER_PROFILE---17
Select * from rdb.dbo.USER_PROFILE_FINAL
Select * from job_batch_log
EXEC sp_USER_PROFILE 112345
Delete from rdb.[dbo].[job_flow_log] where batch_id =112345
Select * from rdb.[dbo].[job_flow_log] where batch_id =112345
DECLARE @batch_id BIGINT = 1123455
*/
         
			
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
												batch_id         ---------------@batch_id
											   ,[Dataflow_Name]  --------------'VACCINATION_RECORD'
											   ,[package_Name]   --------------'RDB.User_Profile'
											   ,[Status_Type]    ---------------START
											   ,[step_number]    ---------------@Proc_Step_no
											   ,[step_name]   ------------------@Proc_Step_Name=sp_start
											   ,[row_count] --------------------0
											   )
											   VALUES
											   (
											   @batch_id
											   ,'User_Profile'
											   ,'RDB.User_Profile'
											   ,'START'
											   ,@Proc_Step_no
											   ,@Proc_Step_Name
											   ,0  );
            COMMIT TRANSACTION;
		
											SELECT @batch_start_time = batch_start_dttm,@batch_end_time = batch_end_dttm
											FROM rdb.[dbo].[job_batch_log]
											--WHERE status_type = 'start' ;
											DECLARE  @USER_PROFILE_COUNT AS BIGINT=0;
											SET  @USER_PROFILE_COUNT= (SELECT COUNT(*) FROM rdb.dbo.USER_PROFILE);
											IF(@USER_PROFILE_COUNT=0)
											   SET @batch_start_time ='01-01-1990'

-------------------------------------------------1. CREATE TABLE TMP_PROVIDER_USER_DIMENSION---------------------------------------------------------------------------

			BEGIN TRANSACTION;
											SET @Proc_Step_name='TMP_PROVIDER_USER_DIMENSION';
											SET @Proc_Step_no = 2;

											IF OBJECT_ID('rdb.dbo.TMP_PROVIDER_USER_DIMENSION', 'U') IS NOT NULL   
 											   drop table rdb.dbo.TMP_PROVIDER_USER_DIMENSION;


												SELECT  
												substring(RTRIM(LTRIM(USER_FIRST_NM)) ,1,50)AS FIRST_NM,
												substring(RTRIM(LTRIM(USER_LAST_NM)) ,1,50)AS LAST_NM,
												A.LAST_CHG_TIME AS LAST_UPDT_TIME, 
												NEDSS_ENTRY_ID, 
												PROVIDER_UID
											  INTO TMP_PROVIDER_USER_DIMENSION
												FROM NBS_ODSE.dbo.AUTH_USER A
												LEFT JOIN NBS_ODSE.DBO.PERSON 
												ON PERSON.person_uid=A.PROVIDER_UID
												WHERE A.LAST_CHG_TIME>= @batch_start_time	AND A.LAST_CHG_TIME <  @batch_end_time---- Incremental 
												OR PERSON.LAST_CHG_TIME>= @batch_start_time	AND PERSON.LAST_CHG_TIME <  @batch_end_time---- Incremental 
										  
												ORDER BY NEDSS_ENTRY_ID;
											SELECT @ROWCOUNT_NO = @@ROWCOUNT;

										INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
										(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
										VALUES(@BATCH_ID,'User_Profile','RDB.User_Profile','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;
--Select * from TMP_PROVIDER_USER_DIMENSION
-------------------------------------------------2. CREATE TABLE TMP_USER_PROVIDER---------------------------------------------------
            BEGIN TRANSACTION;
												SET @Proc_Step_name='TMP_USER_PROVIDER';
												SET @Proc_Step_no = 3;
								
								                IF OBJECT_ID('rdb.dbo.TMP_USER_PROVIDER', 'U') IS NOT NULL   
 												   drop table rdb.dbo.TMP_USER_PROVIDER;

												SELECT distinct
												PROVIDER_UID
												INTO TMP_USER_PROVIDER
												FROM TMP_PROVIDER_USER_DIMENSION where PROVIDER_UID is not null;

												 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

												INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
												(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
												VALUES(@BATCH_ID,'User_Profile','RDB.User_Profile','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;
-----------------------------------------------3. CREATE TMP_USER_PROVIDER_KEY----------------------------------------------------------------------------------
           BEGIN TRANSACTION;
											SET @Proc_Step_name='TMP_USER_PROVIDER_KEY';
											SET @Proc_Step_no = 4;

								             IF OBJECT_ID('rdb.dbo.TMP_USER_PROVIDER_KEY', 'U') IS NOT NULL   
 												drop table rdb.dbo.TMP_USER_PROVIDER_KEY;

												SELECT
												T.PROVIDER_UID, 
												D.PROVIDER_KEY, 
												substring([PROVIDER_QUICK_CODE] ,1,50)AS PROVIDER_QUICK_CODE
												INTO TMP_USER_PROVIDER_KEY
												FROM TMP_USER_PROVIDER T
												INNER JOIN rdb.dbo.D_PROVIDER D on
												T.PROVIDER_UID = D.PROVIDER_UID;
									
										SELECT @ROWCOUNT_NO = @@ROWCOUNT;

										INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
										(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
										VALUES(@BATCH_ID,'User_Profile','RDB.User_Profile','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO)

			COMMIT TRANSACTION;
----Select * from TMP_USER_PROVIDER_KEY
-----------------------------------------------4. CREATE TMP_USER_PROFILE----------------------------------------------------------------------------------

		   BEGIN TRANSACTION;
								SET @Proc_Step_name='TMP_USER_PROFILE';
								SET @Proc_Step_no = 5;

								
								             IF OBJECT_ID('rdb.dbo.TMP_USER_PROFILE', 'U') IS NOT NULL   
 												drop table rdb.dbo.TMP_USER_PROFILE;

											 IF OBJECT_ID('rdb.dbo.USER_PROFILE_FINAL', 'U') IS NOT NULL   
 												drop table rdb.dbo.USER_PROFILE_FINAL;

												SELECT
												P.*, T.provider_key, T.PROVIDER_QUICK_CODE
												INTO TMP_USER_PROFILE
												from TMP_PROVIDER_USER_DIMENSION P
												LEFT JOIN  TMP_USER_PROVIDER_KEY T ON  P.PROVIDER_UID = T.PROVIDER_UID
												ORDER BY NEDSS_ENTRY_ID;
										
											   SELECT FIRST_NM,	LAST_NM	,LAST_UPDT_TIME	,NEDSS_ENTRY_ID	,PROVIDER_UID,PROVIDER_KEY,PROVIDER_QUICK_CODE	
											   
											   into USER_PROFILE_FINAL				
											   FROM
											   (
											   SELECT *, 
											   ROW_NUMBER () OVER (PARTITION BY NEDSS_ENTRY_ID  order by NEDSS_ENTRY_ID ) rowid
											   FROM rdb.dbo.TMP_USER_PROFILE
											   ) AS CTE WHERE rowid=1;

											   UPDATE USER_PROFILE_FINAL
											   SET Provider_Key = 1 where Provider_key is null

									SELECT @ROWCOUNT_NO = @@ROWCOUNT;
									ALTER TABLE rdb.dbo.USER_PROFILE_FINAL ADD USER_NM VARCHAR(100);
							
									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID,'User_Profile','RDB.User_Profile','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 

			COMMIT TRANSACTION;

-----------------------------------------------5. Update USER FULL NAME ----------------------------------------------------------------------------------
		  	   BEGIN TRANSACTION;
								SET @Proc_Step_name='USER_PROFILE FULL NAME';
								SET @Proc_Step_no = 6;

							
								update  rdb.dbo.USER_PROFILE_FINAL 
								SET USER_NM=CAST(substring(LAST_NM ,1,49) + ', ' +substring(FIRST_NM ,1,49) as varchar(100))
								where  LEN(LAST_NM)> 0 AND LEN(FIRST_NM)>0 
								;

								
								 SELECT @ROWCOUNT_NO = @@ROWCOUNT;
		
								update  rdb.dbo.USER_PROFILE_FINAL 
								SET USER_NM=CAST(substring(FIRST_NM ,1,49) as varchar(100))
								where  LEN(LAST_NM)<= 0 AND LEN(FIRST_NM)>0 
								;

								
								 SELECT @ROWCOUNT_NO = @@ROWCOUNT + @ROWCOUNT_NO;

								update  rdb.dbo.USER_PROFILE_FINAL 
								SET USER_NM=CAST(substring(LAST_NM ,1,49) as varchar(100))
								where  LEN(LAST_NM)> 0 AND LEN(FIRST_NM)<=0 
								;


								 SELECT @ROWCOUNT_NO = @@ROWCOUNT + @ROWCOUNT_NO;

									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID,'User_Profile','RDB.User_Profile','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 
	COMMIT TRANSACTION;

-----------------------------------------------6. Update and Insert in Table USER_PROFILE----------------------------------------------------------------------------------

		   BEGIN TRANSACTION;
								SET @Proc_Step_name='USER_PROFILE';
								SET @Proc_Step_no = 7;

					                     INSERT INTO USER_PROFILE
												   ( 
												FIRST_NM, 
												LAST_NM, 
												LAST_UPD_TIME, 
												NEDSS_ENTRY_ID, 
												PROVIDER_UID,
												PROVIDER_KEY,
												PROVIDER_QUICK_CODE,
												USER_NM
											       )
										 
												SELECT 
												FIRST_NM, 
												LAST_NM, 
												LAST_UPDT_TIME, 
												NEDSS_ENTRY_ID, 
												PROVIDER_UID,
												PROVIDER_KEY,
												PROVIDER_QUICK_CODE,
												USER_NM
									            FROM USER_PROFILE_FINAL T
												
												 WHERE NOT EXISTS
												(
												SELECT FIRST_NM, 
												LAST_NM, 
												LAST_UPDT_TIME, 
												NEDSS_ENTRY_ID, 
												PROVIDER_UID,
												PROVIDER_KEY,
												PROVIDER_QUICK_CODE,
												USER_NM
													FROM rdb.dbo.[USER_PROFILE] with (nolock)
												    WHERE [NEDSS_ENTRY_ID] = T.[NEDSS_ENTRY_ID] 
												)ORDER BY [NEDSS_ENTRY_ID]


												UPDATE rdb.dbo.USER_PROFILE
												SET FIRST_NM            = F.FIRST_NM,   ----------1
												    LAST_NM             = F.LAST_NM,   -----------2
													LAST_UPD_TIME       = F.LAST_UPDT_TIME,   ----3
													PROVIDER_QUICK_CODE = F.PROVIDER_QUICK_CODE,--4
													PROVIDER_UID        = F.PROVIDER_UID,   ------5
													PROVIDER_KEY        = F.PROVIDER_KEY,   ------6
													USER_NM             =F.USER_NM         -------7
                                                FROM USER_PROFILE_FINAL F
												WHERE F.nedss_entry_id =rdb.dbo.USER_PROFILE.NEDSS_ENTRY_ID 
												

										 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID,'User_Profile','RDB.User_Profile','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO); 
 
			COMMIT TRANSACTION;
----SELECT * FROM USER_PROFILE

----------------------------------------------Dropping all tmp tables.------------------------------

IF OBJECT_ID('rdb.dbo.TMP_PROVIDER_USER_DIMENSION', 'U') IS NOT NULL  ---Step1 
 	drop table rdb.dbo.TMP_PROVIDER_USER_DIMENSION;
IF OBJECT_ID('rdb.dbo.TMP_USER_PROVIDER', 'U') IS NOT NULL   ------------Step2
 	 drop table rdb.dbo.TMP_USER_PROVIDER;
IF OBJECT_ID('rdb.dbo.TMP_USER_PROVIDER_KEY', 'U') IS NOT NULL --------Step3  
 	drop table rdb.dbo.TMP_USER_PROVIDER_KEY;
IF OBJECT_ID('rdb.dbo.TMP_USER_PROFILE', 'U') IS NOT NULL   ------------Step4
 	drop table rdb.dbo.TMP_USER_PROFILE;
IF OBJECT_ID('rdb.dbo.USER_PROFILE_FINAL', 'U') IS NOT NULL   
 	drop table rdb.dbo.USER_PROFILE_FINAL;
	
----------------------------------------------------------------------------------------------------------------		   
		    BEGIN TRANSACTION ;
			
									SET @Proc_Step_no = 8
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
										   'User_Profile'
										   ,'RDB.User_Profile'
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
					   , 'User_Profile'
					   ,'RDB.User_Profile'
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


