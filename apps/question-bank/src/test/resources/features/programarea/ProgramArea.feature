@list_program_area
Feature: I can list available program areas

    Scenario: I can list existing program areas
        Given I am an admin user
        When I list all program areas
        Then program areas are returned

    Scenario: I cannot view program areas without logging in
        Given I am not logged in
        When I list all program areas
        Then a no credentials found exception is thrown

    Scenario: I cannot view program areas without permission
        Given I am a user without permissions
        When I list all program areas
        Then an accessdenied exception is thrown
