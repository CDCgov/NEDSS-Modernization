@authentication
Feature: Authentication

  Scenario: Authenticated User
    Given A user exists
    And I have authenticated as a user
    When I try to access the patient search API
    Then I get a valid response

  Scenario: Unauthenticated User
    Given I have not authenticated as a user
    When I try to access the patient search API
    Then I get a 401 unauthorized response
