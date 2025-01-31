**************************************************************************************;
*** NBSSR0006: Line list Report of Individual Cases for Selected Geographic Area   ***;
***            and Selected Time Period                                            ***;
*** CREATED : 01-11-2002														   				***;
*** INPUT   : SQLVIEW PHC_TO_PERSON_VIEW										   ***;
**************************************************************************************;

%macro NBSSR00006;

%chk_mv;
%if %upcase(&mac_var_missing)=YES or %upcase(&mac_val_missing)=YES 
	or %upcase(&skip)=YES %then
      %goto finish;

************************************************************************;
** SORTING THE DATA BY STATE COUNTY DISEASE AND YEAR                    ;
************************************************************************;
proc sql;
create table sr6
as select 
state_cd,
State,
County,
public_health_case_uid		label='Case ID',
phc_code_short_desc			label='Disease',
datepart(birth_time) as Birth_Time		label='Birth Date'			format=mmddyy10.,
Ethnic_group_ind_desc		label='Ethnicity',
Zip_cd							label='Zip Code',
datepart(rpt_to_county_time) as Reported_Date label='Reported Date' format=mmddyy10.,
datepart(datepart(diagnosis_date)) as Diagnosis_Date,
count(*) as cases
from &DataSourceName
where state ne '' and state ne '' and County ne ' '
		and phc_code_short_desc ne '' and diagnosis_date gt 0
group by state_cd,state,PHC_code_short_desc,County
order by state_cd,state,PHC_code_short_desc,County;

quit;

Data _null_;
set sr6(obs=1);
call symput('stname',trim(State));
call symput('cntyname',trim(county));
call symput('rptdate',put(date(),MMDDYY10.));
call symput('update',put(date(),MMDDYY10.));
run;

options ls=132 spool;

Title1 BOLD HEIGHT=5 FONT=Times <p align="center">"Cases of Selected Diseases by County"</p>;
Title2 HEIGHT=4 FONT=Times <p align="center"> "For &stname "</p>;
Title3 HEIGHT=4 FONT=Times <p align="center"> "&timeperiod"</p>;

%parse_wcls;

%if %upcase(&exporttype)=REPORT %then %do;

ods html body=sock(no_top_matter no_bottom_matter);
proc sort data=sr6; by county PHC_code_short_desc diagnosis_date;
run;

proc print data=sr6 label N='County Total = ';
 id Public_health_Case_uid;
 by county ;
 var PHC_code_short_desc birth_time Ethnic_group_ind_desc Zip_cd Reported_date ;
 run;

ods html close;
%end;
%else 
      %export(work,sr6,sock,&exporttype);
Title;
Footnote;

%finish:
%mend NBSSR00006;
%NBSSR00006;
