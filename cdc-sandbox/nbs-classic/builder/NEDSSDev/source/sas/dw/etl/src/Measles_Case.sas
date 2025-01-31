/*********************************/
/*** Measles Case Fact Table ***/
/*********************************/

%let whereclause=investigation_form_cd like 'INV_FORM_MEA%';

/* select only Measles observations */
Data MEAObsInvFrmQ1;
  Set rdbdata.PHCchildobs_NONINC (where=(&whereclause)); 
Run;

/* join observations with Codeset */
Proc SQL;
 Create table MEAObsInvFrmQ2 as 
  Select a.PUBLIC_HEALTH_CASE_UID,
		 a.ROOT_OBS_UID,
		 a.child_obs_uid,
		 a.child_obs_cd,
		 b.tbl_nm,
		 b.col_nm
   From MEAObsInvFrmQ1 a LEFT JOIN rdbdata.CodeSet b
     On a.child_obs_cd = b.cd
   Order by 1,2,3,4;
Quit;

/* for testing to identify missing codes in Codeset */
Data MEAObsInvFrmQ;
 Set MEAObsInvFrmQ2;
 If col_nm = '' then col_nm = child_obs_cd;
Run;


/* separate data by response type (coded, numeric, text, date) */
Data MEAchildobscodedsingle 
     /* MEAchildobscodedmulti  */
     MEAchildobstext 
     MEAchildobsdate 		/*from_time*/
     MEAchildobsnumeric1 	
     MEAchildobsorphan;
  Set MEAObsInvFrmQ; 
  
  Select (child_obs_cd);
    /* single-select coded questions */
	    When (
            'MEA001',       /* PATIENT_HAVE_RASH_IND */
            'MEA004',       /* RASH_GENERALIZED_IND */
            'MEA005',       /* PATIENT_HAVE_FEVER_IND */
            'MEA007',       /* HIGHEST_TEMPERATURE_UNIT */
            'MEA008',       /* COUGH_IND */
            'MEA009',       /* CROUP_IND */
            'MEA010',       /* CORYZA_IND */
            'MEA011',       /* HEPATITIS_IND */
            'MEA012',       /* CONJUNCTIVITIS_IND */
            'MEA013',       /* OTTIS_MEDIA_IND */
            'MEA014',       /* DIARRHEA_IND */
            'MEA015',       /* PNEUMONIA_IND */
            'MEA016',       /* ENCEPHALITIS_IND */
            'MEA017',       /* THROMBOCYTOPENIA_IND */
            'MEA018',       /* OTHER_COMPLICATION_IND */
            'MEA023',       /* PATIENT_HOSPTLIZED_IND */
            'MEA027',       /* LAB_MEASLES_TEST_DONE_IND */
            'MEA029',       /* IGM_TEST_RESULT */
            'MEA032',       /* IGG_TEST_RESULT */
            'MEA033',       /* OTHER_LAB_TEST_DONE_IND */
            'MEA038',       /* SPECIMEN_TO_CDC_GENOTYPING_IND */
            'MEA039',       /* CONTAIN_MEASLES_VACC_RECEIVD */
            'MEA040',       /* NO_MEASLES_VACC_REASON */
            'MEA041',       /* NO_MEASLES_VACC_OTHER_REASON */
            'MEA044',       /* BEFORE_B_DAY_VACCINE_REASON */
            'MEA045',       /* AFTER_B_DAY_VACCINE_REASON */
            'MEA057',       /* TRANSMISSION_SETTING */
            'MEA059',       /* AGE_AND_SETTING_VERIFIED_IND */
            'MEA060',       /* PATIENT_RESIDE_IN_USA_IND */
            'MEA067',       /* EPI_LINKED_TOANOTHER_CASE_IND */
			'MEA068',       /* CASE_TRACEABLE_IND */  	
			'MEA073',       /* IGM_TESTING_PERFORMED_IND */
            'MEA074',       /* IGG_TESTING_PERFORMED_IND */
            'MEA075',       /* RASH_OCCUR_IN_18DAYS_ENTER_USA */
			'MEA079',	   /*GENOTYPE_SEQUENCED_MEASLES*/
			'MEA080'       /* GENOTYPE_IDENTIFIED_MEASLES*/
            )
			 output MEAchildobscodedsingle;

       /* multi-select coded questions */
*      When (
            )
             output CRSchildobscodedmulti;
		
       /* text questions */
	   When (
            'MEA019',       /* OTHER_COMPLICATIONS */
            'MEA034',       /* OTHER_LAB_TEST_DESC */
            'MEA036',       /* OTHER_LAB_TEST_RESULT */
            'MEA076',       /* SRC_OF_INFECTION */
            'MEA077',        /* MEASLES_SPECIMEN_TYPE */
			'MEA081'	   /*GENOTYPE_OTHER_IDENTIFIED_MEASLES*/
			)
			output MEAchildobstext;

       /* numeric questions */
	   When (
			'MEA003',       /* RASH_LAST_DAY_NBR */
            'MEA006',       /* HIGHEST_TEMPERATURE_MEASURED */
            'MEA026',       /* HSPTLIZE_DURATION_DAYS */
            'MEA042',       /* NBR_DOSE_RECEIVED_PRIOR_1BDAY */
            'MEA043'        /* NBR_DOSE_RECEIVED_SINCE_1BDAY */
			)
			output MEAchildobsnumeric1;

       /* date questions */
	   When (
	
            'MEA002',       /* RASH_ONSET_DT */
            'MEA024',       /* HSPTL_ADMISSION_DT */
            'MEA025',       /* HSPTL_DISCHARGE_DT */
            'MEA028',       /* IGM_SPECIMEN_TAKEN_DT */
            'MEA030',       /* IGG_ACUTE_SPECIMEN_TAKEN_DT */
            'MEA031',       /* IGG_CONVALESCENT_SPECIMEN_DT */
            'MEA035',       /* OTHER_LAB_TEST_DT */
            'MEA055',       /* RPT_TO_HEALTH_DEPT_DT */
            'MEA056',       /* DIAGNOSIS_DT */
            'MEA071',       /* FEVER_ONSET_DT */
            'MEA072'        /* GENOTYPING_DT */
			)
			output MEAchildobsdate;

       /* known orphan questions */
*	   When (
			'INV132',       /* HSPTL_ADMISSION_DT */
			'INV133',       /* HSPTL_DISCHARGE_DT */
			'INV134',       /* HSPTLIZE_DURATION_DAYS */
          	'INV128',       /* HSPTLIZD_IND */
			'INV153',       /* IMPORT_FROM_CNTRY */
			'INV154',       /* IMPORT_FROM_STATE */
			'INV155',       /* IMPORT_FROM_CITY */
			'INV156',       /* IMPORT_FROM_CNTY */
            'MEA061',       /* PART_OF_3PLUS_CASES_OUTBRK_IND */              /* from PHCrootobs */
            'MEA062',       /* PART_OF_3PLUS_CASES_OUTBRK_NM */               /* from PHCrootobs */
            'MEA063',       /* DISS_ACQUIRD_LOCATION_KEY */                   /* from PHCrootobs */
 			'MEA069',       /* CONFIRMATION_METHOD_DESC */
            'MEA070'        /* INV_CASE_STATUS */
			'MEA078'        /* DIE_FRM_THIS_ILLNESS_IND */
       
			)
			output MEAchildobsorphan;

		Otherwise 
			output MEAchildobsorphan;
	End;
Run;

%getobscode(MEAcodedvalue,MEAchildobscodedsingle);
%getobstxt(MEAobstxt,MEAchildobstext);
/*extract each field from obs_value_date */
%getobsdate(MEAfrom_time,MEAchildobsdate,from_time);  
%getobsnum(MEANum1, MEAchildobsnumeric1,numeric_value_1);


/* transpose responses to columns */
%rows_to_columns(MEAcodedvalue,MEAcodedvalue_trans);
%rows_to_columns(MEAobstxt,MEAobstxt_trans);
%rows_to_columns(MEAfrom_time,MEAfrom_time_trans);
%rows_to_columns(MEANum1,MEANum1_trans);


/* combine transposed response data sets with root data to form complete Hepatitis Case Fact table */
Data rdbdata.Measles_Case1;
  Merge MEAcodedvalue_trans MEAobstxt_trans MEAfrom_time_trans MEANum1_trans;
  By public_health_case_uid root_obs_uid; 
Run;

/* In case the Measles case has minimum data in public_health_case table and no data in Observation at all
table then the rdbdata.Measles_Case will be empty so far.  So we need to join back to 
the public_health_case record in order to create a record in Measles_Case fact table.
in Measles_Case */
proc sort data=rdbdata.Measles_Case1; by public_health_case_uid; run;
proc sort data=rdbdata.PHCrootobs_NONINC out=MEAPhc (keep=public_health_case_uid); 
by public_health_case_uid;
	where &whereclause; 
Run;
data rdbdata.Measles_Case2;
	merge MEAPhc rdbdata.Measles_Case1 ;
	by public_health_case_uid;
run;


%add_common_inv_keys(rdbdata.Measles_Case3,rdbdata.Measles_Case2);

/* LDF_Group_Key */
%AddLDFGroupKeytoFactTable (rdbdata.Measles_Case,rdbdata.Measles_Case3);


/* clean up fact table */
Data rdbdata.Measles_Case;
  Set rdbdata.Measles_Case (drop=public_health_case_uid root_obs_uid); 

  /* assign missing keys */
  If LDF_Group_Key=. then LDF_Group_Key=1;

  /* drop unused keys */
  Drop INV_START_DT_KEY DIAGNOSIS_DT_KEY;
Run;
PROC SORT DATA=rdbdata.Measles_Case NODUPKEY; BY  INVESTIGATION_KEY; RUN;


/***************************************************/

PROC datasets library = work nolist;
delete MEAObsInvFrmQ
MEAObsInvFrmQ1
MEAObsInvFrmQ2
MEAchildobscodedsingle
MEAchildobstext
MEAchildobsdate
MEAchildobsnumeric1
MEAchildobsorphan
MEAcodedvalue_trans
MEAobstxt_trans
MEAfrom_time_trans
MEANum1_trans
Meacodedvalue
Meafrom_time
Meanum1
Meaobstxt
Meaphc
;
run;
quit;
