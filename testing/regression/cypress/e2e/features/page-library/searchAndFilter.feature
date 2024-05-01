Feature: User can search and filter the existing page library data here.

    Background:
        Given I am logged in as "superuser" and password ""
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
