**************************************************************************************;
*** NBSSR0016: Number of Cases and Deaths by Disease, Year Count                   ***;
***                                                                                ***;
*** CREATED : 01-14-2002														   ***;
*** INPUT   : SQLVIEW PHC_TO_PERSON_VIEW										   ***;
**************************************************************************************;

%let where=input(state_cd,2.)=stfips("IL")and year(datepart(rpt_form_cmplt_time)) <= 1995
			and trim(phc_code_short_desc)='Anthrax';
*Libname sqlv odbc database=atldev_2 user=nbs_ods password=ods;
Libname sqlv "\\atlnt1\CDC\Standard Reports";
*filename sock 'c:\out';

%macro sr16(inview,rptyear,dis_name,sock);

%include 's:/subset.sas';
%if &skip=yes %then %goto finish;

data sr16;
  set &inview (keep=state Death_Date Death_ind Disease County) nobs=numobs;

	length start_date end_date 8 disease_str state_str County_str $2000;
	retain start_date end_date 0 disease_str state_str county_str;
    length yeartxt $6 Deaths 4;

	count=1;
	Year=year(Death_date);
	if year > &rptyear then delete;
	else if year < &rptyear - 2 then 
		yeartxt=" <" ||put(&rptyear - 2,4.);
	else yeartxt=put(year,4.);
	if Death_ind='Y' then deaths=1;
		else Deaths=0;
****************     Generating Macro variables **********************;
	if _n_=1 then do;
		start_date=Diagnosis_date;
		end_date=Diagnosis_date;
		disease_str=trim(disease);
		state_str=trim(state);
		county_str=trim(county);
	end;
	else do;
		if Diagnosis_date < start_date then
			start_date=Diagnosis_date;
		if Diagnosis_date > end_date then 
			end_date=Diagnosis_date;
		if not indexw(disease_str,trim(disease)) then
			disease_str=trim(disease_str)||" , " || trim(disease);
		if not indexw(state_str,trim(state)) then
			state_str=trim(state_str)||" , "||trim(state);
		if not indexw(county_str,trim(county)) then
			county_str=trim(county_str)||" , "||trim(county);
	end;

	if _n_=numobs then do;
		call symput('start_date',start_date);
		call symput('end_date',end_date);
		call symput('update',put(date(),mmddyy10.));

		call symput('disease_str',' ');
		call symput('disease_str',trim(disease_str));

		call symput('state_str',' ');
		call symput('state_str',trim(state_str));

		call symput('county_str',' ');
		call symput('county_str',trim(county_str));
	end;
run;

proc sort data=sr16; by yeartxt Disease;
run;

proc summary data=sr16 nway;
class yeartxt county;
var count deaths;
output out=sr16_count(drop=_freq_ _type_) sum=;
run;

Title;
Footnote;

options ls=132 spool;
ods html body=sock(no_top_matter no_bottom_matter);

%local styear;
%let styear=%eval(&rptyear-2);

Title1 BOLD HEIGHT=5 FONT=Times <p align="center"> "IL: Number of Cases and Deaths by &dis_name during &rptyear - &styear "</p>;
Footnote1 h=1 j=L Font=swiss "This report was built using the Following Criteria:"; 
Footnote2 h=1 j=L Font=swiss "State: &State_str"; 
Footnote3 h=1 j=L Font=swiss "County: &County_str";
Footnote4 h=1 j=L Font=swiss "Disease: &disease_str"; 
Footnote5 h=1 j=R Font=swissb "This report is created on &update"; 

proc tabulate data=sr16_count format=comma6.0;
	class yeartxt County;
	var count Deaths;
	table County=' ' All='Total',Count='Cases' *(yeartxt=' ' all='Total') 
				Deaths * (yeartxt=' ' all='Total')/box='Counties' misstext='0';
   keylabel sum=' ' All=' ';
run;
quit;

ods html close;
Title;
Footnote;

%finish:

%mend sr16;
%sr16(phc_To_Person_View,1995,Anthrax,sock);