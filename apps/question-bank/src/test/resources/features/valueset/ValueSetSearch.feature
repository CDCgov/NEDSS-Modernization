@valueSet_search
Feature: I can search for  value set

Background:
    Given I am logged in
    And A valueSet exists


    Scenario: I am able to retrieve all value sets
            Given I am an admin user
            When I make a request to retrieve all value sets
            Then All Value sets should be returned


    Scenario: I can search for a value set that does not exist
            Given I am an admin user
            When I make a request for a value set that does not exist
            Then Value sets should not be returned

    Scenario: I can search for a value set that exists
            Given I am an admin user
            When I search for a value set that exists
            Then Value sets should be returned
