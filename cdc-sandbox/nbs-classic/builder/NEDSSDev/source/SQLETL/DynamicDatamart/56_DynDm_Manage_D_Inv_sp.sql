 
USE rdb;
GO

--		exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  999 ,'AL_LTBI' , 'D_INV_ADMINISTRATIVE' , 'D_INV_ADMINISTRATIVE' , 'D_INV_ADMINISTRATIVE_KEY' ; 

--   exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  231117175143 ,'COVID' , 'D_INV_AL_LTBI' , 'D_INV_AL_LTBI' , 'D_INV_AL_LTBI_KEY' ; 
--  exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  231206183732 ,'CYCLOSPORIASIS' , 'D_INV_SYMPTON' , 'D_INV_SYMPTON' , 'D_INV_SYMPTON_KEY' ; 

--  exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  231207132840 ,'CYCLOSPORIASIS' , 'D_INV_SYMPTOM' , 'D_INV_SYMPTOM' , 'D_INV_SYMPTOM_KEY' ; 
-- exec  rdb.dbo.DynDM_MANAGE_D_INV_sp  999 ,'STD' , 'D_INV_UNDER_CONDITION' , 'D_INV_UNDER_CONDITION' , 'D_INV_UNDER_CONDITION_KEY' ; 

--SYM_NEUROLOGIC_SIGN_SYM_OTH NEUROLOGIC_SIGN_SYM_OTH,SYM_SignsSymptoms_OTH SignsSymptoms_OTH,SYM_LATE_CLINICAL_MANIFES AS LATE_CLINICAL_MANIFES,SYM_NEUROLOGIC_SIGN_SYM AS NEUROLOGIC_SIGN_SYM,SYM_OCULAR_MANIFESTATIONS AS OCULAR_MANIFESTATIONS,SYM_OTIC_MANIFESTATION AS OTIC_MANIFESTATIONS,SYM_SignsSymptoms AS SignsSymptoms ,

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


			 IF OBJECT_ID('rdb.dbo.DynDm_Manage_D_Inv_sp', 'P') IS NOT NULL
				BEGIN
				DROP PROCEDURE dbo.DynDm_Manage_D_Inv_sp;
				END;

GO



CREATE PROCEDURE [dbo].DynDm_Manage_D_Inv_sp
 
            @batch_id BIGINT,
			@DATAMART_NAME VARCHAR(100),
			@RDB_TABLE_NM  VARCHAR(100),
			@TABLE_NM  VARCHAR(100),
			@DIM_KEY VARCHAR(100)
	AS
BEGIN  
	 BEGIN TRY
	

	--    	DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) =  'STD' , @RDB_TABLE_NM  VARCHAR(100) = 'D_INV_ADMINISTRATIVE',  @TABLE_NM  VARCHAR(100) = 'D_INV_ADMINISTRATIVE', @DIM_KEY  VARCHAR(100) = 'D_INV_ADMINISTRATIVE_KEY'; 
		
		
		DECLARE @RowCount_no INT = 0 ;
		DECLARE @Proc_Step_no FLOAT = 0 ;
		DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
		DECLARE @batch_start_time datetime = null ;
		DECLARE @batch_end_time datetime = null ;




    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = 1;
	SET @Proc_Step_Name = 'SP_Start';
	
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_D_INV_METADATA';
	
		
		

	--    	DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) =  'STD' , @RDB_TABLE_NM  VARCHAR(100) = 'D_INV_UNDER_CONDITION',  @TABLE_NM  VARCHAR(100) = 'D_INV_UNDER_CONDITION', @DIM_KEY  VARCHAR(100) = 'D_INV_UNDER_CONDITION_KEY'; 
	



/*
PROC SQL;
	CREATE TABLE STD_TESTER(COUNTSTD NUM);
	INSERT INTO STD_TESTER( COUNTSTD) VALUES(NULL);
	UPDATE STD_TESTER SET COUNTSTD= (select  count(*) from CASE_MANAGEMENT_METADATA);
QUIT;
*/
        declare @countstd int = 0;


	   select  @COUNTSTD = count(*) 
	    from rdb.dbo.tmp_DynDm_Case_Management_Metadata with (nolock)
        ;

		
		/*
DATA _NULL_;
SET STD_TESTER;
*/
/*This is to decide what Fact table we are using based with (nolock) with (nolock) ON tmp_DynDm_the countstd*/
 /* IF COUNTSTD>1 then call symputx('FACT_CASE', 'F_STD_PAGE_CASE'); 
  IF COUNTSTD<2 then call symputx('FACT_CASE', 'F_PAGE_CASE'); 
  RUN;
  */

  declare @FACT_CASE varchar(40) = '';


  if @countstd > 1
     begin
      set @FACT_CASE = 'F_STD_PAGE_CASE';
	end
  else 
     begin
      set @FACT_CASE = 'F_PAGE_CASE';
     end
  ;

  --select @fact_case;



/*It creates a table with the metadata for that specific datamartnm and rdb_table_nm which is the one received as a parameter*/

--	CREATE TABLE D_INV_METADATA AS


     IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_METADATA', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_D_INV_METADATA;


	SELECT  DISTINCT tmp_INIT.FORM_CD, tmp_INIT.DATAMART_NM, NBS_RDB_METADATA.RDB_TABLE_NM, NBS_RDB_METADATA.RDB_COLUMN_NM,NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM  
	  into rdb.dbo.tmp_DynDm_D_INV_METADATA
	  FROM rdb.dbo.tmp_INIT  with (nolock) 
	     INNER JOIN NBS_ODSE..NBS_UI_METADATA  with (nolock) ON  NBS_UI_METADATA.INVESTIGATION_FORM_CD = tmp_INIT.FORM_CD
	     INNER JOIN NBS_ODSE..NBS_RDB_METADATA  with (nolock) ON NBS_UI_METADATA.NBS_UI_METADATA_UID = NBS_RDB_METADATA.NBS_UI_METADATA_UID
	WHERE RDB_TABLE_NM=@RDB_TABLE_NM 
	  AND NBS_UI_METADATA.INVESTIGATION_FORM_CD=(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE with (nolock) WHERE DATAMART_NM= @DATAMART_NAME )
	  AND QUESTION_GROUP_SEQ_NBR IS NULL
	  AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM <> '' and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
   ;

	update rdb.dbo.tmp_DynDm_D_INV_METADATA
	  set FORM_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE  with (nolock)  WHERE DATAMART_NM= @DATAMART_NAME ),
	  RDB_TABLE_NM=@RDB_TABLE_NM
	;

	
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;



 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  


	 COMMIT TRANSACTION;
	
    BEGIN TRANSACTION;
	
			SET @Proc_Step_no = @Proc_Step_no + 1;
			SET @Proc_Step_Name = 'REMOVING MISSING COLUMNS  tmp_DynDm_D_INV_METADATA';
	
			--delete from  rdb.dbo.tmp_DynDm_D_INV_METADATA
			--WHERE RDB_COLUMN_NM not in ( SELECT      COLUMN_NAME FROM  INFORMATION_SCHEMA.COLUMNS
			--where    TABLE_NAME = 'D_INV_LAB_FINDING')
			--;


	declare @SQL varchar(max);

            
			 SET @SQL = ' delete from  rdb.dbo.tmp_DynDm_D_INV_METADATA' +
						'   WHERE RDB_COLUMN_NM not in ( SELECT      COLUMN_NAME FROM  INFORMATION_SCHEMA.COLUMNS ' +
						'    where    TABLE_NAME = '''+@RDB_TABLE_NM +''' ); '
						;

		--DX	  select 2,@SQL;

			 EXEC (@SQL);
  


			SELECT @ROWCOUNT_NO = @@ROWCOUNT;


		 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
		 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  


	 COMMIT TRANSACTION;



	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'UPDATING tmp_DynDm_D_INV_METADATA';
	
  
		
		
	/*

-- %ASSIGN_KEY (D_INV_METADATA, SORT_KEY);

DATA DYNINVLISTING;
  SET D_INV_METADATA;
	BY SORT_KEY;



	
	LENGTH RDB_COLUMN_NAME_LIST @MAX_COLUMN_CHARACTER_LIMIT; 
	RETAIN RDB_COLUMN_NAME_LIST;
	
	IF FIRST.SORT_KEY AND NOT MISSING(DATAMART_NM) THEN 
	
	/*Replace the rdb_column_nm value wherever it finds the user_defined_column_nm in the user_defined_column_name column
	USER_DEFINED_COLUMN_NAME = ADM_PUBLISHED_INDICATOR AS PUBLISHED_INDICATOR 'USER_DEFINED_COLUMN_NM'
	At the end it is creating a list of all the rdb_column_nm*/
	
	
	USER_DEFINED_COLUMN_NAME = COMPRESS(RDB_COLUMN_NM) || ' AS ' ||COMPRESS(USER_DEFINED_COLUMN_NM)|| " '"||COMPRESS(USER_DEFINED_COLUMN_NM)||"'";
	
	IF (LENGTHN(USER_DEFINED_COLUMN_NAME))>0 THEN  RDB_COLUMN_NAME_LIST = COMPBL((USER_DEFINED_COLUMN_NAME || ', ' || RDB_COLUMN_NAME_LIST));  
	OUTPUT;
	*/


	update rdb.dbo.tmp_DynDm_D_INV_METADATA
	 set USER_DEFINED_COLUMN_NM = rtrim(RDB_COLUMN_NM)
	where USER_DEFINED_COLUMN_NM is null
	;

	--DX select * from rdb.dbo.tmp_DynDm_D_INV_METADATA  with (nolock)
	
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;


 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  

	 COMMIT TRANSACTION;
	

	BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_D_INV_METADATA_1';
  
	
	
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_METADATA_1', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_D_INV_METADATA_1;



	SELECT a.* 
	INTO  rdb.dbo.tmp_DynDm_D_INV_METADATA_1 
	FROM  rdb.dbo.tmp_DynDm_D_INV_METADATA a
	INNER JOIN 
          (SELECT    ROW_NUMBER() over(PARTITION BY USER_DEFINED_COLUMN_NM ORDER BY  USER_DEFINED_COLUMN_NM) AS SEQ,  rdb.dbo.tmp_DynDm_D_INV_METADATA.*
            FROM             rdb.dbo.tmp_DynDm_D_INV_METADATA) b
        ON a.USER_DEFINED_COLUMN_NM = b.USER_DEFINED_COLUMN_NM and a.rdb_column_nm = b.rdb_column_nm
    WHERE b.SEQ = 1
	;

	
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;


 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  

	 COMMIT TRANSACTION;
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_D_INV_METADATA_distinct';
  
		
		

     IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_METADATA_distinct', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_D_INV_METADATA_distinct;

	select distinct (RDB_COLUMN_NM  + ' AS ' + USER_DEFINED_COLUMN_NM) as USER_DEFINED_COLUMN_NM
	into rdb.dbo.tmp_DynDm_D_INV_METADATA_distinct
	from rdb.dbo.tmp_DynDm_D_INV_METADATA_1  with (nolock)
	;

	-- select * from rdb.dbo.tmp_DynDm_D_INV_METADATA_distinct DD_R_ADM_NUMERIC_UNIT  order by 1
	
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;


 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  


	 COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_D_INV_METADATA_OTH';
		
		

	DECLARE @D_INV_CASE_LIST VARCHAR(MAX);


	
	SET  @D_INV_CASE_LIST =null;

	SELECT @D_INV_CASE_LIST = COALESCE(@D_INV_CASE_LIST+',' ,'') + USER_DEFINED_COLUMN_NM
	 FROM  rdb.dbo.tmp_DynDm_D_INV_METADATA_distinct with (nolock);



--	SELECT '@D_INV_CASE_LIST',@D_INV_CASE_LIST;


	--SELECT *
	--FROM rdb.dbo.tmp_DynDm_D_INV_METADATA
	----where  @DATAMART_NAME 
	--;



	/*
PROC SQL; 
DELETE FROM DYNINVLISTING WHERE SORT_KEY <(SELECT MAX (SORT_KEY) FROM DYNINVLISTING);
QUIT;

DATA DYNINVLISTING;
SET DYNINVLISTING;
LENGTH TRIMMED_VALUE $30000; 

	CALL SYMPUTX('D_INV_CASE', '');

	LENGTH=LENGTHN(COMPRESS(RDB_COLUMN_NAME_LIST));

	RDB_COLUMN_NAME_LIST = TRIM(RDB_COLUMN_NAME_LIST);

	/*If the length is more than 1, then it gets rid off the last character, and it is less than 2 it gets the investigatiON tmp_DynDm_key from the fact table*/
	IF (LENGTH>1)  THEN TRIMMED_VALUE=SUBSTR((RDB_COLUMN_NAME_LIST), 1, LENGTHN(RDB_COLUMN_NAME_LIST)-1);

	IF (LENGTH<2) THEN TRIMMED_VALUE= TRIM("'"||@FACT_CASE ||"."||INVESTIGATION_KEY||"'");

	CALL SYMPUTX('D_INV_CASE', COMPBL(TRIMMED_VALUE));/*If there are more than 2 spaces, it replaces to 1 space*/

	/*COMPBL: removes multiple blanks*/
RUN;

*/




/*This is the same table than the previous one but for questions with other indicator as true*/

--	CREATE TABLE D_INV_METADATA_OTH AS



     IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_METADATA_OTH', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_D_INV_METADATA_OTH;


	SELECT  DISTINCT OTHER_VALUE_IND_CD, INIT.DATAMART_NM,
	         NBS_RDB_METADATA.RDB_TABLE_NM, 
	         substring(LTRIM(RTRIM(NBS_RDB_METADATA.RDB_COLUMN_NM)),1,26)+'_OTH' as RDB_COLUMN_NM,
			 NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM +'_OTH' as  USER_DEFINED_COLUMN_NM
	into rdb.dbo.tmp_DynDm_D_INV_METADATA_OTH
	FROM NBS_ODSE..NBS_UI_METADATA with (nolock)
	INNER JOIN NBS_ODSE..NBS_RDB_METADATA with (nolock) ON NBS_UI_METADATA.NBS_UI_METADATA_UID = NBS_RDB_METADATA.NBS_UI_METADATA_UID
	INNER JOIN rdb.dbo.TMP_INIT INIT with (nolock) ON NBS_UI_METADATA.INVESTIGATION_FORM_CD = INIT.FORM_CD
	WHERE RDB_TABLE_NM=@RDB_TABLE_NM  
	AND NBS_UI_METADATA.INVESTIGATION_FORM_CD=(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE with (nolock) WHERE DATAMART_NM= @DATAMART_NAME )
	AND QUESTION_GROUP_SEQ_NBR IS NULL
	AND OTHER_VALUE_IND_CD='T'
	AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM <> '' and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
	--ORDER BY NBS_RDB_METADATA.RDB_COLUMN_NM
	;
	/*

/*It replaces the name of the rdb_column_nm and the user_defined_column_nm by the same one with _OTH appended at the end*/
DATA D_INV_METADATA_OTH;
LENGTH RDB_COLUMN_NM2 $26;

  DO UNTIL(LAST.SORT_KEY);
    SET D_INV_METADATA_OTH;
    BY SORT_KEY;
   /* OUTPUT;*/
  end;
  DO UNTIL(LAST.SORT_KEY);
    SET D_INV_METADATA_OTH;
    BY SORT_KEY;
	RDB_COLUMN_NM2 =RDB_COLUMN_NM;
	RDB_COLUMN_NM =TRIM(RDB_COLUMN_NM2) || '_OTH';
	USER_DEFINED_COLUMN_NM= TRIM(USER_DEFINED_COLUMN_NM) || '_OTH';
    OUTPUT;
  END;
  DROP RDB_COLUMN_NM2;
RUN;
*/



/*It creates the list of OTH columns*/


/*
DATA DYNINVLISTING_OTH;
SET D_INV_METADATA_OTH;
	BY SORT_KEY;

	LENGTH RDB_COLUMN_NAME_LIST @MAX_COLUMN_CHARACTER_LIMIT; 
	RETAIN RDB_COLUMN_NAME_LIST;
	IF FIRST.SORT_KEY AND NOT MISSING(DATAMART_NM) THEN 
		USER_DEFINED_COLUMN_NAME = COMPRESS(RDB_COLUMN_NM) || ' AS ' ||COMPRESS(USER_DEFINED_COLUMN_NM)|| " '"||COMPRESS(USER_DEFINED_COLUMN_NM)||"'";
	IF (LENGTHN(USER_DEFINED_COLUMN_NAME))>0 THEN  RDB_COLUMN_NAME_LIST = COMPBL((USER_DEFINED_COLUMN_NAME || ', ' || RDB_COLUMN_NAME_LIST));  
	OUTPUT;
RUN;

PROC SQL; 
DELETE FROM DYNINVLISTING_OTH WHERE SORT_KEY <(SELECT MAX (SORT_KEY) FROM DYNINVLISTING_OTH);
QUIT;

DATA _NULL_;
	CALL SYMPUTX('D_INV_CASE_OTH', '');
RUN;

*/


/*

DATA DYNINVLISTING_OTH;
SET DYNINVLISTING_OTH;
LENGTH TRIMMED_VALUE $30000; 
	CALL SYMPUTX('D_INV_CASE_OTH', '');
	LENGTH=LENGTHN(COMPRESS(RDB_COLUMN_NAME_LIST));
	RDB_COLUMN_NAME_LIST = TRIM(RDB_COLUMN_NAME_LIST);
	IF (LENGTH>1)  THEN TRIMMED_VALUE=SUBSTR((RDB_COLUMN_NAME_LIST), 1, LENGTHN(RDB_COLUMN_NAME_LIST)-1);
	IF (LENGTH<2) THEN TRIMMED_VALUE= TRIM("'"||@FACT_CASE ||"."||INVESTIGATION_KEY||"'");
	IF (LENGTHN(TRIMMED_VALUE) >3) THEN TRIMMED_VALUE = TRIM(TRIMMED_VALUE) || ",";
	CALL SYMPUTX('D_INV_CASE_OTH', COMPBL(TRIMMED_VALUE));
RUN;
*/


			SELECT @ROWCOUNT_NO = @@ROWCOUNT;


 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  

 COMMIT TRANSACTION;
	
	

	BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_D_INV_METADATA_OTH_1';
  
	
	
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_METADATA_OTH_1', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_D_INV_METADATA_OTH_1;



	SELECT a.* 
	INTO  rdb.dbo.tmp_DynDm_D_INV_METADATA_OTH_1 
	FROM  rdb.dbo.tmp_DynDm_D_INV_METADATA_OTH a
	INNER JOIN 
          (SELECT    ROW_NUMBER() over(PARTITION BY USER_DEFINED_COLUMN_NM ORDER BY  USER_DEFINED_COLUMN_NM) AS SEQ,  rdb.dbo.tmp_DynDm_D_INV_METADATA_OTH.*
            FROM             rdb.dbo.tmp_DynDm_D_INV_METADATA_OTH) b
        ON a.USER_DEFINED_COLUMN_NM = b.USER_DEFINED_COLUMN_NM and a.rdb_column_nm = b.rdb_column_nm
    WHERE b.SEQ = 1
	;

	
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;


 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  

	 COMMIT TRANSACTION;	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  COLUMN LIST';
	
		
		
DECLARE @D_INV_CASE_OTH_list VARCHAR(MAX) = null;
DECLARE @D_INV_CASE_OTH_list_flag  int = 0;


SELECT @D_INV_CASE_OTH_list = coalesce((COALESCE(@D_INV_CASE_OTH_list+',' ,'') + RDB_COLUMN_NM + ' AS ' +  coalesce(USER_DEFINED_COLUMN_NM,'')),'')
FROM  rdb.dbo.tmp_DynDm_D_INV_METADATA_OTH_1 with (nolock);




if (len(@D_INV_CASE_OTH_list) <  1 or @D_INV_CASE_OTH_list is null )
	 begin 
           --  SET @D_INV_CASE_OTH_list = @FACT_CASE+'.INVESTIGATION_KEY';

		  --    SET @D_INV_CASE_OTH_list = ''''' as t1 ,';

			  SET @D_INV_CASE_OTH_list_flag = 1;
	end


--DX  SELECT '@D_INV_CASE_OTH_list',len(@D_INV_CASE_OTH_list) , @D_INV_CASE_OTH_list

 
/*Numeric unit processing*/
/*The same than before for the UNIT columns*/
--	CREATE TABLE D_INV_METADATA_UNIT AS


			SELECT @ROWCOUNT_NO = @@ROWCOUNT;


 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  

 COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_D_INV_METADATA_UNIT';
	
  
		
		

     IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT;



	SELECT  DISTINCT OTHER_VALUE_IND_CD, INIT.DATAMART_NM,
	        NBS_RDB_METADATA.RDB_TABLE_NM,
			NBS_RDB_METADATA.RDB_COLUMN_NM +  '_UNIT' as RDB_COLUMN_NM ,
			NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM +  '_UNIT' as  USER_DEFINED_COLUMN_NM 
	into rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT
	FROM NBS_ODSE..NBS_UI_METADATA with (nolock)
    	INNER JOIN NBS_ODSE..NBS_RDB_METADATA  with (nolock) ON NBS_UI_METADATA.NBS_UI_METADATA_UID = NBS_RDB_METADATA.NBS_UI_METADATA_UID
	    INNER JOIN rdb.dbo.TMP_INIT INIT  with (nolock) ON NBS_UI_METADATA.INVESTIGATION_FORM_CD = INIT.FORM_CD
	WHERE RDB_TABLE_NM=@RDB_TABLE_NM  
	AND NBS_UI_METADATA.INVESTIGATION_FORM_CD=(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE with (nolock) WHERE DATAMART_NM= @DATAMART_NAME )
	AND QUESTION_GROUP_SEQ_NBR IS NULL
	AND DATA_TYPE IN ('Numeric','NUMERIC') AND CODE_SET_GROUP_ID IS NULL AND MASK IS NOT NULL and UPPER(UNIT_TYPE_CD)='CODED'
	AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM <> '' and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
		and  NOT EXISTS (    SELECT 1
            FROM  rdb.dbo.tmp_DynDm_D_INV_METADATA AS t2
               WHERE t2.USER_DEFINED_COLUMN_NM = NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM +  '_UNIT'
         )
	;

	
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;


 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  

	 COMMIT TRANSACTION;
	
	BEGIN TRANSACTION;
	
			SET @Proc_Step_no = @Proc_Step_no + 1;
			SET @Proc_Step_Name = 'REMOVING MISSING COLUMNS tmp_DynDm_D_INV_METADATA_UNIT';
	
			--delete from  rdb.dbo.tmp_DynDm_D_INV_METADATA
			--WHERE RDB_COLUMN_NM not in ( SELECT      COLUMN_NAME FROM  INFORMATION_SCHEMA.COLUMNS
			--where    TABLE_NAME = 'D_INV_LAB_FINDING')
			--;


	--declare @SQL varchar(max);

            
			 SET @SQL = ' delete from  rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT' +
						'   WHERE RDB_COLUMN_NM not in ( SELECT      COLUMN_NAME FROM  INFORMATION_SCHEMA.COLUMNS ' +
						'    where    TABLE_NAME = '''+@RDB_TABLE_NM +''' ); '
						;

	--DX		  select 3,@SQL;

			 EXEC (@SQL);
  


			SELECT @ROWCOUNT_NO = @@ROWCOUNT;


		 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
		 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  


	 COMMIT TRANSACTION;

	 

	BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_D_INV_METADATA_UNIT_1';
  
	
	
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT_1', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT_1;



	SELECT a.* 
	INTO  rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT_1 
	FROM  rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT a
	INNER JOIN 
          (SELECT    ROW_NUMBER() over(PARTITION BY USER_DEFINED_COLUMN_NM ORDER BY  USER_DEFINED_COLUMN_NM) AS SEQ,  rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT.*
            FROM             rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT) b
        ON a.USER_DEFINED_COLUMN_NM = b.USER_DEFINED_COLUMN_NM and a.rdb_column_nm = b.rdb_column_nm
    WHERE b.SEQ = 1
	;

	
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;


 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  

	 COMMIT TRANSACTION;	
	


	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;

	SET @Proc_Step_Name = 'GENERATING  RDB.dbo.tmp_DynDM_'+@RDB_TABLE_NM ;

		
		

/*
/*Replace the value of the columns appending the _UNIT at the end like it was done before*/
DATA D_INV_METADATA_UNIT;
LENGTH RDB_COLUMN_NM2 $25;
LENGTH RDB_COLUMN_NM $30;

  DO UNTIL(LAST.SORT_KEY);
    SET D_INV_METADATA_UNIT;
    BY SORT_KEY;
   /* OUTPUT;*/
  end;
  DO UNTIL(LAST.SORT_KEY);
    SET D_INV_METADATA_UNIT;
    BY SORT_KEY;
	RDB_COLUMN_NM2 =RDB_COLUMN_NM;
	RDB_COLUMN_NM =TRIM(RDB_COLUMN_NM2) || '_UNIT';
	USER_DEFINED_COLUMN_NM= TRIM(USER_DEFINED_COLUMN_NM) || '_UNIT';
    OUTPUT;
  END;
  DROP RDB_COLUMN_NM2;
RUN;

DATA D_INV_METADATA_UNIT;
SET D_INV_METADATA_UNIT;
	BY SORT_KEY;

	LENGTH RDB_COLUMN_NAME_LIST @MAX_COLUMN_CHARACTER_LIMIT; 
	RETAIN RDB_COLUMN_NAME_LIST;
	IF FIRST.SORT_KEY AND NOT MISSING(DATAMART_NM) THEN 
		USER_DEFINED_COLUMN_NAME = COMPRESS(RDB_COLUMN_NM) || ' AS ' ||COMPRESS(USER_DEFINED_COLUMN_NM)|| " '"||COMPRESS(USER_DEFINED_COLUMN_NM)||"'";
	IF (LENGTHN(USER_DEFINED_COLUMN_NAME))>0 THEN  RDB_COLUMN_NAME_LIST = COMPBL((USER_DEFINED_COLUMN_NAME || ', ' || RDB_COLUMN_NAME_LIST));  
	OUTPUT;
RUN;

PROC SQL; 
DELETE FROM D_INV_METADATA_UNIT WHERE SORT_KEY <(SELECT MAX (SORT_KEY) FROM D_INV_METADATA_UNIT);
QUIT;
*/

/*
DATA _NULL_;
	CALL SYMPUTX('D_INV_CASE_UNIT', '');
RUN;

DATA D_INV_METADATA_UNIT;
SET D_INV_METADATA_UNIT;
LENGTH TRIMMED_VALUE $30000; 
	CALL SYMPUTX('D_INV_CASE_UNIT', '');
	LENGTH=LENGTHN(COMPRESS(RDB_COLUMN_NAME_LIST));
	RDB_COLUMN_NAME_LIST = TRIM(RDB_COLUMN_NAME_LIST);
	IF (LENGTH>1)  THEN TRIMMED_VALUE=SUBSTR((RDB_COLUMN_NAME_LIST), 1, LENGTHN(RDB_COLUMN_NAME_LIST)-1);
	IF (LENGTH<2) THEN TRIMMED_VALUE= TRIM("'"||@FACT_CASE ||"."||INVESTIGATION_KEY||"'");
	IF (LENGTHN(TRIMMED_VALUE) >3) THEN TRIMMED_VALUE = TRIM(TRIMMED_VALUE) || ",";

	CALL SYMPUTX('D_INV_CASE_UNIT', COMPBL(TRIMMED_VALUE));
RUN;
 
 */


 declare @D_INV_CASE_UNIT_list varchar(Max) = null ;




SELECT @D_INV_CASE_UNIT_list = coalesce((COALESCE(@D_INV_CASE_UNIT_list+',' ,'') + RDB_COLUMN_NM + ' AS  ' +  coalesce(USER_DEFINED_COLUMN_NM,'')),'')
FROM  rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT_1;




if ((len(@D_INV_CASE_UNIT_list) <  1 or @D_INV_CASE_UNIT_list is null )  )
	 begin 
         --    SET @D_INV_CASE_UNIT_list = @FACT_CASE+'.INVESTIGATION_KEY'
		      
			 SET @D_INV_CASE_UNIT_list = null ;
	end


--DX SELECT '@D_INV_CASE_UNIT_list',len(@D_INV_CASE_UNIT_list) , @D_INV_CASE_UNIT_list





/* Numeric Unit Processing*/



/*It creates the rdb_table_nm table in different ways depending of if the rdb_table_nm exists or not*/


/*
%CONDITIONALCHECKER; 
*/

--declare @RDB_TABLE_NM as varchar(400);



declare @listStr as varchar(max) = ''; 


SET @listStr =   coalesce(@D_INV_CASE_UNIT_list + ',','') + coalesce(@D_INV_CASE_OTH_list + ',','') + coalesce(@D_INV_CASE_list+' ,','');

 -- select 'FINAL lisstr',@listStr;



DECLARE @TableName VARCHAR(200) = 'RDB.dbo.tmp_DynDM_'+@RDB_TABLE_NM

IF OBJECT_ID(@TableName) IS NOT NULL
    EXEC ('DROP Table ' + @TableName)
	;


		IF  NOT EXISTS(SELECT * FROM sys.indexes WHERE object_id = object_id('dbo.tmp_DynDm_SUMM_DATAMART') AND NAME ='idx_tmp_summart_dissesgrp')	
		CREATE NONCLUSTERED INDEX  idx_tmp_summart_dissesgrp ON rdb.[dbo].[tmp_DynDm_SUMM_DATAMART] ([DISEASE_GRP_CD]);


		IF  NOT EXISTS(SELECT * FROM sys.indexes WHERE object_id = object_id('dbo.tmp_DynDm_SUMM_DATAMART') AND NAME ='idx_tmp_summart_invkey')	
		CREATE CLUSTERED INDEX idx_tmp_summart_invkey ON rdb.[dbo].[tmp_DynDM_SUMM_DATAMART]( [INVESTIGATION_KEY] ASC);




		IF (EXISTS (select * FROM RDB.INFORMATION_SCHEMA.TABLES
		WHERE TABLE_SCHEMA = 'dbo'
		AND TABLE_NAME like  'D_INV_STD'))
		   BEGIN

					IF  NOT EXISTS(SELECT * FROM SYS.INDEXES WHERE OBJECT_ID = OBJECT_ID('DBO.D_INV_STD') AND NAME ='idx_D_INV_STD_key') 
							 CREATE  NONCLUSTERED INDEX  idx_D_INV_STD_key ON rdb.dbo.D_INV_STD(D_INV_STD_key); 

		   END;

		





		IF (EXISTS (select * FROM RDB.INFORMATION_SCHEMA.TABLES
		WHERE TABLE_SCHEMA = 'dbo'
		AND TABLE_NAME like  'F_PAGE_CASE'))
		   BEGIN

					IF  NOT EXISTS(SELECT * FROM rdb.sys.indexes WHERE object_id = object_id('rdb.dbo.F_PAGE_CASE') AND NAME ='idx_fpgcase_invkey')	
					CREATE NONCLUSTERED INDEX idx_fpgcase_invkey ON rdb.[dbo].[F_PAGE_CASE] ([INVESTIGATION_KEY]); 

		   END;



		IF (EXISTS (select * FROM RDB.INFORMATION_SCHEMA.TABLES
		WHERE TABLE_SCHEMA = 'dbo'
		AND TABLE_NAME like  'F_STD_PAGE_CASE'))
		   BEGIN
			 IF  NOT EXISTS(SELECT * FROM rdb.sys.indexes WHERE object_id = object_id('rdb.dbo.F_STD_PAGE_CASE') AND NAME ='idx_fstdpgcase_invkey')	
				 CREATE NONCLUSTERED INDEX idx_fstdpgcase_invkey ON rdb.[dbo].[F_STD_PAGE_CASE] ([INVESTIGATION_KEY]); 
		   END;


		if  object_id('RDB.dbo.'+@RDB_TABLE_NM) is not null
		  Begin

			 SET @SQL = '   SELECT distinct  '+@listStr + ' tmp_DynDM_SUMM_DATAMART.INVESTIGATION_KEY ' +
						'    into RDB.dbo.tmp_DynDM_'+@RDB_TABLE_NM +
						'    FROM rdb.dbo.tmp_DynDM_SUMM_DATAMART  with (nolock)'+
						'		INNER JOIN  rdb.dbo.'+@FACT_CASE +'   with (nolock)  ON tmp_DynDM_SUMM_DATAMART.INVESTIGATION_KEY  = '+@FACT_CASE+'.INVESTIGATION_KEY '+
						'		INNER JOIN  rdb.dbo.'+@RDB_TABLE_NM+'  with (nolock) ON	'+@FACT_CASE+'.'+@DIM_KEY+'  = '+@RDB_TABLE_NM+'.'+@DIM_KEY +
						'	  WHERE tmp_DynDm_SUMM_DATAMART.DISEASE_GRP_CD = (SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM='''+@DATAMART_NAME+''' )'
						;

			-- select 1,@SQL;

			 --EXEC (@SQL);
		  end
		else
		  Begin

		SET @SQL = '   SELECT  distinct '+@listStr + ' tmp_DynDM_SUMM_DATAMART.INVESTIGATION_KEY ' +
						'    into RDB.dbo.tmp_DynDM_'+@RDB_TABLE_NM +
						'    FROM rdb.dbo.tmp_DynDM_SUMM_DATAMART with (nolock) '
						;

			 -- select 2,@SQL;

			-- EXEC (@SQL);
		  end
		  ;

		 EXEC (@SQL);
  

		/*
		%IF %SYSFUNC(EXIST(NBS_RDB.DBO. &RDB_TABLE_NM)) %THEN %DO;

		CREATE TABLE &RDB_TABLE_NM AS 
			SELECT  &D_INV_CASE_UNIT  &D_INV_CASE_OTH &D_INV_CASE , SUMM_DATAMART.INVESTIGATION_KEY FROM NBS_RDB.DBO.SUMM_DATAMART
			INNER JOIN  NBS_RDB .&FACT_CASE ON
			SUMM_DATAMART.INVESTIGATION_KEY  =&FACT_CASE .INVESTIGATION_KEY
			INNER JOIN  NBS_RDB.DBO. &RDB_TABLE_NM ON
			&FACT_CASE .&DIM_KEY  =&RDB_TABLE_NM .&DIM_KEY
			WHERE SUMM_DATAMART.DISEASE_GRP_CD = (SELECT FORM_CD FROM NBS_RDB.DBO.NBS_PAGE WHERE DATAMART_NM=&DATAMART_NAME);
		   %END;

		%ELSE %DO;     
			CREATE TABLE &RDB_TABLE_NM AS 
			SELECT  &D_INV_CASE_UNIT  &D_INV_CASE_OTH &D_INV_CASE , SUMM_DATAMART.INVESTIGATION_KEY FROM NBS_RDB.DBO.SUMM_DATAMART;
			%END;
	 
		*/


					SELECT @ROWCOUNT_NO = @@ROWCOUNT;

		 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
		 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  

		 COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
			SET @Proc_Step_no = @Proc_Step_no + 1;
			SET @Proc_Step_Name = 'SP_COMPLETE';
	
		 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
		 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+' - '+@RDB_TABLE_NM ,'COMPLETE' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  
		
		

    	COMMIT TRANSACTION;





	/*
	DATA @RDB_TABLE_NM;
	SET @RDB_TABLE_NM; 
	DROP _TEMA001 _TEMA002 _TEMA003 _TEMA004 _TEMA005 _TEMA006 _TEMA007 _TEMA008 ;
	RUN;
	*/
	/*-------D_INV---------*/
	--%mend INVEST_FORM_PROC; 

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
							INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [Error_Description], [row_count] )
							VALUES( @Batch_id, 'DYNAMIC_DATAMART', 'RDB.DBO.sp_DynDm_Manage_D_Inv_sp '+@DATAMART_NAME+'-'+@RDB_TABLE_NM, 'ERROR', @Proc_Step_no, 'ERROR - '+@Proc_Step_name, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );
							RETURN -1;
				END CATCH;
	END;

	GO

