@request
Feature: Person Names Options REST API

  # No setup here, use default users instead

  Scenario: I can find all person names
    When I am retrieving all the person names
    Then there are 5 options included
    And the option named "Dekalb LocalUser" is included
    And the option named "Fulton LocalUser" is included
    And the option named "DIS LocalUser" is included
    And the option named "PKS PKS" is included
    And the option named "Supervisor StateUser" is included

  Scenario: I can find all person names without program areas specified
    When I am retrieving all the person names
    Then there are 5 options included
    And the option named "Dekalb LocalUser" is included
    And the option named "Fulton LocalUser" is included
    And the option named "DIS LocalUser" is included
    And the option named "PKS PKS" is included
    And the option named "Supervisor StateUser" is included
