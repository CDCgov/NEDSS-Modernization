@race_search
Feature: I can search for races

  Scenario: I search for races
    Given Races exist
    When I search for races
    Then I find races
