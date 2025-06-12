@patient-file @patient-race-demographics
Feature: Permissions for viewing the race demographics of a patient

  Background:
    Given I have a patient

  Scenario: I cannot view a Patient's race demographics without logging in
    Given I am not logged in at all
    When I view the patient's race demographics
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot view a Patient's race demographics without  permission
    Given I am logged into NBS
    When I view the patient's race demographics
    Then I am not allowed due to insufficient permissions
