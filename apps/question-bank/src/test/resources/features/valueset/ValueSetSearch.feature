@valueSet_search
Feature: I can search for  value set


    Scenario: I am able to retrieve all value sets
            Given I am an admin user
            When I make a request to retrieve all value sets
            Then Value sets should be returned


    Scenario: I can search for a value set that does not exist
            Given I am an admin user
            When I make a request for a value set that does not exist
            Then A value sets should not be returned

    Scenario: I can search for a value set that exists
            Given I am an admin user
            When I search for a value set that exists
            Then Value sets should be returned
