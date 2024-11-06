@patient-profile-returning-redirect
Feature: NBS Classic Patient Profile returns to modernized Patient Profile

  Scenario: A user in NBS Classic is returning to the Patient Profile without an active session
    Given I have a patient
    When Returning to a Patient Profile
    Then I am redirected to the timeout page

