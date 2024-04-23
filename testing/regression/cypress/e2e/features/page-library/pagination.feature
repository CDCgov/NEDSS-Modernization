Feature: User can view existing page library data here.

    Background:
        Given I am logged in as "superuser" and password ""
        When User navigates to Page Library and views the Page library

    Scenario: User checks for 10 rows of pages listed in the library
        Then User should see by default 10 rows of pages listed in the library

    Scenario: User selects for 20 rows to show in the Page library
        And User select 20 left footer of the page to show the list of pages
        Then User should see only 20 rows in the library and for each subsequent list where applicable
