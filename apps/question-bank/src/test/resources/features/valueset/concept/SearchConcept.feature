@concept_search
Feature: I can search for concepts

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a valueset named "test_value_set"
    And I have the following create concept request:
      | localCode            | testCode                 |
      | longName             | testLongName             |
      | display              | testDisplay              |
      | effectiveFromTime    | 2000-02-15T00:00:00.00   |
      | effectiveToTime      | 2030-01-01T00:00:00.00   |
      | status               | ACTIVE                   |
      | adminComments        | testAdminComments        |
      | conceptCode          | testConceptCode          |
      | conceptName          | testConceptName          |
      | preferredConceptName | testPreferredConceptName |
      | codeSystem           | YN_IND_HL7               |
    And I send a create concept request for the valueset "test_value_set"
    And I have the following create concept request:
      | localCode            | ztestCode                 |
      | longName             | ztestLongName             |
      | display              | ztestDisplay              |
      | effectiveFromTime    | 2021-02-15T00:00:00.00    |
      | effectiveToTime      | 2010-01-01T00:00:00.00    |
      | status               | ACTIVE                    |
      | adminComments        | ztestAdminComments        |
      | conceptCode          | ztestConceptCode          |
      | conceptName          | ztestConceptName          |
      | preferredConceptName | ztestPreferredConceptName |
      | codeSystem           | YN_IND_HL7                |
    And I send a create concept request for the valueset "test_value_set"

  Scenario: I can search for concepts within a valueset
    When I search for concepts in "test_value_set" sorted by "<field>" "<order>"
    Then the first concept returned is "<first entry>"
    Examples:
      | field         | order      | first entry |
      | code          | ascending  | testCode    |
      | code          | descending | ztestCode   |
      | display       | ascending  | testCode    |
      | display       | descending | ztestCode   |
      | conceptCode   | ascending  | testCode    |
      | conceptCode   | descending | ztestCode   |
      | effectiveDate | ascending  | testCode    |
      | effectiveDate | descending | ztestCode   |