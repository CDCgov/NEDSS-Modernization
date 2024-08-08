@advanced-search-redirect
Feature: Navigating to Advanced Search from the NBS home page

  Background:
    Given I am logged in

  Scenario: Advanced search is redirected
    When I click Advanced Search on the NBS Home page
    Then I am redirected to Advanced Search
    And the user Id is present in the redirect
    And the token is present in the redirect

  Scenario: I do not have a session id
    Given I am not logged in
    And I click Advanced Search on the NBS Home page
    Then I am not allowed due to insufficient permissions
