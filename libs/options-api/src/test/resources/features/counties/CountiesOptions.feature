@county @options
Feature: County Options REST API

  Background:
    Given there is a "rubaboo" county
    And there is a "recense" county
    And there is a "philter" county
    And there is a "regrate" county
    And there is a "silesia" county
    And there is a "regulus" county
    And there is a "exergue" county
    And there is a "regreet" county
    And there is a "rhombos" county
    And there is a "wive" county
    And there is a "waveson" county
    And there is a "withe" county
    And there is a "wasm" county
    And there is a "wanion" county
    And there is a "washery" county
    And there is a "waterman" county
    And there is a "waulk" county
    And there is a "wheelhouse" county

  Scenario: I can find specific counties
    When I am trying to find counties that start with "reg"
    Then there are options available
    And the option named "regrate" is included
    And the option named "regulus" is included
    And the option named "regreet" is not included

  Scenario: I can find a specific number of counties
    When I am trying to find at most 4 counties that start with "w"
    Then there are 4 options included

  Scenario: I cannot find specific concepts that do not exist
    When I am trying to find counties that start with "zzzzzzzzz"
    Then there aren't any options available
