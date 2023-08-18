@read_conditions
Feature: Read Condition

    Scenario: I can get all conditions
        Given I am an admin user
        When I request all conditions
        Then all conditions are returned


    Scenario: I cannot get all conditions without logging in
        Given I am not logged in
        When I request all conditions
        Then a no credentials found exception is thrown

    Scenario: I cannot get all conditions without proper permission
        Given I am a user without permissions
        When I request all conditions
        Then an accessdenied exception is thrown

    Scenario: Get a page of conditions
        Given I am an admin user
        When I request to retrieve a page of conditions
        Then Conditions successfully return

    Scenario: I cannot get a page of conditions without logging in
        Given I am not logged in
        When I request to retrieve a page of conditions
        Then a no credentials found exception is thrown

    Scenario: I cannot get a page of conditions without proper permission
        Given I am a user without permissions
        When I request to retrieve a page of conditions
        Then an accessdenied exception is thrown


    Scenario: Search for a condition that exists as an admin
        Given I am an admin user
        When I search for a condition that exists
        Then A condition should be returned

    Scenario: I cannot search for conditions without logging in
        Given I am not logged in
        When I search for a condition that exists
        Then a no credentials found exception is thrown

    Scenario: I cannot search for conditions without proper permission
        Given I am a user without permissions
        When I search for a condition that exists
        Then an accessdenied exception is thrown


    Scenario: Search for a condition that does not exist
        Given I am an admin user
        When I search for a condition that does not exist
        Then A condition should not be returned