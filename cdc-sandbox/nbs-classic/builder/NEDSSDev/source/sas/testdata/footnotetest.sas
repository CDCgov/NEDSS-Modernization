*******   Testing footnote.sas;

%let footer = State: IL GA OH NY | WhereClause: ( State_Cd = IL and PHC_code_short_desc = Rubella )  | Diagnosis Date: 12/1999 - 01/2002 | Diseases: Cholera* Malaria*;

filename rptutil 'C:\NBS1.1_Rel_Development\development\source\sas\util';
%include rptutil(footnote.sas);

data _null_;
x=1;
run;

%footnote;
ods listing close;
ods html body='c:\test.html';
proc print data=sasuser.heart;
run;
footnote;
ods html close;
ods listing;