USE RDB;
go
 

IF OBJECT_ID('rdb.dbo.DynDM_Manage_Case_Management_sp', 'P') IS NOT NULL
				DROP PROCEDURE dbo.DynDM_Manage_Case_Management_sp;

GO


 
CREATE PROCEDURE [dbo].DynDM_Manage_Case_Management_sp
            @batch_id BIGINT,
			@DATAMART_NAME VARCHAR(100)
	AS
BEGIN  
	 BEGIN TRY
	
	    --	DECLARE  @batch_id BIGINT = 999;  DECLARE  @DATAMART_NAME VARCHAR(100) = 'TB_LTBI_GA'; 
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
           ,'RDB.DBO.DynDM_Manage_Case_Management ' + @DATAMART_NAME
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
     
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  LIST STRING';
		


		

/*Creating the list in the rdb_column_name_list with all the user defined column name*/

/*
DATA CMLISTING;
  SET CASE_MANAGEMENT_METADATA; 
	BY SORT_KEY;

	LENGTH RDB_COLUMN_NAME_LIST &MAX_COLUMN_CHARACTER_LIMIT; 
	RETAIN RDB_COLUMN_NAME_LIST;
	IF FIRST.SORT_KEY AND NOT MISSING(DATAMART_NM) THEN 
	USER_DEFINED_COLUMN_NAME = COMPRESS(RDB_COLUMN_NM) || ' AS ' ||COMPRESS(USER_DEFINED_COLUMN_NM)|| " '"||COMPRESS(USER_DEFINED_COLUMN_NM)||"'";
	IF (LENGTHN(USER_DEFINED_COLUMN_NAME))>0 THEN  RDB_COLUMN_NAME_LIST = (USER_DEFINED_COLUMN_NAME || ', ' || RDB_COLUMN_NAME_LIST);  
	OUTPUT;
RUN;

 
DELETE FROM CMLISTING WHERE SORT_KEY <(SELECT MAX (SORT_KEY) FROM CMLISTING);


*/


--DECLARE @DATAMART_NAME varchar(100) = 'CONG_SYPHILIS';

--DX select * from  rdb.dbo.tmp_DynDm_Case_Management_Metadata;



DECLARE @listStr VARCHAR(MAX) = null;

SELECT @listStr = COALESCE(@listStr+',' ,'') +  RDB_COLUMN_NM  + ' '+ coalesce(USER_DEFINED_COLUMN_NM,'')
FROM  rdb.dbo.tmp_DynDm_Case_Management_Metadata with (nolock);


--DX SELECT @listStr


/*

/*if there's nothing on the list, we get the investigation_key at the minimum, else, we get the list.*/
DATA CMLISTING;
SET CMLISTING;
		CALL SYMPUTX('CM_CASE', '');
	LENGTH=LENGTHN(COMPRESS(RDB_COLUMN_NAME_LIST));
	RDB_COLUMN_NAME_LIST = TRIM(RDB_COLUMN_NAME_LIST);
	IF (LENGTH>1)  THEN TRIMMED_VALUE=SUBSTR((RDB_COLUMN_NAME_LIST), 1, LENGTHN(RDB_COLUMN_NAME_LIST)-1);
	IF (LENGTH<2) THEN TRIMMED_VALUE= 'D_CASE_MANAGEMENT.INVESTIGATION_KEY AS INVESTIGATION_KEY';
	CALL SYMPUTX('CM_CASE', TRIMMED_VALUE);

RUN;
*/




/*it creates the case_management_data table with the rdb_column_nm associated to the case management plus the investigation key*/

	-- CREATE TABLE CASE_MANAGEMENT_DATA AS 
	
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_Manage_Case_Management '+@DATAMART_NAME  ,'START' ,@Proc_Step_no , @Proc_Step_Name , @ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_Case_Management_Data';
		
		

	
  IF OBJECT_ID('rdb.dbo.tmp_DynDm_Case_Management_Data', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_Case_Management_Data;
	


    declare @SQL varchar(MAX);

	select 'list'+ @listStr;

	if len(@listStr) > 1
	 begin 
	 
		SET @SQL =  ' SELECT  '+@listStr +' , sd.INVESTIGATION_KEY ' +
					' INTO  RDB.dbo.tmp_DynDM_CASE_MANAGEMENT_DATA  '+ 
					' FROM rdb.dbo.tmp_DynDM_SUMM_DATAMART sd  '+ 
					'	LEFT JOIN  rdb.dbo.D_CASE_MANAGEMENT ON 	sd.INVESTIGATION_KEY  =D_CASE_MANAGEMENT.INVESTIGATION_KEY '+ 
					' WHERE sd.DISEASE_GRP_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM= '''+@DATAMART_NAME+''' )  '+ 
					'	AND D_CASE_MANAGEMENT.INVESTIGATION_KEY>1 ;  '
      end		
	else
	  begin
	  
		SET @SQL =  ' SELECT  sd.INVESTIGATION_KEY ' +
 					' INTO  RDB.dbo.tmp_DynDM_CASE_MANAGEMENT_DATA  '+ 
					' FROM rdb.dbo.tmp_DynDM_SUMM_DATAMART sd  '+ 
					'	LEFT JOIN  rdb.dbo.D_CASE_MANAGEMENT ON 	sd.INVESTIGATION_KEY  =D_CASE_MANAGEMENT.INVESTIGATION_KEY '+ 
					' WHERE sd.DISEASE_GRP_CD =(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM= '''+@DATAMART_NAME+''' )  '+ 
					'	AND D_CASE_MANAGEMENT.INVESTIGATION_KEY>1 ;  '
     

	  end
	   
     --DX   select @SQL;

        EXEC (@SQL);


	--DX	select * from  RDB.dbo.tmp_DynDM_CASE_MANAGEMENT_DATA;


		
				SELECT @ROWCOUNT_NO = @@ROWCOUNT;

 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDM_Manage_Case_Management '+@DATAMART_NAME  ,'START' ,@Proc_Step_no , @Proc_Step_Name , @ROWCOUNT_NO );
  
		
	
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
						   ,'RDB.DBO.DynDM_Manage_Case_Management '+@DATAMART_NAME
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
           ,'DYNAMIC_DATAMART'
           ,'RDB.DBO.DynDM_Manage_Case_Management'
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




