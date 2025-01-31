/***********************************************************

	Case_Count Fact

************************************************************/
PROC SQL;
Create table PHC as select public_health_case_uid,
CD,
activity_from_time,
diagnosis_time,
Case_Type_CD,
rpt_form_cmplt_time,
group_case_cnt			as INVESTIGATION_COUNT,
group_case_cnt			as Case_COUNT
from NBS_ODS.public_health_case;

create table PHC_CASE as select PHC.*,
L_INVESTIGATION.INVESTIGATION_KEY FROM 
PHC inner join 
nbs_rdb.L_INVESTIGATION on L_INVESTIGATION.case_uid=PHC.public_health_case_uid;

QUIT;
proc sql;
create table Case_Count1 as 
Select 
inv.public_health_case_uid            as public_health_case_uid 'public_health_case_uid',
		con.Condition_key,
		coalesce(per1.Patient_key,1)	as Patient_key 'Patient_key',	/*Summary Case patient key = 1*/
		coalesce(per2.Provider_key, 1)			as Investigator_key 'Investigator_key',
		coalesce(per3.Provider_key, 1)			as Physician_key 'Physician_key',
		coalesce(per4.Provider_key, 1)			as Reporter_key 'Reporter_key',
		coalesce(org1.Organization_key, 1)			as Rpt_Src_Org_key 'Rpt_Src_Org_key',
		coalesce(org2.Organization_key, 1)			as ADT_HSPTL_KEY 'ADT_HSPTL_KEY',
		coalesce(dt1.Date_key, 1)			as Inv_Assigned_dt_key 'Inv_Assigned_dt_key',		
		inv.Investigation_key,
		coalesce(dt2.Date_key, 1)			as INV_START_DT_KEY 'INV_START_DT_KEY',
		coalesce(dt3.Date_key, 1)			as DIAGNOSIS_DT_KEY 'DIAGNOSIS_DT_KEY',
		coalesce(dt4.Date_key, 1)			as INV_RPT_DT_KEY 'INV_RPT_DT_KEY',
		coalesce(loc.geocoding_Location_key,1)	as geocoding_Location_key 'geocoding_Location_key',
	inv.case_type_cd,
	inv.CD			label='condition_code',
	inv.INVESTIGATION_COUNT,
	inv.Case_COUNT
	
From
	PHC_CASE inv

	/*condition */
	inner join rdbdata.condition		as con
		on	con.condition_cd = inv.CD 

	/*patient, left join to include summary case*/
	left join nbs_ods.participation	as par1
		on inv.public_health_case_uid = par1.act_uid
		and par1.type_cd ='SubjOfPHC'
		and par1.act_class_cd = 'CASE'
		and par1.subject_class_cd = 'PSN'
	left join nbs_rdb.l_Patient	as per1
		on	per1.patient_uid = par1.subject_entity_uid 
		
	/* Investigator */

	left join nbs_ods.participation	as par2
		on inv.public_health_case_uid = par2.act_uid
		and par2.type_cd ='InvestgrOfPHC'
		and par2.act_class_cd = 'CASE'
		and par2.subject_class_cd = 'PSN'
	left join nbs_rdb.l_Provider  as per2
		on	per2.provider_uid = par2.subject_entity_uid

	/* Provider */

	left join nbs_ods.participation	as par3
		on inv.public_health_case_uid = par3.act_uid
		and par3.type_cd ='PhysicianOfPHC'
		and par3.act_class_cd = 'CASE'
		and par3.subject_class_cd = 'PSN'
	left join nbs_rdb.l_Provider as per3
		on	per3.provider_uid = par3.subject_entity_uid

	/* ReporterOfPHC */

	left join nbs_ods.participation	as par4
		on inv.public_health_case_uid = par4.act_uid
		and par4.type_cd ='PerAsReporterOfPHC'
		and par4.act_class_cd = 'CASE'
		and par4.subject_class_cd = 'PSN'
	left join nbs_rdb.l_Provider as per4
		on	per4.provider_uid = par4.subject_entity_uid


	/* Reporting Org */
	left join nbs_ods.participation	 as par5
		on inv.public_health_case_uid = par5.act_uid
		and par5.type_cd ='OrgAsReporterOfPHC'
		and par5.act_class_cd = 'CASE'
		and par5.subject_class_cd = 'ORG'
	left join nbs_rdb.l_Organization as org1
		on	org1.organization_uid = par5.subject_entity_uid 

	/* ADT Hospital Org */
	left join nbs_ods.participation	as par6
		on inv.public_health_case_uid = par6.act_uid
		and par6.type_cd ='HospOfADT'
		and par6.act_class_cd = 'CASE'
		and par6.subject_class_cd = 'ORG'
	left join nbs_rdb.l_Organization as org2
		on	org2.Organization_uid = par6.subject_entity_uid
	
	/* Investigator assigned date */
	left join rdbdata.Datetable 		as dt1
		on dt1.DATE_MM_DD_YYYY = par2.from_time
	/*INV_START_DT_KEY*/
	left join rdbdata.Datetable 		as dt2
		on dt2.DATE_MM_DD_YYYY = inv.activity_from_time

	/*DIAGNOSIS_DT_KEY*/
	left join rdbdata.Datetable 		as dt3
		on dt3.DATE_MM_DD_YYYY = inv.diagnosis_time

	left join nbs_rdb.geocoding_Location as loc
		on loc.entity_uid = per1.patient_uid

	/*INV_RPT_DT_KEY */
	left join rdbdata.Datetable 		as dt4
		on dt4.DATE_MM_DD_YYYY = inv.rpt_form_cmplt_time
where  inv.Investigation_key ~=1 
Order by 1;
quit;

proc sql;
create table sumcasecnt as
	select 	
		ar2.target_act_uid 	as public_health_case_uid,
		ar2.source_act_uid 	as sum_report_form_uid,
		ar1.target_act_uid	as ar1_sum_report_form_uid, /*--Summary_Report_Form Obs_uid*/ 
		ar1.source_act_uid	as ar1_sum107_uid, /*--SUM107 Obs_uid*/
		ob1.observation_uid as ob1_sum107_uid,
		ob1.cd 				as ob1_cd,
		ob2.observation_uid as ob2_sum_report_form_uid,
		ob2.cd 				as ob2_cd,
		ovn.numeric_value_1 as Case_COUNT,
		ar1.type_cd			as ar1_type_cd,
		ar1.source_class_cd	as ar1_source_class_cd,
		ar1.target_class_cd	as ar1_target_class_cd,
		ar2.type_cd			as ar2_type_cd,
		ar2.source_class_cd	as ar2_source_class_cd,
		ar2.target_class_cd	as ar2_target_class_cd
		
	from 	
		Case_Count1	phc,
		nbs_ods.act_relationship as ar1, 	/*-- OBS sum107 to OBS Summary_Report_Form */
		nbs_ods.observation as ob1, 		/*--SUM107 */
		nbs_ods.observation as ob2, 		/* --Summary_Report_Form*/
		nbs_ods.obs_value_numeric as ovn, 	/*--group_case_cnt*/
		nbs_ods.act_relationship as ar2 	/*OBS Summary_Report_Form to phc */ 
	where 	
		phc.case_type_cd ='S'
		and phc.public_health_case_uid = ar2.target_act_uid
		and ar1.source_act_uid = ob1.observation_uid
		and ar1.target_act_uid = ob2.observation_uid
		and ar1.type_cd='SummaryFrmQ'
		and ob1.cd = 'SUM107'
		and ob2.cd = 'Summary_Report_Form'
		and ob1.observation_uid = ovn.observation_uid
		and ob2.observation_uid = ar2.source_act_uid
		and ar2.type_cd =  'SummaryForm'
		order by public_health_case_uid;

quit;

Data Case_Count1;
update Case_Count1 sumcasecnt(keep=public_health_case_uid Case_COUNT);
by public_health_case_uid;
run;

/*%add_common_inv_keys(Case_Count,Case_Count1);*/
data Case_Count1;
format Case_Count 8.;
set Case_Count1;
	/*drop temp fields that was not used or used for debug
	drop
	public_health_case_uid 
	condition_cd
	case_type_cd
	INV_START_DT_KEY
	DIAGNOSIS_DT_KEY
	INV_RPT_DT_KEY
	;*/
run;
proc sort data = Case_Count1 NODUPKEY;
BY Condition_Key Patient_key Investigator_key Physician_key Reporter_key Rpt_Src_Org_key ADT_HSPTL_KEY Inv_Assigned_dt_key Investigation_Key geocoding_location_key;
%DBLOAD (Case_Count, Case_Count1);

data rdbdata.Phc_keys;
set  Case_Count1;
run;

PROC SQL; 
DROP TABLE NBS_RDB.Phc_keys;
QUIT;

proc sql;
	create table NBS_RDB.Phc_keys as select * from Case_Count1;
quit;

/*
PROC datasets library = work nolist;
delete Case_Count;
delete Case_Count1;
delete sumcasecnt;
run;
quit;
*/
