/*
	Patient data
*/
proc sql;
CREATE  table PHCPatientInfo as	
Select 
	PAR.ACT_UID as public_health_case_uid,  /*join PHC on PAR.ACT_UID = PHC.public_health_case_uid*/
	PER.person_uid,
	PER.person_parent_uid,
	PER.local_id				as person_local_id,
	trim(PNM.last_nm) ||', '||PNM.first_nm	as PatientName,
	PST.state_cd,
    srt1.code_desc_txt 			as state label='State',
	PST.cnty_cd,
	srt3.code_desc_txt 			as county label='County',
	PAR.awareness_cd,
	PAR.awareness_desc_txt,
	PAR.type_cd 				as PAR_type_cd,
	MPR.adults_in_house_nbr,
	PER.age_category_cd,
	input(PER.age_reported,4.0) as age_reported label='age_reported',
	PER.age_reported_time,
	PER.age_reported_unit_cd,
	MPR.birth_gender_cd,
	MPR.birth_order_nbr,
	PER.birth_time,
	PER.birth_time_calc,
	PER.cd 						as Person_cd 		label='Person Code',
	PER.cd_desc_txt 			as Person_code_desc	label='Person Code Desc',
	MPR.children_in_house_nbr,
	PER.curr_sex_cd,
	PER.deceased_ind_cd,
	PER.deceased_time,
	MPR.education_level_cd,
	/*PER.education_level_desc_txt,*/
	CVG.code_short_desc_txt 	as education_level_desc_txt label='education_level_desc_txt',
	PER.ethnic_group_ind,
	srt4.code_short_desc_txt 	as ethnic_group_ind_desc,
	PER.marital_status_cd,
	/*PER.marital_status_desc_txt,*/
	CVG2.code_short_desc_txt	as marital_status_desc_txt label='marital_status_desc_txt',
	MPR.multiple_birth_ind,
	MPR.occupation_cd,
	MPR.prim_lang_cd,
	/*PER.prim_lang_desc_txt,*/
	LNG.code_short_desc_txt		as prim_lang_desc_txt label='prim_lang_desc_txt',
	ELP.from_time 				as ELP_from_time,
	ELP.to_time 				as ELP_to_time,
	ELP.class_cd 				as ELP_class_cd,
	ELP.use_cd 					as ELP_use_cd,
	ELP.as_of_date				as sub_addr_as_of_date,
	PST.postal_locator_uid,
	PST.census_block_cd,
	PST.census_minor_civil_division_cd,
	PST.census_track_cd,
	PST.city_cd,
	PST.city_desc_txt,
	PST.cntry_cd,
	PST.region_district_cd,
	PST.MSA_congress_district_cd,
	PST.zip_cd,
	PST.record_status_time 		as PST_record_status_time,
	PST.record_status_cd 		as PST_record_status_cd,
	PST.street_addr1,
	PST.street_addr2
From
	nbs_ods.Person PER
	/*inner JOIN Participation PAR on PAR.ACT_UID = PHC.public_health_case_uid*/
	inner JOIN  nbs_ods.Participation PAR
	/*Reporting on PIT info since the PAR.subject_entity_uid here is PIT person uid*/ 
			on PER.PERSON_UID = PAR.subject_entity_uid
	LEFT JOIN nbs_ods.Person MPR
			on PER.person_parent_uid = MPR.person_uid
	LEFT OUTER JOIN nbs_ods.entity_locator_participation ELP 
			on ELP.entity_uid = PER.person_uid
			and  ELP.use_cd =&ELP_USE_CD_HOME
			and  ELP.class_cd = &ELP_CLASS_CD_POSTAL
			and  ELP.record_status_cd = &RECORD_STATUS_CD_ACTIVE
	LEFT OUTER JOIN  nbs_ods.postal_locator PST 
			on ELP.locator_uid = PST.postal_locator_uid
			and PST.record_status_cd = &RECORD_STATUS_CD_ACTIVE
	LEFT OUTER JOIN nbs_srt.state_county_code_value srt1 
			on PST.state_cd = srt1.code
	LEFT OUTER JOIN nbs_srt.state_county_code_value srt3 
			on PST.cnty_cd = srt3.code
	LEFT OUTER JOIN nbs_srt.code_value_general srt4 
			on PER.ethnic_group_ind = srt4.code
			and srt4.code_set_nm = &CSN_P_ETHN_GRP
	LEFT OUTER JOIN nbs_ods.Person_name PNM
			on PER.person_uid = PNM.person_uid
			and PNM.nm_use_cd=&PNM_NM_USE_CD_LEGAL
			and PNM.record_status_cd=&RECORD_STATUS_CD_ACTIVE
			/* Jamie 02/05: NBS allows only one legal name*/
	LEFT OUTER JOIN nbs_srt.code_value_general CVG
			on MPR.education_level_cd = CVG.code
			and CVG.code_set_nm=&CSN_P_EDUC_LVL
	LEFT OUTER JOIN nbs_srt.code_value_general CVG2
			on PER.marital_status_cd = CVG2.code
			and CVG2.code_set_nm=&CSN_P_MARITAL
	LEFT OUTER JOIN nbs_srt.language_code LNG
			on MPR.prim_lang_cd = LNG.code
where	PAR.type_cd = &PAR_TYPE_CD_SUBJECT
		
			
order by PAR.ACT_UID
;
quit;
/* make sure one case associated with only one person name, address*/
proc sort data=PHCPatientInfo nodupkeys; by public_health_case_uid; run;
