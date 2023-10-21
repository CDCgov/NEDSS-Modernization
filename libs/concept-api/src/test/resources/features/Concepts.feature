Feature: Concepts

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

  Scenario: I can find concepts for a value set
    When I retrieve all concepts for the "Specific" value set
    Then there are 7 concepts found
    And the "rubaboo" concept is included
    And the "recense" concept is included
    And the "philter" concept is included
    And the "regrate" concept is included
    And the "regulus" concept is included
    And the "exergue" concept is included
    And the "rhombos" concept is included
    And the "silesia" concept is not included
    And the "regreet" concept is not included

  Scenario: I can find specific concepts for a value set
    When I retrieve any concept in the "Specific" value set with a name starting with "reg"
    Then there are 2 concepts found
    And the "regrate" concept is included
    And the "regulus" concept is included
    And the "regreet" concept is not included

