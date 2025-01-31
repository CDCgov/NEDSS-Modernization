%macro VAR_PAM;
proc sql;
 create table PAT_VAR_keystore as
select F_S_VAR_pam.VAR_pam_uid, F_S_VAR_pam.person_uid, patient.patient_key as patient_key 'patient_key', provider_uid,PHYSICIAN_UID 
	from nbs_rdb.D_patient patient, nbs_rdb.f_s_VAR_pam
	where f_s_VAR_pam.person_uid= patient.patient_uid;
 quit;
proc sql;
 create table PAT_VAR_prov1_keystore as
select VAR_pam_uid, PAT_VAR_keystore.person_uid, patient_key,PHYSICIAN_UID, PAT_VAR_keystore.provider_uid, person.provider_key as provider_key 'provider_key'
	from nbs_rdb.D_PROVIDER PERSON, PAT_VAR_keystore
	where PAT_VAR_keystore.provider_uid= person.PROVIDER_uid;
 quit;
proc sql;
 create table PAT_VAR_prov2_keystore as
select VAR_pam_uid, b.person_uid, patient_key,  b.provider_uid, b.provider_key, a.PROVIDER_key as Physician_key 'Physician_key'
	from nbs_rdb.D_PROVIDER a, PAT_VAR_prov1_keystore b
	where b.PHYSICIAN_UID= a.PROVIDER_uid;
 quit;
 PROC SORT DATA=PAT_VAR_prov2_keystore nodupkey; BY VAR_pam_uid; RUN;
 Proc sql;
CREATE TABLE  D_RASH_LOC_GEN AS SELECT DISTINCT D_RASH_LOC_GEN_GROUP_KEY, VAR_PAM_UID FROM nbs_rdb.D_RASH_LOC_GEN;
CREATE TABLE  D_PCR_SOURCE AS SELECT DISTINCT D_PCR_SOURCE_GROUP_KEY, VAR_PAM_UID FROM nbs_rdb.D_PCR_SOURCE;
quit;
proc sql;
create table HOSPITAL_UID_keystore as
	select F_S_VAR_pam.VAR_pam_uid, F_S_VAR_pam.HOSPITAL_UID, organization_key as hospital_key 'hospital_key'
	from nbs_rdb.f_s_VAR_pam left outer join nbs_rdb.D_organization organization
	on f_s_VAR_pam.HOSPITAL_uid= organization.organization_UID;
create table ORG_AS_REPORTER_UID_keystore as
	select F_S_VAR_pam.VAR_pam_uid, F_S_VAR_pam.ORG_AS_REPORTER_UID, organization_key as ORG_AS_REPORTER_key 'ORG_AS_REPORTER_key'
	from nbs_rdb.f_s_VAR_pam left outer join nbs_rdb.D_organization organization
	on f_s_VAR_pam.ORG_AS_REPORTER_UID= organization.organization_UID;
create table PERSON_AS_REPORTER_keystore as
	select F_S_VAR_pam.VAR_pam_uid, F_S_VAR_pam.PERSON_AS_REPORTER_UID, PROVIDER_key as PERSON_AS_REPORTER_key 'PERSON_AS_REPORTER_key'
 	from nbs_rdb.f_s_VAR_pam left outer join nbs_rdb.D_PROVIDER person
 	on f_s_VAR_pam.PERSON_AS_REPORTER_UID= person.PROVIDER_uid;
quit;
data HOSPITAL_UID_keystore;
set HOSPITAL_UID_keystore;
if hospital_key =. then hospital_key=1;
run;
data ORG_AS_REPORTER_UID_keystore;
set ORG_AS_REPORTER_UID_keystore;
if ORG_AS_REPORTER_key =. then ORG_AS_REPORTER_key=1;
run;
data PERSON_AS_REPORTER_keystore;
set PERSON_AS_REPORTER_keystore;
if PERSON_AS_REPORTER_key =. then PERSON_AS_REPORTER_key=1;
run;
proc sql;
create table F_VAR_PAM as SELECT     
	D_RASH_LOC_GEN.D_RASH_LOC_GEN_GROUP_KEY, 
	D_PCR_SOURCE.D_PCR_SOURCE_GROUP_KEY, 
    KEYSTORE.VAR_PAM_UID, 
    KEYSTORE.patient_key AS person_key, 
	KEYSTORE.Physician_key,
	KEYSTORE.provider_uid, 
	KEYSTORE.provider_key AS D_PERSON_RACE_GROUP_KEY, 
    KEYSTORE.provider_key, 
	PERSON_REPORTER.PERSON_AS_REPORTER_key AS PERSON_AS_REPORTER_key,
	REPORTING_ORG.ORG_AS_REPORTER_key AS ORG_AS_REPORTER_key,
	HOSPITAL.hospital_key AS hospital_key,
	VAR.D_VAR_PAM_KEY,
	INV.INVESTIGATION_KEY,
	DATE.date_key as add_date_key, DATE2.date_key as last_chg_date_key
FROM   D_RASH_LOC_GEN 
       INNER JOIN
       PAT_VAR_prov2_keystore AS KEYSTORE ON D_RASH_LOC_GEN.VAR_PAM_UID = KEYSTORE.VAR_PAM_UID 
       INNER JOIN
       D_PCR_SOURCE ON KEYSTORE.VAR_PAM_UID = D_PCR_SOURCE.VAR_PAM_UID 
	   INNER JOIN
       nbs_rdb.D_VAR_PAM AS VAR ON KEYSTORE.VAR_PAM_UID = VAR.VAR_PAM_UID 
	   INNER JOIN
       HOSPITAL_UID_keystore HOSPITAL ON KEYSTORE.VAR_PAM_UID = HOSPITAL.VAR_PAM_UID 
       INNER JOIN
       ORG_AS_REPORTER_UID_keystore AS REPORTING_ORG ON KEYSTORE.VAR_PAM_UID = REPORTING_ORG.VAR_PAM_UID 
       INNER JOIN
       PERSON_AS_REPORTER_keystore  AS PERSON_REPORTER ON KEYSTORE.VAR_PAM_UID = PERSON_REPORTER.VAR_PAM_UID 
	   INNER JOIN
       NBS_RDB.INVESTIGATION INV ON INV.CASE_UID = VAR.VAR_PAM_UID 
	   INNER JOIN
       NBS_RDB.event_metric em ON em.EVENT_UID = VAR.VAR_PAM_UID 
	   left outer join 
	   nbs_rdb.RDB_DATE DATE 	on DATEPART(DATE.DATE_MM_DD_YYYY)=DATEPART(em.add_time)
	   left outer join 
		nbs_rdb.RDB_DATE DATE2 on DATEPART(DATE2.DATE_MM_DD_YYYY)=DATEPART(em.last_chg_time);
quit;
PROC SORT DATA=F_VAR_PAM nodupkey; BY person_key; RUN;
%DBLOAD (F_VAR_PAM, F_VAR_PAM);
%mend VAR_PAM;
PROC SQL;
CREATE TABLE VAR_PORT_IND 
AS
select PORT_REQ_IND_CD from nbs_srt.condition_code where condition_cd ='10030';
QUIT;
DATA _null_;
  set VAR_PORT_IND;
  if PORT_REQ_IND_CD='T' then call execute('%VAR_PAM');
RUN;
