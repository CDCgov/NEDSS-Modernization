@patient-profile-birth-change
Feature: Patient Profile Birth Changes

  Background:
    Given I have a patient

  Scenario: I can update a patient's birth
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a patient's birth is changed
    Then the patient has the changed birth

  Scenario: I cannot update a patient's birth without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to change a patient's birth
