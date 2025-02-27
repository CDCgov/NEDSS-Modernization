@patient-search @patient-search-results
Feature: Patient Search Result Sorting by Email

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient
    And the patient has the Email Address - Home email address of "xyz@test.com" as of 11/07/2023
    And the patient has the Email Address - Home email address of "other@ema.il" as of 11/07/2024
    And the patient has the Email Address - Home email address of "abc@test.com" as of 11/07/2024
    And I have another patient
    And the patient has a "email address" of "cba@test.com"
    And I have another patient
    And the patient has a "email address" of "bac@test.com"
    And patients are available for search

  Scenario: I can find the most relevant patient when sorting by email ascending
    Given I want patients sorted by "email" "asc"
    When I search for patients
    Then search result 1 has a "email address" of "abc@test.com"
    And search result 2 has a "email address" of "bac@test.com"
    And search result 3 has a "email address" of "cba@test.com"

  Scenario: I can find the most relevant patient when sorting by email descending
    Given I want patients sorted by "email" "desc"
    When I search for patients
    Then search result 1 has a "email address" of "cba@test.com"
    And search result 2 has a "email address" of "bac@test.com"
    And search result 3 has a "email address" of "abc@test.com"
