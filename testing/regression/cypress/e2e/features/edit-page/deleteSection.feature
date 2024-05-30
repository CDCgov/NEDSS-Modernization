Feature: Page Builder - User can verify edit page here.

  Background:
    Given I am logged in as "superuser" and password ""
    When User navigates to Edit page and views Edit page

    # 2295
  Scenario: Delete Section with subsections via edit page view
    And clicks on 3 dots next to section
    Then click on 'Delete section' link
    Then verify warning pop up window is displayed with message "Section cannot be deleted." and "This section contains elements (subsections and questions) inside it. Remove the contents first, and then the section can be deleted."
    And verify 'Okay' button is displayed and enabled section will be displayed with the entered information on Edit page
