@patient_extended_create
Feature: Creation of Patients with birth demographics

  Background:
    Given I am logged into NBS
    And I can "add" any "patient"
    And I can "find" any "patient"

  Scenario: I can create a patient with birth demographics information
    Given I enter the birth demographics as of date 07/03/1990
    And I enter the birth demographics with the patient born on 04/31/1990
    And the birth demographics are included in the extended patient data
    When I create a patient with extended data
    Then I view the Patient Profile Birth demographics
    And the patient profile birth demographics has the as of date 07/03/1990
    And the patient profile birth demographics has the patient born on 04/31/1990
