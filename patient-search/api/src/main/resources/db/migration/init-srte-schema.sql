CREATE TABLE anatomic_site_code
(
    anatomic_site_uid            bigint       NOT NULL,
    code_set_nm                  varchar(256) NOT NULL,
    seq_num                      smallint     NOT NULL,
    code                         varchar(20)  NOT NULL,
    anatomic_site_desc_txt       varchar(300),
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_system_cd               varchar(300),
    code_system_desc_txt         varchar(100),
    nbs_uid                      bigint,
    is_modifiable_ind            char(1),
    status_cd                    char(1),
    status_time                  datetime,
    CONSTRAINT pk_anatomic_site_code PRIMARY KEY (anatomic_site_uid)
)
    GO

CREATE TABLE city_code_value
(
    code                         varchar(20) NOT NULL,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_desc_txt                varchar(255),
    code_short_desc_txt          varchar(50),
    effective_from_time          datetime,
    effective_to_time            datetime,
    excluded_txt                 varchar(256),
    indent_level_nbr             smallint,
    is_modifiable_ind            char(1),
    parent_is_cd                 varchar(20),
    status_cd                    varchar(1),
    status_time                  datetime,
    code_set_nm                  varchar(256),
    seq_num                      smallint,
    nbs_uid                      int,
    source_concept_id            varchar(20),
    CONSTRAINT pk_city_code_value PRIMARY KEY (code)
)
    GO

CREATE TABLE cntycity_code_value
(
    effective_from_time datetime,
    effective_to_time   datetime,
    cnty_code           varchar(20) NOT NULL,
    city_code           varchar(20) NOT NULL,
    CONSTRAINT pk_cntycity_code_value PRIMARY KEY (cnty_code, city_code)
)
    GO

CREATE TABLE code_value_clinical
(
    snomed_cd                    varchar(20),
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    order_number                 smallint     NOT NULL,
    code_desc_txt                varchar(300),
    code_short_desc_txt          varchar(50),
    code_system_code             varchar(300),
    code_system_desc_txt         varchar(100),
    common_name                  varchar(300),
    other_names                  varchar(300),
    status_cd                    char(1),
    status_time                  datetime,
    code_set_nm                  varchar(256) NOT NULL,
    seq_num                      smallint     NOT NULL,
    code                         varchar(20)  NOT NULL,
    CONSTRAINT pk_code_value_clinical PRIMARY KEY (code_set_nm, seq_num, code)
)
    GO

CREATE TABLE code_value_general
(
    code_desc_txt           varchar(300),
    code_short_desc_txt     varchar(100),
    code_system_cd          varchar(300),
    code_system_desc_txt    varchar(100),
    effective_from_time     datetime,
    effective_to_time       datetime,
    indent_level_nbr        smallint,
    is_modifiable_ind       char(1),
    nbs_uid                 int,
    parent_is_cd            varchar(20),
    source_concept_id       varchar(20),
    super_code_set_nm       varchar(256),
    super_code              varchar(20),
    status_cd               char(1),
    status_time             datetime,
    concept_type_cd         varchar(20),
    concept_code            varchar(256),
    concept_nm              varchar(256),
    concept_preferred_nm    varchar(256),
    concept_status_cd       varchar(256),
    concept_status_time     datetime,
    code_system_version_nbr varchar(256),
    concept_order_nbr       int,
    admin_comments          varchar(2000),
    add_time                datetime,
    add_user_id             bigint,
    code_set_nm             varchar(256) NOT NULL,
    code                    varchar(20)  NOT NULL,
    CONSTRAINT pk_code_value_general PRIMARY KEY (code_set_nm, code)
)
    GO

CREATE TABLE codeset
(
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_set_desc_txt            varchar(2000),
    effective_from_time          datetime,
    effective_to_time            datetime,
    is_modifiable_ind            char(1),
    nbs_uid                      int,
    source_version_txt           varchar(20),
    source_domain_nm             varchar(50),
    status_cd                    varchar(1),
    status_to_time               datetime,
    code_set_group_id            bigint,
    admin_comments               varchar(2000),
    value_set_nm                 varchar(256),
    ldf_picklist_ind_cd          char(1),
    value_set_code               varchar(256),
    value_set_type_cd            varchar(20),
    value_set_oid                varchar(256),
    value_set_status_cd          varchar(256),
    value_set_status_time        datetime,
    parent_is_cd                 bigint,
    add_time                     datetime,
    add_user_id                  bigint,
    class_cd                     varchar(30)  NOT NULL,
    code_set_nm                  varchar(256) NOT NULL,
    CONSTRAINT pk_codeset PRIMARY KEY (class_cd, code_set_nm)
)
    GO

CREATE TABLE codeset_group_metadata
(
    code_set_group_id       bigint       NOT NULL,
    code_set_nm             varchar(256) NOT NULL,
    vads_value_set_code     varchar(256),
    code_set_desc_txt       varchar(2000),
    code_set_short_desc_txt varchar(2000),
    ldf_picklist_ind_cd     char(1),
    phin_std_val_ind        char(1),
    CONSTRAINT pk_codeset_group_metadata PRIMARY KEY (code_set_group_id)
)
    GO

CREATE TABLE condition_code
(
    condition_cd                  varchar(20) NOT NULL,
    condition_codeset_nm          varchar(256),
    condition_seq_num             smallint,
    assigning_authority_cd        varchar(199),
    assigning_authority_desc_txt  varchar(100),
    code_system_cd                varchar(300),
    code_system_desc_txt          varchar(100),
    condition_desc_txt            varchar(300),
    condition_short_nm            varchar(50),
    effective_from_time           datetime,
    effective_to_time             datetime,
    indent_level_nbr              smallint,
    investigation_form_cd         varchar(50),
    is_modifiable_ind             char(1),
    nbs_uid                       bigint,
    nnd_ind                       char(1)     NOT NULL,
    parent_is_cd                  varchar(20),
    prog_area_cd                  varchar(20),
    reportable_morbidity_ind      char(1)     NOT NULL,
    reportable_summary_ind        char(1)     NOT NULL,
    status_cd                     char(1),
    status_time                   datetime,
    nnd_entity_identifier         varchar(200),
    nnd_summary_entity_identifier varchar(200),
    summary_investigation_form_cd varchar(50),
    contact_tracing_enable_ind    char(1),
    vaccine_enable_ind            char(1),
    treatment_enable_ind          char(1),
    lab_report_enable_ind         char(1),
    morb_report_enable_ind        char(1),
    port_req_ind_cd               char(1),
    family_cd                     varchar(256),
    coinfection_grp_cd            varchar(20),
    rhap_parse_nbs_ind            varchar(1),
    rhap_action_value             varchar(200),
    CONSTRAINT pk_condition_code PRIMARY KEY (condition_cd)
)
    GO

CREATE TABLE country_code
(
    code                         varchar(20) NOT NULL,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_desc_txt                varchar(255),
    code_short_desc_txt          varchar(50),
    effective_from_time          datetime,
    effective_to_time            datetime,
    excluded_txt                 varchar(1300),
    key_info_txt                 varchar(2000),
    indent_level_nbr             smallint,
    is_modifiable_ind            char(1),
    parent_is_cd                 varchar(20),
    status_cd                    varchar(1),
    status_time                  datetime,
    code_set_nm                  varchar(256),
    seq_num                      smallint,
    nbs_uid                      int,
    source_concept_id            varchar(20),
    code_system_cd               varchar(300),
    code_system_desc_txt         varchar(100),
    CONSTRAINT pk_country_code PRIMARY KEY (code)
)
    GO

CREATE TABLE country_code_iso
(
    code_desc_txt                varchar(100),
    code_system_cd               varchar(300),
    code_system_desc_txt         varchar(100),
    code_short_desc_txt          varchar(100),
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    status_cd                    varchar(1),
    status_time                  datetime,
    code_set_nm                  varchar(256) NOT NULL,
    seq_num                      smallint     NOT NULL,
    code                         varchar(20)  NOT NULL,
    CONSTRAINT pk_country_code_iso PRIMARY KEY (code_set_nm, seq_num, code)
)
    GO

CREATE TABLE country_xref
(
    country_xref_uid   bigint IDENTITY (1, 1) NOT NULL,
    from_code_set_nm   varchar(256) NOT NULL,
    from_seq_num       smallint     NOT NULL,
    from_code          varchar(20)  NOT NULL,
    from_code_desc_txt varchar(100),
    to_code_desc_txt   varchar(50),
    to_code_system_cd  varchar(300),
    status_cd          varchar(1),
    status_time        datetime,
    alpha2_to_code     varchar(2),
    to_code_set_nm     varchar(256) NOT NULL,
    to_seq_num         smallint     NOT NULL,
    to_code            varchar(20)  NOT NULL,
    CONSTRAINT pk_country_xref PRIMARY KEY (country_xref_uid)
)
    GO

CREATE TABLE elr_xref
(
    effective_from_time datetime,
    effective_to_time   datetime,
    status_cd           char(1),
    status_time         datetime,
    laboratory_id       varchar(20),
    nbs_uid             int,
    from_code_set_nm    varchar(256) NOT NULL,
    from_seq_num        smallint     NOT NULL,
    from_code           varchar(20)  NOT NULL,
    to_code_set_nm      varchar(256) NOT NULL,
    to_seq_num          smallint     NOT NULL,
    to_code             varchar(20)  NOT NULL,
    CONSTRAINT pk_elr_xref PRIMARY KEY (from_code_set_nm, from_seq_num, from_code, to_code_set_nm, to_seq_num, to_code)
)
    GO

CREATE TABLE imrdbmapping
(
    imrdbmapping_id  int IDENTITY (1, 1) NOT NULL,
    unique_cd        varchar(255),
    unique_name      varchar(255),
    description      varchar(255),
    db_table         varchar(255),
    db_field         varchar(255),
    rdb_table        varchar(255),
    rdb_attribute    varchar(255),
    other_attributes varchar(255),
    condition_cd     varchar(255),
    CONSTRAINT pk_imrdbmapping PRIMARY KEY (imrdbmapping_id)
)
    GO

CREATE TABLE investigation_code
(
    investigation_cd             varchar(20)  NOT NULL,
    investigation_desc_txt       varchar(255),
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_system_cd               varchar(300),
    code_system_desc_txt         varchar(80),
    code_version                 varchar(10),
    effective_from_time          datetime,
    effective_to_time            datetime,
    status_cd                    varchar(1),
    status_time                  datetime,
    nbs_uid                      smallint,
    source_concept_id            varchar(20),
    code_set_nm                  varchar(256) NOT NULL,
    seq_num                      smallint     NOT NULL,
    CONSTRAINT pk_investigation_code PRIMARY KEY (investigation_cd)
)
    GO

CREATE TABLE jurisdiction_code
(
    code                         varchar(20) NOT NULL,
    type_cd                      varchar(20) NOT NULL,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_desc_txt                varchar(255),
    code_short_desc_txt          varchar(50),
    effective_from_time          datetime,
    effective_to_time            datetime,
    indent_level_nbr             smallint,
    is_modifiable_ind            char(1),
    parent_is_cd                 varchar(20),
    state_domain_cd              varchar(20),
    status_cd                    varchar(1),
    status_time                  datetime,
    code_set_nm                  varchar(256),
    code_seq_num                 smallint,
    nbs_uid                      int,
    source_concept_id            varchar(20),
    code_system_cd               varchar(300),
    code_system_desc_txt         varchar(100),
    export_ind                   char(1),
    CONSTRAINT pk_jurisdiction_code PRIMARY KEY (code)
)
    GO

CREATE TABLE jurisdiction_participation
(
    effective_from_time datetime,
    effective_to_time   datetime,
    jurisdiction_cd     varchar(20) NOT NULL,
    fips_cd             varchar(20) NOT NULL,
    type_cd             varchar(20) NOT NULL,
    CONSTRAINT pk_jurisdiction_participation PRIMARY KEY (jurisdiction_cd, fips_cd, type_cd)
)
    GO

CREATE TABLE lab_coding_system
(
    laboratory_id                varchar(20) NOT NULL,
    laboratory_system_desc_txt   varchar(100),
    coding_system_cd             varchar(20),
    code_system_desc_txt         varchar(100),
    electronic_lab_ind           char(1),
    effective_from_time          datetime,
    effective_to_time            datetime,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    nbs_uid                      bigint,
    CONSTRAINT pk_lab_coding_system PRIMARY KEY (laboratory_id)
)
    GO

CREATE TABLE lab_result
(
    lab_result_desc_txt      varchar(50),
    effective_from_time      datetime,
    effective_to_time        datetime,
    nbs_uid                  bigint,
    default_prog_area_cd     varchar(20),
    organism_name_ind        char(1),
    default_condition_cd     varchar(20),
    pa_derivation_exclude_cd char(1),
    code_system_cd           varchar(300),
    code_set_nm              varchar(256),
    lab_result_cd            varchar(20) NOT NULL,
    laboratory_id            varchar(20) NOT NULL,
    CONSTRAINT pk_lab_result PRIMARY KEY (lab_result_cd, laboratory_id)
)
    GO

CREATE TABLE lab_result_snomed
(
    effective_from_time datetime,
    effective_to_time   datetime,
    status_cd           char(1),
    status_time         datetime,
    lab_result_cd       varchar(20) NOT NULL,
    laboratory_id       varchar(20) NOT NULL,
    snomed_cd           varchar(20) NOT NULL,
    CONSTRAINT pk_lab_result_snomed PRIMARY KEY (lab_result_cd, laboratory_id, snomed_cd)
)
    GO

CREATE TABLE lab_test
(
    lab_test_desc_txt                      varchar(100),
    test_type_cd                           varchar(20) NOT NULL,
    nbs_uid                                bigint      NOT NULL,
    effective_from_time                    datetime,
    effective_to_time                      datetime,
    default_prog_area_cd                   varchar(20),
    default_condition_cd                   varchar(20),
    drug_test_ind                          char(1),
    organism_result_test_ind               char(1),
    indent_level_nbr                       smallint,
    pa_derivation_exclude_cd               char(1),
    lab_test_cd                            varchar(20) NOT NULL,
    laboratory_id                          varchar(20) NOT NULL,
    labtest_progarea_mapping_lab_test_cd   varchar(20),
    labtest_progarea_mapping_laboratory_id varchar(20),
    CONSTRAINT pk_lab_test PRIMARY KEY (lab_test_cd, laboratory_id)
)
    GO

CREATE TABLE labtest_loinc
(
    effective_from_time datetime,
    effective_to_time   datetime,
    status_cd           char(1),
    status_time         datetime,
    lab_test_cd         varchar(20) NOT NULL,
    laboratory_id       varchar(20) NOT NULL,
    loinc_cd            varchar(20) NOT NULL,
    CONSTRAINT pk_labtest_loinc PRIMARY KEY (lab_test_cd, laboratory_id, loinc_cd)
)
    GO

CREATE TABLE labtest_progarea_mapping
(
    lab_test_desc_txt        varchar(100),
    test_type_cd             varchar(20) NOT NULL,
    condition_cd             varchar(20),
    condition_short_nm       varchar(50),
    condition_desc_txt       varchar(300),
    prog_area_cd             varchar(20) NOT NULL,
    prog_area_desc_txt       varchar(50) NOT NULL,
    organism_result_test_ind char(1),
    indent_level_nbr         smallint,
    lab_test_cd              varchar(20) NOT NULL,
    laboratory_id            varchar(20) NOT NULL,
    CONSTRAINT pk_labtest_progarea_mapping PRIMARY KEY (lab_test_cd, laboratory_id)
)
    GO

CREATE TABLE language_code
(
    code                         varchar(20) NOT NULL,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_desc_txt                varchar(255),
    code_short_desc_txt          varchar(50),
    effective_from_time          datetime,
    effective_to_time            datetime,
    key_info_txt                 varchar(2000),
    indent_level_nbr             smallint,
    is_modifiable_ind            char(1),
    parent_is_cd                 varchar(20),
    nbs_uid                      int         NOT NULL,
    status_cd                    varchar(1),
    status_time                  datetime,
    code_set_nm                  varchar(256),
    seq_num                      smallint,
    source_concept_id            varchar(20),
    CONSTRAINT pk_language_code PRIMARY KEY (code)
)
    GO

CREATE TABLE ldf_page_set
(
    ldf_page_id         varchar(20) NOT NULL,
    business_object_nm  varchar(20),
    condition_cd        varchar(20),
    ui_display          varchar(10),
    indent_level_nbr    smallint,
    parent_is_cd        varchar(20),
    code_set_nm         varchar(256),
    seq_num             smallint,
    code_version        varchar(10),
    nbs_uid             int,
    effective_from_time datetime,
    effective_to_time   datetime,
    status_cd           char(1),
    code_short_desc_txt varchar(50),
    display_row         smallint,
    display_column      smallint,
    CONSTRAINT pk_ldf_page_set PRIMARY KEY (ldf_page_id)
)
    GO

CREATE TABLE loinc_code
(
    loinc_cd                 varchar(20) NOT NULL,
    component_name           varchar(200),
    property                 varchar(10),
    time_aspect              varchar(10),
    system_cd                varchar(50),
    scale_type               varchar(20),
    method_type              varchar(50),
    display_nm               varchar(300),
    nbs_uid                  bigint,
    effective_from_time      datetime,
    effective_to_time        datetime,
    related_class_cd         varchar(50),
    pa_derivation_exclude_cd char(1),
    CONSTRAINT pk_loinc_code PRIMARY KEY (loinc_cd)
)
    GO

CREATE TABLE loinc_condition
(
    loinc_cd               varchar(20) NOT NULL,
    condition_cd           varchar(20) NOT NULL,
    disease_nm             varchar(200),
    reported_value         varchar(20),
    reported_numeric_value varchar(20),
    status_cd              char(1),
    status_time            datetime,
    effective_from_time    datetime,
    effective_to_time      datetime,
    CONSTRAINT pk_loinc_condition PRIMARY KEY (loinc_cd)
)
    GO

CREATE TABLE loinc_snomed_condition
(
    loinc_snomed_cc_uid bigint      NOT NULL,
    snomed_cd           varchar(20),
    loinc_cd            varchar(20) NOT NULL,
    condition_cd        varchar(20),
    status_cd           char(1),
    status_time         datetime,
    effective_from_time datetime,
    effective_to_time   datetime,
    CONSTRAINT pk_loinc_snomed_condition PRIMARY KEY (loinc_snomed_cc_uid)
)
    GO

CREATE TABLE naics_industry_code
(
    code                         varchar(20) NOT NULL,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_desc_txt                varchar(255),
    code_short_desc_txt          varchar(50),
    effective_from_time          datetime,
    effective_to_time            datetime,
    key_info_txt                 varchar(2000),
    indent_level_nbr             smallint,
    is_modifiable_ind            char(1),
    parent_is_cd                 varchar(20),
    status_cd                    varchar(1),
    status_time                  datetime,
    code_set_nm                  varchar(256),
    seq_num                      smallint,
    nbs_uid                      int,
    source_concept_id            varchar(20),
    CONSTRAINT pk_naics_industry_code PRIMARY KEY (code)
)
    GO

CREATE TABLE occupation_code
(
    code                         varchar(20) NOT NULL,
    naics_industry_cd            varchar(20),
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_desc_txt                varchar(255),
    code_short_desc_txt          varchar(100),
    effective_from_time          datetime,
    effective_to_time            datetime,
    key_info_txt                 varchar(2000),
    indent_level_nbr             smallint,
    is_modifiable_ind            char(1),
    parent_is_cd                 varchar(20),
    status_cd                    varchar(1),
    status_time                  datetime,
    code_set_nm                  varchar(256),
    seq_num                      smallint,
    nbs_uid                      int,
    source_concept_id            varchar(20),
    CONSTRAINT pk_occupation_code PRIMARY KEY (code)
)
    GO

CREATE TABLE participation_type
(
    record_status_cd    varchar(20)  NOT NULL,
    record_status_time  datetime     NOT NULL,
    type_desc_txt       varchar(100) NOT NULL,
    type_prefix         varchar(8)   NOT NULL,
    act_class_cd        varchar(20)  NOT NULL,
    subject_class_cd    varchar(20)  NOT NULL,
    type_cd             varchar(50)  NOT NULL,
    question_identifier varchar(50)  NOT NULL,
    CONSTRAINT pk_participation_type PRIMARY KEY (act_class_cd, subject_class_cd, type_cd, question_identifier)
)
    GO

CREATE TABLE program_area_code
(
    prog_area_cd       varchar(20) NOT NULL,
    prog_area_desc_txt varchar(50),
    nbs_uid            int,
    status_cd          char(1),
    status_time        datetime,
    code_set_nm        varchar(256),
    code_seq           smallint,
    CONSTRAINT pk_program_area_code PRIMARY KEY (prog_area_cd)
)
    GO

CREATE TABLE race_code
(
    code                         varchar(20) NOT NULL,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_desc_txt                varchar(255),
    code_short_desc_txt          varchar(50),
    effective_from_time          datetime,
    effective_to_time            datetime,
    excluded_txt                 varchar(256),
    key_info_txt                 varchar(2000),
    indent_level_nbr             smallint,
    is_modifiable_ind            char(1),
    parent_is_cd                 varchar(20),
    status_cd                    varchar(1),
    status_time                  datetime,
    code_set_nm                  varchar(256),
    seq_num                      smallint,
    nbs_uid                      int,
    source_concept_id            varchar(20),
    code_system_cd               varchar(300),
    code_system_desc_txt         varchar(100),
    CONSTRAINT pk_race_code PRIMARY KEY (code)
)
    GO

CREATE TABLE snomed_code
(
    snomed_cd                varchar(20) NOT NULL,
    snomed_desc_txt          varchar(100),
    source_concept_id        varchar(20) NOT NULL,
    source_version_id        varchar(20) NOT NULL,
    status_cd                char(1),
    status_time              datetime,
    nbs_uid                  int,
    effective_from_time      datetime,
    effective_to_time        datetime,
    pa_derivation_exclude_cd char(1),
    CONSTRAINT pk_snomed_code PRIMARY KEY (snomed_cd)
)
    GO

CREATE TABLE snomed_condition
(
    disease_nm          varchar(200),
    organism_set_nm     varchar(100),
    status_cd           char(1),
    status_time         datetime,
    effective_from_time datetime,
    effective_to_time   datetime,
    snomed_cd           varchar(20) NOT NULL,
    condition_cd        varchar(20) NOT NULL,
    CONSTRAINT pk_snomed_condition PRIMARY KEY (snomed_cd, condition_cd)
)
    GO

CREATE TABLE specimen_source_code
(
    specimen_source_uid          bigint       NOT NULL,
    code_set_nm                  varchar(256) NOT NULL,
    seq_num                      smallint     NOT NULL,
    code                         varchar(20)  NOT NULL,
    specimen_source_desc_txt     varchar(300),
    code_system_cd               varchar(300),
    code_system_desc_txt         varchar(100),
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    is_modifiable_ind            char(1),
    status_cd                    char(1),
    status_time                  datetime,
    CONSTRAINT pk_specimen_source_code PRIMARY KEY (specimen_source_uid)
)
    GO

CREATE TABLE standard_xref
(
    standard_xref_uid  bigint IDENTITY (1, 1) NOT NULL,
    from_code_set_nm   varchar(256) NOT NULL,
    from_seq_num       smallint     NOT NULL,
    from_code          varchar(20)  NOT NULL,
    from_code_desc_txt varchar(300),
    to_code_set_nm     varchar(256) NOT NULL,
    to_seq_num         smallint     NOT NULL,
    to_code            varchar(20)  NOT NULL,
    to_code_desc_txt   varchar(300),
    to_code_system_cd  varchar(300),
    status_cd          varchar(1),
    status_time        datetime,
    CONSTRAINT pk_standard_xref PRIMARY KEY (standard_xref_uid)
)
    GO

CREATE TABLE state_code
(
    state_cd                     varchar(20) NOT NULL,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    state_nm                     varchar(2),
    code_desc_txt                varchar(50),
    effective_from_time          datetime,
    effective_to_time            datetime,
    excluded_txt                 varchar(1300),
    indent_level_nbr             smallint,
    is_modifiable_ind            char(1),
    key_info_txt                 varchar(2000),
    parent_is_cd                 varchar(20),
    status_cd                    varchar(1),
    status_time                  datetime,
    code_set_nm                  varchar(256),
    seq_num                      smallint,
    nbs_uid                      int,
    source_concept_id            varchar(20),
    code_system_cd               varchar(300),
    code_system_desc_txt         varchar(100),
    CONSTRAINT pk_state_code PRIMARY KEY (state_cd)
)
    GO

CREATE TABLE state_county_code_value
(
    code                         varchar(20) NOT NULL,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_desc_txt                varchar(255),
    code_short_desc_txt          varchar(50),
    effective_from_time          datetime,
    effective_to_time            datetime,
    excluded_txt                 varchar(256),
    indent_level_nbr             smallint,
    is_modifiable_ind            char(1),
    parent_is_cd                 varchar(20),
    status_cd                    varchar(1),
    status_time                  datetime,
    code_set_nm                  varchar(256),
    seq_num                      smallint,
    nbs_uid                      int,
    source_concept_id            varchar(20),
    code_system_cd               varchar(300),
    code_system_desc_txt         varchar(100),
    CONSTRAINT pk_state_county_code_value PRIMARY KEY (code)
)
    GO

CREATE TABLE state_model
(
    record_status_code_set_nm    varchar(256) NOT NULL,
    record_status_to_code        varchar(20)  NOT NULL,
    record_status_seq_nm         smallint     NOT NULL,
    object_status_code_set_nm    varchar(256) NOT NULL,
    object_status_from_code      varchar(20)  NOT NULL,
    object_status_to_code        varchar(20)  NOT NULL,
    object_status_seq_nm         smallint     NOT NULL,
    nbs_uid                      int,
    business_trigger_code_set_nm varchar(256) NOT NULL,
    business_trigger_set_seq_num smallint     NOT NULL,
    business_trigger_code        varchar(20)  NOT NULL,
    module_cd                    varchar(20)  NOT NULL,
    record_status_from_code      varchar(20)  NOT NULL,
    CONSTRAINT pk_state_model PRIMARY KEY (business_trigger_code_set_nm, business_trigger_set_seq_num,
                                           business_trigger_code, module_cd, record_status_from_code)
)
    GO

CREATE TABLE totalidm
(
    totalidm_id   int IDENTITY (1, 1) NOT NULL,
    unique_cd     nvarchar(255),
    srt_reference nvarchar(255),
    format        nvarchar(255),
    label         nvarchar(255),
    CONSTRAINT pk_totalidm PRIMARY KEY (totalidm_id)
)
    GO

CREATE TABLE treatment_code
(
    treatment_cd                 varchar(20) NOT NULL,
    treatment_desc_txt           varchar(300),
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_system_cd               varchar(300),
    code_system_desc_txt         varchar(80),
    code_version                 varchar(10),
    treatment_type_cd            char(1),
    nbs_uid                      smallint,
    effective_from_time          datetime,
    effective_to_time            datetime,
    status_cd                    varchar(1),
    status_time                  datetime,
    source_concept_id            varchar(20),
    code_set_nm                  varchar(256),
    seq_num                      smallint    NOT NULL,
    drug_cd                      varchar(20),
    drug_desc_txt                varchar(255),
    dose_qty                     varchar(20),
    dose_qty_unit_cd             varchar(10),
    route_cd                     varchar(20),
    route_desc_txt               varchar(255),
    interval_cd                  varchar(20),
    interval_desc_txt            varchar(300),
    duration_amt                 varchar(20),
    duration_unit_cd             varchar(20),
    CONSTRAINT pk_treatment_code PRIMARY KEY (treatment_cd)
)
    GO

CREATE TABLE unit_code
(
    unit_uid                     bigint       NOT NULL,
    code_set_nm                  varchar(256) NOT NULL,
    seq_num                      smallint     NOT NULL,
    code                         varchar(20)  NOT NULL,
    unit_desc_txt                varchar(300),
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_system_cd               varchar(300),
    code_system_desc_txt         varchar(100),
    nbs_uid                      bigint,
    is_modifiable_ind            char(1),
    status_cd                    char(1),
    status_time                  datetime,
    CONSTRAINT pk_unit_code PRIMARY KEY (unit_uid)
)
    GO

CREATE TABLE xss_filter_pattern
(
    xss_filter_pattern_uid bigint       NOT NULL,
    reg_exp                varchar(250) NOT NULL,
    flag                   varchar(250) NOT NULL,
    desc_txt               varchar(200),
    status_cd              varchar(50)  NOT NULL,
    status_time            datetime     NOT NULL,
    CONSTRAINT pk_xss_filter_pattern PRIMARY KEY (xss_filter_pattern_uid)
)
    GO

CREATE TABLE zip_code_value
(
    code                         varchar(20) NOT NULL,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    code_desc_txt                varchar(255),
    code_short_desc_txt          varchar(50),
    effective_from_time          datetime,
    effective_to_time            datetime,
    excluded_txt                 varchar(256),
    indent_level_nbr             smallint,
    is_modifiable_ind            char(1),
    parent_is_cd                 varchar(20),
    status_cd                    varchar(1),
    status_time                  datetime,
    code_set_nm                  varchar(256),
    seq_num                      smallint,
    nbs_uid                      int,
    source_concept_id            varchar(20),
    CONSTRAINT pk_zip_code_value PRIMARY KEY (code)
)
    GO

CREATE TABLE zipcnty_code_value
(
    effective_from_time datetime,
    effective_to_time   datetime,
    zip_code            varchar(20) NOT NULL,
    cnty_code           varchar(20) NOT NULL,
    CONSTRAINT pk_zipcnty_code_value PRIMARY KEY (zip_code, cnty_code)
)
    GO

ALTER TABLE cntycity_code_value
    ADD CONSTRAINT FK_CNTYCITY_CODE_VALUE_ON_CITY_CODE FOREIGN KEY (city_code) REFERENCES city_code_value (code)
    GO

ALTER TABLE cntycity_code_value
    ADD CONSTRAINT FK_CNTYCITY_CODE_VALUE_ON_CNTY_CODE FOREIGN KEY (cnty_code) REFERENCES state_county_code_value (code)
    GO

ALTER TABLE codeset
    ADD CONSTRAINT FK_CODESET_ON_CODE_SET_GROUP FOREIGN KEY (code_set_group_id) REFERENCES codeset_group_metadata (code_set_group_id)
    GO

ALTER TABLE code_value_clinical
    ADD CONSTRAINT FK_CODE_VALUE_CLINICAL_ON_SNOMED_CD FOREIGN KEY (snomed_cd) REFERENCES snomed_code (snomed_cd)
    GO

ALTER TABLE condition_code
    ADD CONSTRAINT FK_CONDITION_CODE_ON_PROG_AREA_CD FOREIGN KEY (prog_area_cd) REFERENCES program_area_code (prog_area_cd)
    GO

ALTER TABLE country_xref
    ADD CONSTRAINT FK_COUNTRY_XREF_ON_TOCOSENMTOSENUTOCO FOREIGN KEY (to_code_set_nm, to_seq_num, to_code) REFERENCES country_code_iso (code_set_nm, seq_num, code)
    GO

ALTER TABLE jurisdiction_participation
    ADD CONSTRAINT FK_JURISDICTION_PARTICIPATION_ON_JURISDICTION_CD FOREIGN KEY (jurisdiction_cd) REFERENCES jurisdiction_code (code)
    GO

ALTER TABLE labtest_loinc
    ADD CONSTRAINT FK_LABTEST_LOINC_ON_LATECDLAID FOREIGN KEY (lab_test_cd, laboratory_id) REFERENCES lab_test (lab_test_cd, laboratory_id)
    GO

ALTER TABLE labtest_loinc
    ADD CONSTRAINT FK_LABTEST_LOINC_ON_LOINC_CD FOREIGN KEY (loinc_cd) REFERENCES loinc_code (loinc_cd)
    GO

ALTER TABLE labtest_progarea_mapping
    ADD CONSTRAINT FK_LABTEST_PROGAREA_MAPPING_ON_LATECDLAID FOREIGN KEY (lab_test_cd, laboratory_id) REFERENCES lab_test (lab_test_cd, laboratory_id)
    GO

ALTER TABLE lab_result
    ADD CONSTRAINT FK_LAB_RESULT_ON_DEFAULT_CONDITION_CD FOREIGN KEY (default_condition_cd) REFERENCES condition_code (condition_cd)
    GO

ALTER TABLE lab_result
    ADD CONSTRAINT FK_LAB_RESULT_ON_DEFAULT_PROG_AREA_CD FOREIGN KEY (default_prog_area_cd) REFERENCES program_area_code (prog_area_cd)
    GO

ALTER TABLE lab_result
    ADD CONSTRAINT FK_LAB_RESULT_ON_LABORATORY FOREIGN KEY (laboratory_id) REFERENCES lab_coding_system (laboratory_id)
    GO

ALTER TABLE lab_result_snomed
    ADD CONSTRAINT FK_LAB_RESULT_SNOMED_ON_LARECDLAID FOREIGN KEY (lab_result_cd, laboratory_id) REFERENCES lab_result (lab_result_cd, laboratory_id)
    GO

ALTER TABLE lab_result_snomed
    ADD CONSTRAINT FK_LAB_RESULT_SNOMED_ON_SNOMED_CD FOREIGN KEY (snomed_cd) REFERENCES snomed_code (snomed_cd)
    GO

ALTER TABLE lab_test
    ADD CONSTRAINT FK_LAB_TEST_ON_DEFAULT_CONDITION_CD FOREIGN KEY (default_condition_cd) REFERENCES condition_code (condition_cd)
    GO

ALTER TABLE lab_test
    ADD CONSTRAINT FK_LAB_TEST_ON_DEFAULT_PROG_AREA_CD FOREIGN KEY (default_prog_area_cd) REFERENCES program_area_code (prog_area_cd)
    GO

ALTER TABLE lab_test
    ADD CONSTRAINT FK_LAB_TEST_ON_LABORATORY FOREIGN KEY (laboratory_id) REFERENCES lab_coding_system (laboratory_id)
    GO

ALTER TABLE lab_test
    ADD CONSTRAINT FK_LAB_TEST_ON_LALATECDLALAID FOREIGN KEY (labtest_progarea_mapping_lab_test_cd,
                                                              labtest_progarea_mapping_laboratory_id) REFERENCES labtest_progarea_mapping (lab_test_cd, laboratory_id)
    GO

ALTER TABLE ldf_page_set
    ADD CONSTRAINT FK_LDF_PAGE_SET_ON_CONDITION_CD FOREIGN KEY (condition_cd) REFERENCES condition_code (condition_cd)
    GO

ALTER TABLE loinc_condition
    ADD CONSTRAINT FK_LOINC_CONDITION_ON_CONDITION_CD FOREIGN KEY (condition_cd) REFERENCES condition_code (condition_cd)
    GO

ALTER TABLE loinc_condition
    ADD CONSTRAINT FK_LOINC_CONDITION_ON_LOINC_CD FOREIGN KEY (loinc_cd) REFERENCES loinc_code (loinc_cd)
    GO

ALTER TABLE loinc_snomed_condition
    ADD CONSTRAINT FK_LOINC_SNOMED_CONDITION_ON_CONDITION_CD FOREIGN KEY (condition_cd) REFERENCES condition_code (condition_cd)
    GO

ALTER TABLE loinc_snomed_condition
    ADD CONSTRAINT FK_LOINC_SNOMED_CONDITION_ON_LOINC_CD FOREIGN KEY (loinc_cd) REFERENCES loinc_code (loinc_cd)
    GO

ALTER TABLE loinc_snomed_condition
    ADD CONSTRAINT FK_LOINC_SNOMED_CONDITION_ON_SNOMED_CD FOREIGN KEY (snomed_cd) REFERENCES snomed_code (snomed_cd)
    GO

ALTER TABLE occupation_code
    ADD CONSTRAINT FK_OCCUPATION_CODE_ON_NAICS_INDUSTRY_CD FOREIGN KEY (naics_industry_cd) REFERENCES naics_industry_code (code)
    GO

ALTER TABLE snomed_condition
    ADD CONSTRAINT FK_SNOMED_CONDITION_ON_CONDITION_CD FOREIGN KEY (condition_cd) REFERENCES condition_code (condition_cd)
    GO

ALTER TABLE snomed_condition
    ADD CONSTRAINT FK_SNOMED_CONDITION_ON_SNOMED_CD FOREIGN KEY (snomed_cd) REFERENCES snomed_code (snomed_cd)
    GO

ALTER TABLE zipcnty_code_value
    ADD CONSTRAINT FK_ZIPCNTY_CODE_VALUE_ON_CNTY_CODE FOREIGN KEY (cnty_code) REFERENCES state_county_code_value (code)
    GO

ALTER TABLE zipcnty_code_value
    ADD CONSTRAINT FK_ZIPCNTY_CODE_VALUE_ON_ZIP_CODE FOREIGN KEY (zip_code) REFERENCES zip_code_value (code)
    GO