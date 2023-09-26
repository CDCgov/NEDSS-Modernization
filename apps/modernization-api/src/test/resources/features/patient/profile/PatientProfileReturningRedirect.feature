@patient-profile-returning-redirect @web-interaction
Feature: NBS Classic Patient Profile returns to modernized Patient Profile

  Scenario: A user in NBS Classic is returning to the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I have a patient
    When Returning to a Patient Profile
    Then I am redirected to the Modernized Patient Profile
    And the user Id is present in the redirect

  Scenario: A user in NBS Classic is returning to the Patient Profile without an active session
    Given I have a patient
    When Returning to a Patient Profile
    Then I am redirected to the timeout page

  Scenario: A user in NBS Classic is returning to the Patient Profile Summary tab
    Given I am logged into NBS and a security log entry exists
    And I have a patient
    When Returning to a Patient Profile "Summary" tab
    Then I am redirected to the Modernized Patient Profile
    And the user Id is present in the redirect

  Scenario: A user in NBS Classic is returning to the Patient Profile Events tab
    Given I am logged into NBS and a security log entry exists
    And I have a patient
    When Returning to a Patient Profile "Events" tab
    Then I am redirected to the Modernized Patient Profile
    And the user Id is present in the redirect

  Scenario: A user in NBS Classic is viewing a Patient File from an Investigation
    Given I am logged into NBS and a security log entry exists
    And I have a patient
    And the patient is a subject of an investigation
    When Navigating to a Patient Profile from an "investigation"
    Then I am redirected to the Modernized Patient Profile