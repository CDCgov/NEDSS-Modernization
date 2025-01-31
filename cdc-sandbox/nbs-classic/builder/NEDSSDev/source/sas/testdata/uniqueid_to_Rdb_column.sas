/*************************************************************
	Lookup the rdb column name for a given cd value

	Note:
	example: cd=BMD103 --> rdbfield=TRANSFERRED_IND
	This is used only for observation for inv form questions.
	You can also merge with metadata based on unique id to get
	the rdb field name
**************************************************************/
%macro uniqueid_to_Rdb_column(tbl_in, tbl_out);
	data &tbl_out;
	set &tbl_in;
	format rdbfield $50.;
 	rdbfield = put(cd, $col_nm.);
	run;
%mend uniqueid_to_Rdb_column;