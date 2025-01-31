proc sql;
create table rdbdata.geocoding_location as
select geocoding.*, PATIENT.PATIENT_KEY 
From nbs_rdb.L_PATIENT PATIENT
LEFT OUTER join  nbs_ods.ENTITY_LOCATOR_PARTICIPATION elp
	on elp.ENTITY_UID = PATIENT.PATIENT_UID 
left outer join nbs_ods.POSTAL_LOCATOR pl
on  pl.postal_locator_uid = elp.locator_uid
left outer join nbs_ods.geocoding_result geocoding
on pl.postal_locator_uid = geocoding.postal_locator_uid
where geocoding_result.record_status_cd = 'ACTIVE';
quit;
%assign_key (ds=rdbdata.geocoding_location, key=geocoding_location_key);

