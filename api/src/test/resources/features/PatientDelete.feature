@patient_delete
Feature: Patient delete

  Background: 
    Given A patient exists

  Scenario: I can delete a patient
    When I send a delete patient request
    Then The patient does not exist
