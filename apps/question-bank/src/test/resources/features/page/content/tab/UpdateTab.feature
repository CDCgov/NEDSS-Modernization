@update_tab
Feature: Update Tab

    Background:
        Given pages exist

    Scenario: I can update a tab
        Given I am an admin user
        When I send an update tab request
        Then the tab is updated

    Scenario: I cannot update a tab without logging in
        Given I am not logged in
        When I send an update tab request
        Then a no credentials found exception is thrown

    Scenario: I cannot update a tab without permissions
        Given I am a user without permissions
        When I send an update tab request
        Then an accessdenied exception is thrown
