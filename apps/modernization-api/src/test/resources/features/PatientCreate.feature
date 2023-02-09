@patient_create
Feature: Patient create

  Background: 
    Given I have the authorities: "FIND-PATIENT,ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"

  Scenario: I can send a create patient request
    When I send a create patient request
    Then the patient create request is posted to kafka
