%macro ascii(libname,dsname,outfile,headtype,delimiter);
%local del;
%if %upcase(&delimiter)=COMMA %then %let del=',';
%if %upcase(&delimiter)=TAB %then %let del='09'x;

%local dsid nvars varlist nl datastr varlable chklabel;
%let dsid=%sysfunc(open(&libname..&dsname,i));
%let nvars=%sysfunc(attrn(&dsid,nvars));
%let varlist=;
%let varlabel=;
%let datastr=;
%let chklabel=;
%let nl='0d0a'x;

%do i=1 %to &nvars;
	%if &i=&nvars %then %do;
		%let varlist= &varlist %sysfunc(varname(&dsid,&i));
		%let chklabel=%sysfunc(varlabel(&dsid,&i));
		%if %quote(&chklabel) ne  %then
			%let varlabel=&varlabel%sysfunc(varlabel(&dsid,&i));
		%else %let varlabel=&varlabel%sysfunc(varname(&dsid,&i));
	%end;
	%else %do;
		%let varlist=&varlist %sysfunc(varname(&dsid,&i));
		%let chklabel=%sysfunc(varlabel(&dsid,&i));
		%put &chklabel;
		%if %quote(&chklabel) ne   %then
			%let varlabel=&varlabel%sysfunc(varlabel(&dsid,&i)),;
		%else %let varlabel=&varlabel%sysfunc(varname(&dsid,&i)),;
	%end;
%end;

%let rc=%sysfunc(close(&dsid));

data _null_;
length datastr $32767;
 	datastr="data _null_;  file &outfile dsd dlm=&del LRECL=32000;  set &libname..&dsname;";
	datastr=trim(datastr)|| " If _n_=1 then do;";
 	
	do i=1 to &nvars;
	    if i=&nvars then
			datastr=trim(datastr)||"Put '" ||scan("&varlabel",i,',')|| "';end;" ;
		else if  i=1 then 
			datastr=trim(datastr)||" Put '" ||scan("&varlabel",i,',')||"'@; Put &del @;"; 
		else 
		datastr=trim(datastr)||"Put '" ||scan("&varlabel",i,',')||"'@; Put &del @;"; 
 	end;
	datastr=trim(datastr)|| " Put &varlist; run;";

	call symput('datastr',datastr);
	put datastr=;
	y=length(datastr);
	put y=;
run;
&datastr;
%mend ascii;
