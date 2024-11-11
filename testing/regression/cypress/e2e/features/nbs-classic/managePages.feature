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

