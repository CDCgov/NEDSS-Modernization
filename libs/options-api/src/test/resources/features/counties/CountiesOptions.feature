@county @options
Feature: County Options REST API

  Background:
    Given there is a "rubaboo" county for New York state
    And there is a "recense" county for New York state
    And there is a "philter" county for New York state
    And there is a "regrate" county for New York state
    And there is a "silesia" county for New York state
    And there is a "regulus" county for New York state
    And there is a "exergue" county for New York state
    And there is a "regreet" county for New York state
    And there is a "rhombos" county for New York state
    And there is a "wive" county for New York state
    And there is a "waveson" county for New York state
    And there is a "withe" county for New York state
    And there is a "wasm" county for New York state
    And there is a "wanion" county for New York state
    And there is a "washery" county for New York state
    And there is a "waterman" county for New York state
    And there is a "waulk" county for New York state
    And there is a "wheelhouse" county for New York state
    And there is a "Mist County" county for Minnesota state

  Scenario: I can find counties for a state
    When I am trying to find counties for Minnesota state
    Then there are options available
    And the option named "Mist County" is included

  Scenario: I can find specific counties
    When I am trying to find counties that start with "reg" for New York state
    Then there are options available
    And the option named "regrate" is included
    And the option named "regulus" is included
    And the option named "regreet" is not included

  Scenario: I can find a specific number of counties
    When I am trying to find at most 4 counties that start with "w" for New York state
    Then there are 4 options included

  Scenario: I cannot find specific counties that do not exist
    When I am trying to find counties that start with "zzzzzzzzz" for New York state
    Then there aren't any options available

  Scenario: I cannot find specific counties in the wrong state
    When I am trying to find counties that start with "reg" for Missouri state
    Then there aren't any options available

