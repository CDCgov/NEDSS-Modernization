@update_page_details
Feature: Update page details

    Background:
        Given pages exist

    Scenario: I can update a page's details
        Given I am an admin user
        When I send an update page details request
        Then the page details are updated

    Scenario: I cannot update a page's without logging in
        Given I am not logged in
        When I send an update page details request
        Then a no credentials found exception is thrown

    Scenario: I cannot update a page's without proper permission
        Given I am a user without permissions
        When I send an update page details request
        Then an accessdenied exception is thrown
