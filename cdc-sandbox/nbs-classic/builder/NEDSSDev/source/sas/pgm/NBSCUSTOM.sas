**********************************************************************************;
*** Macro: NBSSR_cus.SAS                                                       ****;
*** Input: Macro Variables (ExportType DataSourceName TimePeriod Dispaly wclause)**;
***********************************************************************************;
%macro nbssrcustom;
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;

/*the next three steps format all datetime columns*/
proc contents data=&DataSourceName out=DScolumns (keep=format name);
run;

data _null_;
set DScolumns end=done;
	if format='DATETIME' then do;
		i+1;
		call symput('var'|| trim(left(put(i,8.))),trim(name));
		end;
	if done then call symput('total',trim(left(put(i,8.))));
run;

proc datasets lib=work nolist nowarn;
	modify &DataSourceName;
	%do i=1 %to &total;
		format &&var&i nbsrptdt.;
	%end;
run;

Title;
Title1 BOLD HEIGHT=5 FONT=Times J=C <p align="center"> "Custom Report For Table: &DataSourceName" </p>;
Title2 HEIGHT=4 FONT=Times <p align="center"> " &timeperiod"</p>;

%footnote;

%if %upcase(&exporttype)=REPORT %then %do;

	data _null_;
	if 0 then set &DataSourceName nobs=nrec;
	if 0 then set DScolumns nobs=ncol;
	call symput('cellsize', put(nrec*ncol, 9.));
	stop;
	run;
	%put cellsize=&cellsize;
	%if &cellsize gt &MAX_COLUMN_ROW_LIMIT_HTML %then %do;
		data _null_;
		file sock;
		put 'Your search has resulted in an abnormally large number of  records.  Please refine your search criteria entered.';
		*put 'Your selection resulted a large volumn of data. Please export your data or further limit your selection and try again. ';
		run;
		%goto finish;
	%end;

	ods listing close;
	ods html body=sock 	stylesheet=(URL="report/template1.css");
	*ods html body=sock 	stylesheet=(URL="file:\\\C:\NBS1.0.2_Dev_Development\Tomcat\webapps\nedss\web\resources\styles\template1.css");
	Proc print data=&DataSourceName noobs label;
	run;
	ods html close;
	ods listing;
	quit;
%end;
%else 

%do;
data _null_;
	if 0 then set &DataSourceName nobs=nrec;
	if 0 then set DScolumns nobs=ncol;
	call symput('cellsize', put(nrec*ncol, 9.));
	stop;
	run;
	%put cellsize=&cellsize;
	%if &cellsize gt &MAX_COLUMN_ROW_LIMIT_CSV %then %do;
		data _null_;
		file sock;
		put 'Your search has resulted in an abnormally large number of  records.  Please refine your search criteria entered.';
		run;
		%goto finish;
	%end;
      %export(work,&DataSourceName,sock,&exporttype);
%end;
Title;
Footnote;
%finish:
%mend nbssrcustom;
%nbssrcustom;