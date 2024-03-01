@numeric_question_create
Feature: Create numeric question

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can create a numeric question
    Given I have the following create numeric question request:
      | codeSet             | LOCAL                          |
      | uniqueId            | numericQuestionId              |
      | uniqueName          | numericQuestionName            |
      | description         | numeric question description!  |
      | mask                | NUM                            |
      | displayControl      | 1008                           |
      | fieldLength         | 5                              |
      | defaultValue        | 10                             |
      | minValue            | 0                              |
      | maxValue            | 10000                          |
      | relatedUnitsLiteral | Literal Related Unit           |
      | label               | A numeric question for testing |
      | tooltip             | numeric question tooltip       |
      | subgroup            | ADM                            |
      | adminComments       | Admin comments                 |
      | reportLabel         | reportLabel                    |
      | defaultRdbTableName | defaultRdbTableName            |
      | rdbColumnName       | RDB_COL                        |
      | dataMartColumnName  | DATA_MART_COL                  |
      | includedInMessage   | true                           |
      | messageVariableId   | msgVariableId                  |
      | labelInMessage      | Message Label                  |
      | codeSystem          | ABNORMAL_FLAGS_HL7             |
      | requiredInMessage   | false                          |
      | hl7DataType         | CE                             |
    When I send the create numeric question request
    Then the numeric question is created
