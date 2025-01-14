@patient-profile-address-change
Feature: Patient Profile Address Changes

  Background:
    Given I have a patient

  Scenario: I can update a patient with a new address
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a patient's address is added
    Then the patient profile has the new address

  Scenario: I can update a patient's existing address
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has an address
    When a patient's address is changed
    Then the patient profile has the expected address
    And the patient entity locator history contains the previous version
    And the patient postal locator history contains the previous version

  Scenario: I can remove a patient's existing address
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has an address
    When a patient's address is removed
    Then the patient does not have the expected address
    And the profile has no associated addresses
    And the patient entity locator history contains the previous version
    And the patient postal locator history contains the previous version

  Scenario: I cannot add a patient's address without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to add a patient's address

  Scenario: I cannot update a patient's address without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to change a patient's address

  Scenario: I cannot remove a patient's address without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to remove a patient's address
