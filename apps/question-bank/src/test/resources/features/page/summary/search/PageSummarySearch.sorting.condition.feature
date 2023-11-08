@page-summary-search
Feature: Searching for Sorted Page Summaries

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I am looking for page summaries that contain "sorting"

  Scenario: I can search for Page Summaries sorted by condition ascending
    Given I have a page named "sorting one"
    And the page is tied to the Trichinellosis condition
    And I have a page named "sorting two"
    And the page is tied to the Neurosyphilis condition
    And I have a page named "sorting three"
    And the page is tied to the Crusted Scabies condition
    And I have a page named "sorting four"
    And the page is tied to the Diphtheria condition
    And I am looking for page summaries sorted by "condition" ascending
    When I search for page summaries
    Then the 1st found page summary has the "condition" "Crusted Scabies"
    And the 2nd found page summary has the "condition" "Diphtheria"
    And the 3rd found page summary has the "condition" "Neurosyphilis"
    And the 4th found page summary has the "condition" "Trichinellosis"

  Scenario: I can search for Page Summaries sorted by condition descending
    Given I have a page named "sorting one"
    And the page is tied to the Trichinellosis condition
    And I have a page named "sorting two"
    And the page is tied to the Neurosyphilis condition
    And I have a page named "sorting three"
    And the page is tied to the Crusted Scabies condition
    And I have a page named "sorting four"
    And the page is tied to the Diphtheria condition
    And I am looking for page summaries sorted by "condition" descending
    When I search for page summaries
    Then the 1st found page summary has the "condition" "Trichinellosis"
    And the 2nd found page summary has the "condition" "Neurosyphilis"
    And the 3rd found page summary has the "condition" "Diphtheria"
    And the 4th found page summary has the "condition" "Crusted Scabies"
