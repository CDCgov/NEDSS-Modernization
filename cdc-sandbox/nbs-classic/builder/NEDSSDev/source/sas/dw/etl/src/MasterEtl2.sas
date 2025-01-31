%etllib;
OPTIONS COMPRESS=YES;
options fmtsearch=(nbsfmt);
%include etlpgm(etlmacro.sas);
%global etlerr;
%let etlerr=0;


%macro checkerr;
	%if &syserr > 4 %then %let etlerr=%eval(&etlerr+1);
	%put syserr=&syserr;
	%if &etlerr !=0 %then %do;
		%put dataset=&syslast;
		%put syserr=&syserr;
	%end;
%mend checkerr;


%macro MasterEtl;


%if  %SYSFUNC(LIBREF(nbs_ods)) NE 0 %then %goto liberr;
%if  %SYSFUNC(LIBREF(nbs_srt)) NE 0 %then %goto liberr;
%if  %SYSFUNC(LIBREF(nbs_rdb)) NE 0 %then %goto liberr;
%if  %SYSFUNC(LIBREF(nbsfmt)) NE 0 %then %goto liberr;

Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start BMIRD_STREP_PNEUMO_DATAMART*********';
run;
%include etlpgm (BMIRD_STREP_PNEUMO_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End BMIRD_STREP_PNEUMO_DATAMART*********';
run;

Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start MORBIDITY_REPORT_DATAMART*********';
run;
%include etlpgm (MORBIDITY_REPORT_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End MORBIDITY_REPORT_DATAMART*********';
run;

Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start AGGREGATE_DATAMART*********';
run;
%include etlpgm (AGGREGATE_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End AGGREGATE_DATAMART*********';
run;

Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start SummaryReportFact*********';
run;
%include etlpgm (SummaryReportFact.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End SummaryReportFact*********';
run;

Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start SR100*********';
run;
%include etlpgm (SR100.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End SR100*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start PamCases*********';
run;
%include etlpgm (PamCases.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End PamCases*********';
run;

Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start TB_DATAMART*********';
run;
%include etlpgm (TB_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End TB_DATAMART*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start TB_HIV_DATAMART*********';
run;
%include etlpgm (TB_HIV_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End TB_HIV_DATAMART*********';
run;
Data _Null_;
	format Time Datetime20.;
	Time=datetime();
	put '	Start Time:   ' Time   Datetime20.;
	put '*****The Start VAR_DATAMART*********';
run;
%include etlpgm (VAR_DATAMART.sas);
Data _Null_;
	format Time1 Datetime20.;
	Time1=datetime();
	put '	End Time:   ' Time1   Datetime20.;
	put '*****The End VAR_DATAMART*********';
run;
%include "&SAS_REPORT_HOME/custom/Custom_controller.sas";

%goto finish;

%liberr:
	%put Libname statement error &syslibrc &SYSMSG ;
	%let etlerr=1;	

%finish:

/*need to put more checking here*/
Data _Null_;
	length Etlmsg $20.;
	if  &etlerr = 0
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

data rdbdata.etlstatus;
format etlendtime datetime20.;
etlendtime=datetime();
run;
%mend MasterEtl;
%MasterEtl;

%put &etlerr;
