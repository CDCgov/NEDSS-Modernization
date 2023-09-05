@find_users
Feature: Find users

  Background:
    Given I am logged into NBS
    And the "clerical" user exists
    And the "clerical" user can "View" any "Patient" for "STD" within all jurisdictions

  Scenario: I can find users in my program area
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    When I search for users
    Then The clerical user is returned

  Scenario: I can not find users in other program areas
    Given I can "Find" any "Patient" for "ARBO" within all jurisdictions
    When I search for users
    Then The clerical user is not returned
