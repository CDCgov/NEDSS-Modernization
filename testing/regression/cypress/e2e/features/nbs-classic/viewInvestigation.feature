# problematic page, case info filling borked
@skip-broken
Feature: View Open Investigation

  Background:
    Given I am logged in as secure user and stay on classic

#  Scenario: Accessing and viewing an Open Investigation
#    When I click on "Open Investigation" in the menu bar
#    Then I should land on the "Open Investigation Queue" page
#    Then I click and view an Investigation

  Scenario: Create notification for an open investigation
    When I click on "Open Investigation" in the menu bar
    Then Click on Patient name from open investigation queue
    Then Click Events tab on Patient Profile Page
    Then Click Add Investigation button on Events tab
    Then Select condition form the dropdown in Select Condition Page
    Then Click Submit button in Select Condition Page
    Then Click on Case Info Tab in Add Investigation for the selected condition
    Then Select Jurisdiction as it is mandatory field in Add Investigation for the selected condition
    Then Select status from Case Status dropdown in Add Investigation for the selected condition
    Then Click Submit button in Add Investigation for the selected condition
    Then Click Create Notifications button from top action button group
    Then Click Submit button in newly opened window Create Notification Page
