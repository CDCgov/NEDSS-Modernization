@add_subsection_steps
Feature: Create SubSection Service

    Background:
        Given pages exist

    Scenario: I can add a subsection to a page
        Given I am an admin user
        When I send an add subsection request with "<visibility>"
        Then the subsection is created with "<visibility>"
        Examples:
            | visibility |
            | T          |
            | F          |

    Scenario: I cannot add a subsection without logging in
        Given I am not logged in
        When I send an add subsection request with "T"
        Then a no credentials found exception is thrown

    Scenario: I cannot add a subsection without permissions
        Given I am a user without permissions
        When I send an add subsection request with "F"
        Then an accessdenied exception is thrown
