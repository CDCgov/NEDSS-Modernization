@patient_create
Feature: Patient create

  Scenario: I can send a create patient request
    Given I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When I send a create patient request
    Then the patient create request is posted to kafka

  Scenario: I cannot send a create patient request without the FIND-PATIENT permission
    Given I have the authorities: "ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When I send a create patient request
    Then I get an access denied exception

  Scenario: I cannot send a create patient request without the ADD-PATIENT permission
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When I send a create patient request
    Then I get an access denied exception
