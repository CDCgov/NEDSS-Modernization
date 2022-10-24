@patient_create
Feature: Patient create

  Background: 
    Given A patient does not exist
    And I am logged in

  Scenario: I can create a patient
    When I send a create patient request
    Then I can find the patient
