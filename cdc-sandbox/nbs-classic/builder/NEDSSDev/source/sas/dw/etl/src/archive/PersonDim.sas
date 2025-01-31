/*************************************************************************/
/* PERSON DIMENSION TABLE */
/*************************************************************************/

/* select person details from ODS.person table */
Data person (drop=person_parent_uid cd age_reported);
/*  Format /* multiple_birth_ind $1.
         deceased_ind_cd $1.
         ssn $25.
         age_calc_unit_cd $20.
         nm_suffix $18.
		 marital_status_cd $1.;  */
  Set nbs_ods.person (keep=local_id birth_time curr_sex_cd as_of_date_sex birth_gender_cd multiple_birth_ind
      marital_status_cd marital_status_desc_txt occupation_cd prim_lang_cd prim_lang_desc_txt cd
      deceased_ind_cd deceased_time as_of_date_morbidity mothers_maiden_nm birth_order_nbr education_level_cd
      education_level_desc_txt ethnic_group_ind description add_time electronic_ind 
      last_nm first_nm 
      age_calc age_calc_time age_calc_unit_cd age_category_cd age_reported age_reported_time age_reported_unit_cd
      as_of_date_general as_of_date_admin as_of_date_ethnicity 
      adults_in_house_nbr children_in_house_nbr person_uid person_parent_uid record_status_cd last_chg_time);

	  age=input(age_reported,4.0);
  If person_uid = person_parent_uid then
	MPR_IND = 'Y';
  Else 
    MPR_IND = 'N';

  If cd = 'PRV' then
    PERSON_TYPE_CD = 'PROVIDER';
  Else
    PERSON_TYPE_CD = 'PATIENT';
    
  Rename
    local_id=PERSON_LOCAL_ID
    birth_time=PERSON_DOB
    curr_sex_cd=PERSON_CURR_GENDER
    as_of_date_sex=PERSON_SEX_AS_OF_DT
    birth_gender_cd=PATIENT_BIRTH_GENDER
    multiple_birth_ind=MULTIPLE_BIRTH_IND
    marital_status_cd=PATIENT_MARITAL_STATUS
    marital_status_desc_txt=PATIENT_MARITAL_STATUS_DESC
    occupation_cd=PATIENT_PRIMARY_OCCUPATION
    prim_lang_cd=PATIENT_PRIMARY_LANG
    prim_lang_desc_txt=PATIENT_PRIMARY_LANG_DESC
    /*ssn=PATIENT_SSN*/
    deceased_ind_cd=PATIENT_DECEASED_IND
    deceased_time=PATIENT_DECEASE_DT
    as_of_date_morbidity=PERSON_MORTALITY_AS_OF_DT
    mothers_maiden_nm=PATIENT_MOTHER_MAIDEN_NM
    birth_order_nbr=PATIENT_BIRTH_ORDER
    education_level_cd=HIGHEST_EDU_LVL_RECEIVED
    education_level_desc_txt=HIGHEST_EDU_LVL_RECEIVED_DESC
    ethnic_group_ind=PATIENT_HISPANIC_IND
    description=PATIENT_DESCRIPTION
    add_time=PERSON_ADD_TIME
	electronic_ind=PERSON_ELECTRONIC_IND
    last_nm=PERSON_LAST_NM
    first_nm=PERSON_FIRST_NM
    /*middle_nm=PERSON_MIDDLE_NM
    nm_prefix=PERSON_NM_PREFIX
    nm_suffix=PERSON_NM_SUFFIX*/
    age_calc=AGE_CALC
    age_calc_time=AGE_CALC_TIME
    age_calc_unit_cd=AGE_CALC_UNIT_CD
    age_category_cd=AGE_CATEGORY_CD
    age=AGE
    age_reported_time=AGE_REPORTED_TIME
    age_reported_unit_cd=AGE_REPORT_UNIT_CD
    as_of_date_general=INFO_AS_OF_DT
    as_of_date_admin=PERSON_ADMIN_INFO_AS_OF_DT
    adults_in_house_nbr=NBR_ADULTS_IN_RESIDENCE
    children_in_house_nbr=NBR_CHILDREN_IN_RESIDENCE
    record_status_cd=RECORD_STATUS_CD ;
Run;

/* get degree data from person_name */
Data degree;
  Set nbs_ods.person_name (keep=person_uid nm_degree nm_use_cd person_name_seq );
  Where nm_use_cd = 'L';
Run;

/* sort to identify multiple records */
Proc Sort Data=degree;
  By person_uid person_name_seq;
Run;

/* select only the highest sequence number */
Data degree2;
  Set degree;
  By person_uid person_name_seq;
  If last.person_uid then output;
Run;


/* merge degree data in with person data */
/* use LEFT JOIN to preserve all person records without a match */
Proc SQL;
 Create Table rdbdata.person1 as
  Select a.*, b.nm_degree AS PERSON_DEGREE
   From person a LEFT JOIN degree2 b
    On a.person_uid = b.person_uid;
Quit;

%assign_key (ds=rdbdata.person1, key=person_key);

/*************************************************************************/
/* OUTRIGGER AND BRIDGE TABLES */
/*************************************************************************/

/************************/
/*  NAME / PERSON_NAME  */
/************************/

/*** NAME OUTRIGGER TABLE ***/

/* select name details from ODS.Person_Name */
/* nm_use_cd used for comparison to build bridge table - need to drop later */
Data ODS_person_name;
  Set nbs_ods.person_name (keep=first_nm middle_nm last_nm nm_prefix nm_suffix nm_use_cd);
    patient_nm_type = put(nm_use_cd,$DEM100f.);    /* code_set_nm = 'P_NM_USE' */
  Rename first_nm=person_first_nm 
         middle_nm=patient_middle_nm 
         last_nm=person_last_nm
         nm_prefix=patient_nm_prefix
         nm_suffix=patient_nm_suffix;
Run;

/* remove duplicate records */
Proc Sort nodupkey Data=ODS_person_name out=rdbdata.name;
  By person_first_nm patient_middle_nm person_last_nm patient_nm_prefix patient_nm_suffix patient_nm_type;
Run;

%assign_key (ds=rdbdata.name, key=name_key);

/*** PERSON_NAME BRIDGE TABLE ***/

/* join person to ODS.person_name to get multiple person/name records */
/* use LEFT JOIN to ensure every person record is tied to a name record */
Proc SQL;
 Create Table personnames as
  Select a.person_key,
         b.first_nm, b.middle_nm, b.last_nm, b.nm_prefix, b.nm_suffix, b.nm_use_cd, b.as_of_date as person_nm_as_of_dt, b.record_status_cd
   From rdbdata.person1 a LEFT JOIN nbs_ods.person_name b
     On a.person_uid = b.person_uid;
Quit;

/* join with name to create person_name bridge table */
/* use LEFT JOIN to ensure every name record is tied to a person record */
/* use DISTINCT to prevent duplicate name entries for same person */
Proc SQL;
 Create Table rdbdata.person_name as
  Select DISTINCT a.name_key, b.person_key, b.person_nm_as_of_dt, b.record_status_cd
   From rdbdata.name a LEFT JOIN personnames b
     On a.person_first_nm = b.first_nm
     and a.patient_middle_nm = b.middle_nm
     and a.person_last_nm = b.last_nm
     and a.patient_nm_prefix = b.nm_prefix
     and a.patient_nm_suffix = b.nm_suffix
     and a.nm_use_cd = b.nm_use_cd;
Quit;

%assign_key (ds=rdbdata.person_name, key=person_name_key);

/* assign key values for records without a match */
Data rdbdata.person_name;
  Set rdbdata.person_name;
  If person_key = . then person_key = 1;
  If name_key = . then name_key = 1;
  If record_status_cd = '' then record_status_cd = 'ACTIVE';
Run;

/*** cleanup ***/

/* drop nm_use_cd from name */
Data rdbdata.name;
 Set rdbdata.name (drop=nm_use_cd);
Run;


/************************/
/*   RACE /PERSON_RACE  */
/************************/

/*** RACE OUTRIGGER TABLE ***/

/* select race codes from SRT.Race_Code */
Data SRT_race_code;
  Set nbs_srt.race_code (keep=code code_short_desc_txt parent_is_cd);
  Rename code=race_cd 
         code_short_desc_txt=race_desc;
  Format race_cd_eff_dt race_cd_end_dt datetime20.;
     race_cd_eff_dt = today()*24*60*60;
     race_cd_end_dt = mdy(1,1,3000)*24*60*60;
Run;

/* optional step - no duplicates exist */
Proc Sort nodupkey Data=SRT_race_code out=unique_race;
  By race_cd race_desc parent_is_cd;
Run;

/* get race category from SRT.race_code using parent_is_cd */
/* use LEFT JOIN to preserve all race records without a match */
Proc SQL;
 Create Table RDBDATA.race as
  Select a.race_cd, a.race_desc,a.parent_is_cd, a.race_cd_eff_dt, a.race_cd_end_dt, b.code_short_desc_txt as race_cat_cd
   From unique_race a LEFT JOIN nbs_srt.race_code b
     On a.parent_is_cd = b.code;
Quit;

%assign_key (ds=rdbdata.race, key=race_key);

/*** PERSON_RACE BRIDGE TABLE ***/

/* join person to ODS.person_race to get multiple person/race records */
/* use LEFT JOIN to ensure every person record is tied to a race record */
Proc SQL;
 Create Table personraces as
  Select a.person_key, b.race_cd, b.as_of_date, b.RACE_CATEGORY_CD, b.record_status_cd
   From rdbdata.person1 a LEFT JOIN nbs_ods.person_race b
     On a.person_uid = b.person_uid;
Quit;

/* join with race to create person_race bridge table */
/* use Inner JOIN to ensure no extra record created in person_race */
/* use DISTINCT to prevent duplicate race entries for same person */
/*Retrieve  the NBS RACE patients*/
Proc SQL;
 Create Table rdbdata.person_race as
  Select DISTINCT a.race_key, b.person_key, b.as_of_date as person_race_as_of_dt, b.record_status_cd
   From rdbdata.race a Inner JOIN personraces b
     On a.race_cd = b.race_cd
     Order by race_key;
Quit;

/***Retrieve the NETSS RACE patients ****/
PROC SQL;
	CREATE TABLE netss_race as 
	Select DISTINCT a.race_key, b.person_key, b.as_of_date as person_race_as_of_dt, b.record_status_cd
   From rdbdata.race a Inner JOIN personraces b
     On a.race_cd = b.RACE_CATEGORY_CD
	 and b.RACE_CD = 'ROOT'
     Order by race_key;

/***Combine the NBS RACE patients with NETSS RACE patients***/
data rdbdata.person_race;
set rdbdata.person_race netss_race;
where race_key ~=1 and person_key~=1; /*remove occurences of 1*/
run;

%assign_key (ds=rdbdata.person_race, key=person_race_key);

/* assign key values for records without a match */
Data rdbdata.person_race;
  Set rdbdata.person_race;
  If person_key = . then person_key = 1;
  If race_key = . then race_key = 1;
  If record_status_cd = '' then record_status_cd = 'ACTIVE';
Run;


/***************************************/
/* DETAIL_ETHNICITY / PERSON_ETHNICITY */
/***************************************/

/*** DETAIL_ETHNICITY OUTRIGGER TABLE ***/

/* select ethnicity codes from SRT.Code_Value_General */
Data rdbdata.detail_ethnicity (drop=code_set_nm);
  Set nbs_srt.code_value_general (Where=(code_set_nm='P_ETHN') keep=code code_short_desc_txt code_set_nm);
  Rename code=person_ethnicity_cd
         code_short_desc_txt=person_ethnicity_desc;
Run;

/* optional step - no duplicates exist */
Proc Sort nodupkey Data=rdbdata.detail_ethnicity;
 By person_ethnicity_cd person_ethnicity_desc;
Run;

%assign_key (ds=rdbdata.detail_ethnicity, key=detail_ethnicity_key);

/*** PERSON_ETHNICITY BRIDGE TABLE ***/

/* join person to ODS.person_ethnic_group to get multiple person/ethnicity records */
/* use LEFT JOIN to ensure every person record is tied to a ethnicity record */
Proc SQL;
 Create Table personethnics as
  Select a.person_key, b.ethnic_group_cd, a.as_of_date_ethnicity as person_ethnicity_as_of_dt, b.record_status_cd
   From rdbdata.person1 a LEFT JOIN nbs_ods.person_ethnic_group b
     On a.person_uid = b.person_uid;
Quit;

/* join with detail_ethnicity to create person_ethnicity bridge table */
/* use Inner JOIN to ensure that if a ethnicity is not used, it needs not to be in person_ethnicity */
/* use DISTINCT to prevent duplicate ethnicity entries for same person */
Proc SQL;
 Create Table rdbdata.person_ethnicity as
  Select DISTINCT a.detail_ethnicity_key, b.person_key, b.person_ethnicity_as_of_dt, b.record_status_cd
   From rdbdata.detail_ethnicity a Inner JOIN personethnics b
     On a.person_ethnicity_cd = b.ethnic_group_cd;
Quit;

%assign_key (ds=rdbdata.person_ethnicity, key=person_ethnicity_key);

/* assign key values for records without a match */
Data rdbdata.person_ethnicity;
  Set rdbdata.person_ethnicity;
  If person_key = . then person_key = 1;
  If detail_ethnicity_key = . then detail_ethnicity_key = 1;
  If record_status_cd = '' then record_status_cd = 'ACTIVE';
Run;


/**************************************************/
/* PERSON_RELATIONSHIP */
/**************************************************/

/*** PERSON_RELATIONSHIP OUTRIGGER TABLE ***/

/* join person to ODS.Role to get multiple person/relationship records */
/* use LEFT JOIN to ensure every person record is tied to a person_role_code record */
Proc SQL;
 Create Table personrelationships1 as
  Select p.person_key, 
         r.scoping_entity_uid,   /* needed to lookup scoping_person_key */
         r.cd as subject_role_cd,
		 r.subject_class_cd,
         r.scoping_role_cd, 
		 r.scoping_class_cd,
		 r.record_status_cd
   From rdbdata.person1 p LEFT JOIN nbs_ods.role r
     On p.person_uid = r.subject_entity_uid;
Quit;

/* join to person table to get scoping_person_key */
/* use LEFT JOIN to preserve all person records without a match */
Proc SQL;
 Create Table personrelationships as
  Select pr.person_key, 
         pr.subject_role_cd,
		 pr.subject_class_cd,
         pr.scoping_role_cd, 
		 pr.scoping_class_cd,
		 p.person_key as scoping_person_key,
		 pr.record_status_cd
   From personrelationships1 pr LEFT JOIN rdbdata.person1 p
     On pr.scoping_entity_uid=p.person_uid;
Quit;


/* get role descriptions from formats (need to create new format for RL_CLASS) */
Data rdbdata.person_relationship;
  Set personrelationships;
  subject_role_desc = put(subject_role_cd,$RL_TYPE.);   /* code_set_nm = 'RL_TYPE' */
  scoping_role_desc = put(scoping_role_cd,$RL_TYPE.);   /* code_set_nm = 'RL_TYPE' */
  subject_class_desc = put(subject_class_cd,$RLCLASS.);   /* code_set_nm = 'RL_CLASS' */
  scoping_class_desc = put(scoping_class_cd,$RLCLASS.);   /* code_set_nm = 'RL_CLASS' */
  Format relationship_info_eff_dt relationship_info_end_dt datetime20.;
    relationship_info_eff_dt = today()*24*60*60;
    relationship_info_end_dt = mdy(1,1,3000)*24*60*60;
Run;



%assign_key (ds=rdbdata.person_relationship, key=PERSON_RELATIONSHIP_SEQ_KEY);

/* assign person_key for records without a match */
Data rdbdata.person_relationship;
  Set rdbdata.person_relationship;
  If person_key = . then person_key = 1;
  If record_status_cd = '' then record_status_cd = 'ACTIVE';
Run;


/***********************************/
/***  PERSON_ID OUTRIGGER TABLE  ***/
/***********************************/

/* join person to entity_id to get multiple person/id records */
/* use LEFT JOIN to preserve all person records without a match */
Proc SQL;
 Create Table rdbdata.person_id as
  Select a.person_key, 
         b.root_extension_txt as person_id_val,
         b.type_cd as patient_id_type,
		 put(b.type_cd,$EI_TYPE.) as person_id_type_desc,   /* code_set_nm = EI_TYPE */
         b.assigning_authority_cd as person_id_assign_auth_cd,
         b.assigning_authority_desc_txt as person_id_assign_auth_desc,
         b.valid_to_time as person_id_expire_dt,
         b.as_of_date as person_id_as_of_dt,
		 b.record_status_cd
   From rdbdata.person1 a LEFT JOIN nbs_ods.entity_id b
     On a.person_uid = b.entity_uid;
Quit;

%assign_key (ds=rdbdata.person_id, key=person_id_key);

/* assign person_key for records without a match */
Data rdbdata.person_id;
  Set rdbdata.person_id;
  If person_key = . then person_key = 1;
  If record_status_cd = '' then record_status_cd = 'ACTIVE';
Run;


/**********************************/
/* cleanup */

/* drop unused columns from person */
Data rdbdata.person1;
 Set rdbdata.person1 (drop=as_of_date_ethnicity);
Run;


proc sql;
create table merge_person as 
select pn.person_key, n.patient_middle_nm as person_middle_nm, 
n.patient_nm_prefix as person_nm_prefix,
n.patient_nm_suffix as person_nm_suffix
from rdbdata.person_name pn, rdbdata.name n 
where pn.name_key = n.name_key ;
run;

proc sort data = merge_person nodupkey;
by person_key;
run;

data rdbdata.person1;
merge merge_person rdbdata.person1;
by person_key;
run;

Data rdbdata.person1;
  Set rdbdata.person1;
  If record_status_cd = '' then record_status_cd = 'ACTIVE';
  
  /* Translate different record_status_cd values to a common value set of {ACTIVE,INACTIVE} */
  if record_status_cd = 'SUPERCEDED' then record_status_cd = 'INACTIVE' ;
  if record_status_cd = 'LOG_DEL' then record_status_cd = 'INACTIVE' ;
  rename 
	AGE=AGE_REPORTED
  ;
Run;

/*Get the SSN from NBS_ODS.Entity_Id table as opposed...currently the application is not 
saving the snn in NBS_ODS.Person.snn
*/
proc sql;
create table rdbdata.person as
select p.*, e.root_extension_txt as patient_ssn 
from rdbdata.person1 as p 
left join nbs_ods.entity_id as e 
on p.person_uid = e.entity_uid
and e.type_cd = 'SS'
and e.assigning_authority_cd = 'SSA';
quit;


/**Clean up **/
PROC datasets library = work nolist;
delete 
	person
	degree
	degree2
	ODS_person_name
	personnames
	SRT_race_code
	personraces
	personethnics
	personrelationships1
	personrelationships
	merge_person
	Unique_race
	netss_race
;
run;
quit;
