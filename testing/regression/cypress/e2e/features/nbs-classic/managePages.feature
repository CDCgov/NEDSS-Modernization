Feature: Classic NBS - User can view and manage data in Manage Pages

  Background:
    Given I am logged in as secure user and stay on classic
    Then Navigate to Page Library on classic

  Scenario: Add new Investigation
    Then Click on Add New button on add new classic page
    Then Select Page as "Investigation" type on add new classic page
    Then Select Template form dropdown on add new classic page
    Then Select Message Mapping Guide from dropdown on add new classic page
    Then Enter Page name on add new classic page
    Then Click on Submit button on add new classic page

  Scenario: Add new Interview
    Then Click on Add New button on add new classic page
    Then Select Page as "Interview" type on add new classic page
    Then Select Template form dropdown on add new classic page
    Then Enter Page name on add new classic page
    Then Click on Submit button on add new classic page

  Scenario: Add new Contact Record
    Then Click on Add New button on add new classic page
    Then Select Page as "Contact Record" type on add new classic page
    Then Select Template form dropdown on add new classic page
    Then Enter Page name on add new classic page
    Then Click on Submit button on add new classic page

  Scenario: Investigation page details view
    Then Click on view icon to display the page details on classic
    Then Check details displayed for "Patient Information"
    Then Check details displayed for "General Information"
    Then Check details displayed for "Name Information"

  Scenario: Investigation page view tabs
    Then Click on view icon to display the page details on classic
    Then Check "Patient" tab displayed in investigation page details
    Then Check "Case Info" tab displayed in investigation page details
    Then Check "Contact Tracing" tab displayed in investigation page details
    Then Check "Contact Records" tab displayed in investigation page details
    Then Check "Supplemental Info" tab displayed in investigation page details


  Scenario: Investigation page clone page
    Then Click on view icon to display the page details on classic
    Then Click on Page Details button on investigation page view
    Then Click on Clone Page button on investigation page details view
    Then Select Message Mapping Guide from dropdown on add new classic page
    Then Enter Page name on add new classic page
    Then Add Related Conditions on details page classic page
    Then Click on Submit button in clone page on add new classic page

  Scenario: Investigation page details edit
    Then Click on view icon to display the page details on classic
    Then Click on Page Details button on investigation page view
    Then Click on Edit button on investigation page details view
    Then Re-enter Page description on add new classic page
    Then Click on Submit button in edit page on add new classic page

  Scenario: Investigation page rules add new
    Then Click on view icon to display the page details on classic
    Then Click on Page Rules button on investigation page view
    Then Click on Add New button on investigation page rules view
    Then Select Function form dropdown on add new rule classic page
    Then Select Source form dropdown on add new rule classic page
    Then Select Logic form dropdown on add new rule classic page
    Then Select Target form dropdown on add new rule classic page
    Then Click on Submit button in add new rule on classic page

  Scenario: Investigation page add/import elements
    Then Click on view icon to display the page details on classic
    Then Click on Edit button on investigation view page
    Then Click Add Elements Icon in Edit page
    Then Select Static Element as Element Type in Add Element page
    Then Select static element type from dropdown
    Then Click on Submit Button in Add Element page
    Then Click on Close Button in Add Element page

