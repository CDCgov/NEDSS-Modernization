%macro NBSSR00018;

%chk_mv;
%if  %upcase(&skip)=YES %then
      %goto finish;

data sr00018;
	set work.TB_DATAMART;
run;

Proc SQL noprint;
	create table SR18 as select CASE_VERIFICATION, CALC_DISEASE_SITE from sr00018;
quit;

proc tabulate  data=SR18 format=comma9. out=SR18_01;
	class CASE_VERIFICATION CALC_DISEASE_SITE / PRELOADFMT ;
	TABLE CASE_VERIFICATION='' ALL='Total', CALC_DISEASE_SITE='' ALL='All Cases';
run;

data SR18_02;
	retain CASE_VERIFICATION CALC_DISEASE_SITE counts;
	set SR18_01;
	if _TYPE_=11 then do;
		counts=N;
	end;
	else DELETE;
run;
data SR18_03;
	set SR18_02;
	drop _TYPE_ _PAGE_ _TABLE_ N;
run;

proc format ;
    picture pctfmt low-high = '009%';

proc format;
  value $typ(notsorted default=40) 
	'Pulmonary' = 'Pulmonary TB'
	'Extra Pulmonary'='Extrapulmonary TB'
	'Both'='Both'
	'Unknown'='Unknown';
RUN;

proc format;
  value $case (default=40) 
	'0 - Not a Verified Case' = '0 - Not a Verified Case'
	'1 - Positive Culture'='1 - Positive Culture'
	'1A - Positive NAA'='1A - Positive NAA'
	'2 - Positive Smear/Tissue'='2 - Positive Smear'
	'3 - Clinical Case Definition'='3 - Clinical Case Definition'
	'4 - Verified by Provider Diagnosis'='4 - Provider Diagnosis'
	'5 - Suspect'='5 - Suspect Case';
RUN;

proc sql noprint;                                                          
   select sum(counts)                                                      
   into :total                                                             
   from sr18_03;                                                           
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
   set sr18_03;                                                            
   flag=1;                                                                 
run;  
ods escapechar='^'; 

%footnote;
Footnote;

Footnote1 h=8pt "Selected Filters: << &footer >>";
Footnote2 h=8pt "Report run on: &rptdate";
*Footnote3 h=8pt "Data refreshed on: &update"; 

%if %upcase(&exporttype)=REPORT %then %do;
 ods html body = sock(no_bottom_matter) stylesheet=(URL="nbsreport.css");
  proc report data=temp nowd headline headskip completerows;                                               
   col flag CASE_VERIFICATION CALC_DISEASE_SITE, (counts counts=counts2)                                 
        ('All Cases' totnum totpct);                                                                     
   
   title BOLD HEIGHT=5 FONT=Times <p align="center"> 'TB Case Verification Report'</p>;                                                                                                          
   define CASE_VERIFICATION / group width=25 ' ' format=$case40. preloadfmt;                             
   define flag              / group noprint ;                                                            
   define CALC_DISEASE_SITE / across width=15 ' ' format=$typ40. preloadfmt order=data;                  
   define counts            / sum 'No.' format=missf8.;                                                  
   define counts2           / pctsum '%' format=misspctf.;                                               
   define totnum            / computed 'No.' format=missf.;                                              
   define totpct            / computed '%' format=misspctf.;                                             
                                                                                                         
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
       CASE_VERIFICATION='% of All Cases';                                                               
      _c3_=_c3_/&total; if _C3_=. then _C3_=9999;                                                        
      _c5_=_c5_/&total; if _C5_=. then _C5_=9999;                                                        
      _c7_=_c7_/&total; if _C7_=. then _C7_=9999;                                                        
      _c9_=_c9_/&total; if _C9_=. then _C9_=9999;                                                        
      totnum=99999;                                                                                      
                                                                                                         
      call define(_row_,"style","style=[font_weight=bold]");                                             
   endcomp;                                                                                              
                                                                                                         
   compute after flag;                                                                                   
      _c4_=9999;  _c6_=9999;  _c8_=9999; _c10_=9999; totpct=9999;                                        
      CASE_VERIFICATION='Total';                                                                         
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
      %export(work,sr18,sock,&exporttype);
Title;
%finish:
%mend NBSSR00018;
%NBSSR00018;

