Feature: Page Builder - User can verify advanced condition search add condition here.

    Background:
        Given I am logged in as secure user
        When User navigates to Create New Page and Advanced condition search modal already displayed

    Scenario: Create a new condition from Search and add conditions modal
        And User views the Create new condition button
        When User clicks the Create new condition button
        Then Create new condition window displays
        When User completes all required and available fields
        And User clicks the Create and add to page button
        Then Create new condition pop-up window closes and new condition is displayed in the Conditions field on Add new page
