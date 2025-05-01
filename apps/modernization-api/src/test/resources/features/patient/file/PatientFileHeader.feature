@patient-file-header
Feature: Patient File Header

  Scenario: I can retrieve the patient file header for a patient
    Given I am logged into NBS
    And I have a patient
    And the patient has a legal name
    And the patient has a Case Report
    Then I view the Patient File Header
