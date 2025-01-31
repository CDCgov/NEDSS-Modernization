CREATE  VIEW PHC_To_Summary_View as	
Select 
	PHC.public_health_case_uid,
	PHC.diagnosis_time diagnosis_date,
	PHC.cd PHC_code,
	PHC.cd_desc_txt PHC_code_desc,
	srt3.condition_short_nm PHC_code_short_desc,
	srt1.parent_is_cd state_cd,
	srt2.code_desc_txt state,
	srt1.code_desc_txt county,
	PHC.rpt_cnty_cd cnty_cd,
	PHC.case_class_cd,
	PHC.cd_system_cd,
	PHC.cd_system_desc_txt,
	--PHC.confidentiality_cd,
	--PHC.confidentiality_desc_txt,
	--PHC.detection_method_cd,
	--PHC.detection_method_desc_txt,
	--PHC.disease_imported_cd,
	--PHC.disease_imported_desc_txt,
	PHC.group_case_cnt,
	PHC.investigation_status_cd,
	PHC.jurisdiction_cd,
	PHC.mmwr_week,
	PHC.mmwr_year,
	--PHC.outbreak_ind,
	--PHC.outbreak_from_time,
	--PHC.outbreak_to_time,
	--PHC.outbreak_name,
	--PHC.outcome_cd,
	--PHC.pat_age_at_onset,
	--PHC.pat_age_at_onset_unit_cd,
	PHC.prog_area_cd,
	PHC.record_status_cd,
	PHC.rpt_cnty_cd,
	PHC.rpt_form_cmplt_time,
	PHC.rpt_source_cd,
	PHC.rpt_source_cd_desc_txt rpt_source_desc_txt,
	PHC.rpt_to_county_time,
	PHC.rpt_to_state_time,
	PHC.status_cd,
	--PHC.effective_from_time as OnSetDate,
	PHC.add_time as PHC_add_time,
	PHC.case_type_cd
From	public_health_case PHC
	LEFT OUTER JOIN nbs_srtd..state_county_code_value srt1 on
	 PHC.rpt_cnty_cd = srt1.code
      	 LEFT OUTER JOIN nbs_srtd..state_code srt2 on srt1.parent_is_cd = srt2.state_cd
	LEFT OUTER JOIN nbs_srtd..condition_code srt3 on PHC.cd = srt3.condition_cd
where  PHC.case_type_cd = 'S'

