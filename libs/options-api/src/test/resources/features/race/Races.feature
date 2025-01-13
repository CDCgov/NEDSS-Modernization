@options
Feature: Race Options REST API

  Scenario: I can find all races
    When I request all races
    Then there are 10 options included
    And the 1st option is "American Indian or Alaska Native"
    And the 2nd option is "Asian"
    And the 3rd option is "Black or African American"
    And the 4th option is "Native Hawaiian or Other Pacific Islander"
    And the 5th option is "White"
    And the 6th option is "Other"
    And the 7th option is "Multi-Race"
    And the 8th option is "Refused to answer"
    And the 9th option is "Not Asked"
    And the 10th option is "Unknown"

  Scenario: I can find specific races
    When I am trying to find races that start with "A"
    Then there are options available
    And the option named "American Indian or Alaska Native" is included
    And the option named "Asian" is included

  Scenario: I can find a specific number of races
    When I am trying to find at most 1 races that start with "a"
    Then there are 1 options included

  Scenario: I cannot find specific races that do not exist
    When I am trying to find races that start with "zzzzzzzzz"
    Then there aren't any options available


  Scenario: I can find detailed races for a category
    When I am trying to find Asian race details
    Then there are options available

  Scenario: I can find specific detailed races for a category
    When I am trying to find American Indian or Alaska Native race details that start with "al"
    Then there are 1 options included

  Scenario: I cannot find specific detailed races for a category that do not exist
    When I am trying to find Asian race details that start with "zzzzzzzzz"
    Then there aren't any options available
