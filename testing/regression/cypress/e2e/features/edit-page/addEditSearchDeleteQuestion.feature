Feature: Page Builder - User can verify managing question while editing the page here.

  Background:
    Given I am logged in as "superuser" and password ""
    When User navigates to Edit page and views Edit page and Subsection already expanded
#
  Scenario: Create a question (Text Only)
    When User clicks the Add question button
    Then Question library pop-up modal displays
    When User clicks Create New button
    Then Add question pop-up modal displays
    When User completes all required and applicable fields Local or Phin, selecting Text only from Field Type
    And User clicks Create and add to page button
    Then A confirmation message displays "Successfully added questions to page"
    And Add new question pop-up window will disappear with the newly added question displayed on Edit page

  Scenario: Edit new question created
    When User clicks the pencil icon for editing a question
    Then Edit question pop-up modal displays
    When User completes the applicable fields that are editable
    And User clicks the Save button
    Then Application will close the Edit question pop-window with the changes saved

  Scenario: Create a question using same Unique ID (test only)
    When User clicks the Add question button
    Then Question library pop-up modal displays
    When User clicks Create New button
    And Enters an existing Unique ID and completes all required and applicable fields, selecting Text only
    And User clicks Create and add to page button
    Then An error message should display similar to "Unique ID cannot be duplicated"

  Scenario: Delete a question
    When User clicks the trash icon for deleting a question
    Then A confirmation pop-up modal displays "Are you sure you want to delete the question?" and "Deleting this question cannot be undone. Are you sure you want to continue?"
    When User clicks Yes, delete to delete question
    Then A success message displays "Question deleted successfully"
    And Application deletes the selected question and remain on the page

