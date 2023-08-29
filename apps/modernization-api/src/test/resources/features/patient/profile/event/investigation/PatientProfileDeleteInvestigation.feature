@patient-profile-delete-investigation
Feature: Patient Profile Investigation Removal

  Background:
    Given I have a patient

  Scenario: An investigation is deleted from Classic NBS
    Given I am logged into NBS and a security log entry exists
    When an investigation is deleted from Classic NBS
    Then the investigation delete is submitted to Classic NBS
    Then I am redirected to the Modernized Patient Profile

  Scenario: An existing legacy investigation is deleted from Classic NBS
    Given I am logged into NBS and a security log entry exists
    When a legacy investigation is deleted from Classic NBS
    Then the legacy investigation delete is submitted to Classic NBS
    Then I am redirected to the Modernized Patient Profile

  Scenario: A newly created legacy investigation is deleted from Classic NBS
    Given I am logged into NBS and a security log entry exists
    When a newly created legacy investigation is deleted from Classic NBS
    Then the legacy investigation delete is submitted to Classic NBS
    Then I am redirected to the Modernized Patient Profile
