@date_question_create
Feature: Create date question

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can create a date question
    Given I have the following create date question request:
      | codeSet             | PHIN                        |
      | uniqueId            | dateQuestionId              |
      | uniqueName          | dateQuestionName            |
      | description         | date question description!  |
      | mask                | DATE                        |
      | allowFutureDates    | false                       |
      | displayControl      | 1008                        |
      | label               | A date question for testing |
      | tooltip             | date question tooltip       |
      | subgroup            | ADM                         |
      | adminComments       | Admin comments              |
      | reportLabel         | reportLabel                 |
      | defaultRdbTableName | defaultRdbTableName         |
      | rdbColumnName       | RDB_COL                     |
      | dataMartColumnName  | DATA_MART_COL               |
      | includedInMessage   | true                        |
      | messageVariableId   | msgVariableId               |
      | labelInMessage      | Message Label               |
      | codeSystem          | ABNORMAL_FLAGS_HL7          |
      | requiredInMessage   | false                       |
      | hl7DataType         | CE                          |
    When I send the create date question request
    Then the date question is created
