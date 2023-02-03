@ethnicity_search
Feature: I can search for ethnicities

  Scenario: I search for ethnicities
    Given Ethnicities exist
    When I search for ethnicities
    Then I find ethnicities
