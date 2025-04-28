Feature: Classic NBS - Modern Search - User can search and filter for patients

  Background:
    Given I am logged in as secure user and stay on classic
    Given I am on the modernized Patient Search page

  Scenario: Search patient by exact Patient ID
    Then I feel input id "name.last" with text "rat"
    When I select input id "name.lastOperator" with type "Contains"
    And Click on Search in Patient Search pane
    Then I click search filter result icon
    Then I search filter column "text-filter-id" with "78917"
    Then I verify unique search row contains "78917"
    Then Clear search filter "text-filter-id"
    Then Verify top Search result is not "78917"
