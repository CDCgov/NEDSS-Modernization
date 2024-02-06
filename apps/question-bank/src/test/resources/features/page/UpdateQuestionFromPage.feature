@page @update_question_from_page
Feature: I can update a question from a page

    Background: Pages and Questions exist
        Given I have a page
        And A text question exists
        And the page has a question named "Question One" in the 1st sub-section

    Scenario: I can update a question from a page
        Given I am an admin user
        When I update a question from a page
        Then the question is updated from the page

    Scenario: I cannot update a question from a page without logging in
        Given I am not logged in
        When I update a question from a page
        Then a no credentials found exception is thrown

    Scenario: I cannot update a question from a page without proper permission
        Given I am a user without permissions
        When I update a question from a page
        Then an accessdenied exception is thrown
