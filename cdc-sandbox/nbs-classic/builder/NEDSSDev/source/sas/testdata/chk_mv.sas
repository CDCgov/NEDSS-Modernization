%macro chk_mv;
****     Checking for erros in subsetting                                ****;
%global zero_rec tech_err;
%let skip=no; 
%let zero_rec=no;
%let tech_err=no;
data _null_;
if &sqlobs=0 or &sqlrc > 0 or &syserr > 0 or &sysdbrc > 0 then
call symput('skip','yes');
if &sqlobs=0 then 
call symput('zero_rec','yes');
if &sqlrc > 0 or &syserr > 0 or &sysdbrc > 0 then
call symput('tech_err','yes');
run;

%local ds_exist exp_exist tp_exist;
%let ds_exist=no;
%let et_exist=no;
%let tp_exist=no;

%global mac_var_missing mac_val_missing;
%let mac_var_missing=no;
%let mac_val_missing=no;

****    Checking for the existence of macro variables and their value   ****;

data _null_;
   set sashelp.vmacro;
   if trim(name)='DATASOURCENAME' and trim(scope)='GLOBAL' then do;
      %do; %let ds_exist=yes; %end;
   end;
   if trim(name)='EXPORTTYPE' and trim(scope)='GLOBAL' then do;
      %do; %let et_exist=yes; %end;
   end;
   if name='TIMEPERIOD' and scope='GLOBAL' then do;
      %do; %let tp_exist=yes; %end;
   end;
run;
%let mac_var_missing=no;
%if %quote(&ds_exist)='no' or %quote(&et_exist)='no' or %quote(&tp_exist)='no' %then %do;
      %let mac_var_missing=yes;
%end;

%let mac_val_missing=no;
%if %quote(&datasourcename)= or %quote(&exporttype)= or %quote(timeperiod)= %then %do;
      %let mac_val_missing=yes;
%end;

*** Assigning Filename to the socket   *******;
*filename sock SOCKET "&host:&port";
filename sock 'c:\temp\sr.out';

%if %upcase(&mac_var_missing)=YES or %upcase(&mac_val_missing)=YES 
	or %upcase(&tech_err)=YES 
		%then  %do;
	data a;
		label X='00'x;
		X='There is no data for the criteria you selected. Please check your selection and try again.';
	run;
	ods html body=sock (no_top_matter no_bottom_matter);
	title;
	proc print data=a noobs label;
	run;
	ods html close;
	%end;

%if %upcase(&zero_rec)=YES and %upcase(&tech_err)=NO
		%then  %do;
	data a;
		label x='00'x;
		X='There is no data for the criteria you selected. Please check your selection and try again. ';
	run;
	ods html body=sock (no_top_matter no_bottom_matter);
	title;
	proc print data=a noobs label;
	run;
	ods html close;
	%end;

data _null_;
	if (("&mac_val_missing"="yes" or "&mac_var_missing"="yes") and "&skip" ~= "yes") then
	call symput('skip','yes');
run;

/*data refreshed date*/
/*
proc sql noprint;
select put(datepart(max(mart_record_creation_time)),mmddyy10.) 
	into: update 
	from &DataSourceName;
quit;
%put "Row last updated date: &update";
*/
%mend chk_mv;
