/********************************************************************

	Confirmation_Method Dimension


*********************************************************************/

proc sort nodupkey data = nbs_ods.Confirmation_Method
	(keep = public_health_case_uid Confirmation_Method_cd)
	out=Confirmation_Method; 
	by public_health_case_uid Confirmation_Method_cd ; 
	*where Confirmation_Method_cd ~='NA';
run;

data Confirmation_Method2;
set Confirmation_Method;
	by public_health_case_uid Confirmation_Method_cd;
	length catCDs $30000;
	retain catCDs '';
		if first.public_health_case_uid then do;
			catCDs = '';
		end;
		catCDs = trim(catCDs) || Confirmation_Method_cd;
		if last.public_health_case_uid and last.Confirmation_Method_cd then do;
			output;
		end;
run;

proc sort nodupkey data=Confirmation_Method2 out = Confirmation_Method3(keep=catCDs);
	by catCDs ;
run;

data  rdbdata.Confirmation_Method_Group	(keep= Confirmation_Method_Grp_key)	
	Confirmation_Method4 (keep=Confirmation_Method_Grp_key catCDs) ;
	retain Confirmation_Method_Grp_key 0;
	set Confirmation_Method3;
	by catCDs ;
	
	if first.catCDs then do;
		Confirmation_Method_Grp_key +1;
		output  rdbdata.Confirmation_Method_Group;
	end;
	output Confirmation_Method4;

run;

proc sql;
create table Confirmation_method5 as 
select 	cm1.public_health_case_uid,
		cm1.Confirmation_Method_cd,
		cm4.Confirmation_Method_Grp_key,
		cvg.code_short_desc_txt		as Confirmation_Method_Desc

from	Confirmation_Method			as cm1
	left join Confirmation_Method2	as cm2
		on cm1.public_health_case_uid = cm2.public_health_case_uid
	left join Confirmation_Method4	as cm4
		on cm2.catCDs = cm4.catCDs
	
	left join nbs_srt.code_value_general	as cvg
			on	cm1.Confirmation_Method_cd = cvg.code
			and cvg.code_set_nm = 'PHC_CONF_M' 
;
quit;

	
data rdbdata.Confirmation_method 
	(keep = public_health_case_uid Confirmation_method_key Confirmation_Method_Grp_key Confirmation_Method_Desc);
set Confirmation_method5;
	Confirmation_method_key = _n_;
run;