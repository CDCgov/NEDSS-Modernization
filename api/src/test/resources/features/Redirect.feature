@redirect
Feature: NBS routes are redirected successfully

  Background: I am logged into NBS
    Given I am logged into NBS

  Scenario: Simple search is redirected
    Given I send a request to the NBS simple search
    Then I am redirected to the simple search react page

  Scenario: Simple search parameters are forwarded
    Given I send a search request to the NBS simple search
    Then My search params are passed to the simple search react page

  Scenario: Simple search is redirected
    Given I navigate to the NBS advanced search page
    Then I am redirected to the advanced search react page

  Scenario: I am not logged in
    Given I am not logged into NBS
    And I send a search request to the NBS simple search
    Then I am redirected to the timeout page

  Scenario: The user id is included in the redirect
    Given I send a request to the NBS simple search
    Then the user Id is present in the redirect
