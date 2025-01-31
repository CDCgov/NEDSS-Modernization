/*                                                      */
/* This program is first module ETL job calls.          */
/* Put stuffs here that are applicable to etl programs. */ 
/*                                                      */

/*
libname nbslib "&SAS_REPORT_HOME/nbslib";
options mstored sasmstore=nbslib;
filename NBSPGM  "&SAS_REPORT_HOME/pgm";
*/
%etllib;
*libname nbsfmt "&SAS_REPORT_HOME\format";
libname nbsfmt ('C:\r1.1.1\format','C:\r1.1.1\format2' );
options fmtsearch=(nbsfmt);

/*utils used in etl*/
%include rptutil(	
	assign_key.sas
	);

