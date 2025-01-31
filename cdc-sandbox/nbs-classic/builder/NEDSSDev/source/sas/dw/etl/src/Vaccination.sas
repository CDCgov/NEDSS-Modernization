/**********************************************************
*														  *	
*	              Vaccination Fact						  *
*														  *
*														  *		
*														  *			
***********************************************************/

proc sql;
create table vacageUnit as 
select 	act.target_act_uid		as intervention_uid label='Intervention uid',
		obs.observation_uid,
		obs.cd,
		obc.code
from 	nbs_ods.act_relationship as act,
		nbs_ods.observation		as obs,
		nbs_ods.obs_value_coded	as obc
where 	
		act.source_act_uid  = obs.observation_uid
		and act.type_cd = 'VAC105'
		and act.source_class_cd='OBS'
		and act.target_class_cd='INTV'
		and obs.observation_uid = obc.observation_uid
		and  obs.cd = 'VAC106'
order by 1
	;
quit;
proc sql;
create table vacage as 
select 	act.target_act_uid			as intervention_uid label='Intervention uid',
		obs.observation_uid,
		obs.cd,
		obc.numeric_value_1
from 	nbs_ods.act_relationship 	as act,
		nbs_ods.observation			as obs,
		nbs_ods.obs_value_numeric	as obc
where 	
		act.source_act_uid  = obs.observation_uid
		and act.type_cd = 'VAC105'
		and act.source_class_cd='OBS'
		and act.target_class_cd='INTV'
		and obs.observation_uid = obc.observation_uid
		and  obs.cd = 'VAC105'
order by 1
	;
quit;

data vacageAll (keep=intervention_uid Age_At_Vaccination Age_At_Vaccination_unit);
merge vacage vacageUnit;
by intervention_uid;
Age_At_Vaccination_unit = put(code,$VAC106f.);
rename 	numeric_value_1 = Age_At_Vaccination;
run;
proc sort data=vacageAll nodupkey; by intervention_uid; run;


proc sql;
create table Vaccination as 
select 	vac.Intervention_uid,
		vac.local_id				as VACCINATION_RECORD_ID,
		vac.record_status_cd ,
		par1.subject_entity_uid		as patient_uid	label='Patient uid',
		par2.subject_entity_uid		as provider_uid,
		par3.subject_entity_uid		as provider_org_uid,
		vac.ACTIVITY_FROM_TIME		as date_administered,
		put(vac.target_site_cd, $vac104f.)			as VACCINATION_ANATOMICAL_SITE,

		mat.cd_desc_txt				as VACCINATION_ADMIND_NM,
		/*mat.nm						as VACCINATION_ADMINISTERED,*/
		org.nm_txt,
		man.lot_nm					as VACCINE_LOT_NBR,
		man.expiration_time			as VACCINE_EXPIRATION_DT,

		age.AGE_AT_VACCINATION,
		age.AGE_AT_VACCINATION_UNIT,

		org2.Organization_key		as VACCINE_MANUFACTURER_ORG_KEY,
		dt.Date_key					as VACCINATION_DT_KEY,
		prv.provider_key				as VACCINE_GIVEN_BY_KEY,
		pat.patient_key				as PATIENT_KEY,
		orgprv.Organization_key		as VACCINE_GIVEN_BY_ORG_KEY,		
		act.target_act_uid			as public_health_case_uid,
		phc.INVESTIGATION_KEY
	
from 	nbs_ods.Intervention 		as vac

	/*  Patient */
	left join nbs_ods.participation as par1
		on vac.Intervention_uid = par1.act_uid 
		and par1.type_cd = 'SubOfVacc'
		and par1.act_class_cd = 'INTV'
		and par1.subject_class_cd = 'PSN'

	/*  Provider */
	left join nbs_ods.participation as par2
		on vac.Intervention_uid = par2.act_uid 
		and par2.type_cd = 'PerformerOfVacc'
		and par2.act_class_cd = 'INTV'
		and par2.subject_class_cd = 'PSN'

	/*  Provider Org */
	left join nbs_ods.participation as par3
		on vac.Intervention_uid = par3.act_uid 
		and par3.type_cd = 'PerformerOfVacc'
		and par3.act_class_cd = 'INTV'
		and par3.subject_class_cd = 'ORG'

	/*  Vac Given Material */
	left join nbs_ods.participation as par4
		on vac.Intervention_uid = par4.act_uid 
		and par4.type_cd = 'VaccGiven'
		and par4.act_class_cd = 'INTV'
		and par4.subject_class_cd = 'MAT'

	/* vaccine administered*/
	left join	nbs_ods.material				as mat
		on par4.subject_entity_uid = mat.material_uid

	/* vaccine manufacturer*/
	left join	nbs_ods.role					as rol1
		on mat.material_uid = rol1.subject_entity_uid
		and rol1.subject_class_cd ='MAT'
		and rol1.cd = 'MfgdVaccine'
		and rol1.SCOPING_CLASS_CD ='ORG'
		and rol1.SCOPING_ROLE_CD = 'MfgrOfVaccine'
	left join	nbs_ods.Organization_name		as org
		on par3.subject_entity_uid = org.organization_uid
		/*on rol1.SCOPING_ENTITY_UID = org.organization_uid*/
	
	/* Lot_nm and Expiration time*/
	left join	nbs_ods.manufactured_material 	as man
		on mat.material_uid = man.material_uid

	left join 	vacageAll						as age
		on	age.intervention_uid = vac.intervention_uid
	
	/* keys */
	left join	nbs_rdb.d_Organization			as org2
		on rol1.SCOPING_ENTITY_UID = org2.Organization_uid
	left join 	rdbdata.Datetable				as	dt
		on dt.DATE_MM_DD_YYYY = vac.ACTIVITY_FROM_TIME
	/* provider key */
	left join 	nbs_rdb.d_provider				as	prv
		on prv.provider_uid = par2.subject_entity_uid
	/* patient key*/
	left join 	nbs_rdb.d_patient				as	pat
		on pat.patient_uid = par1.subject_entity_uid

	/* provider org key */
	left join 	nbs_rdb.d_Organization			as	orgprv
		on orgprv.Organization_uid = org.organization_uid

	/* investigation key */
	/* There seem to a problem: one vac was linked to two PHC*/
	left join	nbs_ods.act_relationship		as act
		on	act.source_act_uid = vac.intervention_uid
		and act.type_cd = '1180'
		and act.target_class_cd = 'CASE'
		and act.source_class_cd = 'INTV'
		left join nbs_rdb.Investigation				as phc
		on phc.case_uid = act.target_act_uid
		
;
quit;

data Vaccination;
set Vaccination;
IF VACCINE_MANUFACTURER_ORG_KEY=. THEN VACCINE_MANUFACTURER_ORG_KEY=1;
IF  VACCINE_GIVEN_BY_KEY=. THEN  VACCINE_GIVEN_BY_KEY=1;
IF  VACCINE_GIVEN_BY_ORG_KEY=. THEN  VACCINE_GIVEN_BY_ORG_KEY=1;
run;
/* VACCINATION LDF */
Proc SQL;
  Create table vaccinationall as
   Select fact.*, ldf.LDF_Group_Key
     From Vaccination fact LEFT JOIN LDF_Group ldf
       On fact.Intervention_uid = ldf.business_object_uid;
 Quit;

data vaccinationall;
set vaccinationall;
if patient_key = . then patient_key=1; run;


data vaccinationall;
	set vaccinationall;
	if VACCINATION_DT_KEY =. then VACCINATION_DT_KEY = 1;
	where patient_key ~=1;
run;

data vaccinationall;
	set vaccinationall;
	If record_status_cd = '' then record_status_cd = 'ACTIVE';
	If record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE';
	IF  INVESTIGATION_KEY=. THEN  INVESTIGATION_KEY=1;
run;


proc sort data=vaccinationall;
by  intervention_uid;

Data rdbdata.Vaccination;
set vaccinationall (drop =
	patient_uid
	provider_uid
	provider_org_uid
	date_administered
	nm_txt
	intervention_uid
	public_health_case_uid
	
	)
;
run;


/**Delete temporary data sets**/
PROC datasets library = work nolist;
delete 
	vacageUnit
	vacage
 	vacageAll
	VaccinationAll
	Vaccination
;
run;
quit;


		

/*
VACCINE_MANUFACTURER_ORG_KEY,
VACCINATION_DT,
VACCINE_GIVEN_BY_ORG_KEY, 
PATIENT_KEY,
VACCINATION_ADMIND_NM, 
VACCINATION_RECORD_ID,
VACCINATION_ANATOMICAL_SITE,
AGE_AT_VACCINATION, 
AGE_AT_VACCINATION_UNIT,
VACCINE_LOT_NBR,
VACCINE_EXPIRATION_DT,
ADULT_RECOMMENDED_DOSES,
CHILD_RECOMMENDED_DOSES,
VACCINE_GIVEN_BY_KEY,
INVESTIGATION_KEY
*/
