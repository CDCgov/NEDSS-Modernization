@request
Feature: Person Names Options REST API

  Background:
    Given there is a "Boba" "Fett" person name
    And there is a "Jyn" "Erso" person name
    And there is a "Cassian" "Andor" person name
    And there is a "Baze" "Malbus" person name
    And there is a "Chirrut" "Imwe" person name

  Scenario: I can find all person names
    When I am retrieving all the person names
    Then there are 10 options included
    And the option named "Jyn Erso" is included
    And the option named "Darth Vader" is not included