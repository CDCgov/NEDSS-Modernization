@patient @patient-search @patient-search-event
Feature: Patient Search by Event ID

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I have a patient

  Scenario: I can find a patient with a Morbidity Report ID
    Given I have another patient
    And the patient has a Morbidity Report
    And patients are available for search
    And I would like to search for a patient using the Morbidity Report ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result


  Scenario: I can find a patient with a Document ID
    Given I have another patient
    And the patient has a Case Report
    And patients are available for search
    And I would like to search for a patient using the Document ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result

  Scenario: I can find a patient with a Lab Report ID
    Given I have another patient
    And the patient has a Lab Report
    And patients are available for search
    And I would like to search for a patient using the Lab Report ID
    When I search for patients
    Then the patient is in the search results
    And there is only one patient search result