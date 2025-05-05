@patient-file @patient-demographics-summary
Feature: Permissions for summarizing the demographics of a patient

  Background:
    Given I have a patient

  Scenario: I cannot view the demographics summary of a patient without logging in
    Given I am not logged in at all
    When I view the demographics summary of the patient
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot view the demographics summary of a patient without permission
    Given I am logged into NBS
    When I view the demographics summary of the patient
    Then I am not allowed due to insufficient permissions
