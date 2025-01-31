/*Program Name : Rpt_formats.sas																												*/
/*																																				*/
/*Created by : SA																																*/
/*																																				*/
/*Program Created Date:	11-16-2016																												*/
/*																																				*/
/*Program Last Modified Date:																													*/
/*							:																													*/
/*							:																													*/
/*Program Description:	SAS Formats for STD Reports																								*/
/*																																				*/
/*Comments:																																		*/


%Macro Formats;
proc format;
value $gender		
'Male' = 'M'		
'Female' = 'F'
'Male to Female' = 'MTF'
'Female to Male' = 'FTM'
'Transgender, unspecified','Unknown' = 'U'
;

value $preg
'Yes' = 'Y'
'No' = 'N'
'Unknown' = 'U'
;

value $mar	
'Annulled' ='A'
'Divorced' = 'D'
'Interlocutory' = 'I'
'Legally separated' = 'L'
'Married' = 'M'
'Polygamous' = 'P'
'Refused to answer' = 'R'
'Single, Never married' = 'S'
'Domestic partner' = 'T'
'Unknown' = 'U'
'Widowed' = 'W'
;

value $field
'Deceased' = 'DEC'
'Field Follow-up' = 'FF'
'Insufficient Info'= 'II'
'Not Program Priority' = 'NPP'
'Other' = 'OTH'
'Risk of Domestic Violence' = 'RDV'
'Physician Closure'= 'PC'
'Record Search Closure' = 'RSC'
'Secondary Referral' = 'SR'
Other,'Send OOJ' , 'Administrative Closure' , 'BFP - No Follow-up' = ' ' 
;

value $nine
' ' = '00'
;

value $null
'NULL' = ' ';

value $diag
' ' , 'NULL' = ' ' 
;

value miss
. , other = ' ' 

;

value $ref
'P1 - Partner, Sex' = 'P1'
'P2 - Partner, Needle-Sharing'  = 'P2'
'P3 - Partner, Both'  = 'P3'
'A1 - Associate 1' = 'A1'
'A2 - Associate 2' = 'A2'
'A3 - Associate 3' = 'A3'
'C1- Cohort' = 'C1'
'S1 - Social Contact 1' = 'S1'
'S2 - Social Contact 2' = 'S2'
'S3 - Social Contact 3' = 'S3'
;

run;

%Mend;

%Formats;
