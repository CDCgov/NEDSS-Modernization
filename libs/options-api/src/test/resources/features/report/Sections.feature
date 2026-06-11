@request
Feature: Report Sections Options REST API

  Scenario: I can find all report sections
    When I am retrieving all the report sections
    Then there are 3 options included
    And the option named "Default Report Section" is included
    And the option named "Fun reports" is not included
