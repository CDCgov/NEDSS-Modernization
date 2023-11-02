@page-summary-search
Feature: Searching for Filtered Page Summaries

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page named "Other"

  Scenario: I can search for Page Summaries filtered by a name equals
    Given I have a page named "Page One"
    And I have a page named "Page Two"
    And I have a page named "Page Three"
    And I filter page summaries by "name" equals "Page One"
    When I search for page summaries
    Then there is only one page summary found
    And the found page summaries contain a page with the "name" "Page One"

  Scenario: I can search for Page Summaries filtered by a name not equal to
    Given I have a page named "Page One"
    And I have a page named "Page Two"
    And I have a page named "Page Three"
    And I am looking for page summaries that contain "page"
    And I filter page summaries by "name" not equal to "Page One"
    When I search for page summaries
    Then the found page summaries do not contain a page with the "name" "Page One"
    And the found page summaries contain a page with the "name" "Page Two"
    And the found page summaries contain a page with the "name" "Page Three"

  Scenario: I can search for Page Summaries filtered by a name Starts with
    Given I have a page named "Page One"
    And I have a page named "Page Two"
    And I have a page named "Page Three"
    And I filter page summaries by "name" starts with "Page"
    When I search for page summaries
    Then the found page summaries contain a page with the "name" "Page One"
    And the found page summaries contain a page with the "name" "Page Two"
    And the found page summaries contain a page with the "name" "Page Three"
    And the found page summaries do not contain a page with the "name" "Other"

  Scenario: I can search for Page Summaries filtered by a name contains
    Given I have a page named "Page One"
    And I have a page named "Page Two"
    And I have a page named "Page Three"
    And I filter page summaries by "name" contains "ge t"
    When I search for page summaries
    Then the found page summaries contain a page with the "name" "Page Two"
    And the found page summaries contain a page with the "name" "Page Three"
    And the found page summaries do not contain a page with the "name" "Page One"

  Scenario: I can search for Page Summaries filtered by name not equal to multiple values
    Given I have a page named "Page One"
    And I have a page named "Page Two"
    And I have a page named "Page Three"
    And I filter page summaries by "name" not equal to
    | Page two |
    | Page one |
    When I search for page summaries
    Then the found page summaries do not contain a page with the "name" "Page Two"
    And the found page summaries do not contain a page with the "name" "Page One"
    And there are page summaries found

  Scenario: I can search for Page Summaries filtered by name starts with multiple values
    Given I have a page named "Page One"
    And I have a page named "Page Two"
    And I have a page named "Page Three"
    And I have a page named "five"
    And I filter page summaries by "name" starts with
      | pa   |
      | fiv |
    When I search for page summaries
    Then the found page summaries contain a page with the "name" "Page Two"
    And the found page summaries contain a page with the "name" "Page Three"
    And the found page summaries contain a page with the "name" "five"
    And the found page summaries do not contain a page with the "name" "Other"

  Scenario: I can search for Page Summaries filtered by name contains multiple values
    Given I have a page named "Page One"
    And I have a page named "Page Two"
    And I have a page named "Page Three"
    And I filter page summaries by "name" contains
      | two   |
      | three |
    When I search for page summaries
    Then the found page summaries contain a page with the "name" "Page Two"
    And the found page summaries contain a page with the "name" "Page Three"
    And the found page summaries do not contain a page with the "name" "Page One"
