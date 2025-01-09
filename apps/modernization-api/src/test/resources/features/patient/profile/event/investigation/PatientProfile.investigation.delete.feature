@patient-profile-delete-investigation
Feature: Patient Profile Investigation Removal

  Background:
    Given I have a patient
    And I am logged into NBS and a security log entry exists

  Scenario: An investigation is deleted from Classic NBS
    Given I view an investigation from the patient profile
    And the viewed investigation has no associated events
    When I delete the investigation from NBS6
    Then the investigation delete is submitted to NBS6
    And I am redirected to the Modernized Patient Profile

  Scenario: An investigation with an associated Lab Report is deleted from Classic NBS
    Given I view an investigation from the patient profile
    And the viewed investigation has an associated Lab Report
    When I delete the investigation from NBS6
    Then the investigation delete is submitted to NBS6
    And I am returned to the investigation in NBS6

  Scenario: An open investigation is deleted from Classic NBS
    Given I view an open investigation from the patient profile
    And the viewed investigation has no associated events
    When I delete the investigation from NBS6
    Then the investigation delete is submitted to NBS6
    And I am redirected to the Modernized Patient Profile

  Scenario: An investigation is deleted from Classic NBS
    Given I view an investigation from a queue
    And the viewed investigation has no associated events
    When I delete the investigation from NBS6
    Then the investigation delete is submitted to NBS6
    And I am redirected to the Modernized Patient Profile
