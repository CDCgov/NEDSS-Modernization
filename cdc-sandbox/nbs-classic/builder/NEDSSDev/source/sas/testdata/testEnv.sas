*libname nbs_ods ODBC DSN=nbs_ods UID=nbs_ods PASSWORD=ods;
*filename NBSPGM 'C:\BLDC_Dev_Development\development\source\sas\pgm';
*filename RPTUTIL 'C:\BLDC_Dev_Development\development\source\sas\util';
filename NBSPGM 'C:\NBS1.1_Rel_Development\development\source\sas\pgm';
filename RPTUTIL 'C:\NBS1.1_Rel_Development\development\source\sas\util';

options symbolgen mprint mlogic;
%global ExportType DataSourceName TimePeriod rptdate update footer skip host port;
%*let footer = State: IL GA OH NY | WhereClause: ( State_Cd = IL and PHC_code_short_desc = Rubella )  | Diagnosis Date: 12/1999 - 01/2002 | Diseases: Cholera* Malaria*;
%let footer = %nrstr("State: IL G&A OH N)Y | Diagnosis Date: 12/1999 - 01/2002 | Diseases: Fifth's Disease*");
%put &footer;
%let ExportType=Report;
%let DataSourceName=phcdemographic;
%let TimePeriod=From mm/dd/yyyy To mm/dd/yyyy;

/*
%include 'C:\BLDC_Dev_Development\development\source\sas\testdata\chk_mv.sas';
%include rptutil(
	parse_wcls.sas
	footnote.sas 
	ascii.sas
	);
*/

%include rptutil(reportenv.sas);
%*include 'C:\BLDC_Dev_Development\development\source\sas\testdata\chk_mv.sas';
%include 'C:\NBS1.1_Rel_Development\development\source\sas\testdata\chk_mv.sas';

%rptlib;
*libname nbs_ods oracle user=nbs_odse password=ods path=nbsdb;
*libname nbs_srt oracle user=nbs_odse password=ods path=nbsdb schema=nbs_srte ACCESS=READONLY;
libname nbs_ods ODBC DSN=nedss1 UID=nbs_ods PASSWORD=ods;
libname nbs_srt ODBC DSN=nbs_srt UID=nbs_ods PASSWORD=ods ACCESS=READONLY;

proc sql;
create table phcdemographic as 
select phc_code_short_desc, phc_code, event_date
from nbs_ods.phcdemographic
/*where state_cd='31' ;/*multi diseases*/
/*where state_cd='31' and phc_code='10030'*/;
quit;

/*
proc sql;
create table phc_to_person_view as 
select * 
from nbs_ods.phc_to_person_view
where state_cd='47' and phc_code='10140';
quit;
*/
/*
data phcdemographic;
modify phcdemographic;
	state='TN';
	phc_code_short_desc='Tuberculosis';
run;
*/