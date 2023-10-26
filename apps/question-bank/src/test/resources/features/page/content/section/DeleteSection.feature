@delete_section
Feature: Delete Section

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab

    Scenario: I can delete a section
        Given I am an admin user
        When I send a delete section request
        Then the section is deleted

    Scenario: I cannot delete a section without logging in
        Given I am not logged in
        When I send a delete section request
        Then a no credentials found exception is thrown

    Scenario: I cannot delete a section without permissions
        Given I am a user without permissions
        When I send a delete section request
        Then an accessdenied exception is thrown
