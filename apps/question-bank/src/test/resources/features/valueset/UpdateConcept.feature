@update_concept
Feature: I can update a concept
    Background:
        Given a value set exists

    Scenario: I can update a concept
        Given I am an admin user
        When I send an update concept request
        Then the concept is updated

    Scenario: I cannot update a concept that doesn't exist
        Given I am an admin user
        When I send an update concept request for a concept that doesn't exist
        Then a not found exception is thrown

    Scenario: I cannot update a concept without logging in
        Given I am not logged in
        When I send an update concept request
        Then a no credentials found exception is thrown

    Scenario: I cannot update a concept without proper permission
        Given I am a user without permissions
        When I send an update concept request
        Then an accessdenied exception is thrown
