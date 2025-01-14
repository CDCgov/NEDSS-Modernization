@advanced-search-redirect
Feature: Navigating to Search from the NBS home page

  Background:
    Given I am logged in

  Scenario: NBS home page Advanced Search is redirected
    When I click Advanced Search on the NBS Home page
    Then I am redirected to Advanced Search
    And the user Id is present in the redirect
    And the token is present in the redirect

  Scenario: NBS home page Advanced Search is not redirected a user is not logged in
    Given I am not logged in
    And I click Advanced Search on the NBS Home page
    Then I am not allowed due to insufficient permissions
