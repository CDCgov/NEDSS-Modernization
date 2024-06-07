Feature: Lab Report Search by criteria

  Background:
    Given I am logged in as "superuser" and password "@test"
    Given I navigate the event laboratory report 
    Given I click Lab report criteria tab

  Scenario: Basic Info - Search by Results test
    When I select resulted test for event laboratory report
    Then I should see No Results found text

  Scenario: Basic Info - Search by Coded result/organism
    When I select coded result for event laboratory report
    Then I should see Results with the link "Lab Report" 
