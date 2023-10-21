@concept @request
Feature: Concepts REST API

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
    When I request all concepts for the "Specific" value set
    Then there are 7 concepts returned
    And the response includes the "rubaboo" concept
    And the response includes the "recense" concept
    And the response includes the "philter" concept
    And the response includes the "regrate" concept
    And the response includes the "regulus" concept
    And the response includes the "exergue" concept
    And the response includes the "rhombos" concept
    And the response does not include the "silesia" concept
    And the response does not include the "regreet" concept

  Scenario: I cannot find concepts that do not exist
    When I request all concepts for the "non-existing" value set
    Then the response does not include any concepts
