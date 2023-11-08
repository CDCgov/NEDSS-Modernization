@page-summary-search @filtering
Feature: Searching for Page Summaries Filtered by Event Type

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I am looking for page summaries that contain "filtering"

  Scenario: I can search for Page Summaries filtered by event type equals
    Given I have a page named "filtering one"
    And the page is for a Vaccination
    And I have a page named "filtering two"
    And the page is for a Lab Susceptibility
    And I have a page named "filtering three"
    And  the page is for a Contact Record
    And I have a page named "filtering four"
    And the page is for an Interview
    And I filter page summaries by "event type" equals "Vaccination"
    When I search for page summaries
    Then there is only one page summary found
    And the found page summaries contain a page with the "event type" "Vaccination"

  Scenario: I can search for Page Summaries filtered by event type not equal to
    Given I have a page named "filtering one"
    And the page is for a Vaccination
    And I have a page named "filtering two"
    And the page is for a Lab Susceptibility
    And I have a page named "filtering three"
    And  the page is for a Contact Record
    And I have a page named "filtering four"
    And the page is for an Interview
    And I filter page summaries by "event type" not equal to "Vaccination"
    When I search for page summaries
    Then there are 3 page summaries found
    And the found page summaries contain a page with the "event type" "Lab Susceptibility"
    And the found page summaries contain a page with the "event type" "Contact Record"
    And the found page summaries contain a page with the "event type" "Interview"

  Scenario: I can search for Page Summaries filtered by event type Starts with
    Given I have a page named "filtering one"
    And the page is for a Vaccination
    And I have a page named "filtering two"
    And the page is for a Lab Susceptibility
    And I have a page named "filtering three"
    And  the page is for a Lab Isolate Tracking
    And I have a page named "filtering four"
    And the page is for an Interview
    And I filter page summaries by "event type" starts with "Lab"
    When I search for page summaries
    Then there are 2 page summaries found
    And the found page summaries contain a page with the "event type" "Lab Susceptibility"
    And the found page summaries contain a page with the "event type" "Lab Isolate Tracking"

  Scenario: I can search for Page Summaries filtered by event type contains
    Given I have a page named "filtering one"
    And the page is for a Vaccination
    And I have a page named "filtering two"
    And the page is for a Lab Susceptibility
    And I have a page named "filtering three"
    And  the page is for a Investigation
    And I have a page named "filtering four"
    And the page is for an Interview
    And I filter page summaries by "event type" contains "ati"
    When I search for page summaries
    Then there are 2 page summaries found
    And the found page summaries contain a page with the "event type" "Vaccination"
    And the found page summaries contain a page with the "event type" "Investigation"

  Scenario: I can search for Page Summaries filtered by event type equal to multiple values
    Given I have a page named "filtering one"
    And the page is for a Vaccination
    And I have a page named "filtering two"
    And the page is for a Lab Susceptibility
    And I have a page named "filtering three"
    And  the page is for a Contact Record
    And I have a page named "filtering four"
    And the page is for an Interview
    And I filter page summaries by "event type" equals
      | Vaccination    |
      | Contact Record |
    When I search for page summaries
    Then there are 2 page summaries found
    And the found page summaries contain a page with the "event type" "Vaccination"
    And the found page summaries contain a page with the "event type" "Contact Record"

  Scenario: I can search for Page Summaries filtered by event type not equal to multiple values
    Given I have a page named "filtering one"
    And the page is for a Vaccination
    And I have a page named "filtering two"
    And the page is for a Lab Susceptibility
    And I have a page named "filtering three"
    And  the page is for a Contact Record
    And I have a page named "filtering four"
    And the page is for an Interview
    And I filter page summaries by "event type" not equal to
      | Vaccination    |
      | Contact Record |
    When I search for page summaries
    Then there are 2 page summaries found
    And the found page summaries contain a page with the "event type" "Lab Susceptibility"
    And the found page summaries contain a page with the "event type" "Interview"

  Scenario: I can search for Page Summaries filtered by name starts with multiple values
    Given I have a page named "filtering one"
    And the page is for a Vaccination
    And I have a page named "filtering two"
    And the page is for a Lab Susceptibility
    And I have a page named "filtering three"
    And  the page is for a Contact Record
    And I have a page named "filtering four"
    And the page is for an Interview
    And I filter page summaries by "event type" starts with
      | Lab |
      | In  |
    When I search for page summaries
    Then there are 2 page summaries found
    And the found page summaries contain a page with the "event type" "Lab Susceptibility"
    And the found page summaries contain a page with the "event type" "Interview"

  Scenario: I can search for Page Summaries filtered by name contains multiple values
    Given I have a page named "filtering one"
    And the page is for a Vaccination
    And I have a page named "filtering two"
    And the page is for a Lab Susceptibility
    And I have a page named "filtering three"
    And  the page is for a Investigation
    And I have a page named "filtering four"
    And the page is for an Interview
    And I filter page summaries by "event type" contains
      | ati |
      | tervi |
    When I search for page summaries
    Then there are 3 page summaries found
    And the found page summaries contain a page with the "event type" "Vaccination"
    And the found page summaries contain a page with the "event type" "Investigation"
    And the found page summaries contain a page with the "event type" "Interview"
