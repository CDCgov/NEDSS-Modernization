@page @page-component
Feature: Page Component Access Restrictions

  Background:
    Given I am logged in

  Scenario: I cannot retrieve the components of a page without the proper permission
    When I view the components of a page
    Then the components of a page are not accessible
