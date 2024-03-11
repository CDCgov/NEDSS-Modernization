@condition_search_available
Feature: I can search for conditions not associated with a page

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can search for all conditions that are not in use
    Given a condition exists
    And I have a page named "available condition test1"
    And the page is related to the condition
    When i search for all available conditions
    Then the condition is not in the available conditions

  Scenario: I can search for all conditions that are not in use or associated with the given page
    Given a condition exists
    And I have a page named "available condition test"
    And the page is related to the condition
    When i search for all available conditions and those related to a page
    Then the condition is in the available conditions