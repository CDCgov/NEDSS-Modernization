@page-print
Feature: Simplified Page View for Printing

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page

  @web-interaction
  Scenario: A page is printed from Page Preview
    When the page is printed from Page Preview
    Then I am redirected to Classic NBS to view the simplified Page
