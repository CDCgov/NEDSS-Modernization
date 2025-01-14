Feature: Page Builder - User can verify advanced condition search create new condition here.

    Background:
        Given I am logged in as secure user
        When User navigates to Create New Page for new condition

    Scenario: Create a new condition
        When User clicks in the Event Type and selects Investigation
        Then Additional required and applicable fields displays
        When User clicks the Create a new condition here button
        Then Create new condition modal window displays
        When User completes the required and applicable fields
        And Clicks the Create and add to page button
        Then Create new condition pop-up window closes and confirmation success message displays with the new condition added in the Conditions field on Create new page

    Scenario: Create new page with newly added condition
        Given New condition created
        And User already on Add new page with new condition added to the Conditions field
        When User completes the required and applicable fields remaining
        And Clicks the Create page button to add created condition
        Then New page created displays with the Patient tab active for the user to start editing the detailed page information
