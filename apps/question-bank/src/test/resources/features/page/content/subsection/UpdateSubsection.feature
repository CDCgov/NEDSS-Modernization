@update_subsection
Feature: Update Subsection

    Background:
        Given I have a page named "Page"
        And the page has a tab named "Tab"
        And the page has a section named "Section" in the "Tab" tab
        And the page has a sub-section named "Subsection" in the "Section" section

    Scenario: I can update a subsection
        Given I am an admin user
        When I send an update subsection request
        Then the subsection is updated

    Scenario: I cannot update a subsection without logging in
        Given I am not logged in
        When I send an update subsection request
        Then a no credentials found exception is thrown

    Scenario: I cannot update a subsection without permissions
        Given I am a user without permissions
        When I send an update subsection request
        Then an accessdenied exception is thrown
