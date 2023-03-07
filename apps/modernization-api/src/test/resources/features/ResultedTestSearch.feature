@resulted_test
Feature: I can search for resulted tests

  Scenario: I can search for local resulted tests
    Given local resulted tests exist
    When I search for local resulted tests by "<searchText>"
    Then A local test is "<result>"

    Examples:
      | searchText      | result    |
      | Thyroxine       | found     |
      | Thyr            | found     |
      | oxine           | found     |
      | HIV-1 ABS, QUAL | found     |
      | zzzzzzzzzzzz    | not found |
      | 9123213123123   | not found |

  Scenario: I can search for loinc resulted tests
    Given loinc resulted tests exist
    When I search for loinc resulted tests by "<searchText>"
    Then A loinc test is "<result>"

    Examples:
      | searchText    | result    |
      | ACYCLOVIR     | found     |
      | ACYCL         | found     |
      | AMDINOCILLIN  | found     |
      | NOCILLIN      | found     |
      | zzzzzzzzzzzz  | not found |
      | 9123213123123 | not found |
