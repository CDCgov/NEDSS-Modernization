Feature: Page Builder - User can verify advanced condition search pagination here.

    Background:
        Given I am logged in as secure user
        When User navigates to Create New Page and Advanced condition search modal already displayed

    Scenario: User advances to the next list of conditions on the Search and add conditions modal
        And User clicks 2 in the pagination section
        Then User should see the subsequent rows listed on the modal for the number of rows selected. Same results when paginating pages 3, 4, and 5

    Scenario: User advances to the next list of conditions on the Search and add conditions modal using the Next (link)
        And User click the Next
        Then User should see the subsequent row of conditions listed on the Search and add conditions modal
        When User click Next again
        Then User should see the next list in sequence in search list
        When User click the Previous link
        Then User should see the previous list in sequence in search list

