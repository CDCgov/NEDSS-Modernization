@request
Feature: Report Data Source Columns Options REST API

  Scenario: I can find all data source columns
    When I am retrieving all the report columns for data source "1"
    Then there are 30 options included
    And the option named "BIRTH_DATE" is included
    And the option named "FUN_COLUMN" is not included
