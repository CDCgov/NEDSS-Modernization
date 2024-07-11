Feature: Page Builder - User can view existing business rules logic here.

    Background:
        Given I am logged in as "superuser" and password "@test"
        When User navigates to Business Rules and views the Business Rules

    Scenario: Verify in (Edit Mode) Business Rules landing page is accessible
        Then Application will display the Business Rules landing page

    Scenario: Verify if Function is (Disable, Enable, Hide, Unhide), value(s) in the Target fields can be any subsection(s) or questions(s) in the current page
        And Function column is populated with Disable, Enable, Hide or Unhide
        Then Target fields column can be any subsections or questions in the current page

    Scenario: Search library by keywords and ID
        And User enters a Unique name in the Search text field for Source field keyword
        And User clicks the magnifying glass button
        Then Business Rule list will be filtered based on the keywords entered
        When User enters a Unique ID
        And User clicks the magnifying glass button
        Then Business Rule list will be filtered based on the Unique ID entered

    Scenario Outline: Verify the data element for Logic if Function column equal to Date validation
        And Logic column is populated
        Then Logic will display possible values as "<Logic>"
        Examples:
            | Logic                    |
            | show Less than or        |
            | show Less or equal to or |
            | show Greater than or     |
            | show Greater or equal to |
