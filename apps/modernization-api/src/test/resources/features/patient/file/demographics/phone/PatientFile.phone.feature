@patient-file @patient-phone-demographics
Feature: Viewing the phone demographics of a patient

  Background:
    Given I am logged into NBS
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can view the patient's phone demographics
    Given the patient has the Email Address - Home number of "555-555-5555" as of 11/07/2023
    And the patient has the Fax - Home number of "555-444-4444" as of 11/07/2024
    And the patient has the Phone - Home number of "555-111-1111" as of 11/07/2024
    And the patient has the Email Address - Home email address of "xyz@test.com" as of 11/07/2024
    When I view the patient's phone demographics
    And the patient file phone demographics includes an Email Address - Home number of "555-555-5555" as of 11/07/2023
    And the patient file phone demographics includes a Fax - Home number of "555-444-4444" as of 11/07/2024
    And the patient file phone demographics includes a Phone - Home number of "555-111-1111" as of 11/07/2024
    And the patient file phone demographics includes an Email Address - Home email address of "xyz@test.com" as of 11/07/2024

  Scenario: I can view the patient's phone demographics ordered by as of date with the newer entries being first
    Given the patient has the Email Address - Home number of "555-555-5555" as of 11/07/2023
    And the patient has the Fax - Home number of "555-444-4444" as of 11/07/2019
    And the patient has the Phone - Home number of "555-111-1111" as of 11/07/2024
    And the patient has the Email Address - Home email address of "xyz@test.com" as of 11/07/2024
    When I view the patient's phone demographics
    Then the 1st phones demographics on the patient file includes an Email Address - Home email address of "xyz@test.com" as of 11/07/2024
    And the 2nd phones demographics on the patient file includes a Phone - Home number of "555-111-1111" as of 11/07/2024
    And the 3rd phones demographics on the patient file includes an Email Address - Home number of "555-555-5555" as of 11/07/2023
    And the 4th phones demographics on the patient file includes a Fax - Home number of "555-444-4444" as of 11/07/2019

  Scenario: No phone demographics are return when a patient does not have any Phone demographics
    When I view the patient's phone demographics
    Then no values are returned
