@condition_search
Feature: I can search for conditions

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: Search for a condition that is not in use
    Given a condition exists
    When i search for the condition "excluding" in use
    Then the condition is returned


  Scenario: Search for a condition that is in use including in use
    Given a condition exists
    And I have a page named "a condition test"
    And the page is related to the condition
    When i search for the condition "including" in use
    Then the condition is returned
