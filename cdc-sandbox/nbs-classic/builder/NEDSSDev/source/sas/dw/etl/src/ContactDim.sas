/*************************************************/
/*   CONTACT_INFO / PERSON_CONTACT / ORG_CONTACT */
/*************************************************/
/* (to be run after Person and Organization Dimension tables are created) */

/****************************************/
/***   CONTACT_INFO DIMENSION TABLE   ***/
/****************************************/

/* select contact details from ODS.entity_locator_participation and ODS.tele_locator */
Proc SQL;
 Create Table contactinfo as 
  Select 
         tl.PHONE_NBR_TXT as phone_nbr, 
         elp.cd as phone_type, 
		 put(elp.cd,$EL_TYPE.) as phone_type_desc,   /* code_set_nm = 'EL_TYPE' */
		 elp.USE_CD as phone_use, 
		 TRANSLATE(elp.locator_desc_txt,' ' ,'0D0A'x) AS phone_comments,
         TL.EXTENSION_TXT as phone_ext, 
         tl.CNTRY_CD, 
         tl.URL_ADDRESS as url, 
         tl.EMAIL_ADDRESS, 
		 elp.entity_uid,    	/* needed to join to person and organization tables - will be dropped later */
         elp.as_of_date,     	/* needed for person_contact bridge table - will be dropped later */
		 elp.record_status_cd 	/* Ajith 05/04/05 NBS 1.1.4
		 						   Since we collapse elp with telelocator each having its own record
		 						   status, elp.record_status_cd is used here as a overriding status
		 						   indicator over telelocator.record_status_cd. Since ELP represents relationship
		 						   between an entity and its contact, preserving the ELP record status code
		 						   is more important than indicating whether a piece of contact information is
		 						   ACTIVE/INACTIVE by it self. It is important to note that due to the merge,
		 						   telelocator.record_status_cd is lost. */	
   From 
        nbs_ods.entity_locator_participation elp, 
        nbs_ods.tele_locator tl
   Where 
        tl.tele_locator_uid = elp.locator_uid
        and elp.class_cd = 'TELE';
Quit;


/* join person to contactinfo to select only person-related contact records */
Proc SQL;
 Create Table person_contact_info as 
  Select b.phone_nbr, 
         b.phone_type, 
         b.phone_type_desc,
		 b.phone_use, 
		 b.phone_comments, 
		 b.phone_ext, 
		 b.CNTRY_CD, 
		 b.url, 
		 b.EMAIL_ADDRESS, 
         b.as_of_date, 
         b.entity_uid,
		 b.record_status_cd
   From     
         rdbdata.person a, 
         contactinfo b
   Where 
         a.person_uid = b.entity_uid;
Quit;


/* join organization to contactinfo to select only person-related contact records */
Proc SQL;
 Create Table organization_contact_info as
  Select b.phone_nbr, b.phone_type, b.phone_type_desc,
		 b.phone_use, b.phone_comments, b.phone_ext, b.CNTRY_CD, b.url, b.EMAIL_ADDRESS, 
         b.as_of_date, b.entity_uid, b.record_status_cd
   From rdbdata.organization a, contactinfo b
     Where a.org_uid = b.entity_uid;
Quit;


/* combine person and organization contact info */
Data rdbdata.contact_info;
 Set person_contact_info organization_contact_info;
Run;

%assign_key (ds=rdbdata.contact_info, key=contact_key);


/*************************************/
/***  PERSON_CONTACT BRIDGE TABLE  ***/
/*************************************/

/* join person with contact_info to get multiple person/contact records */
/* use LEFT JOIN to ensure every person record is tied to a contact_info record */
/* use DISTINCT to prevent duplicate contact entries for same person */
Proc SQL;
 Create Table rdbdata.person_contact as 
  Select DISTINCT b.contact_key, a.person_key, a.record_status_cd,
         b.as_of_date as person_contact_info_as_of_dt
   From rdbdata.person a LEFT JOIN rdbdata.contact_info b
     On a.person_uid = b.entity_uid;
Quit;

%assign_key (ds=rdbdata.person_contact, key=person_con_key);

/* assign key values for records without a match */
Data rdbdata.person_contact;
  Set rdbdata.person_contact;
  If contact_key = . then contact_key = 1;
  If person_key = . then person_key = 1;
  If record_status_cd = '' then record_status_cd = 'ACTIVE' ;
Run;


/*******************************************/
/***  ORGANIZATION_CONTACT BRIDGE TABLE  ***/
/*******************************************/

/* join organization with contact_info to get multiple org/contact records */
/* use LEFT JOIN to ensure every organization record is tied to a contact_info record */
/* use DISTINCT to prevent duplicate contact entries for same organization */
Proc SQL;
 Create Table rdbdata.org_contact as 
  Select DISTINCT b.contact_key, a.org_key, a.record_status_cd
   From rdbdata.organization a LEFT JOIN rdbdata.contact_info b
     On a.org_uid = b.entity_uid;
Quit;

/* assign key values for records without a match */
Data rdbdata.org_contact;
  Set rdbdata.org_contact;
  If contact_key = . then contact_key = 1;
  If org_key = . then org_key = 1;
  If record_status_cd = '' then record_status_cd = 'ACTIVE' ;
Run;


/********************************/
/* cleanup */
/********************************/

/* drop entity_uid and as_of_date from contact_info */
Data rdbdata.contact_info;
 Set rdbdata.contact_info (drop=entity_uid as_of_date);
 If record_status_cd = '' then record_status_cd = 'ACTIVE' ;
Run;

PROC datasets library = work nolist;
delete contactinfo;
delete person_contact_info;
delete organization_contact_info;
run;
quit;
