@redirect
Feature: NBS routes are redirected successfully

  Scenario: Simple search is redirected
    Given I send a request to the NBS simple search
    Then I am redirected to the simple search react page

  Scenario: Simple search parameters are forwarded
    Given I send a search request to the NBS simple search
    Then My search params are passed to the simple search react page

  Scenario: Simple search is redirected
    Given I navigate to the NBS advanced search page
    Then I am redirected to the advanced search react page
