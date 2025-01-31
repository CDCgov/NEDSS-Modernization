
  IF OBJECT_ID('rdb.dbo.tmp_DynDm_Investigation_Data', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_Investigation_Data;
	
	go


USE RDB;
go





		--select  distinct ''''+DATAMART_NM+'''',*
		--from rdb.dbo.tmp_DynDm_INIT_FORM_SET
		--;
		--go

--%MACRO INVEST_FORM_PROC();

-- exec [dbo].sp_DynDM_INVEST_FORM_PROC 999, 'CONG_SYPHILIS';


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


IF OBJECT_ID('rdb.dbo.DynDm_Invest_Form_Proc_sp', 'P') IS NOT NULL
				DROP PROCEDURE dbo.DynDm_Invest_Form_Proc_sp;

GO


CREATE  PROCEDURE [dbo].DynDm_Invest_Form_Proc_sp

            @batch_id BIGINT,
			@DATAMART_NAME VARCHAR(100)
	AS
BEGIN  
	 BEGIN TRY
	
	    --	DECLARE  @batch_id BIGINT = 999;  DECLARE  @DATAMART_NAME VARCHAR(100) = 'CONG_SYPHILIS'; 
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
           ,'RDB.DBO.DynDM_INVEST_FORM_PROC '+@DATAMART_NAME
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
     COMMIT TRANSACTION ;
	
	

	 BEGIN TRANSACTION;

	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_SUMM_DATAMART';
	

	


  --DROP TABLE RDB.DBO.dbo.tmp_DynDm_SUMM_DATAMART;


/*Creates a summ_datamart with patient key, investigation key and disease group code*/
  --CREATE TABLE rdb.dbo.SUMM_DATAMART AS
  
  IF OBJECT_ID('rdb.dbo.tmp_DynDm_SUMM_DATAMART', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_SUMM_DATAMART;
	

  SELECT PATIENT_KEY AS PATIENT_KEY, INVESTIGATION_KEY, DISEASE_GRP_CD
    into rdb.dbo.tmp_DynDm_SUMM_DATAMART
     FROM rdb.dbo.INV_SUMM_DATAMART with ( nolock) 
       INNER JOIN rdb.dbo.CONDITION with ( nolock)  ON   INV_SUMM_DATAMART.DISEASE_CD = CONDITION.CONDITION_CD
     WHERE CONDITION.DISEASE_GRP_CD = (SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM=@DATAMART_NAME);
	 ;


	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_INVEST_FORM_PROC '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_INV_METADATA';
	



/*Investigation data*/
/*-------INVESTIGATION----------*/

/*creates a table with all the metadata related to the investigation*/
--CREATE TABLE INV_METADATA AS



  IF OBJECT_ID('rdb.dbo.tmp_DynDm_INV_METADATA', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_INV_METADATA;
	

SELECT  DISTINCT INIT.FORM_CD, INIT.DATAMART_NM, NBS_RDB_METADATA.RDB_TABLE_NM, 
            NBS_RDB_METADATA.RDB_COLUMN_NM,NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM 
into rdb.dbo.tmp_DynDm_INV_METADATA
FROM rdb.dbo.TMP_INIT INIT
  INNER JOIN NBS_ODSE..NBS_UI_METADATA  with ( nolock) ON NBS_UI_METADATA.INVESTIGATION_FORM_CD = INIT.FORM_CD
  INNER JOIN NBS_ODSE..NBS_RDB_METADATA  with ( nolock)  ON NBS_UI_METADATA.NBS_UI_METADATA_UID = NBS_RDB_METADATA.NBS_UI_METADATA_UID
WHERE RDB_TABLE_NM='INVESTIGATION' 
  AND NBS_UI_METADATA.INVESTIGATION_FORM_CD=(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM=@DATAMART_NAME)
  AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM <> '' and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
;



	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_INVEST_FORM_PROC '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_Investigation_Data';
	

/*Make a list of rdb_column_nm separated by ,*/

/*
%ASSIGN_KEY (INV_METADATA, SORT_KEY);
DATA INVLISTING;
  SET INV_METADATA;
	BY SORT_KEY;

	LENGTH RDB_COLUMN_NAME_LIST &MAX_COLUMN_CHARACTER_LIMIT; 
	RETAIN RDB_COLUMN_NAME_LIST;
	IF FIRST.SORT_KEY THEN
		USER_DEFINED_COLUMN_NAME = TRIM(RDB_COLUMN_NM) || ' AS ' ||TRIM(USER_DEFINED_COLUMN_NM)|| "'"||TRIM(USER_DEFINED_COLUMN_NM)||"'";
		RDB_COLUMN_NAME_LIST = (USER_DEFINED_COLUMN_NAME || ', ' || RDB_COLUMN_NAME_LIST);  
	OUTPUT;
*/


--DX select * from  rdb.dbo.tmp_DynDm_INV_METADATA ;

DECLARE @listStr VARCHAR(MAX)

SELECT @listStr = COALESCE(@listStr+',' ,'') + RDB_COLUMN_NM  + ' '+ coalesce(USER_DEFINED_COLUMN_NM,'')
FROM  rdb.dbo.tmp_DynDm_INV_METADATA;


--DX SELECT @listStr



/*
/*It seems it is getting rid off the last character*/
	 CALL SYMPUTX('INV_SEL2', '');
	LENGTH=LENGTHN(RDB_COLUMN_NAME_LIST);
	TRIMMED_VALUE=SUBSTR(RDB_COLUMN_NAME_LIST, 1, LENGTH-1);
	 CALL SYMPUTX('INV_SEL2', TRIMMED_VALUE);
*/


/*It creates a new table with a list of all the rdb column name plus the investigation key*/
--	CREATE TABLE INVESTIGATION_DATA AS 



  IF OBJECT_ID('rdb.dbo.tmp_DynDm_Investigation_Data', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_Investigation_Data;
	


    declare @SQL varchar(MAX);


	SET @SQL = ' SELECT  '+@listStr +' , tmp_DynDm_SUMM_DATAMART.INVESTIGATION_KEY ' +
	       ' into rdb.dbo.tmp_DynDm_Investigation_Data ' +
	       ' FROM rdb.dbo.INVESTIGATION  with ( nolock) ' +
	       '    INNER JOIN rdb.dbo.tmp_DynDm_SUMM_DATAMART ON	tmp_DynDm_SUMM_DATAMART.INVESTIGATION_KEY  =INVESTIGATION.INVESTIGATION_KEY ' +
           ' WHERE tmp_DynDm_SUMM_DATAMART.DISEASE_GRP_CD = (SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM='''+@DATAMART_NAME+''')'
           ;


--DX	select  @SQL;

     EXEC (@SQL);


	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_INVEST_FORM_PROC '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_Case_Management_Metadata';
	

	--DX  select * from rdb.dbo.tmp_DynDm_Investigation_Data;

/*-------INVESTIGATION ENDS----------*/
/*CMData  data*/


/*it creates a new table with all the metadata associated to that investigation for the specific datamart name*/
--	CREATE TABLE CASE_MANAGEMENT_METADATA AS


     IF OBJECT_ID('rdb.dbo.tmp_DynDm_Case_Management_Metadata', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_Case_Management_Metadata;
	



	SELECT  DISTINCT INIT.FORM_CD, INIT.DATAMART_NM, NBS_RDB_METADATA.RDB_TABLE_NM,
	NBS_RDB_METADATA.RDB_COLUMN_NM,NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM
	into rdb.dbo.tmp_DynDm_Case_Management_Metadata
	FROM rdb.dbo.TMP_INIT INIT
	 INNER JOIN NBS_ODSE..NBS_UI_METADATA  with ( nolock) 	ON NBS_UI_METADATA.INVESTIGATION_FORM_CD = INIT.FORM_CD
	 INNER JOIN NBS_ODSE..NBS_RDB_METADATA  with ( nolock) 	ON NBS_UI_METADATA.NBS_UI_METADATA_UID = NBS_RDB_METADATA.NBS_UI_METADATA_UID
	WHERE RDB_TABLE_NM='D_CASE_MANAGEMENT' 
	 AND NBS_UI_METADATA.INVESTIGATION_FORM_CD=(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM=@DATAMART_NAME)
	 AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM <> '' 
	 and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
	--ORDER BY INIT.FORM_CD,  NBS_RDB_METADATA.RDB_COLUMN_NM
	;

	--DX select *	from  rdb.dbo.tmp_DynDm_Case_Management_Metadata;

	
	update  rdb.dbo.tmp_DynDm_Case_Management_Metadata 
	set FORM_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM=@DATAMART_NAME),
	RDB_TABLE_NM='D_CASE_MANAGEMENT'
	;

	

	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_INVEST_FORM_PROC '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_INV_SUMM_DATAMART';
	
	
	
	

/*New table with some columns of the investigation summary datamart*/


--CREATE TABLE INV_SUMM_DATAMART AS



     IF OBJECT_ID('rdb.dbo.tmp_DynDm_INV_SUMM_DATAMART', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_INV_SUMM_DATAMART;
	
	
	 SELECT INV_SUMM_DATAMART.PROGRAM_JURISDICTION_OID,
			INV_SUMM_DATAMART.INVESTIGATION_KEY,
			INV_SUMM_DATAMART.PATIENT_KEY,
			INV_SUMM_DATAMART.PATIENT_LOCAL_ID,
			INV_SUMM_DATAMART.INVESTIGATION_CREATE_DATE,
			INV_SUMM_DATAMART.INVESTIGATION_CREATED_BY,
			INV_SUMM_DATAMART.INVESTIGATION_LAST_UPDTD_DATE,
			INV_SUMM_DATAMART.INVESTIGATION_LAST_UPDTD_BY,
			INV_SUMM_DATAMART.EVENT_DATE,
			INV_SUMM_DATAMART.EVENT_DATE_TYPE,
			INV_SUMM_DATAMART.LABORATORY_INFORMATION,
			INV_SUMM_DATAMART.EARLIEST_SPECIMEN_COLLECT_DATE,
			INV_SUMM_DATAMART.NOTIFICATION_STATUS,
			INV_SUMM_DATAMART.CONFIRMATION_METHOD, 
			INV_SUMM_DATAMART.CONFIRMATION_DT, 
			INV_SUMM_DATAMART.DISEASE_CD,
			INV_SUMM_DATAMART.DISEASE,
			INV_SUMM_DATAMART.NOTIFICATION_LAST_UPDATED_DATE, 
			INV_SUMM_DATAMART.NOTIFICATION_LOCAL_ID,
			INV_SUMM_DATAMART.PROGRAM_AREA, 
			--INV_SUMM_DATAMART.INVESTIGATION_LAST_UPDTD_BY,
			INV_SUMM_DATAMART.PATIENT_COUNTY_CODE,
			INV_SUMM_DATAMART.JURISDICTION_NM
	into rdb.dbo.tmp_DynDm_INV_SUMM_DATAMART
	FROM rdb.dbo.INV_SUMM_DATAMART  with ( nolock) 
	INNER JOIN rdb.dbo.tmp_DynDm_Investigation_Data ON tmp_DynDm_Investigation_Data.INVESTIGATION_KEY = INV_SUMM_DATAMART.INVESTIGATION_KEY
	;
	


/*Patient data*/

	--CREATE TABLE PAT_METADATA AS 


	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_INVEST_FORM_PROC '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_PAT_METADATA';
	


     IF OBJECT_ID('rdb.dbo.tmp_DynDm_PAT_METADATA', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_PAT_METADATA;

	SELECT  DISTINCT INIT.FORM_CD, INIT.DATAMART_NM, NBS_RDB_METADATA.RDB_TABLE_NM, NBS_RDB_METADATA.RDB_COLUMN_NM,NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM 
	into rdb.dbo.tmp_DynDm_PAT_METADATA
	FROM rdb.dbo.TMP_INIT INIT
	  INNER JOIN NBS_ODSE..NBS_UI_METADATA  with ( nolock) ON NBS_UI_METADATA.INVESTIGATION_FORM_CD = INIT.FORM_CD
      INNER JOIN NBS_ODSE..NBS_RDB_METADATA  with ( nolock) ON NBS_UI_METADATA.NBS_UI_METADATA_UID = NBS_RDB_METADATA.NBS_UI_METADATA_UID
   WHERE RDB_TABLE_NM='D_PATIENT' 
     AND NBS_UI_METADATA.INVESTIGATION_FORM_CD=(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM=@DATAMART_NAME)
     AND RDB_COLUMN_NM NOT IN ('PATIENT_WORK_STREET_ADDRESS_1', 'PATIENT_WORK_STREET_ADDRESS_2')
     AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM <> '' 
	 and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
    ;



	
	/*
/*List of rdb_column name for patient data*/
%ASSIGN_KEY (PAT_METADATA, SORT_KEY);
DATA LISTING;
  SET PAT_METADATA;
	BY SORT_KEY;

	LENGTH RDB_COLUMN_NAME_LIST &MAX_COLUMN_CHARACTER_LIMIT; 
	RETAIN RDB_COLUMN_NAME_LIST;
	IF FIRST.SORT_KEY THEN
		USER_DEFINED_COLUMN_NAME = ' AS ' ||USER_DEFINED_COLUMN_NM|| "'"||USER_DEFINED_COLUMN_NM||"'";
		RDB_COLUMN_NAME_LIST = (TRIM(RDB_COLUMN_NM) || USER_DEFINED_COLUMN_NAME || ', ' || RDB_COLUMN_NAME_LIST);  

	OUTPUT;

 
DELETE FROM LISTING WHERE SORT_KEY <(SELECT MAX (SORT_KEY) FROM LISTING);
*/

--DECLARE @listStr VARCHAR(MAX), @SQL VARCHAR(MAX)


SET  @listStr =null;

SELECT @listStr = COALESCE(@listStr+',' ,'') + RDB_COLUMN_NM  + ' '+ coalesce(USER_DEFINED_COLUMN_NM,'')
FROM  rdb.dbo.tmp_DynDm_PAT_METADATA;


--SELECT @listStr;




/*
DATA LISTING;
SET LISTING;
	 CALL SYMPUTX('PAT_SEL', '');
	LENGTH=LENGTHN(RDB_COLUMN_NAME_LIST);
	TRIMMED_VALUE=TRIM(SUBSTR(RDB_COLUMN_NAME_LIST, 1, LENGTH-1));
	 CALL SYMPUTX('PAT_SEL', TRIMMED_VALUE);
*/



	--CREATE TABLE PATIENT_DATA AS
	

	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_INVEST_FORM_PROC '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_Patient_Data';
	
	
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_Patient_Data', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_Patient_Data;

	
	SET @SQL = ' SELECT  '+@listStr +' , tmp_DynDm_SUMM_DATAMART.INVESTIGATION_KEY ' +
	           ' into rdb.dbo.tmp_DynDm_Patient_Data ' + 
	           ' FROM rdb.dbo.D_PATIENT  with ( nolock) ' +
               '    INNER JOIN rdb.dbo.tmp_DynDm_SUMM_DATAMART ON 	D_PATIENT.PATIENT_KEY = tmp_DynDm_SUMM_DATAMART.PATIENT_KEY '
	  ;


	  --select 'PATIENT DATA',@SQL;

	   EXEC (@SQL);



	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_INVEST_FORM_PROC '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
	

--DX select * from rdb.dbo.tmp_DynDm_Patient_Data ;


--%mend INVEST_FORM_PROC; 
	 COMMIT TRANSACTION ;
						
		
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
						   ,'RDB.DBO.DynDM_INVEST_FORM_PROC '+@DATAMART_NAME
						   ,'COMPLETE'
						   ,@Proc_Step_no
						   ,@Proc_Step_name
						   ,@RowCount_no
						   );
  
	
	 COMMIT TRANSACTION ;
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
           ,'DYNAMIC_DATAMART'
           ,'RDB.DBO.DynDM_INVEST_FORM_PROC' +@DATAMART_NAME
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




