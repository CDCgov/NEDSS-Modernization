@redirect
Feature: NBS routes are redirected successfully

  Background: I am logged into NBS with a security log event
    Given I am logged into NBS and a security log entry exists

  Scenario: Simple search is redirected
    When I send a request to the NBS simple search
    Then I am redirected to the simple search react page

  Scenario: Simple search parameters are forwarded
    When I send a search request to the NBS simple search
    Then My search params are passed to the simple search react page

  Scenario: Advanced search is redirected
    When I navigate to the NBS advanced search page
    Then I am redirected to the advanced search react page

  Scenario: I do not have a session id
    Given A sessionId is not set
    And I send a search request to the NBS simple search
    Then I am redirected to the timeout page

  Scenario: The user id is included in the redirect
    When I send a request to the NBS simple search
    Then the user Id is present in the redirect

  Scenario: I can perform a search with redirected search parameters
    When I send a search request to the NBS simple search
    And I submit the search filter to the patient search API
    Then The search is executed successfully
