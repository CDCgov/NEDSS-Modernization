@patient-file @patient-administrative
Feature: Permissions for viewing the administrative information of a patient

  Background:
    Given I have a patient

  Scenario: I cannot view a Patient's administrative information without logging in
    Given I am not logged in at all
    When I view the patient's Administrative information
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot view a Patient's administrative information without  permission
    Given I am logged into NBS
    When I view the patient's Administrative information
    Then I am not allowed due to insufficient permissions
