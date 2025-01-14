@patient-profile-mortality-change
Feature: Patient Profile Mortality Changes

  Background:
    Given I have a patient

  Scenario: I can update a patient's mortality
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a patient is deceased
    Then the patient profile contains the details of mortality

  Scenario: I can update a patient's mortality
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a patient is not known to be deceased
    Then the patient does not contain the details of mortality

  Scenario: I cannot update a patient's mortality without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to change a patient's mortality
