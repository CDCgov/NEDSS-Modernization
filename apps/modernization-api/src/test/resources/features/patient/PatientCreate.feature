@patient_create
Feature: Patient create

  Scenario: I can create a patient
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    When I submit the patient
    Then the patient is created

  Scenario: I can create a patient with birth
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    And the new patient's birth is entered
    When I submit the patient
    Then the new patient has the entered birth

  Scenario: I can create a patient with mortality
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    And the new patient's mortality is entered
    When I submit the patient
    Then the new patient has the entered mortality

  Scenario: I can create a patient with marital status
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    And the new patient's marital status is entered
    When I submit the patient
    Then the new patient has the entered marital status

  Scenario: I can create a patient with comments
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    And the new patient comment is entered
    When I submit the patient
    Then the new patient has the entered comment

  Scenario: I can create a patient with ethnicity
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    And the new patient's ethnicity is entered
    When I submit the patient
    Then the new patient has the entered ethnicity

  Scenario: I can create a patient with a name
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    And the new patient's name is entered
    When I submit the patient
    Then the new patient has the entered name

  Scenario: I can create a patient with a race
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    And the new patient's race is entered
    When I submit the patient
    Then the new patient has the entered race

  Scenario: I can create a patient with an email address
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    And the new patient's email address is entered
    When I submit the patient
    Then the new patient has the entered email address

  Scenario: I can create a patient with a phone number
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    And the new patient's phone number is entered
    When I submit the patient
    Then the new patient has the entered phone number

  Scenario: I can create a patient with an address
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    And the new patient's address is entered
    When I submit the patient
    Then the new patient has the entered address

  Scenario: I can create a patient with an identification
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    And the new patient's identification is entered
    When I submit the patient
    Then the new patient has the entered identification

  Scenario: I cannot send a create patient request without the FIND-PATIENT permission
    Given I am logged into NBS
    And I have the authorities: "ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    When I submit the patient
    Then I am not allowed to create a patient

  Scenario: I cannot send a create patient request without the ADD-PATIENT permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I am adding a new patient
    When I submit the patient
    Then I am not allowed to create a patient
