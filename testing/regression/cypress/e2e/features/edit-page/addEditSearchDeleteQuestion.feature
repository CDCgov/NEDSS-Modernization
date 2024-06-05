Feature: Page Builder - User can verify managing question while editing the page here.

  Background:
    Given I am logged in as "superuser" and password ""
    When User navigates to Edit page and views Edit page and Subsection already expanded

  Scenario: Create a question (Text Only)
    When User clicks the Add question button
    Then Question library pop-up modal displays
    When User clicks Create New button
    Then Add question pop-up modal displays
    When User completes all required and applicable fields Local or Phin, selecting Text only from Field Type
    And User clicks Create and add to page button
    Then A confirmation message displays "Successfully added questions to page"
    And Add new question pop-up window will disappear with the newly added question displayed on Edit page
