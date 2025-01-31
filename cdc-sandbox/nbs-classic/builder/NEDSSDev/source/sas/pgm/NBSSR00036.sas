%macro NBSSR00036;
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;
libname nbs_srt ODBC DSN=nbs_srt UID=nbs_ods PASSWORD=ods ACCESS=READONLY;
Proc sql;
	create table ls2_lab as SELECT distinct 
	lmr.LAB_TEST_CD, lmr.Observation_uid , lmr.PROCESSING_DECISION_CD,lmr.ALT_LAB_TEST_CD, lc.condition_cd, 
    SHD.INIT_FUP_INITIAL_FOLL_UP_CD, lmr.Observation_key, SLI.INVESTIGATION_KEY, lmr.Type
	FROM LAB_MORB_REACTOR lmr left outer join  
	nbs_rdb.D_STD_LAB_INV SLI on lmr.Observation_uid= SLI.observation_uid
    LEFT OUTER JOIN nbs_rdb.STD_HIV_DATAMART SHD ON
    SHD.INVESTIGATION_KEY=SLI.INVESTIGATION_KEY 
	left outer join nbs_srt.Loinc_condition lc on
	lmr.LAB_TEST_CD=lc.loinc_cd where lmr.Type='Lab'; 
quit;

Proc sql;
	create table ls2_lab_filter_cond as 	
	SELECT * from ls2_lab where condition_cd in ('10311','10312','10313','10314','10315','10316','10318','700'); 
quit;

Proc sql;
	create table ls2_lab_inv as SELECT distinct 
	lmr.LAB_TEST_CD, lmr.Observation_uid , lmr.PROCESSING_DECISION_CD,lmr.ALT_LAB_TEST_CD, lc.condition_cd, 
    SHD.INIT_FUP_INITIAL_FOLL_UP_CD, lmr.Observation_key, SLI.INVESTIGATION_KEY, lmr.Type
	FROM LAB_MORB_REACTOR lmr left outer join  
	nbs_rdb.D_STD_LAB_INV SLI on lmr.Observation_uid= SLI.observation_uid
    LEFT OUTER JOIN nbs_rdb.STD_HIV_DATAMART SHD ON
    SHD.INVESTIGATION_KEY=SLI.INVESTIGATION_KEY 
	left outer join nbs_srt.Loinc_condition lc on
	lmr.ALT_LAB_TEST_CD=lc.loinc_cd where lmr.Type='Lab'; 
quit;

Proc sql;
	create table ls2_lab_inv_filter_cond as 	
	SELECT * from ls2_lab_inv where condition_cd in ('10311','10312','10313','10314','10315','10316','10318','700'); 
quit;

Proc sql;
	create table ls2_final_lab_pd as 	
	SELECT * from ls2_lab_filter_cond
	union
    SELECT * from ls2_lab_inv_filter_cond;
quit;

data ls2_final_lab_pd;
SET ls2_final_lab_pd;
drop LAB_TEST_CD parent_test_pntr ALT_LAB_TEST_CD Observation_key INVESTIGATION_KEY;
RUN;

Proc sql;
    create table ls2_final_morb_pd as 
	select lmr.Observation_uid, cc.CONDITION_CD, shd.INIT_FUP_INITIAL_FOLL_UP_CD, lmr.Type
	from LAB_MORB_REACTOR lmr inner join nbs_rdb.MORBIDITY_REPORT_EVENT mre on
	lmr.Observation_key=mre.MORB_RPT_KEY
	inner join nbs_rdb.CONDITION cc on
	mre.CONDITION_KEY=cc.CONDITION_KEY
	and condition_cd in ('10311','10312','10313','10314','10315','10316','10318','700')
	left outer join nbs_rdb.STD_HIV_DATAMART shd on
	shd.INVESTIGATION_KEY=mre.INVESTIGATION_KEY;
quit;

Proc sql;
	create table ls2_final_pd as 	
	SELECT * from ls2_final_lab_pd
	union
    SELECT * from ls2_final_morb_pd;
quit;

data ls2_final_pd;
SET ls2_final_pd;
    if PROCESSING_DECISION_CD='AC' then DO; PROCESSING_DECISION_CD_NEW='Administrative Closure';end;
	if PROCESSING_DECISION_CD='BFP' then DO; PROCESSING_DECISION_CD_NEW='BFP - No Follow-up';end;
	if PROCESSING_DECISION_CD='FF' then DO; PROCESSING_DECISION_CD_NEW='Field Follow-up';end;
	if PROCESSING_DECISION_CD='II' then DO; PROCESSING_DECISION_CD_NEW='Insufficient Info';end;
	if PROCESSING_DECISION_CD='OOJ' then DO; PROCESSING_DECISION_CD_NEW='Send OOJ';end;
	if PROCESSING_DECISION_CD='PC' then DO; PROCESSING_DECISION_CD_NEW='Physician Closure';end;
	if PROCESSING_DECISION_CD='RSC' then DO; PROCESSING_DECISION_CD_NEW='Record Search Closure';end;
	if PROCESSING_DECISION_CD='SF' then DO; PROCESSING_DECISION_CD_NEW='Surveillance Follow-up';end;
	if PROCESSING_DECISION_CD='' then DO; PROCESSING_DECISION_CD_NEW=INIT_FUP_INITIAL_FOLL_UP_CD;end;
RUN;

Proc sql;
	create table ls2_reactor as 	
	SELECT PROCESSING_DECISION_CD_NEW, 1 as count 'count', 'T1/T2' as Reactor 'Reactor' from ls2_final_pd;
quit;

Proc sql;
	create table ls2_Reactor_FFU as
	select FL_FUP_DISPOSITION_DESC, con.condition_cd, DIAGNOSIS_CD, 1 as count 'count'
	from nbs_rdb.STD_HIV_DATAMART shd 
    inner join NBS_RDB.D_STD_LAB_INV sli on
    shd.INVESTIGATION_KEY = sli.INVESTIGATION_KEY
	inner join LAB_MORB_REACTOR lmr on
    sli.observation_uid =lmr.observation_uid
    inner join nbs_rdb.CASE_COUNT cc on 
	shd.INVESTIGATION_KEY= cc.INVESTIGATION_KEY 
	inner join nbs_rdb.condition con on
	con.condition_key=cc.condition_key
	where con.condition_cd in ('10311','10312','10313','10314','10315','10316','10318','700') and INIT_FUP_INITIAL_FOLL_UP_CD='Field Follow-up';
quit;

data ls2_Reactor_FFU;
SET ls2_Reactor_FFU;
    if FL_FUP_DISPOSITION_DESC='A - Preventative Treatment' then DO; TYPE='New Field Records Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='B - Refused Preventative Treatment' then DO; TYPE='New Field Records Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='C - Infected, Brought to Treatment' then DO; TYPE='New Field Records Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='D - Infected, Not Treated' then DO; TYPE='New Field Records Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='F - Not Infected' then DO; TYPE='New Field Records Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='G - Insufficient Info to Begin Investigation' then DO; TYPE='New Field Records Not Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='H - Unable to Locate' then DO; TYPE='New Field Records Not Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='J - Located, Not Examined and/or Interviewed' then DO; TYPE='New Field Records Not Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='K - Sent Out Of Jurisdiction' then DO; TYPE='New Field Records Not Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='L - Other' then DO; TYPE='New Field Records Not Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='Q - Administrative Closure' then DO; TYPE='New Field Records Not Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='V - Domestic Violence Risk' then DO; TYPE='New Field Records Not Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='X - Patient Deceased' then DO; TYPE='New Field Records Not Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='Z - Previous Preventative Treatment' then DO; TYPE='New Field Records Not Exam''d';end;
	if FL_FUP_DISPOSITION_DESC='' then DO; TYPE='Open (Not Dispo''d)';end;
	if FL_FUP_DISPOSITION_DESC='E - Previously Treated for This Infection' then DO; TYPE='Previously Rx';end;

	if (DIAGNOSIS_CD='710' or DIAGNOSIS_CD='720') then DO; DIAGNOSIS='Primary & Secondary';end;
	if DIAGNOSIS_CD='730' then DO; DIAGNOSIS='Early Laatent' ;end;
	if DIAGNOSIS_CD='740' then DO; DIAGNOSIS='Unknown Duration' ;end;
	if DIAGNOSIS_CD='745' then DO; DIAGNOSIS='Late Latent' ;end;
	if DIAGNOSIS_CD='750' then DO; DIAGNOSIS='Late w/Clinical Manif.' ;end;
	if DIAGNOSIS_CD='790' then DO; DIAGNOSIS='Congenital Syphilis' ;end;
	drop DIAGNOSIS_CD;
RUN;

Proc sql;
	create table ls2_Reactor_FFU_1 as
	select FL_FUP_DISPOSITION_DESC, condition_cd, DIAGNOSIS, 1 as count 'count'
	from ls2_Reactor_FFU
	where DIAGNOSIS~= '';
quit;

proc format;
   value $disfmt 'A - Preventative Treatment'='A - Preventative Treatment'
				 'B - Refused Preventative Treatment'='B - Refused Preventative Treatment'
				 'C - Infected, Brought to Treatment'='C - Infected, Brought to Treatment'
				 'D - Infected, Not Treated'='D - Infected, Not Treated'
				 'F - Not Infected'='F - Not Infected'
				 'E - Previously Treated for This Infection'='E - Previously Treated for This Infection'
				 'G - Insufficient Info to Begin Investigation'='G - Insufficient Info to Begin Investigation'
				 'H - Unable to Locate'='H - Unable to Locate'
				 'J - Located, Not Examined and/or Interviewed'='J - Located, Not Examined and/or Interviewed'
				 'K - Sent Out Of Jurisdiction'='K - Sent Out Of Jurisdiction'
				 'L - Other'='L - Other'
				 'Q - Administrative Closure'='Q - Administrative Closure'
				 'V - Domestic Violence Risk'='V - Domestic Violence Risk'
				 'X - Patient Deceased'='X - Patient Deceased'
				 'Z - Previous Preventative Treatment'='Z - Previous Preventative Treatment';
   value $examfmt 'New Field Records Exam''d'='New Field Records Exam''d'
                 'New Field Records Not Exam''d'='New Field Records Not Exam''d'
				 'Open (Not Dispo''d)'='Open (Not Dispo''d)'
				 'Previously Rx'='Previously Rx';
   value $dispofmt 'Primary & Secondary'='Primary & Secondary'
                 'Early Latent'='Early Latent'
				 'Unknown Duration'='Unknown Duration'
				 'Late Latent'='Late Latent'
				 'Late w/Clinical Manif.'='Late w/Clinical Manif.'
				 'Congenital Syphilis'='Congenital Syphilis';
   picture pctfmt low-high='009.99%';
run;

%footnote;
title 'LS02 Syphilis Reactor Statistics'; 
%if %upcase(&exporttype)=REPORT %then %do;
ODS LISTING CLOSE;
ods html body=sock (no_bottom_matter);
ods text='.';
	proc tabulate MISSING data=ls2_reactor format=comma12.; 
     class PROCESSING_DECISION_CD_NEW; 
     var count; 
     table (PROCESSING_DECISION_CD_NEW='Reactor Received/Processed' ALL), 
	           count=''*(sum='Count' COLPCTSUM='Percentage'*f=pctfmt9.)
	           /rts=20 printmiss;
			   options missing = '0';
		title;
	run; 

	proc tabulate MISSING data=ls2_Reactor_FFU format=comma12.; 
	class TYPE FL_FUP_DISPOSITION_DESC /preloadfmt EXCLUSIVE; 
	var count; 
	table TYPE=''*FL_FUP_DISPOSITION_DESC='Reactor Field Follow-up', 
	       count=''*(sum='Count' COLPCTSUM='Percentage'*f=pctfmt9.)
	       /rts=20;
		   	options missing = '0';
		    format TYPE $examfmt.; 

		title;
	run; 

	proc tabulate MISSING data=ls2_Reactor_FFU_1 format=comma12.; 
     class DIAGNOSIS /preloadfmt EXCLUSIVE;
     var count; 
     table (DIAGNOSIS='Reactor Received/Processed' ALL), 
	           count=''*(sum='Count' COLPCTSUM='Percentage'*f=pctfmt9.)
	           /rts=25 printmiss;
			   	options missing = '0';
			   format DIAGNOSIS $dispofmt.; 
		title;
	run;
        
   quit;
 ods html close;
%end;
%else 
      %export(work,ls2_Reactor_FFU,sock,&exporttype);
Title;
Footnote;
%finish:
%mend NBSSR00036;
%NBSSR00036;
