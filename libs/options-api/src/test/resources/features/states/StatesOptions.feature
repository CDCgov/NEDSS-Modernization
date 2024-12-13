@state @options
Feature: State Options REST API

  Scenario: I can find specific states
    When I am trying to find states that start with "mo"
    Then there are options available
    And the option named "Montana" is included

  Scenario: I can find a specific number of states
    When I am trying to find at most 2 states that start with "w"
    Then there are 2 options included

  Scenario: I cannot find specific states that do not exist
    When I am trying to find states that start with "zzzzzzzzz"
    Then there aren't any options available
