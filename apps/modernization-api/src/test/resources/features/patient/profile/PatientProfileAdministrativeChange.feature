@patient-profile-administrative-change
Feature: Patient Demographics Administrative Changes
  Background:
    Given I have a patient

  Scenario: I can update a patient's administrative
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a patient's administrative is changed
    Then the patient has the changed administrative

  Scenario: I cannot update a patient's administrative without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to change a patient's administrative
    And a patient event is not emitted
