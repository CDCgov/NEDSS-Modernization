/*******************************************************

	Update Condition Key
	On fact table

	Note: Requies the public_health_case_uid field on 
	the temporary fact table
********************************************************/
%macro update_Condition_Key2(fact_table);
proc sql;
	update &fact_table 
	set Condition_Key= 
	(select Condition_Key from rdbdata.Condition as con, rdbdata.Phcrootobs as phc
	where 	 &fact_table..public_health_case_uid = phc.public_health_case_uid
	and con.condition_cd =phc.condition_cd)
;

	update &fact_table
	set Condition_Key= 1 where Condition_Key= .;
quit;
%mend update_Condition_Key2;