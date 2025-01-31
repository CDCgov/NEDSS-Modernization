proc sql;
	create table notification as
	select	target_act_uid, 
			target_class_cd,
			source_act_uid,
			source_class_cd,
			nf.version_ctrl_nbr,
			nf.add_time, 
			nf.add_user_id, 
			nf.rpt_sent_time,
			nf.record_status_cd,
			nf.record_status_time,
			nf.last_chg_time,
			'Y' as hist_ind
	from 	nbs_ods.act_relationship ar, nbs_ods.notification_hist nf 
	where 	ar.source_act_uid = nf.notification_uid 
			and source_class_cd ='NOTF'
			and target_class_cd ='CASE'
			and (nf.record_status_cd ='COMPLETED'
				or nf.record_status_cd ='MSG_FAIL'
				or nf.record_status_cd ='REJECTED'
				or nf.record_status_cd ='PEND_APPR'
				or nf.record_status_cd ='APPROVED'
				)
	union
	select	target_act_uid, 
			target_class_cd,
			source_act_uid,
			source_class_cd,
			0	as version_ctrl_nbr,
			nf.add_time, 
			nf.add_user_id, 
			nf.rpt_sent_time,
			nf.record_status_cd,
			nf.record_status_time,
			nf.last_chg_time,
			'N' as hist_ind
	from 	nbs_ods.act_relationship ar, nbs_ods.notification nf 
	where 	ar.source_act_uid = nf.notification_uid 
			and source_class_cd ='NOTF'
			and target_class_cd ='CASE'
			and (nf.record_status_cd ='COMPLETED'
				or nf.record_status_cd ='MSG_FAIL'
				or nf.record_status_cd ='REJECTED'
				or nf.record_status_cd ='PEND_APPR'
				or nf.record_status_cd ='APPROVED'
				)
	;
quit;

proc sort data=notification out=notif2; by target_act_uid last_chg_time;
run;

/* notification created count, does not matter if it's sent */
data phcnotification(rename =(target_act_uid=public_health_case_uid)
			drop= target_class_cd source_act_uid source_class_cd
			version_ctrl_nbr add_time add_user_id	
			record_status_time rpt_sent_time last_chg_time  
			record_status_cd hist_ind x1 x2);
	set notif2;
	format firstnotificationdate datetime20.;
	format lastnotificationdate datetime20.;
	format firstnotificationsenddate datetime20.;
	format lastnotificationsenddate datetime20.;
	format firstnotificationstatus $20.;
	format notificationdate datetime20.;
	format notifCreatedCount 3.;	
	format notifSentCount 3.;
	retain firstnotificationdate 
		   lastnotificationdate 
		   firstnotificationsenddate
		   lastnotificationsenddate 
		   firstnotificationsubmittedby 
		   lastnotificationsubmittedby
		   notifCreatedCount x1 x2 notifSentCount 0;
	retain firstnotificationstatus '';

	by target_act_uid last_chg_time;

	if first.target_act_uid and first.last_chg_time then do;
		firstnotificationsenddate =.;
		lastnotificationsenddate =.;
		lastnotificationdate = .;
		firstnotificationdate = last_chg_time;
		firstnotificationsubmittedby = add_user_id;
		firstnotificationstatus = record_status_cd;
		notifSentCount = 0;
		notifCreatedCount = 0;
		x1 = 0;
		x2 = 0;
		end;

	if (firstnotificationsenddate =. and record_status_cd = 'COMPLETED' ) then firstnotificationsenddate = rpt_sent_time;

	if (record_status_cd = 'COMPLETED' and rpt_sent_time ~= . ) then lastnotificationsenddate = rpt_sent_time;
	if (record_status_cd = 'APPROVED' or record_status_cd = 'PEND_APPR' ) then do;
		lastnotificationdate = last_chg_time;
		x1 +1;
		end;
	if (hist_ind ='Y' and  record_status_cd = 'PEND_APPR' ) then x2+1;
	
	if record_status_cd = 'COMPLETED' then notifSentCount + 1;

	if last.target_act_uid and last.last_chg_time then do;
		notificationdate = firstnotificationsenddate; 
		lastnotificationsubmittedby = add_user_id;
		notifCreatedCount = x1 - x2;
		output;
	end;
run;

