@county_search
Feature: I can search for counties

  Scenario: I search for counties
    Given Counties exist
    When I search for counties
    Then I find counties
