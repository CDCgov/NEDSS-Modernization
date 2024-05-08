
Feature: Investigation Search by general search

  Background:
    Given I am logged in as "superuser" and password ""

  Scenario: Basic Info - Search by Condition
    When I navigate the event investigation
    When I select a condition for event investigation
    Then I should see Results with the Condition "Dengue"

  Scenario: Basic Info - Search by Program Area
    When I navigate the event investigation
    When I select a program area for event investigation
    Then I should see No Results found text