    create table question_bank.dbo.display_element (
       display_type varchar(31) not null,
        id uniqueidentifier not null,
        version int not null,
        add_time datetime2 not null,
        add_user bigint not null,
        last_update_time datetime2 not null,
        last_update_user bigint not null,
        status varchar(20) not null,
        status_time datetime2 not null,
        code_set varchar(20) not null,
        allow_future_dates bit,
        label varchar(300),
        tooltip varchar(200),
        multiselect bit,
        default_numeric_value int,
        max_value int,
        min_value int,
        text varchar(255),
        default_text_value varchar(255),
        max_length int,
        placeholder varchar(100),
        default_answer_id uniqueidentifier,
        value_set_id uniqueidentifier,
        units_set uniqueidentifier,
        primary key nonclustered (id, version)
    );

    create table question_bank.dbo.display_element_group (
       version int not null,
        id uniqueidentifier not null,
        label varchar(255) not null,
        primary key nonclustered (version, id)
    );

    create table question_bank.dbo.group_or_element_ref (
       reference_type varchar(31) not null,
        id bigint identity not null,
        display_order int not null,
        tab_id uniqueidentifier not null,
        display_element_id uniqueidentifier,
        display_element_version int,
        group_id int,
        group_version uniqueidentifier,
        primary key (id)
    );

    create table question_bank.dbo.questionnaire (
       id uniqueidentifier not null,
        version int not null,
        description varchar(500),
        name varchar(255),
        primary key nonclustered (id, version)
    );

    create table question_bank.dbo.questionnaire_rule (
       id uniqueidentifier not null,
        questionnaire_id uniqueidentifier not null,
        version int not null,
        primary key nonclustered (id)
    );

    create table question_bank.dbo.tab (
       id uniqueidentifier not null,
        display_order int not null,
        name varchar(100) not null,
        questionnaire_id uniqueidentifier not null,
        version int not null,
        primary key nonclustered (id)
    );

    create table question_bank.dbo.[value] (
       id uniqueidentifier not null,
        code varchar(255) not null,
        comment varchar(255),
        display varchar(255) not null,
        display_order int not null,
        [value] varchar(255) not null,
        value_set_id uniqueidentifier,
        primary key nonclustered (id)
    );

    create table question_bank.dbo.value_set (
       id uniqueidentifier not null,
        code varchar(255) not null,
        description varchar(300),
        name varchar(255) not null,
        code_set varchar(20) not null,
        primary key nonclustered (id)
    );

    create table dbo.display_element_group_elements (
       group_id int not null,
        group_version uniqueidentifier not null,
        element_id uniqueidentifier not null,
        element_version int not null,
        display_order int not null,
        primary key nonclustered (group_id, group_version, display_order)
    );

    create table dbo.questionnaire_conditions (
       questionnaire_id uniqueidentifier not null,
        questionnaire_version int not null,
        condition_codes varchar(255)
    );

    alter table dbo.display_element_group_elements 
       add constraint UK_k2dj4hfm5qt796pj3xl1t8ojw unique (element_id, element_version);

    alter table question_bank.dbo.display_element 
       add constraint FKbwcamhslss831pffsxwh20jmj 
       foreign key (default_answer_id) 
       references question_bank.dbo.[value];

    alter table question_bank.dbo.display_element 
       add constraint FK71gr2is7p9ud74hd5wsabwe55 
       foreign key (value_set_id) 
       references question_bank.dbo.value_set;

    alter table question_bank.dbo.display_element 
       add constraint FKltq3oumkjl23s1pwbl82p3cci 
       foreign key (units_set) 
       references question_bank.dbo.value_set;

    alter table question_bank.dbo.group_or_element_ref 
       add constraint FKj4xcdcd32serk2e9sbamkcfte 
       foreign key (tab_id) 
       references question_bank.dbo.tab;

    alter table question_bank.dbo.group_or_element_ref 
       add constraint FK98f1j618ub117an4ovl5y6jv2 
       foreign key (display_element_id, display_element_version) 
       references question_bank.dbo.display_element;

    alter table question_bank.dbo.group_or_element_ref 
       add constraint FKkboondj5evwoqil1vqu59h5q3 
       foreign key (group_id, group_version) 
       references question_bank.dbo.display_element_group;

    alter table question_bank.dbo.questionnaire_rule 
       add constraint FKhh0hnrxhvrkg00mdj8joul74o 
       foreign key (questionnaire_id, version) 
       references question_bank.dbo.questionnaire;

    alter table question_bank.dbo.tab 
       add constraint FK1yfpk1pgtssw29pmejyw14et7 
       foreign key (questionnaire_id, version) 
       references question_bank.dbo.questionnaire;

    alter table question_bank.dbo.[value] 
       add constraint FKcybbi4uvjoyf3pfocj0lrqvgn 
       foreign key (value_set_id) 
       references question_bank.dbo.value_set;

    alter table dbo.display_element_group_elements 
       add constraint FK7fpl5dvnhce03iaqlrinctlln 
       foreign key (element_id, element_version) 
       references question_bank.dbo.display_element;

    alter table dbo.display_element_group_elements 
       add constraint FKs8y9klhihke537qfp6eag5oe6 
       foreign key (group_id, group_version) 
       references question_bank.dbo.display_element_group;

    alter table dbo.questionnaire_conditions 
       add constraint FKtkfc1ypw3m4xffsp9f9mxw11v 
       foreign key (questionnaire_id, questionnaire_version) 
       references question_bank.dbo.questionnaire;
