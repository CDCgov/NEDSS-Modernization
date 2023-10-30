@coded_results
Feature: I can search for coded results

    Scenario: I can search for coded results
        Given local coded results exist
        And snomed coded results exist
        When I search for coded results by "<searchText>"
        Then A coded result is "<result>"

        Examples:
            | searchText    | result    |
            | ANOMRES       | found     |
            | ANOM          | found     |
            | abnormal      | found     |
            | AbNoRmAl      | found     |
            | ormal         | found     |
            | No growth     | found     |
            | growth        | found     |
            | zzzzzzzzzzzz  | not found |
            | 9123213123123 | not found |
