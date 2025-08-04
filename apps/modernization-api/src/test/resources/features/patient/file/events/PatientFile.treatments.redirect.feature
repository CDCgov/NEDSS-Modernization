@patient-file-treatment
Feature: Patient File Treatments redirection

  Background:
    Given I am logged in
    And I have a patient
    And the patient is a subject of an investigation
    And the patient is a subject of a Treatment

  Scenario: A Treatment is viewed from the Patient Profile
    Given I can "VIEW" any "TREATMENT"
    When the Treatment is viewed from the Patient file
    Then NBS is prepared to view a Treatment
    And I am redirected to Classic NBS to view a Treatment

  Scenario: A Treatment is viewed from the Patient Profile without required permissions
    When the Treatment is viewed from the Patient file
    Then I am not allowed due to insufficient permissions
