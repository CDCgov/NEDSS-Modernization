**********************************************************************************;
*** Macro: NBSSR00008.SAS                                                      ****;
*** Input: Macro Variables (ExportType DataSourceName TimePeriod Dispaly wclause)**;
***********************************************************************************;
%macro nbssr00008;

%if  %SYSFUNC(LIBREF(maps)) NE 0 %then 
	Libname maps '!SASROOT\maps';

%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;
Proc SQL;
Create table sr8 as 
select 
	state_cd,
	State,
	County as countyname		length=50,
	phc_code_short_desc			,
	event_date,
	cnty_cd,
	sum(group_case_cnt)	 as Cases	label='Cases'
from &DataSourceName
by group state_cd,state,county,phc_code_short_desc,event_date,cnty_cd;
quit;

Data _null_;
set sr8(obs=1);
	call symput('stname',trim(State));
	call symput('disease',trim(phc_code_short_desc));
	call symput ('stfips',substr(state_cd,1,2));
run;

Data rptdata(keep=state county cases countyname);
set sr8(keep=state_cd cnty_cd cases event_date countyname);
	county=input(substr(cnty_cd,3,3),3.);
	state=input(substr(state_cd,1,2),2.);
run;

Proc Means data=rptdata noprint;
	class county;
	id state countyname;
	var cases;
	output out=rptdata2(drop=_type_ _freq_) sum=;
run;

proc sort data=rptdata2; by state county;

proc sql;
	create table reportstate as
	select distinct state, county
	from maps.uscounty
	where state = &stfips
	order by state, county;
quit;

data rptdata0;
	merge reportstate (in=A) rptdata2(in=B where=(county ~=.));
	by state county;
	if A and not B then Cases =0;
	label cases = "&disease Cases";
run;

goptions reset=goptions;
Title;
Title1 BOLD HEIGHT=5 FONT=Times J=C "SR8: State Map Report of Disease Cases Over Selected Time Period";
Title2 HEIGHT=4 FONT=Times "For &stname &timeperiod";
Title3 <div style='background-color:#E6E6E6;'>
<table><tr>
<td style='font-size:11pt; font-family:Times New Roman;'><b><U>Report content</U></b></td>
</tr>
<tr><td style='font-size:11pt; font-family:Times New Roman;'><b>Data Source: </b> nbs_ods.PHCDemographic (publichealthcasefact)</td></tr>
<tr>
<td style='font-size:11pt; font-family:Times New Roman;'><b>Output:</b> Report demonstrates, using a choropleth map, the total number of Investigation(s) [both Individual and Summary] by County irrespective of Case Status.  Output:</LI>
<LI>Does not include Investigation(s) that have been logically deleted</LI>
<LI>Is filtered based on the state, disease, time frame and advanced criteria selected by the user</LI>
<LI>Will not include Investigation(s) that do not have a value for the State selected by the user</LI>
<LI>Will not include Investigation(s) that do not have a value for the County (even though a value for State may exist)</LI>
<LI>State and county boundaries are based on the SAS maps library uscounty dataset</LI>
<LI>Is based on the calculated Event Date</LI>
</td></tr>
<tr>
<td style='font-size:11pt; font-family:Times New Roman;'><b>Calculations:</b></td></tr>
<tr><td style='font-size:11pt;font-family:Times New Roman;'>
<LI><b>Total Cases by County: </b>Total Investigation(s) [both Individual and Summary] by county for a selected time frame</LI>
</td></tr>
</table>
<div>;


%footnote;

%if %upcase(&exporttype)=REPORT %then %do;

ods listing close;
*filename sock 'c:\temp\sr8.out';
ods html file= sock 
         /*parameters=( "DRILLDOWNMODE"="URL")*/ 
		 
	CODEBASE= "report/resource/jar/";

goptions gunit=pct cback=white
         ftext=swiss htitle=6 htext=3.5
		 /*use dev=javameta if you want to show other stuff on tooltips*/
         device=java transparency; 

pattern1 v=s c=cxccff99;
pattern2 v=s c=cxc1cc78;
pattern3 v=s c=cxb7995a;
pattern4 v=s c=cxad663c;
pattern5 v=s c=cxa3331e;
pattern6 v=s c=cx990000;

proc gmap data=rptdata0 map=maps.uscounty;
   id state county;
   choro Cases /  coutline=black
   				  levels = 5
                  des="State of &stname"
                  name="SR8Map"
				  	;
run;
quit;
ods html close;
ods listing;
goptions reset=goptions;
%end;
%else 
      %export(work,sr8,sock,&exporttype);

Title;
Footnote;
%finish:
%mend nbssr00008;
%nbssr00008;

