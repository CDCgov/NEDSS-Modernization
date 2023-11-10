@page-information
Feature: Searching for Page Summaries

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can retrieve Information of a Page
    Given I have an Investigation page named "Testing Information" mapped by Generic Case Notification Message Mapping Guide v.2
    And the page has a "description" of "Testing Information Description"
    And the page has a "datamart" of "Testing Datamart"
    And the page is associated with the Brucellosis condition
    And the page is associated with the Mumps condition
    And the page is associated with the Pertussis condition
    When I retrieve the information of a page
    Then the page information should have an "Event Type" equal to "Investigation"
    And the page information should have a "Message Mapping Guide" equal to "Generic Case Notification Message Mapping Guide v.2"
    And the page information should have a "name" equal to "Testing Information"
    And the page information should have a "description" equal to "Testing Information Description"
    And the page information should have a "datamart" equal to "Testing Datamart"
    And the page information should have a "condition" equal to "Brucellosis"
    And the page information should have a "condition" equal to "Mumps"
    And the page information should have a "condition" equal to "Pertussis"
