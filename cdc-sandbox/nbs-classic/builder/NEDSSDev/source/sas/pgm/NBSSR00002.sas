**********************************************************************************;
*** Macro: NBSSR00002.SAS                                                      ****;
*** Input: Macro Variables (ExportType DataSourceName TimePeriod Dispaly wclause)**;
***********************************************************************************;
%macro nbssr00002;
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;


data sr00002;
	set &DataSourceName;
	if County='' then County='N/A';
	if State='' then State='N/A';
run;

Proc SQL noprint;
Create table sr2
as select
State,
County,
phc_code_short_desc,
sum(group_case_cnt)	 as Cases
from sr00002
by group state,county,phc_code_short_desc;
quit;


Data _null_;
set sr2(obs=1);
call symput('stname',trim(state));
run;

Title;
Title1 BOLD HEIGHT=5 FONT=Times <p align="center">"SR2: Counts of Reportable Diseases by County for Selected Time frame"</p>;
Title2 HEIGHT=4 FONT=Times <p align="center">"For &stname and &timeperiod"</p>;
Title3  <div style='background-color:#E6E6E6;'>
	<table><tr>
	<td style='font-size:11pt;font-family:Times New Roman;'><b><U>Report content</U></b></td>
	</tr>
	<tr><td style='font-size:11pt;font-family:Times New Roman;'><b>Data Source: </b> nbs_ods.PHCDemographic (publichealthcasefact)</td></tr>
	<tr>
	<td style='font-size:11pt;font-family:Times New Roman;'><b>Output:</b> Report demonstrates, in table form, the total number of Investigation(s) [both Individual and Summary] by County irrespective of Case Status.  Output:
	<LI>Does not include Investigation(s) that have been logically deleted</LI>
	<LI>Is filtered based on the state, county(s), disease(s), time frame and advanced criteria selected by user</LI>
	<LI>Will not include Investigation(s) that do not have a value for the State selected by the user</LI>
	<LI>Will not include Investigation(s) that do not have a value for the County(s) selected by the user</LI>
	<LI>Is based on the calculated Event Date</LI>
	</td></tr>
	<tr>
	<td style='font-size:11pt;font-family:Times New Roman;'><b>Calculations:</b></td></tr>
	<tr><td style='font-size:11pt;font-family:Times New Roman;'>
	<LI><b>Total Count by disease:  </b> Total Investigations for each disease over a selected time frame</LI>
	<LI><b>Total Count for all diseases: </b>  Total Investigations for all diseases over a selected time frame</LI>
	</td></tr></table>
	<div>;

%footnote;

%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_top_matter no_bottom_matter);
      Proc Tabulate data=sr2 FORMAT=comma10.;
      Class phc_code_short_desc County;
      var cases;
      Keylabel sum=' ';
      Table phc_code_short_desc=' ' all='Total' ,(County='County'  all='Total')*cases=' ' /
      misstext=[label='0']
      box=[label='Disease/Condition'];
      run;
      quit;
ods html close;
%end;
%else 
      %export(work,sr2,sock,&exporttype);

Title;
Footnote;
%finish:
%mend nbssr00002;
%nbssr00002;
