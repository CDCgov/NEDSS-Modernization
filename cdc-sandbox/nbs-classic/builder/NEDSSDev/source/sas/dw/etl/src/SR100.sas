Proc SQL;
create table rdbdata.SR100
as
select 
	i.inv_local_id as LOCAL_ID,
	i.CASE_RPT_MMWR_WK as MMWRWK, 
	I.CASE_RPT_MMWR_YR as MMWRYR,
	src.SUM_RPT_CASE_COUNT as NBR_CASES,
	c.CONDITION_CD as CONDITION_CD,
	c.CONDITION_DESC as CONDITION,
	SRC.COUNTY_CD as COUNTY_CD,
	sccv.code_desc_txt as COUNTY_NAME,
	SRC.state_CD as STATE_CD,
	scg.SUMMARY_CASE_SRC_TXT as RPT_SOURCE,
	cvg.code_desc as RPT_SOURCE_DESC,
	rd1.DATE_MM_DD_YYYY as DATE_REPORTED,
	rd1.CLNDR_MON_NAME as MONTH_REPORTED,
	rd.DATE_MM_DD_YYYY as  NOTIF_CREATE_DATE,
	rd.CLNDR_MON_NAME as  NOTIF_CREATE_MONTH,
	rd.CLNDR_YR as  NOTIF_CREATE_YEAR,
	src.SUM_RPT_CASE_COMMENTS as REPORT_COMMENTS,
	em.ADD_TIME as DATE_ADDED,
	em.ADD_USER_NAME as ADD_USER_NAME,
	src.INVESTIGATION_KEY as INVESTIGATION_KEY
	
from
NBS_RDB.SUMMARY_REPORT_CASE SRC
left outer join NBS_RDB.SUMMARY_CASE_GROUP SCG
	on src.summary_case_src_key = scg.summary_case_src_key
left outer join NBS_RDB.CODE_VAL_GENERAL CVG
	on scg.SUMMARY_CASE_SRC_TXT = cvg.code_val
		and cvg.cd = 'SUM103'
join NBS_RDB.INVESTIGATION I
	on src.investigation_key = i.investigation_key
left outer join NBS_RDB.rdb_date RD 
	on rd.date_key = src.NOTIFICATION_SEND_DT_KEY
left outer join NBS_RDB.rdb_date RD1 
	on rd1.DATE_MM_DD_YYYY = I.EARLIEST_RPT_TO_STATE_DT
join NBS_RDB.condition c 
	on c.condition_key = src.condition_key 
left outer join NBS_RDB.CASE_COUNT cc
	on cc.investigation_key=i.investigation_key
join NBS_SRT.state_county_code_value sccv
	on SRC.county_cd= sccv.code
join NBS_RDB.event_metric em
	on em.local_id=I.inv_local_id
order by 
	i.inv_local_id asc;
quit;

/** Translate values 

SOURCE - If no source selected, translate to 'N/A'
SOURCE_DESC - If no source selected, translate to 'No Source Selected'

**/

data rdbdata.SR100;
	set rdbdata.SR100;
		if RPT_SOURCE = '' then RPT_SOURCE = 'N/A';
		if RPT_SOURCE_DESC = '' then RPT_SOURCE_DESC = 'No Source Selected';
run;

%dbload (SR100, rdbdata.SR100);

quit;

