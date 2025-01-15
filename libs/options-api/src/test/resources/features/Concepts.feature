@concept @request
Feature: Concept Options REST API

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
    And the "Augustine" is a race concept
    And the "Cabazon" is a race concept
    And the "Morongo" is a race concept

  Scenario: I can find concepts for a value set
    When I request all concepts for the "Specific" value set
    Then there are 7 concepts included
    And the concept named "rubaboo" is included
    And the concept named "recense" is included
    And the concept named "philter" is included
    And the concept named "regrate" is included
    And the concept named "regulus" is included
    And the concept named "exergue" is included
    And the concept named "rhombos" is included
    And the concept named "silesia" is not included
    And the concept named "regreet" is not included
    
  Scenario: I cannot find concepts that do not exist
    When I request all concepts for the "non-existing" value set
    Then there aren't any concepts available
