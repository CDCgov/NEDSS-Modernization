@patient-file @patient-address-demographics
Feature: Permissions for viewing the address demographics of a patient

  Background:
    Given I have a patient

  Scenario: I cannot view a Patient's address demographics without logging in
    Given I am not logged in at all
    When I view the patient's address demographics
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot view a Patient's address demographics without  permission
    Given I am logged into NBS
    When I view the patient's address demographics
    Then I am not allowed due to insufficient permissions
