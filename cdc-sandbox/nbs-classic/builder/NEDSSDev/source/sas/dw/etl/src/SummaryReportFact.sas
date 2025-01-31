/*********************************/
/*** Summary Report Case Fact Table ***/
/*********************************/

/* assigns keys (key) to a dataset(ds). Assigns the same key if the (code) is the same.  */
%macro assign_dup_key (ds, code, key);                                                                                                            
 data &ds (Drop = temp_code);                                                                                                                              
  retain &key 1 temp_code;
  
  if &key=1 then output;                                                                                                                
  set &ds;  
  if   temp_code ^= &code then do;
        &key+1;                                                                                                                         
        output; 
		temp_code = &code;
  END; 
  else output;
 run;                                                                                                                                   
%mend assign_dup_key;

/*Creates the initial Summary Report Work dataset   */

Proc SQL;
 Create table SumRptWork as 
  SELECT act.TARGET_ACT_UID as TARGET_UID,
         act.SOURCE_ACT_UID as SUMMARY_ROOT_UID, 
         phc.PUBLIC_HEALTH_CASE_UID as CASE_UID, 
         phc.CD as CONDITION_CD,  
		 dt.DATE_KEY as LAST_UPDATE_DT_KEY, 
		 phc.RPT_CNTY_CD as COUNTY_CD 'COUNTY_CD',
		 scc.PARENT_IS_CD as STATE_CD 'STATE_CD',
		 SCC.CODE_DESC_TXT AS COUNTY_NAME 'COUNTY_NAME'
  FROM	 nbs_ods.Observation obs, nbs_ods.Act_Relationship act, 
         nbs_ods.Public_Health_Case phc, nbs_srt.State_County_Code_Value scc,
		 rdbdata.Datetable dt
  WHERE obs.OBSERVATION_UID = act.SOURCE_ACT_UID
  AND PUBLIC_HEALTH_CASE_UID = act.TARGET_ACT_UID
  AND datepart(phc.LAST_CHG_TIME) = datepart(dt.DATE_MM_DD_YYYY)
  AND phc.RPT_CNTY_CD = scc.CODE
  AND obs.CD like 'Summary_Report_Form%';
Quit;
Proc SQL;
 Create table SummaryIDs as
  SELECT act.SOURCE_ACT_UID , act.TARGET_ACT_UID as SUMMARY_ROOT_UID , sum.CASE_UID
   FROM nbs_ods.Act_Relationship act , SumRptWork sum
   WHERE act.TARGET_ACT_UID = sum.SUMMARY_ROOT_UID;
Quit;
Proc SQL;
 Create table TargetIDs as
  SELECT act.SOURCE_ACT_UID , act.TARGET_ACT_UID as TARGET_UID 
   FROM nbs_ods.Act_Relationship act, SumRptWork sum
   WHERE act.TARGET_ACT_UID = sum.TARGET_UID; 
Quit;

/* Get all the SUM_RPT_CASE_COUNT data */

Proc SQL;
 Create table CountData as
  SELECT obn.NUMERIC_VALUE_1 as SUM_RPT_CASE_COUNT, si.SUMMARY_ROOT_UID,si.SOURCE_ACT_UID
   FROM nbs_ods.Obs_Value_Numeric obn,nbs_ods.Observation obs, SummaryIDs si
   WHERE obs.OBSERVATION_UID = obn.OBSERVATION_UID
   and obs.OBSERVATION_UID = si.SOURCE_ACT_UID
   and obs.CD = 'SUM104';
Quit;


Proc Sort  Data = CountData;
  FORMAT SUM_RPT_CASE_COUNT 6.0;
  INFORMAT SUM_RPT_CASE_COUNT  6.0;
  By SUMMARY_ROOT_UID SOURCE_ACT_UID;
Run;

/* Get all the SUM_RPT_CASE_COMMENTS data */

Proc SQL;
 Create table CommentData as
  SELECT TRANSLATE(obt.VALUE_TXT,' ' ,'0D0A'x) as SUM_RPT_CASE_COMMENTS, si.SUMMARY_ROOT_UID,si.SOURCE_ACT_UID
   FROM nbs_ods.Obs_Value_Txt obt,nbs_ods.Observation obs, SummaryIDs si
   WHERE obs.OBSERVATION_UID = obt.OBSERVATION_UID
   and obs.OBSERVATION_UID = si.SOURCE_ACT_UID
   and obs.CD = 'SUM105';
Quit;

Proc Sort Data = CommentData;
  By SUMMARY_ROOT_UID SOURCE_ACT_UID;
Run;


/* Get all the SUM_RPT_CASE_STATUS, NOTIFICATION_SEND_DT_KEY data */

Proc SQL;
 Create table NotificationData as
  SELECT nt.RECORD_STATUS_CD as SUM_RPT_CASE_STATUS, nt.RPT_SENT_TIME as NOTIFICATION_SEND_DT, 
		dt.DATE_KEY as NOTIFICATION_SEND_DT_KEY, ti.TARGET_UID,
		nt.last_chg_time as NOTI_LAST_CHG_TIME
   FROM nbs_ods.Notification nt, TargetIDs ti, rdbdata.Datetable dt
   WHERE nt.NOTIFICATION_UID = ti.SOURCE_ACT_UID
   AND datepart(nt.RPT_SENT_TIME) = datepart(dt.DATE_MM_DD_YYYY);
Quit;

Proc Sort Data = NotificationData ;
  By TARGET_UID;
Run;


Proc Sort Data = SumRptWork;
  By SUMMARY_ROOT_UID;
Run;

/* Add The SUM_RPT_CASE_COUNT column and The SUM_RPT_CASE_COMMENTS column to the SumRptWork data set */

Data SumRptWork;
  Merge SumRptWork (in=a) CountData CommentData;
  By SUMMARY_ROOT_UID;
  if a;
Run;

Proc Sort Data = SumRptWork;
  By TARGET_UID;
Run;

/* Add The SUM_RPT_CASE_STATUS, NOTIFICATION_SEND_DT_KEY columns to the SumRptWork data set */

Data SumRptWork ;
  Merge SumRptWork(in=a) NotificationData;
  By TARGET_UID;
  if NOTIFICATION_SEND_DT_KEY =. then NOTIFICATION_SEND_DT_KEY = 1;
  if LAST_UPDATE_DT_KEY =. then LAST_UPDATE_DT_KEY = 1;
  if a;
Run;

Proc Sort Data = SumRptWork;
  By CONDITION_CD;
Run;

/* Get all the Condition data */

Proc SQL;
 Create table ConditionWork as
  SELECT co.CONDITION_CD, co.CONDITION_KEY 
   FROM rdbdata.Condition co;
Quit;

Proc Sort  Data = ConditionWork;
  By CONDITION_CD;
Run;

/* Add The CONDITION_KEY column to the SumRptWork data set */

Data SumRptWork (DROP = CONDITION_CD);
  Merge SumRptWork(in=a) ConditionWork ;
  By CONDITION_CD;
  if CONDITION_KEY =. then CONDITION_KEY = 1;
  if a;
Run;
Proc Sort Data = SumRptWork;
  By CASE_UID;
Run;

/* Get all the Investigation data */

Proc SQL;
 Create table InvestigationWork as
  SELECT inv.CASE_UID , inv.INVESTIGATION_KEY 
   FROM nbs_rdb.Investigation inv;
Quit;

Proc Sort  Data = InvestigationWork NODUPKEY;
  By CASE_UID;
Run;

/* Add The INVESTIGATION_KEY column to the SumRptWork data set */

Data SumRptWork;
  Merge SumRptWork(in=a) InvestigationWork ;
  By CASE_UID;
  if INVESTIGATION_KEY =. then INVESTIGATION_KEY = 1;
  if a;
Run;

/* Create the CaseSrc working data set for the Summary_Case_Group table */

Proc SQL;
 Create table CaseSrc as
  SELECT obc.CODE as SUMMARY_CASE_SRC_TXT, si.SUMMARY_ROOT_UID, si.CASE_UID,si.SOURCE_ACT_UID,
  obs.record_status_cd as STATUS
   FROM nbs_ods.Obs_Value_Coded obc,nbs_ods.Observation obs, SummaryIDs si
   WHERE obs.OBSERVATION_UID = obc.OBSERVATION_UID
   and obs.OBSERVATION_UID = si.SOURCE_ACT_UID 
   and obs.CD = 'SUM103';   
Quit;

Proc Sort Data = CaseSrc;
  By SUMMARY_ROOT_UID SOURCE_ACT_UID;
Run;

%assign_dup_key (ds=CaseSrc, code=SUMMARY_CASE_SRC_TXT, key=SUMMARY_CASE_SRC_KEY);

/*Proc Sort  Data = CaseSrc;
  By CASE_UID;
Run;*/

/* Add the SUMMARY_CASE_SRC_KEY column to the SumRptWork data set */
/* Temporarily set LDF_GROUP_KEY =1; */

Data SumRptWork (DROP = SUMMARY_CASE_SRC_TXT);
  Merge SumRptWork(in=a) CaseSrc ;
  By SUMMARY_ROOT_UID;
  if SUMMARY_CASE_SRC_KEY =. then SUMMARY_CASE_SRC_KEY = 1;
  If SUM_RPT_CASE_COUNT = . then SUM_RPT_CASE_COUNT = 0;
  LDF_GROUP_KEY = 1;
  if a;
Run;

/* Create the Rdbdata.Summary_Case_Group data set that will be used to create the Summary_Case_Group database table */

Proc SQL;
 Create table RDBDATA.Summary_Case_Group as
  SELECT distinct(SUMMARY_CASE_SRC_KEY),SUMMARY_CASE_SRC_TXT
   FROM CaseSrc;
Quit;

proc sort data=SumRptWork;
	BY descending NOTI_LAST_CHG_TIME;
RUN;

/*There should not be duplicate keys but if there is for some strange reason remove duplicate keys if any*/
proc sort data=SumRptWork NODUPKEY;
	BY Condition_Key Investigation_Key SUMMARY_CASE_SRC_KEY LDF_GROUP_KEY;
RUN;

/* Filter INACTIVE observations (Status) related to the summary report */
data SumRptWork (DROP = NOTI_LAST_CHG_TIME ID);
	set SumRptWork;
	if STATUS = 'ACTIVE' or STATUS = '' then output;	
run;

Data Rdbdata.Summary_Report_Case;
  Set SumRptWork(DROP = TARGET_UID SUMMARY_ROOT_UID CASE_UID NOTIFICATION_SEND_DT STATUS);
Run;

/**Delete temporary data sets**/
PROC datasets library = work nolist;
delete 
	SumRptWork
	SummaryIDs
	TargetIDs
	CountData
	CommentData
	NotificationData
	ConditionWork
	InvestigationWork
	CaseSrc 
;
run;
quit;



/*-----------------------------------------
	SUMMARY_REPORT
-------------------------------------------*/
%dbload (SUMMARY_CASE_GROUP, rdbdata.SUMMARY_CASE_GROUP);
%dbload (SUMMARY_REPORT_CASE, rdbdata.SUMMARY_REPORT_CASE);
%checkerr;