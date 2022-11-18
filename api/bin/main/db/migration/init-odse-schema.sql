CREATE TABLE act
(
    act_uid  bigint      NOT NULL,
    class_cd varchar(10) NOT NULL,
    mood_cd  varchar(10) NOT NULL,
    CONSTRAINT pk_act PRIMARY KEY (act_uid)
)
    GO

CREATE TABLE act_id
(
    add_reason_cd                varchar(20),
    add_time                     datetime,
    add_user_id                  bigint,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    duration_amt                 varchar(20),
    duration_unit_cd             varchar(20),
    last_chg_reason_cd           varchar(20),
    last_chg_time                datetime,
    last_chg_user_id             bigint,
    record_status_cd             varchar(20),
    record_status_time           datetime,
    root_extension_txt           varchar(199),
    status_cd                    char(1),
    status_time                  datetime,
    type_cd                      varchar(50),
    type_desc_txt                varchar(100),
    user_affiliation_txt         varchar(20),
    valid_from_time              datetime,
    valid_to_time                datetime,
    act_uid                      bigint   NOT NULL,
    act_id_seq                   smallint NOT NULL,
    CONSTRAINT pk_act_id PRIMARY KEY (act_uid, act_id_seq)
)
    GO

CREATE TABLE act_id_hist
(
    add_reason_cd                varchar(20),
    add_time                     datetime,
    add_user_id                  bigint,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    duration_amt                 varchar(20),
    duration_unit_cd             varchar(20),
    last_chg_reason_cd           varchar(20),
    last_chg_time                datetime,
    last_chg_user_id             bigint,
    record_status_cd             varchar(20),
    record_status_time           datetime,
    root_extension_txt           varchar(199),
    status_cd                    char(1),
    status_time                  datetime,
    type_cd                      varchar(50),
    type_desc_txt                varchar(100),
    user_affiliation_txt         varchar(20),
    valid_from_time              datetime,
    valid_to_time                datetime,
    act_uid                      bigint   NOT NULL,
    act_id_seq                   smallint NOT NULL,
    version_ctrl_nbr             smallint NOT NULL,
    CONSTRAINT pk_act_id_hist PRIMARY KEY (act_uid, act_id_seq, version_ctrl_nbr)
)
    GO

CREATE TABLE act_locator_participation
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    from_time            datetime,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    record_status_cd     varchar(20),
    record_status_time   datetime,
    to_time              datetime,
    status_cd            char(1),
    status_time          datetime,
    type_cd              varchar(50),
    type_desc_txt        varchar(100),
    user_affiliation_txt varchar(20),
    entity_uid           bigint NOT NULL,
    locator_uid          bigint NOT NULL,
    act_uid              bigint NOT NULL,
    CONSTRAINT pk_act_locator_participation PRIMARY KEY (entity_uid, locator_uid, act_uid)
)
    GO

CREATE TABLE act_locator_participation_hist
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    from_time            datetime,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    record_status_cd     varchar(20),
    record_status_time   datetime,
    to_time              datetime,
    status_cd            char(1),
    status_time          datetime,
    type_cd              varchar(50),
    type_desc_txt        varchar(100),
    user_affiliation_txt varchar(20),
    entity_uid           bigint   NOT NULL,
    locator_uid          bigint   NOT NULL,
    act_uid              bigint   NOT NULL,
    version_ctrl_nbr     smallint NOT NULL,
    CONSTRAINT pk_act_locator_participation_hist PRIMARY KEY (entity_uid, locator_uid, act_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE act_relationship
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    from_time            datetime,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    record_status_cd     varchar(20),
    record_status_time   datetime,
    sequence_nbr         smallint,
    source_class_cd      varchar(10),
    status_cd            char(1),
    status_time          datetime,
    target_class_cd      varchar(10),
    to_time              datetime,
    type_desc_txt        varchar(100),
    user_affiliation_txt varchar(20),
    source_act_uid       bigint      NOT NULL,
    target_act_uid       bigint      NOT NULL,
    type_cd              varchar(50) NOT NULL,
    CONSTRAINT pk_act_relationship PRIMARY KEY (source_act_uid, target_act_uid, type_cd)
)
    GO

CREATE TABLE act_relationship_hist
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    from_time            datetime,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    record_status_cd     varchar(20),
    record_status_time   datetime,
    sequence_nbr         smallint,
    status_cd            char(1),
    status_time          datetime,
    source_class_cd      varchar(10),
    target_class_cd      varchar(10),
    to_time              datetime,
    type_desc_txt        varchar(100),
    user_affiliation_txt varchar(20),
    source_act_uid       bigint      NOT NULL,
    target_act_uid       bigint      NOT NULL,
    type_cd              varchar(50) NOT NULL,
    version_ctrl_nbr     smallint    NOT NULL,
    CONSTRAINT pk_act_relationship_hist PRIMARY KEY (source_act_uid, target_act_uid, type_cd, version_ctrl_nbr)
)
    GO

CREATE TABLE activity_log
(
    activity_log_uid   bigint NOT NULL,
    doc_type           varchar(50),
    doc_nm             varchar(250),
    record_status_cd   varchar(20),
    record_status_time datetime,
    message_txt        varchar(MAX
) ,
    action_txt         varchar(50),
    source_type_cd     varchar(50),
    target_type_cd     varchar(50),
    source_id          varchar(50),
    target_id          varchar(50),
    add_user_id        bigint   NOT NULL,
    add_time           datetime NOT NULL,
    CONSTRAINT pk_activity_log PRIMARY KEY (activity_log_uid)
) GO

CREATE TABLE alert
(
    alert_uid        bigint      NOT NULL,
    type_cd          varchar(20) NOT NULL,
    condition_cd     varchar(20),
    jurisdiction_cd  varchar(20),
    add_time         datetime,
    last_chg_time    datetime,
    record_status_cd varchar(20),
    add_user_id      bigint,
    last_chg_user_id bigint,
    severity_cd      varchar(20),
    alert_msg_txt    varchar(MAX
) ,
    CONSTRAINT pk_alert PRIMARY KEY (alert_uid)
) GO

CREATE TABLE alert_email_message
(
    alert_email_message_uid   bigint       NOT NULL,
    type_cd                   varchar(20)  NOT NULL,
    type                      varchar(50)  NOT NULL,
    severity_cd               varchar(20)  NOT NULL,
    severity                  varchar(50)  NOT NULL,
    simulated_alert           char(1),
    jurisdiction_cd           varchar(20)  NOT NULL,
    jurisdiction_description  varchar(100) NOT NULL,
    associated_condition_cd   varchar(20),
    associated_condition_desc varchar(100),
    event_add_time            datetime     NOT NULL,
    alert_add_time            datetime     NOT NULL,
    event_local_id            varchar(50)  NOT NULL,
    transmission_status       varchar(20)  NOT NULL,
    prog_area_cd              varchar(20),
    prog_area_description     varchar(100),
    alert_uid                 bigint       NOT NULL,
    email_sent_time           datetime,
    CONSTRAINT pk_alert_email_message PRIMARY KEY (alert_email_message_uid)
)
    GO

CREATE TABLE alert_log
(
    alert_log_uid  bigint   NOT NULL,
    alert_uid      bigint   NOT NULL,
    event_local_id varchar(50),
    activity_log   varchar(2000),
    add_time       datetime NOT NULL,
    CONSTRAINT pk_alert_log PRIMARY KEY (alert_log_uid)
)
    GO

CREATE TABLE alert_log_detail
(
    alert_log_detail_uid      bigint NOT NULL,
    alert_log_uid             bigint NOT NULL,
    alert_activity_detail_log varchar(2000),
    CONSTRAINT pk_alert_log_detail PRIMARY KEY (alert_log_detail_uid)
)
    GO

CREATE TABLE alert_user
(
    alert_user_uid bigint NOT NULL,
    alert_uid      bigint NOT NULL,
    nedss_entry_id bigint NOT NULL,
    CONSTRAINT pk_alert_user PRIMARY KEY (alert_user_uid)
)
    GO

CREATE TABLE auth_bus_obj_rt
(
    auth_bus_obj_rt_uid   bigint      NOT NULL,
    auth_perm_set_uid     bigint      NOT NULL,
    auth_bus_obj_type_uid bigint      NOT NULL,
    add_time              datetime    NOT NULL,
    add_user_id           bigint      NOT NULL,
    last_chg_time         datetime    NOT NULL,
    last_chg_user_id      bigint      NOT NULL,
    record_status_cd      varchar(20) NOT NULL,
    record_status_time    datetime    NOT NULL,
    CONSTRAINT pk_auth_bus_obj_rt PRIMARY KEY (auth_bus_obj_rt_uid)
)
    GO

CREATE TABLE auth_bus_obj_type
(
    auth_bus_obj_type_uid bigint      NOT NULL,
    bus_obj_nm            varchar(100),
    bus_obj_disp_nm       varchar(1000),
    prog_area_ind         char(1),
    jurisdiction_ind      char(1),
    add_time              datetime    NOT NULL,
    add_user_id           bigint      NOT NULL,
    last_chg_time         datetime    NOT NULL,
    last_chg_user_id      bigint      NOT NULL,
    record_status_cd      varchar(20) NOT NULL,
    record_status_time    datetime    NOT NULL,
    operation_sequence    int         NOT NULL,
    CONSTRAINT pk_auth_bus_obj_type PRIMARY KEY (auth_bus_obj_type_uid)
)
    GO

CREATE TABLE auth_bus_op_rt
(
    auth_bus_op_rt_uid   bigint      NOT NULL,
    auth_bus_op_type_uid bigint      NOT NULL,
    auth_bus_obj_rt_uid  bigint      NOT NULL,
    bus_op_user_rt       char(1),
    bus_op_guest_rt      char(1),
    add_time             datetime    NOT NULL,
    add_user_id          bigint      NOT NULL,
    last_chg_time        datetime    NOT NULL,
    last_chg_user_id     bigint      NOT NULL,
    record_status_cd     varchar(20) NOT NULL,
    record_status_time   datetime    NOT NULL,
    CONSTRAINT pk_auth_bus_op_rt PRIMARY KEY (auth_bus_op_rt_uid)
)
    GO

CREATE TABLE auth_bus_op_type
(
    auth_bus_op_type_uid bigint      NOT NULL,
    bus_op_nm            varchar(100),
    bus_op_disp_nm       varchar(1000),
    add_time             datetime    NOT NULL,
    add_user_id          bigint      NOT NULL,
    last_chg_time        datetime    NOT NULL,
    last_chg_user_id     bigint      NOT NULL,
    record_status_cd     varchar(20) NOT NULL,
    record_status_time   datetime    NOT NULL,
    operation_sequence   int         NOT NULL,
    CONSTRAINT pk_auth_bus_op_type PRIMARY KEY (auth_bus_op_type_uid)
)
    GO

CREATE TABLE auth_perm_set
(
    auth_perm_set_uid        bigint      NOT NULL,
    perm_set_nm              varchar(100),
    perm_set_desc            varchar(1000),
    sys_defined_perm_set_ind char(1),
    add_time                 datetime    NOT NULL,
    add_user_id              bigint      NOT NULL,
    last_chg_time            datetime    NOT NULL,
    last_chg_user_id         bigint      NOT NULL,
    record_status_cd         varchar(20) NOT NULL,
    record_status_time       datetime    NOT NULL,
    CONSTRAINT pk_auth_perm_set PRIMARY KEY (auth_perm_set_uid)
)
    GO

CREATE TABLE auth_prog_area_admin
(
    auth_prog_area_admin_uid bigint      NOT NULL,
    prog_area_cd             varchar(100),
    auth_user_uid            bigint      NOT NULL,
    auth_user_ind            char(1)     NOT NULL,
    add_time                 datetime    NOT NULL,
    add_user_id              bigint      NOT NULL,
    last_chg_time            datetime    NOT NULL,
    last_chg_user_id         bigint      NOT NULL,
    record_status_cd         varchar(20) NOT NULL,
    record_status_time       datetime    NOT NULL,
    CONSTRAINT pk_auth_prog_area_admin PRIMARY KEY (auth_prog_area_admin_uid)
)
    GO

CREATE TABLE auth_user
(
    auth_user_uid               bigint      NOT NULL,
    user_id                     varchar(256),
    user_type                   varchar(100),
    user_title                  varchar(100),
    user_department             varchar(100),
    user_first_nm               varchar(100),
    user_last_nm                varchar(100),
    user_work_email             varchar(100),
    user_work_phone             varchar(100),
    user_mobile_phone           varchar(100),
    master_sec_admin_ind        char(1),
    prog_area_admin_ind         char(1),
    nedss_entry_id              bigint      NOT NULL,
    external_org_uid            bigint,
    user_password               varchar(100),
    user_comments               varchar(100),
    add_time                    datetime    NOT NULL,
    add_user_id                 bigint      NOT NULL,
    last_chg_time               datetime    NOT NULL,
    last_chg_user_id            bigint      NOT NULL,
    record_status_cd            varchar(20) NOT NULL,
    record_status_time          datetime    NOT NULL,
    jurisdiction_derivation_ind char(1),
    provider_uid                bigint,
    CONSTRAINT pk_auth_user PRIMARY KEY (auth_user_uid)
)
    GO

CREATE TABLE auth_user_role
(
    auth_user_role_uid bigint      NOT NULL,
    auth_role_nm       varchar(100),
    prog_area_cd       varchar(100),
    jurisdiction_cd    varchar(100),
    auth_user_uid      bigint      NOT NULL,
    auth_perm_set_uid  bigint      NOT NULL,
    role_guest_ind     char(1),
    read_only_ind      char(1),
    disp_seq_nbr       int,
    add_time           datetime    NOT NULL,
    add_user_id        bigint      NOT NULL,
    last_chg_time      datetime    NOT NULL,
    last_chg_user_id   bigint      NOT NULL,
    record_status_cd   varchar(20) NOT NULL,
    record_status_time datetime    NOT NULL,
    CONSTRAINT pk_auth_user_role PRIMARY KEY (auth_user_role_uid)
)
    GO

CREATE TABLE bus_obj_df_sf_mdata_group
(
    business_object_uid      bigint   NOT NULL,
    version_ctrl_nbr         smallint NOT NULL,
    df_sf_metadata_group_uid bigint   NOT NULL,
    CONSTRAINT pk_bus_obj_df_sf_mdata_group PRIMARY KEY (business_object_uid)
)
    GO

CREATE TABLE case_management
(
    case_management_uid           bigint NOT NULL,
    public_health_case_uid        bigint NOT NULL,
    status_900                    varchar(20),
    ehars_id                      varchar(10),
    epi_link_id                   varchar(20),
    field_foll_up_ooj_outcome     varchar(20),
    field_record_number           varchar(20),
    fld_foll_up_dispo             varchar(20),
    fld_foll_up_dispo_date        datetime,
    fld_foll_up_exam_date         datetime,
    fld_foll_up_expected_date     datetime,
    fld_foll_up_expected_in       varchar(20),
    fld_foll_up_internet_outcome  varchar(20),
    fld_foll_up_notification_plan varchar(20),
    fld_foll_up_prov_diagnosis    varchar(20),
    fld_foll_up_prov_exm_reason   varchar(20),
    init_foll_up                  varchar(20),
    init_foll_up_clinic_code      varchar(50),
    init_foll_up_closed_date      datetime,
    init_foll_up_notifiable       varchar(20),
    internet_foll_up              varchar(20),
    ooj_agency                    varchar(20),
    ooj_due_date                  datetime,
    ooj_number                    varchar(20),
    pat_intv_status_cd            varchar(20),
    subj_complexion               varchar(20),
    subj_hair                     varchar(20),
    subj_height                   varchar(20),
    subj_oth_idntfyng_info        varchar(2000),
    subj_size_build               varchar(20),
    surv_closed_date              datetime,
    surv_patient_foll_up          varchar(20),
    surv_prov_diagnosis           varchar(20),
    surv_prov_exm_reason          varchar(20),
    surv_provider_contact         varchar(20),
    act_ref_type_cd               varchar(20),
    initiating_agncy              varchar(20),
    ooj_initg_agncy_outc_due_date datetime,
    ooj_initg_agncy_outc_snt_date datetime,
    ooj_initg_agncy_recd_date     datetime,
    case_review_status            varchar(20),
    surv_assigned_date            datetime,
    foll_up_assigned_date         datetime,
    init_foll_up_assigned_date    datetime,
    interview_assigned_date       datetime,
    init_interview_assigned_date  datetime,
    case_closed_date              datetime,
    case_review_status_date       datetime,
    CONSTRAINT pk_case_management PRIMARY KEY (case_management_uid)
)
    GO

CREATE TABLE case_management_hist
(
    case_management_hist_uid      bigint   NOT NULL,
    case_management_uid           bigint   NOT NULL,
    public_health_case_uid        bigint   NOT NULL,
    version_ctrl_nbr              smallint NOT NULL,
    status_900                    varchar(20),
    ehars_id                      varchar(10),
    epi_link_id                   varchar(20),
    field_foll_up_ooj_outcome     varchar(20),
    field_record_number           varchar(20),
    fld_foll_up_dispo             varchar(20),
    fld_foll_up_dispo_date        datetime,
    fld_foll_up_exam_date         datetime,
    fld_foll_up_expected_date     datetime,
    fld_foll_up_expected_in       varchar(20),
    fld_foll_up_internet_outcome  varchar(20),
    fld_foll_up_notification_plan varchar(20),
    fld_foll_up_prov_diagnosis    varchar(20),
    fld_foll_up_prov_exm_reason   varchar(20),
    init_foll_up                  varchar(20),
    init_foll_up_clinic_code      varchar(50),
    init_foll_up_closed_date      datetime,
    init_foll_up_notifiable       varchar(20),
    internet_foll_up              varchar(20),
    ooj_agency                    varchar(20),
    ooj_due_date                  datetime,
    ooj_number                    varchar(20),
    pat_intv_status_cd            varchar(20),
    subj_complexion               varchar(20),
    subj_hair                     varchar(20),
    subj_height                   varchar(20),
    subj_oth_idntfyng_info        varchar(2000),
    subj_size_build               varchar(20),
    surv_closed_date              datetime,
    surv_patient_foll_up          varchar(20),
    surv_prov_diagnosis           varchar(20),
    surv_prov_exm_reason          varchar(20),
    surv_provider_contact         varchar(20),
    act_ref_type_cd               varchar(20),
    initiating_agncy              varchar(20),
    ooj_initg_agncy_outc_due_date datetime,
    ooj_initg_agncy_outc_snt_date datetime,
    ooj_initg_agncy_recd_date     datetime,
    case_review_status            varchar(20),
    surv_assigned_date            datetime,
    foll_up_assigned_date         datetime,
    init_foll_up_assigned_date    datetime,
    interview_assigned_date       datetime,
    init_interview_assigned_date  datetime,
    case_closed_date              datetime,
    case_review_status_date       datetime,
    CONSTRAINT pk_case_management_hist PRIMARY KEY (case_management_hist_uid)
)
    GO

CREATE TABLE cdf_subform_import_data_log
(
    action_type      varchar(20),
    import_time      datetime,
    log_message_txt  varchar(2000),
    process_cd       varchar(20),
    version_ctrl_nbr smallint    NOT NULL,
    import_log_uid   bigint      NOT NULL,
    data_oid         varchar(50) NOT NULL,
    data_type        varchar(20) NOT NULL,
    CONSTRAINT pk_cdf_subform_import_data_log PRIMARY KEY (import_log_uid, data_oid, data_type)
)
    GO

CREATE TABLE cdf_subform_import_log
(
    import_log_uid     bigint   NOT NULL,
    log_message_txt    varchar(2000),
    import_time        datetime,
    import_version_nbr bigint   NOT NULL,
    process_cd         varchar(20),
    source_nm          varchar(200),
    version_ctrl_nbr   smallint NOT NULL,
    admin_comment      varchar(300),
    CONSTRAINT pk_cdf_subform_import_log PRIMARY KEY (import_log_uid)
)
    GO

CREATE TABLE chart_report_metadata
(
    chart_report_metadata_uid   bigint       NOT NULL,
    chart_report_cd             varchar(20)  NOT NULL,
    chart_report_desc_txt       varchar(250) NOT NULL,
    external_class_nm           varchar(250),
    external_method_nm          varchar(250),
    x_axis_title                varchar(250),
    y_axis_title                varchar(250),
    secured_ind_cd              char(1),
    chart_type_uid              bigint       NOT NULL,
    chart_report_short_desc_txt varchar(30)  NOT NULL,
    object_nm                   varchar(30),
    operation_nm                varchar(30),
    secured_by_object_operation char(1),
    record_status_cd            varchar(20)  NOT NULL,
    record_status_time          datetime,
    add_user_id                 bigint,
    add_time                    datetime,
    last_chg_user_id            bigint,
    last_chg_time               datetime,
    default_ind_cd              char(1),
    CONSTRAINT pk_chart_report_metadata PRIMARY KEY (chart_report_metadata_uid)
)
    GO

CREATE TABLE chart_type
(
    chart_type_uid      bigint       NOT NULL,
    chart_type_cd       varchar(20)  NOT NULL,
    chart_type_desc_txt varchar(250) NOT NULL,
    record_status_cd    varchar(20)  NOT NULL,
    record_status_time  datetime,
    add_user_id         bigint,
    add_time            datetime,
    last_chg_user_id    bigint,
    last_chg_time       datetime,
    CONSTRAINT pk_chart_type PRIMARY KEY (chart_type_uid)
)
    GO

CREATE TABLE clinical_document
(
    clinical_document_uid      bigint   NOT NULL,
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    copy_from_time             datetime,
    copy_to_time               datetime,
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    practice_setting_cd        varchar(20),
    practice_setting_desc_txt  varchar(100),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    status_cd                  char(1),
    status_time                datetime,
    txt                        varchar(2000),
    user_affiliation_txt       varchar(20),
    version_nbr                smallint,
    program_jurisdiction_oid   bigint,
    shared_ind                 char(1)  NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_clinical_document PRIMARY KEY (clinical_document_uid)
)
    GO

CREATE TABLE clinical_document_hist
(
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    copy_from_time             datetime,
    copy_to_time               datetime,
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    practice_setting_cd        varchar(20),
    practice_setting_desc_txt  varchar(100),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    status_cd                  char(1),
    status_time                datetime,
    txt                        varchar(2000),
    user_affiliation_txt       varchar(20),
    version_nbr                smallint,
    program_jurisdiction_oid   bigint,
    shared_ind                 char(1)  NOT NULL,
    clinical_document_uid      bigint   NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_clinical_document_hist PRIMARY KEY (clinical_document_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE cn_transportq_out
(
    cn_transportq_out_uid bigint NOT NULL,
    add_reason_cd         varchar(20),
    add_time              datetime,
    add_user_id           bigint,
    last_chg_reason_cd    varchar(20),
    last_chg_time         datetime,
    last_chg_user_id      bigint,
    message_payload       varchar(MAX
) ,
    notification_uid            bigint NOT NULL,
    notification_local_id       varchar(50),
    public_health_case_local_id varchar(50),
    report_status_cd            varchar(20),
    record_status_cd            varchar(20),
    record_status_time          datetime,
    version_ctrl_nbr            smallint,
    CONSTRAINT pk_cn_transportq_out PRIMARY KEY (cn_transportq_out_uid)
) GO

CREATE TABLE confirmation_method
(
    confirmation_method_desc_txt varchar(100),
    confirmation_method_time     datetime,
    public_health_case_uid       bigint      NOT NULL,
    confirmation_method_cd       varchar(20) NOT NULL,
    CONSTRAINT pk_confirmation_method PRIMARY KEY (public_health_case_uid, confirmation_method_cd)
)
    GO

CREATE TABLE confirmation_method_hist
(
    confirmation_method_desc_txt varchar(80),
    confirmation_method_time     datetime,
    public_health_case_uid       bigint      NOT NULL,
    confirmation_method_cd       varchar(20) NOT NULL,
    version_ctrl_nbr             smallint    NOT NULL,
    CONSTRAINT pk_confirmation_method_hist PRIMARY KEY (public_health_case_uid, confirmation_method_cd, version_ctrl_nbr)
)
    GO

CREATE TABLE consequence_indicator
(
    conseq_ind_uid      bigint NOT NULL,
    conseq_ind_code     varchar(1),
    conseq_ind_desc_txt varchar(100),
    conseq_ind_type     varchar(25),
    CONSTRAINT pk_consequence_indicator PRIMARY KEY (conseq_ind_uid)
)
    GO

CREATE TABLE ct_contact
(
    ct_contact_uid             bigint      NOT NULL,
    local_id                   varchar(50) NOT NULL,
    subject_entity_uid         bigint      NOT NULL,
    contact_entity_uid         bigint      NOT NULL,
    subject_entity_phc_uid     bigint      NOT NULL,
    contact_entity_phc_uid     bigint,
    record_status_cd           varchar(20) NOT NULL,
    record_status_time         datetime    NOT NULL,
    add_user_id                bigint      NOT NULL,
    add_time                   datetime    NOT NULL,
    last_chg_time              datetime    NOT NULL,
    last_chg_user_id           bigint      NOT NULL,
    prog_area_cd               varchar(20),
    jurisdiction_cd            varchar(20),
    program_jurisdiction_oid   bigint,
    shared_ind_cd              char(1),
    contact_status             varchar(20),
    priority_cd                varchar(20),
    group_name_cd              varchar(20),
    investigator_assigned_date datetime,
    disposition_cd             varchar(20),
    disposition_date           datetime,
    named_on_date              datetime,
    relationship_cd            varchar(20),
    health_status_cd           varchar(20),
    txt                        varchar(2000),
    symptom_cd                 varchar(20),
    symptom_onset_date         datetime,
    symptom_txt                varchar(2000),
    risk_factor_cd             varchar(20),
    risk_factor_txt            varchar(2000),
    evaluation_completed_cd    varchar(20),
    evaluation_date            datetime,
    evaluation_txt             varchar(2000),
    treatment_initiated_cd     varchar(20),
    treatment_start_date       datetime,
    treatment_not_start_rsn_cd varchar(20),
    treatment_end_cd           varchar(20),
    treatment_end_date         datetime,
    treatment_not_end_rsn_cd   varchar(20),
    treatment_txt              varchar(2000),
    version_ctrl_nbr           smallint    NOT NULL,
    shared_ind                 char(1),
    third_party_entity_uid     bigint,
    third_party_entity_phc_uid bigint,
    processing_decision_cd     varchar(20),
    subject_entity_epi_link_id varchar(20),
    contact_entity_epi_link_id varchar(20),
    named_during_interview_uid bigint,
    contact_referral_basis_cd  varchar(20),
    CONSTRAINT pk_ct_contact PRIMARY KEY (ct_contact_uid)
)
    GO

CREATE TABLE ct_contact_answer
(
    ct_contact_answer_uid         bigint      NOT NULL,
    ct_contact_uid                bigint      NOT NULL,
    answer_txt                    varchar(2000),
    nbs_question_uid              bigint      NOT NULL,
    nbs_question_version_ctrl_nbr smallint    NOT NULL,
    last_chg_time                 datetime    NOT NULL,
    last_chg_user_id              bigint      NOT NULL,
    record_status_cd              varchar(20) NOT NULL,
    record_status_time            datetime    NOT NULL,
    seq_nbr                       smallint,
    answer_large_txt              varchar(MAX
) ,
    answer_group_seq_nbr          int,
    CONSTRAINT pk_ct_contact_answer PRIMARY KEY (ct_contact_answer_uid)
) GO

CREATE TABLE ct_contact_answer_hist
(
    ct_contact_answer_hist_uid    bigint      NOT NULL,
    ct_contact_answer_uid         bigint      NOT NULL,
    ct_contact_uid                bigint      NOT NULL,
    answer_txt                    varchar(2000),
    nbs_question_uid              bigint      NOT NULL,
    nbs_question_version_ctrl_nbr smallint    NOT NULL,
    ct_contact_version_ctrl_nbr   bigint      NOT NULL,
    last_chg_time                 datetime    NOT NULL,
    last_chg_user_id              bigint      NOT NULL,
    record_status_cd              varchar(20) NOT NULL,
    record_status_time            datetime    NOT NULL,
    seq_nbr                       smallint,
    answer_large_txt              varchar(MAX
) ,
    answer_group_seq_nbr          int,
    CONSTRAINT pk_ct_contact_answer_hist PRIMARY KEY (ct_contact_answer_hist_uid)
) GO

CREATE TABLE ct_contact_attachment
(
    ct_contact_attachment_uid bigint   NOT NULL,
    ct_contact_uid            bigint   NOT NULL,
    desc_txt                  varchar(2000),
    last_chg_time             datetime NOT NULL,
    last_chg_user_id          bigint   NOT NULL,
    attachment                varbinary( MAX),
    file_nm_txt               varchar(250),
    CONSTRAINT pk_ct_contact_attachment PRIMARY KEY (ct_contact_attachment_uid)
)
    GO

CREATE TABLE ct_contact_hist
(
    ct_contact_hist_uid        bigint      NOT NULL,
    ct_contact_uid             bigint      NOT NULL,
    local_id                   varchar(50) NOT NULL,
    subject_entity_uid         bigint      NOT NULL,
    contact_entity_uid         bigint      NOT NULL,
    subject_entity_phc_uid     bigint      NOT NULL,
    contact_entity_phc_uid     bigint,
    record_status_cd           varchar(20) NOT NULL,
    record_status_time         datetime    NOT NULL,
    add_user_id                bigint      NOT NULL,
    add_time                   datetime    NOT NULL,
    last_chg_time              datetime    NOT NULL,
    last_chg_user_id           bigint      NOT NULL,
    prog_area_cd               varchar(20),
    jurisdiction_cd            varchar(20),
    program_jurisdiction_oid   bigint,
    shared_ind_cd              char(1),
    contact_status             varchar(20),
    priority_cd                varchar(20),
    group_name_cd              varchar(20),
    investigator_assigned_date datetime,
    disposition_cd             varchar(20),
    disposition_date           datetime,
    named_on_date              datetime,
    relationship_cd            varchar(20),
    health_status_cd           varchar(20),
    txt                        varchar(2000),
    symptom_cd                 varchar(20),
    symptom_onset_date         datetime,
    symptom_txt                varchar(2000),
    risk_factor_cd             varchar(20),
    risk_factor_txt            varchar(2000),
    evaluation_completed_cd    varchar(20),
    evaluation_date            datetime,
    evaluation_txt             varchar(2000),
    treatment_initiated_cd     varchar(20),
    treatment_start_date       datetime,
    treatment_not_start_rsn_cd varchar(20),
    treatment_end_cd           varchar(20),
    treatment_end_date         datetime,
    treatment_not_end_rsn_cd   varchar(20),
    treatment_txt              varchar(2000),
    version_ctrl_nbr           smallint    NOT NULL,
    third_party_entity_uid     bigint,
    third_party_entity_phc_uid bigint,
    processing_decision_cd     varchar(20),
    subject_entity_epi_link_id varchar(20),
    contact_entity_epi_link_id varchar(20),
    named_during_interview_uid bigint,
    contact_referral_basis_cd  varchar(20),
    CONSTRAINT pk_ct_contact_hist PRIMARY KEY (ct_contact_hist_uid)
)
    GO

CREATE TABLE ct_contact_note
(
    ct_contact_note_uid bigint        NOT NULL,
    ct_contact_uid      bigint        NOT NULL,
    record_status_cd    varchar(20)   NOT NULL,
    record_status_time  datetime      NOT NULL,
    add_time            datetime      NOT NULL,
    add_user_id         bigint        NOT NULL,
    last_chg_time       datetime      NOT NULL,
    last_chg_user_id    bigint        NOT NULL,
    note                varchar(2000) NOT NULL,
    private_ind_cd      char(1)       NOT NULL,
    CONSTRAINT pk_ct_contact_note PRIMARY KEY (ct_contact_note_uid)
)
    GO

CREATE TABLE custom_subform_metadata
(
    custom_subform_metadata_uid bigint   NOT NULL,
    add_time                    datetime NOT NULL,
    admin_comment               varchar(300),
    business_object_nm          varchar(50),
    class_cd                    varchar(20),
    condition_cd                varchar(20),
    condition_desc_txt          varchar(100),
    display_order_nbr           int      NOT NULL,
    html_data                   varchar(MAX
) ,
    import_version_nbr          bigint      NOT NULL,
    deployment_cd               varchar(20),
    page_set_id                 varchar(50),
    record_status_cd            varchar(20) NOT NULL,
    record_status_time          datetime    NOT NULL,
    state_cd                    varchar(20),
    status_cd                   char(1),
    subform_oid                 varchar(50),
    subform_nm                  varchar(100),
    version_ctrl_nbr            smallint    NOT NULL,
    CONSTRAINT pk_custom_subform_metadata PRIMARY KEY (custom_subform_metadata_uid)
) GO

CREATE TABLE data_migration_batch
(
    data_migration_batch_uid bigint NOT NULL,
    batch_nm                 varchar(300),
    batch_start_time         datetime,
    batch_end_time           datetime,
    records_to_migrate_nbr   smallint,
    records_migrated_nbr     smallint,
    records_failed_nbr       smallint,
    CONSTRAINT pk_data_migration_batch PRIMARY KEY (data_migration_batch_uid)
)
    GO

CREATE TABLE data_migration_detail
(
    failed_detail_txt         varchar(4000),
    failed_reason_txt         varchar(300),
    data_migration_detail_uid bigint NOT NULL,
    data_migration_batch_uid  bigint NOT NULL,
    data_migration_record_uid bigint NOT NULL,
    CONSTRAINT pk_data_migration_detail PRIMARY KEY (data_migration_detail_uid, data_migration_batch_uid,
                                                     data_migration_record_uid)
)
    GO

CREATE TABLE data_migration_record
(
    case_id_txt        varchar(100),
    data_migration_sts varchar(10),
    failed_record_txt  varchar(MAX
) ,
    sub_nm                    varchar(100),
    data_migration_record_uid bigint NOT NULL,
    data_migration_batch_uid  bigint NOT NULL,
    CONSTRAINT pk_data_migration_record PRIMARY KEY (data_migration_record_uid, data_migration_batch_uid)
) GO

CREATE TABLE data_source
(
    data_source_uid             bigint  NOT NULL,
    column_max_len              smallint,
    condition_security          char(1),
    data_source_name            varchar(50),
    data_source_title           varchar(50),
    data_source_type_code       varchar(20),
    desc_txt                    varchar(300),
    effective_from_time         datetime,
    effective_to_time           datetime,
    jurisdiction_security       char(1),
    org_access_permis           varchar(2000),
    prog_area_access_permis     varchar(2000),
    status_cd                   char(1) NOT NULL,
    status_time                 datetime,
    reporting_facility_security char(1),
    CONSTRAINT pk_data_source PRIMARY KEY (data_source_uid)
)
    GO

CREATE TABLE data_source_code_data
(
    data_source_codedata_uid bigint        NOT NULL,
    data_source_name         varchar(300),
    column_name              varchar(300),
    codeset_name             varchar(2000) NOT NULL,
    code_desc_cd             varchar(1),
    status_cd                char(1),
    status_time              datetime,
    CONSTRAINT pk_data_source_codedata PRIMARY KEY (data_source_codedata_uid)
)
    GO

CREATE TABLE data_source_codeset
(
    data_source_codeset_uid bigint NOT NULL,
    column_uid              bigint,
    code_desc_cd            varchar(20),
    codeset_nm              varchar(256),
    status_cd               char(1),
    status_time             datetime,
    CONSTRAINT pk_data_source_codeset PRIMARY KEY (data_source_codeset_uid)
)
    GO

CREATE TABLE data_source_column
(
    column_uid          bigint  NOT NULL,
    column_max_len      int,
    column_name         varchar(50),
    column_title        varchar(60),
    column_type_code    varchar(20),
    data_source_uid     bigint  NOT NULL,
    desc_txt            varchar(300),
    displayable         char(1),
    effective_from_time datetime,
    effective_to_time   datetime,
    filterable          char(1),
    status_cd           char(1) NOT NULL,
    status_time         datetime,
    CONSTRAINT pk_data_source_column PRIMARY KEY (column_uid)
)
    GO

CREATE TABLE data_source_operator
(
    data_source_operator_uid bigint  NOT NULL,
    filter_operator_uid      bigint,
    column_type_code         varchar(20),
    status_cd                char(1) NOT NULL,
    status_time              datetime,
    CONSTRAINT pk_data_source_operator PRIMARY KEY (data_source_operator_uid)
)
    GO

CREATE TABLE deduplication_activity_log
(
    deduplication_activity_log_uid bigint NOT NULL,
    batch_start_time               datetime,
    batch_end_time                 datetime,
    merged_records_identified_nbr  smallint,
    merged_records_survived_nbr    smallint,
    override_ind                   char(1),
    similar_group_nbr              int,
    process_type                   varchar(50),
    process_exception              varchar(2000),
    CONSTRAINT pk_deduplication_activity_log PRIMARY KEY (deduplication_activity_log_uid)
)
    GO

CREATE TABLE df_sf_mdata_field_group
(
    field_type               varchar(20) NOT NULL,
    version_ctrl_nbr         smallint    NOT NULL,
    df_sf_metadata_group_uid bigint      NOT NULL,
    field_uid                bigint      NOT NULL,
    CONSTRAINT pk_df_sf_mdata_field_group PRIMARY KEY (df_sf_metadata_group_uid, field_uid)
)
    GO

CREATE TABLE df_sf_metadata_group
(
    df_sf_metadata_group_uid bigint   NOT NULL,
    group_name               varchar(3000),
    version_ctrl_nbr         smallint NOT NULL,
    CONSTRAINT pk_df_sf_metadata_group PRIMARY KEY (df_sf_metadata_group_uid)
)
    GO

CREATE TABLE display_column
(
    display_column_uid bigint   NOT NULL,
    column_uid         bigint   NOT NULL,
    sequence_nbr       smallint NOT NULL,
    status_cd          char(1)  NOT NULL,
    status_time        datetime,
    data_source_uid    bigint   NOT NULL,
    report_uid         bigint   NOT NULL,
    CONSTRAINT pk_display_column PRIMARY KEY (display_column_uid)
)
    GO

CREATE TABLE dsm_algorithm
(
    dsm_algorithm_uid     bigint NOT NULL,
    algorithm_nm          varchar(250),
    event_type            varchar(50),
    condition_list        varchar(250),
    frequency             varchar(50),
    apply_to              varchar(50),
    sending_system_list   varchar(250),
    reporting_system_list varchar(250),
    event_action          varchar(50),
    algorithm_payload     varchar(MAX
) ,
    admin_comment         varchar(2000),
    status_cd             varchar(50) NOT NULL,
    status_time           datetime    NOT NULL,
    last_chg_user_id      bigint      NOT NULL,
    last_chg_time         datetime    NOT NULL,
    resulted_test_list    varchar(8000),
    CONSTRAINT pk_dsm_algorithm PRIMARY KEY (dsm_algorithm_uid)
) GO

CREATE TABLE dsm_algorithm_hist
(
    dsm_algorithm_hist_uid bigint NOT NULL,
    dsm_algorithm_uid      bigint NOT NULL,
    version_ctrl_nbr       int,
    algorithm_nm           varchar(250),
    event_type             varchar(50),
    condition_list         varchar(250),
    frequency              varchar(50),
    apply_to               varchar(50),
    sending_system_list    varchar(250),
    reporting_system_list  varchar(250),
    event_action           varchar(50),
    algorithm_payload      varchar(MAX
) ,
    admin_comment          varchar(2000),
    status_cd              varchar(50) NOT NULL,
    status_time            datetime    NOT NULL,
    last_chg_user_id       bigint      NOT NULL,
    last_chg_time          datetime    NOT NULL,
    resulted_test_list     varchar(8000),
    CONSTRAINT pk_dsm_algorithm_hist PRIMARY KEY (dsm_algorithm_hist_uid)
) GO

CREATE TABLE dtproperties
(
    objectid int,
    value    varchar(255),
    uvalue   varchar(255),
    lvalue   varbinary( MAX),
    version  int         NOT NULL,
    id       int         NOT NULL,
    property varchar(64) NOT NULL,
    CONSTRAINT pk_dtproperties PRIMARY KEY (id, property)
)
    GO

CREATE TABLE edx_activity_detail_log
(
    edx_activity_detail_log_uid bigint NOT NULL,
    edx_activity_log_uid        bigint NOT NULL,
    record_id                   varchar(256),
    record_type                 varchar(50),
    record_nm                   varchar(250),
    log_type                    varchar(50),
    log_comment                 varchar(2000),
    CONSTRAINT pk_edx_activity_detail_log PRIMARY KEY (edx_activity_detail_log_uid)
)
    GO

CREATE TABLE edx_activity_log
(
    edx_activity_log_uid bigint NOT NULL,
    source_uid           bigint,
    target_uid           bigint,
    doc_type             varchar(50),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    exception_txt        varchar(MAX
) ,
    imp_exp_ind_cd        char(1),
    source_type_cd        varchar(50),
    target_type_cd        varchar(50),
    business_obj_local_id varchar(50),
    doc_nm                varchar(250),
    source_nm             varchar(250),
    algorithm_action      varchar(10),
    algorithm_name        varchar(250),
    message_id            varchar(255),
    entity_nm             varchar(255),
    accession_nbr         varchar(100),
    CONSTRAINT pk_edx_activity_log PRIMARY KEY (edx_activity_log_uid)
) GO

CREATE TABLE edx_document
(
    edx_document_uid bigint NOT NULL,
    act_uid          bigint,
    payload          varchar(MAX
) NOT NULL,
    record_status_cd          varchar(20)  NOT NULL,
    record_status_time        datetime     NOT NULL,
    add_time                  datetime     NOT NULL,
    doc_type_cd               varchar(20)  NOT NULL,
    nbs_document_metadata_uid bigint       NOT NULL,
    original_payload          varchar(MAX),
    original_doc_type_cd      varchar(20),
    edx_document_parent_uid   bigint,
    CONSTRAINT pk_edx_document PRIMARY KEY (edx_document_uid)
) GO

CREATE TABLE edx_entity_match
(
    edx_entity_match_uid  bigint        NOT NULL,
    entity_uid            bigint        NOT NULL,
    match_string          varchar(2000) NOT NULL,
    type_cd               varchar(100)  NOT NULL,
    match_string_hashcode bigint        NOT NULL,
    CONSTRAINT pk_edx_entity_match PRIMARY KEY (edx_entity_match_uid)
)
    GO

CREATE TABLE edx_event_process
(
    edx_event_process_uid bigint      NOT NULL,
    nbs_document_uid      bigint,
    nbs_event_uid         bigint      NOT NULL,
    source_event_id       varchar(250),
    doc_event_type_cd     varchar(50) NOT NULL,
    add_user_id           bigint      NOT NULL,
    add_time              datetime    NOT NULL,
    parsed_ind            char(1),
    edx_document_uid      bigint,
    CONSTRAINT pk_edx_event_process PRIMARY KEY (edx_event_process_uid)
)
    GO

CREATE TABLE edx_patient_match
(
    edx_patient_match_uid bigint        NOT NULL,
    patient_uid           bigint        NOT NULL,
    match_string          varchar(2000) NOT NULL,
    type_cd               varchar(100)  NOT NULL,
    match_string_hashcode bigint        NOT NULL,
    CONSTRAINT pk_edx_patient_match PRIMARY KEY (edx_patient_match_uid)
)
    GO

CREATE TABLE elr_activity_log
(
    filler_nbr           varchar(100),
    id                   varchar(100),
    ods_observation_uid  varchar(50),
    status_cd            char(1)     NOT NULL,
    process_time         datetime    NOT NULL,
    process_cd           varchar(20) NOT NULL,
    subject_nm           varchar(100),
    report_fac_nm        varchar(100),
    detail_txt           varchar(1000),
    msg_observation_uid  bigint      NOT NULL,
    elr_activity_log_seq smallint    NOT NULL,
    CONSTRAINT pk_elr_activity_log PRIMARY KEY (msg_observation_uid, elr_activity_log_seq)
)
    GO

CREATE TABLE entity
(
    entity_uid bigint      NOT NULL,
    class_cd   varchar(10) NOT NULL,
    CONSTRAINT pk_entity PRIMARY KEY (entity_uid)
)
    GO

CREATE TABLE entity_group
(
    entity_group_uid     bigint   NOT NULL,
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    cd                   varchar(50),
    cd_desc_txt          varchar(100),
    description          varchar(1000),
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    from_time            datetime,
    group_cnt            smallint,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    local_id             varchar(50),
    nm                   varchar(100),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    status_cd            char(1),
    status_time          datetime,
    to_time              datetime,
    user_affiliation_txt varchar(20),
    version_ctrl_nbr     smallint NOT NULL,
    CONSTRAINT pk_entity_group PRIMARY KEY (entity_group_uid)
)
    GO

CREATE TABLE entity_group_hist
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    cd                   varchar(50),
    cd_desc_txt          varchar(100),
    description          varchar(1000),
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    from_time            datetime,
    group_cnt            smallint,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    local_id             varchar(50),
    nm                   varchar(100),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    status_cd            char(1),
    status_time          datetime,
    to_time              datetime,
    user_affiliation_txt varchar(20),
    entity_group_uid     bigint   NOT NULL,
    version_ctrl_nbr     smallint NOT NULL,
    CONSTRAINT pk_entity_group_hist PRIMARY KEY (entity_group_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE entity_id
(
    add_reason_cd                varchar(20),
    add_time                     datetime,
    add_user_id                  bigint,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    duration_amt                 varchar(20),
    duration_unit_cd             varchar(20),
    effective_from_time          datetime,
    effective_to_time            datetime,
    last_chg_reason_cd           varchar(20),
    last_chg_time                datetime,
    last_chg_user_id             bigint,
    record_status_cd             varchar(20),
    record_status_time           datetime,
    root_extension_txt           varchar(100),
    status_cd                    char(1),
    status_time                  datetime,
    type_cd                      varchar(50),
    type_desc_txt                varchar(100),
    user_affiliation_txt         varchar(20),
    valid_from_time              datetime,
    valid_to_time                datetime,
    as_of_date                   datetime,
    assigning_authority_id_type  varchar(50),
    entity_uid                   bigint   NOT NULL,
    entity_id_seq                smallint NOT NULL,
    CONSTRAINT pk_entity_id PRIMARY KEY (entity_uid, entity_id_seq)
)
    GO

CREATE TABLE entity_id_hist
(
    add_reason_cd                varchar(20),
    add_time                     datetime,
    add_user_id                  bigint,
    assigning_authority_cd       varchar(199),
    assigning_authority_desc_txt varchar(100),
    duration_amt                 varchar(20),
    duration_unit_cd             varchar(20),
    effective_from_time          datetime,
    effective_to_time            datetime,
    last_chg_reason_cd           varchar(20),
    last_chg_time                datetime,
    last_chg_user_id             bigint,
    record_status_cd             varchar(20),
    record_status_time           datetime,
    root_extension_txt           varchar(100),
    status_cd                    char(1),
    status_time                  datetime,
    type_cd                      varchar(50),
    type_desc_txt                varchar(100),
    user_affiliation_txt         varchar(20),
    valid_from_time              datetime,
    valid_to_time                datetime,
    as_of_date                   datetime,
    assigning_authority_id_type  varchar(50),
    entity_uid                   bigint   NOT NULL,
    entity_id_seq                smallint NOT NULL,
    version_ctrl_nbr             smallint NOT NULL,
    CONSTRAINT pk_entity_id_hist PRIMARY KEY (entity_uid, entity_id_seq, version_ctrl_nbr)
)
    GO

CREATE TABLE entity_loc_participation_hist
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    cd                   varchar(50),
    cd_desc_txt          varchar(100),
    class_cd             varchar(10),
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    from_time            datetime,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    locator_desc_txt     varchar(2000),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    status_cd            char(1)  NOT NULL,
    status_time          datetime NOT NULL,
    to_time              datetime,
    use_cd               varchar(20),
    user_affiliation_txt varchar(20),
    valid_time_txt       varchar(100),
    as_of_date           datetime,
    entity_uid           bigint   NOT NULL,
    locator_uid          bigint   NOT NULL,
    version_ctrl_nbr     smallint NOT NULL,
    CONSTRAINT pk_entity_loc_participation_hist PRIMARY KEY (entity_uid, locator_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE entity_locator_participation
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    cd                   varchar(50),
    cd_desc_txt          varchar(100),
    class_cd             varchar(10),
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    from_time            datetime,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    locator_desc_txt     varchar(2000),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    status_cd            char(1),
    status_time          datetime,
    to_time              datetime,
    use_cd               varchar(20),
    user_affiliation_txt varchar(20),
    valid_time_txt       varchar(100),
    version_ctrl_nbr     smallint NOT NULL,
    as_of_date           datetime,
    entity_uid           bigint   NOT NULL,
    locator_uid          bigint   NOT NULL,
    CONSTRAINT pk_entity_locator_participation PRIMARY KEY (entity_uid, locator_uid)
)
    GO

CREATE TABLE error_message
(
    error_message_uid bigint      NOT NULL,
    error_cd          varchar(20) NOT NULL,
    error_desc_txt    varchar(150),
    CONSTRAINT pk_error_message PRIMARY KEY (error_message_uid)
)
    GO

CREATE TABLE export_receiving_facility
(
    export_receiving_facility_uid bigint       NOT NULL,
    add_time                      datetime     NOT NULL,
    add_user_id                   bigint       NOT NULL,
    last_chg_time                 datetime     NOT NULL,
    last_chg_user_id              varchar(50)  NOT NULL,
    receiving_system_nm           varchar(100),
    receiving_system_oid          varchar(100),
    record_status_cd              varchar(20)  NOT NULL,
    message_recipient             varchar(255),
    public_key_ldap_address       varchar(255),
    public_key_ldap_basedn        varchar(50),
    public_key_ldapdn             varchar(255),
    priority_int                  smallint,
    encrypt                       varchar(10),
    signature                     varchar(10),
    receiving_system_short_nm     varchar(10)  NOT NULL,
    receiving_system_owner        varchar(10)  NOT NULL,
    receiving_system_desc_txt     varchar(2000),
    sending_ind_cd                char(1)      NOT NULL,
    receiving_ind_cd              char(1),
    allow_transfer_ind_cd         char(1),
    admin_comment                 varchar(2000),
    receiving_system_owner_oid    varchar(100) NOT NULL,
    jur_derive_ind_cd             varchar(10),
    type_cd                       varchar(20),
    CONSTRAINT pk_export_receiving_facility PRIMARY KEY (export_receiving_facility_uid)
)
    GO

CREATE TABLE filter_code
(
    filter_uid          bigint      NOT NULL,
    code_table          varchar(50) NOT NULL,
    desc_txt            varchar(300),
    effective_from_time datetime,
    effective_to_time   datetime,
    filter_code         varchar(20),
    filter_code_set_nm  varchar(256),
    filter_type         varchar(20),
    filter_name         varchar(50),
    status_cd           char(1),
    status_time         datetime,
    CONSTRAINT pk_filter_code PRIMARY KEY (filter_uid)
)
    GO

CREATE TABLE filter_operator
(
    filter_operator_uid  bigint NOT NULL,
    filter_operator_code varchar(20),
    filter_operator_desc varchar(200),
    status_cd            char(1),
    status_time          datetime,
    CONSTRAINT pk_filter_operator PRIMARY KEY (filter_operator_uid)
)
    GO

CREATE TABLE filter_value
(
    value_uid         bigint NOT NULL,
    report_filter_uid bigint NOT NULL,
    sequence_nbr      smallint,
    value_type        varchar(20),
    column_uid        bigint,
    operator          varchar(20),
    value_txt         varchar(2000),
    CONSTRAINT pk_filter_value PRIMARY KEY (value_uid)
)
    GO

CREATE TABLE form_rule_instance
(
    form_rule_instance_uid bigint      NOT NULL,
    rule_instance_uid      bigint      NOT NULL,
    form_code              varchar(20) NOT NULL,
    record_status_cd       varchar(20),
    CONSTRAINT pk_form_rule_instance PRIMARY KEY (form_rule_instance_uid)
)
    GO

CREATE TABLE geocoding_activity_log
(
    geocoding_activity_log_uid bigint NOT NULL,
    batch_run_mode             varchar(1),
    batch_start_time           datetime,
    batch_end_time             datetime,
    completed_ind              varchar(1),
    completion_reason          varchar(1000),
    total_nbr                  int,
    single_match_nbr           int,
    multi_match_nbr            int,
    zero_match_nbr             int,
    error_record_nbr           int,
    error_nbr                  int,
    CONSTRAINT pk_geocoding_activity_log PRIMARY KEY (geocoding_activity_log_uid)
)
    GO

CREATE TABLE geocoding_result
(
    geocoding_result_uid bigint     NOT NULL,
    postal_locator_uid   bigint     NOT NULL,
    add_time             datetime   NOT NULL,
    add_user_id          bigint,
    last_chg_time        datetime   NOT NULL,
    last_chg_user_id     bigint,
    firm_name            varchar(50),
    street_addr1         varchar(100),
    street_addr2         varchar(100),
    city                 varchar(50),
    state                varchar(2),
    zip_cd               varchar(10),
    country              varchar(50),
    cnty_cd              varchar(10),
    house_number         varchar(20),
    prefix_direction     varchar(10),
    street_name          varchar(50),
    street_type          varchar(10),
    postfix_direction    varchar(10),
    unit_number          varchar(20),
    unit_type            varchar(10),
    zip5_cd              varchar(10),
    zip4_cd              varchar(10),
    longitude            int,
    latitude             int,
    census_block_id      varchar(20),
    segment_id           varchar(20),
    data_type            varchar(1),
    location_quality_cd  varchar(10),
    match_cd             varchar(10),
    score                decimal(11, 2),
    result_type          varchar(1) NOT NULL,
    num_matches          tinyint    NOT NULL,
    next_score_diff      decimal(11, 2),
    next_score_count     tinyint,
    entity_uid           bigint,
    entity_class_cd      varchar(10),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    CONSTRAINT pk_geocoding_result PRIMARY KEY (geocoding_result_uid)
)
    GO

CREATE TABLE geocoding_result_hist
(
    postal_locator_uid   bigint     NOT NULL,
    add_time             datetime   NOT NULL,
    add_user_id          bigint,
    last_chg_time        datetime   NOT NULL,
    last_chg_user_id     bigint,
    firm_name            varchar(50),
    street_addr1         varchar(100),
    street_addr2         varchar(100),
    city                 varchar(50),
    state                varchar(2),
    zip_cd               varchar(10),
    country              varchar(50),
    cnty_cd              varchar(10),
    house_number         varchar(20),
    prefix_direction     varchar(10),
    street_name          varchar(50),
    street_type          varchar(10),
    postfix_direction    varchar(10),
    unit_number          varchar(20),
    unit_type            varchar(20),
    zip5_cd              varchar(10),
    zip4_cd              varchar(10),
    longitude            int,
    latitude             int,
    census_block_id      varchar(20),
    segment_id           varchar(20),
    data_type            varchar(1),
    location_quality_cd  varchar(10),
    match_cd             varchar(10),
    score                decimal(11, 2),
    result_type          varchar(1) NOT NULL,
    num_matches          tinyint    NOT NULL,
    next_score_diff      decimal(11, 2),
    next_score_count     tinyint,
    entity_uid           bigint,
    entity_class_cd      varchar(10),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    geocoding_result_uid bigint     NOT NULL,
    version_ctrl_nbr     smallint   NOT NULL,
    CONSTRAINT pk_geocoding_result_hist PRIMARY KEY (geocoding_result_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE intervention
(
    intervention_uid           bigint   NOT NULL,
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    cd_system_cd               varchar(20),
    cd_system_desc_txt         varchar(100),
    class_cd                   varchar(10),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    jurisdiction_cd            varchar(20),
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    method_cd                  varchar(20),
    method_desc_txt            varchar(100),
    prog_area_cd               varchar(20),
    priority_cd                varchar(20),
    priority_desc_txt          varchar(100),
    qty_amt                    varchar(20),
    qty_unit_cd                varchar(20),
    reason_cd                  varchar(20),
    reason_desc_txt            varchar(100),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    repeat_nbr                 smallint,
    status_cd                  char(1),
    status_time                datetime,
    target_site_cd             varchar(20),
    target_site_desc_txt       varchar(100),
    txt                        varchar(1000),
    user_affiliation_txt       varchar(20),
    program_jurisdiction_oid   bigint,
    shared_ind                 char(1)  NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    material_cd                varchar(20),
    age_at_vacc                smallint,
    age_at_vacc_unit_cd        varchar(20),
    vacc_mfgr_cd               varchar(20),
    material_lot_nm            varchar(50),
    material_expiration_time   datetime,
    vacc_dose_nbr              smallint,
    vacc_info_source_cd        varchar(20),
    electronic_ind             char(1),
    CONSTRAINT pk_intervention PRIMARY KEY (intervention_uid)
)
    GO

CREATE TABLE intervention_hist
(
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    cd_system_cd               varchar(20),
    cd_system_desc_txt         varchar(100),
    class_cd                   varchar(10),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    jurisdiction_cd            varchar(20),
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    method_cd                  varchar(20),
    method_desc_txt            varchar(100),
    priority_cd                varchar(20),
    priority_desc_txt          varchar(100),
    prog_area_cd               varchar(20),
    qty_amt                    varchar(20),
    qty_unit_cd                varchar(20),
    reason_cd                  varchar(20),
    reason_desc_txt            varchar(100),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    repeat_nbr                 smallint,
    status_cd                  char(1),
    status_time                datetime,
    target_site_cd             varchar(20),
    target_site_desc_txt       varchar(100),
    txt                        varchar(1000),
    user_affiliation_txt       varchar(20),
    program_jurisdiction_oid   bigint,
    shared_ind                 char(1)  NOT NULL,
    electronic_ind             char(1),
    material_cd                varchar(20),
    age_at_vacc                smallint,
    age_at_vacc_unit_cd        varchar(20),
    vacc_mfgr_cd               varchar(20),
    material_lot_nm            varchar(50),
    material_expiration_time   datetime,
    vacc_dose_nbr              smallint,
    vacc_info_source_cd        varchar(20),
    intervention_uid           bigint   NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_intervention_hist PRIMARY KEY (intervention_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE interview
(
    interview_uid       bigint      NOT NULL,
    interview_status_cd varchar(20),
    interview_date      datetime,
    interviewee_role_cd varchar(20),
    interview_type_cd   varchar(20),
    interview_loc_cd    varchar(20),
    local_id            varchar(50) NOT NULL,
    record_status_cd    varchar(20) NOT NULL,
    record_status_time  datetime    NOT NULL,
    add_time            datetime    NOT NULL,
    add_user_id         bigint      NOT NULL,
    last_chg_time       datetime    NOT NULL,
    last_chg_user_id    bigint      NOT NULL,
    version_ctrl_nbr    smallint    NOT NULL,
    CONSTRAINT pk_interview PRIMARY KEY (interview_uid)
)
    GO

CREATE TABLE interview_hist
(
    interview_hist_uid  bigint      NOT NULL,
    interview_uid       bigint      NOT NULL,
    interview_status_cd varchar(20),
    interview_date      datetime,
    interviewee_role_cd varchar(20),
    interview_type_cd   varchar(20),
    interview_loc_cd    varchar(20),
    local_id            varchar(50) NOT NULL,
    record_status_cd    varchar(20) NOT NULL,
    record_status_time  datetime    NOT NULL,
    add_time            datetime    NOT NULL,
    add_user_id         bigint      NOT NULL,
    last_chg_time       datetime    NOT NULL,
    last_chg_user_id    bigint      NOT NULL,
    version_ctrl_nbr    smallint    NOT NULL,
    CONSTRAINT pk_interview_hist PRIMARY KEY (interview_hist_uid)
)
    GO

CREATE TABLE lab_event
(
    lab_event_uid                 bigint NOT NULL,
    observation_uid               bigint,
    investigation_uid             bigint,
    notification_uid              bigint,
    result_uid                    bigint,
    susceptibility_uid            bigint,
    lab_result_status_cd          char(1),
    ref_range_frm                 varchar(20),
    ref_range_to                  varchar(20),
    specimen_qty                  varchar(20),
    ordered_lab_test_cd           varchar(50),
    numeric_value_2               decimal(38, 5),
    comparator_cd_1               varchar(10),
    numeric_value_1               varchar(100),
    separator_cd                  varchar(10),
    numeric_unit_cd               varchar(20),
    interpretation_cd             varchar(2000),
    reason_for_test_cd            varchar(2000),
    target_site_cd                varchar(20),
    lab_rpt_status_cd             char(1),
    result_rpt_dt                 datetime,
    lab_result_txt_val            varchar(2000),
    resultedtest_cd               varchar(20),
    specimen_analyzed_dt          datetime,
    specimen_src_desc             varchar(100),
    person_uid                    bigint,
    specimen_collection_dt        datetime,
    accession_nbr                 varchar(100),
    specimen_qty_unit             varchar(20),
    lab_result_comments           varchar(2000),
    test_method_cd                varchar(2000),
    organization_uid              bigint,
    specimen_src_cd               varchar(50),
    resulted_lab_test_cd          varchar(50),
    resulted_lab_test_drug_cd     varchar(50),
    suscep_lab_result_txt_val     varchar(2000),
    suscep_resultedtest_cd        varchar(20),
    suscep_comparator_cd_1        varchar(10),
    suscep_numeric_value_1        varchar(100),
    suscep_separator_cd           varchar(10),
    suscep_numeric_value_2        decimal(15, 5),
    suscep_numeric_unit_cd        varchar(20),
    suscep_ref_range_frm          varchar(20),
    suscep_ref_range_to           varchar(20),
    suscep_lab_result_comments    varchar(2000),
    suscep_lab_rslt_status_cd     char(1),
    suscep_test_method_cd         varchar(2000),
    suscep_interpretation_cd      varchar(20),
    suscep_lab_rpt_status_cd      char(1),
    suscep_accession_nbr          varchar(100),
    suscep_ordered_lab_test_cd    varchar(50),
    suscep_specimen_collection_dt datetime,
    suscep_result_rpt_dt          datetime,
    CONSTRAINT pk_lab_event PRIMARY KEY (lab_event_uid)
)
    GO

CREATE TABLE lab_event_hist
(
    lab_event_hist_uid            bigint NOT NULL,
    lab_event_uid                 bigint NOT NULL,
    observation_uid               bigint,
    investigation_uid             bigint,
    notification_uid              bigint,
    result_uid                    bigint,
    susceptibility_uid            bigint,
    add_time                      datetime,
    lab_result_status_cd          char(1),
    ref_range_frm                 varchar(20),
    ref_range_to                  varchar(20),
    specimen_qty                  varchar(20),
    ordered_lab_test_cd           varchar(50),
    numeric_value_2               decimal(38, 5),
    comparator_cd_1               varchar(10),
    numeric_value_1               varchar(100),
    separator_cd                  varchar(10),
    numeric_unit_cd               varchar(20),
    interpretation_cd             varchar(2000),
    reason_for_test_cd            varchar(2000),
    target_site_cd                varchar(20),
    lab_rpt_status_cd             char(1),
    result_rpt_dt                 datetime,
    lab_result_txt_val            varchar(2000),
    resultedtest_cd               varchar(20),
    specimen_analyzed_dt          datetime,
    specimen_src_desc             varchar(100),
    person_uid                    bigint,
    specimen_collection_dt        datetime,
    accession_nbr                 varchar(100),
    specimen_qty_unit             varchar(20),
    lab_result_comments           varchar(2000),
    test_method_cd                varchar(2000),
    organization_uid              bigint,
    specimen_src_cd               varchar(50),
    resulted_lab_test_cd          varchar(50),
    resulted_lab_test_drug_cd     varchar(50),
    suscep_lab_result_txt_val     varchar(2000),
    suscep_resultedtest_cd        varchar(20),
    suscep_comparator_cd_1        varchar(10),
    suscep_numeric_value_1        varchar(100),
    suscep_separator_cd           varchar(10),
    suscep_numeric_value_2        decimal(15, 5),
    suscep_numeric_unit_cd        varchar(20),
    suscep_ref_range_frm          varchar(20),
    suscep_ref_range_to           varchar(20),
    suscep_lab_result_comments    varchar(2000),
    suscep_lab_rslt_status_cd     char(1),
    suscep_test_method_cd         varchar(2000),
    suscep_interpretation_cd      varchar(20),
    suscep_lab_rpt_status_cd      char(1),
    suscep_accession_nbr          varchar(100),
    suscep_ordered_lab_test_cd    varchar(50),
    suscep_specimen_collection_dt datetime,
    suscep_result_rpt_dt          datetime,
    CONSTRAINT pk_lab_event_hist PRIMARY KEY (lab_event_hist_uid)
)
    GO

CREATE TABLE local_uid_generator
(
    class_name_cd  varchar(50) NOT NULL,
    type_cd        varchar(10) NOT NULL,
    uid_prefix_cd  varchar(10),
    uid_suffix_cd  varchar(10),
    seed_value_nbr bigint      NOT NULL,
    CONSTRAINT pk_local_uid_generator PRIMARY KEY (class_name_cd)
)
    GO

CREATE TABLE lookup_answer
(
    lookup_answer_uid         bigint     NOT NULL,
    lookup_question_uid       bigint     NOT NULL,
    from_answer_code          varchar(250),
    from_ans_display_nm       varchar(250),
    from_code_system_cd       varchar(250),
    from_code_system_desc_txt varchar(250),
    to_answer_code            varchar(250),
    to_ans_display_nm         varchar(250),
    to_code_system_cd         varchar(250),
    to_code_system_desc_txt   varchar(250),
    add_time                  datetime   NOT NULL,
    add_user_id               bigint     NOT NULL,
    last_chg_time             datetime   NOT NULL,
    last_chg_user_id          bigint     NOT NULL,
    status_cd                 varchar(1) NOT NULL,
    status_time               datetime   NOT NULL,
    CONSTRAINT pk_lookup_answer PRIMARY KEY (lookup_answer_uid)
)
    GO

CREATE TABLE lookup_question
(
    lookup_question_uid        bigint     NOT NULL,
    from_question_identifier   varchar(250),
    from_question_display_name varchar(250),
    from_code_system_cd        varchar(250),
    from_code_system_desc_txt  varchar(250),
    from_data_type             varchar(250),
    from_code_set              varchar(250),
    from_form_cd               varchar(250),
    to_question_identifier     varchar(250),
    to_question_display_name   varchar(250),
    to_code_system_cd          varchar(250),
    to_code_system_desc_txt    varchar(250),
    to_data_type               varchar(250),
    to_code_set                varchar(250),
    to_form_cd                 varchar(250),
    rdb_column_nm              varchar(30),
    add_time                   datetime   NOT NULL,
    add_user_id                bigint     NOT NULL,
    last_chg_time              datetime   NOT NULL,
    last_chg_user_id           bigint     NOT NULL,
    status_cd                  varchar(1) NOT NULL,
    status_time                datetime   NOT NULL,
    CONSTRAINT pk_lookup_question PRIMARY KEY (lookup_question_uid)
)
    GO

CREATE TABLE manufactured_material
(
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    expiration_time            datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    lot_nm                     varchar(50),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    user_affiliation_txt       varchar(20),
    stability_from_time        datetime,
    stability_to_time          datetime,
    stability_duration_cd      varchar(20),
    stability_duration_unit_cd varchar(20),
    material_uid               bigint   NOT NULL,
    manufactured_material_seq  smallint NOT NULL,
    CONSTRAINT pk_manufactured_material PRIMARY KEY (material_uid, manufactured_material_seq)
)
    GO

CREATE TABLE manufactured_material_hist
(
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    expiration_time            datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    lot_nm                     varchar(50),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    user_affiliation_txt       varchar(20),
    stability_from_time        datetime,
    stability_to_time          datetime,
    stability_duration_cd      varchar(20),
    stability_duration_unit_cd varchar(20),
    material_uid               bigint   NOT NULL,
    manufactured_material_seq  smallint NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_manufactured_material_hist PRIMARY KEY (material_uid, manufactured_material_seq, version_ctrl_nbr)
)
    GO

CREATE TABLE material
(
    material_uid               bigint   NOT NULL,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    description                varchar(1000),
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    form_cd                    varchar(20),
    form_desc_txt              varchar(100),
    handling_cd                varchar(20),
    handling_desc_txt          varchar(100),
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    nm                         varchar(100),
    qty                        varchar(20),
    qty_unit_cd                varchar(20),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    risk_cd                    varchar(20),
    risk_desc_txt              varchar(100),
    status_cd                  char(1),
    status_time                datetime,
    user_affiliation_txt       varchar(20),
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_material PRIMARY KEY (material_uid)
)
    GO

CREATE TABLE material_hist
(
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    description                varchar(1000),
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    form_cd                    varchar(20),
    form_desc_txt              varchar(100),
    handling_cd                varchar(20),
    handling_desc_txt          varchar(100),
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    nm                         varchar(100),
    qty                        varchar(20),
    qty_unit_cd                varchar(20),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    risk_cd                    varchar(20),
    risk_desc_txt              varchar(100),
    status_cd                  char(1),
    status_time                datetime,
    user_affiliation_txt       varchar(20),
    material_uid               bigint   NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_material_hist PRIMARY KEY (material_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE message_log
(
    message_log_uid    bigint        NOT NULL,
    message_txt        varchar(2000) NOT NULL,
    condition_cd       varchar(50),
    person_uid         bigint,
    assigned_to_uid    bigint,
    event_uid          bigint,
    event_type_cd      varchar(20),
    message_status_cd  varchar(20),
    record_status_cd   varchar(20)   NOT NULL,
    record_status_time datetime      NOT NULL,
    add_time           datetime      NOT NULL,
    add_user_id        bigint        NOT NULL,
    last_chg_time      datetime      NOT NULL,
    last_chg_user_id   bigint        NOT NULL,
    CONSTRAINT pk_message_log PRIMARY KEY (message_log_uid)
)
    GO

CREATE TABLE nbs_act_entity
(
    nbs_act_entity_uid      bigint      NOT NULL,
    act_uid                 bigint      NOT NULL,
    add_time                datetime    NOT NULL,
    add_user_id             bigint      NOT NULL,
    entity_uid              bigint      NOT NULL,
    entity_version_ctrl_nbr smallint    NOT NULL,
    last_chg_time           datetime    NOT NULL,
    last_chg_user_id        bigint      NOT NULL,
    record_status_cd        varchar(20) NOT NULL,
    record_status_time      datetime    NOT NULL,
    type_cd                 varchar(50),
    CONSTRAINT pk_nbs_act_entity PRIMARY KEY (nbs_act_entity_uid)
)
    GO

CREATE TABLE nbs_act_entity_hist
(
    nbs_act_entity_hist_uid bigint      NOT NULL,
    act_uid                 bigint      NOT NULL,
    act_version_ctrl_nbr    smallint    NOT NULL,
    add_time                datetime    NOT NULL,
    add_user_id             bigint      NOT NULL,
    entity_uid              bigint      NOT NULL,
    entity_version_ctrl_nbr smallint    NOT NULL,
    last_chg_time           datetime    NOT NULL,
    last_chg_user_id        bigint      NOT NULL,
    nbs_act_entity_uid      bigint      NOT NULL,
    record_status_cd        varchar(20) NOT NULL,
    record_status_time      datetime    NOT NULL,
    type_cd                 varchar(50),
    CONSTRAINT pk_nbs_act_entity_hist PRIMARY KEY (nbs_act_entity_hist_uid)
)
    GO

CREATE TABLE nbs_aggregate
(
    nbs_aggregate_uid bigint      NOT NULL,
    label             varchar(250),
    desc_txt          varchar(2000),
    tool_tip          varchar(2000),
    record_status_cd  varchar(20) NOT NULL,
    add_time          datetime,
    add_user_id       bigint,
    last_chg_time     datetime,
    last_chg_user_id  bigint,
    code              varchar(20) NOT NULL,
    nbs_question_uid  bigint      NOT NULL,
    CONSTRAINT pk_nbs_aggregate PRIMARY KEY (nbs_aggregate_uid)
)
    GO

CREATE TABLE nbs_answer
(
    nbs_answer_uid                bigint   NOT NULL,
    act_uid                       bigint   NOT NULL,
    answer_txt                    varchar(2000),
    nbs_question_uid              bigint   NOT NULL,
    nbs_question_version_ctrl_nbr smallint NOT NULL,
    seq_nbr                       smallint,
    answer_large_txt              varchar(MAX
) ,
    answer_group_seq_nbr          int,
    record_status_cd              varchar(20) NOT NULL,
    record_status_time            datetime    NOT NULL,
    last_chg_time                 datetime    NOT NULL,
    last_chg_user_id              bigint      NOT NULL,
    CONSTRAINT pk_nbs_answer PRIMARY KEY (nbs_answer_uid)
) GO

CREATE TABLE nbs_answer_hist
(
    nbs_answer_hist_uid           bigint   NOT NULL,
    nbs_answer_uid                bigint   NOT NULL,
    act_uid                       bigint   NOT NULL,
    answer_txt                    varchar(2000),
    nbs_question_uid              bigint   NOT NULL,
    nbs_question_version_ctrl_nbr smallint NOT NULL,
    seq_nbr                       smallint,
    answer_large_txt              varchar(MAX
) ,
    answer_group_seq_nbr          int,
    record_status_cd              varchar(20) NOT NULL,
    record_status_time            datetime    NOT NULL,
    last_chg_time                 datetime    NOT NULL,
    last_chg_user_id              bigint      NOT NULL,
    CONSTRAINT pk_nbs_answer_hist PRIMARY KEY (nbs_answer_hist_uid)
) GO

CREATE TABLE nbs_attachment
(
    nbs_attachment_uid    bigint      NOT NULL,
    attachment_parent_uid bigint      NOT NULL,
    desc_txt              varchar(2000),
    last_chg_time         datetime    NOT NULL,
    last_chg_user_id      bigint      NOT NULL,
    attachment            varbinary( MAX),
    file_nm_txt           varchar(250),
    type_cd               varchar(20) NOT NULL,
    CONSTRAINT pk_nbs_attachment PRIMARY KEY (nbs_attachment_uid)
)
    GO

CREATE TABLE nbs_case_answer
(
    nbs_case_answer_uid           bigint      NOT NULL,
    act_uid                       bigint      NOT NULL,
    add_time                      datetime    NOT NULL,
    add_user_id                   bigint      NOT NULL,
    answer_txt                    varchar(2000),
    nbs_question_uid              bigint      NOT NULL,
    nbs_question_version_ctrl_nbr int         NOT NULL,
    last_chg_time                 datetime    NOT NULL,
    last_chg_user_id              bigint      NOT NULL,
    record_status_cd              varchar(20) NOT NULL,
    record_status_time            datetime    NOT NULL,
    seq_nbr                       int,
    answer_large_txt              varchar(MAX
) ,
    nbs_table_metadata_uid        bigint,
    nbs_ui_metadata_ver_ctrl_nbr  int,
    answer_group_seq_nbr          int,
    CONSTRAINT pk_nbs_case_answer PRIMARY KEY (nbs_case_answer_uid)
) GO

CREATE TABLE nbs_case_answer_hist
(
    nbs_case_answer_hist_uid      bigint      NOT NULL,
    act_uid                       bigint      NOT NULL,
    act_version_ctrl_nbr          smallint    NOT NULL,
    add_time                      datetime    NOT NULL,
    add_user_id                   bigint      NOT NULL,
    answer_txt                    varchar(2000),
    nbs_question_version_ctrl_nbr int         NOT NULL,
    last_chg_time                 datetime    NOT NULL,
    last_chg_user_id              bigint      NOT NULL,
    nbs_case_answer_uid           bigint      NOT NULL,
    nbs_question_uid              bigint      NOT NULL,
    record_status_cd              varchar(20) NOT NULL,
    record_status_time            datetime    NOT NULL,
    seq_nbr                       int,
    answer_large_txt              varchar(MAX
) ,
    nbs_table_metadata_uid        bigint,
    nbs_ui_metadata_ver_ctrl_nbr  int,
    answer_group_seq_nbr          int,
    CONSTRAINT pk_nbs_case_answer_hist PRIMARY KEY (nbs_case_answer_hist_uid)
) GO

CREATE TABLE nbs_configuration
(
    config_key       varchar(200) NOT NULL,
    config_value     varchar(2000),
    short_name       varchar(80),
    desc_txt         varchar(2000),
    default_value    varchar(2000),
    valid_values     varchar(2000),
    category         varchar(50),
    add_release      varchar(50),
    version_ctrl_nbr smallint     NOT NULL,
    add_user_id      bigint       NOT NULL,
    add_time         datetime     NOT NULL,
    last_chg_user_id bigint       NOT NULL,
    last_chg_time    datetime     NOT NULL,
    status_cd        char(1)      NOT NULL,
    status_time      datetime     NOT NULL,
    admin_comment    varchar(2000),
    system_usage     varchar(2000),
    CONSTRAINT pk_nbs_configuration PRIMARY KEY (config_key)
)
    GO

CREATE TABLE nbs_conversion_condition
(
    nbs_conversion_condition_uid bigint      NOT NULL,
    condition_cd                 varchar(50) NOT NULL,
    condition_cd_group_id        bigint,
    nbs_conversion_page_mgmt_uid bigint,
    status_cd                    varchar(20),
    add_time                     datetime,
    last_chg_time                datetime,
    CONSTRAINT pk_nbs_conversion_condition PRIMARY KEY (nbs_conversion_condition_uid)
)
    GO

CREATE TABLE nbs_conversion_error
(
    nbs_conversion_error_uid   bigint        NOT NULL,
    error_cd                   varchar(250)  NOT NULL,
    error_message_txt          varchar(4000) NOT NULL,
    nbs_conversion_master_uid  bigint,
    condition_cd_group_id      bigint,
    nbs_conversion_mapping_uid bigint,
    CONSTRAINT pk_nbs_conversion_error PRIMARY KEY (nbs_conversion_error_uid)
)
    GO

CREATE TABLE nbs_conversion_mapping
(
    nbs_conversion_mapping_uid bigint      NOT NULL,
    from_code                  varchar(2000),
    from_code_set_nm           varchar(256),
    from_data_type             varchar(50),
    from_question_id           varchar(30),
    condition_cd_group_id      bigint,
    to_code                    varchar(2000),
    to_code_set_nm             varchar(256),
    to_data_type               varchar(30),
    to_question_id             varchar(30),
    translation_required_ind   varchar(20) NOT NULL,
    from_db_location           varchar(50) NOT NULL,
    to_db_location             varchar(50) NOT NULL,
    from_label                 varchar(200),
    to_label                   varchar(200),
    legacy_block_ind           varchar(50),
    block_id_nbr               int,
    other_ind                  char(1),
    unit_ind                   char(1),
    unit_type_cd               varchar(50),
    unit_value                 varchar(50),
    trigger_question_id        varchar(30),
    trigger_question_value     varchar(50),
    from_other_question_id     varchar(30),
    conversion_type            varchar(50),
    answer_group_seq_nbr       int,
    CONSTRAINT pk_nbs_conversion_mapping PRIMARY KEY (nbs_conversion_mapping_uid)
)
    GO

CREATE TABLE nbs_conversion_master
(
    nbs_conversion_master_uid    bigint      NOT NULL,
    act_uid                      bigint,
    end_time                     datetime,
    start_time                   datetime,
    process_type_ind             varchar(50) NOT NULL,
    process_message_txt          varchar(2000),
    status_cd                    varchar(50),
    nbs_conversion_condition_uid bigint,
    CONSTRAINT pk_nbs_conversion_master PRIMARY KEY (nbs_conversion_master_uid)
)
    GO

CREATE TABLE nbs_conversion_page_mgmt
(
    nbs_conversion_page_mgmt_uid bigint       NOT NULL,
    map_name                     varchar(100) NOT NULL,
    from_page_form_cd            varchar(50)  NOT NULL,
    to_page_form_cd              varchar(50)  NOT NULL,
    mapping_status_cd            varchar(20)  NOT NULL,
    add_time                     datetime     NOT NULL,
    add_user_id                  bigint       NOT NULL,
    last_chg_time                datetime     NOT NULL,
    last_chg_user_id             bigint       NOT NULL,
    xml_pay_load                 varchar(MAX
) ,
    CONSTRAINT pk_nbs_conversion_page_mgmt PRIMARY KEY (nbs_conversion_page_mgmt_uid)
) GO

CREATE TABLE nbs_document
(
    nbs_document_uid bigint NOT NULL,
    doc_payload      varchar(MAX
) NOT NULL,
    doc_type_cd               varchar(20)  NOT NULL,
    local_id                  varchar(50)  NOT NULL,
    record_status_cd          varchar(20)  NOT NULL,
    record_status_time        datetime     NOT NULL,
    add_user_id               bigint       NOT NULL,
    add_time                  datetime     NOT NULL,
    prog_area_cd              varchar(20),
    jurisdiction_cd           varchar(20),
    txt                       varchar(2000),
    program_jurisdiction_oid  bigint,
    shared_ind                char(1)      NOT NULL,
    version_ctrl_nbr          smallint     NOT NULL,
    cd                        varchar(50),
    last_chg_time             datetime,
    last_chg_user_id          bigint,
    doc_purpose_cd            varchar(50),
    doc_status_cd             varchar(20),
    cd_desc_txt               varchar(250),
    sending_facility_nm       varchar(250),
    nbs_interface_uid         bigint       NOT NULL,
    sending_app_event_id      varchar(250),
    sending_app_patient_id    varchar(50),
    phdc_doc_derived          varchar(MAX),
    payload_view_ind_cd       varchar(20),
    nbs_document_metadata_uid bigint       NOT NULL,
    external_version_ctrl_nbr smallint,
    processing_decision_txt   varchar(1000),
    processing_decision_cd    varchar(1000),
    CONSTRAINT pk_nbs_document PRIMARY KEY (nbs_document_uid)
) GO

CREATE TABLE nbs_document_hist
(
    nbs_document_hist_uid bigint NOT NULL,
    doc_payload           varchar(MAX
) NOT NULL,
    doc_type_cd               varchar(20)  NOT NULL,
    local_id                  varchar(50)  NOT NULL,
    record_status_cd          varchar(20)  NOT NULL,
    record_status_time        datetime     NOT NULL,
    add_user_id               bigint       NOT NULL,
    add_time                  datetime     NOT NULL,
    prog_area_cd              varchar(20),
    jurisdiction_cd           varchar(20),
    txt                       varchar(2000),
    program_jurisdiction_oid  bigint,
    shared_ind                char(1)      NOT NULL,
    version_ctrl_nbr          smallint     NOT NULL,
    cd                        varchar(50),
    last_chg_time             datetime,
    last_chg_user_id          bigint,
    doc_purpose_cd            varchar(50),
    doc_status_cd             varchar(20),
    cd_desc_txt               varchar(250),
    sending_facility_nm       varchar(250),
    nbs_interface_uid         bigint       NOT NULL,
    sending_app_event_id      varchar(50),
    sending_app_patient_id    varchar(50),
    nbs_document_uid          bigint       NOT NULL,
    phdc_doc_derived          varchar(MAX),
    payload_view_ind_cd       varchar(20),
    nbs_document_metadata_uid bigint       NOT NULL,
    external_version_ctrl_nbr smallint,
    processing_decision_txt   varchar(1000),
    processing_decision_cd    varchar(1000),
    CONSTRAINT pk_nbs_document_hist PRIMARY KEY (nbs_document_hist_uid)
) GO

CREATE TABLE nbs_document_metadata
(
    nbs_document_metadata_uid bigint       NOT NULL,
    xml_schema_location       varchar(250) NOT NULL,
    document_view_xsl         varchar(MAX
) NOT NULL,
    description               varchar(250),
    doc_type_cd               varchar(20),
    add_time                  datetime,
    add_user_id               bigint,
    record_status_cd          varchar(20),
    record_status_time        datetime,
    xmlbean_factory_class_nm  varchar(100),
    parser_class_nm           varchar(100),
    document_view_cda_xsl     varchar(MAX),
    doc_type_version_txt      varchar(250),
    CONSTRAINT pk_nbs_document_metadata PRIMARY KEY (nbs_document_metadata_uid)
) GO

CREATE TABLE nbs_indicator
(
    nbs_indicator_uid bigint      NOT NULL,
    label             varchar(250),
    desc_txt          varchar(2000),
    tool_tip          varchar(2000),
    record_status_cd  varchar(20) NOT NULL,
    add_time          datetime,
    add_user_id       bigint,
    last_chg_time     datetime,
    last_chg_user_id  bigint,
    code              varchar(20) NOT NULL,
    nbs_question_uid  bigint      NOT NULL,
    CONSTRAINT pk_nbs_indicator PRIMARY KEY (nbs_indicator_uid)
)
    GO

CREATE TABLE nbs_metadata_rule
(
    nbs_metadata_rule_uid bigint NOT NULL,
    component_uid         bigint NOT NULL,
    component_identifier  varchar(50),
    component_type        varchar(20),
    CONSTRAINT pk_nbs_metadata_rule PRIMARY KEY (nbs_metadata_rule_uid)
)
    GO

CREATE TABLE nbs_note
(
    nbs_note_uid       bigint        NOT NULL,
    note_parent_uid    bigint        NOT NULL,
    record_status_cd   varchar(20),
    record_status_time datetime      NOT NULL,
    add_time           datetime      NOT NULL,
    add_user_id        bigint        NOT NULL,
    last_chg_time      datetime      NOT NULL,
    last_chg_user_id   bigint        NOT NULL,
    note               varchar(2000) NOT NULL,
    private_ind_cd     char(1)       NOT NULL,
    type_cd            varchar(20)   NOT NULL,
    CONSTRAINT pk_nbs_note PRIMARY KEY (nbs_note_uid)
)
    GO

CREATE TABLE nbs_page
(
    nbs_page_uid       bigint      NOT NULL,
    wa_template_uid    bigint      NOT NULL,
    form_cd            varchar(50),
    desc_txt           varchar(2000),
    jsp_payload        varbinary( MAX),
    datamart_nm        varchar(21),
    local_id           varchar(50),
    bus_obj_type       varchar(50) NOT NULL,
    last_chg_user_id   bigint      NOT NULL,
    last_chg_time      datetime    NOT NULL,
    record_status_cd   varchar(20) NOT NULL,
    record_status_time datetime    NOT NULL,
    CONSTRAINT pk_nbs_page PRIMARY KEY (nbs_page_uid)
)
    GO

CREATE TABLE nbs_page_hist
(
    nbs_page_hist_uid  bigint      NOT NULL,
    nbs_page_uid       bigint      NOT NULL,
    wa_template_uid    bigint      NOT NULL,
    form_cd            varchar(50),
    desc_txt           varchar(2000),
    jsp_payload        varbinary( MAX),
    datamart_nm        varchar(21),
    local_id           varchar(50),
    bus_obj_type       varchar(50) NOT NULL,
    last_chg_user_id   bigint      NOT NULL,
    last_chg_time      datetime    NOT NULL,
    record_status_cd   varchar(20) NOT NULL,
    record_status_time datetime    NOT NULL,
    version_ctrl_nbr   int         NOT NULL,
    CONSTRAINT pk_nbs_page_hist PRIMARY KEY (nbs_page_hist_uid)
)
    GO

CREATE TABLE nbs_question
(
    nbs_question_uid         bigint      NOT NULL,
    add_time                 datetime,
    add_user_id              bigint,
    code_set_group_id        bigint,
    data_cd                  varchar(50),
    data_location            varchar(150),
    question_identifier      varchar(50) NOT NULL,
    question_oid             varchar(150),
    question_oid_system_txt  varchar(100),
    question_unit_identifier varchar(20),
    data_type                varchar(20),
    data_use_cd              varchar(20),
    last_chg_time            datetime,
    last_chg_user_id         bigint,
    question_label           varchar(300),
    question_tool_tip        varchar(2000),
    datamart_column_nm       varchar(30),
    part_type_cd             varchar(50),
    default_value            varchar(300),
    version_ctrl_nbr         int,
    unit_parent_identifier   varchar(20),
    question_group_seq_nbr   int,
    future_date_ind_cd       char(1),
    legacy_data_location     varchar(150),
    repeats_ind_cd           char(1),
    CONSTRAINT pk_nbs_question PRIMARY KEY (nbs_question_uid)
)
    GO

CREATE TABLE nbs_question_hist
(
    nbs_question_hist_uid    bigint      NOT NULL,
    nbs_question_uid         bigint      NOT NULL,
    add_time                 datetime,
    add_user_id              bigint,
    code_set_group_id        bigint,
    data_cd                  varchar(50),
    data_location            varchar(150),
    question_identifier      varchar(50) NOT NULL,
    question_oid             varchar(150),
    question_oid_system_txt  varchar(100),
    question_unit_identifier varchar(20),
    data_type                varchar(20),
    data_use_cd              varchar(20),
    last_chg_time            datetime,
    last_chg_user_id         bigint,
    question_label           varchar(300),
    question_tool_tip        varchar(2000),
    datamart_column_nm       varchar(30),
    part_type_cd             varchar(50),
    default_value            varchar(300),
    version_ctrl_nbr         int,
    unit_parent_identifier   varchar(20),
    question_group_seq_nbr   int,
    future_date_ind_cd       char(1),
    legacy_data_location     varchar(150),
    repeats_ind_cd           char(1),
    CONSTRAINT pk_nbs_question_hist PRIMARY KEY (nbs_question_hist_uid)
)
    GO

CREATE TABLE nbs_rdb_metadata
(
    nbs_rdb_metadata_uid   bigint      NOT NULL,
    nbs_page_uid           bigint,
    nbs_ui_metadata_uid    bigint      NOT NULL,
    rdb_table_nm           varchar(30),
    user_defined_column_nm varchar(30),
    record_status_cd       varchar(20) NOT NULL,
    record_status_time     datetime    NOT NULL,
    last_chg_user_id       bigint      NOT NULL,
    last_chg_time          datetime    NOT NULL,
    local_id               varchar(50),
    rpt_admin_column_nm    varchar(50),
    rdb_column_nm          varchar(30),
    block_pivot_nbr        int,
    CONSTRAINT pk_nbs_rdb_metadata PRIMARY KEY (nbs_rdb_metadata_uid)
)
    GO

CREATE TABLE nbs_rdb_metadata_hist
(
    nbs_rdb_metadata_hist_uid bigint      NOT NULL,
    nbs_rdb_metadata_uid      bigint      NOT NULL,
    nbs_page_uid              bigint,
    nbs_ui_metadata_uid       bigint      NOT NULL,
    rdb_table_nm              varchar(30),
    user_defined_column_nm    varchar(30),
    record_status_cd          varchar(20) NOT NULL,
    record_status_time        datetime    NOT NULL,
    last_chg_user_id          bigint      NOT NULL,
    last_chg_time             datetime    NOT NULL,
    local_id                  varchar(50),
    rpt_admin_column_nm       varchar(50),
    rdb_column_nm             varchar(30),
    version_ctrl_nbr          int         NOT NULL,
    block_pivot_nbr           int,
    CONSTRAINT pk_nbs_rdb_metadata_hist PRIMARY KEY (nbs_rdb_metadata_hist_uid)
)
    GO

CREATE TABLE nbs_release
(
    nbs_release_uid int           NOT NULL,
    name            varchar(250)  NOT NULL,
    version         varchar(25)   NOT NULL,
    description     varchar(4000) NOT NULL,
    publish_date    datetime      NOT NULL,
    deployment_date datetime      NOT NULL,
    CONSTRAINT pk_nbs_release PRIMARY KEY (nbs_release_uid)
)
    GO

CREATE TABLE nbs_table
(
    nbs_table_uid    bigint       NOT NULL,
    nbs_table_nm     varchar(250) NOT NULL,
    record_status_cd varchar(20)  NOT NULL,
    add_time         datetime,
    add_user_id      bigint,
    last_chg_time    datetime,
    last_chg_user_id bigint,
    CONSTRAINT pk_nbs_table PRIMARY KEY (nbs_table_uid)
)
    GO

CREATE TABLE nbs_table_metadata
(
    nbs_table_metadata_uid bigint      NOT NULL,
    nbs_table_uid          bigint      NOT NULL,
    nbs_indicator_uid      bigint      NOT NULL,
    nbs_aggregate_uid      bigint      NOT NULL,
    nbs_question_uid       bigint      NOT NULL,
    datamart_column_nm     varchar(30),
    aggregate_seq_nbr      int         NOT NULL,
    record_status_cd       varchar(20) NOT NULL,
    add_time               datetime,
    add_user_id            bigint,
    last_chg_time          datetime,
    last_chg_user_id       bigint,
    indicator_seq_nbr      int         NOT NULL,
    msg_exclude_ind_cd     varchar(20),
    question_identifier    varchar(50),
    CONSTRAINT pk_nbs_table_metadata PRIMARY KEY (nbs_table_metadata_uid)
)
    GO

CREATE TABLE nbs_ui_component
(
    nbs_ui_component_uid bigint NOT NULL,
    type_cd              varchar(10),
    type_cd_desc         varchar(50),
    ldf_available_ind    varchar(10),
    display_order        int,
    component_behavior   varchar(20),
    CONSTRAINT pk_nbs_ui_component PRIMARY KEY (nbs_ui_component_uid)
)
    GO

CREATE TABLE nbs_ui_metadata
(
    nbs_ui_metadata_uid       bigint NOT NULL,
    nbs_ui_component_uid      bigint NOT NULL,
    nbs_question_uid          bigint,
    parent_uid                bigint,
    add_time                  datetime,
    add_user_id               bigint,
    admin_comment             varchar(2000),
    css_style                 varchar(50),
    default_value             varchar(300),
    display_ind               varchar(1),
    enable_ind                varchar(1),
    field_size                varchar(10),
    investigation_form_cd     varchar(50),
    last_chg_time             datetime,
    last_chg_user_id          bigint,
    ldf_position              varchar(10),
    ldf_page_id               varchar(20),
    ldf_status_cd             varchar(20),
    ldf_status_time           datetime,
    max_length                bigint,
    order_nbr                 int,
    question_label            varchar(300),
    question_tool_tip         varchar(2000),
    required_ind              varchar(2),
    record_status_cd          varchar(20),
    record_status_time        datetime,
    tab_order_id              int,
    tab_name                  varchar(50),
    version_ctrl_nbr          int    NOT NULL,
    future_date_ind_cd        char(1),
    nbs_table_uid             bigint,
    code_set_group_id         bigint,
    data_cd                   varchar(50),
    data_location             varchar(150),
    data_type                 varchar(20),
    data_use_cd               varchar(20),
    legacy_data_location      varchar(150),
    part_type_cd              varchar(50),
    question_group_seq_nbr    int,
    question_identifier       varchar(50),
    question_oid              varchar(150),
    question_oid_system_txt   varchar(100),
    question_unit_identifier  varchar(20),
    repeats_ind_cd            char(1),
    unit_parent_identifier    varchar(20),
    group_nm                  varchar(50),
    sub_group_nm              varchar(50),
    desc_txt                  varchar(256),
    mask                      varchar(50),
    min_value                 bigint,
    max_value                 bigint,
    nbs_page_uid              bigint,
    local_id                  varchar(50),
    standard_nnd_ind_cd       char(1),
    unit_type_cd              varchar(20),
    unit_value                varchar(50),
    other_value_ind_cd        char(1),
    batch_table_appear_ind_cd char(1),
    batch_table_header        varchar(50),
    batch_table_column_width  int,
    coinfection_ind_cd        char(1),
    block_nm                  varchar(30),
    CONSTRAINT pk_nbs_ui_metadata PRIMARY KEY (nbs_ui_metadata_uid)
)
    GO

CREATE TABLE nbs_ui_metadata_hist
(
    nbs_ui_metadata_hist_uid  bigint NOT NULL,
    nbs_ui_metadata_uid       bigint NOT NULL,
    nbs_ui_component_uid      bigint NOT NULL,
    nbs_question_uid          bigint,
    parent_uid                bigint,
    add_time                  datetime,
    add_user_id               bigint,
    admin_comment             varchar(2000),
    css_style                 varchar(50),
    default_value             varchar(300),
    display_ind               varchar(1),
    enable_ind                varchar(1),
    field_size                varchar(10),
    investigation_form_cd     varchar(50),
    last_chg_time             datetime,
    last_chg_user_id          bigint,
    ldf_position              varchar(10),
    ldf_page_id               varchar(20),
    ldf_status_cd             varchar(20),
    ldf_status_time           datetime,
    max_length                bigint,
    order_nbr                 int,
    question_label            varchar(300),
    question_tool_tip         varchar(2000),
    required_ind              varchar(2),
    record_status_cd          varchar(20),
    record_status_time        datetime,
    tab_order_id              int,
    tab_name                  varchar(50),
    version_ctrl_nbr          int    NOT NULL,
    future_date_ind_cd        char(1),
    nbs_table_uid             bigint,
    code_set_group_id         bigint,
    data_cd                   varchar(50),
    data_location             varchar(150),
    data_type                 varchar(20),
    data_use_cd               varchar(20),
    legacy_data_location      varchar(150),
    part_type_cd              varchar(50),
    question_group_seq_nbr    int,
    question_identifier       varchar(50),
    question_oid              varchar(150),
    question_oid_system_txt   varchar(100),
    question_unit_identifier  varchar(20),
    repeats_ind_cd            char(1),
    unit_parent_identifier    varchar(20),
    group_nm                  varchar(50),
    sub_group_nm              varchar(50),
    desc_txt                  varchar(256),
    mask                      varchar(50),
    min_value                 bigint,
    max_value                 bigint,
    nbs_page_uid              bigint,
    local_id                  varchar(50),
    standard_nnd_ind_cd       char(1),
    unit_type_cd              varchar(20),
    unit_value                varchar(50),
    other_value_ind_cd        char(1),
    batch_table_appear_ind_cd char(1),
    batch_table_header        varchar(50),
    batch_table_column_width  int,
    coinfection_ind_cd        char(1),
    block_nm                  varchar(30),
    CONSTRAINT pk_nbs_ui_metadata_hist PRIMARY KEY (nbs_ui_metadata_hist_uid)
)
    GO

CREATE TABLE nnd_activity_log
(
    error_message_txt    varchar(2000) NOT NULL,
    local_id             varchar(50)   NOT NULL,
    record_status_cd     varchar(20)   NOT NULL,
    record_status_time   datetime      NOT NULL,
    status_cd            char(1)       NOT NULL,
    status_time          datetime      NOT NULL,
    service              varchar(300),
    nnd_activity_log_uid bigint        NOT NULL,
    nnd_activity_log_seq smallint      NOT NULL,
    CONSTRAINT pk_nnd_activity_log PRIMARY KEY (nnd_activity_log_uid, nnd_activity_log_seq)
)
    GO

CREATE TABLE nnd_metadata
(
    nnd_metadata_uid        bigint      NOT NULL,
    add_time                datetime    NOT NULL,
    add_user_id             bigint      NOT NULL,
    hl7_segment_field       varchar(30),
    investigation_form_cd   varchar(50),
    last_chg_time           datetime    NOT NULL,
    last_chg_user_id        bigint      NOT NULL,
    order_group_id          varchar(5),
    question_identifier_nnd varchar(50),
    question_identifier     varchar(50) NOT NULL,
    question_label_nnd      varchar(150),
    question_required_nnd   char(1)     NOT NULL,
    question_data_type_nnd  varchar(10),
    record_status_cd        varchar(20) NOT NULL,
    record_status_time      datetime    NOT NULL,
    translation_table_nm    varchar(30),
    repeat_group_seq_nbr    int,
    question_order_nnd      int,
    msg_trigger_ind_cd      char(1),
    xml_path                varchar(2000),
    xml_tag                 varchar(300),
    xml_data_type           varchar(50),
    part_type_cd            varchar(50),
    nbs_page_uid            bigint,
    nbs_ui_metadata_uid     bigint,
    question_map            varchar(2000),
    indicator_cd            varchar(2000),
    CONSTRAINT pk_nnd_metadata PRIMARY KEY (nnd_metadata_uid)
)
    GO

CREATE TABLE nnd_metadata_hist
(
    nnd_metadata_hist_uid   bigint      NOT NULL,
    nnd_metadata_uid        bigint      NOT NULL,
    add_time                datetime    NOT NULL,
    add_user_id             bigint      NOT NULL,
    hl7_segment_field       varchar(30),
    investigation_form_cd   varchar(50),
    last_chg_time           datetime    NOT NULL,
    last_chg_user_id        bigint      NOT NULL,
    order_group_id          varchar(5),
    question_identifier_nnd varchar(50),
    question_identifier     varchar(50) NOT NULL,
    question_label_nnd      varchar(150),
    question_required_nnd   char(1)     NOT NULL,
    question_data_type_nnd  varchar(10),
    record_status_cd        varchar(20) NOT NULL,
    record_status_time      datetime    NOT NULL,
    translation_table_nm    varchar(30),
    repeat_group_seq_nbr    int,
    question_order_nnd      int,
    msg_trigger_ind_cd      char(1),
    xml_path                varchar(2000),
    xml_tag                 varchar(300),
    xml_data_type           varchar(50),
    part_type_cd            varchar(50),
    nbs_page_uid            bigint,
    nbs_ui_metadata_uid     bigint,
    version_ctrl_nbr        int         NOT NULL,
    question_map            varchar(2000),
    indicator_cd            varchar(2000),
    CONSTRAINT pk_nnd_metadata_hist PRIMARY KEY (nnd_metadata_hist_uid)
)
    GO

CREATE TABLE non_person_living_subject
(
    non_person_uid                bigint   NOT NULL,
    add_reason_cd                 varchar(20),
    add_time                      datetime,
    add_user_id                   bigint,
    birth_sex_cd                  char(1),
    birth_order_nbr               smallint,
    birth_time                    datetime,
    breed_cd                      varchar(20),
    breed_desc_txt                varchar(100),
    cd                            varchar(50),
    cd_desc_txt                   varchar(100),
    deceased_ind_cd               char(1),
    deceased_time                 datetime,
    description                   varchar(1000),
    last_chg_reason_cd            varchar(20),
    last_chg_time                 datetime,
    last_chg_user_id              bigint,
    local_id                      varchar(50),
    multiple_birth_ind            char(1),
    nm                            varchar(100),
    record_status_cd              varchar(20),
    record_status_time            datetime,
    status_cd                     char(1),
    status_time                   datetime,
    taxonomic_classification_cd   varchar(20),
    taxonomic_classification_desc varchar(100),
    user_affiliation_txt          varchar(20),
    version_ctrl_nbr              smallint NOT NULL,
    CONSTRAINT pk_non_person_living_subject PRIMARY KEY (non_person_uid)
)
    GO

CREATE TABLE non_person_living_subject_hist
(
    add_reason_cd                 varchar(20),
    add_time                      datetime,
    add_user_id                   bigint,
    birth_sex_cd                  char(1),
    birth_order_nbr               smallint,
    birth_time                    datetime,
    breed_cd                      varchar(20),
    breed_desc_txt                varchar(100),
    cd                            varchar(50),
    cd_desc_txt                   varchar(100),
    deceased_ind_cd               char(1),
    deceased_time                 datetime,
    description                   varchar(1000),
    last_chg_reason_cd            varchar(20),
    last_chg_time                 datetime,
    last_chg_user_id              bigint,
    local_id                      varchar(50),
    multiple_birth_ind            char(1),
    nm                            varchar(100),
    record_status_cd              varchar(20),
    record_status_time            datetime,
    status_cd                     char(1),
    status_time                   datetime,
    taxonomic_classification_cd   varchar(20),
    taxonomic_classification_desc varchar(100),
    user_affiliation_txt          varchar(20),
    non_person_uid                bigint   NOT NULL,
    version_ctrl_nbr              smallint NOT NULL,
    CONSTRAINT pk_non_person_living_subject_hist PRIMARY KEY (non_person_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE notification
(
    notification_uid           bigint NOT NULL,
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    case_class_cd              varchar(20),
    case_condition_cd          varchar(20),
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    confirmation_method_cd     varchar(20),
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    jurisdiction_cd            varchar(20),
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    message_txt                varchar(MAX
) ,
    method_cd                     varchar(20),
    method_desc_txt               varchar(100),
    mmwr_week                     varchar(10),
    mmwr_year                     varchar(10),
    nedss_version_nbr             varchar(10),
    prog_area_cd                  varchar(20),
    reason_cd                     varchar(20),
    reason_desc_txt               varchar(100),
    record_count                  varchar(10),
    record_status_cd              varchar(20),
    record_status_time            datetime,
    repeat_nbr                    smallint,
    rpt_sent_time                 datetime,
    rpt_source_cd                 varchar(20),
    rpt_source_type_cd            varchar(20),
    status_cd                     char(1),
    status_time                   datetime,
    txt                           varchar(1000),
    user_affiliation_txt          varchar(20),
    program_jurisdiction_oid      bigint,
    shared_ind                    char(1)  NOT NULL,
    version_ctrl_nbr              smallint NOT NULL,
    auto_resend_ind               char(1),
    export_receiving_facility_uid bigint,
    nbs_interface_uid             bigint,
    CONSTRAINT pk_notification PRIMARY KEY (notification_uid)
) GO

CREATE TABLE notification_hist
(
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    case_class_cd              varchar(20),
    case_condition_cd          varchar(20),
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    confirmation_method_cd     varchar(20),
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    jurisdiction_cd            varchar(20),
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    message_txt                varchar(MAX
) ,
    method_cd                     varchar(20),
    method_desc_txt               varchar(100),
    mmwr_week                     varchar(10),
    mmwr_year                     varchar(10),
    nedss_version_nbr             varchar(10),
    prog_area_cd                  varchar(20),
    reason_cd                     varchar(20),
    reason_desc_txt               varchar(100),
    record_count                  varchar(10),
    record_status_cd              varchar(20),
    record_status_time            datetime,
    repeat_nbr                    smallint,
    rpt_sent_time                 datetime,
    rpt_source_cd                 varchar(20),
    rpt_source_type_cd            varchar(20),
    status_cd                     char(1),
    status_time                   datetime,
    txt                           varchar(1000),
    user_affiliation_txt          varchar(20),
    program_jurisdiction_oid      bigint,
    shared_ind                    char(1)  NOT NULL,
    auto_resend_ind               char(1),
    export_receiving_facility_uid bigint,
    nbs_interface_uid             bigint,
    notification_uid              bigint   NOT NULL,
    version_ctrl_nbr              smallint NOT NULL,
    CONSTRAINT pk_notification_hist PRIMARY KEY (notification_uid, version_ctrl_nbr)
) GO

CREATE TABLE obs_value_coded
(
    code_system_cd         varchar(300),
    code_system_desc_txt   varchar(100),
    code_version           varchar(10),
    display_name           varchar(300),
    original_txt           varchar(300),
    alt_cd                 varchar(50),
    alt_cd_desc_txt        varchar(100),
    alt_cd_system_cd       varchar(300),
    alt_cd_system_desc_txt varchar(100),
    code_derived_ind       char(1),
    observation_uid        bigint      NOT NULL,
    code                   varchar(20) NOT NULL,
    CONSTRAINT pk_obs_value_coded PRIMARY KEY (observation_uid, code)
)
    GO

CREATE TABLE obs_value_coded_hist
(
    code_system_cd         varchar(300),
    code_system_desc_txt   varchar(100),
    code_version           varchar(10),
    display_name           varchar(300),
    original_txt           varchar(300),
    alt_cd                 varchar(50),
    alt_cd_desc_txt        varchar(100),
    alt_cd_system_cd       varchar(300),
    alt_cd_system_desc_txt varchar(100),
    code_derived_ind       char(1),
    observation_uid        bigint      NOT NULL,
    code                   varchar(20) NOT NULL,
    version_ctrl_nbr       smallint    NOT NULL,
    CONSTRAINT pk_obs_value_coded_hist PRIMARY KEY (observation_uid, code, version_ctrl_nbr)
)
    GO

CREATE TABLE obs_value_coded_mod
(
    code_system_cd       varchar(20),
    code_system_desc_txt varchar(100),
    code_version         varchar(10),
    display_name         varchar(300),
    original_txt         varchar(100),
    observation_uid      bigint      NOT NULL,
    code                 varchar(20) NOT NULL,
    code_mod_cd          varchar(20) NOT NULL,
    CONSTRAINT pk_obs_value_coded_mod PRIMARY KEY (observation_uid, code, code_mod_cd)
)
    GO

CREATE TABLE obs_value_coded_mod_hist
(
    code_system_cd       varchar(20),
    code_system_desc_txt varchar(100),
    code_version         varchar(10),
    display_name         varchar(300),
    original_txt         varchar(100),
    observation_uid      bigint      NOT NULL,
    code                 varchar(20) NOT NULL,
    code_mod_cd          varchar(20) NOT NULL,
    version_ctrl_nbr     smallint    NOT NULL,
    CONSTRAINT pk_obs_value_coded_mod_hist PRIMARY KEY (observation_uid, code, code_mod_cd, version_ctrl_nbr)
)
    GO

CREATE TABLE obs_value_date
(
    duration_amt       varchar(20),
    duration_unit_cd   varchar(20),
    from_time          datetime,
    to_time            datetime,
    observation_uid    bigint   NOT NULL,
    obs_value_date_seq smallint NOT NULL,
    CONSTRAINT pk_obs_value_date PRIMARY KEY (observation_uid, obs_value_date_seq)
)
    GO

CREATE TABLE obs_value_date_hist
(
    duration_amt       varchar(20),
    duration_unit_cd   varchar(20),
    from_time          datetime,
    to_time            datetime,
    observation_uid    bigint   NOT NULL,
    obs_value_date_seq smallint NOT NULL,
    version_ctrl_nbr   smallint NOT NULL,
    CONSTRAINT pk_obs_value_date_hist PRIMARY KEY (observation_uid, obs_value_date_seq, version_ctrl_nbr)
)
    GO

CREATE TABLE obs_value_numeric
(
    high_range            varchar(20),
    low_range             varchar(20),
    comparator_cd_1       varchar(10),
    numeric_value_1       decimal(15, 5),
    numeric_value_2       decimal(15, 5),
    numeric_unit_cd       varchar(20),
    separator_cd          varchar(10),
    numeric_scale_1       smallint,
    numeric_scale_2       smallint,
    observation_uid       bigint   NOT NULL,
    obs_value_numeric_seq smallint NOT NULL,
    CONSTRAINT pk_obs_value_numeric PRIMARY KEY (observation_uid, obs_value_numeric_seq)
)
    GO

CREATE TABLE obs_value_numeric_hist
(
    high_range            varchar(20),
    low_range             varchar(20),
    comparator_cd_1       varchar(10),
    numeric_value_1       decimal(15, 5),
    numeric_value_2       decimal(15, 5),
    numeric_unit_cd       varchar(20),
    separator_cd          varchar(10),
    numeric_scale_1       smallint,
    numeric_scale_2       smallint,
    observation_uid       bigint   NOT NULL,
    obs_value_numeric_seq smallint NOT NULL,
    version_ctrl_nbr      smallint NOT NULL,
    CONSTRAINT pk_obs_value_numeric_hist PRIMARY KEY (observation_uid, obs_value_numeric_seq, version_ctrl_nbr)
)
    GO

CREATE TABLE obs_value_txt
(
    data_subtype_cd   varchar(20),
    encoding_type_cd  varchar(20),
    txt_type_cd       varchar(20),
    value_image_txt   varbinary( MAX),
    value_txt         varchar(2000),
    observation_uid   bigint   NOT NULL,
    obs_value_txt_seq smallint NOT NULL,
    CONSTRAINT pk_obs_value_txt PRIMARY KEY (observation_uid, obs_value_txt_seq)
)
    GO

CREATE TABLE obs_value_txt_hist
(
    data_subtype_cd   varchar(20),
    encoding_type_cd  varchar(20),
    txt_type_cd       varchar(20),
    value_image_txt   varbinary( MAX),
    value_txt         varchar(2000),
    observation_uid   bigint   NOT NULL,
    obs_value_txt_seq smallint NOT NULL,
    version_ctrl_nbr  smallint NOT NULL,
    CONSTRAINT pk_obs_value_txt_hist PRIMARY KEY (observation_uid, obs_value_txt_seq, version_ctrl_nbr)
)
    GO

CREATE TABLE observation
(
    observation_uid            bigint   NOT NULL,
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(1000),
    cd_system_cd               varchar(300),
    cd_system_desc_txt         varchar(100),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    ctrl_cd_display_form       varchar(20),
    ctrl_cd_user_defined_1     varchar(20),
    ctrl_cd_user_defined_2     varchar(20),
    ctrl_cd_user_defined_3     varchar(20),
    ctrl_cd_user_defined_4     varchar(20),
    derivation_exp             smallint,
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    electronic_ind             char(1),
    group_level_cd             varchar(10),
    jurisdiction_cd            varchar(20),
    lab_condition_cd           varchar(20),
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    method_cd                  varchar(2000),
    method_desc_txt            varchar(2000),
    obs_domain_cd              varchar(20),
    obs_domain_cd_st_1         varchar(20),
    pnu_cd                     char(1),
    priority_cd                varchar(20),
    priority_desc_txt          varchar(100),
    prog_area_cd               varchar(20),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    repeat_nbr                 smallint,
    status_cd                  char(1),
    status_time                datetime,
    subject_person_uid         bigint,
    target_site_cd             varchar(20),
    target_site_desc_txt       varchar(100),
    txt                        varchar(1000),
    user_affiliation_txt       varchar(20),
    value_cd                   varchar(20),
    ynu_cd                     char(1),
    program_jurisdiction_oid   bigint,
    shared_ind                 char(1)  NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    alt_cd                     varchar(50),
    alt_cd_desc_txt            varchar(1000),
    alt_cd_system_cd           varchar(300),
    alt_cd_system_desc_txt     varchar(100),
    cd_derived_ind             char(1),
    rpt_to_state_time          datetime,
    cd_version                 varchar(50),
    processing_decision_cd     varchar(20),
    pregnant_ind_cd            varchar(20),
    pregnant_week              smallint,
    processing_decision_txt    varchar(1000),
    CONSTRAINT pk_observation PRIMARY KEY (observation_uid)
)
    GO

CREATE TABLE observation_hist
(
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(1000),
    cd_system_cd               varchar(300),
    cd_system_desc_txt         varchar(100),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    ctrl_cd_display_form       varchar(20),
    ctrl_cd_user_defined_1     varchar(20),
    ctrl_cd_user_defined_2     varchar(20),
    ctrl_cd_user_defined_3     varchar(20),
    ctrl_cd_user_defined_4     varchar(20),
    derivation_exp             smallint,
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    electronic_ind             char(1),
    group_level_cd             varchar(10),
    jurisdiction_cd            varchar(20),
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    lab_condition_cd           varchar(20),
    local_id                   varchar(50),
    obs_domain_cd              varchar(20),
    obs_domain_cd_st_1         varchar(10),
    pnu_cd                     char(1),
    priority_cd                varchar(20),
    priority_desc_txt          varchar(100),
    prog_area_cd               varchar(20),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    repeat_nbr                 smallint,
    status_cd                  char(1),
    status_time                datetime,
    subject_person_uid         bigint,
    target_site_cd             varchar(20),
    target_site_desc_txt       varchar(100),
    txt                        varchar(1000),
    user_affiliation_txt       varchar(20),
    value_cd                   varchar(20),
    ynu_cd                     char(1),
    program_jurisdiction_oid   bigint,
    shared_ind                 char(1)  NOT NULL,
    alt_cd                     varchar(50),
    alt_cd_desc_txt            varchar(1000),
    alt_cd_system_cd           varchar(300),
    alt_cd_system_desc_txt     varchar(100),
    cd_derived_ind             char(1),
    rpt_to_state_time          datetime,
    cd_version                 varchar(50),
    processing_decision_cd     varchar(20),
    pregnant_ind_cd            varchar(20),
    pregnant_week              smallint,
    processing_decision_txt    varchar(1000),
    observation_uid            bigint   NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_observation_hist PRIMARY KEY (observation_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE observation_interp
(
    observation_uid         bigint       NOT NULL,
    interpretation_cd       varchar(20)  NOT NULL,
    interpretation_desc_txt varchar(100) NOT NULL,
    CONSTRAINT pk_observation_interp PRIMARY KEY (observation_uid, interpretation_cd, interpretation_desc_txt)
)
    GO

CREATE TABLE observation_interp_hist
(
    observation_uid    bigint       NOT NULL,
    interpretation_cd  varchar(20)  NOT NULL,
    version_ctrl_nbr   smallint     NOT NULL,
    interpretation_txt varchar(100) NOT NULL,
    CONSTRAINT pk_observation_interp_hist PRIMARY KEY (observation_uid, interpretation_cd, version_ctrl_nbr,
                                                       interpretation_txt)
)
    GO

CREATE TABLE observation_reason
(
    reason_desc_txt varchar(100),
    observation_uid bigint      NOT NULL,
    reason_cd       varchar(20) NOT NULL,
    CONSTRAINT pk_observation_reason PRIMARY KEY (observation_uid, reason_cd)
)
    GO

CREATE TABLE observation_reason_hist
(
    reason_desc_txt  varchar(100),
    observation_uid  bigint      NOT NULL,
    reason_cd        varchar(20) NOT NULL,
    version_ctrl_nbr smallint    NOT NULL,
    CONSTRAINT pk_observation_reason_hist PRIMARY KEY (observation_uid, reason_cd, version_ctrl_nbr)
)
    GO

CREATE TABLE operator_type
(
    operator_type_uid      bigint NOT NULL,
    operator_type_code     varchar(20),
    operator_type_desc_txt varchar(50),
    CONSTRAINT pk_operator_type PRIMARY KEY (operator_type_uid)
)
    GO

CREATE TABLE organization
(
    organization_uid           bigint   NOT NULL,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    description                varchar(1000),
    duration_amt               varchar(20),
    duration_unit_cd           varchar(20),
    from_time                  datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    standard_industry_class_cd varchar(20),
    standard_industry_desc_txt varchar(100),
    status_cd                  char(1),
    status_time                datetime,
    to_time                    datetime,
    user_affiliation_txt       varchar(20),
    display_nm                 varchar(100),
    street_addr1               varchar(100),
    street_addr2               varchar(100),
    city_cd                    varchar(20),
    city_desc_txt              varchar(100),
    state_cd                   varchar(20),
    cnty_cd                    varchar(20),
    cntry_cd                   varchar(20),
    zip_cd                     varchar(20),
    phone_nbr                  varchar(20),
    phone_cntry_cd             varchar(20),
    version_ctrl_nbr           smallint NOT NULL,
    electronic_ind             char(1),
    edx_ind                    varchar(1),
    CONSTRAINT pk_organization PRIMARY KEY (organization_uid)
)
    GO

CREATE TABLE organization_hist
(
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    description                varchar(1000),
    duration_amt               varchar(20),
    duration_unit_cd           varchar(20),
    from_time                  datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    standard_industry_class_cd varchar(20),
    standard_industry_desc_txt varchar(100),
    status_cd                  char(1),
    status_time                datetime,
    to_time                    datetime,
    user_affiliation_txt       varchar(20),
    display_nm                 varchar(100),
    street_addr1               varchar(100),
    street_addr2               varchar(100),
    city_cd                    varchar(20),
    city_desc_txt              varchar(100),
    state_cd                   varchar(20),
    cnty_cd                    varchar(20),
    cntry_cd                   varchar(20),
    zip_cd                     varchar(20),
    phone_nbr                  varchar(20),
    phone_cntry_cd             varchar(20),
    electronic_ind             char(1),
    edx_ind                    varchar(1),
    organization_uid           bigint   NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_organization_hist PRIMARY KEY (organization_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE organization_name
(
    nm_txt                varchar(100),
    nm_use_cd             varchar(20),
    record_status_cd      varchar(20),
    default_nm_ind        char(1),
    organization_uid      bigint   NOT NULL,
    organization_name_seq smallint NOT NULL,
    CONSTRAINT pk_organization_name PRIMARY KEY (organization_uid, organization_name_seq)
)
    GO

CREATE TABLE organization_name_hist
(
    nm_txt                varchar(100),
    nm_use_cd             varchar(20),
    record_status_cd      varchar(20),
    default_nm_ind        char(1),
    organization_uid      bigint   NOT NULL,
    organization_name_seq smallint NOT NULL,
    version_ctrl_nbr      smallint NOT NULL,
    CONSTRAINT pk_organization_name_hist PRIMARY KEY (organization_uid, organization_name_seq, version_ctrl_nbr)
)
    GO

CREATE TABLE page_cond_mapping
(
    page_cond_mapping_uid bigint      NOT NULL,
    wa_template_uid       bigint      NOT NULL,
    condition_cd          varchar(20) NOT NULL,
    add_time              datetime    NOT NULL,
    add_user_id           bigint      NOT NULL,
    last_chg_time         datetime    NOT NULL,
    last_chg_user_id      bigint      NOT NULL,
    CONSTRAINT pk_page_cond_mapping PRIMARY KEY (page_cond_mapping_uid)
)
    GO

CREATE TABLE page_cond_mapping_hist
(
    page_cond_mapping_hist_uid bigint      NOT NULL,
    page_cond_mapping_uid      bigint      NOT NULL,
    wa_template_uid            bigint      NOT NULL,
    condition_cd               varchar(20) NOT NULL,
    add_time                   datetime    NOT NULL,
    add_user_id                bigint      NOT NULL,
    last_chg_time              datetime    NOT NULL,
    last_chg_user_id           bigint      NOT NULL,
    CONSTRAINT pk_page_cond_mapping_hist PRIMARY KEY (page_cond_mapping_hist_uid)
)
    GO

CREATE TABLE participation
(
    act_class_cd            varchar(10),
    add_reason_cd           varchar(20),
    add_time                datetime,
    add_user_id             bigint,
    awareness_cd            varchar(20),
    awareness_desc_txt      varchar(100),
    duration_amt            varchar(20),
    duration_unit_cd        varchar(20),
    from_time               datetime,
    last_chg_reason_cd      varchar(20),
    last_chg_time           datetime,
    last_chg_user_id        bigint,
    record_status_cd        varchar(20),
    record_status_time      datetime,
    status_cd               char(1),
    status_time             datetime,
    subject_class_cd        varchar(10),
    to_time                 datetime,
    type_desc_txt           varchar(100),
    user_affiliation_txt    varchar(20),
    cd                      varchar(40),
    subject_entity_uid      bigint      NOT NULL,
    act_uid                 bigint      NOT NULL,
    type_cd                 varchar(50) NOT NULL,
    role_subject_entity_uid bigint      NOT NULL,
    role_role_seq           bigint      NOT NULL,
    role_cd                 varchar(40) NOT NULL,
    CONSTRAINT pk_participation PRIMARY KEY (subject_entity_uid, act_uid, type_cd)
)
    GO

CREATE TABLE participation_hist
(
    act_class_cd         varchar(7),
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    awareness_cd         varchar(20),
    awareness_desc_txt   varchar(100),
    cd                   varchar(50),
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    from_time            datetime,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    record_status_cd     varchar(20),
    record_status_time   datetime,
    role_seq             bigint,
    status_cd            char(1),
    status_time          datetime,
    subject_class_cd     varchar(7),
    to_time              datetime,
    type_desc_txt        varchar(100),
    user_affiliation_txt varchar(20),
    subject_entity_uid   bigint      NOT NULL,
    act_uid              bigint      NOT NULL,
    type_cd              varchar(50) NOT NULL,
    version_ctrl_nbr     smallint    NOT NULL,
    CONSTRAINT pk_participation_hist PRIMARY KEY (subject_entity_uid, act_uid, type_cd, version_ctrl_nbr)
)
    GO

CREATE TABLE patient_encounter
(
    patient_encounter_uid      bigint   NOT NULL,
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    acuity_level_cd            varchar(20),
    acuity_level_desc_txt      varchar(100),
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    admission_source_cd        varchar(20),
    admission_source_desc_txt  varchar(100),
    birth_encounter_ind        char(1),
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    priority_cd                varchar(20),
    priority_desc_txt          varchar(100),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    referral_source_cd         varchar(20),
    referral_source_desc_txt   varchar(100),
    repeat_nbr                 smallint,
    status_cd                  char(1),
    status_time                datetime,
    txt                        varchar(1000),
    user_affiliation_txt       varchar(20),
    program_jurisdiction_oid   bigint,
    shared_ind                 char(1)  NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_patient_encounter PRIMARY KEY (patient_encounter_uid)
)
    GO

CREATE TABLE patient_encounter_hist
(
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    acuity_level_cd            varchar(20),
    acuity_level_desc_txt      varchar(100),
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    admission_source_cd        varchar(20),
    admission_source_desc_txt  varchar(100),
    birth_encounter_ind        char(1),
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    priority_cd                varchar(20),
    priority_desc_txt          varchar(100),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    referral_source_cd         varchar(20),
    referral_source_desc_txt   varchar(100),
    repeat_nbr                 smallint,
    status_cd                  char(1),
    status_time                datetime,
    txt                        varchar(1000),
    user_affiliation_txt       varchar(20),
    program_jurisdiction_oid   bigint,
    shared_ind                 char(1)  NOT NULL,
    patient_encounter_uid      bigint   NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_patient_encounter_hist PRIMARY KEY (patient_encounter_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE person
(
    person_uid               bigint   NOT NULL,
    add_reason_cd            varchar(20),
    add_time                 datetime,
    add_user_id              bigint,
    administrative_gender_cd varchar(20),
    age_calc                 smallint,
    age_calc_time            datetime,
    age_calc_unit_cd         char(1),
    age_category_cd          varchar(20),
    age_reported             varchar(10),
    age_reported_time        datetime,
    age_reported_unit_cd     varchar(20),
    birth_gender_cd          char(1),
    birth_order_nbr          smallint,
    birth_time               datetime,
    birth_time_calc          datetime,
    cd                       varchar(50),
    cd_desc_txt              varchar(100),
    curr_sex_cd              char(1),
    deceased_ind_cd          varchar(20),
    deceased_time            datetime,
    description              varchar(2000),
    education_level_cd       varchar(20),
    education_level_desc_txt varchar(100),
    ethnic_group_ind         varchar(20),
    last_chg_reason_cd       varchar(20),
    last_chg_time            datetime,
    last_chg_user_id         bigint,
    local_id                 varchar(50),
    marital_status_cd        varchar(20),
    marital_status_desc_txt  varchar(100),
    mothers_maiden_nm        varchar(50),
    multiple_birth_ind       varchar(20),
    occupation_cd            varchar(20),
    preferred_gender_cd      varchar(20),
    prim_lang_cd             varchar(20),
    prim_lang_desc_txt       varchar(100),
    record_status_cd         varchar(20),
    record_status_time       datetime,
    status_cd                char(1),
    status_time              datetime,
    survived_ind_cd          char(1),
    user_affiliation_txt     varchar(20),
    first_nm                 varchar(50),
    last_nm                  varchar(50),
    middle_nm                varchar(50),
    nm_prefix                varchar(20),
    nm_suffix                varchar(20),
    preferred_nm             varchar(50),
    hm_street_addr1          varchar(100),
    hm_street_addr2          varchar(100),
    hm_city_cd               varchar(20),
    hm_city_desc_txt         varchar(100),
    hm_state_cd              varchar(20),
    hm_zip_cd                varchar(20),
    hm_cnty_cd               varchar(20),
    hm_cntry_cd              varchar(20),
    hm_phone_nbr             varchar(20),
    hm_phone_cntry_cd        varchar(20),
    hm_email_addr            varchar(100),
    cell_phone_nbr           varchar(20),
    wk_street_addr1          varchar(100),
    wk_street_addr2          varchar(100),
    wk_city_cd               varchar(20),
    wk_city_desc_txt         varchar(100),
    wk_state_cd              varchar(20),
    wk_zip_cd                varchar(20),
    wk_cnty_cd               varchar(20),
    wk_cntry_cd              varchar(20),
    wk_phone_nbr             varchar(20),
    wk_phone_cntry_cd        varchar(20),
    wk_email_addr            varchar(100),
    ssn                      varchar(100),
    medicaid_num             varchar(100),
    dl_num                   varchar(100),
    dl_state_cd              varchar(20),
    race_cd                  varchar(20),
    race_seq_nbr             smallint,
    race_category_cd         varchar(20),
    ethnicity_group_cd       varchar(20),
    ethnic_group_seq_nbr     smallint,
    adults_in_house_nbr      smallint,
    children_in_house_nbr    smallint,
    birth_city_cd            varchar(20),
    birth_city_desc_txt      varchar(100),
    birth_cntry_cd           varchar(20),
    birth_state_cd           varchar(20),
    race_desc_txt            varchar(100),
    ethnic_group_desc_txt    varchar(100),
    version_ctrl_nbr         smallint NOT NULL,
    as_of_date_admin         datetime,
    as_of_date_ethnicity     datetime,
    as_of_date_general       datetime,
    as_of_date_morbidity     datetime,
    as_of_date_sex           datetime,
    electronic_ind           char(1),
    person_parent_uid        bigint,
    dedup_match_ind          char(1),
    group_nbr                int,
    group_time               datetime,
    edx_ind                  varchar(1),
    speaks_english_cd        varchar(20),
    additional_gender_cd     varchar(50),
    ehars_id                 varchar(20),
    ethnic_unk_reason_cd     varchar(20),
    sex_unk_reason_cd        varchar(20),
    CONSTRAINT pk_person PRIMARY KEY (person_uid)
)
    GO

CREATE TABLE person_ethnic_group
(
    add_reason_cd         varchar(20),
    add_time              datetime,
    add_user_id           bigint,
    ethnic_group_desc_txt varchar(100),
    last_chg_reason_cd    varchar(20),
    last_chg_time         datetime,
    last_chg_user_id      bigint,
    record_status_cd      varchar(20),
    record_status_time    datetime,
    user_affiliation_txt  varchar(20),
    person_uid            bigint      NOT NULL,
    ethnic_group_cd       varchar(20) NOT NULL,
    CONSTRAINT pk_person_ethnic_group PRIMARY KEY (person_uid, ethnic_group_cd)
)
    GO

CREATE TABLE person_ethnic_group_hist
(
    add_reason_cd         varchar(20),
    add_time              datetime,
    add_user_id           bigint,
    ethnic_group_desc_txt varchar(100),
    last_chg_reason_cd    varchar(20),
    last_chg_time         datetime,
    last_chg_user_id      bigint,
    record_status_cd      varchar(20),
    record_status_time    datetime,
    user_affiliation_txt  varchar(20),
    person_uid            bigint      NOT NULL,
    ethnic_group_cd       varchar(20) NOT NULL,
    version_ctrl_nbr      smallint    NOT NULL,
    CONSTRAINT pk_person_ethnic_group_hist PRIMARY KEY (person_uid, ethnic_group_cd, version_ctrl_nbr)
)
    GO

CREATE TABLE person_hist
(
    add_reason_cd            varchar(20),
    add_time                 datetime,
    add_user_id              bigint,
    administrative_gender_cd varchar(20),
    age_calc                 smallint,
    age_calc_time            datetime,
    age_calc_unit_cd         char(1),
    age_category_cd          varchar(20),
    age_reported             varchar(10),
    age_reported_time        datetime,
    age_reported_unit_cd     varchar(20),
    birth_gender_cd          char(1),
    birth_order_nbr          smallint,
    birth_time               datetime,
    birth_time_calc          datetime,
    cd                       varchar(50),
    cd_desc_txt              varchar(100),
    curr_sex_cd              char(1),
    deceased_ind_cd          varchar(20),
    deceased_time            datetime,
    description              varchar(2000),
    education_level_cd       varchar(20),
    education_level_desc_txt varchar(100),
    ethnic_group_ind         varchar(20),
    last_chg_reason_cd       varchar(20),
    last_chg_time            datetime,
    last_chg_user_id         bigint,
    local_id                 varchar(50),
    marital_status_cd        varchar(20),
    marital_status_desc_txt  varchar(20),
    mothers_maiden_nm        varchar(50),
    multiple_birth_ind       varchar(20),
    occupation_cd            varchar(20),
    preferred_gender_cd      varchar(20),
    prim_lang_cd             varchar(20),
    prim_lang_desc_txt       varchar(100),
    record_status_cd         varchar(20),
    record_status_time       datetime,
    status_cd                char(1),
    status_time              datetime,
    survived_ind_cd          char(1),
    user_affiliation_txt     varchar(20),
    first_nm                 varchar(50),
    last_nm                  varchar(50),
    middle_nm                varchar(50),
    nm_prefix                varchar(20),
    nm_suffix                varchar(20),
    preferred_nm             varchar(50),
    hm_street_addr1          varchar(100),
    hm_street_addr2          varchar(100),
    hm_city_cd               varchar(20),
    hm_city_desc_txt         varchar(100),
    hm_state_cd              varchar(20),
    hm_zip_cd                varchar(20),
    hm_cnty_cd               varchar(20),
    hm_cntry_cd              varchar(20),
    hm_phone_nbr             varchar(20),
    hm_phone_cntry_cd        varchar(20),
    hm_email_addr            varchar(100),
    cell_phone_nbr           varchar(20),
    wk_street_addr1          varchar(100),
    wk_street_addr2          varchar(100),
    wk_city_cd               varchar(20),
    wk_city_desc_txt         varchar(100),
    wk_state_cd              varchar(20),
    wk_zip_cd                varchar(20),
    wk_cnty_cd               varchar(20),
    wk_cntry_cd              varchar(20),
    wk_phone_nbr             varchar(20),
    wk_phone_cntry_cd        varchar(20),
    wk_email_addr            varchar(100),
    ssn                      varchar(100),
    medicaid_num             varchar(100),
    dl_num                   varchar(100),
    dl_state_cd              varchar(20),
    race_cd                  varchar(20),
    race_seq_nbr             smallint,
    race_category_cd         varchar(20),
    ethnicity_group_cd       varchar(20),
    ethnicity_group_seq_nbr  smallint,
    adults_in_house_nbr      smallint,
    children_in_house_nbr    smallint,
    birth_city_cd            varchar(20),
    birth_city_desc_txt      varchar(100),
    birth_cntry_cd           varchar(20),
    birth_state_cd           varchar(20),
    race_desc_txt            varchar(100),
    ethnic_group_desc_txt    varchar(100),
    as_of_date_admin         datetime,
    as_of_date_ethnicity     datetime,
    as_of_date_general       datetime,
    as_of_date_morbidity     datetime,
    as_of_date_sex           datetime,
    electronic_ind           char(1),
    person_parent_uid        bigint,
    dedup_match_ind          char(1),
    group_nbr                int,
    group_time               datetime,
    edx_ind                  varchar(1),
    speaks_english_cd        varchar(20),
    additional_gender_cd     varchar(50),
    ehars_id                 varchar(20),
    ethnic_unk_reason_cd     varchar(20),
    sex_unk_reason_cd        varchar(20),
    person_uid               bigint   NOT NULL,
    version_ctrl_nbr         smallint NOT NULL,
    CONSTRAINT pk_person_hist PRIMARY KEY (person_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE person_merge
(
    superceded_parent_uid       bigint   NOT NULL,
    surviving_version_ctrl_nbr  smallint,
    surviving_parent_uid        bigint   NOT NULL,
    record_status_cd            varchar(20),
    record_status_time          datetime,
    merge_user_id               varchar(20),
    merge_time                  datetime,
    superced_person_uid         bigint   NOT NULL,
    superceded_version_ctrl_nbr smallint NOT NULL,
    surviving_person_uid        bigint   NOT NULL,
    CONSTRAINT pk_person_merge PRIMARY KEY (superced_person_uid, superceded_version_ctrl_nbr, surviving_person_uid)
)
    GO

CREATE TABLE person_name
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    default_nm_ind       char(1),
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    first_nm             varchar(50),
    first_nm_sndx        varchar(50),
    from_time            datetime,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    last_nm              varchar(50),
    last_nm_sndx         varchar(50),
    last_nm2             varchar(50),
    last_nm2_sndx        varchar(50),
    middle_nm            varchar(50),
    middle_nm2           varchar(50),
    nm_degree            varchar(20),
    nm_prefix            varchar(20),
    nm_suffix            varchar(20),
    nm_use_cd            varchar(20),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    status_cd            char(1)  NOT NULL,
    status_time          datetime NOT NULL,
    to_time              datetime,
    user_affiliation_txt varchar(20),
    as_of_date           datetime,
    person_uid           bigint   NOT NULL,
    person_name_seq      smallint NOT NULL,
    CONSTRAINT pk_person_name PRIMARY KEY (person_uid, person_name_seq)
)
    GO

CREATE TABLE person_name_hist
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    default_nm_ind       char(1),
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    first_nm             varchar(50),
    first_nm_sndx        varchar(50),
    from_time            datetime,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    last_nm              varchar(50),
    last_nm_sndx         varchar(50),
    last_nm2             varchar(50),
    last_nm2_sndx        varchar(50),
    middle_nm            varchar(50),
    middle_nm2           varchar(50),
    nm_degree            varchar(20),
    nm_prefix            varchar(20),
    nm_suffix            varchar(20),
    nm_use_cd            varchar(20),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    status_cd            char(1)  NOT NULL,
    status_time          datetime NOT NULL,
    to_time              datetime,
    user_affiliation_txt varchar(20),
    as_of_date           datetime,
    person_uid           bigint   NOT NULL,
    person_name_seq      smallint NOT NULL,
    version_ctrl_nbr     smallint NOT NULL,
    CONSTRAINT pk_person_name_hist PRIMARY KEY (person_uid, person_name_seq, version_ctrl_nbr)
)
    GO

CREATE TABLE person_race
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    race_category_cd     varchar(20),
    race_desc_txt        varchar(100),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    user_affiliation_txt varchar(20),
    as_of_date           datetime,
    person_uid           bigint      NOT NULL,
    race_cd              varchar(20) NOT NULL,
    CONSTRAINT pk_person_race PRIMARY KEY (person_uid, race_cd)
)
    GO

CREATE TABLE person_race_hist
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    race_category_cd     varchar(20),
    race_desc_txt        varchar(100),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    user_affiliation_txt varchar(20),
    as_of_date           datetime,
    person_uid           bigint      NOT NULL,
    race_cd              varchar(20) NOT NULL,
    version_ctrl_nbr     smallint    NOT NULL,
    CONSTRAINT pk_person_race_hist PRIMARY KEY (person_uid, race_cd, version_ctrl_nbr)
)
    GO

CREATE TABLE physical_locator
(
    physical_locator_uid bigint NOT NULL,
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    image_txt            varbinary( MAX),
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    locator_txt          varchar(1000),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    user_affiliation_txt varchar(20),
    CONSTRAINT pk_physical_locator PRIMARY KEY (physical_locator_uid)
)
    GO

CREATE TABLE physical_locator_hist
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    image_txt            varbinary( MAX),
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    locator_txt          varchar(1000),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    user_affiliation_txt varchar(20),
    physical_locator_uid bigint   NOT NULL,
    version_ctrl_nbr     smallint NOT NULL,
    CONSTRAINT pk_physical_locator_hist PRIMARY KEY (physical_locator_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE place
(
    place_uid            bigint   NOT NULL,
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    cd                   varchar(50),
    cd_desc_txt          varchar(100),
    description          varchar(1000),
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    from_time            datetime,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    local_id             varchar(50),
    nm                   varchar(50),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    status_cd            char(1),
    status_time          datetime,
    to_time              datetime,
    user_affiliation_txt varchar(20),
    street_addr1         varchar(100),
    street_addr2         varchar(100),
    city_cd              varchar(20),
    city_desc_txt        varchar(100),
    state_cd             varchar(20),
    zip_cd               varchar(20),
    cnty_cd              varchar(20),
    cntry_cd             varchar(20),
    phone_nbr            varchar(20),
    phone_cntry_cd       varchar(20),
    version_ctrl_nbr     smallint NOT NULL,
    CONSTRAINT pk_place PRIMARY KEY (place_uid)
)
    GO

CREATE TABLE place_hist
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    cd                   varchar(50),
    cd_desc_txt          varchar(100),
    description          varchar(1000),
    duration_amt         varchar(20),
    duration_unit_cd     varchar(20),
    from_time            datetime,
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    local_id             varchar(50),
    nm                   varchar(100),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    status_cd            char(1)  NOT NULL,
    status_time          datetime NOT NULL,
    to_time              datetime,
    user_affiliation_txt varchar(20),
    street_addr1         varchar(100),
    street_addr2         varchar(100),
    city_cd              varchar(20),
    city_desc_txt        varchar(100),
    state_cd             varchar(20),
    zip_cd               varchar(20),
    cnty_cd              varchar(20),
    cntry_cd             varchar(20),
    phone_nbr            varchar(20),
    phone_cntry_cd       varchar(20),
    place_uid            bigint   NOT NULL,
    version_ctrl_nbr     smallint NOT NULL,
    CONSTRAINT pk_place_hist PRIMARY KEY (place_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE postal_locator
(
    postal_locator_uid             bigint NOT NULL,
    add_reason_cd                  varchar(80),
    add_time                       datetime,
    add_user_id                    bigint,
    census_block_cd                varchar(20),
    census_minor_civil_division_cd varchar(20),
    census_track_cd                varchar(20),
    city_cd                        varchar(20),
    city_desc_txt                  varchar(100),
    cntry_cd                       varchar(20),
    cntry_desc_txt                 varchar(100),
    cnty_cd                        varchar(20),
    cnty_desc_txt                  varchar(100),
    last_chg_reason_cd             varchar(20),
    last_chg_time                  datetime,
    last_chg_user_id               bigint,
    msa_congress_district_cd       varchar(20),
    record_status_cd               varchar(20),
    record_status_time             datetime,
    region_district_cd             varchar(20),
    state_cd                       varchar(20),
    street_addr1                   varchar(100),
    street_addr2                   varchar(100),
    user_affiliation_txt           varchar(20),
    zip_cd                         varchar(20),
    geocode_match_ind              varchar(1),
    within_city_limits_ind         varchar(3),
    census_tract                   varchar(10),
    CONSTRAINT pk_postal_locator PRIMARY KEY (postal_locator_uid)
)
    GO

CREATE TABLE postal_locator_hist
(
    add_reason_cd                  varchar(20),
    add_time                       datetime,
    add_user_id                    bigint,
    census_block_cd                varchar(20),
    census_minor_civil_division_cd varchar(20),
    census_track_cd                varchar(20),
    city_cd                        varchar(20),
    city_desc_txt                  varchar(100),
    cntry_cd                       varchar(20),
    cntry_desc_txt                 varchar(100),
    cnty_cd                        varchar(20),
    cnty_desc_txt                  varchar(100),
    last_chg_reason_cd             varchar(20),
    last_chg_time                  datetime,
    last_chg_user_id               bigint,
    msa_congress_district_cd       varchar(20),
    record_status_cd               varchar(20),
    record_status_time             datetime,
    region_district_cd             varchar(20),
    state_cd                       varchar(30),
    street_addr1                   varchar(100),
    street_addr2                   varchar(100),
    user_affiliation_txt           varchar(20),
    zip_cd                         varchar(20),
    geocode_match_ind              varchar(1),
    within_city_limits_ind         varchar(3),
    census_tract                   varchar(10),
    postal_locator_uid             bigint   NOT NULL,
    version_ctrl_nbr               smallint NOT NULL,
    CONSTRAINT pk_postal_locator_hist PRIMARY KEY (postal_locator_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE procedure1
(
    intervention_uid       bigint NOT NULL,
    approach_site_cd       varchar(20),
    approach_site_desc_txt varchar(100),
    CONSTRAINT pk_procedure1 PRIMARY KEY (intervention_uid)
)
    GO

CREATE TABLE procedure1_hist
(
    approach_site_cd       varchar(20),
    approach_site_desc_txt varchar(100),
    intervention_uid       bigint   NOT NULL,
    version_ctrl_nbr       smallint NOT NULL,
    CONSTRAINT pk_procedure1_hist PRIMARY KEY (intervention_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE public_health_case
(
    public_health_case_uid      bigint   NOT NULL,
    activity_duration_amt       varchar(20),
    activity_duration_unit_cd   varchar(20),
    activity_from_time          datetime,
    activity_to_time            datetime,
    add_reason_cd               varchar(20),
    add_time                    datetime,
    add_user_id                 bigint,
    case_class_cd               varchar(20),
    case_type_cd                char(1),
    cd                          varchar(50),
    cd_desc_txt                 varchar(100),
    cd_system_cd                varchar(20),
    cd_system_desc_txt          varchar(100),
    confidentiality_cd          varchar(20),
    confidentiality_desc_txt    varchar(100),
    detection_method_cd         varchar(20),
    detection_method_desc_txt   varchar(100),
    diagnosis_time              datetime,
    disease_imported_cd         varchar(20),
    disease_imported_desc_txt   varchar(100),
    effective_duration_amt      varchar(20),
    effective_duration_unit_cd  varchar(20),
    effective_from_time         datetime,
    effective_to_time           datetime,
    group_case_cnt              smallint,
    investigation_status_cd     varchar(20),
    jurisdiction_cd             varchar(20),
    last_chg_reason_cd          varchar(20),
    last_chg_time               datetime,
    last_chg_user_id            bigint,
    local_id                    varchar(50),
    mmwr_week                   varchar(10),
    mmwr_year                   varchar(10),
    outbreak_ind                varchar(20),
    outbreak_from_time          datetime,
    outbreak_to_time            datetime,
    outbreak_name               varchar(100),
    outcome_cd                  varchar(20),
    pat_age_at_onset            varchar(20),
    pat_age_at_onset_unit_cd    varchar(20),
    patient_group_id            bigint,
    prog_area_cd                varchar(20),
    record_status_cd            varchar(20),
    record_status_time          datetime,
    repeat_nbr                  smallint,
    rpt_cnty_cd                 varchar(20),
    rpt_form_cmplt_time         datetime,
    rpt_source_cd               varchar(20),
    rpt_source_cd_desc_txt      varchar(100),
    rpt_to_county_time          datetime,
    rpt_to_state_time           datetime,
    status_cd                   char(1),
    status_time                 datetime,
    transmission_mode_cd        varchar(20),
    transmission_mode_desc_txt  varchar(100),
    txt                         varchar(2000),
    user_affiliation_txt        varchar(20),
    program_jurisdiction_oid    bigint,
    shared_ind                  char(1)  NOT NULL,
    version_ctrl_nbr            smallint NOT NULL,
    investigator_assigned_time  datetime,
    hospitalized_ind_cd         varchar(20),
    hospitalized_admin_time     datetime,
    hospitalized_discharge_time datetime,
    hospitalized_duration_amt   decimal(18, 0),
    pregnant_ind_cd             varchar(20),
    day_care_ind_cd             varchar(20),
    food_handler_ind_cd         varchar(20),
    imported_country_cd         varchar(20),
    imported_state_cd           varchar(20),
    imported_city_desc_txt      varchar(250),
    imported_county_cd          varchar(20),
    deceased_time               datetime,
    count_interval_cd           varchar(20),
    priority_cd                 varchar(50),
    contact_inv_txt             varchar(2000),
    infectious_from_date        datetime,
    infectious_to_date          datetime,
    contact_inv_status_cd       varchar(50),
    referral_basis_cd           varchar(20),
    curr_process_state_cd       varchar(20),
    inv_priority_cd             varchar(20),
    coinfection_id              varchar(50),
    CONSTRAINT pk_public_health_case PRIMARY KEY (public_health_case_uid)
)
    GO

CREATE TABLE public_health_case_fact
(
    public_health_case_uid          bigint       NOT NULL,
    adults_in_house_nbr             smallint,
    age_in_months                   smallint,
    age_in_years                    smallint,
    age_category_cd                 varchar(20),
    age_reported_time               datetime,
    age_reported_unit_cd            varchar(20),
    age_reported                    decimal(8, 0),
    awareness_cd                    varchar(20),
    awareness_desc_txt              varchar(100),
    birth_gender_cd                 char(1),
    birth_order_nbr                 smallint,
    birth_time                      datetime,
    birth_time_calc                 datetime,
    birth_time_std                  datetime,
    case_class_cd                   varchar(20)  NOT NULL,
    case_type_cd                    char(1),
    cd_system_cd                    varchar(20),
    cd_system_desc_txt              varchar(100),
    census_block_cd                 varchar(20),
    census_minor_civil_division_cd  varchar(20),
    census_track_cd                 varchar(20),
    cnty_code_desc_txt              varchar(200),
    children_in_house_nbr           smallint,
    city_cd                         varchar(20),
    city_desc_txt                   varchar(100),
    confidentiality_cd              varchar(20),
    confidentiality_desc_txt        varchar(100),
    confirmation_method_cd          varchar(300),
    confirmation_method_time        datetime,
    county                          varchar(255),
    cntry_cd                        varchar(20),
    cnty_cd                         varchar(20),
    curr_sex_cd                     char(1),
    deceased_ind_cd                 varchar(20),
    deceased_time                   datetime,
    detection_method_cd             varchar(20),
    detection_method_desc_txt       varchar(100),
    diagnosis_date                  datetime,
    disease_imported_cd             varchar(20),
    disease_imported_desc_txt       varchar(100),
    education_level_cd              varchar(20),
    elp_class_cd                    varchar(10),
    elp_from_time                   datetime,
    elp_to_time                     datetime,
    ethnic_group_ind                varchar(20),
    ethnic_group_ind_desc           varchar(50),
    event_date                      datetime,
    event_type                      varchar(10),
    education_level_desc_txt        varchar(100),
    first_notification_senddate     datetime,
    first_notificationdate          datetime,
    first_notification_status       varchar(20),
    first_notification_submitted_by bigint,
    geo_latitude                    float(53),
    geo_longitude                   float(53),
    group_case_cnt                  decimal(11, 5),
    investigation_status_cd         varchar(20),
    investigator_assigneddate       datetime,
    investigator_name               varchar(102),
    investigator_phone              varchar(20),
    jurisdiction_cd                 varchar(20),
    last_notificationdate           datetime,
    last_notification_senddate      datetime,
    last_notification_submitted_by  bigint,
    marital_status_cd               varchar(20),
    marital_status_desc_txt         varchar(100),
    mart_record_creation_date       datetime,
    mart_record_creation_time       datetime,
    mmwr_week                       decimal(8, 0),
    mmwr_year                       decimal(8, 0),
    msa_congress_district_cd        varchar(20),
    multiple_birth_ind              varchar(20),
    notif_created_count             int,
    notificationdate                datetime,
    notif_sent_count                int,
    occupation_cd                   varchar(20),
    on_set_date                     datetime,
    organization_name               varchar(100),
    outcome_cd                      varchar(20),
    outbreak_from_time              datetime,
    outbreak_ind                    varchar(20),
    outbreak_name                   varchar(100),
    outbreak_to_time                datetime,
    par_type_cd                     varchar(50),
    pat_age_at_onset                decimal(8, 0),
    pat_age_at_onset_unit_cd        varchar(20),
    postal_locator_uid              bigint,
    person_cd                       varchar(50),
    person_code_desc                varchar(100),
    person_uid                      bigint,
    phc_add_time                    datetime,
    phc_code                        varchar(50)  NOT NULL,
    phc_code_desc                   varchar(100) NOT NULL,
    phc_code_short_desc             varchar(50),
    prim_lang_cd                    varchar(20),
    prim_lang_desc_txt              varchar(100),
    prog_area_cd                    varchar(20)  NOT NULL,
    provider_phone                  varchar(20),
    provider_name                   varchar(102),
    pst_record_status_time          datetime,
    pst_record_status_cd            varchar(20),
    race_concatenated_txt           varchar(100),
    race_concatenated_desc_txt      varchar(500),
    region_district_cd              varchar(20),
    record_status_cd                varchar(20),
    reporter_name                   varchar(102),
    reporter_phone                  varchar(20),
    rpt_cnty_cd                     varchar(20),
    rpt_form_cmplt_time             datetime,
    rpt_source_cd                   varchar(20),
    rpt_source_desc_txt             varchar(100),
    rpt_to_county_time              datetime,
    rpt_to_state_time               datetime,
    shared_ind                      varchar(1),
    state                           varchar(255),
    state_cd                        varchar(20),
    state_code_short_desc_txt       varchar(200),
    status_cd                       char(1),
    street_addr1                    varchar(100),
    street_addr2                    varchar(100),
    elp_use_cd                      varchar(20),
    zip_cd                          varchar(20),
    patient_name                    varchar(102),
    jurisdiction                    varchar(50),
    investigationstartdate          datetime,
    program_jurisdiction_oid        bigint,
    report_date                     datetime,
    person_parent_uid               bigint,
    person_local_id                 varchar(50),
    sub_addr_as_of_date             datetime,
    state_case_id                   varchar(199),
    local_id                        varchar(50),
    notifcurrentstate               varchar(50),
    age_reported_unit_desc_txt      varchar(300),
    birth_gender_desc_txt           varchar(300),
    case_class_desc_txt             varchar(300),
    cntry_desc_txt                  varchar(300),
    curr_sex_desc_txt               varchar(300),
    investigation_status_desc_txt   varchar(300),
    occupation_desc_txt             varchar(300),
    outcome_desc_txt                varchar(300),
    pat_age_at_onset_unit_desc_txt  varchar(300),
    prog_area_desc_txt              varchar(300),
    rpt_cnty_desc_txt               varchar(300),
    outbreak_name_desc              varchar(300),
    confirmation_method_desc_txt    varchar(300),
    lastupdate                      datetime,
    phctxt                          varchar(2000),
    notitxt                         varchar(2000),
    notification_local_id           varchar(50),
    hsptl_admission_dt              datetime,
    hsptl_discharge_dt              datetime,
    hospitalized_ind                varchar(100),
    CONSTRAINT pk_publichealthcasefact PRIMARY KEY (public_health_case_uid)
)
    GO

CREATE TABLE public_health_case_hist
(
    activity_duration_amt       varchar(20),
    activity_duration_unit_cd   varchar(20),
    activity_from_time          datetime,
    activity_to_time            datetime,
    add_reason_cd               varchar(20),
    add_time                    datetime,
    add_user_id                 bigint,
    cd                          varchar(50),
    cd_desc_txt                 varchar(100),
    cd_system_cd                varchar(20),
    cd_system_desc_txt          varchar(100),
    case_class_cd               varchar(20),
    case_type_cd                char(1),
    confidentiality_cd          varchar(20),
    confidentiality_desc_txt    varchar(100),
    detection_method_cd         varchar(20),
    detection_method_desc_txt   varchar(100),
    diagnosis_time              datetime,
    disease_imported_cd         varchar(20),
    disease_imported_desc_txt   varchar(100),
    effective_duration_amt      varchar(20),
    effective_duration_unit_cd  varchar(20),
    effective_from_time         datetime,
    effective_to_time           datetime,
    group_case_cnt              smallint,
    investigation_status_cd     varchar(20),
    jurisdiction_cd             varchar(20),
    last_chg_reason_cd          varchar(20),
    last_chg_time               datetime,
    last_chg_user_id            bigint,
    local_id                    varchar(50),
    mmwr_year                   varchar(10),
    mmwr_week                   varchar(10),
    outbreak_ind                varchar(20),
    outbreak_from_time          datetime,
    outbreak_to_time            datetime,
    outbreak_name               varchar(100),
    outcome_cd                  varchar(20),
    pat_age_at_onset            varchar(10),
    pat_age_at_onset_unit_cd    varchar(20),
    patient_group_id            bigint,
    prog_area_cd                varchar(20),
    record_status_cd            varchar(20),
    record_status_time          datetime,
    repeat_nbr                  smallint,
    rpt_cnty_cd                 varchar(20),
    rpt_form_cmplt_time         datetime,
    rpt_source_cd               varchar(20),
    rpt_source_cd_desc_txt      varchar(100),
    rpt_to_county_time          datetime,
    rpt_to_state_time           datetime,
    status_cd                   char(1),
    status_time                 datetime,
    transmission_mode_cd        varchar(20),
    transmission_mode_desc_txt  varchar(100),
    txt                         varchar(2000),
    user_affiliation_txt        varchar(20),
    program_jurisdiction_oid    bigint,
    shared_ind                  char(1)  NOT NULL,
    investigator_assigned_time  datetime,
    hospitalized_ind_cd         varchar(20),
    hospitalized_admin_time     datetime,
    hospitalized_discharge_time datetime,
    hospitalized_duration_amt   decimal(18, 0),
    pregnant_ind_cd             varchar(20),
    day_care_ind_cd             varchar(20),
    food_handler_ind_cd         varchar(20),
    imported_country_cd         varchar(20),
    imported_state_cd           varchar(20),
    imported_city_desc_txt      varchar(250),
    imported_county_cd          varchar(20),
    deceased_time               datetime,
    count_interval_cd           varchar(20),
    priority_cd                 varchar(50),
    contact_inv_txt             varchar(2000),
    infectious_from_date        datetime,
    infectious_to_date          datetime,
    contact_inv_status_cd       varchar(50),
    referral_basis_cd           varchar(20),
    curr_process_state_cd       varchar(20),
    inv_priority_cd             varchar(20),
    coinfection_id              varchar(50),
    public_health_case_uid      bigint   NOT NULL,
    version_ctrl_nbr            smallint NOT NULL,
    CONSTRAINT pk_public_health_case_hist PRIMARY KEY (public_health_case_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE referral
(
    referral_uid               bigint   NOT NULL,
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    reason_txt                 varchar(100),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    referral_desc_txt          varchar(100),
    repeat_nbr                 smallint,
    status_cd                  char(1),
    status_time                datetime,
    txt                        varchar(1000),
    user_affiliation_txt       varchar(20),
    program_jurisdiction_oid   bigint,
    shared_ind                 char(1)  NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_referral PRIMARY KEY (referral_uid)
)
    GO

CREATE TABLE referral_hist
(
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    reason_txt                 varchar(100),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    referral_desc_txt          varchar(100),
    repeat_nbr                 smallint,
    status_cd                  char(1),
    status_time                datetime,
    txt                        varchar(1000),
    user_affiliation_txt       varchar(20),
    program_jurisdiction_oid   bigint,
    shared_ind                 char(1)  NOT NULL,
    referral_uid               bigint   NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_referral_hist PRIMARY KEY (referral_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE report
(
    add_reason_cd           varchar(20),
    add_time                datetime,
    add_user_uid            bigint,
    desc_txt                varchar(300),
    effective_from_time     datetime,
    effective_to_time       datetime,
    filter_mode             char(1),
    is_modifiable_ind       char(1),
    location                varchar(300),
    owner_uid               bigint,
    org_access_permis       varchar(2000),
    prog_area_access_permis varchar(2000),
    report_title            varchar(100),
    report_type_code        varchar(20),
    shared                  char(1),
    status_cd               char(1),
    status_time             datetime,
    category                varchar(20),
    section_cd              varchar(5) NOT NULL,
    data_source_uid         bigint     NOT NULL,
    report_uid              bigint     NOT NULL,
    CONSTRAINT pk_report PRIMARY KEY (data_source_uid, report_uid)
)
    GO

CREATE TABLE report_filter
(
    report_filter_uid bigint NOT NULL,
    filter_uid        bigint NOT NULL,
    status_cd         char(1),
    max_value_cnt     int,
    min_value_cnt     int,
    column_uid        bigint,
    data_source_uid   bigint NOT NULL,
    report_uid        bigint NOT NULL,
    CONSTRAINT pk_report_filter PRIMARY KEY (report_filter_uid)
)
    GO

CREATE TABLE report_filter_validation
(
    report_filter_validation_uid bigint NOT NULL,
    report_filter_uid            bigint NOT NULL,
    report_filter_ind            varchar(1),
    status_cd                    char(1),
    status_time                  datetime,
    CONSTRAINT pk_report_filter_validation PRIMARY KEY (report_filter_validation_uid)
)
    GO

CREATE TABLE report_section
(
    add_time           datetime,
    add_user_id        bigint,
    comments           varchar(250),
    last_chg_user_id   bigint,
    last_chg_time      datetime,
    section_desc_txt   varchar(100) NOT NULL,
    status_cd          varchar(10),
    report_section_uid bigint       NOT NULL,
    section_cd         varchar(50)  NOT NULL,
    CONSTRAINT pk_report_section PRIMARY KEY (report_section_uid, section_cd)
)
    GO

CREATE TABLE report_sort_column
(
    report_sort_column_uid   bigint NOT NULL,
    report_sort_order_code   varchar(4),
    report_sort_sequence_num int,
    column_uid               bigint,
    status_cd                char(1),
    status_time              datetime,
    data_source_uid          bigint,
    report_uid               bigint,
    CONSTRAINT pk_report_sort_column PRIMARY KEY (report_sort_column_uid)
)
    GO

CREATE TABLE role
(
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd_desc_txt                varchar(100),
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    record_status_cd           varchar(20),
    record_status_time         datetime,
    scoping_class_cd           varchar(10),
    scoping_entity_uid         bigint,
    scoping_role_cd            varchar(20),
    scoping_role_seq           smallint,
    status_cd                  char(1)     NOT NULL,
    status_time                datetime,
    subject_class_cd           varchar(10),
    user_affiliation_txt       varchar(20),
    subject_entity_uid         bigint      NOT NULL,
    role_seq                   bigint      NOT NULL,
    cd                         varchar(40) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (subject_entity_uid, role_seq, cd)
)
    GO

CREATE TABLE role_hist
(
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    cd_desc_txt                varchar(100),
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    record_status_cd           varchar(20),
    record_status_time         datetime,
    scoping_class_cd           varchar(10),
    scoping_entity_uid         bigint,
    scoping_role_cd            varchar(20),
    scoping_role_seq           smallint,
    status_time                datetime    NOT NULL,
    status_cd                  char(1),
    subject_class_cd           varchar(10),
    user_affiliation_txt       varchar(20),
    subject_entity_uid         bigint      NOT NULL,
    role_seq                   bigint      NOT NULL,
    cd                         varchar(40) NOT NULL,
    version_ctrl_nbr           smallint    NOT NULL,
    CONSTRAINT pk_role_hist PRIMARY KEY (subject_entity_uid, role_seq, cd, version_ctrl_nbr)
)
    GO

CREATE TABLE [rule]
(
    rule_uid
    bigint
    NOT
    NULL,
    rule_name
    varchar
(
    50
),
    rule_type_uid bigint NOT NULL,
    comments varchar
(
    255
),
    CONSTRAINT pk_rule PRIMARY KEY
(
    rule_uid
)
    )
    GO

CREATE TABLE rule_instance
(
    rule_instance_uid bigint NOT NULL,
    conseq_ind_uid    bigint NOT NULL,
    rule_uid          bigint NOT NULL,
    comments          varchar(255),
    CONSTRAINT pk_rule_instance PRIMARY KEY (rule_instance_uid)
)
    GO

CREATE TABLE rule_type
(
    rule_type_uid         bigint  NOT NULL,
    rule_type_code        char(1) NOT NULL,
    rule_type_description varchar(100),
    CONSTRAINT pk_rule_type PRIMARY KEY (rule_type_uid)
)
    GO

CREATE TABLE security_log
(
    security_log_uid bigint NOT NULL,
    user_id          varchar(50),
    event_type_cd    varchar(20),
    event_time       datetime,
    session_id       varchar(500),
    user_ip_addr     varchar(500),
    nedss_entry_id   bigint,
    first_nm         varchar(50),
    last_nm          varchar(50),
    CONSTRAINT pk_security_log PRIMARY KEY (security_log_uid)
)
    GO

CREATE TABLE source_field
(
    source_field_uid      bigint NOT NULL,
    nbs_metadata_rule_uid bigint NOT NULL,
    rule_instance_uid     bigint NOT NULL,
    source_field_seq_nbr  int,
    CONSTRAINT pk_source_field PRIMARY KEY (source_field_uid)
)
    GO

CREATE TABLE source_value
(
    source_value_uid  bigint NOT NULL,
    source_field_uid  bigint NOT NULL,
    source_value      varchar(50),
    operator_type_uid bigint NOT NULL,
    CONSTRAINT pk_source_value PRIMARY KEY (source_value_uid)
)
    GO

CREATE TABLE state_defined_field_data
(
    add_time            datetime,
    business_object_nm  varchar(50) NOT NULL,
    last_chg_time       datetime,
    ldf_value           varchar(2000),
    version_ctrl_nbr    smallint,
    ldf_uid             bigint      NOT NULL,
    business_object_uid bigint      NOT NULL,
    CONSTRAINT pk_state_defined_field_data PRIMARY KEY (ldf_uid, business_object_uid)
)
    GO

CREATE TABLE state_defined_field_data_hist
(
    add_time            datetime,
    business_object_nm  varchar(50) NOT NULL,
    last_chg_time       datetime,
    ldf_value           varchar(2000),
    ldf_uid             bigint      NOT NULL,
    business_object_uid bigint      NOT NULL,
    version_ctrl_nbr    smallint    NOT NULL,
    CONSTRAINT pk_state_defined_field_data_hist PRIMARY KEY (ldf_uid, business_object_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE state_defined_field_metadata
(
    ldf_uid                     bigint      NOT NULL,
    active_ind                  char(1),
    add_time                    datetime    NOT NULL,
    admin_comment               varchar(300),
    business_object_nm          varchar(50),
    category_type               varchar(50),
    cdc_national_id             varchar(50),
    class_cd                    varchar(20),
    code_set_nm                 varchar(256),
    condition_cd                varchar(10),
    condition_desc_txt          varchar(100),
    data_type                   varchar(50),
    deployment_cd               varchar(10),
    display_order_nbr           int,
    field_size                  varchar(10),
    label_txt                   varchar(300),
    ldf_page_id                 varchar(50),
    required_ind                char(1),
    state_cd                    varchar(10),
    validation_txt              varchar(50),
    validation_jscript_txt      varchar(3000),
    record_status_time          datetime    NOT NULL,
    record_status_cd            varchar(20) NOT NULL,
    custom_subform_metadata_uid bigint,
    html_tag                    varchar(50),
    import_version_nbr          bigint,
    nnd_ind                     char(1),
    ldf_oid                     varchar(50),
    version_ctrl_nbr            smallint,
    nbs_question_uid            bigint,
    CONSTRAINT pk_state_defined_field_metadata PRIMARY KEY (ldf_uid)
)
    GO

CREATE TABLE substance_administration
(
    intervention_uid bigint NOT NULL,
    dose_qty         varchar(10),
    dose_qty_unit_cd varchar(20),
    form_cd          varchar(20),
    form_desc_txt    varchar(100),
    rate_qty         varchar(10),
    rate_qty_unit_cd varchar(20),
    route_cd         varchar(20),
    route_desc_txt   varchar(100),
    CONSTRAINT pk_substance_administration PRIMARY KEY (intervention_uid)
)
    GO

CREATE TABLE substance_administration_hist
(
    dose_qty         varchar(10),
    dose_qty_unit_cd varchar(20),
    form_cd          varchar(20),
    form_desc_txt    varchar(100),
    rate_qty         varchar(10),
    rate_qty_unit_cd varchar(20),
    route_cd         varchar(20),
    route_desc_txt   varchar(100),
    intervention_uid bigint   NOT NULL,
    version_ctrl_nbr smallint NOT NULL,
    CONSTRAINT pk_substance_administration_hist PRIMARY KEY (intervention_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE target_field
(
    target_field_uid      bigint NOT NULL,
    nbs_metadata_rule_uid bigint NOT NULL,
    rule_instance_uid     bigint NOT NULL,
    CONSTRAINT pk_target_field PRIMARY KEY (target_field_uid)
)
    GO

CREATE TABLE target_value
(
    target_value_uid  bigint NOT NULL,
    target_field_uid  bigint NOT NULL,
    target_value      varchar(50),
    error_message_uid bigint NOT NULL,
    operator_type_uid bigint,
    conseq_ind_uid    bigint NOT NULL,
    CONSTRAINT pk_target_value PRIMARY KEY (target_value_uid)
)
    GO

CREATE TABLE tele_locator
(
    tele_locator_uid     bigint NOT NULL,
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    cntry_cd             varchar(20),
    email_address        varchar(100),
    extension_txt        varchar(20),
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    phone_nbr_txt        varchar(20),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    url_address          varchar(100),
    user_affiliation_txt varchar(20),
    CONSTRAINT pk_tele_locator PRIMARY KEY (tele_locator_uid)
)
    GO

CREATE TABLE tele_locator_hist
(
    add_reason_cd        varchar(20),
    add_time             datetime,
    add_user_id          bigint,
    cntry_cd             varchar(20),
    email_address        varchar(100),
    extension_txt        varchar(20),
    last_chg_reason_cd   varchar(20),
    last_chg_time        datetime,
    last_chg_user_id     bigint,
    phone_nbr_txt        varchar(20),
    record_status_cd     varchar(20),
    record_status_time   datetime,
    url_address          varchar(100),
    user_affiliation_txt varchar(20),
    tele_locator_uid     bigint   NOT NULL,
    version_ctrl_nbr     smallint NOT NULL,
    CONSTRAINT pk_tele_locator_hist PRIMARY KEY (tele_locator_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE treatment
(
    treatment_uid             bigint NOT NULL,
    activity_duration_amt     varchar(20),
    activity_duration_unit_cd varchar(20),
    activity_from_time        datetime,
    activity_to_time          datetime,
    add_reason_cd             varchar(20),
    add_time                  datetime,
    add_user_id               bigint,
    cd                        varchar(50),
    cd_desc_txt               varchar(150),
    cd_system_cd              varchar(20),
    cd_system_desc_txt        varchar(100),
    cd_version                varchar(10),
    class_cd                  varchar(10),
    jurisdiction_cd           varchar(20),
    last_chg_reason_cd        varchar(20),
    last_chg_time             datetime,
    last_chg_user_id          bigint,
    local_id                  varchar(50),
    prog_area_cd              varchar(20),
    program_jurisdiction_oid  bigint,
    record_status_cd          varchar(20),
    record_status_time        datetime,
    shared_ind                char(1),
    status_cd                 char(1),
    status_time               datetime,
    txt                       varchar(1000),
    version_ctrl_nbr          smallint,
    CONSTRAINT pk_treatment PRIMARY KEY (treatment_uid)
)
    GO

CREATE TABLE treatment_administered
(
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    cd_system_cd               varchar(20),
    cd_system_desc_txt         varchar(100),
    cd_version                 varchar(10),
    dose_qty                   varchar(20),
    dose_qty_unit_cd           varchar(20),
    effective_duration_amt     varchar(10),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    form_cd                    varchar(20),
    form_desc_txt              varchar(100),
    interval_cd                varchar(20),
    interval_desc_txt          varchar(100),
    rate_qty                   varchar(10),
    rate_qty_unit_cd           varchar(20),
    route_cd                   varchar(20),
    route_desc_txt             varchar(100),
    status_cd                  char(1),
    status_time                datetime,
    treatment_uid              bigint   NOT NULL,
    treatment_administered_seq smallint NOT NULL,
    CONSTRAINT pk_treatment_administered PRIMARY KEY (treatment_uid, treatment_administered_seq)
)
    GO

CREATE TABLE treatment_administered_hist
(
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    cd_system_cd               varchar(20),
    cd_system_desc_txt         varchar(100),
    cd_version                 varchar(10),
    dose_qty                   varchar(20),
    dose_qty_unit_cd           varchar(20),
    effective_duration_amt     varchar(10),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    form_cd                    varchar(20),
    form_desc_txt              varchar(100),
    interval_cd                varchar(20),
    interval_desc_txt          varchar(100),
    rate_qty                   varchar(10),
    rate_unit_cd               varchar(20),
    route_cd                   varchar(20),
    route_desc_txt             varchar(100),
    status_cd                  char(1),
    status_time                datetime,
    treatment_uid              bigint   NOT NULL,
    treatment_administered_seq smallint NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_treatment_administered_hist PRIMARY KEY (treatment_uid, treatment_administered_seq, version_ctrl_nbr)
)
    GO

CREATE TABLE treatment_hist
(
    activity_duration_amt     varchar(20),
    activity_duration_unit_cd varchar(20),
    activity_from_time        datetime,
    activity_to_time          datetime,
    add_reason_cd             varchar(20),
    add_time                  datetime,
    add_user_id               bigint,
    cd                        varchar(50),
    cd_desc_txt               varchar(150),
    cd_system_cd              varchar(20),
    cd_system_desc_txt        varchar(100),
    cd_version                varchar(10),
    class_cd                  varchar(10),
    jurisdiction_cd           varchar(20),
    last_chg_reason_cd        varchar(20),
    last_chg_time             datetime,
    last_chg_user_id          bigint,
    local_id                  varchar(50),
    prog_area_cd              varchar(20),
    program_jurisdiction_oid  bigint,
    record_status_cd          varchar(20),
    record_status_time        datetime,
    shared_ind                char(1),
    status_cd                 char(1),
    status_time               datetime,
    txt                       varchar(1000),
    treatment_uid             bigint   NOT NULL,
    version_ctrl_nbr          smallint NOT NULL,
    CONSTRAINT pk_treatment_hist PRIMARY KEY (treatment_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE treatment_procedure
(
    approach_site_cd           varchar(20),
    approach_site_desc_txt     varchar(100),
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    cd_system_cd               varchar(20),
    cd_system_desc_txt         varchar(100),
    cd_version                 varchar(10),
    effective_duration_amt     varchar(10),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    status_cd                  char(1),
    status_time                datetime,
    treatment_uid              bigint   NOT NULL,
    treatment_procedure_seq    smallint NOT NULL,
    CONSTRAINT pk_treatment_procedure PRIMARY KEY (treatment_uid, treatment_procedure_seq)
)
    GO

CREATE TABLE treatment_procedure_hist
(
    approach_site_cd           varchar(20),
    approach_site_desc_txt     varchar(100),
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    cd_system_cd               varchar(20),
    cd_system_desc_txt         varchar(100),
    cd_version                 varchar(10),
    effective_duration_amt     varchar(10),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    status_cd                  char(1),
    status_time                datetime,
    treatment_uid              bigint   NOT NULL,
    treatment_procedure_seq    smallint NOT NULL,
    version_ctlr_nbr           smallint NOT NULL,
    CONSTRAINT pk_treatment_procedure_hist PRIMARY KEY (treatment_uid, treatment_procedure_seq, version_ctlr_nbr)
)
    GO

CREATE TABLE updated_notification
(
    case_class_cd       varchar(20),
    case_status_chg_ind char(1),
    add_time            datetime,
    add_user_id         bigint,
    last_chg_time       datetime,
    last_chg_user_id    bigint,
    status_cd           char(1),
    status_time         datetime,
    notification_uid    bigint   NOT NULL,
    version_ctrl_nbr    smallint NOT NULL,
    CONSTRAINT pk_updated_notification PRIMARY KEY (notification_uid, version_ctrl_nbr)
)
    GO

CREATE TABLE user_email
(
    user_email_uid   bigint NOT NULL,
    nedss_entry_id   bigint NOT NULL,
    seq_nbr          int    NOT NULL,
    email_id         varchar(100),
    add_time         datetime,
    last_chg_time    datetime,
    record_status_cd varchar(8),
    add_user_id      bigint,
    last_chg_user_id bigint,
    CONSTRAINT pk_user_email PRIMARY KEY (user_email_uid)
)
    GO

CREATE TABLE user_email_alert
(
    user_email_alert_uid    bigint NOT NULL,
    alert_email_message_uid bigint NOT NULL,
    nedss_entry_uid         bigint NOT NULL,
    email_id                varchar(100),
    seq_nbr                 int,
    CONSTRAINT pk_user_email_alert PRIMARY KEY (user_email_alert_uid)
)
    GO

CREATE TABLE user_profile
(
    nedss_entry_id bigint NOT NULL,
    first_nm       varchar(50),
    last_upd_time  datetime,
    last_nm        varchar(50),
    CONSTRAINT pk_user_profile PRIMARY KEY (nedss_entry_id)
)
    GO

CREATE TABLE wa_conversion_mapping
(
    wa_conversion_mapping_uid    bigint NOT NULL,
    nbs_conversion_page_mgmt_uid bigint NOT NULL,
    from_question_id             varchar(30),
    from_answer                  varchar(2000),
    to_question_id               varchar(30),
    to_answer                    varchar(2000),
    to_code_set_group_id         bigint,
    to_data_type                 varchar(30),
    to_nbs_ui_component_uid      bigint,
    block_id_nbr                 int,
    mapping_status               varchar(20),
    question_mapped              varchar(20),
    answer_mapped                varchar(20),
    answer_group_seq_nbr         int,
    conversion_type              varchar(50),
    CONSTRAINT pk_wa_conversion_mapping PRIMARY KEY (wa_conversion_mapping_uid)
)
    GO

CREATE TABLE wa_nnd_metadata
(
    wa_nnd_metadata_uid     bigint      NOT NULL,
    wa_template_uid         bigint      NOT NULL,
    question_identifier_nnd varchar(50),
    question_label_nnd      varchar(150),
    question_required_nnd   char(1)     NOT NULL,
    question_data_type_nnd  varchar(10) NOT NULL,
    hl7_segment_field       varchar(30) NOT NULL,
    order_group_id          varchar(5),
    translation_table_nm    varchar(30),
    add_time                datetime    NOT NULL,
    add_user_id             bigint      NOT NULL,
    last_chg_time           datetime    NOT NULL,
    last_chg_user_id        bigint      NOT NULL,
    record_status_cd        varchar(20) NOT NULL,
    record_status_time      datetime    NOT NULL,
    question_identifier     varchar(50),
    xml_path                varchar(2000),
    xml_tag                 varchar(300),
    xml_data_type           varchar(50),
    part_type_cd            varchar(50),
    repeat_group_seq_nbr    int,
    question_order_nnd      int,
    local_id                varchar(50),
    wa_ui_metadata_uid      bigint      NOT NULL,
    question_map            varchar(2000),
    indicator_cd            varchar(2000),
    CONSTRAINT pk_wa_nnd_metadata PRIMARY KEY (wa_nnd_metadata_uid)
)
    GO

CREATE TABLE wa_nnd_metadata_hist
(
    wa_nnd_metadata_hist_uid bigint      NOT NULL,
    wa_nnd_metadata_uid      bigint      NOT NULL,
    wa_template_uid          bigint      NOT NULL,
    question_identifier_nnd  varchar(50),
    question_label_nnd       varchar(150),
    question_required_nnd    char(1)     NOT NULL,
    question_data_type_nnd   varchar(10) NOT NULL,
    hl7_segment_field        varchar(30) NOT NULL,
    order_group_id           varchar(5),
    translation_table_nm     varchar(30),
    add_time                 datetime    NOT NULL,
    add_user_id              bigint      NOT NULL,
    last_chg_time            datetime    NOT NULL,
    last_chg_user_id         bigint      NOT NULL,
    record_status_cd         varchar(20) NOT NULL,
    record_status_time       datetime    NOT NULL,
    question_identifier      varchar(50),
    xml_path                 varchar(2000),
    xml_tag                  varchar(300),
    xml_data_type            varchar(50),
    part_type_cd             varchar(50),
    repeat_group_seq_nbr     int,
    question_order_nnd       int,
    local_id                 varchar(50),
    wa_template_hist_uid     bigint      NOT NULL,
    wa_ui_metadata_uid       bigint      NOT NULL,
    question_map             varchar(2000),
    indicator_cd             varchar(2000),
    CONSTRAINT pk_wa_nnd_metadata_hist PRIMARY KEY (wa_nnd_metadata_hist_uid)
)
    GO

CREATE TABLE wa_question
(
    wa_question_uid            bigint      NOT NULL,
    code_set_group_id          bigint,
    data_cd                    varchar(50),
    data_location              varchar(150),
    question_identifier        varchar(50) NOT NULL,
    question_oid               varchar(150),
    question_oid_system_txt    varchar(100),
    question_unit_identifier   varchar(20),
    data_type                  varchar(20),
    data_use_cd                varchar(20),
    question_label             varchar(300),
    question_tool_tip          varchar(2000),
    rdb_column_nm              varchar(30),
    part_type_cd               varchar(50),
    default_value              varchar(300),
    version_ctrl_nbr           int         NOT NULL,
    unit_parent_identifier     varchar(20),
    question_group_seq_nbr     int,
    future_date_ind_cd         char(1),
    legacy_data_location       varchar(150),
    repeats_ind_cd             char(1),
    add_user_id                bigint      NOT NULL,
    add_time                   datetime    NOT NULL,
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    question_nm                varchar(50),
    group_nm                   varchar(50),
    sub_group_nm               varchar(50),
    desc_txt                   varchar(2000),
    mask                       varchar(50),
    field_size                 varchar(10),
    rpt_admin_column_nm        varchar(50),
    nnd_msg_ind                char(1),
    question_identifier_nnd    varchar(50),
    question_label_nnd         varchar(150),
    question_required_nnd      char(1),
    question_data_type_nnd     varchar(10),
    hl7_segment_field          varchar(30),
    order_group_id             varchar(5),
    record_status_cd           varchar(20) NOT NULL,
    record_status_time         datetime,
    nbs_ui_component_uid       bigint,
    standard_question_ind_cd   char(1),
    entry_method               varchar(20),
    question_type              varchar(20),
    admin_comment              varchar(2000),
    rdb_table_nm               varchar(30),
    user_defined_column_nm     varchar(30),
    min_value                  bigint,
    max_value                  bigint,
    standard_nnd_ind_cd        char(1),
    legacy_question_identifier varchar(50),
    unit_type_cd               varchar(20),
    unit_value                 varchar(50),
    other_value_ind_cd         char(1),
    source_nm                  varchar(250),
    coinfection_ind_cd         char(1),
    CONSTRAINT pk_wa_question PRIMARY KEY (wa_question_uid)
)
    GO

CREATE TABLE wa_question_hist
(
    wa_question_hist_uid       bigint      NOT NULL,
    wa_question_uid            bigint      NOT NULL,
    code_set_group_id          bigint,
    data_cd                    varchar(50),
    data_location              varchar(150),
    question_identifier        varchar(50) NOT NULL,
    question_oid               varchar(150),
    question_oid_system_txt    varchar(100),
    question_unit_identifier   varchar(20),
    data_type                  varchar(20),
    data_use_cd                varchar(20),
    question_label             varchar(300),
    question_tool_tip          varchar(2000),
    rdb_column_nm              varchar(30),
    part_type_cd               varchar(50),
    default_value              varchar(300),
    version_ctrl_nbr           int         NOT NULL,
    unit_parent_identifier     varchar(20),
    question_group_seq_nbr     int,
    future_date_ind_cd         char(1),
    legacy_data_location       varchar(150),
    repeats_ind_cd             char(1),
    add_user_id                bigint      NOT NULL,
    add_time                   datetime    NOT NULL,
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    question_nm                varchar(50),
    group_nm                   varchar(50),
    sub_group_nm               varchar(50),
    desc_txt                   varchar(2000),
    mask                       varchar(50),
    field_size                 varchar(10),
    rpt_admin_column_nm        varchar(50),
    nnd_msg_ind                char(1),
    question_identifier_nnd    varchar(50),
    question_label_nnd         varchar(150),
    question_required_nnd      char(1),
    question_data_type_nnd     varchar(10),
    hl7_segment_field          varchar(30),
    order_group_id             varchar(5),
    record_status_cd           varchar(20) NOT NULL,
    record_status_time         datetime,
    nbs_ui_component_uid       bigint,
    standard_question_ind_cd   char(1),
    entry_method               varchar(20),
    question_type              varchar(20),
    admin_comment              varchar(2000),
    rdb_table_nm               varchar(30),
    user_defined_column_nm     varchar(30),
    min_value                  bigint,
    max_value                  bigint,
    standard_nnd_ind_cd        char(1),
    legacy_question_identifier varchar(50),
    unit_type_cd               varchar(20),
    unit_value                 varchar(50),
    other_value_ind_cd         char(1),
    source_nm                  varchar(250),
    coinfection_ind_cd         char(1),
    CONSTRAINT pk_wa_question_hist PRIMARY KEY (wa_question_hist_uid)
)
    GO

CREATE TABLE wa_rdb_metadata
(
    wa_rdb_metadata_uid    bigint      NOT NULL,
    wa_template_uid        bigint      NOT NULL,
    user_defined_column_nm varchar(30),
    record_status_cd       varchar(20) NOT NULL,
    record_status_time     datetime    NOT NULL,
    add_user_id            bigint      NOT NULL,
    add_time               datetime    NOT NULL,
    last_chg_time          datetime,
    last_chg_user_id       bigint,
    local_id               varchar(50),
    wa_ui_metadata_uid     bigint,
    rdb_table_nm           varchar(30),
    rpt_admin_column_nm    varchar(50),
    rdb_column_nm          varchar(30),
    question_identifier    varchar(50),
    block_pivot_nbr        int,
    CONSTRAINT pk_wa_rdb_metadata PRIMARY KEY (wa_rdb_metadata_uid)
)
    GO

CREATE TABLE wa_rdb_metadata_hist
(
    wa_rdb_metadata_hist_uid bigint      NOT NULL,
    wa_rdb_metadata_uid      bigint      NOT NULL,
    wa_template_uid          bigint      NOT NULL,
    user_defined_column_nm   varchar(30),
    record_status_cd         varchar(20) NOT NULL,
    record_status_time       datetime    NOT NULL,
    add_user_id              bigint      NOT NULL,
    add_time                 datetime    NOT NULL,
    last_chg_time            datetime,
    last_chg_user_id         bigint,
    local_id                 varchar(50),
    wa_template_hist_uid     bigint      NOT NULL,
    wa_ui_metadata_uid       bigint,
    rdb_table_nm             varchar(30),
    rpt_admin_column_nm      varchar(50),
    rdb_column_nm            varchar(30),
    question_identifier      varchar(50),
    block_pivot_nbr          int,
    CONSTRAINT pk_wa_rdb_metadata_hist PRIMARY KEY (wa_rdb_metadata_hist_uid)
)
    GO

CREATE TABLE wa_rule_metadata
(
    wa_rule_metadata_uid       bigint        NOT NULL,
    wa_template_uid            bigint        NOT NULL,
    rule_cd                    varchar(50)   NOT NULL,
    rule_expression            varchar(4000),
    err_msg_txt                varchar(4000) NOT NULL,
    source_question_identifier varchar(4000),
    target_question_identifier varchar(4000),
    record_status_cd           varchar(20)   NOT NULL,
    record_status_time         datetime      NOT NULL,
    add_time                   datetime      NOT NULL,
    add_user_id                bigint        NOT NULL,
    last_chg_time              datetime      NOT NULL,
    last_chg_user_id           bigint        NOT NULL,
    rule_desc_txt              varchar(4000),
    javascript_function        varchar(MAX
) NOT NULL,
    javascript_function_nm     varchar(100)  NOT NULL,
    user_rule_id               varchar(50),
    logic                      varchar(20),
    source_values              varchar(4000),
    target_type                varchar(50),
    CONSTRAINT pk_wa_rule_metadata PRIMARY KEY (wa_rule_metadata_uid)
) GO

CREATE TABLE wa_rule_metadata_hist
(
    wa_rule_metadata_hist_uid  bigint        NOT NULL,
    wa_rule_metadata_uid       bigint        NOT NULL,
    wa_template_uid            bigint        NOT NULL,
    rule_cd                    varchar(50)   NOT NULL,
    rule_expression            varchar(4000),
    err_msg_txt                varchar(4000) NOT NULL,
    source_question_identifier varchar(4000),
    target_question_identifier varchar(4000),
    record_status_cd           varchar(20)   NOT NULL,
    record_status_time         datetime      NOT NULL,
    add_time                   datetime      NOT NULL,
    add_user_id                bigint        NOT NULL,
    last_chg_time              datetime      NOT NULL,
    last_chg_user_id           bigint        NOT NULL,
    rule_desc_txt              varchar(4000),
    javascript_function        varchar(MAX
) NOT NULL,
    javascript_function_nm     varchar(100)  NOT NULL,
    user_rule_id               varchar(50),
    logic                      varchar(20),
    source_values              varchar(4000),
    target_type                varchar(50),
    CONSTRAINT pk_wa_rule_metadata_hist PRIMARY KEY (wa_rule_metadata_hist_uid)
) GO

CREATE TABLE wa_template
(
    wa_template_uid bigint      NOT NULL,
    template_type   varchar(50) NOT NULL,
    xml_payload     varchar(MAX
) ,
    publish_version_nbr   int,
    form_cd               varchar(50),
    condition_cd          varchar(20),
    bus_obj_type          varchar(50) NOT NULL,
    datamart_nm           varchar(21),
    record_status_cd      varchar(20) NOT NULL,
    record_status_time    datetime    NOT NULL,
    last_chg_time         datetime    NOT NULL,
    last_chg_user_id      bigint      NOT NULL,
    local_id              varchar(50),
    desc_txt              varchar(2000),
    template_nm           varchar(50),
    publish_ind_cd        char(1),
    add_time              datetime    NOT NULL,
    add_user_id           bigint      NOT NULL,
    nnd_entity_identifier varchar(200),
    parent_template_uid   bigint,
    source_nm             varchar(250),
    template_version_nbr  int,
    version_note          varchar(2000),
    CONSTRAINT pk_wa_template PRIMARY KEY (wa_template_uid)
) GO

CREATE TABLE wa_template_hist
(
    wa_template_hist_uid bigint      NOT NULL,
    wa_template_uid      bigint      NOT NULL,
    template_type        varchar(50) NOT NULL,
    xml_payload          varchar(MAX
) ,
    publish_version_nbr   int,
    form_cd               varchar(50),
    condition_cd          varchar(20),
    bus_obj_type          varchar(50) NOT NULL,
    datamart_nm           varchar(21),
    record_status_cd      varchar(20) NOT NULL,
    record_status_time    datetime    NOT NULL,
    last_chg_time         datetime    NOT NULL,
    last_chg_user_id      bigint      NOT NULL,
    local_id              varchar(50),
    desc_txt              varchar(2000),
    template_nm           varchar(50),
    publish_ind_cd        char(1),
    add_time              datetime    NOT NULL,
    add_user_id           bigint      NOT NULL,
    nnd_entity_identifier varchar(200),
    parent_template_uid   bigint,
    source_nm             varchar(250),
    template_version_nbr  int,
    version_note          varchar(2000),
    CONSTRAINT pk_wa_template_hist PRIMARY KEY (wa_template_hist_uid)
) GO

CREATE TABLE wa_ui_metadata
(
    wa_ui_metadata_uid        bigint NOT NULL,
    wa_template_uid           bigint NOT NULL,
    nbs_ui_component_uid      bigint NOT NULL,
    parent_uid                bigint,
    question_label            varchar(300),
    question_tool_tip         varchar(2000),
    enable_ind                varchar(1),
    default_value             varchar(300),
    display_ind               varchar(1),
    order_nbr                 int,
    required_ind              varchar(2),
    add_time                  datetime,
    add_user_id               bigint,
    last_chg_time             datetime,
    last_chg_user_id          bigint,
    record_status_cd          varchar(20),
    record_status_time        datetime,
    max_length                bigint,
    admin_comment             varchar(2000),
    version_ctrl_nbr          int    NOT NULL,
    field_size                varchar(10),
    future_date_ind_cd        char(1),
    local_id                  varchar(50),
    code_set_group_id         bigint,
    data_cd                   varchar(50),
    data_location             varchar(150),
    data_type                 varchar(20),
    data_use_cd               varchar(20),
    legacy_data_location      varchar(150),
    part_type_cd              varchar(50),
    question_group_seq_nbr    int,
    question_identifier       varchar(50),
    question_oid              varchar(150),
    question_oid_system_txt   varchar(100),
    question_unit_identifier  varchar(20),
    repeats_ind_cd            char(1),
    unit_parent_identifier    varchar(20),
    group_nm                  varchar(50),
    sub_group_nm              varchar(50),
    desc_txt                  varchar(2000),
    mask                      varchar(50),
    entry_method              varchar(20),
    question_type             varchar(20),
    publish_ind_cd            char(1),
    min_value                 bigint,
    max_value                 bigint,
    standard_question_ind_cd  char(1),
    standard_nnd_ind_cd       char(1),
    question_nm               varchar(50),
    unit_type_cd              varchar(20),
    unit_value                varchar(50),
    other_value_ind_cd        char(1),
    batch_table_appear_ind_cd char(1),
    batch_table_header        varchar(50),
    batch_table_column_width  int,
    coinfection_ind_cd        char(1),
    block_nm                  varchar(30),
    CONSTRAINT pk_wa_ui_metadata PRIMARY KEY (wa_ui_metadata_uid)
)
    GO

CREATE TABLE wa_ui_metadata_hist
(
    wa_ui_metadata_hist_uid   bigint NOT NULL,
    wa_ui_metadata_uid        bigint NOT NULL,
    wa_template_uid           bigint NOT NULL,
    nbs_ui_component_uid      bigint NOT NULL,
    parent_uid                bigint,
    question_label            varchar(300),
    question_tool_tip         varchar(2000),
    enable_ind                varchar(1),
    default_value             varchar(300),
    display_ind               varchar(1),
    order_nbr                 int,
    required_ind              varchar(2),
    add_time                  datetime,
    add_user_id               bigint,
    last_chg_time             datetime,
    last_chg_user_id          bigint,
    record_status_cd          varchar(20),
    record_status_time        datetime,
    max_length                bigint,
    admin_comment             varchar(2000),
    field_size                varchar(10),
    future_date_ind_cd        char(1),
    local_id                  varchar(50),
    wa_template_hist_uid      bigint NOT NULL,
    code_set_group_id         bigint,
    data_cd                   varchar(50),
    data_location             varchar(150),
    data_type                 varchar(20),
    data_use_cd               varchar(20),
    legacy_data_location      varchar(150),
    part_type_cd              varchar(50),
    question_group_seq_nbr    int,
    question_identifier       varchar(50),
    question_oid              varchar(150),
    question_oid_system_txt   varchar(100),
    question_unit_identifier  varchar(20),
    repeats_ind_cd            char(1),
    unit_parent_identifier    varchar(20),
    group_nm                  varchar(50),
    sub_group_nm              varchar(50),
    desc_txt                  varchar(2000),
    mask                      varchar(50),
    entry_method              varchar(20),
    question_type             varchar(20),
    publish_ind_cd            char(1),
    min_value                 bigint,
    max_value                 bigint,
    standard_question_ind_cd  char(1),
    standard_nnd_ind_cd       char(1),
    question_nm               varchar(50),
    unit_type_cd              varchar(20),
    unit_value                varchar(50),
    other_value_ind_cd        char(1),
    batch_table_appear_ind_cd char(1),
    batch_table_header        varchar(50),
    batch_table_column_width  int,
    coinfection_ind_cd        char(1),
    block_nm                  varchar(30),
    CONSTRAINT pk_wa_ui_metadata_hist PRIMARY KEY (wa_ui_metadata_hist_uid)
)
    GO

CREATE TABLE workup
(
    workup_uid                 bigint   NOT NULL,
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    assign_time                datetime,
    assign_worker_id           bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    diagnosis_cd               varchar(20),
    diagnosis_desc_txt         varchar(100),
    disposition_cd             varchar(20),
    disposition_desc_txt       varchar(100),
    disposition_time           datetime,
    disposition_worker_id      bigint,
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    exposure_frequency         varchar(20),
    exposure_from_time         datetime,
    exposure_to_time           datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    repeat_nbr                 smallint,
    status_cd                  char(1)  NOT NULL,
    status_time                datetime NOT NULL,
    txt                        varchar(1000),
    user_affiliation_txt       varchar(20),
    program_jurisdiction_oid   bigint,
    shared_ind                 char(1)  NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_workup PRIMARY KEY (workup_uid)
)
    GO

CREATE TABLE workup_hist
(
    activity_duration_amt      varchar(20),
    activity_duration_unit_cd  varchar(20),
    activity_from_time         datetime,
    activity_to_time           datetime,
    add_reason_cd              varchar(20),
    add_time                   datetime,
    add_user_id                bigint,
    assign_time                datetime,
    assign_user_id             bigint,
    cd                         varchar(50),
    cd_desc_txt                varchar(100),
    confidentiality_cd         varchar(20),
    confidentiality_desc_txt   varchar(100),
    diagnosis_cd               varchar(20),
    diagnosis_desc_txt         varchar(100),
    disposition_cd             varchar(20),
    disposition_desc_txt       varchar(100),
    disposition_time           datetime,
    disposition_worker_id      bigint,
    effective_duration_amt     varchar(20),
    effective_duration_unit_cd varchar(20),
    effective_from_time        datetime,
    effective_to_time          datetime,
    exposure_frequency         varchar(20),
    exposure_from_time         datetime,
    exposure_to_time           datetime,
    last_chg_reason_cd         varchar(20),
    last_chg_time              datetime,
    last_chg_user_id           bigint,
    local_id                   varchar(50),
    record_status_cd           varchar(20),
    record_status_time         datetime,
    repeat_nbr                 smallint,
    status_cd                  char(1)  NOT NULL,
    status_time                datetime NOT NULL,
    txt                        varchar(1000),
    user_affiliation_txt       varchar(20),
    program_jurisdiction_oid   bigint,
    shared_ind                 char(1)  NOT NULL,
    workup_uid                 bigint   NOT NULL,
    version_ctrl_nbr           smallint NOT NULL,
    CONSTRAINT pk_workup_hist PRIMARY KEY (workup_uid, version_ctrl_nbr)
)
    GO

ALTER TABLE act_id_hist
    ADD CONSTRAINT FK_ACT_ID_HIST_ON_ACUIACIDSE FOREIGN KEY (act_uid, act_id_seq) REFERENCES act_id (act_uid, act_id_seq)
    GO

ALTER TABLE act_id
    ADD CONSTRAINT FK_ACT_ID_ON_ACT_UID FOREIGN KEY (act_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE act_locator_participation_hist
    ADD CONSTRAINT FK_ACT_LOCATOR_PARTICIPATION_HIST_ON_ENUILOUIACUI FOREIGN KEY (entity_uid, locator_uid, act_uid) REFERENCES act_locator_participation (entity_uid, locator_uid, act_uid)
    GO

ALTER TABLE act_locator_participation
    ADD CONSTRAINT FK_ACT_LOCATOR_PARTICIPATION_ON_ACT_UID FOREIGN KEY (act_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE act_relationship
    ADD CONSTRAINT FK_ACT_RELATIONSHIP_ON_SOURCE_ACT_UID FOREIGN KEY (source_act_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE act_relationship
    ADD CONSTRAINT FK_ACT_RELATIONSHIP_ON_TARGET_ACT_UID FOREIGN KEY (target_act_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE alert_email_message
    ADD CONSTRAINT FK_ALERT_EMAIL_MESSAGE_ON_ALERT_UID FOREIGN KEY (alert_uid) REFERENCES alert (alert_uid)
    GO

ALTER TABLE alert_log_detail
    ADD CONSTRAINT FK_ALERT_LOG_DETAIL_ON_ALERT_LOG_UID FOREIGN KEY (alert_log_uid) REFERENCES alert_log (alert_log_uid)
    GO

ALTER TABLE alert_log
    ADD CONSTRAINT FK_ALERT_LOG_ON_ALERT_UID FOREIGN KEY (alert_uid) REFERENCES alert (alert_uid)
    GO

ALTER TABLE alert_user
    ADD CONSTRAINT FK_ALERT_USER_ON_ALERT_UID FOREIGN KEY (alert_uid) REFERENCES alert (alert_uid)
    GO

ALTER TABLE auth_bus_obj_rt
    ADD CONSTRAINT FK_AUTH_BUS_OBJ_RT_ON_AUTH_BUS_OBJ_TYPE_UID FOREIGN KEY (auth_bus_obj_type_uid) REFERENCES auth_bus_obj_type (auth_bus_obj_type_uid)
    GO

ALTER TABLE auth_bus_obj_rt
    ADD CONSTRAINT FK_AUTH_BUS_OBJ_RT_ON_AUTH_PERM_SET_UID FOREIGN KEY (auth_perm_set_uid) REFERENCES auth_perm_set (auth_perm_set_uid)
    GO

ALTER TABLE auth_bus_op_rt
    ADD CONSTRAINT FK_AUTH_BUS_OP_RT_ON_AUTH_BUS_OBJ_RT_UID FOREIGN KEY (auth_bus_obj_rt_uid) REFERENCES auth_bus_obj_rt (auth_bus_obj_rt_uid)
    GO

ALTER TABLE auth_bus_op_rt
    ADD CONSTRAINT FK_AUTH_BUS_OP_RT_ON_AUTH_BUS_OP_TYPE_UID FOREIGN KEY (auth_bus_op_type_uid) REFERENCES auth_bus_op_type (auth_bus_op_type_uid)
    GO

ALTER TABLE auth_prog_area_admin
    ADD CONSTRAINT FK_AUTH_PROG_AREA_ADMIN_ON_AUTH_USER_UID FOREIGN KEY (auth_user_uid) REFERENCES auth_user (auth_user_uid)
    GO

ALTER TABLE auth_user
    ADD CONSTRAINT FK_AUTH_USER_ON_EXTERNAL_ORG_UID FOREIGN KEY (external_org_uid) REFERENCES organization (organization_uid)
    GO

ALTER TABLE auth_user
    ADD CONSTRAINT FK_AUTH_USER_ON_PROVIDER_UID FOREIGN KEY (provider_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE auth_user_role
    ADD CONSTRAINT FK_AUTH_USER_ROLE_ON_AUTH_PERM_SET_UID FOREIGN KEY (auth_perm_set_uid) REFERENCES auth_perm_set (auth_perm_set_uid)
    GO

ALTER TABLE auth_user_role
    ADD CONSTRAINT FK_AUTH_USER_ROLE_ON_AUTH_USER_UID FOREIGN KEY (auth_user_uid) REFERENCES auth_user (auth_user_uid)
    GO

ALTER TABLE bus_obj_df_sf_mdata_group
    ADD CONSTRAINT FK_BUS_OBJ_DF_SF_MDATA_GROUP_ON_DF_SF_METADATA_GROUP_UID FOREIGN KEY (df_sf_metadata_group_uid) REFERENCES df_sf_metadata_group (df_sf_metadata_group_uid)
    GO

ALTER TABLE case_management
    ADD CONSTRAINT FK_CASE_MANAGEMENT_ON_PUBLIC_HEALTH_CASE_UID FOREIGN KEY (public_health_case_uid) REFERENCES public_health_case (public_health_case_uid)
    GO

ALTER TABLE cdf_subform_import_data_log
    ADD CONSTRAINT FK_CDF_SUBFORM_IMPORT_DATA_LOG_ON_IMPORT_LOG_UID FOREIGN KEY (import_log_uid) REFERENCES cdf_subform_import_log (import_log_uid)
    GO

ALTER TABLE chart_report_metadata
    ADD CONSTRAINT FK_CHART_REPORT_METADATA_ON_CHART_TYPE_UID FOREIGN KEY (chart_type_uid) REFERENCES chart_type (chart_type_uid)
    GO

ALTER TABLE clinical_document_hist
    ADD CONSTRAINT FK_CLINICAL_DOCUMENT_HIST_ON_CLINICAL_DOCUMENT_UID FOREIGN KEY (clinical_document_uid) REFERENCES clinical_document (clinical_document_uid)
    GO

ALTER TABLE clinical_document
    ADD CONSTRAINT FK_CLINICAL_DOCUMENT_ON_CLINICAL_DOCUMENT_UID FOREIGN KEY (clinical_document_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE cn_transportq_out
    ADD CONSTRAINT FK_CN_TRANSPORTQ_OUT_ON_NOTIFICATION_UID FOREIGN KEY (notification_uid) REFERENCES notification (notification_uid)
    GO

ALTER TABLE confirmation_method
    ADD CONSTRAINT FK_CONFIRMATION_METHOD_ON_PUBLIC_HEALTH_CASE_UID FOREIGN KEY (public_health_case_uid) REFERENCES public_health_case (public_health_case_uid)
    GO

ALTER TABLE ct_contact_answer_hist
    ADD CONSTRAINT FK_CT_CONTACT_ANSWER_HIST_ON_CT_CONTACT_UID FOREIGN KEY (ct_contact_uid) REFERENCES ct_contact (ct_contact_uid)
    GO

ALTER TABLE ct_contact_answer
    ADD CONSTRAINT FK_CT_CONTACT_ANSWER_ON_CT_CONTACT_UID FOREIGN KEY (ct_contact_uid) REFERENCES ct_contact (ct_contact_uid)
    GO

ALTER TABLE ct_contact_attachment
    ADD CONSTRAINT FK_CT_CONTACT_ATTACHMENT_ON_CT_CONTACT_UID FOREIGN KEY (ct_contact_uid) REFERENCES ct_contact (ct_contact_uid)
    GO

ALTER TABLE ct_contact_hist
    ADD CONSTRAINT FK_CT_CONTACT_HIST_ON_CT_CONTACT_UID FOREIGN KEY (ct_contact_uid) REFERENCES ct_contact (ct_contact_uid)
    GO

ALTER TABLE ct_contact_hist
    ADD CONSTRAINT FK_CT_CONTACT_HIST_ON_THIRD_PARTY_ENTITY_PHC_UID FOREIGN KEY (third_party_entity_phc_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE ct_contact_hist
    ADD CONSTRAINT FK_CT_CONTACT_HIST_ON_THIRD_PARTY_ENTITY_UID FOREIGN KEY (third_party_entity_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE ct_contact_note
    ADD CONSTRAINT FK_CT_CONTACT_NOTE_ON_CT_CONTACT_UID FOREIGN KEY (ct_contact_uid) REFERENCES ct_contact (ct_contact_uid)
    GO

ALTER TABLE ct_contact
    ADD CONSTRAINT FK_CT_CONTACT_ON_CONTACT_ENTITY_PHC_UID FOREIGN KEY (contact_entity_phc_uid) REFERENCES public_health_case (public_health_case_uid)
    GO

ALTER TABLE ct_contact
    ADD CONSTRAINT FK_CT_CONTACT_ON_CONTACT_ENTITY_UID FOREIGN KEY (contact_entity_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE ct_contact
    ADD CONSTRAINT FK_CT_CONTACT_ON_SUBJECT_ENTITY_PHC_UID FOREIGN KEY (subject_entity_phc_uid) REFERENCES public_health_case (public_health_case_uid)
    GO

ALTER TABLE ct_contact
    ADD CONSTRAINT FK_CT_CONTACT_ON_SUBJECT_ENTITY_UID FOREIGN KEY (subject_entity_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE ct_contact
    ADD CONSTRAINT FK_CT_CONTACT_ON_THIRD_PARTY_ENTITY_PHC_UID FOREIGN KEY (third_party_entity_phc_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE ct_contact
    ADD CONSTRAINT FK_CT_CONTACT_ON_THIRD_PARTY_ENTITY_UID FOREIGN KEY (third_party_entity_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE data_migration_detail
    ADD CONSTRAINT FK_DATA_MIGRATION_DETAIL_ON_DAMIREUIDAMIBAUI FOREIGN KEY (data_migration_record_uid, data_migration_batch_uid) REFERENCES data_migration_record (data_migration_record_uid, data_migration_batch_uid)
    GO

ALTER TABLE data_migration_detail
    ADD CONSTRAINT FK_DATA_MIGRATION_DETAIL_ON_DATA_MIGRATION_BATCH_UID FOREIGN KEY (data_migration_batch_uid) REFERENCES data_migration_batch (data_migration_batch_uid)
    GO

ALTER TABLE data_migration_record
    ADD CONSTRAINT FK_DATA_MIGRATION_RECORD_ON_DATA_MIGRATION_BATCH_UID FOREIGN KEY (data_migration_batch_uid) REFERENCES data_migration_batch (data_migration_batch_uid)
    GO

ALTER TABLE data_source_codeset
    ADD CONSTRAINT FK_DATA_SOURCE_CODESET_ON_COLUMN_UID FOREIGN KEY (column_uid) REFERENCES data_source_column (column_uid)
    GO

ALTER TABLE data_source_column
    ADD CONSTRAINT FK_DATA_SOURCE_COLUMN_ON_DATA_SOURCE_UID FOREIGN KEY (data_source_uid) REFERENCES data_source (data_source_uid)
    GO

ALTER TABLE data_source_operator
    ADD CONSTRAINT FK_DATA_SOURCE_OPERATOR_ON_FILTER_OPERATOR_UID FOREIGN KEY (filter_operator_uid) REFERENCES filter_operator (filter_operator_uid)
    GO

ALTER TABLE df_sf_mdata_field_group
    ADD CONSTRAINT FK_DF_SF_MDATA_FIELD_GROUP_ON_DF_SF_METADATA_GROUP_UID FOREIGN KEY (df_sf_metadata_group_uid) REFERENCES df_sf_metadata_group (df_sf_metadata_group_uid)
    GO

ALTER TABLE display_column
    ADD CONSTRAINT FK_DISPLAY_COLUMN_ON_COLUMN_UID FOREIGN KEY (column_uid) REFERENCES data_source_column (column_uid)
    GO

ALTER TABLE display_column
    ADD CONSTRAINT FK_DISPLAY_COLUMN_ON_DASOUIREUI FOREIGN KEY (data_source_uid, report_uid) REFERENCES report (data_source_uid, report_uid)
    GO

ALTER TABLE dsm_algorithm_hist
    ADD CONSTRAINT FK_DSM_ALGORITHM_HIST_ON_DSM_ALGORITHM_UID FOREIGN KEY (dsm_algorithm_uid) REFERENCES dsm_algorithm (dsm_algorithm_uid)
    GO

ALTER TABLE edx_activity_detail_log
    ADD CONSTRAINT FK_EDX_ACTIVITY_DETAIL_LOG_ON_EDX_ACTIVITY_LOG_UID FOREIGN KEY (edx_activity_log_uid) REFERENCES edx_activity_log (edx_activity_log_uid)
    GO

ALTER TABLE edx_document
    ADD CONSTRAINT FK_EDX_DOCUMENT_ON_ACT_UID FOREIGN KEY (act_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE edx_document
    ADD CONSTRAINT FK_EDX_DOCUMENT_ON_NBS_DOCUMENT_METADATA_UID FOREIGN KEY (nbs_document_metadata_uid) REFERENCES nbs_document_metadata (nbs_document_metadata_uid)
    GO

ALTER TABLE edx_entity_match
    ADD CONSTRAINT FK_EDX_ENTITY_MATCH_ON_ENTITY_UID FOREIGN KEY (entity_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE edx_event_process
    ADD CONSTRAINT FK_EDX_EVENT_PROCESS_ON_NBS_DOCUMENT_UID FOREIGN KEY (nbs_document_uid) REFERENCES nbs_document (nbs_document_uid)
    GO

ALTER TABLE edx_event_process
    ADD CONSTRAINT FK_EDX_EVENT_PROCESS_ON_NBS_EVENT_UID FOREIGN KEY (nbs_event_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE edx_patient_match
    ADD CONSTRAINT FK_EDX_PATIENT_MATCH_ON_PATIENT_UID FOREIGN KEY (patient_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE entity_group_hist
    ADD CONSTRAINT FK_ENTITY_GROUP_HIST_ON_ENTITY_GROUP_UID FOREIGN KEY (entity_group_uid) REFERENCES entity_group (entity_group_uid)
    GO

ALTER TABLE entity_group
    ADD CONSTRAINT FK_ENTITY_GROUP_ON_ENTITY_GROUP_UID FOREIGN KEY (entity_group_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE entity_id_hist
    ADD CONSTRAINT FK_ENTITY_ID_HIST_ON_ENUIENIDSE FOREIGN KEY (entity_uid, entity_id_seq) REFERENCES entity_id (entity_uid, entity_id_seq)
    GO

ALTER TABLE entity_id
    ADD CONSTRAINT FK_ENTITY_ID_ON_ENTITY_UID FOREIGN KEY (entity_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE entity_locator_participation
    ADD CONSTRAINT FK_ENTITY_LOCATOR_PARTICIPATION_ON_ENTITY_UID FOREIGN KEY (entity_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE entity_loc_participation_hist
    ADD CONSTRAINT FK_ENTITY_LOC_PARTICIPATION_HIST_ON_ENUILOUI FOREIGN KEY (entity_uid, locator_uid) REFERENCES entity_locator_participation (entity_uid, locator_uid)
    GO

ALTER TABLE filter_value
    ADD CONSTRAINT FK_FILTER_VALUE_ON_REPORT_FILTER_UID FOREIGN KEY (report_filter_uid) REFERENCES report_filter (report_filter_uid)
    GO

ALTER TABLE form_rule_instance
    ADD CONSTRAINT FK_FORM_RULE_INSTANCE_ON_RULE_INSTANCE_UID FOREIGN KEY (rule_instance_uid) REFERENCES rule_instance (rule_instance_uid)
    GO

ALTER TABLE geocoding_result_hist
    ADD CONSTRAINT FK_GEOCODING_RESULT_HIST_ON_GEOCODING_RESULT_UID FOREIGN KEY (geocoding_result_uid) REFERENCES geocoding_result (geocoding_result_uid)
    GO

ALTER TABLE geocoding_result
    ADD CONSTRAINT FK_GEOCODING_RESULT_ON_POSTAL_LOCATOR_UID FOREIGN KEY (postal_locator_uid) REFERENCES postal_locator (postal_locator_uid)
    GO

ALTER TABLE intervention_hist
    ADD CONSTRAINT FK_INTERVENTION_HIST_ON_INTERVENTION_UID FOREIGN KEY (intervention_uid) REFERENCES intervention (intervention_uid)
    GO

ALTER TABLE intervention
    ADD CONSTRAINT FK_INTERVENTION_ON_INTERVENTION_UID FOREIGN KEY (intervention_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE interview_hist
    ADD CONSTRAINT FK_INTERVIEW_HIST_ON_INTERVIEW_UID FOREIGN KEY (interview_uid) REFERENCES interview (interview_uid)
    GO

ALTER TABLE interview
    ADD CONSTRAINT FK_INTERVIEW_ON_INTERVIEW_UID FOREIGN KEY (interview_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE lookup_answer
    ADD CONSTRAINT FK_LOOKUP_ANSWER_ON_LOOKUP_QUESTION_UID FOREIGN KEY (lookup_question_uid) REFERENCES lookup_question (lookup_question_uid)
    GO

ALTER TABLE manufactured_material_hist
    ADD CONSTRAINT FK_MANUFACTURED_MATERIAL_HIST_ON_MAUIMAMASE FOREIGN KEY (material_uid, manufactured_material_seq) REFERENCES manufactured_material (material_uid, manufactured_material_seq)
    GO

ALTER TABLE manufactured_material
    ADD CONSTRAINT FK_MANUFACTURED_MATERIAL_ON_MATERIAL_UID FOREIGN KEY (material_uid) REFERENCES material (material_uid)
    GO

ALTER TABLE material_hist
    ADD CONSTRAINT FK_MATERIAL_HIST_ON_MATERIAL_UID FOREIGN KEY (material_uid) REFERENCES material (material_uid)
    GO

ALTER TABLE material
    ADD CONSTRAINT FK_MATERIAL_ON_MATERIAL_UID FOREIGN KEY (material_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE nbs_act_entity_hist
    ADD CONSTRAINT FK_NBS_ACT_ENTITY_HIST_ON_ACT_UID FOREIGN KEY (act_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE nbs_act_entity_hist
    ADD CONSTRAINT FK_NBS_ACT_ENTITY_HIST_ON_ENTITY_UID FOREIGN KEY (entity_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE nbs_act_entity
    ADD CONSTRAINT FK_NBS_ACT_ENTITY_ON_ACT_UID FOREIGN KEY (act_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE nbs_act_entity
    ADD CONSTRAINT FK_NBS_ACT_ENTITY_ON_ENTITY_UID FOREIGN KEY (entity_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE nbs_aggregate
    ADD CONSTRAINT FK_NBS_AGGREGATE_ON_NBS_QUESTION_UID FOREIGN KEY (nbs_question_uid) REFERENCES nbs_question (nbs_question_uid)
    GO

ALTER TABLE nbs_answer_hist
    ADD CONSTRAINT FK_NBS_ANSWER_HIST_ON_ACT_UID FOREIGN KEY (act_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE nbs_answer
    ADD CONSTRAINT FK_NBS_ANSWER_ON_ACT_UID FOREIGN KEY (act_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE nbs_attachment
    ADD CONSTRAINT FK_NBS_ATTACHMENT_ON_ATTACHMENT_PARENT_UID FOREIGN KEY (attachment_parent_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE nbs_case_answer_hist
    ADD CONSTRAINT FK_NBS_CASE_ANSWER_HIST_ON_ACT_UID FOREIGN KEY (act_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE nbs_case_answer_hist
    ADD CONSTRAINT FK_NBS_CASE_ANSWER_HIST_ON_NBS_QUESTION_UID FOREIGN KEY (nbs_question_uid) REFERENCES nbs_question (nbs_question_uid)
    GO

ALTER TABLE nbs_case_answer
    ADD CONSTRAINT FK_NBS_CASE_ANSWER_ON_ACT_UID FOREIGN KEY (act_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE nbs_case_answer
    ADD CONSTRAINT FK_NBS_CASE_ANSWER_ON_NBS_QUESTION_UID FOREIGN KEY (nbs_question_uid) REFERENCES nbs_question (nbs_question_uid)
    GO

ALTER TABLE nbs_case_answer
    ADD CONSTRAINT FK_NBS_CASE_ANSWER_ON_NBS_TABLE_METADATA_UID FOREIGN KEY (nbs_table_metadata_uid) REFERENCES nbs_table_metadata (nbs_table_metadata_uid)
    GO

ALTER TABLE nbs_conversion_condition
    ADD CONSTRAINT FK_NBS_CONVERSION_CONDITION_ON_NBS_CONVERSION_PAGE_MGMT_UID FOREIGN KEY (nbs_conversion_page_mgmt_uid) REFERENCES nbs_conversion_page_mgmt (nbs_conversion_page_mgmt_uid)
    GO

ALTER TABLE nbs_conversion_error
    ADD CONSTRAINT FK_NBS_CONVERSION_ERROR_ON_NBS_CONVERSION_MASTER_UID FOREIGN KEY (nbs_conversion_master_uid) REFERENCES nbs_conversion_master (nbs_conversion_master_uid)
    GO

ALTER TABLE nbs_conversion_master
    ADD CONSTRAINT FK_NBS_CONVERSION_MASTER_ON_NBS_CONVERSION_CONDITION_UID FOREIGN KEY (nbs_conversion_condition_uid) REFERENCES nbs_conversion_condition (nbs_conversion_condition_uid)
    GO

ALTER TABLE nbs_document_hist
    ADD CONSTRAINT FK_NBS_DOCUMENT_HIST_ON_NBS_DOCUMENT_UID FOREIGN KEY (nbs_document_uid) REFERENCES nbs_document (nbs_document_uid)
    GO

ALTER TABLE nbs_document
    ADD CONSTRAINT FK_NBS_DOCUMENT_ON_NBS_DOCUMENT_METADATA_UID FOREIGN KEY (nbs_document_metadata_uid) REFERENCES nbs_document_metadata (nbs_document_metadata_uid)
    GO

ALTER TABLE nbs_indicator
    ADD CONSTRAINT FK_NBS_INDICATOR_ON_NBS_QUESTION_UID FOREIGN KEY (nbs_question_uid) REFERENCES nbs_question (nbs_question_uid)
    GO

ALTER TABLE nbs_metadata_rule
    ADD CONSTRAINT FK_NBS_METADATA_RULE_ON_COMPONENT_UID FOREIGN KEY (component_uid) REFERENCES nbs_question (nbs_question_uid)
    GO

ALTER TABLE nbs_note
    ADD CONSTRAINT FK_NBS_NOTE_ON_NOTE_PARENT_UID FOREIGN KEY (note_parent_uid) REFERENCES public_health_case (public_health_case_uid)
    GO

ALTER TABLE nbs_question_hist
    ADD CONSTRAINT FK_NBS_QUESTION_HIST_ON_NBS_QUESTION_UID FOREIGN KEY (nbs_question_uid) REFERENCES nbs_question (nbs_question_uid)
    GO

ALTER TABLE nbs_rdb_metadata_hist
    ADD CONSTRAINT FK_NBS_RDB_METADATA_HIST_ON_NBS_PAGE_UID FOREIGN KEY (nbs_page_uid) REFERENCES nbs_page (nbs_page_uid)
    GO

ALTER TABLE nbs_rdb_metadata_hist
    ADD CONSTRAINT FK_NBS_RDB_METADATA_HIST_ON_NBS_UI_METADATA_UID FOREIGN KEY (nbs_ui_metadata_uid) REFERENCES nbs_ui_metadata (nbs_ui_metadata_uid)
    GO

ALTER TABLE nbs_rdb_metadata
    ADD CONSTRAINT FK_NBS_RDB_METADATA_ON_NBS_PAGE_UID FOREIGN KEY (nbs_page_uid) REFERENCES nbs_page (nbs_page_uid)
    GO

ALTER TABLE nbs_rdb_metadata
    ADD CONSTRAINT FK_NBS_RDB_METADATA_ON_NBS_UI_METADATA_UID FOREIGN KEY (nbs_ui_metadata_uid) REFERENCES nbs_ui_metadata (nbs_ui_metadata_uid)
    GO

ALTER TABLE nbs_table_metadata
    ADD CONSTRAINT FK_NBS_TABLE_METADATA_ON_NBS_AGGREGATE_UID FOREIGN KEY (nbs_aggregate_uid) REFERENCES nbs_aggregate (nbs_aggregate_uid)
    GO

ALTER TABLE nbs_table_metadata
    ADD CONSTRAINT FK_NBS_TABLE_METADATA_ON_NBS_INDICATOR_UID FOREIGN KEY (nbs_indicator_uid) REFERENCES nbs_indicator (nbs_indicator_uid)
    GO

ALTER TABLE nbs_table_metadata
    ADD CONSTRAINT FK_NBS_TABLE_METADATA_ON_NBS_QUESTION_UID FOREIGN KEY (nbs_question_uid) REFERENCES nbs_question (nbs_question_uid)
    GO

ALTER TABLE nbs_table_metadata
    ADD CONSTRAINT FK_NBS_TABLE_METADATA_ON_NBS_TABLE_UID FOREIGN KEY (nbs_table_uid) REFERENCES nbs_table (nbs_table_uid)
    GO

ALTER TABLE nbs_ui_metadata
    ADD CONSTRAINT FK_NBS_UI_METADATA_ON_NBS_QUESTION_UID FOREIGN KEY (nbs_question_uid) REFERENCES nbs_question (nbs_question_uid)
    GO

ALTER TABLE nbs_ui_metadata
    ADD CONSTRAINT FK_NBS_UI_METADATA_ON_NBS_TABLE_UID FOREIGN KEY (nbs_table_uid) REFERENCES nbs_table (nbs_table_uid)
    GO

ALTER TABLE nbs_ui_metadata
    ADD CONSTRAINT FK_NBS_UI_METADATA_ON_NBS_UI_COMPONENT_UID FOREIGN KEY (nbs_ui_component_uid) REFERENCES nbs_ui_component (nbs_ui_component_uid)
    GO

ALTER TABLE non_person_living_subject_hist
    ADD CONSTRAINT FK_NON_PERSON_LIVING_SUBJECT_HIST_ON_NON_PERSON_UID FOREIGN KEY (non_person_uid) REFERENCES non_person_living_subject (non_person_uid)
    GO

ALTER TABLE non_person_living_subject
    ADD CONSTRAINT FK_NON_PERSON_LIVING_SUBJECT_ON_NON_PERSON_UID FOREIGN KEY (non_person_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE notification_hist
    ADD CONSTRAINT FK_NOTIFICATION_HIST_ON_NOTIFICATION_UID FOREIGN KEY (notification_uid) REFERENCES notification (notification_uid)
    GO

ALTER TABLE notification
    ADD CONSTRAINT FK_NOTIFICATION_ON_NOTIFICATION_UID FOREIGN KEY (notification_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE observation_hist
    ADD CONSTRAINT FK_OBSERVATION_HIST_ON_OBSERVATION_UID FOREIGN KEY (observation_uid) REFERENCES observation (observation_uid)
    GO

ALTER TABLE observation_interp
    ADD CONSTRAINT FK_OBSERVATION_INTERP_ON_OBSERVATION_UID FOREIGN KEY (observation_uid) REFERENCES observation (observation_uid)
    GO

ALTER TABLE observation
    ADD CONSTRAINT FK_OBSERVATION_ON_OBSERVATION_UID FOREIGN KEY (observation_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE observation
    ADD CONSTRAINT FK_OBSERVATION_ON_SUBJECT_PERSON_UID FOREIGN KEY (subject_person_uid) REFERENCES person (person_uid)
    GO

ALTER TABLE observation_reason
    ADD CONSTRAINT FK_OBSERVATION_REASON_ON_OBSERVATION_UID FOREIGN KEY (observation_uid) REFERENCES observation (observation_uid)
    GO

ALTER TABLE obs_value_coded
    ADD CONSTRAINT FK_OBS_VALUE_CODED_ON_OBSERVATION_UID FOREIGN KEY (observation_uid) REFERENCES observation (observation_uid)
    GO

ALTER TABLE obs_value_date_hist
    ADD CONSTRAINT FK_OBS_VALUE_DATE_HIST_ON_OBUIOBVADASE FOREIGN KEY (observation_uid, obs_value_date_seq) REFERENCES obs_value_date (observation_uid, obs_value_date_seq)
    GO

ALTER TABLE obs_value_date
    ADD CONSTRAINT FK_OBS_VALUE_DATE_ON_OBSERVATION_UID FOREIGN KEY (observation_uid) REFERENCES observation (observation_uid)
    GO

ALTER TABLE obs_value_numeric_hist
    ADD CONSTRAINT FK_OBS_VALUE_NUMERIC_HIST_ON_OBUIOBVANUSE FOREIGN KEY (observation_uid, obs_value_numeric_seq) REFERENCES obs_value_numeric (observation_uid, obs_value_numeric_seq)
    GO

ALTER TABLE obs_value_numeric
    ADD CONSTRAINT FK_OBS_VALUE_NUMERIC_ON_OBSERVATION_UID FOREIGN KEY (observation_uid) REFERENCES observation (observation_uid)
    GO

ALTER TABLE obs_value_txt_hist
    ADD CONSTRAINT FK_OBS_VALUE_TXT_HIST_ON_OBUIOBVATXSE FOREIGN KEY (observation_uid, obs_value_txt_seq) REFERENCES obs_value_txt (observation_uid, obs_value_txt_seq)
    GO

ALTER TABLE obs_value_txt
    ADD CONSTRAINT FK_OBS_VALUE_TXT_ON_OBSERVATION_UID FOREIGN KEY (observation_uid) REFERENCES observation (observation_uid)
    GO

ALTER TABLE organization_hist
    ADD CONSTRAINT FK_ORGANIZATION_HIST_ON_ORGANIZATION_UID FOREIGN KEY (organization_uid) REFERENCES organization (organization_uid)
    GO

ALTER TABLE organization_name_hist
    ADD CONSTRAINT FK_ORGANIZATION_NAME_HIST_ON_ORUIORNASE FOREIGN KEY (organization_uid, organization_name_seq) REFERENCES organization_name (organization_uid, organization_name_seq)
    GO

ALTER TABLE organization_name
    ADD CONSTRAINT FK_ORGANIZATION_NAME_ON_ORGANIZATION_UID FOREIGN KEY (organization_uid) REFERENCES organization (organization_uid)
    GO

ALTER TABLE organization
    ADD CONSTRAINT FK_ORGANIZATION_ON_ORGANIZATION_UID FOREIGN KEY (organization_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE page_cond_mapping
    ADD CONSTRAINT FK_PAGE_COND_MAPPING_ON_WA_TEMPLATE_UID FOREIGN KEY (wa_template_uid) REFERENCES wa_template (wa_template_uid)
    GO

ALTER TABLE participation
    ADD CONSTRAINT FK_PARTICIPATION_ON_ACT_UID FOREIGN KEY (act_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE participation
    ADD CONSTRAINT FK_PARTICIPATION_ON_ROSUENUIROROSEROCD FOREIGN KEY (role_subject_entity_uid, role_role_seq, role_cd) REFERENCES role (subject_entity_uid, role_seq, cd)
    GO

ALTER TABLE patient_encounter_hist
    ADD CONSTRAINT FK_PATIENT_ENCOUNTER_HIST_ON_PATIENT_ENCOUNTER_UID FOREIGN KEY (patient_encounter_uid) REFERENCES patient_encounter (patient_encounter_uid)
    GO

ALTER TABLE patient_encounter
    ADD CONSTRAINT FK_PATIENT_ENCOUNTER_ON_PATIENT_ENCOUNTER_UID FOREIGN KEY (patient_encounter_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE person_ethnic_group
    ADD CONSTRAINT FK_PERSON_ETHNIC_GROUP_ON_PERSON_UID FOREIGN KEY (person_uid) REFERENCES person (person_uid)
    GO

ALTER TABLE person_hist
    ADD CONSTRAINT FK_PERSON_HIST_ON_PERSON_UID FOREIGN KEY (person_uid) REFERENCES person (person_uid)
    GO

ALTER TABLE person_merge
    ADD CONSTRAINT FK_PERSON_MERGE_ON_SURVIVING_PERSON_UID FOREIGN KEY (surviving_person_uid) REFERENCES person (person_uid)
    GO

ALTER TABLE person_name_hist
    ADD CONSTRAINT FK_PERSON_NAME_HIST_ON_PEUIPENASE FOREIGN KEY (person_uid, person_name_seq) REFERENCES person_name (person_uid, person_name_seq)
    GO

ALTER TABLE person_name
    ADD CONSTRAINT FK_PERSON_NAME_ON_PERSON_UID FOREIGN KEY (person_uid) REFERENCES person (person_uid)
    GO

ALTER TABLE person
    ADD CONSTRAINT FK_PERSON_ON_PERSON_PARENT_UID FOREIGN KEY (person_parent_uid) REFERENCES person (person_uid)
    GO

ALTER TABLE person
    ADD CONSTRAINT FK_PERSON_ON_PERSON_UID FOREIGN KEY (person_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE person_race
    ADD CONSTRAINT FK_PERSON_RACE_ON_PERSON_UID FOREIGN KEY (person_uid) REFERENCES person (person_uid)
    GO

ALTER TABLE physical_locator_hist
    ADD CONSTRAINT FK_PHYSICAL_LOCATOR_HIST_ON_PHYSICAL_LOCATOR_UID FOREIGN KEY (physical_locator_uid) REFERENCES physical_locator (physical_locator_uid)
    GO

ALTER TABLE place_hist
    ADD CONSTRAINT FK_PLACE_HIST_ON_PLACE_UID FOREIGN KEY (place_uid) REFERENCES place (place_uid)
    GO

ALTER TABLE place
    ADD CONSTRAINT FK_PLACE_ON_PLACE_UID FOREIGN KEY (place_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE postal_locator_hist
    ADD CONSTRAINT FK_POSTAL_LOCATOR_HIST_ON_POSTAL_LOCATOR_UID FOREIGN KEY (postal_locator_uid) REFERENCES postal_locator (postal_locator_uid)
    GO

ALTER TABLE procedure1_hist
    ADD CONSTRAINT FK_PROCEDURE1_HIST_ON_INTERVENTION_UID FOREIGN KEY (intervention_uid) REFERENCES procedure1 (intervention_uid)
    GO

ALTER TABLE procedure1
    ADD CONSTRAINT FK_PROCEDURE1_ON_INTERVENTION_UID FOREIGN KEY (intervention_uid) REFERENCES intervention (intervention_uid)
    GO

ALTER TABLE public_health_case_hist
    ADD CONSTRAINT FK_PUBLIC_HEALTH_CASE_HIST_ON_PUBLIC_HEALTH_CASE_UID FOREIGN KEY (public_health_case_uid) REFERENCES public_health_case (public_health_case_uid)
    GO

ALTER TABLE public_health_case
    ADD CONSTRAINT FK_PUBLIC_HEALTH_CASE_ON_PUBLIC_HEALTH_CASE_UID FOREIGN KEY (public_health_case_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE referral_hist
    ADD CONSTRAINT FK_REFERRAL_HIST_ON_REFERRAL_UID FOREIGN KEY (referral_uid) REFERENCES referral (referral_uid)
    GO

ALTER TABLE referral
    ADD CONSTRAINT FK_REFERRAL_ON_REFERRAL_UID FOREIGN KEY (referral_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE report_filter
    ADD CONSTRAINT FK_REPORT_FILTER_ON_COLUMN_UID FOREIGN KEY (column_uid) REFERENCES data_source_column (column_uid)
    GO

ALTER TABLE report_filter
    ADD CONSTRAINT FK_REPORT_FILTER_ON_DASOUIREUI FOREIGN KEY (data_source_uid, report_uid) REFERENCES report (data_source_uid, report_uid)
    GO

ALTER TABLE report_filter
    ADD CONSTRAINT FK_REPORT_FILTER_ON_FILTER_UID FOREIGN KEY (filter_uid) REFERENCES filter_code (filter_uid)
    GO

ALTER TABLE report_filter_validation
    ADD CONSTRAINT FK_REPORT_FILTER_VALIDATION_ON_REPORT_FILTER_UID FOREIGN KEY (report_filter_uid) REFERENCES report_filter (report_filter_uid)
    GO

ALTER TABLE report
    ADD CONSTRAINT FK_REPORT_ON_DATA_SOURCE_UID FOREIGN KEY (data_source_uid) REFERENCES data_source (data_source_uid)
    GO

ALTER TABLE report_sort_column
    ADD CONSTRAINT FK_REPORT_SORT_COLUMN_ON_DASOUIREUI FOREIGN KEY (data_source_uid, report_uid) REFERENCES report (data_source_uid, report_uid)
    GO

ALTER TABLE role
    ADD CONSTRAINT FK_ROLE_ON_SUBJECT_ENTITY_UID FOREIGN KEY (subject_entity_uid) REFERENCES entity (entity_uid)
    GO

ALTER TABLE rule_instance
    ADD CONSTRAINT FK_RULE_INSTANCE_ON_CONSEQ_IND_UID FOREIGN KEY (conseq_ind_uid) REFERENCES consequence_indicator (conseq_ind_uid)
    GO

ALTER TABLE rule_instance
    ADD CONSTRAINT FK_RULE_INSTANCE_ON_RULE_UID FOREIGN KEY (rule_uid) REFERENCES [rule] (rule_uid)
    GO

ALTER TABLE [rule]
    ADD CONSTRAINT FK_RULE_ON_RULE_TYPE_UID FOREIGN KEY (rule_type_uid) REFERENCES rule_type (rule_type_uid)
    GO

ALTER TABLE source_field
    ADD CONSTRAINT FK_SOURCE_FIELD_ON_NBS_METADATA_RULE_UID FOREIGN KEY (nbs_metadata_rule_uid) REFERENCES nbs_metadata_rule (nbs_metadata_rule_uid)
    GO

ALTER TABLE source_field
    ADD CONSTRAINT FK_SOURCE_FIELD_ON_RULE_INSTANCE_UID FOREIGN KEY (rule_instance_uid) REFERENCES rule_instance (rule_instance_uid)
    GO

ALTER TABLE source_value
    ADD CONSTRAINT FK_SOURCE_VALUE_ON_OPERATOR_TYPE_UID FOREIGN KEY (operator_type_uid) REFERENCES operator_type (operator_type_uid)
    GO

ALTER TABLE source_value
    ADD CONSTRAINT FK_SOURCE_VALUE_ON_SOURCE_FIELD_UID FOREIGN KEY (source_field_uid) REFERENCES source_field (source_field_uid)
    GO

ALTER TABLE state_defined_field_data
    ADD CONSTRAINT FK_STATE_DEFINED_FIELD_DATA_ON_LDF_UID FOREIGN KEY (ldf_uid) REFERENCES state_defined_field_metadata (ldf_uid)
    GO

ALTER TABLE state_defined_field_metadata
    ADD CONSTRAINT FK_STATE_DEFINED_FIELD_METADATA_ON_CUSTOM_SUBFORM_METADATA_UID FOREIGN KEY (custom_subform_metadata_uid) REFERENCES custom_subform_metadata (custom_subform_metadata_uid)
    GO

ALTER TABLE substance_administration_hist
    ADD CONSTRAINT FK_SUBSTANCE_ADMINISTRATION_HIST_ON_INTERVENTION_UID FOREIGN KEY (intervention_uid) REFERENCES substance_administration (intervention_uid)
    GO

ALTER TABLE substance_administration
    ADD CONSTRAINT FK_SUBSTANCE_ADMINISTRATION_ON_INTERVENTION_UID FOREIGN KEY (intervention_uid) REFERENCES intervention (intervention_uid)
    GO

ALTER TABLE target_field
    ADD CONSTRAINT FK_TARGET_FIELD_ON_NBS_METADATA_RULE_UID FOREIGN KEY (nbs_metadata_rule_uid) REFERENCES nbs_metadata_rule (nbs_metadata_rule_uid)
    GO

ALTER TABLE target_field
    ADD CONSTRAINT FK_TARGET_FIELD_ON_RULE_INSTANCE_UID FOREIGN KEY (rule_instance_uid) REFERENCES rule_instance (rule_instance_uid)
    GO

ALTER TABLE target_value
    ADD CONSTRAINT FK_TARGET_VALUE_ON_CONSEQ_IND_UID FOREIGN KEY (conseq_ind_uid) REFERENCES consequence_indicator (conseq_ind_uid)
    GO

ALTER TABLE target_value
    ADD CONSTRAINT FK_TARGET_VALUE_ON_ERROR_MESSAGE_UID FOREIGN KEY (error_message_uid) REFERENCES error_message (error_message_uid)
    GO

ALTER TABLE target_value
    ADD CONSTRAINT FK_TARGET_VALUE_ON_OPERATOR_TYPE_UID FOREIGN KEY (operator_type_uid) REFERENCES operator_type (operator_type_uid)
    GO

ALTER TABLE target_value
    ADD CONSTRAINT FK_TARGET_VALUE_ON_TARGET_FIELD_UID FOREIGN KEY (target_field_uid) REFERENCES target_field (target_field_uid)
    GO

ALTER TABLE tele_locator_hist
    ADD CONSTRAINT FK_TELE_LOCATOR_HIST_ON_TELE_LOCATOR_UID FOREIGN KEY (tele_locator_uid) REFERENCES tele_locator (tele_locator_uid)
    GO

ALTER TABLE treatment_administered_hist
    ADD CONSTRAINT FK_TREATMENT_ADMINISTERED_HIST_ON_TRUITRADSE FOREIGN KEY (treatment_uid, treatment_administered_seq) REFERENCES treatment_administered (treatment_uid, treatment_administered_seq)
    GO

ALTER TABLE treatment_administered
    ADD CONSTRAINT FK_TREATMENT_ADMINISTERED_ON_TREATMENT_UID FOREIGN KEY (treatment_uid) REFERENCES treatment (treatment_uid)
    GO

ALTER TABLE treatment_hist
    ADD CONSTRAINT FK_TREATMENT_HIST_ON_TREATMENT_UID FOREIGN KEY (treatment_uid) REFERENCES treatment (treatment_uid)
    GO

ALTER TABLE treatment
    ADD CONSTRAINT FK_TREATMENT_ON_TREATMENT_UID FOREIGN KEY (treatment_uid) REFERENCES act (act_uid)
    GO

ALTER TABLE treatment_procedure_hist
    ADD CONSTRAINT FK_TREATMENT_PROCEDURE_HIST_ON_TRUITRPRSE FOREIGN KEY (treatment_uid, treatment_procedure_seq) REFERENCES treatment_procedure (treatment_uid, treatment_procedure_seq)
    GO

ALTER TABLE treatment_procedure
    ADD CONSTRAINT FK_TREATMENT_PROCEDURE_ON_TREATMENT_UID FOREIGN KEY (treatment_uid) REFERENCES treatment (treatment_uid)
    GO

ALTER TABLE updated_notification
    ADD CONSTRAINT FK_UPDATED_NOTIFICATION_ON_NOTIFICATION_UID FOREIGN KEY (notification_uid) REFERENCES notification (notification_uid)
    GO

ALTER TABLE user_email_alert
    ADD CONSTRAINT FK_USER_EMAIL_ALERT_ON_ALERT_EMAIL_MESSAGE_UID FOREIGN KEY (alert_email_message_uid) REFERENCES alert_email_message (alert_email_message_uid)
    GO

ALTER TABLE wa_conversion_mapping
    ADD CONSTRAINT FK_WA_CONVERSION_MAPPING_ON_NBS_CONVERSION_PAGE_MGMT_UID FOREIGN KEY (nbs_conversion_page_mgmt_uid) REFERENCES nbs_conversion_page_mgmt (nbs_conversion_page_mgmt_uid)
    GO

ALTER TABLE wa_nnd_metadata_hist
    ADD CONSTRAINT FK_WA_NND_METADATA_HIST_ON_WA_TEMPLATE_HIST_UID FOREIGN KEY (wa_template_hist_uid) REFERENCES wa_template_hist (wa_template_hist_uid)
    GO

ALTER TABLE wa_nnd_metadata
    ADD CONSTRAINT FK_WA_NND_METADATA_ON_WA_TEMPLATE_UID FOREIGN KEY (wa_template_uid) REFERENCES wa_template (wa_template_uid)
    GO

ALTER TABLE wa_nnd_metadata
    ADD CONSTRAINT FK_WA_NND_METADATA_ON_WA_UI_METADATA_UID FOREIGN KEY (wa_ui_metadata_uid) REFERENCES wa_ui_metadata (wa_ui_metadata_uid)
    GO

ALTER TABLE wa_question_hist
    ADD CONSTRAINT FK_WA_QUESTION_HIST_ON_WA_QUESTION_UID FOREIGN KEY (wa_question_uid) REFERENCES wa_question (wa_question_uid)
    GO

ALTER TABLE wa_rdb_metadata_hist
    ADD CONSTRAINT FK_WA_RDB_METADATA_HIST_ON_WA_TEMPLATE_HIST_UID FOREIGN KEY (wa_template_hist_uid) REFERENCES wa_template_hist (wa_template_hist_uid)
    GO

ALTER TABLE wa_rdb_metadata
    ADD CONSTRAINT FK_WA_RDB_METADATA_ON_WA_TEMPLATE_UID FOREIGN KEY (wa_template_uid) REFERENCES wa_template (wa_template_uid)
    GO

ALTER TABLE wa_rule_metadata
    ADD CONSTRAINT FK_WA_RULE_METADATA_ON_WA_TEMPLATE_UID FOREIGN KEY (wa_template_uid) REFERENCES wa_template (wa_template_uid)
    GO

ALTER TABLE wa_ui_metadata_hist
    ADD CONSTRAINT FK_WA_UI_METADATA_HIST_ON_WA_TEMPLATE_HIST_UID FOREIGN KEY (wa_template_hist_uid) REFERENCES wa_template_hist (wa_template_hist_uid)
    GO

ALTER TABLE wa_ui_metadata
    ADD CONSTRAINT FK_WA_UI_METADATA_ON_WA_TEMPLATE_UID FOREIGN KEY (wa_template_uid) REFERENCES wa_template (wa_template_uid)
    GO

ALTER TABLE workup_hist
    ADD CONSTRAINT FK_WORKUP_HIST_ON_WORKUP_UID FOREIGN KEY (workup_uid) REFERENCES workup (workup_uid)
    GO

ALTER TABLE workup
    ADD CONSTRAINT FK_WORKUP_ON_WORKUP_UID FOREIGN KEY (workup_uid) REFERENCES act (act_uid)
    GO