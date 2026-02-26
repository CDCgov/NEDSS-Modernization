Feature: Page Builder - User can view existing business rules logic here.

    Background:
        Given I am logged in as secure user
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

    Scenario: Create Business Rule using (Unhide) (Logic =)
        Given Add new business rules already displayed
        And Function Unhide is selected
        And User enters all required and applicable fields with logic is equal to
        And User clicks the Add to library button in new business rules modal
        Then Application will direct the user to the Business Library with the entries populated in the applicable columns

    Scenario: Create Business Rule using (Require if) (Logic =)
        Given Add new business rules already displayed
        And Function Require if is selected
        And User enters all required and applicable fields with logic is equal to
        And User clicks the Add to library button in new business rules modal
        Then Application will direct the user to the Business Library with the entries populated in the applicable columns

    Scenario: Delete a selected Business Rule
        And User clicks one of the questions in the Source Field
        Then Edit business rules page displays
        When User clicks the Delete button in edit business rules page
        Then Warning message is presented "Are you sure you want to delete this business rule?" and "Once deleted, this business rule will be permanently removed from the system and will no longer be associated with the page."
        And User clicks the Yes, delete button in edit business rules page
        Then Application will delete the selected business rule and return to the business rule list screen

    Scenario: Cancel deleting a selected Business Rule
        And User clicks one of the questions in the Source Field
        Then Edit business rules page displays
        When User clicks the Delete button in edit business rules page
        Then Warning message is presented "Are you sure you want to delete this business rule?" and "Once deleted, this business rule will be permanently removed from the system and will no longer be associated with the page."
        And User clicks the Cancel button in edit business rules page
        Then Application will not delete the selected business rule but return to the business rule list screen

    Scenario: Access Edit Business Rule screen
        And User clicks one of the questions in the Source Field
        Then Application should direct the user to the Edit Business Rule screen

    Scenario: Verify editing a business rule with the function as Enable
        And User clicks one of the questions in the Source Field
        Then Edit business rules page displays
        And User selects a different Logic from the drop-down
        When User edits a Target question from the Target questions section, and clicks the Continue button
        Then Updated question appears in the Target questions section, checked
        When Submit button on Edit business rule screen is clicked
        Then Application will validate the values added, add the business rule to the selected page and then navigate to the Business Rule library with the changes made. A confirmation success message displays, The business rule is successfully updated. Please click the unique name to edit.

    Scenario Outline: Access the Filter pop-up window clicking the Filter button from Business Rules Library
        And User clicks the Filter button in business rules library page
        Then User view the Filter pop-up window displays to add one or more filters in business rules library page
        Then Filter pop-window in business rules page displays the initial basic elements "<name>"
        Examples:
            | Name               | Element                  |
            | Filter             | Pop-up window title name |
            | X                  | Close pop-up window      |
            | Show the following | Heading                  |
            | All pages          | Read-only box            |
            | + Add Filter       | Sub-menu function        |
            | Clear filters      | Button                   |
            | Apply              | Button                   |

    Scenario: Filter by Function = Date Validation using (Equals to)
        Given Filter section already displayed
        When User selects Function from the drop-down box
        And User selects Equals to from the Operator field
        And User enters Date validation in the Value field
        And User clicks the Done button
        Then The application will finish adding a filter, display the added filter, and display the “+ Add Filter” link for user to add more filter (if needed)
        When User clicks the Apply button
        Then Added filter(s) are applied and only the records matching the filter(s) are displayed in the Business Rules library list
