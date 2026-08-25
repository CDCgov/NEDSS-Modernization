@skip-if-disabled-is-int
Feature: Page Builder - User can verify manage section here.

  Background:
    Given I am logged in as secure user
    And User navigates to Edit page and views Manage section pop-up window

  Scenario Outline: Manage sections modal to display key elements
    Then User will see the following by "<Content>" "<Type>"
    Examples:
      | Content         | Type    |
      | Manage sections | title   |
      | Add new section | button  |
      | Patient         | heading |
      | Six dots        | icon    |
      | Pencil          | icon    |
      | Trash can       | icon    |
      | Cross-eye       | icon    |
      | Close           | button  |

  Scenario: Edit / rename an existing section
    When User views the pencil icon to the right of the section name
    And User clicks the pencil icon
    Then Edit section modal window displays
    When User modifies the section name
    And clicks the Save button
    Then Edit section modal closes
    And Inline confirmation message "Your changes have been saved successfully" displays under the Manage sections heading at the top

  Scenario: Delete a section from Manage section without (subsections and questions)
    When User views the trash icon to the right of the section name
    And User clicks the trash icon
    Then Yellow inline message "Are you sure you want to delete this section" displays above the section name with options "Yes, delete" and "Cancel"
    When User clicks Yes, delete button
    Then Yellow banner message closes
    And Green inline confirmation message "You've successfully delete" whatever the section name displays under the Manage sections heading at the top
    And Deleted section is removed from Manage sections modal and Edit page

  Scenario: Add a section from Manage section
    Then verify page header as "Manage sections"
    When click on 'Add new section' from the pop up window
    And enter section name
    And click on 'Add section' button
    Then verify same section is visible in edit page view

