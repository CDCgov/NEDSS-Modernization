/*******************************/
/***   CRS Case Fact Table   ***/
/*******************************/

%let whereclause=investigation_form_cd like 'INV_FORM_CRS%';

/* select only CRS observations */
Data CRSObsInvFrmQ1;
  Set rdbdata.PHCchildobs_NONINC (where=(&whereclause)); 
Run;

/* join observations with Codeset */
Proc SQL;
 Create table CRSObsInvFrmQ2 as 
  Select a.PUBLIC_HEALTH_CASE_UID,
		 a.ROOT_OBS_UID,
		 a.child_obs_uid,
		 a.child_obs_cd,
		 b.tbl_nm,
		 b.col_nm
   From CRSObsInvFrmQ1 a LEFT JOIN rdbdata.CodeSet b
     On a.child_obs_cd = b.cd
   Order by 1,2,3,4;
Quit;

/* for testing to identify missing codes in Codeset */
Data CRSObsInvFrmQ;
 Set CRSObsInvFrmQ2;
 If col_nm = '' then col_nm = child_obs_cd;
Run;


/* separate data by response type (coded, numeric, text, date) */
Data CRSchildobscodedsingle 
     /* CRSchildobscodedmulti */
     CRSchildobstext 
     CRSchildobsdate 		/*from_time*/
     CRSchildobsnumeric1 	
     CRSchildobsorphan;
  Set CRSObsInvFrmQ; 

  Select (child_obs_cd);
    /* single-select coded questions */
    When (
          'CRS007',       /* AUTOPSY_PERFORMED_IND */
          'CRS009',       /* BIRTH_STATE */
          'CRS011a',      /* CHILD_AGE_AT_DIAGNOSIS_UNIT */
          'CRS014',       /* BIRTH_WEIGHT_UNIT */
          'CRS015',       /* CHILD_CATARACTS */
          'CRS016',       /* CHILD_HEARING_LOSS */
          'CRS017',       /* CHILD_HAS_CONGNITAL_HEART_DISEASE */
          'CRS018',       /* CHILD_PATENT_DUCTUS_ARTERIOSUS */
          'CRS019',       /* CHILD_PULMONIC_STENOSIS */
          'CRS020',       /* OTHER_CONGNITAL_HEART_DISS_IND */
          'CRS022',       /* MOTHER_HAS_MACULOPAPULAR_RASH */
          'CRS024',       /* MOTHER_HAD_FEVER */
          'CRS027',       /* MOTHER_ARTHRALGIA_ARTHRITIS */
          'CRS028',       /* MOTHER_HAD_LYMPHADENOPATHY */
          'CRS030',       /* CHILD_CONGNITAL_GLAUCOMA */
          'CRS031',       /* CHILD_PIGMENTARY_RETINOPATHY */
          'CRS032',       /* CHILD_MENTAL_RETARDATION */
          'CRS033',       /* CHILD_MENINGOENCEPHALITIS */
          'CRS034',       /* CHILD_MICROENCEPHALY */
          'CRS035',       /* CHILD_PURPURA */
          'CRS036',       /* CHILD_ENLARGED_SPLEEN */
          'CRS037',       /* CHILD_ENLARGED_LIVER */
          'CRS038',       /* CHILD_RADIOLUCENT_BONE */
          'CRS039',       /* CHILD_JAUNDICE */
          'CRS040',       /* CHILD_LOW_PLATELETS */
          'CRS041',       /* CHILD_DERMAL_ERYTHROPOISESIS */
          'CRS042',       /* CHILD_OTHER_ABNORMALITIES */
          'CRS049',       /* CHILD_RUBELLA_LAB_TEST_DONE */
          'CRS050',       /* RUBELLA_IGM_EIA_TESTED */
          'CRS052',       /* IGM_EIA_NONCAPTURE_RESULT */
          'CRS053',       /* RUBELLA_IGM_EIA_CAPTURE */
          'CRS055',       /* RUBELLA_IGM_EIA_CAPTURE_RESULT */
          'CRS056',       /* RUBELLA_IGM_OTHER_TEST */
          'CRS059',       /* RUBELLA_IGM_OTHER_TEST_RESULT */
          'CRS060',       /* RUBELLA_IGG_TEST_1 */
          'CRS062',       /* RUBELLA_IGG_TEST_2 */
          'CRS064',       /* DIFFERENCE_BETWEEN_TEST_1_2 */
          'CRS065',       /* VIRUS_ISOLATION_PERFORMED */
          'CRS067',       /* VIRUS_ISOLATION_SPECIMEN_SRC */
          'CRS069',       /* VIRUS_ISOLATION_RESULT */
          'CRS070',       /* RT_PCR_PERFORMED */
          'CRS072',       /* RT_PCR_SRC */
          'CRS073',       /* RT_PCR_RESULT */
          'CRS074',       /* OTHER_RUBELLA_LAB_TEST_DONE */
          'CRS077',       /* SPECIMEN_TO_CDC_FOR_GENOTYPING */
          'CRS080',       /* MOTHER_BIRTH_CNTRY */
          'CRS085',       /* CHILD_18YOUNGR_RUBELLA_VACCD */
          'CRS087',       /* FAMILYPLAND_PRIOR_CONCEPTION */
          'CRS088',       /* PRENATAL_CARE_THIS_PREGNANCY */
          'CRS090',       /* PRENATAL_CARE_OBTAINED_FRM_1, PRENATAL_CARE_OBTAINED_FRM_2, PRENATAL_CARE_OBTAINED_FRM_3 */
          'CRS091',       /* RUBELLA_LIKE_ILL_IN_PREGNANCY */
          'CRS093',       /* DIAGNOSED_BY_PHYSICIAN_IND */
          'CRS095',       /* SEROLOGICAL_CONFIRMED_AT_ILL */
          'CRS096',       /* MOTHER_KNOW_EXPOSED_AT_WHERE */
          'CRS097',       /* MOTHER_RUBELLA_ACQUIRED_PLACE */
          'CRS098',       /* MOTHER_RUBELLA_ACQUIRED_CNTRY */
          'CRS100',       /* MOTHER_UNK_EXPOSURE_TRAVEL_IND */
          'CRS105',       /* MOTHER_EXPOSD_TO_RUBELLA_CASE */
          'CRS106',       /* MOTHER_RELATIONTO_RUBELLA_CASE */
          'CRS139',       /* RUBELLA_IGG_TEST_1_RESULT */
          'CRS140',       /* RUBELLA_IGG_TEST_2_RESULT */
          'CRS142',       /* REASON_NOT_A_CRS_CASE */
          'CRS147',       /* MOTHER_IMMUNIZED_IND */
          'CRS149',       /* MOTHERRUBELLA_IMMUNIZE_INFOSRC */
          'CRS151',       /* VACCINE_SRC */
          'CRS153',       /* MOTHER_GIVEN_PRIOR_BIRTH_IN_US */
          'CRS161',       /* SEROLOGICAL_TST_BEFR_PREGNANCY */
          'CRS162',       /* MOTHER_RUBELLA_ACQUIRED_STATE */
          'CRS163',       /* MOTHER_RUBELLA_ACQUIRED_CNTY */
          'CRS164',       /* MOTHER_TRAVEL_1_TO_CNTRY */
          'CRS165',       /* MOTHER_TRAVEL_2_TO_CNTRY */
          'CRS172',       /* RUBELLA_SPECIMEN_TYPE */
          'CRS175',       /* SEROLOGICALLY_CONFIRMD_RESULT */
          'CRS176',       /* MOTHER_RUBELLA_LAB_TESTING_IND */
          'CRS177',       /* MOTHER_IS_A_RPTD_RUBELLA_CASE */
          'CRS178',       /* IGM_EIA_1_METHOD_USED */
          'CRS179',       /* IGM_EIA_2_METHOD_USED */
          'CRS180',       /* INFANT_DEATH_FRM_CRS */
		  'CRS182',       /* GENOTYPE_SEQUENCED_CRS */
		  'CRS183'        /* GENOTYPE_IDENTIFIED_CRS */
		  
          )
          output CRSchildobscodedsingle;

    /* multi-select coded questions */
*    When (
          )
          output CRSchildobscodedmulti;

    /* text questions */
    When (
          'CRS005',        /* DEATH_CERTIFICATE_PRIMARY_CAUS */
          'CRS006',        /* DEATH_CERTIFICATE_2NDARY_CAUSE */
          'CRS008',        /* FINAL_ANATOMICAL_DEATH_CAUSE */
          'CRS021',       /* OTHER_CONGNITALHEART_DISS_DESC */
          'CRS043',       /* CHILD_OTHER_ABNORMALITIES_1 */
          'CRS044',       /* CHILD_OTHER_ABNORMALITIES_2 */
          'CRS045',       /* CHILD_OTHER_ABNORMALITIES_3 */
          'CRS046',       /* CHILD_OTHER_ABNORMALITIES_4 */
          'CRS057',       /* RUBELLA_IGM_OTHER_TEST_DESC */
          'CRS068',       /* VIRUS_ISOLATION_OTHER_SRC */
          'CRS075',     /* OTHER_RUBELLA_LAB_TEST_DESC */
          'CRS076',     /* OTHER_RUBELLA_LAB_TEST_RESULT */
          'CRS082',       /* MOTHER_OCCUPATION_ATCONCEPTION */
          'CRS094',       /* BY_WHOM_NOT_MD_DIAGNSD_RUBELLA */
          'CRS099',       /* MOTHER_RUBELLA_ACQUIRED_CITY */
		  'CRS144',       /* RUBELLA_IGG_TEST1_RESULT_VAL, should be numeric in PS */
          'CRS145',       /* RUBELLA_IGG_TEST2_RESULT_VAL, should be numeric in PS*/
          'CRS150',       /* MOTHER_OTHER_VACC_INFO_SRC */
          'CRS152',       /* MATERNAL_ILL_CLINICAL_FEATURE */
          'CRS154',       /* YR_MOTHER_PRIOR_DELIVERY_IN_US */
          'CRS155',       /* RT_PCR_OTHER_SRC */
          'CRS157',       /* RT_PCR_OTHER_SPECIMEN_SRC */
          'CRS166',       /* OTHER_RELATIONSHIP */
          'CRS167',       /* IGM_EIA_TEST_1_RESULT_VAL */
          'CRS168',       /* IGM_EIA_TEST_2_RESULT_VAL */
          'CRS169',       /* IGM_EIA_OTHER_TST_RESULT_VAL */
          'CRS170',       /* RT_PCR_TEST_RESULT_VAL */
          'CRS171',       /* OTHER_RUBELLA_TEST_RESULT_VAL */
          'CRS173',        /* OTHER_RUBELLA_SPECIMEN_TYPE */
		  'CRS184'        /* GENOTYPE_OTHER_IDENTIFIED_CRS */

          ) 
          output CRSchildobstext;

    /* date questions */
    When (
          'CRS002',        /* HEALTH_PROVIDER_LAST_EVAL_DT */
          'CRS022a',       /* MOTHER_RASH_ONSET_DT */
          'CRS051',        /* RUBELLA_IGM_EIA_NONCAPTURE_DT */
          'CRS054',        /* RUBELLA_IGM_EIA_CAPTURE_DT */
          'CRS058',        /* RUBELLA_IGM_OTHER_TEST_DT */
          'CRS061',        /* RUBELLA_IGG_TEST_1_DT */
          'CRS063',        /* RUBELLA_IGG_TEST_2_DT */
          'CRS066',        /* VIRUS_ISOLATION_DT */
          'CRS071',        /* RT_PCR_DT */
          'CRS089',        /* PRENATAL_FIRST_VISIT_DT */
          'CRS101',        /* MOTHER_TRAVEL_OUT_US_1_DT */
          'CRS102',        /* MOTHER_TRAVEL_BACK_US_1_DT */
          'CRS103',        /* MOTHER_TRAVEL_OUT_US_2_DT */
          'CRS104',        /* MOTHER_TRAVEL_BACK_US_2_DT */
          'CRS107',        /* MOTHER_RUBELLA_CASE_EXPOSE_DT */
          'CRS141',        /* OTHER_RUBELLA_LAB_TEST_DT */
          'CRS143',        /* SENT_FOR_GENOTYPING_DT */
          'CRS148',        /* MOTHER_VACCINATED_DT */
          'CRS174'         /* SEROLOGICALLY_CONFIRMD_DT */
          ) 
          output CRSchildobsdate;

    /* numeric questions */
    When (
          'CRS010',       /* GESTATIONAL_AGE_IN_WK_AT_BIRTH */
          'CRS011',       /* CHILD_AGE_AT_THIS_DIAGNOSIS */
          'CRS013',       /* BIRTH_WEIGHT */                     /* Date ???? - error in mapping doc */
          'CRS023',       /* MOTHER_RASH_LAST_DAY_NBR */
          'CRS081',       /* MOTHER_AGE_AT_GIVEN_BIRTH */
          'CRS083',       /* MOTHER_LIVING_IN_US_YRS */
          'CRS084',       /* AT_PREGNANCY_18YOUNGR_CHILDNBR */
          'CRS086',       /* RUBELLAVACCD_18YOUNGR_CHILDNBR */
          'CRS092',       /* PREGNANCY_MO_RUBELLA_SYMPTM_UP */
         
          'CRS158',       /* PREVIOUS_PREGNANCY_NBR */
          'CRS159',       /* TOTAL_LIVE_BIRTH_NBR */
          'CRS160'        /* BIRTH_DELIVERED_IN_US_NBR */
          )
          output CRSchildobsnumeric1;

    /* known orphan questions */
*    When (
		  'INV128',       /* PATIENT_HSPTLIZD_IND */
		  'INV132',       /* HSPTL_ADMISSION_DT */
		  'INV133',       /* HSPTL_DISCHARGE_DT */
		  'INV134'        /* HSPTLIZE_DURATION_DAYS */     
          )
          output CRSchildobsorphan;

	Otherwise 
		  output CRSchildobsorphan;
  End;
run;

%getobscode(CRScodedvalue,CRSchildobscodedsingle);
%getobstxt(CRSobstxt,CRSchildobstext);
%getobsdate(CRSfrom_time,CRSchildobsdate,from_time);  
%getobsnum(CRSNum1, CRSchildobsnumeric1,numeric_value_1);


/* CRS090 is multi-selection but modeled as multi single fields in RDB */
Data CRScodedvalue2 CRS090;
 Set CRScodedvalue;
	if child_obs_cd='CRS090' then output CRS090;
	else output CRScodedvalue2;
Run;

Proc Sort data=CRS090; 
  By root_obs_uid; 
Run;

Data CRS090;
  Set CRS090;
  By root_obs_uid;
  Retain crs090select 0;
	if first.root_obs_uid then crs090select = 0;
	crs090select+1;
	col_nm ='PRENATAL_CARE_OBTAINED_FRM_'||left(put(crs090select,3.));
	drop crs090select;
Run;

Proc Append data=CRS090 base=CRScodedvalue2; 
Run;


%rows_to_columns(CRScodedvalue2,CRScodedvalue_trans);
%rows_to_columns(CRSobstxt,CRSobstxt_trans);
%rows_to_columns(CRSfrom_time,CRSfrom_time_trans);
%rows_to_columns(CRSNum1,CRSNum1_trans);


/* select CRS-specific root data from PHCrootobs */
/*** sample code - no root data to include ***/
/*
Data CRSrootobs;
  Set rdbdata.PHCrootobs_NONINC (where=(&whereclause));
  Keep public_health_case_uid root_obs_uid
       effective_from_time 
       outcome_cd;
  Rename effective_from_time=ILLNESS_ONSET_DT
         outcome_cd=DIE_FRM_THIS_ILLNESS_IND;
Run;
*/

/* combine transposed response data sets with root data to form complete Hepatitis Case Fact table */
Data rdbdata.CRS_Case1;
  Merge CRScodedvalue_trans CRSobstxt_trans CRSfrom_time_trans CRSNum1_trans;    /* CRSrootobs */
  By public_health_case_uid root_obs_uid; 
Run;

/* In case user entered minimum data in public_health_case table and no data in Observation at all
the rdbdata.CRS_Case will be empty so far.  So we need to join back to 
the public_health_case record in order to create a record in fact table.*/
proc sort data=rdbdata.CRS_Case1; by public_health_case_uid; run;
proc sort data=rdbdata.PHCrootobs_NONINC out=CRSPhc (keep=public_health_case_uid); 
by public_health_case_uid;
	where &whereclause; 
Run;
data rdbdata.CRS_Case2;
	merge CRSPhc rdbdata.CRS_Case1 ;
	by public_health_case_uid;
run;



%add_common_inv_keys(rdbdata.CRS_Case3,rdbdata.CRS_Case2);

/* LDF_Group_Key */
%AddLDFGroupKeytoFactTable (rdbdata.CRS_Case,rdbdata.CRS_Case3);


/* clean up fact table */
Data rdbdata.CRS_Case;
  Set rdbdata.CRS_Case (drop=public_health_case_uid root_obs_uid); 

  /* assign missing keys */
  If LDF_Group_Key=. then LDF_Group_Key=1;

  /* drop unused keys */
  Drop INV_START_DT_key DIAGNOSIS_DT_KEY INV_RPT_DT_KEY;
Run;
PROC SORT DATA=rdbdata.CRS_Case NODUPKEY; BY  INVESTIGATION_KEY; RUN;


proc datasets lib=work nolist;
	delete 
		CRS090                  
		CRSCHILDOBSCODEDSINGLE  
		CRSCHILDOBSDATE         
		CRSCHILDOBSNUMERIC1     
		CRSCHILDOBSORPHAN       
		CRSCHILDOBSTEXT         
		CRSCODEDVALUE           
		CRSCODEDVALUE2
		CRSCODEDVALUE_TRANS
		CRSFROM_TIME       
		CRSFROM_TIME_TRANS 
		CRSNUM1            
		CRSNUM1_TRANS      
		CRSOBSINVFRMQ
		CRSOBSINVFRMQ1
		CRSOBSINVFRMQ2
		CRSOBSTXT          
		CRSOBSTXT_TRANS    
		CRSPHC             
		OBS_VALUE_DATE_SEQ1
		OBS_VALUE_NUMERIC_SEQ1
		OBS_VALUE_TEXT_SEQ1
;
run;
quit;
/***************************************************/

