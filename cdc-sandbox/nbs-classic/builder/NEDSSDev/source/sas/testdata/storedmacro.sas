/*Saving compiled macro in prodution env will make program more effient:
* Here's list of candidates for stored compiled macro:
	rptlib
	etllib
	chk_mv.sas 
	footnote.sas 
	ascii.sas
	export.sas
	odsmarkup.sas 
*/

*libname nbslib 'C:\NBS1.1_Devel_Development\development\source\sas\nbslib';
libname nbslib "&SAS_REPORT_HOME/nbslib";
options mstored sasmstore=nbslib;
*options mstored sasmstore=sasuser;
%macro rptlib / store;
%if %upcase(%sysget(SAS_REPORT_DBTYPE))=ORACLE %then
  libname nbs_ods oracle user=nbs_odse password=ods path=nbsdb ACCESS=READONLY;
%else
  libname nbs_ods ODBC DSN=nedss1 UID=nbs_ods PASSWORD=ods ACCESS=READONLY;
%mend rptlib;

%macro etllib / store;
%if %upcase(%sysget(SAS_REPORT_DBTYPE))=ORACLE %then
	%do;
  libname nbs_ods oracle user=nbs_odse password=ods path=nbsdb;
  libname nbs_srt oracle user=nbs_odse password=ods path=nbsdb schema=nbs_srte ACCESS=READONLY;
  	%end;
%else
	%do;
  libname nbs_ods ODBC DSN=nedss1 UID=nbs_ods PASSWORD=ods;
  libname nbs_srt ODBC DSN=nbs_srt UID=nbs_ods PASSWORD=ods ACCESS=READONLY;
  	%end;
%mend etllib;
*%rptlib;
