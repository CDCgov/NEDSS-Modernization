%macro NBSSR00033;
%chk_mv;

%if  %upcase(&skip)=YES %then
      %goto finish;

Proc sql;
create table pa4_init as SELECT 
distinct di.IX_TYPE, 
DIAGNOSIS_CD,INV_LOCAL_ID, 
FL_FUP_DISPOSITION_DESC,
CA_PATIENT_INTV_STATUS, 
cr.CONTACT_REFERRAL_BASIS,
cr.PROCESSING_DECSN_DESCRIPTION,
1 as Total 'Total'
  FROM STD_HIV_DATAMART shd , 
	   nbs_rdb.D_INTERVIEW di,  
	   nbs_rdb.F_CONTACT_RECORD_CASE fcrc, 
	   nbs_rdb.d_CONTACT_RECORD cr 
	   where shd.INVESTIGATION_KEY = fcrc.SUBJECT_INVESTIGATION_KEY 
	   and fcrc.CONTACT_INTERVIEW_KEY = di.D_INTERVIEW_KEY 
	   and cr.d_contact_record_key = fcrc.D_CONTACT_RECORD_KEY 
	   order by INV_LOCAL_ID
;
quit;

Proc sql;
create table pa4_exp as SELECT 
distinct di.IX_TYPE, 
DIAGNOSIS_CD,INV_LOCAL_ID, 
FL_FUP_DISPOSITION_DESC,
CA_PATIENT_INTV_STATUS, 
cr.CONTACT_REFERRAL_BASIS,
cr.PROCESSING_DECSN_DESCRIPTION
  FROM STD_HIV_DATAMART shd , 
	   nbs_rdb.D_INTERVIEW di,  
	   nbs_rdb.F_CONTACT_RECORD_CASE fcrc, 
	   nbs_rdb.d_CONTACT_RECORD cr 
	   where shd.INVESTIGATION_KEY = fcrc.SUBJECT_INVESTIGATION_KEY 
	   and fcrc.CONTACT_INTERVIEW_KEY = di.D_INTERVIEW_KEY 
	   and cr.d_contact_record_key = fcrc.D_CONTACT_RECORD_KEY 
	   order by INV_LOCAL_ID
;
quit;

Proc sql;
	create table pa4_count as
	select 'Cases Closed' as Type 'Type' ,count(*) as Count 'Count', 1 as idnumber 'idnumber'
	 from  nbs_rdb.STD_HIV_DATAMART shd 
	 where shd.CC_CLOSED_DT is not null
	 and INV_CASE_STATUS in ('Confirmed','Probable')
 union
	select 'Cases Interviewed' as Type 'Type' ,count(*) as Count 'Count', 2 as idnumber 'idnumber'
	 from  nbs_rdb.STD_HIV_DATAMART shd, nbs_rdb.F_INTERVIEW_CASE fic, nbs_rdb.D_INTERVIEW di 
	 where fic.INVESTIGATION_KEY = shd.INVESTIGATION_KEY 
	 and di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY 
	 and CA_PATIENT_INTV_STATUS = 'I - Interviewed' 
	 and di.IX_TYPE = 'Initial/Original' 
	 and di.interview_loc_cd in('C','F')
 union
	SELECT 'Cases NCI' as Type 'Type' ,count(*) as Count 'Count', 3 as idnumber 'idnumber'
	  FROM STD_HIV_DATAMART shd 
	  where shd.CA_PATIENT_INTV_STATUS = 'I - Interviewed' 
	  and shd.INVESTIGATION_KEY not in (select fcrc.CONTACT_INVESTIGATION_KEY 
	  from nbs_rdb.F_CONTACT_RECORD_CASE fcrc, nbs_rdb.D_CONTACT_RECORD cd 
		where fcrc.D_CONTACT_RECORD_KEY = cd.D_CONTACT_RECORD_KEY 
		and cd.PROCESSING_DECSN_DESCRIPTION ~='Record Search Closure')
union
	select 'Cases re-interviewed' as Type 'Type' ,count(*) as Count 'Count', 4 as idnumber 'idnumber'
	  from STD_HIV_DATAMART shd, nbs_rdb.F_INTERVIEW_CASE fic, nbs_rdb.D_INTERVIEW di  
	  where fic.INVESTIGATION_KEY = shd.INVESTIGATION_KEY 
	  and di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY 
	  and  CA_PATIENT_INTV_STATUS = 'I - Interviewed' 
	  and di.IX_TYPE = 'Re-Interview'
union
	SELECT 'Cases NCI with re-interview' as Type 'Type' ,count(*) as Count 'Count', 5 as idnumber 'idnumber'
	  FROM STD_HIV_DATAMART shd , nbs_rdb.F_INTERVIEW_CASE fic, nbs_rdb.D_INTERVIEW di  
	  where fic.INVESTIGATION_KEY = shd.INVESTIGATION_KEY 
	  and di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY 
	  and  CA_PATIENT_INTV_STATUS = 'I - Interviewed' 
	  and di.IX_TYPE = 'Re-Interview' 
	  and shd.INVESTIGATION_KEY not in (select fcrc.CONTACT_INVESTIGATION_KEY 
	    from nbs_rdb.F_CONTACT_RECORD_CASE fcrc, nbs_rdb.D_CONTACT_RECORD cd 
		where fcrc.D_CONTACT_RECORD_KEY = cd.D_CONTACT_RECORD_KEY 
		and cd.PROCESSING_DECSN_DESCRIPTION ~='Record Search Closure')
union
	select 'Cases HIV Tested' as Type 'Type' ,count(*) as Count 'Count', 6 as idnumber 'idnumber'
		from STD_HIV_DATAMART shd 
		where shd.HIV_900_TEST_IND = 'Yes'
union
	select 'Cases HIV Seropositive' as Type 'Type' ,count(*) as Count 'Count', 7 as idnumber 'idnumber'
		from STD_HIV_DATAMART shd 
		where shd.HIV_900_RESULT in ('13-Positive/Reactive','21-HIV-1 Pos','22-HIV-1 Pos, Possible Acute','23-HIV-2 Pos','24-HIV-Undifferentiated','12-Prelim Positive')
union
	select 'Cases Post Test Counseled' as Type 'Type' ,count(*) as Count 'Count', 8 as idnumber 'idnumber'
		from STD_HIV_DATAMART shd 
		where shd.HIV_POST_TEST_900_COUNSELLING = 'Yes'
/*union
	select 'Total Period Partners' as Type 'Type', SUM(INPUT(STD_PRTNRS_PRD_FML_TTL,4.0),INPUT(STD_PRTNRS_PRD_MALE_TTL, 4.0),INPUT(STD_PRTNRS_PRD_TRNSGNDR_TTL, 4.0)) as Count 'Count', 9 as idnumber 'idnumber'
		from STD_HIV_DATAMART shd 
	 	where shd.CC_CLOSED_DT is  not null and shd.INV_CASE_STATUS in ('Confirmed','Probable')*/
;
quit;

proc sort data=pa4_count;
   by idnumber;
run;

data pa4_count;
	set pa4_count(drop=idnumber);
run;

data pa4_init;
SET pa4_init;
    LENGTH PartnerCluster $78;
	if((PROCESSING_DECSN_DESCRIPTION='Field Follow-up' or PROCESSING_DECSN_DESCRIPTION='Secondary Referral') and (CONTACT_REFERRAL_BASIS='P1 - Partner, Sex' or CONTACT_REFERRAL_BASIS='P2 - Partner, Needle-Sharing' or CONTACT_REFERRAL_BASIS='P3 - Partner, Both') and (FL_FUP_DISPOSITION_DESC='A - Preventative Treatment' or FL_FUP_DISPOSITION_DESC='B - Refused Preventative Treatment' or FL_FUP_DISPOSITION_DESC='C - Infected, Brought to Treatment' or FL_FUP_DISPOSITION_DESC='D - Infected, Not Treated' or FL_FUP_DISPOSITION_DESC='F - Not Infected' or FL_FUP_DISPOSITION_DESC='1 - Prev. Pos' or FL_FUP_DISPOSITION_DESC='2 - Prev. Neg, New Pos' or FL_FUP_DISPOSITION_DESC='3 - Prev. Neg, Still Neg' or FL_FUP_DISPOSITION_DESC='4 - Prev. Neg, No Test' or FL_FUP_DISPOSITION_DESC='5 - No Prev Test, New Pos' or FL_FUP_DISPOSITION_DESC='6 - No Prev Test, New Neg' or FL_FUP_DISPOSITION_DESC='7 - No Prev Test, No Test')) then do; PartnerCluster='Partners Examined';end;
	if((PROCESSING_DECSN_DESCRIPTION='Field Follow-up' or PROCESSING_DECSN_DESCRIPTION='Secondary Referral') and (CONTACT_REFERRAL_BASIS='P1 - Partner, Sex' or CONTACT_REFERRAL_BASIS='P2 - Partner, Needle-Sharing' or CONTACT_REFERRAL_BASIS='P3 - Partner, Both') and (FL_FUP_DISPOSITION_DESC='G - Insufficient Information to Begin Investigation' or FL_FUP_DISPOSITION_DESC='H - Unable to Locate' or FL_FUP_DISPOSITION_DESC='J - Located, Not Examined, Treated, and/or Interview' or FL_FUP_DISPOSITION_DESC='Q - Administrative Closure' or FL_FUP_DISPOSITION_DESC='K - Sent Out Of Jurisdiction' or FL_FUP_DISPOSITION_DESC='L - Other' or FL_FUP_DISPOSITION_DESC='V - Domestic Violence Risk' or FL_FUP_DISPOSITION_DESC='X - Patient Deceased' or FL_FUP_DISPOSITION_DESC='K - Sent Out Of Jurisdiction' or FL_FUP_DISPOSITION_DESC='E - Previously Treated for This Infection')) then do; PartnerCluster='Partners Not Examined';end;
	if((PROCESSING_DECSN_DESCRIPTION='Field Follow-up' or PROCESSING_DECSN_DESCRIPTION='Secondary Referral') and (CONTACT_REFERRAL_BASIS='C1- Cohort' or CONTACT_REFERRAL_BASIS='S1 - Social Contact 1' or CONTACT_REFERRAL_BASIS='S2 - Social Contact 2' or CONTACT_REFERRAL_BASIS='S3 - Social Contact 3' or CONTACT_REFERRAL_BASIS='A1 - Associate 1' or CONTACT_REFERRAL_BASIS='A2 - Associate 2' or CONTACT_REFERRAL_BASIS='A3 - Associate 3') and (FL_FUP_DISPOSITION_DESC='A - Preventative Treatment' or FL_FUP_DISPOSITION_DESC='B - Refused Preventative Treatment' or FL_FUP_DISPOSITION_DESC='C - Infected, Brought to Treatment' or FL_FUP_DISPOSITION_DESC='D - Infected, Not Treated' or FL_FUP_DISPOSITION_DESC='F - Not Infected' or FL_FUP_DISPOSITION_DESC='1 - Prev. Pos' or FL_FUP_DISPOSITION_DESC='2 - Prev. Neg, New Pos' or FL_FUP_DISPOSITION_DESC='3 - Prev. Neg, Still Neg' or FL_FUP_DISPOSITION_DESC='4 - Prev. Neg, No Test' or FL_FUP_DISPOSITION_DESC='5 - No Prev Test, New Pos' or FL_FUP_DISPOSITION_DESC='6 - No Prev Test, New Neg' or FL_FUP_DISPOSITION_DESC='7 - No Prev Test, No Test')) then do; PartnerCluster='Cluster Examined';end;
	if((PROCESSING_DECSN_DESCRIPTION='Field Follow-up' or PROCESSING_DECSN_DESCRIPTION='Secondary Referral') and (CONTACT_REFERRAL_BASIS='C1- Cohort' or CONTACT_REFERRAL_BASIS='S1 - Social Contact 1' or CONTACT_REFERRAL_BASIS='S2 - Social Contact 2' or CONTACT_REFERRAL_BASIS='S3 - Social Contact 3' or CONTACT_REFERRAL_BASIS='A1 - Associate 1' or CONTACT_REFERRAL_BASIS='A2 - Associate 2' or CONTACT_REFERRAL_BASIS='A3 - Associate 3') and (FL_FUP_DISPOSITION_DESC='G - Insufficient Information to Begin Investigation' or FL_FUP_DISPOSITION_DESC='H - Unable to Locate' or FL_FUP_DISPOSITION_DESC='J - Located, Not Examined, Treated, and/or Interview' or FL_FUP_DISPOSITION_DESC='Q - Administrative Closure' or FL_FUP_DISPOSITION_DESC='K - Sent Out Of Jurisdiction' or FL_FUP_DISPOSITION_DESC='L - Other' or FL_FUP_DISPOSITION_DESC='V - Domestic Violence Risk' or FL_FUP_DISPOSITION_DESC='X - Patient Deceased' or FL_FUP_DISPOSITION_DESC='K - Sent Out Of Jurisdiction' or FL_FUP_DISPOSITION_DESC='E - Previously Treated for This Infection')) then do; PartnerCluster='Cluster Not Examined';end;
RUN;

proc format;
   value $disfmt '2 - Prev. Neg, New Pos'='Dispo 2'
                 '3 - Prev. Neg, Still Neg'='Dispo 3'
                 '4 - Prev. Neg, No Test'='Dispo 4'
				 '5 - No Prev Test, New Pos'='Dispo 5'
				 '6 - No Prev Test, New Neg'='Dispo 6'
				 '7 - No Prev Test, No Test'='Dispo 7'
				 'A - Preventative Treatment'='Dispo A'
				 'B - Refused Preventative Treatment'='Dispo B'
				 'C - Infected, Brought to Treatment'='Dispo C'
				 'D - Infected, Not Treated'='Dispo D'
				 'F - Not Infected'='Dispo F'
				 'E - Previously Treated for This Infection'='Dispo E'
				 'G - Insufficient Info to Begin Investigation'='Dispo G'
				 'H - Unable to Locate'='Dispo H'
				 'J - Located, Not Examined and/or Interviewed'='Dispo K'
				 'K - Sent Out Of Jurisdiction'='Dispo K'
				 'L - Other'='Dispo L'
				 'Q - Administrative Closure'='Dispo Q'
				 'V - Domestic Violence Risk'='Dispo V'
				 'X - Patient Deceased'='Dispo X'
				 'Z - Previous Preventative Treatment'='Dispo Z';
   value $intfmt 'Initial/Original'='From OI'
                 'Re-Interview'='From RI';
   picture pctfmt low-high='009.99%';
run;

proc  sql noprint;
	Create table pa4
	as select FL_FUP_DISPOSITION_DESC as Disposition 'Disposition',
	      PartnerCluster as Type 'Type',
	      IX_TYPE,
	      Total from pa4_init order by PartnerCluster;
quit;
Title 'Program Indicator Report';
%footnote;

%if %upcase(&exporttype)=REPORT %then %do;
ODS LISTING CLOSE;
	ods html body=sock (no_bottom_matter);
		Proc print data=pa4_count noobs label;
			run;
		proc tabulate data=pa4 MISSING  format=comma12.; 
		     class Type Disposition IX_TYPE / preloadfmt EXCLUSIVE; 
		     var Total; 
		     table Type*(Disposition all), 
		           IX_TYPE=''*Total=' '*(sum='Count' COLPCTSUM='Percentage'*f=pctfmt9.)
		           all = ''*Total*(sum='Count' COLPCTSUM='Percentage'*f=pctfmt9.)
		           /rts=20 printmiss;
				   options missing = '0';
			     format IX_TYPE $intfmt. Disposition $disfmt.; 
			run; 
	 ods html close;
 ods listing;
%end;
%else 
      %export(work,pa4_exp,sock,&exporttype);
Title;
Footnote;
%finish:
%mend NBSSR00033;
%NBSSR00033;
