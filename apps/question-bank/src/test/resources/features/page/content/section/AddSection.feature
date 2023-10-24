@add_section_steps
Feature: Create Section Service

    Background:
        Given pages exist

    Scenario: I can add a section to a page
        Given I am an admin user
        When I send an add section request with "<visibility>"
        Then the section is created with "<visibility>"
        Examples:
            | visibility |
            | T          |
            | F          |

    Scenario: I cannot add a section without logging in
        Given I am not logged in
        When I send an add section request with "T"
        Then a no credentials found exception is thrown

    Scenario: I cannot add a section without permissions
        Given I am a user without permissions
        When I send an add section request with "F"
        Then an accessdenied exception is thrown
