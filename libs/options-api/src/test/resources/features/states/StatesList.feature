@states @options
Feature: List States REST API

  Scenario: I can fetch all the states sorted
    When I am retrieving all the states
    Then there are options available
    And the option named "Missouri" is included
    And there are 59 options included
    And the 1st option is "Alabama"
    And the 59th option is "US Virgin Islands"
