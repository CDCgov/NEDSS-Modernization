/**********************************************************************
*																	  *
*	Program Name: Treatment_Event.sas                                 *
*	Purpose: Extract treatment records from ODS and transform         *   
*			 them into Treatment_Event & Treatment datasets 		  * 
*	Produces Datasets:treatment.sas7bdat & treatment_event.sas7bdat   *
*																	  *
*																	  *			
***********************************************************************/


PROC SQL;
	create table treatment_all1 AS 
	select
	rx1.treatment_uid, 
	rx1.local_id					AS Treatment_local_id,
	rx1.cd_desc_txt					AS Treatment_nm,
	rx1.program_jurisdiction_oid	AS Treatment_oid,
	TRANSLATE(rx1.txt,' ' ,'0D0A'x)		AS Treatment_comments, 
	rx1.shared_ind					AS Treatment_shared_ind,
	rx1.cd,
	rx2.effective_from_time			AS Treatment_dt,
	rx2.cd							AS Treatment_drug	label='Rx Drug',
	put(rx2.cd, $RXDRUG.)			AS Treatment_drug_nm,
	rx2.dose_qty					AS Treatment_dosage_strength,
	rx2.dose_qty_unit_cd			AS Treatment_dosage_strength_unit,
	rx2.interval_cd					AS Treatment_frequency,
	rx2.effective_duration_amt		AS Treatment_duration,
	rx2.effective_duration_unit_cd	AS Treatment_duration_unit,
	rx2.route_cd					AS Treatment_route,
	act1.target_act_uid 			AS public_health_case_uid,
	coalesce(pk.patient_key,viewPatientKeys.patient_key)           AS patient_key,
	pk.condition_key,
	pk.investigation_key,
	1								AS Morb_rpt_key,
	prv.provider_key            					AS TREATMENT_PHYSICIAN_KEY,
	1 								AS Treatment_count,
	coalesce(dt.Date_Key,1)			AS Treatment_dt_key,
	org1.Organization_key 					AS Treatment_providing_org_key,
	rx1.record_status_cd
	FROM nbs_ods.treatment AS rx1 
	INNER JOIN nbs_ods.Treatment_administered AS rx2 
	ON rx1.treatment_uid = rx2.treatment_uid 

	LEFT JOIN nbs_ods.act_relationship AS act1 
	ON rx1.Treatment_uid = act1.source_act_uid
	and act1.target_class_cd = 'CASE'
	and act1.source_class_cd = 'TRMT'
	and act1.type_cd='TreatmentToPHC'

	LEFT JOIN nbs_ods.participation			AS par
	ON rx1.Treatment_uid = par.act_uid
	and par.type_cd = 'ReporterOfTrmt'
	and par.subject_class_cd ='ORG'
	and par.act_class_cd = 'TRMT'

	LEFT JOIN nbs_rdb.d_Organization			AS org1
	ON par.subject_entity_uid = org1.Organization_uid


	LEFT JOIN nbs_ods.participation			AS par1
	ON rx1.Treatment_uid = par1.act_uid
	and par1.type_cd = 'ProviderOfTrmt'
	and par1.subject_class_cd ='PSN'
	and par1.act_class_cd = 'TRMT'

	LEFT JOIN nbs_rdb.d_provider			AS prv
	ON par1.subject_entity_uid = prv.provider_uid

	LEFT JOIN rdbdata.Datetable	AS dt
	ON datepart(rx2.effective_from_time)*24*60*60 = dt.DATE_MM_DD_YYYY

	/*get investigation_key and condition_key*/
	LEFT JOIN nbs_ods.act_relationship as act2
	ON rx1.Treatment_uid = act2.source_act_uid
	and act2.type_cd = 'TreatmentToPHC'
	LEFT JOIN rdbdata.phc_keys as pk
	ON act2.target_act_uid = pk.public_health_case_uid 
	LEFT JOIN nbs_ods.uvw_treatment_patient_keys as  viewPatientKeys
    ON rx1.treatment_uid = viewPatientKeys.treatment_uid

;
quit;

%assign_key(treatment_all1, treatment_key);
/*Staging table to create morb key*/
PROC SQL;
	CREATE TABLE treatment_morbidity AS
	SELECT DISTINCT morb.Morb_rpt_key,  t.treatment_uid FROM nbs_rdb.Morbidity_report AS morb, nbs_ods.act_relationship AS act, treatment_all1 AS t
	where t.treatment_uid = act.source_act_uid AND 
	act.target_act_uid = morb.morb_rpt_uid;
quit;
/*Setting Morb_rpt_key */
PROC SQL;
	update treatment_all1
	set morb_rpt_key = 
	(select morb_rpt_key FROM treatment_morbidity tm where treatment_all1.treatment_uid = tm.treatment_uid);
quit;

PROC SQL;
	create table treatment_custom AS 
	select treatment_key, cd, Treatment_nm FROM treatment_all1;

quit;

DATA treatment_custom ;
	SET treatment_custom ;
	IF cd = 'OTH' THEN CUSTOM_TREATMENT = Treatment_nm;
	ELSE CUSTOM_TREATMENT = '';
	OUTPUT;
RUN;

DATA treatment_custom 
	(DROP = cd 
	Treatment_nm);
	SET treatment_custom;
run;

DATA treatment_all1;
	MERGE treatment_all1 treatment_custom;
	BY Treatment_Key;
run;

/* Treatment LDF */
Proc SQL;
  Create table treatment_all as
   Select fact.*, ldf.LDF_Group_Key
     From treatment_all1 fact LEFT JOIN LDF_Group ldf
       On fact.Treatment_uid = ldf.business_object_uid;
 Quit;

DATA Treatment (keep=
			Treatment_Key 
			Treatment_local_id
			Treatment_uid 
			Treatment_nm
			Treatment_drug 
			Treatment_dosage_strength 
			Treatment_dosage_strength_unit
			Treatment_frequency
			Treatment_duration
			Treatment_duration_unit
			Treatment_comments
			Treatment_route
			Custom_treatment
			Treatment_shared_ind
			Treatment_oid
			record_status_cd
			)

	Treatment_Event (keep=
			Treatment_Key
			Treatment_Dt_Key 
			Condition_Key 
			TREATMENT_PHYSICIAN_KEY 
			Patient_Key 
			Treatment_providing_org_key
			Morb_Rpt_Key
			Investigation_Key 
			Treatment_Count
			LDF_Group_Key
			RECORD_STATUS_CD
			)
			
	;

SET treatment_all;
OUTPUT Treatment_Event;
OUTPUT Treatment;
run;

DATA rdbdata.Treatment;
	SET Treatment;
	If record_status_cd = '' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE';
run;
DATA rdbdata.Treatment_Event;
SET Treatment_Event;
if condition_key =. then condition_key = 1;
if investigation_key =. then investigation_key = 1;
if Treatment_Dt_Key =. then Treatment_Dt_Key =1;
if Treatment_providing_org_key=. then Treatment_providing_org_key=1;
if LDF_Group_Key =. then LDF_Group_Key =1;
If record_status_cd = '' then record_status_cd = 'ACTIVE';
If record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE';
if Morb_Rpt_Key =. then Morb_Rpt_Key=1;
if TREATMENT_PHYSICIAN_KEY =. then TREATMENT_PHYSICIAN_KEY=1;
if Patient_Key=. then Patient_Key=1;
run;


PROC datasets library = work nolist;
delete 
	Treatment_morbidity
	Treatment_event
	Treatment_custom
	Treatment_all
	Treatment_all1
	Treatment
;
run;
quit;

/**********Treatment RDB Table Attributes*************

TREATMENT_KEY, 
TREATMENT_UID, 
TREATMENT_LOCAL_ID, 
TREATMENT_NM, 
TREATMENT_DRUG,
TREATMENT_DOSAGE_STRENGTH,
TREATMENT_DOSAGE_STRENGTH_UNIT,
TREATMENT_FREQUENCY,
TREATMENT_DURATION,
TREATMENT_DURATION_UNIT,
TREATMENT_COMMENTS,
TREATMENT_ROUTE,
CUSTOM_TREATMENT,
TREATMENT_SHARED_IND,
TREATMENT_OID


*******************************************************/
/*********Treatment_Event RDB Table Attributes*********

TREATMENT_DT, 
TREATMENT_PROVIDING_ORG_KEY, 
PATIENT_KEY, 
TREATMENT_COUNT, 
TREATMENT_KEY, 
MORB_RPT_KEY, 
PERSON_KEY, 
INVESTIGATION_KEY, 
CONDITION_KEY

******************************************************/


