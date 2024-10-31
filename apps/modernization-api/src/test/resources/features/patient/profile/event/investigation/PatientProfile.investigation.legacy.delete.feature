@patient-profile-delete-investigation
Feature: Patient Profile Investigation Removal

  Background:
    Given I have a patient
    And I am logged into NBS and a security log entry exists

  Scenario: A legacy investigation is deleted from Classic NBS
    Given I view a legacy investigation from the patient profile
    And the viewed legacy investigation has no associated events
    When I delete the legacy investigation from NBS6
    Then the investigation delete is submitted to NBS6
    And I am redirected to the Modernized Patient Profile

  Scenario: A legacy investigation with an associated Lab Report is deleted from Classic NBS
    Given I view a legacy investigation from the patient profile
    And the viewed legacy investigation has an associated Lab Report
    When I delete the legacy investigation from NBS6
    Then the investigation delete is submitted to NBS6
    And I am returned to the investigation in NBS6

  Scenario: An open legacy investigation is deleted from Classic NBS
    Given I view an open legacy investigation from the patient profile
    And the viewed legacy investigation has no associated events
    When I delete the legacy investigation from NBS6
    Then the investigation delete is submitted to NBS6
    And I am redirected to the Modernized Patient Profile

  Scenario: A legacy investigation navigated to from a queue is deleted from Classic NBS
    Given I view a legacy investigation from a queue
    And the viewed legacy investigation has no associated events
    When I delete the legacy investigation from NBS6
    Then the investigation delete is submitted to NBS6
    And I am redirected to the Modernized Patient Profile
