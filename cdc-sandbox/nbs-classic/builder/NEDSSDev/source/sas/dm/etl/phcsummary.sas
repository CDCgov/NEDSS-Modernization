/*Summary Data: need to updates the county, state and group_case count info*/

proc sql;
	create table summaryuid as
	select 
		PHC.public_health_case_uid,
		srt1.parent_is_cd 			as state_cd	label='State_cd',
		srt2.code_desc_txt 			as state	label='State',
		PHC.rpt_cnty_cd 			as cnty_cd,
		srt1.code_desc_txt			as county	label='County'
	From
		nbs_ods.public_health_case PHC
		LEFT OUTER JOIN nbs_srt.state_county_code_value srt1 
			on 	PHC.rpt_cnty_cd = srt1.code
      	LEFT OUTER JOIN nbs_srt.state_code srt2 
			on srt1.parent_is_cd = srt2.state_cd
	where 	
		case_type_cd is not null 
		and case_type_cd = &PHC_CASE_TYPE_CD_SUMMARY			
	order by public_health_case_uid
;

	create table casecnt as
	select 	
		ar2.target_act_uid 	as public_health_case_uid,
		ar2.source_act_uid 	as sum_report_form_uid,
		ar1.target_act_uid	as ar1_sum_report_form_uid, /*--Summary_Report_Form Obs_uid*/ 
		ar1.source_act_uid	as ar1_sum107_uid, /*--SUM107 Obs_uid*/
		ob1.observation_uid as ob1_sum107_uid,
		ob1.cd 				as ob1_cd,
		ob2.observation_uid as ob2_sum_report_form_uid,
		ob2.cd 				as ob2_cd,
		ovn.numeric_value_1 as group_case_cnt,
		ar1.type_cd			as ar1_type_cd,
		ar1.source_class_cd	as ar1_source_class_cd,
		ar1.target_class_cd	as ar1_target_class_cd,
		ar2.type_cd			as ar2_type_cd,
		ar2.source_class_cd	as ar2_source_class_cd,
		ar2.target_class_cd	as ar2_target_class_cd
		
	from 	
		nbs_ods.act_relationship as ar1, 	/*-- OBS sum107 to OBS Summary_Report_Form */
		nbs_ods.observation as ob1, 		/*--SUM107 */
		nbs_ods.observation as ob2, 		/* --Summary_Report_Form*/
		nbs_ods.obs_value_numeric as ovn, 	/*--group_case_cnt*/
		nbs_ods.act_relationship as ar2 	/*OBS Summary_Report_Form to phc */ 
	where 	ar1.source_act_uid = ob1.observation_uid
		and ar1.target_act_uid = ob2.observation_uid
		and ar1.type_cd=&AR_TYPE_CD_SUMMARY_FORMQ
		and ob1.cd = &OBS_CD_SUMMARY_ROOT
		and ob2.cd = &OBS_CD_SUMMARY_REPORT_FORM
		and ob1.observation_uid = ovn.observation_uid
		and ob2.observation_uid = ar2.source_act_uid
		and ar2.type_cd = &AR_TYPE_CD_SUMMARY_FORM
		order by public_health_case_uid;

quit;

data phcsummary;
	merge 	summaryuid (in=A ) 
			casecnt (in=B keep=public_health_case_uid group_case_cnt) END=eof;
	by public_health_case_uid;

	retain matchCount 0;
	if A and B then matchCount+1;
	if eof then do;
		put 'Number of Summary records updated with Summary Group Case Count:' matchCount comma8.;
	end;
	drop matchCount;
run;
