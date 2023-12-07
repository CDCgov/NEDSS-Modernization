@page @add_question_to_page
Feature: I can add a question to a page

  Background: Pages and Questions exist
    Given I have a page
    And the page has a tab
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And A text question exists
    And I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can add a question to a page
    When I add a question to a page
    Then the question is added to the page at order number 5

