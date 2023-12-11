@condition @options
Feature: Condition Options REST API

  Background:
    Given there is a "rubaboo" condition
    And there is a "recense" condition
    And there is a "philter" condition
    And there is a "regrate" condition
    And there is a "silesia" condition
    And there is a "regulus" condition
    And there is a "exergue" condition
    And there is a "regreet" condition
    And there is a "rhombos" condition
    And there is a "wive" condition
    And there is a "waveson" condition
    And there is a "withe" condition
    And there is a "wasm" condition
    And there is a "wanion" condition
    And there is a "washery" condition
    And there is a "waterman" condition
    And there is a "waulk" condition
    And there is a "wheelhouse" condition

  Scenario: I can find all conditions
    When I request all conditions
    Then there are options available

  Scenario: I can find specific conditions
    When I am trying to find conditions that start with "reg"
    Then there are options available
    And the option named "regrate" is included
    And the option named "regulus" is included
    And the option named "regreet" is not included

  Scenario: I can find a specific number of conditions
    When I am trying to find at most 4 conditions that start with "w"
    Then there are 4 options included

  Scenario: I cannot find specific concepts that do not exist
    When I am trying to find conditions that start with "zzzzzzzzz"
    Then there aren't any options available
