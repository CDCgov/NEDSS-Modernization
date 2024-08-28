Feature: Page Builder - User can verify create new page elements here.

  Background:
    Given I am logged in as secure user
    When User navigates to Create New Page and views the page

  Scenario Outline: Verify Add new page displays required key elements
    Then User should see the following required elements by "<Items required>" "<Type>"
    Examples:
      | Items required                                                         | Type                             |
      | Page Builder                                                           | Title heading                    |
      | Page Library                                                           | Link (to return to page library) |
      | Create new page                                                        | Page heading                     |
      | Let's fill out some information about your new page before creating it | Text                             |
      | Condition(s)                                                           | Heading, drop-down box           |
      | Can't find the condition you're looking for?                           | Text                             |
      | Create a new condition here                                            | Link                             |
      | Page name                                                              | Heading, text field              |
      | Event type                                                             | Heading, drop-down box           |
      | Template                                                               | Heading, drop-down box           |
      | Can't find the template you're looking for?                            | Heading and Link                 |
      | Import a new template here                                             | Heading and Link                 |
      | Page description                                                       | Heading, text fields             |
      | Data mart name                                                         | Heading, text fields             |
      | Cancel                                                                 | Buttons                          |
      | Create page                                                            | Buttons                          |

  Scenario: Select a single condition
    And User clicks in the Condition field
    Then A drop-down box displays with a list of conditions
    When User clicks the check box to select a single condition
    Then A single condition is added in the Conditions field
    When User clicks the up or down arrow - right-side of the field
    Then Drop-down list box closes

  Scenario: Verify Page name field allows entry of text characters
    And User clicks in the Page name field
    Then Page name field is highlighted with a rectangular blue box
    When User enters a Page name in the text field
    Then Page name field allows entry of text successfully

  Scenario Outline: Event Type field displays required values in the drop-down box (upon selection) on Add new page
    And User clicks the Event Type field
    Then Event Type field is highlighted with a rectangular blue box
    And Drop-down box displays with the following required values by "<Option text>"

    Examples:
      | Option text          |
      | Contact Record       |
      | Interview            |
      | Investigation        |
      | Lab Isolate Tracking |
      | Lab Report           |
      | Lab Susceptibility   |
      | Vaccination          |

  Scenario: Verify selection of a Template populates the field
    And User clicks the Template field
    Then Template field is highlighted with a rectangular blue box
    And Drop-down box displays with a list of Templates to select
    When User selects a Template
    Then A Template is populated successfully in the Templates field template

  Scenario: Select an MMG (Reporting Mechanism) to populate the field
    And User clicks the MMG field
    Then MMG field is highlighted with a rectangular blue box
    And Drop-down box displays with a list of MMGs to select
    When User selects an MMG
    Then Message Mapping Guide is populated successfully in the MMG field

  Scenario: Verify clicking the Cancel button closes Add new page
    And User clicks the Cancel button in the footer
    Then Add new page closes and user is returned to the Page Library

  Scenario: Clicking the Page Library link returns user to the Page Library
    And User clicks the Page Library link - top left of the page
    Then Add new page closes and user is returned to the Page Library

