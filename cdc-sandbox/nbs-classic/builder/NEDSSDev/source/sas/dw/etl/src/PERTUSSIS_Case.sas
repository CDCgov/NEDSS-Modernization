
/************************************************
	PERTUSSIS_Case
	Observation data extraction

*************************************************/
%let whereclause=investigation_form_cd like 'INV_FORM_PER%';

Data PERObsInvFrmQ1;
set rdbdata.PHCchildobs_NONINC (where=(&whereclause)); 
run;

/* Join the codeset here instead of in PHCobservation
	since we are not sure if same unique_id (cd) could exist 
	in different fact tables
*/
Proc SQL;
Create table PERObsInvFrmQ as
Select 	phc.*,
		c.col_nm
From 	PERObsInvFrmQ1 phc 
	LEFT JOIN rdbdata.Codeset c 
		On c.cd = phc.child_obs_cd;
Quit;


Data PerBatchItems1;
set rdbdata.BatchItem_NONINC (where=(&whereclause)); 
run;
Proc SQL;
Create table PerBatchItems as
Select 	phc.*,
		c.col_nm
From 	PerBatchItems1 phc 
	LEFT JOIN rdbdata.Codeset c 
		On c.cd = phc.item_obs_cd;
Quit;


Data PERchildobscoded 
     /*PERchildobscodedmulti*/
	 /*PERchildobscodedBatch*/
     PERchildobstext 
     PERchildobsdate 		/*from_time*/
     PERchildobsnumeric1 	
     PERchildobsorphan
	 ;
set PERObsInvFrmQ;
	select (child_obs_cd);
		when(
			'PRT021',
			'PRT023',
			'PRT024',
			'PRT074',
			'PRT075',
			'PRT076',
			'PRT077',
			'PRT078',
			'PRT087',
			'PRT088'

		) output PERchildobsorphan;
		when(
			/* 'INV128', hospitalized ind moved to investigation */
			'PRT059',
			'PRT001',
			'PRT003', /* PAROXYSMAL_COUGH_IND*/
			'PRT004',
			'PRT005',
			'PRT006',
			'PRT008',
			'PRT011',
			'PRT012',
			'PRT013',
			'PRT020',
			/*'PRT021', antibiotic, treatment batch */
			'PRT029',
			'PRT031',
			'PRT034',
			'PRT038',
			'PRT041',
			'PRT044',
			'PRT046',
			'PRT047',
			'PRT060',
			'PRT067',
			'PRT068',
			'PRT070',
			/*'PRT075',infection source batch*/
			/*'PRT076',infection source batch*/
			/*'PRT077',infection source batch*/
			'PRT081',
			/*'PRT087', infection source batch */
			'PRT089',
			'PRT090',
			'PRT091',
			'PRT092',
			'PRT093',
			'PRT096',
			'PRT098',
			'PRT100',
			/*'PRT103', DIE_FRM_THIS_ILLNESS_IND moved to Investigation*/
			'PRT104',
			'PRT105',
			'PRT107'

		) output PERchildobscoded;
		when (
			/* 'INV132', HSPTL_ADMISSION_DT moved to Investigation*/
			/* 'INV133', HSPTL_DISCHARGE_DT moved to Investigation*/
			'PRT085',
			'PRT002',
			'PRT007',
			/*'PRT023', start date, treatment batch */
			'PRT030',
			'PRT033',
			'PRT037',
			'PRT040',
			'PRT045',
			'PRT058',
			'PRT083',
			/*'PRT088', cough onset date, infection source batch */
			'PRT094'

		) output PERchildobsdate;
			
		when (
			'PRT061', /* EPI_LINKED_TO_CASE_ID */
			'PRT066',
			'PRT022',
			'PRT048',
			'PRT069',
			/*'PRT078', Relationship Other, Infection source batch */
			'PRT082',
			'PRT084',
			'PRT097',
			'PRT099',
			'PRT101',
			'PRT102'
		) output PERchildobstext;

		when (
			 /* 'INV134',HSPTLIZE_DURATION_DAYS moved to Investigation */
			 /*'PRT061', EPI_LINKED_TO_CASE_ID */
			'PRT009',
			'PRT024',
			'PRT071',
			'PRT080',
			'PRT108',
			'PRT109',
			'PRT110',
			'PRT111',
			'PRT112'
			/*'PRT074' age, infection source batch*/
		) output PERchildobsnumeric1;
		otherwise output PERchildobsorphan;
	end;
run;

%getobscode(PERcodedvalue,PERchildobscoded);
%getobstxt(PERobstxt,PERchildobstext);
%getobsdate(PERfrom_time,PERchildobsdate,from_time);  
%getobsnum(PERNum1, PERchildobsnumeric1,numeric_value_1);

/* separate the multi-coded, there should not be multi select field for Pertussis */
/*
data PERcodedvaluesingle PERcodedvaluemulti;
set PERcodedvalue;
	if etl_ctrl = 'Multi-Coded' then output PERcodedvaluemulti;
	else output PERcodedvaluesingle;
run;
*/
/************************************************************
	Turning the records to columns
**************************************************************/
%rows_to_columns(PERcodedvalue,PERcodedvaluesingle2);
%rows_to_columns(PERobstxt,PERobstxt2);
%rows_to_columns(PERfrom_time,PERfrom_time2);
%rows_to_columns(PERNum1,PERNum1_2);

data PERTUSSIS_Case1;
merge PERcodedvaluesingle2 PERobstxt2 PERfrom_time2 PERNum1_2;
  By public_health_case_uid root_obs_uid; 
run;


/*add fields that are not in Observation table*/
Data PERrootobs;
  Set rdbdata.PHCrootobs_NONINC (where=(&whereclause));
  Keep 	public_health_case_uid 
		root_obs_uid
		transmission_mode_cd
		transmission_mode_desc_txt
       	;
	if transmission_mode_desc_txt = '' then
		transmission_mode_desc_txt = put(transmission_mode_cd, $PRT065f.);
  Rename 
		transmission_mode_cd	=TRANSMISSION_SETTING
	
;
Run;

/*left join makes sure we still create a record in fact table
even user did not enter any child observation data*/
proc sql;
create table PERTUSSIS_Case2 as 
select 	
		phc.public_health_case_uid as public_health_case_uid0,
		phc.root_obs_uid			as root_obs_uid0,
		phc.TRANSMISSION_SETTING,
		per.*
		
		
from	PERrootobs		as phc
		left join PERTUSSIS_Case1	as per
	on	per.public_health_case_uid = phc.public_health_case_uid
	and per.ROOT_OBS_UID = phc.ROOT_OBS_UID
;
quit;

data PERTUSSIS_Case;
set PERTUSSIS_Case2(drop = public_health_case_uid root_obs_uid);
rename 
	public_health_case_uid0=public_health_case_uid
	root_obs_uid0 = root_obs_uid
;
run;
/*******************************************************

	Batch Entry

********************************************************/

/* Batch item observations*/
Data Itemobscoded 
     Itemobstext 		/*PER should not have batch item txt */
     Itemobsdate 		/*from_time*/
     Itemobsnumeric1 	
     Itemobsorphan
	 ;
set PerBatchItems;
	rename 	child_obs_cd = bat_obs_cd
			child_obs_uid= bat_obs_uid
			item_obs_cd  = child_obs_cd
			item_obs_uid = child_obs_uid
			;
	select (item_obs_cd);
		when(
			'PRT023',         /* ANTIBIOTICS_START_DT */
			'PRT088'          /* SOURCE_COUGH_ONSET_DT */
		) output Itemobsdate;
			
		when(
			'PRT021',           /* ANTIBIOTICS_RECEIVED_DESC */
			'PRT075',           /* SOURCE_AGE_UNIT */
			'PRT076',           /* SOURCE_GENDER */
			'PRT077',           /* SOURCE_RELATION */
			'PRT087'           /* SOURCE_VACC_DOSE_NUMBER */

		) output Itemobscoded;

		when(
			'PRT078'           /* SOURCE_RELATION_OTHER */

		) output Itemobstext;

		when (
			'PRT024',          /* ANTIBIOTICS_TAKEN_DAY_NBR */
			'PRT074'           /* SOURCE_AGE */

		) output Itemobsnumeric1;

		otherwise output ItemobsOrphan;
	end;
run;
/* Batch item values*/
%getobscode(Itemobscoded2,Itemobscoded);
%getobsdate(Itemobsdate2,Itemobsdate, from_time);  
%getobsnum(Itemobsnumeric1_2, Itemobsnumeric1, numeric_value_1);
%getobstxt(Itemobstext2,Itemobstext)

/* Separate the two batches and transpose rows into columns format */
data per_treatment_code 
	 per_infection_source_code
		;
set Itemobscoded2;
	select (child_obs_cd);
		when (
			'PRT021' 	/*antibiotic*/
			)
			output per_treatment_code;
		
		when(
			'PRT075',	/*age unit*/	 
			'PRT076',	/*sex*/
			'PRT077',	/*relation*/
			'PRT087'	/*vaccine doses*/

			)	
			output per_infection_source_code;
		otherwise;
	end; 
run;
%rows_to_columns2(per_treatment_code,per_treatment_code2,response,root_obs_uid bat_obs_uid);
%rows_to_columns2(per_infection_source_code,per_infection_source_code2,response,root_obs_uid bat_obs_uid);

/* batch num field*/
data per_infection_source_num per_treatment_num;
set Itemobsnumeric1_2;
	if child_obs_cd='PRT074' then output per_infection_source_num;
	else if child_obs_cd='PRT024' then output per_treatment_num;
run;
%rows_to_columns2(per_infection_source_num,per_infection_source_num2, response,root_obs_uid bat_obs_uid);
%rows_to_columns2(per_treatment_num,per_treatment_num2, response,root_obs_uid bat_obs_uid);

/* batch date fields */
data per_treatment_date 
	 per_infection_source_date
		;
set Itemobsdate2;
	if child_obs_cd='PRT023' 	/*antibiotic start date */
		then	output per_treatment_date;
		
	else if child_obs_cd ='PRT088'	/*cough date */	 
		then	output per_infection_source_date;
	else ;/*removed bad data, if any, before transpose*/
run;

%rows_to_columns2(per_treatment_date,per_treatment_date2,response,root_obs_uid bat_obs_uid);
%rows_to_columns2(per_infection_source_date,per_infection_source_date2,response,root_obs_uid bat_obs_uid);

/* batch date txt */
data per_infection_source_txt;
set Itemobstext2;
	if child_obs_cd='PRT078';		/*Relation ship Other */ 
run;
%rows_to_columns2(per_infection_source_txt,per_infection_source_txt2,response,root_obs_uid bat_obs_uid);


/*******************************************************

	Treatment Batch Dimension
	Treatment Batch_Group Bridge

	Note: merge the coded and date data and assign keys
********************************************************/
data treatment_batch;
merge per_treatment_code2 per_treatment_date2 per_treatment_num2;	
	by root_obs_uid bat_obs_uid;
run;

data rdbdata.PERTUSSIS_TREATMENT_FIELD 
	rdbdata.PERTUSSIS_TREATMENT_GROUP(keep=PERTUSSIS_TREATMENT_GRP_KEY );
	retain PERTUSSIS_TREATMENT_FLD_KEY 1 	PERTUSSIS_TREATMENT_GRP_KEY  1;		
	if (PERTUSSIS_TREATMENT_FLD_KEY = 1) then do;
		output rdbdata.PERTUSSIS_TREATMENT_FIELD;
		output rdbdata.PERTUSSIS_TREATMENT_GROUP ;
		end;
	set treatment_batch;
	by root_obs_uid bat_obs_uid;

	if first.root_obs_uid then do;
		PERTUSSIS_TREATMENT_GRP_KEY +1;
		output rdbdata.PERTUSSIS_TREATMENT_GROUP ;
		end;

	PERTUSSIS_TREATMENT_FLD_KEY + 1;
	output rdbdata.PERTUSSIS_TREATMENT_FIELD;
run;

/*******************************************************

	SuspectedSrc Batch Dimension
	SuspectedSrc Batch_Group Bridge

********************************************************/
data suspected_batch;
merge per_infection_source_code2 per_infection_source_date2 
		per_infection_source_txt2 per_infection_source_num2
		;
by root_obs_uid bat_obs_uid;
run;

proc sort data= suspected_batch;by root_obs_uid bat_obs_uid; run;

data rdbdata.PERTUSSIS_SUSPECTED_SOURCE_FLD 
	rdbdata.PERTUSSIS_SUSPECTED_SOURCE_GRP (keep=PERTUSSIS_SUSPECT_SRC_GRP_KEY);
	retain PERTUSSIS_SUSPECT_SRC_FLD_KEY 1 	PERTUSSIS_SUSPECT_SRC_GRP_KEY 1;		
	if (PERTUSSIS_SUSPECT_SRC_FLD_KEY = 1) then do;
		output rdbdata.PERTUSSIS_SUSPECTED_SOURCE_GRP;
		output rdbdata.PERTUSSIS_SUSPECTED_SOURCE_FLD ;
		end;
	set suspected_batch;
	by root_obs_uid bat_obs_uid;

	if first.root_obs_uid then do;
		PERTUSSIS_SUSPECT_SRC_GRP_KEY+1;
		output rdbdata.PERTUSSIS_SUSPECTED_SOURCE_GRP  ;
		end;

	PERTUSSIS_SUSPECT_SRC_FLD_KEY + 1;
	output rdbdata.PERTUSSIS_SUSPECTED_SOURCE_FLD;
run;
		


/*******************************************************

	Key Update

********************************************************/
/***
	add	Pertussis_Treatment_Grp_Key and PERTUSSIS_SUSPECT_SRC_GRP_KEY
*/
proc sort data = rdbdata.PERTUSSIS_TREATMENT_FIELD out=PERTUSSIS_TREATMENT_FIELD0 nodupkey; by root_obs_uid Pertussis_Treatment_Grp_Key; run;
proc sort data = rdbdata.PERTUSSIS_SUSPECTED_SOURCE_FLD out=PERTUSSIS_SUSPECTED_SOURCE_FLD0 nodupkey; by root_obs_uid PERTUSSIS_SUSPECT_SRC_GRP_KEY;run;

proc sql;
create table rdbdata.Pertussis_Case1 as 
select 
		a.*,
		coalesce(b.Pertussis_Treatment_Grp_Key, 1) 		as Pertussis_Treatment_Grp_Key,
		coalesce(c.PERTUSSIS_SUSPECT_SRC_GRP_KEY, 1) 	as PERTUSSIS_SUSPECT_SRC_GRP_KEY
from	PERTUSSIS_Case	a
	left join	PERTUSSIS_TREATMENT_FIELD0 b
		on a.root_obs_uid = b.root_obs_uid
	left join	PERTUSSIS_SUSPECTED_SOURCE_FLD0 c
		on 	a.root_obs_uid = c.root_obs_uid
;
quit;



/* add comman investigation keys*/
%add_common_inv_keys(rdbdata.Pertussis_Case2,rdbdata.Pertussis_Case1);

/* LDF_Group_Key */
%AddLDFGroupKeytoFactTable (rdbdata.Pertussis_Case,rdbdata.Pertussis_Case2);


data rdbdata.PERTUSSIS_SUSPECTED_SOURCE_FLD;
set  rdbdata.PERTUSSIS_SUSPECTED_SOURCE_FLD (drop = ROOT_OBS_UID bat_obs_uid);
run;

data rdbdata.PERTUSSIS_TREATMENT_FIELD;
set rdbdata.PERTUSSIS_TREATMENT_FIELD (drop = ROOT_OBS_UID bat_obs_uid);
run;

proc sql;
   alter table rdbdata.Pertussis_Case
      add BIRTH_WEIGHT_UNKNOWN CHAR label='BIRTH_WEIGHT_UNKNOWN';
RUN;
PROC SORT DATA=rdbdata.Pertussis_Case NODUPKEY; BY  INVESTIGATION_KEY; RUN;

/*temporary solution in order to load*/
data rdbdata.Pertussis_Case;
set rdbdata.Pertussis_Case (drop=public_health_case_uid root_obs_uid);

*TRANSFER_FRM_HSPTL_KEY =1;

  /* assign missing keys */
  If LDF_Group_Key=. then LDF_Group_Key=1;
  IF WEIGHT_UNKNOWN_NUMERIC =-1.00000 THEN BIRTH_WEIGHT_UNKNOWN='Yes';
  /* drop unused keys */
  Drop
		INV_START_DT_KEY
		DIAGNOSIS_DT_KEY
		INV_RPT_DT_KEY
;
run;


proc datasets lib=work nolist;
	delete
	    PERObsInvFrmQ1
		PERObsInvFrmQ
		PERchildobscoded 
		PERchildobstext 
		PERchildobsdate 		
		PERchildobsnumeric1 	
		PERchildobsorphan
		PERcodedvaluesingle2 
		PERobstxt2 
		PERfrom_time2 
		PERNum1_2
		Itemobscoded 
		Itemobstext 		
		Itemobsdate 		
		Itemobsnumeric1 	
		Itemobsorphan
		Itemobscoded2
		Itemobsdate2  
		Itemobsnumeric1_2
		Itemobstext2
		per_treatment_code 
		per_infection_source_code
		per_infection_source_num 
		per_treatment_num
		per_treatment_date 
		per_infection_source_date
		per_infection_source_txt
		per_treatment_code2
		per_treatment_date2
		per_treatment_num2	
		per_infection_source_code2
		per_infection_source_date2
		per_infection_source_txt2
		per_infection_source_num2
		suspected_batch
		treatment_batch
		Pertussis_case
		Pertussis_suspected_source_fld0
		Perobstxt
		Pernum1
		Perrootobs
		Percodedvalue
		Perbatchitems1
		Perbatchitems
		Pertussis_treatment_field0
		Perfrom_time
		;
run;
quit;

/*

*/
