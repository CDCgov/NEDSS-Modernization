%let where=input(state_cd,2.)=stfips("IL") and (1994<=year(datepart(Diagnosis_Date))<=1998) and month(datepart(Diagnosis_Date))<=5;
%include rptutil(subset.sas);
%include rpttest(nbssr00007.sas);
