@patient-profile-incoming-redirect
Feature: NBS Classic Patient Profile redirects to modernized Patient Profile

  Scenario: A user in NBS Classic is navigating to a Master Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I have a patient
    When Redirecting a Classic Master Patient Record Profile
    Then I am redirected to the Modernized Patient Profile
    And the user Id is present in the redirect

  Scenario: A user in NBS Classic is navigating to a Master Patient Profile without an active session
    Given I have a patient
    When Redirecting a Classic Master Patient Record Profile
    Then I am redirected to the timeout page

  Scenario: A user in NBS Classic is navigating to a Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I have a patient
    When Redirecting a Classic Revision Patient Profile
    Then I am redirected to the Modernized Patient Profile
    And the user Id is present in the redirect

  Scenario: A user in NBS Classic is navigating to a Patient Profile without an active session
    Given I have a patient
    When Redirecting a Classic Revision Patient Profile
    Then I am redirected to the timeout page
