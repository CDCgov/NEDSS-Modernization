@language @options
Feature: Primary Language Options REST API

  Background:
    Given there is a "rubaboo" primary language
    And there is a "recense" primary language
    And there is a "philter" primary language
    And there is a "regrate" primary language
    And there is a "silesia" primary language
    And there is a "regulus" primary language
    And there is a "exergue" primary language
    And there is a "regreet" primary language
    And there is a "rhombos" primary language
    And there is a "wive" primary language
    And there is a "waveson" primary language
    And there is a "withe" primary language
    And there is a "wasm" primary language
    And there is a "wanion" primary language
    And there is a "washery" primary language
    And there is a "waterman" primary language
    And there is a "waulk" primary language
    And there is a "wheelhouse" primary language

  Scenario: I can find all primary languages
    When I request all primary languages
    Then there are options available

  Scenario: I can find specific primary languages
    When I am trying to find primary languages that start with "reg"
    Then there are options available
    And the option named "regrate" is included
    And the option named "regulus" is included
    And the option named "regreet" is not included

  Scenario: I can find a specific number of primary languages
    When I am trying to find at most 4 primary languages that start with "w"
    Then there are 4 options included

  Scenario: I cannot find specific concepts that do not exist
    When I am trying to find primary languages that start with "zzzzzzzzz"
    Then there aren't any options available
