Feature: Laboratory Report Search by general search

  Background:
    Given I am logged in as "superuser" and password ""
    Given I navigate the event laboratory report

  Scenario: Basic Info - Search by Program Area
    When I select program area for event laboratory report
    Then I should see Results with the link "Lab Report"

  Scenario: Basic Info - Search by Jurisdiction
    When I select a jurisdiction for event laboratory report
    Then I should see Results with the link "Lab Report"
