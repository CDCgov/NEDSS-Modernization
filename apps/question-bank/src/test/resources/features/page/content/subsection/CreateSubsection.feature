@create_subsection
Feature: Create Subsection

    Background:
        Given I have a page
        And the page has a tab
        And the page has a section in the 1st tab

    Scenario: I can create a subsection
        Given I am an admin user
        When I send a create subsection request
        Then the subsection is created

    Scenario: I cannot create a subsection without logging in
        Given I am not logged in
        When I send a create subsection request
        Then a no credentials found exception is thrown

    Scenario: I cannot create a section without permissions
        Given I am a user without permissions
        When I send a create subsection request
        Then an accessdenied exception is thrown