@patient_create
Feature: Patient create

  Background: 
    Given A patient does not exist

  Scenario: I can create a patient
    When I send a create patient request
    Then The patient exists
