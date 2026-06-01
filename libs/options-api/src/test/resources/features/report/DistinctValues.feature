@request
Feature: Distinct Values Options REST API

  Scenario: I can find distinct values for a column
    When I request all distinct values for the "30431" column
    Then there are 1 options included
    And the option named "Warning" is included
    And the option named "123" is not included

  Scenario: I cannot find values for a non-hardcoded column
    When I request all distinct values for the "30231" column
    Then there aren't any options available

  Scenario: I cannot find values for empty table
    When I request all distinct values for the "104" column
    Then there aren't any options available

  Scenario: I cannot find values for malformed column ids 
    When I request all distinct values for the "not-an-int" column
    Then there aren't any options available

  Scenario: I cannot find values for unknwon column ids 
    When I request all distinct values for the "123" column
    Then there aren't any options available
