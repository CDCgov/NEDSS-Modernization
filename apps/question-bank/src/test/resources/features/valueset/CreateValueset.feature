@create_valueset
Feature: Create Valueset

  Background:
    Given I am logged in
    And I can "LDFADMINISTRATION" any "SYSTEM"

  Scenario: I can create a valueset
    When I send a request to create a valueset with type "<type>", code "<code>", name "<name>", and description "<description>"
    Then the valueset is created with type "<type>", code "<code>", name "<name>", and description "<description>"
    Examples:
      | type | code       | name       | description       |
      | PHIN | testCode_1 | testName_1 | test description! |