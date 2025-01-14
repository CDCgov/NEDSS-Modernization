@patient-profile-gender-change
Feature: Patient Profile Gender Changes

  Background:
    Given I have a patient

  Scenario: I can update a patient's gender
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a patient's gender is changed
    Then the patient has the changed gender

  Scenario: I cannot update a patient's gender without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to change a patient's gender
