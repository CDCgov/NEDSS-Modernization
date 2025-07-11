@patient-create
Feature: Creation of Patients with race demographics

  Background:
    Given I am logged into NBS
    And I can "add" any "patient"
    And I can "viewworkup" any "patient"

  Scenario: I can create a patient with a race
    Given I am entering the Other race as of 04/11/2019
    And the race is included with the extended patient data
    When I create a patient with extended data
    And I view the patient's race demographics
    Then the patient file race demographics includes the category Other

  Scenario: I can create a patient with a detailed
    Given I am entering the Asian race as of 09/07/2024
    And I am entering the detailed race Taiwanese
    And I am entering the detailed race Bangladeshi
    And the race is included with the extended patient data
    When I create a patient with extended data
    And I view the patient's race demographics
    Then the patient file race demographics includes Taiwanese within the Asian race
    And the patient file race demographics includes Bangladeshi within the Asian race
