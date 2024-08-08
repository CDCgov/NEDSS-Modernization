@patient-profile-phone-change
Feature: Patient Profile Phone Changes

  Background:
    Given I have a patient

  Scenario: I can update a patient with a new phone
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a patient's phone is added
    Then the patient has the new phone

  Scenario: I can update a patient's existing phone
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a phone
    When a patient's phone is changed
    Then the patient has the expected phone
    And the patient phone locator history contains the previous version

  Scenario: I can remove a patient's existing phone
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a phone
    When a patient's phone is removed
    Then the patient does not have the expected phone
    And the patient phone locator history contains the previous version

  Scenario: I cannot add a patient's phone without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to add a patient's phone

  Scenario: I cannot update a patient's phone without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to change a patient's phone

  Scenario: I cannot remove a patient's phone without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to remove a patient's phone