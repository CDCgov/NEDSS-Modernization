@page-summary-search
Feature: Searching for Page Summaries Filtered by Name

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I am looking for page summaries that contain "filtering"

  Scenario: I can search for Page Summaries filtered by last updated today
    Given the user "Paige" "Arthur" exists as "paige.arthur"
    And I have a page
    And paige.arthur changed the page name to "filtering one"
    And I have another page
    And the user "Other" "User" exists as "other.user"
    And other.user changed the page name to "filtering two" a day ago
    And I have another page
    And the user "Another" "User" exists as "another.user"
    And another.user changed the page name to "filtering three" 7 months ago
    And I have another page
    And the user "Other" "Clerical" exists as "other.clerical"
    And other.clerical changed the page name to "filtering four" 5 days ago
    And I filter page summaries last updated today
    When I search for page summaries
    Then there is only one page summary found
    And the found page summaries contain a page with the "name" "Filtering One"

  Scenario: I can search for Page Summaries filtered by last updated in the last 7 days
    Given the user "Paige" "Arthur" exists as "paige.arthur"
    And I have a page
    And paige.arthur changed the page name to "filtering one"
    And I have another page
    And the user "Other" "User" exists as "other.user"
    And other.user changed the page name to "filtering two" a day ago
    And I have another page
    And the user "Another" "User" exists as "another.user"
    And another.user changed the page name to "filtering three" 7 months ago
    And I have another page
    And the user "Other" "Clerical" exists as "other.clerical"
    And other.clerical changed the page name to "filtering four" 10 days ago
    And I filter page summaries last updated in the last 7 days
    When I search for page summaries
    Then there are 2 page summaries found
    And the found page summaries contain a page with the "name" "Filtering One"
    And the found page summaries contain a page with the "name" "Filtering Two"

  Scenario: I can search for Page Summaries filtered by last updated in the last 14 days
    Given the user "Paige" "Arthur" exists as "paige.arthur"
    And I have a page
    And paige.arthur changed the page name to "filtering one"
    And I have another page
    And the user "Other" "User" exists as "other.user"
    And other.user changed the page name to "filtering two" a day ago
    And I have another page
    And the user "Another" "User" exists as "another.user"
    And another.user changed the page name to "filtering three" 7 months ago
    And I have another page
    And the user "Other" "Clerical" exists as "other.clerical"
    And other.clerical changed the page name to "filtering four" 10 days ago
    And I filter page summaries last updated in the last 14 days
    When I search for page summaries
    Then there are 3 page summaries found
    And the found page summaries contain a page with the "name" "Filtering One"
    And the found page summaries contain a page with the "name" "Filtering Two"
    And the found page summaries contain a page with the "name" "Filtering Four"

  Scenario: I can search for Page Summaries filtered by last updated in the last 30 days
    Given the user "Paige" "Arthur" exists as "paige.arthur"
    And I have a page
    And paige.arthur changed the page name to "filtering one"
    And I have another page
    And the user "Other" "User" exists as "other.user"
    And other.user changed the page name to "filtering two" a day ago
    And I have another page
    And the user "Another" "User" exists as "another.user"
    And another.user changed the page name to "filtering three" 7 months ago
    And I have another page
    And the user "Other" "Clerical" exists as "other.clerical"
    And other.clerical changed the page name to "filtering four" 10 days ago
    And I filter page summaries last updated in the last 30 days
    When I search for page summaries
    Then there are 3 page summaries found
    And the found page summaries contain a page with the "name" "Filtering One"
    And the found page summaries contain a page with the "name" "Filtering Two"
    And the found page summaries contain a page with the "name" "Filtering Four"

  Scenario: I can search for Page Summaries filtered by last updated more than 30d ago
    Given the user "Paige" "Arthur" exists as "paige.arthur"
    And I have a page
    And paige.arthur changed the page name to "filtering one"
    And I have another page
    And the user "Other" "User" exists as "other.user"
    And other.user changed the page name to "filtering two" a day ago
    And I have another page
    And the user "Another" "User" exists as "another.user"
    And another.user changed the page name to "filtering three" 7 months ago
    And I have another page
    And the user "Other" "Clerical" exists as "other.clerical"
    And other.clerical changed the page name to "filtering four" 10 days ago
    And I filter page summaries last updated more than 30 days
    When I search for page summaries
    Then there is only one page summary found
    And the found page summaries contain a page with the "name" "Filtering Three"

  Scenario: I can search for Page Summaries filtered by last updated between
    Given the user "Paige" "Arthur" exists as "paige.arthur"
    And I have a page
    And paige.arthur changed the page name to "filtering one"
    And I have another page
    And the user "Other" "User" exists as "other.user"
    And other.user changed the page name to "filtering two" 2 days ago
    And I have another page
    And the user "Another" "User" exists as "another.user"
    And another.user changed the page name to "filtering three" 7 months ago
    And I have another page
    And the user "Other" "Clerical" exists as "other.clerical"
    And other.clerical changed the page name to "filtering four" 10 days ago
    And I filter page summaries last updated between 10 days ago and 2 days ago
    When I search for page summaries
    Then there are 2 page summaries found
    And the found page summaries contain a page with the "name" "Filtering Two"
    And the found page summaries contain a page with the "name" "Filtering Four"

  Scenario: I can search for Page Summaries filtered by last updated before
    Given the user "Paige" "Arthur" exists as "paige.arthur"
    And I have a page
    And paige.arthur changed the page name to "filtering one"
    And I have another page
    And the user "Other" "User" exists as "other.user"
    And other.user changed the page name to "filtering two" 2 days ago
    And I have another page
    And the user "Another" "User" exists as "another.user"
    And another.user changed the page name to "filtering three" 7 months ago
    And I have another page
    And the user "Other" "Clerical" exists as "other.clerical"
    And other.clerical changed the page name to "filtering four" 10 days ago
    And I filter page summaries last updated before 2 days ago
    When I search for page summaries
    Then there are 3 page summaries found
    And the found page summaries contain a page with the "name" "Filtering Two"
    And the found page summaries contain a page with the "name" "Filtering Three"
    And the found page summaries contain a page with the "name" "Filtering Four"

  Scenario: I can search for Page Summaries filtered by last updated after
    Given the user "Paige" "Arthur" exists as "paige.arthur"
    And I have a page
    And paige.arthur changed the page name to "filtering one"
    And I have another page
    And the user "Other" "User" exists as "other.user"
    And other.user changed the page name to "filtering two" 2 days ago
    And I have another page
    And the user "Another" "User" exists as "another.user"
    And another.user changed the page name to "filtering three" 7 months ago
    And I have another page
    And the user "Other" "Clerical" exists as "other.clerical"
    And other.clerical changed the page name to "filtering four" 10 days ago
    And I filter page summaries last updated after 10 days ago
    When I search for page summaries
    Then there are 3 page summaries found
    And the found page summaries contain a page with the "name" "Filtering One"
    And the found page summaries contain a page with the "name" "Filtering Two"
    And the found page summaries contain a page with the "name" "Filtering Four"
