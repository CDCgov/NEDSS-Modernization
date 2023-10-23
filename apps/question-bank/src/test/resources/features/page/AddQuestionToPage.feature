@page @add_question_to_page
Feature: I can add a question to a page

    Background: Pages and Questions exist
        Given pages exist
        And A text question exists

    Scenario: I can add a question to a page
        Given I am an admin user
        When I add a question to a page
        Then the question is added to the page

    Scenario: I cannot add a question to a page without logging in
        Given I am not logged in
        When I add a question to a page
        Then a no credentials found exception is thrown

    Scenario: I cannot add a question to a page without proper permission
        Given I am a user without permissions
        When I add a question to a page
        Then an accessdenied exception is thrown
