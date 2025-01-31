
options nocenter mprint mlogic symbolgen missing=' ';


%macro QA05;

%chk_mv;
%etllib;
%if  %upcase(&skip)=YES %then %goto finish;
 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;
%formats;

proc sql buffersize=1M;
create table INV as
select distinct count(*) as OOJ_REFF , a.FIRST_NM, a.LAST_NM, a.PROVIDER_QUICK_CODE, a.ADD_USER_ID 
from v_event_metric a
inner join nbs_rdb.STD_HIV_DATAMART c on a.LOCAL_ID = c.INV_LOCAL_ID
inner join nbs_rdb.F_STD_PAGE_CASE d on d.investigation_key=c.investigation_key
inner join nbs_rdb.D_INV_ADMINISTRATIVE e on e.D_INV_ADMINISTRATIVE_KEY = d.D_INV_ADMINISTRATIVE_KEY
where a.EVENT_TYPE in ('PHCInvForm') and e.ADM_REFERRAL_BASIS_OOJ is not null 
group by a.FIRST_NM, a.LAST_NM, a.PROVIDER_QUICK_CODE, a.ADD_USER_ID 
order by a.ADD_USER_ID
;
quit;

proc sql;
create table PROG_AREA as
select distinct PROG_AREA_CD from nbs_srt.condition_code where (condition_cd in ('10560','900') or nnd_entity_identifier='STD_Case_Map_v1.0')
;
quit;

proc sql;
create table LAB_MORB as 
select distinct count(*) as REACTOR , FIRST_NM, LAST_NM,PROVIDER_QUICK_CODE, ADD_USER_ID 
from v_event_metric 
where EVENT_TYPE in ('LabReport','MorbReport') and prog_area_cd in (select PROG_AREA_CD from PROG_AREA) 
AND (V_EVENT_METRIC.ELECTRONIC_IND = 'N')
group by FIRST_NM, LAST_NM, PROVIDER_QUICK_CODE, ADD_USER_ID 
order by ADD_USER_ID
;
quit;

proc sql;
create table CONTACT as
select distinct count(*) as PART_CLUS , FIRST_NM, LAST_NM, PROVIDER_QUICK_CODE, ADD_USER_ID 
from v_event_metric 
where EVENT_TYPE in ('CONTACT') and prog_area_cd in (select PROG_AREA_CD from PROG_AREA) group by FIRST_NM, LAST_NM, PROVIDER_QUICK_CODE, ADD_USER_ID order by ADD_USER_ID
;
quit;

DATA QA05_MERGE;
length user_qc $100.;
     MERGE INV LAB_MORB CONTACT;
     by ADD_USER_ID ;
user_qc=(trim(provider_quick_code)||' - '||trim(first_nm)||' '||trim(last_nm));

array nvar(*) _numeric_;
	do i= 1 to dim(nvar);  
    if nvar(i)=. then nvar(i)=0;
end;
drop i;

run;

proc sort data=QA05_MERGE out=work;
by user_qc;
run;

*proc print data=QA05_MERGE (obs=100);
*run;


ods listing close;
options orientation=portrait CENTER NONUMBER NODATE LS=248 PS =256 COMPRESS=NO  MISSING = ' ' 
topmargin=.25in bottommargin=.25in leftmargin=.02in rightmargin=.02in  nobyline  papersize=a4;

%if %upcase(&exporttype)=REPORT %then %do;

ods escapechar='^';

title j=center f=Calibri bold h=12pt "&reportTitle";

%footnote;
Footnote;
Footnote1 j=L h=8pt f= Calibri "___________________________________________________________________________________________________________________________________________________";
Footnote2 j=C h=8pt f= Calibri "Report run on:&rptdate";
Footnote3 j=C h=8pt f= Calibri "Data Refreshed on: &update";
Footnote4 j=C h=8pt f= Calibri "This Report was built using the following criteria: &footer";
Footnote5 j=C h=8pt f= Calibri "Page ^{thispage} of ^{lastpage}";
options printerpath=pdf;
ods PDF body = sock style=styles.listing notoc uniform;


PROC REPORT  data=work nowindows ls=256
style(header)={just=center font_weight=bold font_face="Calibri" font_size = 10pt};
COLUMNS user_qc REACTOR PART_CLUS OOJ_REFF; 
DEFINE user_qc / display 'USER' style(column)=[cellwidth=40mm just=left vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0]
       style(header)={just=left};
DEFINE REACTOR / display 'REACTOR REPORTS' center style(column)=[cellwidth=35mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0];
DEFINE PART_CLUS / display "PARTNER\CLUSTER REFERRALS" center style(column)=[cellwidth=55mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0];
DEFINE OOJ_REFF / display 'OOJ REFERRALS' center style(column)=[cellwidth=35mm just=center vjust=center font_face='Calibri' font_size=8pt cellpadding=1.0 cellspacing=0];
RUN; 
ods pdf close;
ods listing;

data _null_;

call symputx ('START_TIME',PUT(DATETIME(),15.));
START_TIME = &START_TIME;
END_TIME = DATETIME();
ELAPSED = END_TIME - START_TIME;

PUT ' NOTE: Start Time (HH:MM) = ' START_TIME TIMEAMPM8.;
PUT ' NOTE: End Time (HH:MM) = ' END_TIME TIMEAMPM8.;
PUT ' NOTE: Elapsed Time (HH:MM:SS) = ' ELAPSED TIME.; PUT ' ';
run;

%end;

%else 

%export(work,work,sock,&exporttype);
Title;
%finish:
%mend QA05;
%QA05;








