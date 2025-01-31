


     IF OBJECT_ID('rdb.dbo.tmp_DynDm_Organization', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_Organization;
go





USE rdb;
GO

/*
--     exec rdb.dbo.DynDm_OrgData_sp 999,'GENERIC_V2';
        	select *  from job_flow_log order by 1 desc;

*/

--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'CONG_SYPHILIS';


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


			 IF OBJECT_ID('rdb.dbo.DynDm_OrgData_sp', 'P') IS NOT NULL
				BEGIN
				DROP PROCEDURE dbo.DynDm_OrgData_sp;
				END;

GO



CREATE PROCEDURE [dbo].DynDm_OrgData_sp
 
            @batch_id BIGINT,
			@DATAMART_NAME VARCHAR(100)
	AS
BEGIN  
	 BEGIN TRY
	


--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_NAME  VARCHAR(100) = 'CONG_SYPHILIS';

		

		
		DECLARE @RowCount_no INT = 0  ;
		DECLARE @Proc_Step_no FLOAT = 0 ;
		DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
		DECLARE @batch_start_time datetime = null ;
		DECLARE @batch_end_time datetime = null ;



			SET @Proc_Step_no = 1;
	SET @Proc_Step_Name = 'SP_Start';

	

	
	BEGIN TRANSACTION;
	
   SELECT @ROWCOUNT_NO = @@ROWCOUNT; 

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
           ,'RDB.DBO.DynDm_OrgData_sp ' + @DATAMART_NAME
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
    
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  FACT_CASE';
	
	

	BEGIN TRANSACTION;



      declare @countstd int = 0;


	   select  @COUNTSTD =   count(*) from rdb.dbo.tmp_DynDm_Case_Management_Metadata
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
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_OrgData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_Organization_METADATA';
	

--CREATE TABLE ORGANIZATION_METADATA  AS 
     IF OBJECT_ID('rdb.dbo.tmp_DynDm_Organization_METADATA', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_Organization_METADATA;



SELECT DISTINCT RDB_COLUMN_NM, user_defined_column_nm, part_type_cd,
cast( null as varchar(2000)) as [Key],
cast( null as varchar(2000)) as Detail,
cast( null as varchar(2000)) as QEC,
cast( null as varchar(2000)) as [UID]
into rdb.dbo.tmp_DynDm_Organization_METADATA
FROM NBS_ODSE..NBS_RDB_METADATA 
INNER JOIN NBS_ODSE..NBS_UI_METADATA ON NBS_RDB_METADATA.NBS_UI_METADATA_UID =NBS_UI_METADATA.NBS_UI_METADATA_UID
WHERE INVESTIGATION_FORM_CD=
(SELECT FORM_CD FROM rdb.dbo.NBS_PAGE WHERE DATAMART_NM=@DATAMART_NAME)
AND NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM <> '' 
and NBS_RDB_METADATA.USER_DEFINED_COLUMN_NM IS NOT NULL
AND PART_TYPE_CD IS NOT NULL 
AND RDB_TABLE_NM ='D_ORGANIZATION' 
AND DATA_TYPE='PART';


  
SELECT @ROWCOUNT_NO = @@ROWCOUNT; 

  						  	 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_OrgData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	
    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_Organization';
	


     IF OBJECT_ID('rdb.dbo.tmp_DynDm_Organization', 'U') IS NOT NULL   
 				drop table rdb.dbo.tmp_DynDm_Organization;


--CREATE TABLE rdb.dbo.tmp_DynDm_Organization 
--(INVESTIGATION_KEY bigint);

select distinct investigation_key,
cast(null as bigint) as ORGANIZATION_UID
into rdb.dbo.tmp_DynDm_Organization
 FROM rdb.dbo.tmp_DynDm_SUMM_DATAMART
;



update rdb.dbo.tmp_DynDm_Organization_METADATA
set PART_TYPE_CD='FACILITY_FLD_FOLLOW_UP_KEY'
where  part_type_cd= 'FldFupFacilityOfPHC';



update rdb.dbo.tmp_DynDm_Organization_METADATA
set  PART_TYPE_CD='HOSPITAL_KEY' 
where part_type_cd= 'HospOfADT' ;



update rdb.dbo.tmp_DynDm_Organization_METADATA
set  PART_TYPE_CD='ORDERING_FACILITY_KEY' 
where part_type_cd= 'OrgAsClinicOfPHC' ;



update rdb.dbo.tmp_DynDm_Organization_METADATA
set  PART_TYPE_CD='DELIVERING_HOSP_KEY' 
where  part_type_cd= 'OrgAsHospitalOfDelivery' ;
	
	
	
update rdb.dbo.tmp_DynDm_Organization_METADATA
set PART_TYPE_CD='ORG_AS_REPORTER_KEY' 
where   part_type_cd= 'OrgAsReporterOfPHC' ;



/*
DATA ORGANIZATION_METADATA;
  SET ORGANIZATION_METADATA;
	BY SORT_KEY;
	LENGTH DETAIL $2000;
	IF FIRST.SORT_KEY THEN
	IF part_type_cd= 'FldFupFacilityOfPHC' THEN PART_TYPE_CD='FACILITY_FLD_FOLLOW_UP_KEY';
	IF part_type_cd= 'HospOfADT' THEN PART_TYPE_CD='HOSPITAL_KEY';
	IF part_type_cd= 'OrgAsClinicOfPHC' THEN PART_TYPE_CD='ORDERING_FACILITY_KEY';
	IF part_type_cd= 'OrgAsHospitalOfDelivery' THEN PART_TYPE_CD='DELIVERING_HOSP_KEY';
	IF part_type_cd= 'OrgAsReporterOfPHC' THEN PART_TYPE_CD='ORG_AS_REPORTER_KEY';
*/

/*

	KEY  = SUBSTR(USER_DEFINED_COLUMN_NM, 1, INDEX(USER_DEFINED_COLUMN_NM, "_UID"))||'KEY';
	DETAIL  = SUBSTR(USER_DEFINED_COLUMN_NM, 1, INDEX(USER_DEFINED_COLUMN_NM, "_UID"))||'DETAIL';
	QEC  = SUBSTR(USER_DEFINED_COLUMN_NM, 1, INDEX(USER_DEFINED_COLUMN_NM, "_UID"))||'QEC';
	UID= USER_DEFINED_COLUMN_NM;

*/




update rdb.dbo.tmp_DynDm_Organization_METADATA
set 
 [KEY] = substring(USER_DEFINED_COLUMN_NM,1,CHARINDEX('_UID',USER_DEFINED_COLUMN_NM))+'KEY' ,
 DETAIL = substring(USER_DEFINED_COLUMN_NM,1,CHARINDEX('_UID',USER_DEFINED_COLUMN_NM))+'DETAIL' ,
 QEC = substring(USER_DEFINED_COLUMN_NM,1,CHARINDEX('_UID',USER_DEFINED_COLUMN_NM))+'QEC' ,
 [UID] = USER_DEFINED_COLUMN_NM 
 ;




--DX select *  from rdb.dbo.tmp_DynDm_Organization_METADATA;


  
SELECT @ROWCOUNT_NO = @@ROWCOUNT; 

  						  	 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_OrgData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name , @ROWCOUNT_NO );
  
		
	
    COMMIT TRANSACTION;
	
	

/*
D_ORG
--	call execute('%POPULATE_ORGANIZATION('|| PART_TYPE_CD||','||  SUMM_DATAMART||','||  KEY||            ','||  DETAIL||','||  QEC||','||  UID||')');
                %MACRO POPULATE_ORGANIZATION(PART_TYPE_KEY     ,     SUMM_DATAMART3  ,    key,                  DETAIL  ,     QEC,        UID, @FACT_CASE);
*/


--CREATE TABLE ORGPART_TABLE AS







  declare  @USER_DEFINED_COLUMN_NM varchar(max) ,@PART_TYPE_CD  varchar(max) ,@DETAIL  varchar(max) ,@QEC  varchar(max) ,@UID varchar(max);




 declare @SQL varchar(max)



 --DX select PART_TYPE_CD,*  from rdb.dbo.tmp_DynDm_Organization_METADATA;


 

--declare @PART_TYPE_CD varchar(max) = null , @SQL varchar(max);



DECLARE db_cursor_org CURSOR LOCAL FOR 
 select PART_TYPE_CD , [key],DETAIL,QEC ,[UID] from rdb.dbo.tmp_DynDm_Organization_METADATA;

OPEN db_cursor_org  
FETCH NEXT FROM db_cursor_org INTO @PART_TYPE_CD ,@USER_DEFINED_COLUMN_NM,@DETAIL ,@QEC ,@UID

WHILE @@FETCH_STATUS = 0  
BEGIN  
      

	  
  

    BEGIN TRANSACTION;
	
	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'GENERATING  tmp_DynDm_OrgPart_Table_temp';
	
					 IF OBJECT_ID('rdb.dbo.tmp_DynDm_OrgPart_Table_temp', 'U') IS NOT NULL   
 								drop table rdb.dbo.tmp_DynDm_OrgPart_Table_temp;



				CREATE TABLE rdb.[dbo].[tmp_DynDm_OrgPart_Table_temp](
					[ORGANIZATION_KEY] [bigint] NULL,
					[ORGANIZATION_QUICK_CODE] [varchar](50) NULL,
					[ORGANIZATION_NAME] [varchar](50) NULL,
					[ORGANIZATION_LOCAL_ID] [varchar](50) NULL,
					[ORGANIZATION_UID] [bigint] NULL,
					[ORGANIZATION_STREET_ADDRESS_1] [varchar](50) NULL,
					[ORGANIZATION_STREET_ADDRESS_2] [varchar](50) NULL,
					[ORGANIZATION_CITY] [varchar](50) NULL,
					[ORGANIZATION_STATE] [varchar](50) NULL,
					[ORGANIZATION_ZIP] [varchar](10) NULL,
					[ORGANIZATION_COUNTY] [varchar](50) NULL,
					[ORGANIZATION_PHONE_WORK] [varchar](50) NULL,
					[ORGANIZATION_PHONE_EXT_WORK] [varchar](50) NULL,
					[PART_TYPE_CD] [bigint] NULL,
					[PART_TYPE_CD_NM] varchar(100),
					[CITY_STATE_ZIP] varchar(2000),
					[DETAIL] varchar(2000),
					[INVESTIGATION_KEY] [bigint] NOT NULL
				)
				;	    
						SET @SQL = ' insert into rdb.dbo.tmp_DynDm_OrgPart_Table_temp '
						   + ' ([ORGANIZATION_KEY] '
						   + ', [ORGANIZATION_QUICK_CODE] '
						   + ', [ORGANIZATION_NAME] '
						   + ', [ORGANIZATION_LOCAL_ID] '
						   + ', [ORGANIZATION_UID] '
						   + ', [ORGANIZATION_STREET_ADDRESS_1] '
						   + ', [ORGANIZATION_STREET_ADDRESS_2] '
						   + ', [ORGANIZATION_CITY] '
						   + ', [ORGANIZATION_STATE] '
						   + ', [ORGANIZATION_ZIP] '
						   + ', [ORGANIZATION_COUNTY] '
						   + ', [ORGANIZATION_PHONE_WORK] '
						   + ', [ORGANIZATION_PHONE_EXT_WORK] '
						   + ', [PART_TYPE_CD] '
						   + ', [PART_TYPE_CD_NM] '
						   + ', [CITY_STATE_ZIP] '
						   + ', [DETAIL] '
						   + ', [INVESTIGATION_KEY] )'
						+ ' SELECT  distinct '+ 
						' (d_o.ORGANIZATION_KEY)   ,  '+
						' d_o.ORGANIZATION_QUICK_CODE ,  '+
						' d_o.ORGANIZATION_NAME ,  '+
						' d_o.ORGANIZATION_LOCAL_ID ,  '+ 
						' d_o.ORGANIZATION_UID ,  '+
						' d_o.ORGANIZATION_STREET_ADDRESS_1 ,  '+
						' d_o.ORGANIZATION_STREET_ADDRESS_2 ,  '+
						' d_o.ORGANIZATION_CITY ,  '+
						' d_o.ORGANIZATION_STATE ,  '+ 
						' d_o.ORGANIZATION_ZIP ,  '+
						' d_o.ORGANIZATION_COUNTY ,  '+
						' d_o.ORGANIZATION_PHONE_WORK ,  '+
						' d_o.ORGANIZATION_PHONE_EXT_WORK ,  '+
						   @PART_TYPE_CD +', '+
						''''+   @PART_TYPE_CD +''', '+
						'   coalesce( ltrim(rtrim(ORGANIZATION_CITY))+'', '','''')+coalesce( ltrim(rtrim(ORGANIZATION_STATE))+'' '','''')+coalesce( ltrim(rtrim(ORGANIZATION_ZIP)),'''') '  + 
						' , null ,'+
						' s_d.INVESTIGATION_KEY AS INVESTIGATION_KEY '+
						' FROM rdb.dbo.tmp_DynDm_SUMM_DATAMART s_d '+
						' INNER JOIN rdb.dbo.'+@FACT_CASE+ '   ON s_d.INVESTIGATION_KEY =  '+@FACT_CASE+ '.INVESTIGATION_KEY '+
						' LEFT JOIN rdb.dbo.D_ORGANIZATION  d_o ON '+@FACT_CASE+'.'+@PART_TYPE_CD+' = d_o.ORGANIZATION_KEY  '+
						 '; '

						-- select 'INSERT',@PART_TYPE_CD ,@USER_DEFINED_COLUMN_NM,@DETAIL ,@QEC ,@UID, @SQL;


							EXEC(@SQL);

							
  
								SELECT @ROWCOUNT_NO = @@ROWCOUNT; 

  						  	 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
								 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_OrgData_sp '+@DATAMART_NAME ,'START' ,@Proc_Step_no ,@Proc_Step_Name   + '-'+@PART_TYPE_CD  , @ROWCOUNT_NO );
  
		
	
									COMMIT TRANSACTION;
	
	
									BEGIN TRANSACTION;
	
									SET @Proc_Step_no = @Proc_Step_no + 1;
									SET @Proc_Step_Name = 'GENERATING  UPDATE tmp_DynDm_OrgPart_Table_temp';
	



			
								update rdb.dbo.tmp_DynDm_OrgPart_Table_temp SET DETAIL ='<b></b>' + LTRIM(RTRIM(coalesce(ORGANIZATION_LOCAL_ID,''))) where LTRIM(RTRIM(ORGANIZATION_LOCAL_ID)) is not null ;

								update rdb.dbo.tmp_DynDm_OrgPart_Table_temp SET  DETAIL = DETAIL  + '<br>'  +   (ORGANIZATION_NAME)  where LTRIM(RTRIM(ORGANIZATION_NAME)) is not null ;
								update rdb.dbo.tmp_DynDm_OrgPart_Table_temp SET  DETAIL=  DETAIL  + '<br>'  +  LTRIM(RTRIM(ORGANIZATION_STREET_ADDRESS_1))  where LTRIM(RTRIM(ORGANIZATION_STREET_ADDRESS_1)) is not null ;
								update rdb.dbo.tmp_DynDm_OrgPart_Table_temp SET  DETAIL=  DETAIL  + '<br>'  +  LTRIM(RTRIM(ORGANIZATION_STREET_ADDRESS_2))  where LTRIM(RTRIM(ORGANIZATION_STREET_ADDRESS_2)) is not null ; 
								update rdb.dbo.tmp_DynDm_OrgPart_Table_temp SET  DETAIL=  DETAIL  + '<br>'  +  LTRIM(RTRIM(CITY_STATE_ZIP))  where LTRIM(RTRIM(CITY_STATE_ZIP)) is not null ; 
								update rdb.dbo.tmp_DynDm_OrgPart_Table_temp SET  DETAIL=  DETAIL  + '<br>'  +  LTRIM(RTRIM(ORGANIZATION_COUNTY))  where LTRIM(RTRIM(ORGANIZATION_COUNTY)) is not null ; 
								update rdb.dbo.tmp_DynDm_OrgPart_Table_temp SET  DETAIL=  DETAIL  + '<br>'  +  LTRIM(RTRIM(ORGANIZATION_PHONE_WORK))  where LTRIM(RTRIM(ORGANIZATION_PHONE_WORK)) is not null ; 
	
								update rdb.dbo.tmp_DynDm_OrgPart_Table_temp SET  DETAIL= DETAIL +  ', ext. '  +  LTRIM(  RTRIM(ORGANIZATION_PHONE_EXT_WORK)) 
								where  LTRIM(RTRIM(ORGANIZATION_PHONE_WORK)) is not null  and LTRIM(RTRIM(ORGANIZATION_PHONE_EXT_WORK)) is not null ;

								update rdb.dbo.tmp_DynDm_OrgPart_Table_temp SET  DETAIL= DETAIL +  '<br> ext. '  +  LTRIM(  RTRIM(ORGANIZATION_PHONE_EXT_WORK))
								where  LTRIM(RTRIM(ORGANIZATION_PHONE_WORK)) is  null  and LTRIM(RTRIM(ORGANIZATION_PHONE_EXT_WORK)) is not null ;
				
								update rdb.dbo.tmp_DynDm_OrgPart_Table_temp SET  DETAIL= DETAIL +  '<br> '  where   LTRIM(RTRIM(DETAIL)) is not null ;


	
								SELECT @ROWCOUNT_NO = @@ROWCOUNT; 

  						  	 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
								 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_OrgData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name +'-'+ @PART_TYPE_CD  , @ROWCOUNT_NO );
  
		
	
									COMMIT TRANSACTION;
	
	
									BEGIN TRANSACTION;
	
									SET @Proc_Step_no = @Proc_Step_no + 1;
									SET @Proc_Step_Name = 'GENERATING  ALTER tmp_DynDm_OrgPart_Table_temp';
	

		



							SET @SQL =  'alter table rdb.dbo.tmp_DynDm_OrgPart_Table_temp add  ' +  @DETAIL  + ' [varchar](2000) , ' +  @USER_DEFINED_COLUMN_NM+ ' bigint , '  +  @QEC+ ' [varchar](50) , ' +  @UID+ ' bigint ; '

  					   -- select 'ALTER', @PART_TYPE_CD, @SQL;


							EXEC(@SQL);


			
							SET @SQL =  'alter table rdb.dbo.tmp_DynDm_Organization add  ' +  @DETAIL  + ' [varchar](2000) , ' +  @USER_DEFINED_COLUMN_NM+ ' bigint , '  +  @QEC+ ' [varchar](50) , ' +  @UID+ ' bigint ; '

  	   
							EXEC(@SQL);



								SELECT @ROWCOUNT_NO = @@ROWCOUNT; 

  						  	 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
								 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_OrgData_sp '+@DATAMART_NAME   ,'START' ,@Proc_Step_no ,@Proc_Step_Name + ' - '+@PART_TYPE_CD  , @ROWCOUNT_NO  );
  
		
	
									COMMIT TRANSACTION;
	
	
									BEGIN TRANSACTION;
	
									SET @Proc_Step_no = @Proc_Step_no + 1;
									SET @Proc_Step_Name = 'GENERATING  UPDATE tmp_DynDm_OrgPart_Table_temp';
	





						--@USER_DEFINED_COLUMN_NM=ORGANIZATION_KEY;
						--@QEC =ORGANIZATION_QUICK_CODE;
						--@UID= ORGANIZATION_UID;

							SET @SQL =  'update tDO SET  ORGANIZATION_UID = orgtemp.ORGANIZATION_UID ,'
									  +  @DETAIL  + ' = DETAIL , ' 
									  +  @USER_DEFINED_COLUMN_NM+ ' =  ORGANIZATION_KEY , '  
									  +  @QEC+ ' = ORGANIZATION_QUICK_CODE , ' 
									  +  @UID+ ' = orgtemp.ORGANIZATION_UID '
									  +  ' FROM rdb.dbo.tmp_DynDm_Organization  tDO '
									  +  ' INNER JOIN rdb.dbo.tmp_DynDm_OrgPart_Table_temp orgtemp  ON  tDO.investigation_key = orgtemp.investigation_key '
									  + ' ; '

  					   -- select 'UPDATE', @PART_TYPE_CD, @SQL;

						--select 'tmp_DynDm_OrgPart_Table_temp',* from tmp_DynDm_OrgPart_Table_temp;

							EXEC(@SQL);

							
								SELECT @ROWCOUNT_NO = @@ROWCOUNT; 

  						  	 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
								 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_OrgData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name +'-'+ @PART_TYPE_CD  , @ROWCOUNT_NO );
  
		
	
									COMMIT TRANSACTION;
	


      FETCH NEXT FROM db_cursor_org INTO @PART_TYPE_CD ,@USER_DEFINED_COLUMN_NM,@DETAIL ,@QEC ,@UID

END 

CLOSE db_cursor_org  
DEALLOCATE db_cursor_org
;




--IF OBJECT_ID('rdb.dbo.tmp_DynDm_ORGPART_TABLE', 'U') IS NOT NULL   
-- 				drop table rdb.dbo.tmp_DynDm_ORGPART_TABLE;



--select distinct  * 
--into rdb.dbo.tmp_DynDm_ORGPART_TABLE 
--from  rdb.dbo.tmp_DynDm_OrgPart_Table_temp;


/*
DATA ORGPART_TABLE;
SET ORGPART_TABLE;
LENGTH CITY_STATE_ZIP $2000;
LENGTH DETAIL   $2000;
 
IF   ORGANIZATION_KEY =1 THEN ORGANIZATION_KEY=.;
@USER_DEFINED_COLUMN_NM=ORGANIZATION_KEY;
@QEC =ORGANIZATION_QUICK_CODE;
@UID= ORGANIZATION_UID;


IF LENGTHN(TRIM(ORGANIZATION_CITY))>0 THEN CITY_STATE_ZIP = (TRIM(ORGANIZATION_CITY));
IF LENGTHN(TRIM(ORGANIZATION_STATE))>0 THEN CITY_STATE_ZIP = LTRIM(  RTRIM(CITY_STATE_ZIP) || ', ' ||  (TRIM(ORGANIZATION_STATE)) ;
IF LENGTHN(TRIM(ORGANIZATION_ZIP))>0 THEN CITY_STATE_ZIP = LTRIM(  RTRIM(CITY_STATE_ZIP) || ' ' || LTRIM(  RTRIM(ORGANIZATION_ZIP) ;

If LENGTHN(TRIM(ORGANIZATION_LOCAL_ID))>0 THEN DETAIL  ='<b></b>' ||TRIM(ORGANIZATION_LOCAL_ID);
If LENGTHN(TRIM(ORGANIZATION_NAME))>0 THEN DETAIL  =TRIM(DETAIL ) ||'<br>' ||  (ORGANIZATION_NAME);
IF LENGTHN(TRIM(ORGANIZATION_STREET_ADDRESS_1))>0 THEN DETAIL =  LTRIM(  RTRIM(DETAIL ) ||'<br>' || (TRIM(ORGANIZATION_STREET_ADDRESS_1));
IF LENGTHN(TRIM(ORGANIZATION_STREET_ADDRESS_2))>0 then DETAIL =  LTRIM(  RTRIM(DETAIL ) || '<br>' ||  (TRIM(ORGANIZATION_STREET_ADDRESS_2)) ;
IF LENGTHN(TRIM(CITY_STATE_ZIP))>0 then DETAIL = LTRIM(  RTRIM(DETAIL )|| '<br>' || LTRIM(  RTRIM(CITY_STATE_ZIP) ;
IF LENGTHN(TRIM(ORGANIZATION_COUNTY))>0 then DETAIL = LTRIM(  RTRIM(DETAIL )|| '<br>' ||  (TRIM(ORGANIZATION_COUNTY));
IF LENGTHN(TRIM(ORGANIZATION_PHONE_WORK))>0 then DETAIL = LTRIM(  RTRIM(DETAIL )|| '<br>' || LTRIM(  RTRIM(ORGANIZATION_PHONE_WORK) ;
IF LENGTHN(TRIM(DETAIL ))>0 then DETAIL = LTRIM(  RTRIM(DETAIL )|| '<br>'; 


IF LENGTHN(TRIM(ORGANIZATION_PHONE_WORK))>0 and LENGTHN(TRIM(ORGANIZATION_PHONE_EXT_WORK))>0 then DETAIL = LTRIM(  RTRIM(DETAIL )|| ', ext. ' || LTRIM(  RTRIM(ORGANIZATION_PHONE_EXT_WORK) ;
IF LENGTHN(TRIM(ORGANIZATION_PHONE_WORK))=0 and LENGTHN(TRIM(ORGANIZATION_PHONE_EXT_WORK))>0 then DETAIL = LTRIM(  RTRIM(DETAIL )|| '<br> ext. ' || LTRIM(  RTRIM(ORGANIZATION_PHONE_EXT_WORK) ;

*/

/*


	
DROP ORGANIZATION_LOCAL_ID ORGANIZATION_NAME ORGANIZATION_STREET_ADDRESS_1 ORGANIZATION_STREET_ADDRESS_2 ORGANIZATION_COUNTY ORGANIZATION_PHONE_WORK ORGANIZATION_KEY 
ORGANIZATION_PHONE_EXT_WORK CITY_STATE_ZIP ORGANIZATION_CITY ORGANIZATION_ZIP ORGANIZATION_STATE ORGANIZATION_QUICK_CODE @PART_TYPE_KEY;

RUN; 
*/

/*
alter table rdb.dbo.tmp_DynDm_Organization 
drop column  ORGANIZATION_LOCAL_ID,ORGANIZATION_NAME, ORGANIZATION_STREET_ADDRESS_1, ORGANIZATION_STREET_ADDRESS_2, ORGANIZATION_COUNTY, ORGANIZATION_PHONE_WORK, ORGANIZATION_KEY, 
ORGANIZATION_PHONE_EXT_WORK, CITY_STATE_ZIP, ORGANIZATION_CITY, ORGANIZATION_ZIP, ORGANIZATION_STATE, ORGANIZATION_QUICK_CODE;


alter table rdb.dbo.tmp_DynDm_Organization drop column  PART_TYPE_CD , PART_TYPE_CD_NM ;
*/





/*
 PROC SORT DATA=ORGPART_TABLE NODUPKEY; BY INVESTIGATION_KEY;RUN;

DATA ORGANIZATION;
MERGE ORGPART_TABLE ORGANIZATION;
BY INVESTIGATION_KEY;
RUN;
*/





--IF OBJECT_ID('rdb.dbo.tmp_DynDm_Organization'#, 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_Organization;



--select * 
--into rdb.dbo.tmp_DynDm_Organization
--from tmp_DynDm_ORGPART_TABLE
--;

--select * from rdb.dbo.tmp_DynDm_Organization;

	
									BEGIN TRANSACTION;
	
									SET @Proc_Step_no = @Proc_Step_no + 1;
									SET @Proc_Step_Name = 'SP_COMPLETE';

								SELECT @ROWCOUNT_NO = @@ROWCOUNT; 

  						  	 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
								 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_OrgData_sp '+@DATAMART_NAME  ,'START' ,@Proc_Step_no ,@Proc_Step_Name  , @ROWCOUNT_NO );
  
		
	
									COMMIT TRANSACTION;
	
	






COMMIT TRANSACTION;


    END TRY
            BEGIN CATCH
						IF @@TRANCOUNT > 0
						BEGIN
							ROLLBACK TRANSACTION;
						END;
						DECLARE @ErrorNumber int= ERROR_NUMBER();
						DECLARE @ErrorLine int= ERROR_LINE();
						DECLARE @ErrorMessage nvarchar(4000)= ERROR_MESSAGE();
						DECLARE @ErrorSeverity int= ERROR_SEVERITY();
						DECLARE @ErrorState int= ERROR_STATE();

                        select @ErrorMessage;

						INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [Error_Description], [row_count] )
						VALUES( @Batch_id, 'DYNAMIC_DATAMART', 'RDB.DBO.DynDm_OrgData_sp', 'ERROR', @Proc_Step_no, 'ERROR - '+@Proc_Step_name, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );
						RETURN -1;
	        END CATCH;
END;

GO






















