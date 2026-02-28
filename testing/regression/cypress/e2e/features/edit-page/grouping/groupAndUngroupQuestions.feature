# Uses old page flow
@skip-broken
@skip-if-disabled-is-int
Feature: Page Builder - User can verify grouping and ungrouping questions here.

  Background:
    Given I am logged in as secure user
    When User navigates to Edit page and views Edit page for Grouping

  Scenario: Edit Page - Ungroup questions - View after clicking
    And user already has grouped question for a subsection
    When user clicks on 3 dots to edit subsection
    Then user should see option for "Ungroup questions"
    And clicks on Ungroup questions
    Then verify a warning pop up window is opened
    Then verify "Cancel" and "Ungroup" button are available
    When user clicks on Ungroup button
    Then verify success message subsection is ungrouped on top of screen
    And subsection does not have 'R' next to subsection name

  Scenario: Edit Page- Edit group questions- accessible
    And user already has grouped question for a subsection
    And user navigates to subsection which has grouped questions
    Then user clicks on 3 dots to edit subsection
    And click on edit subsection
    Then verify user is brought to "Edit subsection" pop window
    And verify edit subsection modal is prefilled with all the information for both sections
    And user should see 'Cancel' and 'Submit' button are enabled

  Scenario: Edit Page- Edit group questions- accessible
    And user already has grouped question for a subsection
    And user navigates to subsection which has grouped questions
    Then user clicks on 3 dots to edit subsection
    And click on edit subsection
    Then verify user is brought to "Edit subsection" pop window
    And change Subsection name to new name
    And change Block name to new block name
    And change Data mart request number to 2
    And change one of the question Appears in table to No
    And change the required Table column % to meet 100%
    Then verify Submit button is enabled
    When user clicks on Submit button grouped
    Then user is brought back to Edit page with success message on top
    And verify all the changes made are visible on edit page subsection

  Scenario: Edit Page-Group questions - End to End flow
    Then user clicks on 3 dots to edit subsection to group
    And user clicks on Group question
    Then verify user is brought to Edit Subsection page
    Then verify Subsection name is prefilled
    And Subsection Visible is default to yes
    Then enter Block name as TEST123
    And Data mart repeat number as 0
    Then user enter percentage for each question eg 50 for each when 2 questions to make "100%"
    And verify Submit button is available and enabled
    When user clicks on Submit button ungrouped
    Then verify user is brought back on edit draft page with success message
    And verify "R" is displayed next to subsection name
    When clicked on Preview button
    Then verify Subsection shows grouped with "R" on preview page with proper columns
