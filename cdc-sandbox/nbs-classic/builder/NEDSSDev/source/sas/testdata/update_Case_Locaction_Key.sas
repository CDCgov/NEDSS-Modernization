/**********************************************************

	Update Case Location

	Note:  Case location is defined as the PIT Patient 
	address. Since PIT has unique address, it's safe to 
	do a nodupkey sort on Person Location Dimension without
	lossing the pit address


***********************************************************/
%macro update_Case_Locaction_Key(fact_table);
proc sort data=nbs_rdb.Person_location 
	out=Person_location (keep=Person_Key Location_Key) nodupkey;
	by  Person_Key;
run;

data &fact_table;
modify &fact_table Person_location (rename=(Person_key=Patient_key Location_key=Case_location));
	by Patient_key;
	if _iorc_ =0 then replace;
	else _error_=0;
run;
%mend update_Case_Locaction_Key;