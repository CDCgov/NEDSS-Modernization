@skip-if-disabled-is-int
Feature: Page Builder - User can verify manage subsection here.

  Background:
    Given I am logged in as secure user
    When User navigates to Edit page and views Edit page

  Scenario: Manage subsection - Edit subsection
    Then user is at Manage Subsection window
    And clicks on the edit icon
    Then verify user is at Edit subsection window with all the details
    And verify Cancel and Save Changes button are available
    And verify Save changes button disabled by default
    Then updates subsection name
    And verify Save changes button is enabled
    Then click on Save changes button
    Then verify user is brought back to manage subsection window
    And verify changes in name are successfully displayed

  Scenario: Manage subsection with element: Delete
    Then user is at Manage Subsection window
    And clicks on delete icon
    Then user is given in line message "Subsection cannot be deleted. This subsection contains elements (questions) inside it. Remove the contents first, and then the subsection can be deleted."
    And user clicks ok link

  Scenario: Manage subsection without element: Delete
    Then user is at Manage Subsection window
    And clicks on delete icon
    Then user is given in line message "Are you sure you want to delete this section?"
    And given options "Yes, delete" and 'Cancel'

  Scenario: Manage subsection hide/unhide
    Then user is at Manage Subsection window
    And user clicks on hide or unhide icon
    Then verify hide or unhide icon is greyed out if already visible state
    And verify success message "Subsection hidden successfully"
    Then user clicks on hide or unhide icon again
    And verify success message "Subsection unhidden successfully"
    Then verify hide or unhide icon is blue active if already invisible state

  Scenario: Manage subsection - Add new subsection
    And user clicks on Add new subsection button
    Then user is brought to add subsection window
    And user can enter subsection name in field
    And user can toggle visible or non-visible
    And click on Add subsection button
    Then verify new subsection is successfully added

  Scenario: Manage subsection - 6 points icon
    Then user is at Manage Subsection window
    And user clicks on 6 point icon
    Then verify user can drag and move the sections up or down
    Then click on close on manage subsection
    And verify user can see the change on edit page view


