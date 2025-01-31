
USE rdb;
GO

--		exec rdb.dbo.DynDm_AlterKey_sp 999,'CYCLOSPORIASIS';

--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'CONG_SYPHILIS';


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


			 IF OBJECT_ID('rdb.dbo.DynDm_AlterKey_sp', 'P') IS NOT NULL
				BEGIN
				DROP PROCEDURE dbo.DynDm_AlterKey_sp;
				END;

GO



CREATE PROCEDURE [dbo].DynDm_AlterKey_sp
 
            @batch_id BIGINT,
			@DATAMART_NAME VARCHAR(100)
	AS
BEGIN  
	 BEGIN TRY
	


--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'GENERIV_V2';

		

		
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
           ,'RDB.DBO.DynDm_AlterKey_sp '+ @DATAMART_NAME
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
    COMMIT TRANSACTION;
	
	

	BEGIN TRANSACTION;

	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  Rename Key Batch 1';

		
		--select * from rdb.dbo.tmp_DynDm_Patient_Data

	exec sp_rename 'rdb.dbo.tmp_DynDm_Investigation_Data.INVESTIGATION_KEY', 'INVESTIGATION_KEY_INVESTIGATION_DATA','COLUMN';

	exec sp_rename 'rdb.dbo.tmp_DynDm_Patient_Data.INVESTIGATION_KEY', 'INVESTIGATION_KEY_PATIENT_DATA','COLUMN';


    exec sp_rename 'rdb.dbo.tmp_DynDm_Patient_Data.PATIENT_LOCAL_ID', 'PATIENT_LOCAL_ID_PATIENT_DATA','COLUMN';



		
	SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_AlterKey_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;


	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  Rename Key Batch 2';
	
		
exec sp_rename 'rdb.dbo.tmp_DynDm_Case_Management_Data.INVESTIGATION_KEY', 'INVESTIGATION_KEY_CASE_MANAGEMENT_DATA','COLUMN';

exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_Administrative.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_ADMINISTRATIVE','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_CLINICAL.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_CLINICAL','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_COMPLICATION.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_COMPLICATION','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_CONTACT.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_CONTACT','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_DEATH.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_DEATH','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_EPIDEMIOLOGY.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_EPIDEMIOLOGY','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_HIV.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_HIV','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_PATIENT_OBS.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_PATIENT_OBS','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_ISOLATE_TRACKING.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_ISOLATE_TRACKING','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_LAB_FINDING.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_LAB_FINDING','COLUMN';


	SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_AlterKey_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  Rename Key Batch 3';





exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_MEDICAL_HISTORY.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_MEDICAL_HISTORY','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_MOTHER.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_MOTHER','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_OTHER.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_OTHER','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_PREGNANCY_BIRTH.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_PREGNANCY_BIRTH','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_RESIDENCY.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_RESIDENCY','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_RISK_FACTOR.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_RISK_FACTOR','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_SOCIAL_HISTORY.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_SOCIAL_HISTORY','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_SYMPTOM.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_SYMPTOM','COLUMN';


	SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_AlterKey_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  Rename Key Batch 4';
			
		
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_TREATMENT.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_TREATMENT','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_TRAVEL.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_TRAVEL','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_UNDER_CONDITION.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_UNDER_CONDITION','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_VACCINATION.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_VACCINATION','COLUMN';


exec sp_rename 'rdb.dbo.tmp_DynDm_D_INV_STD.INVESTIGATION_KEY', 'INVESTIGATION_KEY_D_INV_STD','COLUMN';


	SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_AlterKey_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  Rename Key Batch 5';
			


exec sp_rename 'rdb.dbo.tmp_DynDm_Organization.INVESTIGATION_KEY', 'INVESTIGATION_KEY_ORGANIZATION','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_PROVIDER.INVESTIGATION_KEY', 'INVESTIGATION_KEY_PROVIDER','COLUMN';


exec sp_rename 'rdb.dbo.tmp_DynDm_REPEAT_BLOCK_DATE_ALL.INVESTIGATION_KEY', 'INVESTIGATION_KEY_REPEAT_BLOCK_DATE_ALL','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_DATE.INVESTIGATION_KEY', 'INVESTIGATION_KEY_REPEAT_DATE','COLUMN';


	SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_AlterKey_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_fixcols';
	  


  IF OBJECT_ID('rdb.dbo.tmp_DynDm_fixcols', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_fixcols;



						select table_name, column_name, cast( null as varchar(max)) as dsql,  RANK() OVER (PARTITION BY column_name order by table_name)  as Rank_no
									into		rdb.dbo.tmp_DynDm_fixcols	
									from INFORMATION_SCHEMA.COLUMNS where lower(column_name) in (
									SELECT      lower(COLUMN_NAME ) 
									--,TABLE_NAME AS  'TableName'

						FROM        INFORMATION_SCHEMA.COLUMNS
						WHERE       ( lower(TABLE_NAME) LIKE lower('tmp_DynDm_D_INV_%')  
						              or lower(table_name) like '%tmp_DynDm_%_REPEAT_%' 
									  or lower(table_name) in  ('tmp_DynDm_Investigation_Data','tmp_DynDm_Patient_Data','tmp_DynDm_Case_Management_Data')
									  ) 
								 and COLUMN_NAME not in (
								 'DATAMART_NM',
						'OTHER_VALUE_IND_CD',
						'RDB_COLUMN_NM',
						'RDB_TABLE_NM',
						'USER_DEFINED_COLUMN_NM'
						)
						group  BY   Column_Name
			
									having count(*) > 1
									)
						and        ( lower(TABLE_NAME) LIKE lower('tmp_DynDm_D_INV_%')  or lower(table_name) like '%tmp_DynDm_%_REPEAT_%'
												  or lower(table_name) in  ('tmp_DynDm_Investigation_Data','tmp_DynDm_Patient_Data','tmp_DynDm_Case_Management_Data')
			                        )
									;



						update   rdb.dbo.tmp_DynDm_fixcols
						set dsql = 'exec sp_rename ''rdb.dbo.'+table_name+'.'+column_name+''', '''+column_name+ cast(Rank_no as varchar)+''',''COLUMN'' ;'
						where rank_no <> 1
						;

						DECLARE @Sql NVARCHAR(MAX);


						DECLARE Cur CURSOR LOCAL FAST_FORWARD FOR 
						SELECT  dsql
						FROM  rdb.dbo.tmp_DynDm_fixcols 
						where rank_no <> 1
						;


						OPEN Cur

						  FETCH NEXT FROM Cur INTO @Sql 

						WHILE (@@FETCH_STATUS = 0)
						BEGIN
						   --DX  select @Sql;
							Exec sp_executesql @Sql;
							 FETCH NEXT FROM Cur INTO @Sql 
						END

						CLOSE Cur
						DEALLOCATE Cur;



				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
		 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
		 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_AlterKey_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,0 );
  
		
	
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
						   ,'RDB.DBO.DynDm_AlterKey_sp '+@DATAMART_NAME
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
						VALUES( @Batch_id, 'DYNAMIC_DATAMART', 'RDB.DBO.DynDm_AlterKey_sp : ' + @DATAMART_NAME, 'ERROR', @Proc_Step_no, 'ERROR - '+@Proc_Step_name, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );
						RETURN -1;
	        END CATCH;
END;

GO








