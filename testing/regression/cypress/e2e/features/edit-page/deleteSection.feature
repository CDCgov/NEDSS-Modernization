@skip-if-disabled-is-int
Feature: Page Builder - User can verify delete section while editing the page here.

  Background:
    Given I am logged in as secure user
    When User navigates to Edit page and views Edit page

  Scenario: Delete Section with subsections via edit page view
    And clicks on 3 dots next to section
    Then click on 'Delete section' link
    Then verify warning pop up window is displayed with message "Section cannot be deleted." and "This section contains elements (subsections and questions) inside it. Remove the contents first, and then the section can be deleted."
    And verify 'Okay' button is displayed and enabled section will be displayed with the entered information on Edit page

  Scenario: Delete Section without elements via edit page view
    And clicks on 3 dots next to section without subsections
    Then click on 'Delete section' link without subsections
    And user gets a warning pop up window with message "Are you sure you want to delete the section?" "Deleting this section cannot be undone. Are you sure you want to continue?"
    Then user clicks on 'Yes, delete' button
    Then verify success message "You have successfully deleted" is displayed on top right corner

