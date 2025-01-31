%let footer = State: IL GA OH NY | WhereClause: ( State_Cd = IL and PHC_code_short_desc = Rubella )  | Diagnosis Date: 12/1999 - 01/2002 | Diseases: Cholera* Malaria*;
options macrogen symbolgen mprint mlogic;
%macro parse_wcls;
%let i=1;

%do %until ( %length(%scan("&footer",&i,"|~^")) = 0 or &i >9);
%let x= %scan("&footer",&i,"|~^");
footnote&i h=1 j=l "&x";
%let i = %eval(&i+1);
%end;

%mend parse_wcls;

%parse_wcls;
ods listing close;
ods html body='c:\test.html';
proc print data=sasuser.houses;
run;
footnote;
ods html close;
ods listing;