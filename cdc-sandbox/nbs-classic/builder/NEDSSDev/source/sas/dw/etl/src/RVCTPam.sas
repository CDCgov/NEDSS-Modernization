proc sql;
 create table PAT_keystore as
select f_s_TB_pam.TB_pam_uid, f_s_TB_pam.person_uid, patient_key as patient_key 'patient_key', provider_uid 
	from nbs_rdb.d_patient, nbs_rdb.f_s_TB_pam
	where f_s_TB_pam.person_uid= d_patient.patient_uid;
 quit;
proc sql;
 create table PAT_prov_keystore as
select TB_pam_uid, PAT_keystore.person_uid, patient_key, PAT_keystore.provider_uid, person.provider_key as provider_key 'provider_key'
	from nbs_rdb.d_provider person, PAT_keystore
	where PAT_keystore.provider_uid= person.provider_uid;
 quit;
 PROC SORT DATA=PAT_prov_keystore nodupkey; BY TB_pam_uid; RUN;
 Proc sql;
	create table  d_move_state as select distinct d_move_state_group_key, tb_pam_uid from nbs_rdb.d_move_state;
	create table  d_hc_prov_ty_3 as select distinct d_hc_prov_ty_3_group_key, tb_pam_uid from nbs_rdb.d_hc_prov_ty_3;
	create table  d_disease_site as select distinct d_disease_site_group_key, tb_pam_uid from nbs_rdb.d_disease_site;
	create table  d_addl_risk as select distinct d_addl_risk_group_key, tb_pam_uid from nbs_rdb.d_addl_risk;
	create table  d_move_cnty as select distinct d_move_cnty_group_key, tb_pam_uid from nbs_rdb.d_move_cnty;
	create table  d_gt_12_reas as select distinct d_gt_12_reas_group_key, tb_pam_uid from nbs_rdb.d_gt_12_reas;
	create table  d_move_cntry as select distinct d_move_cntry_group_key, tb_pam_uid from nbs_rdb.d_move_cntry;
	create table  d_moved_where as select distinct d_moved_where_group_key, tb_pam_uid from nbs_rdb.d_moved_where;
	create table  d_smr_exam_ty as select distinct d_smr_exam_ty_group_key, tb_pam_uid from nbs_rdb.d_smr_exam_ty;
	create table  d_out_of_cntry as select distinct d_out_of_cntry_group_key, tb_pam_uid from nbs_rdb.d_out_of_cntry;
	create table hospital_uid_keystore as
		select f_s_tb_pam.tb_pam_uid, f_s_tb_pam.hospital_uid, organization_key as hospital_key 'hospital_key'
		from nbs_rdb.f_s_tb_pam left outer join nbs_rdb.d_organization organization
		on f_s_tb_pam.hospital_uid= organization.organization_uid;
	create table org_as_reporter_uid_keystore as
		select f_s_tb_pam.tb_pam_uid, f_s_tb_pam.org_as_reporter_uid, organization_key as org_as_reporter_key 'org_as_reporter_key'
		from nbs_rdb.f_s_tb_pam left outer join nbs_rdb.d_organization organization
		on f_s_tb_pam.org_as_reporter_uid= organization.organization_uid;
	create table person_as_reporter_keystore as
		select f_s_tb_pam.tb_pam_uid, f_s_tb_pam.person_as_reporter_uid, provider_key as person_as_reporter_key 'person_as_reporter_key'
		from nbs_rdb.f_s_tb_pam left outer join nbs_rdb.d_provider 
		on f_s_tb_pam.person_as_reporter_uid= d_provider.provider_uid;
	create table PHYSICIAN_keystore as
		select F_S_TB_pam.TB_pam_uid, F_S_TB_pam.PHYSICIAN_UID, provider_key as PHYSICIAN_KEY 'PHYSICIAN_KEY'
 		from nbs_rdb.f_s_TB_pam left outer join nbs_rdb.d_provider
 		on f_s_TB_pam.PHYSICIAN_UID= d_provider.provider_uid;
quit;

data HOSPITAL_UID_keystore;
set HOSPITAL_UID_keystore;
	if hospital_key =. then hospital_key=1;
run;

data ORG_AS_REPORTER_UID_keystore;
set ORG_AS_REPORTER_UID_keystore;
	if ORG_AS_REPORTER_key =. then ORG_AS_REPORTER_key=1;
run;

data PERSON_AS_REPORTER_keystore;
set PERSON_AS_REPORTER_keystore;
	if PERSON_AS_REPORTER_key =. then PERSON_AS_REPORTER_key=1;
run;
data PHYSICIAN_keystore;
set PHYSICIAN_keystore;
	if PHYSICIAN_key =. then PHYSICIAN_key=1;
run;

proc sql;
create table F_TB_PAM as SELECT     
	D_MOVE_STATE.D_MOVE_STATE_GROUP_KEY, 
	D_HC_PROV_TY_3.D_HC_PROV_TY_3_GROUP_KEY, 
    D_DISEASE_SITE.D_DISEASE_SITE_GROUP_KEY, 
	D_ADDL_RISK.D_ADDL_RISK_GROUP_KEY, 
    D_MOVE_CNTY.D_MOVE_CNTY_GROUP_KEY, 
	D_GT_12_REAS.D_GT_12_REAS_GROUP_KEY, 
    D_MOVE_CNTRY.D_MOVE_CNTRY_GROUP_KEY, 
	D_MOVED_WHERE.D_MOVED_WHERE_GROUP_KEY, 
    D_SMR_EXAM_TY.D_SMR_EXAM_TY_GROUP_KEY, 
	D_OUT_OF_CNTRY.D_OUT_OF_CNTRY_GROUP_KEY, 
	KEYSTORE.TB_PAM_UID, 
    KEYSTORE.patient_key AS person_key, 
	KEYSTORE.provider_uid, 
	KEYSTORE.provider_key, 
	TB.D_TB_PAM_KEY,
	INV.INVESTIGATION_KEY,
	Physician_key,
	hospital_key,
	PERSON_REPORTER.PERSON_AS_REPORTER_key AS PERSON_AS_REPORTER_key,
	REPORTING_ORG.ORG_AS_REPORTER_key AS ORG_AS_REPORTER_key,
	HOSPITAL.hospital_key AS hospital_key,
	DATE.date_key as add_date_key, DATE2.date_key as last_chg_date_key
FROM   D_MOVE_STATE INNER JOIN
      PAT_prov_keystore AS KEYSTORE ON D_MOVE_STATE.TB_PAM_UID = KEYSTORE.TB_PAM_UID 
INNER JOIN
       D_HC_PROV_TY_3 ON KEYSTORE.TB_PAM_UID = D_HC_PROV_TY_3.TB_PAM_UID 
INNER JOIN
       D_DISEASE_SITE ON KEYSTORE.TB_PAM_UID = D_DISEASE_SITE.TB_PAM_UID 
INNER JOIN
       D_ADDL_RISK ON KEYSTORE.TB_PAM_UID = D_ADDL_RISK.TB_PAM_UID 
INNER JOIN
       D_MOVE_CNTY ON KEYSTORE.TB_PAM_UID = D_MOVE_CNTY.TB_PAM_UID 
INNER JOIN
       D_GT_12_REAS ON KEYSTORE.TB_PAM_UID = D_GT_12_REAS.TB_PAM_UID 
INNER JOIN
       D_MOVE_CNTRY ON KEYSTORE.TB_PAM_UID = D_MOVE_CNTRY.TB_PAM_UID 
INNER JOIN
       D_MOVED_WHERE ON KEYSTORE.TB_PAM_UID = D_MOVED_WHERE.TB_PAM_UID 
INNER JOIN
       D_SMR_EXAM_TY ON KEYSTORE.TB_PAM_UID = D_SMR_EXAM_TY.TB_PAM_UID 
INNER JOIN
       D_OUT_OF_CNTRY ON KEYSTORE.TB_PAM_UID = D_OUT_OF_CNTRY.TB_PAM_UID 
INNER JOIN
	   nbs_rdb.D_TB_PAM AS TB ON KEYSTORE.TB_PAM_UID = TB.TB_PAM_UID 
INNER JOIN
       NBS_RDB.INVESTIGATION INV ON INV.CASE_UID = TB.TB_PAM_UID 
INNER JOIN
       NBS_RDB.event_metric em ON em.EVENT_UID = TB.TB_PAM_UID  
INNER JOIN
       HOSPITAL_UID_keystore HOSPITAL ON KEYSTORE.TB_PAM_UID = HOSPITAL.TB_PAM_UID 
INNER JOIN
       ORG_AS_REPORTER_UID_keystore AS REPORTING_ORG ON KEYSTORE.TB_PAM_UID = REPORTING_ORG.TB_PAM_UID 
INNER JOIN
       PERSON_AS_REPORTER_keystore  AS PERSON_REPORTER ON KEYSTORE.TB_PAM_UID = PERSON_REPORTER.TB_PAM_UID 
INNER JOIN
       PHYSICIAN_keystore ON KEYSTORE.TB_PAM_UID = PHYSICIAN_keystore.TB_PAM_UID 
left outer join 
	   nbs_rdb.RDB_DATE DATE 	on DATEPART(DATE.DATE_MM_DD_YYYY)=DATEPART(em.add_time)
left outer join 
		nbs_rdb.RDB_DATE DATE2 on DATEPART(DATE2.DATE_MM_DD_YYYY)=DATEPART(em.last_chg_time);
quit;
PROC SORT DATA=F_TB_PAM nodupkey; BY person_key; RUN;
%DBLOAD (F_TB_PAM, F_TB_PAM);

