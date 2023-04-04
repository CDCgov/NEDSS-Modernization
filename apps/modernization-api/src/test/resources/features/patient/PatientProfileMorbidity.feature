@patient-profile-morbidity
Feature: Patient Profile Morbidity

  Background:
    Given I have a patient

  Scenario: I can retrieve morbidity reports for a patient
    Given I have the authorities: "FIND-PATIENT,VIEW-OBSERVATIONMORBIDITYREPORT" for the jurisdiction: "ALL" and program area: "STD"
    When the patient has a Morbidity Report
    Then the profile has an associated morbidity report

  Scenario: I cannot retrieve morbidity reports for a patient without morbidity reports
    Given I have the authorities: "FIND-PATIENT,VIEW-OBSERVATIONMORBIDITYREPORT" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile has no associated morbidity report


  Scenario: I cannot retrieve morbidity reports without proper authorities
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile morbidity reports are not returned
