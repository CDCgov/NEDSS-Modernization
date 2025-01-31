USE RDB;
GO
 
 IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_METADATA_OUT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_METADATA_OUT;
	

 IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT;
	
  IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_REPEAT_ALL;
							   				 			  
  IF OBJECT_ID('rdb.dbo.tmp_DynDM_BLOCK_DATA', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_BLOCK_DATA;
				

  IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK;
				

  IF OBJECT_ID('rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_DATE', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_DATE;


  IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE;


IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL;


--  exec  rdb.dbo.DynDM_REPEATDATEDATA_sp  231115002231 ,'STD' ;



			--	DD_R_PDI_DA
go

USE rdb;
GO

		--  exec  rdb.dbo.DynDM_REPEATDATEDATA_sp 999,'MALARIA' ;

--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'HIV_PERI';


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


			 IF OBJECT_ID('rdb.dbo.DynDM_REPEATDATEDATA_sp', 'P') IS NOT NULL
				BEGIN
				DROP PROCEDURE dbo.DynDM_REPEATDATEDATA_sp;
				END;

GO



CREATE PROCEDURE [dbo].DynDM_REPEATDATEDATA_sp
 
            @batch_id BIGINT,
			@DATAMART_NAME VARCHAR(100)
with RECOMPILE
	AS
BEGIN  
	 BEGIN TRY
	


--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'HIV_PERI';

		

		
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
           ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+ @DATAMART_NAME
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
    COMMIT TRANSACTION;
	

  IF OBJECT_ID('rdb.dbo.tmp_DynDM_Metadata', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_Metadata;
		

	
  IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_ALL', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_REPEAT_ALL;





  IF OBJECT_ID('rdb.dbo.tmp_DynDM_BLOCK_DATA', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_BLOCK_DATA;



  IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK;



  IF OBJECT_ID('rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_DATE', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_DATE;


  IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE;





	 
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_Metadata';
	

		



--  DECLARE  @batch_id BIGINT = 999;  DECLARE  @DATAMART_NAME VARCHAR(100) = 'HIV_PERI'; DECLARE @Proc_Step_no FLOAT = 0 ;	DECLARE @Proc_Step_Name VARCHAR(200) = '' ;	DECLARE @RowCount_no INT ;
		 
--CREATE TABLE METADATA AS 

  
  IF OBJECT_ID('rdb.dbo.tmp_DynDM_Metadata', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_Metadata;



 SELECT  distinct INIT.DATAMART_NM, RDB_COLUMN_NM, USER_DEFINED_COLUMN_NM, coalesce(NBS_RDB_METADATA.BLOCK_PIVOT_NBR,1) as BLOCK_PIVOT_NBR , BLOCK_NM 
 ,rtrim(USER_DEFINED_COLUMN_NM) +'_1' as USER_DEFINED_COLUMN_NM_1
 ,rtrim(USER_DEFINED_COLUMN_NM) +'_2' as USER_DEFINED_COLUMN_NM_2
 ,rtrim(USER_DEFINED_COLUMN_NM) +'_3' as USER_DEFINED_COLUMN_NM_3
 ,rtrim(USER_DEFINED_COLUMN_NM) +'_4' as USER_DEFINED_COLUMN_NM_4
 ,rtrim(USER_DEFINED_COLUMN_NM) +'_5' as USER_DEFINED_COLUMN_NM_5
 into rdb.dbo.tmp_DynDM_Metadata
 FROM NBS_ODSE..NBS_UI_METADATA 
INNER JOIN NBS_ODSE..NBS_RDB_METADATA with (nolock) ON NBS_UI_METADATA.NBS_UI_METADATA_UID = NBS_RDB_METADATA.NBS_UI_METADATA_UID
INNER JOIN rdb.dbo.TMP_INIT INIT with (nolock) ON NBS_UI_METADATA.INVESTIGATION_FORM_CD = INIT.FORM_CD
WHERE INVESTIGATION_FORM_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE with (nolock) WHERE DATAMART_NM= @DATAMART_NAME)
	AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM != '' and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
	AND RDB_TABLE_NM ='D_INVESTIGATION_REPEAT' 
	and data_type in ( 'DATETIME','DATE', 'Date','Date/Time')  and code_set_group_id is null
ORDER BY RDB_COLUMN_NM
;

--select ' rdb.dbo.tmp_DynDM_Metadata',* from rdb.dbo.tmp_DynDM_Metadata where rdb_column_nm like '%DD_R_PDI_DATE%';

						
                   -- print 'I AM HERE';


	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

  
 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING update tmp_DynDM_Metadata';
	



/*
IF BLOCK_PIVOT_NBR=. THEN BLOCK_PIVOT_NBR=1;
*/



             update  rdb.dbo.tmp_DynDM_Metadata
				 SET USER_DEFINED_COLUMN_NM_1 = '',
					 USER_DEFINED_COLUMN_NM_2 = '', 
				 USER_DEFINED_COLUMN_NM_3 = '', 
				 USER_DEFINED_COLUMN_NM_4 = '', 
				 USER_DEFINED_COLUMN_NM_5 = ''
				where BLOCK_PIVOT_NBR = 0
				;


			 update  rdb.dbo.tmp_DynDM_Metadata
				 SET  USER_DEFINED_COLUMN_NM_2 = '', 
				 USER_DEFINED_COLUMN_NM_3 = '', 
				 USER_DEFINED_COLUMN_NM_4 = '', 
				 USER_DEFINED_COLUMN_NM_5 = ''
				where BLOCK_PIVOT_NBR = 1
				;


			update  rdb.dbo.tmp_DynDM_Metadata
				 SET  USER_DEFINED_COLUMN_NM_3 = '', 
				 USER_DEFINED_COLUMN_NM_4 = '', 
				 USER_DEFINED_COLUMN_NM_5 = '' 
				where BLOCK_PIVOT_NBR = 2
				;


			update  rdb.dbo.tmp_DynDM_Metadata
				 SET  USER_DEFINED_COLUMN_NM_4 = '', 
				 USER_DEFINED_COLUMN_NM_5 = ''
				where BLOCK_PIVOT_NBR = 3
				;

		     update  rdb.dbo.tmp_DynDM_Metadata
				 SET   USER_DEFINED_COLUMN_NM_5 = '' 
				where BLOCK_PIVOT_NBR = 4
				;

				--DX select 'tmp_DynDM_Metadata',* from  rdb.dbo.tmp_DynDM_Metadata where rdb_column_nm like '%DD_R_PDI_DATE%';


				declare @countmeta int = 0;
				
				select top 2 @countmeta = count(*) from  rdb.dbo.tmp_DynDM_Metadata with (nolock);

			--	select '@countmeta',@countmeta

				 if @countmeta < 1
                 begin

				 select 'No repeat date metadata';

				 
				  IF OBJECT_ID('rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_DATE', 'U') IS NOT NULL   
 								drop table rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_DATE;
	

	
				  IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_DATE_ALL', 'U') IS NOT NULL   
 								drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_DATE_ALL;
	

				 CREATE TABLE rdb.[dbo].tmp_DynDM_INVESTIGATION_REPEAT_DATE(
						[INVESTIGATION_KEY] [bigint] NULL
						) ON [PRIMARY]
					;

					CREATE TABLE rdb.[dbo].tmp_DynDM_REPEAT_BLOCK_DATE_ALL(
						[INVESTIGATION_KEY] [bigint] NULL
					) ON [PRIMARY]

					;


					
				SET @Proc_Step_no = @Proc_Step_no + 1;		
				SET @Proc_Step_Name = 'SP_COMPLETE'; 

	                 
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  

	
													
	               COMMIT TRANSACTION ;

					 return ;

				 end;


/*
PROC SORT DATA=METADATA ; BY RDB_COLUMN_NM;RUN;
PROC TRANSPOSE DATA=METADATA OUT=METADATA_OUT;
	VAR USER_DEFINED_COLUMN_NM_1 USER_DEFINED_COLUMN_NM_2 USER_DEFINED_COLUMN_NM_3 USER_DEFINED_COLUMN_NM_4 
USER_DEFINED_COLUMN_NM_5;
COPY  BLOCK_NM;
    BY RDB_COLUMN_NM;
RUN;
*/


/*

rdb_column_nm, block_nm, var1 ( _NAME), Value_var1 ( col1)



*/

	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
			SET @Proc_Step_no = @Proc_Step_no + 1;
			SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_METADATA_OUT';
	

  
  IF OBJECT_ID('rdb.dbo.tmp_DynDM_METADATA_OUT', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_METADATA_OUT;


  
  IF OBJECT_ID('rdb.dbo.tmp_DynDM_TransposedData', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_TransposedData;
	



select rdb_column_nm,block_nm,variable as '_NAME_',value as 'COL1'
into rdb.dbo.tmp_DynDM_METADATA_OUT
from (
select rdb_column_nm,block_nm, USER_DEFINED_COLUMN_NM_1 ,USER_DEFINED_COLUMN_NM_2 ,USER_DEFINED_COLUMN_NM_3,USER_DEFINED_COLUMN_NM_4,USER_DEFINED_COLUMN_NM_5
from  rdb.dbo.tmp_DynDM_METADATA with (nolock)
) as t
unpivot (
value for variable in (  USER_DEFINED_COLUMN_NM_1 ,USER_DEFINED_COLUMN_NM_2 ,USER_DEFINED_COLUMN_NM_3,USER_DEFINED_COLUMN_NM_4,USER_DEFINED_COLUMN_NM_5)
) as unpvt

;


--DX select 'tmp_DynDM_METADATA_OUT', * from rdb.dbo.tmp_DynDM_METADATA_OUT where rdb_column_nm like '%DD_R_PDI_DATE%';



	/*


CREATE TABLE rdb.dbo.tmp_DynDM_TransposedData (
    RDB_COLUMN_NM VARCHAR(255),
    ColumnName VARCHAR(255),
    ColumnValue  VARCHAR(255),  
    BLOCK_NM VARCHAR(255)
);

-- Insert data into the temporary table
INSERT INTO  rdb.dbo.tmp_DynDM_TransposedData (RDB_COLUMN_NM, ColumnName, ColumnValue, BLOCK_NM)
SELECT RDB_COLUMN_NM, 'USER_DEFINED_COLUMN_NM_1', USER_DEFINED_COLUMN_NM_1, BLOCK_NM FROM rdb.dbo.tmp_DynDM_Metadata
UNION ALL
SELECT RDB_COLUMN_NM, 'USER_DEFINED_COLUMN_NM_2', USER_DEFINED_COLUMN_NM_2, BLOCK_NM FROM rdb.dbo.tmp_DynDM_Metadata
UNION ALL
SELECT RDB_COLUMN_NM, 'USER_DEFINED_COLUMN_NM_3', USER_DEFINED_COLUMN_NM_3, BLOCK_NM FROM rdb.dbo.tmp_DynDM_Metadata
UNION ALL
SELECT RDB_COLUMN_NM, 'USER_DEFINED_COLUMN_NM_4', USER_DEFINED_COLUMN_NM_4, BLOCK_NM FROM rdb.dbo.tmp_DynDM_Metadata
UNION ALL
SELECT RDB_COLUMN_NM, 'USER_DEFINED_COLUMN_NM_5', USER_DEFINED_COLUMN_NM_5, BLOCK_NM FROM rdb.dbo.tmp_DynDM_Metadata;


SELECT *
INTO rdb.dbo.tmp_DynDM_METADATA_OUT
FROM  rdb.dbo.tmp_DynDM_TransposedData;

drop table  rdb.dbo.tmp_DynDM_TransposedData;
*/


 /*@@CHECK for Debug
select * from rdb.dbo.tmp_DynDM_METADATA;
select * from rdb.dbo.tmp_DynDM_METADATA_OUT;
*/



/*
DATA METADATA_OUT;
SET METADATA_OUT;
IF MISSING(COL1) THEN COL1=.;
RUN;
*/


IF COL_LENGTH('rdb.dbo.tmp_DynDM_METADATA_OUT', 'COL1') IS NULL
BEGIN
    ALTER TABLE rdb.dbo.tmp_DynDM_METADATA_OUT  ADD COL1 VARCHAR(8000)
END





/*
--CREATE TABLE METADATA_OUT as 
select RDB_COLUMN_NM,BLOCK_NM, _NAME_, COL1
FROM rdb.dbo.tmp_DynDM_METADATA_OUT_TMP;
;
*/





/*
PROC SQL;
	DELETE * FROM METADATA_OUT WHERE COL1 IS NULL;
QUIT;

*/

DELETE  FROM rdb.dbo.tmp_DynDM_METADATA_OUT WHERE (COL1)  IS NULL or rtrim(COL1)  = '' ;

-- select * from rdb.dbo.tmp_DynDM_METADATA_OUT;
  

  
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
			SET @Proc_Step_no = @Proc_Step_no + 1;
			SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_METADATA_OUT1';
	



            IF OBJECT_ID('rdb.dbo.tmp_DynDM_METADATA_OUT1', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_METADATA_OUT1;





				--CREATE TABLE METADATA_OUT1 AS 
				SELECT * 
				into rdb.dbo.tmp_DynDM_METADATA_OUT1
				FROM rdb.dbo.tmp_DynDM_METADATA_OUT  with (nolock)
				WHERE BLOCK_NM IS NOT NULL;




				
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_METADATA_OUT_final';
	

			  IF OBJECT_ID('rdb.dbo.tmp_DynDM_METADATA_OUT_final', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDM_METADATA_OUT_final;


/*

DATA METADATA_OUT;
SET METADATA_OUT; 
	ANSWER_GROUP_SEQ_NBR = INPUT(substrn(COL1,max(1,length(COL1)),1), comma20.);
	DROP BLOCK_NM;
	RENAME BLOCK_NM1 = BLOCK_NM;
RUN;
*/




--	CREATE TABLE METADATA_OUT AS 
SELECT DISTINCT mo.*, mo1.BLOCK_NM AS BLOCK_NM1 
, CAST(SUBSTRING(mo.COL1, LEN(mo.COL1), 1) AS INT) AS ANSWER_GROUP_SEQ_NBR
into rdb.dbo.tmp_DynDM_METADATA_OUT_final 
FROM rdb.dbo.tmp_DynDM_METADATA_OUT mo  with (nolock)
INNER JOIN rdb.dbo.tmp_DynDM_METADATA_OUT1  mo1  with (nolock) ON  mo1.RDB_COLUMN_NM = mo.RDB_COLUMN_NM;



--DX select * from rdb.dbo.tmp_DynDM_METADATA_OUT_final;






/*

PROC SQL;	
	CREATE TABLE STD_TESTER(COUNTSTD NUM);
	INSERT INTO STD_TESTER( COUNTSTD) VALUES(NULL);
	UPDATE STD_TESTER SET COUNTSTD= (select  count(*) from CASE_MANAGEMENT_METADATA);
QUIT;

DATA _NULL_;
SET STD_TESTER;

  IF COUNTSTD>1 then call symputx('FACT_CASE', 'F_STD_PAGE_CASE'); 
  IF COUNTSTD<2 then call symputx('FACT_CASE', 'F_PAGE_CASE'); 
  RUN;
*/

declare @countstd int = 0;


	   select  @COUNTSTD = count(*) 
	    from rdb.dbo.tmp_DynDM_CASE_MANAGEMENT_METADATA
        ;


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

 -- select @fact_case;


 	
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_D_INV_REPEAT_METADATA';
	


					  IF OBJECT_ID('rdb.dbo.tmp_DynDM_D_INV_REPEAT_METADATA', 'U') IS NOT NULL   
 									drop table rdb.dbo.tmp_DynDM_D_INV_REPEAT_METADATA;



					--	CREATE TABLE D_INV_REPEAT_METADATA AS 
					SELECT DISTINCT DATAMART_NM, RDB_COLUMN_NM, USER_DEFINED_COLUMN_NM,BLOCK_PIVOT_NBR
					into rdb.dbo.tmp_DynDM_D_INV_REPEAT_METADATA
						FROM rdb.dbo.tmp_DynDM_Metadata  with (nolock);



--Dx select * from  rdb.dbo.tmp_DynDM_D_INV_REPEAT_METADATA;


/*                                                      

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
	  */
	  	
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  db_cursor CURSOR ';
	


						-- Initialize variables
						DECLARE @RDB_COLUMN_NAME_LIST NVARCHAR(4000) = '';
						DECLARE @RDB_COLUMN_LIST NVARCHAR(4000) = '';
						DECLARE @RDB_COLUMN_COMMA_LIST NVARCHAR(4000) = '';

						-- Loop through the source table
						DECLARE @SORT_KEY INT;
						DECLARE @DATAMART_NM NVARCHAR(4000);
						DECLARE @RDB_COLUMN_NM NVARCHAR(4000);
						DECLARE @USER_DEFINED_COLUMN_NM NVARCHAR(4000);
						DECLARE @USER_DEFINED_COLUMN_NAME NVARCHAR(4000);
						DECLARE @RDB_COLUMN_NAME NVARCHAR(4000);

						DECLARE db_cursor CURSOR FOR
						SELECT  DATAMART_NM, RDB_COLUMN_NM, USER_DEFINED_COLUMN_NM
						FROM rdb.dbo.tmp_DynDM_D_INV_REPEAT_METADATA with (nolock) 
						ORDER BY RDB_COLUMN_NM ;

						OPEN db_cursor;
						FETCH NEXT FROM db_cursor INTO  @DATAMART_NM, @RDB_COLUMN_NM, @USER_DEFINED_COLUMN_NM;

						WHILE @@FETCH_STATUS = 0
						BEGIN
							-- Check conditions and build column lists
							IF  @DATAMART_NM IS NOT NULL
							BEGIN
								SET @USER_DEFINED_COLUMN_NAME = QUOTENAME(@RDB_COLUMN_NM) + ' AS ' + QUOTENAME(@USER_DEFINED_COLUMN_NM);
								SET @RDB_COLUMN_NAME = QUOTENAME(@RDB_COLUMN_NM);
        
								IF LEN(@USER_DEFINED_COLUMN_NAME) > 0
									SET @RDB_COLUMN_NAME_LIST = @USER_DEFINED_COLUMN_NAME + ', ' + @RDB_COLUMN_NAME_LIST;
        
								IF LEN(@RDB_COLUMN_NAME) > 0
								BEGIN
									SET @RDB_COLUMN_LIST = @RDB_COLUMN_NAME + ' ' + @RDB_COLUMN_LIST;
									SET @RDB_COLUMN_COMMA_LIST = @RDB_COLUMN_NAME + ', ' + @RDB_COLUMN_COMMA_LIST;
								END
							END
    

							--  Select @DATAMART_NM, @RDB_COLUMN_NM, @USER_DEFINED_COLUMN_NM,@RDB_COLUMN_COMMA_LIST;


							FETCH NEXT FROM db_cursor INTO @DATAMART_NM, @RDB_COLUMN_NM, @USER_DEFINED_COLUMN_NM;
						END

						---- Insert the results into the temporary table
						--INSERT INTO #DYNINVLISTING (RDB_COLUMN_NAME_LIST, RDB_COLUMN_LIST, RDB_COLUMN_COMMA_LIST)
						--VALUES (@RDB_COLUMN_NAME_LIST, @RDB_COLUMN_LIST, @RDB_COLUMN_COMMA_LIST);

						-- Clean up
						CLOSE db_cursor;
						DEALLOCATE db_cursor;

						--DX SELECT @RDB_COLUMN_LIST,@RDB_COLUMN_COMMA_LIST,@RDB_COLUMN_NAME_LIST;


							  /*


						DROP datamart_nm USER_DEFINED_COLUMN_NAME form_cd; 
						RUN;                                                                                                                                                       

						PROC SQL;                                                                                                                                                  
						DELETE FROM DYNINVLISTING WHERE SORT_KEY <(SELECT MAX (SORT_KEY) FROM DYNINVLISTING);                                                                      
						QUIT;
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
							RDB_COLUMN_NAME_LIST = TRIM(RDB_COLUMN_NAME_LIST);
							IF (LENGTH>1)  THEN TRIMMED_VALUE=SUBSTR((RDB_COLUMN_NAME_LIST), 1, LENGTHN(RDB_COLUMN_NAME_LIST)-1);
							CALL SYMPUTX('D_REPEAT_CASE', TRIMMED_VALUE);
							CALL SYMPUTX('D_REPEAT_CASE_NAME', RDB_COLUMN_LIST);
							CALL SYMPUTX('D_REPEAT_COMMA_NAME', RDB_COLUMN_COMMA_LIST);
	
						RUN;
						%put _user_;
						PROC SQL;

						*/


							
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  RDB_COLUMN_NAME_LIST';
	


						DECLARE @D_REPEAT_CASE NVARCHAR(4000) = '';
						DECLARE @D_REPEAT_CASE_NAME NVARCHAR(4000) = '';
						DECLARE @D_REPEAT_COMMA_NAME NVARCHAR(4000) = '';
						DECLARE @D_REPEAT_COMMA_NAME1 NVARCHAR(4000) = '';



						if LEN(@RDB_COLUMN_NAME_LIST) < 1 
						begin
						 SET @RDB_COLUMN_NAME_LIST = ' '
						 SET @RDB_COLUMN_LIST = ' '
						 SET @RDB_COLUMN_COMMA_LIST = ' '
						end


						SET  @D_REPEAT_CASE = LEFT(@RDB_COLUMN_NAME_LIST, LEN(@RDB_COLUMN_NAME_LIST) - 1);

						SET  @D_REPEAT_CASE_NAME = @RDB_COLUMN_LIST;

						SET  @D_REPEAT_COMMA_NAME = @RDB_COLUMN_COMMA_LIST;

						SET  @D_REPEAT_COMMA_NAME1 = LEFT(@RDB_COLUMN_COMMA_LIST, LEN(@RDB_COLUMN_COMMA_LIST) - 1);




						/*
						%IF %SYSFUNC(EXIST(rdb.dbo.D_INVESTIGATION_REPEAT)) %THEN %DO;
							CREATE TABLE REPEAT_BLOCK AS 
							SELECT  &D_REPEAT_COMMA_NAME ANSWER_GROUP_SEQ_NBR, D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_KEY, SUMM_DATAMART.INVESTIGATION_KEY, D_INVESTIGATION_REPEAT.BLOCK_NM 
							FROM rdb.dbo.SUMM_DATAMART
							INNER JOIN  NBS_RDB .&FACT_CASE ON	SUMM_DATAMART.INVESTIGATION_KEY  = @FACT_CASE .INVESTIGATION_KEY
							INNER JOIN  rdb.dbo.D_INVESTIGATION_REPEAT ON	&FACT_CASE .D_INVESTIGATION_REPEAT_KEY  =D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_KEY
						WHERE D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_KEY>1;
						%ELSE %DO;     
							CREATE TABLE REPEAT_BLOCK AS 
							SELECT  SUMM_DATAMART.INVESTIGATION_KEY  FROM rdb.dbo.SUMM_DATAMART;
							%END;
						;
						QUIT;
						*/

						declare @SQL varchar(8000)
							
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_REPEAT_BLOCK';
	


						  IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK', 'U') IS NOT NULL   
 										drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK;




						if  object_id('rdb.dbo.D_INVESTIGATION_REPEAT') is not null
						  Begin

							 SET @SQL = '   SELECT '+@D_REPEAT_COMMA_NAME + ' ANSWER_GROUP_SEQ_NBR, D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_KEY, tmp_DynDM_SUMM_DATAMART.INVESTIGATION_KEY, D_INVESTIGATION_REPEAT.BLOCK_NM ' +
										'    into RDB.dbo.tmp_DynDM_REPEAT_BLOCK' +
										'    FROM rdb.dbo.tmp_DynDM_SUMM_DATAMART  with (nolock)'+
										'		INNER JOIN  rdb.dbo.'+@FACT_CASE +'   with (nolock)  ON tmp_DynDM_SUMM_DATAMART.INVESTIGATION_KEY  = '+@FACT_CASE+'.INVESTIGATION_KEY '+
										'		INNER JOIN  rdb.dbo.D_INVESTIGATION_REPEAT'+'  with (nolock) ON	'+@FACT_CASE+'.'+'D_INVESTIGATION_REPEAT_KEY  = D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_KEY ' +
										'	  WHERE D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_KEY>1 '
										;

						-- select 1,@SQL;

							 EXEC (@SQL);
						  end
						else
						  Begin

							 SET @SQL = '   SELECT tmp_DynDM_SUMM_DATAMART.INVESTIGATION_KEY ' +
										'    into RDB.dbo.tmp_DynDM_REPEAT_BLOCK' +
										'    FROM rdb.dbo.tmp_DynDM_SUMM_DATAMART '
										;

							 -- select 2,@SQL;

							 EXEC (@SQL);
						  end
						 ;


						 --select * from RDB.dbo.tmp_DynDM_REPEAT_BLOCK;
						 --select '@D_REPEAT_COMMA_NAME1',@D_REPEAT_COMMA_NAME1;


						/*
						 9
						PROC SQL;
						DELETE FROM REPEAT_BLOCK WHERE (SELECT COUNT(*) FROM DYNINVLISTING) =0;
						QUIT;
						PROC SORT DATA=REPEAT_BLOCK ; BY INVESTIGATION_KEY BLOCK_NM ANSWER_GROUP_SEQ_NBR;RUN;
						*/


						/*



						PROC TRANSPOSE DATA=REPEAT_BLOCK OUT=REPEAT_BLOCK_OUT;
							VAR VAR &D_REPEAT_CASE_NAME;
							BY INVESTIGATION_KEY BLOCK_NM ANSWER_GROUP_SEQ_NBR;
						RUN;
						*/


						-- DECLARE @D_REPEAT_COMMA_NAME1 NVARCHAR(4000) = '';
							
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_REPEAT_BLOCK_OUT';
	


						  IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT', 'U') IS NOT NULL   
 										drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT;


										   declare @columns varchar(8000);

											select @columns = @RDB_COLUMN_COMMA_LIST;

											--select '@columns_1', @columns;

									   			
												SET @sql = N' select INVESTIGATION_KEY ,BLOCK_NM as BLOCK_NM_BLOCK_OUT ,ANSWER_GROUP_SEQ_NBR,variable as RDB_COLUMN_NM_BLOCK_OUT,value as dateColumn '
														  + ' into rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT '
														  + ' from ( '
														  + ' select INVESTIGATION_KEY ,BLOCK_NM ,ANSWER_GROUP_SEQ_NBR,  ' + @D_REPEAT_COMMA_NAME1
														  + ' from  rdb.dbo.tmp_DynDM_REPEAT_BLOCK  with (nolock)'
														  + ' ) as t '
														  + ' unpivot ( '
														  + ' value for variable in ( ' + @D_REPEAT_COMMA_NAME1 + ') '
														  + ' ) as unpvt '

														 ; 


											--DX	select  '@sql tmp_DynDM_REPEAT_BLOCK_OUT',@sql;


												EXEC ( @sql);




						--DX select 'tmp_DynDM_REPEAT_BLOCK_OUT',* from rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT;



						--/*
						----- VS

						--DATA REPEAT_BLOCK_OUT;
						--set REPEAT_BLOCK_OUT;
						--RDB_COLUMN_NM = _NAME_;
						--coloumnText=ANSWER_TXT; 
						--rename col1=dateColumn;
						--RUN;

						--*/

						--select * from rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT;




						----CREATE TABLE BLOCK_DATA AS	
						---- DECLARE  @batch_id BIGINT = 999;  DECLARE  @DATAMART_NAME VARCHAR(100) = 'STD'; 
							
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_BLOCK_DATA';
	


						  IF OBJECT_ID('rdb.dbo.tmp_DynDM_BLOCK_DATA', 'U') IS NOT NULL   
 										drop table rdb.dbo.tmp_DynDM_BLOCK_DATA;




						SELECT BLOCK_NM, 
						RDB_COLUMN_NM  
						into rdb.dbo.tmp_DynDM_BLOCK_DATA
						FROM NBS_ODSE..NBS_RDB_METADATA  with (nolock)
						INNER JOIN NBS_ODSE..NBS_UI_METADATA  with (nolock) ON  NBS_RDB_METADATA.NBS_UI_METADATA_UID =NBS_UI_METADATA.NBS_UI_METADATA_UID 
						WHERE RDB_TABLE_NM='D_INVESTIGATION_REPEAT'
							AND NBS_UI_METADATA.INVESTIGATION_FORM_CD=(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM= @DATAMART_NAME)
							AND PART_TYPE_CD IS NULL
							AND QUESTION_GROUP_SEQ_NBR IS NOT NULL 
							AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM != '' and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
							AND NBS_RDB_METADATA.BLOCK_PIVOT_NBR IS NOT NULL
							and data_type in ( 'DATETIME','DATE', 'Date','Date/Time') and code_set_group_id is null 
							ORDER BY RDB_COLUMN_NM, BLOCK_NM;
						;



						--DX select 'tmp_DynDM_BLOCK_DATA',* from rdb.dbo.tmp_DynDM_BLOCK_DATA;





						--DX select 'tmp_DynDM_REPEAT_BLOCK_OUT',* from rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT;
						--DX select 'tmp_DynDM_BLOCK_DATA',* from rdb.dbo.tmp_DynDM_BLOCK_DATA;
							
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_REPEAT_BLOCK_OUT_BASE';
	


						  IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE', 'U') IS NOT NULL   
 										drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE;




						----CREATE TABLE REPEAT_BLOCK_OUT_BASE AS 
						SELECT DISTINCT * 
						into  rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE
						FROM rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT rbo
							INNER JOIN rdb.dbo.tmp_DynDM_BLOCK_DATA bd with (nolock) ON 	rbo.BLOCK_NM_BLOCK_OUT =bd.BLOCK_NM
								   AND UPPER(rbo.RDB_COLUMN_NM_BLOCK_OUT)= UPPER(bd.RDB_COLUMN_NM);
	

 							--DX select 'tmp_DynDM_REPEAT_BLOCK_OUT_BASE',* from rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE;


						/*
						PROC DATASETS LIBRARY = WORK NOLIST;
							DELETE 
							BLOCK_DATA REPEAT_BLOCK_OUT RUN;
						QUIT;

						PROC SORT DATA=REPEAT_BLOCK_OUT_BASE NODUPKEY ; BY INVESTIGATION_KEY RDB_COLUMN_NM BLOCK_NM ANSWER_GROUP_SEQ_NBR ;RUN;
						*/



						/*
						DATA REPEAT_BLOCK_OUT_ALL;
						SET REPEAT_BLOCK_OUT_BASE;
							BY INVESTIGATION_KEY RDB_COLUMN_NM BLOCK_NM ANSWER_GROUP_SEQ_NBR;
	
							FORMAT ANSWER_DESCCHECK $10.;
							FORMAT ANSWER_DESC1-ANSWER_DESC20 $100.;
							LENGTH ANSWER_DESC21 $8000;
	
							ARRAY ANSWER_DESC(20) ANSWER_DESC1-ANSWER_DESC20;
							RETAIN  ANSWER_DESC1-ANSWER_DESC21 ' ' I 0;
							IF FIRST.RDB_COLUMN_NM  THEN DO;
								DO J=1 TO 20; ANSWER_DESC(J) = '';	
								END;
								I = 0; ANSWER_DESC21 = ''; 
								ANSWER_DESCCHECK='';
							END;
							I+1;
							X=LENGTHN(ANSWER_DESC21);
							IF I <= 20 THEN DO;
								TEXTDATE =  PUT(dateColumn, yymmddd10.); 
								ANSWER_DESC(I) = TEXTDATE;
								IF LENGTHN(TEXTDATE)< 5 THEN TEXTDATE='';
								IF LENGTHN(TEXTDATE)>4 AND LENGTHN(ANSWER_DESCCHECK)<2 THEN ANSWER_DESCCHECK ='TRUE';
								IF I = 1 THEN ANSWER_DESC21 =  LEFT(TRIM(TEXTDATE));
								ELSE ANSWER_DESC21 =LEFT(TRIM(ANSWER_DESC21))|| ' ~ '|| LEFT(TRIM(TEXTDATE));
							END;	
							IF LAST.RDB_COLUMN_NM THEN OUTPUT; 
							IF  LENGTHN(COMPRESS(ANSWER_DESCCHECK))<3 THEN ANSWER_DESC21='';
							DROP ANSWER_DESC1-ANSWER_DESC20  _NAME_ _LABEL_ ANSWER_DESCCHECK I J;
						RUN;
						*/
							
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_REPEAT_BLOCK_OUT_ALL';
	

						  IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL', 'U') IS NOT NULL   
 										drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL;


						 SELECT
							 INVESTIGATION_KEY,RDB_COLUMN_NM_BLOCK_OUT, BLOCK_NM_BLOCK_OUT,ANSWER_GROUP_SEQ_NBR,
							 cast(STUFF(
								 (select(SELECT   rtrim(' ~'+ coalesce(' '+rtrim(ltrim(dateColumn)) ,'.'))
									 FROM  rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE with (nolock)
								  where INVESTIGATION_KEY = a.INVESTIGATION_KEY 
								  AND RDB_COLUMN_NM_BLOCK_OUT= a.RDB_COLUMN_NM_BLOCK_OUT
								  AND  BLOCK_NM_BLOCK_OUT = a.BLOCK_NM_BLOCK_OUT 
								  AND  ANSWER_GROUP_SEQ_NBR = a.ANSWER_GROUP_SEQ_NBR
 								  order by INVESTIGATION_KEY,RDB_COLUMN_NM_BLOCK_OUT, BLOCK_NM_BLOCK_OUT, ANSWER_GROUP_SEQ_NBR
								 FOR XML PATH (''),TYPE).value('.','varchar(8000)'))
								  , 1, 1, '')  as varchar(8000)) AS  ANSWER_DESC21
						into rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL
						FROM rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE AS a with (nolock)
						group  BY INVESTIGATION_KEY,RDB_COLUMN_NM_BLOCK_OUT, BLOCK_NM_BLOCK_OUT,ANSWER_GROUP_SEQ_NBR

						--having count(*) > 1
						;


						update rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL
						set ANSWER_DESC21 = substring(ANSWER_DESC21,3, len(ANSWER_DESC21));

						 --DX select 'tmp_DynDM_REPEAT_BLOCK_OUT_ALL',* from rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL;


						/*
						DATA REPEAT_BLOCK_OUT_BASE ;
						SET REPEAT_BLOCK_OUT_BASE ;

						E=dateColumn;
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


						*/

							
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_METADATA_OUT1';
	

						  IF OBJECT_ID('rdb.dbo.tmp_DynDM_METADATA_OUT1', 'U') IS NOT NULL   
 										drop table rdb.dbo.tmp_DynDM_METADATA_OUT1;


						----CREATE TABLE METADATA_OUT as 
						select RDB_COLUMN_NM, _NAME_, COL1, BLOCK_NM, ANSWER_GROUP_SEQ_NBR
						into rdb.dbo.tmp_DynDM_METADATA_OUT1
						FROM rdb.dbo.tmp_DynDM_METADATA_OUT_final  with (nolock);


						--DX select 'tmp_DynDM_METADATA_OUT1',* from rdb.dbo.tmp_DynDM_METADATA_OUT1;
						--DX select 'tmp_DynDM_REPEAT_BLOCK_OUT_BASE',* from tmp_DynDM_REPEAT_BLOCK_OUT_BASE;

							
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_METADATA_MERGED_INIT';
	


						  IF OBJECT_ID('rdb.dbo.tmp_DynDM_METADATA_MERGED_INIT', 'U') IS NOT NULL   
 										drop table rdb.dbo.tmp_DynDM_METADATA_MERGED_INIT;


						--CREATE TABLE METADATA_MERGED_INIT AS 
						SELECT distinct dmo.* , drbob.dateColumn, drbob.investigation_key
						into rdb.dbo.tmp_DynDM_METADATA_MERGED_INIT
						FROM rdb.dbo.tmp_DynDM_METADATA_OUT1 dmo with (nolock)
						LEFT OUTER JOIN rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE  drbob  with (nolock) ON
						UPPER(dmo.RDB_COLUMN_NM)  = UPPER(drbob.RDB_COLUMN_NM)
						AND dmo.ANSWER_GROUP_SEQ_NBR =drbob.ANSWER_GROUP_SEQ_NBR
						AND dmo.BLOCK_NM = drbob.BLOCK_NM 
						--where drbob.investigation_key is not null
						;


						--DX select 'tmp_DynDM_METADATA_MERGED_INIT',* from rdb.dbo.tmp_DynDM_METADATA_MERGED_INIT ;


						/*
						PROC SORT DATA=METADATA_MERGED_INIT ; BY INVESTIGATION_KEY;RUN;

						PROC TRANSPOSE DATA=METADATA_MERGED_INIT OUT=INVESTIGATION_REPEAT_DATE;
							VAR dateColumn;
							ID COL1;
							BY INVESTIGATION_KEY;
						RUN;

						DATA INVESTIGATION_REPEAT_DATE;
						SET INVESTIGATION_REPEAT_DATE;
						DROP _NAME_;
						RUN;
						*/

						
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	


	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_INVESTIGATION_REPEAT_DATE';
	


									--DECLARE @sql NVARCHAR(4000)   , @columns NVARCHAR(4000);
						
									IF OBJECT_ID('rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_DATE', 'U') IS NOT NULL 
														drop table rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_DATE;


						

												SET @columns = N'';

												SELECT @columns+=N', p.'+QUOTENAME(LTRIM(RTRIM([COL1])))
												FROM
												(
													SELECT [COL1] 
													FROM rdb.[dbo].tmp_DynDM_METADATA_MERGED_INIT AS p with (nolock)
													GROUP BY [COL1]
												) AS x;


												SET @sql = N'
												SELECT [INVESTIGATION_KEY] , '+STUFF(@columns, 1, 2, '')+
												' into rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_DATE ' +
												'FROM (
												SELECT [INVESTIGATION_KEY], [dateColumn] , [COL1] 
												 FROM rdb.[dbo].tmp_DynDM_METADATA_MERGED_INIT  with (nolock)
													group by [INVESTIGATION_KEY], [dateColumn] , [COL1] 
														) AS j PIVOT (max(dateColumn) FOR [COL1] in 
													   ('+STUFF(REPLACE(@columns, ', p.[', ',['), 1, 1, '')+')) AS p;';

												print @sql;
												EXEC  (@sql);

												IF OBJECT_ID('rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_DATE', 'U') IS  NULL 
																	 CREATE TABLE rdb.[dbo].tmp_DynDM_INVESTIGATION_REPEAT_DATE([INVESTIGATION_KEY] [bigint] NULL)	;


			


												delete from  rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_DATE where investigation_key is null;

								--DX select 'tmp_DynDM_INVESTIGATION_REPEAT_DATE',* from rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_DATE;




						/*


						PROC SQL;
						CREATE TABLE REPEAT_ALL AS SELECT DISTINCT RDB_COLUMN_NM, USER_DEFINED_COLUMN_NM  FROM D_INV_REPEAT_METADATA;
						QUIT;

						DATA REPEAT_ALL;
						SET REPEAT_ALL;
						LENGTH USER_DEFINED_COLUMN_NM_ALL $4000;
						USER_DEFINED_COLUMN_NM_ALL = COMPRESS(USER_DEFINED_COLUMN_NM || '_ALL');
						DROP USER_DEFINED_COLUMN_NM;
						RUN;
						*/

						--CREATE TABLE REPEAT_ALL AS 

	
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_REPEAT_ALL';
	

						
						IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_ALL', 'U') IS NOT NULL 
														drop table rdb.dbo.tmp_DynDM_REPEAT_ALL;

						SELECT DISTINCT RDB_COLUMN_NM, USER_DEFINED_COLUMN_NM+'_ALL' as USER_DEFINED_COLUMN_NM_ALL
						into rdb.dbo.tmp_DynDM_REPEAT_ALL
						FROM rdb.dbo.tmp_DynDM_D_INV_REPEAT_METADATA  with (nolock);



						--DX select 'tmp_DynDM_REPEAT_ALL',* from rdb.dbo.tmp_DynDM_REPEAT_ALL;

						--DX select 'tmp_DynDM_REPEAT_BLOCK_OUT_ALL',* from rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL;

	
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  insert tmp_DynDM_REPEAT_BLOCK_OUT_ALL ';
	


								with t1 as (select   INVESTIGATION_KEY, RDB_COLUMN_NM_BLOCK_OUT, BLOCK_NM_BLOCK_OUT,max(ANSWER_GROUP_SEQ_NBR) maxANSWER_GROUP_SEQ_NBR, min(ANSWER_GROUP_SEQ_NBR) minANSWER_GROUP_SEQ_NBR 
											from  rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL  
											group by   INVESTIGATION_KEY, RDB_COLUMN_NM_BLOCK_OUT, BLOCK_NM_BLOCK_OUT)
								,x as (select INVESTIGATION_KEY, RDB_COLUMN_NM_BLOCK_OUT, BLOCK_NM_BLOCK_OUT, minANSWER_GROUP_SEQ_NBR, maxANSWER_GROUP_SEQ_NBR from t1
									   union all
									   select INVESTIGATION_KEY, RDB_COLUMN_NM_BLOCK_OUT, BLOCK_NM_BLOCK_OUT, minANSWER_GROUP_SEQ_NBR+1, maxANSWER_GROUP_SEQ_NBR 
									   from x 
									   where minANSWER_GROUP_SEQ_NBR < maxANSWER_GROUP_SEQ_NBR
									   )
								insert into rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL  ([INVESTIGATION_KEY]
										   ,[RDB_COLUMN_NM_BLOCK_OUT]
										   ,[BLOCK_NM_BLOCK_OUT]
										   ,[ANSWER_GROUP_SEQ_NBR]
										   ,[ANSWER_DESC21])
								select x.INVESTIGATION_KEY, x.RDB_COLUMN_NM_BLOCK_OUT, x.BLOCK_NM_BLOCK_OUT,x.minANSWER_GROUP_SEQ_NBR as missing_ANSWER_GROUP_SEQ_NBR ,null
								from x  
								left join rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL  t
										   on    t.INVESTIGATION_KEY=x.INVESTIGATION_KEY 
											 and t.RDB_COLUMN_NM_BLOCK_OUT=x.RDB_COLUMN_NM_BLOCK_OUT 
											 and t.BLOCK_NM_BLOCK_OUT = x.BLOCK_NM_BLOCK_OUT 
											 and t.ANSWER_GROUP_SEQ_NBR = x.minANSWER_GROUP_SEQ_NBR
								where t.ANSWER_GROUP_SEQ_NBR is null 
								order by 1,2,3,4
								;


						--DX select 'tmp_DynDM_REPEAT_BLOCK_OUT_ALL - 2 ',* from rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL;

	
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	

    	BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max';
	

						
						IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max', 'U') IS NOT NULL 
														drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max;



	
									select 'tmp_DynDM_REPEAT_BLOCK_OUT_BASE_1' as tb ,INVESTIGATION_KEY,BLOCK_NM_BLOCK_OUT,max(ANSWER_GROUP_SEQ_NBR) as block_max
									into rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max
									from rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE
									group by  investigation_key,BLOCK_NM_BLOCK_OUT
									;

	
	
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max_1';
	

						
						IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max_1', 'U') IS NOT NULL 
														drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max_1;




						select 'tmp_DynDM_REPEAT_BLOCK_OUT_BASE_1' as tb ,rb.INVESTIGATION_KEY,rb.BLOCK_NM_BLOCK_OUT,rb.RDB_COLUMN_NM,
						max(ANSWER_GROUP_SEQ_NBR) as block_clock_max,block_max,block_max-max(ANSWER_GROUP_SEQ_NBR) as block_insert_count, cast(null as varchar(30)) as block_pad
						into rdb..tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max_1
						from rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_BASE rb
						inner join tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max rbx on rbx.BLOCK_NM_BLOCK_OUT = rb.BLOCK_NM_BLOCK_OUT and rbx.investigation_key = rb.INVESTIGATION_KEY
						--where  rb.INVESTIGATION_KEY = 146
						group by  rb.investigation_key,rb.BLOCK_NM_BLOCK_OUT,rb.RDB_COLUMN_NM,block_max
						;


						delete 
						from rdb..tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max_1
						where block_clock_max = block_max
						;


						--update rdb..tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max_1 set block_insert_count = 3;


						 update rdb..tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max_1 set block_pad = substring(substring(replicate('~.',block_insert_count) ,2,len(replicate('~.',block_insert_count))),1,30)	;

						insert into rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL
						select investigation_key,rdb_column_nm,block_nm_block_out,block_max,block_pad
						from rdb..tmp_DynDM_REPEAT_BLOCK_OUT_BASE_max_1
						;

	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_REPEAT_BLOCK_METADATA_OUT';
	

						
						IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_METADATA_OUT', 'U') IS NOT NULL 
														drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_METADATA_OUT;


						--CREATE TABLE REPEAT_BLOCK_METADATA_OUT AS 
						SELECT rba.*, ra.USER_DEFINED_COLUMN_NM_ALL 
						into rdb.dbo.tmp_DynDM_REPEAT_BLOCK_METADATA_OUT
						FROM rdb.dbo.tmp_DynDM_REPEAT_ALL ra with (nolock)
						LEFT OUTER JOIN rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT_ALL rba  with (nolock)
						ON UPPER(ra.RDB_COLUMN_NM) = UPPER(rba.RDB_COLUMN_NM_BLOCK_OUT);


						--DX select 'tmp_DynDM_REPEAT_BLOCK_METADATA_OUT',* from rdb.dbo.tmp_DynDM_REPEAT_BLOCK_METADATA_OUT    ;


						/*

						 PROC SORT DATA=REPEAT_BLOCK_METADATA_OUT ; BY INVESTIGATION_KEY ;RUN;

						PROC TRANSPOSE DATA=REPEAT_BLOCK_METADATA_OUT OUT=REPEAT_BLOCK_DATE_ALL;
							VAR ANSWER_DESC21;
							ID USER_DEFINED_COLUMN_NM_ALL;
							BY INVESTIGATION_KEY ;
						RUN;

						DATA REPEAT_BLOCK_DATE_ALL;
						SET REPEAT_BLOCK_DATE_ALL;
						DROP _NAME_;
						RUN;
						%MEND REPEATDATEDATA;
						*/


	
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_REPEAT_BLOCK_METADATA_OUT_FINAL';
	

					
												   IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_METADATA_OUT_FINAL', 'U') IS NOT NULL 
														drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_METADATA_OUT_FINAL;

													SELECT
														 [INVESTIGATION_KEY], [USER_DEFINED_COLUMN_NM_ALL],
														 STUFF(
															 (SELECT  ' ~ ' + coalesce([ANSWER_DESC21],'.')
															  FROM rdb.[dbo].tmp_DynDM_REPEAT_BLOCK_METADATA_OUT with (nolock)
															  WHERE [INVESTIGATION_KEY] = a.[INVESTIGATION_KEY] AND [USER_DEFINED_COLUMN_NM_ALL] = a.[USER_DEFINED_COLUMN_NM_ALL]
															  order by [INVESTIGATION_KEY], [USER_DEFINED_COLUMN_NM_ALL],answer_group_seq_nbr
												  FOR XML PATH (''))
															  , 1, 3, '')  AS ANSWER_DESC21
													into rdb.dbo.tmp_DynDM_REPEAT_BLOCK_METADATA_OUT_FINAL
													FROM  rdb.[dbo].tmp_DynDM_REPEAT_BLOCK_METADATA_OUT  AS a
													GROUP BY [INVESTIGATION_KEY], [USER_DEFINED_COLUMN_NM_ALL]
													;



						--DX							select 'rdb.dbo.tmp_DynDM_REPEAT_BLOCK_METADATA_OUT_FINAL',* from rdb.dbo.tmp_DynDM_REPEAT_BLOCK_METADATA_OUT_FINAL   ;

	
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_REPEAT_BLOCK_DATE_ALL';
	
						
									IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_DATE_ALL', 'U') IS NOT NULL 
														drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_DATE_ALL;


							--DECLARE @columns NVARCHAR(4000);
							--DECLARE @sql NVARCHAR(4000);

											SET @columns = N'';

												SELECT @columns+=N', p.'+QUOTENAME(LTRIM(RTRIM([USER_DEFINED_COLUMN_NM_ALL])))
												FROM
												(
													SELECT [USER_DEFINED_COLUMN_NM_ALL] 
													FROM rdb.[dbo].tmp_DynDM_REPEAT_BLOCK_METADATA_OUT_FINAL AS p
													GROUP BY [USER_DEFINED_COLUMN_NM_ALL]
												) AS x;


												SET @sql = N'
												SELECT [INVESTIGATION_KEY]  , '+STUFF(@columns, 1, 2, '')+
												' into rdb.dbo.tmp_DynDM_REPEAT_BLOCK_DATE_ALL ' +
												'FROM (
												SELECT [INVESTIGATION_KEY], [ANSWER_DESC21] , [USER_DEFINED_COLUMN_NM_ALL] 
												 FROM rdb.[dbo].tmp_DynDM_REPEAT_BLOCK_METADATA_OUT_FINAL with (nolock)
													group by [INVESTIGATION_KEY], [ANSWER_DESC21] , [USER_DEFINED_COLUMN_NM_ALL] 
														) AS j PIVOT (max(ANSWER_DESC21) FOR [USER_DEFINED_COLUMN_NM_ALL] in 
													   ('+STUFF(REPLACE(@columns, ', p.[', ',['), 1, 1, '')+')) AS p;';

												print @sql;
												EXEC ( @sql);
						

						
						
									IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_DATE_ALL', 'U') IS  NULL
						                         			CREATE TABLE rdb.[dbo].tmp_DynDM_REPEAT_BLOCK_DATE_ALL(
																	[INVESTIGATION_KEY] [bigint] NULL
																) ;



												--select 'tmp_DynDM_REPEAT_BLOCK_DATE_ALL',* from rdb.dbo.tmp_DynDM_REPEAT_BLOCK_DATE_ALL;


	
	 
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

   
                INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
                 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_REPEATDATEDATA_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
			COMMIT TRANSACTION;
	
	
			BEGIN TRANSACTION;
	
				SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'GENERATING  tmp_DynDM_REPEAT_BLOCK_OUT';
	


						
						  IF OBJECT_ID('rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT', 'U') IS NOT NULL   
 										drop table rdb.dbo.tmp_DynDM_REPEAT_BLOCK_OUT;

						
			 COMMIT TRANSACTION ;

			 BEGIN TRANSACTION;

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
												   ,'RDB.DBO.DynDM_REPEATDATEDATA_sp ' + @DATAMART_NAME
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
						VALUES( @Batch_id, 'DYNAMIC_DATAMART', 'RDB.DBO.DynDM_REPEATDATEDATA_sp : ' + @DATAMART_NAME, 'ERROR', @Proc_Step_no, 'ERROR - '+@Proc_Step_name, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );
						RETURN -1;
	        END CATCH;
END;

GO



--SELECT * 
--FROM  rdb.dbo.tmp_DynDM_INVESTIGATION_REPEAT_VARCHAR irv 
--LEFT JOIN rdb.dbo.tmp_DynDM_REPEAT_BLOCK_VARCHAR_ALL rbva ON rbva.INVESTIGATION_KEY =irv.INVESTIGATION_KEY


