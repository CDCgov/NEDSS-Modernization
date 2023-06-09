@question_create
Feature: Create question

    Background: question setup
        Given No questions exist

    Scenario: I can create a text question
        Given I am an admin user
        When I send a create text question request
        Then the text question is created
