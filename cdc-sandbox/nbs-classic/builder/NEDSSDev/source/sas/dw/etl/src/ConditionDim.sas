/*****************************************************

	Condition Dimension

******************************************************/
 PROC SQL;
CREATE TABLE Condition AS SELECT condition_cd, 	
		condition_desc_txt ,
		condition_short_nm,
		effective_from_time, 
		effective_to_time, 
		nnd_ind,
		prog_area_cd,
		code_system_cd,
		code_system_desc_txt,
		assigning_authority_cd,
		assigning_authority_desc_txt,
		investigation_form_cd FROM NBS_SRT.CONDITION_CODE 
		ORDER BY effective_from_time;

 QUIT;

 
data rdbdata.Condition;

format disease_grp_cd $50. disease_grp_desc $50.;
SET Condition;
/*set nbs_srt.Condition_code	(SORTEDBY=STATUS_TIME DESCENDING keep=
		condition_cd 	
		condition_desc_txt 
		condition_short_nm
		effective_from_time 
		effective_to_time 
		nnd_ind
		prog_area_cd
		code_system_cd
		code_system_desc_txt
		assigning_authority_cd
		assigning_authority_desc_txt
		investigation_form_cd
		STATUS_TIME
		);*/

	program_area_desc = put(prog_area_cd, $PAMCODE.);
	select (substr(investigation_form_cd, 1,50));
		when ('INV_FORM_BMD') Disease_grp_cd='Bmird_Case';
		when ('INV_FORM_CRS') Disease_grp_cd='CRS_Case';
		when ('INV_FORM_GEN') Disease_grp_cd='Generic_Case';
		when ('INV_FORM_VAR') Disease_grp_cd='Generic_Case';
		when ('INV_FORM_RVC') Disease_grp_cd='Generic_Case';
		when ('INV_FORM_HEP') Disease_grp_cd='Hepatitis_Case';
		when ('INV_FORM_MEA') Disease_grp_cd='Measles_Case';
		when ('INV_FORM_PER') Disease_grp_cd='Pertussis_Case';
		when ('INV_FORM_RUB') Disease_grp_cd='Rubella_Case';
		otherwise Disease_grp_cd=investigation_form_cd;
	end;
	disease_grp_desc=disease_grp_cd; 

	rename    
		condition_desc_txt	= condition_desc
		effective_from_time = condition_cd_eff_dt
		effective_to_time	= condition_cd_end_dt
		prog_area_cd		= program_area_cd
		code_system_cd		= condition_cd_sys_cd
		code_system_desc_txt= condition_cd_sys_cd_nm
		assigning_authority_desc_txt = assigning_authority_desc
		;
	drop investigation_form_cd;
run;
/* Lab Report has program area, but no condition */
proc sql;
create table pamonly as 
select distinct program_area_cd,program_area_desc
from rdbdata.Condition
;
quit; 
proc append data=pamonly base=rdbdata.Condition force; run;
%assign_key (rdbdata.Condition, Condition_Key);
%dbload (Condition, rdbdata.Condition);
PROC datasets library = work nolist;
delete pamonly;
run;
quit;
