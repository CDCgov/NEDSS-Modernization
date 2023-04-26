@patient-profile-incoming-redirect
Feature: NBS Classic Patient Profile redirects to modernized Patient Profile

  Scenario: Classic Master Patient Record Profile is redirected
    Given I am logged into NBS and a security log entry exists
    When Redirecting a Classic Master Patient Record Profile
    Then I am redirected to the Modernized Patient Profile
    And the user Id is present in the redirect

  Scenario: Classic Master Patient Record Profile is redirected without an active session
    When Redirecting a Classic Master Patient Record Profile
    Then I am redirected to the timeout page

  Scenario: Classic Revision Patient Profile is redirected
    Given I am logged into NBS and a security log entry exists
    When Redirecting a Classic Revision Patient Profile
    Then I am redirected to the Modernized Patient Profile
    And the user Id is present in the redirect

  Scenario: Classic Revision Patient Profile is redirected without an active session
    When Redirecting a Classic Revision Patient Profile
    Then I am redirected to the timeout page
