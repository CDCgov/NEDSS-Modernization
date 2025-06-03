@patient-create
Feature: Creation of Patients with identification demographics

  Background:
    Given I am logged into NBS
    And I can "add" any "patient"
    And I can "view" any "patient"

  Scenario: I can create a patient with an identification demographic
    Given I am entering a Driver's license number identification of "00123" as of 05/13/1997
    And the identification is included with the extended patient data
    When I create a patient with extended data
