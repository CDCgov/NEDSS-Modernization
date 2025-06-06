@patient @patient-names-api
Feature: Patient Names API

  Background: I am logged in and can find patient
    Given I am logged into NBS
    Given I have a patient

  Scenario: The api return a blank packet if i do not have permissions
    When I call the patient names api
    Then I am not allowed due to insufficient permissions

  Scenario: The api returns an empty array when the patient has no names
    Given I can "View" any "Patient"
    When I call the patient names api
    Then no values are returned

  Scenario: I can find patient names
    Given I can "View" any "Patient"
    And the patient has the legal name "Joe" "Jacob" "Smith", Jr. as of 01/01/2000
    When I call the patient names api
    Then patient names are returned
    Then the 1st name has a "first" of "Joe"
    Then the 1st name has a "middle" of "Jacob"
    Then the 1st name has a "last" of "Smith"
    Then the 1st name has an "asOf" of "2000-01-01"
    Then the 1st name has a "typevalue" of "L"
    Then the 1st name has a "typeName" of "Legal"
    Then the 1st name has a "suffixValue" of "JR"
    Then the 1st name has a "suffixName" of "JR"
