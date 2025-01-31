%macro csv;
filename sock 'c:\out';
%local dsid nvars varlist nl datastr dsname varlable chklabel;
%let dsname=sashelp.vidmsg;
%let dsid=%sysfunc(open(&dsname,i));
%let nvars=%sysfunc(attrn(&dsid,nvars));
%let varlist=;
%let varlabel=;
%let datastr=;
%let chklabel=;
%let nl='0d0a'x;

%do i=1 %to &nvars;
	%if &i=&nvars %then %do;
		%let varlist=&varlist%sysfunc(varname(&dsid,&i));
		%let chklabel=%sysfunc(varlabel(&dsid,&i));
		%if %quote(&chklabel) ne  %then
			%let varlabel=&varlabel%sysfunc(varlabel(&dsid,&i));
		%else %let varlabel=&varlabel%sysfunc(varname(&dsid,&i));
	%end;
	%else %do;
		%let varlist=&varlist%sysfunc(varname(&dsid,&i)),;
		%let chklabel=%sysfunc(varlabel(&dsid,&i));
		%put &chklabel;
		%if %quote(&chklabel) ne   %then
			%let varlabel=&varlabel%sysfunc(varlabel(&dsid,&i)),;
		%else %let varlabel=&varlabel%sysfunc(varname(&dsid,&i)),;
	%end;
%end;

%let memtype=%sysfunc(attrc(&dsid,MTYPE));
%let dsid=%sysfunc(close(&dsid));

data _null_;
file sock;
put "&varlabel";
run;

%if %quote(&memtype)=VIEW %then %do;
data csvdata;
  set &dsname;
run;
%LET dsname=csvdata;
%end;

data _null_;
length datastr $2000;
retain datastr;
 set &dsname nobs=numobs;
 if _n_=1 then do
 	datastr="data _null_;  file sock mod;  set &dsname;";
 	do i=1 to &nvars;
		if i=&nvars then
		datastr=trim(datastr)||"Put " ||scan("&varlist",i,',')||";" ;
		else
		datastr=trim(datastr)||"Put " ||scan("&varlist",i,',')||"@; Put ','@;"; 
 	end;
 end;
 if _n_=numobs then do;
 	datastr=trim(datastr)||'run;';
	call symput('datastr',datastr);
	put datastr=;
 end;
run;
&datastr;
%mend;
%csv;
