/*-------------------------------------------------------

	Lab_Test Fact table

	Note: 
		1) Transcriptionist_Key,
		2) Copy_To_Provider_Key,
		3) Result_Interpreter_Key,
		4) Lab_Test_Technician_Key,
		5) Assistant_Interpreter_Key,
		6) Specimen_Last_Change_Time_Key,
		7) Specimen_Add_Time_Key,
		8) Specimen_Collector_Key

		9) lab_test_uid to 	rsn.reason_cd	is one to many, not 1-1

---------------------------------------------------------*/

proc sql;
create table Lab_Test as 
select 	
		obs.observation_uid			as lab_test_uid,
		1							as root_ordered_test_pntr,
		1							as parent_test_pntr,
		obs.observation_uid			as lab_test_pntr,
		obs.status_cd				as lab_test_status,
		/*cvg.code_short_desc_txt		as lab_test_status0,*/
		obs.activity_from_time		as lab_test_dt,
		obs.method_cd				as test_method_cd,
		obs.method_desc_txt			as test_method_desc,
		oin.interpretation_cd		as interpretation_flg,  
	   	oin.interpretation_desc_txt,
		ai.root_extension_txt 		as ACCESSION_NBR, 
		obs.priority_cd				as priority_cd,
		rsn.reason_desc_txt			as reason_for_test_desc,
		rsn.reason_cd				as reason_for_test_cd,

		mat.cd						as specimen_src	label='Specimen',
		mat.nm						as specimen_nm	label='Specimen Name',
		/*put(mat.cd specmn_src $lab165f.)		as s,*/
		mat.description				as Specimen_details,
		mat.qty						as Specimen_collection_vol,
		mat.qty_unit_cd				as Specimen_collection_vol_unit,
		mat.Cd_desc_txt				as Specimen_desc,
		obs.target_site_cd			as specimen_site	label='Specimen Site',
		obs.target_site_desc_txt  	as SPECIMEN_SITE_desc label='Specimen Site Desc', 
		obs.txt						as Clinical_information,
		mat.Risk_cd					as Danger_cd,
		mat.Risk_desc_txt			as Danger_cd_desc,
		obs.obs_domain_cd_st_1 		as Lab_Test_Type,
		obs.cd					 	as Lab_test_cd, 	
		obs.Cd_desc_txt				as Lab_test_cd_desc,
		obs.Cd_system_cd			as Lab_test_cd_sys_cd,

		obs.effective_from_time	 	as date_specimen_collected, 
		obs.cd_desc_txt			 	as ordered_test_name,
		obs.Cd_system_desc_txt		as Lab_test_cd_sys_nm,
		obs.Alt_cd					as Alt_lab_test_cd,
		obs.Alt_cd_desc_txt			as Alt_lab_test_cd_desc,
		obs.Alt_cd_system_cd		as Alt_lab_test_cd_sys_cd,
		obs.Alt_cd_system_desc_txt 	as Alt_lab_test_cd_sys_nm
/*
		1							as Result_Comment_Grp_Key,
		1							as Test_Result_Grp_Key,
		1							as Lab_Rpt_Key,
		1							as Performing_Lab_Key,
		1							as Patient_Key,
		1							as Transcriptionist_Key,
		1							as Copy_To_Provider_Key,
		1							as Result_Interpreter_Key,
		1							as Lab_Test_Technician_Key,
		1							as Assistant_Interpreter_Key,
		1							as Specimen_Collection_Dt_Key,
		1							as Specimen_Last_Change_Time_Key,
		1							as Specimen_Add_Time_Key,
		1							as Specimen_Collector_Key
*/		
		
from 	nbs_ods.observation as obs
	left join 	nbs_ods.act_id  as ai
		on obs.OBSERVATION_UID = ai.ACT_UID
	  	and ai.type_cd='FN'
		and ai.act_id_seq=2

	/*left join 	nbs_srt.code_value_general  as cvg
		on obs.status_cd = cvg.code
	  	and cvg.code_set_nm = 'ELR_LCA_STATUS'*/


	left join nbs_ods.observation_interp	as oin
		on obs.observation_uid = oin.observation_uid
		and oin.INTERPRETATION_CD ~=''

	left join nbs_ods.observation_reason	as rsn
		on obs.observation_uid = rsn.observation_uid

	left join nbs_ods.participation			as par
		on obs.observation_uid = par.act_uid
		and par.type_cd ='SPC'
		and par.subject_class_cd = 'MAT'
		and par.act_class_cd = 'OBS'
	left join nbs_ods.material				as mat
		on par.subject_entity_uid = mat.material_uid

where 	
	  	/*obs.CTRL_CD_DISPLAY_FORM  in ('LabReport', 'LabReportMorb')*/
		obs.obs_domain_cd_st_1 in ('Order','Result','R_Order','R_Result')

order by 1
;
quit;
/*got rid of the multi reason_cd */
proc sort data = Lab_Test nodupkey; by lab_test_uid; run;


/* update parent of R_Result */
proc sql;
create table R_Result_to_R_Order as
select 	act.source_act_uid			as lab_test_uid label='R_Result_uid',
		act.target_act_uid			as parent_test_pntr label='R_Order_uid'
		

from 	Lab_Test as tst,
		/*R_Result_to_R_Order*/
		nbs_ods.act_relationship	as act
where	 tst.lab_test_uid = act.source_act_uid
		/*and act.type_cd = 'COMP'*/
		and act.target_class_cd ='OBS'
		and act.source_class_cd ='OBS'
 		and tst.lab_test_type = 'R_Result'

order by 1
;
quit;

/* update root of R_Result */
proc sql;
create table R_Result_to_R_Order_to_Order as
select 	tst.*,
		act2.target_act_uid				as root_thru_srpt, 	
		act3.target_act_uid				as root_thru_refr,
		coalesce(act2.target_act_uid, 	act4.target_act_uid)
									as root_ordered_test_pntr label='Order uid'

from 	R_Result_to_R_Order	as tst
	
	/*R_Order to Order */
	left join	nbs_ods.act_relationship as act2
		on tst.parent_test_pntr = act2.source_act_uid
		and act2.type_cd = 'SPRT'
		and act2.target_class_cd = 'OBS'
		and act2.source_class_cd ='OBS'

	/*R_Order to Result to Order */
	left join	nbs_ods.act_relationship as act3
		on tst.parent_test_pntr  = act3.source_act_uid
		and act3.type_cd = 'REFR'
		and act3.target_class_cd = 'OBS'
		and act3.source_class_cd ='OBS'
	left join	nbs_ods.act_relationship as act4
		on act3.target_act_uid = act4.source_act_uid
		and act4.type_cd = 'COMP'
		and act4.target_class_cd = 'OBS'
		and act4.source_class_cd ='OBS'

order by 1
;
quit;


data Lab_Test;
merge Lab_Test R_Result_to_R_Order_to_Order
	(keep=lab_test_uid parent_test_pntr root_ordered_test_pntr)
	;
by lab_test_uid;
run;

/* update root order test and parent of R_Order */

proc sql;
create table R_Order_to_Result as
select 	act.source_act_uid			as lab_test_uid label='R_Order_uid',
		act.target_act_uid			as parent_test_pntr label='Result_uid',
		act2.target_act_uid			as root_ordered_test_pntr label='Order uid'
from 	Lab_Test as tst,
		/*R_Order_to_Result */
		nbs_ods.act_relationship	as act,
		/*Result to Order */
		nbs_ods.act_relationship	as act2
where tst.lab_test_type = 'R_Order'
		and tst.lab_test_uid = act.source_act_uid
		and act.type_cd = 'REFR'
		and act.target_class_cd ='OBS'
		and act.source_class_cd ='OBS'
		and act.target_act_uid = act2.source_act_uid
		and act2.type_cd = 'COMP'
		and act2.target_class_cd ='OBS'
		and act2.source_class_cd ='OBS'
order by 1;
;
quit;
data Lab_Test;
merge Lab_Test R_Order_to_Result;
by lab_test_uid;
run;


/* update root and parent of Result */

proc sql;
create table Result_to_Order as
select 	act.source_act_uid			as lab_test_uid label='Result_uid',
		act.target_act_uid			as parent_test_pntr label='Order_uid',
		act.target_act_uid			as root_ordered_test_pntr label='Order uid'
from 	Lab_Test as tst,
		nbs_ods.act_relationship	as act
where	tst.lab_test_uid = act.source_act_uid
		and tst.lab_test_type = 'Result'
		and act.type_cd = 'COMP'
		and act.target_class_cd ='OBS'
		and act.source_class_cd ='OBS'
order by 1

;
quit;

data Lab_Test;
merge Lab_Test Result_to_Order;
by lab_test_uid;
run;

/*	update root and parent of Order, which is itself*/
data Lab_Test;
set Lab_Test;
	if lab_test_type = 'Order' then do;
		parent_test_pntr = lab_test_pntr;
		root_ordered_test_pntr = lab_test_pntr;
	end;
run;

/*-------------------------------------------------------

	Lab_Result_Comment Dimension

	Note: User Comments for Result Test Object (Lab104)

---------------------------------------------------------*/
data Result_And_R_Result;
set Lab_Test;
	if (Lab_Test_Type = 'Result' or Lab_Test_Type = 'R_Result');
run; 

proc sql;
create table Lab_Result_Comment as 
select 
		lab104.lab_test_uid,
		ovt.value_txt			as Lab_Result_Comments
		
from 	
		Result_And_R_Result		as lab104,
		nbs_ods.obs_value_txt	as ovt
where 	ovt.value_txt is not null 
		and ovt.txt_type_cd = 'N'
		and ovt.OBS_VALUE_TXT_SEQ =2
		and ovt.observation_uid =  lab104.lab_test_uid
order by 1
;
quit;
proc sort data = Lab_Result_Comment nodupkey; by Lab_test_uid; run;
%assign_key(Lab_Result_Comment, Lab_Result_Comment_Key);

/*-------------------------------------------------------

	Lab_Result_Comment Dimension
	Result_Comment_Group

---------------------------------------------------------*/

data Lab_Result_Comment Result_Comment_Group (keep=Result_Comment_Grp_Key);
set  Lab_Result_Comment;
 	Result_Comment_Grp_Key = Lab_Result_Comment_Key;
	output Lab_Result_Comment Result_Comment_Group;
run;





/*-------------------------------------------------------

	Lab_Result_Val Dimension
	Test_Result_Grouping Dimension

---------------------------------------------------------*/
proc sql;
create table Lab_Result_Val as
select 
		rslt.lab_test_uid,
		otxt.value_txt				as Lab_Result_Txt_Val,
		trim(onum.COMPARATOR_CD_1)|| trim(put(NUMERIC_VALUE_1, 8.)) ||trim(NUMERIC_UNIT_CD)
									as Numeric_Result	length=25,
		onum.LOW_RANGE				as Ref_Range_Frm,
		onum.HIGH_RANGE				as Ref_Range_To,
		code.code					as Test_result_val_cd,
		code.display_name			as Test_result_val_cd_desc,
		code.CODE_SYSTEM_CD			as Test_result_val_cd_sys_cd,
		code.CODE_SYSTEM_DESC_TXT	as Test_result_val_cd_sys_nm,
		code.ALT_CD					as Alt_result_val_cd,
		code.ALT_CD_DESC_TXT		as Alt_result_val_cd_desc,
		code.ALT_CD_SYSTEM_CD		as Alt_result_val_cd_sys_cd,
		code.ALT_CD_SYSTEM_DESC_TXT	as Alt_result_val_cd_system_nm

from	Result_And_R_Result			as rslt,
		nbs_ods.obs_value_txt		as otxt,
		nbs_ods.obs_value_numeric	as onum,
		nbs_ods.obs_value_coded		as code
where	rslt.lab_test_uid = otxt.observation_uid
	and otxt.OBS_VALUE_TXT_SEQ =1
	and rslt.lab_test_uid = onum.observation_uid
	and rslt.lab_test_uid = code.observation_uid
order by 1
;
quit;

data Lab_Result_Val	Test_Result_Grouping(keep = Test_Result_Grp_Key) ;
	retain Test_Result_Val_Key Test_Result_Grp_Key 1;
	if Test_Result_Val_Key = 1 then do;
		output Lab_Result_Val Test_Result_Grouping ;
	end; 
	
	set Lab_Result_Val; by  lab_test_uid;

	if first.lab_test_uid then do;
		Test_Result_Grp_Key + 1;
		output Test_Result_Grouping;
	end; 

	Test_Result_Val_Key +1;
	output Lab_Result_Val;
run;

/* Update Lab_Test Keys */

/* Result_Comment_Grp_Key */
proc sql;
create table Lab_Test as 
select 	tst.*,
		coalesce(lrc.Result_Comment_Grp_key,1) as Result_Comment_Grp_key
from 	Lab_Test		as tst  
	left join	Lab_Result_Comment as lrc
		on tst.Lab_test_uid = lrc.Lab_test_uid
		and lrc.Result_Comment_Grp_key ~=1
order by tst.Lab_test_uid
;
quit;

proc sort data = Lab_Result_Comment nodupkey; by Lab_test_uid; run;

data Lab_Test;
merge 	Lab_Test (in=a)
		Lab_Result_Comment (in=b keep=Lab_test_uid Result_Comment_Grp_key);
	by Lab_test_uid ;
	if not a then delete;
	if a and not b then Result_Comment_Grp_key = 1;
run;



/* Test_Result_Grp_Key */
proc sql;
create table Lab_Test as 
select 	tst.*,
		coalesce(lrv.Test_Result_Grp_Key,1) as Test_Result_Grp_Key
from 	
		Lab_Test		as tst  
	left join	Lab_Result_Val as lrv
		on tst.Lab_test_uid = lrv.Lab_test_uid
		and lrv.Test_Result_Grp_Key ~=1
order by tst.Lab_test_uid
;
quit;

/* Lab_Rpt_Key */

proc sort data = Lab_Test; by Root_Ordered_Test_Pntr; run;
proc sql;
create table Lab_Test as 
select	tst.*,
		coalesce (rpt.Lab_Rpt_Key,1)	as Lab_Rpt_Key

from Lab_Test 			as tst
	 left join rdbdata.Lab_Report as rpt
		on rpt.Lab_Rpt_uid = tst.Root_Ordered_Test_Pntr
		and rpt.Lab_Rpt_Key ~=1
order by tst.Root_Ordered_Test_Pntr
;
quit;

/* Patient Key */
/* bad data seen for a order test without patient, will reseach later*/
proc sql;
create table Lab_Test as 
select 	tst.*,
		coalesce(psn.person_key,1) as patient_key
from 	Lab_Test as tst
	left join nbs_ods.participation as par
		on tst.Root_Ordered_Test_Pntr = par.act_uid
		and par.type_cd ='PATSBJ'
		and par.act_class_cd ='OBS'
		and par.subject_class_cd = 'PSN'
		and par.record_status_cd ='ACTIVE'
	left join rdbdata.Person as psn
		on par.subject_entity_uid = psn.person_uid
		and psn.person_key ~=1
order by tst.lab_test_uid
;
quit;


/* Performing Lab */
proc sql;
create table Lab_Test as 
select 	tst.*,
		coalesce(org.Org_key,1) as Performing_lab_key

from 	Lab_Test as tst
	left join nbs_ods.participation as par
		on tst.Root_Ordered_Test_Pntr = par.act_uid
		and par.type_cd ='PRF'
		and par.act_class_cd ='OBS'
		and par.subject_class_cd = 'ORG'
		and par.record_status_cd ='ACTIVE'
	left join rdbdata.Organization  as org
		on par.subject_entity_uid = org.Org_uid
		and org.Org_key ~=1
	;
quit;

/* Specimen collection date */
proc sql;
create table Lab_Test as 
select 	tst.*,
		coalesce(dt.Date_key,1) as Sepcimen_collection_dt_key

from 	Lab_Test as tst
	left join nbs_rdb.Datetable as dt
		on tst.date_specimen_collected = dt.date
		and dt.Date_key ~=1
	;
quit;

data rdbdata.Lab_Test;
set Lab_Test;
run;