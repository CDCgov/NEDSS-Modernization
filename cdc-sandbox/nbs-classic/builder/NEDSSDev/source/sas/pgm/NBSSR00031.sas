%macro NBSSR00031;
%chk_mv;

%if  %upcase(&skip)=YES %then
      %goto finish;

Proc sql;
	create table pa2_init as select
	INVESTIGATOR_QUICK_CODE,
	INVESTIGATOR_NAME,
	REFERRAL_BASIS,
	FL_FUP_INIT_ASSGN_DT,
	FL_FUP_DISPO_DT,
	FL_FUP_DISPOSITION_DESC,
	1 AS count
	from STD_HIV_DATAMART;
quit;
Proc sql;
	create table pa2_exp as select
	INVESTIGATOR_QUICK_CODE,
	INVESTIGATOR_NAME,
	REFERRAL_BASIS,
	FL_FUP_INIT_ASSGN_DT,
	FL_FUP_DISPO_DT,
	FL_FUP_DISPOSITION_DESC
	from STD_HIV_DATAMART;
quit;

data pa2_init;
SET pa2_init;
	startDate=datepart(FL_FUP_DISPO_DT);
   	finalDate=datepart(FL_FUP_INIT_ASSGN_DT);
    disposition_days=(startDate-finalDate); 

    LENGTH WORKER $78;
	len=lengthn(INVESTIGATOR_QUICK_CODE);
	if(len>1) then WORKER=trim(INVESTIGATOR_QUICK_CODE)||"-"||trim(INVESTIGATOR_NAME);
	else WORKER=INVESTIGATOR_NAME;

	if(INVESTIGATOR_NAME)=', ' then WORKER='NA';

	exam=0;
	if(FL_FUP_DISPOSITION_DESC='A - Preventative Treatment' or FL_FUP_DISPOSITION_DESC='B - Refused Preventative Treatment' or FL_FUP_DISPOSITION_DESC='C - Infected, Brought to Treatment' or FL_FUP_DISPOSITION_DESC='D - Infected, Not Treated' or FL_FUP_DISPOSITION_DESC='F - Not Infected' or FL_FUP_DISPOSITION_DESC='2 - Prev. Neg, New Pos' or FL_FUP_DISPOSITION_DESC='3 - Prev. Neg, Still Neg' or FL_FUP_DISPOSITION_DESC='4 - Prev. Neg, No Test' or FL_FUP_DISPOSITION_DESC='5 - No Prev Test, New Pos' or FL_FUP_DISPOSITION_DESC='6 - No Prev Test, New Neg' or FL_FUP_DISPOSITION_DESC='7 - No Prev Test, No Test') then do; exam=0;end;
	if(disposition_days=0 or disposition_days<=3 and (FL_FUP_DISPOSITION_DESC='A - Preventative Treatment' or FL_FUP_DISPOSITION_DESC='B - Refused Preventative Treatment' or FL_FUP_DISPOSITION_DESC='C - Infected, Brought to Treatment' or FL_FUP_DISPOSITION_DESC='D - Infected, Not Treated' or FL_FUP_DISPOSITION_DESC='F - Not Infected' or FL_FUP_DISPOSITION_DESC='2 - Prev. Neg, New Pos' or FL_FUP_DISPOSITION_DESC='3 - Prev. Neg, Still Neg' or FL_FUP_DISPOSITION_DESC='4 - Prev. Neg, No Test' or FL_FUP_DISPOSITION_DESC='5 - No Prev Test, New Pos' or FL_FUP_DISPOSITION_DESC='6 - No Prev Test, New Neg' or FL_FUP_DISPOSITION_DESC='7 - No Prev Test, No Test')) then do; exam=1;end;
	if(disposition_days>3 and disposition_days<=5 and (FL_FUP_DISPOSITION_DESC='A - Preventative Treatment' or FL_FUP_DISPOSITION_DESC='B - Refused Preventative Treatment' or FL_FUP_DISPOSITION_DESC='C - Infected, Brought to Treatment' or FL_FUP_DISPOSITION_DESC='D - Infected, Not Treated' or FL_FUP_DISPOSITION_DESC='F - Not Infected' or FL_FUP_DISPOSITION_DESC='2 - Prev. Neg, New Pos' or FL_FUP_DISPOSITION_DESC='3 - Prev. Neg, Still Neg' or FL_FUP_DISPOSITION_DESC='4 - Prev. Neg, No Test' or FL_FUP_DISPOSITION_DESC='5 - No Prev Test, New Pos' or FL_FUP_DISPOSITION_DESC='6 - No Prev Test, New Neg' or FL_FUP_DISPOSITION_DESC='7 - No Prev Test, No Test')) then do; exam=2;end;
	if(disposition_days >5 and disposition_days<=7 and (FL_FUP_DISPOSITION_DESC='A - Preventative Treatment' or FL_FUP_DISPOSITION_DESC='B - Refused Preventative Treatment' or FL_FUP_DISPOSITION_DESC='C - Infected, Brought to Treatment' or FL_FUP_DISPOSITION_DESC='D - Infected, Not Treated' or FL_FUP_DISPOSITION_DESC='F - Not Infected' or FL_FUP_DISPOSITION_DESC='2 - Prev. Neg, New Pos' or FL_FUP_DISPOSITION_DESC='3 - Prev. Neg, Still Neg' or FL_FUP_DISPOSITION_DESC='4 - Prev. Neg, No Test' or FL_FUP_DISPOSITION_DESC='5 - No Prev Test, New Pos' or FL_FUP_DISPOSITION_DESC='6 - No Prev Test, New Neg' or FL_FUP_DISPOSITION_DESC='7 - No Prev Test, No Test')) then do; exam=3;end;
	if(disposition_days >7 and disposition_days<=14 and (FL_FUP_DISPOSITION_DESC='A - Preventative Treatment' or FL_FUP_DISPOSITION_DESC='B - Refused Preventative Treatment' or FL_FUP_DISPOSITION_DESC='C - Infected, Brought to Treatment' or FL_FUP_DISPOSITION_DESC='D - Infected, Not Treated' or FL_FUP_DISPOSITION_DESC='F - Not Infected' or FL_FUP_DISPOSITION_DESC='2 - Prev. Neg, New Pos' or FL_FUP_DISPOSITION_DESC='3 - Prev. Neg, Still Neg' or FL_FUP_DISPOSITION_DESC='4 - Prev. Neg, No Test' or FL_FUP_DISPOSITION_DESC='5 - No Prev Test, New Pos' or FL_FUP_DISPOSITION_DESC='6 - No Prev Test, New Neg' or FL_FUP_DISPOSITION_DESC='7 - No Prev Test, No Test')) then do; exam=4;end;
    if(startDate = null)then DO; exam=6;end;
	if(FL_FUP_DISPOSITION_DESC='G - Insufficient Information to Begin Investigation' or FL_FUP_DISPOSITION_DESC='H - Unable to Locate' or FL_FUP_DISPOSITION_DESC='J - Located, Not Examined, Treated, and/or Interview' or FL_FUP_DISPOSITION_DESC='Q - Administrative Closure' or FL_FUP_DISPOSITION_DESC='K - Sent Out Of Jurisdiction' or FL_FUP_DISPOSITION_DESC='L - Other' or FL_FUP_DISPOSITION_DESC='V - Domestic Violence Risk' or FL_FUP_DISPOSITION_DESC='X - Patient Deceased' or FL_FUP_DISPOSITION_DESC='K - Sent Out Of Jurisdiction' or FL_FUP_DISPOSITION_DESC='E - Previously Treated for This Infection') then do; exam=5;end;
    if(FL_FUP_DISPOSITION_DESC='E - Previously Treated for This Infection')then DO; exam=7;end;
RUN;

proc format;
   value examfmt 0='Exam''d'
				 1='Exam''d w/in 3'
                 2='Exam''d w/in 5'
                 3='Exam''d w/in 7'
                 4='Exam''d w/in 14'
				 5='Not Exam''d'
				 6='Open'
				 7='Dispo E';

   value $disfmt '1'='Dispo 1'
				 '2'='Dispo 2'
                 '3'='Dispo 3'
                 '4'='Dispo 4'
				 '5'='Dispo 5'
				 '6'='Dispo 6'
				 '7'='Dispo 7'
				 'A - Preventative Treatment'='Dispo A'
				 'B - Refused Preventative Treatment'='Dispo B'
				 'C - Infected, Brought to Treatment'='Dispo C'
				 'D - Infected, Not Treated'='Dispo D'
				 'F - Not Infected'='Dispo F'
				 'E - Previously Treated for This Infection'='Dispo E'
				 'G - Insufficient Info to Begin Investigation'='Dispo G'
				 'H - Unable to Locate'='Dispo H'
				 'J - Located, Not Examined and/or Interviewed'='Dispo K'
				 'K - Sent Out Of Jurisdiction'='Dispo K'
				 'L - Other'='Dispo L'
				 'Q - Administrative Closure'='Dispo Q'
				 'V - Domestic Violence Risk'='Dispo V'
				 'X - Patient Deceased'='Dispo X'
				 'Z - Previous Preventative Treatment'='Dispo Z';
   value $refType 'P1 - Partner, Sex'='Partner'
				  'P2 - Partner, Needle-Sharing'='Partner'
				  'P3 - Partner, Both'='Partner'
				  'S1 - Social Contact 1'='Cluster'
				  'S2 - Social Contact 2'='Cluster'
				  'S3 - Social Contact 3'='Cluster'	
				  'A1 - Associate 1'='Cluster'
				  'A2 - Associate 2'='Cluster'
				  'A3 - Associate 3'='Cluster'
				  'T1 - Positive Test'='Reactor'
				  'T2 - Morbidity Report'='Reactor'
				  other='Other';
run;

proc  sql noprint;
	Create table pa2
	as select FL_FUP_DISPOSITION_DESC as Disposition 'Disposition',
	      exam as Exam,
	      REFERRAL_BASIS,
	      WORKER,
	      count from pa2_init order by WORKER;
quit;
%footnote;
title1 'Filed Investigation Outcomes - STD'; 
title2 'Summary(All Workers)';
%if %upcase(&exporttype)=REPORT %then %do;
ODS LISTING CLOSE;
ods html body=sock (no_bottom_matter);
ods text = '.';
	proc tabulate data=pa2 format=comma12.; 
	     class Exam Disposition REFERRAL_BASIS; 
	     var count; 
	     table  Exam*Disposition
	         all='Total Assigned'*f=3., 
	           REFERRAL_BASIS='Referral Basis'*count=' '*sum=' '
	         all='Total'*count=' '*sum=' ' 
	        / rts=25; 
		 options missing = '0';
	     format Exam examfmt. Disposition $disfmt. REFERRAL_BASIS $refType.;
	run;
	title;
	Footnote;
	proc tabulate data=pa2 MISSING format=comma12.; 
	     class Exam Disposition REFERRAL_BASIS / MISSING EXCLUSIVE; 
	     var count; 
		 by WORKER;
	     table  Exam*Disposition
	         all='Total Assigned'*f=3., 
	         REFERRAL_BASIS='Referral Basis'*count=' '*sum=' '
	         all='Total'*count=' '*sum=' '
	        / rts=25 printmiss; 
		 options missing = '0';
	     format Exam examfmt. Disposition $disfmt. REFERRAL_BASIS $refType.; 
	run;
   quit;
 ods html close;
%end;
%else 
      %export(work,pa2_exp,sock,&exporttype);
Title;
Footnote;
%finish:
%mend NBSSR00031;
%NBSSR00031;
