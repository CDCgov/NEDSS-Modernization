/*
 %include etlpgm (LDF_Foodborne_Datamart.sas)
 It uses incremental data generated from stored procedure sp_LDF_DIMENSIONAL_DATA (generated table S_LDF_DIMENSIONAL_DATA)
*/

USE [RDB]
GO

	IF OBJECT_ID('RDB.dbo.TMP_BASE_FOODBORNE', 'U') IS NOT NULL  
		 DROP TABLE RDB.dbo.TMP_BASE_FOODBORNE; 

	IF OBJECT_ID('RDB.dbo.TMP_LINKED_FOODBORNE', 'U') IS NOT NULL  
			 DROP TABLE RDB.dbo.TMP_LINKED_FOODBORNE; 

	IF OBJECT_ID('RDB.dbo.TMP_ALL_FOODBORNE', 'U') IS NOT NULL  
			 DROP TABLE RDB.dbo.TMP_ALL_FOODBORNE; 

	IF OBJECT_ID('RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL', 'U') IS NOT NULL  
			 DROP TABLE RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL; 

	IF OBJECT_ID('RDB.dbo.TMP_ALL_FOODBORNE_TA', 'U') IS NOT NULL  
			 DROP TABLE RDB.dbo.TMP_ALL_FOODBORNE_TA; 

	IF OBJECT_ID('RDB.dbo.TMP_FOODBORNE_TA', 'U') IS NOT NULL  
			 DROP TABLE RDB.dbo.TMP_FOODBORNE_TA; 

	IF OBJECT_ID('RDB.dbo.TMP_FOODBORNE_SHORT_COL', 'U') IS NOT NULL  
			 DROP TABLE RDB.dbo.TMP_FOODBORNE_SHORT_COL; 
			 
	IF OBJECT_ID('RDB.dbo.TMP_FOODBORNE', 'U') IS NOT NULL  
			 DROP TABLE RDB.dbo.TMP_FOODBORNE; 
						 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO

IF OBJECT_ID('dbo.sp_LDF_FOODBORNE_DATAMART', 'P') IS NOT NULL
    DROP PROCEDURE [dbo].sp_LDF_FOODBORNE_DATAMART;
GO

CREATE PROCEDURE [dbo].sp_LDF_FOODBORNE_DATAMART
  @batch_id BIGINT
 as

  BEGIN

    DECLARE @RowCount_no INT ;
    DECLARE @Proc_Step_no FLOAT = 0 ;
    DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
	DECLARE @batch_start_time datetime2(7) = null ;
	DECLARE @batch_end_time datetime2(7) = null ;
 
	DECLARE @cols  AS NVARCHAR(MAX)='';
	DECLARE @query AS NVARCHAR(MAX)='';

	DECLARE @Alterdynamiccolumnlist varchar(max)=''
	DECLARE @dynamiccolumnUpdate varchar(max)=''
	DECLARE @dynamiccolumninsert varchar(max)=''
	
	DECLARE @dynamiccolumnList varchar(max)=''	--insert into LDF_FOODBORNE table from TMP_FOODBORNE table
	DECLARE @count BIGINT;
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
           ,'LDF_FOODBORNE'
           ,'RDB.LDF_FOODBORNE'
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
    COMMIT TRANSACTION;
	
	
	SELECT @batch_start_time = batch_start_dttm,@batch_end_time = batch_end_dttm
	FROM [dbo].[job_batch_log]
	WHERE status_type = 'start';

	SET @count =
	(
	SELECT COUNT(1)
		FROM RDB.dbo.S_LDF_DIMENSIONAL_DATA with (nolock)
			 INNER JOIN RDB.dbo.LDF_DATAMART_TABLE_REF with (nolock) ON S_LDF_DIMENSIONAL_DATA.PHC_CD = RDB.dbo.LDF_DATAMART_TABLE_REF.condition_cd
														  AND DATAMART_NAME = 'LDF_FOODBORNE'
	);	
		
	IF (@count > 0)

	BEGIN

		BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 2;
				SET @PROC_STEP_NAME = ' GENERATING TMP_BASE_FOODBORNE'; 

			IF OBJECT_ID('RDB.dbo.TMP_BASE_FOODBORNE', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_BASE_FOODBORNE;

				SELECT S_LDF_DIMENSIONAL_DATA.*
							INTO RDB.dbo.TMP_BASE_FOODBORNE
							FROM RDB.dbo.S_LDF_DIMENSIONAL_DATA  with (nolock)
								 INNER JOIN RDB.dbo.LDF_DATAMART_TABLE_REF  with (nolock) ON PHC_CD = LDF_DATAMART_TABLE_REF.CONDITION_CD
																			  AND DATAMART_NAME = 'LDF_FOODBORNE';

				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_FOODBORNE','RDB.LDF_FOODBORNE','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
			
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 3;
				SET @PROC_STEP_NAME = ' GENERATING TMP_LINKED_FOODBORNE'; 
				
			
				IF OBJECT_ID('RDB.dbo.TMP_LINKED_FOODBORNE', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_LINKED_FOODBORNE;


						SELECT GEN_LDF.*, 
							INV.INVESTIGATION_KEY, 
							INV.INV_LOCAL_ID 'INVESTIGATION_LOCAL_ID', 
							INV.CASE_OID 'PROGRAM_JURISDICTION_OID',
							GEN.PATIENT_KEY,
							PATIENT.PATIENT_LOCAL_ID 'PATIENT_LOCAL_ID',
							CONDITION.CONDITION_SHORT_NM 'DISEASE_NAME'
						INTO  RDB.dbo.TMP_LINKED_FOODBORNE
						FROM
							RDB.dbo.TMP_BASE_FOODBORNE GEN_LDF
							INNER JOIN  RDB.dbo.INVESTIGATION INV  with (nolock)
						ON  
							GEN_LDF.INVESTIGATION_UID=INV.CASE_UID 
						INNER JOIN RDB.dbo.GENERIC_CASE GEN  with (nolock)
						ON 
							GEN.INVESTIGATION_KEY=INV.INVESTIGATION_KEY
						INNER JOIN RDB.dbo.CONDITION  with (nolock)
						ON 
							CONDITION.CONDITION_KEY= GEN.CONDITION_KEY
						INNER JOIN RDB.dbo.D_PATIENT PATIENT  with (nolock)
						ON 
							PATIENT.PATIENT_KEY=GEN.PATIENT_KEY
						ORDER BY 
							INVESTIGATION_UID;
						
								 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_FOODBORNE','RDB.LDF_FOODBORNE','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
			
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 4;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_FOODBORNE'; 

			-- CREATE TABLE ALL_FOODBORNE

				IF OBJECT_ID('RDB.dbo.TMP_ALL_FOODBORNE', 'U') IS NOT NULL  
						 DROP TABLE RDB.dbo.TMP_ALL_FOODBORNE;

						SELECT A.*, 
						B.DATAMART_COLUMN_NM 'DM',
						A.phc_cd 'DISEASE_CD',
						A.page_set 'DISEASE_NM'
						INTO RDB.dbo.TMP_ALL_FOODBORNE
						FROM RDB.dbo.LDF_DATAMART_COLUMN_REF  B  with (nolock)
						FULL OUTER JOIN RDB.dbo.TMP_LINKED_FOODBORNE A  with (nolock)
						ON A.LDF_UID= B.LDF_UID WHERE
						(B.LDF_PAGE_SET ='OTHER'
						OR B.CONDITION_CD IN (SELECT CONDITION_CD FROM 
							RDB.dbo.LDF_DATAMART_TABLE_REF WHERE DATAMART_NAME = 'LDF_FOODBORNE') 
						)
						ORDER BY INVESTIGATION_UID;

					
						UPDATE RDB.dbo.TMP_ALL_FOODBORNE
						SET DISEASE_CD = CONDITION_CD
						WHERE DATALENGTH(REPLACE(CONDITION_CD, ' ', ''))>1;  
					
						UPDATE RDB.dbo.TMP_ALL_FOODBORNE
						SET DISEASE_CD = PHC_CD
						WHERE DATALENGTH(REPLACE(CONDITION_CD, ' ', ''))<=1;  

						UPDATE RDB.dbo.TMP_ALL_FOODBORNE
						SET DISEASE_NM= PAGE_SET
						WHERE DATALENGTH(DISEASE_NM)<2;

						UPDATE RDB.dbo.TMP_ALL_FOODBORNE
						SET DATAMART_COLUMN_NM=DM
						WHERE DATALENGTH(DM)>2;  


					SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_FOODBORNE','RDB.LDF_FOODBORNE','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
			
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 5;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_FOODBORNE_SHORT_COL'; 
					
				IF OBJECT_ID('RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL', 'U') IS NOT NULL  
						 DROP TABLE RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL;

					
						SELECT INVESTIGATION_KEY,
								INVESTIGATION_LOCAL_ID,
								PROGRAM_JURISDICTION_OID,
								PATIENT_KEY,
								PATIENT_LOCAL_ID,
								DISEASE_NAME,
								DISEASE_CD,
								DATAMART_COLUMN_NM,
								col1  
						INTO RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL 
						FROM RDB.dbo.TMP_ALL_FOODBORNE  with (nolock)
						WHERE data_type IN ('CV', 'ST');

					
					SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_FOODBORNE','RDB.LDF_FOODBORNE','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
			
				
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 6;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_FOODBORNE_TA'; 

					IF OBJECT_ID('RDB.dbo.TMP_ALL_FOODBORNE_TA', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_ALL_FOODBORNE_TA;

					
						SELECT INVESTIGATION_KEY,
								INVESTIGATION_LOCAL_ID,
								PROGRAM_JURISDICTION_OID,
								PATIENT_KEY,
								PATIENT_LOCAL_ID,
								DISEASE_NAME,
								DISEASE_CD,
								DATAMART_COLUMN_NM,
								col1  
						INTO RDB.dbo.TMP_ALL_FOODBORNE_TA
						FROM RDB.dbo.TMP_ALL_FOODBORNE  with (nolock)
						WHERE data_type IN ('LIST_ST');   
					
			
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_FOODBORNE','RDB.LDF_FOODBORNE','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
			
				---- create table ldf_base_clinical_translated
				
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 7;
				SET @PROC_STEP_NAME = ' GENERATING TMP_FOODBORNE_TA'; 
				set @count = (SELECT count(*) FROM TMP_ALL_FOODBORNE_TA);
				IF @count > 0
				BEGIN
					IF OBJECT_ID('RDB.dbo.TMP_FOODBORNE_TA', 'U') IS NOT NULL  
							DROP TABLE RDB.dbo.TMP_FOODBORNE_TA;

						
							ALTER TABLE TMP_ALL_FOODBORNE_TA
							ADD ANSWERCOL varchar(8000);

							UPDATE TMP_ALL_FOODBORNE_TA
							SET ANSWERCOL = SUBSTRING(COL1, 1, 8000); 

							ALTER TABLE TMP_ALL_FOODBORNE_TA
							DROP COLUMN COL1;

							--DECLARE @cols  AS NVARCHAR(MAX)='';
							--DECLARE @query AS NVARCHAR(MAX)='';
							SET @cols='';
							SET @query='';

							SELECT @cols = @cols + QUOTENAME(DATAMART_COLUMN_NM) + ',' FROM (select distinct DATAMART_COLUMN_NM from TMP_ALL_FOODBORNE_TA ) as tmp
							select @cols = substring(@cols, 0, len(@cols)) --trim "," at end

							--PRINT CAST(@cols AS NVARCHAR(3000))
							set @query = 
							'SELECT *
							INTO TMP_FOODBORNE_TA
							fROM
							( 
							SELECT     INVESTIGATION_KEY,
									   INVESTIGATION_LOCAL_ID,
									   PROGRAM_JURISDICTION_OID,
									   PATIENT_KEY,
									   PATIENT_LOCAL_ID,
									   DISEASE_NAME,
									   DISEASE_CD,
									   DATAMART_COLUMN_NM,
									   ANSWERCOL
								
							FROM RDB.dbo.TMP_ALL_FOODBORNE_TA )
							as A 

							PIVOT ( MAX([ANSWERCOL]) FOR DATAMART_COLUMN_NM   IN (' + @cols + ')) AS PivotTable';
							execute(@query)
				
								 SELECT @ROWCOUNT_NO = @@ROWCOUNT;
				END	
				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_FOODBORNE','RDB.LDF_FOODBORNE','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
				
				-- If data does not exist create TMP_FOODBORNE_TA table same as TMP_ALL_FOODBORNE_TA, which will be used while merging table in step 9
				set @count = (SELECT count(*) FROM RDB.dbo.TMP_ALL_FOODBORNE_TA);
				IF @count = 0
				BEGIN
					IF OBJECT_ID('RDB.dbo.TMP_FOODBORNE_TA', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_FOODBORNE_TA;
							
						SELECT INVESTIGATION_KEY,
							   INVESTIGATION_LOCAL_ID,
							   PROGRAM_JURISDICTION_OID,
							   PATIENT_KEY,
							   PATIENT_LOCAL_ID,
							   DISEASE_NAME,
							   DISEASE_CD
						INTO RDB.dbo.TMP_FOODBORNE_TA
						FROM RDB.dbo.TMP_ALL_FOODBORNE_TA  with (nolock);
						
				END	
				
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 8;
				SET @PROC_STEP_NAME = ' GENERATING TMP_FOODBORNE_SHORT_COL'; 
				set @count = (SELECT count(*) FROM RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL)
				IF @count>0
					
					BEGIN
					
						IF OBJECT_ID('RDB.dbo.TMP_FOODBORNE_SHORT_COL', 'U') IS NOT NULL  
							 DROP TABLE RDB.dbo.TMP_FOODBORNE_SHORT_COL;

				
							ALTER TABLE RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL
							ADD ANSWERCOL varchar(8000);

							UPDATE RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL
							SET ANSWERCOL = SUBSTRING(COL1, 1, 8000); 

							ALTER TABLE RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL
							DROP COLUMN COL1;

							--DECLARE @cols  AS NVARCHAR(MAX)='';
							--DECLARE @query AS NVARCHAR(MAX)='';
							SET @cols='';
							SET @query='';
							BEGIN TRY
								SELECT @cols = @cols + QUOTENAME(DATAMART_COLUMN_NM) + ',' FROM (select distinct DATAMART_COLUMN_NM from RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL ) as tmp
								select @cols = substring(@cols, 0, len(@cols)) --trim "," at end

								set @query = 
								'SELECT *
								INTO TMP_FOODBORNE_SHORT_COL
								fROM
								( 
								SELECT     INVESTIGATION_KEY,
										   INVESTIGATION_LOCAL_ID,
										   PROGRAM_JURISDICTION_OID,
										   PATIENT_KEY,
										   PATIENT_LOCAL_ID,
										   DISEASE_NAME,
										   DISEASE_CD,
										   DATAMART_COLUMN_NM,
										   ANSWERCOL
									
								FROM RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL )
								as A 

								PIVOT ( MAX([ANSWERCOL]) FOR DATAMART_COLUMN_NM   IN (' + @cols + ')) AS PivotTable';
								execute(@query)
							END TRY  
							BEGIN CATCH
									DECLARE @ErrorNumber1 INT = ERROR_NUMBER();
									DECLARE @ErrorLine1 INT = ERROR_LINE();
									DECLARE @ErrorMessage1 NVARCHAR(4000) = ERROR_MESSAGE();

								IF @ErrorNumber1=511
								BEGIN
									SET @cols='';
									SET @query='';
									SELECT @cols = @cols + QUOTENAME(DATAMART_COLUMN_NM) + ',' FROM (select distinct top 300 DATAMART_COLUMN_NM from RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL ) as tmp
									select @cols = substring(@cols, 0, len(@cols)) --trim "," at end

									IF OBJECT_ID('RDB.dbo.TMP_FOODBORNE_SHORT_COL', 'U') IS NOT NULL  
									DROP TABLE RDB.dbo.TMP_FOODBORNE_SHORT_COL;

									set @query = 
									'SELECT *
									INTO TMP_FOODBORNE_SHORT_COL
									fROM
									( 
									SELECT     INVESTIGATION_KEY,
											   INVESTIGATION_LOCAL_ID,
											   PROGRAM_JURISDICTION_OID,
											   PATIENT_KEY,
											   PATIENT_LOCAL_ID,
											   DISEASE_NAME,
											   DISEASE_CD,
											   DATAMART_COLUMN_NM,
											   ANSWERCOL
								
									FROM RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL )
									as A 

									PIVOT ( MAX([ANSWERCOL]) FOR DATAMART_COLUMN_NM   IN (' + @cols + ')) AS PivotTable';
									execute(@query)
								END
								ELSE
									THROW @ErrorNumber1, @ErrorMessage1, @ErrorMessage1;
							END CATCH
						END	
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_FOODBORNE','RDB.LDF_FOODBORNE','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
				
				-- If data does not exist create TMP_FOODBORNE_SHORT_COL table same as TMP_ALL_FOODBORNE_SHORT_COL, which will be used while merging table in step 9
				set @count = (SELECT count(*) FROM RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL)
				IF @count=0
				BEGIN
				IF OBJECT_ID('RDB.dbo.TMP_FOODBORNE_SHORT_COL', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_FOODBORNE_SHORT_COL;
					
					SELECT INVESTIGATION_KEY,
						   INVESTIGATION_LOCAL_ID,
						   PROGRAM_JURISDICTION_OID,
						   PATIENT_KEY,
						   PATIENT_LOCAL_ID,
						   DISEASE_NAME,
						   DISEASE_CD
						INTO RDB.dbo.TMP_FOODBORNE_SHORT_COL
						FROM RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL  with (nolock);
				END	
			
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 9;
				SET @PROC_STEP_NAME = ' GENERATING TMP_FOODBORNE'; 

					IF OBJECT_ID('RDB.dbo.TMP_FOODBORNE', 'U') IS NOT NULL  
						 DROP TABLE RDB.dbo.TMP_FOODBORNE;
			
						EXECUTE  [dbo].[sp_MERGE_TWO_TABLES] 
						   @INPUT_TABLE1='RDB.dbo.TMP_FOODBORNE_SHORT_COL'
						  ,@INPUT_TABLE2='RDB.dbo.TMP_FOODBORNE_TA'
						  ,@OUTPUT_TABLE='RDB.dbo.TMP_FOODBORNE'
						  ,@JOIN_ON_COLUMN='INVESTIGATION_KEY';

						SELECT @ROWCOUNT_NO = @@ROWCOUNT;
					
						DELETE FROM RDB.dbo.TMP_FOODBORNE WHERE INVESTIGATION_KEY IS NULL;


				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_FOODBORNE','RDB.LDF_FOODBORNE','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
			
				--- data  ldf_base_country_translated; length x $4000; length col1 $4000; 
				--- line 308 to 316


				SET @PROC_STEP_NO = 10;
				SET @PROC_STEP_NAME = ' GENERATING LDF_FOODBORNE'; 
					IF OBJECT_ID('RDB.dbo.LDF_FOODBORNE', 'U') IS NOT NULL
					BEGIN
						BEGIN TRANSACTION;
							SET @Alterdynamiccolumnlist='';
							SET @dynamiccolumnUpdate='';
							 
							SELECT   @Alterdynamiccolumnlist  = @Alterdynamiccolumnlist+ 'ALTER TABLE RDB.dbo.LDF_FOODBORNE ADD [' + name   +  '] varchar(4000) ',
								@dynamiccolumnUpdate= @dynamiccolumnUpdate + 'LDF_FOODBORNE.[' +  name  + ']='  + 'RDB.dbo.TMP_FOODBORNE.['  +name  + '] ,'
							FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID('RDB.dbo.TMP_FOODBORNE')
							AND name NOT IN  ( SELECT name FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID('RDB..LDF_FOODBORNE'))
							
							
							--PRINT '@@Alterdynamiccolumnlist -----------	'+CAST(@Alterdynamiccolumnlist AS NVARCHAR(max))
							--PRINT '@@@@dynamiccolumnUpdate -----------	'+CAST(@dynamiccolumnUpdate AS NVARCHAR(max))

							IF @Alterdynamiccolumnlist IS NOT NULL AND @Alterdynamiccolumnlist!=''
							BEGIN

								EXEC(  @Alterdynamiccolumnlist)

								SET  @dynamiccolumnUpdate=SUBSTRING(@dynamiccolumnUpdate,1,LEN(@dynamiccolumnUpdate)-1)

								EXEC ('update  RDB.dbo.LDF_FOODBORNE  SET ' +   @dynamiccolumnUpdate + ' FROM RDB.dbo.TMP_FOODBORNE     
								   inner join  RDB.dbo.LDF_FOODBORNE  on  RDB.dbo.TMP_FOODBORNE.INVESTIGATION_LOCAL_ID =  RDB.dbo.LDF_FOODBORNE.INVESTIGATION_LOCAL_ID')

							END
					
						COMMIT TRANSACTION;
					
						BEGIN TRANSACTION;
							--In case of updates, delete the existing ones and insert updated ones in LDF_FOODBORNE
							DELETE FROM RDB.dbo.LDF_FOODBORNE WHERE INVESTIGATION_KEY IN (SELECT INVESTIGATION_KEY FROM RDB.dbo.TMP_FOODBORNE);

						COMMIT TRANSACTION;
					
						BEGIN TRANSACTION;
					
						--- During update if TMP_FOODBORNE has 4 columns updated only and the LDF_FOODBORNE has 7 columns then get column name dynamically from TMP_FOODBORNE and populate them.
					
							SET @dynamiccolumnList =''
							SELECT @dynamiccolumnList= @dynamiccolumnList +'['+ name +'],' FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID('RDB.dbo.TMP_FOODBORNE')
							SET  @dynamiccolumnList=SUBSTRING(@dynamiccolumnList,1,LEN(@dynamiccolumnList)-1)

							--PRINT '@@@@@dynamiccolumnList -----------	'+CAST(@dynamiccolumnList AS NVARCHAR(max))

							EXEC ('INSERT INTO RDB.dbo.LDF_FOODBORNE ('+@dynamiccolumnList+')
							SELECT '+@dynamiccolumnList +'
							FROM RDB.dbo.TMP_FOODBORNE');
					
							SELECT @ROWCOUNT_NO = @@ROWCOUNT;
							
						COMMIT TRANSACTION;
					END
					
					---- This is one time deal, if table does not exist then create it.
					IF OBJECT_ID('RDB.dbo.LDF_FOODBORNE', 'U') IS NULL 
					BEGIN
						BEGIN TRANSACTION;
						
							SELECT *
							INTO RDB.dbo.LDF_FOODBORNE
							FROM RDB.dbo.TMP_FOODBORNE;
						
							SELECT @ROWCOUNT_NO = @@ROWCOUNT;
						COMMIT TRANSACTION;
					END
					
				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_FOODBORNE','RDB.LDF_FOODBORNE','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			
				
				--- DELETE Temp tables.
				
				IF OBJECT_ID('RDB.dbo.TMP_BASE_FOODBORNE', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_BASE_FOODBORNE; 

				IF OBJECT_ID('RDB.dbo.TMP_LINKED_FOODBORNE', 'U') IS NOT NULL  
						 DROP TABLE RDB.dbo.TMP_LINKED_FOODBORNE; 

				IF OBJECT_ID('RDB.dbo.TMP_ALL_FOODBORNE', 'U') IS NOT NULL  
						 DROP TABLE RDB.dbo.TMP_ALL_FOODBORNE; 

				IF OBJECT_ID('RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL', 'U') IS NOT NULL  
						 DROP TABLE RDB.dbo.TMP_ALL_FOODBORNE_SHORT_COL; 

				IF OBJECT_ID('RDB.dbo.TMP_ALL_FOODBORNE_TA', 'U') IS NOT NULL  
						 DROP TABLE RDB.dbo.TMP_ALL_FOODBORNE_TA; 

				IF OBJECT_ID('RDB.dbo.TMP_FOODBORNE_TA', 'U') IS NOT NULL  
						 DROP TABLE RDB.dbo.TMP_FOODBORNE_TA; 

				IF OBJECT_ID('RDB.dbo.TMP_FOODBORNE_SHORT_COL', 'U') IS NOT NULL  
						 DROP TABLE RDB.dbo.TMP_FOODBORNE_SHORT_COL; 
						 
				IF OBJECT_ID('RDB.dbo.TMP_FOODBORNE', 'U') IS NOT NULL  
						 DROP TABLE RDB.dbo.TMP_FOODBORNE; 


	END	-- END for IF EXISTS (SELECT * FROM RDB.dbo.S_LDF_DIMENSIONAL_DATA WHERE  PHC_CD IN .................  at line 69

    BEGIN TRANSACTION ;
	
	SET @Proc_Step_no = 11;
	SET @Proc_Step_Name = 'SP_COMPLETE'; 


	INSERT INTO rdb.[dbo].[job_flow_log] (
		    [batch_id]
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
           'LDF_FOODBORNE'
           ,'RDB.LDF_FOODBORNE'
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
           ,'LDF_FOODBORNE'
           ,'RDB.LDF_FOODBORNE'
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