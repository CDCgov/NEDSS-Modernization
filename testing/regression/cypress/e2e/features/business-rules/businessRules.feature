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
        And User clicks the magnifying glass button business rules library search
        Then Business Rule list will be filtered based on the keywords entered
        When User enters a Unique ID
        And User clicks the magnifying glass button business rules library search
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

    Scenario: User checks for 10 rows of rules listed in the library
        Then User should see by default 10 rows of rules listed in the library in Business Rules

    Scenario: User selects for 50 rows to show in the Business Rules library
        And User select 50 to show the list of business rules
        Then User should see only 50 rows in the library and for each subsequent list where applicable

    Scenario: Create Business Rule using (Enable), (Logic =)
        Given Add new business rules already displayed
        And Function Enable is selected
        And User enters all required and applicable fields with logic is equal to
        And User clicks the Add to library button in new business rules modal
        Then Application will direct the user to the Business Library with the entries populated in the applicable columns

    Scenario: Create Business Rule using (Disable) (Logic <>)
        Given Add new business rules already displayed
        And Function Disable is selected
        And User enters all required and applicable fields with logic is not equal to
        And User clicks the Add to library button in new business rules modal
        Then Application will direct the user to the Business Library with the entries populated in the applicable columns

    Scenario: Create Business Rule using (Hide) (Logic =)
        Given Add new business rules already displayed
        And Function Hide is selected
        And User enters all required and applicable fields with logic is equal to
        And User clicks the Add to library button in new business rules modal
        Then Application will direct the user to the Business Library with the entries populated in the applicable columns

