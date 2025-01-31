USE [RDB]
GO

/****** Object:  StoredProcedure [dbo].[sp_EVENT_METRIC_DATAMART]    Script Date: 2/19/2021 4:43:21 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO
 
 
if not exists (select * from sysobjects where name='EVENT_METRIC_INC' and xtype='U')
  CREATE TABLE [dbo].[EVENT_METRIC_INC](
	[EVENT_TYPE] [varchar](50) NOT NULL,
	[EVENT_UID] [bigint] NOT NULL,
	[LOCAL_ID] [varchar](50) NULL,
	[LOCAL_PATIENT_ID] [varchar](50) NULL,
	[CONDITION_CD] [varchar](50) NULL,
	[CONDITION_DESC_TXT] [varchar](300) NULL,
	[PROG_AREA_CD] [varchar](20) NULL,
	[PROG_AREA_DESC_TXT] [varchar](50) NULL,
	[PROGRAM_JURISDICTION_OID] [bigint] NULL,
	[JURISDICTION_CD] [varchar](20) NULL,
	[JURISDICTION_DESC_TXT] [varchar](255) NULL,
	[RECORD_STATUS_CD] [varchar](20) NULL,
	[RECORD_STATUS_DESC_TXT] [varchar](300) NULL,
	[RECORD_STATUS_TIME] [datetime] NULL,
	[ELECTRONIC_IND] [char](1) NULL,
	[STATUS_CD] [varchar](20) NULL,
	[STATUS_DESC_TXT] [varchar](300) NULL,
	[STATUS_TIME] [datetime] NULL,
	[ADD_TIME] [datetime] NULL,
	[ADD_USER_ID] [bigint] NULL,
	[LAST_CHG_TIME] [datetime] NULL,
	[LAST_CHG_USER_ID] [bigint] NULL,
	[CASE_CLASS_CD] [varchar](20) NULL,
	[CASE_CLASS_DESC_TXT] [varchar](300) NULL,
	[INVESTIGATION_STATUS_CD] [varchar](20) NULL,
	[INVESTIGATION_STATUS_DESC_TXT] [varchar](300) NULL,
	[ADD_USER_NAME] [varchar](300) NULL,
	[LAST_CHG_USER_NAME] [varchar](300) NULL,
 CONSTRAINT [PK_EVENT_METRIC_INC] PRIMARY KEY CLUSTERED 
(
	[EVENT_TYPE] ASC,
	[EVENT_UID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO



IF OBJECT_ID('rdb.dbo.[sp_EVENT_METRIC_DATAMART]', 'P') IS NOT NULL
BEGIN

DROP PROCEDURE dbo.sp_EVENT_METRIC_DATAMART;
END;
GO


CREATE PROCEDURE [dbo].[sp_EVENT_METRIC_DATAMART]
  @batch_id BIGINT
 as

  BEGIN
  
			DECLARE @RowCount_no INT ;
			DECLARE @Proc_Step_no FLOAT = 0 ;
			DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
			DECLARE @batch_start_time datetime2(7) = null ;
			DECLARE @batch_end_time datetime2(7) = null ;
			

 
 BEGIN TRY
	
			
			SET @Proc_Step_no = 1;
			SET @Proc_Step_Name = 'SP_Start';

	
		BEGIN TRANSACTION;
				INSERT INTO rdb.[dbo].[job_flow_log] (
								batch_id         ---------------@batch_id
							   ,[Dataflow_Name]  --------------'EVENT_METRIC_DATAMART'
							   ,[package_Name]   --------------'RDB.EVENT_METRIC'
							   ,[Status_Type]    ---------------START
							   ,[step_number]    ---------------@Proc_Step_no
							   ,[step_name]   ------------------@Proc_Step_Name=sp_start
							   ,[row_count] --------------------0
							   )
							   VALUES
							   (
							   @batch_id
							   ,'EVENT_METRIC DATAMART'
							   ,'RDB.dbo.EVENT_METRIC_INC'
							   ,'START'
							   ,@Proc_Step_no
							   ,@Proc_Step_Name
							   ,0
							   );
		  
		    COMMIT TRANSACTION;
			
			
				SELECT @batch_start_time = batch_start_dttm,@batch_end_time = batch_end_dttm
				FROM rdb.[dbo].[job_batch_log]
				WHERE status_type = 'start' and type_code='MasterETL';

				DECLARE  @EVENT_METRIC_COUNT AS BIGINT=0;
				SET  @EVENT_METRIC_COUNT= (SELECT COUNT(*) FROM rdb..EVENT_METRIC_INC);

				IF(@EVENT_METRIC_COUNT=0)
				   SET @batch_start_time ='01-01-1990'
   
-----------------------------------------------------------------------------2 Create Table TMP_NOTIFICATION--2-----------------------

           BEGIN TRANSACTION;
					SET @Proc_Step_name='Generating TMP_NOTIFICATION';
					SET @Proc_Step_no = 2;
						

									IF OBJECT_ID('rdb.dbo.TMP_NOTIFICATION', 'U') IS NOT NULL   
 									   drop table rdb.dbo.TMP_NOTIFICATION;
		
		                       SELECT
								   'Notification' as EVENT_TYPE, 		
									n.notification_uid as EVENT_UID,		
									n.local_id as LOCAL_ID, 
									per.local_id as LOCAL_PATIENT_ID,				
									n.prog_area_cd, 		
									n.program_jurisdiction_oid, 
									n.jurisdiction_cd, 			
									n.record_status_cd, 		
									n.record_status_time, 		
									n.status_time, 			
									n.add_time, 			
									n.add_user_id, 			
									n.last_chg_time, 		
									n.last_chg_user_id
									 INTO rdb.dbo.TMP_NOTIFICATION
									FROM  nbs_odse.dbo.notification N
								JOIN nbs_odse.dbo.act_relationship as ar   with (nolock)   ON ar.type_cd='Notification' AND N.notification_uid=ar.source_act_uid
								JOIN nbs_odse.dbo.participation    as part with (nolock)   ON part.type_cd='SubjOfPHC'  AND part.act_uid=ar.target_act_uid
							    JOIN nbs_odse.dbo.person           as per  with (nolock)   ON per.cd='PAT' AND per.person_uid = part.subject_entity_uid
  								---WHERE N.add_time >= @batch_start_time	AND  N.add_time <  @batch_end_time
								WHERE N.[last_chg_time]>= @batch_start_time	AND  N.[last_chg_time] <  @batch_end_time

								SELECT @ROWCOUNT_NO = @@ROWCOUNT;

									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID, 'EVENT_METRIC DATAMART','RDB.dbo.EVENT_METRIC_INC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  


              COMMIT TRANSACTION 
------------------------------------------------------------------3. Create Table TMP_NOT_PROG -----------------------------------

              BEGIN TRANSACTION;
					SET @Proc_Step_name='Generating TMP_NOT_PROG';
					SET @Proc_Step_no = 3;
						

									IF OBJECT_ID('rdb.dbo.TMP_NOT_PROG', 'U') IS NOT NULL   
 									   drop table rdb.dbo.TMP_NOT_PROG;
		
		                       SELECT     N.EVENT_TYPE,
										  N.EVENT_UID,
										  N.LOCAL_ID,	
										  N.LOCAL_PATIENT_ID,
										  N.prog_area_cd,
										  p.prog_area_desc_txt as PROG_AREA_DESC_TXT,
										  N.program_jurisdiction_oid,	
										  N.jurisdiction_cd,
										  N.record_status_cd,
										  N.record_status_time,
									      N.status_time,
										  N.add_time,
										  N.add_user_id,
										  N.last_chg_time,
										  N.last_chg_user_id	
							             INTO rdb.dbo.TMP_NOT_PROG
							   FROM rdb.dbo.TMP_NOTIFICATION N
								LEFT OUTER JOIN  [NBS_SRTE].dbo.program_area_code p with (nolock) ON  N.prog_area_cd = p.prog_area_cd;
								
								
								SELECT @ROWCOUNT_NO = @@ROWCOUNT;

									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID, 'EVENT_METRIC DATAMART','RDB.dbo.EVENT_METRIC_INC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

								
           COMMIT TRANSACTION	
				
-----------------------------------------------------4. Create Table TMP_NOT_PROG_JURI --------------------

           BEGIN TRANSACTION;
					SET @Proc_Step_name='Generating TMP_NOT_PROG_JURI';
					SET @Proc_Step_no = 4;
						

									IF OBJECT_ID('rdb.dbo.TMP_NOT_PROG_JURI', 'U') IS NOT NULL   
 									   drop table rdb.dbo.TMP_NOT_PROG_JURI;
		
		                       SELECT 
									N.EVENT_TYPE,
									N.EVENT_UID,
									N.LOCAL_ID,	
									N.LOCAL_PATIENT_ID,	
									N.prog_area_cd,
									N.PROG_AREA_DESC_TXT,
									N.program_jurisdiction_oid,
									N.jurisdiction_cd,	
								    J.code_desc_txt  as JURISDICTION_DESC_TXT,
									N.record_status_cd,	
									N.record_status_time,	
									N.status_time	,
									N.add_time,	
									N.add_user_id	,
									N.last_chg_time,	
									N.last_chg_user_id
								    INTO rdb.dbo.TMP_NOT_PROG_JURI
										FROM  rdb.dbo.TMP_NOT_PROG N
	                                    LEFT OUTER JOIN  [NBS_SRTE].dbo.jurisdiction_code as J with (nolock) ON N.jurisdiction_cd = J.code;


										SELECT @ROWCOUNT_NO = @@ROWCOUNT;

									
									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID, 'EVENT_METRIC DATAMART','RDB.dbo.EVENT_METRIC_INC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION

----------------------------------------------------5. Create Table TMP_NOT_PROG_JURI_CVG -----------------------

           BEGIN TRANSACTION;
					SET @Proc_Step_name='Generating TMP_NOT_PROG_JURI_CVG';
					SET @Proc_Step_no = 5;
						

									IF OBJECT_ID('rdb.dbo.TMP_NOT_PROG_JURI_CVG', 'U') IS NOT NULL   
 									   drop table rdb.dbo.TMP_NOT_PROG_JURI_CVG;

									SELECT  N.EVENT_TYPE,	
											N.EVENT_UID,	
											N.LOCAL_ID,	
											N.LOCAL_PATIENT_ID,	
											N.prog_area_cd,	
											N.PROG_AREA_DESC_TXT	,
											N.program_jurisdiction_oid,	
											N.jurisdiction_cd,
											N.JURISDICTION_DESC_TXT,
											N.record_status_cd	,
											C.CODE_DESC_TXT as RECORD_STATUS_DESC_TXT,
											N.record_status_time	,
											N.status_time	,
											N.add_time,	
											N.add_user_id	,
											N.last_chg_time,	
											N.last_chg_user_id
                                    INTO rdb.dbo.TMP_NOT_PROG_JURI_CVG
           						    FROM rdb.dbo.TMP_NOT_PROG_JURI N
	                                LEFT OUTER JOIN  [NBS_SRTE].dbo.code_value_general as C with (nolock) on N.record_status_cd = C.code
	   																and c.code_set_nm='REC_STAT';

                             SELECT @ROWCOUNT_NO = @@ROWCOUNT;

									
									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID, 'EVENT_METRIC DATAMART','RDB.dbo.EVENT_METRIC_INC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION

-------------------------------------------------------6. Create Table TMP_EVENT_NOT -----------------------

           BEGIN TRANSACTION;
					SET @Proc_Step_name='Generating TMP_EVENT_NOT';
					SET @Proc_Step_no = 6;
						

									IF OBJECT_ID('rdb.dbo.TMP_EVENT_NOT', 'U') IS NOT NULL   
 									   drop table rdb.dbo.TMP_EVENT_NOT;

									SELECT 
									EVENT_TYPE,	
									EVENT_UID,	
									LOCAL_ID,	
									LOCAL_PATIENT_ID,	
									prog_area_cd,	
									PROG_AREA_DESC_TXT	,
									program_jurisdiction_oid,	
									jurisdiction_cd	,
									JURISDICTION_DESC_TXT,	
									record_status_cd,
									RECORD_STATUS_DESC_TXT,	
									record_status_time,	
									status_time	,
									add_time,	
									add_user_id	,
									last_chg_time,
									last_chg_user_id,	
									RTRIM(Ltrim(up1.last_nm))+', '+ RTRIM(Ltrim(up1.first_nm)) as ADD_USER_NAME,
                                    RTRIM(Ltrim(up2.last_nm))+', '+ RTRIM(Ltrim(up2.first_nm))as LAST_CHG_USER_NAME
									INTO rdb.dbo.TMP_EVENT_NOT
									FROM rdb.dbo.TMP_NOT_PROG_JURI_CVG  N
									LEFT outer join [NBS_ODSE].dbo.user_profile as up1 with (nolock) on n.add_user_id = up1.nedss_entry_id
									LEFT outer join [NBS_ODSE].dbo.user_profile as up2 with (nolock) on n.last_chg_user_id = up2.nedss_entry_id;

								SELECT @ROWCOUNT_NO = @@ROWCOUNT;

									
									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID, 'EVENT_METRIC DATAMART','RDB.dbo.EVENT_METRIC_INC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);   

			COMMIT TRANSACTION

---------------------------------------------------------7. Create Table  TMP_EVENT_METRIC-----UNION Start--------------------------

           BEGIN TRANSACTION;
					SET @Proc_Step_name='Generating TMP_EVENT_METRIC';
					SET @Proc_Step_no = 7;
						

									IF OBJECT_ID('rdb.dbo.TMP_EVENT_METRIC', 'U') IS NOT NULL   
 									   drop table rdb.dbo.TMP_EVENT_METRIC;
									
									SELECT 
											EVENT_TYPE,	---1---NOTIFICATION
											EVENT_UID,	---2
											LOCAL_ID,	---3
											LOCAL_PATIENT_ID,	---4
											NULL as [CONDITION_CD],---5 ---''
											NULL as [CONDITION_DESC_TXT], ---6 ---''
											prog_area_cd,	---7
											PROG_AREA_DESC_TXT,	----8
											program_jurisdiction_oid,	---9
											jurisdiction_cd,	---10
											JURISDICTION_DESC_TXT,	---11
											record_status_cd,	---12
											RECORD_STATUS_DESC_TXT,	---13
											record_status_time,	---14
											NULL as electronic_ind,--15 ---''
											NULL as [STATUS_CD],---16 ---''
											NULL as [STATUS_DESC_TXT],---17 --''
											status_time	,---18
											add_time,	---19
											add_user_id	,---20
											last_chg_time,	---21
											last_chg_user_id,--22	
											NULL as [CASE_CLASS_CD] ,--23 ---''
											NULL as [CASE_CLASS_DESC_TXT] ,---24 --''
											NULL as [INVESTIGATION_STATUS_CD] ,--25 ---''
											NULL as [INVESTIGATION_STATUS_DESC_TXT] ,--26 ---''
											ADD_USER_NAME,	--27
											LAST_CHG_USER_NAME ---28
											INTO  rdb.dbo.TMP_EVENT_METRIC
											FROM rdb.dbo.TMP_EVENT_NOT
									
               UNION 									
									
	---------------------------------------------	'PHCInvForm'---------------------------------------------------------------------														
									
									SELECT

											 'PHCInvForm' as Event_Type,----1
											  phc.public_health_case_uid as EVENT_UID,---2
											  phc.local_id,----3
											  person.local_id as local_Patient_id ,---4
											  phc.cd as Condition_Cd,----5
											  phc.cd_desc_txt as Condition_desc_txt,----6
											  phc.prog_area_cd,----7
											  p.prog_area_desc_txt,----8
											  phc.program_jurisdiction_oid,----9
											  phc.jurisdiction_cd as Jurisdiction_cd,----10
											  j.code_desc_txt  as Jurisdiction_desc_txt,----11
											  phc.record_status_cd,----12
											  c.code_desc_txt as Record_status_desc_txt ,----13
											  phc.record_status_time,-----14
											  NULL  as electronic_ind,---15 ---''
											  NULL as status_cd,---16---''
											  NULL as status_desc_txt,----17 --''
											  phc.status_time, 	---18		
											  phc.add_time,--19
											  phc.add_user_id,--20
											  phc.last_chg_time,---21
											  phc.last_chg_user_id,---22
											  case when len(phc.case_class_cd)=0  then NULL else phc.case_class_cd end  as case_class_cd,---23---4/27/2021
											----  phc.case_class_cd as case_class_cd,---23
											  d.code_short_desc_txt as case_class_desc_txt,---24
											  phc.investigation_status_cd,---25
											  e.code_desc_txt as investigation_status_desc_txt,---26
											  RTRIM(Ltrim(up1.last_nm))+', '+RTRIM(Ltrim(up1.first_nm))as ADD_USER_NAME,---27
											  RTRIM(Ltrim(up2.last_nm))+', '+RTRIM(Ltrim(up2.first_nm))as LAST_CHG_USER_NAME ---28
		                                      FROM [NBS_ODSE].dbo.public_health_case phc   
												LEFT OUTER JOIN [NBS_SRTE].dbo.program_area_code  p  with (nolock) ON phc.prog_area_cd = p.prog_area_cd  
												LEFT OUTER JOIN [NBS_SRTE].dbo.jurisdiction_code  j  with (nolock) ON phc.jurisdiction_cd = j.code  
												LEFT OUTER JOIN [NBS_SRTE].dbo.code_value_general c  with (nolock) ON phc.record_status_cd = c.code AND 
	   																								c.code_set_nm='REC_STAT'
											    LEFT JOIN [NBS_ODSE].dbo.participation as part       with (nolock) ON phc.public_health_case_uid = part.act_uid AND
	                                                                                             	part.type_cd = 'SubjOfPHC'
											    LEFT JOIN [NBS_ODSE].dbo.person                      with (nolock) ON person.person_uid = part.subject_entity_uid
     
      											LEFT OUTER JOIN [NBS_SRTE].dbo.code_value_general d  with (nolock) ON phc.case_class_cd = d.code AND
																									d.code_set_nm='PHC_CLASS'

      											LEFT OUTER JOIN [NBS_SRTE].dbo.code_value_general e  with (nolock) ON phc.investigation_status_cd = e.code AND
																									  e.code_set_nm='PHC_IN_STS'

												LEFT outer join [NBS_ODSE].dbo.user_profile  up1     with (nolock) ON phc.add_user_id = up1.nedss_entry_id

      											LEFT outer join [NBS_ODSE].dbo.user_profile  up2     with (nolock) ON phc.last_chg_user_id = up2.nedss_entry_id
											    ----WHERE  phc.add_time >= @batch_start_time	AND  phc.add_time <  @batch_end_time
												 WHERE  phc.[last_chg_time] >= @batch_start_time	AND  phc.[last_chg_time] <  @batch_end_time


--------------------------------------------------------Vaccination------------------------------------------
                UNION 
	                                      SELECT
												 'Vaccination',---1
												  i.intervention_uid,---2
												  i.local_id,---3
												  person.local_id as Local_Patient_ID,---4
												  NULL as Condition_cd, ---5 ---''   
												  NULL as Condition_desc_txt,---6    ---''
												  i.prog_area_cd,---7
												  p.prog_area_desc_txt,---8
												  i.program_jurisdiction_oid,---9
												  i.jurisdiction_cd,---10
												  j.code_desc_txt as Jurisdiction_DESC_TXT,---11
												  i.record_status_cd,--12
												  c.CODE_DESC_TXT as record_status_desc_txt,---13
												  i.record_status_time,---14
													
												  i.electronic_ind,---15  ---''
												  NULL as status_cd,---16 ---''
												  NULL as status_desc_txt,---17 --''
												   i.status_time, ---18
												   i.add_time,---19
												   i.add_user_id,---20
												   i.last_chg_time,---21
												   i.last_chg_user_id,---22
												 
												 
												  NULL,---23----case class cd---''
												  NULL,---24---case class desc txt---''
												  NULL,---25  ---investigation status cd---''
												  NULL,---26 -----investigation status desc txt---''
											  RTRIM(Ltrim(up1.last_nm))+', '+ RTRIM(Ltrim(up1.first_nm))as ADD_USER_NAME,---27
											  RTRIM(Ltrim(up2.last_nm))+', '+ RTRIM(Ltrim(up2.first_nm))as LAST_CHG_USER_NAME ---28
		                                     
											  FROM[NBS_ODSE].dbo.intervention i
												LEFT OUTER JOIN [NBS_SRTE].dbo.program_area_code  as p  with (nolock) ON  i.prog_area_cd = p.prog_area_cd
												LEFT OUTER JOIN [NBS_SRTE].dbo.jurisdiction_code  as j  with (nolock) ON  i.jurisdiction_cd = j.code
												LEFT OUTER JOIN [NBS_SRTE].dbo.code_value_general as c  with (nolock) ON  i.record_status_cd = c.code AND
												                                                     c.code_set_nm='REC_STAT'
												LEFT JOIN [NBS_ODSE].dbo.nbs_act_entity          as part with (nolock) ON i.intervention_uid = part.act_uid AND 
																									part.type_cd = 'SubOfVacc'
												INNER JOIN  [NBS_ODSE].dbo.person                        with (nolock) ON person.person_uid = part.entity_uid
												
												LEFT outer join [NBS_ODSE].dbo.user_profile       as up1 with (nolock) ON i.add_user_id = up1.nedss_entry_id
												
												LEFT outer join[NBS_ODSE].dbo.user_profile       as up2 with (nolock) ON i.last_chg_user_id = up2.nedss_entry_id
												---WHERE	 i.add_time >= @batch_start_time	AND  i.add_time <  @batch_end_time
												 WHERE	 i.[last_chg_time] >= @batch_start_time	AND  i.[last_chg_time] <  @batch_end_time

------------------------------------------------------'LabReport'--Non ELR  --------------------------------------------------------------
                 UNION
														SELECT 
															  'LabReport',    ---Non ELR  ---1
															  o.observation_uid,---2
															  o.local_id,----3
															  person.local_id as Local_Patient_ID ,----4
												  			  NULL as Condition_cd, ---5    ---''
												              NULL as Condition_desc_txt,---6   ---''  
															  o.prog_area_cd,---7
															  p.prog_area_desc_txt,----8
															  o.program_jurisdiction_oid,----9
															  o.jurisdiction_cd,---10
															  j.code_desc_txt as Jurisdiction_DESC_TXT,---11
															  o.record_status_cd,---12
															  c.CODE_DESC_TXT as Record_status_desc_txt ,----13
															  o.record_status_time,---14
															  o.electronic_ind,---15
															  o.STATUS_CD,---16
															  cvgst.CODE_DESC_TXT as status_desc_txt,---17
															  
															  o.STATUS_TIME,---18	
															  o.add_time,---19
															  o.add_user_id,---20
															  o.last_chg_time,---21
															  o.last_chg_user_id,---22
															  NULL,---23----case class cd---23---''
												              NULL,---24---case class desc txt---24---''
												              NULL,---25  ---investigation status cd----25---''
												              NULL,---26 -----investigation status desc txt----26---''
															 RTRIM(Ltrim(up1.last_nm))+', '+RTRIM(Ltrim(up1.first_nm))as ADD_USER_NAME,---27
											                 RTRIM(Ltrim(up2.last_nm))+', '+RTRIM(Ltrim(up2.first_nm))as LAST_CHG_USER_NAME ---28
															  FROM   [NBS_ODSE].dbo.observation o
															LEFT OUTER JOIN [NBS_SRTE].dbo.program_area_code  as p with (nolock)    ON o.prog_area_cd = p.prog_area_cd
															LEFT OUTER JOIN [NBS_SRTE].dbo.jurisdiction_code  as j with (nolock)    ON o.jurisdiction_cd = j.code
															LEFT OUTER JOIN [NBS_SRTE].dbo.code_value_general as c with (nolock)   ON  o.record_status_cd = c.code AND
																                                                c.code_set_nm='REC_STAT'
															LEFT JOIN [NBS_ODSE].dbo.participation            as part with (nolock) ON o.observation_uid = part.act_uid AND
																                                                part.type_cd = 'PATSBJ'
														    left outer join [NBS_SRTE].dbo.code_value_general as cvgst with (nolock) ON o.status_cd = cvgst.code
	   																                                              and cvgst.code_set_nm='ACT_OBJ_ST'
															LEFT JOIN  [NBS_ODSE].dbo.person                      with (nolock) ON  person.person_uid = part.subject_entity_uid
  												            LEFT outer join  [NBS_ODSE].dbo.user_profile as up1   with (nolock) ON o.add_user_id = up1.nedss_entry_id
															LEFT outer join  [NBS_ODSE].dbo.user_profile as up2   with (nolock) ON o.last_chg_user_id = up2.nedss_entry_id
															WHERE
																  o.obs_domain_cd_st_1='Order' AND o.ctrl_cd_display_form='LabReport' AND
																  o.electronic_ind <> 'Y' 
																-----  and  (o.add_time >= @batch_start_time	AND  o.add_time <  @batch_end_time)
								                             and  (o.[last_chg_time] >= @batch_start_time	AND  o.[last_chg_time] <  @batch_end_time)
								
	--------------------------------------------Lab Reports ( ELR )---------------------------------------------------------------
	           UNION
												SELECT 
													  'LabReport',---1    Lab Reports ( ELR )
													  o.observation_uid,---2
													  o.local_id,---3
													  person.local_id as Local_Patient_ID ,----4
													   NULL as Condition_cd, ---5  --''  
													   NULL as Condition_desc_txt,---6  ---'' 
													  o.prog_area_cd,---7
													  p.prog_area_desc_txt,----8
													  o.program_jurisdiction_oid,-----9
													  o.jurisdiction_cd,----10
													  j.code_desc_txt,----11
													  o.record_status_cd,----12
													  c.code_desc_txt   as Record_status_desc_txt ,----13
													  o.record_status_time,----14
													  o.electronic_ind,----15
													  o.status_cd,----16
													  c1.code_desc_txt as status_desc_txt,---17
													  o.status_time,----18
													  o.add_time,----19
													  o.add_user_id,---20
													  o.last_chg_time,---21
													  o.last_chg_user_id,---22
													  NULL,---23---''
													  NULL,---24---''
													  NULL,---25---''
													  NULL,---26---''
													 RTRIM(Ltrim(up1.last_nm))+', '+RTRIM(Ltrim(up1.first_nm))as ADD_USER_NAME,---27
													 RTRIM(Ltrim(up2.last_nm))+', '+RTRIM(Ltrim(up2.first_nm))as LAST_CHG_USER_NAME ---28
												FROM  [NBS_ODSE].dbo.observation o
												LEFT OUTER JOIN [NBS_SRTE].dbo.program_area_code as p   with (nolock) ON o.prog_area_cd = p.prog_area_cd
												LEFT OUTER JOIN [NBS_SRTE].dbo.jurisdiction_code as j   with (nolock) ON o.jurisdiction_cd = j.code
												LEFT OUTER JOIN [NBS_SRTE].dbo.code_value_general as c1 with (nolock) ON o.status_cd = c1.code AND
																								   c1.code_set_nm='ACT_OBJ_ST' 
												LEFT OUTER JOIN [NBS_SRTE].dbo.code_value_general as c with (nolock)   ON o.record_status_cd = c.code AND
																								   c.code_set_nm='REC_STAT'
												LEFT JOIN [NBS_ODSE].dbo.participation as part with (nolock)           ON  o.observation_uid = part.act_uid AND
																							part.type_cd = 'PATSBJ'
												LEFT JOIN  [NBS_ODSE].dbo.person              with (nolock)            ON person.person_uid = part.subject_entity_uid
  	
												LEFT outer join  [NBS_ODSE].dbo.user_profile as up1 on o.add_user_id = up1.nedss_entry_id
												LEFT outer join  [NBS_ODSE].dbo.user_profile as up2 on o.last_chg_user_id = up2.nedss_entry_id
												WHERE
												  o.obs_domain_cd_st_1='Order' AND o.ctrl_cd_display_form='LabReport' AND
												  o.electronic_ind='Y'
												 ----- and ( o.add_time >= @batch_start_time	AND  o.add_time <  @batch_end_time)
												    and  (o.[last_chg_time] >= @batch_start_time	AND  o.[last_chg_time] <  @batch_end_time)



------------------------------------------------Morbidity Reports-------------------------------------------------------
           UNION

													SELECT 
														  'MorbReport',---1
														  o.observation_uid,---2
														  o.local_id,---3
														  person.local_id as Local_Patient_ID ,----4
														  o.cd,---5
														  q.condition_short_nm,---6
														  o.prog_area_cd,---7
														  p.prog_area_desc_txt,---8
														  o.program_jurisdiction_oid,---9
														  o.jurisdiction_cd,---10
														  j.code_desc_txt,---11
														  o.record_status_cd,---12
														  c.CODE_DESC_TXT  as Record_status_desc_txt ,---13
														  o.record_status_time,---14
														  o.electronic_ind,---15
														  o.STATUS_CD,---16
														  cvgst.CODE_DESC_TXT as status_desc_txt,---17
														  o.STATUS_TIME,---18	
														  o.add_time,----19
														  o.add_user_id,---20
														  o.last_chg_time,---21
														  o.last_chg_user_id,---22
														  NULL,---23---''
														  NULL,---24 ---''
														  NULL,---25---''
														  NULL,---26---''
														 RTRIM(Ltrim(up1.last_nm))+', '+RTRIM(Ltrim(up1.first_nm))as ADD_USER_NAME,---27
														 RTRIM(Ltrim(up2.last_nm))+', '+RTRIM(Ltrim(up2.first_nm))as LAST_CHG_USER_NAME ---28
													FROM [NBS_ODSE].dbo.observation o
													LEFT OUTER JOIN [NBS_SRTE].dbo.program_area_code  as p with (nolock)     ON o.prog_area_cd = p.prog_area_cd
													LEFT OUTER JOIN [NBS_SRTE].dbo.condition_code     as q with (nolock)     ON o.cd = q.condition_cd
													LEFT OUTER JOIN [NBS_SRTE].dbo.code_value_general as cvgst with (nolock) ON cvgst.code_set_nm='ACT_OBJ_ST' AND
																													 o.status_cd = cvgst.code
													LEFT OUTER JOIN  [NBS_SRTE].dbo.jurisdiction_code as j with (nolock)     ON o.jurisdiction_cd = j.code
													LEFT OUTER JOIN  [NBS_SRTE].dbo.code_value_general as c with (nolock)    ON  o.record_status_cd = c.code AND
																												  c.code_set_nm='REC_STAT'
													LEFT JOIN  [NBS_ODSE].dbo.participation as part with (nolock)            ON  o.observation_uid = part.act_uid AND
																													 part.type_cd = 'SubjOfMorbReport'
													LEFT JOIN  [NBS_ODSE].dbo.person                 with (nolock)           ON person.person_uid = part.subject_entity_uid
													LEFT outer join  [NBS_ODSE].dbo.user_profile as up1 with (nolock        )ON o.add_user_id = up1.nedss_entry_id
													LEFT outer join  [NBS_ODSE].dbo.user_profile as up2 with (nolock)        ON o.last_chg_user_id = up2.nedss_entry_id
													   WHERE
													  o.obs_domain_cd_st_1='Order' AND
													  o.ctrl_cd_display_form='MorbReport'
													-----  and  (o.add_time >= @batch_start_time	AND  o.add_time <  @batch_end_time)
													   and  (o.[last_chg_time] >= @batch_start_time	AND  o.[last_chg_time] <  @batch_end_time)
								

-----------------------------------------------------------------------Contact -------------------------------------------------------------------
         UNION
														SELECT 
														'CONTACT' ,---1
														CT_CONTACT_UID ,---2
														CT_CONTACT.LOCAL_ID,---3
														PERSON.LOCAL_ID AS LOCAL_PATIENT_ID,---4
														NULL as Condition_cd, ---5   ---''
														NULL as Condition_desc_txt,---6 ---''   
														CT_CONTACT.PROG_AREA_CD,----7
														P.PROG_AREA_DESC_TXT,---8
														CT_CONTACT.PROGRAM_JURISDICTION_OID,----9
														CT_CONTACT.JURISDICTION_CD,		---10  
														J.CODE_DESC_TXT,---11
														CT_CONTACT.RECORD_STATUS_CD, ----12
														C.CODE_DESC_TXT,----13
														CT_CONTACT.RECORD_STATUS_TIME ,---14
														NULL,----15---''
														NULL,----16---''
														NULL,---17---''
														NULL,----18---''
														CT_CONTACT.ADD_TIME,---19
														CT_CONTACT.ADD_USER_ID,---20
														CT_CONTACT.LAST_CHG_TIME,---21
														CT_CONTACT.LAST_CHG_USER_ID,---22
													    NULL,---23---''
														NULL,---24---''
														NULL,---25---''
														NULL,---26---''
													 RTRIM(Ltrim(up1.last_nm))+', '+RTRIM(Ltrim(up1.first_nm))as ADD_USER_NAME,---27
													 RTRIM(Ltrim(up2.last_nm))+', '+RTRIM(Ltrim(up2.first_nm))as LAST_CHG_USER_NAME ---28
													  FROM [NBS_ODSE].dbo.CT_CONTACT
													  INNER JOIN [NBS_ODSE].dbo.PERSON                   with (nolock) ON PERSON.PERSON_UID = CT_CONTACT.SUBJECT_ENTITY_UID
													  INNER JOIN [NBS_SRTE].dbo.PROGRAM_AREA_CODE AS P   with (nolock) ON CT_CONTACT.PROG_AREA_CD = P.PROG_AREA_CD  
													  INNER JOIN [NBS_SRTE].dbo.JURISDICTION_CODE AS J   with (nolock) ON CT_CONTACT.JURISDICTION_CD = J.CODE  
													  INNER JOIN [NBS_SRTE].dbo.CODE_VALUE_GENERAL C     with (nolock) ON CT_CONTACT.RECORD_STATUS_CD = C.CODE AND C.CODE_SET_NM='REC_STAT'
													  LEFT OUTER JOIN [NBS_ODSE].dbo.USER_PROFILE AS UP1 with (nolock) ON CT_CONTACT.ADD_USER_ID = UP1.NEDSS_ENTRY_ID
													  LEFT OUTER JOIN [NBS_ODSE].dbo.USER_PROFILE AS UP2 with (nolock) ON CT_CONTACT.LAST_CHG_USER_ID = UP2.NEDSS_ENTRY_ID
													----WHERE CT_CONTACT.add_time >= @batch_start_time	AND CT_CONTACT.add_time <  @batch_end_time
													  WHERE CT_CONTACT.[last_chg_time]   >= @batch_start_time	AND CT_CONTACT.[last_chg_time]   <  @batch_end_time

	                                 SELECT @ROWCOUNT_NO = @@ROWCOUNT;

									
									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID, 'EVENT_METRIC DATAMART','RDB.dbo.EVENT_METRIC_INC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION

			
-----------------------------------------------------------8. UPDATE NEW RECORDS------------------------------

            BEGIN TRANSACTION

					           SET @PROC_STEP_NO = 8;
					           SET @PROC_STEP_NAME = 'Update [dbo].[EVENT_METRIC_INC]'; 

							   UPDATE rdb.[dbo].[EVENT_METRIC_INC] 
			                  
							SET	[EVENT_TYPE]=TEM.[EVENT_TYPE],
								[EVENT_UID]= TEM.[EVENT_UID],
								[LOCAL_ID] =TEM. [LOCAL_ID],
								[LOCAL_PATIENT_ID]=TEM.[LOCAL_PATIENT_ID],
								[CONDITION_CD]=TEM.[CONDITION_CD],
								[CONDITION_DESC_TXT]=TEM.[CONDITION_DESC_TXT],
								[PROG_AREA_CD]=TEM.[PROG_AREA_CD],
								[PROG_AREA_DESC_TXT]=TEM.[PROG_AREA_DESC_TXT],
								[PROGRAM_JURISDICTION_OID]=TEM.[PROGRAM_JURISDICTION_OID],
								[JURISDICTION_CD]=TEM.[JURISDICTION_CD],
								[JURISDICTION_DESC_TXT]=TEM.[JURISDICTION_DESC_TXT],
								[RECORD_STATUS_CD]=TEM.[RECORD_STATUS_CD],
								[RECORD_STATUS_DESC_TXT]=TEM.[RECORD_STATUS_DESC_TXT],
								[RECORD_STATUS_TIME]=TEM.[RECORD_STATUS_TIME],
								[ELECTRONIC_IND]=TEM.[ELECTRONIC_IND],
								[STATUS_CD]=TEM.[STATUS_CD],
								[STATUS_DESC_TXT]=TEM.[STATUS_DESC_TXT],
								[STATUS_TIME]=TEM.[STATUS_TIME],
								[ADD_TIME]=TEM.[ADD_TIME],
								[ADD_USER_ID]=TEM.[ADD_USER_ID],
								[LAST_CHG_TIME]=TEM.[LAST_CHG_TIME],
								[LAST_CHG_USER_ID]=TEM.[LAST_CHG_USER_ID],
								[CASE_CLASS_CD]=TEM.[CASE_CLASS_CD],
								[CASE_CLASS_DESC_TXT]=TEM.[CASE_CLASS_DESC_TXT],
								[INVESTIGATION_STATUS_CD]=TEM.[INVESTIGATION_STATUS_CD],
								[INVESTIGATION_STATUS_DESC_TXT]=TEM.[INVESTIGATION_STATUS_DESC_TXT],
								[ADD_USER_NAME]=TEM.[ADD_USER_NAME],
								[LAST_CHG_USER_NAME]=TEM.[LAST_CHG_USER_NAME]
								FROM TMP_EVENT_METRIC TEM
							   WHERE TEM.[EVENT_UID] = rdb.[dbo].[EVENT_METRIC_INC].[EVENT_UID]
							   and   TEM.[EVENT_TYPE]= rdb.[dbo].[EVENT_METRIC_INC].[EVENT_TYPE]
                               
							   
							    SELECT @ROWCOUNT_NO = @@ROWCOUNT

									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID, 'EVENT_METRIC DATAMART','RDB.dbo.EVENT_METRIC_INC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION


---------------------------------------------------------------9. INSERT INTO EVENT METRIC  -------------------------
            BEGIN TRANSACTION
                               SET @PROC_STEP_NO = 9;
					           SET @PROC_STEP_NAME = 'INSERTING INTO EVENT METRIC_INC'; 					
									
									INSERT INTO  rdb.dbo.EVENT_METRIC_INC
									(
									[EVENT_TYPE],[EVENT_UID],[LOCAL_ID],[LOCAL_PATIENT_ID],[CONDITION_CD],[CONDITION_DESC_TXT],
									[PROG_AREA_CD] ,[PROG_AREA_DESC_TXT] ,[PROGRAM_JURISDICTION_OID] ,[JURISDICTION_CD] ,[JURISDICTION_DESC_TXT] ,
									[RECORD_STATUS_CD] ,[RECORD_STATUS_DESC_TXT],[RECORD_STATUS_TIME],[ELECTRONIC_IND],[STATUS_CD],
									[STATUS_DESC_TXT],[STATUS_TIME],[ADD_TIME] ,[ADD_USER_ID] ,[LAST_CHG_TIME] ,[LAST_CHG_USER_ID] ,
									[CASE_CLASS_CD] ,[CASE_CLASS_DESC_TXT] ,[INVESTIGATION_STATUS_CD] ,[INVESTIGATION_STATUS_DESC_TXT] ,
									[ADD_USER_NAME] ,[LAST_CHG_USER_NAME] 
									)

                                               
								   SELECT 
									TEM.[EVENT_TYPE],TEM.[EVENT_UID],TEM.[LOCAL_ID],TEM.[LOCAL_PATIENT_ID],TEM.[CONDITION_CD],TEM.[CONDITION_DESC_TXT],
									TEM.[PROG_AREA_CD] ,TEM.[PROG_AREA_DESC_TXT],TEM.[PROGRAM_JURISDICTION_OID] ,TEM.[JURISDICTION_CD] ,TEM.[JURISDICTION_DESC_TXT] ,
									TEM.[RECORD_STATUS_CD],TEM.[RECORD_STATUS_DESC_TXT],TEM.[RECORD_STATUS_TIME],TEM.[ELECTRONIC_IND],TEM.[STATUS_CD],
									TEM.[STATUS_DESC_TXT],TEM.[STATUS_TIME],[ADD_TIME] ,TEM.[ADD_USER_ID] ,TEM.[LAST_CHG_TIME] ,TEM.[LAST_CHG_USER_ID] ,
									TEM.[CASE_CLASS_CD],TEM.[CASE_CLASS_DESC_TXT],TEM.[INVESTIGATION_STATUS_CD] ,TEM.[INVESTIGATION_STATUS_DESC_TXT] ,
									TEM.[ADD_USER_NAME],TEM.[LAST_CHG_USER_NAME] 


                                                           FROM rdb.dbo.TMP_EVENT_METRIC TEM
							                  	 WHERE NOT EXISTS
								                        (
								                         SELECT * FROM rdb.dbo.EVENT_METRIC_INC EM
								                         WHERE EM.[EVENT_UID] = TEM.[EVENT_UID]
														 and   EM.[EVENT_TYPE]= TEM.[EVENT_TYPE]
								                         )
									
                            SELECT @ROWCOUNT_NO = @@ROWCOUNT;

									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID, 'EVENT_METRIC_INC DATAMART','RDB.dbo.EVENT_METRIC_INC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION


---------------------------------------------------------------10. INSERT INTO EVENT METRIC  -------------------------
            BEGIN TRANSACTION
								SET @PROC_STEP_NO = 10;
								SET @PROC_STEP_NAME = 'INSERTING INTO EVENT METRIC'; 					
								Declare @count bigint=0
								set @count=(select config_value from nbs_odse.dbo.NBS_configuration where config_key = 'METRICS_GOBACKBY_DAYS');
	
									INSERT INTO  rdb.dbo.EVENT_METRIC
									(
									[EVENT_TYPE],[EVENT_UID],[LOCAL_ID],[LOCAL_PATIENT_ID],[CONDITION_CD],[CONDITION_DESC_TXT],
									[PROG_AREA_CD] ,[PROG_AREA_DESC_TXT] ,[PROGRAM_JURISDICTION_OID] ,[JURISDICTION_CD] ,[JURISDICTION_DESC_TXT] ,
									[RECORD_STATUS_CD] ,[RECORD_STATUS_DESC_TXT],[RECORD_STATUS_TIME],[ELECTRONIC_IND],[STATUS_CD],
									[STATUS_DESC_TXT],[STATUS_TIME],[ADD_TIME] ,[ADD_USER_ID] ,[LAST_CHG_TIME] ,[LAST_CHG_USER_ID] ,
									[CASE_CLASS_CD] ,[CASE_CLASS_DESC_TXT] ,[INVESTIGATION_STATUS_CD] ,[INVESTIGATION_STATUS_DESC_TXT] ,
									[ADD_USER_NAME] ,[LAST_CHG_USER_NAME] 
									)

                                               
								   SELECT 
									TEM.[EVENT_TYPE],TEM.[EVENT_UID],TEM.[LOCAL_ID],TEM.[LOCAL_PATIENT_ID],TEM.[CONDITION_CD],TEM.[CONDITION_DESC_TXT],
									TEM.[PROG_AREA_CD] ,TEM.[PROG_AREA_DESC_TXT],TEM.[PROGRAM_JURISDICTION_OID] ,TEM.[JURISDICTION_CD] ,TEM.[JURISDICTION_DESC_TXT] ,
									TEM.[RECORD_STATUS_CD],TEM.[RECORD_STATUS_DESC_TXT],TEM.[RECORD_STATUS_TIME],TEM.[ELECTRONIC_IND],TEM.[STATUS_CD],
									TEM.[STATUS_DESC_TXT],TEM.[STATUS_TIME],[ADD_TIME] ,TEM.[ADD_USER_ID] ,TEM.[LAST_CHG_TIME] ,TEM.[LAST_CHG_USER_ID] ,
									TEM.[CASE_CLASS_CD],TEM.[CASE_CLASS_DESC_TXT],TEM.[INVESTIGATION_STATUS_CD] ,TEM.[INVESTIGATION_STATUS_DESC_TXT] ,
									TEM.[ADD_USER_NAME],TEM.[LAST_CHG_USER_NAME] 


                                                           FROM rdb.dbo.EVENT_METRIC_INC TEM WITH (NOLOCK) 
							                  	 WHERE DATEDIFF(day, ADD_TIME,GETDATE()) between 0 and @count 
									
                            SELECT @ROWCOUNT_NO = @@ROWCOUNT;

									INSERT INTO RDB.[DBO].[JOB_FLOW_LOG] 
									(BATCH_ID,[DATAFLOW_NAME],[PACKAGE_NAME] ,[STATUS_TYPE],[STEP_NUMBER],[STEP_NAME],[ROW_COUNT])
									VALUES(@BATCH_ID, 'EVENT_METRIC DATAMART','RDB.dbo.EVENT_METRIC','START',@PROC_STEP_NO,@PROC_STEP_NAME,@ROWCOUNT_NO);  

			COMMIT TRANSACTION


----------------------------------------------------------------------------------------------------------------
				

IF OBJECT_ID('rdb.dbo.TMP_NOTIFICATION', 'U') IS NOT NULL   
 drop table rdb.dbo.TMP_NOTIFICATION;

IF OBJECT_ID('rdb.dbo.TMP_NOT_PROG', 'U') IS NOT NULL   
drop table rdb.dbo.TMP_NOT_PROG;

IF OBJECT_ID('rdb.dbo.TMP_NOT_PROG_JURI', 'U') IS NOT NULL   
drop table rdb.dbo.TMP_NOT_PROG_JURI;

IF OBJECT_ID('rdb.dbo.TMP_NOT_PROG_JURI_CVG', 'U') IS NOT NULL   
drop table rdb.dbo.TMP_NOT_PROG_JURI_CVG;

IF OBJECT_ID('rdb.dbo.TMP_EVENT_NOT', 'U') IS NOT NULL   
 drop table rdb.dbo.TMP_EVENT_NOT;

IF OBJECT_ID('rdb.dbo.TMP_EVENT_METRIC', 'U') IS NOT NULL   
 drop table rdb.dbo.TMP_EVENT_METRIC;

-------------------------------------------------------------------------------------------------------

			BEGIN TRANSACTION ;
			
				SET @Proc_Step_no = 10;
				SET @Proc_Step_Name = 'SP_COMPLETE'; 


				INSERT INTO rdb.[dbo].[job_flow_log] 
						(
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
					  'EVENT_METRIC DATAMART',
					  'RDB.dbo.EVENT_METRIC',
					   'COMPLETE',
					    @Proc_Step_no,
					    @Proc_Step_name,
					    @RowCount_no
					   );
		  
			
			COMMIT TRANSACTION;
 END TRY
-----------------------------------------

  BEGIN CATCH
  
     
				IF @@TRANCOUNT > 0   ROLLBACK TRANSACTION;
 
				DECLARE @ErrorNumber INT = ERROR_NUMBER();
				DECLARE @ErrorLine INT = ERROR_LINE();
				DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
				DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
				DECLARE @ErrorState INT = ERROR_STATE();
			 
	
				INSERT INTO rdb.[dbo].[job_flow_log] 
					  (
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
					    @batch_id,
					   'EVENT_METRIC DATAMART',
					   'RDB.dbo.EVENT_METRIC',
					   'ERROR',
					   @Proc_Step_no,
					   'ERROR - '+ @Proc_Step_name,
					    'Step -' +CAST(@Proc_Step_no AS VARCHAR(3))+' -' +CAST(@ErrorMessage AS VARCHAR(500)),
					    0
					   );
  

			return -1 ;

	END CATCH
	
END;

GO


