
/************************************************************

	Note: Most datetime fields used as a dimension are 
	in the Public_Health_Case tables.  If a datetime is
	not in the PHC, this macro can not be used.
*************************************************************/
%macro update_DateKeys_From_Datetable(fact_table, fact_key, phc_field);
proc sql;
	update &fact_table 
	set &fact_key= 
	(select Date_Key from nbs_rdb.Datetable	as Dt, nbs_ods.public_health_case as phc
	where 	 phc.&phc_field is not null
	and &fact_table..public_health_case_uid = phc.public_health_case_uid
	and Dt.Date =datepart(phc.&phc_field)*24*60*60)
;

	update bmdfact
	set &fact_key= 1 where &fact_key= .;
quit;
%mend update_DateKeys_From_Datetable;