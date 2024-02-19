@create_concept
Feature: Create concept

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a valueset named "test_value_set"

  Scenario: I can create a concept
    Given I have the following create concept request:
      | localCode            | testCode                 |
      | longName             | testLongName             |
      | display              | testDisplay              |
      | effectiveFromTime    | 2022-02-15T00:00:00.00   |
      | effectiveToTime      | 2030-01-01T00:00:00.00   |
      | status               | ACTIVE                   |
      | adminComments        | testAdminComments        |
      | conceptCode          | testConceptCode          |
      | conceptName          | testConceptName          |
      | preferredConceptName | testPreferredConceptName |
      | codeSystem           | YN_IND_HL7               |
    When I send a create concept request for the valueset "test_value_set"
    Then the concept is created
