Feature: Classic NBS - User can view and manage data in NBS Conditions

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Add new condition
    Then Navigate to Condition Library
    And Click on Add new in Condition Library
    Then Fill the details to create new condition
    Then Click submit button to create condition

  Scenario: Edit condition
    Then Navigate to Condition Library
    And Click a condition in Condition Library
    Then Click submit button to create condition

