@update_page_coded_valueset
Feature: I can update the valueset for a coded question

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page
    And the page has a tab named "tab"
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And I have the following create coded question request:
      | codeSet             | PHIN                         |
      | uniqueId            | codedUpdateVS                |
      | uniqueName          | codedUpdateVS                |
      | description         | coded question description!  |
      | displayControl      | 1007                         |
      | valueSet            | 4150                         |
      | label               | A coded question for testing |
      | tooltip             | coded question tooltip       |
      | subgroup            | ADM                          |
      | adminComments       | Admin comments               |
      | reportLabel         | reportLabel                  |
      | defaultRdbTableName | defaultRdbTableName          |
      | rdbColumnName       | CODED_UPVS_RDB_COL           |
      | dataMartColumnName  | CODED_UPVS_CREATE_D          |
      | includedInMessage   | true                         |
      | messageVariableId   | msgVariableId                |
      | labelInMessage      | Message Label                |
      | codeSystem          | ABNORMAL_FLAGS_HL7           |
      | requiredInMessage   | false                        |
      | hl7DataType         | CE                           |
    And I send the create coded question request
    And I add the "codedUpdateVS" question to a page

  Scenario: I can change the valueset for a coded question
    Given I have the following update question valueset request for a page:
      | valueset | 107880 |
    When I send the update coded question valueset request for a page
    Then the coded question valueset is updated for the page