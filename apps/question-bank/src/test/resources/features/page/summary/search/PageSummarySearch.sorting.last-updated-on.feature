@page-summary-search
Feature: Searching for Sorted Page Summaries

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I am looking for page summaries that contain "sorting"

  Scenario: I can search for Page Summaries sorted by last updated on ascending
    Given the user "Paige" "Arthur" exists as "paige.arthur"
    And I have a page
    And paige.arthur changed the page name to "sorting one" 3 years ago
    And I have another page
    And the user "Other" "User" exists as "other.user"
    And other.user changed the page name to "sorting two" a day ago
    And I have another page
    And the user "Another" "User" exists as "another.user"
    And another.user changed the page name to "sorting three" 7 months ago
    And I have another page
    And the user "Other" "Clerical" exists as "other.clerical"
    And other.clerical changed the page name to "sorting four" 5 days ago
    And I am looking for page summaries sorted by "last updated on" ascending
    When I search for page summaries
    Then the 1st found page summary was "last updated by" "Paige Arthur"
    And the 2nd found page summary was "last updated by" "Another User"
    And the 3rd found page summary was "last updated by" "Other Clerical"
    And the 4th found page summary was "last updated by" "Other User"

  Scenario: I can search for Page Summaries sorted by last updated on descending
    Given the user "Paige" "Arthur" exists as "paige.arthur"
    And I have a page
    And paige.arthur changed the page name to "sorting one" 3 years ago
    And I have another page
    And the user "Other" "User" exists as "other.user"
    And other.user changed the page name to "sorting two" a day ago
    And I have another page
    And the user "Another" "User" exists as "another.user"
    And another.user changed the page name to "sorting three" 7 months ago
    And I have another page
    And the user "Other" "Clerical" exists as "other.clerical"
    And other.clerical changed the page name to "sorting four" 5 days ago
    And I am looking for page summaries sorted by "last updated on" descending
    When I search for page summaries
    Then the 1st found page summary was "last updated by" "Other User"
    And the 2nd found page summary was "last updated by" "Other Clerical"
    And the 3rd found page summary was "last updated by" "Another User"
    And the 4th found page summary was "last updated by" "Paige Arthur"
