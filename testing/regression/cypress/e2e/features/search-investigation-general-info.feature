
Feature: Investigation Search by general search

  Background:
    Given I am logged in as "superuser" and password ""
    Given I navigate the event investigation

  Scenario: Basic Info - Search by Condition
    When I select a condition for event investigation
    Then I should see Results with the link "Dengue"

  Scenario: Basic Info - Search by Program Area
    When I select a program area for event investigation
    Then I should see Results with the link "Dengue"

  Scenario: Basic Info - Search by Jurisdiction
    When I select a jurisdiction for event investigation
    Then I should see Results with the text "Cobb County"
