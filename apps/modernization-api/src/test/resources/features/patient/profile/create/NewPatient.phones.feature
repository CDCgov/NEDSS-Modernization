@patient-create
Feature: Creation of Patients with phone demo graphics

  Background:
    Given I am logged into NBS
    And I can "add" any "patient"
    And I can "view" any "patient"

  Scenario: I can create a patient with a phone demographic
    Given I am entering the Email Address - Home number of "555-555-5555" as of 11/07/2023
    And the phone is included with the extended patient data
    When I create a patient with extended data
    And I view the patient's phone demographics
    Then  the patient file phone demographics includes an Email Address - Home number of "555-555-5555" as of 11/07/2023
