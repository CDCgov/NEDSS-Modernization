@patient-file @patient-race-demographics
Feature: Patient File Race Validation

  Background:
    Given I am logged in
    And I can "view" any "patient"
    And I can "edit" any "patient"
    And I have a patient

  Scenario: A patient file should allow at least one entry for a Race Category
    Given the patient has the race category Unknown
    When I check if the patient race demographics can include Black or African American
    Then I am able to include the race category as a patient race demographic

  Scenario: A patient file should not allow more than one entry for a Race Category
    Given the patient has the race category Black or African American
    When I check if the patient race demographics can include Black or African American
    Then I am not able to include Black or African American as a patient race demographic
