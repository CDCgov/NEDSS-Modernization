@page-summary-search @filtering
Feature: Searching for Page Summaries Filtered by Status

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page named "filtering one"
    And the page is Published
    And I have a page named "filtering two"
    And the page is Published with draft
    And I have a page named "filtering three"
    And the page is Initial draft
    And I have a page named "filtering four"
    And the page is a Draft

  Scenario Outline: I can search for Page Summaries filtered by status equals
    Given I filter page summaries by "status" equals "<status>"
    When I search for page summaries
    Then there are page summaries found
    And all found page summaries contain a page with the "status" "<expected>"

    Examples:
      | status               | expected             |
      | Draft                | Initial Draft        |
      | Published            | Published            |
      | Published with draft | Published with draft |

  Scenario Outline: I can search for Page Summaries filtered by status not equal to
    Given I filter page summaries by "status" not equal to "<status>"
    When I search for page summaries
    Then there are page summaries found
    And the found page summaries do not contain a page with the "status" "<expected>"

    Examples:
      | status               | expected             |
      | Draft                | Initial Draft        |
      | Published            | Published            |
      | Published with draft | Published with draft |

