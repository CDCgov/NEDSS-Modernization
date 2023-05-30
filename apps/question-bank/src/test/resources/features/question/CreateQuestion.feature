@create_question
Feature: Question create

    Background:
        Given No questions exist
        Given A value set exists

    Scenario: I can create a question
        Given I am an admin user
        When I submit a create "<question type>" question request
        Then the "<question type>" question is created

        Examples:
            | question type |
            | text          |
            | numeric       |
            | date          |
            | dropdown      |

    Scenario: I cant create a question without permissions
        Given I am not logged in
        When I submit a create "<question type>" question request
        Then I am not authorized

        Examples:
            | question type |
            | text          |
            | numeric       |
            | date          |
            | dropdown      |
