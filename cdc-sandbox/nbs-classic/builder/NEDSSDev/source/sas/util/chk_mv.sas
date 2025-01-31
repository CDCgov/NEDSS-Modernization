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
   if name='BEGIN_RANGE' and scope='GLOBAL' then do;
      %do; %let tp_exist=yes; %end;
   end;
   if name='END_RANGE' and scope='GLOBAL' then do;
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
filename sock SOCKET "&host:&port";

%if %upcase(&mac_var_missing)=YES or %upcase(&mac_val_missing)=YES 
	or %upcase(&tech_err)=YES 
		%then  %do;
	data _null_;
		file sock;
		put 'There is a technical error. Please check with your sas administrator.';
	run;
	/*
	data a;
		label X='00'x;
		X='There is a technical error. Please check with your sas admisnitrator.';
	run;
	ods html body=sock (no_top_matter no_bottom_matter);
	title;
	proc print data=a noobs label;
	run;
	ods html close;
	*/
	%end;

%if %upcase(&zero_rec)=YES and %upcase(&tech_err)=NO
		%then  %do;
	
	data _null_;
		file sock;
		put 'There is no data for the criteria you selected. Please check your selection and try again. ';
	run;

	/*
	data a;
		label x='00'x;
		X='There is no data for the criteria you selected. Please check your selection and try again. ';
	run;
	ods html body=sock (no_top_matter no_bottom_matter);
	title;
	proc print data=a noobs label;
	run;
	ods html close;
	*/
	%end;

%mend chk_mv;
