@find_users
Feature: Find users

  Background: 
    Given A user exists
    And A clerical permission set exists
    And The "clerical" user has the "clerical" permission set for the "STD" program area

  Scenario: I can find users in my program area
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When I search for users
    Then The clerical user is returned

  Scenario: I can not find users in other program areas
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "ARBO"
    When I search for users
    Then The clerical user is not returned
