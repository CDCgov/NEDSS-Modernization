Feature: User can view existing page library data here.

    Background:
        Given I am logged in as "superuser" and password ""

    Scenario: User list Page name in descending and ascending order
        Given User navigates to Page Library
        When User views the Page library
        And User click the up or down arrow in the Page name column
        Then Page names are listed in descending order
        And User click the up or down arrow in the Page name column
        Then Page names are listed in ascending order

    Scenario: User list Event type in descending and ascending order
        Given User already on Page Library
        And User click the up or down arrow in the Event type column
        Then Event type is listed in descending order
        And User click the up or down arrow in the Event type column
        Then Event type is listed in ascending order
