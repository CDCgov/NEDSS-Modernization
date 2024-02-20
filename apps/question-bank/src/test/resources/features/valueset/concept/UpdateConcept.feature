@concept_update
Feature: I can update concepts
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

  Scenario: I update a concept
    Given I have the following update concept request:
      | longName             | updatedLongName        |
      | display              | updateDisplay          |
      | effectiveFromTime    | 2010-02-15T00:00:00.00 |
      | effectiveToTime      | 2012-01-01T00:00:00.00 |
      | status               | INACTIVE               |
      | adminComments        | updated comments!      |
      | conceptCode          | updatedConceptCode     |
      | conceptName          | updateConceptName      |
      | preferredConceptName | updatedPreferredName   |
      | codeSystem           | ABNORMAL_FLAGS_HL7     |
    When I send the update concept request for valueset "test_value_set" and local code "testCode"
    Then the concept is updated