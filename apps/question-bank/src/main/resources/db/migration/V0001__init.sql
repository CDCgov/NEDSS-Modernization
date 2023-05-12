
    create table question_bank.dbo.display_element (
       display_type varchar(31) not null,
        id bigint identity not null,
        allow_future_dates bit not null,
        label varchar(300),
        required bit,
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
        primary key (id)
    );

    create table question_bank.dbo.display_group (
       id bigint identity not null,
        label varchar(255) not null,
        primary key (id)
    );

    create table question_bank.dbo.element_ref (
       element_type varchar(31) not null,
        id bigint identity not null,
        questionnaire_id bigint not null,
        element_id bigint,
        element_group_id bigint,
        primary key (id)
    );

    create table question_bank.dbo.questionnaire (
       id bigint identity not null,
        questionnaire_type varchar(100) not null,
        primary key (id)
    );

    create table question_bank.dbo.value (
       id bigint identity not null,
        code varchar(255) not null,
        comment varchar(255),
        display varchar(255) not null,
        value varchar(255) not null,
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

    create table dbo.display_group_elements (
       element_id bigint not null,
        elements_id bigint not null
    );

    create table dbo.questionnaire_conditions (
       questionnaire_id bigint not null,
        condition_codes varchar(255)
    );

    alter table dbo.display_group_elements 
       add constraint UK_g6jm9sb5prro0q34iiapsccys unique (elements_id);

    alter table question_bank.dbo.display_element 
       add constraint FKpouv8pp03v60s8oc75pxf93p5 
       foreign key (default_answer_id) 
       references question_bank.dbo.value;

    alter table question_bank.dbo.display_element 
       add constraint FK71gr2is7p9ud74hd5wsabwe55 
       foreign key (value_set_id) 
       references question_bank.dbo.value_set;

    alter table question_bank.dbo.display_element 
       add constraint FKltq3oumkjl23s1pwbl82p3cci 
       foreign key (units_set) 
       references question_bank.dbo.value_set;

    alter table question_bank.dbo.element_ref 
       add constraint FK1phu102o2d0h9xjwmxrqc7axx 
       foreign key (questionnaire_id) 
       references question_bank.dbo.questionnaire;

    alter table question_bank.dbo.element_ref 
       add constraint FKm4nmpasdqccs9e18unm15644s 
       foreign key (element_id) 
       references question_bank.dbo.display_element;

    alter table question_bank.dbo.element_ref 
       add constraint FK8ekaj4eg76e87dhdxud27dr3c 
       foreign key (element_group_id) 
       references question_bank.dbo.display_group;

    alter table question_bank.dbo.value 
       add constraint FK9ci2o1gpkn77gs4wsg260bgsx 
       foreign key (value_set_id) 
       references question_bank.dbo.value_set;

    alter table dbo.display_group_elements 
       add constraint FKcjeb7n59tldvw38t0jlr1pk3e 
       foreign key (elements_id) 
       references question_bank.dbo.display_element;

    alter table dbo.display_group_elements 
       add constraint FK6dtu3j0vqp1ykrj3ivluaioa5 
       foreign key (element_id) 
       references question_bank.dbo.display_group;

    alter table dbo.questionnaire_conditions 
       add constraint FK8bhsllvenpcqne8qk5s1grebn 
       foreign key (questionnaire_id) 
       references question_bank.dbo.questionnaire;
