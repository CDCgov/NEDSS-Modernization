/*********************************/
/***  Rubella Case Fact Table  ***/
/*********************************/

%let whereclause=investigation_form_cd like 'INV_FORM_RUB%';

/* select only Rubella observations */
Data RUBObsInvFrmQ1;
  Set rdbdata.PHCchildobs_NONINC (where=(&whereclause)); 
*  If child_obs_cd = 'RUB91a' then child_obs_cd = 'RUB091a';     /* error in maping doc - RUB091a */
Run;

/* join observations with Codeset */
Proc SQL;
 Create table RUBObsInvFrmQ as 
  Select a.PUBLIC_HEALTH_CASE_UID,
		 a.ROOT_OBS_UID,
		 a.child_obs_uid,
		 a.child_obs_cd,
		 b.tbl_nm,
		 b.col_nm
   From RUBObsInvFrmQ1 a LEFT JOIN rdbdata.CodeSet b
     On a.child_obs_cd = b.cd
   Order by 1,2,3,4;
Quit;

/* for testing to identify missing codes in Codeset */
Data RUBObsInvFrmQ;
 Set RUBObsInvFrmQ;
 If col_nm = '' then col_nm = child_obs_cd;
Run;


/* separate data by response type (coded, numeric, text, date) */
Data RUBchildobscodedsingle 
     /* RUBchildobscodedmulti */
     RUBchildobstext 
     RUBchildobsdate 		/*from_time*/
     RUBchildobsnumeric1 	
     RUBchildobsorphan;
  set RUBObsInvFrmQ;

  Select (child_obs_cd);
    /* single-select coded questions */
    When (
		'RUB003',       /* MACULOPAPULAR_RASH_IND */
          'RUB006',       /* PATIENT_FEVER_IND */
          'RUB008',       /* HIGHEST_TEMPERATUR_UNIT */
          'RUB009',       /* ARTHRALGIA_ARTHRITIS_SYMPTOM */
          'RUB010',       /* LYMPHADENOPATHY_IND */
          'RUB011',       /* CONJUNCTIVITIS_IND */
          'RUB019',       /* ENCEPHALITIS_IND */
          'RUB020',       /* THROMBOCYTOPENIA_IND */
          'RUB021',       /* OTHER_COMPLICATIONS_IND */
          'RUB033',       /* RUBELLA_LAB_TEST_DONE_IND */
          'RUB034',       /* RUBELLA_IGM_EIA_TEST_IND */
          'RUB036',       /* RUBELLA_IGM_EIA_TEST_RESULT */
          'RUB037',       /* RUBELLA_IGM_EIA_CAPTURE_IND */
          'RUB039',       /* RUBELLA_IGM_EIA_CAPTURE_RESULT */
          'RUB040',       /* OTHER_RUBELLA_IGM_IND */
          'RUB043',       /* OTHER_RUBELLA_IGM_RESULT */
          'RUB044',       /* RUBELLA_IGG_EIA_ACUTE_IND */
          'RUB046',       /* RUBELLA_IGG_EIA_CNVLSNT_IND */
          'RUB048',       /* IGG_EIA_ACUTE_CNVLSNT_DIFF */
          'RUB049',       /* HEMAG_INHIBIT_ACUTE_IND */
          'RUB051',       /* HEMAG_INHIBIT_CNVLSNT_IND */
          'RUB053',       /* HEMAGINHIBIT_ACUTECNVLSNT_DIFF */
          'RUB054',       /* CMPLMNT_FIXATION_ACUTE_IND */
          'RUB056',       /* CMPLMNT_FIXATION_CNVLSNT_IND */
          'RUB058',       /* CMPLMNT_FIX_ACUTE_CNVLSNT_DIFF */
          'RUB059',       /* RUBELLA_IGG_OTHER_TEST1_IND */
          'RUB062',       /* RUBELLA_IGG_OTHER_TEST1_RESULT */
          'RUB063',       /* RUBELLA_IGG_OTHER_TEST2_IND */
          'RUB066',       /* RUBELLA_IGG_OTHER_TEST2_RESULT */
          'RUB067',       /* RUBELLA_IGG_OTHER_TEST3_IND */
          'RUB070',       /* RUBELLA_IGG_OTHER_TEST3_RESULT */
          'RUB071',       /* VIRUS_ISOLATION_PERFORMED_IND */
          'RUB073',       /* VIRUS_ISOLATION_PERFORMED_SRC */
          'RUB075',       /* VIRUS_ISOLATION_RESULT */
          'RUB076',       /* RT_PCR_PERFORMED_IND */
          'RUB078',       /* RT_PCR_SRC */                                 /* missing in Codeset */
          'RUB079',       /* RT_PCR_RESULT */
          'RUB080',       /* LATEX_AGGLUTINATION_TESTED_IND */
          'RUB083',       /* LATEX_AGGLUTINATION_TESTRESULT */
          'RUB084',       /* IMMUNOFLUORESCENT_ASSAY_IND */
          'RUB086',       /* IMMUNOFLUORESCENT_ASSAY_SRC */
          'RUB087',       /* IMMUNOFLUORESCENT_ASSAY_RESULT */
          'RUB088',       /* OTHER_RUBELLA_TEST_DONE_IND */
          'RUB091',       /* SPECIMEN_TO_CDC_GENOTYPING_IND */
          /*'RUB091a',       SPECIMENTO_CDC_GENOTYPING_TYPE */   /* error NBS, RUB91a inserted instead */
          'RUB91a',       /* SPECIMENTO_CDC_GENOTYPING_TYPE */   /* error NBS, RUB91a inserted instead */
          'RUB093',       /* RUBELLA_VACCINE_RECEIVED_IND */
          'RUB094',       /* RUBELLA_VACCINED_NEVER_REASON */
          'RUB112',       /* EPI_LINKED_TO_ANOTHER_CASE_IND */
          'RUB117',       /* PREGNANCY_IND */
          'RUB121',       /* GESTATION_TRIMESTER_ST_RUBELLA */
          'RUB122',       /* PREVIOUS_RUBELLA_IMMUNITY_DOC */
          'RUB123',       /* PREVIOUSIMMUNITY_TESTINGRESULT */
          'RUB126',       /* RUBELLA_PRIOR_TO_PREGNANCY_IND */
          'RUB127',       /* SEROLOGICALLYCONFIRMED_RUBELLA */
          'RUB130',       /* PREGNANCY_CURRENT_OUTCOME */
          'RUB132',       /* LIVE_BIRTH_OUTCOME */
          'RUB133',       /* NON_LIVING_BIRTH_OUTCOME */
          'RUB135',       /* NON_LIVING_BIRTH_AUTOPSY_STUDY */
          'RUB146',       /* NATION_CD */
          'RUB147',       /* ARTHRALGIA_ARTHRITIS_COMPLICAT */
          'RUB159',       /* RASH_ONSET_ENTERING_USA */
          'RUB160',       /* IGM_EIA_1ST_METHOD_USED */
          'RUB161',       /* IGM_EIA_2ND_METHOD_USED */
		  'RUB163',       /* RUBELLA_CASE_TRACEABLE_IND */
		  'RUB164',       /* GENOTYPE_SEQUENCED_RUB */
		  'RUB165'        /* GENOTYPE_IDENTIFIED_RUBELLA */
          )
          output RUBchildobscodedsingle;

    /* multi-select coded questions */
*    When (
          )
          output RUBchildobscodedmulti;

    /* text questions */
    When (
          'RUB022',       /* OTHER_COMPLICATIONS_DESC */
          'RUB028',       /* CAUSE_OF_DEATH */
          'RUB041',       /* OTHER_RUBELLA_IGM_DESC */
          'RUB060',       /* RUBELLA_IGG_OTHER_TEST1_DESC */
          'RUB064',       /* RUBELLA_IGG_OTHER_TEST2_DESC */
          'RUB068',       /* RUBELLA_IGG_OTHER_TEST3_DESC */
          'RUB074',       /* VIRUS_ISOLATION_OTHER_SRC */
          'RUB078a',      /* RT_PCR_OTHER_SRC */
          'RUB086a',      /* IMUNOFLRESNT_ASSAY_OTHER_SRC */
          'RUB089',       /* OTHER_RUBELLA_TEST_DESC */
          'RUB089b',      /* OTHER_RUBELLA_TEST_RESULT */
          'RUB092',       /* GENOTYPING_SPECIMEN_OTHER_TYPE */
          'RUB095',       /* RUBELLAVACCINED_NOTOTHERREASON */
          'RUB119',       /* EXPECTED_DELIVERY_PLACE */
          'RUB131',       /* PREGNANCY_OTHER_OUTCOME */
          'RUB136',       /* AUTOPSY_PATHOLOGY_STUDY_RESULT */
          'RUB140',       /* EIA_ACUTE_TEST_RESULT_VALUE */
          'RUB141',       /* EIA_CNVLSNT_TEST_RESULT_VALUE */
          'RUB142',       /* HEMAG_INHIBIT_ACUTE_VALUE */
          'RUB143',       /* HEMAG_INHIBIT_CNVLSNT_VALUE */
          'RUB144',       /* CMPLMNT_FIXATION_ACUTE_VALUE */
          'RUB145',       /* CMPLMNT_FIXATION_CNVLSNT_VALUE */
          'RUB148',       /* IGM_EIA_1ST_TEST_RESULT_VALUE */
          'RUB149',       /* IGM_EIA_2ND_TEST_RESULT_VALUE */
          'RUB150',       /* OTHER_IGM_TEST_RESULT_VALUE */
          'RUB151',       /* IGG_OTHER_TEST_1_RESULT_VALUE */
          'RUB152',       /* IGG_OTHER_TEST_2_RESULT_VALUE */
          'RUB153',       /* IGG_OTHER_TEST_3_RESULT_VALUE */
          'RUB154',       /* RT_PCR_TEST_RESULT_VALUE */
          'RUB155',       /* LATEX_AGG_TEST_RESULT_VALUE */
          'RUB156',       /* ASSAY_TEST_RESULT_VALUE */
          'RUB157',       /* OTHER_RUBELLA_TESTRESULT_VALUE */
          'RUB158',       /* INFECTION_SRC */
		  'RUB166'		  /*GENOTYPE_OTHER_IDENTIFIED_RUBELLA*/	
          ) 
          output RUBchildobstext;

    /* date questions */
    When (
		  'INV132',       /* HSPTL_ADMISSION_DT */
		  'INV133',       /* HSPTL_DISCHARGE_DT */
          'RUB004',       /* PATIENT_RASH_ONSET_DT */
          'RUB030',       /* HSPTL_ADMISSION_DT */
          'RUB031',       /* HSPTL_DISCHARGE_DT */
          'RUB032',       /* HSPTLIZED_DAY_NBR */                 /* use INV134 - HSPTL_DURATION_DAYS */ 
          'RUB035',       /* RUBELLA_IGM_EIA_TEST_DT */
          'RUB038',       /* RUBELLA_IGM_EIA_CAPTURE_DT */
          'RUB042',       /* OTHER_RUBELLA_IGM_DT */
          'RUB045',       /* RUBELLA_IGG_EIA_ACUTE_DT */
          'RUB047',       /* RUBELLA_IGG_EIA_CNVLSNT_DT */
          'RUB050',       /* HEMAG_INHIBIT_ACUTE_DT */
          'RUB052',       /* HEMAG_INHIBIT_CNVLSNT_DT */
          'RUB055',       /* CMPLMNT_FIXATION_ACUTE_DT */
          'RUB057',       /* CMPLMNT_FIXATION_CNVLSNT_DT */
          'RUB061',       /* RUBELLA_IGG_OTHER_TEST1_DT */
          'RUB065',       /* RUBELLA_IGG_OTHER_TEST2_DT */
          'RUB069',       /* RUBELLA_IGG_OTHER_TEST_3_DT */
          'RUB072',       /* VIRUS_ISOLATION_PERFORMED_DT */
          'RUB077',       /* RT_PCR_DT */
          'RUB081',       /* LATEX_AGGLUTINATION_TESTED_DT */
          'RUB085',       /* IMMUNOFLUORESCENT_ASSAY_DT */
          'RUB089a',      /* OTHER_RUBELLA_TEST_DT */
          'RUB098',       /* RUBELLA_VACCINE_RECEIVED_DT */
          'RUB105',       /* RUBELLA_VACCINE_EXPIRATION_DT */
          'RUB106',       /* INV_RPT_DT */
          'RUB107',       /* DIAGNOSIS_DT */
          'RUB118',       /* PREGNANCY_DELIVERY_EXPECTED_DT */
          'RUB139'        /* RUBELLA_GENOTYPING_DT */
          ) 
          output RUBchildobsdate;

    /* numeric questions */
    When (
		  'RUB001',       /* LENGTH_OF_TIME_IN_US */
          'RUB005',       /* RASH_DURATION_DAYS */
          'RUB007',       /* HIGHEST_MEASURED_TEMPERATURE */
          'RUB096',       /* ON_AFTER_1ST_DOB_DOSES_NBR */
          'RUB120',       /* GESTATION_WK_NBR_AT_RUBELLA */
          'RUB124',       /* PREVIOUS_IMMUNITY_TESTING_YR */
          'RUB125',       /* WOMAN_AGE_AT_IMMUNITY_TESTING */
          'RUB128',       /* PREVIOUS_DISS_YR */
          'RUB129',       /* PREVIOUS_DISS_AGE */
          'RUB134'        /* PREGNANCY_CESSATION_FETUS_WK */
          )
          output RUBchildobsnumeric1;

    /* known orphan questions */
*    When (
		  'INV128',       /* HSPTLIZD_IND */
	      'INV134',       /* HSPTLIZE_DURATION_DAYS */        /* replaces RUB032 - HSPTLIZE_DURATION_DAYS */
      	  'INV153',       /* IMPORT_FROM_CNTRY */
		  'INV154',       /* IMPORT_FROM_STATE */
		  'INV155',       /* IMPORT_FROM_CITY */
		  'INV156',       /* IMPORT_FROM_CNTY */
          'RUB00d',       /* STATE_FIPS */
          'RUB026',       /* PATIENT_DECEASED_IND */
          'RUB027',       /* PATIENT_DECEASE_DT */
          'RUB029',       /* PATIENT_HSPTLIZED_IND */
          'RUB91a',       /* SPECIMENTO_CDC_GENOTYPING_TYPE */              /* error in mapping doc - RUB091a */
          'RUB108',       /* TRANSMISSION_SETTING */                        /* from PHCrootobs */
          'RUB109',       /* TRANSMISSION_SETTING_OTHER */                  /* from PHCrootobs */
          'RUB110',       /* PARTOF_3PLUS_CASES_OUTBRK_IND */               /* from PHCrootobs */
          'RUB137',       /* RUBELLA_CONFIRMATION_METHOD */
          'RUB138',       /* INV_CASE_STATUS */
		  'RUB162'        /* DIE_FRM_THIS_ILLNESS_IND */
     
          )
          output RUBchildobsorphan;

	Otherwise 
		  output RUBchildobsorphan;
  End;
Run;

%getobscode(RUBcodedvalue,RUBchildobscodedsingle);
%getobstxt(RUBobstxt,RUBchildobstext);
%getobsdate(RUBfrom_time,RUBchildobsdate,from_time);  
%getobsnum(RUBNum1, RUBchildobsnumeric1,numeric_value_1);

%rows_to_columns(RUBcodedvalue,RUBcodedvalue_trans);
%rows_to_columns(RUBobstxt,RUBobstxt_trans);
%rows_to_columns(RUBfrom_time,RUBfrom_time_trans);
%rows_to_columns(RUBNum1,RUBNum1_trans);


/* select Rubella-specific root data from PHCrootobs */
Data RUBrootobs;
  Set rdbdata.PHCrootobs_NONINC (where=(&whereclause));
  Keep public_health_case_uid root_obs_uid
       TRANSMISSION_MODE_CD 
       TRANSMISSION_MODE_DESC_TXT
		;
  Rename TRANSMISSION_MODE_CD=TRANSMISSION_SETTING
         TRANSMISSION_MODE_DESC_TXT=TRANSMISSION_SETTING_OTHER
         ;
Run;


/* combine transposed response data sets with root data to form complete Hepatitis Case Fact table */
Data rdbdata.Rubella_Case1;
  Merge RUBcodedvalue_trans RUBobstxt_trans RUBfrom_time_trans RUBNum1_trans RUBrootobs;
  By public_health_case_uid root_obs_uid; 
Run;

/* In case user entered minimum data in public_health_case table and no data in Observation at all
we still need to join back to the public_health_case record in order to create a record in fact table.*/
proc sort data=rdbdata.Rubella_Case1; by public_health_case_uid; run;
proc sort data=rdbdata.PHCrootobs_NONINC out=RubellaPhc (keep=public_health_case_uid); 
by public_health_case_uid;
	where &whereclause; 
Run;
data rdbdata.Rubella_Case2;
	merge RubellaPhc rdbdata.Rubella_Case1 ;
	by public_health_case_uid;
run;





%add_common_inv_keys(rdbdata.Rubella_Case3,rdbdata.Rubella_Case2);

/* LDF_Group_Key */
%AddLDFGroupKeytoFactTable (rdbdata.Rubella_Case,rdbdata.Rubella_Case3);


/* clean up fact table */
Data rdbdata.Rubella_Case;
  Set rdbdata.Rubella_Case (drop=public_health_case_uid root_obs_uid); 

    /* assign missing keys */
  If LDF_Group_Key=. then LDF_Group_Key=1;

  /* drop unused keys */
  Drop INV_START_DT_key 
	   DIAGNOSIS_DT_KEY 
	   INV_RPT_DT_KEY
;
Run;

PROC SORT DATA=rdbdata.Rubella_Case NODUPKEY; BY  INVESTIGATION_KEY; RUN;


/***************************************************

Outstanding Issues

***************************************************/
/*

NBS inserted Wrong CD (RUB91a) in observation instead of RUB091a
WARNING: Variable SPECIMENTO_CDC_GENOTYPING_TYPE was not found on DATA file.  (RUB091a / RUB91a)

RUB032 is not used by NBS, INV134 is populated.

No Pagespec mapping found:
RUBELLA_VACCINE_MANUFACTURER was not found on DATA file. No mapping
RUBELLA_VACCINE_RECEIVED_DT (RUB098) was not found on DATA file. 
RUBELLA_VACCINE_EXPIRATION_DT (RUB105) was not found on DATA file. 
RUBELLAVACCINED_NOTOTHERREASON (RUB095) was not found on DATA file.
PREGNANCY_OTHER_OUTCOME(RUB131) was not found on DATA file.
 
*/

PROC datasets library = work nolist;
delete
RUBObsInvFrmQ
RUBObsInvFrmQ1
RUBchildobscodedsingle 
RUBchildobstext 
RUBchildobsdate 	
RUBchildobsnumeric1 	
RUBchildobsorphan
RUBcodedvalue
RUBobstxt
RUBfrom_time
RUBNum1
RUBcodedvalue_trans 
RUBobstxt_trans 
RUBfrom_time_trans 
RUBNum1_trans 
RUBrootobs
RubellaPhc;
run;
quit;
