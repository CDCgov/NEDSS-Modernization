-- THE FOLLOWING SQL WILL SHRINK ANY DATABASE TO AN ARBITRARY NUMBER OF PATIENTS

-- The physical size of the current production anonymized masters are too unwieldy (restore time, size, etc.). 
-- This script pares down any database to an arbitrary size with the most performant method. 
-- Since tables are very large any method using deletes would take forever to run. 
-- So we chose to rebuild from the ground up while satisfying any constraints 
-- (that the subset of patients requires) in the most performant manner as possible as follows:

-- 1) analyze all schemas and identify largest schemas/tables and start with biggest and work our way down
-- 2) for each schema remove all database constraints
-- 3) rebuild tables without indexes (while maintaining copies of the old and creating new unique key names to prevent collisions)
-- 4) bulk insert with select intos from old tables (fast since no indexes)
-- 5) re-add/build indexes (with new unique names to avoid collisions)
-- 6) rename tables (switch old and new)
-- 7) remove old tables

-- THESE WERE THE SIZES FOR EACH SCHEMA BASED ON A FULL CDC ANONYMIZED DB:

-- NBS_DataIngest 16M
-- NBS_MSGOUTE 153M
-- NBS_ODSE 1.6T <- EXTREMELY LARGE
-- NBS_SRTE 285M
-- RDB 1.6T <- EXTREMELY LARGE

-- FOLLOWING QUERY LISTS LARGEST TABLE SIZES IN THE SCHEMA:
  select schema_name(tab.schema_id) + '.' + tab.name as [table],
    cast(sum(spc.used_pages * 8)/1024.00 as numeric(36, 2)) as used_mb,
    cast(sum(spc.total_pages * 8)/1024.00 as numeric(36, 2)) as allocated_mb
from sys.tables tab
    inner join sys.indexes ind 
        on tab.object_id = ind.object_id
    inner join sys.partitions part 
        on ind.object_id = part.object_id and ind.index_id = part.index_id
    inner join sys.allocation_units spc
        on part.partition_id = spc.container_id
group by schema_name(tab.schema_id) + '.' + tab.name
order by sum(spc.used_pages) desc

-- LARGEST TABLES IN NBS_ODSE:
-- |table                                  |used_mb  |allocated_mb|
-- |---------------------------------------|---------|------------|
-- |dbo.Observation                        |66,942.38|66,943.86   |
-- |dbo.Participation                      |54,708.93|54,729.25   |
-- |dbo.Entity_id                          |49,674.71|49,676.54   |
-- |dbo.Person                             |46,704.04|46,707.88   |
-- |dbo.Entity_locator_participation       |39,826.88|39,828.16   |
-- |dbo.Act_id                             |33,323.13|33,323.6    |
-- |dbo.Entity_loc_participation_hist      |28,392.43|28,392.55   |
-- |dbo.Act_relationship                   |27,324.23|27,327.36   |
-- |dbo.Person_name                        |23,338.17|25,214.56   |

USE NBS_ODSE;

-- Disable constraints for all tables in the database:
EXEC sp_msforeachtable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL'

CREATE TABLE NBS_ODSE.dbo.Person_shrunken (
	person_uid bigint NOT NULL,
	add_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	add_time datetime NULL,
	add_user_id bigint NULL,
	administrative_gender_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	age_calc smallint NULL,
	age_calc_time datetime NULL,
	age_calc_unit_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	age_category_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	age_reported varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	age_reported_time datetime NULL,
	age_reported_unit_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	birth_gender_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	birth_order_nbr smallint NULL,
	birth_time datetime NULL,
	birth_time_calc datetime NULL,
	cd varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cd_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	curr_sex_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	deceased_ind_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	deceased_time datetime NULL,
	description varchar(2000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	education_level_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	education_level_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ethnic_group_ind varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_chg_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_chg_time datetime NULL,
	last_chg_user_id bigint NULL,
	local_id varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	marital_status_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	marital_status_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	mothers_maiden_nm varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	multiple_birth_ind varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	occupation_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	preferred_gender_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	prim_lang_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	prim_lang_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_time datetime NULL,
	status_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_time datetime NULL,
	survived_ind_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_affiliation_txt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	first_nm varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_nm varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	middle_nm varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	nm_prefix varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	nm_suffix varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	preferred_nm varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	hm_street_addr1 varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	hm_street_addr2 varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	hm_city_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	hm_city_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	hm_state_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	hm_zip_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	hm_cnty_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	hm_cntry_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	hm_phone_nbr varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	hm_phone_cntry_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	hm_email_addr varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cell_phone_nbr varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	wk_street_addr1 varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	wk_street_addr2 varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	wk_city_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	wk_city_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	wk_state_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	wk_zip_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	wk_cnty_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	wk_cntry_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	wk_phone_nbr varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	wk_phone_cntry_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	wk_email_addr varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	SSN varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	medicaid_num varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	dl_num varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	dl_state_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	race_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	race_seq_nbr smallint NULL,
	race_category_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ethnicity_group_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ethnic_group_seq_nbr smallint NULL,
	adults_in_house_nbr smallint NULL,
	children_in_house_nbr smallint NULL,
	birth_city_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	birth_city_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	birth_cntry_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	birth_state_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	race_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ethnic_group_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	version_ctrl_nbr smallint NOT NULL,
	as_of_date_admin datetime NULL,
	as_of_date_ethnicity datetime NULL,
	as_of_date_general datetime NULL,
	as_of_date_morbidity datetime NULL,
	as_of_date_sex datetime NULL,
	electronic_ind char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	person_parent_uid bigint NULL,
	dedup_match_ind char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	group_nbr int NULL,
	group_time datetime NULL,
	edx_ind varchar(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	speaks_english_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	additional_gender_cd varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ehars_id varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ethnic_unk_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	sex_unk_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK_Person_2 PRIMARY KEY (person_uid)
);

-- MANUAL STEP
-- MANIPULATE NUMBERS (IE 10137000) BELOW FOR DESIRED NUMBER OF PATIENTS:

-- SELECT COUNT(*) FROM person
-- WHERE person_parent_uid <= 10137000 and record_status_cd='ACTIVE' and cd='PAT';
-- TOTAL PATIENT RECORDS: 6404

-- SELECT COUNT(*) FROM person
-- WHERE person_parent_uid=person_uid and person_parent_uid <= 10137000 and record_status_cd='ACTIVE' and cd='PAT';
-- UNIQUE PATIENTS: 1067

INSERT INTO Person_shrunken
SELECT * FROM person
WHERE person_parent_uid <= 10137000 and record_status_cd='ACTIVE' and cd='PAT';

 CREATE NONCLUSTERED INDEX IDX_DEDUP_PERSON_2 ON dbo.Person_shrunken (  cd ASC  , electronic_ind ASC  , record_status_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX IDX_PERSON_DEDUP1_2 ON dbo.Person_shrunken (  cd ASC  , record_status_cd ASC  , dedup_match_ind ASC  , group_nbr ASC  )  
	 INCLUDE ( cd_desc_txt , person_parent_uid ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_PERSON_DEDUP2_2 ON dbo.Person_shrunken (  cd ASC  , group_nbr ASC  )  
	 INCLUDE ( dedup_match_ind ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_PERSON_DEDUP3_2 ON dbo.Person_shrunken (  group_nbr ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_PERSON_PARENT_UID_2 ON dbo.Person_shrunken (  person_parent_uid ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX INDEX_PERSON_IND03_2 ON dbo.Person_shrunken (  curr_sex_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX INDEX_PERSON_IND04_2 ON dbo.Person_shrunken (  ethnic_group_ind ASC  , record_status_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX INDEX_PERSON_IND05_2 ON dbo.Person_shrunken (  cd ASC  , record_status_cd ASC  )  
	 INCLUDE ( person_parent_uid ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX INDEX_PERSON_SEX_BIRTH_2 ON dbo.Person_shrunken (  curr_sex_cd ASC  , birth_time ASC  , ethnic_group_ind ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX PERF_02032021_01_2 ON dbo.Person_shrunken (  cd ASC  , local_id ASC  , record_status_cd ASC  )  
	 INCLUDE ( person_parent_uid , person_uid ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX PERF_02052021_05_2 ON dbo.Person_shrunken (  cd ASC  , record_status_cd ASC  )  
	 INCLUDE ( ehars_id , person_parent_uid , person_uid ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX PERF_03282021_01_2 ON dbo.Person_shrunken (  cd ASC  , record_status_cd ASC  , group_nbr ASC  , birth_time ASC  )  
	 INCLUDE ( birth_gender_cd , cd_desc_txt , curr_sex_cd , person_parent_uid ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX PERSON_CDDESCTXT_NIDX_2 ON dbo.Person_shrunken (  cd_desc_txt ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX PERSON_INDX_PERF1_2 ON dbo.Person_shrunken (  person_uid ASC  , person_parent_uid ASC  , cd ASC  , record_status_cd ASC  , last_nm ASC  , first_nm ASC  , birth_time_calc ASC  )  
	 INCLUDE ( age_reported , age_reported_unit_cd , as_of_date_admin , birth_time , curr_sex_cd , deceased_time , ethnic_group_ind , local_id , marital_status_cd , SSN , version_ctrl_nbr ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX PERSON_PERSONPARENTUID_NIDX_2 ON dbo.Person_shrunken (  person_parent_uid ASC  , birth_time ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX RDB_PERF_03242021_01_2 ON dbo.Person_shrunken (  record_status_cd ASC  )  
	 INCLUDE ( last_chg_time ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX RDB_PERF_03242021_06_2 ON dbo.Person_shrunken (  cd ASC  , record_status_cd ASC  , cd_desc_txt ASC  , last_chg_time ASC  )  
	 INCLUDE ( person_parent_uid ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX RDB_PERF_04042021_01_2 ON dbo.Person_shrunken (  last_chg_time ASC  )  
	 INCLUDE ( local_id ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX RDB_PERF_04042021_03_2 ON dbo.Person_shrunken (  local_id ASC  , last_chg_time ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;

CREATE TABLE NBS_ODSE.dbo.Participation_shrunken (
	subject_entity_uid bigint NOT NULL,
	act_uid bigint NOT NULL,
	type_cd varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	act_class_cd varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	add_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	add_time datetime NULL,
	add_user_id bigint NULL,
	awareness_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	awareness_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cd varchar(40) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_amt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_unit_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	from_time datetime NULL,
	last_chg_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_chg_time datetime NULL,
	last_chg_user_id bigint NULL,
	record_status_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_time datetime NULL,
	role_seq bigint NULL,
	status_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_time datetime NULL,
	subject_class_cd varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	to_time datetime NULL,
	type_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_affiliation_txt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	as_of_date datetime NULL,
	CONSTRAINT PK_Participation_2 PRIMARY KEY (subject_entity_uid,act_uid,type_cd)
);

INSERT INTO Participation_shrunken 
SELECT * FROM Participation
WHERE subject_entity_uid in (select person_uid from Person_shrunken)

 CREATE NONCLUSTERED INDEX PARTICIPATION_TYPE_CD_2 ON dbo.Participation_shrunken (  type_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX PERF_02062021_12_2 ON dbo.Participation_shrunken (  type_cd ASC  )  
	 INCLUDE ( act_uid , from_time , subject_entity_uid ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_PARTICIPATION ] ;
 CREATE NONCLUSTERED INDEX Participation_ix01_2 ON dbo.Participation_shrunken (  act_uid ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX participation_classrecord_ix_2 ON dbo.Participation_shrunken (  act_uid ASC  , act_class_cd ASC  , record_status_cd ASC  , type_cd ASC  , subject_class_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_INDEX ] ;

CREATE TABLE NBS_ODSE.dbo.Observation_shrunken (
	observation_uid bigint NOT NULL,
	activity_duration_amt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	activity_duration_unit_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	activity_from_time datetime NULL,
	activity_to_time datetime NULL,
	add_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	add_time datetime NULL,
	add_user_id bigint NULL,
	cd varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cd_desc_txt varchar(1000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cd_system_cd varchar(300) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cd_system_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	confidentiality_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	confidentiality_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ctrl_cd_display_form varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ctrl_cd_user_defined_1 varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ctrl_cd_user_defined_2 varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ctrl_cd_user_defined_3 varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ctrl_cd_user_defined_4 varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	derivation_exp smallint NULL,
	effective_duration_amt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	effective_duration_unit_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	effective_from_time datetime NULL,
	effective_to_time datetime NULL,
	electronic_ind char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	group_level_cd varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	jurisdiction_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	lab_condition_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_chg_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_chg_time datetime NULL,
	last_chg_user_id bigint NULL,
	local_id varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	method_cd varchar(2000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	method_desc_txt varchar(2000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	obs_domain_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	obs_domain_cd_st_1 varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	pnu_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	priority_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	priority_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	prog_area_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_time datetime NULL,
	repeat_nbr smallint NULL,
	status_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_time datetime NULL,
	subject_person_uid bigint NULL,
	target_site_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	target_site_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	txt varchar(1000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_affiliation_txt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	value_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ynu_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	program_jurisdiction_oid bigint NULL,
	shared_ind char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	version_ctrl_nbr smallint NOT NULL,
	alt_cd varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	alt_cd_desc_txt varchar(1000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	alt_cd_system_cd varchar(300) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	alt_cd_system_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cd_derived_ind char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	rpt_to_state_time datetime NULL,
	cd_version varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	processing_decision_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	pregnant_ind_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	pregnant_week smallint NULL,
	processing_decision_txt varchar(1000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK_Observation_2 PRIMARY KEY (observation_uid)
);

INSERT INTO Observation_shrunken 
SELECT * FROM Observation
WHERE observation_uid in (select act_uid from Participation_shrunken)

CREATE NONCLUSTERED INDEX IDX_OBSERVATION_QUEUE_2 ON dbo.Observation_shrunken (  ctrl_cd_display_form ASC  , obs_domain_cd_st_1 ASC  )  
INCLUDE ( jurisdiction_cd , prog_area_cd , rpt_to_state_time ) 
WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
ON [ODS_ACTS ] ;
CREATE NONCLUSTERED INDEX IDX_OBSERVATION_QUEUE1_2 ON dbo.Observation_shrunken (  ctrl_cd_display_form ASC  , record_status_cd ASC  )  
INCLUDE ( cd , jurisdiction_cd , local_id , prog_area_cd , rpt_to_state_time , shared_ind ) 
WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
ON [ODS_ACTS ] ;
CREATE NONCLUSTERED INDEX IDX_OBSERVATION_QUE_COUNT_2 ON dbo.Observation_shrunken (  program_jurisdiction_oid ASC  , ctrl_cd_display_form ASC  , record_status_cd ASC  )  
WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
ON [ODS_ACTS ] ;
CREATE NONCLUSTERED INDEX IDX_Observation_02162021_05_2 ON dbo.Observation_shrunken (  obs_domain_cd_st_1 ASC  , ctrl_cd_display_form ASC  )  
INCLUDE ( jurisdiction_cd , prog_area_cd ) 
WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
ON [ODS_ACTS ] ;
CREATE NONCLUSTERED INDEX OBSERVATION_OBSDOMAINCDST1_NIDX_2 ON dbo.Observation_shrunken (  obs_domain_cd_st_1 ASC  )  
WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
ON [PRIMARY ] ;
CREATE NONCLUSTERED INDEX PERF_02102021_01_2 ON dbo.Observation_shrunken (  ctrl_cd_display_form ASC  , obs_domain_cd_st_1 ASC  , record_status_cd ASC  )  
INCLUDE ( effective_from_time , observation_uid ) 
WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
ON [ODS_ACTS ] ;
CREATE NONCLUSTERED INDEX PERF_08262022_02_2 ON dbo.Observation_shrunken (  ctrl_cd_display_form ASC  , program_jurisdiction_oid ASC  , record_status_cd ASC  , version_ctrl_nbr ASC  , rpt_to_state_time ASC  )  
INCLUDE ( electronic_ind ) 
WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
ON [ODS_ACTS ] ;
CREATE NONCLUSTERED INDEX RDB_PERF_03242021_05_2 ON dbo.Observation_shrunken (  last_chg_time ASC  )  
INCLUDE ( ctrl_cd_display_form , local_id ) 
WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
ON [ODS_ACTS ] ;
CREATE NONCLUSTERED INDEX RDB_PERF_03302021_09_2 ON dbo.Observation_shrunken (  ctrl_cd_display_form ASC  , obs_domain_cd_st_1 ASC  , electronic_ind ASC  , last_chg_time ASC  )  
INCLUDE ( add_time , add_user_id , jurisdiction_cd , last_chg_user_id , local_id , prog_area_cd , program_jurisdiction_oid , record_status_cd , record_status_time , status_cd , status_time ) 
WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
ON [ODS_ACTS ] ;
CREATE NONCLUSTERED INDEX observation_INDX_perf1_2 ON dbo.Observation_shrunken (  ctrl_cd_display_form ASC  , obs_domain_cd_st_1 ASC  , prog_area_cd ASC  , jurisdiction_cd ASC  )  
WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
ON [PRIMARY ] ;

CREATE TABLE NBS_ODSE.dbo.Person_name_shrunken (
	person_uid bigint NOT NULL,
	person_name_seq smallint NOT NULL,
	add_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	add_time datetime NULL,
	add_user_id bigint NULL,
	default_nm_ind char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_amt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_unit_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	first_nm varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	first_nm_sndx varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	from_time datetime NULL,
	last_chg_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_chg_time datetime NULL,
	last_chg_user_id bigint NULL,
	last_nm varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_nm_sndx varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_nm2 varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_nm2_sndx varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	middle_nm varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	middle_nm2 varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	nm_degree varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	nm_prefix varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	nm_suffix varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	nm_use_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_time datetime NULL,
	status_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	status_time datetime NOT NULL,
	to_time datetime NULL,
	user_affiliation_txt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	as_of_date datetime NULL,
	CONSTRAINT PK_Person_name_2 PRIMARY KEY (person_uid,person_name_seq),
);

INSERT INTO Person_name_shrunken 
SELECT * FROM Person_name 
WHERE person_uid in (select person_uid from Person_shrunken)

 CREATE NONCLUSTERED INDEX IDX_PERSON_NM_02112021_01_2 ON dbo.Person_name_shrunken (  nm_use_cd ASC  , record_status_cd ASC  , first_nm ASC  , last_nm ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX IDX_PERSON_NM_DEDUP_2 ON dbo.Person_name_shrunken (  nm_use_cd ASC  , record_status_cd ASC  , last_nm ASC  )  
	 INCLUDE ( as_of_date ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX IDX_PERSON_NM_DEDUP1_2 ON dbo.Person_name_shrunken (  nm_use_cd ASC  , record_status_cd ASC  )  
	 INCLUDE ( first_nm_sndx , last_nm_sndx , last_nm2_sndx , middle_nm ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX IDX_PERSON_NM_DEDUP2_2 ON dbo.Person_name_shrunken (  first_nm_sndx ASC  , last_nm_sndx ASC  , nm_use_cd ASC  , record_status_cd ASC  )  
	 INCLUDE ( last_nm2_sndx , middle_nm ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX INDEX_LAST_CHG_TIME_2 ON dbo.Person_name_shrunken (  last_chg_time ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX INDEX_PERSON_NAME_IND01_2 ON dbo.Person_name_shrunken (  record_status_cd ASC  , last_nm ASC  , first_nm ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX INDEX_PERSON_NAME_IND02_2 ON dbo.Person_name_shrunken (  nm_use_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;

CREATE TABLE NBS_ODSE.dbo.Act_id_shrunken (
	act_uid bigint NOT NULL,
	act_id_seq smallint NOT NULL,
	add_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	add_time datetime NULL,
	add_user_id bigint NULL,
	assigning_authority_cd varchar(199) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	assigning_authority_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_amt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_unit_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_chg_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_chg_time datetime NULL,
	last_chg_user_id bigint NULL,
	record_status_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_time datetime NULL,
	root_extension_txt varchar(199) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_time datetime NULL,
	type_cd varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	type_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_affiliation_txt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	valid_from_time datetime NULL,
	valid_to_time datetime NULL,
	CONSTRAINT PK_Act_id_2 PRIMARY KEY (act_uid,act_id_seq),
);

INSERT INTO Act_id_shrunken 
SELECT * FROM Act_id 
WHERE act_uid in (select observation_uid from Observation_shrunken)

CREATE NONCLUSTERED INDEX IDX_ACT_ID_ELR1_2 ON dbo.Act_id_shrunken (  root_extension_txt ASC  , type_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ACTS ] ;

CREATE TABLE NBS_ODSE.dbo.Act_relationship_shrunken (
	target_act_uid bigint NOT NULL,
	source_act_uid bigint NOT NULL,
	type_cd varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	add_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	add_time datetime NULL,
	add_user_id bigint NULL,
	duration_amt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_unit_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	from_time datetime NULL,
	last_chg_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_chg_time datetime NULL,
	last_chg_user_id bigint NULL,
	record_status_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_time datetime NULL,
	sequence_nbr smallint NULL,
	source_class_cd varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_time datetime NULL,
	target_class_cd varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	to_time datetime NULL,
	type_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_affiliation_txt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK_Act_relationship_2 PRIMARY KEY (source_act_uid,target_act_uid,type_cd)
);

INSERT INTO Act_relationship_shrunken 
SELECT * FROM Act_relationship 
WHERE source_act_uid in (select observation_uid from Observation_shrunken)

 CREATE NONCLUSTERED INDEX IX_Act_relationship_2 ON dbo.Act_relationship_shrunken (  type_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IX_Act_relationship_1_2 ON dbo.Act_relationship_shrunken (  source_class_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX NonClusteredIndex_act_relationship_source_act_uid_2 ON dbo.Act_relationship_shrunken (  source_act_uid ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX RDB_PERF_03242021_04_2 ON dbo.Act_relationship_shrunken (  type_cd ASC  )  
	 INCLUDE ( last_chg_time ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ACTS ] ;
 CREATE NONCLUSTERED INDEX act_relationship_ix01_2 ON dbo.Act_relationship_shrunken (  target_act_uid ASC  , type_cd ASC  , status_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX act_relationship_ix02_2 ON dbo.Act_relationship_shrunken (  target_act_uid ASC  , type_cd ASC  , target_class_cd ASC  , record_status_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX act_relationship_ix03_2 ON dbo.Act_relationship_shrunken (  target_act_uid ASC  , type_cd ASC  , target_class_cd ASC  , record_status_cd ASC  , source_class_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;

CREATE TABLE NBS_ODSE.dbo.Entity_id_shrunken (
	entity_uid bigint NOT NULL,
	entity_id_seq smallint NOT NULL,
	add_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	add_time datetime NULL,
	add_user_id bigint NULL,
	assigning_authority_cd varchar(199) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	assigning_authority_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_amt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_unit_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	effective_from_time datetime NULL,
	effective_to_time datetime NULL,
	last_chg_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_chg_time datetime NULL,
	last_chg_user_id bigint NULL,
	record_status_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_time datetime NULL,
	root_extension_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_time datetime NULL,
	type_cd varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	type_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_affiliation_txt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	valid_from_time datetime NULL,
	valid_to_time datetime NULL,
	as_of_date datetime NULL,
	assigning_authority_id_type varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK_Entity_id_2 PRIMARY KEY (entity_uid,entity_id_seq)
);

INSERT INTO Entity_id_shrunken 
SELECT * FROM Entity_id 
WHERE entity_uid in (select person_uid from Person_shrunken)


 CREATE NONCLUSTERED INDEX ENTITY_ID_INDX_PERF1_2 ON dbo.Entity_id_shrunken (  entity_uid ASC  , status_cd ASC  , root_extension_txt ASC  , type_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_ENTITY_ID_02112021_02_2 ON dbo.Entity_id_shrunken (  record_status_cd ASC  )  
	 INCLUDE ( root_extension_txt , type_cd ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX IDX_ENTITY_ID_02112021_03_2 ON dbo.Entity_id_shrunken (  root_extension_txt ASC  , type_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX IDX_ENTITY_ID_ELR1_2 ON dbo.Entity_id_shrunken (  assigning_authority_cd ASC  , root_extension_txt ASC  , type_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX INDEX_ENTITY_ID_IND01_2 ON dbo.Entity_id_shrunken (  status_cd ASC  , type_cd ASC  , root_extension_txt ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX INDEX_LAST_CHG_TIME_2 ON dbo.Entity_id_shrunken (  last_chg_time ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX RDB_PERF_03302021_04_2 ON dbo.Entity_id_shrunken (  type_cd ASC  )  
	 INCLUDE ( assigning_authority_cd , root_extension_txt ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;
 CREATE NONCLUSTERED INDEX RDB_PERF_04042021_07_2 ON dbo.Entity_id_shrunken (  assigning_authority_cd ASC  , record_status_cd ASC  , type_cd ASC  )  
	 INCLUDE ( as_of_date , entity_uid , root_extension_txt ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITIES ] ;


CREATE TABLE NBS_ODSE.dbo.Entity_locator_Participation_shrunken (
	entity_uid bigint NOT NULL,
	locator_uid bigint NOT NULL,
	add_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	add_time datetime NULL,
	add_user_id bigint NULL,
	cd varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cd_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	class_cd varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_amt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_unit_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	from_time datetime NULL,
	last_chg_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_chg_time datetime NULL,
	last_chg_user_id bigint NULL,
	locator_desc_txt varchar(2000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_time datetime NULL,
	status_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_time datetime NULL,
	to_time datetime NULL,
	use_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_affiliation_txt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	valid_time_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	version_ctrl_nbr smallint NOT NULL,
	as_of_date datetime NULL,
	CONSTRAINT PK_Entity_loc_participation_2 PRIMARY KEY (entity_uid,locator_uid)
);

INSERT INTO Entity_locator_Participation_shrunken 
SELECT * FROM Entity_locator_participation 
WHERE entity_uid in (select person_uid from Person)

 CREATE NONCLUSTERED INDEX ELP_INDX_PERF1_2 ON dbo.Entity_locator_Participation_shrunken (  entity_uid ASC  , class_cd ASC  , use_cd ASC  , cd ASC  , status_cd ASC  , locator_uid ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_ELR_PARTICIPATION_ECU_2 ON dbo.Entity_locator_Participation_shrunken (  entity_uid ASC  , cd ASC  , use_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX INDEX_ELR_PARTICIPATION_IND01_2 ON dbo.Entity_locator_Participation_shrunken (  class_cd ASC  , status_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX INDEX_ELR_PARTICIPATION_IND02_2 ON dbo.Entity_locator_Participation_shrunken (  locator_uid ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX INDEX_ELR_PARTICIPATION_IND03_2 ON dbo.Entity_locator_Participation_shrunken (  cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX INDEX_ENTITY_LOCATOR_PARTIC_2 ON dbo.Entity_locator_Participation_shrunken (  use_cd ASC  , record_status_cd ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 80   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX INDEX_LAST_CHG_TIME_2 ON dbo.Entity_locator_Participation_shrunken (  last_chg_time ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITY_LOCATOR ] ;
 CREATE NONCLUSTERED INDEX PERF_02052021_07_2 ON dbo.Entity_locator_Participation_shrunken (  class_cd ASC  , record_status_cd ASC  , status_cd ASC  )  
	 INCLUDE ( cd , entity_uid , locator_uid , use_cd ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITY_LOCATOR ] ;
 CREATE NONCLUSTERED INDEX RDB_PERF_03302021_03_2 ON dbo.Entity_locator_Participation_shrunken (  cd ASC  , class_cd ASC  , use_cd ASC  )  
	 INCLUDE ( locator_desc_txt ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [ODS_ENTITY_LOCATOR ] ;

CREATE TABLE NBS_ODSE.dbo.Auth_user_shrunken (
	auth_user_uid bigint IDENTITY(1,1) NOT NULL,
	user_id varchar(256) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_type varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_title varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_department varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_first_nm varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_last_nm varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_work_email varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_work_phone varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_mobile_phone varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	master_sec_admin_ind char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	prog_area_admin_ind char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	nedss_entry_id bigint NOT NULL,
	external_org_uid bigint NULL,
	user_password varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_comments varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	add_time datetime NOT NULL,
	add_user_id bigint NOT NULL,
	last_chg_time datetime NOT NULL,
	last_chg_user_id bigint NOT NULL,
	record_status_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	record_status_time datetime NOT NULL,
	jurisdiction_derivation_ind char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	provider_uid bigint NULL,
	CONSTRAINT PK__Auth_use__610DFBB4519AEE00_2 PRIMARY KEY (auth_user_uid),
	CONSTRAINT UQ_Auth_user_id_2 UNIQUE (user_id),
);

SET IDENTITY_INSERT Auth_user_shrunken ON;

INSERT INTO Auth_user_shrunken (auth_user_uid,user_id,user_type,user_title,user_department,user_first_nm,user_last_nm,user_work_email,user_work_phone,
	user_mobile_phone,master_sec_admin_ind,prog_area_admin_ind,nedss_entry_id,external_org_uid,user_password,user_comments,
	add_time,add_user_id,last_chg_time,last_chg_user_id,record_status_cd,record_status_time,jurisdiction_derivation_ind,provider_uid)
SELECT auth_user_uid,user_id,user_type,user_title,user_department,user_first_nm,user_last_nm,user_work_email,user_work_phone,
	user_mobile_phone,master_sec_admin_ind,prog_area_admin_ind,nedss_entry_id,external_org_uid,user_password,user_comments,
	add_time,add_user_id,last_chg_time,last_chg_user_id,record_status_cd,record_status_time,jurisdiction_derivation_ind,provider_uid FROM Auth_user where user_id in ('state', 'superuser', 'coordinator', 'clerical');

 CREATE NONCLUSTERED INDEX IDX_Auth_user_02162021_04_2 ON dbo.Auth_user_shrunken (  nedss_entry_id ASC  )  
	 INCLUDE ( user_first_nm , user_last_nm ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;

CREATE TABLE NBS_ODSE.dbo.Entity_loc_participation_hist_shrunken (
	entity_uid bigint NOT NULL,
	locator_uid bigint NOT NULL,
	version_ctrl_nbr smallint NOT NULL,
	add_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	add_time datetime NULL,
	add_user_id bigint NULL,
	cd varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cd_desc_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	class_cd varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_amt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	duration_unit_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	from_time datetime NULL,
	last_chg_reason_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_chg_time datetime NULL,
	last_chg_user_id bigint NULL,
	locator_desc_txt varchar(2000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	record_status_time datetime NULL,
	status_cd char(1) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	status_time datetime NOT NULL,
	to_time datetime NULL,
	use_cd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_affiliation_txt varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	valid_time_txt varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	as_of_date datetime NULL,
	CONSTRAINT PK_Entity_loc_particip_hist2 PRIMARY KEY (entity_uid,locator_uid,version_ctrl_nbr)
);

INSERT INTO Entity_loc_participation_hist_shrunken 
SELECT * FROM Entity_loc_participation_hist 
WHERE entity_uid in (select person_uid from Person)

EXEC sp_rename 'Person', 'Person_original';
EXEC sp_rename 'Person_shrunken', 'Person';

EXEC sp_rename 'Observation', 'Observation_original';
EXEC sp_rename 'Observation_shrunken', 'Observation';

EXEC sp_rename 'Participation', 'Participation_original';
EXEC sp_rename 'Participation_shrunken', 'Participation';

EXEC sp_rename 'Act_id', 'Act_id_original';
EXEC sp_rename 'Act_id_shrunken', 'Act_id';

EXEC sp_rename 'Person_name', 'Person_name_original';
EXEC sp_rename 'Person_name_shrunken', 'Person_name';

EXEC sp_rename 'Act_relationship', 'Act_relationship_original';
EXEC sp_rename 'Act_relationship_shrunken', 'Act_relationship';

EXEC sp_rename 'Entity_id', 'Entity_id_original';
EXEC sp_rename 'Entity_id_shrunken', 'Entity_id';

EXEC sp_rename 'Auth_user', 'Auth_user_original';
EXEC sp_rename 'Auth_user_shrunken', 'Auth_user';

EXEC sp_rename 'Entity_loc_participation_hist', 'Entity_loc_participation_hist_original';
EXEC sp_rename 'Entity_loc_participation_hist_shrunken', 'Entity_loc_participation_hist';


--- truncate all history tables (optional - assuming we don't want to test existing data)
truncate table act_id_hist;
truncate table act_locator_participation_hist;
truncate table act_relationship_hist;
truncate table case_management_hist;
truncate table clinical_document_hist;
truncate table confirmation_method_hist;
truncate table ct_contact_answer_hist;
truncate table ct_contact_hist;
truncate table dsm_algorithm_hist;
truncate table entity_group_hist;
truncate table entity_id_hist;
truncate table entity_loc_participation_hist;
truncate table geocoding_result_hist;
truncate table intervention_hist;
truncate table interview_hist;
truncate table lab_event_hist;
truncate table manufactured_material_hist;
truncate table material_hist;
truncate table nbs_act_entity_hist;
truncate table nbs_answer_hist;
truncate table nbs_case_answer_hist;
truncate table nbs_document_hist;
truncate table nbs_page_hist;
truncate table nbs_question_hist;
truncate table nbs_rdb_metadata_hist;
truncate table nbs_ui_metadata_hist;
truncate table nnd_metadata_hist;
truncate table non_person_living_subject_hist;
truncate table notification_hist;
truncate table obs_value_coded_hist;
truncate table obs_value_coded_mod_hist;
truncate table obs_value_date_hist;
truncate table obs_value_numeric_hist;
truncate table obs_value_txt_hist;
truncate table observation_hist;
truncate table observation_interp_hist;
truncate table observation_reason_hist;
truncate table organization_hist;
truncate table organization_name_hist;
truncate table page_cond_mapping_hist;
truncate table participation_hist;
truncate table patient_encounter_hist;
truncate table participation_hist;
truncate table person_ethnic_group_hist;
truncate table person_hist;
truncate table person_name_hist;
truncate table person_race_hist;
truncate table physical_locator_hist;
truncate table place_hist;
truncate table postal_locator_hist;
truncate table procedure1_hist;
truncate table public_health_case_hist;
truncate table referral_hist;
truncate table role_hist;
truncate table state_defined_field_data_hist;
truncate table substance_administration_hist;
truncate table tele_locator_hist;
truncate table treatment_administered_hist;
truncate table treatment_hist;
truncate table treatment_procedure_hist;
truncate table wa_nnd_metadata_hist;
truncate table wa_question_hist;
truncate table wa_rdb_metadata_hist;
truncate table wa_rule_metadata_hist;
truncate table wa_template_hist;
truncate table wa_ui_metadata_hist;
truncate table workup_hist;

USE RDB;

-- |table                             |used_mb  |allocated_mb|
-- |----------------------------------|---------|------------|
-- |dbo.LAB100                        |51,319   |51,325      |
-- |dbo.LAB_TEST                      |38,794.75|38,795.09   |
-- |dbo.D_PATIENT                     |13,875.15|13,875.98   |
-- |dbo.LAB_TEST_RESULT               |12,872.61|12,874.23   |
-- |dbo.S_PATIENT                     |11,122.59|11,122.91   |
-- |dbo.LAB_RESULT_VAL                |8,242.22 |8,242.66    |
-- |dbo.EVENT_METRIC_INC              |8,033.3  |8,033.46    |
-- |dbo.D_ORGANIZATION                |5,126.22 |5,126.48    |

--- truncate all tables.  the ingestion team can populate them on the fly;
truncate table RDB.dbo.LAB100;
truncate table RDB.dbo.LAB_TEST;
truncate table RDB.dbo.D_PATIENT;
truncate table RDB.dbo.LAB_TEST_RESULT;
truncate table RDB.dbo.S_PATIENT;
truncate table RDB.dbo.LAB_RESULT_VAL;
truncate table RDB.dbo.EVENT_METRIC_INC;
truncate table RDB.dbo.D_ORGANIZATION;

-- Re-enable constraints for all tables in the database:
EXEC sp_msforeachtable 'ALTER TABLE ? WITH CHECK CHECK CONSTRAINT ALL'
