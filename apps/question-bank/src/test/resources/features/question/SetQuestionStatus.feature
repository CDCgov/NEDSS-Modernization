@question_status
Feature: Update Question Status

    Background:
        Given I am not logged in

    Scenario: I can set a question's status
        Given I am an admin user
        And A text question exists
        When I update a question's status to "<status>"
        Then the question's status is set to "<status>"
        And a question history is created

        Examples:
            | status   |
            | Active   |
            | Inactive |

    Scenario: I cannot set a question's status without logging in
        Given I am not logged in
        When I update a question's status to "<status>"
        Then a no credentials found exception is thrown

        Examples:
            | status   |
            | Active   |
            | Inactive |

    Scenario: I cannot set a question's status without proper permissions
        Given I am a user without permissions
        When I update a question's status to "<status>"
        Then an accessdenied exception is thrown

        Examples:
            | status   |
            | Active   |
            | Inactive |
