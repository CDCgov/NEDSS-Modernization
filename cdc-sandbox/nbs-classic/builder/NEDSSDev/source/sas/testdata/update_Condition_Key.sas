
/*******************************************************

	Update Condition Key
	On fact table

	Note: Requires the Condition_cd field on the 
	temporary fact table
********************************************************/
%macro update_Condition_Key(fact_table);
data &fact_table;
	modify &fact_table nbs_rdb.Condition(keep=Condition_Key Condition_cd);
	by Condition_cd;
	if _iorc_ = 0 then replace;
	else _error_=0;
	run;
%mend update_Condition_Key;