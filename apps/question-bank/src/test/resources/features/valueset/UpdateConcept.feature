@update_concept
Feature: I can update a concept
    Background:
        Given a value set exists

    Scenario: I can update a concept
        Given I am logged in 
        And I can "LDFAdministration" any "System"
        And I create an update concept request
        When I send an update concept request
        Then the concept is updated

    Scenario: I cannot update a concept that doesn't exist
        Given I am logged in 
        And I can "LDFAdministration" any "System"
        When I send an update concept request for a concept that doesn't exist
        Then an illegal state exception is thrown

    Scenario: I cannot update a concept without logging in
        Given I am not logged in at all
        And I create an update concept request
        When I send an update concept request
        Then an illegal state exception is thrown

    Scenario: I cannot update a concept without proper permission
        Given I am a user without permissions
        And I create an update concept request
        When I send an update concept request
        Then an illegal state exception is thrown
