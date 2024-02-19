@concept_list
Feature: I can list the concepts associated with a value set

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can list all concepts for a value set
    Given I have a valueset named "test value set"
    And the value set has a concept named "first concept" with value "val1"
    And the value set has a concept named "second concept" with value "val2"
    When I list concepts for the "test value set" value set
    Then I find these concepts:
      | name           | value |
      | first concept  | val1  |
      | second concept | val2  |
