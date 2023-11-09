@page-summary-search
Feature: Searching for Sorted Page Summaries

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I am looking for page summaries that contain "sorting"

  Scenario: I can search for Page Summaries sorted by event type ascending
    Given I have a page named "sorting one"
    And the page is for a Vaccination
    And I have a page named "sorting two"
    And the page is for a Lab Susceptibility
    And I have a page named "sorting three"
    And  the page is for a Contact Record
    And I have a page named "sorting four"
    And the page is for an Interview
    And I am looking for page summaries sorted by "event type" ascending
    When I search for page summaries
    Then the 1st found page summary has the "event type" "contact record"
    And the 2nd found page summary has the "event type" "interview"
    And the 3rd found page summary has the "event type" "lab susceptibility"
    And the 4th found page summary has the "event type" "vaccination"

  Scenario: I can search for Page Summaries sorted by event type descending
    Given I have a page named "sorting one"
    And the page is for a Vaccination
    And I have a page named "sorting two"
    And the page is for a Lab Susceptibility
    And I have a page named "sorting three"
    And  the page is for a Contact Record
    And I have a page named "sorting four"
    And the page is for an Interview
    And I am looking for page summaries sorted by "event type" descending
    When I search for page summaries
    Then the 1st found page summary has the "event type" "vaccination"
    And the 2nd found page summary has the "event type" "lab susceptibility"
    And the 3rd found page summary has the "event type" "interview"
    And the 4th found page summary has the "event type" "contact record"
