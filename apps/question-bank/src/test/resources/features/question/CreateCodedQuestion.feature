@coded_question_create
Feature: Create coded question

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can create a coded question
    Given I have the following create coded question request:
      | codeSet             | PHIN                         |
      | uniqueId            | codedQuestionId              |
      | uniqueName          | codedQuestionName            |
      | description         | coded question description!  |
      | displayControl      | 1007                         |
      | valueSet            | 4150                         |
      | label               | A coded question for testing |
      | tooltip             | coded question tooltip       |
      | subgroup            | ADM                          |
      | adminComments       | Admin comments               |
      | reportLabel         | reportLabel                  |
      | defaultRdbTableName | defaultRdbTableName          |
      | rdbColumnName       | RDB_COL                      |
      | dataMartColumnName  | DATA_MART_COL                |
      | includedInMessage   | true                         |
      | messageVariableId   | msgVariableId                |
      | labelInMessage      | Message Label                |
      | codeSystem          | ABNORMAL_FLAGS_HL7           |
      | requiredInMessage   | false                        |
      | hl7DataType         | CE                           |
    When I send the create coded question request
    Then the coded question is created
