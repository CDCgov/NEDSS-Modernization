@patient_profile_create
Feature: Patient Profile create

  Scenario: I can create a patient with names
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient with names
    When I send a create patient request
    Then the patient is created
    And the patient created has the entered names

  Scenario: I can create a patient with addresses
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient with addresses
    When I send a create patient request
    Then the patient is created
    And the patient created has the entered addresses

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

  Scenario: I can create a patient with emails
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient with emails
    When I send a create patient request
    Then the patient is created
    And the patient created has the entered emails

  Scenario: I can create a patient with phones
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient with phones
    When I send a create patient request
    Then the patient is created
    And the patient created has the entered phones

  Scenario: I can create a patient with races
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient with races
    When I send a create patient request
    Then the patient is created
    And the patient created has the entered races

  Scenario: I can create a patient with identifications
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient with identifications
    When I send a create patient request
    Then the patient is created
    And the patient created has the entered identifications
