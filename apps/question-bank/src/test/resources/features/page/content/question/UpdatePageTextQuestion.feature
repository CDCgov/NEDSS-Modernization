@update_page_text_question
Feature: I can update a text question on a page

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page
    And the page has a tab named "tab"
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And I have the following create text question request:
      | codeSet             | LOCAL                       |
      | uniqueId            | updatePageText              |
      | uniqueName          | updatePageName              |
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
      | rdbColumnName       | UP_TXT_RDB_COL              |
      | dataMartColumnName  | UpdatePageTextDmart         |
      | includedInMessage   | false                       |
    And I send the create text question request
    And I add the "updatePageText" question to a page

  Scenario: I can update a text question on a page
    Given I have the following update text question request for a page:
      | displayControl     | 1008                        |
      | fieldLength        | 10                          |
      | defaultValue       | default                     |
      | label              | A text question for testing |
      | tooltip            | Text question tooltip       |
      | adminComments      | Admin comments              |
      | visible            | false                       |
      | enabled            | false                       |
      | required           | false                       |
      | reportLabel        | reportLabel                 |
      | dataMartColumnName | UpdatePageTextDmart         |
      | includedInMessage  | true                        |
      | messageVariableId  | msgVariableId               |
      | labelInMessage     | Message Label               |
      | codeSystem         | LOINC_HL7                   |
      | requiredInMessage  | true                        |
      | hl7DataType        | CWE                         |
    When I send the update text question request for a page
    Then the text question is updated for the page
