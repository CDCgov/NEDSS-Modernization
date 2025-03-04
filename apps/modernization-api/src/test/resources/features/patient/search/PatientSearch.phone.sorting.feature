@patient-search @patient-search-results
Feature: Patient Search Result Sorting by Phone

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient
    And the patient has the Phone Number - Home number of "555-555-5555" as of 11/07/2023
    And the patient has the Phone Number - Home number of "555-444-4444" as of 11/07/2024
    And the patient has the Phone Number - Home number of "555-111-1111" as of 11/07/2024
    And I have another patient
    And the patient has a "phone number" of "555-333-3333"
    And I have another patient
    And the patient has a "phone number" of "555-222-2222"
    And patients are available for search

  Scenario: I can find the most relevant patient when sorting by phone ascending
    Given I want patients sorted by "phone" "asc"
    When I search for patients
    Then search result 1 has a "phone number" of "555-111-1111"
    And search result 2 has a "phone number" of "555-222-2222"
    And search result 3 has a "phone number" of "555-333-3333"

  Scenario: I can find the most relevant patient when sorting by phone descending
    Given I want patients sorted by "phone" "desc"
    When I search for patients
    Then search result 1 has a "phone number" of "555-333-3333"
    And search result 2 has a "phone number" of "555-222-2222"
    And search result 3 has a "phone number" of "555-111-1111"
