%macro NBSSR00034;
%chk_mv;

%if  %upcase(&skip)=YES %then
      %goto finish;

Proc sql;
	create table qa5_init as select 
	REFERRAL_BASIS, 
    up.PROVIDER_QUICK_CODE, 
    up.FIRST_NM, up.LAST_NM,
	ADM_ReferralBasisOOJ,
	1 as Count 'Count'
    from STD_HIV_DATAMART shd, nbs_rdb.USER_PROFILE up  
	where shd.add_user_id = up.NEDSS_ENTRY_ID;
quit;
Proc sql;
	create table qa5_exp as select 
	REFERRAL_BASIS, 
    up.PROVIDER_QUICK_CODE, 
    up.FIRST_NM, up.LAST_NM,
	ADM_ReferralBasisOOJ 
    from STD_HIV_DATAMART shd, nbs_rdb.USER_PROFILE up  
	where shd.add_user_id = up.NEDSS_ENTRY_ID;
quit;

data qa5_init;
SET qa5_init;

    LENGTH UserName $78;
	len=lengthn(PROVIDER_QUICK_CODE);
	if(len>1) then UserName=trim(PROVIDER_QUICK_CODE)||"-"||trim(FIRST_NM) ||" "||trim(LAST_NM);
	else UserName=trim(FIRST_NM) ||" "||trim(LAST_NM);

	if(ADM_ReferralBasisOOJ ~='')then DO; REFERRAL_BASIS='OOJ';end;

RUN;

proc format;
   value $ref1Type 'P1 - Partner, Sex'='Part./Clus. Referrals'
				  'P2 - Partner, Needle-Sharing'='Part./Clus. Referrals'
				  'P3 - Partner, Both'='Part./Clus. Referrals'
				  'S1 - Social Contact 1'='Part./Clus. Referrals'
				  'S2 - Social Contact 2'='Part./Clus. Referrals'
				  'S3 - Social Contact 3'='Part./Clus. Referrals'	
				  'A1 - Associate 1'='Part./Clus. Referrals'
				  'A2 - Associate 2'='Part./Clus. Referrals'
				  'A3 - Associate 3'='Part./Clus. Referrals'
				  'T1 - Positive Test'='Reactor Reports'
				  'T2 - Morbidity Report'='Reactor Reports'
				  'OOJ'= 'OOJ Referrals'
				  other='Other Referrals';
run;

proc  sql noprint;
	Create table qa5
	as select REFERRAL_BASIS,
	      UserName, Count from qa5_init order by UserName;
quit;

%footnote;

%if %upcase(&exporttype)=REPORT %then %do;
ODS LISTING CLOSE;
ods html body=sock (no_bottom_matter);
		proc tabulate MISSING data=qa5 format=comma12.; 
	     class UserName REFERRAL_BASIS / preloadfmt EXCLUSIVE; 
	     var count; 
	     table  UserName
	         all='Total'*f=3., 
	           REFERRAL_BASIS='Referral Basis'*count=' '*sum=' '
	         all='Total'*count=' '*sum=' ' 
	        / rts=25 printmiss; 
	     format REFERRAL_BASIS $ref1Type.; 
		 options missing = '0';
	     title 'Number of Records Entered by User ID';
	run; 

   quit;
 ods html close;
%end;
%else 
      %export(work,qa5_exp,sock,&exporttype);

Title;
Footnote;
%finish:
%mend NBSSR00034;
%NBSSR00034;
