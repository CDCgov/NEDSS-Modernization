@patient-profile-race @patient-profile-race-validation @web-interaction
Feature: Patient Profile Race Validation

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And I can "edit" any "patient"
    And I have a patient

  Scenario: A patient profile should allow at least one entry for a Race Category
    Given the patient has the race category Unknown
    When I check if the patient profile can include the race category Black or African American
    Then I am able to include the race category on the patient profile

  Scenario: A patient profile should not allow more than one entry for a Race Category
    Given the patient has the race category Black or African American
    When I check if the patient profile can include the race category Black or African American
    Then I am not able to include the race category Black or African American on the patient profile
