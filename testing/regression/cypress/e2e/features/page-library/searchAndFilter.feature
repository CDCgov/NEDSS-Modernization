Feature: User can search and filter the existing page library data here.

    Background:
        Given I am logged in as "superuser" and password ""
        When User navigates to Page Library and views the Page library

    Scenario: User search for a page equal to the Page name
        And User enters keyword "Malaria" in the Search field
        And User click the magnifying glass icon
        Then All pages related to "Malaria" will be returned as a list in the library in "Page name"
