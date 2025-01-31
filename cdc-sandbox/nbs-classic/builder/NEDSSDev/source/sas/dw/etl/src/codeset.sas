/*----------------------------------------------------

 	CODESET TABLE
	This program should be part of the setup
	and needs to run only once before all other
	porgrams

------------------------------------------------------*/
%macro loadCodeSet;
/*Check to see if the libref has been assigned. If a libref is not assigned 
then execute the macro called etllib defined in either autoexec.sas or etldevenv.sas*/
%if %SYSFUNC(LIBREF(nbs_ods)) NE 0 or
	%SYSFUNC(LIBREF(nbs_srt)) NE 0 or
	%SYSFUNC(LIBREF(nbs_rdb)) NE 0
%then %do;
%etllib;
%include etlpgm(etlmacro.sas);
%end;


/* totalidm.XLS
SELECT     	[Unique ID], 
			[SRT Reference], 
			format,
			label
FROM  imdbo.tblTOTALIDM
WHERE     (Status = 'Active') and [Unique ID] NOT IN ('------', '******') 
ORDER BY [Unique ID] 
*/
/* Load the codeset data sets from Excel */


/*Proc Import datafile="&SAS_REPORT_HOME\metadata\totalidm.XLS" Replace Out=nbsfmt.totalidm;   Run;*/


Proc SQL;
 Create table nbsfmt.totalidm as
	select
	unique_cd as unique_id 'unique_id',
	SRT_reference as SRT_reference 'SRT_reference',
	format as format 'format',
	label as label 'label'
	from nbs_srt.totalidm;
Quit;

/*
%macro importfact (sheet, DSout);
	Proc Import datafile="&SAS_REPORT_HOME\metadata\imRDBmapping.xls" Replace Out=&DSout;
  
	Sheet=&sheet;
 Run;
%mend importfact;

%importfact ('bmird',BMIRDcodeset);
%importfact ('sumcase',SUMcodeset);
%importfact ('labobs',LABcodeset);
%importfact ('morbidity',MORBcodeset);
%importfact ('treatment',TREATcodeset);
%importfact ('vaccination',VACCcodeset);
%importfact ('rubella',RUBcodeset);
%importfact ('pertussis',PERTcodeset);
%importfact ('crs',CRScodeset);
%importfact ('measles',MEAcodeset);
%importfact ('hepatitis',HEPcodeset);
%importfact ('person',PERScodeset);
%importfact ('org',ORGcodeset);
%importfact ('notification',NOTcodeset);
%importfact ('investigation',INVcodeset);
*/

Proc SQL;
 Create table rdbdata.imrdbmapping as
	select unique_cd as UNIQUE_ID 'UNIQUE_ID',
	unique_name as unique_name 'unique_name',
	description as description 'description',
	DB_table as DB_table 'DB_table',
	DB_field as DB_field 'DB_field',
	RDB_table as RDB_table 'RDB_table',
	RDB_attribute as RDB_attribute 'RDB_attribute',
	other_attributes as other_attributes 'other_attributes',
	condition_cd as condition_cd 'condition_cd'
from nbs_srt.imrdbmapping;
Quit;



/* append all codeset data sets */
Data ALLcodeset;
 /*if a unique id has > 7, it may cuase the extra char lost*/
 format Unique_ID $7. RDB_Attribute $32.;
 Set rdbdata.imrdbmapping;
 Rename RDB_Table=TBL_NM RDB_Attribute=COL_NM;
 Drop Unique_Name Description DB_Table DB_Field Other_Attributes;
Run;

Proc Sort Data=ALLcodeset;
 By Unique_ID;
Run;
Proc Sort Data=nbsfmt.totalidm out=totalidm;
 By Unique_ID;
Run;

/*NBS inserted RUB91a instead of RUB091a, we can change imRDBmapping and totalIDM, 
or add  change here to chnge the unique_ID from RUB091a to RUB91a 
for ALLcodeset and totalidm before joining the datasets*/


/* join totalidm to rdbcodeset to get Code_Set_Nm */
Data RDBCodeset;
 Merge 
		totalidm (rename=(srt_reference=code_set_nm) drop=format)
		ALLcodeset (in=B);
 By Unique_ID;
 if B;
 Rename Unique_ID=CD;
 format cd_desc $300.;
 cd_desc = label;
 drop label;
Run;

/*get Code_Set information (system, version, etc.) from Srt */
Data SRTcodeset;
 Set nbs_srt.Codeset (KEEP=code_set_nm code_set_desc_txt source_version_txt);
 Rename code_set_desc_txt=CD_DESC 
        source_version_txt=CD_SYS_VER;
 /* always select first entry */
 DROP seq_num;
Run;

Proc Sort Data=SRTcodeset;
  By code_set_nm;
Run;

Proc Sort Data=RDBcodeset;
  By code_set_nm;
Run;

/* Join RDBCodeset and SRTCodeset */
Data rdbdata.Codeset;
 Merge RDBCodeset(in=a) SRTcodeset;
 By code_set_nm; 
 If a;
Run;




/**************************************/
/* CODE_VALUE_GENERAL TABLE */

/* join Codeset and Code_Value_General on Code_Set_Nm */

Proc SQL;
 Create table rdbdata.code_val_general as
  select a.cd, b.code as CODE_VAL, 
         b.code_short_desc_txt as CODE_DESC, 
         b.code_system_cd as CODE_SYS_CD, 
         b.code_system_desc_txt as CODE_SYS_CD_DESC,
         b.effective_from_time as CODE_EFF_DT, 
		 b.effective_to_time as CODE_END_DT
   from rdbdata.Codeset a, nbs_srt.Code_Value_General b
   where a.code_set_nm = b.code_set_nm;
Quit;

data rdbdata.code_val_general;
set rdbdata.code_val_general;
	code_key = _n_;
run;



Data _null_;
if 0 then set rdbdata.codeset nobs=n_codeset;
if 0 then set rdbdata.code_val_general nobs=n_cvg;
call symput('n_codeset',put(n_codeset, 8.));
call symput('n_cvg',put(n_cvg, 8.));
stop;
run;
%put &n_codeset;


%if &n_codeset ~= 0 and &n_cvg ~= 0 %then %do;
	%dbdelete(code_val_general);
	%dbdelete(codeset);
	%dbload(codeset, rdbdata.codeset);
	%dbload(code_val_general, rdbdata.code_val_general);
	%end;
%else
	%put Check these two files: totalIDM.xls and imRDBmapping.xls.;
%mend loadCodeSet;
%loadCodeSet;





