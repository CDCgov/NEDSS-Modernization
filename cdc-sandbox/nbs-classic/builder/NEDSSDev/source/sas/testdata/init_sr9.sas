%let where=input(state_cd,2.)=stfips("IL") and  PHC_code_desc='STD' and year(datepart(Diagnosis_Date))=1998;
%include rptutil(subset.sas);
%include rpttest(nbssr00009.sas);
