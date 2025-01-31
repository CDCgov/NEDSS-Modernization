%macro NBSSR00038;
%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;


proc sql;
create table init_key as select investigation_key from LAB_MORB_REACTOR
inner join nbs_rdb.D_STD_LAB_INV on 
D_STD_LAB_INV.observation_uid =LAB_MORB_REACTOR.observation_uid;

CREATE TABLE init_data AS select CLN_NONTREP_SYPH_RSLT_QNT, FL_FUP_DISPOSITION_DESC, CURR_PROCESS_STATE, INIT_FOLL_UP, DIAGNOSIS 
from init_key, nbs_rdb.STD_HIV_DATAMART
where 
STD_HIV_DATAMART.investigation_key =init_key.investigation_key and DIAGNOSIS in ('710 - Syphilis, primary','720 - Syphilis, secondary',
'730 - Syphilis, early latent','740 - Syphilis, unknown duration','745 - Syphilis, late latent','750 - Syphilis, late w/ symptom',
'790 – Syphilis, congenital') ;

quit;

data init_data;
set init_data;
IF LENGTHN(TRIM(CLN_NONTREP_SYPH_RSLT_QNT))=0 THEN CLN_NONTREP_SYPH_RSLT_QNT='MISSING';
IF TRIM(CLN_NONTREP_SYPH_RSLT_QNT)='unknown' THEN CLN_NONTREP_SYPH_RSLT_QNT='MISSING';
counter =1;
run;
proc format;
   value $follfmt 	'PC'='PMD Close'
				 	'SC'='Open SurvLog'
				 	'FF'='Field Invest.'
				 	'90213003'='Bio. False Positive'
					'II'='Bio. False Positive'
					'RSC'='Bio. False Positive'
					'PHC54'='Bio. False Positive'
					'NPP'='Bio. False Positive'
					'PHC149'='Admin Closures'
		             other='Misc Value';

	value $dispofmt	'A - Preventative Treatment'='A'
					'B - Refused Preventative Treatment'='B'
					'C - Infected, Brought to Treatment'='C'
					'D - Infected, Not Treated'='D'
					'E - Previously Treated for This Infection'='E'
					'F - Not Infected'='F'
					'G - Insufficient Info to Begin Investigation'='G'
					'H - Unable to Locate'='H'
					'J - Located, Not Examined and/or Interviewed'='J'
					'K - Sent Out Of Jurisdiction'='K'
					'L - Other'='L'
					'Q - Administrative Closure'='Q'
					'V - Domestic Violence Risk'='V'
					'X - Patient Deceased'='X'
					'Z - Previous Preventative Treatment'='Z'
					/*=	'1 - Prev. Pos'
					=	'2 - Prev. Neg, New Pos'
					=	'3 - Prev. Neg, Still Neg'
					=	'4 - Prev. Neg, No Test'
					=	'5 - No Prev Test, New Pos'
					=	'6 - No Prev Test, New Neg'
					=	'7 - No Prev Test, No Test'*/
				;
	value $diagnfmt '710'='Dx 710'
					'720'= 'Dx 720'
					'730'='Dx 730'
					'740'='Dx 740'
					'745'='Dx 745'
					'750'='Dx 750'
					'790'='Dx 790'
					;
run;
data class;
length DIAGNOSIS $4000;
length FL_FUP_DISPOSITION_DESC $44;
LENGTH INIT_FOLL_UP $20;
DO INIT_FOLL_UP='PHC149', 'PC', 'SC','FF','90213003', 'II', 'RSC','PHC54','NPP';
OUTPUT;
DO FL_FUP_DISPOSITION_DESC='A', 'B','C','D','E','F','G','H','J','K','L','Q','V','X','Z';
OUTPUT;
DO DIAGNOSIS='Dx 710', 'Dx 720','Dx 730','Dx 730','Dx 745','Dx 750','Dx 790';
OUTPUT;
end;
END;
end;
RUN;
proc format;
value $difmt 
'710 - Syphilis, primary'='Dx 710'
'720 - Syphilis, secondary'='Dx720'
'730 - Syphilis, early latent'='Dx730'
'740 - Syphilis, unknown duration'='Dx740'
'745 - Syphilis, late latent'='745'
'750 - Syphilis, late w/ symptom'='750'
'790 – Syphilis, congenital'='790';
value $follfmt  (multilabel notsorted)
				'PC'='2. PMD Close'
				 'SF'='3. Open SurvLog'
				 'FF'='4. Field Invest.'
				 '90213003'='5. Bio. False Positive'
				'II'='6. No Action'
				'RSC'='6. No Action'
				'PHC54'='6. No Action'
				'NPP'='6. No Action'
				'PHC149'='1. Admin Closures'
             other='Misc Value';

run;



%footnote;
title 'SYPHILIS REACTOR GRID EVALUATION'; 
%if %upcase(&exporttype)=REPORT %then %do;
ODS LISTING CLOSE;
ods html body=sock (no_bottom_matter);
ods text='.';
	
PROC TABULATE DATA=init_data  S=[cellwidth=250 just=c]  missing  ;
 CLASS INIT_FOLL_UP CLN_NONTREP_SYPH_RSLT_QNT counter /PRELOADFMT order=fmt;
 TABLE CLN_NONTREP_SYPH_RSLT_QNT='' ALL='Total', INIT_FOLL_UP='' counter=''*N='Total'   /printmiss MISSTEXT='0';
 format INIT_FOLL_UP $follfmt.;
 title 'OUTCOME BY TITER';
RUN; 

PROC TABULATE DATA=init_data  S=[cellwidth=250 just=c]  missing;
 CLASS FL_FUP_DISPOSITION_DESC CLN_NONTREP_SYPH_RSLT_QNT counter /PRELOADFMT ;
 TABLE CLN_NONTREP_SYPH_RSLT_QNT='' ALL='Total',FL_FUP_DISPOSITION_DESC='' counter=''*N='Total'   /printmiss MISSTEXT='0';
 format FL_FUP_DISPOSITION_DESC $dispofmt.;
 title 'FIELD INVESTIGATIONS RESULTS';
RUN;


PROC TABULATE DATA=init_data S=[cellwidth=250 just=c]  missing;
 CLASS DIAGNOSIS CLN_NONTREP_SYPH_RSLT_QNT counter /PRELOADFMT;
 TABLE CLN_NONTREP_SYPH_RSLT_QNT='' ALL='Total', DIAGNOSIS=''  counter=''*N='Total' / printmiss MISSTEXT='0';
 format DIAGNOSIS $difmt.;
 title 'FIELD INVESTIGATIONS – CASE DIAGNOSES';
RUN; 


        
   quit;
 ods html close;
%end;
%else 
      %export(work,init_data,sock,&exporttype);
Title;
Footnote;
%finish:
%mend NBSSR00038;
%NBSSR00038;
