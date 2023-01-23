@outbreak_search
Feature: I can search for outbreaks

  Scenario: I search for outbreaks
    Given Outbreaks exist
    When I search for outbreaks
    Then I find outbreaks
