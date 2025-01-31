/*******************************/
/*** GENERIC Case Fact Table ***/
/*******************************/
%let whereclause=investigation_form_cd = 'INV_FORM_GEN';

/* select only Generic observations from PHCchildobs and filter out question types to separate data sets */
/* this data step could be metadata-driven from the Codeset table as follows: */
/* if data_type = 'C' and mutli_select_ind = 'N' then output GENchildobscodedsingle; ...  */
Data GenObsInvFrmQ1;
  Set rdbdata.PHCchildobs_NONINC (where=(&whereclause)); 
Run; 

/* join observations with Codeset */
Proc SQL;
 Create table GenObsInvFrmQ as 
  Select a.PUBLIC_HEALTH_CASE_UID,
		 a.ROOT_OBS_UID,
		 a.child_obs_uid,
		 a.child_obs_cd,
		 b.tbl_nm,
		 b.col_nm
   From GenObsInvFrmQ1 a LEFT JOIN rdbdata.CodeSet b
     On a.child_obs_cd = b.cd
   Order by 1,2,3,4;
Quit;


Data GENchildobscodedsingle
     GENchildobsdate
     GENchildobsnumeric
     GENchildobsorphan;
  Set GenObsInvFrmQ; 
 
  Select (child_obs_cd);
    /* single-select coded questions */
    When (
		  'INV113',     /* OTHER_RPT_SRC */
		  'INV148',		/* DAYCARE_ASSOCIATION_FLG */
		  'INV149',		/* FOOD_HANDLR_IND */
		  'INV178',		/* PREGNANCY_STATUS */
		  'INV179',		/* PID_IND */
		  'INV189')		/* CULTURE_IDENT_ORG_ID */
    output GENchildobscodedsingle;

    /* date questions */
    When (
		  'INV184',		/* INV_HSPTL_ID */            /*** date??? ***/
		  'INV191'		/* BIRTH_HSPTL_ID */                    /*** date??? ***/
		  )
    output GENchildobsdate;

    /* numeric questions */
    When (
		  'INV185')		/* DAYCARE_ID */
    output GENchildobsnumeric;

	/*Known Orphan or fields already in Investigation Dimension */
	/*'INV128',	HSPTLIZD_IND moved to investigation */
	/*'INV134',	HSPTLIZE_DURATION_DAYS */
	/*'INV145',	DIE_FRM_ILLNESS_IND */        		/*** not in mapping doc ***/
	/*'INV132', HSPTL_ADMISSION_DT */
	/*'INV133',	HSPTL_DISCHARGE_DT */
	/*'INV176',FIRST_RPT_TO_CDC_DT */                /*** not in mapping doc ***/
	/*'INV177',EARLIEST_RPT_TO_PHD_DT */             /*** not in mapping doc ***/
    Otherwise output GENchildobsorphan;
  End;
Run;

/* get observation values (coded single, coded multi, text, date, numeric) */
%getobscode(GENcodedsingle,GENchildobscodedsingle);
%getobsdate(GENdate,GENchildobsdate,from_time);
%getobsnum(GENnumeric,GENchildobsnumeric,numeric_value_1);

/* transpose each response data set */
%rows_to_columns(GENcodedsingle,GENcodedsingletrans);
%rows_to_columns(GENdate,GENdatetrans);
%rows_to_columns(GENnumeric,GENnumerictrans);

data GenericObsInvFrmQ;
merge GENcodedsingletrans GENdatetrans GENnumerictrans;
by public_health_case_uid;
run;
proc sort data=GenericObsInvFrmQ; by public_health_case_uid; run;

proc sort data=rdbdata.PHCrootobs_NONINC out=GenPHCrootobs; by public_health_case_uid;
where &whereclause;
run;


proc sql;
create table Generic_Case1 as 
Select 
		phc.public_health_case_uid		as public_health_case_uid0,
		phc.root_obs_uid				as root_obs_uid0,
		phc.effective_duration_amt		as ILLNESS_DURATION,
		phc.effective_duration_unit_cd 	as ILLNESS_DURATION_Unit,
		input(phc.pat_age_at_onset,4.0)	as PATIENT_AGE_AT_ONSET label='PATIENT_AGE_AT_ONSET',
		phc.pat_age_at_onset_unit_cd	as PATIENT_AGE_AT_ONSET_UNIT,
		phc.detection_method_cd			as DETECTION_METHOD,
		phc.detection_method_desc_txt	as DETECTION_METHOD_OTHER,
		phc.CONDITION_CD,
		obs.*

From	GenPHCrootobs 	as phc
	/* use left join to ensure we have a fact table record
	even the user did not enter any observation data for the case */
	left join 	GenericObsInvFrmQ	as obs
		on phc.public_health_case_uid = obs.public_health_case_uid
		

;
quit;

proc sort data =Generic_Case1;
by public_health_case_uid0;

data Generic_Case1;
set Generic_Case1(drop = public_health_case_uid root_obs_uid) ;
rename 
	public_health_case_uid0 =public_health_case_uid
	root_obs_uid0 =root_obs_uid;
run;

%add_common_inv_keys(Generic_Case2,Generic_Case1);

/***set LDF_GROUP_KEY value***/
proc sql;
CREATE TABLE GENERIC_CASE AS 
	select distinct gc.*, coalesce(lg.LDF_Group_Key,1) as LDF_Group_Key
	from Generic_case2 gc
	left outer join ldf_group lg
      	on gc.public_health_case_uid = lg.business_object_uid;
QUIT;
PROC SORT DATA=Generic_Case NODUPKEY; BY public_health_case_uid; RUN;

Data rdbdata.Generic_Case;
set Generic_Case;
drop
	public_health_case_uid
	root_obs_uid INV_START_DT_KEY 	DIAGNOSIS_DT_KEY
	INV_RPT_DT_KEY condition_cd
;
run;
PROC SORT DATA=rdbdata.Generic_Case NODUPKEY; BY  INVESTIGATION_KEY; RUN;


PROC datasets library = work nolist;
delete GenObsInvFrmQ
    GenObsInvFrmQ1
	GENchildobscodedsingle
	GENchildobsdate
	GENchildobsnumeric
	GENchildobsorphan
	GENcodedsingle
	GENdate
	GENnumeric
	GENcodedsingletrans
	GENdatetrans
	GENnumerictrans
	GenPHCrootobs
	Generic_Case
	Generic_Case1
	Generic_Case2
	Genericobsinvfrmq
;
run;
quit;


