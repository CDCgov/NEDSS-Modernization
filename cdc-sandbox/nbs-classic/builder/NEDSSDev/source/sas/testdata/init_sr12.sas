%let where=input(state_cd,2.)=stfips("IL") and (1990<=year(datepart(Diagnosis_Date))<=1999);
%include rptutil(subset.sas);
%include rpttest(nbssr00012.sas);
