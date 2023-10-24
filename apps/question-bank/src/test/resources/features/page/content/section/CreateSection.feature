@create_section
Feature: Create Secton

    Background:
        Given I have a page named "Front Page"
        And the page has a tab named "Tab"

    Scenario: I can create a section
        Given I am an admin user
        When I send a create section request
        Then the section is created

    Scenario: I cannot create a section without logging in
        Given I am not logged in
        When I send a create section request
        Then a no credentials found exception is thrown

    Scenario: I cannot create a section without permissions
        Given I am a user without permissions
        When I send a create section request
        Then an accessdenied exception is thrown