@patient-search @patient-search-results
Feature: Patient Search Result Sorting by Address

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient
    And the patient has a House - Home address at "6666 Richmond Street" "Miami" "33266" as of 11/07/2023
    And the patient has a House - Home address at "5555 Richmond Street" "Miami" "33266" as of 11/07/2024
    And the patient has a House - Home address at "1111 Richmond Street" "Miami" "33266" as of 11/07/2024
    And I have another patient
    And the patient has a House - Home address at "3333 Richmond Street" "Miami" "33266" as of 11/07/2023
    And I have another patient
    And the patient has a House - Home address at "2222 Richmond Street" "Miami" "33266" as of 11/07/2023
    And patients are available for search

  Scenario: I can find the most relevant patient when sorting by address ascending
    Given I want patients sorted by "address" "asc"
    When I search for patients
    Then search result 1 has a "address" of "1111 Richmond Street"
    And search result 2 has a "address" of "2222 Richmond Street"
    And search result 3 has a "address" of "3333 Richmond Street"

  Scenario: I can find the most relevant patient when sorting by address descending
    Given I want patients sorted by "address" "desc"
    When I search for patients
    Then search result 1 has a "address" of "3333 Richmond Street"
    And search result 2 has a "address" of "2222 Richmond Street"
    And search result 3 has a "address" of "1111 Richmond Street"
