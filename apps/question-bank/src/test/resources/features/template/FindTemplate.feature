@template
Feature: Find templates

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"
    And A template exists

  @template_find_all
  Scenario: I can find all templates
    When I search for all templates
    Then templates are returned