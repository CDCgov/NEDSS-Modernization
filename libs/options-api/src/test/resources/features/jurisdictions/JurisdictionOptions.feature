@jurisdiction @options
Feature: Jurisdiction Options REST API

  Background:
    Given there is a "rubaboo" jurisdiction
    And there is a "recense" jurisdiction
    And there is a "philter" jurisdiction
    And there is a "regrate" jurisdiction
    And there is a "silesia" jurisdiction
    And there is a "regulus" jurisdiction
    And there is a "exergue" jurisdiction
    And there is a "regreet" jurisdiction
    And there is a "rhombos" jurisdiction
    And there is a "wive" jurisdiction
    And there is a "waveson" jurisdiction
    And there is a "withe" jurisdiction
    And there is a "wasm" jurisdiction
    And there is a "wanion" jurisdiction
    And there is a "washery" jurisdiction
    And there is a "waterman" jurisdiction
    And there is a "waulk" jurisdiction
    And there is a "wheelhouse" jurisdiction

  Scenario: I can find specific jurisdictions
    When I am trying to find jurisdictions that start with "reg"
    Then there are options available
    And the option named "regrate" is included
    And the option named "regulus" is included
    And the option named "regreet" is not included

  Scenario: I can find a specific number of jurisdictions
    When I am trying to find at most 4 jurisdictions that start with "w"
    Then there are 4 options included

  Scenario: I cannot find specific concepts that do not exist
    When I am trying to find jurisdictions that start with "zzzzzzzzz"
    Then there aren't any options available
