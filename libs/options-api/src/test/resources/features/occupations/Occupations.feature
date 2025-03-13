@occupations @options
Feature: Occupation Options REST API

  Background:
    Given there is a "rubaboo" occupation
    And there is a "recense" occupation
    And there is a "philter" occupation
    And there is a "regrate" occupation
    And there is a "silesia" occupation
    And there is a "regulus" occupation
    And there is a "exergue" occupation
    And there is a "regreet" occupation
    And there is a "rhombos" occupation
    And there is a "wive" occupation
    And there is a "waveson" occupation
    And there is a "withe" occupation
    And there is a "wasm" occupation
    And there is a "wanion" occupation
    And there is a "washery" occupation
    And there is a "waterman" occupation
    And there is a "waulk" occupation
    And there is a "wheelhouse" occupation

  Scenario: I can find all occupations
    When I request all occupations
    Then there are options available

  Scenario: I can find specific occupations
    When I am trying to find occupations that start with "reg"
    Then there are options available
    And the option named "regrate" is included
    And the option named "regulus" is included
    And the option named "regreet" is not included

  Scenario: I can find a specific number of occupations
    When I am trying to find at most 4 occupations that start with "w"
    Then there are 4 options included

  Scenario: I cannot find specific concepts that do not exist
    When I am trying to find occupations that start with "zzzzzzzzz"
    Then there aren't any options available
