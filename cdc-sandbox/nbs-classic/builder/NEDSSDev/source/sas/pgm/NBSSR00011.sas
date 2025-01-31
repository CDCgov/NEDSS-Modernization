**********************************************************************************;
*** Macro: NBSSR00011.SAS                                                      ****;
*** Input: Macro Variables (ExportType DataSourceName TimePeriod Dispaly wclause)**;
***********************************************************************************;
%macro nbssr00011;

%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;

data sr00011;
	set work.PHCDemographic;
	if state_cd='' then state_cd='N/A';
	if state='' then state='N/A';
	if county='' then county='N/A';
	if cnty_cd='' then cnty_cd='N/A';
run;

Proc SQL;
Create table sr11 
as select
state_cd,
State,
County,
PHC_code_short_desc,
year(datepart(event_date)) 		as year,
sum(group_case_cnt)	 as Cases 	label='Cases'	format 10.
from sr00011
by group state_cd,state,county,PHC_code_short_desc,year;
quit;

Data _null_;
set sr11(obs=1);
call symput('stname',trim(State));
run;

Title;
Title1 BOLD HEIGHT=5 FONT=Times <p align="center">"SR11: Cases of Selected Diseases By Year Over Time"</p>;
Title2 HEIGHT=4 FONT=Times <p align="center">"For &stname and &timeperiod"</p>;
Title3 <div style='background-color:#E6E6E6;'>
	<table><tr>
	<td style='font-size:11pt; font-family:Times New Roman;'><b><U>Report content</U></b></td>
	</tr>
	<tr><td style='font-size:11pt; font-family:Times New Roman;'><b>Data Source: </b> nbs_ods.PHCDemographic (publichealthcasefact)</td></tr>
	<tr>
	<td style='font-size:11pt; font-family:Times New Roman;'><b>Output:</b> Report demonstrates, in table form, the total number of Investigation(s) [both Individual and Summary] by calculated MMWR Year irrespective of Case Status.  Output:
	<LI>Does not include Investigation(s) that have been logically deleted</LI>
	<LI>Is filtered based on the state, disease(s), time frame and advanced criteria selected by user</LI>
	<LI>Will not include Investigation(s) that do not have a value for the State selected by the user</LI>
	<LI>Is based on the year of the calculated Event Date (not the MMWR Year defined by the user)</LI>
	</td></tr>
	<tr>
	<td style='font-size:11pt; font-family:Times New Roman;'><b>Calculations:</b>
	<LI><b>Event Date:</b>  Derived using the hierarchy of Onset Date, Diagnosis Date, Report to County, Report to State and Date the Investigation was created in the NBS.</LI>
	</td></tr></table>
	<div>;

%footnote;

%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_top_matter no_bottom_matter);
Proc Tabulate data=sr11 FORMAT=10.;
Class PHC_code_short_desc Year;
Var cases;
Keyword all sum;
Keylabel all="Total";
Table (PHC_code_short_desc=' ' all),
year='Year'*cases=' '*sum=' '/
misstext=[label='0']
box=[label='Disease'];
run;
ods html close;
quit;
%end;
%else 
      %export(work,sr11,sock,&exporttype);
Title;
Footnote;
%finish:
%mend nbssr00011;
%nbssr00011;
