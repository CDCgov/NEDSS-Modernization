/************************************************************
*													 	  	*
*	Notification_Event Fact							   	  	*
*														  	*
*		NOTIFICATION_STATUS,								*
*		NOTIFICATION_COMMENTS,								*	
*		PATIENT_KEY,										*
*		NOTIFICATION_SENT_DT_KEY,							*				
*		NOTIFICATION_SUBMIT_DT_KEY,							*
*		NOTIFICATION_LOCAL_ID,								*	
*		NOTIFICATION_SUBMITTED_BY,							*
*		INVESTIGATION_KEY,									*
*		CONDITION_KEY										*
*															*
************************************************************/

proc sql;
	create table Notification_all as
	select	act.target_act_uid		'Public_health_case_uid' as public_health_case_uid, 
			not.notification_uid,
			not.add_time, 
			not.add_user_id 		'Notification_submitted_by' as Notification_submitted_by,		
			not.last_chg_time       'NOTIFICATION_LAST_CHANGE_TIME' AS NOTIFICATION_LAST_CHANGE_TIME, 
			not.rpt_sent_time,		
			not.record_status_cd	'NOTIFICATION_STATUS' as Notification_status,
			not.local_id			'NOTIFICATION_LOCAL_ID' as Notification_local_id,
			TRANSLATE(not.txt,' ' ,'0D0A'x) 'NOTIFICATION_COMMENTS' as Notification_comments,
			phc.Patient_key,
			phc.Investigation_key,
			phc.Condition_key
			
	from 	nbs_ods.act_relationship as act, 
			nbs_ods.notification as not,
			rdbdata.Phc_keys	as phc
	where 	act.source_act_uid = not.notification_uid 
			and source_class_cd ='NOTF'
			and target_class_cd ='CASE'
			and not.cd not in ('EXP_NOTF', 'SHARE_NOTF', 'EXP_NOTF_PHDC','SHARE_NOTF_PHDC')
			and act.target_act_uid = phc.public_health_case_uid

	;
quit;
data Notification (drop = patient_key investigation_key condition_key);
set notification_all;
run;
%assign_key(Notification, notification_key);
data Notification_Event1 
(Drop = public_health_case_uid 
public_health_case_uid 
Notification_submitted_by 
Notification_status
Notification_local_id
Notification_comments);
set notification_all;
run;

proc sql;
create table Notification_Event2 as
select not_e.*, not.notification_key, 1 as count  
from notification_event1 as not_e, notification as not
where not_e.notification_uid = not.notification_uid;
quit;


proc sql;
create table Notification_event as 
select 	
		ne.*,
		COALESCE(d1.Date_key,1) as NOTIFICATION_SENT_DT_KEY,		
		COALESCE(d2.Date_key,1) as NOTIFICATION_SUBMIT_DT_KEY,
		COALESCE(d3.Date_key,1) as NOTIFICATION_UPD_DT_KEY	
from	Notification_event2 as ne
	left join Rdbdata.Datetable  as d1
		on ne.rpt_sent_time ~=.
		and d1.Date_key ~= 1
	    and datepart(ne.rpt_sent_time)*24*60*60 = d1.DATE_MM_DD_YYYY
		
	left join	rdbdata.Datetable  as d2
		on ne.add_time ~= .
		and datepart(ne.add_time)*24*60*60 = d2.DATE_MM_DD_YYYY	
		
	left join	rdbdata.Datetable  as d3
		on ne.NOTIFICATION_LAST_CHANGE_TIME ~= .
		and datepart(ne.NOTIFICATION_LAST_CHANGE_TIME)*24*60*60 = d3.DATE_MM_DD_YYYY
	
;
quit;
data rdbdata.notification (drop = notification_uid rpt_sent_time add_time public_health_case_uid);
set notification;
run;
data rdbdata.notification_event (drop = notification_uid rpt_sent_time add_time NOTIFICATION_LAST_CHANGE_TIME);
set notification_event;
run;

proc sort data = rdbdata.notification_event;
by notification_key;run;


/*
proc sql;
	create table Notification as
	select	act.target_act_uid		as public_health_case_uid, 
			not.notification_uid,
			not.add_time			as notification_add_dt, 
			not.add_user_id			as Notification_submitted_by, 
			not.rpt_sent_time		as notification_sent_dt,
			not.record_status_cd	as Notification_status,
			not.local_id			as Notification_local_id,
			not.txt					as Notification_comments
			
			
			
	from 	nbs_ods.act_relationship as act, 
			nbs_ods.notification as not,
			rdbdata.Phc_keys	as phc
	where 	act.source_act_uid = not.notification_uid 
			and source_class_cd ='NOTF'
			and target_class_cd ='CASE'
			and act.target_act_uid = phc.public_health_case_uid

	;
quit;
%assign_key(Notification, notification_key);
*/







/*
proc sql;
	create table Notification_event as
	select	act.target_act_uid		as public_health_case_uid, 
			not.notification_uid,
			not.add_time			as add_time, 
			not.add_user_id			as Notification_submitted_by, 
			not.rpt_sent_time		as rpt_sent_time,
			not.record_status_cd	as Notification_status,
			not.local_id			as Notification_local_id,
			not.txt					as Notification_comments,
			phc.Patient_key,
			phc.Investigation_key,
			phc.Condition_key
			
	from 	nbs_ods.act_relationship as act, 
			nbs_ods.notification as not,
			rdbdata.Phc_keys	as phc
	where 	act.source_act_uid = not.notification_uid 
			and source_class_cd ='NOTF'
			and target_class_cd ='CASE'
			and act.target_act_uid = phc.public_health_case_uid

	;
quit;*/
/* Notification Submit Key*/
/* Notification Sent Key*/
/*
proc sql;
create table Notification_event2 as 
select 	
		ne.*,
		COALESCE(d1.Date_key,1) as NOTIFICATION_SENT_DT_KEY,		
		COALESCE(d2.Date_key,1) as NOTIFICATION_SUBMIT_DT_KEY
from	Notification_event as ne
	left join Rdbdata.Datetable  as d1
		on ne.rpt_sent_time ~=.
		and d1.Date_key ~= 1
	    and datepart(ne.rpt_sent_time)*24*60*60 = d1.DATE_MM_DD_YYYY
		
	left join	rdbdata.Datetable  as d2
		on ne.add_time ~= .
		and datepart(ne.add_time)*24*60*60 = d2.DATE_MM_DD_YYYY	
	
;
quit;


		
data Rdbdata.Notification_event;
set Notification_event2;
drop
	public_health_case_uid
	notification_uid
	add_time
	rpt_sent_time
;
run;*/


/**Delete temporary data sets**/
PROC datasets library = work nolist;
delete 
	Notification_all
	Notification 
	Notification_event
	Notification_event1
	Notification_event2
;
run;
quit;
