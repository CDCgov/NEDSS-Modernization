/*
 %include etlpgm (LDF_Generic_Datamart.sas)
 It uses incremental data generated from stored procedure sp_LDF_DIMENSIONAL_DATA (generated table S_LDF_DIMENSIONAL_DATA)
*/

USE [RDB]
GO

		IF OBJECT_ID('RDB.dbo.TMP_BASE_GENERIC', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_BASE_GENERIC; 

		IF OBJECT_ID('RDB.dbo.TMP_LINKED_GENERIC', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_LINKED_GENERIC; 

		IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_ALL_GENERIC; 

		IF OBJECT_ID('RDB.dbo.TMP_GENERIC', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_GENERIC; 
				 
		IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC_SHORT_COL', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_ALL_GENERIC_SHORT_COL; 

		IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC_TA', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_ALL_GENERIC_TA; 

		IF OBJECT_ID('RDB.dbo.TMP_GENERIC_TA', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_GENERIC_TA; 

		IF OBJECT_ID('RDB.dbo.TMP_GENERIC_SHORT_COL', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_GENERIC_SHORT_COL; 
				 
		IF OBJECT_ID('RDB.dbo.TMP_GENERIC', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_GENERIC; 
				 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO

IF OBJECT_ID('dbo.[sp_LDF_GENERIC_DATAMART]', 'P') IS NOT NULL
    DROP PROCEDURE dbo.[sp_LDF_GENERIC_DATAMART];
GO

CREATE PROCEDURE [dbo].[sp_LDF_GENERIC_DATAMART]
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

	DECLARE  @Alterdynamiccolumnlist varchar(max)=''
	DECLARE  @dynamiccolumnUpdate varchar(max)=''
	DECLARE  @dynamiccolumninsert varchar(max)=''
	
	DECLARE  @dynamiccolumnList varchar(max)=''	--insert into LDF_GENERIC table from TMP_GENERIC table
	DECLARE @count BIGINT;
	
	DECLARE @ErrorNumber1 INT = ERROR_NUMBER();
	DECLARE @ErrorLine1 INT = ERROR_LINE();
	DECLARE @ErrorMessage1 NVARCHAR(4000) = ERROR_MESSAGE();
 BEGIN TRY
    
	SET @Proc_Step_no = 1;
	SET @Proc_Step_Name = 'SP_Start';

	

	
	BEGIN TRANSACTION;
	
    INSERT INTO RDB.[dbo].[job_flow_log] (
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
           ,'LDF_GENERIC'
           ,'RDB.LDF_GENERIC'
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

	---CREATE TABLE BASE_GENERIC AS 
	SET @count =
	(
		SELECT COUNT(1)
		FROM RDB.dbo.S_LDF_DIMENSIONAL_DATA   with (nolock)
			 INNER JOIN RDB.dbo.LDF_DATAMART_TABLE_REF   with (nolock) ON S_LDF_DIMENSIONAL_DATA.PHC_CD = RDB.dbo.LDF_DATAMART_TABLE_REF.condition_cd
														  AND DATAMART_NAME = 'LDF_GENERIC'
	);	
		
	IF (@count > 0)
	BEGIN

		--------- Create RDB.dbo.LDF_GENERIC1 table

		BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 2;
				SET @PROC_STEP_NAME = ' GENERATING LDF_GENERIC'; 

			--------- Create RDB.dbo.LDF_GENERIC table

			IF OBJECT_ID('RDB.dbo.TMP_BASE_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_BASE_GENERIC;
		
			SELECT S_LDF_DIMENSIONAL_DATA.*
							INTO RDB.dbo.TMP_BASE_GENERIC
							FROM RDB.dbo.S_LDF_DIMENSIONAL_DATA
								 INNER JOIN RDB.dbo.LDF_DATAMART_TABLE_REF ON PHC_CD = LDF_DATAMART_TABLE_REF.CONDITION_CD
																			  AND DATAMART_NAME = 'LDF_GENERIC';
				 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC','RDB.LDF_GENERIC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				--- CREATE TABLE LINKED_GENERIC AS 
			
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 3;
				SET @PROC_STEP_NAME = ' GENERATING TMP_LINKED_GENERIC'; 

			IF OBJECT_ID('RDB.dbo.TMP_LINKED_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_LINKED_GENERIC;


					SELECT GEN_LDF.*, 
						INV.INVESTIGATION_KEY, 
						INV.INV_LOCAL_ID 'INVESTIGATION_LOCAL_ID', 
						INV.CASE_OID 'PROGRAM_JURISDICTION_OID',
						GEN.PATIENT_KEY,
						PATIENT.PATIENT_LOCAL_ID 'PATIENT_LOCAL_ID',
						CONDITION.CONDITION_SHORT_NM 'DISEASE_NAME'
					INTO  RDB.dbo.TMP_LINKED_GENERIC
					FROM
						RDB.dbo.TMP_BASE_GENERIC GEN_LDF  with (nolock)
						INNER JOIN  RDB.dbo.INVESTIGATION INV
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
					VALUES(@BATCH_ID,'LDF_GENERIC','RDB.LDF_GENERIC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				----- CREATE TABLE ALL_GENERIC AS 
			
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 4;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_GENERIC'; 

			IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_ALL_GENERIC;

					SELECT A.*, 
					B.DATAMART_COLUMN_NM 'DM',
					A.phc_cd 'DISEASE_CD',
					A.page_set 'DISEASE_NM'
					INTO RDB.dbo.TMP_ALL_GENERIC
					FROM 	RDB.dbo.LDF_DATAMART_COLUMN_REF  B with (nolock)
					FULL OUTER JOIN RDB.dbo.TMP_LINKED_GENERIC A with (nolock)
					ON A.LDF_UID= B.LDF_UID WHERE
					(
						B.LDF_PAGE_SET ='OTHER'
						OR B.CONDITION_CD IN (SELECT CONDITION_CD FROM 
						RDB.dbo.LDF_DATAMART_TABLE_REF WHERE DATAMART_NAME = 'LDF_GENERIC') 
					)
					ORDER BY INVESTIGATION_UID;

					--- line 48 to 57
				
					UPDATE RDB.dbo.TMP_ALL_GENERIC
					SET DISEASE_CD = CONDITION_CD
					WHERE DATALENGTH(REPLACE(CONDITION_CD, ' ', ''))>1;  
				
					UPDATE RDB.dbo.TMP_ALL_GENERIC
					SET DISEASE_CD = PHC_CD
					WHERE DATALENGTH(REPLACE(CONDITION_CD, ' ', ''))<=1;  

					UPDATE RDB.dbo.TMP_ALL_GENERIC
					SET DISEASE_NM= PAGE_SET
					WHERE DATALENGTH(DISEASE_NM)<2;

					UPDATE RDB.dbo.TMP_ALL_GENERIC
					SET DATAMART_COLUMN_NM=DM
					WHERE DATALENGTH(DM)>2;  
				 
								 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC','RDB.LDF_GENERIC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				--- CREATE TABLE ALL_GENERIC_SHORT_COL AS          
			
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 5;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_GENERIC_SHORT_COL'; 

			IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC_SHORT_COL', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_ALL_GENERIC_SHORT_COL;

				
					SELECT INVESTIGATION_KEY,
								INVESTIGATION_LOCAL_ID,
								PROGRAM_JURISDICTION_OID,
								PATIENT_KEY,
								PATIENT_LOCAL_ID,
								DISEASE_NAME,
								DISEASE_CD,
								DATAMART_COLUMN_NM,
								col1 
					INTO RDB.dbo.TMP_ALL_GENERIC_SHORT_COL
					FROM RDB.dbo.TMP_ALL_GENERIC with (nolock) 
					WHERE data_type IN ('CV', 'ST'); 
					;

				
								 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC','RDB.LDF_GENERIC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				--- CREATE TABLE ALL_GENERIC_TA AS                
			
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 6;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_GENERIC_TA'; 

			IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC_TA', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_ALL_GENERIC_TA;

				
					SELECT INVESTIGATION_KEY,
								INVESTIGATION_LOCAL_ID,
								PROGRAM_JURISDICTION_OID,
								PATIENT_KEY,
								PATIENT_LOCAL_ID,
								DISEASE_NAME,
								DISEASE_CD,
								DATAMART_COLUMN_NM,
								col1 
					INTO RDB.dbo.TMP_ALL_GENERIC_TA
					FROM RDB.dbo.TMP_ALL_GENERIC with (nolock) 
					WHERE data_type IN ('LIST_ST');   
				
				
					 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC','RDB.LDF_GENERIC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
			
			
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 7;
				SET @PROC_STEP_NAME = ' GENERATING TMP_GENERIC_TA'; 
				set @count = (SELECT count(*) FROM TMP_ALL_GENERIC_TA)
				IF @count > 0
				BEGIN
					IF OBJECT_ID('RDB.dbo.TMP_GENERIC_TA', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_GENERIC_TA;

				
					ALTER TABLE TMP_ALL_GENERIC_TA
					ADD ANSWERCOL varchar(8000);

					UPDATE TMP_ALL_GENERIC_TA
					SET ANSWERCOL = SUBSTRING(COL1, 1, 8000); 

					ALTER TABLE TMP_ALL_GENERIC_TA
					DROP COLUMN COL1;

					--DECLARE @cols  AS NVARCHAR(MAX)='';
					--DECLARE @query AS NVARCHAR(MAX)='';
					SET @cols='';
					SET @query='';

					SELECT @cols = @cols + QUOTENAME(DATAMART_COLUMN_NM) + ',' FROM (select distinct DATAMART_COLUMN_NM from TMP_ALL_GENERIC_TA ) as tmp
					select @cols = substring(@cols, 0, len(@cols)) --trim "," at end

					--PRINT CAST(@cols AS NVARCHAR(3000))
					set @query = 
					'SELECT *
					INTO TMP_GENERIC_TA
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
						
					FROM RDB.dbo.TMP_ALL_GENERIC_TA )
					as A 

					PIVOT ( MAX([ANSWERCOL]) FOR DATAMART_COLUMN_NM   IN (' + @cols + ')) AS PivotTable';
					execute(@query)
		
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;
				END
				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC','RDB.LDF_GENERIC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
				
				-- If data does not exist create TMP_GENERIC_TA table same as TMP_ALL_GENERIC_TA, which will be used while merging table in step 9
				set @count = (SELECT count(*) FROM TMP_ALL_GENERIC_TA)
				IF @count = 0
				BEGIN
					IF OBJECT_ID('RDB.dbo.TMP_GENERIC_TA', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_GENERIC_TA;
							
						SELECT INVESTIGATION_KEY,
							   INVESTIGATION_LOCAL_ID,
							   PROGRAM_JURISDICTION_OID,
							   PATIENT_KEY,
							   PATIENT_LOCAL_ID,
							   DISEASE_NAME,
							   DISEASE_CD
						INTO RDB.dbo.TMP_GENERIC_TA
						FROM RDB.dbo.TMP_ALL_GENERIC_TA with (nolock);
						
				END	
				
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 8;
				SET @PROC_STEP_NAME = ' GENERATING TMP_GENERIC_SHORT_COL'; 
				set @count = (SELECT count(*) FROM TMP_ALL_GENERIC_SHORT_COL)
				IF @count > 0
				BEGIN
					IF OBJECT_ID('RDB.dbo.TMP_GENERIC_SHORT_COL', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_GENERIC_SHORT_COL;

		
					ALTER TABLE RDB.dbo.TMP_ALL_GENERIC_SHORT_COL
					ADD ANSWERCOL varchar(8000);

					UPDATE RDB.dbo.TMP_ALL_GENERIC_SHORT_COL
					SET ANSWERCOL = SUBSTRING(COL1, 1, 8000);

					ALTER TABLE RDB.dbo.TMP_ALL_GENERIC_SHORT_COL
					DROP COLUMN COL1;

					--DECLARE @cols  AS NVARCHAR(MAX)='';
					--DECLARE @query AS NVARCHAR(MAX)='';
					SET @cols='';
					SET @query='';
					BEGIN TRY
						SELECT @cols = @cols + QUOTENAME(DATAMART_COLUMN_NM) + ',' FROM (select distinct DATAMART_COLUMN_NM from RDB.dbo.TMP_ALL_GENERIC_SHORT_COL ) as tmp
						select @cols = substring(@cols, 0, len(@cols)) --trim "," at end
						
						set @query = 
						'SELECT *
						INTO TMP_GENERIC_SHORT_COL
						FROM
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
							
						FROM RDB.dbo.TMP_ALL_GENERIC_SHORT_COL )
						as A 

						PIVOT ( MAX([ANSWERCOL]) FOR DATAMART_COLUMN_NM   IN (' + @cols + ')) AS PivotTable';
						execute(@query)
					END TRY  
					BEGIN CATCH

						IF @ErrorNumber1=511
						BEGIN
							SET @cols='';
							SET @query='';
							SELECT @cols = @cols + QUOTENAME(DATAMART_COLUMN_NM) + ',' FROM (select distinct top 300 DATAMART_COLUMN_NM from RDB.dbo.TMP_ALL_GENERIC_SHORT_COL ) as tmp
							select @cols = substring(@cols, 0, len(@cols)) --trim "," at end

							IF OBJECT_ID('RDB.dbo.TMP_GENERIC_SHORT_COL', 'U') IS NOT NULL  
							DROP TABLE RDB.dbo.TMP_GENERIC_SHORT_COL;

							set @query = 
							'SELECT *
							INTO TMP_GENERIC_SHORT_COL
							FROM
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
								
							FROM RDB.dbo.TMP_ALL_GENERIC_SHORT_COL )
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
					VALUES(@BATCH_ID,'LDF_GENERIC','RDB.LDF_GENERIC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				-- If data does not exist create TMP_GENERIC_SHORT_COL table same as TMP_ALL_GENERIC_SHORT_COL, which will be used while merging table in step 9
				set @count = (SELECT count(*) FROM TMP_ALL_GENERIC_SHORT_COL)
				IF @count = 0
				BEGIN
				IF OBJECT_ID('RDB.dbo.TMP_GENERIC_SHORT_COL', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_GENERIC_SHORT_COL;
					
					SELECT INVESTIGATION_KEY,
						   INVESTIGATION_LOCAL_ID,
						   PROGRAM_JURISDICTION_OID,
						   PATIENT_KEY,
						   PATIENT_LOCAL_ID,
						   DISEASE_NAME,
						   DISEASE_CD
						INTO RDB.dbo.TMP_GENERIC_SHORT_COL
						FROM RDB.dbo.TMP_ALL_GENERIC_SHORT_COL;
				END
				
				--- MERGE  GENERIC_SHORT_COL GENERIC_TA;
			
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 9;
				SET @PROC_STEP_NAME = ' GENERATING TMP_GENERIC'; 

			IF OBJECT_ID('RDB.dbo.TMP_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_GENERIC;

					EXECUTE  [dbo].[sp_MERGE_TWO_TABLES] 
						   @INPUT_TABLE1='RDB.dbo.TMP_GENERIC_SHORT_COL'
						  ,@INPUT_TABLE2='RDB.dbo.TMP_GENERIC_TA'
						  ,@OUTPUT_TABLE='RDB.dbo.TMP_GENERIC'
						  ,@JOIN_ON_COLUMN='INVESTIGATION_KEY';

					DELETE FROM RDB.dbo.TMP_GENERIC WHERE INVESTIGATION_KEY IS NULL;
				
						SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC','RDB.LDF_GENERIC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
			
				SET @PROC_STEP_NO = 10;
				SET @PROC_STEP_NAME = ' GENERATING LDF_GENERIC'; 

			--- LDF_GENERIC is Dimensonal table, insert/update incremental data
				IF OBJECT_ID('RDB.dbo.LDF_GENERIC', 'U') IS NOT NULL
				BEGIN
					--- If the TMP_GENERIC has additional columns compare to LDF_GENERIC, add these additional columns in LDF_GENERIC table.
					BEGIN TRANSACTION;
						SET @Alterdynamiccolumnlist='';
						SET @dynamiccolumnUpdate='';
						 
						SELECT   @Alterdynamiccolumnlist  = @Alterdynamiccolumnlist+ 'ALTER TABLE RDB.dbo.LDF_GENERIC ADD [' + name   +  '] varchar(4000) ',
							@dynamiccolumnUpdate= @dynamiccolumnUpdate + 'LDF_GENERIC.[' +  name  + ']='  + 'RDB.dbo.TMP_GENERIC.['  +name  + '] ,'
						FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID('RDB.dbo.TMP_GENERIC')
						AND name NOT IN  ( SELECT name FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID('RDB..LDF_GENERIC'))
						
						
						--PRINT '@@Alterdynamiccolumnlist -----------	'+CAST(@Alterdynamiccolumnlist AS NVARCHAR(max))
						--PRINT '@@@@dynamiccolumnUpdate -----------	'+CAST(@dynamiccolumnUpdate AS NVARCHAR(max))

						IF @Alterdynamiccolumnlist IS NOT NULL AND @Alterdynamiccolumnlist!=''
						BEGIN

							EXEC(  @Alterdynamiccolumnlist)

							SET  @dynamiccolumnUpdate=substring(@dynamiccolumnUpdate,1,len(@dynamiccolumnUpdate)-1)

							EXEC ('update  RDB.dbo.LDF_GENERIC  SET ' +   @dynamiccolumnUpdate + ' FROM RDB.dbo.TMP_GENERIC     
							   inner join  RDB.dbo.LDF_GENERIC  on  RDB.dbo.TMP_GENERIC.INVESTIGATION_LOCAL_ID =  RDB.dbo.LDF_GENERIC.INVESTIGATION_LOCAL_ID')

						END
				
					COMMIT TRANSACTION;
				
					BEGIN TRANSACTION;
						--In case of updates, delete the existing ones and insert updated ones in LDF_GENERIC
						DELETE FROM RDB.dbo.LDF_GENERIC WHERE INVESTIGATION_KEY IN (SELECT INVESTIGATION_KEY FROM RDB.dbo.TMP_GENERIC);

					COMMIT TRANSACTION;
				
					BEGIN TRANSACTION;
				
					--- During update if TMP_GENERIC has 4 columns updated only and the LDF_GENERIC has 7 columns then get column name dynamically from TMP_GENERIC and populate them.
				
						SET @dynamiccolumnList =''
						SELECT @dynamiccolumnList= @dynamiccolumnList +'['+ name +'],' FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID('RDB.dbo.TMP_GENERIC')
						SET  @dynamiccolumnList=substring(@dynamiccolumnList,1,len(@dynamiccolumnList)-1)

						--PRINT '@@@@@dynamiccolumnList -----------	'+CAST(@dynamiccolumnList AS NVARCHAR(max))

						EXEC ('INSERT INTO RDB.dbo.LDF_GENERIC ('+@dynamiccolumnList+')
						SELECT '+@dynamiccolumnList +'
						FROM RDB.dbo.TMP_GENERIC');

						SELECT @ROWCOUNT_NO = @@ROWCOUNT;
						
					COMMIT TRANSACTION;
							 
				END
					
				---- This is one time deal, if table does not exist then create it.
				IF OBJECT_ID('RDB.dbo.LDF_GENERIC', 'U') IS NULL 
				BEGIN
					BEGIN TRANSACTION;
					
						SELECT *
						INTO RDB.dbo.LDF_GENERIC
						FROM RDB.dbo.TMP_GENERIC;
					
						SELECT @ROWCOUNT_NO = @@ROWCOUNT;
					COMMIT TRANSACTION;
				END

				BEGIN TRANSACTION;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC','RDB.LDF_GENERIC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
			/*
				PROC DATASETS LIBRARY = WORK NOLIST;
				DELETE 
					BASE_GENERIC
					LINKED_GENERIC
					ALL_GENERIC
					GENERIC;
				RUN;
			*/
			IF OBJECT_ID('RDB.dbo.TMP_BASE_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_BASE_GENERIC; 

			IF OBJECT_ID('RDB.dbo.TMP_LINKED_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_LINKED_GENERIC; 

			IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_ALL_GENERIC; 

			IF OBJECT_ID('RDB.dbo.TMP_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_GENERIC; 
		
		END	
		SET @count =
	(
		SELECT COUNT(1)
		FROM RDB.dbo.S_LDF_DIMENSIONAL_DATA
			 INNER JOIN RDB.dbo.LDF_DATAMART_TABLE_REF ON S_LDF_DIMENSIONAL_DATA.PHC_CD = RDB.dbo.LDF_DATAMART_TABLE_REF.condition_cd
														  AND DATAMART_NAME = 'LDF_GENERIC1'
	);	
		IF @count>0
		BEGIN
		
			--------- Create RDB.dbo.LDF_GENERIC1 table
			
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 11;
			SET @PROC_STEP_NAME = ' GENERATING TMP_BASE_GENERIC'; 

			IF OBJECT_ID('RDB.dbo.TMP_BASE_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_BASE_GENERIC;

					SELECT S_LDF_DIMENSIONAL_DATA.*
							INTO RDB.dbo.TMP_BASE_GENERIC
							FROM RDB.dbo.S_LDF_DIMENSIONAL_DATA with (nolock)
								 INNER JOIN RDB.dbo.LDF_DATAMART_TABLE_REF with (nolock) ON PHC_CD = LDF_DATAMART_TABLE_REF.CONDITION_CD
																			  AND DATAMART_NAME = 'LDF_GENERIC1';
				
				 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC1','RDB.LDF_GENERIC1','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 12;
				SET @PROC_STEP_NAME = ' GENERATING TMP_LINKED_GENERIC'; 

			IF OBJECT_ID('RDB.dbo.TMP_LINKED_GENERIC', 'U') IS NOT NULL  
					DROP TABLE RDB.dbo.TMP_LINKED_GENERIC;

					SELECT GEN_LDF.*, 
						INV.INVESTIGATION_KEY, 
						INV.INV_LOCAL_ID  'INVESTIGATION_LOCAL_ID', 
						INV.CASE_OID  'PROGRAM_JURISDICTION_OID',
						GEN.PATIENT_KEY,
						PATIENT.PATIENT_LOCAL_ID 'PATIENT_LOCAL_ID',
						CONDITION.CONDITION_SHORT_NM 'DISEASE_NAME'
					INTO RDB.dbo.TMP_LINKED_GENERIC
					FROM
						TMP_BASE_GENERIC GEN_LDF with (nolock)
						INNER JOIN  RDB.dbo.INVESTIGATION INV with (nolock)
					ON  
						GEN_LDF.INVESTIGATION_UID=INV.CASE_UID 
					INNER JOIN RDB.dbo.GENERIC_CASE GEN with (nolock)
					ON 
						GEN.INVESTIGATION_KEY=INV.INVESTIGATION_KEY
					INNER JOIN RDB.dbo.CONDITION with (nolock)
					ON 
						CONDITION.CONDITION_KEY= GEN.CONDITION_KEY
					INNER JOIN RDB.dbo.D_PATIENT PATIENT with (nolock)
					ON 
						PATIENT.PATIENT_KEY=GEN.PATIENT_KEY
					ORDER BY 
						INVESTIGATION_UID;
				
				
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC1','RDB.LDF_GENERIC1','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
			---- CREATE TABLE ALL_GENERIC AS 
		
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 13;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_GENERIC '; 

			IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_ALL_GENERIC;

		
					SELECT A.*, 
					B.DATAMART_COLUMN_NM 'DM',
					A.phc_cd 'DISEASE_CD',
					A.page_set 'DISEASE_NM'
					INTO TMP_ALL_GENERIC
					FROM 	RDB.dbo.LDF_DATAMART_COLUMN_REF  B with (nolock)
					FULL OUTER JOIN RDB.dbo.TMP_LINKED_GENERIC A with (nolock) 
					ON A.LDF_UID= B.LDF_UID WHERE
					(B.LDF_PAGE_SET ='OTHER'
					OR B.CONDITION_CD IN (SELECT CONDITION_CD FROM 
						RDB.dbo.LDF_DATAMART_TABLE_REF with (nolock) WHERE DATAMART_NAME = 'LDF_GENERIC1') 
					)
					ORDER BY 
						INVESTIGATION_UID;
				
					--- line 165 to 172
				
					UPDATE RDB.dbo.TMP_ALL_GENERIC
					SET DISEASE_CD = CONDITION_CD
					WHERE DATALENGTH(REPLACE(CONDITION_CD, ' ', ''))>1;  
				
					UPDATE RDB.dbo.TMP_ALL_GENERIC
					SET DISEASE_CD = PHC_CD
					WHERE DATALENGTH(REPLACE(CONDITION_CD, ' ', ''))<=1;  

					UPDATE RDB.dbo.TMP_ALL_GENERIC
					SET DISEASE_NM= PAGE_SET
					WHERE DATALENGTH(DISEASE_NM)<2;

					UPDATE RDB.dbo.TMP_ALL_GENERIC
					SET DATAMART_COLUMN_NM=DM
					WHERE DATALENGTH(DM)>2;  
				 
				 
		    					 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC1','RDB.LDF_GENERIC1','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 14;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_GENERIC_SHORT_COL'; 

			IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC_SHORT_COL', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_ALL_GENERIC_SHORT_COL;

		
					SELECT INVESTIGATION_KEY,
								INVESTIGATION_LOCAL_ID,
								PROGRAM_JURISDICTION_OID,
								PATIENT_KEY,
								PATIENT_LOCAL_ID,
								DISEASE_NAME,
								DISEASE_CD,
								DATAMART_COLUMN_NM,
								col1  
					INTO RDB.dbo.TMP_ALL_GENERIC_SHORT_COL
					FROM RDB.dbo.TMP_ALL_GENERIC with (nolock)
					WHERE data_type IN ('CV', 'ST'); 
					;
			
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC1','RDB.LDF_GENERIC1','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 15;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_GENERIC_TA'; 

			IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC_TA', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_ALL_GENERIC_TA;

				 	
					SELECT INVESTIGATION_KEY,
								INVESTIGATION_LOCAL_ID,
								PROGRAM_JURISDICTION_OID,
								PATIENT_KEY,
								PATIENT_LOCAL_ID,
								DISEASE_NAME,
								DISEASE_CD,
								DATAMART_COLUMN_NM,
								col1  
					INTO RDB.dbo.TMP_ALL_GENERIC_TA
					FROM RDB.dbo.TMP_ALL_GENERIC with (nolock) 
					WHERE data_type IN ('LIST_ST');   
				
			
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC1','RDB.LDF_GENERIC1','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO =16;
				SET @PROC_STEP_NAME = ' GENERATING TMP_GENERIC_TA'; 
				set @count = (SELECT count(*) FROM TMP_ALL_GENERIC_TA)
				if @count > 0
				BEGIN
					IF OBJECT_ID('RDB.dbo.TMP_GENERIC_TA', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_GENERIC_TA;

		
					ALTER TABLE TMP_ALL_GENERIC_TA
					ADD ANSWERCOL varchar(8000);

					UPDATE TMP_ALL_GENERIC_TA
					SET ANSWERCOL = SUBSTRING(COL1, 1, 8000); 

					ALTER TABLE TMP_ALL_GENERIC_TA
					DROP COLUMN COL1;

					--DECLARE @cols  AS NVARCHAR(MAX)='';
					--DECLARE @query AS NVARCHAR(MAX)='';
					SET @cols='';
					SET @query='';


					SELECT @cols = @cols + QUOTENAME(DATAMART_COLUMN_NM) + ',' FROM (select distinct DATAMART_COLUMN_NM from TMP_ALL_GENERIC_TA ) as tmp
					select @cols = substring(@cols, 0, len(@cols)) --trim "," at end

					--PRINT CAST(@cols AS NVARCHAR(3000))
					set @query = 
					'SELECT *
					INTO TMP_GENERIC_TA
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
						
					FROM RDB.dbo.TMP_ALL_GENERIC_TA  with (nolock))
					as A 

					PIVOT ( MAX([ANSWERCOL]) FOR DATAMART_COLUMN_NM   IN (' + @cols + ')) AS PivotTable';
					execute(@query)
		
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;
				END
				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC1','RDB.LDF_GENERIC1','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				-- If data does not exist create TMP_GENERIC_TA table same as TMP_ALL_GENERIC_TA, which will be used while merging table in step 18
				set @count = (SELECT count(*) FROM TMP_ALL_GENERIC_TA)
				if @count = 0
				BEGIN
					IF OBJECT_ID('RDB.dbo.TMP_GENERIC_TA', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_GENERIC_TA;
							
						SELECT INVESTIGATION_KEY,
							   INVESTIGATION_LOCAL_ID,
							   PROGRAM_JURISDICTION_OID,
							   PATIENT_KEY,
							   PATIENT_LOCAL_ID,
							   DISEASE_NAME,
							   DISEASE_CD
						INTO RDB.dbo.TMP_GENERIC_TA
						FROM RDB.dbo.TMP_ALL_GENERIC_TA with (nolock);
						
				END
				
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 17;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_GENERIC_SHORT_COL'; 
				set @count = (SELECT count(*) FROM TMP_ALL_GENERIC_SHORT_COL)
				if @count > 0
				BEGIN
					IF OBJECT_ID('RDB.dbo.TMP_GENERIC_SHORT_COL', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_GENERIC_SHORT_COL;

					ALTER TABLE TMP_ALL_GENERIC_SHORT_COL
					ADD ANSWERCOL varchar(8000);

					UPDATE TMP_ALL_GENERIC_SHORT_COL
					SET ANSWERCOL = SUBSTRING(COL1, 1, 8000); 

					ALTER TABLE TMP_ALL_GENERIC_SHORT_COL
					DROP COLUMN COL1;

					--DECLARE @cols  AS NVARCHAR(MAX)='';
					--DECLARE @query AS NVARCHAR(MAX)='';

					SET @cols='';
					SET @query='';
					BEGIN TRY
						SELECT @cols = @cols + QUOTENAME(DATAMART_COLUMN_NM) + ',' FROM (select distinct DATAMART_COLUMN_NM from TMP_ALL_GENERIC_SHORT_COL ) as tmp
						select @cols = substring(@cols, 0, len(@cols)) --trim "," at end

						set @query = 
						'SELECT *
						INTO TMP_GENERIC_SHORT_COL
						FROM
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
							
						FROM RDB.dbo.TMP_ALL_GENERIC_SHORT_COL with (nolock) )
						as A 

						PIVOT ( MAX([ANSWERCOL]) FOR DATAMART_COLUMN_NM   IN (' + @cols + ')) AS PivotTable';
						execute(@query)
					END TRY  
					BEGIN CATCH

						IF @ErrorNumber1=511
						BEGIN
							SET @cols='';
							SET @query='';
							SELECT @cols = @cols + QUOTENAME(DATAMART_COLUMN_NM) + ',' FROM (select distinct top 300 DATAMART_COLUMN_NM from RDB.dbo.TMP_ALL_GENERIC_SHORT_COL ) as tmp
							select @cols = substring(@cols, 0, len(@cols)) --trim "," at end

							IF OBJECT_ID('RDB.dbo.TMP_GENERIC_SHORT_COL', 'U') IS NOT NULL  
							DROP TABLE RDB.dbo.TMP_GENERIC_SHORT_COL;

							set @query = 
							'SELECT *
							INTO TMP_GENERIC_SHORT_COL
							FROM
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
								
							FROM RDB.dbo.TMP_ALL_GENERIC_SHORT_COL with (nolock) )
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
					VALUES(@BATCH_ID,'LDF_GENERIC1','RDB.LDF_GENERIC1','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				-- If data does not exist create TMP_GENERIC_SHORT_COL table same as TMP_ALL_GENERIC_SHORT_COL, which will be used while merging table in step 18
				set @count = (SELECT count(*) FROM TMP_ALL_GENERIC_SHORT_COL)
				if @count = 0
				BEGIN
				IF OBJECT_ID('RDB.dbo.TMP_GENERIC_SHORT_COL', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_GENERIC_SHORT_COL;
					
					SELECT INVESTIGATION_KEY,
						   INVESTIGATION_LOCAL_ID,
						   PROGRAM_JURISDICTION_OID,
						   PATIENT_KEY,
						   PATIENT_LOCAL_ID,
						   DISEASE_NAME,
						   DISEASE_CD
						INTO RDB.dbo.TMP_GENERIC_SHORT_COL
						FROM RDB.dbo.TMP_ALL_GENERIC_SHORT_COL with (nolock);
				END
				
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 18;
				SET @PROC_STEP_NAME = ' GENERATING TMP_GENERIC'; 

			IF OBJECT_ID('RDB.dbo.TMP_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_GENERIC;

					EXECUTE  [dbo].[sp_MERGE_TWO_TABLES] 
						   @INPUT_TABLE1='RDB.dbo.TMP_GENERIC_SHORT_COL'
						  ,@INPUT_TABLE2='RDB.dbo.TMP_GENERIC_TA'
						  ,@OUTPUT_TABLE='RDB.dbo.TMP_GENERIC'
						  ,@JOIN_ON_COLUMN='INVESTIGATION_KEY';
						  
					DELETE FROM RDB.dbo.TMP_GENERIC WHERE INVESTIGATION_KEY IS NULL;
		
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC1','RDB.LDF_GENERIC1','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		

				SET @PROC_STEP_NO = 19;
				SET @PROC_STEP_NAME = ' GENERATING LDF_GENERIC1'; 

				IF OBJECT_ID('RDB.dbo.LDF_GENERIC1', 'U') IS NOT NULL
				BEGIN
					--- LDF_GENERIC1 is Dimensonal table, insert/update incremental data
							
					--- If the TMP_GENERIC has additional columns compare to LDF_GENERIC1, add these additional columns in LDF_GENERIC1 table.
					
					BEGIN TRANSACTION;
						SET @Alterdynamiccolumnlist='';
						SET @dynamiccolumnUpdate='';
							 
						SELECT   @Alterdynamiccolumnlist  = @Alterdynamiccolumnlist+ 'ALTER TABLE RDB.dbo.LDF_GENERIC1 ADD [' + name   +  '] varchar(4000) ',
							@dynamiccolumnUpdate= @dynamiccolumnUpdate + 'LDF_GENERIC1.[' +  name  + ']='  + 'RDB.dbo.TMP_GENERIC.['  +name  + '] ,'
						FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID('RDB.dbo.TMP_GENERIC')
						AND name NOT IN  ( SELECT name FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID('RDB..LDF_GENERIC1'))
							
							
						--PRINT '@@Alterdynamiccolumnlist -----------	'+CAST(@Alterdynamiccolumnlist AS NVARCHAR(max))
						--PRINT '@@@@dynamiccolumnUpdate -----------	'+CAST(@dynamiccolumnUpdate AS NVARCHAR(max))

						IF @Alterdynamiccolumnlist IS NOT NULL AND @Alterdynamiccolumnlist!=''
						BEGIN

							EXEC(  @Alterdynamiccolumnlist)

							SET  @dynamiccolumnUpdate=substring(@dynamiccolumnUpdate,1,len(@dynamiccolumnUpdate)-1)

							EXEC ('update  RDB.dbo.LDF_GENERIC1  SET ' +   @dynamiccolumnUpdate + ' FROM RDB.dbo.TMP_GENERIC     
							   inner join  RDB.dbo.LDF_GENERIC1  on  RDB.dbo.TMP_GENERIC.INVESTIGATION_LOCAL_ID =  RDB.dbo.LDF_GENERIC1.INVESTIGATION_LOCAL_ID')

						END
					COMMIT TRANSACTION;
					
					BEGIN TRANSACTION;
						--In case of updates, delete the existing ones and insert updated ones in LDF_GENERIC1
						DELETE FROM RDB.dbo.LDF_GENERIC1 WHERE INVESTIGATION_KEY IN (SELECT INVESTIGATION_KEY FROM RDB.dbo.TMP_GENERIC);
					COMMIT TRANSACTION;

					BEGIN TRANSACTION;
					
						--- During update if TMP_GENERIC has 4 columns updated only and the LDF_GENERIC1 has 7 columns then get column name dynamically from TMP_GENERIC and populate them.
					
						SET @dynamiccolumnList =''
						SELECT @dynamiccolumnList= @dynamiccolumnList +'['+ name +'],' FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID('RDB.dbo.TMP_GENERIC')
						SET  @dynamiccolumnList=substring(@dynamiccolumnList,1,len(@dynamiccolumnList)-1)

						--PRINT '@@@@@dynamiccolumnList -----------	'+CAST(@dynamiccolumnList AS NVARCHAR(max))

						EXEC ('INSERT INTO RDB.dbo.LDF_GENERIC1 ('+@dynamiccolumnList+')
						SELECT '+@dynamiccolumnList +'
						FROM RDB.dbo.TMP_GENERIC');
						
						SELECT @ROWCOUNT_NO = @@ROWCOUNT;
						
					COMMIT TRANSACTION;
				
				END
					
				---- This is one time deal, if table does not exist then create it.
				IF OBJECT_ID('RDB.dbo.LDF_GENERIC1', 'U') IS NULL 
				BEGIN
					BEGIN TRANSACTION;
					
						SELECT *
						INTO RDB.dbo.LDF_GENERIC1
						FROM RDB.dbo.TMP_GENERIC;
					
						SELECT @ROWCOUNT_NO = @@ROWCOUNT;
					COMMIT TRANSACTION;
				END			 

				BEGIN TRANSACTION;
				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC1','RDB.LDF_GENERIC1','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
			IF OBJECT_ID('RDB.dbo.TMP_BASE_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE rdb.dbo.TMP_BASE_GENERIC; 

			IF OBJECT_ID('RDB.dbo.TMP_LINKED_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE rdb.dbo.TMP_LINKED_GENERIC; 

			IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE rdb.dbo.TMP_ALL_GENERIC; 

			IF OBJECT_ID('RDB.dbo.TMP_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE rdb.dbo.TMP_GENERIC; 
		
		END

	SET @count =
	(
		SELECT COUNT(1)
		FROM RDB.dbo.S_LDF_DIMENSIONAL_DATA with (nolock)
			 INNER JOIN RDB.dbo.LDF_DATAMART_TABLE_REF with (nolock) ON S_LDF_DIMENSIONAL_DATA.PHC_CD = RDB.dbo.LDF_DATAMART_TABLE_REF.condition_cd
														  AND DATAMART_NAME = 'LDF_GENERIC2'
	);	
		IF @count>0

		BEGIN
			--------- Create RDB.dbo.LDF_GENERIC2 table
			
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO = 20;
			SET @PROC_STEP_NAME = ' GENERATING TMP_BASE_GENERIC'; 

			IF OBJECT_ID('RDB.dbo.TMP_BASE_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_BASE_GENERIC;

					 SELECT S_LDF_DIMENSIONAL_DATA.*
							INTO RDB.dbo.TMP_BASE_GENERIC
							FROM RDB.dbo.S_LDF_DIMENSIONAL_DATA
								 INNER JOIN RDB.dbo.LDF_DATAMART_TABLE_REF ON PHC_CD = LDF_DATAMART_TABLE_REF.CONDITION_CD
																			  AND DATAMART_NAME = 'LDF_GENERIC2';
		
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC2','RDB.LDF_GENERIC2','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 21;
				SET @PROC_STEP_NAME = ' GENERATING TMP_LINKED_GENERIC'; 

			IF OBJECT_ID('RDB.dbo.TMP_LINKED_GENERIC', 'U') IS NOT NULL  
					DROP TABLE RDB.dbo.TMP_LINKED_GENERIC;

					SELECT GEN_LDF.*, 
						INV.INVESTIGATION_KEY, 
						INV.INV_LOCAL_ID 'INVESTIGATION_LOCAL_ID', 
						INV.CASE_OID 'PROGRAM_JURISDICTION_OID',
						GEN.PATIENT_KEY,
						PATIENT.PATIENT_LOCAL_ID 'PATIENT_LOCAL_ID',
						CONDITION.CONDITION_SHORT_NM 'DISEASE_NAME'
					INTO RDB.dbo.TMP_LINKED_GENERIC
					FROM
						TMP_BASE_GENERIC GEN_LDF with (nolock)
						INNER JOIN  RDB.dbo.INVESTIGATION INV
					ON  
						GEN_LDF.INVESTIGATION_UID=INV.CASE_UID 
					INNER JOIN RDB.dbo.GENERIC_CASE GEN with (nolock)
					ON 
						GEN.INVESTIGATION_KEY=INV.INVESTIGATION_KEY
					INNER JOIN RDB.dbo.CONDITION with (nolock)
					ON 
						CONDITION.CONDITION_KEY= GEN.CONDITION_KEY
					INNER JOIN RDB.dbo.D_PATIENT PATIENT with (nolock)
					ON 
						PATIENT.PATIENT_KEY=GEN.PATIENT_KEY
					ORDER BY 
						INVESTIGATION_UID;
				
				
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC2','RDB.LDF_GENERIC2','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
			---- CREATE TABLE ALL_GENERIC AS 
		
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 22;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_GENERIC '; 

			IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_ALL_GENERIC;

		
					SELECT A.*, 
					B.DATAMART_COLUMN_NM 'DM',
					A.phc_cd 'DISEASE_CD',
					A.page_set 'DISEASE_NM'
					INTO TMP_ALL_GENERIC
					FROM 	RDB.dbo.LDF_DATAMART_COLUMN_REF  B with (nolock)
					FULL OUTER JOIN RDB.dbo.TMP_LINKED_GENERIC A with (nolock)
					ON A.LDF_UID= B.LDF_UID WHERE
					(B.LDF_PAGE_SET ='OTHER'
					OR B.CONDITION_CD IN (SELECT CONDITION_CD FROM 
						RDB.dbo.LDF_DATAMART_TABLE_REF WHERE DATAMART_NAME = 'LDF_GENERIC2') 
					)
					ORDER BY 
						INVESTIGATION_UID;
				
					--- line 165 to 172
				
					UPDATE RDB.dbo.TMP_ALL_GENERIC
					SET DISEASE_CD = CONDITION_CD
					WHERE DATALENGTH(REPLACE(CONDITION_CD, ' ', ''))>1;  
				
					UPDATE RDB.dbo.TMP_ALL_GENERIC
					SET DISEASE_CD = PHC_CD
					WHERE DATALENGTH(REPLACE(CONDITION_CD, ' ', ''))<=1;  

					UPDATE RDB.dbo.TMP_ALL_GENERIC
					SET DISEASE_NM= PAGE_SET
					WHERE DATALENGTH(DISEASE_NM)<2;

					UPDATE RDB.dbo.TMP_ALL_GENERIC
					SET DATAMART_COLUMN_NM=DM
					WHERE DATALENGTH(DM)>2;  
				 
				 
		    					 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC2','RDB.LDF_GENERIC2','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 23;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_GENERIC_SHORT_COL'; 

			IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC_SHORT_COL', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_ALL_GENERIC_SHORT_COL;

		
					SELECT INVESTIGATION_KEY,
								INVESTIGATION_LOCAL_ID,
								PROGRAM_JURISDICTION_OID,
								PATIENT_KEY,
								PATIENT_LOCAL_ID,
								DISEASE_NAME,
								DISEASE_CD,
								DATAMART_COLUMN_NM,
								col1  
					INTO RDB.dbo.TMP_ALL_GENERIC_SHORT_COL
					FROM RDB.dbo.TMP_ALL_GENERIC with (nolock)
					WHERE data_type IN ('CV', 'ST'); 
					;
			
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC2','RDB.LDF_GENERIC2','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 24;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_GENERIC_TA'; 

			IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC_TA', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_ALL_GENERIC_TA;

				 	
					SELECT INVESTIGATION_KEY,
								INVESTIGATION_LOCAL_ID,
								PROGRAM_JURISDICTION_OID,
								PATIENT_KEY,
								PATIENT_LOCAL_ID,
								DISEASE_NAME,
								DISEASE_CD,
								DATAMART_COLUMN_NM,
								col1  
					INTO RDB.dbo.TMP_ALL_GENERIC_TA
					FROM RDB.dbo.TMP_ALL_GENERIC with (nolock) 
					WHERE data_type IN ('LIST_ST');   
				
			
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC2','RDB.LDF_GENERIC2','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO =25;
				SET @PROC_STEP_NAME = ' GENERATING TMP_GENERIC_TA'; 
				set @count = (SELECT count(*) FROM TMP_ALL_GENERIC_TA)
				if @count > 0
				BEGIN
					IF OBJECT_ID('RDB.dbo.TMP_GENERIC_TA', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_GENERIC_TA;

		
					ALTER TABLE TMP_ALL_GENERIC_TA
					ADD ANSWERCOL varchar(8000);

					UPDATE TMP_ALL_GENERIC_TA
					SET ANSWERCOL = SUBSTRING(COL1, 1, 8000); 

					ALTER TABLE TMP_ALL_GENERIC_TA
					DROP COLUMN COL1;

					--DECLARE @cols  AS NVARCHAR(MAX)='';
					--DECLARE @query AS NVARCHAR(MAX)='';

					SET @cols='';
					SET @query='';

					SELECT @cols = @cols + QUOTENAME(DATAMART_COLUMN_NM) + ',' FROM (select distinct DATAMART_COLUMN_NM from TMP_ALL_GENERIC_TA ) as tmp
					select @cols = substring(@cols, 0, len(@cols)) --trim "," at end

					--PRINT CAST(@cols AS NVARCHAR(30))
					set @query = 
					'SELECT *
					INTO TMP_GENERIC_TA
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
						
					FROM RDB.dbo.TMP_ALL_GENERIC_TA  with (nolock))
					as A 

					PIVOT ( MAX([ANSWERCOL]) FOR DATAMART_COLUMN_NM   IN (' + @cols + ')) AS PivotTable';
					execute(@query)
		
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;
				END
				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC2','RDB.LDF_GENERIC2','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
				
				-- If data does not exist create TMP_GENERIC_TA table same as TMP_ALL_GENERIC_TA, which will be used while merging table in step 9
				set @count = (SELECT count(*) FROM TMP_ALL_GENERIC_TA)
				if @count = 0
				BEGIN
					IF OBJECT_ID('RDB.dbo.TMP_GENERIC_TA', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_GENERIC_TA;
							
						SELECT INVESTIGATION_KEY,
							   INVESTIGATION_LOCAL_ID,
							   PROGRAM_JURISDICTION_OID,
							   PATIENT_KEY,
							   PATIENT_LOCAL_ID,
							   DISEASE_NAME,
							   DISEASE_CD
						INTO RDB.dbo.TMP_GENERIC_TA
						FROM RDB.dbo.TMP_ALL_GENERIC_TA with (nolock);
						
				END	
				
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 26;
				SET @PROC_STEP_NAME = ' GENERATING TMP_ALL_GENERIC_SHORT_COL'; 
				set @count = (SELECT count(*) FROM TMP_ALL_GENERIC_SHORT_COL)
				if @count > 0
				BEGIN
					IF OBJECT_ID('RDB.dbo.TMP_GENERIC_SHORT_COL', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_GENERIC_SHORT_COL;

					ALTER TABLE TMP_ALL_GENERIC_SHORT_COL
					ADD ANSWERCOL varchar(8000);

					UPDATE TMP_ALL_GENERIC_SHORT_COL
					SET ANSWERCOL = SUBSTRING(COL1, 1, 8000); 

					ALTER TABLE TMP_ALL_GENERIC_SHORT_COL
					DROP COLUMN COL1;

					--DECLARE @cols  AS NVARCHAR(MAX)='';
					--DECLARE @query AS NVARCHAR(MAX)='';

					SET @cols='';
					SET @query='';
					BEGIN TRY
						SELECT @cols = @cols + QUOTENAME(DATAMART_COLUMN_NM) + ',' FROM (select distinct DATAMART_COLUMN_NM from TMP_ALL_GENERIC_SHORT_COL ) as tmp
						select @cols = substring(@cols, 0, len(@cols)) --trim "," at end

						set @query = 
						'SELECT *
						INTO TMP_GENERIC_SHORT_COL
						FROM
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
							
						FROM RDB.dbo.TMP_ALL_GENERIC_SHORT_COL with (nolock) )
						as A 

						PIVOT ( MAX([ANSWERCOL]) FOR DATAMART_COLUMN_NM   IN (' + @cols + ')) AS PivotTable';
						execute(@query)
					END TRY  
					BEGIN CATCH

						IF @ErrorNumber1=511
						BEGIN
							SET @cols='';
							SET @query='';
							SELECT @cols = @cols + QUOTENAME(DATAMART_COLUMN_NM) + ',' FROM (select distinct top 300 DATAMART_COLUMN_NM from RDB.dbo.TMP_ALL_GENERIC_SHORT_COL ) as tmp
							select @cols = substring(@cols, 0, len(@cols)) --trim "," at end

							IF OBJECT_ID('RDB.dbo.TMP_GENERIC_SHORT_COL', 'U') IS NOT NULL  
							DROP TABLE RDB.dbo.TMP_GENERIC_SHORT_COL;

							set @query = 
							'SELECT *
							INTO TMP_GENERIC_SHORT_COL
							FROM
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
								
							FROM RDB.dbo.TMP_ALL_GENERIC_SHORT_COL with (nolock) )
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
					VALUES(@BATCH_ID,'LDF_GENERIC2','RDB.LDF_GENERIC2','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				-- If data does not exist create TMP_GENERIC_SHORT_COL table same as TMP_ALL_GENERIC_SHORT_COL, which will be used while merging table in step 9
				set @count = (SELECT count(*) FROM TMP_ALL_GENERIC_SHORT_COL)
				if @count = 0
				BEGIN
				IF OBJECT_ID('RDB.dbo.TMP_GENERIC_SHORT_COL', 'U') IS NOT NULL  
						DROP TABLE RDB.dbo.TMP_GENERIC_SHORT_COL;
					
					SELECT INVESTIGATION_KEY,
						   INVESTIGATION_LOCAL_ID,
						   PROGRAM_JURISDICTION_OID,
						   PATIENT_KEY,
						   PATIENT_LOCAL_ID,
						   DISEASE_NAME,
						   DISEASE_CD
						INTO RDB.dbo.TMP_GENERIC_SHORT_COL
						FROM RDB.dbo.TMP_ALL_GENERIC_SHORT_COL with (nolock);
				END
				
				BEGIN TRANSACTION;

				SET @PROC_STEP_NO = 27;
				SET @PROC_STEP_NAME = ' GENERATING TMP_GENERIC'; 

			IF OBJECT_ID('RDB.dbo.TMP_GENERIC', 'U') IS NOT NULL  
					 DROP TABLE RDB.dbo.TMP_GENERIC;

					EXECUTE  [dbo].[sp_MERGE_TWO_TABLES] 
						   @INPUT_TABLE1='RDB.dbo.TMP_GENERIC_SHORT_COL'
						  ,@INPUT_TABLE2='RDB.dbo.TMP_GENERIC_TA'
						  ,@OUTPUT_TABLE='RDB.dbo.TMP_GENERIC'
						  ,@JOIN_ON_COLUMN='INVESTIGATION_KEY';
						  
					
					DELETE FROM RDB.dbo.TMP_GENERIC WHERE INVESTIGATION_KEY IS NULL;
		
							 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

				 INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC2','RDB.LDF_GENERIC2','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		
				

				SET @PROC_STEP_NO = 28;
				SET @PROC_STEP_NAME = ' GENERATING LDF_GENERIC2'; 

				--- LDF_GENERIC2 is Dimensonal table, insert/update incremental data
						
				--- If the TMP_GENERIC has additional columns compare to LDF_GENERIC2, add these additional columns in LDF_GENERIC2 table.
				
				IF OBJECT_ID('RDB.dbo.LDF_GENERIC2', 'U') IS NOT NULL
				BEGIN
				
					BEGIN TRANSACTION;
					
						SET @Alterdynamiccolumnlist='';
						SET @dynamiccolumnUpdate='';
							 
						SELECT   @Alterdynamiccolumnlist  = @Alterdynamiccolumnlist+ 'ALTER TABLE RDB.dbo.LDF_GENERIC2 ADD [' + name   +  '] varchar(4000) ',
							@dynamiccolumnUpdate= @dynamiccolumnUpdate + 'LDF_GENERIC2.[' +  name  + ']='  + 'RDB.dbo.TMP_GENERIC.['  +name  + '] ,'
						FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID('RDB.dbo.TMP_GENERIC')
						AND name NOT IN  ( SELECT name FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID('RDB..LDF_GENERIC2'))
							
							
						--PRINT '@@Alterdynamiccolumnlist -----------	'+CAST(@Alterdynamiccolumnlist AS NVARCHAR(max))
						--PRINT '@@@@dynamiccolumnUpdate -----------	'+CAST(@dynamiccolumnUpdate AS NVARCHAR(max))

						IF @Alterdynamiccolumnlist IS NOT NULL AND @Alterdynamiccolumnlist!=''
						BEGIN

							EXEC(  @Alterdynamiccolumnlist)

							SET  @dynamiccolumnUpdate=substring(@dynamiccolumnUpdate,1,len(@dynamiccolumnUpdate)-1)

							EXEC ('update  RDB.dbo.LDF_GENERIC2  SET ' +   @dynamiccolumnUpdate + ' FROM RDB.dbo.TMP_GENERIC     
							   inner join  RDB.dbo.LDF_GENERIC2  on  RDB.dbo.TMP_GENERIC.INVESTIGATION_LOCAL_ID =  RDB.dbo.LDF_GENERIC2.INVESTIGATION_LOCAL_ID')

						END
					
					COMMIT TRANSACTION;
					
					BEGIN TRANSACTION;
						--In case of updates, delete the existing ones and insert updated ones in LDF_GENERIC2
						DELETE FROM RDB.dbo.LDF_GENERIC2 WHERE INVESTIGATION_KEY IN (SELECT INVESTIGATION_KEY FROM RDB.dbo.TMP_GENERIC);
					COMMIT TRANSACTION;
					
					BEGIN TRANSACTION;
					
						--- During update if TMP_GENERIC has 4 columns updated only and the LDF_GENERIC2 has 7 columns then get column name dynamically from TMP_GENERIC and populate them.
					
						SET @dynamiccolumnList =''
						SELECT @dynamiccolumnList= @dynamiccolumnList +'['+ name +'],' FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID('RDB.dbo.TMP_GENERIC')
						SET  @dynamiccolumnList=substring(@dynamiccolumnList,1,len(@dynamiccolumnList)-1)

						--PRINT '@@@@@dynamiccolumnList -----------	'+CAST(@dynamiccolumnList AS NVARCHAR(max))

						EXEC ('INSERT INTO RDB.dbo.LDF_GENERIC2 ('+@dynamiccolumnList+')
						SELECT '+@dynamiccolumnList +'
						FROM RDB.dbo.TMP_GENERIC');
						
						SELECT @ROWCOUNT_NO = @@ROWCOUNT;
						
					COMMIT TRANSACTION;
				
				END
					
					---- This is one time deal, if table does not exist then create it.
					IF OBJECT_ID('RDB.dbo.LDF_GENERIC2', 'U') IS NULL 
					BEGIN
						BEGIN TRANSACTION;
						
							SELECT *
							INTO RDB.dbo.LDF_GENERIC2
							FROM RDB.dbo.TMP_GENERIC;
						
							SELECT @ROWCOUNT_NO = @@ROWCOUNT;
						COMMIT TRANSACTION;
					END			 

				BEGIN TRANSACTION;
				
				INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
					(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
					VALUES(@BATCH_ID,'LDF_GENERIC2','RDB.LDF_GENERIC2','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

				COMMIT TRANSACTION;
		END

		IF OBJECT_ID('RDB.dbo.TMP_BASE_GENERIC', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_BASE_GENERIC; 

		IF OBJECT_ID('RDB.dbo.TMP_LINKED_GENERIC', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_LINKED_GENERIC; 

		IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_ALL_GENERIC; 

		IF OBJECT_ID('RDB.dbo.TMP_GENERIC', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_GENERIC; 
				 
		IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC_SHORT_COL', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_ALL_GENERIC_SHORT_COL; 

		IF OBJECT_ID('RDB.dbo.TMP_ALL_GENERIC_TA', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_ALL_GENERIC_TA; 

		IF OBJECT_ID('RDB.dbo.TMP_GENERIC_TA', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_GENERIC_TA; 

		IF OBJECT_ID('RDB.dbo.TMP_GENERIC_SHORT_COL', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_GENERIC_SHORT_COL; 
				 
		IF OBJECT_ID('RDB.dbo.TMP_GENERIC', 'U') IS NOT NULL  
				 DROP TABLE RDB.dbo.TMP_GENERIC; 


    BEGIN TRANSACTION ;
	
	SET @Proc_Step_no = 29;
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
           'LDF_GENERIC'
           ,'RDB.LDF_GENERIC'
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
           ,'LDF_GENERIC'
           ,'RDB.LDF_GENERIC'
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