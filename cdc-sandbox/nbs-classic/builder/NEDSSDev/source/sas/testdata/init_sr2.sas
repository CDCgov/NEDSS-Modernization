%let where=input(state_cd,2.)=stfips("IL") and (1993<=year(datepart(diagnosis_date))<=1996);
%include rptutil(subset.sas);
%include rpttest(nbssr00002.sas);

