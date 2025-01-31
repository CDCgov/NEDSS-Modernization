%macro footnote;
proc sql noprint;
select put(datepart(max(etlendtime)),mmddyy10.)
	into: update 
	from rdbdata.etlstatus;
quit;
%put "Row last updated date: &update";

/*see reportenv.sas for format nbs*/
data _null_;
rptdt=input("&SYSDATE9"||' '||"&SYSTIME",DATETIME20.);
call symput('rptdate',put(rptdt,nbsrptdt. ));
run;


%local ft i;
%let i=1;

Footnote;
Footnote1 h=1 j=L Font=swiss "This report was built using the following criteria:"
		  j=R "Report run on: &rptdate";
Footnote2 h=1 j=R  Font=swiss "Data refreshed on: &update"; 
%let test=;
%do %until( &test=%qscan(&footer,&i,|) or (&i >9));
	%let ft=%qscan(&footer,&i,|);
	%let i = %eval(&i+1);
	%let j = %eval(&i+1);
	/*the footer is quoted in the form %nrstr("...") 
	here the double quote " at the beginning and end are removed */
	Footnote&j h=1 j=l Font=swiss "%qsysfunc(tranwrd(&ft,%nrstr(%"),%nrstr()))";
%end;
%mend;

