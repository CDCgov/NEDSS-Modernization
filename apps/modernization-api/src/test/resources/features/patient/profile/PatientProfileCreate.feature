@patient_profile_create
Feature: Patient Profile create

  Scenario: I can create a patient with comments
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient with comments
    When I send a create patient request
    Then the patient is created
    And the patient created has the entered comment

  Scenario: I cannot send a create patient request without ADD-PATIENT permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient with comments
    When I send a create patient request
    Then I am unable to create a patient

  Scenario: I cannot send a create patient request without FIND-PATIENT permission
    Given I am logged into NBS
    And I have the authorities: "ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient with comments
    When I send a create patient request
    Then I am unable to create a patient