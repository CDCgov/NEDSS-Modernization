@classic-page-create
Feature: Classic Page Create

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can redirect to the Classic create page
    When I am redirected to the classic create page
    Then the NBS Classic create page is loaded