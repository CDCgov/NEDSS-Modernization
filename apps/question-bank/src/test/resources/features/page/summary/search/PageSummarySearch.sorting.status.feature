@page-summary-search
Feature: Searching for Sorted Page Summaries

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I am looking for page summaries that contain "sorting"

  Scenario: I can search for Page Summaries sorted by status ascending
    Given I have a page named "sorting one"
    And the page is Published
    And I have a page named "sorting two"
    And the page is Published with draft
    And I have a page named "sorting three"
    And the page is Initial draft
    And I have a page named "sorting four"
    And the page is a Draft
    And I am looking for page summaries sorted by "status" ascending
    When I search for page summaries
    Then the 1st found page summary has the "status" "Initial Draft"
    And the 2nd found page summary has the "status" "Initial Draft"
    And the 3rd found page summary has the "status" "Published with draft"
    And the 4th found page summary has the "status" "Published"

  Scenario: I can search for Page Summaries sorted by status descending
    Given I have a page named "sorting one"
    And the page is Published
    And I have a page named "sorting two"
    And the page is Published with draft
    And I have a page named "sorting three"
    And the page is Initial draft
    And I have a page named "sorting four"
    And the page is a Draft
    And I am looking for page summaries sorted by "status" descending
    When I search for page summaries
    Then the 1st found page summary has the "status" "Published"
    And the 2nd found page summary has the "status" "Published with draft"
    And the 3rd found page summary has the "status" "Initial Draft"
    And the 4th found page summary has the "status" "Initial Draft"
