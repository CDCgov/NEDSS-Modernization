@patient-file @patient-mortality-demographics
Feature: Permissions for viewing the mortality demographics of a patient

  Background:
    Given I have a patient

  Scenario: I cannot view a Patient's mortality demographics without logging in
    Given I am not logged in at all
    When I view the patient's mortality demographics
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot view a Patient's mortality demographics without  permission
    Given I am logged into NBS
    When I view the patient's mortality demographics
    Then I am not allowed due to insufficient permissions
