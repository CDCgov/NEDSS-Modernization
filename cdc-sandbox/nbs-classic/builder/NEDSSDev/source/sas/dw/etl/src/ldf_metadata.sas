proc sql;
create table ldf_meta_data  as 
	select     
		a.ldf_uid, 
		a.active_ind, 
		a.business_object_nm, 
		a.cdc_national_id, 
		a.class_cd, 
		a.code_set_nm, 
		a.condition_cd, 
		a.label_txt, 
		a.state_cd, 
		a.custom_subform_metadata_uid, 
		page_set.code_short_desc_txt as page_set 'page_set'
	from          
		nbs_ods.state_defined_field_metadata as a 
	left outer join
		nbs_srt.ldf_page_set as page_set 
	on 
		page_set.ldf_page_id =a.ldf_page_id
	where a.business_object_nm in ('PHC','BMD','NIP', 'HEP','PAT')
and a.data_type in ('ST', 'CV', 'LIST_ST')
and (a.condition_cd in (select condition_cd from nbs_rdb.ldf_datamart_table_ref)
Or a.condition_cd is null )
order by a.business_object_nm;
Quit;
data ldf_meta_data;
set ldf_meta_data;
	if business_object_nm= 'BMD' and lengthn(trim(condition_cd))<2 then LDF_PAGE_SET='BMIRD';
	if business_object_nm= 'NIP' and lengthn(trim(condition_cd))<2 then LDF_PAGE_SET='VPD';
	if business_object_nm= 'PHC' and lengthn(trim(condition_cd))<2 then LDF_PAGE_SET='OTHER';
	if business_object_nm= 'HEP' and lengthn(trim(condition_cd))<2 then LDF_PAGE_SET='HEP';
run;

proc sql;
create table 
ldf_metadata 
as select  
ldf_uid, label_txt, cdc_national_id, condition_cd, class_cd,custom_subform_metadata_uid,LDF_PAGE_SET, business_object_nm
from ldf_meta_data
order by cdc_national_id, ldf_uid, label_txt;
quit;
proc sql;
create table ldf_metadata_n
as select a.cdc_national_id,label_txt as ldf_label 'ldf_label', a.ldf_uid,a.condition_cd,a.class_cd,custom_subform_metadata_uid,LDF_PAGE_SET, business_object_nm
from ldf_meta_data a where ldf_uid not in (select ldf_uid from nbs_rdb.ldf_datamart_column_ref);
quit;
%macro assign_nozero_key (ds, key);
 data &ds;
  set &ds;
	&key+1;
	output;
 run;
%mend assign_nozero_key;
 %assign_nozero_key (ldf_metadata_n, ldf_datamart_column_ref_uid);
data ldf_metadata_new;
set ldf_metadata_n;
length datamart_column_nm  $30;
ldfchar=put(ldf_uid,8.);
datamart_column_nm = tranwrd(trim(ldf_label), " ", "_");
len= length(compress(datamart_column_nm) );
lencdcnatid=length(compress(cdc_national_id));
lenLDFid=length(compress(ldfchar));
lenCSid=length(compress(custom_subform_metadata_uid));

if class_cd='STATE' then  datamart_column_nm = compress("L_"||''||trim(ldfchar)||''|| trim(datamart_column_nm) ) ;
ELSE if (lencdcnatid>1 and (lencdcnatid+ len) and lenCSid>1 ) then datamart_column_nm= compress("C_"||''||cdc_national_id||''||datamart_column_nm );
else if class_cd='CDC' then datamart_column_nm= compress(trim("C_"||''||trim((ldfchar))||''||datamart_column_nm));
datamart_column_nm = substr(datamart_column_nm ,1,29);
run;
%dbload (ldf_datamart_column_ref, ldf_metadata_new);
