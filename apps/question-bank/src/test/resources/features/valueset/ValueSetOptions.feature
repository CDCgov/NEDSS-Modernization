@valueset-options
Feature: I can list all available value sets as options

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"


  Scenario: Active valuesets are in the listed options
    Given I have a valueset named "active"
    When I list all valueset options
    Then "active" is in the valueset options

  Scenario: Inactive valuesets are not in the listed options
    Given I have a valueset named "deleted"
    And the valueset is "Inactive"
    When I list all valueset options
    Then "deleted" is not in the valueset options
