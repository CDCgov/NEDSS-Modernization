@request
Feature: Distinct Values Options REST API

  Scenario: I can find distinct values for a column
    When I request all distinct values for the "30421" column
    Then there are 138 options included
    And the option named "10000000" is included
    And the option named "10000001" is included
    And the option named "123" is not included

  Scenario: I cannot find values for empty table
    When I request all distinct values for the "30317" column
    Then there aren't any options available

  Scenario: I cannot find values for malformed column ids 
    When I request all distinct values for the "not-an-int" column
    Then there aren't any options available

  Scenario: I cannot find values for unknwon column ids 
    When I request all distinct values for the "123" column
    Then there aren't any options available
