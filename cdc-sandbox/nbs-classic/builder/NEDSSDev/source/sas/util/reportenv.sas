/*                                                      */
/* This program is first module sas ejb calls.          */
/* Put stuffs here that are applicable to all reports.  */ 
/*                                                      */

/*
libname nbslib "&SAS_REPORT_HOME/nbslib";
options mstored sasmstore=nbslib;
filename NBSPGM  "&SAS_REPORT_HOME/pgm";
*/
%rptlib;
%global ExportType DataSourceName TimePeriod rptdate update footer skip host port;
%include rptutil(	
	chk_mv.sas 
	footnote.sas 
	ascii.sas
	export.sas
	odsmarkup.sas
	Rpt_formats.sas
	);


/*date time format for nbs report*/
proc format;
picture nbsrptdt low-high='%0m/%0d/%Y %0H:%0M:%0S' (datatype=datetime);
run;
