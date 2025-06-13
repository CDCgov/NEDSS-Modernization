@patient-file @patient-ethnicity-demographics
Feature: Permissions for viewing the ethnicity demographics of a patient

  Background:
    Given I have a patient

  Scenario: I cannot view a Patient's ethnicity demographics without logging in
    Given I am not logged in at all
    When I view the patient's ethnicity demographics
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot view a Patient's ethnicity demographics without  permission
    Given I am logged into NBS
    When I view the patient's ethnicity demographics
    Then I am not allowed due to insufficient permissions
