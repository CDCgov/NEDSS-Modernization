Feature: Page Builder - User can verify advanced condition search here.

    Background:
        Given I am logged in as "superuser" and password "@test"
        When User navigates to Create New Page and Advanced condition search modal already displayed

    #1227
    Scenario: Verify Search by condition
        And User views the Search field
        When User enters "virus" in the Search field
        And User clicks the magnifying glass button
        Then Conditions list will be filtered based on the keywords entered for the Condition column. Only conditions with the matching keywords west nile will be displayed

    #1228
    Scenario: Verify Search by condition code
        And User views the Search field
        When User enters "10056" in the Search field
        And User clicks the magnifying glass button
        Then Conditions list will be filtered based on the keywords entered for the Condition column. Only conditions with the matching keywords west nile will be displayed

    #1278
    Scenario: User closes Search and add condition(s) modal clicking the Cancel button
        And User views the Search and add conditions modal
        When User click the Cancel button footer
        Then Search and add conditions modal closes and returns the user to Add new page