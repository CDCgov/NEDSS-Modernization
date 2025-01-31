**********************************************************************************;
*** Macro: NBSSR00012.SAS                                                      ****;
*** Input: Macro Variables (ExportType DataSourceName TimePeriod Dispaly wclause)**;
***********************************************************************************;
%macro nbssr00012;

%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;

data sr00012;
	set work.PHCDemographic;
	if state_cd='' then state_cd='N/A';
	if state='' then state='N/A';
	if county='' then county='N/A';
	if cnty_cd='' then cnty_cd='N/A';
run;

Proc SQL;
Create table sr12 
as select 
state_cd,
State,
County,
PHC_code_short_desc,
year(datepart(event_date)) 	as year	label='Year',
sum(group_case_cnt)	 as Cases label='Cases' 
from sr00012
by group state_cd,state,county,PHC_code_short_desc,year;

quit;

Data _null_;
set sr12(obs=1);
call symput('stname',trim(fipnamel(input(state_cd,2.))));
call symput('disease',trim(PHC_code_short_desc));
run;

Title;
Title1 BOLD HEIGHT=5 FONT=Times <p align="center"> "SR12: Cases of Selected Disease By County By Year"</p> ;
Title2 HEIGHT=4 FONT=Times <p align="center"> "&stname &timeperiod"</p>;
Title3 HEIGHT=4 FONT=Times <p align="center"> "&disease"</p>;
Title4  <div style='background-color:#E6E6E6;'>
	<table><tr>
	<td style='font-size:11pt;font-family:Times New Roman;'><b><U>Report content</U></b></td>
	</tr>
	<tr><td style='font-size:11pt;font-family:Times New Roman;'><b>Data Source: </b> nbs_ods.PHCDemographic (publichealthcasefact)</td></tr>
	<tr>
	<td style='font-size:11pt;font-family:Times New Roman;'><b>Output:</b> Report demonstrates, in table form, the total number of Investigation(s) [both Individual and Summary] by Year (calculated based on Event Date year) and by County.  Output:
	<LI>Does not include Investigation(s) that have been logically deleted</LI>
	<LI>Is filtered based on the state, county(s), disease(s), time frame and advanced criteria selected by user</LI>
	<LI>Will not include Investigation(s) that do not have a value for the State selected by the user</LI>
	<LI>Will not include Investigation(s) that do not have a value for the County selected by the user</LI>
	<LI>Is based on the year of the calculated Event Date (not the MMWR Year defined by the user)</LI>
	</td></tr>
	<tr>
	<td style='font-size:11pt;font-family:Times New Roman;'><b>Calculations:</b></td></tr>
	<tr><td style='font-size:11pt;font-family:Times New Roman;'>
	<LI><b>Event Date:  </b> Derived using the hierarchy of Onset Date, Diagnosis Date, Report to County, Report to State and Date the Investigation was created in the NBS.</LI>
	</td></tr></table>
	<div>;

%footnote;

%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_top_matter no_bottom_matter);
Proc Tabulate data=sr12
Format=10.;
Class County Year;
var cases;
Keyword all sum;
Keylabel all="Total";
Table (County='County' all),(Year='Year'*cases=' '*sum=' ' all*cases=' '*sum=' ') /
misstext=[label='0'];
run;
ods html close;
quit;
%end;
%else 
      %export(work,sr12,sock,&exporttype);
Title;
Footnote;
%finish:
%mend nbssr00012;
%nbssr00012;
