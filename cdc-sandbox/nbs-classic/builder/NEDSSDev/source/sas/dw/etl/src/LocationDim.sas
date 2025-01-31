/*************************************************/
/*   LOCATION / PERSON_LOCATION / ORG_LOCATION   */
/*************************************************/
/* (to be run after Person and Organization Dimension tables are created) */

/************************************/
/***   LOCATION DIMENSION TABLE   ***/
/************************************/ 
proc sql;
create table rdbdata.geocoding_location as
select * from  nbs_ods.geocoding_result where record_status_cd = 'ACTIVE';
quit;
%assign_key (ds=rdbdata.geocoding_location, key=geocoding_location_key);

/* select location details from ODS.postal_locator, ODS.entity_locator_participation */
Proc SQL; 
  Create Table entitylocation as 
   Select elp.entity_uid,       /* needed to join to person table */
          pl.cntry_cd AS NATION_CD,
	      pl.state_cd AS STATE_FIPS,
	      pl.cnty_cd AS CNTY_FIPS, 
          pl.city_cd AS CITY_FIPS,
          pl.city_desc_txt AS CITY_SHORT_DESC,
	      pl.zip_cd AS ZIP_CD_5, 
	      pl.street_addr1 AS STREET_ADDR_1,
	      pl.street_addr2 AS STREET_ADDR_2,
		  pl.within_city_limits_ind as WITHIN_CITY_LIMIT_IND 'WITHIN_CITY_LIMIT_IND',
    	  /* the following fields are needed for the bridge tables */
          elp.cd AS ADDR_TYPE,
	      elp.cd_desc_txt AS ADDRESS_TYPE_DESC, 
	      elp.use_cd AS ADDRESS_USE,
		  TRANSLATE(elp.locator_desc_txt,' ' ,'0D0A'x) AS ADDRESS_COMMENTS,
		  elp.as_of_date,
		  elp.record_status_cd, 
		  geocoding.geocoding_location_key,
		  geocoding.entity_class_cd
		  /* Ajith 05/04/05 NBS 1.1.4
		 						   Since we collapse elp with postal_locator each having its own record
		 						   status, elp.record_status_cd is used here as a overriding status
		 						   indicator over telelocator.record_status_cd. Since ELP represents relationship
		 						   between an entity and its location, preserving the ELP record status code
		 						   is more important than indicating whether a piece of location information is
		 						   ACTIVE/INACTIVE by it self. It is important to note that due to the merge,
		 						   telelocator.record_status_cd is lost. */	

From nbs_ods.POSTAL_LOCATOR As pl
inner join  nbs_ods.ENTITY_LOCATOR_PARTICIPATION elp
	on  pl.postal_locator_uid = elp.locator_uid
left outer join rdbdata.geocoding_location geocoding
	on pl.postal_locator_uid = geocoding.postal_locator_uid;
Quit;
/* select unique occurrences of location detail (without entity_uid) */
/* replace with Proc Sort nodupkey */
Proc SQL;
  Create Table location1 as 
   Select DISTINCT NATION_CD,
	      STATE_FIPS,
	      CNTY_FIPS, 
          CITY_FIPS,
          CITY_SHORT_DESC,
	      ZIP_CD_5, 
	      STREET_ADDR_1,
	      STREET_ADDR_2,
		  WITHIN_CITY_LIMIT_IND
    From entitylocation
    Where NATION_CD IS NOT NULL
	   Or STATE_FIPS IS NOT NULL 
	   Or CNTY_FIPS IS NOT NULL   
       Or CITY_FIPS IS NOT NULL 
       Or CITY_SHORT_DESC IS NOT NULL 
	   Or ZIP_CD_5 IS NOT NULL
	   Or STREET_ADDR_1 IS NOT NULL 
	   Or STREET_ADDR_2 IS NOT NULL
		Or WITHIN_CITY_LIMIT_IND is NOT NULL;
Quit;

/* lookup names - replace with formats */
Proc SQL;
  /* lookup nation name from SRT.country_code */
  /* use LEFT JOIN to preserve all location records without a match */
  Create Table location2 as 
   Select a.*, 
          b.code_desc_txt AS NATION_NM
    From location1 a LEFT JOIN nbs_srt.COUNTRY_CODE b
      On a.nation_CD = b.CODE;

  /* lookup state name from SRT.state_code */
  /* use LEFT JOIN to preserve all location records without a match */
  Create Table location3 as 
   Select a.*, 
          b.code_desc_txt AS STATE_SHORT_DESC  
    From location2 a LEFT JOIN nbs_srt.STATE_CODE b
      On a.STATE_FIPS = b.STATE_CD;

  /* lookup county name from SRT.state_county_code_value */
  /* use LEFT JOIN to preserve all location records without a match */
  Create Table location4 as 
   Select a.*, 
          b.code_desc_txt AS CNTY_SHORT_DESC
    From location3 a LEFT JOIN nbs_srt.STATE_COUNTY_CODE_VALUE b
      On a.CNTY_FIPS = b.CODE;

	/* lookup WITHIN_CITY_LIMITS from SRT.YNU */
  /* use LEFT JOIN to preserve all location records without a match */
  Create Table location5 as 
   Select a.*, 
          b.code_desc_txt AS WITHIN_CITY_LIMITS
    From location4 a LEFT JOIN nbs_srt.CODE_VALUE_GENERAL b
      On a.WITHIN_CITY_LIMIT_IND= b.CODE
		and b.code_set_nm='YNU';

  /* lookup zip description from SRT.zip_code_value */
  /* use LEFT JOIN to preserve all location records without a match */
  Create Table rdbdata.location as 
   Select a.*, 
          b.code_desc_txt AS ZIP_SHORT_DESC
    From location5 a LEFT JOIN nbs_srt.ZIP_CODE_VALUE b
      On a.ZIP_CD_5 = b.CODE;
Quit; 

%assign_key (ds=rdbdata.location, key=location_key);



/****************************************/
/***   PERSON_LOCATION BRIDGE TABLE   ***/
/****************************************/
/* (person table must exist) */

/* join person with entitylocation get multiple person/location records */
/* use LEFT JOIN to ensure every person record is tied to a location record */
Proc SQL;
 Create Table personlocations as
  Select a.person_key, b.* 
   From rdbdata.person a LEFT JOIN entitylocation b
     On a.person_uid = b.entity_uid;
Quit;

/* join with location to create person_location bridge table */
/* use LEFT JOIN to ensure every location record is tied to a person record */
/* use DISTINCT to prevent duplicate location entries for same person */
Proc SQL;
 Create Table rdbdata.person_location as
  Select DISTINCT 
			a.location_key, 
			b.person_key as PERSON_KEY,
         	b.ADDR_TYPE, 
			b.ADDRESS_TYPE_DESC, 
			b.ADDRESS_USE, 
			b.ADDRESS_COMMENTS, 
         	b.as_of_date as Person_Location_eff_dt,
			b.record_status_cd,
			b.geocoding_location_key,
			b.entity_class_cd,
			b.WITHIN_CITY_LIMIT_IND
   From rdbdata.location a LEFT JOIN personlocations b
	     On a.NATION_CD = b.NATION_CD
		and a.STATE_FIPS = b.STATE_FIPS
		and a.CNTY_FIPS = b.CNTY_FIPS
	    and a.CITY_FIPS = b.CITY_FIPS
	    and a.CITY_SHORT_DESC = b.CITY_SHORT_DESC
		and a.ZIP_CD_5 = b.ZIP_CD_5 
		and a.STREET_ADDR_1 = b.STREET_ADDR_1
		and a.STREET_ADDR_2 = b.STREET_ADDR_2
		and a.WITHIN_CITY_LIMIT_IND = b.WITHIN_CITY_LIMIT_IND
		AND ((b.entity_class_cd IS NULL) OR (b.entity_class_cd='PSN'));
Quit;

%assign_key (ds=rdbdata.person_location, key=person_loc_key);

/* assign key values for records without a match */
Data rdbdata.person_location;
  Set rdbdata.person_location;

  If person_key = . then person_key = 1;
  If location_key = . then location_key = 1;
  If geocoding_location_key =. then geocoding_location_key=1;	
  If ADDRESS_USE = 'DTH' then
    Decease_Location_Ind = 'Y';
  Else Decease_Location_Ind = 'N';
  If ADDRESS_USE = 'BIR' then
    Birth_Location_Ind = 'Y';
  Else Birth_Location_Ind = 'N';
  If record_status_cd = '' then record_status_cd = 'ACTIVE' ;
Run;


/*************************************/
/***   ORG_LOCATION BRIDGE TABLE   ***/
/*************************************/
/* (organization table must exist) */

/* join organization with entitylocation get multiple org/location records */
/* use LEFT JOIN to ensure every organization record is tied to a location record */
Proc SQL;
 Create Table orglocations as
  Select a.org_key, b.*
   From rdbdata.organization a LEFT JOIN entitylocation b
     On a.org_uid = b.entity_uid;
Quit;

/* join with location to create org_location bridge table */
/* use LEFT JOIN to ensure every location record is tied to a organization record */
/* use DISTINCT to prevent duplicate location entries for same organization */
Proc SQL;
 Create Table rdbdata.org_location as
  Select DISTINCT 
			a.location_key, 
			b.org_key, 
         	b.ADDR_TYPE, 
			b.ADDRESS_TYPE_DESC, 
			b.ADDRESS_USE, 
			b.ADDRESS_COMMENTS,
         	b.as_of_date as Org_Location_eff_dt,
			b.record_status_cd,
			b.geocoding_location_key
   From rdbdata.location a LEFT JOIN orglocations b
	     On a.NATION_CD = b.NATION_CD
		and a.STATE_FIPS = b.STATE_FIPS
		and a.CNTY_FIPS = b.CNTY_FIPS
	    and a.CITY_FIPS = b.CITY_FIPS
	    and a.CITY_SHORT_DESC = b.CITY_SHORT_DESC
		and a.ZIP_CD_5 = b.ZIP_CD_5 
		and a.STREET_ADDR_1 = b.STREET_ADDR_1
		and a.STREET_ADDR_2 = b.STREET_ADDR_2
		AND ((b.entity_class_cd IS NULL) OR (b.entity_class_cd='ORG'));
Quit;

%assign_key (ds=rdbdata.org_location, key=org_location_key);

/* assign key values for records without a match */
Data rdbdata.org_location;
  Set rdbdata.org_location;
  If org_key = . then org_key = 1;
  If location_key = . then location_key = 1;
  If geocoding_location_key =. then geocoding_location_key=1;	
  If record_status_cd = '' then record_status_cd = 'ACTIVE' ;
Run;

PROC datasets library = work nolist;
delete 
	entitylocation
	location1
	location2
	personlocations
	/*orglocations*/
	Location3
	Location4
;
run;
quit;
