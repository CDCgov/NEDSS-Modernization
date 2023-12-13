@delete_rule
Feature: Delete rule

    Background:
        Given I have a page
         And the page has a rule


    Scenario: I can delete a rule
        Given I am an admin user
        When I send a delete rule request
        Then the rule is deleted

    Scenario: I cannot delete a rule without logging in
        Given I am not logged in
        When I send a delete rule request
        Then a no credentials found exception is thrown

    Scenario: I cannot delete a rule without permissions
        Given I am a user without permissions
        When I send a delete rule request
        Then an accessdenied exception is thrown
