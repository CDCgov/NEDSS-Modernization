Feature: User can view and manage data in classic NBS Conditions

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Add new condition
    Then Navigate to Condition Library
    And Click on Add new in Condition Library
    Then Fill the details to create new condition
    Then Click submit button to create condition

