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
'Male' 									= 'M'	
'M'    									= 'M'
'Female' 								= 'F'
'F'										= 'F'
'Male to Female' 						= 'MtF'
'Female to Male' 						= 'FtM'
'Refused'								= 'R'
'R'										= 'R'
'Unknown'								= 'U'
'U'										= 'U'
'Transgender, unspecified'				= 'T'
'Transgender unspecified'				= 'T'
'Did not ask'							= 'NASK'
'NASK'									= 'NASK'
' '                                     = ' '
'NULL'									= ' '
'Other'									= 'OTH'
'OTH'									= 'OTH'
other									= 'XXX'
;

value $preg
'Yes' 				= 'Y'
'No' 				= 'N'
'Unknown' 			= 'U'
'Refused to answer' = 'R'
'NULL'				= ' '
' '         		= ' '
other				= 'XXX'

;


value $race
'American Indian or Alaska Native'			= 'AI/AN'
'Asian' 									= 'A'
'Black or African American' 				= 'B'
'Native Hawaiian or Other Pacific Islander' = 'NH/PI'
'White'										= 'W'
'Other Race' 								= 'O'
'Asked But Unknown' 						= 'AKSU'
'Not Asked'									= 'NA'
'No Information'                            = 'NI'
'Refused To Answer'                    		= 'R'
'Unknown'									= 'U'
'Multi-Race'								= 'MR'
'NULL'										= ' '
' '											= ' '
other									    = 'XXX'
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

value $dispo
'1 - Prev. Pos' = '1'
'2 - Prev. Neg, New Pos' = '2'
'3 - Prev. Neg, Still Neg' = '3'
'4 - Prev. Neg, No Test' = '4'
'5 - No Prev Test, New Pos' = '5'
'6 - No Prev Test, New Neg' = '6'
'7 - No Prev Test, No Test' = '7'
'A - Preventative Treatment' = 'A'
'B - Refused Preventative Treatment' = 'B'
'C - Infected, Brought to Treatment' = 'C'
'D - Infected, Not Treated' = 'D'
'E - Previously Treated for This Infection' = 'E'
'F - Not Infected' = 'F'
'G - Insufficient Info to Begin Investigation' = 'G'
'H - Unable to Locate' = 'H'
'J - Located, Not Examined and/or Interviewed' = 'J'
'K - Sent Out Of Jurisdiction' = 'K'
'L - Other' = 'L'
'Q - Administrative Closure' = 'Q'
'V - Domestic Violence Risk' = 'V'
'X - Patient Deceased' = 'X'
'Z - Previous Preventative Treatment' = 'Z' 
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

value $place
''='# Associated Cases:'
other = '# Associated Cases:'
;
value $combdx
'300','350' = '300'
'200' = '200'
'710' = '710'
'720' = '720'
'730' ='730'
'740'='740'
'745' ='745'
'750'='750'
'755'='755'
'790'='790'
'900'='900'
'950'='950'       
;
value $states
'Georgia' = 'GA'
;

run;

%Mend;

%Formats;
