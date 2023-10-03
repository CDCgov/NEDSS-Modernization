@resulted_test
Feature: I can search for resulted tests

    Scenario: I can search for local resulted tests
        Given local resulted tests exist
        And loinc resulted tests exist
        When I search for resulted tests by "<searchText>"
        Then A test is "<result>"

        Examples:
            | searchText      | result    |
            | Thyroxine       | found     |
            | Thyr            | found     |
            | oxine           | found     |
            | HIV-1 ABS, QUAL | found     |
            | ACYCLOVIR       | found     |
            | ACYCL           | found     |
            | AMDINOCILLIN    | found     |
            | NOCILLIN        | found     |
            | NoCiLlIn        | found     |
            | zzzzzzzzzzzz    | not found |
            | 9123213123123   | not found |

