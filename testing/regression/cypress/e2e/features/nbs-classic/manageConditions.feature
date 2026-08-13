Feature: Classic NBS - User can view and manage data in NBS Conditions

  Background:
    Given I am logged in as secure user and stay on classic

  Scenario: Add new condition
    When Navigate to Condition Library
    And Click on Add new in Condition Library
    And Fill the details to create new condition
    And Click submit button to create condition
    Then I should see a success message saying the condition has been added to the system

  Scenario: Edit condition
    When Navigate to Condition Library
    And Click a condition in Condition Library
    And Click submit button to create condition
    Then I should see a success message saying the condition has been updated in the system
