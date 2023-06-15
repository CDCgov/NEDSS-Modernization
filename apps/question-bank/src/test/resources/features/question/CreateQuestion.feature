@question_create
Feature: Create question

    Background: question setup
        Given No questions exist

    Scenario: I can create a question
        Given I am an admin user
        When I send a create "<question type>" question request
        Then the "<question type>" question is created
        Examples:
            | question type |
            | text          |
            | date          |
            | numeric       |

    Scenario: I cannot create a question without logging in
        Given I am not logged in
        When I send a create "<question type>" question request
        Then a not authorized exception is thrown
        Examples:
            | question type |
            | text          |
            | date          |
            | numeric       |

    Scenario: I cannot create a question without permissions
        Given I am a user without permissions
        When I send a create "<question type>" question request
        Then a not authorized exception is thrown
        Examples:
            | question type |
            | text          |
            | date          |
            | numeric       |

    Scenario: I cannot create a question with non unique fields
        Given I am an admin user
        When I send a create "text" question request
        Then the "text" question is created
        When I send a create question request with duplicate "<field>"
        Then a question creation exception is thrown
        Examples:
            | field                 |
            | question name         |
            | question identifier   |
            | data mart column name |
            | rdb column name       |
