@patient-profile-identification-change
Feature: Patient Profile Identification Changes

  Background:
    Given I have a patient

  Scenario: I can update a patient with a new identification
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a patient's identification is added
    Then the patient has the new identification

  Scenario: I can update a patient's existing identification
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has identification
    When a patient's identification is changed
    Then the patient has the expected identification
    And the patient identification history contains the previous version

  Scenario: I can remove a patient's existing identification
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has identification
    When a patient's identification is removed
    Then the patient does not have the expected identification
    And the patient identification history contains the previous version

  Scenario: I cannot add a patient's identification without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to add a patient's identification

  Scenario: I cannot update a patient's identification without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to change a patient's identification

  Scenario: I cannot remove a patient's identification without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to remove a patient's identification
