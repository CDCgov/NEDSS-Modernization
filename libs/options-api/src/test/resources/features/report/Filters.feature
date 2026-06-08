@request
Feature: Report Filters Options REST API

  Scenario: I can find all report filters
    When I am retrieving all the report filters
    Then there are 18 options included
    And the option named "Basic Text Filter" is included
    And the option named "Fun filter" is not included
