@patient-file @patient-general-demographics
Feature: Permissions for viewing the general demographics of a patient

  Background:
    Given I have a patient

  Scenario: I cannot view a Patient's general demographics without logging in
    Given I am not logged in at all
    When I view the patient's general information demographics
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot view a Patient's general demographics without  permission
    Given I am logged into NBS
    When I view the patient's general information demographics
    Then I am not allowed due to insufficient permissions
