IF OBJECT_ID('rdb.dbo.tmp_Morbidity_Report', 'U') IS NOT NULL 
		drop table rdb..tmp_Morbidity_Report


go


/*-------------------------------------------------------


	Morb Report Dimension

	Note:
		1) morb_rpt_share_ind and morb_rpt_oid need to 
		be renamed to shared_ind and program_jurisdiction_oid

		2 wrong modeling,  morb 2 trmt is one-many
		3 MORB_RPT_TEST_DT_KEY, no mapping
		4 NURSING_HOME_KEY, no mapping
---------------------------------------------------------*/



USE [RDB]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF OBJECT_ID('rdb.dbo.[sp_D_Morbidity_Report]', 'P') IS NOT NULL
BEGIN
DROP PROCEDURE dbo.sp_D_Morbidity_Report;
END;
GO



USE [RDB]
GO
/****** Object:  StoredProcedure [dbo].[sp_D_Morbidity_Report]    Script Date: 8/19/2021 5:51:37 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO






CREATE PROCEDURE [dbo].[sp_D_Morbidity_Report]
  @batch_id BIGINT
 as

  BEGIN

  --
--UPDATE ACTIVITY_LOG_DETAIL SET 
--START_DATE=DATETIME();
-- declare  @batch_id BIGINT



    DECLARE @RowCount_no INT ;
    DECLARE @Proc_Step_no FLOAT = 0 ;
    DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
	DECLARE @batch_start_time datetime2(7) = null ;
	DECLARE @batch_end_time datetime2(7) = null ;
 
 BEGIN TRY
    
	SET @Proc_Step_no = 1;
	SET @Proc_Step_Name = 'SP_Start';

	

	
	BEGIN TRANSACTION;


--create table updated_observation_List as

 

    		  SELECT @ROWCOUNT_NO = @@ROWCOUNT;
	
		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',  @PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);


			COMMIT TRANSACTION;

           BEGIN TRANSACTION; 
	
			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  updt_MORBIDITY_REPORT_list '; 



			IF OBJECT_ID('rdb.dbo.tmp_updt_MORBIDITY_REPORT_list', 'U') IS NOT NULL 
								drop table rdb..tmp_updt_MORBIDITY_REPORT_list ;


						select morb_rpt_uid, morb_rpt_key 
						into rdb..tmp_updt_MORBIDITY_REPORT_list
						from RDB..MORBIDITY_REPORT 
						where morb_rpt_uid in (select observation_uid from RDB..updated_observation_List)
						and morb_rpt_uid is not null;



			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_SAS_updt_MORBIDITY_REPORT_list'; 


			IF OBJECT_ID('rdb.dbo.tmp_SAS_updt_MORBIDITY_REPORT_list', 'U') IS NOT NULL 
								drop table rdb..tmp_SAS_updt_MORBIDITY_REPORT_list ;


						--CREATE TABLE RDB..SAS_updt_MORBIDITY_REPORT_list AS 
						SELECT * 
						into rdb..tmp_SAS_updt_MORBIDITY_REPORT_list
						FROM rdb..tmp_updt_MORBIDITY_REPORT_list;


						--create table updt_MORBIDITY_REPORT_Event_list as 
			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_updt_MORBIDITY_REPORT_Event_list'; 


			IF OBJECT_ID('rdb.dbo.tmp_updt_MORBIDITY_REPORT_Event_list', 'U') IS NOT NULL 
								drop table rdb..tmp_updt_MORBIDITY_REPORT_Event_list ;



						select morb_rpt_key 
						into rdb..tmp_updt_MORBIDITY_REPORT_Event_list
						from RDB.dbo.MORBIDITY_REPORT_Event 
						where morb_rpt_key in (select morb_rpt_key from rdb..tmp_updt_MORBIDITY_REPORT_list);
						;



						--CREATE TABLE RDB..SAS_up_MORBIDITY_RPT_EVNT_lst AS 

			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_SAS_up_MORBIDITY_RPT_EVNT_lst'; 


			IF OBJECT_ID('rdb.dbo.tmp_SAS_up_MORBIDITY_RPT_EVNT_lst', 'U') IS NOT NULL 
								drop table rdb..tmp_SAS_up_MORBIDITY_RPT_EVNT_lst ;


						SELECT * 
						into rdb..tmp_SAS_up_MORBIDITY_RPT_EVNT_lst
						FROM rdb..tmp_updt_MORBIDITY_REPORT_Event_list;

						/*
						---VS
						/* Texas - Moved code execution to database 08/20/2020 */
						/* delete * from RDB..MORBIDITY_REPORT_Event where morb_rpt_key in (select morb_rpt_key from updt_MORBIDITY_REPORT_Event_list); */

						PROC SQL;
						connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
						EXECUTE (
						delete from MORBIDITY_REPORT_Event where morb_rpt_key in (select morb_rpt_key from SAS_up_MORBIDITY_RPT_EVNT_lst);
						) by sql;
						disconnect from sql; 
						QUIT;
						*/

						delete from rdb..MORBIDITY_REPORT_Event 
						where morb_rpt_key in (select morb_rpt_key from rdb..tmp_SAS_up_MORBIDITY_RPT_EVNT_lst);



						--create table UPDT_MORB_RPT_USER_COMMENT_LIST as 

			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_UPDT_MORB_RPT_USER_COMMENT_LIST'; 


			IF OBJECT_ID('rdb.dbo.tmp_UPDT_MORB_RPT_USER_COMMENT_LIST', 'U') IS NOT NULL 
								drop table rdb..tmp_UPDT_MORB_RPT_USER_COMMENT_LIST ;



						select MORB_RPT_UID 
						into rdb..tmp_UPDT_MORB_RPT_USER_COMMENT_LIST
						from RDB..MORB_RPT_USER_COMMENT 
						where MORB_RPT_UID in (select observation_uid from RDB..updated_observation_List);


						/*

						/* Texas - Moved code execution to database 08/20/2020 */
						/* delete * from RDB..MORB_RPT_USER_COMMENT where morb_rpt_key in (select morb_rpt_key from updt_MORBIDITY_REPORT_list); */
						PROC SQL;
						connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
						EXECUTE (
						delete from MORB_RPT_USER_COMMENT where morb_rpt_key in (select morb_rpt_key from SAS_updt_MORBIDITY_REPORT_list);
						) by sql;
						disconnect from sql; 
						QUIT;

						/* Texas - Moved code execution to database 08/20/2020 */
						/* delete * from RDB..LAB_TEST_RESULT where morb_rpt_key in (select morb_rpt_key from updt_MORBIDITY_REPORT_list); */
						PROC SQL;
						connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
						EXECUTE (
						delete from LAB_TEST_RESULT where morb_rpt_key in (select morb_rpt_key from SAS_updt_MORBIDITY_REPORT_list);
						) by sql;
						disconnect from sql; 
						QUIT;

						/* Texas - Moved code execution to database 08/20/2020 */
						/* delete * from RDB..MORBIDITY_REPORT where morb_rpt_key in (select morb_rpt_key from updt_MORBIDITY_REPORT_list); */
						PROC SQL;
						connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
						EXECUTE (
						delete from MORBIDITY_REPORT where morb_rpt_key in (select morb_rpt_key from SAS_updt_MORBIDITY_REPORT_list);
						) by sql;
						disconnect from sql; 
						QUIT;

						*/

						delete from rdb..MORB_RPT_USER_COMMENT where morb_rpt_key in (select morb_rpt_key from rdb..tmp_SAS_updt_MORBIDITY_REPORT_list);

						delete from rdb..LAB_TEST_RESULT where morb_rpt_key in (select morb_rpt_key from rdb..tmp_SAS_updt_MORBIDITY_REPORT_list);

						delete a from rdb..Morbidity_Report a inner join tmp_SAS_updt_MORBIDITY_REPORT_list on  a.morb_rpt_key = tmp_SAS_updt_MORBIDITY_REPORT_list.morb_rpt_key;


						--create table Morb_Root as 


			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_Morb_Root'; 


			IF OBJECT_ID('rdb.dbo.tmp_Morb_Root', 'U') IS NOT NULL 
								drop table rdb..tmp_Morb_Root ;


						CREATE TABLE rdb.[dbo].[tmp_Morb_Root](
							morb_Rpt_Key_id  [int] IDENTITY(1,1) NOT NULL,
							[morb_rpt_local_id] [varchar](50) NULL,
							[morb_rpt_share_ind] [char](1) NOT NULL,
							[morb_rpt_oid] [bigint] NULL,
							[morb_RPT_Created_DT] [datetime] NULL,
							[morb_RPT_Create_BY] [bigint] NULL,
							[PH_RECEIVE_DT] [datetime] NULL,
							[morb_RPT_LAST_UPDATE_DT] [datetime] NULL,
							[morb_RPT_LAST_UPDATE_BY] [bigint] NULL,
							[Jurisdiction_cd] [varchar](20) NULL,
							[Jurisdiction_nm] [varchar](50)  NULL,
							[morb_report_date] [datetime] NULL,
							[Condition_cd] [varchar](50) NULL,
							[morb_rpt_uid] [bigint] NOT NULL,
							[ELECTRONIC_IND] [char](1) NULL,
							[record_status_cd] [varchar](20) NULL,
							[PROCESSING_DECISION_CD] [varchar](20) NULL,
							[PROCESSING_DECISION_DESC] [varchar](25) null,
							[PROVIDER_KEY] [numeric](18, 0) NULL,
							morb_rpt_KEY int 

						) ON [PRIMARY]
						;

						insert into rdb.[dbo].[tmp_Morb_Root](
						[morb_rpt_local_id]
							  ,[morb_rpt_share_ind]
							  ,[morb_rpt_oid]
							  ,[morb_RPT_Created_DT]
							  ,[morb_RPT_Create_BY]
							  ,[PH_RECEIVE_DT]
							  ,[morb_RPT_LAST_UPDATE_DT]
							  ,[morb_RPT_LAST_UPDATE_BY]
							  ,[Jurisdiction_cd]
							  ,[Jurisdiction_nm]
							  ,[morb_report_date]
							  ,[Condition_cd]
							  ,[morb_rpt_uid]
							  ,[ELECTRONIC_IND]
							  ,[record_status_cd]
							  ,[PROCESSING_DECISION_CD]
							  ,[PROCESSING_DECISION_DESC]
						)
						select 	obs.local_id				 as morb_rpt_local_id,
								obs.shared_ind				 as morb_rpt_share_ind,
								obs.PROGRAM_JURISDICTION_OID  as morb_rpt_oid,	
								obs.ADD_TIME				 as morb_RPT_Created_DT,
								obs.ADD_USER_ID  		 	 as morb_RPT_Create_BY, 
								obs.rpt_to_state_time  		 as PH_RECEIVE_DT,
								obs.LAST_CHG_TIME 			 as morb_RPT_LAST_UPDATE_DT, 
								obs.LAST_CHG_USER_ID		 as morb_RPT_LAST_UPDATE_BY, /**/
								obs.jurisdiction_cd			 as Jurisdiction_cd,		/*mrb137*/
								 null, --VS put(obs.jurisdiction_cd, $JURCODE.)  as Jurisdiction_nm,
								obs.activity_to_time   	 	 as morb_report_date, 	/*mrb101*/
								obs.cd						 as Condition_cd, 		/*MRB121*/
								obs.observation_uid			 as morb_rpt_uid,
								obs.electronic_ind			 as ELECTRONIC_IND, 
								obs.record_status_cd,
								obs.PROCESSING_DECISION_CD ,
								substring(cvg.Code_short_desc_txt,1,25)

						from 	RDB..s_updated_lab as updated_lab 
						inner join nbs_odse..observation obs on updated_lab.observation_uid =obs.observation_uid
						left outer join NBS_SRTE..Code_value_general  cvg on cvg.code_set_nm = 'STD_NBS_PROCESSING_DECISION_ALL' AND cvg.code = obs.PROCESSING_DECISION_CD 
						where obs.obs_domain_cd_st_1 = 'Order'
							  and obs.CTRL_CD_DISPLAY_FORM  = 'MorbReport'
						;

						UPDATE rdb.dbo.tmp_Morb_Root 
						set jurisdiction_nm = (
												select code_short_desc_txt 
												from nbs_srte..jurisdiction_code where code= tmp_Morb_Root.Jurisdiction_cd and code_set_nm = 'S_JURDIC_C'
												)
												where Jurisdiction_cd is not null
												;






						/*

						proc sort data = Morb_Root;
						by morb_rpt_uid;

						%assign_key(Morb_Root, morb_Rpt_Key); --VS
						proc sql;
						tmp_
						*/
						/*

						--delete from rdb..tmp_Morb_Root where  morb_Rpt_Key=1;


						ALTER TABLE Morb_Root ADD morb_rpt_KEY_MAX_VAL  NUMERIC;


						UPDATE rdb..tmp_Morb_Root SET morb_rpt_KEY_MAX_VAL=(SELECT MAX(morb_rpt_KEY) FROM RDB..morbidity_report);
						*/




						UPDATE rdb.dbo.tmp_Morb_Root 
							  SET morb_rpt_KEY= morb_rpt_KEY_id + coalesce((SELECT MAX(morb_rpt_KEY) FROM RDB.dbo.Morbidity_Report),1)
							   ;





						-- VS PROCESSING_DECISION_DESC=PUT(PROCESSING_DECISION_CD,$APROCDNF.);



							UPDATE rdb.dbo.tmp_Morb_Root  
							  set  record_status_cd = 'INACTIVE' 
							  where  record_status_cd = 'LOG_DEL';

							UPDATE rdb.dbo.tmp_Morb_Root  
							  set  record_status_cd = 'ACTIVE' 
							  where  rtrim(record_status_cd) in (  'PROCESSED','UNPROCESSED', '')
							  ;


	  
						/* Morb Report Form Question */

						--create table MorbFrmQ as 


			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_MorbFrmQ'; 


			IF OBJECT_ID('rdb.dbo.tmp_MorbFrmQ', 'U') IS NOT NULL 
								drop table rdb..tmp_MorbFrmQ ;



						select 	mr.morb_rpt_uid,
								oq.cd,
								oq.observation_uid
						into rdb..tmp_MorbFrmQ
						from	rdb..tmp_morb_root					as mr,
								nbs_odse..act_relationship	as ar,
								nbs_odse..observation			as oq
						where 	mr.morb_rpt_uid = ar.target_act_uid
								and ar.type_cd = 'MorbFrmQ'
								and ar.RECORD_STATUS_CD = 'ACTIVE'
								and oq.observation_uid = ar.source_act_uid
						;




						/*Morb Report Coded */

						--create table MorbFrmQCoded as 


			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_MorbFrmQCoded'; 


			IF OBJECT_ID('rdb.dbo.tmp_MorbFrmQCoded', 'U') IS NOT NULL 
								drop table rdb..tmp_MorbFrmQCoded ;


						select 	oq.*,
								ob.code
						into rdb..tmp_MorbFrmQCoded
						from	rdb..tmp_MorbFrmQ					as oq,
								nbs_odse..obs_value_coded 	as ob
						where 	oq.observation_uid = ob.observation_uid

						;

						/*Morb Report date  */

						--create table MorbFrmQDate as 



			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_MorbFrmQDate'; 


			IF OBJECT_ID('rdb.dbo.tmp_MorbFrmQDate', 'U') IS NOT NULL 
								drop table rdb..tmp_MorbFrmQDate ;


						select 	oq.*,
								ob.from_time
						into rdb..tmp_MorbFrmQDate
						from	rdb..tmp_MorbFrmQ					as oq,
								nbs_odse..obs_value_date	 	as ob
						where 	oq.observation_uid = ob.observation_uid

						;

						/*Morb Report Txt  */

						--create table MorbFrmQTxt as 


			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_MorbFrmQTxt'; 


			IF OBJECT_ID('rdb.dbo.tmp_MorbFrmQTxt', 'U') IS NOT NULL 
								drop table rdb..tmp_MorbFrmQTxt ;


						select 	oq.*,
								REPLACE(REPLACE(ob.value_txt, CHAR(13), ' '), CHAR(10), ' ')	as VALUE_TXT
						into rdb..tmp_MorbFrmQTxt
						from rdb..tmp_MorbFrmQ					as oq,
								nbs_odse..obs_value_txt	 	as ob
						where 	oq.observation_uid = ob.observation_uid

						;


						/*

						proc sort data = MorbFrmQTxt;
						by morb_rpt_uid;


						proc transpose data = MorbFrmQCoded out =MorbFrmQCoded2(drop= _name_ _label_);
							id cd;
							var code;
							by morb_rpt_uid;

						run;
						*/


			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_MorbFrmQCoded2'; 


			IF OBJECT_ID('rdb.dbo.tmp_MorbFrmQCoded2', 'U') IS NOT NULL 
								drop table rdb..tmp_MorbFrmQCoded2 ;


						DECLARE @columns NVARCHAR(MAX);
						DECLARE @sql NVARCHAR(MAX);

						SET @columns = N'';

						SELECT @columns+=N', p.'+QUOTENAME(LTRIM(RTRIM([CD])))
						FROM
						(
							SELECT [CD] 
							FROM rdb.[dbo].tmp_MorbFrmQCoded AS p
							GROUP BY [CD]
						) AS x;
						SET @sql = N'
						SELECT [morb_rpt_uid] as morb_rpt_uid_coded, '+STUFF(@columns, 1, 2, '')+
						' into rdb.dbo.tmp_MorbFrmQCoded2 ' +
						'FROM (
						SELECT [morb_rpt_uid], [code] , [CD] 
						 FROM rdb.[dbo].tmp_MorbFrmQCoded
							group by [morb_rpt_uid], [code] , [CD] 
								) AS j PIVOT (max(code) FOR [CD] in 
							   ('+STUFF(REPLACE(@columns, ', p.[', ',['), 1, 1, '')+')) AS p;';

						print @sql;
						EXEC sp_executesql @sql;




						/*

						proc transpose data = MorbFrmQDate out =MorbFrmQDate2 (drop= _name_ _label_);
							id cd;
							var from_time;
							by morb_rpt_uid;
						run;
						*/


			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_MorbFrmQDate2'; 


			IF OBJECT_ID('rdb.dbo.tmp_MorbFrmQDate2', 'U') IS NOT NULL 
								drop table rdb..tmp_MorbFrmQDate2 ;

						--DECLARE @columns NVARCHAR(MAX);
						--DECLARE @sql NVARCHAR(MAX);

						SET @columns = N'';

						SELECT @columns+=N', p.'+QUOTENAME(LTRIM(RTRIM([CD])))
						FROM
						(
							SELECT [CD] 
							FROM rdb.[dbo].tmp_MorbFrmQDate AS p
							GROUP BY [CD]
						) AS x;

						SET @sql = N'
						SELECT [morb_rpt_uid] as morb_rpt_uid_date, '+STUFF(@columns, 1, 2, '')+
						' into rdb.dbo.tmp_MorbFrmQDate2 ' +
						'FROM (
						SELECT [morb_rpt_uid], [from_time] , [CD] 
						 FROM rdb.[dbo].tmp_MorbFrmQDate
							group by [morb_rpt_uid], [from_time] , [CD] 
								) AS j PIVOT (max(from_time) FOR [CD] in 
							   ('+STUFF(REPLACE(@columns, ', p.[', ',['), 1, 1, '')+')) AS p;';

						print @sql;
						EXEC sp_executesql @sql;

						/*
						proc transpose data = MorbFrmQTxt out =MorbFrmQTxt2 (drop= _name_ _label_);
							id cd;
							var value_txt;
							by morb_rpt_uid;
						run;

						*/



			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_MorbFrmQTxt2'; 


			IF OBJECT_ID('rdb.dbo.tmp_MorbFrmQTxt2', 'U') IS NOT NULL 
								drop table rdb..tmp_MorbFrmQTxt2;


						--DECLARE @columns NVARCHAR(MAX);
						--DECLARE @sql NVARCHAR(MAX);

						SET @columns = N'';

						SELECT @columns+=N', p.'+QUOTENAME(LTRIM(RTRIM([CD])))
						FROM
						(
							SELECT [CD] 
							FROM rdb.[dbo].tmp_MorbFrmQTxt AS p
							GROUP BY [CD]
						) AS x;
						SET @sql = N'
						SELECT [morb_rpt_uid] as morb_rpt_uid_txt, '+STUFF(@columns, 1, 2, '')+
						' into rdb.dbo.tmp_MorbFrmQTxt2 ' +
						'FROM (
						SELECT [morb_rpt_uid], [value_txt] , [CD] 
						 FROM rdb.[dbo].tmp_MorbFrmQTxt
							group by [morb_rpt_uid], [value_txt] , [CD] 
								) AS j PIVOT (max(value_txt) FOR [CD] in 
							   ('+STUFF(REPLACE(@columns, ', p.[', ',['), 1, 1, '')+')) AS p;';

						print @sql;
						EXEC sp_executesql @sql;



			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_MorbFrmQCoded2'; 


			IF OBJECT_ID('rdb.dbo.tmp_MorbFrmQCoded2', 'U') IS  NULL 
								create table rdb..tmp_MorbFrmQCoded2 (	morb_rpt_uid_coded [bigint] NOT NULL
						) ON [PRIMARY]
						;

			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_MorbFrmQDate2'; 


			IF OBJECT_ID('rdb.dbo.tmp_MorbFrmQDate2', 'U') IS  NULL 
								create table rdb..tmp_MorbFrmQDate2 (	morb_rpt_uid_date [bigint] NOT NULL
						) ON [PRIMARY]
						;

			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_MorbFrmQTxt2'; 


			IF OBJECT_ID('rdb.dbo.tmp_MorbFrmQTxt2', 'U') IS  NULL 
								create table rdb..tmp_MorbFrmQTxt2 (	morb_rpt_uid_txt [bigint] NOT NULL
						) ON [PRIMARY]
						;


			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_Morbidity_Report'; 


			IF OBJECT_ID('rdb.dbo.tmp_Morbidity_Report', 'U') IS NOT NULL 
								drop table rdb..tmp_Morbidity_Report;


						/*

						data Morbidity_Report;
							merge Morb_Root MorbFrmQCoded2 MorbFrmQDate2 MorbFrmQTxt2;
							by morb_rpt_uid;
						run;
						*/

						select mr.*, tmc2.*, tmd2.*,tmt2.*,
						Cast( null as datetime) as TEMP_ILLNESS_ONSET_DT_KEY,
						Cast( null as datetime) as TEMP_DIAGNOSIS_DT_KEY,
						Cast( null as datetime) as DIAGNOSIS_DT,
						Cast( null as datetime) as HSPTL_ADMISSION_DT,
						Cast( null as datetime) as TEMP_HSPTL_DISCHARGE_DT_KEY,
						Cast( null as VARCHAR(50)) as HOSPITALIZED_IND,
						Cast( null as VARCHAR(50)) as DIE_FROM_ILLNESS_IND,
						Cast( null as VARCHAR(50)) as DAYCARE_IND,
						Cast( null as VARCHAR(50)) as FOOD_HANDLER_IND,
						Cast( null as VARCHAR(50)) as PREGNANT_IND,
						Cast( null as VARCHAR(50)) as HEALTHCARE_ORG_ASSOCIATE_IND,
						Cast( null as VARCHAR(50)) as SUSPECT_FOOD_WTRBORNE_ILLNESS,
						Cast( null as VARCHAR(20)) as MORB_RPT_TYPE,
						Cast( null as VARCHAR(20)) as MORB_RPT_DELIVERY_METHOD,
						Cast( null as VARCHAR(2000)) as MORB_RPT_COMMENTS,
						Cast( null as VARCHAR(2000)) as MORB_RPT_OTHER_SPECIFY,
						Cast( null as VARCHAR(1)) as NURSING_HOME_ASSOCIATE_IND,
						Cast( null as datetime)  as RDB_LAST_REFRESH_TIME 
						into rdb..tmp_Morbidity_Report
						from RDB..TMP_morb_root mr
						full outer join rdb..tmp_MorbFrmQCoded2 tmc2 on mr.morb_rpt_uid = tmc2.morb_rpt_uid_coded
						full outer join rdb..tmp_MorbFrmQDate2 tmd2  on mr.morb_rpt_uid = tmd2.morb_rpt_uid_date
						full outer join rdb..tmp_MorbFrmQTxt2 tmt2   on mr.morb_rpt_uid = tmt2.morb_rpt_uid_txt
						;



						/*


						data Morbidity_Report;
						format MRB122 MRB165 MRB166 MRB167 DATETIME20. ;
						format INV128 INV145 INV148 INV149 INV178 MRB130 MRB168 $50.;
						format MRB100 MRB161 $20. MRB102 MRB169 $2000.;

							INV128 = '';		
							INV145 = '';
							INV148 = '';
							INV149 = '';
							INV178 = '';
							MRB100 = '';
							MRB102 = '';
							MRB122 = .;
							MRB129 = '';
							MRB130 = '';
							MRB161 = '';
							MRB165 = .;
							MRB166 = .;
							MRB167 = .;
							MRB168 = '';
							MRB169 = '';
						*/


						/*
							set Morbidity_Report;
							if record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE' ;
							if record_status_cd = 'PROCESSED' then record_status_cd = 'ACTIVE' ;
							if record_status_cd = 'UNPROCESSED' then record_status_cd = 'ACTIVE' ;
							If record_status_cd = '' then record_status_cd = 'ACTIVE';
						run;
						*/


						UPDATE RDB..TMP_Morbidity_Report
						 SET record_status_cd = 'INACTIVE'
						 WHERE record_status_cd = 'LOG_DEL'
						 ;


 
						UPDATE RDB..TMP_Morbidity_Report
						 SET record_status_cd = 'ACTIVE'
						 WHERE record_status_cd in ( 'PROCESSED','UNPROCESSED') 
						 or rtrim(record_status_cd) is null
						 ;


						 ;


						/*Reason for not using lookup to find rdb column names
							1. Some columns in root obs. These columns must be hard coded, not suitable for lookup
							2. Same as above for Key columns, must be hard coded
							3. Unique id to Column name lookup table Not Reliable
						*/
						/*

						proc datasets lib=work nolist;
							modify Morbidity_Report;
							rename 
								/*These were no longer in the logical model*/
								INV128 = HOSPITALIZED_IND		
								INV145 = DIE_FROM_ILLNESS_IND
								INV148 = DAYCARE_IND
								INV149 = FOOD_HANDLER_IND
								INV178 = PREGNANT_IND
								MRB100 = MORB_RPT_TYPE
								MRB102 = MORB_RPT_COMMENTS
								MRB122 = TEMP_ILLNESS_ONSET_DT_KEY
								MRB129 = NURSING_HOME_ASSOCIATE_IND
								MRB130 = HEALTHCARE_ORG_ASSOCIATE_IND
								MRB161 = MORB_RPT_DELIVERY_METHOD
								MRB165 = TEMP_DIAGNOSIS_DT_KEY
								MRB166 = HSPTL_ADMISSION_DT
								MRB167 = TEMP_HSPTL_DISCHARGE_DT_KEY
								MRB168 = SUSPECT_FOOD_WTRBORNE_ILLNESS
								MRB169 = MORB_RPT_OTHER_SPECIFY
						;
						run;
						*/

						--UPDATE RDB..TMP_Morbidity_Report set	 HOSPITALIZED_IND	=	INV128 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'INV128') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 HOSPITALIZED_IND	=	INV128 	;
						END
						;

						--UPDATE RDB..TMP_Morbidity_Report set	 DIE_FROM_ILLNESS_IND	=	INV145 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'INV145') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 DIE_FROM_ILLNESS_IND	=	INV145 	;
						END
						;

						--UPDATE RDB..TMP_Morbidity_Report set	 DAYCARE_IND	=	INV148 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'INV148') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 DAYCARE_IND	=	INV148 	;
						END
						;

						--UPDATE RDB..TMP_Morbidity_Report set	 FOOD_HANDLER_IND	=	INV149 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'INV149') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 FOOD_HANDLER_IND	=	INV149 	;
						END
						;

						--UPDATE RDB..TMP_Morbidity_Report set	 PREGNANT_IND	=	INV178 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'INV178') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 PREGNANT_IND	=	INV178 	;
						END
						;

						--UPDATE RDB..TMP_Morbidity_Report set	 MORB_RPT_TYPE	=	MRB100 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'MRB100') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 MORB_RPT_TYPE	=	MRB100 	;
						END
						;

						--UPDATE RDB..TMP_Morbidity_Report set	 MORB_RPT_COMMENTS	=	rtrim(MRB102) 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'MRB102') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 MORB_RPT_COMMENTS	=	rtrim(MRB102) 	;
						END
						;

						--UPDATE RDB..TMP_Morbidity_Report set	 TEMP_ILLNESS_ONSET_DT_KEY	=	MRB122 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'MRB122') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 TEMP_ILLNESS_ONSET_DT_KEY	=	MRB122 	;
						END
						;

						--UPDATE RDB..TMP_Morbidity_Report set	 NURSING_HOME_ASSOCIATE_IND	=	substring(MRB129,1,1) 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'MRB129') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 NURSING_HOME_ASSOCIATE_IND	=	substring(MRB129,1,1)  	;
						END
						;

						--UPDATE RDB..TMP_Morbidity_Report set	 HEALTHCARE_ORG_ASSOCIATE_IND	=	MRB130 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'MRB130') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 HEALTHCARE_ORG_ASSOCIATE_IND	=	MRB130 	;
						END
						;


						--UPDATE RDB..TMP_Morbidity_Report set	 MORB_RPT_DELIVERY_METHOD	=	MRB161 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'MRB161') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 MORB_RPT_DELIVERY_METHOD	=	MRB161 	;
						END
						;

						--UPDATE RDB..TMP_Morbidity_Report set	 TEMP_DIAGNOSIS_DT_KEY	=	MRB165 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'MRB165') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 TEMP_DIAGNOSIS_DT_KEY	=	MRB165 	;
						END
						;



						--UPDATE RDB..TMP_Morbidity_Report set	 DIAGNOSIS_DT	=	MRB165 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'MRB165') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 DIAGNOSIS_DT	=	MRB165 	;
						END
						;


						--UPDATE RDB..TMP_Morbidity_Report set	 HSPTL_ADMISSION_DT	=	MRB166 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'MRB166') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 HSPTL_ADMISSION_DT	=	MRB166 	;
						END
						;



						--UPDATE RDB..TMP_Morbidity_Report set	 TEMP_HSPTL_DISCHARGE_DT_KEY	=	MRB167 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'MRB167') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 TEMP_HSPTL_DISCHARGE_DT_KEY	=	MRB167 	;
						END
						;

						--UPDATE RDB..TMP_Morbidity_Report set	 SUSPECT_FOOD_WTRBORNE_ILLNESS	=	MRB168 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'MRB168') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 SUSPECT_FOOD_WTRBORNE_ILLNESS	=	MRB168 	;
						END
						;

						--UPDATE RDB..TMP_Morbidity_Report set	 MORB_RPT_OTHER_SPECIFY	=	MRB169 	;
						IF(COL_LENGTH('rdb..TMP_Morbidity_Report', 'MRB169') IS  NOT NULL)
						BEGIN
						UPDATE RDB..TMP_Morbidity_Report set	 MORB_RPT_OTHER_SPECIFY	=	MRB169 	;
						END
						;




						/*-------------------------------------------------------

							morb_Report_User_Comment Dimension

							Note: Comments under the Order Test object (LAB214)
						---------------------------------------------------------*/




						create index IDX_morb_rpt_uid on RDB..TMP_Morbidity_Report(morb_rpt_uid);


						/*
						/* Texas - Moved code execution to database 08/20/2020 */
						PROC SQL;
						DROP TABLE RDB..SAS_morb_Rpt_User_Comment;
						DROP TABLE RDB..SAS_Morbidity_Report;
						QUIT;

						PROC SQL;
						CREATE TABLE RDB..SAS_Morbidity_Report AS SELECT * FROM Morbidity_Report;
						QUIT;

						PROC SQL;
						connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
						execute (CREATE INDEX morb_rpt_uid ON SAS_Morbidity_Report(morb_rpt_uid)) by sql;
						disconnect from sql;
						QUIT;
						*/



						 -- (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  SAS_Morbidity_Report'; 


			IF OBJECT_ID('rdb.dbo.tmp_SAS_Morbidity_Report', 'U') IS NOT NULL 
								drop table rdb..tmp_SAS_Morbidity_Report;

/*						insert into rdb..tmp_SAS_Morbidity_Report
						([TEMP_ILLNESS_ONSET_DT_KEY]
							  ,[TEMP_DIAGNOSIS_DT_KEY]
							  ,[HSPTL_ADMISSION_DT]
							  ,[TEMP_HSPTL_DISCHARGE_DT_KEY]
							  ,[HOSPITALIZED_IND]
							  ,[DIE_FROM_ILLNESS_IND]
							  ,[DAYCARE_IND]
							  ,[FOOD_HANDLER_IND]
							  ,[PREGNANT_IND]
							  ,[HEALTHCARE_ORG_ASSOCIATE_IND]
							  ,[SUSPECT_FOOD_WTRBORNE_ILLNESS]
							  ,[MORB_RPT_TYPE]
							  ,[MORB_RPT_DELIVERY_METHOD]
							  ,[MORB_RPT_COMMENTS]
							  ,[MORB_RPT_OTHER_SPECIFY]
							  ,[NURSING_HOME_ASSOCIATE_IND]
							  ,[morb_Rpt_Key]
							  ,[morb_rpt_local_id]
							  ,[morb_rpt_share_ind]
							  ,[morb_rpt_oid]
							  ,[morb_RPT_Created_DT]
							  ,[morb_RPT_Create_BY]
							  ,[PH_RECEIVE_DT]
							  ,[morb_RPT_LAST_UPDATE_DT]
							  ,[morb_RPT_LAST_UPDATE_BY]
							  ,[Jurisdiction_cd]
							  ,[Jurisdiction_nm]
							  ,[morb_report_date]
							  ,[Condition_cd]
							  ,[morb_rpt_uid]
							  ,[ELECTRONIC_IND]
							  ,[record_status_cd]
							  ,[processing_decision_cd]
							  ,[PROCESSING_DECISION_DESC])
*/

						SELECT [TEMP_ILLNESS_ONSET_DT_KEY]
							  ,[TEMP_DIAGNOSIS_DT_KEY]
							  ,[HSPTL_ADMISSION_DT]
							  ,[TEMP_HSPTL_DISCHARGE_DT_KEY]
							  ,[HOSPITALIZED_IND]
							  ,[DIE_FROM_ILLNESS_IND]
							  ,[DAYCARE_IND]
							  ,[FOOD_HANDLER_IND]
							  ,[PREGNANT_IND]
							  ,[HEALTHCARE_ORG_ASSOCIATE_IND]
							  ,[SUSPECT_FOOD_WTRBORNE_ILLNESS]
							  ,[MORB_RPT_TYPE]
							  ,[MORB_RPT_DELIVERY_METHOD]
							  ,[MORB_RPT_COMMENTS]
							  ,[MORB_RPT_OTHER_SPECIFY]
							  ,[NURSING_HOME_ASSOCIATE_IND]
							  ,[morb_Rpt_Key]
							  ,[morb_rpt_local_id]
							  ,[morb_rpt_share_ind]
							  ,[morb_rpt_oid]
							  ,[morb_RPT_Created_DT]
							  ,[morb_RPT_Create_BY]
							  ,[PH_RECEIVE_DT]
							  ,[morb_RPT_LAST_UPDATE_DT]
							  ,[morb_RPT_LAST_UPDATE_BY]
							  ,[Jurisdiction_cd]
							  ,[Jurisdiction_nm]
							  ,[morb_report_date]
							  ,[Condition_cd]
							  ,[morb_rpt_uid]
							  ,[ELECTRONIC_IND]
							  ,[record_status_cd]
							  ,[processing_decision_cd]
							  ,[PROCESSING_DECISION_DESC] 
						into rdb..tmp_SAS_Morbidity_Report
						FROM rdb..tmp_Morbidity_Report
						;


						--create index IDX_sas_morb_rpt_uid on RDB..SAS_Morbidity_Report(morb_rpt_uid);



			     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  SAS_morb_Rpt_User_Comment'; 


			IF OBJECT_ID('rdb.dbo.SAS_morb_Rpt_User_Comment', 'U') IS NOT NULL 
								drop table rdb..SAS_morb_Rpt_User_Comment;


						/*
						PROC SQL;
						connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
						EXECUTE (
						*/


        

								select 	root.morb_Rpt_Key,
									root.morb_rpt_uid,
									obs.activity_to_time	 as user_comments_dt,
									mrb180.add_user_id		 as user_comments_by,
									REPLACE(ovt.value_txt,'0D0A',' ') as external_morb_rpt_comments,  /* TRANSLATE(ovt.value_txt,' ' ,'0D0A'x) 'EXTERNAL_MORB_RPT_COMMENTS' as external_morb_rpt_comments, */
									root.record_status_cd 
							into  RDB..SAS_morb_Rpt_User_Comment
							from 	rdb..tmp_SAS_Morbidity_Report			as root,
									nbs_odse.dbo.act_relationship 	as ar1,
									nbs_odse.dbo.observation			as obs,
									nbs_odse.dbo.act_relationship 	as ar2,
									nbs_odse.dbo.observation			as mrb180,
									nbs_odse.dbo.obs_value_txt 				as ovt
							where   ovt.value_txt is not null
									and root.morb_rpt_uid = ar1.target_act_uid
									and ar1.type_cd = 'APND'
									and ar1.source_act_uid = obs.observation_uid
									and obs.OBS_DOMAIN_CD_ST_1 ='C_Order'
									and obs.CTRL_CD_DISPLAY_FORM ='MorbComment'
									and obs.observation_uid = ar2.target_act_uid
									and ar2.source_act_uid = mrb180.observation_uid
									and ar2.type_cd = 'COMP'
									and mrb180.OBS_DOMAIN_CD_ST_1 ='C_Result'
									and mrb180.observation_uid = ovt.observation_uid

							   ;





									--create table morb_Rpt_User_Comment as 



						     SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_morb_Rpt_User_Comment'; 


			IF OBJECT_ID('rdb.dbo.tmp_morb_Rpt_User_Comment', 'U') IS NOT NULL 
											drop table rdb..tmp_morb_Rpt_User_Comment;



									CREATE TABLE rdb.[dbo].[tmp_morb_Rpt_User_Comment](
										User_Comment_Key_id  [int] IDENTITY(1,1) NOT NULL,
										[morb_Rpt_Key] [int] NULL,
										[morb_rpt_uid] [bigint] NULL,
										[user_comments_dt] [datetime] NULL,
										[user_comments_by] [bigint] NULL,
										[external_morb_rpt_comments] [varchar](8000) NULL,
										[record_status_cd] [varchar](20) NULL,
										User_Comment_key int
									) ON [PRIMARY]

									;


									insert into rdb.[dbo].[tmp_morb_Rpt_User_Comment]
									( [morb_Rpt_Key]
										  ,[morb_rpt_uid]
										  ,[user_comments_dt]
										  ,[user_comments_by]
										  ,[external_morb_rpt_comments]
										  ,[record_status_cd]
										  )
									select distinct [morb_Rpt_Key]
										  ,[morb_rpt_uid]
										  ,[user_comments_dt]
										  ,[user_comments_by]
										  ,[external_morb_rpt_comments]
										  ,[record_status_cd]
									from RDB..SAS_morb_Rpt_User_Comment
									;

			
									UPDATE rdb.dbo.[tmp_morb_Rpt_User_Comment] 
										  SET User_Comment_key= User_Comment_Key_id + coalesce((SELECT MAX(User_Comment_key) FROM RDB.dbo.morb_rpt_user_comment),0)
										   ;


								   /*  
								  delete from rdb.dbo.tmp_morb_Rpt_User_Comment where USER_COMMENT_KEY=1 and USER_COMMENT_KEY_MAX_VAL >0;
								  delete from rdb.dbo.tmp_morb_Rpt_User_Comment where USER_COMMENT_KEY=1 and USER_COMMENT_KEY_MAX_VAL is null ;
								  delete from rdb.dbo.tmp_morb_Rpt_User_Comment where morb_rpt_KEY is null;           
								  */

						/*

						%assign_key(morb_Rpt_User_Comment, User_Comment_key);


						DATA morb_rpt_user_comment;
						set morb_rpt_user_comment;
						if morb_rpt_key = . then morb_rpt_key = 1;
						run;

						proc sql;
						ALTER TABLE morb_rpt_user_comment ADD User_Comment_key_MAX_VAL NUMERIC;
						UPDATE  morb_rpt_user_comment SET User_Comment_key_MAX_VAL=(SELECT MAX(User_Comment_key) FROM RDB..morb_rpt_user_comment);
						quit;
						DATA  morb_rpt_user_comment;
						SET  morb_rpt_user_comment;
						IF User_Comment_key_MAX_VAL  ~=. THEN User_Comment_key= User_Comment_key+User_Comment_key_MAX_VAL;
						RUN;


						*/




						/*-------------------------------------------------------

							MORBIDITY_REPORT_Event( Keys table )

						---------------------------------------------------------*/

			 SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES
    		(@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			

			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Generating  tmp_MORBIDITY_REPORT_Event_Final'; 


			IF OBJECT_ID('rdb.dbo.tmp_MORBIDITY_REPORT_Event_Final', 'U') IS NOT NULL 
											drop table rdb..tmp_MORBIDITY_REPORT_Event_Final;

						--create table tmp_MORBIDITY_REPORT_Event_Final as 




						select 	pat.PATIENT_Key				'PATIENT_KEY' ,
								con.CONDITION_KEY,
								--con.condition_cd,
			
								coalesce(org1.Organization_key,1)				as HEALTH_CARE_KEY,
								coalesce(dt3.Date_key,1)	as HSPTL_DISCHARGE_DT_KEY,
								org2.Organization_key				as HSPTL_KEY,
								coalesce(dt4.Date_key,1)	as ILLNESS_ONSET_DT_KEY,
								inv.INVESTIGATION_KEY,
								rpt.morb_Rpt_Key,

								coalesce(dt5.Date_key,1)	as MORB_RPT_CREATE_DT_KEY,
								coalesce(dt6.Date_key,1)	as MORB_RPT_DT_KEY,
		
								org3.Organization_Key				as MORB_RPT_SRC_ORG_KEY,
								coalesce(phy.provider_key,1)		as PHYSICIAN_KEY,
								per1.provider_key				as REPORTER_KEY,
								--'' as LDF_GROUP_KEY, --VS 
								coalesce(ldf_g.ldf_group_key,1) as LDF_GROUP_KEY,
								1							as Morb_Rpt_Count,
								1							as Nursing_Home_Key, /*cannot find mapping*/
								rpt.record_status_cd
							into rdb..tmp_MORBIDITY_REPORT_Event_Final
							from rdb..tmp_Morbidity_Report	rpt
    							/*PATIENT_KEY*/
							left join nbs_odse..participation 	par on rpt.morb_rpt_uid = par.act_uid
								and par.type_cd = 'SubjOfMorbReport'
								and par.subject_class_cd = 'PSN'
								and par.act_class_cd ='OBS'
								and par.record_status_cd = 'ACTIVE'
							left join RDB..d_patient pat on par.subject_entity_uid = pat.patient_uid
							left join rdb..condition	con on  rpt.condition_cd =con.condition_cd	AND rtrim(con.condition_cd) != ''
								/*HEALTH_CARE_KEY   */
							left join nbs_odse..participation par1 on rpt.morb_rpt_uid = par1.act_uid and par1.type_cd = 'HCFAC'
							left join RDB..d_Organization org1 on org1.Organization_uid = par1.subject_entity_uid
								/*HSPTL_DISCHARGE_DT_KE*/
							left join rdb..rdb_date	dt3	on rpt.TEMP_HSPTL_DISCHARGE_DT_KEY = dt3.DATE_MM_DD_YYYY
								/*	HSPTL_KEY*/
							left join nbs_odse..participation 	par2 on rpt.morb_rpt_uid = par2.act_uid	and par2.type_cd = 'HospOfMorbObs'
								AND par2.subject_class_cd = 'ORG'
							left join RDB..d_Organization org2 on par2.subject_entity_uid = org2.Organization_uid	
	
							/*ILLNESS_ONSET_DT_KEY*/
							left join rdb..rdb_date	dt4	on rpt.TEMP_ILLNESS_ONSET_DT_KEY = dt4.DATE_MM_DD_YYYY
	
							/* INVESTIGATION_KEY  */
							left join nbs_odse..act_relationship ar1 on rpt.morb_rpt_uid = ar1.source_act_uid and ar1.type_cd = 'MorbReport'
								and ar1.source_class_cd ='OBS'
								and ar1.target_class_cd ='CASE'
								and ar1.record_status_cd = 'ACTIVE'
							left join RDB..Investigation inv on ar1.target_act_uid = inv.case_uid
								/*MORB_RPT_CREATE_DT_KEY*/
							left join rdb..rdb_date	dt5	on CAST(CONVERT(VARCHAR,rpt.morb_RPT_Created_DT,102) AS DATETIME)  = dt5.DATE_MM_DD_YYYY
								/*MORB_RPT_DT_KEY*/
							left join rdb..rdb_date	dt6	on rpt.morb_report_date = dt6.DATE_MM_DD_YYYY
								/*MORB_RPT_SRC_ORG_KEY */
							left join nbs_odse..participation par3 on rpt.morb_rpt_uid = par3.act_uid
								and par3.type_cd = 'ReporterOfMorbReport'
								and par3.subject_class_cd ='ORG'
							left join RDB..d_Organization org3	on par3.subject_entity_uid = org3.Organization_uid
								/*PHYSICIAN_KEY*/
							left join nbs_odse..participation par4 on rpt.morb_rpt_uid = par4.act_uid
								and par4.type_cd = 'PhysicianOfMorb'
								and par4.subject_class_cd = 'PSN'
								and par4.act_class_cd ='OBS'
								and par4.record_status_cd = 'ACTIVE'
							left join RDB..d_provider phy	on par4.subject_entity_uid = phy.provider_uid
								/*	REPORTER_KEY           */
							left join nbs_odse..participation par6	on rpt.morb_rpt_uid = par6.act_uid
								and par6.type_cd = 'ReporterOfMorbReport'
								and par6.subject_class_cd = 'PSN'
								and par6.act_class_cd ='OBS'
								and par6.record_status_cd = 'ACTIVE'
							left join RDB..d_provider per1	on par6.subject_entity_uid = per1.provider_uid
								/*Ldf group key*/		
							 left join rdb..ldf_group as ldf_g on rpt.morb_rpt_uid = ldf_g.business_object_uid

						;

						
			 SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES 		(@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;

			
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' UPDATE rdb.dbo.tmp_Morbidity_Report '; 

			/*

						/*Need this because there is bad data existing in ODS...once the bad data 
						is removed this code will not execute*/
						/*data ;
						set MORBIDITY_REPORT_Event;
						if lab_test_key =. then lab_test_key =1;
						run;*/

						data morbidity_report 
							(drop = /*TEMP_PH_RECEIVE_DT_KEY*/
									TEMP_ILLNESS_ONSET_DT_KEY
									/*TEMP_DIAGNOSIS_DT_KEY*/
									/*TEMP_HSPTL_ADMISSION_DT_KEY*/
									TEMP_HSPTL_DISCHARGE_DT_KEY
									/*DIE_FROM_ILLNESS_IND
									DAYCARE_IND
									FOOD_HANDLER_IND
									PREGNANT_IND*/
									morb_RPT_Created_DT
									morb_report_date
									Condition_cd
									/*HOSPITALIZED_IND*/
									/*ELECTRONIC_IND*/

							);

							set morbidity_report;
						run;
						data morbidity_report 
							(rename = (TEMP_DIAGNOSIS_DT_KEY = DIAGNOSIS_DT))
							;
							set morbidity_report;
						data rdb..Morbidity_Report;
							set Morbidity_Report;
						run;
						proc sql;
						delete from rdb..MORBIDITY_REPORT where morb_rpt_uid is null;
						quit;
						*/


						/*
						alter table rdb..tmp_morbidity_report 
							drop column
									TEMP_ILLNESS_ONSET_DT_KEY
									,TEMP_HSPTL_DISCHARGE_DT_KEY
									,morb_RPT_Created_DT
									,morb_report_date
									,Condition_cd
									;

                        */
						/*

						DATA rdb..MORBIDITY_REPORT;

						SET rdb..MORBIDITY_REPORT;
						RDB_LAST_REFRESH_TIME=DATETIME();
						RUN;
						%dbload (MORBIDITY_REPORT, rdb..MORBIDITY_REPORT);
						*/


   							UPDATE rdb.dbo.tmp_Morbidity_Report  
							 set PROCESSING_DECISION_CD  = null where rtrim(PROCESSING_DECISION_CD) = ''
							 ;

							update rdb..tmp_Morbidity_Report
							set RDB_LAST_REFRESH_TIME = GETDATE();


							--alter table rdb..tmp_Morbidity_Report
							 --  drop column
								 --  [morb_Rpt_Key_id]
								--  [PROVIDER_KEY]
								 -- [morb_rpt_uid_coded]
								  --,[INV128]
								  --,[INV145]
								  --,[INV148]
								 -- ,[INV149]
								 -- ,[INV178]
								 -- ,[MRB100]
								--  ,[MRB129]
								 -- ,[MRB130]
								--  ,[MRB161]
								--  ,[MRB168]
								 -- ,[morb_rpt_uid_date]
								--  ,[MRB122]
								  --,[MRB165]
								  --,[MRB166]
								  --,[MRB167]
								 -- ,[morb_rpt_uid_txt]
								  --,[MRB102]
								  --,[MRB169]
							   ;


                      
						
						 SELECT @RowCount_no = @@ROWCOUNT;

						 INSERT INTO rdb.[dbo].[job_flow_log] 
							(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
							VALUES
    					(@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

						COMMIT TRANSACTION;
		
						BEGIN TRANSACTION;

						SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
						SET @PROC_STEP_NAME = 'Inserting into rdb.dbo.Morbidity_Report '; 


						insert  into rdb..Morbidity_Report
						([MORB_RPT_KEY]
							  ,[MORB_RPT_UID]
							  ,[MORB_RPT_LOCAL_ID]
							  ,[MORB_RPT_SHARE_IND]
							  ,[MORB_RPT_OID]
							  ,[MORB_RPT_TYPE]
							  ,[MORB_RPT_COMMENTS]
							  ,[MORB_RPT_DELIVERY_METHOD]
							  ,[SUSPECT_FOOD_WTRBORNE_ILLNESS]
							  ,[MORB_RPT_OTHER_SPECIFY]
							  ,[NURSING_HOME_ASSOCIATE_IND]
							  ,[JURISDICTION_CD]
							  ,[JURISDICTION_NM]
							  ,[HEALTHCARE_ORG_ASSOCIATE_IND]
							  ,[MORB_RPT_CREATE_BY]
							  ,[MORB_RPT_LAST_UPDATE_DT]
							  ,[MORB_RPT_LAST_UPDATE_BY]
							  ,[DIAGNOSIS_DT]
							  ,[HSPTL_ADMISSION_DT]
							  ,[PH_RECEIVE_DT]
							  ,[DIE_FROM_ILLNESS_IND]
							  ,[HOSPITALIZED_IND]
							  ,[PREGNANT_IND]
							  ,[FOOD_HANDLER_IND]
							  ,[DAYCARE_IND]
							  ,[ELECTRONIC_IND]
							  ,[RECORD_STATUS_CD]
							  ,[RDB_LAST_REFRESH_TIME]
							  ,[PROCESSING_DECISION_CD]
							  ,[PROCESSING_DECISION_DESC])
						SELECT [MORB_RPT_KEY]
							    ,MORB_RPT_UID
								, substring(MORB_RPT_LOCAL_ID ,1,50) 
								,MORB_RPT_SHARE_IND
								,MORB_RPT_OID
								, substring(MORB_RPT_TYPE ,1,50) 
								, substring(MORB_RPT_COMMENTS ,1,2000) 
								, substring(MORB_RPT_DELIVERY_METHOD ,1,50) 
								, substring(SUSPECT_FOOD_WTRBORNE_ILLNESS ,1,50) 
								, substring(MORB_RPT_OTHER_SPECIFY ,1,2000) 
								, substring(NURSING_HOME_ASSOCIATE_IND ,1,50) 
								, substring(JURISDICTION_CD ,1,20) 
								, substring(JURISDICTION_NM ,1,100) 
								, substring(HEALTHCARE_ORG_ASSOCIATE_IND ,1,50) 
								,MORB_RPT_CREATE_BY
								,MORB_RPT_LAST_UPDATE_DT
								,MORB_RPT_LAST_UPDATE_BY
								,DIAGNOSIS_DT
								,HSPTL_ADMISSION_DT
								,PH_RECEIVE_DT
								, substring(DIE_FROM_ILLNESS_IND ,1,50) 
								, substring(HOSPITALIZED_IND ,1,50) 
								, substring(PREGNANT_IND ,1,50) 
								, substring(FOOD_HANDLER_IND ,1,50) 
								, substring(DAYCARE_IND ,1,50) 
								, substring(ELECTRONIC_IND ,1,50) 
								, substring(RECORD_STATUS_CD ,1,8) 
								,RDB_LAST_REFRESH_TIME
								, substring(PROCESSING_DECISION_CD ,1,50) 
								, substring(PROCESSING_DECISION_DESC ,1,50) 

						FROM rdb..tmp_Morbidity_Report
						;

									  
			SELECT @ROWCOUNT_NO = @@ROWCOUNT;
		     INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
				(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
				VALUES(@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',  @PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);


			COMMIT TRANSACTION;


						insert into RDB.dbo.Morbidity_Report (morb_rpt_KEY,[RECORD_STATUS_CD])
						select 1,'ACTIVE'
						WHERE not exists (SELECT (morb_rpt_KEY) FROM RDB.dbo.Morbidity_Report where morb_rpt_KEY = 1)
						;


						/*
						proc sql;
						delete from morb_Rpt_User_Comment where USER_COMMENT_KEY=1 and USER_COMMENT_KEY_MAX_VAL >0;
						delete from morb_Rpt_User_Comment where USER_COMMENT_KEY=1 and USER_COMMENT_KEY_MAX_VAL =.;
						delete from morb_Rpt_User_Comment where morb_rpt_KEY=.;
						quit;
						PROC SQL;



						data rdb..morb_Rpt_User_Comment;
							set morb_Rpt_User_Comment;
							If record_status_cd = '' then record_status_cd = 'ACTIVE';
						run;
						DATA rdb..MORB_RPT_USER_COMMENT;
						SET rdb..MORB_RPT_USER_COMMENT;
						RDB_LAST_REFRESH_TIME=DATETIME();
						RUN;
						%dbload (MORB_RPT_USER_COMMENT, rdb..MORB_RPT_USER_COMMENT);
						PROC SQL;



						data MORBIDITY_REPORT_Event (drop= condition_cd);
							set MORBIDITY_REPORT_Event;
							if patient_key =. then patient_key =1;
							if condition_key =. then condition_key=1;
							if investigation_key =. then investigation_key=1;
							if MORB_RPT_SRC_ORG_KEY=. then MORB_RPT_SRC_ORG_KEY=1;
							if HSPTL_KEY=. then HSPTL_KEY=1;
							if HEALTH_CARE_KEY=. then HEALTH_CARE_KEY=1;
							if PHYSICIAN_KEY=. then PHYSICIAN_KEY=1;
							if REPORTER_KEY=. then REPORTER_KEY=1;
							if Nursing_Home_Key=. then Nursing_Home_Key=1;
						run;

						/*if treatment_key = . then treatment_key =1;*/
						data rdb..MORBIDITY_REPORT_Event;
							set MORBIDITY_REPORT_Event;
						run;
						proc sql;
						delete from rdb..MORBIDITY_REPORT_Event where morb_rpt_key is null;
						quit;
						proc sort data = rdb..MORBIDITY_REPORT_Event;
							by morb_rpt_key;
						run;
						DATA rdb..MORBIDITY_REPORT_Event;
						SET rdb..MORBIDITY_REPORT_Event;
						RDB_LAST_REFRESH_TIME=DATETIME();
						RUN;

						%dbload (MORBIDITY_REPORT_Event, rdb..MORBIDITY_REPORT_Event);



						/**Delete temporary data sets**/
						PROC datasets library = work nolist;
						delete
						Morb_Root
						MorbFrmQ
						MorbFrmQCoded
						MorbFrmQDate
						MorbFrmQTxt
						MorbFrmQCoded2
						MorbFrmQDate2
						MorbFrmQTxt2
						Morbidity_Report
						morb_Rpt_User_Comment
						MORBIDITY_REPORT_Event;
						run;
						quit;
						*/




			  --(@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

           
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' inserting iNTO MORBIDITY_REPORT_Event'; 




						--create table tmp_MORBIDITY_REPORT_Event_Final as 


							 insert into  rdb.dbo.MORBIDITY_REPORT_Event
						  ( [PATIENT_KEY]
							  ,[Condition_Key]
							  ,[HEALTH_CARE_KEY]
							  ,[HSPTL_DISCHARGE_DT_KEY]
							  ,[HSPTL_KEY]
							  ,[ILLNESS_ONSET_DT_KEY]
							  ,[INVESTIGATION_KEY]
							  ,[morb_Rpt_Key]
							  ,[MORB_RPT_CREATE_DT_KEY]
							  ,[MORB_RPT_DT_KEY]
							  ,[MORB_RPT_SRC_ORG_KEY]
							  ,[PHYSICIAN_KEY]
							  ,[REPORTER_KEY]
							  ,[LDF_GROUP_KEY]
							  ,[Morb_Rpt_Count]
							  ,[Nursing_Home_Key]
							  ,[record_status_cd]
							  )
							SELECT  [PATIENT_KEY]
							  ,coalesce([Condition_Key],'')
							  ,coalesce([HEALTH_CARE_KEY],'')
							  ,coalesce([HSPTL_DISCHARGE_DT_KEY],'')
							  ,coalesce([HSPTL_KEY],'1')
							  ,coalesce([ILLNESS_ONSET_DT_KEY],'')
							  ,coalesce([INVESTIGATION_KEY],'1')
							  ,coalesce([morb_Rpt_Key],'')
							  ,coalesce([MORB_RPT_CREATE_DT_KEY],'')
							  ,coalesce([MORB_RPT_DT_KEY],'')
							  ,coalesce([MORB_RPT_SRC_ORG_KEY],1)
							  ,coalesce([PHYSICIAN_KEY],'')
							  ,coalesce([REPORTER_KEY],'1')
							  ,[LDF_GROUP_KEY]
							  ,[Morb_Rpt_Count]
							  ,[Nursing_Home_Key]
							  ,substring(RECORD_STATUS_CD ,1,8) 
							  
						  FROM [RDB].[dbo].tmp_MORBIDITY_REPORT_Event_Final
						  ;

			
				SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES  (@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
			BEGIN TRANSACTION;

			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;
			SET @PROC_STEP_NAME = ' Insert into morb_Rpt_User_Comment'; 


	
	
								insert into rdb..morb_Rpt_User_Comment
									(
								  	[MORB_RPT_UID]
									,[USER_COMMENT_KEY]
									,[MORB_RPT_KEY]
									,[EXTERNAL_MORB_RPT_COMMENTS]
									,[USER_COMMENTS_BY]
									,[USER_COMMENTS_DT]
									,[RECORD_STATUS_CD]
									,[RDB_LAST_REFRESH_TIME]
									)
									select MORB_RPT_UID
											,USER_COMMENT_KEY
											,MORB_RPT_KEY
											, substring(rtrim(EXTERNAL_MORB_RPT_COMMENTS) ,1,2000) 
											,USER_COMMENTS_BY
											,USER_COMMENTS_DT
											, substring(RECORD_STATUS_CD ,1,8) 
											,getdate() as [RDB_LAST_REFRESH_TIME]
									from rdb..[tmp_morb_Rpt_User_Comment]
									;

			
			 SELECT @RowCount_no = @@ROWCOUNT;

		     INSERT INTO rdb.[dbo].[job_flow_log] 
				(batch_id,[Dataflow_Name],[package_Name] ,[Status_Type],[step_number],[step_name],[row_count])
				VALUES
    		(@BATCH_ID,'D_Morbidity_Report','RDB.D_Morbidity_Report','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION;
		
    		IF OBJECT_ID('rdb..tmp_Morbidity_Report', 'U') IS NOT NULL  drop table    	rdb..tmp_Morbidity_Report	;
			IF OBJECT_ID('rdb..tmp_updt_MORBIDITY_REPORT_list', 'U') IS NOT NULL  drop table    	rdb..tmp_updt_MORBIDITY_REPORT_list 	;
			IF OBJECT_ID('rdb..tmp_SAS_updt_MORBIDITY_REPORT_list', 'U') IS NOT NULL  drop table    	rdb..tmp_SAS_updt_MORBIDITY_REPORT_list 	;
			IF OBJECT_ID('rdb..tmp_updt_MORBIDITY_REPORT_Event_list', 'U') IS NOT NULL  drop table    	rdb..tmp_updt_MORBIDITY_REPORT_Event_list 	;
			IF OBJECT_ID('rdb..tmp_SAS_up_MORBIDITY_RPT_EVNT_lst', 'U') IS NOT NULL  drop table    	rdb..tmp_SAS_up_MORBIDITY_RPT_EVNT_lst 	;
			IF OBJECT_ID('rdb..tmp_UPDT_MORB_RPT_USER_COMMENT_LIST', 'U') IS NOT NULL  drop table    	rdb..tmp_UPDT_MORB_RPT_USER_COMMENT_LIST 	;
			IF OBJECT_ID('rdb..tmp_Morb_Root', 'U') IS NOT NULL  drop table    	rdb..tmp_Morb_Root 	;
			IF OBJECT_ID('rdb..tmp_MorbFrmQ', 'U') IS NOT NULL  drop table    	rdb..tmp_MorbFrmQ 	;
			IF OBJECT_ID('rdb..tmp_MorbFrmQCoded', 'U') IS NOT NULL  drop table    	rdb..tmp_MorbFrmQCoded 	;
			IF OBJECT_ID('rdb..tmp_MorbFrmQDate', 'U') IS NOT NULL  drop table    	rdb..tmp_MorbFrmQDate 	;
			IF OBJECT_ID('rdb..tmp_MorbFrmQTxt', 'U') IS NOT NULL  drop table    	rdb..tmp_MorbFrmQTxt 	;
			IF OBJECT_ID('rdb..tmp_MorbFrmQCoded2', 'U') IS NOT NULL  drop table    	rdb..tmp_MorbFrmQCoded2 	;
			IF OBJECT_ID('rdb..tmp_MorbFrmQDate2', 'U') IS NOT NULL  drop table    	rdb..tmp_MorbFrmQDate2 	;
			IF OBJECT_ID('rdb..tmp_MorbFrmQTxt2', 'U') IS NOT NULL  drop table    	rdb..tmp_MorbFrmQTxt2	;
			IF OBJECT_ID('rdb..tmp_Morbidity_Report', 'U') IS NOT NULL  drop table    	rdb..tmp_Morbidity_Report	;
			IF OBJECT_ID('rdb..tmp_SAS_Morbidity_Report', 'U') IS NOT NULL  drop table    	rdb..tmp_SAS_Morbidity_Report	;
			IF OBJECT_ID('rdb..SAS_morb_Rpt_User_Comment', 'U') IS NOT NULL  drop table    	rdb..SAS_morb_Rpt_User_Comment	;
			IF OBJECT_ID('rdb..tmp_morb_Rpt_User_Comment', 'U') IS NOT NULL  drop table    	rdb..tmp_morb_Rpt_User_Comment	;
			IF OBJECT_ID('rdb..tmp_MORBIDITY_REPORT_Event_Final', 'U') IS NOT NULL  drop table    	rdb..tmp_MORBIDITY_REPORT_Event_Final	;

            BEGIN TRANSACTION; 
			SET @PROC_STEP_NO =  @PROC_STEP_NO + 1 ;

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
						   VALUES  				   (
						   @batch_id,
						   'D_Morbidity_Report'
						   ,'RDB.D_Morbidity_Report'
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
           ,'D_Morbidity_Report'
           ,'RDB.D_Morbidity_Report'
		   ,'ERROR'
		   ,@Proc_Step_no
		   ,'ERROR - '+ @Proc_Step_name
           , 'Step -' +CAST(@Proc_Step_no AS VARCHAR(3))+' -' +CAST(@ErrorMessage AS VARCHAR(500))
           ,0
		   );
  
        --COMMIT TRANSACTION;
      
      return -1 ;

	END CATCH
	
END

;











