%macro update_OrgKeys_From_OrgDim(fact_table, fact_org_key, par_type_cd);
proc sql;
	update &fact_table 
	set &fact_org_key = 
	(select Organization_Key from nbs_rdb.Organization as org, 
		nbs_ods.participation as par
	where  par.subject_entity_uid = org.org_uid
	and &fact_table..public_health_case_uid = par.act_uid
	and par.type_cd=&par_type_cd
	and par.act_class_cd='CASE'
	and par.subject_class_cd='ORG')
;
	update &fact_table 
	set &fact_org_key= 1 where &fact_org_key= .;
quit;
%mend update_OrgKeys_From_OrgDim;