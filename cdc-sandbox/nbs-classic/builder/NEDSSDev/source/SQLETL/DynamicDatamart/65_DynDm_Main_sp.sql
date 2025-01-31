
USE rdb;
GO

--exec  rdb.dbo.DyDM_REPEATDATEDATA_sp  231115011922 ,'GENERIC_V2' ;
		
/*   
    commit;


	--delete   from job_batch_log ;
	--select * from job_batch_log order by 1 desc;

	-- 
	delete from rdb.dbo.job_flow_log ;

	exec  rdb.dbo.DynDM_CLEAR_sp 999;

	exec rdb.dbo.DynDm_Main_sp;



select *
from rdb.dbo.job_batch_Log
order by 1 desc;



*/


--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_TABLE_NAME  VARCHAR(100) = 'GENERIC_V2';


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


			 IF OBJECT_ID('rdb.dbo.DynDm_Main_sp', 'P') IS NOT NULL
				BEGIN
				DROP PROCEDURE dbo.DynDm_Main_sp;
				END;

GO



CREATE PROCEDURE dbo.DynDm_Main_sp
 
  		--,@DATAMART_TABLE_NAME VARCHAR(100)
	AS
BEGIN  
	DECLARE @RowCount_no INT;
	DECLARE @batch_id BIGINT;

	DECLARE @type_code VARCHAR(200) = 'DynamicDatamart';
	DECLARE @type_description VARCHAR(200) = 'DynamicDatamart Process';
	DECLARE @return_value INT = 0;
	DECLARE @Proc_Step_no FLOAT = 0 ;
	DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
	DECLARE @batch_start_time datetime = null ;
	DECLARE @batch_end_time datetime = null ;



	 BEGIN TRY
		EXEC @return_value = [rdb].[dbo].[sp_nbs_batch_start] @type_code
			,@type_description;

		SELECT 'Return Value TEST ' = @return_value;




		SELECT @batch_id = batch_id
			,@batch_start_time = batch_start_dttm --,
			--  @batch_end_time = batch_end_dttm
		FROM [rdb].[dbo].[job_batch_log]
		WHERE type_code = 'DynamicDatamart'
			AND status_type = 'start'

		SET @batch_end_time = getdate();

		PRINT 'starttime' + LEFT(CONVERT(VARCHAR, @batch_start_time, 120), 10)
		PRINT 'endtime' + LEFT(CONVERT(VARCHAR, @batch_end_time, 120), 10)
		PRINT CAST(@batch_id AS VARCHAR(max))

/*

	
--DynDM_INVEST_FORM_PROC  	/*Compile and organize  formatting metadata tables for investigation, patients, etc.*/
		
--DynDM_MANAGE_CASE_MANAGEMENT_sp /*Procedure to Compile and organize  formatting metadata tables for case management*/

--DynDM_MANAGE_D_INV_sp     /* Procedure to capture  the columns necessary like OTH, UNIT, plus the regular ones.*/

--DynDM_ORGDATA.         /* Procedure  to capture  the  rdb_column_nm, user_defined_column_nm, part_type_cd, which relates to the elements coming from D_Organization*?

--DynDM_PROVDATA 		  /* Procedure  to capture the  rdb_column_nm, user_defined_column_nm, part_type_cd, which relates to the elements coming from  D_PROVIDER*/
		
--DynDM_REPEATVARCHARDATA /*  Procedure  to handle repeating blocks of varchar questions*/
		
--DynDM_REPEATDATEDATA    /*  Procedure  to handle repeating blocks of date questions*/	
		
--DynDM_REPEATNUMERICDATA /* Procedure  to ahndle repeating blocks of numeric questions*/
		
--DyDM_Main_sp          /* Procedure  to craete  actual Dyanmic datamart_table. */
		
--DynDM_INVEST_FORM_CLEAR_PROC /* Procedure  to cleanup the collected data set */

*/
*/
	


--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_TABLE_NAME  VARCHAR(100) = 'GENERIC_V2';

	SET @Proc_Step_no = 1;
	SET @Proc_Step_Name = 'SP_Clear';

		print @batch_id
		EXEC  RDB.dbo.DynDM_CLEAR_sp ;
		EXEC  RDB.dbo.[sp_nbs_batch_log_activity]  @batch_id,  @package_Name = N'RDB.DBO.DynDm Clear' ;
				
		print 'clear DynDM_CLEAR_sp called'

			SET @Proc_Step_no = 1;
	SET @Proc_Step_Name = 'SP_Start';

	

	
	BEGIN TRANSACTION; 
	
    INSERT INTO rdb.dbo.[job_flow_log] (
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
           ,'RDB.DBO.DynDm_Main_sp'
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
    COMMIT TRANSACTION;
	
	

	BEGIN TRANSACTION;

	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING tmp_INIT';
	


	   	  

/*
/*calling the existing macro etllib*/
%etllib;

/*formatting*/
OPTIONS COMPRESS=YES;
options fmtsearch=(nbsfmt);
/*Creates a global variable assigned to 0*/
%global etlerr;
%let etlerr=0; 
/*Include this file in order to be able to call the macros. We need to run the code from Autoexec*/
%include dyndmpgm(DynamicDataMacro.sas);
OPTIONS NOCARDIMAGE;
/***TBD start*/
/*Only shows if key is not null*/

%MACRO ASSIGN_KEY (DS, KEY);
 DATA &DS;
  IF &KEY=1 THEN OUTPUT;
  SET &DS;  
	&KEY+1;
	OUTPUT;     
 RUN; 
 PROC SORT DATA=&DS NODUPKEY; BY &KEY;RUN;
%MEND ASSIGN_KEY;
/*loading the data in the database*/
PROC DATASETS LIB=WORK MEMTYPE=DATA 
		KILL; 
RUN; 
 


  DROP TABLE rdb.dbo.INIT;

*/


-- DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_TABLE_NAME  VARCHAR(100) = 'GENERIC_V2';



/*It creates a table with 2 columns: form_cd and the datamart_nm associated to the investigation page, where there's a datamart name defined and a condition associated to the investigation.*/
--CREATE TABLE rdb.dbo.INIT AS

  IF OBJECT_ID('rdb.dbo.tmp_INIT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_INIT;
	
	SELECT  NBS_PAGE.FORM_CD, NBS_PAGE.DATAMART_NM 
	into rdb.dbo.tmp_INIT
	FROM NBS_ODSE..PAGE_COND_MAPPING 
	   INNER JOIN NBS_ODSE..NBS_PAGE 	ON PAGE_COND_MAPPING.WA_TEMPLATE_UID = NBS_PAGE.WA_TEMPLATE_UID
    WHERE DATAMART_NM IS NOT NULL 
	  AND CONDITION_CD IS NOT NULL
	;



	
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Main_sp '  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  NBS_PAGE';
	

/*It creates a table with 2 columns: form_cd and the datamart_nm associated to the investigation page, where there's a datamart name defined and a condition associated to the investigation.*/
--CREATE TABLE rdb.dbo.NBS_PAGE AS

  IF OBJECT_ID('rdb.dbo.NBS_PAGE', 'U') IS NOT NULL   
 				drop table rdb.dbo.NBS_PAGE;
	
	SELECT  DISTINCT NBS_PAGE.FORM_CD, NBS_PAGE.DATAMART_NM 
	into rdb.dbo.NBS_PAGE
	FROM NBS_ODSE..PAGE_COND_MAPPING 
	   INNER JOIN NBS_ODSE..NBS_PAGE 	ON PAGE_COND_MAPPING.WA_TEMPLATE_UID = NBS_PAGE.WA_TEMPLATE_UID
    WHERE DATAMART_NM IS NOT NULL 
	AND CONDITION_CD IS NOT NULL
	;


	

	
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Main_sp '  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_INIT_FORM_SET';
	


/*it creates a table with 2 columns: investigation_form_cd and the datamart_nm associated to the investigation page where the rdb_table_nm is investigation*/
-- CREATE TABLE INIT_FORM_SET AS


  IF OBJECT_ID('rdb.dbo.tmp_INIT_FORM_SET', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_INIT_FORM_SET;

SELECT distinct tINIT.FORM_CD  AS INVESTIGATION_FORM_CD , tINIT.DATAMART_NM  
into rdb.dbo.tmp_INIT_FORM_SET
FROM rdb.dbo.tmp_INIT tINIT
   INNER JOIN NBS_ODSE..NBS_UI_METADATA ON NBS_UI_METADATA.INVESTIGATION_FORM_CD = tINIT.FORM_CD
   INNER JOIN NBS_ODSE..NBS_RDB_METADATA ON NBS_UI_METADATA.NBS_UI_METADATA_UID = NBS_RDB_METADATA.NBS_UI_METADATA_UID
WHERE RDB_TABLE_NM='INVESTIGATION' 
--ORDER BY tINIT.FORM_CD,  NBS_RDB_METADATA.RDB_COLUMN_NM
;


				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Main_sp '  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    
select *
from  rdb.dbo.tmp_INIT_FORM_SET;

	
	


/*Sorting the data with no duplicates and order by investigation_form_cd*/
--PROC SORT DATA= INIT_FORM_SET NODUPKEY; BY INVESTIGATION_FORM_CD;RUN;


/*SAS needs to assign keys this way*/
--%ASSIGN_KEY (INIT_FORM_SET, SORT_KEY);


--******************************************************************************************************
--**** VS ************************LOOP OVER tmp_INIT_FORM_SET  ******************************************
--******************************************************************************************************


--DATA INIT_FORM_SET;
--  SET INIT_FORM_SET;
--	BY SORT_KEY;
--	LENGTH RDB_COLUMN_NAME $30;
--	LENGTH INVESTIGATION_FORM_CODE $30; 
--	IF FIRST.SORT_KEY THEN/*this is the way SAS iterates through each of the records*/


		--DATAMART_NAME = COMPRESS( "'"||DATAMART_NM||"'") ;

		--INVESTIGATION_FORM_CODE = TRIM("'")||TRIM(FORM_CD)||TRIM("'") ;

 	--	call symputx('DATAMART_NAME', COMPRESS("'"||DATAMART_NM||"'"));/*set the value into the first parameter (variable) and trims*/
 	--	DATAMART_TABLE_NAME = COMPRESS('DM_INV_'|| DATAMART_NM);


declare @DATAMART_NM  varchar(max) ;

	
DECLARE db_cursor_init_formset CURSOR LOCAL FOR 
    select datamart_nm from  rdb.dbo.tmp_INIT_FORM_SET order by datamart_nm;


OPEN db_cursor_init_formset  
FETCH NEXT FROM db_cursor_init_formset INTO @DATAMART_NM

WHILE @@FETCH_STATUS = 0  
BEGIN  

 
 
 Select 'DATAMART PROCESSING ',@DATAMART_NM;


 BEGIN TRANSACTION;
 
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'STARTING DYNAMIC DATAMART ' + @DATAMART_NM;
	
  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Main_sp '  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
 		
	
    COMMIT TRANSACTION;


--declare @DATAMART_NM  varchar(max) = 'GENERIC_V2';

declare @SQL varchar(max)

declare @DATAMART_NAME varchar(1000);
declare @INVESTIGATION_FORM_CODE varchar(1000);
declare @DATAMART_TABLE_NAME varchar(1000);


set @DATAMART_NAME =  ltrim(rtrim(@DATAMART_NM));
	--INVESTIGATION_FORM_CODE = TRIM("'")||TRIM(FORM_CD)||TRIM("'") ;tmp_DynDm_Case_Management_Metadata
set @INVESTIGATION_FORM_CODE =  ltrim(rtrim(@DATAMART_NM));

set @DATAMART_TABLE_NAME =  'DM_INV_'+ltrim(rtrim(@DATAMART_NM));

 	--	DATAMART_TABLE_NAME = COMPRESS('DM_INV_'|| DATAMART_NM);


--select getdate();

	
	

	BEGIN TRANSACTION;


		/*Compiling and organizing and formatting metadata tables for investigation, patients, etc.*/
		
--		exec  rdb.dbo.DynDM_INVEST_FORM_PROC_sp 999,'GENERIC_V2';

SET @SQL = 'exec  rdb.dbo.DynDM_INVEST_FORM_PROC_sp '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' ;';

EXEC(@SQL);


--select getdate();


/*Compiling and organizing and formatting metadata tables for case management*/
		

--		exec  rdb.dbo.DynDM_MANAGE_CASE_MANAGEMENT_sp 999,'GENERIC_V2';

SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_CASE_MANAGEMENT_sp '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' ;';

EXEC(@SQL);

COMMIT TRANSACTION;
	
	

	BEGIN TRANSACTION;


-- select getdate();
		
		/*MANAGE_D_INV ('D_INV_ADMINISTRATIVE','D_INV_ADMINISTRATIVE','D_INV_ADMINISTRATIVE_KEY')       */
		/*MANAGE_D_INV: creates all the columns necessary like OTH, UNIT, plus the regular ones. etc*/
	
	    declare @DIM1  varchar(400);
		declare @DIM1_KEY varchar(400);


	    set @DIM1='D_INV_ADMINISTRATIVE';
		set @DIM1_KEY= @DIM1 +  '_KEY';

		-- call execute('%MANAGE_D_INV ('||DIM1||','|| "'"||DIM1||"'"||','|| DIM1_KEY ||')');

--		exec rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'HEPATITIS_CORE','D_INV_ADMINISTRATIVE','D_INV_ADMINISTRATIVE','D_INV_ADMINISTRATIVE_KEY';

			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
			SELECT @SQL ;		
			EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_CLINICAL';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);
		 

    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_COMPLICATION';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);
		 

    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_CONTACT';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);
		 

    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_DEATH';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

 		SET @DIM1 = 'D_INV_EPIDEMIOLOGY';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_HIV';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_PATIENT_OBS';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_ISOLATE_TRACKING';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_LAB_FINDING';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_MEDICAL_HISTORY';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL =
			'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_MOTHER';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_OTHER';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_PREGNANCY_BIRTH';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_RESIDENCY';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_RISK_FACTOR';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

 		SET @DIM1 = 'D_INV_SOCIAL_HISTORY';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_SYMPTOM';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_TREATMENT';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_TRAVEL';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);
		 

    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_UNDER_CONDITION';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);
		 

    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_VACCINATION';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 SELECT @SQL ; 
		 EXEC(@SQL);
		

    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

		SET @DIM1 = 'D_INV_STD';
		SET @DIM1_KEY =  ( @DIM1 +  '_KEY');
			SET @SQL = 'exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1+ ''' , ''' +@DIM1_KEY+ ''' ; ' 
 		 -- SELECT @SQL ; 
		 EXEC(@SQL);


    COMMIT TRANSACTION;

	BEGIN TRANSACTION;


		 
		
    --   select  getdate(), ' I AM HERE END'

		
		/*
		It creates a table with rdb_column_nm, user_defined_column_nm, part_type_cd, which relates to the elements coming from D_Organization 
		and it creates the key, detail and qec columns for each of them removing the uid from the user_defined_column_nm, and then appending
		_key, _detail, _qec.
		*/
		--call execute('%ORGDATA ()');
	
	-- DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'GENERIC_V2', @SQL varchar(max);

		SET @SQL = 'exec  rdb.dbo.DynDM_ORGDATA_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' ;';

		--select 'ORG data',@SQL
		
		EXEC(@SQL);

		

    COMMIT TRANSACTION;

	BEGIN TRANSACTION;


	--	select getdate();

	



		/*Same than previous one but the data comes from rdb_table_nm = D_PROVIDER*/
	--	call execute('%PROVDATA ()');
		
		
	-- --DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'GENERIC_V2', @SQL varchar(max);

		SET @SQL = 'exec  rdb.dbo.DynDM_PROVDATA_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' ;';

		select 'ORG data',@SQL
		
		EXEC(@SQL);

		

    COMMIT TRANSACTION;

	BEGIN TRANSACTION;
	 
  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA;
	
	
    COMMIT TRANSACTION;

	BEGIN TRANSACTION;

	     IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL;
 
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE;

	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK;

	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_OUT_final', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_OUT_final;

				  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA;
	
	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_UNIT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_UNIT;
	

					
	
    COMMIT TRANSACTION;

		IF @@TRANCOUNT > 0
						BEGIN
							COMMIT  TRANSACTION;
						END;

	BEGIN TRANSACTION;

		/*This macro handles repeating blocks of varchar questions*/
	--	call execute('%REPEATVARCHARDATA()');
		
		-- DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'GENERIC_V2', @SQL varchar(max);

		SET @SQL = 'exec  rdb.dbo.DynDM_REPEATVARCHARDATA_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' ;';

		select 'DyDm_REPEATVARCHARDATA_sp ',@SQL
		
		EXEC(@SQL);



		select getdate();

		

    COMMIT TRANSACTION;
			IF @@TRANCOUNT > 0
						BEGIN
							COMMIT  TRANSACTION;
						END;


		BEGIN TRANSACTION;


 IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_METADATA_OUT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_METADATA_OUT;
	

 IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT;
	
  IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_ALL;
							   				 			  
  IF OBJECT_ID('rdb.dbo.tmp_DynDm_BLOCK_DATA', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_BLOCK_DATA;
				

  IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK;
				

  IF OBJECT_ID('rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_DATE', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_DATE;


  IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE;


IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL;


	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_UNIT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_UNIT;

	
	  	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_Metadata', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_Metadata;
				
	 

    COMMIT TRANSACTION;

			IF @@TRANCOUNT > 0
						BEGIN
							COMMIT  TRANSACTION;
						END;

		BEGIN TRANSACTION;


		/*This macro handles repeating blocks of varchar questions*/
	--	call execute('%REPEATVARCHARDATA()');
		
	-- --DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'GENERIC_V2', @SQL varchar(max);

		SET @SQL = 'exec  rdb.dbo.DynDM_REPEATDATEDATA_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' ;';

		select 'DyDm_REPEATDATE_sp ',@SQL
		
		EXEC(@SQL);



		select getdate();


		--select '**********', @DATAMART_NAME,  * from  rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_DATE;
		--select 'XXXXXXXXXX', @DATAMART_NAME,* from rdb.dbo.tmp_DynDm_REPEAT_BLOCK_DATE_ALL;


		     COMMIT TRANSACTION;


		IF @@TRANCOUNT > 0
						BEGIN
							COMMIT  TRANSACTION;
						END;


	BEGIN TRANSACTION;

		    IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL;
 
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE;

	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK;

	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_OUT_final', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_OUT_final;
	

	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_UNIT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_UNIT;

	
	  	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_Metadata', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_Metadata;
				
	 
    COMMIT TRANSACTION;



	BEGIN TRANSACTION;



		/*This macro handles repeating blocks of varchar questions*/
	--	call execute('%REPEATVARCHARDATA()');
		
	-- --DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'GENERIC_V2', @SQL varchar(max);

		SET @SQL = 'exec  rdb.dbo.DynDM_REPEATNUMERICDATA_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' ;';

		select 'DyDM_DyDm_REPEATNUMERIC_sp ',@SQL
		
		EXEC(@SQL);



		select getdate();

		

    COMMIT TRANSACTION;

		IF @@TRANCOUNT > 0
						BEGIN
							COMMIT  TRANSACTION;
						END;


	BEGIN TRANSACTION;

		/*
		/*This macro handles repeating blocks of date questions*/
		call execute('%REPEATDATEDATA()');
		
		/*This macro handles repeating blocks of numeric questions*/
		
		call execute('%REPEATNUMERICDATA()');
		*/


		/*it creates the actual datamart_table_name. First it removed the table if already exists, and then it is created.*/
		--call execute('%CREATEDM('||DATAMART_TABLE_NAME||')');

		
		-- DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'GENERIC_V2', @SQL varchar(max);


		SET @SQL = 'exec  rdb.dbo.DynDM_AlterKey_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_NAME+ ''' ;';

		--select 'ALT KEY data',@SQL
		
		EXEC(@SQL);

			-- declare @DATAMART_TABLE_NAME  varchar(max) = 'DM_INV_HIV', @SQL varchar(max);

		

    COMMIT TRANSACTION;

	BEGIN TRANSACTION;
		
		-- DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_TABLE_NAME  VARCHAR(100) = 'GENERIC_V2', @SQL varchar(max);

SET @SQL = 'exec	 rdb.dbo.DynDM_CreateDM_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_TABLE_NAME+ ''' ;';

		select 'CREATE DTM data',@SQL
		
		EXEC(@SQL);



    COMMIT TRANSACTION;
	

    
	BEGIN TRANSACTION;



		--exec sp_rename 'rdb.dbo.tmp_DynDm_Investigation_Data.INVESTIGATION_KEY_INVESTIGATION_DATA', 'INVESTIGATION_KEY','COLUMN';


				
		SET @SQL = 'exec  rdb.dbo.DynDM_INVEST_FORM_CLEAR_PROC_sp  '+cast(@batch_id as varchar) +' ,'''+@DATAMART_TABLE_NAME+ ''' ;';

	--	select 'CLEAR PROC data',@SQL
		
		EXEC(@SQL);


		

    COMMIT TRANSACTION;


	

 BEGIN TRANSACTION;
 
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'COMPLETED DYNAMIC DATAMART ' + @DATAMART_NM;
	
  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Main_sp '  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
 		
	
    COMMIT TRANSACTION;


FETCH NEXT FROM db_cursor_init_formset INTO @DATAMART_NM

END 

CLOSE db_cursor_init_formset;

DEALLOCATE db_cursor_init_formset;


select ' I AM HERE END'

        --exec sp_rename 'rdb.dbo.tmp_DynDm_Patient_Data.INVESTIGATION_KEY_PATIENT_DATA', 'INVESTIGATION_KEY','COLUMN';
        --exec sp_rename 'rdb.dbo.tmp_DynDm_Patient_Data.PATIENT_LOCAL_ID_PATIENT_DATA', 'PATIENT_LOCAL_ID','COLUMN'

        --exec sp_rename 'rdb.dbo.tmp_DynDm_Case_Management_Data.INVESTIGATION_KEY_CASE_MANAGEMENT_DATA', 'INVESTIGATION_KEY','COLUMN';


		  

/*The following code was created */

/*A table with the investigations with data mart name associated to it but not condition*/



 BEGIN TRANSACTION;
 
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING INIT_BLANK ' ;
	


	
IF OBJECT_ID('rdb.dbo.INIT_BLANK', 'U') IS NOT NULL 
					drop table rdb.dbo.INIT_BLANK;

--CREATE TABLE INIT_BLANK AS
	SELECT  NBS_PAGE.FORM_CD, NBS_PAGE.DATAMART_NM 
    into rdb.dbo.INIT_BLANK
    FROM NBS_ODSE..NBS_PAGE 
	   LEFT JOIN NBS_ODSE..PAGE_COND_MAPPING 	ON PAGE_COND_MAPPING.WA_TEMPLATE_UID = NBS_PAGE.WA_TEMPLATE_UID
     WHERE DATAMART_NM IS NOT NULL AND CONDITION_CD IS NULL;


/*table for those investigations without condition associated to it, where the rdb_table_nm is investigation */
  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Main_sp '  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
 		
	
    COMMIT TRANSACTION;



	


 BEGIN TRANSACTION;
 
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING INIT_FORM_BLANK_SET ' ;
	



IF OBJECT_ID('rdb.dbo.INIT_FORM_BLANK_SET', 'U') IS NOT NULL 
					drop table rdb.dbo.INIT_FORM_BLANK_SET;


--CREATE TABLE INIT_FORM_BLANK_SET AS
SELECT INIT_BLANK.FORM_CD, INIT_BLANK.DATAMART_NM 
into rdb.dbo.INIT_FORM_BLANK_SET
FROM rdb.dbo.INIT_BLANK 
INNER JOIN NBS_ODSE..NBS_UI_METADATA ON NBS_UI_METADATA.INVESTIGATION_FORM_CD = INIT_BLANK.FORM_CD
INNER JOIN NBS_ODSE..NBS_RDB_METADATA ON NBS_UI_METADATA.NBS_UI_METADATA_UID = NBS_RDB_METADATA.NBS_UI_METADATA_UID
WHERE RDB_TABLE_NM='INVESTIGATION' ORDER BY INIT_BLANK.FORM_CD,  NBS_RDB_METADATA.RDB_COLUMN_NM
;


    COMMIT TRANSACTION;




/*
PROC SORT DATA= INIT_FORM_BLANK_SET NODUPKEY; BY FORM_CD;RUN;

%ASSIGN_KEY (INIT_FORM_BLANK_SET, SORT_KEY);

/*dropping the table*/

DATA INIT_FORM_BLANK_SET;
  SET INIT_FORM_BLANK_SET;
	BY SORT_KEY;
	LENGTH RDB_COLUMN_NAME $30; 
	LENGTH INVESTIGATION_FORM_CODE $30; 
	IF FIRST.SORT_KEY THEN
		DATAMART_NAME = 'DM_INV_'|| DATAMART_NM;
		call execute('%INVEST_FORM_CLEAR_PROC('|| DATAMART_NAME||')');


	OUTPUT;
	
	
RUN; 


 */


BEGIN TRANSACTION;

		EXEC  RDB.dbo.DynDM_CLEAR_sp @batch_id;

COMMIT TRANSACTION;



select ' I AM HERE END 2'




 	 BEGIN TRANSACTION;

					SET @Proc_Step_no = @Proc_Step_no + 1;
					SET @Proc_Step_Name = 'SP_COMPLETE'; 


					INSERT INTO rdb.dbo.[job_flow_log] (
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
						   ,'RDB.DBO.DynDm_Main_sp'
						   ,'COMPLETE'
						   ,@Proc_Step_no
						   ,@Proc_Step_name
						   ,0
						   );
  
	
	 COMMIT TRANSACTION ;


	 EXEC [rdb].[dbo].[sp_nbs_batch_complete] @type_code;
	 PRINT 'BATCH complete called'

    END TRY
            BEGIN CATCH
						IF @@TRANCOUNT > 0
						BEGIN
							ROLLBACK TRANSACTION;
							-- COMMIT TRANSACTION
						END;

						DECLARE @ErrorNumber int= ERROR_NUMBER();
						DECLARE @ErrorLine int= ERROR_LINE();
						DECLARE @ErrorMessage nvarchar(4000)= ERROR_MESSAGE();
						DECLARE @ErrorSeverity int= ERROR_SEVERITY();
						DECLARE @ErrorState int= ERROR_STATE();

                        select @ErrorMessage;

						INSERT INTO rdb.dbo.[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [Error_Description], [row_count] )
						VALUES( @Batch_id, 'DYNAMIC_DATAMART', 'RDB.DBO.DynDM_Main : ' + @DATAMART_TABLE_NAME, 'ERROR', @Proc_Step_no, 'ERROR - '+@Proc_Step_name, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );
						RETURN -1;
	        END CATCH;
END;

GO


