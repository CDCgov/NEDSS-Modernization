Feature: Page Builder - User can verify advanced condition search sorting here.

    Background:
        Given I am logged in as secure user
        When User navigates to Create New Page and Advanced condition search modal already displayed

    Scenario: Sort by Condition in descending and ascending order
        When User views the Search and add conditions modal
        And User clicks the up or down arrows in the Condition name column
        Then Condition names are listed in descending order
        When User clicks the up or down arrow again
        Then Condition names are listed in ascending order
