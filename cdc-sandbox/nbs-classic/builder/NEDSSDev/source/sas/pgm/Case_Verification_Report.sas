
%macro NBS_TB_CASE_VERIFICATION_REPORT;

%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;


PROC SQL;
CREATE TABLE TB_CASE_VER_REPORT_METATDATA AS 
SELECT QUESTION_IDENTIFIER, RDB_COLUMN_NM, RDB_TABLE_NM, USER_DEFINED_COLUMN_NM,DATAMART_NM 
FROM NBS_ODS.NBS_UI_METADATA INNER JOIN NBS_ODS.NBS_RDB_METADATA
ON NBS_UI_METADATA.NBS_UI_METADATA_UID=NBS_RDB_METADATA.NBS_UI_METADATA_UID
INNER JOIN NBS_SRT.CONDITION_CODE ON CONDITION_CODE.INVESTIGATION_FORM_CD=NBS_UI_METADATA.INVESTIGATION_FORM_CD
INNER JOIN NBS_ODS.NBS_PAGE ON NBS_PAGE.FORM_CD=CONDITION_CODE.INVESTIGATION_FORM_CD
WHERE QUESTION_IDENTIFIER IN ('INV1115' , 'INV1133', 'INV111') AND CONDITION_CD IN ('102201');
QUIT;



DATA TB_CASE_VER_REPORT_METATDATA2;
SET TB_CASE_VER_REPORT_METATDATA;
 if QUESTION_IDENTIFIER='INV1133' then call symputx('DISEASE_SITE_DESC', COMPRESS(USER_DEFINED_COLUMN_NM)); 
 if QUESTION_IDENTIFIER='INV1115' then call symputx('CASE_VERIFICATION_DESC', COMPRESS(USER_DEFINED_COLUMN_NM));
 if QUESTION_IDENTIFIER='INV111' then call symputx('INV_RPT_DT', COMPRESS(USER_DEFINED_COLUMN_NM));
 
RUN;


Proc SQL noprint;
	create table TB_CASE_VER 
	AS SELECT &DISEASE_SITE_DESC  AS DISEASE_SITE_DESC 'DISEASE_SITE_DESC', &CASE_VERIFICATION_DESC  AS CASE_VERIFICATION_DESC 'CASE_VERIFICATION_DESC', INVESTIGATION_KEY
from &DataSourceName WHERE DISEASE_CD ='102201' and not missing(&INV_RPT_DT) ;
quit;



PROC SQL;
CREATE TABLE CASE_VERIFIC_CODED
AS SELECT CODE_VALUE_GENERAL.CODE AS CASE_VERIFICATION_CODE, CODE_VALUE_GENERAL.CODE_SHORT_DESC_TXT AS CASE_VERIFICATION_CODE_DESC
FROM NBS_ODS.NBS_UI_METADATA INNER JOIN NBS_ODS.NBS_RDB_METADATA  
ON NBS_UI_METADATA.NBS_UI_METADATA_UID=NBS_RDB_METADATA.NBS_UI_METADATA_UID
INNER JOIN NBS_SRT.CONDITION_CODE ON CONDITION_CODE.INVESTIGATION_FORM_CD=NBS_UI_METADATA.INVESTIGATION_FORM_CD
INNER JOIN NBS_SRT.CODESET ON  CODESET.CODE_SET_GROUP_ID=NBS_UI_METADATA.CODE_SET_GROUP_ID
INNER JOIN NBS_SRT.CODE_VALUE_GENERAL ON
CODESET.CODE_SET_NM=CODE_VALUE_GENERAL.CODE_SET_NM
WHERE QUESTION_IDENTIFIER IN ('INV1115') AND CONDITION_CD IN ('102201');
QUIT;

PROC SQL;
CREATE TABLE DISIEASE_SITE_CODED
AS SELECT CODE_VALUE_GENERAL.CODE as DISEASE_CODE , CODE_VALUE_GENERAL.CODE_SHORT_DESC_TXT AS DISEASE_CODE_DESC
FROM NBS_ODS.NBS_UI_METADATA INNER JOIN NBS_ODS.NBS_RDB_METADATA  
ON NBS_UI_METADATA.NBS_UI_METADATA_UID=NBS_RDB_METADATA.NBS_UI_METADATA_UID
INNER JOIN NBS_SRT.CONDITION_CODE ON CONDITION_CODE.INVESTIGATION_FORM_CD=NBS_UI_METADATA.INVESTIGATION_FORM_CD
INNER JOIN NBS_SRT.CODESET ON  CODESET.CODE_SET_GROUP_ID=NBS_UI_METADATA.CODE_SET_GROUP_ID
INNER JOIN NBS_SRT.CODE_VALUE_GENERAL ON
CODESET.CODE_SET_NM=CODE_VALUE_GENERAL.CODE_SET_NM
WHERE QUESTION_IDENTIFIER IN ('INV1133') AND CONDITION_CD IN ('102201');
QUIT;


DATA TB_CASE_VER;
SET TB_CASE_VER;
IF MISSING(DISEASE_SITE_DESC) THEN DISEASE_SITE_DESC= 'Unknown';
IF DISEASE_SITE_DESC='' THEN DISEASE_SITE_DESC= 'Unknown';

RUN;
data TB_CASE_VER_INIT2 (keep=DISEASE_SITE_DESC CASE_VERIFICATION_DESC DISEASE_SITE_IND INVESTIGATION_KEY);
  set TB_CASE_VER;
  length DISEASE_SITE_IND $100;
  i=1;
  do while (scan(DISEASE_SITE_DESC,i,"|") ne "");
    DISEASE_SITE_IND=scan(DISEASE_SITE_DESC,i,"|");
    i+1;
    output;
  end;
run;
Proc SQL noprint;
	CREATE TABLE TB_CASE_VER3 AS SELECT COMPRESS(DISEASE_CODE) AS DISEASE_CODE 'DISEASE_CODE', TRIM(CASE_VERIFICATION_CODE) AS CASE_VERIFICATION_CODE 'CASE_VERIFICATION_CODE',
TB_CASE_VER_INIT2.CASE_VERIFICATION_DESC, 
TB_CASE_VER_INIT2.DISEASE_SITE_DESC, TRIM(DISEASE_SITE_IND), INVESTIGATION_KEY
FROM TB_CASE_VER_INIT2 LEFT JOIN CASE_VERIFIC_CODED ON CASE_VERIFIC_CODED.CASE_VERIFICATION_CODE_DESC=TB_CASE_VER_INIT2.CASE_VERIFICATION_DESC
LEFT JOIN DISIEASE_SITE_CODED ON COMPRESS(DISIEASE_SITE_CODED.DISEASE_CODE_DESC)=COMPRESS(TB_CASE_VER_INIT2.DISEASE_SITE_IND) order by INVESTIGATION_KEY;
quit;

PROC SQL;
ALTER TABLE TB_CASE_VER3 ADD   DISEASE_SITE_VALUE VARCHAR (400);
UPDATE TB_CASE_VER3 SET DISEASE_SITE_VALUE='Pulmonary TB' WHERE DISEASE_CODE IN ('281778006', '39607008', '3120008') ;
UPDATE TB_CASE_VER3 SET DISEASE_SITE_VALUE='Extrapulmonary TB' WHERE DISEASE_CODE IN ('23451007', '362102006', '53505006', '66754008', '87612001', '59820001', '110522009', '14016003',
'12738006', '76752008', '17401000', '71854001', '38848004', '110547006', '32849002', '16014003', 'PHC4', 'C0230999', '28231008', '80891009', '110611003', '48477009', '10200004', 'PHC2', 
'PHC3', '1231004', '110708006', '123851003', '45206002', '71836000', 'OTH', '15776009', '120228005', '76848001', '83670000', '54066008', '56329008', 
'110973009', '34402009', '385294005', '39937001', '2748008', '78961009', '69695003', '21514008', '281777001', '69831007', '25087005', '71966008', '9875009', '297261005', 
'21974007', '303337002', '44567001');
UPDATE TB_CASE_VER3 SET DISEASE_SITE_VALUE='Unknown' WHERE DISEASE_CODE IN ('PHC5') ;
UPDATE TB_CASE_VER3 SET DISEASE_SITE_VALUE='Unknown' WHERE DISEASE_CODE IS NULL;
QUIT;


DATA TB_CASE_VER4;
SET TB_CASE_VER3;
LENGTH DISEASE_SITE $4000;
LENGTH CASE_VERIFICATION_STAUS $4000;
 
DO UNTIL(LAST.INVESTIGATION_KEY);
	SET TB_CASE_VER3;
	BY INVESTIGATION_KEY NOTSORTED;
IF DISEASE_SITE='' THEN  DISEASE_SITE=DISEASE_SITE_VALUE;
ELSE IF DISEASE_SITE='Pulmonary TB' AND DISEASE_SITE_VALUE='Pulmonary TB' THEN DISEASE_SITE='Pulmonary TB';
ELSE IF DISEASE_SITE='Extrapulmonary TB' AND DISEASE_SITE_VALUE='Extrapulmonary TB' THEN DISEASE_SITE='Extrapulmonary TB';
ELSE IF DISEASE_SITE='Extrapulmonary TB' AND DISEASE_SITE_VALUE='Pulmonary TB' THEN DISEASE_SITE='Both';
ELSE IF DISEASE_SITE='Pulmonary TB' AND DISEASE_SITE_VALUE='Extrapulmonary TB' THEN DISEASE_SITE='Both';
ELSE IF DISEASE_SITE='Unknown' AND DISEASE_SITE_VALUE='Pulmonary TB' THEN DISEASE_SITE='Pulmonary TB';
ELSE IF DISEASE_SITE='Unknown' AND DISEASE_SITE_VALUE='Extrapulmonary TB' THEN DISEASE_SITE='Extrapulmonary TB';

ELSE IF DISEASE_SITE='Both' AND DISEASE_SITE_VALUE='' THEN DISEASE_SITE='Both';
ELSE IF DISEASE_SITE='' AND DISEASE_SITE_VALUE='' THEN DISEASE_SITE='Unknown';

END;
RUN;





proc tabulate  data=TB_CASE_VER4 format=comma9. out=TB_CASE_VER_01;
	class  CASE_VERIFICATION_CODE DISEASE_SITE / PRELOADFMT ;
	TABLE CASE_VERIFICATION_CODE='' ALL='Total', DISEASE_SITE='' ALL='All Cases';
run;

data TB_CASE_VER_02;
	retain CASE_VERIFICATION_CODE DISEASE_SITE counts;
	set TB_CASE_VER_01;
	if _TYPE_=11 then do;
		counts=N;
	end;
	else DELETE;
run;
data TB_CASE_VER_03;
	set TB_CASE_VER_02;
	drop _TYPE_ _PAGE_ _TABLE_ N;
run;

proc format ;
    picture pctfmt low-high = '009%';

proc format;
  value $typ(notsorted default=60) 
	'Pulmonary' = 'Pulmonary TB'
	'Extra'='Extrapulmonary TB'
	'Both'='Both'
	'Unknown'='Unknown';
RUN;

proc format;
 value $case(default=40) 
'PHC162' ='0 - Not a Verified Case'
'PHC97'  ='1 - Positive Culture'
'PHC653'  ='1A - Positive NAA'
'PHC98'  ='2 - Positive Smear/Tissue'
'PHC654'  ='3 - Clinical Case Definition'
'PHC165'  ='4 - Provider Diagnosis'
'415684004'  ='5 - Suspect';



RUN;

proc sql noprint;                                                          
   select sum(counts)                                                      
   into :total                                                             
   from TB_CASE_VER_03;                                                           
   quit;                                                                   

%put &total;                                                               

proc format;                                                                                             
   value missf                                                                                           
           .='0'                                                                                         
        0-<1=[percent8.1]                                                                                
      99999='100.0%'                                                                                     
        9999='0.0%';                                                                                     
                                                                                                         
   value misspctf                                                                                        
          .='0.0%'                                                                                       
       9999=' '                                                                                          
     other=[percent8.1];                                                                                 
run;                                                                        
                                                                           
data temp;                                                                 
   set TB_CASE_VER_03;                                                            
   flag=1;                                                                 
run;  
ods escapechar='^'; 

%footnote;
Footnote;

Footnote1 h=8pt "Selected Filters: << &footer >>";
Footnote2 j=C h=8pt f= Calibri "Report run on: &rptdate";
Footnote3 j=C h=8pt f= Calibri "Data Refreshed on: &update";


%if %upcase(&exporttype)=REPORT %then %do;
 ods html body = sock(no_bottom_matter) stylesheet=(URL="nbsreport.css");
  proc report data=temp nowd headline headskip completerows colwidth=30;                                               
   col flag CASE_VERIFICATION_CODE DISEASE_SITE, (counts counts=counts2)                                 
        ('All Cases' totnum totpct);                                                                     
   
   title BOLD HEIGHT=5 FONT=Times <p align="center"> 'TB Case Verification Report - 2020 RVCT'</p>;                                                                                                          
   define CASE_VERIFICATION_CODE / group width=25 ' ' format=$case40. preloadfmt style(column)=[cellwidth=2in];                             
   define flag              / group noprint style(column)=[cellwidth=3in];                                                            
   define DISEASE_SITE / across width=15 ' ' format=$typ60. preloadfmt order=data style(column)=[cellwidth=2in];                  
   define counts            / sum 'No.' format=missf8. style(column)=[cellwidth=1in];                                                  
   define counts2           / pctsum '%' format=misspctf. style(column)=[cellwidth=1in];                                               
   define totnum            / computed 'No.' format=missf. style(column)=[cellwidth=1in];                                              
   define totpct            / computed '%' format=misspctf. style(column)=[cellwidth=1in];                                             
                                                                                                         
   compute totnum;                                                                                       
      totnum=sum(_c3_,_c5_,_c7_,_c9_);   
	  call define(_col_,"style","style=[font_weight=bold]");   
   endcomp;                                                                                              
                                                                                                         
   compute totpct;                                                                                       
      totpct=totnum/&total;                                    
	  call define(_col_,"style","style=[font_weight=bold]");    
   endcomp;                                                                                              
                                                                                                         
   compute after;                                                                                        
      _c4_=9999;  _c6_=9999; _c8_=9999; _c10_=9999; totpct=9999;                                         
       CASE_VERIFICATION_CODE='% of All Cases';                                                               
      _c3_=_c3_/&total; if _C3_=. then _C3_=9999;                                                        
      _c5_=_c5_/&total; if _C5_=. then _C5_=9999;                                                        
      _c7_=_c7_/&total; if _C7_=. then _C7_=9999;                                                        
      _c9_=_c9_/&total; if _C9_=. then _C9_=9999;                                                        
      totnum=99999;                                                                                      
                                                                                                         
      call define(_row_,"style","style=[font_weight=bold]");                                             
   endcomp;                                                                                              
                                                                                                         
   compute after flag;                                                                                   
      _c4_=9999;  _c6_=9999;  _c8_=9999; _c10_=9999; totpct=9999;                                        
      CASE_VERIFICATION_CODE='Total';                                                                         
      totnum=&total;                                                                                     
                                                                                                         
      call define(_row_,"style","style=[font_weight=bold]");                                             
   endcomp;                                                                                              
                                                                                                         
   break after flag / dol summarize;;                                                                    
                                                                                                         
   rbreak after / summarize;                                                                             
run;                                                                                                     

quit;
ods html close;
%end;
%else 
      %export(work,TB_CASE_VER3,sock,&exporttype);
Title;
%finish:
%mend NBS_TB_CASE_VERIFICATION_REPORT;
%NBS_TB_CASE_VERIFICATION_REPORT;

