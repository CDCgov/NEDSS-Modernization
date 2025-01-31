/*Confirmation Date*/
/*proc sql;
	create table confirm as 
	select	public_health_case_uid,
			confirmation_method_time,
			confirmation_method_cd,
			confirmation_method_desc_txt
	from nbs_ods.confirmation_method
	where confirmation_method_time ~= .
	order by public_health_case_uid,confirmation_method_time;
quit;
*/
/*Updated to prevent duplicate case data due to mismatching confirmation_method_time*/
proc sql;
create table confirm as 
SELECT Confirmation_method.public_health_case_uid, 
       confirmation_method_cd, 
       a.confirmation_method_time
FROM
(
    SELECT Confirmation_method.public_health_case_uid, 
           MAX(confirmation_method_time) AS confirmation_method_time
    FROM nbs_ods.Confirmation_Method
    GROUP BY Confirmation_method.public_health_case_uid
) a
INNER JOIN nbs_ods.Confirmation_method ON a.public_health_case_uid = Confirmation_method.public_health_case_uid
where a.confirmation_method_time ~= .
GROUP BY Confirmation_method.public_health_case_uid, 
         confirmation_method_cd, 
         a.confirmation_method_time;
quit;

data Phcconfirmation;
	set	confirm (keep = public_health_case_uid
		confirmation_method_time confirmation_method_cd);
	by 	public_health_case_uid confirmation_method_time;
	if  first.confirmation_method_time then output;
run;
