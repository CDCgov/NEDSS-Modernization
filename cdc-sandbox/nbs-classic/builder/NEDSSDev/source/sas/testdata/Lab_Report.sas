/*-------------------------------------------------------

	Lap Report Dimension

	Note:
		1) program area cd

---------------------------------------------------------*/

proc sql;
create table Lab_Report as 
select 	obs.local_id				'LAB_RPT_LOCAL_ID' as lab_rpt_local_id,
		obs.shared_ind				'LAB_RPT_SHARE_IND' as lab_rpt_share_ind,
		obs.PROGRAM_JURISDICTION_OID 'OID' as oid,	
		obs.STATUS_CD	   		 	'LAB_RPT_STATUS' as lab_rpt_status,
		obs.ADD_TIME				'LAB_RPT_CREATED_DT' as LAB_RPT_CREATED_DT,
		obs.ADD_USER_ID  		 	'LAB_RPT_CREATED_BY' as LAB_RPT_CREATED_BY, 
		obs.rpt_to_state_time  		'LAB_RPT_RECEIVED_BY_PH_DT' as LAB_RPT_RECEIVED_BY_PH_DT, 
		obs.LAST_CHG_TIME 			'LAB_RPT_LAST_UPDATE_DT' as LAB_RPT_LAST_UPDATE_DT, 
		obs.LAST_CHG_USER_ID		'LAB_RPT_LAST_UPDATE_BY' as LAB_RPT_LAST_UPDATE_BY, 
		obs.electronic_ind			'ELR_IND' as ELR_IND, 
		obs.jurisdiction_cd			'JURISDICTION_CD' as Jurisdiction_cd,
		put(obs.jurisdiction_cd, $JURCODE.)  as JURISDICTION_NM,	
		/*obs.PROG_AREA_CD,*/
		obs.activity_to_time   	 	as lab_report_date, 
		obs.observation_uid			'LAB_RPT_UID'as lab_rpt_uid
from 	nbs_ods.observation as obs
where obs.obs_domain_cd_st_1 = 'Order'
	  and (obs.CTRL_CD_DISPLAY_FORM  = 'LabReport'
	  		or obs.CTRL_CD_DISPLAY_FORM  = 'LabReportMorb')
order by obs.observation_uid
;
quit;

%assign_key(Lab_Report, Lab_Rpt_Key);

/*-------------------------------------------------------

	Lab_Report_User_Comment Dimension

	Note: Comments under the Order Test object (LAB214)
---------------------------------------------------------*/
proc sql;
create table Lab_Rpt_User_Comment as
select 	root.Lab_Rpt_Key,
		/*root.lab_rpt_uid*/
		obs.activity_to_time	'COMMENTS_FOR_ELR_DT' as comments_for_elr_dt,
		lab214.add_user_id		'USER_COMMENT_CREATED_BY' as user_comment_created_by,
		ovt.value_txt			'USER_RPT_COMMENTS' as user_rpt_comments

from 	Lab_Report					as root,
		nbs_ods.act_relationship 	as ar1,
		nbs_ods.observation			as obs,
		nbs_ods.act_relationship 	as ar2,
		nbs_ods.observation			as lab214,
		nbs_ods.obs_value_txt 				as ovt
where   ovt.value_txt is not null
		and root.lab_rpt_uid = ar1.target_act_uid
		and ar1.type_cd = 'APND'
		and ar1.source_act_uid = obs.observation_uid
		and obs.OBS_DOMAIN_CD_ST_1 ='C_Order'
		and obs.observation_uid = ar2.target_act_uid
		and ar2.source_act_uid = lab214.observation_uid
		and ar2.type_cd = 'COMP'
		and lab214.OBS_DOMAIN_CD_ST_1 ='C_Result'
		and lab214.observation_uid = ovt.observation_uid
		
;
quit;	
%assign_key(Lab_Rpt_User_Comment, USER_COMMENT_KEY);


data  Lab_Rpt_User_Comment;
set Lab_Rpt_User_Comment;
if lab_rpt_key =. then lab_rpt_key =1;
run;
DATA lab_report (drop = lab_report_date);
set lab_report;
data rdbdata.Lab_Report;
set Lab_Report;
data rdbdata.Lab_Rpt_User_Comment;
set Lab_Rpt_User_Comment;
run;



