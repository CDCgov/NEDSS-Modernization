@country @options
Feature: Country Options REST API

  Scenario: I can find specific countries
    When I am trying to find countries that start with "un"
    Then there are options available
    And the option named "United States" is included

  Scenario: I can find a specific number of countries
    When I am trying to find at most 2 countries that start with "u"
    Then there are 2 options included

  Scenario: I cannot find specific countries that do not exist
    When I am trying to find countries that start with "zzzzzzzzz"
    Then there aren't any options available

  Scenario: I can list all the countries in order
    When I am retrieving all the countries
    Then there are options available
    And there are 231 options included
    And the 1st option is "Afghanistan"
    And the 231st option is "Zimbabwe"
