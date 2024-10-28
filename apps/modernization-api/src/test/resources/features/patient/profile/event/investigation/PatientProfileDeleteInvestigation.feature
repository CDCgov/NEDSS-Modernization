@patient-profile-delete-investigation
Feature: Patient Profile Investigation Removal

  Background:
    Given I have a patient
    And I am logged into NBS and a security log entry exists

  Scenario: An investigation is deleted from Classic NBS

    When an investigation is deleted from Classic NBS
    Then the investigation delete is submitted to NBS6
    And I am redirected to the Modernized Patient Profile

  Scenario: An investigation with an associated Lab Report is deleted from NBS6
    When an investigation with an associated lab report is deleted from NBS6
    Then the investigation delete is submitted to NBS6
    And I am returned to the investigation in NBS6

  Scenario: An existing legacy investigation is deleted from NBS6
    When a legacy investigation is deleted from NBS6
    Then the legacy investigation delete is submitted to NBS6
    And I am redirected to the Modernized Patient Profile

  Scenario: An existing legacy investigation with an associated Lab Report is deleted from NBS6
    When a legacy investigation with an associated lab report is deleted from NBS6
    Then the legacy investigation delete is submitted to NBS6
    And I am returned to the investigation in NBS6

  Scenario: A newly created legacy investigation is deleted from NBS6
    When a newly created legacy investigation is deleted from NBS6
    Then the legacy investigation delete is submitted to NBS6
    And I am redirected to the Modernized Patient Profile
