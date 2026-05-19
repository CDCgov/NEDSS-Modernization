@request
Feature: Place Options REST API

  Background:
    Given there is a place for "bowling alley"
    And there is a place for "shopping mall"
    And there is a place for "alley"
    And there is a place for "park"
    And there is a place for "vet"

  Scenario: I can find all places
    When I am retrieving all the places
    Then there are 5 options included
    And the option named "vet" is included
    And the option named "1and2" is not included
