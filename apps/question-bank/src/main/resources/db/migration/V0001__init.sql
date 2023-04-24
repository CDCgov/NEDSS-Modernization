CREATE TABLE answer (
    id bigint IDENTITY NOT NULL,
    display varchar(255) NOT NULL,
    val varchar(255) NOT NULL,
    answer_set_id bigint NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE answer_set (
    id bigint IDENTITY NOT NULL,
    description varchar(200),
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE question (
    id bigint IDENTITY NOT NULL,
    allow_future_dates bit,
    label varchar(300),
    max_length int,
    max_value int,
    min_value int,
    multiselect bit,
    placeholder varchar(100),
    required bit,
    tooltip varchar(200),
    units_set bigint,
    default_answer_id bigint,
    answer_set_id bigint,
    question_type varchar(20),
    PRIMARY KEY (id)
);

CREATE TABLE question_group (
    id bigint IDENTITY NOT NULL,
    label varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE question_group_questions (
    question_group_id bigint NOT NULL,
    question_id bigint NOT NULL,
    PRIMARY KEY (question_group_id, question_id)
);

CREATE TABLE condition (
    id bigint IDENTITY NOT NULL,
    name varchar(50) NOT NULL,
    description varchar(300),
    PRIMARY KEY(id)
);

CREATE TABLE questionnaire (
    id bigint IDENTITY NOT NULL,
    questionnaire_type varchar(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE questionnaire_question_group (
    questionnaire_id bigint NOT NULL,
    question_group_id bigint NOT NULL,
    PRIMARY KEY (questionnaire_id, question_group_id)
);

CREATE TABLE questionnaire_conditions (
    questionnaire_id bigint,
    condition_id bigint,
    PRIMARY KEY (questionnaire_id,condition_id)
);

ALTER TABLE questionnaire_conditions
    ADD CONSTRAINT FKnjkg9cre7i0bejyyn783uk7up FOREIGN KEY (condition_id) REFERENCES condition;

ALTER TABLE questionnaire_conditions
    ADD CONSTRAINT FK8bhsllvenpcqne8qk5s1grebn FOREIGN KEY (questionnaire_id) REFERENCES questionnaire;

ALTER TABLE question_group_questions
    ADD CONSTRAINT UK_by81ny1darfq17o2eea443ny0 UNIQUE (question_id);

ALTER TABLE questionnaire_question_group
    ADD CONSTRAINT UK_efuw6119pydmmj1as3bqos947 UNIQUE (question_group_id);

ALTER TABLE answer
    ADD CONSTRAINT FKpiyr5su8j772rgahh2m98cap FOREIGN KEY (answer_set_id) REFERENCES answer_set;

ALTER TABLE question
    ADD CONSTRAINT FKssg1rxfqipf5x1qk4ifa36t8x FOREIGN KEY (default_answer_id) REFERENCES answer;

ALTER TABLE question
    ADD CONSTRAINT FKjmqrcbi9bbs36d0v0nxbkbdo0 FOREIGN KEY (answer_set_id) REFERENCES answer_set;

ALTER TABLE question_group_questions
    ADD CONSTRAINT FKaxjg07cg3ax17o72tlvyx3isl FOREIGN KEY (question_id) REFERENCES question;

ALTER TABLE question_group_questions
    ADD CONSTRAINT FKa6xdvng7g1qxgb6b33kpmrs2a FOREIGN KEY (question_group_id) REFERENCES question_group;

ALTER TABLE questionnaire_question_group
    ADD CONSTRAINT FK35sl2r54gim617rn8ls0oa1mv FOREIGN KEY (question_group_id) REFERENCES question_group;

ALTER TABLE questionnaire_question_group
    ADD CONSTRAINT FK1i40d11ahid347m1nw5tefdq4 FOREIGN KEY (questionnaire_id) REFERENCES questionnaire;