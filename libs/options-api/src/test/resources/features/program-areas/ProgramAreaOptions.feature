@program-area @options
Feature: Program Area Options REST API

  Background:
    Given there is a "rubaboo" program area
    And there is a "recense" program area
    And there is a "philter" program area
    And there is a "regrate" program area
    And there is a "silesia" program area
    And there is a "regulus" program area
    And there is a "exergue" program area
    And there is a "regreet" program area
    And there is a "rhombos" program area
    And there is a "wive" program area
    And there is a "waveson" program area
    And there is a "withe" program area
    And there is a "wasm" program area
    And there is a "wanion" program area
    And there is a "washery" program area
    And there is a "waterman" program area
    And there is a "waulk" program area
    And there is a "wheelhouse" program area

  Scenario: I can find specific program areas
    When I am trying to find program areas that start with "reg"
    Then there are options available
    And the option named "regrate" is included
    And the option named "regulus" is included
    And the option named "regreet" is not included

  Scenario: I can find a specific number of program areas
    When I am trying to find at most 4 program areas that start with "w"
    Then there are 4 options included

  Scenario: I cannot find specific concepts that do not exist
    When I am trying to find program areas that start with "zzzzzzzzz"
    Then there aren't any options available
