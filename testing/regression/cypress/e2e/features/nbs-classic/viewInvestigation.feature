Feature: View Open Investigation

  Background:
    Given I am logged in as secure user and stay on classic

  @skip-broken
  Scenario: Create notification for an open investigation
    When I click on "Open Investigation" in the menu bar
    And Click on Patient name from open investigation queue
    And Click Events tab on Patient Profile Page
    And Click Add Investigation button on Events tab
    And Select condition form the dropdown in Select Condition Page
    And Click Submit button in Select Condition Page
    And Click on Case Info Tab in Add Investigation for the selected condition
    And Select Jurisdiction as it is mandatory field in Add Investigation for the selected condition
    And Select status from Case Status dropdown in Add Investigation for the selected condition
    And Click Submit button in Add Investigation for the selected condition
    And Click Create Notifications button from top action button group
    And Click Submit button in newly opened window Create Notification Page
