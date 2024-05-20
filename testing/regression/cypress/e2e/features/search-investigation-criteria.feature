Feature: Investigation Search by criteria

  Background:
    Given I am logged in as "superuser" and password ""
    Given I navigate the event investigation
    Given I click criteria tab

  Scenario: Basic Info - Search by Condition
    When I select a investigation status for event investigation
    Then I should see Results with the text "CLOSED"