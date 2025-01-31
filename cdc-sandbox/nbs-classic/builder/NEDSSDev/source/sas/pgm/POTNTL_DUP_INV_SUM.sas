
%macro POTNTL_DUP_INV_SUM;
proc sql buffersize=1M;
create table PotentialDuplicate_INIT as
select * from INV_SUMM_DATAMART order by PATIENT_LOCAL_ID, DISEASE_CD, EVENT_DATE;
quit;
%IF &daysValue<0 %THEN %DO;
%LET daysValue=3650;/*default to 10 years if days are left blank in the basic filter*/
%END;


DATA PotentialDuplicatE_DUP1;
	SET PotentialDuplicate_INIT;
	WHERE EVENT_DATE ~=.;
	RETAIN OLD_PATIENT_LOCAL_ID OLD_DISEASE_CD OLD_EVENT_DATE;
	IF OLD_PATIENT_LOCAL_ID = PATIENT_LOCAL_ID  AND OLD_DISEASE_CD = DISEASE_CD THEN DO;
		DAYS_1 = date() - datepart(EVENT_DATE);
		DAYS_2 = date() - datepart(OLD_EVENT_DATE);
		DAYS1 = DAYS_2-DAYS_1;
	END;
	OLD_EVENT_DATE=EVENT_DATE;
	OLD_PATIENT_LOCAL_ID=PATIENT_LOCAL_ID;
	OLD_DISEASE_CD=DISEASE_CD;
	IF DAYS1=. THEN DO;
		DAYS1=0;
	END;
	DROP OLD_EVENT_DATE OLD_PATIENT_LOCAL_ID OLD_DISEASE_CD DAYS_1 DAYS_2;
RUN;

PROC SORT DATA=PotentialDuplicate_INIT;
  BY PATIENT_LOCAL_ID DISEASE_CD DESCENDING EVENT_DATE ;
RUN ;


DATA PotentialDuplicatE_DUP2;
	SET PotentialDuplicate_INIT;
	WHERE EVENT_DATE ~=.;
	RETAIN OLD_PATIENT_LOCAL_ID OLD_DISEASE_CD OLD_EVENT_DATE;
	IF OLD_PATIENT_LOCAL_ID = PATIENT_LOCAL_ID  AND OLD_DISEASE_CD = DISEASE_CD THEN DO;
		DAYS_1 = date() - datepart(EVENT_DATE);
		DAYS_2 = date() - datepart(OLD_EVENT_DATE);
		DAYS2 = DAYS_2-DAYS_1;
	END;
	OLD_EVENT_DATE=EVENT_DATE;
	OLD_PATIENT_LOCAL_ID=PATIENT_LOCAL_ID;
	OLD_DISEASE_CD=DISEASE_CD;
	IF DAYS2=. THEN DO;
		DAYS2=0;
	END;
	DROP OLD_EVENT_DATE OLD_PATIENT_LOCAL_ID OLD_DISEASE_CD DAYS_1 DAYS_2;
RUN;


PROC SORT DATA=PotentialDuplicatE_DUP1;
  BY PATIENT_LOCAL_ID   EVENT_DATE;
RUN ;

PROC SORT DATA=PotentialDuplicatE_DUP2;
  BY PATIENT_LOCAL_ID   EVENT_DATE;
RUN ;




DATA PotentialDuplicatE_MERGE_INIT;
	MERGE PotentialDuplicatE_DUP1 PotentialDuplicatE_DUP2;
	by PATIENT_LOCAL_ID   EVENT_DATE;
run;

PROC SORT DATA=PotentialDuplicatE_MERGE_INIT;
  BY PATIENT_LOCAL_ID DISEASE_CD DESCENDING EVENT_DATE ;
RUN ;



PROC SQL;
	delete from PotentialDuplicatE_MERGE_INIT where DAYS1=0 and DAYS2>&daysValue;
	delete from PotentialDuplicatE_MERGE_INIT where DAYS2=0 and DAYS1> &daysValue;
run;


proc sql;
create table PotentialDuplicate_COUNT as
	select PATIENT_LOCAL_ID, count(PATIENT_LOCAL_ID) as COUNT, DISEASE_CD 
	from PotentialDuplicatE_MERGE_INIT
	WHERE DAYS1 <=&daysValue OR DAYS2>=-&daysValue
	group by PATIENT_LOCAL_ID,DISEASE_CD  
	order by PATIENT_LOCAL_ID,DISEASE_CD;
quit;




DATA PotentialDuplicate_MERGE1;
	MERGE PotentialDuplicatE_MERGE_INIT PotentialDuplicate_COUNT;
	by PATIENT_LOCAL_ID DISEASE_CD;
run;
PROC SQL;
	delete from PotentialDuplicate_MERGE1 where DAYS1=0 and DAYS2>&daysValue;
	delete from PotentialDuplicate_MERGE1 where DAYS2=0 and DAYS1> &daysValue;
run;

proc sql;
create table PotentialDuplicate as
	select * from PotentialDuplicate_MERGE1 where count >1 AND (DAYS1 <= &daysValue  OR DAYS2>=-&daysValue)
	&orderClause;
quit;

%chk_mv;
data PotentialDuplicate;
set PotentialDuplicate;
drop DAYS1 DAYS2 count;
run;

proc contents data=PotentialDuplicate out=DScolumns (keep=format name);
run;

data _null_;
set DScolumns end=done;
	if not missing(format) and format='DATETIME' then do;
		i+1;
		call symput('var'|| trim(left(put(i,8.))),trim(name));
		end;
	if done then call symput('total',trim(left(put(i,8.))));
run;

proc datasets lib=work nolist nowarn;
	modify PotentialDuplicate;
	%do i=1 %to &total;
		format &&var&i nbsrptdt.;
	%end;
run;


Title;
Title1 BOLD HEIGHT=5 FONT=Times J=C <p align="center"> "Potential Duplicate Investigations" </p>;
Title2 HEIGHT=4 FONT=Times <p align="center"> "Duplicate Investigations Time Frame: &daysValue Days"</p>;

%footnote;

%if %upcase(&exporttype)=REPORT %then %do;

	data _null_;
	if 0 then set PotentialDuplicate nobs=nrec;
	if 0 then set DScolumns nobs=ncol;
	call symput('cellsize', put(nrec*ncol, 6.));
	stop;
	run;
	%put cellsize=&cellsize;
	%if &cellsize gt 10000 %then %do;
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
	Proc print data=PotentialDuplicate noobs label;
	run;
	ods html close;
	ods listing; 
	quit;
%end;
%else 
      %export(work,PotentialDuplicate,sock,&exporttype);

Title;
Footnote;
%finish:

%mend POTNTL_DUP_INV_SUM;
%POTNTL_DUP_INV_SUM;