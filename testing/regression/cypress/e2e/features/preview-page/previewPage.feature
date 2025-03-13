@skip-if-disabled-is-int
Feature: Page Builder - User can view Preview Page here.

  Background:
    Given I am logged in as secure user

  Scenario Outline: Verify the data elements in the preview page header buttons linked with functionalities
    When User navigates to Preview Page and views the Preview Page
    Then Below buttons will displays in preview page "<Content>" "<Type>"
    Examples:
      | Content          | Type   |
      | Business rules   | Button |
      | Save as template | Button |
      | Delete draft     | Button |
      | Edit draft       | Button |
      | Preview          | Icon   |
      | Clone            | Icon   |
      | Print            | Icon   |
      | Publish          | Button |

  Scenario Outline: Verify the data elements in the preview page header buttons when page status is draft
    When User navigates to Preview Page and page status is Initial Draft
    Then Below buttons will displays in preview page "<Content>" "<Type>"
    Examples:
      | Content          | Type   |
      | Business rules   | Button |
      | Save as template | Button |
      | Delete draft     | Button |
      | Edit draft       | Button |
      | Preview          | Icon   |
      | Clone            | Icon   |
      | Print            | Icon   |
      | Publish          | Button |

  Scenario Outline: Verify the data elements in the preview page header buttons when page status is published
    When User navigates to Preview Page and page status is Published
    Then Below buttons will displays in preview page "<Content>" "<Type>"
    Examples:
      | Content          | Type   |
      | Business rules   | Button |
      | Save as template | Button |
      | Preview          | Icon   |
      | Print            | Icon   |
      | Create new draft | Button |

  Scenario Outline: Verify the data elements in the preview page header buttons when page status is published with draft
    When user is at Preview page - Page info section with page under Draft or Published with Draft status
    Then Below buttons will displays in preview page "<Content>" "<Type>"
    Examples:
      | Content          | Type   |
      | Business rules   | Button |
      | Save as template | Button |
      | Edit draft       | Button |
      | Preview          | Icon   |
      | Clone            | Icon   |
      | Print            | Icon   |
      | Publish          | Button |

  Scenario: Preview Page - Page Info - View Page details and Edit
    Given user is at Preview page - Page info section with page under Draft or Published with Draft status
    When clicks on Edit Page details in preview page
    Then verify user is navigated to Page details page with prepopulated data
    Then verify Conditions is required and editable field
    Then verify user can remove add the conditions
    And verify Page name is required field and already prefilled
    And verify user can update Page name with max characters 50
    Then verify Event Type is required and uneditable field
    And verify Reporting Mechanism is required and editable field
    Then verify user can select another option from reporting mechanism dropdown
    And verify Page description is optional field
    And verify maximum allowed characters are 2000 in Page description
    Then verify Data mart name is optional field and may display exiting data mart name
    And verify maximum allowed characters are 50 for datamart name
    When click on Cancel in page details page
    Then verify user is brought back to Preview page with correct status on top right
    And verify no changes are made to page
    When click on Save changes in  page details page
    Then verify user navigates to pre-preview page with success message You have successfully saved you changes

  Scenario: Preview Page - Page info - all details
    Given user has created a page with all the required details
    When user clicks on Preview button
    Then preview page info is displayed
    And verify Event type Existing Event type is displayed and not editable
    And verify selected Reporting Mechanism displays
    And verify user can edit when page in draft or published with draft status
    And maximum characters allowed are 50 Reporting mechanism
    And verify Page name is displayed
    And verify user can edit when page in draft or published with draft status
    And maximum characters allowed are 50 in page name
    And verify Data mart name is displayed
    And verify user can edit when page in draft or published with draft status
    And maximum characters allowed are 2000
    Then verify Edit page details button is ONLY available for Draft or Publish with draft status
    When clicked on Edit page details
    Then verify all field are editable except Event Type
    When clicked on Metadata button
    Then verify all page metadata is downloaded in xls format

  Scenario: Preview Page - Page Info - View Page details and Edit
    Given user is at Preview page - Page info section with page under Draft or Published with Draft or Published status
    When user clicks on History tab next to Details
    Then verify user is presented with all history info "<Version>"
      | Version              |
      | Date of changes made  |
      | Name of user         |
      | Changes made         |
    Then verify user sees only 10 changes at a time
    And verify when more than 10 changes pagination is available

  Scenario: Create a new page with Event Type (Investigation) and Publish the page
    Given User already on Create new page with Event Type Investigation selected
    When User selects an existing Condition to create new page
    And User enters a Page name to create new page
    And User selects an existing Template to create new page
    And User selects an existing Reporting mechanism to create new page
    And User enters a Page description to create new page
    And clicks the Create page button to create new page
    Then New page is saved to the database and the application directs the user to the Page library Edit page to edit the additional page information
    When User clicks the Preview button
    Then Application will direct the user to the Preview page to Publish
    When User clicks the Publish button
    Then User receives a confirmation that the page was successfully Published
    And Blue label top, right-side should appear as PREVIEWING:PUBLISHED

