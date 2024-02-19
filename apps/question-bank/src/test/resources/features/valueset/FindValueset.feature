@valueset_find
Feature: I can get a value set

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I send a request to create a valueset with type "PHIN", code "test_find_value_set", name "findTestName", and description "description"

  Scenario: I retrieve a value set
    When I find the value set "test_find_value_set"
    Then the returned value set has type "PHIN", code "test_find_value_set", name "findTestName", and description "description"