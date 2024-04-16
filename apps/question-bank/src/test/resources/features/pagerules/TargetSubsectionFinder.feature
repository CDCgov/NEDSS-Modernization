@target_subsection_finder
Feature: Target Subsection Finder

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page
    And the page has a tab named "tab"
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And the page has a question named "testquestion" in the 1st sub-section
    And the page has a date question named "datetest" in the 1st sub-section

  Scenario: I can find target subsections
    Given I create a target subsection request with "1"
    When I send a target subsection request
    Then Target subsections are returned