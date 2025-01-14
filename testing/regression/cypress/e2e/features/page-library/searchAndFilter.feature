Feature: Page Builder - User can search and filter the existing page library data here.

    Background:
        Given I am logged in as secure user
        When User navigates to Page Library and views the Page library

    Scenario: User search for a page equal to the Page name
        And User enters keyword "Malaria" in the Search field
        And User click the magnifying glass icon
        Then All pages related to "Malaria" will be returned as a list in the library in "Page name"

    Scenario: Search for page that contains part of the Related conditions keyword
        And User enters keyword "Mum" in the Search field
        And User click the magnifying glass icon
        Then All pages related to "Mumps" will be returned as a list in the library in "Related condition(s)"

    Scenario: Filter by Page Name using (Contains)
        Given Filter section already displayed
        When User selects "Page name" from the drop-down box
        And User selects "CONTAINS" from the Operator field
        And User enters "lar" in the Type a value field
        And User clicks the Done button
        When User clicks the Apply button
        Then Added filters "lar" and "Page name" are applied and only the records matching the filters are displayed in the Page Library list

    Scenario: Cancel Filter by Page Name using (Starts With)
        Given Filter section already displayed
        When User selects "Page name" from the drop-down box
        And User selects "STARTS_WITH" from the Operator field
        And User enters "mum" in the Type a value field
        And User clicks the Cancel button
        Then The application will cancel adding a filter and return to display the + Add Filter link

    Scenario: Filter by Page Name using (Not equal to)
        Given Filter section already displayed
        When User selects "Page name" from the drop-down box
        And User selects "NOT_EQUAL_TO" from the Operator field
        And User enters "Cov" in the Type a value field - multi select
        And User clicks the Done button
        When User clicks the Apply button
        Then Added filters "Cov" and "Page name" are applied and only the records matching the filters are displayed in the Page Library list

    Scenario: Filter by Event Type (Investigation) using (Equals to)
        Given Filter section already displayed
        When User selects "Event type" from the drop-down box
        And User selects "EQUALS" from the Operator field
        And User enters "Investigation" in the Type a value field - multi select
        And User clicks the Done button
        When User clicks the Apply button
        Then Added filters "Investigation" and "Event type" are applied and only the records matching the filters are displayed in the Page Library list

    Scenario: Filter by Related Condition(s) using (Equals to)
        Given Filter section already displayed
        When User selects "Related Condition(s)" from the drop-down box
        And User selects "EQUALS" from the Operator field
        And User enters "Tuberculosis" in the Type a value field - multi select
        And User clicks the Done button
        When User clicks the Apply button
        Then Added filters "Tuberculosis" and "Related Condition(s)" are applied and only the records matching the filters are displayed in the Page Library list

    Scenario: Filter by Status (Published) using (Equals to)
        Given Filter section already displayed
        When User selects "Status" from the drop-down box
        And User selects "EQUALS" from the Operator field
        And User enters "Published" in the Type a value field - multi select
        And User clicks the Done button
        When User clicks the Apply button
        Then Added filters "Published" and "Status" are applied and only the records matching the filters are displayed in the Page Library list

    Scenario: Filter by Last Updated using (Past 7 days) operator
        Given Filter section already displayed
        When User selects "Last updated" from the drop-down box
        And User selects "LAST_7_DAYS" from the Operator field
        Then Type a value field is hidden
        When User clicks the Done button
        When User clicks the Apply button
        Then Added filters "LAST_7_DAYS" and "Last updated" are applied and only the records matching the filters are displayed in the Page Library list

    Scenario: Filter by Last Updated by using (Contains)
        Given Filter section already displayed
        When User selects "Last updated by" from the drop-down box
        And User selects "CONTAINS" from the Operator field
        And User enters "Doe" in the Type a value field
        And User clicks the Done button
        When User clicks the Apply button
        Then Added filters "Doe" and "Last updated by" are applied and only the records matching the filters are displayed in the Page Library list
