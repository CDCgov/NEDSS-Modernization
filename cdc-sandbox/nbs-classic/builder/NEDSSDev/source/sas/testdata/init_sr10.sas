%Let where=input(state_cd,2.)=stfips("IL") and PHC_code_desc="STD" and (1995<=year(datepart(Diagnosis_Date))<=1998);
%include rptutil(subset.sas);
%include rpttest(nbssr00010.sas);
