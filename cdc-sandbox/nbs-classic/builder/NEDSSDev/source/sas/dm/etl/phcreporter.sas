/* phc reporter, investigator, reporting organization names and phones*/
proc sql; 
create table reporters as 

	select 	PAR.act_uid, 
			person_uid as entity_uid label='Entity_uid',
			type_cd, 
			TEL.Phone_nbr_txt,
			PAR.from_time,
			trim(pnm.last_nm) || ', '|| pnm.first_nm as name
	from 	nbs_ods.participation as PAR

	LEFT JOIN  nbs_ods.person_name as PNM
			on 	PNM.PERSON_UID = PAR.subject_entity_uid
			and PNM.nm_use_cd=&PNM_NM_USE_CD_LEGAL
			and PNM.record_status_cd='ACTIVE'

	LEFT JOIN nbs_ods.entity_locator_participation as ELP
			on	ELP.entity_uid = PAR.subject_entity_uid
			and ELP.class_cd = 'TELE' 
			and ELP.use_cd = 'WP'		/*work place*/
			and ELP.cd = 'PH'			/*phone*/
			and ELP.record_status_cd = 'ACTIVE'
			
	LEFT JOIN nbs_ods.tele_locator as TEL
	 		on ELP.locator_uid = TEL.tele_locator_uid
			/*and (TEL.record_status_cd is not null and TEL.record_status_cd ='ACTIVE')*/
				
			
	where
			(PAR.type_cd = &PAR_TYPE_CD_PROVIDER
			or PAR.type_cd = &PAR_TYPE_CD_INVESTIGATOR
			or PAR.type_cd = &PAR_TYPE_CD_REPORTER)

	union

	select 	PAR2.act_uid, 
			ORG.organization_uid as entity_uid ,
			type_cd, 
			' ' 	as Phone_nbr_txt,
			PAR2.from_time,
			ORG.nm_txt as name
	from  	nbs_ods.participation as PAR2, nbs_ods.organization_name as ORG
	where 	ORG.organization_UID = PAR2.subject_entity_uid
		and PAR2.record_status_cd = 'ACTIVE'
		and PAR2.type_cd  = &PAR_TYPE_CD_SOURCE
		and nm_txt ~=' '

	order by act_uid;

quit;
	

data PhcReporter(rename=(act_uid=public_health_case_uid) 
				drop = entity_uid name type_cd from_time Phone_nbr_txt);
	set reporters;
	format  providerName reporterName investigatorName  $102.;
	format  providerPhone reporterPhone investigatorPhone  $20.;
	format 	organizationName $100.;
	format 	investigatorAssignedDate datetime20.;
	retain	providerName reporterName investigatorName organizationName ' ' investigatorAssignedDate . ;
	retain	providerPhone reporterPhone investigatorPhone ' ';

	by		act_uid;
	
		if first.act_uid then do;
  			providerName = ' '; 	reporterName = ' ';
			investigatorName = ' '; organizationName =' '; 
			investigatorAssignedDate = .;
			providerPhone = ' ';
			reporterPhone = ' ';
			investigatorPhone = ' ';
  			end;
		if type_cd = &PAR_TYPE_CD_PROVIDER			then do;
			providerName  = name; 
			providerPhone = Phone_nbr_txt;
			end;
		else if type_cd = &PAR_TYPE_CD_REPORTER 	then do;
			reporterName  = name; 
			reporterPhone = Phone_nbr_txt;
			end;
		else if type_cd = &PAR_TYPE_CD_INVESTIGATOR	then do;
			investigatorName  = name;
			investigatorPhone = Phone_nbr_txt;
			investigatorAssignedDate = from_time; 
			end;
		else if type_cd = &PAR_TYPE_CD_SOURCE 		then organizationName = name; 

		if last.act_uid then output;
	run;

