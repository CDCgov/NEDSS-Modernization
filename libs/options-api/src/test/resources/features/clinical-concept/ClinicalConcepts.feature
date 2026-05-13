@request
Feature: Clinical Concept Options REST API

  Scenario: I can find concepts for a value set
    When I request all clinical concepts for the "PHVSFB_ANIMALST" value set
    Then there are 15 options included
    And the option named "goat" is included
    And the option named "1and2" is not included
    And the option named "sheep" is not included

  Scenario: I cannot find concepts that do not exist
    When I request all clinical concepts for the "non-existing" value set
    Then there aren't any options available
