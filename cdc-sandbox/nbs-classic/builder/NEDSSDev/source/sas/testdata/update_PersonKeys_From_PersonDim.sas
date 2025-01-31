%macro update_PersonKeys_From_PersonDim(fact_table, fact_psn_key, par_type_cd);
proc sql;
	update &fact_table 
	set &fact_psn_key = 
	(select Person_Key from nbs_rdb.Person	as per, nbs_ods.participation as par
	where  par.subject_entity_uid = person.person_uid
	and &fact_table..public_health_case_uid = par.act_uid
	and par.type_cd=&par_type_cd
	and par.act_class_cd='CASE'
	and par.subject_class_cd='PSN')
;
	update &fact_table 
	set &fact_psn_key= 1 where &fact_psn_key= .;
quit;
%mend update_PersonKeys_From_PersonDim;