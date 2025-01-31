/*race transform*/
proc sql noprint;
	create table race as 
	select 
		distinct 
		person_uid,
		race_category_cd,
		code_short_desc_txt as race_desc_txt label='Race Category Desc'
	from 
		nbs_ods.person_race 	as PER,
		nbs_srt.Race_code		as RAC
	where 
		PER.race_cd = PER.race_category_cd /*concatenate only category cd and desc*/
			and
		PER.race_cd = RAC.code
			and
		RAC.parent_is_cd = 'ROOT'
			and
		RAC.code_set_nm	= 'P_RACE_CAT'
	; 
		
quit;

/*Retreive Legacy NETSS Race*/
PROC SQL NOPRINT;
	CREATE TABLE RACE1 AS 
	SELECT 		
		distinct 
		person_uid,
		race_category_cd,
		code_short_desc_txt as race_desc_txt label='Race Category Desc'
	FROM
		nbs_ods.person_race 	as PER,
		nbs_srt.Race_code		as RAC
	WHERE race_cd = 'ROOT' 
		AND per.RACE_CATEGORY_CD = rac.code;  
QUIT;
/*COMBINE RACE1 INTO RACE DATA SET*/
PROC APPEND BASE=RACE DATA=RACE1;
RUN;

PROC SORT DATA = RACE;
BY person_uid race_category_cd race_desc_txt; 
QUIT;
	
/*
proc sort data=nbs_ods.person_race
	(where=(race_cd = race_category_cd))				 

	out=race(keep=person_uid race_category_cd race_desc_txt) nodupkey; 
	by person_uid race_category_cd race_desc_txt;
run;
*/

/*keep only first five race values and put them in five columns*/
data phcpersonrace(rename=(
			race6=race_concatenated_txt
			race7=race_concatenated_desc_txt)
			keep=person_uid race6 race7
			);
	set race;
	by	person_uid;
	format race1-race5 $20. race6 $100. race7 $500.;
	array race(5) race1-race5;
	retain race1-race7 ' ' i 0;

	if first.person_uid then do;
		do j=1 to 5; race(j) = ' ';	end;
		i = 0; race6 = ''; race7='';
		end;
	i+1;
	if i <= 5 then do;
		race(i) = race_category_cd;
		race6 =left(trim(race_category_cd)) || left(trim(race6))  ;
		race7 =left(trim(race_desc_txt))		|| left(trim(race7)) ; 
		end;
	if last.person_uid then output;
run;

