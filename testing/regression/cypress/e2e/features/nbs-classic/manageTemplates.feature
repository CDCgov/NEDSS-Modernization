Feature: Classic NBS - User can view and manage data in NBS Templates

  Background:
    Given I am logged in as secure user and stay on classic
    Then Navigate to Template Library

  Scenario: Import template
    And Click on Import in Template Library
    Then Click on Choose File in Template Library

  Scenario: Filter results in template library
    And Click filter button in Template Library
    Then Enter filter text in the input
    And Click OK button to filter

  Scenario: View rule in template library
    And Click a template in template library
    Then Click View Rules button in Template view
    And Verify rules listed in the results page
