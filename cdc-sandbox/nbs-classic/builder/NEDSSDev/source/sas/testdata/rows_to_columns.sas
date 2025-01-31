
/************************************************************
	Turning the records to columns
	
	Note: Normally you use only for observation data where
	the data are stored in records.  Also your input dataset
	rwo_data, must have a column named rdbfield which is the 
	name of the rdb column.  See %Uniqueid_to_Rdb_column
	for more info on how to populate the rdbfield based on 
	the observation's cd for a inv form question.
**************************************************************/
%macro rows_to_columns(row_data, clomn_data, v_name, bylist );
proc sort data=&row_data; by &bylist; run;
proc transpose data=&row_data out=&clomn_data(drop=_name_ );
	id rdbfield;
	by  &bylist;
	var &v_name ;
run;
proc sort data=&clomn_data nodupkey; by &bylist; run;
%mend rows_to_columns;