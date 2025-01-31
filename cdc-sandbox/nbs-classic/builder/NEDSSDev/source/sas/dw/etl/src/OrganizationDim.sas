/****************************************/
/*   ORGANIZATION / ORG_ROLE / ORG_ID   */
/****************************************/

/***********************************/
/***   ORGANIZATION Fact Table   ***/
/***********************************/

/* get organization details from ODS.Organization */
/* Left Join with ODS.Organization_Name to add name details */
Proc Sql ;
  create table rdbdata.organization1 as 
  select 
    org.local_id as org_local_id,
	org.status_cd as org_status,
	org.status_time as org_status_eff_dt,
	org.standard_industry_class_cd as org_std_industry_cls,
	org.standard_industry_desc_txt as org_industry_std_desc,
	orgnm.nm_txt as org_nm, 
	orgnm.nm_use_cd as org_nm_type, 
	org.organization_uid as org_uid, 
	org.add_time as org_add_time,
	org.add_user_id as org_add_user_id,
	TRANSLATE(org.description,' ' ,'0D0A'x) as org_comments,
	org.electronic_ind as org_electronic_ind,
	org.record_status_cd as record_status_cd
  from nbs_ods.organization org LEFT JOIN nbs_ods.organization_name orgnm
    on org.organization_uid = orgnm.organization_uid;
Quit;

PROC SORT DATA = rdbdata.organization1;
BY org_uid;

/*Make a data set composed of all orgs that have a stand ind code value assigned but 
does not have a value for description text*/
DATA Industry_Code_Stage (Keep = ORG_UID ORG_STD_INDUSTRY_CLS org_industry_std_desc);
Set rdbdata.organization1;
RUN;



/*org.standard_industry_desc_txt is null but it shouldn't be...so get the value from the srt table*/
PROC Sql;
	create table Org_Industry_Desc as
	select i.*, n.CODE_SHORT_DESC_TXT as desc
	from nbs_srt.naics_industry_code n, Industry_Code_Stage i 
	where i.org_std_industry_cls = n.CODE;
	run;
DATA Org_Industry_Desc;
SET Org_Industry_Desc;
IF  org_industry_std_desc = '' then  org_industry_std_desc = desc;
run;



DATA ORG_PRE_MERGE (DROP = ORG_STD_INDUSTRY_CLS desc);
SET Org_Industry_Desc;
RUN;
/*Sort the Data before merge*/ 
PROC SORT DATA = Org_Pre_Merge;
BY ORG_UID;



DATA rdbdata.organization1 (DROP =org_industry_std_desc) ;
SET rdbdata.organization1;
/*Merge industry descriptions into rdbdata.organization*/ 
DATA rdbdata.organization1;
MERGE Org_Pre_Merge rdbdata.organization1;
BY ORG_UID;
RUN;


/* Left join with ODS.tele_locator to add URL details */
Proc Sql ;
  create table rdbdata.organization2 as 
  select org.* , tel.url_address as org_url
  from rdbdata.organization1 org LEFT JOIN nbs_ods.tele_locator tel
    on org.org_uid = tel.tele_locator_uid;
Quit;


/* Select CLIA records from ODS.entity_id */
Data entity_id_clia;
  Set nbs_ods.entity_id (where=(type_cd='FI' and assigning_authority_cd = 'CLIA'));
  Keep entity_uid root_extension_txt;
Run;


/* Left join to add CLIA details */
Proc Sql ;
  create table rdbdata.organization3 as 
  select org.*, eic.root_extension_txt as facility_clia_nbr 
  from rdbdata.organization2 org LEFT JOIN entity_id_clia eic
    on org.org_uid = eic.entity_uid;
Quit;


/* Select Quick Code records from ODS.entity_id */
Data entity_id_qec;
  Set nbs_ods.entity_id (where=(type_cd = 'QEC'));
  Keep entity_uid root_extension_txt type_desc_txt assigning_authority_cd assigning_authority_desc_txt;
Run;




/* Left join to add Quick Code details */
Proc Sql ;
  create table rdbdata.organization as 
  select org.*, 
    	eiq.root_extension_txt as org_quick_cd, 
	    eiq.type_desc_txt as org_quick_cd_desc,
	    eiq.assigning_authority_cd as org_q_cd_assign_auth_cd,
	    eiq.assigning_authority_desc_txt as org_q_cd_assign_auth_desc
  from rdbdata.organization3 org LEFT JOIN entity_id_qec eiq
    on org.org_uid = eiq.entity_uid;
Quit;

%assign_key (ds=rdbdata.organization, key=org_key) ;


/**************************************/
/*** ORG_ROLE_CODE Outrigger Table  ***/
/**************************************/

/* select only organization-role records from ODS.Role */
Data ODSrole;
  Set nbs_ods.role (keep=subject_entity_uid cd subject_class_cd);
  Run;
  /*where subject_CLASS_CD = 'ORG'; commented out because roles for elr are not being shown */


/* join organization to ODSrole to get multiple org/role records */
/* use LEFT JOIN to ensure every organization record is tied to an org_role_code record */
Proc SQL;
 Create Table orgroles as
  Select a.org_key, b.cd as org_role_code, a.record_status_cd
   From rdbdata.organization a LEFT JOIN ODSrole b
   On a.org_uid = b.subject_entity_uid;
Quit;


/* select only unique role code records */
Proc Sort nodupkey Data=orgroles (where=(org_role_code ne "")) Out=rdbdata.org_role_code (drop=org_key);
 By org_role_code;
Run;


/* select role codes from SRT.Code_Value_General */
Data rdbdata.org_role_code;
  Set rdbdata.org_role_code;
   org_role_desc = put(org_role_code,$RL_TYPE.);   /* code_set_nm = 'RL_TYPE' */
  Format org_role_eff_dt org_role_end_dt datetime20.;
   org_role_eff_dt = today()*24*60*60;
   org_role_end_dt = mdy(1,1,3000)*24*60*60;
Run;

%assign_key (ds=rdbdata.org_role_code, key=org_role_key);


/********************************/
/***   ORG_ROLE Bridge Table  ***/
/********************************/

/* join with org_role_code to create org_role bridge table */
/* use LEFT JOIN to ensure every org_role_code record is tied to an organization record */
Proc SQL;
 Create Table rdbdata.org_role as
  Select a.org_role_key, 
         b.org_key, 
         b.record_status_cd 
   From rdbdata.org_role_code a LEFT JOIN orgroles b
     On a.org_role_code = b.org_role_code;
Quit;


/* assign org_key for records without a match */
Data rdbdata.org_role;
  Set rdbdata.org_role;
  If org_role_key = . then org_role_key = 1;
  If record_status_cd = '' then record_status_cd='ACTIVE' ;
Run;

proc sort data = rdbdata.org_role nodupkey;
by org_role_key org_key; quit;


/********************************/
/***  ORG_ID Outrigger Table  ***/
/*********************************/

/* Join ODS.Organization and ODS.Entity ID tables to select only organization codes */

/* join organization to entity_id to get multiple org/id records */
/* use LEFT JOIN to ensure every org record has an org_id record */
Proc SQL;
 Create table rdbdata.org_id as
  Select org.org_key,
         eid.type_cd as org_id_type_cd,
      /* eid.type_desc_txt as org_id_type_desc, */
         put(eid.type_cd,$EI_TYPE.) as org_id_type_desc,   /* code_set_nm = 'EI_TYPE' */
	     eid.root_extension_txt as org_id_val,
	     eid.assigning_authority_cd as org_id_assign_auth_cd,
	     eid.assigning_authority_desc_txt as org_id_assign_auth_desc,
	     eid.record_status_cd as record_status_cd
   From rdbdata.organization org LEFT JOIN nbs_ods.entity_id  eid
     On eid.entity_uid = org.org_uid 
		and type_cd ~= 'QEC'
/*     and type_cd IN ('FI','NE','NH','CLIA','MID','OTH') */
;
Quit;

%assign_key (ds=rdbdata.org_id, key=org_id_key);

/* assign person_key for records without a match */
Data rdbdata.org_id;
  Set rdbdata.org_id;
  If org_key = . then org_key = 1;
  If record_status_cd = '' then record_status_cd='ACTIVE' ;
Run;

Data rdbdata.organization;
  Set rdbdata.organization;
  If record_status_cd = '' then record_status_cd='ACTIVE' ;
Run;

PROC datasets library = work nolist;
delete industry_code_stage;
delete org_industry_desc;
delete org_pre_merge;
delete entity_id_clia;
delete entity_id_qec;
delete odsrole;
delete orgroles;
run;
quit;

