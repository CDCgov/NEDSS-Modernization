@resultedtests
Feature: Resulted Test Options REST API

  Background:
    Given there is a LIONC resulted test for "prefix1code1a" "name1"
    And there is a local resulted test for "prefix1code1b" "name2"
    And there is a LIONC resulted test for "prefix2code2a" "name3"
    And there is a local resulted test for "prefix2code2b" "name4"
    And there is a LIONC resulted test for "prefix3code2a" "prefix1 name3"
    And there is a local resulted test for "prefix3code2b" "prefix1 name4"

  Scenario: I can find specific resulted tests
    When I am trying to find resulted tests that start with "prefix1"
    Then there are options available
    And the option named "name1" is included
    And the option named "name2" is included
    And the option named "prefix1 name3" is included
    And the option named "prefix1 name4" is included
    And there are 4 options included
