@patient @patient-search @patient-search-event
Feature: Patient Search by Event ID

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"

  Scenario: I can find a patient with a Morbidity Report ID
    Given I have a patient
    And the patient has a Morbidity Report
    And patients are available for search
    And I would like to search for a patient using the Morbidity Report ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient with a Document ID
    Given I have a patient
    And the patient has a Case Report
    And patients are available for search
    And I would like to search for a patient using the Document ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient with a State Case ID
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation is related to State Case "200021"
    And patients are available for search
    And I would like to search for a patient using the State Case ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  @patient-search-event-abc
  Scenario: I can find a patient with a ABC Case ID
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation is related to ABCs Case "1013675"
    And patients are available for search
    And I would like to search for a patient using the ABC Case ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient with a Lab Report ID
    Given I have a patient
    And the patient has a Lab Report
    And patients are available for search
    And I would like to search for a patient using the Lab Report ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result