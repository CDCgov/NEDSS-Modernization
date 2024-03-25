@target_question_finder
Feature: Target Question Finder

  Background: 
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page
    And the page has a tab named "tab"
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And the page has a question named "testquestion" in the 1st sub-section
    And the page has a date question named "datetest" in the 1st sub-section

  Scenario: I can find target questions
    Given I create a target question request with "Enable"
    When I send a target question request
    Then Target questions are returned

  Scenario: I can find date target questions
    Given I create a target question request with "Date"
    When I send a target question request
    Then Target questions are returned

  Scenario: I can find require if target questions
    Given I create a target question request with "Require"
    When I send a target question request
    Then Target questions are returned