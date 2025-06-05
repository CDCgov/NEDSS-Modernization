@patient-create
Feature: Creation of Patients with address demographics

  Background:
    Given I am logged into NBS
    And I can "add" any "patient"
    And I can "view" any "patient"

  Scenario: I can create a patient with an address demographic
    Given I am entering the House - Home address at "1640 Riverside Drive" "Hill Valley" "33266" as of 11/01/1955
    And the address is included with the extended patient data
    When I create a patient with extended data
    And I view the patient's address demographics
    Then the patient file address demographics includes a House - Home address at "1640 Riverside Drive" "Hill Valley" "33266" as of 11/01/1955
