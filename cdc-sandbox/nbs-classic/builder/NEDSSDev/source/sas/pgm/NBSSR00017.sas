**********************************************************************************;
*** Macro: NBSSR00017.SAS                                                      ****;
*** Input: Macro Variables (ExportType DataSourceName TimePeriod Dispaly wclause)**;
***********************************************************************************;
%etllib;
data inv_status; 
	infile datalines delimiter=','; 
	input cases case_class_desc $30.; 
	datalines; 
0,Confirmed
0,Not a Case
0,Probable
0,Suspect
0,Unknown
;

%macro nbssr00017;
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;

Proc SQL noprint;
Create table sr17
as select
sum(group_case_cnt)	 as cases,
phc_code_short_desc,
(select code_short_desc_txt from nbs_srt.code_value_general cvg where phc.case_class_cd = cvg.code and cvg.code_set_nm ='PHC_CLASS') as case_class_desc,
'1' as dedup_column
from &DataSourceName phc
group by phc.phc_code_short_desc,phc.case_class_cd;

Create table sr17_exp
as select cases as Case 'Case Count',
phc_code_short_desc as Condition 'Condition' ,
case_class_desc as Case_Status 'Case Status'
from sr17 where case_class_desc is not null;
	

data sr_dedup;
	set sr17;
	by dedup_column;
	if first.dedup_column then do;
	end;
	else DELETE;
run;

Proc SQL noprint;
Create table sr_union as 
select * from sr17 
outer union corr 
select cases,(select phc_code_short_desc from sr_dedup) as phc_code_short_desc, case_class_desc from inv_status;
quit; 

Title;
Title1 BOLD HEIGHT=5 FONT=Times <p align="center"> "SR13: Counts of Selected Diseases By Case Status" </p>;
Title2  <div style='background-color:#E6E6E6;'>
	<table><tr>
	<td style='font-size:11pt;font-family:Times New Roman;'><b><U>Report content</U></b></td>
	</tr>
	<tr><td style='font-size:11pt;font-family:Times New Roman;'><b>Data Source: </b> nbs_ods.PHCDemographic (publichealthcasefact)</td></tr>
	<tr>
	<td style='font-size:11pt;font-family:Times New Roman;'><b>Output:</b> Report demonstrates, in table form, the total number of Investigation(s) [both Individual and Summary].  Output:
	<LI>Does not include Investigation(s) that have been logically deleted</LI>
	<LI>Is filtered based on the disease(s) and advanced criteria selected by user</LI>
	<LI>Will not include Investigation(s) that do not have a value for Case Status</LI>
	</td></tr>
	<tr>
	<td style='font-size:11pt;font-family:Times New Roman;'><b>Calculations:</b></td></tr>
	<tr><td style='font-size:11pt;font-family:Times New Roman;'>
	<LI><b>Total Count by disease:  </b> Total Investigations for each disease irrespective of case status</LI>
	<LI><b>Total Count for all diseases: </b> Total Investigations for all diseases irrespective of case status</LI>
	</td></tr></table>
	<div>;
%footnote;


%if %upcase(&exporttype)=REPORT %then %do;
ods html body=sock (no_top_matter no_bottom_matter);
      Proc Tabulate data=sr_union FORMAT=comma10. EXCLNPWGTS;
      Class phc_code_short_desc case_class_desc;
      var cases;
      Keylabel sum=' ';
      Table (phc_code_short_desc=' ' all='Total') ,
      (case_class_desc='Case Status'  all='Total')*cases=' ' /
      misstext=[label='0']
      box=[label='Disease/Condition'];
      run;
      quit;
ods html close;
%end;
%else 
      %export(work,sr17_exp,sock,&exporttype);

Title;
Footnote;
%finish:
%mend nbssr00017;
%nbssr00017;
