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

