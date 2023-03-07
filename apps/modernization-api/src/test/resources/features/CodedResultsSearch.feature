@coded_results
Feature: I can search for coded results

  Scenario: I can search for local coded results
    Given local coded results exist
    When I search for local coded results by "<searchText>"
    Then A local coded result is "<result>"

    Examples:
      | searchText    | result    |
      | ANOMRES       | found     |
      | ANOM          | found     |
      | abnormal      | found     |
      | ormal         | found     |
      | zzzzzzzzzzzz  | not found |
      | 9123213123123 | not found |

  Scenario: I can search for snomed coded results
    Given snomed coded results exist
    When I search for snomed coded results by "<searchText>"
    Then A snomed coded results is "<result>"

    Examples:
      | searchText    | result    |
      | No growth     | found     |
      | growth        | found     |
      | Abnormal      | found     |
      | ormal         | found     |
      | zzzzzzzzzzzz  | not found |
      | 9123213123123 | not found |
