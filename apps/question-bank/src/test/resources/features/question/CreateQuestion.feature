@create_question
Feature: Question create

    Background:
        Given No questions exist

    Scenario: I can create a text question
        Given I am an admin user
        When I submit a create text question request
        Then the text question is created

    Scenario: I cant create a text question without permissions
        Given I am not logged in
        When I submit a create text question request
        Then I am not authorized
