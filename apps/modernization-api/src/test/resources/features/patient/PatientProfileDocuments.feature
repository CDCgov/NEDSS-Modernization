@patient-profile-documents
Feature: Patient Profile Documents

  Background:
    Given I have a patient

  Scenario: I can retrieve documents for a patient
    Given I have the authorities: "FIND-PATIENT,VIEW-DOCUMENT" for the jurisdiction: "ALL" and program area: "STD"
    When the patient has a Case Report
    Then the profile has an associated document

  Scenario: I cannot retrieve documents for a patient without documents
    Given I have the authorities: "FIND-PATIENT,VIEW-DOCUMENT" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile has no associated document


  Scenario: I cannot retrieve documents without proper authorities
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile documents are not returned
