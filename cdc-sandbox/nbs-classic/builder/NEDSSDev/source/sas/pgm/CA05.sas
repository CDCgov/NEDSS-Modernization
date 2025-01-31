/*Program Name : CA05.sas																													*/
/*																																				*/
/*Program Created by : SA																														*/
/*																																				*/
/*Program Created Date:	12-10-2016																												*/
/*																																				*/
/*Program Last Modified Date:	12-30-2016 - Formatted the pdf report output 																												*/
/*							:																													*/
/*							:																													*/
/*Program Description:	Creates CA05 Report: summary hangout  for NBS5.1 																														*/
/*																																				*/
/*Comments:	
*/

%Formats;

%macro CA05;

%if  %upcase(&skip)=YES %then
      %goto finish;

 data _null_;
 call symputx ('START_TIME',PUT(DATETIME(),15.));
 RUN;


proc format;
value $place
''='# Associated Cases:'
other = '# Associated Cases:'
;
value $combdx
'300','350' = '300'
'200' = '200'
'710' = '710'
'720' = '720'
'730' ='730'
'740'='740'
'745' ='745'
'750'='750'
'755'='755'
'790'='790'
'900'='900'
'950'='950'       
;
run;

proc sql buffersize=1M;
create table sumary as 
select distinct TRIM(PLACE_NAME) as Hang_Out_Name LABEL = 'HANGOUT NAME', TRIM(PLACE_TYPE_DESCRIPTION) as Type_of_place LABEL = 'TYPE',
DIAGNOSIS_CD as dx  label = 'Dx' format = $combdx., 
count(distinct INV_LOCAL_ID) as Count
from V_CHALK_TALK 
group by Hang_Out_Name, dx , Type_of_place
order by Hang_Out_Name, dx , Type_of_place
;quit;

%chk_mv;

options missing = 0 ; 
goptions device=actximg;

ODS _all_ CLOSE;
ods results;
OPTIONS orientation=portrait   NONUMBER NODATE LS=248 PS =256 COMPRESS=NO   MISSING = 0 
topmargin=.50in bottommargin=.50in leftmargin=.25in rightmargin=.25in  nobyline  papersize=a4;
ods escapechar='^'; 
%footnote;
Footnote;
Footnote1 j=L h=8pt f= Calibri "____________________________________________________________________________________________________________________________________";
Footnote2 j=C h=8pt f= Calibri "Report run on:&rptdate";
Footnote3 j=C h=8pt f= Calibri "Data Refreshed on: &update";
Footnote4 j=C h=8pt f= Calibri "This Report was built using the following criteria: &footer";
Footnote5 j=C h=8pt f= Calibri "Page ^{thispage} of ^{lastpage}";

%if %upcase(&exporttype)=REPORT %then %do;
options printerpath=pdf;
ods PDF body = sock notoc uniform;
TITLE1 bold f= Calibri h=14pt j=c "%UPCASE(&reportTitle)"		 	;
TITLE3  	" " ;
TITLE4      " ";


proc report data = sumary nowd ls =256  split='~' 
style(header)={just=center font_weight=bold font_face="Calibri"  font_size = 12pt};
*style(report)={font_face="Calibri"  font_size = 10pt rules=none frame=void};
column ('HANGOUT NAME' Hang_Out_Name) ('TYPE' Type_of_place)  dx,count 	;
define  Hang_Out_Name / group ' '  style(column)= { cellwidth=35mm  just=left font_face="Calibri" font_size = 10pt};
define  Type_of_place /group  ' '  style(column)= {cellwidth=30mm font_face="Calibri" font_size = 10pt};
define  dx / across ' ' format=$combdx. preloadfmt  style(column)={font_face="Calibri" just=center cellwidth=10mm vjust = middle font_size = 10pt };
define  count    / sum ' ' style(column)={font_face="Calibri" just=center cellwidth=10mm vjust=center font_size = 10pt};
break after  Hang_Out_Name / skip;
run;




	ods pdf close;
	/*delete work data */
	proc datasets kill lib = work nolist memtype=data;
	quit;

	 data _null_;
	 call symputx ('START_TIME',PUT(DATETIME(),15.));
	 START_TIME = &START_TIME;
	 END_TIME = DATETIME();
	 ELAPSED = END_TIME - START_TIME;
	 PUT ' NOTE: Start Time (HH:MM) = ' START_TIME TIMEAMPM8.;
	 PUT ' NOTE: End Time (HH:MM) = ' END_TIME TIMEAMPM8.;
	 PUT ' NOTE: Elapsed Time (HH:MM:SS) = ' ELAPSED TIME.; PUT ' ';
	RUN;
	%end;
	%else 
	      %export(work,sumary,sock,&exporttype);
	Title;
	%finish:
	%mend CA05;
%CA05;
