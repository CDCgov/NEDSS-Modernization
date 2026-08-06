# already modernized?
@skip-broken
Feature: Classic NBS - User can view and manage data in Manage Pages

  Background:
    Given I am logged in as secure user and stay on classic
    Then Navigate to Page Library on classic

  Scenario: Add new Investigation
    When Click on Add New button on add new classic page
    And Select Page as "Investigation" type on add new classic page
    And Select Template form dropdown on add new classic page
    And Select Message Mapping Guide from dropdown on add new classic page
    And Enter Page name on add new classic page
    And Add a related condition on add new classic page
    And Click on Submit button on add new classic page

  Scenario: Add new Interview
    When Click on Add New button on add new classic page
    And Select Page as "Interview" type on add new classic page
    And Select Template form dropdown on add new classic page
    And Enter Page name on add new classic page
    And Click on Submit button on add new classic page

  Scenario: Add new Contact Record
    When Click on Add New button on add new classic page
    And Select Page as "Contact Record" type on add new classic page
    And Select Template form dropdown on add new classic page
    And Enter Page name on add new classic page
    And Click on Submit button on add new classic page

  Scenario: Investigation page details view
    When Click on view icon to display the page details on classic
    Then Check details displayed for "Patient Information"
    And Check details displayed for "General Information"
    And Check details displayed for "Name Information"

  Scenario: Investigation page view tabs
    When Click on view icon to display the page details on classic
    Then Check "Patient" tab displayed in investigation page details
    And Check "Case Info" tab displayed in investigation page details
    And Check "Contact Tracing" tab displayed in investigation page details
    And Check "Contact Records" tab displayed in investigation page details
    And Check "Supplemental Info" tab displayed in investigation page details


  Scenario: Investigation page clone page
    When Click on Last Updated to sort by most recent pages
    And Click on view icon to display the page details on classic
    And Click on Page Details button on investigation page view
    And Click on Clone Page button on investigation page details view
    And Select Message Mapping Guide from dropdown on add new classic page
    And Enter Page name on add new classic page
    And Add Related Conditions on details page classic page
    And Click on Submit button in clone page on add new classic page

  Scenario: Investigation page details edit
    When Click on Last Updated to sort by most recent pages
    And Click on view icon to display the page details on classic
    And Click on Page Details button on investigation page view
    And Click on Edit button on investigation page details view
    And Re-enter Page description on add new classic page
    And Click on Submit button in edit page on add new classic page

  Scenario: Investigation page rules add new
    When Click on Last Updated to sort by most recent pages
    And Click on view icon to display the page details on classic
    And Click on Page Rules button on investigation page view
    And Click on Add New button on investigation page rules view
    And Select Function form dropdown on add new rule classic page
    And Select Source form dropdown on add new rule classic page
    And Select Logic form dropdown on add new rule classic page
    And Select Target form dropdown on add new rule classic page
    And Click on Submit button in add new rule on classic page

  Scenario: Investigation page add/import elements
    When Click on Last Updated to sort by most recent pages
    And Click on view icon to display the page details on classic
    And Click on Edit button on investigation view page
    And Click Add Elements Icon in Edit page
    And Select Static Element as Element Type in Add Element page
    And Select static element type from dropdown
    And Click on Submit Button in Add Element page
    And Click on Close Button in Add Element page

  @skip
  Scenario: Add a new tab to Investigation
    When Click on Last Updated to sort by most recent pages
    And Click on view icon to display the page details on classic
    And Click on Edit button on investigation view page
    And Click on Add New Tab button on investigation view page
    And Submit a new New Tab via a new window

  Scenario: Add a new section to Investigation
    When Click on Last Updated to sort by most recent pages
    And Click on view icon to display the page details on classic
    And Click on Edit button on investigation view page
    And Click on Add New Section button on investigation view page
    And Submit a section in new window

  Scenario: Add a new subsection to Investigation
    When Click on Last Updated to sort by most recent pages
    And Click on view icon to display the page details on classic
    And Click on Edit button on investigation view page
    And Click on Add New Subsection button on investigation view page
    And Submit a subsection in new window
