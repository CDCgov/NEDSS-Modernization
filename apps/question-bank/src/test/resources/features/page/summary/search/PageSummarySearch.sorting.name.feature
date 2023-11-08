@page-summary-search
Feature: Searching for Sorted Page Summaries

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I am looking for page summaries that contain "sorting"

  Scenario: I can search for Page Summaries sorted by name ascending
    Given I have a page
    And the page has a "name" of "X sorting"
    And I have another page
    And the page has a "name" of "C sorting"
    And I have another page
    And the page has a "name" of "55 sorting"
    And I have another page
    And the page has a "name" of "A sorting"
    And I am looking for page summaries sorted by "name" ascending
    When I search for page summaries
    Then the 1st found page summary has the "name" "55 sorting"
    And the 2nd found page summary has the "name" "A sorting"
    And the 3rd found page summary has the "name" "C sorting"
    And the 4th found page summary has the "name" "X sorting"

  Scenario: I can search for Page Summaries sorted by name descending
    Given I have a page
    And the page has a "name" of "X sorting"
    And I have another page
    And the page has a "name" of "C sorting"
    And I have another page
    And the page has a "name" of "55 sorting"
    And I have another page
    And the page has a "name" of "A sorting"
    And I am looking for page summaries sorted by "name" descending
    When I search for page summaries
    Then the 1st found page summary has the "name" "X sorting"
    And the 2nd found page summary has the "name" "C sorting"
    And the 3rd found page summary has the "name" "A sorting"
    And the 4th found page summary has the "name" "55 sorting"
