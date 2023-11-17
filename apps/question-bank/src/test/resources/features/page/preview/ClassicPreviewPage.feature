@classic-page-preview
Feature: Classic Page Preview

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can preview a page in Classic
    Given I have an Investigation page named "Testing Preview"
    When I view the page preview
    Then the NBS Classic page preview is loaded