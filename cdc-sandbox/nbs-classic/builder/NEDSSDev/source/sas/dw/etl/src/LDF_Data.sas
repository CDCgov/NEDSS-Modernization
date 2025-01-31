/**********************************/
/*** LDF ***/
/**********************************/

/* join State Defined Metadata and Data tables */
Proc SQL;
 Create table LDF_Data1 as
  Select distinct m.*, d.*
   From nbs_ods.State_Defined_Field_MetaData m,
        nbs_ods.State_Defined_Field_Data d,
		nbs_ods.public_health_case p
   Where m.ldf_uid = d.ldf_uid
   		and d.business_object_uid=p.public_health_case_uid
Union
  Select m.*, d.*
   From nbs_ods.State_Defined_Field_MetaData m,
        nbs_ods.State_Defined_Field_Data d,
		nbs_ods.observation o
   Where m.ldf_uid = d.ldf_uid
   		and d.business_object_uid=o.observation_uid
Union
  Select m.*, d.*
   From nbs_ods.State_Defined_Field_MetaData m,
        nbs_ods.State_Defined_Field_Data d,
		nbs_ods.intervention i
   Where m.ldf_uid = d.ldf_uid
   		and d.business_object_uid=i.intervention_uid
Union
  Select m.*, d.*
   From nbs_ods.State_Defined_Field_MetaData m,
        nbs_ods.State_Defined_Field_Data d,
		nbs_ods.organization i
   Where m.ldf_uid = d.ldf_uid
   		and d.business_object_uid=i.organization_uid
Union
  Select m.*, d.*
   From nbs_ods.State_Defined_Field_MetaData m,
        nbs_ods.State_Defined_Field_Data d,
		nbs_ods.person p
   Where m.ldf_uid = d.ldf_uid
   		and d.business_object_uid=p.person_uid
		and p.person_uid<>p.person_parent_uid
		and p.cd='PAT'
Union
  Select m.*, d.*
   From nbs_ods.State_Defined_Field_MetaData m,
        nbs_ods.State_Defined_Field_Data d,
		nbs_ods.person p
   Where m.ldf_uid = d.ldf_uid
   		and d.business_object_uid=p.person_uid
		and p.cd='PRV';
Quit;

/**************************/
/* this can be replaced with a format */
/* subset Code_Value General for LDF */
Data cvg (keep=code code_desc_txt);
 Set nbs_srt.Code_Value_General;
 Where code_set_nm = 'LDF_DATA_TYPE';
Run;

/* join LDF_Data to Code_Value_General to lookup data type code */
Proc SQL;
 Create table LDF_Data as
  Select ldf.*, cvg.code_desc_txt as LDF_COLUMN_TYPE
   From LDF_Data1 ldf LEFT JOIN cvg
     On ldf.data_type = cvg.code
    Order By ldf.business_object_uid, ldf.display_order_nbr;
Quit;


/* create LDF_Group and LDF_Data tables */
/* assign LDF group and LDF data keys */
Data LDF_Group (keep=LDF_GROUP_KEY business_object_uid) 
     LDF_Data (keep=LDF_DATA_KEY LDF_GROUP_KEY LDF_VALUE LDF_COLUMN_TYPE CONDITION_CD CONDITION_DESC_TXT 
                            CDC_NATIONAL_ID CLASS_CD CODE_SET_NM BUSINESS_OBJ_NM DISPLAY_ORDER_NUMBER FIELD_SIZE
							LABEL_TXT IMPORT_VERSION_NBR LDF_OID NND_IND
							)
;
  Retain LDF_Group_Key 1 
         LDF_Data_Key 1; 
  If LDF_Group_Key=1 then do;  /* create initial null record for both tables */
    output LDF_Group;
    output LDF_Data;
  end;

  Set LDF_Data (rename=(BUSINESS_OBJECT_NM=BUSINESS_OBJ_NM 
                        DISPLAY_ORDER_NBR=DISPLAY_ORDER_NUMBER)); 
  By business_object_uid;

  /* create LDF_Group table */
  If first.business_object_uid then do;
    LDF_Group_Key+1;       /* increment group key for each new business_object_uid */
    output LDF_Group;
  end;

  /* create LDF_Data table */
  LDF_Data_Key+1;    /* increment data key for each record */
  output LDF_Data;
Run;



/******************************************/
/* macro to create dimension LDF bridge table */

%macro CreateDimLDFBridgeTable (bridgetable, dimtable, dimkey, dimuid);
 /* join Dimension table with LDF_Group table */
 Proc SQL;
  Create table &bridgetable as
   Select dim.&dimkey, ldf.LDF_Group_Key
     From &dimtable dim INNER JOIN LDF_Group ldf
       On dim.&dimuid = ldf.business_object_uid;
 Quit;

 /* assign key values for records without a match */
 Data &bridgetable;
   Set &bridgetable;
   If LDF_Group_Key = . then LDF_Group_Key = 1;
  Run;

%mend;


/* create Person and Organization LDF Bridge tables */
/* note: Person and Organization tables must be created first */
%CreateDimLDFBridgeTable (Patient_LDF_Group, nbs_rdb.l_patient, patient_key, patient_uid);
%CreateDimLDFBridgeTable (Provider_LDF_Group, nbs_rdb.l_provider, provider_key, provider_uid);
%CreateDimLDFBridgeTable (Organization_LDF_Group, nbs_rdb.L_Organization, Organization_key, Organization_uid);


/******************************************/
/* clean up LDF_Group table after all keys have been assigned to Fact and Dimension tables */
/*
Data rdbdata.LDF_Group;
  Set rdbdata.LDF_Group (drop=business_object_uid);
Run;
*/

Data LDF_Data;
  Set LDF_Data;
  record_status_cd = 'ACTIVE  ' ;
Run;


Data LDF_GROUP;
  Set LDF_GROUP;
  record_status_cd = 'ACTIVE  ' ;
Run;


Data PATIENT_LDF_GROUP;
  Set PATIENT_LDF_GROUP;
  record_status_cd = 'ACTIVE  ' ;
Run;
Data PROVIDER_LDF_GROUP;
  Set PROVIDER_LDF_GROUP;
  record_status_cd = 'ACTIVE  ' ;
Run;

Data ORGANIZATION_LDF_GROUP;
  Set ORGANIZATION_LDF_GROUP;
  record_status_cd = 'ACTIVE  ' ;
Run;
%macro UpdateEntityRecordStatusCode(parentTable, childTable, parentKey, parentRecordStatusCol, childKey );
 Proc SQL;
  UPDATE &childTable childAlias
   SET record_status_cd = 'INACTIVE'
   WHERE &childKey IN ( SELECT &parentKey from &parentTable parentAlias WHERE &parentRecordStatusCol = 'INACTIVE');
 Quit;
%mend;

/* Record status cleanup for PERSON related tables.*/
%UpdateEntityRecordStatusCode (nbs_rdb.D_PATIENT, PATIENT_LDF_GROUP, PATIENT_KEY,PATIENT_RECORD_STATUS, PATIENT_KEY);
%UpdateEntityRecordStatusCode (nbs_rdb.D_PROVIDER, PROVIDER_LDF_GROUP, PROVIDER_KEY,PROVIDER_RECORD_STATUS, PROVIDER_KEY);
%UpdateEntityRecordStatusCode (nbs_rdb.D_ORGANIZATION, ORGANIZATION_LDF_GROUP, ORGANIZATION_KEY,ORGANIZATION_RECORD_STATUS, ORGANIZATION_KEY);

%DBLOAD (LDF_Data, LDF_Data);
%DBLOAD (LDF_GROUP, LDF_GROUP);
%DBLOAD (PATIENT_LDF_GROUP, PATIENT_LDF_GROUP);
%DBLOAD (PROVIDER_LDF_GROUP, PROVIDER_LDF_GROUP);
%DBLOAD (ORGANIZATION_LDF_GROUP, ORGANIZATION_LDF_GROUP);

PROC datasets library = work nolist;
delete LDF_Data;
delete LDF_Data1;
DELETE PATIENT_LDF_GROUP;
delete PROVIDER_LDF_GROUP;
delete ORGANIZATION_LDF_GROUP;
run;
quit;
