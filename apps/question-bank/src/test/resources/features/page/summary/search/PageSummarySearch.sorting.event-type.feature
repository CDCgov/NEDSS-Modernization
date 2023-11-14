@page-summary-search
Feature: Searching for Sorted Page Summaries

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I am looking for page summaries that contain "sorting"

  Scenario: I can search for Page Summaries sorted by event type ascending
    Given I have a Vaccination page named "sorting one"
    And I have a Lab Susceptibility page named "sorting two"
    And I have a Contact Record page named "sorting three"
    And I have an Interview page named "sorting four"
    And I am looking for page summaries sorted by "event type" ascending
    When I search for page summaries
    Then the 1st found page summary has the "event type" "contact record"
    And the 2nd found page summary has the "event type" "interview"
    And the 3rd found page summary has the "event type" "lab susceptibility"
    And the 4th found page summary has the "event type" "vaccination"

  Scenario: I can search for Page Summaries sorted by event type descending
    Given I have a Vaccination page named "sorting one"
    And I have a Lab Susceptibility page named "sorting two"
    And I have a Contact Record page named "sorting three"
    And I have an Interview page named "sorting four"
    And I am looking for page summaries sorted by "event type" descending
    When I search for page summaries
    Then the 1st found page summary has the "event type" "vaccination"
    And the 2nd found page summary has the "event type" "lab susceptibility"
    And the 3rd found page summary has the "event type" "interview"
    And the 4th found page summary has the "event type" "contact record"
