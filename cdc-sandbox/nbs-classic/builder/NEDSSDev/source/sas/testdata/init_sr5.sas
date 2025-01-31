%let where=input(state_cd,2.)=stfips("IL") and (1994<=year(datepart(Diagnosis_date))<=1998) and month(datepart(Diagnosis_date))<=5;
%include rptutil(subset.sas);
%include rpttest(nbssr00005.sas);
