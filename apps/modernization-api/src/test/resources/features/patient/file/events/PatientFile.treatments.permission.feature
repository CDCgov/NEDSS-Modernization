@patient-file-treatment
Feature: Patient File Treatments Permissions

  Background:
    Given I am logged in
    And I have a patient
    And the patient is a subject of a Treatment

  Scenario: I cannot retrieve Treatments for a patient without proper permission
    When I view the treatments for the patient
    Then I am not allowed due to insufficient permissions

  Scenario: I can only view Treatments for a patient with the proper permissions
    Given I can "view" any "Treatment"
    When I view the treatments for the patient
    Then the patient file has the treatment
