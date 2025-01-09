@patient-profile-returning-redirect
Feature: NBS Classic Patient Profile returns to modernized Patient Profile

  Background:
    Given I am logged into NBS and a security log entry exists
    And I have a patient

  Scenario: A user in NBS Classic is returning to the Patient Profile
    When Returning to a Patient Profile
    Then I am redirected to the Modernized Patient Profile
    And the user Id is present in the redirect

  Scenario: A user in NBS Classic is returning to the Patient Profile Summary tab
    When Returning to a Patient Profile Summary tab
    Then I am redirected to the Modernized Patient Profile summary tab
    And the user Id is present in the redirect

  Scenario: A user in NBS Classic is returning to the Patient Profile Events tab
    When Returning to a Patient Profile Events tab
    Then I am redirected to the Modernized Patient Profile events tab
    And the user Id is present in the redirect

  Scenario: A user in NBS Classic is viewing a Patient File from an Investigation
    Given the patient is a subject of an investigation
    When navigating to a Patient Profile from an investigation
    Then I am redirected to the Modernized Patient Profile

  Scenario: A user in NBS6 is viewing a patient from the results of an Event search
    When navigating to a Patient Profile from event search results
    Then I am redirected to the Modernized Patient Profile
