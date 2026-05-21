@request
Feature: Person Names Options REST API

  # No setup here, use default users instead

  Scenario: I can find all person names
    When I am retrieving all the person names
    Then there are 5 options included
    And there are options available
    And the option named "Dekalb LocalUser" is included
    And the option named "Fulton LocalUser" is included
    And the option named "DIS LocalUser" is included
    And the option named "PKS PKS" is included
    And the option named "Supervisor StateUser" is included
