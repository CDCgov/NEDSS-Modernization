Feature: Page Builder - User can verify manage page here.

  Background:
    Given I am logged in as "superuser" and password "@test"
    When User navigates to Edit page and views Edit page and is at Manage Subsection window

  Scenario: Manage subsection - Edit subsection
    And clicks on the edit icon
    Then verify user is at Edit subsection window with all the details
    And verify Cancel and Save Changes button are available
    And verify Save changes button disabled by default
    Then updates subsection name
    And verify Save changes button is enabled
    Then click on Save changes button
    Then verify user is brought back to manage subsection window
    And verify changes in name are successfully displayed
