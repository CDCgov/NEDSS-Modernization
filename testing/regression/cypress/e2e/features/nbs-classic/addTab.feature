Feature: Classic NBS - User can add a new tab in Manage Pages

  Background:
    Given I am logged in as secure user and stay on classic
    Then Navigate to Page Library on classic

  Scenario: Add a new tab to Investigation
    Then Click on view icon to display the page details on classic
    Then Click on Edit button on investigation view page
    Then Click on Add New Tab button on investigation view page
    Then Submit a new New Tab via a new window
