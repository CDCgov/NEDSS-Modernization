
    create table question_bank.dbo.question (
       question_type varchar(31) not null,
        version bigint identity not null,
        id bigint not null,
        label varchar(300),
        required bit,
        tooltip varchar(200),
        allow_future_dates bit not null,
        multiselect bit not null,
        max_value int,
        min_value int,
        max_length int,
        placeholder varchar(100),
        answer_set_id bigint,
        default_answer_id bigint,
        units_set bigint,
        primary key (version, id)
    );

    create table question_bank.dbo.question_group (
       id bigint identity not null,
        label varchar(255),
        primary key (id)
    );

    create table question_bank.dbo.questionnaire (
       id bigint identity not null,
        questionnaire_type varchar(100) not null,
        primary key (id)
    );

    create table question_bank.dbo.[rule] (
       id bigint identity not null,
        expression int not null,
        type varchar(255) not null,
        version bigint not null,
        primary key (id)
    );

    create table question_bank.dbo.value (
       id bigint identity not null,
        display varchar(255) not null,
        val varchar(255) not null,
        value_set_id bigint,
        version bigint,
        primary key (id)
    );

    create table question_bank.dbo.value_set (
       version bigint identity not null,
        id bigint not null,
        description varchar(200),
        name varchar(255),
        primary key (version, id)
    );

    create table dbo.question_group_questions (
       question_group_id bigint not null,
        question_id bigint not null,
        version bigint not null,
        primary key (question_group_id, question_id, version)
    );

    create table dbo.questionnaire_conditions (
       questionnaire_id bigint not null,
        condition_codes varchar(255)
    );

    create table dbo.questionnaire_question_group (
       questionnaire_id bigint not null,
        question_group_id bigint not null
    );

    create table dbo.questionnaire_rules (
       questionnaire_id bigint not null,
        rule_id bigint not null
    );

    alter table dbo.question_group_questions 
       add constraint UK_l24bkh0iwfoe6tvo8jt77lcvh unique (question_id, version);

    alter table dbo.questionnaire_question_group 
       add constraint UK_efuw6119pydmmj1as3bqos947 unique (question_group_id);

    alter table dbo.questionnaire_rules 
       add constraint UK_h052g1d81iau649saj18d1ak6 unique (rule_id);

    alter table question_bank.dbo.question 
       add constraint FKq4ri5yt0cnptya0wraitximdn 
       foreign key (answer_set_id, version) 
       references question_bank.dbo.value_set;

    alter table question_bank.dbo.question 
       add constraint FKdik0m2awxpabt72f3ebkrp8h1 
       foreign key (default_answer_id) 
       references question_bank.dbo.value;

    alter table question_bank.dbo.question 
       add constraint FKsk3hmoqq463i6o18s5j82dh12 
       foreign key (units_set, version) 
       references question_bank.dbo.value_set;

    alter table question_bank.dbo.[rule] 
       add constraint FKh5p4xc4smg466dco57vs54jit 
       foreign key (id, version) 
       references question_bank.dbo.question;

    alter table question_bank.dbo.value 
       add constraint FKj21j497wyvg1bqwm3nuv7xkqn 
       foreign key (value_set_id, version) 
       references question_bank.dbo.value_set;

    alter table dbo.question_group_questions 
       add constraint FKvau0thus6e7brs8vlub07fb2 
       foreign key (question_id, version) 
       references question_bank.dbo.question;

    alter table dbo.question_group_questions 
       add constraint FKa6xdvng7g1qxgb6b33kpmrs2a 
       foreign key (question_group_id) 
       references question_bank.dbo.question_group;

    alter table dbo.questionnaire_conditions 
       add constraint FK8bhsllvenpcqne8qk5s1grebn 
       foreign key (questionnaire_id) 
       references question_bank.dbo.questionnaire;

    alter table dbo.questionnaire_question_group 
       add constraint FK35sl2r54gim617rn8ls0oa1mv 
       foreign key (question_group_id) 
       references question_bank.dbo.question_group;

    alter table dbo.questionnaire_question_group 
       add constraint FK1i40d11ahid347m1nw5tefdq4 
       foreign key (questionnaire_id) 
       references question_bank.dbo.questionnaire;

    alter table dbo.questionnaire_rules 
       add constraint FKoi6spivwe85ftn4rcs2jbai7n 
       foreign key (rule_id) 
       references question_bank.dbo.[rule];

    alter table dbo.questionnaire_rules 
       add constraint FK6adwbkx35kvp8wwy0ywjjuwy0 
       foreign key (questionnaire_id) 
       references question_bank.dbo.questionnaire;
