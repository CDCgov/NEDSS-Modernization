@delete_subsection
Feature: Delete Subsection

    Background:
        Given I have a page named "Front Page"
        And the page has a tab named "Tab"
        And the page has a section named "Section" in the "Tab" tab
        And the page has a sub-section named "Sub-Section" in the "Section" section


    Scenario: I can delete a subsection
        Given I am an admin user
        When I send a delete subsection request
        Then the subsection is deleted

    Scenario: I cannot delete a subsection without logging in
        Given I am not logged in
        When I send a delete subsection request
        Then a no credentials found exception is thrown

    Scenario: I cannot delete a subsection without permissions
        Given I am a user without permissions
        When I send a delete subsection request
        Then an accessdenied exception is thrown
