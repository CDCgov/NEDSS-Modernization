@update_page_date_question
Feature: I can update a date question on a page

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page
    And the page has a tab named "tab"
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And I have the following create date question request:
      | codeSet             | PHIN                        |
      | uniqueId            | dateUpdateQuestionId        |
      | uniqueName          | dateUpdateQuestionName      |
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
      | rdbColumnName       | DATE_UPD_RDB_COL            |
      | dataMartColumnName  | DATE_UPD_DATA_MART_COL      |
      | includedInMessage   | true                        |
      | messageVariableId   | msgVariableId               |
      | labelInMessage      | Message Label               |
      | codeSystem          | ABNORMAL_FLAGS_HL7          |
      | requiredInMessage   | false                       |
    And I send the create date question request
    And I add a question to a page

  Scenario: I can update a date question on a page
    Given I have the following update date question request for a page:
      | displayControl     | 1029                        |
      | allowFutureDates   | true                        |
      | mask               | DATE                        |
      | label              | A date question for testing |
      | tooltip            | Date question tooltip       |
      | adminComments      | Admin comments              |
      | visible            | false                       |
      | enabled            | false                       |
      | required           | false                       |
      | reportLabel        | reportLabelUpdate           |
      | dataMartColumnName | UpdatePageDateDmart         |
      | includedInMessage  | true                        |
      | messageVariableId  | msgVariableId               |
      | labelInMessage     | Message Label               |
      | codeSystem         | LOINC_HL7                   |
      | requiredInMessage  | true                        |
      | hl7DataType        | CWE                         |
    When I send the update date question request for a page
    Then the date question is updated for the page
