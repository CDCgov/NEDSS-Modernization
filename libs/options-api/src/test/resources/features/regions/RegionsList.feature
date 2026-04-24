@regions @options
Feature: List Regions REST API

  Scenario: I can fetch all the regions sorted
    When I am retrieving all the regions
    Then there aren't any options available
