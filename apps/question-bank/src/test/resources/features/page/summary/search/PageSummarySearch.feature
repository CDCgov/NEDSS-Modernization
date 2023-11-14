@page-summary-search
Feature: Searching for Page Summaries

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page

  Scenario: I can search for Page Summaries by name
    Given the page has a "name" of "Needle In a Haystack"
    And I am looking for page summaries that contain "stack"
    When I search for page summaries
    Then there is only one page summary found
    And the found page summaries contain a page with the "name" "Needle In a Haystack"

  Scenario: I can search for Page Summaries by condition
    Given the page is tied to the Crusted Scabies condition
    And I am looking for page summaries that contain "scabies"
    When I search for page summaries
    Then the found page summaries contain a page with the "condition" "Crusted Scabies"

  Scenario: Templates are not included when searching Page Summaries
    Given the page has a "name" of "Needle In a Haystack"
    And the page is a Template
    And I am looking for page summaries that contain "stack"
    When I search for page summaries
    Then there are no page summaries found

