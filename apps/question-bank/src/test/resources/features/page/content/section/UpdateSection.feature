@update_section
Feature: Update Section

    Background:
        Given I have a page named "Page"
        And the page has a tab named "Tab"
        And the page has a section named "Section" in the "Tab" tab

    Scenario: I can update a section
        Given I am an admin user
        When I send an update section request
        Then the section is updated

    Scenario: I cannot update a section without logging in
        Given I am not logged in
        When I send an update section request
        Then a no credentials found exception is thrown

    Scenario: I cannot update a section without permissions
        Given I am a user without permissions
        When I send an update section request
        Then an accessdenied exception is thrown
