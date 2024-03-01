@update_page_numeric_question
Feature: I can update a numeric question on a page

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page
    And the page has a tab named "tab"
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And I have the following create numeric question request:
      | codeSet             | LOCAL                          |
      | uniqueId            | updateNumericQuestionId        |
      | uniqueName          | updateNumericQuestionName      |
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
      | rdbColumnName       | NUM_UPP_RDB_COL                |
      | dataMartColumnName  | NUM_UPP_DMT                    |
      | includedInMessage   | false                          |
    And I send the create numeric question request
    And I add a question to a page

  Scenario: I can update a numeric question on a page
    Given I have the following update numeric question request for a page:

      | mask                 | NUM_YYYY                    |
      | fieldLength          |                             |
      | defaultValue         | 2024                        |
      | minValue             |                             |
      | maxValue             |                             |
      | relatedUnitsLiteral  |                             |
      | relatedUnitsValueSet | 4150                        |
      | displayControl       | 1008                        |
      | label                | A text question for testing |
      | tooltip              | Text question tooltip       |
      | adminComments        | Admin comments              |
      | visible              | false                       |
      | enabled              | false                       |
      | required             | false                       |
      | reportLabel          | reportLabel                 |
      | dataMartColumnName   | UpdatePageTextDmart         |
      | includedInMessage    | true                        |
      | messageVariableId    | msgVariableId               |
      | labelInMessage       | Message Label               |
      | codeSystem           | LOINC_HL7                   |
      | requiredInMessage    | true                        |
      | hl7DataType          | CWE                         |
    When I send the update numeric question request for a page
    Then the numeric question is updated for the page