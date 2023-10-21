@concept @autocomplete
Feature: Concepts Autocomplete REST API

  Background:
    Given the "rubaboo" concept is in the "Specific" value set
    And the "recense" concept is in the "Specific" value set
    And the "philter" concept is in the "Specific" value set
    And the "regrate" concept is in the "Specific" value set
    And the "silesia" concept is in the "Other" value set
    And the "regulus" concept is in the "Specific" value set
    And the "exergue" concept is in the "Specific" value set
    And the "regreet" concept is in the "Other" value set
    And the "rhombos" concept is in the "Specific" value set
    And the "wive" concept is in the "Specific" value set
    And the "waveson" concept is in the "Specific" value set
    And the "withe" concept is in the "Specific" value set
    And the "wasm" concept is in the "Specific" value set
    And the "wanion" concept is in the "Specific" value set
    And the "washery" concept is in the "Other" value set
    And the "waterman" concept is in the "Other" value set
    And the "waulk" concept is in the "Other" value set
    And the "wheelhouse" concept is in the "Other" value set

  Scenario: I can find specific concepts for a value set
    When I am trying to find concepts in the "Specific" value set that start with "reg"
    Then there are 2 concepts returned
    And the response includes the "regrate" concept
    And the response includes the "regulus" concept
    And the response does not include the "regreet" concept

  Scenario: I can find a specific number of concepts for a value set
    When I am trying to find at most 4 concepts in the "Specific" value set that start with "w"
    Then there are 4 concepts returned

  Scenario: I cannot find specific concepts that do not exist
    When I am trying to find concepts in the "Other" value set that start with "z"
    Then the response does not include any concepts
