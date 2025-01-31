%etllib;
OPTIONS COMPRESS=YES;
options fmtsearch=(nbsfmt);
%include etlpgm(etlmacro.sas);
%global etlerr;
%let etlerr=0;

%macro etlinit;
proc datasets lib=rdbdata nolist;
	delete 	
		antimicrobial
		antimicrobial_group
		batchitem
		batchrow
		bmird_case
		bmird_multi_value_field
		bmird_multi_value_field_group
		case_count
		condition
		datetable
		confirmation_method
		confirmation_method_group
		contact_info
		crs_case
		detail_ethnicity
		generic_case
		hep_multi_value_field
		hep_multi_value_field_group
		hepatitis_case
		investigation
		lab_result_comment
		lab_result_val
		lab_rpt_user_comment
		lab_test
		lab_test_result
		user_profile
		ldf_data
		ldf_group
		measles_case
		morb_rpt_user_comment
		morbidity_report
		morbidity_report_event
		notification
		notification_event
		patient_ldf_group
		pertussis_case
		pertussis_suspected_source_fld
		pertussis_suspected_source_grp
		pertussis_treatment_field
		pertussis_treatment_group
		phc_keys
		phcchildobs
		phcrootobs
		race
		result_comment_group
		rubella_case
		summary_case_group
		summary_report_case
		test_result_grouping
		treatment
		treatment_event
		vaccination
		LDF_Dimensional
		SR100
		HEP100
		LAB100
		LAB101
		/*NEDSS_LINK*/
		TB_DATAMART
		TB_HIV_DATAMART
		VAR_DATAMART
		BMIRD_STREP_PNEUMO_DATAMART
		MORBIDITY_REPORT_DATAMART
		AGGREGATE_DATAMART
		CASE_LAB_DATAMART
		INV_SUMM_DATAMART
		PAGE_CASE
	;
run;
quit;
%mend etlinit;
%etlinit;
%macro checkerr;
	%if &syserr > 4 %then %let etlerr=%eval(&etlerr+1);
	%put syserr=&syserr;
	%if &etlerr !=0 %then %do;
		%put dataset=&syslast;
		%put syserr=&syserr;
	%end;
%mend checkerr;


%macro MasterEtl;


%if  %SYSFUNC(LIBREF(nbs_ods)) NE 0 %then %goto liberr;
%if  %SYSFUNC(LIBREF(nbs_srt)) NE 0 %then %goto liberr;
%if  %SYSFUNC(LIBREF(nbs_rdb)) NE 0 %then %goto liberr;
%if  %SYSFUNC(LIBREF(nbsfmt)) NE 0 %then %goto liberr;

data rdbdata.codeset;
set nbs_rdb.codeset;
run;
data _null_;
if 0 then set rdbdata.codeset nobs=codeset_nobs;
call symput('codeset_nobs', put(codeset_nobs,22.));
stop;
run;
%if &codeset_nobs =0 %then %do;
%put &codeset_nobs;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start codeset*********';
run;
%include etlpgm (codeset.sas);
%end;
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End codeset*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start etlformat*********';
run;
%include etlpgm(etlformat.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End etlformat*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start ConditionDim*********';
run;
%include etlpgm(ConditionDim.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End ConditionDim*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start DateDim*********';
run;
%include etlpgm(DateDim.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End DateDim*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start User_profile*********';
run;

%checkerr;
%if &etlerr > 0 %then %goto finish;

%include etlpgm (User_profile.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End User_profile*********';
run;
/*
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Preprocess_Health_check*********';
run;
%include etlpgm (Preprocess_Health_check.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Preprocess_Health_check*********';
run;
*/
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start PatientDimension*********';
run;
%include etlpgm (PatientDimension.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End PatientDimension*********';
run;


Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start ProviderDimension*********';
run;
%include etlpgm (ProviderDimension.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End ProviderDimension*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start OrganizationDimension*********';
run;
%include etlpgm (OrganizationDimension.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End OrganizationDimension*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start LDF_Data*********';
run;
%include etlpgm (LDF_Data.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End LDF_Data*********';
run;
/*
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start ContactDim*********';
run;
%include etlpgm (ContactDim.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End ContactDim*********';
run;
*/
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Geocoding_location*********';
run;
%include etlpgm (Geocoding_location.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Geocoding_location*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start PHCobservations*********';
run;
%include etlpgm (PHCobservations.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End PHCobservations*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start InvestigationDim*********';
run;
%include etlpgm (InvestigationDim.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End InvestigationDim*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Case_Count*********';
run;
%include etlpgm (Case_Count.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Case_Count*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start PERTUSSIS_Case*********';
run;
%include etlpgm (PERTUSSIS_Case.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End PERTUSSIS_Case*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Measles_Case*********';
run;
%include etlpgm (Measles_Case.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Measles_Case*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start CRS_Case*********';
run;
%include etlpgm (CRS_Case.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End CRS_Case*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Generic_Case*********';
run;
%include etlpgm (Generic_Case.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Generic_Case*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Rubella_Case*********';
run;
%include etlpgm (Rubella_Case.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Rubella_Case*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start BMIRD_Case*********';
run;
%include etlpgm (BMIRD_Case.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End BMIRD_Case*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Hepatitis_Case*********';
run;
%include etlpgm (Hepatitis_Case.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Hepatitis_Case*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Event_Metrics*********';
run;
%include etlpgm (Event_Metrics.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Event_Metrics*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Notification_Event*********';
run;
%include etlpgm (Notification_Event.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Notification_Event*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Lab_Test*********';
run;
%include etlpgm (Lab_Test.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Lab_Test*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Morbidity_Report*********';
run;
%include etlpgm (Morbidity_Report.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Morbidity_Report*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Lab_Test_Result*********';
run;
%include etlpgm (Lab_Test_Result.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Lab_Test_Result*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Lab_Result_Val*********';
run;
%include etlpgm (Lab_Result_Val.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Lab_Result_Val*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Treatment_Event*********';
run;
%include etlpgm (Treatment_Event.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Treatment_Event*********';
run;
/*Data _Null_;*/
/*	format Time Datetime20.;*/
/*	Time=datetime();*/
/*	put '	Start Time:   ' Time   Datetime20.;*/
/*	put '*****The Start Vaccination*********';*/
/*run;*/
/*%include etlpgm (Vaccination.sas);*/
/*Data _Null_;*/
/*	format Time1 Datetime20.;*/
/*	Time1=datetime();*/
/*	put '	End Time:   ' Time1   Datetime20.;*/
/*	put '*****The End Vaccination*********';*/
/*run;*/
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The Start VACCINATION RECORD*********';
run;%include etlpgm (VaccinationRecord.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End VACCINATION RECORD*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start SummaryReportFact*********';
run;
%include etlpgm (SummaryReportFact.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End SummaryReportFact*********';
run;
/*Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start Post_ETL*********';
run;
%include etlpgm (Post_ETL.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End Post_ETL*********';
run;
*/
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start DBLoad*********';
run;
%include etlpgm (DBLoad.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End DBLoad*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start PamCases*********';
run;
%include etlpgm (PamCases.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End PamCases*********';
run;
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The Start PLACE DIMENSION CASE*********';
run;%include etlpgm (PlaceDimension.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End PLACE DIMENSION CASE*********';
run;
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start PAGE_CASE*********';
run;
%include etlpgm (Page_Case.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End PAGE_CASE*********';
run;    
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start LDF_Dimensional*********';
run;
%include etlpgm(LDF_Dimensional.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End LDF_Dimensional*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start SR100*********';
run;
%include etlpgm (SR100.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End SR100*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start HEP100*********';
run;
%include etlpgm (HEP100.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End HEP100*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start HEPATITIS_CASE_DATAMART*********';
run;
%include etlpgm (HEPATITIS_CASE_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End HEPATITIS_CASE_DATAMART*********';
run;
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The Start LAB100*********';
run;
%include etlpgm (LAB100.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End LAB100*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start LAB101*********';
run;
%include etlpgm (LAB101.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End LAB101*********';
run;

/*%include etlpgm (NEDSS_LINK.sas);*/

Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start TB_DATAMART*********';
run;
%include etlpgm (TB_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End TB_DATAMART*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start AGGREGATE_DATAMART*********';
run;
%include etlpgm (AGGREGATE_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End AGGREGATE_DATAMART*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start TB_HIV_DATAMART*********';
run;
%include etlpgm (TB_HIV_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End TB_HIV_DATAMART*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start VAR_DATAMART*********';
run;
%include etlpgm (VAR_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End VAR_DATAMART*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start MORBIDITY_REPORT_DATAMART*********';
run;
%include etlpgm (MORBIDITY_REPORT_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End MORBIDITY_REPORT_DATAMART*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start BMIRD_STREP_PNEUMO_DATAMART*********';
run;
%include etlpgm (BMIRD_STREP_PNEUMO_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End BMIRD_STREP_PNEUMO_DATAMART*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start CASE_LAB_DATAMART*********';
run;
%include etlpgm (CASE_LAB_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End CASE_LAB_DATAMART*********';
run;
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The Start HEALTH_CHECK*********';
run;%include etlpgm (HEALTH-CHECK.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End HEALTH_CHECK*********';
run;
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The Start INTERVIEW RECORD*********';
run;%include etlpgm (interviewRecord.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End INTERVIEW RECORD*********';
run;
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The Start CONTACT RECORD CASE*********';
run;%include etlpgm (Contact_Record_Case.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End CONTACT RECORD CASE*********';
run;
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The Start CASE MANAGEMENT CASE*********';
run;%include etlpgm (Case_Management.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End CASE MANAGEMENT CASE*********';
run;
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The Start STD HIV CASE*********';
run;%include etlpgm (STD_AND_HIV_CASE.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End STD HIV CASE*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start INV_SUMM_DATAMART*********';
run;
%include etlpgm (INV_SUMM_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End INV_SUMM_DATAMART*********';
run;
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The Start STD_DATAMART*********';
run;%include etlpgm (STD_Datamart.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End STD_DATAMART*********';
run;

%include "&SAS_REPORT_HOME/custom/Custom_controller.sas";

%goto finish;

%liberr:
	%put Libname statement error &syslibrc &SYSMSG ;
	%let etlerr=1;	

%finish:

/*need to put more checking here*/
Data _Null_;
	length Etlmsg $20.;
	if  &etlerr = 0
		then Etlmsg='Successful';
		else  Etlmsg='Incomplete';
	put 'ETL Process Status: '  Etlmsg $char20.;

	format Start_Time End_Time Datetime20. Total_Time TOD8.;
	Start_Time=input("&SYSDATE9"||' '||"&SYSTIME",DATETIME20.);
	End_Time=datetime();
	Total_Time=End_Time-Start_time;
	put 'ETL Processing Time:';
	put '	Start Time: ' Start_Time Datetime20.;
	put '	End Time:   ' End_Time   Datetime20.;
	put '	Total Time: ' Total_Time time8.;
	put '*****The End*********';
run;

data rdbdata.etlstatus;
format etlendtime datetime20.;
etlendtime=datetime();
run;
%mend MasterEtl;
%MasterEtl;

%put &etlerr;
