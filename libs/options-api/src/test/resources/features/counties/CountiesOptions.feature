@county @options
Feature: County Options REST API

  Background:
    Given there is a "rubaboo" county
    And there is a "recense" county for state "NY"
    And there is a "philter" county for state "NY"
    And there is a "regrate" county for state "NY"
    And there is a "silesia" county for state "NY"
    And there is a "regulus" county for state "NY"
    And there is a "exergue" county for state "NY"
    And there is a "regreet" county for state "NY"
    And there is a "rhombos" county for state "NY"
    And there is a "wive" county for state "NY"
    And there is a "waveson" county for state "NY"
    And there is a "withe" county for state "NY"
    And there is a "wasm" county for state "NY"
    And there is a "wanion" county for state "NY"
    And there is a "washery" county for state "NY"
    And there is a "waterman" county for state "NY"
    And there is a "waulk" county for state "NY"
    And there is a "wheelhouse" county for state "NY"

  Scenario: I can find specific counties
    When I am trying to find counties that start with "reg" for state "NY"
    Then there are options available
    And the option named "regrate" is included
    And the option named "regulus" is included
    And the option named "regreet" is not included

  Scenario: I can find a specific number of counties
    When I am trying to find at most 4 counties that start with "w" for state "NY"
    Then there are 4 options included

  Scenario: I cannot find specific counties that do not exist
    When I am trying to find counties that start with "zzzzzzzzz" for state "NY"
    Then there aren't any options available

  Scenario: I cannot find specific counties in the wrong state
    When I am trying to find counties that start with "reg" for state "MO"
    Then there aren't any options available
