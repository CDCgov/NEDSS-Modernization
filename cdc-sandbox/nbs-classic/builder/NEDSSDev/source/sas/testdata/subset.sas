***************************************************************************************;
*** This is a SQL to subset the Database, and the subset is used to generate reports***; 
*** 	Date Created: 01-07-2002													***;
***		Input Data	: PHC_TO_PERSON_VIEW											***;
***************************************************************************************;

Proc SQL;
Create table PHC_To_Person_View
as select 
region_district_cd,
state_cd,
State,
County,
Cnty_cd,
City_cd,
PHC_code_short_desc,
datepart(Diagnosis_Date)		 		as Diagnosis_Date 	label='Diagnosis Date',
datepart(Deceased_time)					as Deceased_Date		label='Date of Death',
Deceased_ind_cd,
cd_system_cd,
Ethnic_group_ind,
Birth_time,
Zip_cd,
curr_sex_cd,
rpt_to_county_time

from nbs_ods.PHC_To_Person_View(where=(&where));

quit;

data _null_;
  if 0 then set phc_to_person_view nobs=numobs;
  if numobs=0 then
    call symput('skip','yes');
  else call symput('skip','no');
  stop;
run;