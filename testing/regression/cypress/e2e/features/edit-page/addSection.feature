Feature: Page Builder - User can verify add section while editing the page here.

  Background:
    Given I am logged in as secure user
    When User navigates to Edit page and views Add a section pop-up window

  Scenario: Add a section in (Edit Mode)
    And User enters a section name in the the required text field
    When User clicks the Add section button enabled
    Then Confirmation success message will display for 3 to 5 seconds
    And Newly created section will be displayed with the entered information on Edit page
