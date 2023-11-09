@page @page-summary-search
Feature: Page Summary Search Access Restriction

    Background:
        Given I am logged in

    Scenario: I cannot search for page summaries without the proper permission
        When I search for page summaries
        Then the page summaries are not searchable
