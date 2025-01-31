/************************************************
	Bmird_Case
	Observation data extraction
*************************************************/

%let whereclause=investigation_form_cd like 'INV_FORM_BMD%';

/* select only BMIRD observations */
/* send batch observations to a separate data set */
Data BMDObsInvFrmQ1;
 Set rdbdata.PHCchildobs_NONINC (where=(&whereclause)); 
	if child_obs_cd ~= 'ItemToRow';
run;

/* join non-batch observations with Codeset */
Proc SQL;
 Create table BMDObsInvFrmQ as 
  Select a.PUBLIC_HEALTH_CASE_UID,
		 a.ROOT_OBS_UID,
		 a.child_obs_uid,
		 a.child_obs_cd,
		 b.tbl_nm,
		 b.col_nm
   From BMDObsInvFrmQ1 a LEFT JOIN rdbdata.CodeSet b
     On a.child_obs_cd = b.cd
   Order by 1,2,3,4;
Quit;




/* for testing */
Data BMDObsInvFrmQ;
 Set BMDObsInvFrmQ;
 If col_nm = '' then col_nm = child_obs_cd;
Run;


/* separate data by response type (coded, numeric, text, date) */
Data BMDchildobscodedsingle 
     BMDchildobscodedmulti
     BMDchildobstext 
     BMDchildobsdate 		/*from_time*/
	  BMDchildobsHHMM			/*duration_amt*/
     BMDchildobsnumeric1 	
     BMDchildobsorphan;
 Set BMDObsInvFrmQ;

  Select (child_obs_cd);
    /* single-select coded questions */
    When (
          'BMD100',       /* ABCCASE */
          'BMD103',       /* TRANSFERED_IND */
          'BMD105',       /* DAYCARE_IND */
          'BMD107',       /* NURSING_HOME_IND */
          'BMD111',       /* PREGNANT_IND */
          'BMD112',       /* OUTCOME_OF_FETUS */
          'BMD113',       /* UNDER_1_MONTH_IND */
          'BMD120',       /* BACTERIAL_SPECIES_ISOLATED */
          'BMD121',       /* BACTERIAL_OTHER_ISOLATED */
          'BMD126',       /* UNDERLYING_CONDITION_IND */
          'BMD131',       /* CULTURE_SEROTYPE */
          'BMD132',       /* HIB_VACC_RECEIVED_IND */
          'BMD133',       /* CULTURE_SEROGROUP */
          'BMD135',       /* ATTENDING_COLLEGE_IND */
          'BMD137',       /* OXACILLIN_INTERPRETATION */
          'BMD138',       /* PNEUVACC_RECEIVED_IND */
          'BMD139',       /* PNEUCONJ_RECEIVED_IND */
          'BMD140',       /* PERSISTENT_DISEASE_IND */
          'BMD145',       /* PATIENT_HAD_SURGERY_IND */
          'BMD147',       /* PATIENT_HAVE_BABY_IND */
          'BMD150',       /* IDENT_THRU_AUDIT_IND */
          'BMD151',       /* SAME_PATHOGEN_RECURRENT_IND */
          'BMD155',       /* ISOLATE_SEND_TO_CDC_IND */
          'BMD157',       /* ISOLATE_SEND_TO_ST_IND */
          'BMD164',       /* PATIENT_YR_IN_COLLEGE */
          'BMD165',       /* PATIENT_STATUS_IN_COLLEGE */
          'BMD166',       /* PATIENT_CURR_LIVING_SITUATION */
          'BMD169',       /* POLYSAC_MENINGOC_VACC_IND */
          'BMD171',       /* FAMILY_MEDICAL_INSURANCE_TYPE */
          'BMD175',       /* HIB_CONTACT_IN_LAST_2_MON_IND */
          'BMD208',       /* ACUTE_SERUM_AVAILABLE_IND */
          'BMD210',       /* CONVALESNT_SERUM_AVAILABLE_IND */
          'BMD217',       /* BIRTH_OUTSIDE_HSPTL_IND */
          'BMD218',       /* BIRTH_OUTSIDE_HSPTL_LOCATION */
          'BMD221',       /* BABY_SAME_HSPTL_READMIT_IND */
          'BMD223',       /* FRM_HOME_TO_DIF_HSPTL_IND */
          'BMD230',       /* MOTHER_PENICILLIN_ALLERGY_IND */
          'BMD232',       /* MEMBRANE_RUPTURE_GE_18HRS_IND */
          'BMD233',       /* RUPTURE_BEFORE_LABOR_ONSET */
          'BMD234',       /* MEMBRANE_RUPTURE_TYPE */
          'BMD235',       /* DELIVERY_TYPE */
          'BMD236',       /* MOTHER_INTRAPARTUM_FEVER_IND */
          'BMD238',       /* RECEIVED_PRENATAL_CARE_IND */
          'BMD239',       /* PRENATAL_CARE_IN_LABOR_CHART */
          'BMD244',       /* GBS_BACTERIURIA_IN_PREGNANCY */
          'BMD245',       /* PREVIOUS_BIRTH_WITH_GBS_IND */
          'BMD246',       /* GBS_CULTURED_BEFORE_ADMISSION */
          'BMD249',       /* GBS_1ST_CULTURE_POSITIVE_IND */
          'BMD252',       /* GBS_2ND_CULTURE_POSITIVE_IND */
          'BMD253',       /* GBS_AFTER_ADM_BEFORE_DELIVERY */
          'BMD256',       /* GBS_CULTURE_POSITIVE_IND */
          'BMD257',       /* GBS_CULTURE_DELIVERY_AVAILABLE */
          'BMD258',       /* INTRAPARTUM_ANTIBIOTICS_GIVEN */
          'BMD266',       /* INTRAPARTUM_ANTIBIOTICS_REASON */
		  'BMD269',       /* CASE_REPORT_STATUS */
          'BMD271',       /* NEISERRIA_2NDARY_CASE_IND */
          'BMD272',       /* NEISERRIA_2ND_CASE_CONTRACT */
          'BMD274',       /* NEISERRIA_RESIST_TO_RIFAMPIN */
          'BMD275',       /* NEISERRIA_RESIST_TO_SULFA */
		  'BMD276',        /* BIRTH_CNTRY */
		  /*The following were added in 1.1.3*/
		  'BMD295',        /*INTBODYSITE*/
		  'BMD300',	      /*HINFAGE*/		  	
 		  'BMD313'    /*SP1 Specific Has patient received the conjugate meningococcal vaccine?*/	
          )
          output BMDchildobscodedsingle;

    /* multi-select coded questions */
    When (
          'BMD118',       /* TYPES_OF_INFECTIONS */
          'BMD122',       /* STERILE_SITE */
          'BMD125',       /* NON_STERILE_SITE */
          'BMD127',       /* UNDERLYING_CONDITION_NM */
          'BMD142',       /* STREP_PNEUMO_1_CULTURE_SITES */
          'BMD144',       /* STREP_PNEUMO_2_CULTURE_SITES */
          'BMD149',       /* OTHER_PROIR_CONDITION_IND */          /*** multi-select ??? - error in mapping doc ***/
          'BMD161',       /* How_was_case_identified  */
		  'BMD177',       /* PAST_SIGNIFICANT_MEDICAL_HIST */
          'BMD248',       /* GBS_1ST_CULTURE_SITES */
          'BMD251',       /* GBS_2ND_CULTURE_SITES */
          'BMD255',        /* AFTER_ADM_GBS_CULTURE_SITES */
		  /*The following were added in 1.1.3*/
		  'BMD292',  		/*OTHERBUG2*/	
		  'BMD312',      /*SP1 Specific TYPE OF INSURANCE */
		  'BMD308'		/*SP1 Specific Specify PCR Source*/
			)
          output BMDchildobscodedmulti;

    /* text questions */
    When (
	 		 'INV172',       /* INV_PATIENT_CHART_NBR */
          'BMD119',       /* TYPES_OF_OTHER_INFECTION */
          'BMD123',       /* STERILE_SITE_OTHER */
          'BMD128',       /* OTHER_MALIGNANCY */
          'BMD129',       /* ORGAN_TRANSPLANT */
          'BMD130',       /* UNDERLYING_CONDITIONS_OTHER */
          'BMD134',       /* CULTURE_SEROGROUP_OTHER */
		  'BMD152',       /* Birth_Cntry*/
          'BMD162',       /* OTHER_SPECIES_ISOLATE_SITE */
          'BMD163',       /* OTHER_CASE_IDENT_METHOD */
          'BMD167',       /* OTHER_HOUSING_OPTION */
          'BMD168',       /* PATIENT_CURR_ATTEND_SCHOOL_NM */
          'BMD172',       /* FAMILY_MED_INSURANCE_TYPE_OTHE */
          'BMD176',       /* TYPE_HIB_CONTACT_IN_LAST_2_MON */
          'BMD179',       /* IMMUNOSUPRESSION_HIV_STATUS */
          'BMD180',       /* OTHER_PRIOR_ILLNESS */
          'BMD226',       /* MOTHER_LAST_NM */
          'BMD227',       /* MOTHER_FIRST_NM */
          'BMD229',       /* MOTHER_PATIENT_CHART_NBR */
          'BMD268',       /* BACTERIAL_OTHER_SPECIED */
          'BMD273',       /* OTHER_2NDARY_CASE_TYPE */
		  /*The following were added in 1.1.3*/
		  'BMD293',       /*OTHSPEC1*/
          'BMD294',       /*OTHSPEC2*/	
		  'BMD296',		  /*OTHILL2*/
		  'BMD297',		  /*OTHILL3*/
		  'BMD298',		  /*OTHNONSTER*/
		  'BMD299',        /*OTHSEROTYPE*/
		  /*Can't add ABCs Investigator as a key because of SQL Server has a key limit of 16*/
		  'BMD302',       /*ABCsINVLN*/
		  'BMD303',       /*ABCsINVFN*/
		  'BMD304',		  /*ABCsINVEMAIL*/	
		  'BMD305',		  /*ABCsINVTELE*/ 
		  'BMD306',		  /*ABCsINVEXT*/
		  'BMD318',			/*SP1 Specific OTH_STREP_PNEUMO_1_CULTURE_SITES*/
		  'BMD319',			/*SP1 Specific OTH_STREP_PNEUMO_2_CULTURE_SITES*/
		  'BMD309',         /*SP1 Specific IHC specimen Speciment 1*/
		  'BMD310',			/*SP1 Specific IHC specimen Speciment 2*/
		  'BMD311',			/*SP1 Specific IHC specimen Speciment 3*/
		  'BMD317'			/*SP1 Specific  OTH_TYPE_OF_ISURANCE*/
		) 
          output BMDchildobstext;

    /* date questions */
    When (
          'BMD124',       /* FIRST_POSITIVE_CULTURE_DT */
          'BMD141',       /* FIRST_ADDITIONAL_SPECIMEN_DT */
          'BMD143',       /* SECOND_ADDITIONAL_SPECIMEN_DT */
          'BMD146',       /* SURGERY_DT */
          'BMD148',       /* BABY_DELIVERY_DT */
          'BMD154',       /* SPECIMEN_COLLECT_DT */
          'BMD156',       /* ISOLATE_SEND_TO_CDC_DT */
          'BMD158',       /* ISOLATE_SEND_TO_ST_DT */
          'BMD209',       /* ACUTE_SERUM_AVAILABLE_DT */
          'BMD211',       /* CONVALESNT_SERUM_AVAILABLE_DT */
          'BMD220',       /* BABY_HSPTL_DISCHARGE_DTTIME */
          'BMD222',       /* BABY_SAME_HSPTL_READMIT_DTTIME */
          'BMD225',       /* FRM_HOME_TO_DIF_HSPTL_DTTIME */
          'BMD228',       /* MOTHER_HOSPTL_ADMISSION_DTTIME */
          'BMD231',       /* MEMBRANE_RUPTURE_DTTIME */
          'BMD237',       /* FIRST_INTRAPARTUM_FEVER_DTTIME */
          'BMD241',       /* FIRST_PRENATAL_CARE_VISIT_DT */
          'BMD242',       /* LAST_PRENATAL_CARE_VISIT_DT */
          'BMD247',       /* GBS_1ST_CULTURE_DT */
          'BMD250',       /* GBS_2ND_CULTURE_DT */
          'BMD254',       /* AFTER_ADM_GBS_CULTURE_DT */
          'BMD259',       /* FIRST_ANTIBIOTICS_GIVEN_DTTIME */
		  'BMD307'       /* SAMPLE_COLLECTION_DT */
) 
          output BMDchildobsdate;

	/* HH:MM questions incorrectly mapped to obs_value_date*/
	When (
	      'BMD267',       /* BABY_BIRTH_TIME */
			'BMD285',       /* FIRST_HSPTL_DISCHARGE_TIME */
			'BMD286',       /* FIRST_HSPTL_READMISSION_TIME */
			'BMD287',       /* SECOND_HSPTL_ADMISSION_TIME */
			'BMD288',       /* HSPTL_MATERNAL_ADMISSION_TIME */
			'BMD289',       /* MEMBRANE_RUPTURE_TIME */
			'BMD290',       /* INTRAPARTUM_FEVER_RECORD_TIME */
			'BMD291'        /* ANTIBIOTICS_1ST_ADMIN_TIME */
	 )	
	 output BMDchildobsHHMM;

	 /* numeric questions */
    When (
          'BMD114',       /* GESTATIONAL_AGE */
          'BMD115',       /* BIRTH_WEIGHT_IN_GRAMS */
          'BMD116',       /* BIRTH_WEIGHT_POUNDS */
          'BMD117',       /* BIRTH_WEIGHT_OUNCES */
          'BMD136',       /* OXACILLIN_ZONE_SIZE */
          'BMD178',       /* PRETERM_BIRTH_WK_NBR */
          'BMD240',       /* PRENATAL_CARE_VISIT_NBR */
          'BMD243',       /* LAST_PRENATAL_CARE_VISIT_EGA */
          'BMD265',        /* INTRAPARTUMANTIBIOTICSINTERVAL */
		  'BMD320',		/*SP1 Specific WEIGHT_IN_LBS*/
		  'BMD321',		/*SP1 Specific WEIGHT_IN_OZ*/
		  'BMD322',		/*SP1 Specific WEIGHT_IN_KG*/
		  'BMD326',		/*SP1 Specific WEIGHT_IN_UNKNOWN*/
		  'BMD323',		/*SP1 Specific HEIGHT_IN_FT*/
		  'BMD324',		/*SP1 Specific HEIGHT_IN_IN*/
		  'BMD325',		/*SP1 Specific HEIGHT_IN_CM*/
		  'BMD327'		/*SP1 Specific HEIGHT_IN_UNKNOWN*/
          )
          output BMDchildobsnumeric1;

    /* known orphan questions */
*    When (
          'BMD101',       /* INV_ST_CASE_ID */
          'BMD102',       /* ORG_LOCAL_ID */
          'BMD104',       /* ORG_LOCAL_ID */
          'BMD106',       /* ORG_NM */
          'BMD108',       /* ORG_NM */
          'BMD109',       /* DIE_FRM_THIS_ILLNESS_IND */
          'BMD152',       /* STATE_FIPS */
          'BMD161',       /* INV_LOCAL_ID */  	/*???, should not be orphan, INV_LOCAL_ID already mapped to PHC.local_id  */
          'BMD219',       /* BIRTH_HSPTL_ID */
          'BMD224',       /* DIF_HSPTL_ID */
          'BMD269',       /* INV_CASE_STATUS */
          'BMD277',       /* ORG_NM */
          'BMD278',       /* ORG_NM_TYPE */
          'BMD279',       /* ORG_NM */
          'BMD280',       /* ORG_NM_TYPE */
          'BMD281',       /* ORG_NM */
          'BMD282',       /* ORG_NM_TYPE */
          'BMD283',       /* ORG_NM */
          'BMD284',       /* ORG_NM_TYPE */

		  /*these item should be linked to batch row obs only */
          'BMD212',       /* ANTIMICROBIAL_AGENT_TESTED_IND */
          'BMD213',       /* SUSCEPTABILITY_METHOD */
          'BMD214',       /* S_I_R_U_RESULT */
          'BMD215',       /* MIC_SIGN */
          'BMD216',       /* MIC_VALUE */
          'BMD260',       /* INTRAPARTUM_ANTIBIOTICS_NM */
          'BMD261',       /* INTRAPARTUM_ANTIBIOTICS_METHOD */
          'BMD262',       /* INTRAPARTUMANTIBIOTICS_STARTDT */
          'BMD263',       /* INTRAPARTUMANTIBIOTICS_STOPDT */
          'BMD264'        /* INTRAPARTUM_ANTIBIOTICS_DOSE */
          )
          output BMDchildobsorphan;

	Otherwise 
		  output BMDchildobsorphan;
  End;
Run;


/* get observation values (coded single, coded multi, text, date, numeric) */
%getobscode(BMDcodedvaluesingle,BMDchildobscodedsingle);
%getobstxt(BMDobstxt,BMDchildobstext);
%getobsdate(BMDfrom_time,BMDchildobsdate,from_time);
%getobsdate(BMDduration_amt,BMDchildobsHHMM,duration_amt); 
%getobsnum(BMDNum1, BMDchildobsnumeric1,numeric_value_1);

/*BMD265 concatenates three fields in obs_value_numeric */
data BMDNum1 BMD265_1;
set BMDNum1;
	if child_obs_cd='BMD265' then output BMD265_1;
	else output BMDNum1;
run;
proc sql;
create table BMD265 as 
select	a.public_health_case_uid,
		a.root_obs_uid,
		trim(left(put(b.numeric_value_1, 8.))) || trim(b.separator_cd) ||left(put(numeric_value_2, 8.)) 
			as INTRAPARTUMANTIBIOTICSINTERVAL
		
from	BMD265_1 as a,
		nbs_ods.obs_value_numeric as b
	where a.child_obs_uid = b.observation_uid
	order by 1,2
;
quit;

/* transpose each response data set */
%rows_to_columns(BMDcodedvaluesingle,BMDcodedvaluesingle_trans);
%rows_to_columns(BMDobstxt,BMDobstxt_trans);
%rows_to_columns(BMDfrom_time,BMDfrom_time_trans);
%rows_to_columns(BMDduration_amt,BMDduration_amt_trans);
%rows_to_columns(BMDNum1,BMDNum1_trans);

/*BMD267 NBS_ODS.Obs_Value_Date.Duration_Amt*/
/*
data  bmdduration_amt (keep = public_health_case_uid root_obs_uid Baby_Birth_Time);
set bmdduration_amt (rename = (response = Baby_Birth_Time)) ;
where child_obs_cd = 'BMD267';
run;
data BMDfrom_time_trans (drop=Baby_Birth_Time) ;
set BMDfrom_time_trans;run;
data BMDfrom_time_trans;
merge bmdduration_amt BMDfrom_time_trans;
by  public_health_case_uid root_obs_uid; run; 
*/

/* combine transposed response data sets with root data to form complete BMIRD Case Fact table */
Data Bmird_Case1;
  Merge BMDcodedvaluesingle_trans BMDobstxt_trans BMDfrom_time_trans BMDNum1_trans BMDduration_amt_trans BMD265;
  By public_health_case_uid root_obs_uid; 
Run;

data bmdphcrootobs;
set rdbdata.PHCrootobs_NONINC;
where &whereclause;
run;

/* update fields from PHC Root*/
proc sql;
create table Bmird_Case as 
select 	phc.diagnosis_time as FIRST_POSITIVE_CULTURE_DT,
		phc.public_health_case_uid as public_health_case_uid0,
		phc.root_obs_uid			as root_obs_uid0,
		act.root_extension_txt	as abc_state_case_id,
		bmd.*
from 	bmdphcrootobs	phc
		left join Bmird_Case1 bmd
	on 	phc.public_health_case_uid = bmd.public_health_case_uid

		left join nbs_ods.act_id act
	on phc.public_health_case_uid = act.act_uid 
	and act.act_id_seq=2 /*abcs state id*/
;
quit;

data Bmird_Case;
set Bmird_Case (drop=public_health_case_uid root_obs_uid);
rename 
	public_health_case_uid0 =public_health_case_uid
	root_obs_uid0 = root_obs_uid
	;
run;
	

/*******************************************************
	BMIRD_MULTI_Value_Field	Dimension and 
	BMIRD_MULTI_Value_Group Bridge
********************************************************/

%getobscode(BMDcodedvaluemulti,BMDchildobscodedmulti);

Proc Sort data=BMDcodedvaluemulti; 
  By public_health_case_uid root_obs_uid child_obs_uid; 
Run;

data BMDcodedvaluemulti;
	retain cd_seq;
	set BMDcodedvaluemulti;
	by public_health_case_uid root_obs_uid child_obs_uid;
	if first.child_obs_uid then cd_seq=1;
	else cd_seq+1;
	output;
run;


/* transpose so column names go across */
%rows_to_columns2(BMDcodedvaluemulti,BMDcodedvaluemultitrans, response, 
                  public_health_case_uid root_obs_uid cd_seq);


/*pk has different name with table? problem with some tools?*/
data rdbdata.BMIRD_MULTI_Value_Field (drop=public_health_case_uid root_obs_uid cd_seq)
	rdbdata.BMIRD_MULTI_Value_Field_Group (keep=public_health_case_uid root_obs_uid BMIRD_MULTI_VAL_GRP_KEY);

	Retain BMIRD_MULTI_Val_Field_Key 1 
		   BMIRD_MULTI_VAL_GRP_KEY 1;

	if (BMIRD_MULTI_Val_Field_Key=1) then do;
		output rdbdata.BMIRD_MULTI_Value_Field;
		output rdbdata.BMIRD_MULTI_Value_Field_Group;
	end;
	Set BMDcodedvaluemultitrans;
	By public_health_case_uid root_obs_uid cd_seq;

    /* create BMIRD_Multi_Value_Field_Group table */
	if first.root_obs_uid then do;
		BMIRD_MULTI_VAL_GRP_KEY +1;
		output rdbdata.BMIRD_MULTI_Value_Field_Group;
	end;

    /* create BMIRD_Multi_Value_Field table */
	BMIRD_MULTI_Val_Field_Key +1;
	output rdbdata.BMIRD_MULTI_Value_Field;
run; 

/* assign multi value key in fact table */
Proc SQL;
 Create table rdbdata.BMIRD_Case1 as
  Select f.*, m.BMIRD_MULTI_VAL_GRP_KEY
   From BMIRD_Case f LEFT JOIN rdbdata.BMIRD_MULTI_Value_Field_Group m
     On f.public_health_case_uid=m.public_health_case_uid
    and f.root_obs_uid=m.root_obs_uid;
Quit;

proc datasets lib=work nolist;
	delete
		BMIRD_Case1
		BMIRD_Case
	;
run;

/* clean up bridge table */
Data rdbdata.BMIRD_MULTI_Value_Field_Group;
  Set rdbdata.BMIRD_MULTI_Value_Field_Group (keep=BMIRD_MULTI_VAL_GRP_KEY);
Run;


/*******************************************************

	Batch Entry

********************************************************/
Data BmdBatchItems1;
set rdbdata.BatchItem_NONINC (where=(&whereclause)); 
run;
Proc SQL;
Create table BmdBatchItems as
Select 	phc.*,
		c.col_nm
From 	BmdBatchItems1 phc 
	LEFT JOIN rdbdata.Codeset c 
		On c.cd = phc.item_obs_cd;
Quit;

/* Batch item observations*/
Data Itemobscoded 
     /* Itemobstext */ 		/*BMD should not have batch item txt */
     Itemobsdate 			/*from_time*/
	 Itemobsnumeric1
     Itemobsorphan;
	 
 Set BmdBatchItems;
	rename 	child_obs_cd = bat_obs_cd
			child_obs_uid= bat_obs_uid
			item_obs_cd  = child_obs_cd
			item_obs_uid = child_obs_uid
			;
  Select (item_obs_cd);
    /* single-select coded questions */
    When (
          'BMD212',       /* ANTIMICROBIAL_AGENT_TESTED_IND */
          'BMD213',       /* SUSCEPTABILITY_METHOD */
          'BMD214',       /* S_I_R_U_RESULT */
          'BMD215',       /* MIC_SIGN */
          'BMD260',       /* INTRAPARTUM_ANTIBIOTICS_NM */
          'BMD261'        /* INTRAPARTUM_ANTIBIOTICS_METHOD */
          )
          output Itemobscoded;

    /* text questions */
*    When (
          ) 
          output Itemobstext;

    /* date questions */
    When (
          'BMD262',       /* INTRAPARTUMANTIBIOTICS_STARTDT */
          'BMD263'        /* INTRAPARTUMANTIBIOTICS_STOPDT */
          ) 
          output Itemobsdate;

    /* numeric questions */
    When (
          'BMD216',       /* MIC_VALUE */
          'BMD264'        /* INTRAPARTUM_ANTIBIOTICS_DOSE */
          )
          output Itemobsnumeric1;

	Otherwise 
		  output ItemobsOrphan;
  End;
Run;

/* Batch item values*/
%getobscode(Itemobscoded2,Itemobscoded);
%getobsdate(Itemobsdate2,Itemobsdate, from_time);  
%getobsnum(Itemobsnumeric1_2, Itemobsnumeric1, numeric_value_1);

/* Separate the two batches and transpose rows into columns format */
data antimicrobial_code 
	 antibiotic_code;

 Set Itemobscoded2;
	select (child_obs_cd);
		when (
			'BMD212', 	/*antimicrobial agent*/
			'BMD213', 	/*susceptibility method*/
			'BMD214', 	/*S/I/R/U Result*/
			'BMD215')	/*sign */
			output Antimicrobial_code;
		
		when(
			'BMD260',	/*antibiotic*/	 
			'BMD261' )	/*method*/
			output antibiotic_code;
		otherwise;
	end; 
run;

%rows_to_columns2(antimicrobial_code,antimicrobial_code2,response,root_obs_uid bat_obs_uid);
%rows_to_columns2(antibiotic_code,antibiotic_code2,response,root_obs_uid bat_obs_uid);

data antimicrobial_num 
	 antibiotic_num;
set Itemobsnumeric1_2;
	if (child_obs_cd='BMD216')			/*MIC Value*/ 
		then output antimicrobial_num;		
	else if (child_obs_cd='BMD264')		/*Doese Given Before Delivery*/
		then output antibiotic_num;
run;
%rows_to_columns2(antimicrobial_num,antimicrobial_num2, response,root_obs_uid bat_obs_uid);
%rows_to_columns2(antibiotic_num,antibiotic_num2, response,root_obs_uid bat_obs_uid);

/*antimicro has no date field*/
%rows_to_columns2(Itemobsdate2,antibiotic_date,response,root_obs_uid bat_obs_uid);

/*******************************************************

	Antimicrobial Dimension
	Antimicrobial_Group Bridge

********************************************************/
data rdbdata.Antimicrobial_merge;
merge antimicrobial_code2 antimicrobial_num2;          
	by root_obs_uid bat_obs_uid;
run;
PROC SORT DATA=rdbdata.Antimicrobial_merge; BY root_obs_uid bat_obs_uid; RUN;
data rdbdata.Antimicrobial
	rdbdata.Antimicrobial_Group (keep=Antimicrobial_Grp_Key);
	retain Antimicrobial_Key 1 	Antimicrobial_Grp_Key 1 Antimicrobial_GRP_UID_Key 1;		
	if (Antimicrobial_Key = 1) then do;
		output rdbdata.Antimicrobial;
		output rdbdata.Antimicrobial_Group ;
		end;
	set rdbdata.Antimicrobial_merge; 
		if root_obs_uid = . then stop;
	if root_obs_uid then do;
	if Antimicrobial_GRP_UID_Key NE root_obs_uid
	then do;
		Antimicrobial_Grp_Key+1;
		Antimicrobial_GRP_UID_Key =root_obs_uid; 
		output rdbdata.Antimicrobial_Group;
		end;
		Antimicrobial_Key + 1;
		output rdbdata.Antimicrobial;
	end;
run;
proc sql;
create table rdbdata.antimicrobial_code2 as
select * from antimicrobial_code2;

quit;
proc sql;
create table rdbdata.antimicrobial_num2 as
select * from antimicrobial_num2;

quit;
		

/*******************************************************

	Antibiotics Dimension
	Antibiotics_Group Bridge

********************************************************/
/*Antibiotics Group
data rdbdata.Antibiotics
	rdbdata.Antibiotics_Group (keep=Antibiotics_Grp_Key);
	retain Antibiotics_Key 1 	Antibiotics_Grp_Key 1;		
	if (Antibiotics_Key = 1) then do;
		output rdbdata.Antibiotics;
		output rdbdata.Antibiotics_Group;
		end;
	set antibiotic_code2; 
	set antibiotic_num2;	
	set antibiotic_date;
	by root_obs_uid bat_obs_uid;
	if root_obs_uid = . then stop;
	if first.root_obs_uid then do;
		Antibiotics_Grp_Key+1;
		output rdbdata.Antibiotics_Group;
		end;

	Antibiotics_Key + 1;
	output rdbdata.Antibiotics;
run;
*/
/*******************************************************

	Key Update

********************************************************/
/***
	add	Antimicrobial_Grp_Key and Antibiotics_Grp_Key
*/
proc sort data = rdbdata.Antimicrobial out=Antimicrobial0 nodupkey; by root_obs_uid Antimicrobial_Grp_Key; run;

proc sql;
create table rdbdata.Bmird_Case2 as 
select 
		a.*,
		coalesce(b.Antimicrobial_Grp_Key, 1) as Antimicrobial_Grp_Key
from	rdbdata.Bmird_Case1	a
	left join	Antimicrobial0 b
		on a.root_obs_uid = b.root_obs_uid
;
quit;

/* add comman investigation keys*/
%add_common_inv_keys(rdbdata.Bmird_Case3,rdbdata.Bmird_Case2);
%macro add_Inv_OrgKeys(fact_table,fact_table1,fact_org_key, par_type_cd);
proc sql;
create table &fact_table as 
select 	a.*,
	org1.organization_key	as &fact_org_key
from	&fact_table1 a 
	left join nbs_ods.participation as par 
		on a.public_health_case_uid = par.act_uid 
		and par.type_cd= &par_type_cd 
		and par.act_class_cd='CASE' 
		and par.subject_class_cd='ORG' 
	left join nbs_rdb.L_Organization as org1 
	on par.subject_entity_uid = org1.organization_uid;
quit;
%mend add_Inv_OrgKeys;

proc sql;
create table rdbdata.Bmird_Case4 as 
select 	a.*,
		org1.organization_name 'TRANSFER_FRM_HSPTL_NAME ' as TRANSFER_FRM_HSPTL_NAME ,
		org1.organization_LOCAL_ID 'TRANSFER_FRM_HSPTL_ID ' as TRANSFER_FRM_HSPTL_ID ,
		org2.organization_name 'Culture_ident_org_name' as Culture_ident_org_name,
		org3.organization_name 'INITIAL_HSPTL_NAME' as INITIAL_HSPTL_NAME,
		org4.organization_name 'BIRTH_HSPTL_NAME ' as BIRTH_HSPTL_NAME ,
		org5.organization_name 'From_home_hsptl_name' as From_home_hsptl_name,
		org6.organization_name 'PATIENT_CURR_ATTEND_SCHOOL_NM'	as PATIENT_CURR_ATTEND_SCHOOL_NM,
		org7.organization_name 'TREATMENT_HOSPITAL_NM'	as TREATMENT_HOSPITAL_NM,
		org7.organization_key 'TREATMENT_HOSPITAL_KEY' as TREATMENT_HOSPITAL_KEY
from	rdbdata.Bmird_Case3 as a

	/*Transfer_Frm_Hsptl_name*/
	left join nbs_ods.participation as par
		on a.public_health_case_uid = par.act_uid
		and par.type_cd= 'TransferHosp'
		and par.act_class_cd='CASE'
		and par.subject_class_cd='ORG'
	left join nbs_rdb.d_Organization as org1
		on par.subject_entity_uid = org1.organization_uid
	

	/*CULTURE_IDENT_ORG_NAME*/
	left join nbs_ods.participation as par1
		on a.public_health_case_uid = par1.act_uid
		and par1.type_cd= 'HospOfCulture'
		and par1.act_class_cd='CASE'
		and par1.subject_class_cd='ORG'
	left join nbs_rdb.d_Organization as org2
		on par1.subject_entity_uid = org2.organization_uid

	/*INITIAL_HSPTL_KEY*/
	left join nbs_ods.participation as par2
		on a.public_health_case_uid = par2.act_uid
		and par2.type_cd= 'TransferHosp'
		and par2.act_class_cd='CASE'
		and par2.subject_class_cd='ORG'
	left join nbs_rdb.d_Organization as org3
		on par2.subject_entity_uid = org3.organization_uid
	
	/*BIRTH_HSPTL_KEY*/
	left join nbs_ods.participation as par3
		on a.public_health_case_uid = par3.act_uid
		and par3.type_cd= 'HospOfBirth'
		and par3.act_class_cd='CASE'
		and par3.subject_class_cd='ORG'
	left join nbs_rdb.d_Organization as org4
		on par3.subject_entity_uid = org4.organization_uid
	

	/*FROM_HOME_HSPTL_KEY*/
	left join nbs_ods.participation as par4
		on a.public_health_case_uid = par4.act_uid
		and par4.type_cd= 'ReAdmHosp'
		and par4.act_class_cd='CASE'
		and par4.subject_class_cd='ORG'
	left join nbs_rdb.d_Organization as org5
		on par4.subject_entity_uid = org5.organization_uid
	
	/*School name*/
	left join nbs_ods.participation as par5
		on a.public_health_case_uid = par5.act_uid
		and par5.type_cd= 'CollegeUniversity'
		and par5.act_class_cd='CASE'
		and par5.subject_class_cd='ORG'
	left join nbs_rdb.d_Organization as org6
		on par5.subject_entity_uid = org6.organization_uid

	/*Treatment Hospital name*/
	left join nbs_ods.participation as par6
		on a.public_health_case_uid = par6.act_uid
		and par6.type_cd= 'TreatmentHosp'
		and par6.act_class_cd='CASE'
		and par6.subject_class_cd='ORG'
	left join nbs_rdb.d_Organization as org7
		on par6.subject_entity_uid = org7.organization_uid


;
quit;
run;

/* patient's mother names, keeps only the latest one */
proc sql;
create table mother as 
select 	a.public_health_case_uid,
		pn1.first_nm			as MOTHER_FIRST_NM,
		pn1.last_nm			as MOTHER_LAST_NM,
		pn1.as_of_date as PERSON_NM_AS_OF_DT


from	rdbdata.Bmird_Case4 as a
	inner join nbs_ods.participation as par
		on a.public_health_case_uid = par.act_uid
	inner join nbs_ods.person	as per
		on par.type_cd = 'MaternalPSN'
		and par.subject_entity_uid = per.person_uid
	inner join nbs_ods.person_name	as pn1
		on per.person_uid = pn1.person_uid
;
quit;
proc sort data=mother nodupkey; 
by public_health_case_uid /*descending PERSON_NM_AS_OF_DT*/ ;
run;
data mother;
set mother;
by public_health_case_uid PERSON_NM_AS_OF_DT;
if first.PERSON_NM_AS_OF_DT then output;  
run;

proc sql;
create table rdbdata.Bmird_Case5 as 
select 	a.*,
		m.MOTHER_FIRST_NM,
		m.MOTHER_LAST_NM
from	rdbdata.Bmird_Case4 as a
	left join mother as m
	on	a.public_health_case_uid = m.public_health_case_uid
;
quit;




/******************START: Add ABCs Investigator******************************/
proc sql;
create table ABCs_Investigator as 
	SELECT b.public_health_case_uid,
		p.PROVIDER_key,	
		p.PROVIDER_LAST_NAME 'ABCsINVLN' as ABCsINVLN,
		p.PROVIDER_FIRST_NAME  'ABCsINVFN' as ABCsINVFN , 
		p.PROVIDER_EMAIL_WORK 'ABCsINVEMAIL' as ABCsINVEMAIL,
		p.PROVIDER_PHONE_WORK 'ABCsINVTELE'  as ABCsINVTELE,
		p.PROVIDER_PHONE_EXT_WORK 'ABCsINVEXT' as ABCsINVEXT
	FROM rdbdata.Bmird_Case5 b, 
	nbs_ods.participation par, 
	nbs_rdb.d_Provider p
	where b.public_health_case_uid = par.act_uid 
	and par.subject_entity_uid  = p.PROVIDER_UID 
	and par.type_cd = 'ABCInvestgrOfPHC';
quit;
proc sort data = ABCs_Investigator; by public_health_case_uid; quit;
data rdbdata.BMIRD_CASE6;
merge rdbdata.BMIRD_Case5 ABCs_Investigator;
by public_health_case_uid; run;
proc sort data = rdbdata.BMIRD_CASE6 nodupkey;
by public_health_case_uid; quit;
/******************END: Add ABCs Investigator******************************/


/* Transfer_Frm_Hsptl_Key */
*%add_Inv_OrgKeys(rdbdata.Bmird_Case, Transfer_Frm_Hsptl_Key,'TransferHosp');

/*Nursing_Home_Key and Chronic Care*/
%add_Inv_OrgKeys(rdbdata.Bmird_Case7,rdbdata.Bmird_Case6, Nursing_Home_Key, 'ChronicCareFac'); 

/* Daycare_Facility_Key */
%add_Inv_OrgKeys(rdbdata.Bmird_Case8,rdbdata.Bmird_Case7, Daycare_Facility_Key, 'DaycareFac'); 


/* HospOfCulture_Key */
*%add_Inv_OrgKeys(rdbdata.Bmird_Case, CULTURE_IDENT_ORG_KEY, 'HospOfCulture');

/* INITIAL_HSPTL_KEY BMD277 and BMD104, PS107, Instance: Organization(TransferHosp)
Same as the Transfer_Frm_Hsptl_Key???*/
*%add_Inv_OrgKeys(rdbdata.Bmird_Case, INITIAL_HSPTL_KEY, 'TransferHosp');

/* BIRTH_HSPTL_KEY, BMD219, PS119 */
*%add_Inv_OrgKeys(rdbdata.Bmird_Case, BIRTH_HSPTL_KEY, 'HospOfBirth');

/* FROM_HOME_HSPTL_KEY: BMD283, PS119, instance of org(ReAdmHosp)*/
*%add_Inv_OrgKeys(rdbdata.Bmird_Case, FROM_HOME_HSPTL_KEY, 'ReAdmHosp');

/* LDF_Group_Key */
%AddLDFGroupKeytoFactTable (rdbdata.BMIRD_CASE,rdbdata.BMIRD_Case8);


/*Accesssion_Lab_Key*/



proc sql;
   alter table rdbdata.Bmird_Case
      add HEIGHT_UNKNOWN CHAR label='HEIGHT_UNKNOWN',
      WEIGHT_UNKNOWN CHAR label='WEIGHT_UNKNOWN';
RUN;


/* clean up fact table */
Data rdbdata.Bmird_Case;
	Set rdbdata.Bmird_Case (drop=public_health_case_uid root_obs_uid); 

	/* assign missing keys */
	If BMIRD_MULTI_VAL_GRP_KEY=. then BMIRD_MULTI_VAL_GRP_KEY=1;
	If Nursing_Home_Key=. then Nursing_Home_Key=1;
	If Daycare_Facility_Key=. then Daycare_Facility_Key=1;
	If LDF_Group_Key=. then LDF_Group_Key=1;
	IF HEIGHT_UNKNOWN_NUMERIC=-1.00000 THEN HEIGHT_UNKNOWN='Yes';
	IF WEIGHT_UNKNOWN_NUMERIC=-1.00000 THEN WEIGHT_UNKNOWN='Yes';

	/* drop unused keys */
	 drop
			INV_RPT_DT_KEY
			DIAGNOSIS_DT_KEY
			INV_START_DT_KEY
	;
Run;
PROC SORT DATA=rdbdata.Bmird_Case NODUPKEY; BY  INVESTIGATION_KEY; RUN;


/***************************************************/

Data RDBDATA.Antimicrobial;
set RDBDATA.Antimicrobial;
	drop 
		root_obs_uid
		bat_obs_uid
	;
run;
PROC datasets library = work nolist;
delete
BMDObsInvFrmQ
BMDObsInvFrmQ1
BMDchildobscodedsingle 
BMDchildobscodedmulti
BMDchildobstext 
BMDchildobsdate 
BMDchildobsHHMM		
BMDchildobsnumeric1 	
BMDchildobsorphan
BMDcodedvaluesingle
BMDobstxt
BMDfrom_time
BMDduration_amt
BMDNum1
BMD265
BMD265_1
BMDcodedvaluesingle_trans
BMDobstxt_trans
BMDfrom_time_trans
BMDduration_amt_trans
BMDNum1_trans
Bmird_Case
BMDcodedvaluemulti
BmdBatchItems
BmdBatchItems1
Itemobscoded 
Itemobsdate
Itemobsnumeric1
Itemobsorphan
Itemobscoded2
Itemobsdate2
Itemobsnumeric1_2
antimicrobial_code
antimicrobial_num 
antibiotic_num
Antimicrobial0
Antibiotics0
mother
Antimicrobial_num2
Bmdcodedvaluemultitrans
Antimicrobial_code2
Antibiotic_num2
Antibiotic_date
Antibiotic_code2
Antibiotic_code
Bmdphcrootobs
multi_contact_info
multi_contact_info1
ABCs_Investigator
single_contact_info
;
run;
quit;


