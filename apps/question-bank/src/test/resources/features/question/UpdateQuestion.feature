@update_question
Feature: Update question

    Background: Clean questions
        Given No questions exist

    Scenario: I can update a text question
        Given I am an admin user
        And A text question exists
        When I send an update text question request
        Then the text question is updated

    Scenario: I can update a date question
        Given I am an admin user
        And A date question exists
        When I send an update date question request
        Then the date question is updated

    Scenario: I can update a coded question
        Given I am an admin user
        And A coded question exists
        When I send an update coded question request
        Then the coded question is updated

    Scenario: I can update a numeric question
        Given I am an admin user
        And A numeric question exists
        When I send an update numeric question request
        Then the numeric question is updated


    Scenario: I can update a text questions type
        Given I am an admin user
        And A text question exists
        When I send an update question request that changes the question type
        Then the question type is updated

    Scenario: I cannot update a question that does not exist
        Given I am an admin user
        When I send an update question request for a question that doesn't exist
        Then a question not found exception is thrown

    Scenario: I cannot update a question without logging in
        Given I am not logged in
        And A text question exists
        When I send an update text question request
        Then a no credentials found exception is thrown

    Scenario: I cannot update a question without permissions
        Given I am a user without permissions
        And A text question exists
        When I send an update text question request
        Then an accessdenied exception is thrown
