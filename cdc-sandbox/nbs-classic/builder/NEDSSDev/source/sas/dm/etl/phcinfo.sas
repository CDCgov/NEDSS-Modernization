/*PHC Data	*/
data COND_CODE;
set nbs_srt.Condition_code(keep=condition_cd condition_short_nm);
format fmtname $12.;
	fmtname = '$COND_CODE';
	rename condition_cd=start
		condition_short_nm=label;
run;
proc format lib=nbsfmt cntlin=COND_CODE; run;

%MACRO PROCESS_FORMAT(code_set_nm, csn, fmtName);
data &code_set_nm;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
format fmtname $12.;
	where code_set_nm = &csn;
	fmtname = &fmtName; 
	rename code=start
		code_short_desc_txt=label;
run;
 
proc format lib=nbsfmt cntlin=&code_set_nm; run;
%MEND PROCESS_FORMAT;
data CNTYCCD;
set nbs_srt.state_county_code_value(keep=code code_short_desc_txt);
format fmtname $12.;
	fmtname = '$COUNTY_CCD';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=CNTYCCD; run;
data JURD_CD;
set nbs_srt.jurisdiction_code(keep=code_set_nm code code_short_desc_txt);
format fmtname $12.;
	where code_set_nm = 'S_JURDIC_C';
	fmtname = '$JURD_CD';
	rename code=start
		code_short_desc_txt=label;
run; 
proc format lib=nbsfmt cntlin=JURD_CD; run;
data STATE_CCD;
set nbs_srt.V_state_code(keep=code_set_nm code code_desc_txt);
format fmtname $12.;
	where code_set_nm = 'STATE_CCD';
	fmtname = '$STATE_CCD';
	rename code=start
		code_desc_txt=label;
run; 
proc format lib=nbsfmt cntlin=STATE_CCD; run;

%PROCESS_FORMAT(YNU, 'YNU', '$YNU');
%PROCESS_FORMAT(PSL_CNTRY, 'PSL_CNTRY', '$PSL_CNTRY');
%PROCESS_FORMAT(PHC_RPT_SRC_T, 'PHC_RPT_SRC_T', '$PHC_RPT_SRC');
%PROCESS_FORMAT(STATE_CCD, 'STATE_CCD', '$STATE_CCD');
%PROCESS_FORMAT(PHC_IMPRT, 'PHC_IMPRT','$PHC_IMPRT');
%PROCESS_FORMAT(PHC_DET_MT, 'PHC_DET_MT', '$PHC_DET_MT');
options fmtsearch=(nbsfmt);
proc sql;

create table phcinfo1 as 
Select 
	PHC.public_health_case_uid,
	
	PHC.case_type_cd,
	PHC.diagnosis_time 		as diagnosis_date,
	PHC.cd  				as PHC_code			label='PHC_code',
	PHC.cd_desc_txt 		as PHC_code_desc	label='PHC_code_desc',
	PHC.case_class_cd,
	PHC.cd_system_cd,
	PHC.cd_system_desc_txt,
	PHC.confidentiality_cd,
	PHC.confidentiality_desc_txt,
	PHC.detection_method_cd,
	/*PHC.detection_method_desc_txt,*/
	PHC.disease_imported_cd,
	/*PHC.disease_imported_desc_txt,*/
	PHC.group_case_cnt,
	PHC.investigation_status_cd,
	PHC.jurisdiction_cd,
	input(PHC.mmwr_week,4.0) as mmwr_week label='mmwr_week',
	input(PHC.mmwr_year,4.0) as mmwr_year label='mmwr_year',
	PHC.outbreak_ind,
	PHC.outbreak_from_time,
	PHC.outbreak_to_time,
	PHC.outbreak_name,
	PHC.outcome_cd,
	input(PHC.pat_age_at_onset,4.0) as pat_age_at_onset label='pat_age_at_onset',
	PHC.pat_age_at_onset_unit_cd,
	PHC.prog_area_cd,
	PHC.record_status_cd,
	PHC.rpt_cnty_cd,
	PHC.rpt_form_cmplt_time,
	PHC.rpt_source_cd,
	/*PHC.rpt_source_cd_desc_txt*/ 	
	PHC.rpt_to_county_time,
	PHC.rpt_to_state_time,
	PHC.status_cd,
	PHC.effective_from_time 	as onSetDate,
	PHC.activity_from_time		as investigationStartDate 	label='investigationStartDate',
	PHC.add_time 				as PHC_add_time	label='PHC_add_time',
	PHC.program_jurisdiction_oid,
	PHC.shared_ind,
	
	phc.imported_country_cd as imported_country_code 'imported_country_code',
	phc.imported_state_cd as imported_state_code 'imported_state_code',
	phc.imported_county_cd as imported_county_code 'imported_county_code',
	phc.investigator_assigned_time as INVESTIGATOR_ASSIGN_DATE 'INVESTIGATOR_ASSIGN_DATE',
	phc.hospitalized_admin_time as hospitalized_admin_date 'HOSPITALIZED_ADMIN_DATE',
	phc.hospitalized_discharge_time as HOSPITALIZED_DISCHARGE_DATE 'HOSPITALIZED_DISCHARGE_DATE',
	phc.hospitalized_duration_amt,
	phc.imported_city_desc_txt,
	phc.deceased_time as INVESTIGATION_DEATH_DATE 'INVESTIGATION_DEATH_DATE',
	FOOD_HANDLER_IND_CD,hospitalized_ind_cd, day_care_ind_cd, pregnant_ind_cd

	/*srt2.condition_short_nm as PHC_code_short_desc,
	JUR.code_short_desc_txt 	as Jurisdiction					label='Jurisdiction',
	CVG3.code_short_desc_txt	as detection_method_desc_txt 	label='detection_method_desc_txt',
	CVG2.code_short_desc_txt 	as disease_imported_desc_txt 	label='disease_imported_desc_txt',
	CVG4.code_short_desc_txt 	as rpt_source_desc_txt 		label='rpt_source_desc_txt',
	CVG5.code_short_desc_txt 	as HOSPITALIZED		label='HOSPITALIZED',
	CVG6.code_short_desc_txt 	as PREGNANT		label='PREGNANT',
	CVG7.code_short_desc_txt 	as DAY_CARE_IND_CD		label='DAY_CARE_IND_CD',
	CVG8.code_short_desc_txt 	as FOOD_HANDLER_IND_CD		label='FOOD_HANDLER_IND_CD',
	CVG9.code_short_desc_txt 	as IMPORTED_COUNTRY_CD		label='IMPORTED_COUNTRY_CD',
	CVG10.code_short_desc_txt 	as IMPORTED_COUNTY_CD		label='IMPORTED_COUNTY_CD',
	CVG11.code_desc_txt 	as IMPORTED_STATE_CD		label='IMPORTED_STATE_CD'
	*/

From
	nbs_ods.public_health_case PHC;
quit;
PROC SQL;
create table phcinfo2 as 
Select a.*, NBS_Case_Answer.answer_txt as THERAPY_DATE 'THERAPY_DATE' 
	from phcinfo1 a
	LEFT OUTER JOIN nbs_ods.NBS_Case_Answer
		on A.public_health_case_uid= NBS_Case_Answer.act_uid
		and NBS_Case_Answer.nbs_question_uid in (select nbs_question_uid from nbs_ods.nbs_question 
												where question_identifier='TUB170');
QUIT;
PROC SQL;
PROC SQL;
create table phcinfo as 
Select a.*, AID.root_extension_txt	as state_case_id
from phcinfo2 a
LEFT OUTER JOIN nbs_ods.act_id	aid
		on A.public_health_case_uid = aid.act_uid
		and act_id_seq =1
  WHERE A.record_status_cd <> 'LOG_DEL'
	order by A.public_health_case_uid;
quit;
DATA phcinfo;
SET phcinfo;
PHC_code_short_desc=PUT(PHC_code,$COND_CODE.);/*srt2*/
Jurisdiction=PUT(jurisdiction_cd,$JURD_CD.);/*JUR*/
/*????=PUT(rpt_source_cd,$PHC_IMPRT.);NOT USED, was redundant in old code--CVG*/
disease_imported_desc_txt=PUT(disease_imported_cd,$PHC_IMPRT.);/*--CVG2*/
detection_method_desc_txt=PUT(detection_method_cd,$PHC_DET_MT.);/*--CVG3*/
rpt_source_desc_txt=PUT(rpt_source_cd,$PHC_RPT_SRC.);  /*--CVG4*/
HOSPITALIZED=PUT(hospitalized_ind_cd,$YNU.);  /*--CVG5*/
PREGNANT=PUT(pregnant_ind_cd,$YNU.);/*--CVG6*/
DAY_CARE_IND_CD=PUT(day_care_ind_cd,$YNU.);/*--CVG7*/
FOOD_HANDLER_IND_CD=PUT(FOOD_HANDLER_IND_CD,$YNU.);/*--CVG8*/
IMPORTED_COUNTRY_CD=PUT(imported_country_code,$PSL_CNTRY.);/*--CVG9*/
IMPORTED_COUNTY_CD=PUT(imported_county_code,$COUNTY_CCD.);/*--CVG10*/
IMPORTED_STATE_CD=PUT(imported_state_code,$STATE_CCD.);/*--CVG11*/
RUN;
DATA phcinfo;
set phcinfo;
THERAPY_DATE_FIELD= INPUT(THERAPY_DATE, ANYDTDTM32.);
run;
