@find_question
Feature: A user with the necessary permissions is able to find questions

    Scenario: I can find a question
        Given 4 questions exist
        And I am an admin user
        When I search for a question
        Then I find 4 questions

    Scenario: I can page question results
        Given <Question Count> questions exist
        And I am an admin user
        When I search for a question with page size of <Page Size> and a page number of <Page Number>
        Then I receive a page of <Expected Page Content> results with <Expected Total> total results
        Examples:
            | Question Count | Page Size | Page Number | Expected Page Content | Expected Total |
            | 4              | 2         | 0           | 2                     | 4              |
            | 4              | 2         | 1           | 2                     | 4              |
            | 4              | 2         | 2           | 0                     | 4              |
            | 4              | 50        | 0           | 4                     | 4              |
            | 0              | 100       | 0           | 0                     | 0              |


    Scenario: I cannot search without permissions
        Given I am not logged in
        When I search for a question
        Then an exception is thrown
