/*                                                      */
/* This program is first module ETL job calls.          */
/* Put stuffs here that are applicable to etl programs. */ 
/*                                                      */

%etllib;
filename etlpgm "&SAS_REPORT_HOME\dw\etl\src";
libname rdbdata "&SAS_REPORT_HOME\dw\etl\rdbdata";
libname nbsfmt "&SAS_REPORT_HOME\format";
options fmtsearch=(nbsfmt);

%include etlpgm(etlmacro.sas);