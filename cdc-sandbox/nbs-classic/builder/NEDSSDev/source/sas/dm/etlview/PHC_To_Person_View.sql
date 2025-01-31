CREATE  VIEW PHC_To_Person_View as	
Select 
	PHC.public_health_case_uid,
	PHC.case_type_cd,
	PER.person_uid,
	PHC.diagnosis_time diagnosis_date,
	PHC.cd PHC_code,
	srt2.condition_short_nm PHC_code_short_desc,
	PHC.cd_desc_txt PHC_code_desc,
	PST.state_cd,
        srt1.code_desc_txt state,
	PST.cnty_cd,
	srt3.code_desc_txt county,
	PHC.case_class_cd,
	PHC.cd_system_cd,
	PHC.cd_system_desc_txt,
	PHC.confidentiality_cd,
	PHC.confidentiality_desc_txt,
	PHC.detection_method_cd,
	PHC.detection_method_desc_txt,
	PHC.disease_imported_cd,
	PHC.disease_imported_desc_txt,
	PHC.group_case_cnt,
	PHC.investigation_status_cd,
	PHC.jurisdiction_cd,
	PHC.mmwr_week,
	PHC.mmwr_year,
	PHC.outbreak_ind,
	PHC.outbreak_from_time,
	PHC.outbreak_to_time,
	PHC.outbreak_name,
	PHC.outcome_cd,
	PHC.pat_age_at_onset,
	PHC.pat_age_at_onset_unit_cd,
	PHC.prog_area_cd,
	PHC.record_status_cd,
	PHC.rpt_cnty_cd,
	PHC.rpt_form_cmplt_time,
	PHC.rpt_source_cd,
	PHC.rpt_source_cd_desc_txt rpt_source_desc_txt,
	PHC.rpt_to_county_time,
	PHC.rpt_to_state_time,
	PHC.status_cd,
	PAR.awareness_cd,
	PAR.awareness_desc_txt,
	PER.adults_in_house_nbr,
	PER.age_category_cd,
	PER.age_reported,
	PER.age_reported_time,
	PER.age_reported_unit_cd,
	PER.birth_gender_cd,
	PER.birth_order_nbr,
	PER.birth_time,
	PER.birth_time_calc,
	PER.cd,
	PER.cd_desc_txt Person_code_desc,
	PER.children_in_house_nbr,
	PER.curr_sex_cd,
	PER.deceased_ind_cd,
	PER.deceased_time,
	PER.education_level_cd,
	PER.education_level_desc_txt,
	PER.ethnic_group_ind,
	srt4.code_short_desc_txt ethnic_group_ind_desc,
	PER.marital_status_cd,
	PER.marital_status_desc_txt,
	PER.multiple_birth_ind,
	PER.occupation_cd,
	PER.prim_lang_cd,
	PER.prim_lang_desc_txt,
	ELP.from_time ELP_from_time,
	ELP.to_time ELP_to_time,
	ELP.class_cd,
	ELP.use_cd,
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
	PST.record_status_time PST_record_status_time,
	PST.record_status_cd PST_record_status_cd,
	PHC.effective_from_time as onSetDate,
	PAR.type_cd as PAR_type_cd,
	PST.street_addr1,
	PST.street_addr2,
	PHC.add_time as PHC_add_time
From
	public_health_case PHC
	inner JOIN Participation PAR on PAR.ACT_UID = PHC.public_health_case_uid
			and UPPER(PAR.type_cd) = 'SUBJOFPHC'
			and (PHC.case_type_cd != ( 'S') or PHC.case_type_cd is null )
	INNER JOIN  Person	PER on PER.PERSON_UID = PAR.subject_entity_uid
	LEFT OUTER JOIN entity_locator_participation ELP on ELP.entity_uid = PER.person_uid
				and  ELP.cd ='C' 
				and  ELP.use_cd ='H' 
				and  ELP.class_cd = 'PST'
	 LEFT OUTER JOIN  postal_locator PST on ELP.locator_uid = PST.postal_locator_uid
	 LEFT OUTER JOIN nbs_srtd..state_county_code_value srt1 on PST.state_cd = srt1.code
	 LEFT OUTER JOIN nbs_srtd..condition_code srt2 on PHC.cd = srt2.condition_cd
              LEFT OUTER JOIN nbs_srtd..state_county_code_value srt3 on PST.cnty_cd = srt3.code
              LEFT OUTER JOIN nbs_srtd..code_value_general srt4 on PER.ethnic_group_ind = srt4.code
			and srt4.code_set_nm = 'P_ETHN_GRP'
where  case_type_cd ='I' 



















