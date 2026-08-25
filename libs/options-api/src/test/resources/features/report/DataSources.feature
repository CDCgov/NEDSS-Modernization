@request
Feature: Report Data Sources Options REST API

  Scenario: I can find all report data sources
    When I am retrieving all the report data sources
    Then there are 39 options included
    And the option named "nbs_ods.PHCDemographic" is included
    And the option named "nbs_rdb.made_up" is not included
