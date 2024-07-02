@find_users
Feature: Find users

  Background:
    Given I am logged into NBS
    And the user "Another" "User" exists as "another-user"
    And the "another-user" user can "View" any "Patient" for "STD" within all jurisdictions

  Scenario: I can find users in my program area
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    When I retrieve the user list
    Then The "another-user" user is returned

  Scenario: I can not find users in other program areas
    Given I can "Find" any "Patient" for "ARBO" within all jurisdictions
    When I retrieve the user list
    Then The "another-user" user is not returned
