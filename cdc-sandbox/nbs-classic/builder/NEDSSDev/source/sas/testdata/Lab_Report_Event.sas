/*-------------------------------------------------------

	Lab_Report_Event Fact table

	Note: out standing issues
		1) receiving_org_key
		2) condition dimension
---------------------------------------------------------*/
proc sql;
create table rdbdata.Lab_Report_Event as 
select 	tst.Lab_Test_Key,		
		coalesce(org.Org_key,1)		'REPORTING_LAB_KEY'		as Reporting_Lab_Key,
		coalesce(inv.Investigation_key,1) 	'INVESTIGATION_KEY' as Investigation_key,
		pat.person_key		'PATIENT_KEY'				as Patient_Key,
		coalesce(org2.Org_key,1)	'ORDERING_ORG_KEY'		as Ordering_org_key,
		coalesce(prv.person_key,1) 'ORDERING_PROVIDER_KEY' as Ordering_provider_key,
		coalesce(mrb.morb_rpt_key,1) 'MORB_RPT_KEY' as morb_rpt_key,
		coalesce(con.condition_key,1) 'CONDITION_KEY' as condition_key,
		dat.Date_key 						as LAB_RPT_DT_KEY,
		1									as RECEIVING_ORG_KEY,
		coalesce(ldf_g.ldf_group_key,1)			as LDF_GROUP_KEY,
		1									as LAB_RPT_COUNT	
	
from	rdbdata.Lab_Test			as tst
	/* Reporting_Lab*/
	left join nbs_ods.participation as par
		on tst.Lab_test_uid = par.act_uid
		and par.type_cd = 'AUT'
		and par.record_status_cd = 'ACTIVE'
		and par.act_class_cd = 'OBS'
		and par.subject_class_cd = 'ORG'
	left join rdbdata.Organization	as org
		on par.subject_entity_uid = org.org_uid

	/* PHC */
	left join nbs_ods.act_relationship	as act
		on tst.Lab_Test_Uid = act.source_act_uid
		and act.type_cd = 'LabReport'
		and act.target_class_cd = 'CASE'
		and act.source_class_cd = 'OBS'
		and act.record_status_cd = 'ACTIVE'
	left join rdbdata.investigation		as inv
		on act.target_act_uid = inv.case_uid

	/* Patient, keeps those with patient names */
	left join nbs_ods.participation as par1
		on tst.Lab_test_Uid = par1.act_uid
		and par1.type_cd ='PATSBJ'
		and par1.act_class_cd ='OBS'
		and par1.subject_class_cd = 'PSN'
		and par1.record_status_cd ='ACTIVE'
	left join	rdbdata.Person 		as pat
		on	par1.subject_entity_uid = pat.person_uid

	/* Ordering Facility */
	left join nbs_ods.participation as par2
		on tst.Lab_Test_uid = par2.act_uid
		/*and par2.type_cd = 'ORG'*/
		and par2.type_cd = 'ORD'
		and par2.record_status_cd = 'ACTIVE'
		and par2.act_class_cd = 'OBS'
		and par2.subject_class_cd = 'ORG'
	left join rdbdata.Organization	as org2
		on par2.subject_entity_uid = org2.org_uid

	/* Ordering Provider */
	left join nbs_ods.participation as par3
		on tst.Lab_test_Uid = par3.act_uid
		and par3.type_cd ='ORD'
		and par3.act_class_cd ='OBS'
		and par3.subject_class_cd = 'PSN'
		and par3.record_status_cd ='ACTIVE'
	left join	rdbdata.Person 		as prv
		on	par3.subject_entity_uid = prv.person_uid

	
	/* Conditon, it's just program area */

	/*if we add a program area to the Lab_Report Dimension we probably don't 
	even need a condition dimension.  Even though it's OK with the Dimension Modeling
	principle for adding a prog_area_cd row to the condition, it sure will cause 
	some confusion among users.  There's no "disease" on the input. 
	*/
	left join nbs_ods.observation 	as obs
		on tst.Lab_test_Uid = obs.observation_uid
	left join	rdbdata.Condition	as con
		on	obs.prog_area_cd  = con.program_area_cd
		and con.condition_cd is null
	

	/*LDF_GRP_KEY*/
	left join rdbdata.ldf_group as ldf_g
		on tst.Lab_test_UID = ldf_g.business_object_uid

	/* Morb report */
	left join nbs_ods.act_relationship	as act2
		on tst.Lab_test_Uid = act2.source_act_uid
		and act2.type_cd = 'LabReport'
		and act2.target_class_cd = 'OBS'
		and act2.source_class_cd = 'OBS'
		and act2.record_status_cd = 'ACTIVE'
	left join rdbdata.Morbidity_Report	as mrb
		on act2.target_act_uid = mrb.Morb_rpt_uid

	/* Lab_Rpt_Dt */
	left join rdbdata.datetable 		as dat
		on datepart(tst.lab_rpt_created_dt)*24*60*60 = dat.DATE_MM_DD_YYYY

	where tst.lab_test_type = 'Order'	
;
quit;

data rdbdata.Lab_Report_Event;
set rdbdata.Lab_Report_Event;
if patient_key =. then patient_key =1;
run;
