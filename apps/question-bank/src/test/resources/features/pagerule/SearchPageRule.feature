@search_page_rule
Feature: search page rule

    Background:
        Given pages exist

    Scenario: I can search for page rule
        Given I am an admin user
        When I search for page rule
        Then page rule is returned

    Scenario: I cannot get page rule without proper permission
        Given I am a user without permissions
        When I search for page rule
        Then an access denied exception is thrown

