%let where=input(state_cd,2.)=stfips("IL") and PHC_code_desc="STD" and ('01FEB1997'd<=datepart(Diagnosis_Date)<='12MAY1998'd);
%include rptutil(subset.sas);
%include rpttest(nbssr00008.sas);
