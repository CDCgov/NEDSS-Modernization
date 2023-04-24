@patient-profile-investigation
Feature: Patient Profile Investigations

  Background:
    Given I have a patient

  Scenario: I can retrieve all investigations for a patient
    Given I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "STD"
    When the patient is a subject of an investigation
    Then the profile has an associated investigation

  Scenario: I can retrieve closed investigations for a patient
    Given I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "STD"
    When the patient is a subject of an investigation
    And the investigation has been closed
    Then the profile has an associated investigation
    And the profile has no associated open investigation

  Scenario: I can retrieve open investigations for a patient
    Given I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "STD"
    When the patient is a subject of an investigation
    Then the profile has an associated open investigation

  Scenario: I cannot retrieve investigations for a patient not the subject of an investigation
    Given I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile has no associated investigation

  Scenario: I cannot retrieve patient investigations without proper authorities
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile investigations are not accessible
