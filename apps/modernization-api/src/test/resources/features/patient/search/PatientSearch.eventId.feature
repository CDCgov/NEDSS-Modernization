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

  Scenario: I can find a patient with a ABC Case ID
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation is related to ABCs Case "1013675"
    And patients are available for search
    And I would like to search for a patient using the ABC Case ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient with a County Case ID
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation is related to County Case "2023657"
    And patients are available for search
    And I would like to search for a patient using the County Case ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient with a Notification ID
    Given I have a patient
    And the patient is a subject of an investigation
    And the investigation has a notification status of APPROVED
    And patients are available for search
    And I would like to search for a patient using the Notification ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient with a Treatment ID
    Given I have a patient
    And the patient is a subject of an investigation
    And the patient is a subject of a Treatment
    And patients are available for search
    And I would like to search for a patient using the Treatment ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient with a Vaccination ID
    Given I have a patient
    And the patient is vaccinated
    And patients are available for search
    And I would like to search for a patient using the Vaccination ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient with an Investigation ID
    Given I have a patient
    And the patient is a subject of an investigation
    And patients are available for search
    And I would like to search for a patient using the Investigation ID
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

  Scenario: I can find a patient with an Accession number
    Given I have a patient
    And the patient has a Lab Report
    And the lab report has an Accession number of "3034112"
    And patients are available for search
    And I would like to search for a patient using the Accession number
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient with an Accession number with a hyphen (NIS-74)
    Given I have a patient
    And the patient has a Lab Report
    And the lab report has an Accession number of "303-4112"
    And patients are available for search
    And I would like to search for a patient using the Accession number
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result
