@update_page_question_required
Feature: Update page question required

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page
    And the page has a tab named "tab"
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And I have the following create text question request:
      | codeSet             | LOCAL                       |
      | uniqueId            | reqPageText                 |
      | uniqueName          | reqPageName                 |
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
      | rdbColumnName       | UP_REQ_RDB_COL              |
      | dataMartColumnName  | reqTextDmart                |
      | includedInMessage   | false                       |
    And I send the create text question request
    And I add the "reqPageText" question to a page

  Scenario: I can update the required to false
    When I mark a question not required on a page
    Then the question is not required


