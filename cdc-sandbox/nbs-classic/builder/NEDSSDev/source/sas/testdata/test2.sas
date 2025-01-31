%let footer = State: IL GA OH NY | WhereClause: ( State_Cd = IL and PHC_code_short_desc = Rubella )  | Diagnosis Date: 12/1999 - 01/2002 | Diseases: Cholera* Malaria*;

filename rptutil 'c:\reportdev\sas\util';
%include rptutil(setfootnote.sas);

data _null_;
x=1;
run;

%parse_wcls(&footer);
ods listing close;
ods html body='c:\test.html';
proc print data=sasuser.houses;
run;
footnote;
ods html close;
ods listing;