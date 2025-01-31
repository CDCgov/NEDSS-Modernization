USE rdb;
GO
    IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL;
 
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE;

	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK;

	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_OUT_final', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_OUT_final;
				go

      
 
	  	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_Metadata', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_Metadata;
				go

	  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_UNIT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_UNIT;
				go


USE rdb;
GO


--		exec  rdb.dbo.DynDm_RepeatNumericData_sp 999,'HEPATITIS_CORE' ;

--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'HEPATITIS_CORE';


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



IF OBJECT_ID('rdb.dbo.DynDm_RepeatNumericData_sp', 'P') IS NOT NULL
				BEGIN
				DROP PROCEDURE dbo.DynDm_RepeatNumericData_sp;
				END;

GO



CREATE PROCEDURE [dbo].DynDm_RepeatNumericData_sp
 
            @batch_id BIGINT,
			@DATAMART_NAME VARCHAR(100)
	AS
BEGIN  
	 BEGIN TRY
	


--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'HEPATITIS_CORE';

		

		
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
           ,'RDB.DBO.DynDm_RepeatNumericData_sp '+ @DATAMART_NAME
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
    COMMIT TRANSACTION;
	
	


	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_METADATA_INIT';
	

		

-- DECLARE  @batch_id BIGINT = 999;  DECLARE  @DATAMART_NAME VARCHAR(100) = 'STD'; DECLARE @Proc_Step_no FLOAT = 0 ;	DECLARE @Proc_Step_Name VARCHAR(200) = '' ;	DECLARE @RowCount_no INT ;
	

  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_INIT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_INIT;
	

--CREATE TABLE METADATA_INIT AS 
 SELECT  INIT.DATAMART_NM, RDB_COLUMN_NM, USER_DEFINED_COLUMN_NM, NBS_RDB_METADATA.BLOCK_PIVOT_NBR, BLOCK_NM
 into rdb.dbo.tmp_DynDm_METADATA_INIT
 FROM NBS_ODSE..NBS_UI_METADATA 
    INNER JOIN NBS_ODSE..NBS_RDB_METADATA WITH (NOLOCK) ON NBS_UI_METADATA.NBS_UI_METADATA_UID = NBS_RDB_METADATA.NBS_UI_METADATA_UID
    INNER JOIN rdb.dbo.tmp_INIT INIT WITH (NOLOCK) ON NBS_UI_METADATA.INVESTIGATION_FORM_CD = INIT.FORM_CD
WHERE INVESTIGATION_FORM_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE  WITH (NOLOCK) WHERE DATAMART_NM= @DATAMART_NAME)
AND RDB_TABLE_NM ='D_INVESTIGATION_REPEAT' 
AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM != '' and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
and (code_set_group_id < 0 
OR data_type in ( 'Numeric','NUMERIC') )
ORDER BY RDB_COLUMN_NM;


--DX select 'tmp_DynDm_METADATA_INIT',* from rdb.dbo.tmp_DynDm_METADATA_INIT;




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_METADATA_UNIT';
	


  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_UNIT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_UNIT;
	


-- CREATE TABLE METADATA_UNIT AS 
 
 SELECT  distinct INIT.DATAMART_NM,  
        substring(RDB_COLUMN_NM,1,21) + '_UNIT' as RDB_COLUMN_NM, 
        USER_DEFINED_COLUMN_NM + '_UNIT' as USER_DEFINED_COLUMN_NM,
		NBS_RDB_METADATA.BLOCK_PIVOT_NBR,
		BLOCK_NM, MASK
 into rdb.dbo.tmp_DynDm_METADATA_UNIT
 FROM NBS_ODSE..NBS_UI_METADATA   
     INNER JOIN NBS_ODSE..NBS_RDB_METADATA  WITH (NOLOCK) ON NBS_UI_METADATA.NBS_UI_METADATA_UID = NBS_RDB_METADATA.NBS_UI_METADATA_UID
     INNER JOIN rdb.dbo.tmp_init INIT WITH (NOLOCK) ON NBS_UI_METADATA.INVESTIGATION_FORM_CD = INIT.FORM_CD
WHERE INVESTIGATION_FORM_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WITH (NOLOCK) WHERE DATAMART_NM= @DATAMART_NAME)
AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM != '' and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
AND UNIT_TYPE_CD='CODED'
AND RDB_TABLE_NM ='D_INVESTIGATION_REPEAT' 
AND DATA_TYPE IN ('Numeric','NUMERIC') AND CODE_SET_GROUP_ID IS NULL AND MASK IS NOT NULL 

ORDER BY RDB_COLUMN_NM;



--dx select 'rdb.dbo.tmp_DynDm_METADATA_UNIT',* from rdb.dbo.tmp_DynDm_METADATA_UNIT;

/*
DATA METADATA_UNIT;
SET METADATA_UNIT;
LENGTH RDB_COLUMN_NM3 $21;
LENGTH RDB_COLUMN_NM2 $30;
	RDB_COLUMN_NM3=RDB_COLUMN_NM;
	RDB_COLUMN_NM2= LTRIM(  RTRIM(RDB_COLUMN_NM3) || '_UNIT';
	USER_DEFINED_COLUMN_NM = LTRIM(  RTRIM(USER_DEFINED_COLUMN_NM)|| '_UNIT';
	RDB_COLUMN_NM= RDB_COLUMN_NM2;
DROP RDB_COLUMN_NM2 RDB_COLUMN_NM3;
RUN;
*/

				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_METADATA';
	


  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA;
	
/*
PROC SORT DATA=METADATA_UNIT; BY RDB_COLUMN_NM;RUN;

DATA METADATA; 
  MERGE METADATA_UNIT METADATA_INIT; 
  BY RDB_COLUMN_NM; 
RUN;
*/



select [DATAMART_NM]
      ,[BLOCK_PIVOT_NBR]
      ,[BLOCK_NM]
      ,[mask]
      ,[RDB_COLUMN_NM]
      ,[USER_DEFINED_COLUMN_NM]
	  ,cast([USER_DEFINED_COLUMN_NM]+'_1' as varchar(200)) as USER_DEFINED_COLUMN_NM_1  
	  ,cast([USER_DEFINED_COLUMN_NM]+'_2' as varchar(200)) as USER_DEFINED_COLUMN_NM_2  
	  ,cast([USER_DEFINED_COLUMN_NM]+'_3' as varchar(200)) as USER_DEFINED_COLUMN_NM_3  
	  ,cast([USER_DEFINED_COLUMN_NM]+'_4' as varchar(200)) as USER_DEFINED_COLUMN_NM_4  
	  ,cast([USER_DEFINED_COLUMN_NM]+'_5' as varchar(200)) as USER_DEFINED_COLUMN_NM_5  
into rdb.dbo.tmp_DynDm_METADATA
  FROM [RDB].[dbo].[tmp_DynDm_METADATA_UNIT]
union all
select [DATAMART_NM]
      ,[BLOCK_PIVOT_NBR]
      ,[BLOCK_NM]
      ,null
      ,[RDB_COLUMN_NM]
      ,[USER_DEFINED_COLUMN_NM]
	  ,cast([USER_DEFINED_COLUMN_NM]+'_1' as varchar(200)) as USER_DEFINED_COLUMN_NM_1  
	  ,cast([USER_DEFINED_COLUMN_NM]+'_2' as varchar(200)) as USER_DEFINED_COLUMN_NM_2  
	  ,cast([USER_DEFINED_COLUMN_NM]+'_3' as varchar(200)) as USER_DEFINED_COLUMN_NM_3  
	  ,cast([USER_DEFINED_COLUMN_NM]+'_4' as varchar(200)) as USER_DEFINED_COLUMN_NM_4
	  ,cast([USER_DEFINED_COLUMN_NM]+'_5' as varchar(200)) as USER_DEFINED_COLUMN_NM_5
  FROM [RDB].[dbo].[tmp_DynDm_METADATA_INIT]
  ;

  --DX select 'tmp_DynDm_Metadata',* from  rdb.dbo.tmp_DynDm_Metadata;


				declare @countmeta int = 0;
				
				select top 2 @countmeta = count(*) from  rdb.dbo.tmp_DynDm_Metadata;

				--select '@countmeta',@countmeta

				 if @countmeta < 1
                 begin

				 select 'No repeat date metadata';

				 
				  IF OBJECT_ID('rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC', 'U') IS NOT NULL   
 								drop table rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC;
	

	
				  IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL', 'U') IS NOT NULL   
 								drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL;
	

				 CREATE TABLE rdb.[dbo].tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC(
						INVESTIGATION_KEY_REPEAT_NUMERIC [bigint] NULL
						) ON [PRIMARY]
					;

					CREATE TABLE rdb.[dbo].tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL(
						[INVESTIGATION_KEY_REPEAT_BLOCK_NUMERIC_ALL] [bigint] NULL
					) ON [PRIMARY]

					;

	
				SET @Proc_Step_no = @Proc_Step_no + 1;		
				SET @Proc_Step_Name = 'SP_COMPLETE'; 

	                 
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  

	               COMMIT TRANSACTION ;

					 return ;

				 end;



/*
DATA METADATA;
SET METADATA;
LENGTH USER_DEFINED_COLUMN_NM_1 &MAX_COLUMN_CHARACTER_LIMIT; 
LENGTH USER_DEFINED_COLUMN_NM_2 &MAX_COLUMN_CHARACTER_LIMIT; 
LENGTH USER_DEFINED_COLUMN_NM_3 &MAX_COLUMN_CHARACTER_LIMIT; 
LENGTH USER_DEFINED_COLUMN_NM_4 &MAX_COLUMN_CHARACTER_LIMIT; 
LENGTH USER_DEFINED_COLUMN_NM_5 &MAX_COLUMN_CHARACTER_LIMIT; 
USER_DEFINED_COLUMN_NM_1=COMPRESS(USER_DEFINED_COLUMN_NM|| '_1');
USER_DEFINED_COLUMN_NM_2=COMPRESS(USER_DEFINED_COLUMN_NM|| '_2');
USER_DEFINED_COLUMN_NM_3=COMPRESS(USER_DEFINED_COLUMN_NM|| '_3');
USER_DEFINED_COLUMN_NM_4=COMPRESS(USER_DEFINED_COLUMN_NM|| '_4');
USER_DEFINED_COLUMN_NM_5=COMPRESS(USER_DEFINED_COLUMN_NM|| '_5');

IF BLOCK_PIVOT_NBR=. THEN BLOCK_PIVOT_NBR=1;

IF BLOCK_PIVOT_NBR = 0 THEN USER_DEFINED_COLUMN_NM_1 = '';
IF BLOCK_PIVOT_NBR = 0 THEN USER_DEFINED_COLUMN_NM_2 = ''; 
IF BLOCK_PIVOT_NBR = 0 THEN USER_DEFINED_COLUMN_NM_3 = ''; 
IF BLOCK_PIVOT_NBR = 0 THEN USER_DEFINED_COLUMN_NM_4 = ''; 
IF BLOCK_PIVOT_NBR = 0 THEN USER_DEFINED_COLUMN_NM_5 = ''; 

IF BLOCK_PIVOT_NBR = 1 THEN USER_DEFINED_COLUMN_NM_2 = ''; 
IF BLOCK_PIVOT_NBR = 1 THEN USER_DEFINED_COLUMN_NM_3 = ''; 
IF BLOCK_PIVOT_NBR = 1 THEN USER_DEFINED_COLUMN_NM_4 = ''; 
IF BLOCK_PIVOT_NBR = 1 THEN USER_DEFINED_COLUMN_NM_5 = ''; 

IF BLOCK_PIVOT_NBR = 2 THEN USER_DEFINED_COLUMN_NM_3 = ''; 
IF BLOCK_PIVOT_NBR = 2 THEN USER_DEFINED_COLUMN_NM_4 = ''; 
IF BLOCK_PIVOT_NBR = 2 THEN USER_DEFINED_COLUMN_NM_5 = ''; 

IF BLOCK_PIVOT_NBR = 3 THEN USER_DEFINED_COLUMN_NM_4 = ''; 
IF BLOCK_PIVOT_NBR = 3 THEN USER_DEFINED_COLUMN_NM_5 = ''; 

IF BLOCK_PIVOT_NBR = 4 THEN USER_DEFINED_COLUMN_NM_5 = ''; 

RUN;
*/


				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING update tmp_DynDm_METADATA';
	


 update rdb.dbo.tmp_DynDm_METADATA
 SET BLOCK_PIVOT_NBR=1 where  BLOCK_PIVOT_NBR is null;

 update rdb.dbo.tmp_DynDm_METADATA
 SET USER_DEFINED_COLUMN_NM_1 = '', 
 USER_DEFINED_COLUMN_NM_2 = '',  
 USER_DEFINED_COLUMN_NM_3 = '',  
 USER_DEFINED_COLUMN_NM_4 = '',  
 USER_DEFINED_COLUMN_NM_5 = ''  
where  BLOCK_PIVOT_NBR = 0  ; 


 
 update rdb.dbo.tmp_DynDm_METADATA
 SET USER_DEFINED_COLUMN_NM_2 = '',  
 USER_DEFINED_COLUMN_NM_3 = '',  
 USER_DEFINED_COLUMN_NM_4 = '',  
 USER_DEFINED_COLUMN_NM_5 = ''  
where  BLOCK_PIVOT_NBR = 1  ; 

 
 update rdb.dbo.tmp_DynDm_METADATA
 SET USER_DEFINED_COLUMN_NM_3 = '',  
 USER_DEFINED_COLUMN_NM_4 = '',  
 USER_DEFINED_COLUMN_NM_5 = ''  
where  BLOCK_PIVOT_NBR = 2  ; 

 
 update rdb.dbo.tmp_DynDm_METADATA
 SET USER_DEFINED_COLUMN_NM_4 = '',  
 USER_DEFINED_COLUMN_NM_5 = ''  
where  BLOCK_PIVOT_NBR = 3  ; 

 
 update rdb.dbo.tmp_DynDm_METADATA
 SET USER_DEFINED_COLUMN_NM_5 = ''  
where  BLOCK_PIVOT_NBR = 4  ; 

  --DX select 'tmp_DynDm_METADATA',* from  rdb.dbo.tmp_DynDm_METADATA;





/*

PROC SORT DATA=METADATA ; BY RDB_COLUMN_NM;RUN;
PROC TRANSPOSE DATA=METADATA OUT=METADATA_OUT;
	VAR USER_DEFINED_COLUMN_NM_1 USER_DEFINED_COLUMN_NM_2 USER_DEFINED_COLUMN_NM_3 USER_DEFINED_COLUMN_NM_4 
USER_DEFINED_COLUMN_NM_5;
COPY  BLOCK_NM;
    BY RDB_COLUMN_NM;
RUN;
*/



				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_METADATA_OUT';
	


         IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_OUT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_OUT;

		 select rdb_column_nm,block_nm,variable as '_NAME_',value as 'COL1'
			into  rdb.dbo.tmp_DynDm_METADATA_OUT
			from (
				select rdb_column_nm,block_nm, USER_DEFINED_COLUMN_NM_1 ,USER_DEFINED_COLUMN_NM_2 ,USER_DEFINED_COLUMN_NM_3,USER_DEFINED_COLUMN_NM_4,USER_DEFINED_COLUMN_NM_5
				from  rdb.dbo.tmp_DynDm_METADATA WITH (NOLOCK)
				) as t
				unpivot (
				value for variable in (  USER_DEFINED_COLUMN_NM_1 ,USER_DEFINED_COLUMN_NM_2 ,USER_DEFINED_COLUMN_NM_3,USER_DEFINED_COLUMN_NM_4,USER_DEFINED_COLUMN_NM_5)
				) as unpvt

				;

								--DX select 'tmp_DynDm_METADATA_OUT',* from rdb.dbo.tmp_DynDm_METADATA_OUT;


/*
DATA METADATA_OUT;
SET METADATA_OUT;
IF MISSING(COL1) THEN COL1=.;
RUN;


CREATE TABLE METADATA_OUT as select
RDB_COLUMN_NM,BLOCK_NM, _NAME_, COL1
FROM METADATA_OUT;

*/



				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_METADATA_OUT_TMP';
	


 
 IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_OUT_TMP', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_OUT_TMP;
	

--CREATE TABLE METADATA_OUT as 
select RDB_COLUMN_NM,BLOCK_NM,  _NAME_, COL1
into rdb.dbo.tmp_DynDm_METADATA_OUT_TMP
FROM rdb.dbo.tmp_DynDm_METADATA_OUT WITH (NOLOCK);
 
 /*@check to confirm
 --DX select 'tmp_DynDm_METADATA_OUT_TMP',* from rdb.dbo.tmp_DynDm_METADATA_OUT_TMP;
*/
/*


	DELETE * FROM METADATA_OUT WHERE COL1 IS NULL;

	*/
	DELETE  FROM rdb.dbo.tmp_DynDm_METADATA_OUT_TMP  WHERE COL1 IS NULL or LTRIM(  RTRIM(COL1)) = '';
 



				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_METADATA_OUT1';
	

 

--CREATE TABLE METADATA_OUT1 AS SELECT * FROM METADATA_OUT WHERE BLOCK_NM IS NOT NULL;

 IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_OUT1', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_OUT1;
	


SELECT * 
into rdb.dbo.tmp_DynDm_METADATA_OUT1
FROM rdb.dbo.tmp_DynDm_METADATA_OUT_TMP WITH (NOLOCK) 
WHERE BLOCK_NM IS NOT NULL;







--	CREATE TABLE METADATA_OUT AS 





				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_METADATA_OUT_final';
	


 
 IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_OUT_final', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_OUT_final;
	


	
	SELECT DISTINCT mo.*, mo1.BLOCK_NM AS BLOCK_NM1 , cast( null as int) as ANSWER_GROUP_SEQ_NBR
into rdb.dbo.tmp_DynDm_METADATA_OUT_final
FROM rdb.dbo.tmp_DynDm_METADATA_OUT_TMP mo WITH (NOLOCK) 
INNER JOIN rdb.dbo.tmp_DynDm_METADATA_OUT1 mo1 ON UPPER(mo1.RDB_COLUMN_NM) = UPPER(mo.RDB_COLUMN_NM);
 

 --DX select 'tmp_DynDm_METADATA_OUT_final',* from rdb.dbo.tmp_DynDm_METADATA_OUT_final ;



/*
DATA METADATA_OUT;
SET METADATA_OUT; 
	ANSWER_GROUP_SEQ_NBR = INPUT(substrn(COL1,max(1,length(COL1)),1), comma20.);
	DROP BLOCK_NM;
	RENAME BLOCK_NM1 = BLOCK_NM;
RUN;
*/


UPDATE  rdb.dbo.tmp_DynDm_METADATA_OUT_final
SET ANSWER_GROUP_SEQ_NBR = CAST(RIGHT(COL1, 1) AS INT);


/*
	CREATE TABLE STD_TESTER(COUNTSTD NUM);
	INSERT INTO STD_TESTER( COUNTSTD) VALUES(NULL);
	UPDATE STD_TESTER SET COUNTSTD= (select  count(*) from CASE_MANAGEMENT_METADATA);


DATA _NULL_;
SET STD_TESTER;

  IF COUNTSTD>1 then call symputx('FACT_CASE', 'F_STD_PAGE_CASE'); 
  IF COUNTSTD<2 then call symputx('FACT_CASE', 'F_PAGE_CASE'); 
  RUN;

*/

         declare @countSTD int = 0;


	   select  @COUNTSTD = count(*)    from rdb.dbo.tmp_DynDm_Case_Management_Metadata WITH (NOLOCK)
        ;

  declare @FACT_CASE varchar(40) = '';


  if @countSTD > 1
     begin
      set @FACT_CASE = 'F_STD_PAGE_CASE';
	end
  else 
     begin
      set @FACT_CASE = 'F_PAGE_CASE';
     end
  ;

  
 -- select  @FACT_CASE;

/*

CREATE TABLE D_INV_REPEAT_METADATA AS SELECT DISTINCT DATAMART_NM, RDB_COLUMN_NM, USER_DEFINED_COLUMN_NM,BLOCK_PIVOT_NBR
	FROM METADATA;
*/


				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_D_INV_REPEAT_METADATA';
	


 IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_REPEAT_METADATA', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_D_INV_REPEAT_METADATA;
	




    SELECT DISTINCT DATAMART_NM, RDB_COLUMN_NM, USER_DEFINED_COLUMN_NM,BLOCK_PIVOT_NBR
	, cast( null as varchar(400)) as RDB_COLUMN_NAME_LIST
	, cast( null as varchar(400)) as RDB_COLUMN_LIST
	, cast( null as varchar(400)) as RDB_COLUMN_COMMA_LIST
	into rdb.dbo.tmp_DynDm_D_INV_REPEAT_METADATA
	FROM rdb.dbo.tmp_DynDm_METADATA WITH (NOLOCK);


	--DX select 'tmp_DynDm_D_INV_REPEAT_METADATA',* 	from rdb.dbo.tmp_DynDm_D_INV_REPEAT_METADATA WITH (NOLOCK)	;




/*
%ASSIGN_KEY (D_INV_REPEAT_METADATA, SORT_KEY);

                                                                                                                                                           
DATA DYNINVLISTING;                                                                                                                                        
  SET D_INV_REPEAT_METADATA;                                                                                                                               
      BY SORT_KEY;                                                                                                                                         
                                                                                                                                                           
      LENGTH RDB_COLUMN_NAME_LIST &MAX_COLUMN_CHARACTER_LIMIT;                                                                                                                  
      RETAIN RDB_COLUMN_NAME_LIST;                                                                                                                         
                                                                                                                                                           
      LENGTH RDB_COLUMN_LIST &MAX_COLUMN_CHARACTER_LIMIT;                                                                                                                       
      RETAIN RDB_COLUMN_LIST;                                                                                                                              
                                                                                                                                                           
      LENGTH RDB_COLUMN_COMMA_LIST &MAX_COLUMN_CHARACTER_LIMIT;                                                                                                                 
      RETAIN RDB_COLUMN_COMMA_LIST;                                                                                                                        
                                                                                                                                                           
      IF FIRST.SORT_KEY AND NOT MISSING(DATAMART_NM) THEN                                                                                                  
            USER_DEFINED_COLUMN_NAME = COMPRESS( RDB_COLUMN_NM) || ' AS ' ||COMPRESS(USER_DEFINED_COLUMN_NM)|| " '"||COMPRESS(USER_DEFINED_COLUMN_NM)||"'";
      IF FIRST.SORT_KEY AND NOT MISSING(DATAMART_NM) THEN                                                                                                  
            RDB_COLUMN_NAME = COMPRESS( RDB_COLUMN_NM);                                                                                                    
      IF (LENGTHN(USER_DEFINED_COLUMN_NAME))>0 THEN  RDB_COLUMN_NAME_LIST = (USER_DEFINED_COLUMN_NAME || ', ' || RDB_COLUMN_NAME_LIST);                    
                                                                                                                                                           
      IF (LENGTHN(RDB_COLUMN_NAME))>0 THEN  RDB_COLUMN_LIST = (RDB_COLUMN_NAME || ' ' ||  RDB_COLUMN_LIST);                                                
      IF (LENGTHN(RDB_COLUMN_NAME))>0 THEN  RDB_COLUMN_COMMA_LIST = (RDB_COLUMN_NAME || ', ' ||  RDB_COLUMN_COMMA_LIST);                                   
      OUTPUT;    
DROP datamart_nm USER_DEFINED_COLUMN_NAME form_cd; 
RUN;                                                                                                                                                       
*/




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING update tmp_DynDm_D_INV_REPEAT_METADATA';
	



	update rdb.dbo.tmp_DynDm_D_INV_REPEAT_METADATA
	 set USER_DEFINED_COLUMN_NM = rtrim(RDB_COLUMN_NM)
	where USER_DEFINED_COLUMN_NM is null
	;





				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_DYNINVLISTING';
	

	

     IF OBJECT_ID('rdb.dbo.tmp_DynDm_DYNINVLISTING', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_DYNINVLISTING;


	select distinct (RDB_COLUMN_NM  + ' AS ' + USER_DEFINED_COLUMN_NM) as USER_DEFINED_COLUMN_NM
	into rdb.dbo.tmp_DynDm_DYNINVLISTING
	from rdb.dbo.tmp_DynDm_D_INV_REPEAT_METADATA WITH (NOLOCK)
	;

	--DX select 'tmp_DynDm_DYNINVLISTING',* from rdb.dbo.tmp_DynDm_DYNINVLISTING;


	DECLARE @RDB_COLUMN_NAME_LIST VARCHAR(MAX);


	
	SET  @RDB_COLUMN_NAME_LIST =null;

	SELECT @RDB_COLUMN_NAME_LIST = COALESCE(@RDB_COLUMN_NAME_LIST+',' ,'') + USER_DEFINED_COLUMN_NM
	FROM  rdb.dbo.tmp_DynDm_DYNINVLISTING;



	--DX SELECT '@VARCHAR LIST',@RDB_COLUMN_NAME_LIST;


	--DX select * from rdb.dbo.tmp_DynDm_DYNINVLISTING



/*
                                                                                                                                                  
DELETE FROM DYNINVLISTING WHERE SORT_KEY <(SELECT MAX (SORT_KEY) FROM DYNINVLISTING);                                                                      

DATA _NULL_;
	CALL SYMPUTX('D_REPEAT_CASE', '');
	CALL SYMPUTX('D_REPEAT_CASE_NAME', '');
	CALL SYMPUTX('D_REPEAT_COMMA_NAME', '');
RUN;
DATA _NULL_;
	CALL SYMPUTX('D_REPEAT_CASE', '');
	CALL SYMPUTX('D_REPEAT_CASE_NAME', '');
	CALL SYMPUTX('D_REPEAT_COMMA_NAME', '');
RUN;

DATA DYNINVLISTING;
SET DYNINVLISTING;
	CALL SYMPUTX('D_REPEAT_CASE', '');
	CALL SYMPUTX('D_REPEAT_CASE_NAME', '');
	CALL SYMPUTX('D_REPEAT_COMMA_NAME', '');
	LENGTH=LENGTHN(COMPRESS(RDB_COLUMN_NAME_LIST));
	RDB_COLUMN_NAME_LIST = LTRIM(  RTRIM(RDB_COLUMN_NAME_LIST);
	IF (LENGTH>1)  THEN LTRIM(  RTRIMMED_VALUE=SUBSTR((RDB_COLUMN_NAME_LIST), 1, LENGTHN(RDB_COLUMN_NAME_LIST)-1);
	CALL SYMPUTX('D_REPEAT_CASE', LTRIM(  RTRIMMED_VALUE);
	CALL SYMPUTX('D_REPEAT_CASE_NAME', RDB_COLUMN_LIST);
	CALL SYMPUTX('D_REPEAT_COMMA_NAME', RDB_COLUMN_COMMA_LIST);
	
RUN;
%put _user_;
*/


  
	DECLARE @RDB_COLUMN_LIST VARCHAR(MAX);


	
	SET  @RDB_COLUMN_LIST =null;

	SELECT @RDB_COLUMN_LIST = COALESCE(@RDB_COLUMN_LIST+' ' ,'') + RDB_COLUMN_NM
	FROM  rdb.dbo.tmp_DynDm_D_INV_REPEAT_METADATA;



	--SELECT '@RDB_COLUMN_LIST LIST',@RDB_COLUMN_LIST;




	
	DECLARE @RDB_COLUMN_COMMA_LIST VARCHAR(MAX);


	
	SET  @RDB_COLUMN_COMMA_LIST =null;

	SELECT @RDB_COLUMN_COMMA_LIST = COALESCE(@RDB_COLUMN_COMMA_LIST+',' ,'') + RDB_COLUMN_NM
	FROM  rdb.dbo.tmp_DynDm_D_INV_REPEAT_METADATA WITH (NOLOCK);



--	SELECT '@RDB_COLUMN_COMMA_LIST LIST',@RDB_COLUMN_COMMA_LIST;

		DECLARE @RDB_COLUMN_COMMA_LIST_SELECT VARCHAR(MAX);


	
	SET  @RDB_COLUMN_COMMA_LIST_SELECT =null;

	SELECT @RDB_COLUMN_COMMA_LIST_SELECT = COALESCE(@RDB_COLUMN_COMMA_LIST_SELECT+',' ,'') + 'coalesce('+RDB_COLUMN_NM+ ','''') '+  RDB_COLUMN_NM 
	FROM  rdb.dbo.tmp_DynDm_D_INV_REPEAT_METADATA WITH (NOLOCK);



	--SELECT '@RDB_COLUMN_COMMA_LIST_SELECT LIST',@RDB_COLUMN_COMMA_LIST_SELECT;



/*
%IF %SYSFUNC(EXIST(rdb.dbo.D_INVESTIGATION_REPEAT)) %THEN %DO;
	CREATE TABLE REPEAT_BLOCK AS 
	SELECT  &D_REPEAT_COMMA_NAME ANSWER_GROUP_SEQ_NBR, D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_KEY, SUMM_DATAMART.INVESTIGATION_KEY, D_INVESTIGATION_REPEAT.BLOCK_NM 
	FROM rdb.dbo.SUMM_DATAMART
	INNER JOIN  NBS_RDB .&FACT_CASE ON
	SUMM_DATAMART.INVESTIGATION_KEY  = @FACT_CASE .INVESTIGATION_KEY
	INNER JOIN  rdb.dbo.D_INVESTIGATION_REPEAT ON
	&FACT_CASE .D_INVESTIGATION_REPEAT_KEY  =D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_KEY
WHERE D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_KEY>1;

   %END;

%ELSE %DO;     
	CREATE TABLE REPEAT_BLOCK AS 
	SELECT  SUMM_DATAMART.INVESTIGATION_KEY  FROM rdb.dbo.SUMM_DATAMART;
    %END;
;

*/

 --DX select 'D_INVESTIGATION_REPEAT', * from rdb.dbo.D_INVESTIGATION_REPEAT;



				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_REPEAT_BLOCK';
	


 
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK;


 declare @sql varchar(MAX);


  IF OBJECT_ID('rdb.dbo.D_INVESTIGATION_REPEAT', 'U') IS NOT NULL  
     begin

    	--CREATE TABLE REPEAT_BLOCK AS 

	--set @RDB_COLUMN_COMMA_LIST ='EPI_CITY_OF_EXP,EPI_CNTRY_OF_EXP,EPI_CNTY_OF_EXP,EPI_ST_OR_PROV_OF_EXP,FIELD_SUPERVISOR_RVW_NOTE,FL_FUP_NOTE,IX_INV_NOTE,LAB_AST_COLLECTION_SITE,LAB_AST_INTERPRETATION,LAB_AST_QUANT_RSLT,LAB_AST_RESULT_NUM_UNITS,LAB_AST_RSLT_CODED_QUANT,LAB_AST_SPECIMEN_TYPE,LAB_AST_SPECIMEN_TYPE_OTH,LAB_AST_TEST_METHOD,LAB_AST_TEST_TYPE,LAB_CODED_QUANT_TEST_RSLT,LAB_ISOLATE_IDENTIFIER,LAB_MICROORG_IDENTIFIED,LAB_QUANT_RSLT_UNITS,LAB_QUANT_TEST_RESULT,LAB_SPECIMEN_SOURCE,LAB_SPECIMEN_SOURCE_OTH,LAB_TEST_RESULT,LAB_TEST_TYPE,MDH_HX_CONDITION,MDH_HX_CONFIRMED_IND,STD_SIGN_SX_ANATOMIC_SITE,SUPERVISOR_RVW_NOTE,SURV_NOTE,SYM_SIGN_SX_ANATOMIC_SITE_OTH,SYM_SIGN_SX_SOURCE,SYM_SignsSymptoms';

			

	 SET @SQL = '   SELECT '+ @RDB_COLUMN_COMMA_LIST_SELECT + '  ,ANSWER_GROUP_SEQ_NBR, D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_KEY, tsd.INVESTIGATION_KEY, D_INVESTIGATION_REPEAT.BLOCK_NM ' +
				' into  rdb.dbo.tmp_DynDm_REPEAT_BLOCK ' + 
				' FROM rdb.dbo.tmp_DynDm_SUMM_DATAMART tsd  WITH (NOLOCK)  ' + 
				'  INNER JOIN  rdb.dbo.'+ @FACT_CASE+ ' WITH (NOLOCK) ON	tsd.INVESTIGATION_KEY = '+ @FACT_CASE+'.INVESTIGATION_KEY ' + 
				' INNER JOIN  rdb.dbo.D_INVESTIGATION_REPEAT WITH (NOLOCK) ON '+ @FACT_CASE+'.D_INVESTIGATION_REPEAT_KEY  = D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_KEY ' + 
			    ' WHERE D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_KEY>1 ;  ' 

	--DX select 'tmp_DynDm_REPEAT_BLOCK',@SQL;

	
	EXEC (@SQL);



	end
  else 
     begin
 			SELECT  tsd.INVESTIGATION_KEY  
			into rdb.dbo.tmp_DynDm_REPEAT_BLOCK
			FROM rdb.dbo.tmp_DynDm_SUMM_DATAMART tsd  WITH (NOLOCK) ;
	end
  ;


  --DX select 'tmp_DynDm_REPEAT_BLOCK', * from rdb.dbo.tmp_DynDm_REPEAT_BLOCK;

 
/*
DELETE FROM REPEAT_BLOCK WHERE (SELECT COUNT(*) FROM DYNINVLISTING) =0;

PROC SORT DATA=REPEAT_BLOCK ; BY INVESTIGATION_KEY BLOCK_NM ANSWER_GROUP_SEQ_NBR;RUN;

PROC TRANSPOSE DATA=REPEAT_BLOCK OUT=REPEAT_BLOCK_OUT;
	VAR &D_REPEAT_CASE_NAME;
    BY INVESTIGATION_KEY BLOCK_NM ANSWER_GROUP_SEQ_NBR;
RUN;


DATA REPEAT_BLOCK_OUT;
set REPEAT_BLOCK_OUT;
RDB_COLUMN_NM = _NAME_;
run;
*/

       --             select @RDB_COLUMN_LIST;
					  --select @RDB_COLUMN_COMMA_LIST;




					--	DECLARE @columns NVARCHAR(MAX), @sql NVARCHAR(MAX) , @RDB_COLUMN_LIST  NVARCHAR(MAX)  = 'EPI_CITY_OF_EXP,EPI_CNTRY_OF_EXP,EPI_CNTY_OF_EXP,EPI_ST_OR_PROV_OF_EXP';


					/*
						SET @columns = N'';


						SELECT @columns+=N'', p.''+QUOTENAME(LTRIM(RTRIM(+@RDB_COLUMN_LIST)))
						FROM
						(
							SELECT  * 
							FROM rdb.[dbo].tmp_DynDm_REPEAT_BLOCK AS p
							--GROUP BY @RDB_COLUMN_LIST
						) AS x;


						select @columns;
					*/
					




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_REPEAT_BLOCK_OUT';
	

					
 
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT;


			--	select * from rdb.dbo.tmp_DynDm_REPEAT_BLOCK
---VS--********
                    declare @columns varchar(MAX);

					select @columns = @RDB_COLUMN_COMMA_LIST;

				--DX	select '@columns_1', @columns;

									   			
						SET @sql = N' select INVESTIGATION_KEY, BLOCK_NM BLOCK_NM_REPEAT_BLOCK_OUT, ANSWER_GROUP_SEQ_NBR ANSWER_GROUP_SEQ_NBR_REPEAT_BLOCK_OUT,variable as RDB_COLUMN_NM_REPEAT_BLOCK_OUT,value as COL1 '+
						  ' into rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT' +
						  ' from ( '+
						  ' select INVESTIGATION_KEY, BLOCK_NM, ANSWER_GROUP_SEQ_NBR, '+ @columns +
						  ' from  rdb.dbo.tmp_DynDm_REPEAT_BLOCK WITH (NOLOCK)'+
						  ' ) as t '+
						  ' unpivot ( '+
						  ' value for variable in ( '+@columns+ ') '+
						  ' ) as unpvt '
											;


					--	select  '@sql tmp_DynDm_REPEAT_BLOCK_OUT',@sql;


						EXEC ( @sql);


						----DX select 'tmp_DynDm_REPEAT_BLOCK',* from rdb.dbo.tmp_DynDm_REPEAT_BLOCK;
						--DX select 'tmp_DynDm_REPEAT_BLOCK_OUT',* from rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT WITH (NOLOCK);




/*

CREATE TABLE BLOCK_DATA_PL AS	SELECT BLOCK_NM, 
RDB_COLUMN_NM  FROM NBS_ODSE..NBS_RDB_METADATA INNER JOIN NBS_ODSE..NBS_UI_METADATA
	 ON  NBS_RDB_METADATA.NBS_UI_METADATA_UID =NBS_UI_METADATA.NBS_UI_METADATA_UID WHERE 
BLOCK_NM IS NOT NULL 
AND INVESTIGATION_FORM_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM= @DATAMART_NAME)
AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM != '' and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
AND RDB_TABLE_NM ='D_INVESTIGATION_REPEAT' 
and (code_set_group_id >0 
OR data_type in ( 'Numeric','NUMERIC') )
ORDER BY RDB_COLUMN_NM, BLOCK_NM;

*/




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_BLOCK_DATA_PL';
	


 
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_BLOCK_DATA_PL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_BLOCK_DATA_PL;


--  DECLARE  @batch_id BIGINT = 999;  DECLARE  @DATAMART_NAME VARCHAR(100) = 'STD'; 



SELECT BLOCK_NM, RDB_COLUMN_NM  
into rdb.dbo.tmp_DynDm_BLOCK_DATA_PL
FROM NBS_ODSE..NBS_RDB_METADATA 
INNER JOIN NBS_ODSE..NBS_UI_METADATA WITH (NOLOCK) ON  NBS_RDB_METADATA.NBS_UI_METADATA_UID =NBS_UI_METADATA.NBS_UI_METADATA_UID
WHERE BLOCK_NM IS NOT NULL 
AND INVESTIGATION_FORM_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM=@DATAMART_NAME)
AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM != '' and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
AND RDB_TABLE_NM ='D_INVESTIGATION_REPEAT' 
and (code_set_group_id >0 
OR data_type in ( 'Numeric','NUMERIC') )
ORDER BY RDB_COLUMN_NM, BLOCK_NM;
 
--DX select 'tmp_DynDm_BLOCK_DATA_PL',* from  rdb.dbo.tmp_DynDm_BLOCK_DATA_PL;


/*
CREATE TABLE BLOCK_DATA_UNIT AS	SELECT BLOCK_NM, 
COMPRESS(RDB_COLUMN_NM || '_UNIT') AS RDB_COLUMN_NM FROM NBS_ODSE..NBS_RDB_METADATA INNER JOIN NBS_ODSE..NBS_UI_METADATA
	 ON  NBS_RDB_METADATA.NBS_UI_METADATA_UID =NBS_UI_METADATA.NBS_UI_METADATA_UID 
WHERE BLOCK_NM IS NOT NULL 
AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM != '' and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
AND INVESTIGATION_FORM_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM= @DATAMART_NAME)
AND RDB_TABLE_NM ='D_INVESTIGATION_REPEAT' 
and (code_set_group_id >0 
OR data_type in ( 'Numeric','NUMERIC') )
ORDER BY RDB_COLUMN_NM, BLOCK_NM;
*/





				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_BLOCK_DATA_UNIT';
	


 
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_BLOCK_DATA_UNIT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_BLOCK_DATA_UNIT;

 
--CREATE TABLE BLOCK_DATA_UNIT AS	
SELECT BLOCK_NM, RDB_COLUMN_NM+'_UNIT' as RDB_COLUMN_NM
into rdb.dbo.tmp_DynDm_BLOCK_DATA_UNIT
FROM NBS_ODSE..NBS_RDB_METADATA WITH (NOLOCK) 
INNER JOIN NBS_ODSE..NBS_UI_METADATA WITH (NOLOCK) ON  NBS_RDB_METADATA.NBS_UI_METADATA_UID =NBS_UI_METADATA.NBS_UI_METADATA_UID 
WHERE BLOCK_NM IS NOT NULL 
AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM != '' 
and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
AND INVESTIGATION_FORM_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM=@DATAMART_NAME)
AND RDB_TABLE_NM ='D_INVESTIGATION_REPEAT' 
and (code_set_group_id >0 
OR data_type in ( 'Numeric','NUMERIC') )
ORDER BY RDB_COLUMN_NM, BLOCK_NM;
 

 --DX select 'tmp_DynDm_BLOCK_DATA_UNIT', * from rdb.dbo.tmp_DynDm_BLOCK_DATA_UNIT;


/*

 CREATE TABLE BLOCK_DATA_OTH AS	SELECT BLOCK_NM, 
TRIM(RDB_COLUMN_NM || '_OTH') AS RDB_COLUMN_NM FROM NBS_ODSE..NBS_RDB_METADATA INNER JOIN NBS_ODSE..NBS_UI_METADATA
	 ON  NBS_RDB_METADATA.NBS_UI_METADATA_UID =NBS_UI_METADATA.NBS_UI_METADATA_UID 
WHERE BLOCK_NM IS NOT NULL 
AND INVESTIGATION_FORM_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM= @DATAMART_NAME)
AND RDB_TABLE_NM ='D_INVESTIGATION_REPEAT' 
and (code_set_group_id >0 
OR data_type in ( 'Numeric','NUMERIC') )
ORDER BY RDB_COLUMN_NM, BLOCK_NM;


DATA BLOCK_DATA_OTH;
SET BLOCK_DATA_OTH;
LENGTH RDB_COLUMN_NM2 $22;
LENGTH RDB_COLUMN_NM $30;
RDB_COLUMN_NM2=RDB_COLUMN_NM;
RDB_COLUMN_NM = COMPRESS(RDB_COLUMN_NM2|| '_OTH');
DROP RDB_COLUMN_NM2;
RUN; 


CREATE TABLE BLOCK_DATA AS SELECT * FROM BLOCK_DATA_PL 
UNION SELECT * FROM BLOCK_DATA_UNIT
UNION SELECT * FROM BLOCK_DATA_OTH;
*/






				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_BLOCK_DATA_OTH';
	


 
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_BLOCK_DATA_OTH', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_BLOCK_DATA_OTH;


 
--CREATE TABLE BLOCK_DATA_OTH AS	

 /*

DATA BLOCK_DATA_OTH;
SET BLOCK_DATA_OTH;
LENGTH RDB_COLUMN_NM $30;
LENGTH RDB_COLUMN_NM2 $22;
RDB_COLUMN_NM2= RDB_COLUMN_NM;
RDB_COLUMN_NM=COMPRESS(RDB_COLUMN_NM2 || '_OTH');
DROP RDB_COLUMN_NM2;
RUN;

*/



SELECT BLOCK_NM, substring(rtrim(RDB_COLUMN_NM),1,22) + '_OTH' as RDB_COLUMN_NM
into rdb.dbo.tmp_DynDm_BLOCK_DATA_OTH
FROM NBS_ODSE..NBS_RDB_METADATA WITH (NOLOCK) 
INNER JOIN NBS_ODSE..NBS_UI_METADATA WITH (NOLOCK) ON  NBS_RDB_METADATA.NBS_UI_METADATA_UID =NBS_UI_METADATA.NBS_UI_METADATA_UID 
WHERE BLOCK_NM IS NOT NULL 
AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM != '' and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
AND INVESTIGATION_FORM_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM=@DATAMART_NAME)
AND RDB_TABLE_NM ='D_INVESTIGATION_REPEAT' 
and (code_set_group_id >0 
OR data_type in ( 'Numeric','NUMERIC') )
ORDER BY RDB_COLUMN_NM, BLOCK_NM;
 

--DX select 'tmp_DynDm_BLOCK_DATA_OTH', * from rdb.dbo.tmp_DynDm_BLOCK_DATA_OTH;




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_BLOCK_DATA';
	

 
 
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_BLOCK_DATA', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_BLOCK_DATA;



--CREATE TABLE BLOCK_DATA AS 

SELECT * 
into rdb.dbo.tmp_DynDm_BLOCK_DATA
FROM rdb.dbo.tmp_DynDm_BLOCK_DATA_PL WITH (NOLOCK)
UNION SELECT * FROM rdb.dbo.tmp_DynDm_BLOCK_DATA_UNIT WITH (NOLOCK)
UNION SELECT * FROM rdb.dbo.tmp_DynDm_BLOCK_DATA_OTH WITH (NOLOCK);
 
 

 /*@check To confirm
 --DX select 'tmp_DynDm_REPEAT_BLOCK_OUT', * from  rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT WITH (NOLOCK);
 --DX select 'tmp_DynDm_BLOCK_DATA',* from  rdb.dbo.tmp_DynDm_BLOCK_DATA WITH (NOLOCK);
 */



/*

CREATE TABLE REPEAT_BLOCK_OUT_BASE AS SELECT DISTINCT * FROM REPEAT_BLOCK_OUT
	INNER JOIN BLOCK_DATA 
	ON 	REPEAT_BLOCK_OUT.BLOCK_NM =BLOCK_DATA.BLOCK_NM
	AND UPCASE(REPEAT_BLOCK_OUT.RDB_COLUMN_NM)= UPCASE(BLOCK_DATA.RDB_COLUMN_NM);

PROC DATASETS LIBRARY = WORK NOLIST;
	DELETE 
	BLOCK_DATA_OTH BLOCK_DATA_PL BLOCK_DATA_UNIT BLOCK_DATA REPEAT_BLOCK_OUT RUN;


PROC SORT DATA=REPEAT_BLOCK_OUT_BASE NODUPKEY ; BY INVESTIGATION_KEY RDB_COLUMN_NM BLOCK_NM ANSWER_GROUP_SEQ_NBR ;RUN;
*/




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_REPEAT_BLOCK_OUT_BASE';
	


 
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE;




SELECT DISTINCT * 
into rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE
FROM rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT bo
	INNER JOIN rdb.dbo.tmp_DynDm_BLOCK_DATA bd WITH (NOLOCK) ON 	bo.BLOCK_NM_REPEAT_BLOCK_OUT =bd.BLOCK_NM
	       AND UPPER(bo.RDB_COLUMN_NM_REPEAT_BLOCK_OUT)= UPPER(bd.RDB_COLUMN_NM)
		  ;


 


 --DX select 'tmp_DynDm_REPEAT_BLOCK_OUT_BASE',* from rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE
 



/*



DATA REPEAT_BLOCK_OUT_ALL;
SET REPEAT_BLOCK_OUT_BASE;
	BY INVESTIGATION_KEY RDB_COLUMN_NM BLOCK_NM ANSWER_GROUP_SEQ_NBR;
	
	FORMAT ANSWER_DESC1-ANSWER_DESC20 $100.;
	LENGTH ANSWER_DESC21 $8000;
	FORMAT ANSWER_DESCCHECK $10.;	
	ARRAY ANSWER_DESC(20) ANSWER_DESC1-ANSWER_DESC20;
	ANSWER_DESCCHECK='';
	RETAIN  ANSWER_DESC1-ANSWER_DESC21 ' ' I 0;
	IF FIRST.RDB_COLUMN_NM  THEN DO;
		DO J=1 TO 20; ANSWER_DESC(J) = ' ';	
		END;
		I = 0; ANSWER_DESC21 = '';
	END;
	I+1;
	IF I <= 20 THEN DO;
		IF LENGTHN(COMPRESS(col1))>0 AND LENGTHN(ANSWER_DESCCHECK)<2 THEN ANSWER_DESCCHECK ='TRUE';
		IF I = 1 THEN ANSWER_DESC21 =  LEFT(TRIM(col1));
		ELSE  ANSWER_DESC21 =LEFT(TRIM(ANSWER_DESC21))||' ~ '|| LEFT(TRIM(col1)) ;
	END;
	IF LAST.RDB_COLUMN_NM THEN OUTPUT;
	DROP ANSWER_DESC1-ANSWER_DESC20  _NAME_ _LABEL_ col1 I J;
	
	
RUN;
*/




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_REPEAT_BLOCK_OUT_ALL';
	


 
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL;


 SELECT
     INVESTIGATION_KEY,RDB_COLUMN_NM_REPEAT_BLOCK_OUT, BLOCK_NM_REPEAT_BLOCK_OUT,
     cast(STUFF(
         (select(SELECT   rtrim(' ~'+ coalesce(' '+rtrim(ltrim(COL1)) ,''))
             FROM  rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE WITH (NOLOCK)
          where INVESTIGATION_KEY = a.INVESTIGATION_KEY 
		  AND RDB_COLUMN_NM_REPEAT_BLOCK_OUT= a.RDB_COLUMN_NM_REPEAT_BLOCK_OUT
		  AND  BLOCK_NM_REPEAT_BLOCK_OUT = a.BLOCK_NM_REPEAT_BLOCK_OUT 
		  --AND  ANSWER_GROUP_SEQ_NBR_REPEAT_BLOCK_OUT = a.ANSWER_GROUP_SEQ_NBR_REPEAT_BLOCK_OUT
 		  order by INVESTIGATION_KEY,RDB_COLUMN_NM_REPEAT_BLOCK_OUT, BLOCK_NM_REPEAT_BLOCK_OUT, ANSWER_GROUP_SEQ_NBR_REPEAT_BLOCK_OUT
         FOR XML PATH (''),TYPE).value('.','varchar(8000)'))
          , 1, 1, '')  as varchar(8000)) AS  ANSWER_DESC21
into rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL
FROM rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE AS a WITH (NOLOCK) 
group  BY INVESTIGATION_KEY,RDB_COLUMN_NM_REPEAT_BLOCK_OUT, BLOCK_NM_REPEAT_BLOCK_OUT

--having count(*) > 1
;



update rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL
set ANSWER_DESC21 = substring(ANSWER_DESC21,3, len(ANSWER_DESC21));


 --DX select 'tmp_DynDm_REPEAT_BLOCK_OUT_ALL',* from rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL WITH (NOLOCK);



/*
DATA REPEAT_BLOCK_OUT_BASE ;
SET REPEAT_BLOCK_OUT_BASE ;
RDB_COLUMN_NM = _NAME_;
RUN;

DATA REPEAT_BLOCK_OUT_BASE;
SET REPEAT_BLOCK_OUT_BASE;
DATA_VALUE=col1;
IF ANSWER_GROUP_SEQ_NBR = 1 THEN COL2 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 1 THEN COL3 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 1 THEN COL4 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 1 THEN COL5 =' ';  

IF ANSWER_GROUP_SEQ_NBR = 2 THEN COL1 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 2 THEN COL3 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 2 THEN COL4 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 2 THEN COL5 =' ';  

IF ANSWER_GROUP_SEQ_NBR = 3 THEN COL1 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 3 THEN COL2 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 3 THEN COL4 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 3 THEN COL5 =' ';  

IF ANSWER_GROUP_SEQ_NBR = 4 THEN COL1 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 4 THEN COL2 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 4 THEN COL3 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 4 THEN COL5 =' ';  

IF ANSWER_GROUP_SEQ_NBR = 5 THEN COL1 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 5 THEN COL2 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 5 THEN COL3 =' ';  
IF ANSWER_GROUP_SEQ_NBR = 5 THEN COL4 =' ';  

RUN;
*/




/*


CREATE TABLE METADATA_OUT as select
RDB_COLUMN_NM, _NAME_, COL1, BLOCK_NM, ANSWER_GROUP_SEQ_NBR
FROM METADATA_OUT;
*/



				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_METADATA_OUT2';
	



  IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_OUT2', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_OUT2;

 
--CREATE TABLE METADATA_OUT as 

select RDB_COLUMN_NM, _NAME_, COL1, BLOCK_NM, ANSWER_GROUP_SEQ_NBR
into rdb.dbo.tmp_DynDm_METADATA_OUT2
FROM rdb.dbo.tmp_DynDm_METADATA_OUT_final WITH (NOLOCK);
 /*@check to confirm
 --DX select 'tmp_DynDm_METADATA_OUT2',* from rdb.dbo.tmp_DynDm_METADATA_OUT2 WITH (NOLOCK);
 */

 alter table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE
 drop column block_nm, rdb_column_nm;
 
 
--EXEC sp_RENAME 'tmp_DynDm_REPEAT_BLOCK_OUT_BASE.ANSWER_GROUP_SEQ_NBR', 'ANSWER_GROUP_SEQ_NBR_REPEAT_BLOCK_OUT', 'COLUMN';

EXEC sp_RENAME 'tmp_DynDm_REPEAT_BLOCK_OUT_BASE.COL1', 'DATA_VALUE', 'COLUMN';


/*@check to confirm
--DX select 'tmp_DynDm_REPEAT_BLOCK_OUT_BASE',* from rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE WITH (NOLOCK) trb;
 --DX select 'tmp_DynDm_METADATA_OUT_final',* from tmp_DynDm_METADATA_OUT_final WITH (NOLOCK)
 */
 



/*


CREATE TABLE METADATA_MERGED_INIT AS SELECT distinct * FROM METADATA_OUT
LEFT OUTER JOIN REPEAT_BLOCK_OUT_BASE ON
UPCASE(METADATA_OUT.RDB_COLUMN_NM)  = UPCASE(REPEAT_BLOCK_OUT_BASE.RDB_COLUMN_NM)
AND METADATA_OUT.ANSWER_GROUP_SEQ_NBR =REPEAT_BLOCK_OUT_BASE.ANSWER_GROUP_SEQ_NBR
AND METADATA_OUT.BLOCK_NM = REPEAT_BLOCK_OUT_BASE.BLOCK_NM ;
*/

--alter table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE
-- drop column block_nm, rdb_column_nm;
 


				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_METADATA_MERGED_INIT';
	

 

/*@check to confirm
--DX select 'tmp_DynDm_REPEAT_BLOCK_OUT_BASE',* from rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE  trb;
 --DX select 'tmp_DynDm_METADATA_OUT_final',* from tmp_DynDm_METADATA_OUT_final
 */

 IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_MERGED_INIT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_METADATA_MERGED_INIT;

 
 
--CREATE TABLE METADATA_MERGED_INIT AS 
SELECT distinct * 
into rdb.dbo.tmp_DynDm_METADATA_MERGED_INIT
FROM rdb.dbo.tmp_DynDm_METADATA_OUT_final tdof with (NOLOCK)  
LEFT OUTER JOIN rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE  trbb with (NOLOCK) ON UPPER(tdof.RDB_COLUMN_NM)  = UPPER(trbb.RDB_COLUMN_NM_REPEAT_BLOCK_OUT)
AND tdof.ANSWER_GROUP_SEQ_NBR =trbb.ANSWER_GROUP_SEQ_NBR_REPEAT_BLOCK_OUT
AND tdof.BLOCK_NM = trbb.BLOCK_NM_REPEAT_BLOCK_OUT ;
 


 --DX select 'tmp_DynDm_METADATA_MERGED_INIT',* from rdb.dbo.tmp_DynDm_METADATA_MERGED_INIT;
 

/*
PROC SORT DATA=METADATA_MERGED_INIT ; BY INVESTIGATION_KEY;RUN;

PROC TRANSPOSE DATA=METADATA_MERGED_INIT OUT=INVESTIGATION_REPEAT_NUMERIC;
	VAR DATA_VALUE;
	ID COL1;
    BY INVESTIGATION_KEY;
RUN;

DATA INVESTIGATION_REPEAT_NUMERIC;
SET INVESTIGATION_REPEAT_NUMERIC;
DROP _NAME_;
RUN;

*/



				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC';
	


						
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC', 'U') IS NOT NULL 
								drop table rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC;


						--DECLARE @columns NVARCHAR(MAX);
						--DECLARE @sql NVARCHAR(MAX);

						SET @columns = N'';

						SELECT @columns+=N', p.'+QUOTENAME(LTRIM(RTRIM([COL1])))
						FROM
						(
							SELECT [COL1] 
							FROM rdb.[dbo].tmp_DynDm_METADATA_MERGED_INIT  AS p with (NOLOCK) 
							GROUP BY [COL1]
						) AS x;


						SET @sql = N'
						SELECT [INVESTIGATION_KEY] , '+STUFF(@columns, 1, 2, '')+
						' into rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC ' +
						'FROM (
						SELECT [INVESTIGATION_KEY], [DATA_VALUE] , [COL1] 
						 FROM rdb.[dbo].tmp_DynDm_METADATA_MERGED_INIT
							group by [INVESTIGATION_KEY], [DATA_VALUE] , [COL1] 
								) AS j PIVOT (max(DATA_VALUE) FOR [COL1] in 
							   ('+STUFF(REPLACE(@columns, ', p.[', ',['), 1, 1, '')+')) AS p;';

						print @sql;
						EXEC ( @sql);




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC';
	

						
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC', 'U') IS  NULL 
			   begin
						
						SELECT [INVESTIGATION_KEY] 
						into rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC
						 FROM rdb.[dbo].tmp_DynDm_METADATA_MERGED_INIT
						
						
				end;



						--DX select 'tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC',* from rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC;


/*


CREATE TABLE REPEAT_ALL AS SELECT DISTINCT RDB_COLUMN_NM, USER_DEFINED_COLUMN_NM  FROM D_INV_REPEAT_METADATA;


DATA REPEAT_ALL;
SET REPEAT_ALL;
LENGTH USER_DEFINED_COLUMN_NM_ALL $4000;
USER_DEFINED_COLUMN_NM_ALL = COMPRESS(USER_DEFINED_COLUMN_NM || '_ALL');
DROP USER_DEFINED_COLUMN_NM;
RUN;
*/




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_INVESTIGATION_REPEAT_ALL';
	

 
 IF OBJECT_ID('rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_ALL;

 
--CREATE TABLE REPEAT_ALL AS
SELECT DISTINCT RDB_COLUMN_NM, cast((rtrim(USER_DEFINED_COLUMN_NM) + '_ALL' )as varchar(4000)) as USER_DEFINED_COLUMN_NM_ALL
into rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_ALL
FROM rdb.dbo.tmp_DynDm_D_INV_REPEAT_METADATA with (NOLOCK) ;

--DX select 'tmp_DynDm_INVESTIGATION_REPEAT_ALL',* from rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_ALL;

/*

CREATE TABLE REPEAT_BLOCK_METADATA_OUT AS SELECT
REPEAT_BLOCK_OUT_ALL.*, REPEAT_ALL.USER_DEFINED_COLUMN_NM_ALL FROM
REPEAT_ALL LEFT OUTER JOIN REPEAT_BLOCK_OUT_ALL 
ON UPCASE(REPEAT_ALL.RDB_COLUMN_NM) = UPCASE(REPEAT_BLOCK_OUT_ALL.RDB_COLUMN_NM);

*/




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_REPEAT_BLOCK_METADATA_OUT';
	


 IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_METADATA_OUT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_METADATA_OUT;

 
--CREATE TABLE REPEAT_BLOCK_METADATA_OUT AS 
SELECT rboa.*, ra.USER_DEFINED_COLUMN_NM_ALL 
into  rdb.dbo.tmp_DynDm_REPEAT_BLOCK_METADATA_OUT
FROM rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_ALL ra  with (NOLOCK) 
LEFT OUTER JOIN rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL rboa with (NOLOCK)   ON UPPER(ra.RDB_COLUMN_NM) = UPPER(rboa.RDB_COLUMN_NM_REPEAT_BLOCK_OUT)
and investigation_key is not null
;
 

 --DX select ' rdb.dbo.tmp_DynDm_REPEAT_BLOCK_METADATA_OUT',* from rdb.dbo.tmp_DynDm_REPEAT_BLOCK_METADATA_OUT;





DELETE FROM rdb.dbo.tmp_DynDm_REPEAT_BLOCK_METADATA_OUT WHERE USER_DEFINED_COLUMN_NM_ALL= '_ALL';


/*
 PROC SORT DATA=REPEAT_BLOCK_METADATA_OUT ; BY INVESTIGATION_KEY ;RUN;

PROC TRANSPOSE DATA=REPEAT_BLOCK_METADATA_OUT OUT=REPEAT_BLOCK_NUMERIC_ALL;
	VAR ANSWER_DESC21;
	ID USER_DEFINED_COLUMN_NM_ALL;
    BY INVESTIGATION_KEY ;
RUN;

DATA REPEAT_BLOCK_NUMERIC_ALL;
SET REPEAT_BLOCK_NUMERIC_ALL;
DROP _NAME_;
RUN;
%MEND REPEATNUMERICDATA;

*/



				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL';
	


	
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL', 'U') IS NOT NULL 
								drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL;


						--DECLARE @columns NVARCHAR(MAX);
						--DECLARE @sql NVARCHAR(MAX);

						SET @columns = N'';

						SELECT @columns+=N', p.'+QUOTENAME(LTRIM(RTRIM([USER_DEFINED_COLUMN_NM_ALL])))
						FROM
						(
							SELECT [USER_DEFINED_COLUMN_NM_ALL] 
							FROM rdb.[dbo].tmp_DynDm_REPEAT_BLOCK_METADATA_OUT AS p with (NOLOCK)  
							GROUP BY [USER_DEFINED_COLUMN_NM_ALL]
						) AS x;


						SET @sql = N'
						SELECT [INVESTIGATION_KEY] , '+STUFF(@columns, 1, 2, '')+
						' into rdb.dbo.tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL ' +
						'FROM (
						SELECT [INVESTIGATION_KEY], [ANSWER_DESC21] , [USER_DEFINED_COLUMN_NM_ALL] 
						 FROM rdb.[dbo].tmp_DynDm_REPEAT_BLOCK_METADATA_OUT
							group by [INVESTIGATION_KEY], [ANSWER_DESC21] , [USER_DEFINED_COLUMN_NM_ALL] 
								) AS j PIVOT (max(ANSWER_DESC21) FOR [USER_DEFINED_COLUMN_NM_ALL] in 
							   ('+STUFF(REPLACE(@columns, ', p.[', ',['), 1, 1, '')+')) AS p;';

						print @sql;
						EXEC ( @sql);


/*@check to confirm

--DX select 'tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL',* from tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL;
*/
exec sp_rename 'rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC.INVESTIGATION_KEY', 'INVESTIGATION_KEY_REPEAT_NUMERIC','COLUMN';
exec sp_rename 'rdb.dbo.tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL.INVESTIGATION_KEY', 'INVESTIGATION_KEY_REPEAT_BLOCK_NUMERIC_ALL','COLUMN';




				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_RepeatNumericData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
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
						   ,'RDB.DBO.DynDm_RepeatNumericData_sp ' + @DATAMART_NAME
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
							COMMIT  TRANSACTION;
						END;
						DECLARE @ErrorNumber int= ERROR_NUMBER();
						DECLARE @ErrorLine int= ERROR_LINE();
						DECLARE @ErrorMessage nvarchar(4000)= ERROR_MESSAGE();
						DECLARE @ErrorSeverity int= ERROR_SEVERITY();
						DECLARE @ErrorState int= ERROR_STATE();

                        select @ErrorMessage;

						INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [Error_Description], [row_count] )
						VALUES( @Batch_id, 'DYNAMIC_DATAMART', 'RDB.DBO.DynDm_RepeatNumericData_sp : ' + @DATAMART_NAME, 'ERROR', @Proc_Step_no, 'ERROR - '+@Proc_Step_name, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );
						RETURN -1;
	        END CATCH;
END;

GO
