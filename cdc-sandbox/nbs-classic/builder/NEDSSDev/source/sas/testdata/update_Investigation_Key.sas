/**********************************************************

	Update Investigation Key

	Note:  

***********************************************************/

%macro update_Investigation_Key(fact_table);
data &fact_table;
modify &fact_table 
	nbs_rdb.Investigation(keep=case_uid Investigation_key
				rename=(case_uid=public_health_case_uid)
					);
	by public_health_case_uid;
	if _iorc_ = 0 then replace;
	else _error_ = 0;
run;
%mend update_Investigation_Key;