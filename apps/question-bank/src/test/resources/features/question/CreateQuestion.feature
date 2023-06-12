@question_create
Feature: Create question

    Background: question setup
        Given No questions exist

    Scenario: I can create a text question
        Given I am an admin user
        When I send a create text question request
        Then the text question is created

    Scenario: I cannot create a text question without logging in
        Given I am not logged in
        When I send a create text question request
        Then a not authorized exception is thrown

    Scenario: I cannot create a text question without permissions
        Given I am a user without permissions
        When I send a create text question request
        Then a not authorized exception is thrown
