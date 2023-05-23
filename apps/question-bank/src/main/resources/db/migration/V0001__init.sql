
    create table question_bank.dbo.display_element (
       display_type varchar(31) not null,
        version int identity not null,
        id bigint not null,
        allow_future_dates bit not null,
        label varchar(300),
        tooltip varchar(200),
        multiselect bit not null,
        max_value int,
        min_value int,
        text varchar(255),
        max_length int,
        placeholder varchar(100),
        default_answer_id bigint,
        value_set_id bigint,
        units_set bigint,
        active bit not null default 1,
        primary key (version, id)
    );

    create table question_bank.dbo.display_element_group (
       version int identity not null,
        id bigint not null,
        label varchar(255) not null,
        primary key (version, id)
    );

    create table question_bank.dbo.group_or_element_ref (
       reference_type varchar(31) not null,
        id bigint identity not null,
        display_order int not null,
        questionnaire_id bigint not null,
        display_element_id int,
        display_element_version bigint,
        group_id int,
        group_version bigint,
        primary key (id)
    );

    create table question_bank.dbo.questionnaire (
       id bigint identity not null,
        description varchar(500),
        name varchar(255),
        primary key (id)
    );

    create table question_bank.dbo.questionnaire_rule (
       id bigint identity not null,
        questionnaire_id bigint,
        primary key (id)
    );

    create table question_bank.dbo.[value] (
       id bigint identity not null,
        code varchar(255) not null,
        comment varchar(255),
        display varchar(255) not null,
        display_order int not null,
        [value] varchar(255) not null,
        value_set_id bigint,
        primary key (id)
    );

    create table question_bank.dbo.value_set (
       id bigint identity not null,
        code varchar(255) not null,
        description varchar(300),
        name varchar(255) not null,
        type varchar(255) not null,
        primary key (id)
    );

    create table dbo.display_element_group_elements (
       group_id int not null,
        group_version bigint not null,
        element_id int not null,
        element_version bigint not null,
        display_order int not null,
        primary key (group_id, group_version, display_order)
    );

    create table dbo.questionnaire_conditions (
       questionnaire_id bigint not null,
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
       add constraint FKhombk3318f2gjg7rj2gw63r3h 
       foreign key (questionnaire_id) 
       references question_bank.dbo.questionnaire;

    alter table question_bank.dbo.group_or_element_ref 
       add constraint FK98f1j618ub117an4ovl5y6jv2 
       foreign key (display_element_id, display_element_version) 
       references question_bank.dbo.display_element;

    alter table question_bank.dbo.group_or_element_ref 
       add constraint FKkboondj5evwoqil1vqu59h5q3 
       foreign key (group_id, group_version) 
       references question_bank.dbo.display_element_group;

    alter table question_bank.dbo.questionnaire_rule 
       add constraint FK2m2g4ebe8m5ejl29cktnehj2b 
       foreign key (questionnaire_id) 
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
       add constraint FK8bhsllvenpcqne8qk5s1grebn 
       foreign key (questionnaire_id) 
       references question_bank.dbo.questionnaire;
