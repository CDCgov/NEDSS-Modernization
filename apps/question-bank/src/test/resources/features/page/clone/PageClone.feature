@page-clone
Feature: Cloning an existing page

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And I have a page

  @web-interaction
  Scenario: A page is cloned from Page Preview
    When the page is cloned from Page Preview
    Then I am redirected to Classic NBS to clone the page
