@patient-file @patient-address-demographics
Feature: Viewing the address demographics of a patient

  Background:
    Given I am logged into NBS
    And I can "view" any "patient"
    And I have a patient

  Scenario: I can view the patient's address demographics
    Given the patient has a House - Home address at "1640 Riverside Drive" "Hill Valley" "33266" as of 11/01/1955
    When I view the patient's address demographics
    Then the patient file address demographics includes a House - Home address at "1640 Riverside Drive" "Hill Valley" "33266" as of 11/01/1955


  Scenario: No address demographics are returned when a patient does not have any address demographics
    When I view the patient's address demographics
    Then no values are returned
