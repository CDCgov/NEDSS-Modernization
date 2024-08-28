@patient-profile-name-change
Feature: Patient Profile Name Changes

  Background:
    Given I have a patient

  Scenario: I can update a patient with a new name
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a patient's name is added
    Then the patient has the new name

  Scenario: I can update a patient's existing name
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a name
    When a patient's name is changed
    Then the patient has the expected name
    And the patient name history contains the previous version

  Scenario: I can remove a patient's existing name
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a name
    When a patient's name is removed
    Then the patient does not have the expected name
    And the patient name history contains the previous version

  Scenario: I cannot add a patient's name without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to add a patient's name

  Scenario: I cannot update a patient's name without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to change a patient's name

  Scenario: I cannot remove a patient's name without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to remove a patient's name
