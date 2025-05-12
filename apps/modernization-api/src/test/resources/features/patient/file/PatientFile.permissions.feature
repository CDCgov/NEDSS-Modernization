@patient-file
Feature: Permissions for requesting a Patient File

  Background:
    Given I have a patient

  Scenario: I cannot view the a Patient file without logging in
    Given I am not logged in at all
    When I view the demographics summary of the patient
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot view a Patient file without permission
    Given I am logged into NBS
    When I view the demographics summary of the patient
    Then I am not allowed due to insufficient permissions
