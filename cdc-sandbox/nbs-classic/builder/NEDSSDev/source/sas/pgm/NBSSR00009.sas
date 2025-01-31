**********************************************************************************;
*** Macro: NBSSR00009.SAS                                                      ****;
*** Input: Macro Variables (ExportType DataSourceName TimePeriod Dispaly wclause)**;
***********************************************************************************;
%macro nbssr00009;
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;

data sr00009;
	set work.PHCDemographic;
	if state_cd='' then state_cd='N/A';
	if state='' then state='N/A';
	if county='' then county='N/A';
	if cnty_cd='' then cnty_cd='N/A';
run;

Proc SQL;
Create table sr9 
as select 
state_cd,
State,
County,
PHC_code_short_desc,
Put(Datepart(event_date),MONNAME3.) as monyr,
put(Datepart(event_date),YYMMn6.) 	as ord,
sum(group_case_cnt)	 as Cases	label='Cases'
from sr00009
by group state_cd,state,county,PHC_code_short_desc,ord,monyr;

Create table monyr as select distinct 
monyr,ord
from sr9
order by ord;
quit;

Data _null_;
retain order;
length order $30000;
set monyr nobs=numobs;
if _N_=1 then do;
 order="'"||trim(monyr)||"'";
end;
else order=trim(order)||" '"||trim(monyr)||"'";
if _N_=numobs then do;
  call symput('order',trim(order));

end;
run;

Data _null_;
set sr9(obs=1);
call symput('stname',trim(State));
call symput('disease',trim(PHC_code_short_desc));
run;

Proc means data=sr9 noprint;
Class Monyr;
var cases;
output out=rptdata(drop=_type_ _freq_) sum=;
run;

Title;
Title1 "SR9: Bar Graph of Selected Disease by Month";
Title2 "For &stname, &timeperiod";
Title3 J=L UNDERLIN=1 F=SWISS HEIGHT=.7 'Report content'
       J=L 'Data Source: nbs_ods.PHCDemographic (publichealthcasefact)'
       J=L ' '
       J=L 'Output: Report demonstrates, using a vertical bar graph, the total number of monthly Investigation(s)' 
       J=L '[both Individual and Summary] for a given disease, by State, irrespective of Case Status. Output:'
       J=L '1) Does not include Investigation(s) that have been logically deleted'
       J=L '2) Is filtered based on the state, disease, time frame and advanced filter criteria selected by the user'
       J=L '3) Will not include Investigation(s) that do not have a value for the State selected by the user'
       J=L '4) Is based on the calculated Event Date'
       J=L ' '
       J=L 'Calculations:'
       J=L '1) Total Monthly Cases:  Total Investigation(s) [both Individual and Summary] by state and disease' 
       J=L '   for each month over the selected time frame'
       J=L '2) Event Date: Derived using the hierarchy of Onset Date, Diagnosis Date, Report to County, Report to' 
       J=L '   State and Date the Investigation was created in the NBS.';


%footnote;

%if %upcase(&exporttype)=REPORT %then %do;
goptions reset=goptions;
goptions transparency gsfname=sock device=gif noborder
hsize=12 in vsize=20 in;
pattern1 color=blue;

axis1 label=(HEIGHT=1 Font="SWISS" 'Month')
        major=none
        minor=none
		ORDER=(&order)
        ;
 
  axis2 label=(HEIGHT=1 Font="SWISS" Angle=90 'Number of Cases');

 GOPTIONS NOCHARACTERS;
 proc gchart data=rptdata;
   vbar3d Monyr/sumvar=Cases
             maxis=axis1 raxis=axis2 name="Monthly" outside=sum;
 run;
quit;

%end;
%else 
      %export(work,sr9,sock,&exporttype);
Title;
Footnote;
%finish:
%mend nbssr00009;
%nbssr00009;

