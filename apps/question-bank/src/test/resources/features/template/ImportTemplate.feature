@template
Feature: Import template

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And A template exists

  @template_import
  Scenario: I can import a template
    Given I have template to import
    When I import a template
    Then the template is imported