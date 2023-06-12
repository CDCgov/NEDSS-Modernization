@patient-profile-race-change
Feature: Patient Profile Race Changes

  Background:
    Given I have a patient

  Scenario: I can update a patient with a new race
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a patient's race is added
    Then the patient has the expected race
#    And the patient race changed event is emitted

  Scenario: I can update a patient's existing race
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a race
    When a patient's race is changed
    Then the patient has the expected race
#    And the patient race changed event is emitted

  Scenario: I can remove a patient's existing race
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a race
    When a patient's race is removed
    Then the patient does not have the expected race
#    And the patient race changed event is emitted

  Scenario: I cannot update a patient's race without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to add a patient's race
    And a patient event is not emitted

  Scenario: I cannot update a patient's race without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to change a patient's race
    And a patient event is not emitted
