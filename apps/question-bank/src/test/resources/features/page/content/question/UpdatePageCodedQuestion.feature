@update_page_coded_question
Feature: I can update a coded question on a page

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page
    And the page has a tab named "tab"
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And I have the following create coded question request:
      | codeSet             | PHIN                           |
      | uniqueId            | codedUpdateQuestionId          |
      | uniqueName          | codedUpdateQuestionName        |
      | description         | coded question description!    |
      | displayControl      | 1007                           |
      | valueSet            | 4150                           |
      | label               | A coded question for testing   |
      | tooltip             | coded question tooltip         |
      | subgroup            | ADM                            |
      | adminComments       | Admin comments                 |
      | reportLabel         | reportLabel                    |
      | defaultRdbTableName | defaultRdbTableName            |
      | rdbColumnName       | CODED_UPD_RDB_COL              |
      | dataMartColumnName  | CODED_UPD_CREATE_DATA_MART_COL |
      | includedInMessage   | true                           |
      | messageVariableId   | msgVariableId                  |
      | labelInMessage      | Message Label                  |
      | codeSystem          | ABNORMAL_FLAGS_HL7             |
      | requiredInMessage   | false                          |
      | hl7DataType         | CE                             |
    And I send the create coded question request
    And I add a question to a page

  Scenario: I can update a coded question on a page
    Given I have the following update coded question request for a page:
      | displayControl     | 1029                        |
      | defaultValue       | N                           |
      | valueSet           | 107880                      |
      | label              | A date question for testing |
      | tooltip            | Date question tooltip       |
      | adminComments      | Admin comments              |
      | visible            | false                       |
      | enabled            | false                       |
      | required           | false                       |
      | reportLabel        | reportLabelCodedUpdate      |
      | dataMartColumnName | UpdatePageCodedDmart        |
      | includedInMessage  | false                       |
    When I send the update coded question request for a page
    Then the coded question is updated for the page
