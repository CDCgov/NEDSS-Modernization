@identification_type_search
Feature: I can search for identification types

  Scenario: I search for identification types
    Given Identification types exist
    When I search for identification types
    Then I find identification types
