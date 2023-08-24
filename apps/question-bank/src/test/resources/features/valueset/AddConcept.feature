@add_concept
Feature: I can add a concept to a value set

    Background:
        Given a value set exists

    Scenario: I can add a concept
        Given I am an admin user
        When I send a request to add a concept to a value set
        Then the concept is added to the value set

    Scenario: I cannot add a concept without logging in
        Given I am not logged in
        When I send a request to add a concept to a value set
        Then a no credentials found exception is thrown

    Scenario: I cannot add a concept without proper permission
        Given I am a user without permissions
        When I send a request to add a concept to a value set
        Then an accessdenied exception is thrown