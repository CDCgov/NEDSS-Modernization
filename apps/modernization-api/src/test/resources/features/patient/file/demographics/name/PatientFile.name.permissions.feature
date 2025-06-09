@patient-file @patient-name-demographics
Feature: Permissions for viewing the name demographics of a patient

  Background:
    Given I have a patient

  Scenario: I cannot view a Patient's name demographics without logging in
    Given I am not logged in at all
    When I view the patient's name demographics
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot view a Patient's name demographics without  permission
    Given I am logged into NBS
    When I view the patient's name demographics
    Then I am not allowed due to insufficient permissions
