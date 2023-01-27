Feature: I can perform a patient search

  Background: Go to nbs site and login
   When I visit the "/login" page
   Given I sign in with "hclark"

  Scenario: I can navigate to the simple search page and perform a search
    When I visit the "/advanced-search" page
    And I enter "Surma" in "#firstName"
    And I enter "Singh" in "#lastName"
    And I select option 1 from "select[name='gender']"
    And I intercept the "POST" method of "**/encryption/encrypt" alias "encriptionRequest" api
    And I click the ".left-searchbar button[type='submit']" button
    And I expect the "encriptionRequest" api to be called with '{"firstName":"Surma","gender":"M","lastName":"Singh"}'
