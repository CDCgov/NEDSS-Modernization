@delete_tab
Feature: Delete Tab

    Background:
        Given pages exist

    Scenario: I can delete a tab
        Given I am an admin user
        When I send a delete tab request
        Then the tab is deleted


    Scenario: I cannot delete a tab without logging in
        Given I am not logged in
        When I send a delete tab request
        Then a no credentials found exception is thrown

    Scenario: I cannot delete a tab without permissions
        Given I am a user without permissions
        When I send a delete tab request
        Then an accessdenied exception is thrown
