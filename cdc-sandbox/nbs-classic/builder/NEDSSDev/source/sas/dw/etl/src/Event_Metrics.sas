%MACRO DBLOAD (DBTABLE, DSNAME);
 PROC APPEND FORCE BASE=NBS_RDB.&DBTABLE DATA=&DSNAME;
 RUN;
 QUIT;
%MEND DBLOAD;

PROC SQL;
/*
	Notification 
*/
	create table NOTIFICATION  as select 
		'Notification' as EVENT_TYPE, 		
		n.notification_uid as EVENT_UID,		
		n.local_id as LOCAL_ID, 			
		n.prog_area_cd, 		
		n.program_jurisdiction_oid, 
		n.jurisdiction_cd, 			
		n.record_status_cd, 		
		n.record_status_time, 		
		per.local_id as LOCAL_PATIENT_ID,		
		
		n.status_time, 			
		n.add_time, 			
		n.add_user_id, 			
		n.last_chg_time, 		
		n.last_chg_user_id
		
	from nbs_ods.notification N
	JOIN nbs_ods.act_relationship as ar
		ON ar.type_cd='Notification' AND
  			n.notification_uid=ar.source_act_uid
	JOIN nbs_ods.participation as part
		ON part.type_cd='SubjOfPHC' AND
  			part.act_uid=ar.target_act_uid
JOIN nbs_ods.person as per
		ON per.cd='PAT' AND
  			per.person_uid = part.subject_entity_uid
  	WHERE  DATEPART(n.add_time) >=  TODAY()-&METRICS_GOBACKBY_DAYS ;
	QUIT;
	PROC SQL;
CREATE TABLE NOT_PROG AS 
SELECT n.*, 		
p.prog_area_desc_txt as PROG_AREA_DESC_TXT
 FROM NOTIFICATION N
	left outer join nbs_srt.program_area_code as p
		on n.prog_area_cd = p.prog_area_cd;
	QUIT;
	PROC SQL;
CREATE TABLE NOT_PROG_JURI AS 
SELECT n.*,
	j.code_desc_txt  as JURISDICTION_DESC_TXT			
		FROM NOT_PROG N
	left outer join nbs_srt.jurisdiction_code as j
		on n.jurisdiction_cd = j.code;
	QUIT;
	PROC SQL;
CREATE TABLE NOT_PROG_JURI_CVG AS 
SELECT n.*,
	c.CODE_DESC_TXT as RECORD_STATUS_DESC_TXT
	FROM NOT_PROG_JURI N
	left outer join nbs_srt.code_value_general as c
		on n.record_status_cd = c.code
	   	and c.code_set_nm='REC_STAT';
	QUIT;
	PROC SQL;
	
	CREATE TABLE EVENT_NOT as 
	SELECT n.*,
trim(left(up1.last_nm))||', '||trim(left(up1.first_nm)) as ADD_USER_NAME,
                trim(left(up2.last_nm))||', '||trim(left(up2.first_nm)) as LAST_CHG_USER_NAME
FROM NOT_PROG_JURI_CVG  N
  	/* add user name */
	LEFT outer join nbs_ods.user_profile as up1
		    on n.add_user_id = up1.nedss_entry_id
	/* last change user name */
	LEFT outer join nbs_ods.user_profile as up2
            on n.last_chg_user_id = up2.nedss_entry_id;
quit;
%DBLOAD (EVENT_METRIC, EVENT_NOT);


/* Texas - Moved code execution to database 08/20/2020 for performance reasons */
PROC SQL;
/*
	Public Health Case
*/
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
INSERT INTO EVENT_METRIC 
	SELECT 
		  'PHCInvForm',
		  phc.public_health_case_uid,
		  phc.local_id,
		  person.local_id,
		  phc.cd,
		  phc.cd_desc_txt,
		  phc.prog_area_cd,
		  p.prog_area_desc_txt,
		  phc.program_jurisdiction_oid,
		  phc.jurisdiction_cd,
		  j.code_desc_txt,
		  phc.record_status_cd,
		  c.code_desc_txt,
		  phc.record_status_time,
		  NULL,
		  NULL,
		  NULL,
		  phc.status_time, 			
		  phc.add_time,
		  phc.add_user_id,
		  phc.last_chg_time,
		  phc.last_chg_user_id,
		  phc.case_class_cd,
		  d.code_short_desc_txt,
		  phc.investigation_status_cd,
		  e.code_desc_txt,
		LTRIM(RTRIM(up1.last_nm)) + ', ' + LTRIM(RTRIM(up1.first_nm)),
        LTRIM(RTRIM(up2.last_nm)) + ', ' + LTRIM(RTRIM(up2.first_nm))
	FROM 
	  nbs_odse.dbo.public_health_case phc   
	LEFT OUTER JOIN nbs_srte.dbo.program_area_code as p  
		ON phc.prog_area_cd = p.prog_area_cd  
	LEFT OUTER JOIN nbs_srte.dbo.jurisdiction_code as j
		ON phc.jurisdiction_cd = j.code  
	LEFT OUTER JOIN nbs_srte.dbo.code_value_general c
		ON phc.record_status_cd = c.code AND 
	   	c.code_set_nm='REC_STAT'
	LEFT JOIN nbs_odse.dbo.participation as part
		ON phc.public_health_case_uid = part.act_uid AND
	   	part.type_cd = 'SubjOfPHC'
	LEFT JOIN nbs_odse.dbo.person
		ON person.person_uid = part.subject_entity_uid
      	/* case class code */
    LEFT OUTER JOIN nbs_srte.dbo.code_value_general d
	    ON phc.case_class_cd = d.code AND
	    d.code_set_nm='PHC_CLASS'
      	/* inv status_cd */
    LEFT OUTER JOIN nbs_srte.dbo.code_value_general e
	    ON phc.investigation_status_cd = e.code AND
	    e.code_set_nm='PHC_IN_STS'
	/* add user name */
    LEFT outer join nbs_odse.dbo.user_profile as up1
	    on phc.add_user_id = up1.nedss_entry_id
	/* last change user name */
    LEFT outer join nbs_odse.dbo.user_profile as up2
            on phc.last_chg_user_id = up2.nedss_entry_id
	WHERE  CONVERT(DATE, phc.add_time) >=  CONVERT(DATE, DATEADD(DAY, -&METRICS_GOBACKBY_DAYS, GETDATE()))  		/* DATEPART(phc.add_time) >=  TODAY()-&METRICS_GOBACKBY_DAYS */
) by sql;
disconnect from sql; 
QUIT;	

/* Texas - Moved code execution to database 08/20/2020 for performance reasons*/
PROC SQL;
/*
	Vaccination( Intervention )
*/
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
INSERT INTO EVENT_METRIC 
	SELECT 
		  'Vaccination',
		  i.intervention_uid,
		  i.local_id,
		  person.local_id,
		  NULL,
		  NULL,
		  i.prog_area_cd,
		  p.prog_area_desc_txt,
		  i.program_jurisdiction_oid,
		  i.jurisdiction_cd,
		  j.code_desc_txt,
		  i.record_status_cd,
		  c.CODE_DESC_TXT,
		  i.record_status_time,
		  NULL,
		  NULL,
		  NULL,
		  i.status_time, 			
		  i.add_time,
		  i.add_user_id,
		  i.last_chg_time,
		  i.last_chg_user_id,
		  NULL,
		  NULL,
		  NULL,
		  NULL,
		LTRIM(RTRIM(up1.last_nm)) + ', ' + LTRIM(RTRIM(up1.first_nm)),
        LTRIM(RTRIM(up2.last_nm)) + ', ' + LTRIM(RTRIM(up2.first_nm))
	FROM 
	  nbs_odse.dbo.intervention i
	LEFT OUTER JOIN nbs_srte.dbo.program_area_code as p
	ON
	  i.prog_area_cd = p.prog_area_cd
	LEFT OUTER JOIN nbs_srte.dbo.jurisdiction_code as j
	ON
	  i.jurisdiction_cd = j.code
	LEFT OUTER JOIN nbs_srte.dbo.code_value_general as c
	ON 
	  i.record_status_cd = c.code AND
	  c.code_set_nm='REC_STAT'
	LEFT JOIN nbs_odse.dbo.nbs_act_entity as part
	ON 
	  i.intervention_uid = part.act_uid AND
	  part.type_cd = 'SubOfVacc'
	inner JOIN nbs_odse.dbo.person 
	ON 
	  person.person_uid = part.entity_uid
	 /* add user name */
	LEFT outer join nbs_odse.dbo.user_profile as up1
		    on i.add_user_id = up1.nedss_entry_id
	/* last change user name */
	LEFT outer join nbs_odse.dbo.user_profile as up2
    		on i.last_chg_user_id = up2.nedss_entry_id
	WHERE  CONVERT(DATE, i.add_time) >=  CONVERT(DATE, DATEADD(DAY, -&METRICS_GOBACKBY_DAYS, GETDATE()))  	/* DATEPART(i.add_time) >=  TODAY()-&METRICS_GOBACKBY_DAYS */ 
) by sql;
disconnect from sql; 
QUIT;	 

/* Texas - Moved code execution to database 08/20/2020 */
PROC SQL;
/*
	Lab Reports ( NON ELR )
*/
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
INSERT INTO EVENT_METRIC 
	SELECT 
		  'LabReport',
		  o.observation_uid,
		  o.local_id,
		  person.local_id,
		  NULL,
		  NULL,
		  o.prog_area_cd,
		  p.prog_area_desc_txt,
		  o.program_jurisdiction_oid,
		  o.jurisdiction_cd,
		  j.code_desc_txt,
		  o.record_status_cd,
		  c.CODE_DESC_TXT,
		  o.record_status_time,
		  o.electronic_ind,
		  o.STATUS_CD,
		  cvgst.CODE_DESC_TXT,
		  o.STATUS_TIME,	
		  o.add_time,
		  o.add_user_id,
		  o.last_chg_time,
		  o.last_chg_user_id,
		  NULL,
		  NULL,
		  NULL,
		  NULL,
		LTRIM(RTRIM(up1.last_nm)) + ', ' + LTRIM(RTRIM(up1.first_nm)),
        LTRIM(RTRIM(up2.last_nm)) + ', ' + LTRIM(RTRIM(up2.first_nm))
	FROM 
	  nbs_odse.dbo.observation o
	LEFT OUTER JOIN nbs_srte.dbo.program_area_code as p
	ON
	  o.prog_area_cd = p.prog_area_cd
	LEFT OUTER JOIN nbs_srte.dbo.jurisdiction_code as j
	ON
	  o.jurisdiction_cd = j.code
	LEFT OUTER JOIN nbs_srte.dbo.code_value_general as c
	ON 
	  o.record_status_cd = c.code AND
	  c.code_set_nm='REC_STAT'
	LEFT JOIN nbs_odse.dbo.participation as part
	ON 
	  o.observation_uid = part.act_uid AND
	  part.type_cd = 'PATSBJ'
	left outer join nbs_srte.dbo.code_value_general as cvgst
		on o.status_cd = cvgst.code
	   	and cvgst.code_set_nm='ACT_OBJ_ST'
	LEFT JOIN nbs_odse.dbo.person 
	ON 
	  person.person_uid = part.subject_entity_uid
  	/* add user name */
	LEFT outer join nbs_odse.dbo.user_profile as up1
		on o.add_user_id = up1.nedss_entry_id
	/* last change user name */
	LEFT outer join nbs_odse.dbo.user_profile as up2
		on o.last_chg_user_id = up2.nedss_entry_id
	WHERE
	  o.obs_domain_cd_st_1='Order' AND
	  o.ctrl_cd_display_form='LabReport' AND
	  o.electronic_ind <> 'Y' AND
	  CONVERT(DATE, o.add_time) >=  CONVERT(DATE, DATEADD(DAY, -&METRICS_GOBACKBY_DAYS, GETDATE())) 		/* DATEPART(O.add_time) >=  TODAY()-&METRICS_GOBACKBY_DAYS  */
) by sql;
disconnect from sql; 
QUIT;	 

/* Texas - Moved code execution to database 08/20/2020 for performance reasons */
PROC SQL;
/*
	Lab Reports ( ELR )
*/
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
INSERT INTO EVENT_METRIC 
	SELECT 
		  'LabReport',
		  o.observation_uid,
		  o.local_id,
		  person.local_id,
		  NULL,
		  NULL,
		  o.prog_area_cd,
		  p.prog_area_desc_txt,
		  o.program_jurisdiction_oid,
		  o.jurisdiction_cd,
		  j.code_desc_txt,
		  o.record_status_cd,
		  c.code_desc_txt,
		  o.record_status_time,
		  o.electronic_ind,
		  o.status_cd,
		  c1.code_desc_txt,
		  o.status_time,
		  o.add_time,
		  o.add_user_id,
		  o.last_chg_time,
		  o.last_chg_user_id,
		  NULL,
		  NULL,
		  NULL,
		  NULL,
		LTRIM(RTRIM(up1.last_nm)) + ', ' + LTRIM(RTRIM(up1.first_nm)),
        LTRIM(RTRIM(up2.last_nm)) + ', ' + LTRIM(RTRIM(up2.first_nm))
	FROM 
	  nbs_odse.dbo.observation o
	LEFT OUTER JOIN nbs_srte.dbo.program_area_code as p
	ON
	  o.prog_area_cd = p.prog_area_cd
	LEFT OUTER JOIN nbs_srte.dbo.jurisdiction_code as j
	ON
	  o.jurisdiction_cd = j.code
	LEFT OUTER JOIN nbs_srte.dbo.code_value_general as c1
	  on o.status_cd = c1.code AND
	  c1.code_set_nm='ACT_OBJ_ST' 
	LEFT OUTER JOIN nbs_srte.dbo.code_value_general as c
	ON
	  o.record_status_cd = c.code AND
	  c.code_set_nm='REC_STAT'
	LEFT JOIN nbs_odse.dbo.participation as part
	ON 
	  o.observation_uid = part.act_uid AND
	  part.type_cd = 'PATSBJ'
	LEFT JOIN nbs_odse.dbo.person 
	ON 
	  person.person_uid = part.subject_entity_uid
  	/* add user name */
	LEFT outer join nbs_odse.dbo.user_profile as up1
		on o.add_user_id = up1.nedss_entry_id
	/* last change user name */
	LEFT outer join nbs_odse.dbo.user_profile as up2
		on o.last_chg_user_id = up2.nedss_entry_id
	WHERE
	  o.obs_domain_cd_st_1='Order' AND
	  o.ctrl_cd_display_form='LabReport' AND
	  o.electronic_ind='Y' AND
	  CONVERT(DATE, o.add_time) >=  CONVERT(DATE, DATEADD(DAY, -&METRICS_GOBACKBY_DAYS, GETDATE())) 	/* DATEPART(O.add_time) >=  TODAY()-&METRICS_GOBACKBY_DAYS  */
) by sql;
disconnect from sql; 
QUIT;	 

/* Texas - Moved code execution to database 08/20/2020 for performance reasons*/
PROC SQL;
/*
	Morbidity Reports 
*/
connect to odbc as sql (Datasrc=&datasource.  USER=&username.  PASSWORD=&password.);
EXECUTE (
INSERT INTO EVENT_METRIC 
	SELECT 
		  'MorbReport',
		  o.observation_uid,
		  o.local_id,
		  person.local_id,
		  o.cd,
		  q.condition_short_nm,
		  o.prog_area_cd,
		  p.prog_area_desc_txt,
		  o.program_jurisdiction_oid, 
		  o.jurisdiction_cd,
		  j.code_desc_txt,
		  o.record_status_cd,
		  c.CODE_DESC_TXT,
		  o.record_status_time,
		  o.electronic_ind,
		  o.STATUS_CD,
		  cvgst.CODE_DESC_TXT,
		  o.STATUS_TIME,	
		  o.add_time,
		  o.add_user_id,
		  o.last_chg_time,
		  o.last_chg_user_id,
		  NULL,
		  NULL,
		  NULL,
		  NULL,
		LTRIM(RTRIM(up1.last_nm)) + ', ' + LTRIM(RTRIM(up1.first_nm)),
        LTRIM(RTRIM(up2.last_nm)) + ', ' + LTRIM(RTRIM(up2.first_nm))
	FROM 
	  nbs_odse.dbo.observation o
	LEFT OUTER JOIN nbs_srte.dbo.program_area_code as p
	ON
	  o.prog_area_cd = p.prog_area_cd
	LEFT OUTER JOIN nbs_srte.dbo.condition_code as q
	ON
	  o.cd = q.condition_cd
	LEFT OUTER JOIN nbs_srte.dbo.code_value_general as cvgst
	  on cvgst.code_set_nm='ACT_OBJ_ST' AND
	  o.status_cd = cvgst.code
	LEFT OUTER JOIN nbs_srte.dbo.jurisdiction_code as j
	ON
	  o.jurisdiction_cd = j.code
	LEFT OUTER JOIN nbs_srte.dbo.code_value_general as c
	ON 
	  o.record_status_cd = c.code AND
	  c.code_set_nm='REC_STAT'
	LEFT OUTER JOIN nbs_odse.dbo.participation as part
	ON 
	  o.observation_uid = part.act_uid AND
	  part.type_cd = 'SubjOfMorbReport'
	LEFT OUTER JOIN nbs_odse.dbo.person 
	ON 
	  person.person_uid = part.subject_entity_uid
  	/* add user name */
	LEFT outer join nbs_odse.dbo.user_profile as up1
		on o.add_user_id = up1.nedss_entry_id
	/* last change user name */
	LEFT outer join nbs_odse.dbo.user_profile as up2
		on o.last_chg_user_id = up2.nedss_entry_id
	WHERE
	  o.obs_domain_cd_st_1='Order' AND
	  o.ctrl_cd_display_form='MorbReport' AND
	  CONVERT(DATE, o.add_time) >=  CONVERT(DATE, DATEADD(DAY, -&METRICS_GOBACKBY_DAYS, GETDATE())) 		/* DATEPART(o.add_time) >=  TODAY()-&METRICS_GOBACKBY_DAYS */
) by sql;
disconnect from sql; 
QUIT;	 


PROC SQL;
INSERT INTO NBS_RDB.EVENT_METRIC 
	SELECT 
	'CONTACT'
	,CT_CONTACT_UID
	,CT_CONTACT.LOCAL_ID
	,PERSON.LOCAL_ID AS LOCAL_PATIENT_ID
	,''
	,''
	,CT_CONTACT.PROG_AREA_CD
	,P.PROG_AREA_DESC_TXT
	,CT_CONTACT.PROGRAM_JURISDICTION_OID
	,CT_CONTACT.JURISDICTION_CD		  
	,J.CODE_DESC_TXT
	,CT_CONTACT.RECORD_STATUS_CD
	,C.CODE_DESC_TXT
	,CT_CONTACT.RECORD_STATUS_TIME
	,''
	,''
	,''
	,.
	,CT_CONTACT.ADD_TIME
	,CT_CONTACT.ADD_USER_ID
	,CT_CONTACT.LAST_CHG_TIME
	,CT_CONTACT.LAST_CHG_USER_ID
	,''
	,''
	,''
	,''
	,(trim(left(up1.last_nm))||', '||trim(left(up1.first_nm)))
    ,TRIM(LEFT(UP2.LAST_NM))||', '||TRIM(LEFT(UP2.FIRST_NM))
  FROM NBS_ODS.CT_CONTACT
  INNER JOIN NBS_ODS.PERSON ON 
  PERSON.PERSON_UID = CT_CONTACT.SUBJECT_ENTITY_UID
  INNER JOIN NBS_SRT.PROGRAM_AREA_CODE AS P  
		ON CT_CONTACT.PROG_AREA_CD = P.PROG_AREA_CD  
	INNER JOIN NBS_SRT.JURISDICTION_CODE AS J
		ON CT_CONTACT.JURISDICTION_CD = J.CODE  
	INNER JOIN NBS_SRT.CODE_VALUE_GENERAL C
		ON CT_CONTACT.RECORD_STATUS_CD = C.CODE AND 
	   	C.CODE_SET_NM='REC_STAT'
/* ADD USER NAME */
      	LEFT OUTER JOIN NBS_ODS.USER_PROFILE AS UP1
	    ON CT_CONTACT.ADD_USER_ID = UP1.NEDSS_ENTRY_ID
	/* LAST CHANGE USER NAME */
      	LEFT OUTER JOIN NBS_ODS.USER_PROFILE AS UP2
            ON CT_CONTACT.LAST_CHG_USER_ID = UP2.NEDSS_ENTRY_ID
	WHERE  DATEPART(CT_CONTACT.ADD_TIME) >=  TODAY()-&METRICS_GOBACKBY_DAYS ;

QUIT;
