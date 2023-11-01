@add_tab
Feature: Create Tab

    Background:
        Given I have a page

    Scenario: I can add a tab to a page
        Given I am an admin user
        When I send an add tab request with "<visibility>"
        Then the tab is created with "<visibility>"
        Examples:
            | visibility |
            | T          |
            | F          |

    Scenario: I cannot add a tab without logging in
        Given I am not logged in
        When I send an add tab request with "T"
        Then a no credentials found exception is thrown

    Scenario: I cannot add a tab without permissions
        Given I am a user without permissions
        When I send an add tab request with "F"
        Then an accessdenied exception is thrown
