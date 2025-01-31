/**********************************************************

	This program  creates SAS formats for these lookup:
		Unique Id to Srt Reference codeset
		Code to Code Desc Txt
		
		Unique Id to Rdb Column Names (not very reliable)

	Required Lib:	
		nbsfmt
		nbs_srt
		rdbdata
***********************************************************/
*libname im odbc dsn=im11 user=imviewer password=imviewer13;

/*******************************************
	Unique id to rdb column name lookup Format
	Example:
	BMD215 --> MIC_SIGN
********************************************/
data cd2col (rename=(cd=start col_nm=label));
set rdbdata.codeset (keep=cd col_nm);
fmtname = 'col_nm';
type='C';
run;
proc format lib =nbsfmt cntlin=cd2col; run;
/*******************************************
	CD to code set format name Lookup Format
	Example:
	cd = INV107 --> fmtname=$INV107f
********************************************/
data cd2CodeSet(keep=fmtname start label type code_set_nm) ;
format label $50. fmtname $8. type $1.;
set rdbdata.codeset (where=(code_set_nm ~=''));
	
	fmtname = 'typefmt';
	type='C';
		
	/*SAS V9 Changes: will allow long srt refernce name as format name*/
	*label = '$' || trim(srt_reference);

	select (cd);
		when(
			'INV153','BMD276', 'CRS080','CRS098','CRS164','CRS165',
			'DEM126','DEM132','DEM167','HEP140','HEP142','HEP242','HEP255','NPP024',
			'ORD116','RUB146'
			) label = '$COUNTRY';
		 /* some original unique id  too long */
		when ('OBS1201') label = '$OB1201f';
		when ('OBS1026') label = '$OB1026f';
		when ('OBS1710') label = '$OB1710f';
		/*when ('OBS1017') label = '$OB1017f';*/
		when ('CRS011af') label= '$CRS011a';
		when ('RUB091a') label='$RUB091a';
		when ('INV107','GEO100','LAB168','MRB137','OBS1017','PHC127') label='$JURCODE';
		when ('PER130','DEM142','DMH142') label='$P_LANG';
		when ('DEM153','DMH153','HEP256','PER408') label='$P_RACE';
		when ('PER126','DMH139','DEM139') label='$P_OCCUP';
		when (
			'CRS009',
			'CRS162',
			'DEM124',
			'DEM130',
			'DEM162',
			'DMH124',
			'DMH130',
			'DMH162',
			'INV117',
			'INV154',
			'LOC318',
			'LOC721',
			'NPH120',
			'NPP021',
			'ORD113'
		) label = '$ST_CCD';

		when (
			'CRS163',
			'DEM125',
			'DEM131',
			'DEM165',
			'DMH125',
			'DMH131',
			'DMH165',
			'INV119',
			'INV156',
			'INV187',
			'LOC309',
			'LOC712',
			'NOT111',
			'NPH122',
			'NPP023',
			'ORD115',
			'PHC144',
			'SUM100'
		) label = '$CNTYCCD';
		when ('HEP128','INV169','MRB121','PHC108','SUM106') label = '$DISEASE';
		otherwise label = '$' || trim(cd)|| 'f';
	end;
	rename cd=start;
run;
proc format lib =nbsfmt cntlin=cd2CodeSet; run;

%MACRO PROCESS_FORMAT(code_set_nm, csn, fmtName);
data &code_set_nm;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
format fmtname $10.;
	where code_set_nm = &csn;
	fmtname = &fmtName;
	rename code=start
		code_short_desc_txt=label;
run;

proc format lib=nbsfmt cntlin=&code_set_nm; run;
%MEND PROCESS_FORMAT;

%MACRO REVERSE_PROCESS_FORMAT(rev_code_set_nm, csn, fmtName);
data &rev_code_set_nm;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
format fmtname $10.;
	where code_set_nm = &csn;
	fmtname = &fmtName;
	rename code_short_desc_txt=start
		code=label;
run;

proc format lib=nbsfmt cntlin=&rev_code_set_nm; run;
%MEND PROCESS_FORMAT;
%REVERSE_PROCESS_FORMAT(REV_PRVDR_DIAGNOSIS_STD,'PRVDR_DIAGNOSIS_STD', '$R_NBS166F');


/*******************************************
	Code to Desc Lookup Format

	code='13'	---> desc='Georgia'
	Desc id from code_value_general 
********************************************/
proc sql;
create table cvg as 
select cvg.code_set_nm, 
	/*cvg.seq_num, */
	cvg.code, 
	cvg.code_short_desc_txt/*, 
	cs.code_system_cd, 
	cs.code_system_desc_txt*/
from nbs_srt.code_value_general cvg/*,
nbs_srt.codeset cs
where cvg.code_set_nm = cs.code_set_nm
	and cvg.seq_num=cs.seq_num*/
;
quit;

proc sort data=cvg nodupkey; by code_set_nm code; run;

proc sql;
create table CVGFMT as 
select 
	cd2CodeSet.label 	as fmtname	label='fmtname',
	cd2CodeSet.start	as cd		label='CD',
	cvg.code			as start,
	cvg.code_short_desc_txt as label 
from cd2CodeSet,cvg
where cd2CodeSet.code_set_nm = cvg.code_set_nm
and label <> '$COUNTRY'
and label <> '$ST_CCD'
order fmtname, cd, start;
quit;

proc format lib=nbsfmt cntlin=CVGFMT; run;


/*** Other Code to Desc Lookup*******/
/*
*/
proc sql;
create table rdbdata.cd_code_not_in_cvg as 
select label as fmtname,
		start as cd,
		code_set_nm 
from cd2CodeSet
where code_set_nm not in (select code_set_nm from cvg)
order by 3,2
;
quit;


proc sql;
select distinct code_set_nm from rdbdata.cd_code_not_in_cvg ;
quit;
/*Code Set Name = PSL_CNTRY 
INV153,BMD276, CRS080,CRS098,CRS164,CRS165,
DEM126,DEM132,DEM167,HEP140,HEP142,HEP242,HEP255,NPP024,
ORD116,RUB146 */

data country_code;
set nbs_srt.country_code(keep=code code_short_desc_txt);
format fmtname $8.;
	fmtname = '$COUNTRY';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=country_code; run;

/*154, Lookup*/
/* STATE_CCD
proc sql; select cd from rdbdata.codeset where code_set_nm='STATE_CCD'; quit;
'CRS009',
'CRS162',
'DEM124',
'DEM130',
'DEM162',
'DMH124',
'DMH130',
'DMH162',
'INV117',
'INV154',
'LOC318',
'LOC721',
'NPH120',
'NPP021',
'ORD113'

*/
data state_code;
set nbs_srt.state_code(keep=state_cd state_nm);
format fmtname $8.;
	fmtname = '$ST_CCD';
	rename state_cd=start
		state_nm=label;
run;
proc format lib=nbsfmt cntlin=state_code; run;



/*156, Lookup*/
/*
COUNTY_CCD
proc sql; select cd from rdbdata.codeset where code_set_nm='COUNTY_CCD'; quit;
'CRS163',
'DEM125',
'DEM131',
'DEM165',
'DMH125',
'DMH131',
'DMH165',
'INV119',
'INV156',
'INV187',
'LOC309',
'LOC712',
'NOT111',
'NPH122',
'NPP023',
'ORD115',
'PHC144',
'SUM100',

*/
data state_county_code_value;
set nbs_srt.state_county_code_value(keep=code code_desc_txt);
format fmtname $8.;
	fmtname = '$CNTYCCD';
	rename code=start
		code_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=state_county_code_value; run;



/* Jurisdiction Code To Desc Lookup*/
data Jurisdiction_code;
set nbs_srt.Jurisdiction_code(keep=code code_short_desc_txt);
format fmtname $8.;
	fmtname = '$JURCODE';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=Jurisdiction_code; run;


data Condition_code;
set nbs_srt.Condition_code(keep=condition_cd condition_short_nm);
format fmtname $8.;
	fmtname = '$DISEASE';
	rename condition_cd=start
		condition_short_nm=label;
run;
proc format lib=nbsfmt cntlin=Condition_code; run;

data Program_area_code;
set nbs_srt.Program_area_code(keep=prog_area_cd prog_area_desc_txt);
format fmtname $8.;
	fmtname = '$PAMCODE';
	rename prog_area_cd=start
		prog_area_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=Program_area_code; run;


data Treatment_code;
set nbs_srt.Treatment_code(keep=treatment_cd treatment_desc_txt);
format fmtname $8.;
	fmtname = '$RXDRUG';
	rename treatment_cd=start
		treatment_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=Treatment_code; run;


data Language_code;
set nbs_srt.Language_code(keep=code code_short_desc_txt);
format fmtname $8.;
	fmtname = '$P_LANG';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=Language_code; run;



data Occupation_code;
set nbs_srt.Occupation_code(keep=code code_short_desc_txt);
format fmtname $8.;
	fmtname = '$P_OCCUP';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=Occupation_code; run;

data pat_Occupation_code;
set nbs_srt.Naics_industry_code(keep=code code_short_desc_txt);
format fmtname $8.;
	fmtname = '$PT_OCUP';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=pat_Occupation_code; run;

data Race_code;
set nbs_srt.Race_code(keep=code code_short_desc_txt);
format fmtname $8.;
	fmtname = '$P_RACE';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=Race_code; run;
/*** Commented by Amit. It is not needed for lab_test. Causing defect when duplicate lab_test_cd exist in 
     lab_test table in the SRT database. 

proc sort nodupkey data=nbs_srt.Lab_test(keep=Lab_test_cd Lab_test_desc_txt)
		out=Lab_Test; by Lab_test_cd Lab_test_desc_txt; run;
data Lab_Test;
set Lab_Test;
format fmtname $8.;
	fmtname = '$LABTEST';
	rename Lab_test_cd=start
		Lab_test_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=Lab_Test; run;
*/


proc sort nodupkey data=nbs_srt.Lab_result(keep=Lab_result_cd Lab_result_desc_txt)
		out=Lab_result; by Lab_result_cd; run;
data Lab_result;
set Lab_result;
format fmtname $8.;
	where Lab_result_cd not in ('LOW','HIGH');
	fmtname = '$LABRSLT';
	rename Lab_result_cd=start
		Lab_result_desc_txt=label;

run;
proc format lib=nbsfmt cntlin=Lab_result; run;

/* V9 allows longer format names, so we can use code_set_nm as format name like this*/
data ei_rl_el_type;
set nbs_srt.code_value_general(keep=code_set_nm code code_desc_txt);
format fmtname $8.;
	where code_set_nm in ('EI_TYPE','RL_TYPE','EL_TYPE');
	fmtname = '$'||left(trim(code_set_nm));
	rename code=start
		code_desc_txt=label;
	type='C';
run;
proc format lib=nbsfmt cntlin=ei_rl_el_type; run;

data rl_class;
set nbs_srt.code_value_general(keep=code_set_nm code code_desc_txt);
format fmtname $8.;
	where code_set_nm = 'RL_CLASS';
	fmtname = '$RLCLASS';
	rename code=start
		code_desc_txt=label;
	type='C';
run;
proc format lib=nbsfmt cntlin=rl_class; run;
data S_JURDIC_C;
set nbs_srt.jurisdiction_code(keep=code_set_nm code code_desc_txt);
format fmtname $8.;
	where code_set_nm = 'S_JURDIC_C';
	fmtname = '$JURD_CD';
	rename code=start
		code_desc_txt=label;
run; 
proc format lib=nbsfmt cntlin=S_JURDIC_C; run;
data S_PROGRA_C;
set nbs_srt.program_area_code(keep=code_set_nm prog_area_cd prog_area_desc_txt);
	format 
		fmtname $8.;
	fmtname = '$PRG_AR';
	rename 
		prog_area_cd=start
		prog_area_desc_txt=label;  
run;
proc format lib=nbsfmt cntlin=S_PROGRA_C; run;

data YNU;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
	format 
		fmtname $8.;
	where 
		code_set_nm = 'YNU';
		fmtname = '$C_YNU';
	rename 
		code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=YNU; run;
data YN;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
	format 
		fmtname $8.;
	where 
		code_set_nm = 'YN';
		fmtname = '$C_YN';
	rename 
		code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=YN; run;

data STD_CONTACT_RCD_PROCESSING_DEC;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
	format 
		fmtname $8.;
	where 
		code_set_nm = 'STD_CONTACT_RCD_PROCESSING_DECISION';
		fmtname = '$C_STD_P';
	rename 
		code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=STD_CONTACT_RCD_PROCESSING_DEC; run;


data PHC_IN_STS;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
	format 	
		fmtname $8.;
	where 
		code_set_nm = 'PHC_IN_STS';
		fmtname = '$C_PH_STS';
	rename 
		code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=PHC_IN_STS; run;
data NBS_RELATIONSHIP;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
format fmtname $8.;
	where code_set_nm = 'NBS_RELATIONSHIP';
	fmtname = '$C_RLTN';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=NBS_RELATIONSHIP; run;
data NBS_PRIORITY;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
retain hlo 'UJ';
format fmtname $8.;
	where code_set_nm = 'NBS_PRIORITY';
	fmtname = '$C_PRTY';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=NBS_PRIORITY; run;

data NBS_NO_TRTMNT_REAS;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
format fmtname $8.;
	where code_set_nm = 'NBS_NO_TRTMNT_REAS';
	fmtname = '$C_NT_RS';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=NBS_NO_TRTMNT_REAS; run;


data NBS_HEALTH_STATUS;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
format fmtname $8.;
	where code_set_nm = 'NBS_HEALTH_STATUS';
	fmtname = '$C_H_STS';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=NBS_HEALTH_STATUS; run;
data C_DISPO;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
format fmtname $8.;
	where code_set_nm in( 'NBS_DISPO', 'FIELD_FOLLOWUP_DISPOSITION_STD');
	fmtname = '$C_DISPO';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=C_DISPO; run;

data INT_TYPE;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
format fmtname $8.;
	where code_set_nm = 'NBS_INTERVIEW_TYPE_STDHIV';
	fmtname = '$ITYPE';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=INT_TYPE; run;
data INT_LOC;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
format fmtname $8.;
	where code_set_nm in ('NBS_INTVW_LOC','NBS_INTVW_LOC_STDHIV');
	fmtname = '$ILOC';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=INT_LOC; run;

data INTERVIEW_STATUS;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
format fmtname $8.;
	where code_set_nm = 'NBS_INTVW_STATUS';
	fmtname = '$ISTATUS';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=INTERVIEW_STATUS; run;

data INTVWEE_ROLE;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
format fmtname $8.;
	where code_set_nm = 'NBS_INTVWEE_ROLE';
	fmtname = '$IROLE';
	rename code=start
		code_short_desc_txt=label;
run;
proc format lib=nbsfmt cntlin=INTVWEE_ROLE; run;
%PROCESS_FORMAT(FIELD_FOLLOWUP_DISPOSITION_SH,'FIELD_FOLLOWUP_DISPOSITION_STDHIV', '$NBS173F');
%PROCESS_FORMAT(INITIATING_AGENCY,'INITIATING_AGENCY', '$CM_I_AG');
%PROCESS_FORMAT(INTERNET_FOLLOWUP_OUTCOME,'INTERNET_FOLLOWUP_OUTCOME', '$CM_I_OC');
%PROCESS_FORMAT(NOTIFIABLE,'NOTIFIABLE', '$CM_NOTF');
%PROCESS_FORMAT(NOTIFICATION_ACTUAL_METHOD_STD,'NOTIFICATION_ACTUAL_METHOD_STD', '$CM_N_MTD');
%PROCESS_FORMAT(NOTIFICATION_PLAN,'NOTIFICATION_PLAN', '$CM_NP');
%PROCESS_FORMAT(OOJ_AGENCY,'OOJ_AGENCY', '$CM_OOJA');
%PROCESS_FORMAT(OOJ_AGENCY_LOCAL,'OOJ_AGENCY_LOCAL', '$CM_OOJL');
%PROCESS_FORMAT(PAT_INTVW_STATUS,'PAT_INTVW_STATUS', '$CM_INTS');
%PROCESS_FORMAT(PRVDR_CONTACT_OUTCOME,'PRVDR_CONTACT_OUTCOME', '$CM_P_OC');
%PROCESS_FORMAT(PRVDR_DIAGNOSIS_STD,'PRVDR_DIAGNOSIS_STD', '$NBS166F');
%PROCESS_FORMAT(PRVDR_EXAM_REASON,'PRVDR_EXAM_REASON', '$CM_P_RS');
%PROCESS_FORMAT(STATUS_900,'STATUS_900', '$CM_900S'); 
%PROCESS_FORMAT(SCILN_PROC_DECISION,'STD_CREATE_INV_LABMORB_NONSYPHILIS_PROC_DECISION', '$CM_CIND');
%PROCESS_FORMAT(SURVEILLANCE_PATIENT_FOLLOWUP,'SURVEILLANCE_PATIENT_FOLLOWUP', '$CM_S_PF');
%PROCESS_FORMAT(YN,'YN', '$YN');
%PROCESS_FORMAT(PLACE_TYPE,'PLACE_TYPE', '$PL_TPE');
%PROCESS_FORMAT(NBS_CONSYPH_VITAL, 'NBS_CONSYPH_VITAL', '$CSRT_VT');
%PROCESS_FORMAT(NBS_CONSYPH_CLASS, 'NBS_CONSYPH_CLASS', '$CSPT_CL');
%PROCESS_FORMAT(P_ETHN_UNK_REASON, 'P_ETHN_UNK_REASON', '$ETHN_UN');
%PROCESS_FORMAT(SEX_UNK_REASON, 'SEX_UNK_REASON', '$UNK_SEX');
%PROCESS_FORMAT(NBS_STD_GENDER_PARPT, 'NBS_STD_GENDER_PARPT', '$GENPARPT');
%PROCESS_FORMAT(YNU, 'YNU', '$YNU');
%PROCESS_FORMAT(CM_PROCESS_STAGE, 'CM_PROCESS_STAGE', '$NBS115NF');
%PROCESS_FORMAT(REFERRAL_BASIS, 'REFERRAL_BASIS', '$NBS110NF');
%PROCESS_FORMAT(STD_NBS_PROCESSING_DECISION_ALL, 'STD_NBS_PROCESSING_DECISION_ALL', '$APROCDNF');
%PROCESS_FORMAT(NBS_GROUP_NM, 'NBS_GROUP_NM', '$CON127F');
%PROCESS_FORMAT(VAC147F, 'PHVS_VACCINEEVENTINFORMATIONSOURCE_NND', '$VAC147F');
%PROCESS_FORMAT(AGE_UNIT, 'AGE_UNIT', '$VAC106F');
%PROCESS_FORMAT(NIP_ANATOMIC_ST, 'NIP_ANATOMIC_ST', '$VAC104F');
%PROCESS_FORMAT(VAC_NM, 'VAC_NM', '$VAC101F');
%PROCESS_FORMAT(VAC_MFGR, 'VAC_MFGR', '$VAC107F');

%REVERSE_PROCESS_FORMAT(REV_PRVDR_DIAGNOSIS_STD,'PRVDR_DIAGNOSIS_STD', '$R_NBS166F');
%REVERSE_PROCESS_FORMAT(CASE_DIAGNOSIS,'CASE_DIAGNOSIS', '$R_NBS136F');
%REVERSE_PROCESS_FORMAT(YN,'YN', '$R_YNF');

%PROCESS_FORMAT(EL_USE_TELE_PLC,'EL_USE_TELE_PLC', '$U_TEL_PLC');
%PROCESS_FORMAT(EL_TYPE_TELE_PLC,'EL_TYPE_TELE_PLC', '$T_TEL_PLC');



%MACRO PROCESS_FORMAT(code_set_nm, csn1,csn2,fmtName);
data &code_set_nm;
set nbs_srt.code_value_general(keep=code_set_nm code code_short_desc_txt);
format fmtname $10.;
	where code_set_nm = &csn1;
	fmtname = &fmtName;
	rename code=start
		code_short_desc_txt=label;
	where code_set_nm = &csn2;
	fmtname = &fmtName;
	rename code=start
		code_short_desc_txt=label;
run;

proc format lib=nbsfmt cntlin=&code_set_nm; run;
%MEND PROCESS_FORMAT;

