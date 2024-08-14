Feature: Page Builder - User can verify existing page library data elements here.

    Background:
        Given I am logged in as secure user
        When User navigates to Page Library and views the Page library

    Scenario: Verify the page names appear as links in the Page name column of the library
        When User views the "Page name" column
        Then User will see a list of the "Page name links" populated in the "Page name" column

    Scenario: Verify the required values in the Event type column of the Page library
        When User views the "Event type" column
        Then User will see a list of the "event types" populated in the "Event type" column

    Scenario: Verify various related conditions appear in the Related condition column (of the library)
        When User views the "Related condition(s)" column
        Then User will see a list of the "related conditions" populated in the "Related condition(s)" column

    Scenario: Verify the required data values is populated in the Status column (of the Page library)
        When User views the "Status" column
        Then User will see a list of the "Statuses" populated in the "Status" column

    Scenario: Verify the required date format values is populated in the Last Updated column (of the Page library)
        When User views the "Last Updated" column
        Then User will see a list of the "date values" populated in the "Last Updated" column

    Scenario: Verify the user data (First name, Last name) is populated in the Last Updated by column (of the Page library)
        When User views the "Last updated by" column
        Then User will see a list of the "first name and last name" populated in the "Last updated by" column
