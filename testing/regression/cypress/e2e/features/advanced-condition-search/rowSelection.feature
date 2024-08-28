Feature: Page Builder - User can verify advanced condition search row selection here.

    Background:
        Given I am logged in as secure user
        When User navigates to Create New Page and Advanced condition search modal already displayed

    Scenario: Verify 10 rows are listed (by default) on the Search and Conditions modal
        And User views the Search and add conditions modal
        Then User should see 10 rows of conditions by default for selection

    Scenario: User selects 50 to display only 50 rows on the Search and add conditions modal
        And User clicks in the numeric selection box and selects 50
        Then Numeric selection is saved to the system
        When User clicks the X symbol to close the modal
        And clicks the Advanced condition search button on Add new page
        Then User should see only 50 rows of conditions listed, and for each subsequent list

