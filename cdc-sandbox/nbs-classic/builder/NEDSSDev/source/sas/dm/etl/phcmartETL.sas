%etllib;
filename etlpgm "&SAS_REPORT_HOME/dm/etl";
%include etlpgm(etlconstants.sas);

%macro phcdmetl;

***get phc data ***;

%include etlpgm (phcinfo.sas);
%put After Phc data Fetched: sysdbmsg=&sysdbmsg;
%if &sqlobs=0 or &sqlrc > 0 or &syserr > 4 or &sysdbrc ~= 0 %then %do;
%put sqlobs=&sqlobs;
%put sqlrc=&sqlrc;
%put syserr=&syserr;
%put sysdbrc=&sysdbrc;
%put sysdbmsg=&sysdbmsg;
%goto finish;
%end;


***get phc subject data ***;
%include etlpgm(phcsubject.sas);
%put After Phcsubject data Fetched: sysdbmsg=&sysdbmsg;

%if &sqlrc > 0 or &syserr > 4 or &sysdbrc ~= 0 %then %do;
%put sqlobs=&sqlobs;
%put sqlrc=&sqlrc;
%put syserr=&syserr;
%put sysdbrc=&sysdbrc;
%put sysdbmsg=&sysdbmsg;
%goto finish;
%end;

***add summary data ***;

%include etlpgm(phcsummary.sas);
%put After phcsummary data fetched: sysdbmsg=&sysdbmsg;

%if &sqlrc > 0 or &syserr > 4 or &sysdbrc ~= 0 %then %do;
%put sqlobs=&sqlobs;
%put sqlrc=&sqlrc;
%put syserr=&syserr;
%put sysdbrc=&sysdbrc;
%put sysdbmsg=&sysdbmsg;
%goto finish;
%end;

*** merge patient data and summary data to phc ****;
data phcsubject;
	retain patientMatch summaryMatch 0;
	merge phcinfo (in =A) phcpatientinfo (in=B) phcsummary (in=C) END=eof;
		by public_health_case_uid;
		if A;
		if A and B then patientMatch+1;
		if A and C then summaryMatch+1;
	if eof then do;
		put 'Number of PHC records updated with Patient data: ' patientMatch comma8.;
		put 'Number of PHC records updated with Summary data: ' summaryMatch comma8.;
	end;
	drop patientMatch summaryMatch;
run;


***add notification data***;
%include etlpgm(phcnotification.sas);
%put After phcnotification data fetched: sysdbmsg=&sysdbmsg;
data phcfact;
	retain matchCount 0;
	merge phcsubject (in=A) phcnotification(in=B) END=eof;
	by public_health_case_uid;
	if A;
	
	if A and B then matchCount+1;
	if eof then do;
		put 'Number of PHC records updated with notification data: ' matchCount comma8.;
	end;
	drop matchCount;
run;


***add confirmation method ***;
%include etlpgm(phcconfirmation.sas);
%put After phcconfirmation data fetched: sysdbmsg=&sysdbmsg;
data phcfact2;
	merge phcfact (in=A) phcconfirmation(in=B);
	by public_health_case_uid;
	if A;
run;

***add investigator, reporter, organization ***;
%include etlpgm(phcreporter.sas);
%put After phcreporter data Fetched: sysdbmsg=&sysdbmsg;
data phcfact3;
	merge phcfact2 (in=A) PhcReporter(in=B);
	by public_health_case_uid;
	if A;
run;

***add person race ***;
%include etlpgm(phcpersonrace.sas);
%put After phcpersonrace data fetched: sysdbmsg=&sysdbmsg;
proc sort data=phcfact3; by person_uid;run;
data phcmart;
	merge phcfact3 (in=A) phcpersonrace(in=B);
	by person_uid;
	if A;
run;

%if &sqlrc > 0 or &syserr > 4 %then %do;
%put sqlobs=&sqlobs;
%put sqlrc=&sqlrc;
%put syserr=&syserr;
%put sysdbrc=&sysdbrc;
%put sysdbmsg=&sysdbmsg;
%goto finish;
%end;

***event date calculation, add load time***;
data phcmart;
	set phcmart;
	length event_type $20;
	if OnSetDate ~= . then 
		do;
			event_date = OnSetDate;
			event_type = 'O';
		end;
	else if Diagnosis_date ~= . then 
		do;
			event_date = Diagnosis_date;
			event_type = 'D';
		end;
	else if rpt_to_county_time ~=. 	then 
	do;
			event_date = rpt_to_county_time;
			event_type = 'C';
		end;

	*lab date to be added;
	else if rpt_to_state_time ~=. 	then 
		do;
			event_date = rpt_to_state_time;
			event_type = 'S';
		end;
	else 
		do;
			event_date = phc_add_time;
			event_type = 'P';
		end;

	
	if rpt_to_county_time ~=. 	then 
		report_date = rpt_to_county_time;
	else if rpt_to_state_time ~=. 	then 
		report_date = rpt_to_state_time;
	else 
		report_date = phc_add_time;

	if PHC_CODE in ('10220', '10030')  then deceased_time=INVESTIGATION_DEATH_DATE;
	if PHC_CODE in ('10220', '10030')  then investigatorAssigneddate=INVESTIGATOR_ASSIGN_DATE;
	if PHC_CODE in ('10220') and THERAPY_DATE_FIELD ~=. then EVENT_DATE=THERAPY_DATE_FIELD;
	ELSE if PHC_CODE in ('10220') and rpt_form_cmplt_time~=. then EVENT_DATE=rpt_form_cmplt_time;
	if PHC_CODE in ('10220') and THERAPY_DATE_FIELD ~=. then EVENT_TYPE = 'RVCT_DTS';
	ELSE if PHC_CODE in ('10220') and rpt_form_cmplt_time ~=. then EVENT_TYPE = 'RVCT_DR';

	format report_date datetime20. event_date datetime20.;

	/*add record time*/
	length mart_record_creation_time 8;
	format mart_record_creation_time datetime20.;
	informat mart_record_creation_time datetime20.;
	label mart_record_creation_time='DateTime Stamp of when row was loaded';
	mart_record_creation_time=input("&SYSDATE9"||' '||"&SYSTIME",DATETIME20.);
	if _n_ = 1 then do;
		put 'NOTE: mart_record_creation_time for this Load is: ' mart_record_creation_time datetime20.;
	end;
run;

%if &syserr > 4  %then %do;
%put syserr=&syserr;
%goto finish;
%end;



***remove old data***;

proc sql;
	delete * from nbs_ods.SubjectRaceInfo;
	delete * from nbs_ods.PublicHealthCaseFact;
	quit;

%put After removing old data;
%put sqlobs=&sqlobs;
%put sqlrc=&sqlrc;
%put syserr=&syserr;
%put sysdbrc=&sysdbrc;
%put sysdbmsg=&sysdbmsg;

***load new data***;
proc append data=phcmart base=nbs_ods.PublicHealthCaseFact (CNTLLEV=REC)  force; 
run;

%put After inserting new data to fact table;
%put sqlobs=&sqlobs;
%put sqlrc=&sqlrc;
%put syserr=&syserr;
%put sysdbrc=&sysdbrc;
%put sysdbmsg=&sysdbmsg;
%if &sqlrc > 0 or &syserr > 4  %then %do;
%goto finish;
%end;


proc sql;
	create table subjectraceinfo as
	select 	
			public_health_case_uid,
			0 as morbreport_uid format=8.,
			/*pr.person_uid,*/ 
			race_cd, 
			race_category_cd, 
			race_desc_txt
	from	nbs_ods.person_race as pr,
			nbs_ods.PublicHealthCaseFact as pf
	where	pr.person_uid = pf.person_uid
	order by public_health_case_uid;
quit;

proc append data=subjectraceinfo base=nbs_ods.subjectraceinfo (CNTLLEV=REC) force; 
run;
%put After inserting new data to SubjectRaceInfo table;
%put sqlobs=&sqlobs;
%put sqlrc=&sqlrc;
%put syserr=&syserr;
%put sysdbrc=&sysdbrc;
%put sysdbmsg=&sysdbmsg;


%finish:
%put sqlobs=&sqlobs;
%put sqlrc=&sqlrc;
%put syserr=&syserr;

/*Etl processing time*/
Data _Null_;
	length Etlmsg $20.;
	if  &sqlobs => 0 AND &sqlrc = 0 AND &syserr <= 4 
		then Etlmsg='Successful';
		else  Etlmsg='Incomplete';
	put 'ETL Process Status: '  Etlmsg $char20.;

	format Start_Time End_Time Datetime20. Total_Time TOD8.;
	Start_Time=input("&SYSDATE9"||' '||"&SYSTIME",DATETIME20.);
	End_Time=datetime();
	Total_Time=End_Time-Start_time;
	put 'ETL Processing Time:';
	put '	Start Time: ' Start_Time Datetime20.;
	put '	End Time:   ' End_Time   Datetime20.;
	put '	Total Time: ' Total_Time time8.;
	put '*****The End*********';
run;


%mend phcdmetl;
%phcdmetl;



