@patient-profile-ethnicity-change
Feature: Patient Demographics Ethnicity Changes
  Background:
    Given I have a patient

  Scenario: I can update a patient's ethnicity
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a patient's ethnicity is changed
    Then the patient has the changed ethnicity

  Scenario: I can update a patient's ethnicity and history is added
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has the ethnicity Hispanic or Latino
    And the patient ethnicity includes Spaniard
    When a patient's Hispanic or Latino ethnicity is changed to specifically be Cuban
    Then the patient has the changed ethnicity
    And the patient ethnicity history contains the previous version

  Scenario: I cannot update a patient's ethnicity without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to change a patient's ethnicity
