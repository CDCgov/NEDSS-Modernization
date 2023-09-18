@patient_search @patient_relevance_search
Feature: Patient relevance search

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient
    And the patient has the "legal" name "Something" "Other"
    And the patient is searchable

  Scenario: I can find the most relevant patient when searching by first name
    Given I have a patient
    And the patient has the "legal" name "Jon" "Snow"
    And the patient is searchable
    And I have another patient
    And the patient has the "legal" name "John" "Little"
    And the patient is searchable
    And I add the patient criteria for a "first name" "equal to" "John"
    When I search for patients
    Then search result 1 has a "first name" of "John"
    And search result 1 has a "last name" of "Little"
    And search result 2 has a "first name" of "Jon"
    And search result 2 has a "last name" of "Snow"

  Scenario: I can find the most relevant patient when searching by last name
    Given I have a patient
    And the patient has the "legal" name "Albert" "Smyth"
    And the patient is searchable
    And I have another patient
    And the patient has the "legal" name "Fatima" "Smith"
    And the patient is searchable
    And I add the patient criteria for a "last name" "equal to" "Smith"
    When I search for patients
    Then search result 1 has a "first name" of "Fatima"
    And search result 1 has a "last name" of "Smith"
    And search result 2 has a "first name" of "Albert"
    And search result 2 has a "last name" of "Smyth"
