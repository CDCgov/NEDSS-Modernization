/**********************************************************

	Update Inv_Assigned_Dt_Key

	Note:  Inv_Assigned_Dt_Key is handled differently from
	other date keys because it's stored in participation
	table.  The macro requires the public_health_case_uid
	on the temporary fact table. 

***********************************************************/

%macro update_Inv_Assigned_Dt_Key(fact_table);
proc sql;
	update &fact_table 
	set Inv_Assigned_Dt_Key= 
	(select Date_Key from nbs_rdb.Datetable	as Dt, nbs_ods.participation par
	where 	 par.from_time is not null
	and &fact_table..public_health_case_uid = par.act_uid
	and par.type_cd='InvestgrOfPHC'
	and Dt.Date =datepart(par.from_time)*24*60*60)
	
;

	update &fact_table
	set Inv_Assigned_Dt_Key= 1 where Inv_Assigned_Dt_Key= .;
quit;
%mend update_Inv_Assigned_Dt_Key;