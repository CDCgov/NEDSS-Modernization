@create_business_rule
Feature: Create Business Rule

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page
    And the page has a tab named "tab"
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And I have the following create coded question request:
      | codeSet             | PHIN                   |
      | uniqueId            | eRcc                   |
      | uniqueName          | eRcc                   |
      | description         | description            |
      | displayControl      | 1007                   |
      | valueSet            | 4150                   |
      | label               | first question         |
      | tooltip             | coded question tooltip |
      | subgroup            | ADM                    |
      | adminComments       | Admin comments         |
      | reportLabel         | reportLabel            |
      | defaultRdbTableName | defaultRdbTableName    |
      | rdbColumnName       | E_RA                   |
      | dataMartColumnName  | E_RA                   |
      | includedInMessage   | false                  |
    And I send the create coded question request
    And I have the following create coded question request:
      | codeSet             | PHIN                 |
      | uniqueId            | eRcc2                |
      | uniqueName          | eRcc2                |
      | description         | description          |
      | displayControl      | 1007                 |
      | valueSet            | 4150                 |
      | label               | second question      |
      | tooltip             | second tooltip       |
      | subgroup            | ADM                  |
      | adminComments       | Admin comments       |
      | reportLabel         | reportLabel2         |
      | defaultRdbTableName | defaultRdbTableName2 |
      | rdbColumnName       | E_RB                 |
      | dataMartColumnName  | E_RB                 |
      | includedInMessage   | false                |
    And I send the create coded question request
    And I add the "eRcc" question to a page
    And I add the "eRcc2" question to a page

  Scenario: I can create an enable rule
    Given I have the following create rule request:
      | ruleFunction      | ENABLE                   |
      | description       | test description         |
      | sourceIdentifier  | enableRuleCodedQuestion  |
      | anySourceValue    | false                    |
      | sourceValues      | N,No,UNK,Unknown         |
      | comparator        | EQUAL_TO                 |
      | targetType        | QUESTION                 |
      | targetIdentifiers | enableRuleCodedQuestion2 |
      | sourceText        | first question           |
      | targetValueText   | second question          |
    When I create a rule
    Then the rule is created
