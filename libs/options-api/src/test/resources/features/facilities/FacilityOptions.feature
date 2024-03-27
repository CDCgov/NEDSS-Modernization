@facilities
Feature: Facility Options REST API

  Background:
    Given there is a facility for "afacility"
    And there is a facility for "abfacility"
    And there is a facility for "the abfacility"
    And there is a facility for "bfacility"
    And there is a facility for "cfacility"
    And there is a facility for "tafacility"
    And there is a facility for "tbfacility"
    And there is a facility for "tcfacility"
    And there is a facility for "tdfacility"

  Scenario: I can find specific facilities
    When I am trying to find facilities that start with "a"
    Then there are options available
    And the option named "afacility" is included
    And the option named "abfacility" is included
    And the option named "the abfacility" is included
    And the option named "bfacility" is not included

  Scenario: I can find a specific number of facilities
    When I am trying to find at most 4 facilities that start with "t"
    Then there are 4 options included

  Scenario: I cannot find specific facilities that do not exist
    When I am trying to find facilities that start with "zzzzzzzzz"
    Then there aren't any options available

  Scenario: Electronic facilities are not available for search
    Given there is a facility for "Cyberdyne Systems" that was added electronically
    When I am trying to find facilities that start with "cyber"
    Then there aren't any options available
