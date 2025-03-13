@skip-if-disabled-is-int
Feature: Page Builder - User can view existing page library sorting here.

    Background:
        Given I am logged in as secure user
        When User navigates to Page Library and views the Page library

    Scenario: User list Page name in descending and ascending order
        And User click the up or down arrow in the Page name column
        Then Page names are listed in descending order
        And User click the up or down arrow in the Page name column
        Then Page names are listed in ascending order

    Scenario: User list Event type in descending and ascending order
        And User click the up or down arrow in the Event type column
        Then Event type is listed in descending order
        And User click the up or down arrow in the Event type column
        Then Event type is listed in ascending order

    Scenario: User list Status in descending and ascending order
        And User click the up or down arrow in the Status column
        Then Status is listed in descending order
        And User click the up or down arrow in the Status column
        Then Status is listed in ascending order

    Scenario: User list Last Updated in descending and ascending order
        And User click the up or down arrow in the Last Updated column
        Then Last Updated is listed in descending order - latest date
        And User click the up or down arrow in the Last Updated column
        Then Last Updated is listed in ascending order - earliest date

    Scenario: Verify Last Updated displays in the correct date format
        When User views the Last Updated column
        And Application should display the Last Updated date in the correct format

    Scenario: User list Last Updated by in descending and ascending order
        And User click the up or down arrow in the Last Updated by column
        Then Last Updated by is listed in descending order
        And User click the up or down arrow in the Last Updated by column
        Then Last Updated by is listed in ascending order
