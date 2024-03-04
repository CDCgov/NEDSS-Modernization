@text_question_create
Feature: Create text question

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can create a text question
    Given I have the following create text question request:
      | codeSet             | LOCAL                       |
      | uniqueId            | textQuestionId              |
      | uniqueName          | textQuestionName            |
      | description         | text question description!  |
      | mask                | TXT                         |
      | displayControl      | 1008                        |
      | fieldLength         | 10                          |
      | defaultValue        | default                     |
      | label               | A text question for testing |
      | tooltip             | Text question tooltip       |
      | subgroup            | ADM                         |
      | adminComments       | Admin comments              |
      | reportLabel         | reportLabel                 |
      | defaultRdbTableName | defaultRdbTableName         |
      | rdbColumnName       | TXT_CREATE_RDB_COL          |
      | dataMartColumnName  | TXT_CREATE_DATA_MART_COL    |
      | includedInMessage   | true                        |
      | messageVariableId   | msgVariableId               |
      | labelInMessage      | Message Label               |
      | codeSystem          | ABNORMAL_FLAGS_HL7          |
      | requiredInMessage   | false                       |
      | hl7DataType         | CE                          |
    When I send the create text question request
    Then the text question is created
