@validate_page_question_data_mart
Feature: I can validate a Data Mart Column Name for a question on a page

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page
    And the page has a tab
    And the page has a section in the 1st tab
    And the page has a sub-section in the 1st section
    And A text question exists
    And I add a question to a page

  Scenario: Validation passes for a valid Data Mart Column Name
    Given I send a request to validate a Data Mart Column Name for a page
    Then The validation "passes"

  Scenario: Validation fails for an invalid Data Mart Column Name
    Given I send a request to validate a Data Mart Column Name that is already in use for a page
    Then The validation "fails"
