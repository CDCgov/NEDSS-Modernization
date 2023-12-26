@classic-page-create
Feature: Classic Page Create

  Background:
    Given I am logged in
    And I can "LDFAdministration" any "System"

  Scenario: I can create a page in Classic
    When I am redirected to the classic create page
    Then the NBS Classic create page is loaded