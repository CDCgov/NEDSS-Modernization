/*********************************/
/*** HEPATITIS Case Fact Table ***/
/*********************************/

%let whereclause=investigation_form_cd like 'INV_FORM_HEP%';

/* select only Hepatitis observations */
Data HEPObsInvFrmQ1;
  Set rdbdata.PHCchildobs_NONINC (where=(&whereclause)); 
Run;

/* join observations with Codeset */
Proc SQL;
 Create table HEPObsInvFrmQ as 
  Select a.PUBLIC_HEALTH_CASE_UID,
		 a.ROOT_OBS_UID,
		 a.child_obs_uid,
		 a.child_obs_cd,
		 b.tbl_nm,
		 b.col_nm
   From HEPObsInvFrmQ1 a LEFT JOIN rdbdata.CodeSet b
     On a.child_obs_cd = b.cd
   Order by 1,2,3,4;
Quit;

/* for testing to identify missing codes in Codeset */
Data HEPObsInvFrmQ;
 Set HEPObsInvFrmQ;
 If col_nm = '' then col_nm = child_obs_cd;
Run;


/* separate data by response type (coded, numeric, text, date) */
Data HEPchildobscodedsingle 
     HEPchildobscodedmulti 
     HEPchildobstext 
     HEPchildobsdate 
     HEPchildobsnumeric 
     HEPchildobsorphan;
  Set HEPObsInvFrmQ;

  Select (child_obs_cd);
    /* single-select coded questions */
    When (
          'HEP102',       /* PATIENT_SYMPTOMATIC_IND */
          'HEP104',       /* PATIENT_JUNDICED_IND */
          'HEP106',       /* PATIENT_PREGNANT_IND */
          'HEP110',       /* HEP_A_TOTAL_ANTIBODY */
          'HEP111',       /* HEP_A_IGM_ANTIBODY */
          'HEP112',       /* HEP_B_SURFACE_ANTIGEN */
          'HEP113',       /* HEP_B_TOTAL_ANTIBODY */
          'HEP114',       /* HEP_B_IGM_ANTIBODY */
          'HEP115',       /* HEP_C_TOTAL_ANTIBODY */
          'HEP117',       /* ANTIHCV_SUPPLEMENTAL_ASSAY */
          'HEP118',       /* HCV_RNA */
          'HEP119',       /* HEP_D_TOTAL_ANTIBODY */
          'HEP120',       /* HEP_E_TOTAL_ANTIBODY */
          'HEP127',       /* HEP_A_EPLINK_IND */
          'HEP129',       /* HEP_A_CONTACTED_IND */
          'HEP132',       /* D_N_P_EMPLOYEE_IND */
          'HEP133',       /* D_N_P_HOUSEHOLD_CONTACT_IND */
          'HEP134',       /* HEP_A_KEYENT_IN_CHILDCARE_IND */
          'HEP135',       /* HEPA_MALE_SEX_PARTNER_NBR */
          'HEP136',       /* HEPA_FEMALE_SEX_PARTNER_NBR */
          'HEP137',       /* STREET_DRUG_INJECTED_IN_2_6_WK */
          'HEP138',       /* STREET_DRUG_USED_IN_2_6_WK */
          'HEP139',       /* TRAVEL_OUT_USA_CAN_IND */
          'HEP141',       /* HOUSEHOLD_NPP_OUT_USA_CAN */
          'HEP143',       /* PART_OF_AN_OUTBRK_IND */
          'HEP144',       /* ASSOCIATED_OUTBRK_TYPE */
          'HEP146',       /* FOODHANDLER_2_WK_PRIOR_ONSET */
          'HEP147',       /* HEP_A_VACC_RECEIVED_IND */
          'HEP148',       /* HEP_A_VACC_RECEIVED_DOSE */
          'HEP150',       /* IMMUNE_GLOBULIN_RECEIVED_IND */
          'HEP152',       /* HEP_B_CONTACTED_IND */
          'HEP155',       /* HEPB_MALE_SEX_PARTNER_NBR */
          'HEP156',       /* HEPB_FEMALE_SEX_PARTNER_NBR */
          'HEP157',       /* HEPB_STD_TREATED_IND */
          'HEP159',       /* STREET_DRUG_INJECTED_IN6WKMON */
          'HEP160',       /* STREET_DRUG_USED_IN6WKMON */
          'HEP161',       /* HEMODIALYSIS_IN_LAST_6WKMON */
          'HEP162',       /* BLOOD_CONTAMINATION_IN6WKMON */
          'HEP163',       /* HEPB_BLOOD_RECEIVED_IN6WKMON  */
          'HEP165',       /* BLOOD_EXPOSURE_IN_LAST6WKMON */
          'HEP167',       /* HEPB_MED_DEN_EMPLOYEE_IN6WKMON */
          'HEP168',       /* HEPB_MED_DEN_BLOOD_CONTACT_FRQ */
          'HEP169',       /* HEPB_PUB_SAFETY_WORKER_IN6WKMO */
          'HEP170',       /* HEPB_PUBSAFETY_BLOODCONTACT_FRQ */
          'HEP171',       /* TATTOOED_IN6WKMON_BEFORE_ONSET */
          'HEP174',       /* PIERCING_IN6WKMON_BEFORE_ONSET */
          'HEP177',       /* DEN_WORK_OR_SURGERY_IN6WKMON */
          'HEP178',       /* NON_ORAL_SURGERY_IN6WKMON */
          'HEP179',       /* HSPTLIZED_IN6WKMON_BEFORE_ONSET */
          'HEP180',       /* LONGTERMCARE_RESIDENT_IN6WKMON */
          'HEP181',       /* B_INCARCERATED24PLUSHRSIN6WKMO */
          'HEP183',       /* B_INCARCERATED_6PLUS_MON_IND */
          'HEP186',       /* B_LAST_INCARCERATE_PERIOD_UNIT */
          'HEP187',       /* HEP_B_VACC_RECEIVED_IND */
          'HEP188',       /* HEP_B_VACC_SHOT_RECEIVED_NBR */
          'HEP190',       /* ANTI_HBSAG_TESTED_IND */
          'HEP191',       /* ANTI_HBS_POSITIVE_REACTIVE_IND */
          'HEP192',       /* HEP_C_CONTACTED_IND */
          'HEP195',       /* HEPC_MALE_SEX_PARTNER_NBR */
          'HEP196',       /* HEPC_FEMALE_SEX_PARTNER_NBR */
          'HEP197',       /* HEPC_STD_TREATED_IND */
          'HEP199',       /* MED_DEN_EMPLOYEE_IN_2WK6MO */
          'HEP200',       /* HEPC_MED_DEN_BLOOD_CONTACT_FRQ */
          'HEP201',       /* PUBLIC_SAFETY_WORKER_IN_2WK6MO */
          'HEP202',       /* HEPC_PUBSAFETY_BLOODCONTACTFRQ */
          'HEP203',       /* TATTOOED_IN2WK6MO_BEFORE_ONSET */
          'HEP204',       /* TATTOOED_IN2WK6MO_LOCATION */
          'HEP206',       /* PIERCING_IN2WK6MO_BEFORE_ONSET */
          'HEP207',       /* PIERCING_IN2WK6MO_LOCATION */
          'HEP209',       /* STREET_DRUG_INJECTED_IN_2WK6MO */
          'HEP210',       /* STREET_DRUG_USED_IN_2WK6MO */
          'HEP211',       /* HEMODIALYSIS_IN_LAST_2WK6MO */
          'HEP212',       /* BLOOD_CONTAMINATION_IN_2WK6MO */
          'HEP213',       /* HEPC_BLOOD_RECEIVED_IN_2WK6MO  */
          'HEP215',       /* BLOOD_EXPOSURE_IN_LAST2WK6MO */
          'HEP217',       /* DEN_WORK_OR_SURGERY_IN2WK6MO */
          'HEP218',       /* NON_ORAL_SURGERY_IN2WK6MO */
          'HEP219',       /* HSPTLIZED_IN2WK6MO_BEFORE_ONSET */
          'HEP220',       /* LONGTERMCARE_RESIDENT_IN2WK6MO */
          'HEP221',       /* INCARCERATED24PLUSHRSIN2WK6MO */
          'HEP222',       /* HEPC_INCARCERATE_FACILITY_TYPE */
          'HEP223',       /* C_INCARCERATED_6PLUS_MON_IND */         /* date ???? */
          'HEP226',       /* C_LAST_INCARCERATE_PERIOD_UNIT */
          'HEP227',       /* BLOOD_TRANSFUSION_BEFORE_1992 */
          'HEP228',       /* ORGAN_TRANSPLANT_BEFORE_1992 */
          'HEP229',       /* CLOT_FACTOR_CONCERN_BEFORE1987 */
          'HEP230',       /* LONG_TERM_HEMODIALYSIS_IND */
          'HEP231',       /* EVER_INJECT_NONPRESCRIBED_DRUG */
          'HEP233',       /* EVER_INCARCERATED_IND */
          'HEP234',       /* HEPC_STD_TREATED_IND */
          'HEP235',       /* HEPATITIS_CONTACTED_IND */
          'HEP236',       /* HEPATITIS_CONTACT_TYPE */
          'HEP238',       /* HEPC_MED_DEN_EMPLOYEE_IND */
          'HEP241',       /* PATIENT_MOTHER_BORN_OUT_USA */
          'HEP243',       /* MOTHER_HBSAG_POSITIVE_IND */
          'HEP244',       /* MOTHR_HBSAG_POSTV_POSTDELIVERY */
          'HEP246',       /* HEP_B_VACC_DOSE_CHILD_RECEIVED */
          'HEP250',       /* CHILD_RECEIVED_HBIG_IND */
          'HEP252',       /* OUTPATIENT_IV_INFUSION_IN6WKMO */
          'HEP253',       /* OUTPATIENT_IV_INFUSIONIN2WK6MO */
          'HEP260',       /* ANTIHCV_STCR_TEXT_1_UNIT */
          'HEP262',        /* ANTIHCV_STCR_TEXT_2_UNIT */
          'HEP263',        /* HEP_B_E_ANTIGEN */
          'HEP264',        /* HEP_B_DNA */
		  'HEP255'       /* NATION_CD */
          )
          output HEPchildobscodedsingle;

    /* multi-select coded questions */
    When (
          'HEP100',       /* REASON_FOR_TESTING */
          'HEP128',       /* DISEASE_DIAGNOSIS */
          'HEP130',       /* HEP_A_CONTACT_TYPE */
          'HEP140',       /* CNTRY_TRAVELD_OUT_USA_CAN */
          'HEP142',       /* CNTRY_NPP_TRAVELD_OUT_USA_CAN */
          'HEP153',       /* HEP_B_CONTACT_TYPE */
          'HEP172',       /* TATTOOED_IN6WKMON_LOCATION */
          'HEP175',       /* PIERCING_IN6WKMON_LOCATION */
          'HEP182',       /* HEPB_INCARCERATION_FACILITY_TY */
          'HEP193'        /* HEP_C_CONTACT_TYPE */
          )
          output HEPchildobscodedmulti;

    /* text questions */
    When (
	 		 'INV172',       /* INV_PATIENT_CHART_NBR */
          'HEP101',       /* OTHER_REASON_FOR_TESTING */
          'HEP116',       /* ANTIHCV_SIGNAL_TO_CUTOFF_RATIO */
          'HEP131',       /* HEP_A_OTHER_CONTACT_TYPE */
          'HEP145',       /* FOODBORNE_OUTBRK_FOOD_ITEM */
          'HEP154',       /* HEP_B_OTHER_CONTACT_TYPE */
          'HEP166',       /* BLOOD_EXPOSURE_IN6WKMON_OTHER */
          'HEP173',       /* TATTOOED_IN6WKMONOTHERLOCATION */
          'HEP176',       /* PIERCING_IN6WKMONOTHERLOCATION */
          'HEP194',       /* HEP_C_OTHER_CONTACT_TYPE */
          'HEP205',       /* TATTOOED_IN2WK6MOOTHERLOCATION */
          'HEP208',       /* PIERCING_IN2WK6MO_OTHER_LOCAT */
          'HEP216',       /* BLOOD_EXPOSURE_IN2WK6MO_OTHER */
          'HEP237',       /* HEPATITIS_OTHER_CONTACT_TYPE */
          'HEP259',       /* ANTIHCV_STCR_TEXT_1 */
          'HEP261'        /* ANTIHCV_STCR_TEXT_2 */
          ) 
          output HEPchildobstext;

    /* date questions */
    When (
          'HEP107',       /* PATIENT_PREGNANCY_DUE_DT */
          'HEP125',       /* TEMP_ALT_RESULT_DT_KEY */
          'HEP126',       /* TEMP_AST_RESULT_DT_KEY */
          'HEP151',       /* GLOBULIN_LAST_RECEIVED_YR */               
          'HEP164',       /* HEPB_BLOOD_RECEIVED_DT */
          'HEP214',       /* HEPC_BLOOD_RECEIVED_DT */
          'HEP245',       /* MOTHER_HBSAG_POSITIVE_DT */
          'HEP247',       /* HEPB_1STVACC_CHILD_RECEIVED_DT */
          'HEP248',       /* HEPB_2NDVACC_CHILD_RECEIVED_DT */
          'HEP249',       /* HEPB_3RDVACC_CHILD_RECEIVED_DT */
          'HEP251'        /* CHILD_RECEIVED_HBIG_DT */
          ) 
          output HEPchildobsdate;

    /* numeric questions */
    When (
			'HEP121',       /* ALT_SGPT_RESULT */
			'HEP122',       /* ALT_SGPT_RESULT_UPPER_LIMIT */
			'HEP123',       /* AST_SGOT_RESULT */
			'HEP124',       /* AST_SGOT_RESULT_UPPER_LIMIT */
			'HEP149',       /* HEP_A_VACC_LAST_RECEIVED_YR */
			'HEP158',       /* HEPB_STD_LAST_TREATMENT_YR */              /* numeric ? */
			'HEP184',       /* B_LAST6PLUSMON_INCARCERATE_YR */
			'HEP185',       /* BLAST6PLUSMO_INCARCERATEPERIOD */
			'HEP189',       /* HEP_B_VACC_LAST_RECEIVED_YR */
			'HEP198',       /* HEPC_STD_LAST_TREATMENT_YR */
			'HEP224',       /* C_LAST6PLUSMON_INCARCERATE_YR */
			'HEP225',       /* CLAST6PLUSMO_INCARCERATEPERIOD */
			'HEP232'        /* LIFETIME_SEX_PARTNER_NBR */               /* numeric ???? - error in doc */
          )
          output HEPchildobsnumeric;

    /* known orphan questions */
*    When (
		  'INV128',       /* PATIENT_HSPTLIZD_IND */
		  'INV132',       /* HSPTL_ADMISSION_DT */
		  'INV133',       /* HSPTL_DISCHARGE_DT */
		  'INV134',       /* HSPTLIZE_DURATION_DAYS */  
         
          'HEP103',       /* ILLNESS_ONSET_DT */
          'HEP108',       /* DIE_FRM_THIS_ILLNESS_IND */
          'HEP239',       /* RACE_CD */
          'HEP240',       /* PERSON_ETHNICITY_CD */
          'HEP242',       /* NATION_CD */
          'HEP255',       /* NATION_CD */
          'HEP256',       /* RACE_CAT_CD */
          'HEP257',       /* PERSON_ETHNICITY_CD */
          'HEP258'        /* RACE_CD */
          )
          output HEPchildobsorphan;

	Otherwise 
		  output HEPchildobsorphan;
  End;
Run;


/* get observation values (coded single, coded multi, text, date, numeric) */
%getobscode(HEPcodedsingle,HEPchildobscodedsingle);
%getobstxt(HEPtext,HEPchildobstext);
%getobsdate(HEPdate,HEPchildobsdate,from_time);
%getobsnum(HEPnumeric,HEPchildobsnumeric,numeric_value_1);

/* transpose each response data set */
%rows_to_columns(HEPcodedsingle,HEPcodedsingletrans);
%rows_to_columns(HEPtext,HEPtexttrans);
%rows_to_columns(HEPdate,HEPdatetrans);
%rows_to_columns(HEPnumeric,HEPnumerictrans);


/* combine transposed response data sets with root data to form complete Hepatitis Case Fact table */
Data Hepatitis_Case1;
  Merge HEPcodedsingletrans HEPtexttrans HEPdatetrans HEPnumerictrans;  /* HEProotobs */
  By public_health_case_uid root_obs_uid; 
Run;

/* In case user entered minimum data in public_health_case table and no data in Observation at all
the Hepatitis_Case will be empty so far.  So we need to join back to 
the public_health_case record in order to create a record in fact table.*/
proc sort data=Hepatitis_Case1; by public_health_case_uid; run;
proc sort data=rdbdata.PHCrootobs_NONINC out=HepPhc (keep=public_health_case_uid); 
by public_health_case_uid;
	where &whereclause; 
Run;
data Hepatitis_Case2;
	merge HepPhc Hepatitis_Case1 ;
	by public_health_case_uid;
run;




%add_common_inv_keys(Hepatitis_Case3,Hepatitis_Case2);

/* LDF_Group_Key */
%AddLDFGroupKeytoFactTable (Hepatitis_Case4,Hepatitis_Case3);


/********************************************/
/* create Multi Value Field outrigger table */

%getobscode(HEPcodedmulti,HEPchildobscodedmulti);

Proc Sort data=HEPcodedmulti; 
  By public_health_case_uid root_obs_uid child_obs_cd;
Run;

/* assign group key and cd sequence number */
Data HEPcodedmulti;
  Retain HEP_Multi_Val_Grp_Key 1
         cd_seq 1;
  Set HEPcodedmulti;
   By public_health_case_uid root_obs_uid child_obs_cd;
   If first.child_obs_cd then cd_seq=1;     /* initialize cd sequence number for each new phc_uid, root_obs_uid */
   Else cd_seq+1;                           /* increment cd sequence number for each subsequent child_obs_cd */
   If first.root_obs_uid then HEP_Multi_Val_Grp_Key+1;
   output;
Run;

/* transpose so column names go across */
%rows_to_columns2(HEPcodedmulti,HEPcodedmultitrans,response,
                  public_health_case_uid root_obs_uid HEP_Multi_Val_Grp_Key cd_seq);


Data rdbdata.HEP_Multi_Value_Field_Group (keep=public_health_case_uid root_obs_uid HEP_Multi_Val_Grp_Key)
	 rdbdata.HEP_Multi_Value_Field (drop=public_health_case_uid root_obs_uid cd_seq);

	Retain HEP_Multi_Val_Data_Key 1;
	if HEP_Multi_Val_Data_Key=1 then do;    /* output initial null record */
		HEP_Multi_Val_Grp_Key = 1;
		output rdbdata.HEP_Multi_Value_Field_Group;
		output rdbdata.HEP_Multi_Value_Field;
	end;
  Set HEPcodedmultitrans;
  By HEP_Multi_Val_Grp_Key cd_seq;

  /* create HEP_Multi_Value_Field_Group table */
  if first.HEP_Multi_Val_Grp_Key then output rdbdata.HEP_Multi_Value_Field_Group;

  /* create HEP_Multi_Value_Field table */
  HEP_Multi_Val_Data_Key+1;
  output rdbdata.HEP_Multi_Value_Field;
Run;

/* assign multi value key in fact table */
Proc SQL;
 Create table rdbdata.Hepatitis_Case as
  Select f.*, m.HEP_Multi_Val_Grp_Key
   From Hepatitis_Case4 f LEFT JOIN rdbdata.HEP_Multi_Value_Field_Group m
     On f.public_health_case_uid=m.public_health_case_uid
    and f.root_obs_uid=m.root_obs_uid;
Quit;

/* clean up bridge table */
Data rdbdata.HEP_Multi_Value_Field_Group;
  Set rdbdata.HEP_Multi_Value_Field_Group (keep=HEP_Multi_Val_Grp_Key);
run;

/*****************************************/

/* clean up fact table */
Data rdbdata.Hepatitis_Case;
  Set rdbdata.Hepatitis_Case (drop=public_health_case_uid root_obs_uid); 

  /* assign missing keys */
  If HEP_Multi_Val_Grp_Key=. then HEP_Multi_Val_Grp_Key=1;
  If ALT_RESULT_DT_KEY=. then ALT_RESULT_DT_KEY=1;
  If AST_RESULT_DT_KEY=. then AST_RESULT_DT_KEY=1;
  If LDF_Group_Key=. then LDF_Group_Key=1;
  PLACE_OF_BIRTH=NATION_CD;

  /* drop unused keys */
  Drop 
		INV_START_DT_key 
		DIAGNOSIS_DT_KEY 
		INV_RPT_DT_KEY
   	;
Run;
PROC SORT DATA=rdbdata.Hepatitis_Case NODUPKEY; BY  INVESTIGATION_KEY; RUN;


/***************************************************/

/**delete temporary data sets**/
PROC datasets library = work nolist;
delete
	HEPObsInvFrmQ
	HEPObsInvFrmQ1
	HEPchildobscodedsingle 
	HEPchildobscodedmulti 
	HEPchildobstext 
	HEPchildobsdate 
	HEPchildobsnumeric 
	HEPchildobsorphan
	HEPcodedsingle
	HEPtext
	HEPdate
	HEPnumeric
	HEPcodedsingletrans 
	HEPtexttrans 
	HEPdatetrans 
	HEPnumerictrans
	Hepatitis_Case
	Hepatitis_Case1
	Hepatitis_Case2
	Hepatitis_Case3
	Hepatitis_Case4
	HEPcodedmulti
	Hepcodedmultitrans
	Hepphc;
run;
quit;

