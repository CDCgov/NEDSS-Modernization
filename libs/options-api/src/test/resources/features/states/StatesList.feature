@states @options
Feature: List States REST API

  Scenario: I can fetch all the states
    When I am retrieving all the states
    Then there are options available
    And the option named "Montana" is included
    And there are 59 options included
