Feature: I can submit a search

  Background:
   Given I visit the nbs site
   Given I login in with "hclark"


  Scenario: I can navigate to the simple search page and perform a search
    When I navigate to the simple search page
    Then I enter the Last Name "Singh"
    And I enter the First Name "Surma"
    And I click on search button
