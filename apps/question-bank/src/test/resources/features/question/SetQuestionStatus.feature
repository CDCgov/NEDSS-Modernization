@question_status
Feature: Update Question Status

    Scenario: I can set a question's status
        Given A text question exists
        When I update a question's status to "<status>"
        Then the question's status is set to "<status>"

        Examples:
            | status   |
            | Active   |
            | Inactive |