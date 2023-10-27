@page-summary-search
Feature: Searching for Sorted Page Summaries

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can search for Page Summaries sorted by name ascending
    Given I have a page
    And the page has a "name" of "X sorted"
    And I have another page
    And the page has a "name" of "C sorted"
    And I have another page
    And the page has a "name" of "55 sorted"
    And I have another page
    And the page has a "name" of "A sorted"
    And I am looking for page summaries that contain "sorted"
    And I am looking for page summaries sorted by "name" ascending
    When I search for page summaries
    Then the 1st found page summary has the "name" "55 sorted"
    And the 2nd found page summary has the "name" "A sorted"
    And the 3rd found page summary has the "name" "C sorted"
    And the 4th found page summary has the "name" "X sorted"

  Scenario: I can search for Page Summaries sorted by name descending
    Given I have a page
    And the page has a "name" of "X sorted"
    And I have another page
    And the page has a "name" of "C sorted"
    And I have another page
    And the page has a "name" of "55 sorted"
    And I have another page
    And the page has a "name" of "A sorted"
    And I am looking for page summaries that contain "sorted"
    And I am looking for page summaries sorted by "name" descending
    When I search for page summaries
    Then the 1st found page summary has the "name" "X sorted"
    And the 2nd found page summary has the "name" "C sorted"
    And the 3rd found page summary has the "name" "A sorted"
    And the 4th found page summary has the "name" "55 sorted"

  Scenario: I can search for Page Summaries sorted by event type ascending
    Given I have a page named "sorting one"
    And the page has a "event type" of "vaccination"
    And I have a page named "sorting two"
    And the page has a "event type" of "lab susceptibility"
    And I have a page named "sorting three"
    And the page has a "event type" of "contact"
    And I have a page named "sorting four"
    And the page has a "event type" of "interview"
    And I am looking for page summaries that contain "sorted"
    And I am looking for page summaries sorted by "event type" ascending
    When I search for page summaries
    Then the 1st found page summary has the "event type" "contact"
    And the 2nd found page summary has the "event type" "interview"
    And the 3rd found page summary has the "event type" "lab susceptibility"
    And the 4th found page summary has the "event type" "vaccination"
