@patient_create
Feature: Patient create

  Background: 
    Given I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And A patient does not exist

  Scenario: I can create a patient
    When I send a create patient request
    Then I can find the patient
