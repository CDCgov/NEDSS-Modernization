/**********************************/
/*** USER_PROFILE***/
/**********************************/

/* join State Defined Metadata and Data tables */
/*Proc SQL;
 Create table rdbdata.USER_PROFILE as
  Select *
   From nbs_ods.USER_PROFILE;
Quit;


%dbload (USER_PROFILE, rdbdata.USER_PROFILE);  */
PROC SQL;
CREATE TABLE 
PROVIDER_USER_DIMENSION 
AS SELECT USER_FIRST_NM AS FIRST_NM, 
USER_LAST_NM AS LAST_NM, 
LAST_CHG_TIME AS LAST_UPDT_TIME, 
NEDSS_ENTRY_ID, 
PROVIDER_UID
FROM NBS_ODS.AUTH_USER;

CREATE TABLE
user_provider as select  
PROVIDER_UID
FROM PROVIDER_USER_DIMENSION where PROVIDER_UID is not null;

create table user_provider_key as 
select user_provider.PROVIDER_UID, provider_key, PROVIDER_QUICK_CODE
from user_provider 
inner join nbs_rdb.D_provider on
user_provider.PROVIDER_UID = D_provider.PROVIDER_UID;


CREATE TABLE 
rdbdata.USER_PROFILE as select 
PROVIDER_USER_DIMENSION.*, user_provider_key.provider_key, user_provider_key.PROVIDER_QUICK_CODE
from PROVIDER_USER_DIMENSION
left outer join user_provider_key on
PROVIDER_USER_DIMENSION.PROVIDER_UID = user_provider_key.PROVIDER_UID;
QUIT;
proc sort data=rdbdata.USER_PROFILE nodupkey; by NEDSS_ENTRY_ID; run;

DATA rdbdata.USER_PROFILE;
SET rdbdata.USER_PROFILE;
IF provider_key=. THEN provider_key=1;
RUN;

%dbload (USER_PROFILE, rdbdata.USER_PROFILE);
