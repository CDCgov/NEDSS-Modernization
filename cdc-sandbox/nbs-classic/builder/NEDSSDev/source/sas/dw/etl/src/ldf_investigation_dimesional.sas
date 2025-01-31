proc sql;
create table ldf_data  as 
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
		b.ldf_uid,
		b.business_object_uid, 
		b.ldf_value,
		page_set.code_short_desc_txt as page_set 'page_set',
		phc.cd as phc_cd 'phc_cd'
	from          
		nbs_ods.state_defined_field_metadata as a 
	inner join
		nbs_ods.state_defined_field_data as b 
	on 
		a.ldf_uid = b.ldf_uid
	inner join
		nbs_srt.ldf_page_set as page_set 
	on 
		page_set.ldf_page_id =a.ldf_page_id
	inner join
		nbs_ods.public_health_case as phc
	on 
		phc.public_health_case_uid=b.business_object_uid	
	where a.business_object_nm in ('PHC','BMD','NIP', 'HEP','PAT')
	and a.data_type in ('ST', 'CV','LIST_ST')
	and phc.cd in (select condition_cd from nbs_rdb.ldf_datamart_table_ref);

create table
		ldf_data_with_source as 
	select 
		codeset.class_cd as data_source,* 
	from 
		ldf_data 
	left outer join  
		nbs_srt.codeset codeset 
	on 
		ldf_data.code_set_nm=codeset.code_set_nm
order by condition_cd;
quit;

data ldf_data_translated;
set ldf_data_with_source;
	array ldf_data(100) $200 ldf_data1-ldf_data100;
	do i=1 to 100;
		ldf_data{i}=scan(ldf_value,i,'|');
    end;    
run;    
proc sort 
	data=ldf_data_translated; 
	by business_object_uid ldf_uid code_set_nm label_txt data_source class_cd; 
run;
data ldf_data_translated;
set ldf_data_translated( drop= ldf_value);
run;    
proc transpose data=  ldf_data_translated out=  ldf_data_translated_rows;
    by   business_object_uid ldf_uid code_set_nm label_txt data_source;
    copy cdc_national_id code_set_nm label_txt ldf_uid business_object_nm   condition_cd  
                       custom_subform_metadata_uid page_set  phc_cd
                       ldf_uid  class_cd;    
		var ldf_data1 ldf_data2 ldf_data3 ldf_data4 ldf_data5 ldf_data6 ldf_data7 ldf_data8 ldf_data9 
		ldf_data10 ldf_data11 ldf_data12 ldf_data13 ldf_data14 ldf_data15 ldf_data16 ldf_data17 ldf_data18 
		ldf_data19 ldf_data20 ldf_data21 ldf_data22 ldf_data23 ldf_data24 ldf_data25 ldf_data26 ldf_data27 
		ldf_data28 ldf_data29 ldf_data30 ldf_data31 ldf_data32 ldf_data33 ldf_data34 ldf_data35 ldf_data36 
		ldf_data37 ldf_data38 ldf_data39 ldf_data40 ldf_data41 ldf_data42 ldf_data43 ldf_data44 ldf_data45 
		ldf_data46 ldf_data47 ldf_data48 ldf_data49 ldf_data50 ldf_data51 ldf_data52 ldf_data53 ldf_data54 
		ldf_data55 ldf_data56 ldf_data57 ldf_data58 ldf_data59 ldf_data60 ldf_data61 ldf_data62 ldf_data63 
		ldf_data64 ldf_data65 ldf_data66 ldf_data67 ldf_data68 ldf_data69 ldf_data70 ldf_data71 ldf_data72 
		ldf_data73 ldf_data74 ldf_data75 ldf_data76 ldf_data77 ldf_data78 ldf_data79 ldf_data80 ldf_data81 
		ldf_data82 ldf_data83 ldf_data84 ldf_data85 ldf_data86 ldf_data87 ldf_data88 ldf_data89 ldf_data90 
		ldf_data91 ldf_data92 ldf_data93 ldf_data94 ldf_data95 ldf_data96 ldf_data97 ldf_data98 ldf_data99 
		/*ldf_data100 ldf_data101 ldf_data102 ldf_data103 ldf_data104 ldf_data105 ldf_data106 ldf_data107 
		ldf_data108 ldf_data109 ldf_data110 ldf_data111 ldf_data112 ldf_data113 ldf_data114 ldf_data115 
		ldf_data116 ldf_data117 ldf_data118 ldf_data119 ldf_data120 ldf_data121 ldf_data122 ldf_data123 
		ldf_data124 ldf_data125 ldf_data126 ldf_data127 ldf_data128 ldf_data129 ldf_data130 ldf_data131 
		ldf_data132 ldf_data133 ldf_data134 ldf_data135 ldf_data136 ldf_data137 ldf_data138 ldf_data139 
		ldf_data140 ldf_data141 ldf_data142 ldf_data143 ldf_data144 ldf_data145 ldf_data146 ldf_data147 
		ldf_data148 ldf_data149 ldf_data150 ldf_data151 ldf_data152 ldf_data153 ldf_data154 ldf_data155 
		ldf_data156 ldf_data157 ldf_data158 ldf_data159 ldf_data160 ldf_data161 ldf_data162 ldf_data163 
		ldf_data164 ldf_data165 ldf_data166 ldf_data167 ldf_data168 ldf_data169 ldf_data170 ldf_data171 
		ldf_data172 ldf_data173 ldf_data174 ldf_data175 ldf_data176 ldf_data177 ldf_data178 ldf_data179 
		ldf_data180 ldf_data181 ldf_data182 ldf_data183 ldf_data184 ldf_data185 ldf_data186 ldf_data187 
		ldf_data188 ldf_data189 ldf_data190 ldf_data191 ldf_data192 ldf_data193 ldf_data194 ldf_data195 
		ldf_data196 ldf_data197 ldf_data198 ldf_data199 ldf_data200 ldf_data201 ldf_data202 ldf_data203 
		ldf_data204 ldf_data205 ldf_data206 ldf_data207 ldf_data208 ldf_data209 ldf_data210 ldf_data211 
		ldf_data212 ldf_data213 ldf_data214 ldf_data215 ldf_data216 ldf_data217 ldf_data218 ldf_data219 
		ldf_data220 ldf_data221 ldf_data222 ldf_data223 ldf_data224 ldf_data225 ldf_data226 ldf_data227 
		ldf_data228 ldf_data229 ldf_data230 ldf_data231 ldf_data232 ldf_data233 ldf_data234 ldf_data235 
		ldf_data236 ldf_data237 ldf_data238 ldf_data239 ldf_data240 ldf_data241 ldf_data242 ldf_data243 
		ldf_data244 ldf_data245 ldf_data246 ldf_data247 ldf_data248 ldf_data249 ldf_data250*/;
run;
proc sql;
	create table 
		ldf_data_translated_rows_ne as/*non empty data*/
	select 
		* 
	from 
		ldf_data_translated_rows 
	where 
		page_set 
	is not null;
quit;

proc sql;
create table 
	ldf_base_coded_translated as 
	select 	
		col1,   
		cvg.code_desc_txt as code_short_desc_txt, 
		ldf.code_set_nm,
		business_object_uid, 
		ldf_uid,
		class_cd,
		label_txt,
		cdc_national_id,
		business_object_nm,
		condition_cd,
		data_source,
		custom_subform_metadata_uid,
		page_set,
		phc_cd
	from
		ldf_data_translated_rows_ne ldf
	left join 
		nbs_srt.code_value_general cvg
	on
		cvg.code_set_nm=ldf_data_translated_rows_ne.code_set_nm
	and 
		cvg.code=ldf_data_translated_rows_ne.col1
	and 
		ldf_data_translated_rows_ne.data_source='code_value_general'
	order by 
		business_object_uid, ldf_uid, col1;
quit;

data ldf_base_coded_translated;
	set ldf_base_coded_translated;
	if 
		lengthn(code_short_desc_txt)>0 
	then 
		col1= code_short_desc_txt;
	else
		col1= col1;
run;
proc sql;
create table ldf_base_clinical_translated as 
	select 	
		col1, 
		cvg.code_desc_txt as code_short_desc_txt, 
		ldf.code_set_nm,
		business_object_uid, 
		ldf_uid,
		class_cd,
		ldf.code_set_nm,
		label_txt,
		data_source,
		cdc_national_id,
		business_object_nm,
		condition_cd,
		custom_subform_metadata_uid,
		page_set,
		phc_cd
	from	
		ldf_base_coded_translated ldf
		left join nbs_srt.code_value_clinical cvg
		on cvg.code_set_nm=ldf.code_set_nm
		and cvg.code=ldf.col1
		and ldf.data_source='code_value_clinical'
	order by business_object_uid, col1;
quit;
data ldf_base_clinical_translated;
set ldf_base_clinical_translated;
	if lengthn(code_short_desc_txt)>0 then col1= code_short_desc_txt;
	else col1= col1;
run;
proc sql;
create table ldf_base_state_translated as 
	select 	
		col1,  
		business_object_uid, 
		cvg.code_desc_txt as code_short_desc_txt, 
		ldf_uid,
		class_cd,
		ldf.code_set_nm,
		label_txt,
		data_source,
		cdc_national_id,
		business_object_nm,
		condition_cd,
		custom_subform_metadata_uid,
		page_set,
		phc_cd
	from	
		ldf_base_clinical_translated ldf
		left outer join nbs_srt.v_state_code cvg
	on 
		cvg.code_set_nm=ldf.code_set_nm
		and cvg.state_cd=ldf.col1
		and ldf.data_source 
	in 
		('STATE_CCD', 'V_state_code')
	order by 
		business_object_uid, col1;
quit;
data  ldf_base_state_translated;
set  ldf_base_state_translated;
	if lengthn(code_short_desc_txt)>0 then col1= code_short_desc_txt;
	else col1= col1;
run;
proc sql;
create table ldf_base_country_translated as 
select 
		col1,  
		business_object_uid, 
		cvg.code_desc_txt as code_short_desc_txt, 
		ldf_uid,
		class_cd,
		ldf.code_set_nm,
		label_txt,
		data_source,
		cdc_national_id,
		business_object_nm,
		condition_cd,
		custom_subform_metadata_uid,
		page_set,
		phc_cd
        from	
			ldf_base_state_translated ldf
	    	left outer join nbs_srt.country_code cvg
		on cvg.code_set_nm=ldf.code_set_nm
		and cvg.code=ldf.col1
		and ldf.data_source in ('COUNTRY_CODE')
	order by 
			business_object_uid, col1;
quit;
data ldf_base_country_translated;
set ldf_base_country_translated;
	if lengthn(code_short_desc_txt)>0 then col1= code_short_desc_txt;
	else col1= col1;
run;
proc sort data= ldf_base_country_translated; by business_object_uid label_txt; 
run;
data  ldf_base_country_translated; 
length x $4000; 
length col1 $4000; 
	set  ldf_base_country_translated; by business_object_uid label_txt; 
	retain x; 
	if  first.label_txt then x=' '; x=catx(' | ',x,col1); 
	if last.label_txt; 
	if lengthn(x)>0 then col1=x;
run; 
proc sort data= ldf_base_country_translated; by label_txt  cdc_national_id ldf_uid class_cd; 
run;

proc sql;
	create table ldf as
		select * from ldf_base_country_translated a,
		nbs_rdb.ldf_datamart_column_ref b
		where a.ldf_uid= b.ldf_uid;
quit;
proc sql;
	create table ldf_translated_data as
	select 
		col1,  
		a.business_object_uid, 
		a.code_short_desc_txt, 
		a.code_set_nm,
		a.data_source,
		a.phc_cd,
		b.ldf_uid,
		b.cdc_national_id,
		b.business_object_nm,
		b.condition_cd,
		b.custom_subform_metadata_uid,
		b.page_set,
		b.ldf_uid,
		b.label_txt,
		LDF_PAGE_SET
	from 
		ldf_meta_data b left outer join ldf_base_country_translated a  
	on 
		a.ldf_uid= b.ldf_uid
	order by 
		label_txt,page_set, business_object_uid, ldf_uid, code_set_nm,  data_source;

	delete * from 
		ldf_translated_data 
	where 
		ldf_uid is null;
quit;
PROC SQL;
create table ldf_dimensional_data as
select 
		col1,  
		business_object_uid AS INVESTIGATION_UID 'INVESTIGATION_UID', 
		dim.code_short_desc_txt, 
		dim.ldf_uid,
		dim.code_set_nm,
		dim.label_txt,
		dim.data_source,
		dim.cdc_national_id,
		dim.business_object_nm,
		dim.condition_cd,
		dim.custom_subform_metadata_uid,
		dim.page_set,
		ref.datamart_column_nm as datamart_column_nm1 'datamart_column_nm1',
		ref2.datamart_column_nm as datamart_column_nm2 'datamart_column_nm2',
		dim.phc_cd
	from 
		ldf_translated_data dim 
	left outer join
		nbs_rdb.ldf_datamart_column_ref  ref 
	on 
		dim.ldf_uid  =ref.ldf_uid
	left outer join 
		nbs_rdb.ldf_datamart_column_ref  ref2 
	on
		dim.cdc_national_id  =ref2.cdc_national_id
	and 
		ref2.ldf_uid is null
	and 
		ref.cdc_national_id is null
order by business_object_uid;
quit;
proc datasets library = work nolist;
delete 
	Ldf_translated_data;
run;
quit;
data ldf_dimensional_data;
set ldf_dimensional_data;
  if length(datamart_column_nm1)<2 then datamart_column_nm=datamart_column_nm2;
  else datamart_column_nm= datamart_column_nm1;
run;
