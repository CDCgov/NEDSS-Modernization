@patient-profile
Feature: Patient Profile

  Background:
    Given I have a patient

  Scenario: I can retrieve a profile by patient identifier
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a profile is requested by patient identifier
    Then the profile is found

  Scenario: I can retrieve a profile by short identifier
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a profile is requested by short identifier
    Then the profile is found

  Scenario: I cannot retrieve a profile without proper authorities
    Given I have the authorities: "NOTHING" for the jurisdiction: "ALL" and program area: "STD"
    When a profile is requested by patient identifier
    Then the profile is not accessible

  Scenario: I cannot retrieve a profile without proper authorities
    Given I have the authorities: "NOTHING" for the jurisdiction: "ALL" and program area: "STD"
    When a profile is requested by short identifier
    Then the profile is not accessible

  Scenario: I can delete a patient that is not associated with events
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a profile is requested by patient identifier
    Then the patient can be deleted

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is a subject of an investigation
    When a profile is requested by patient identifier
    Then the patient can not be deleted

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a lab Report
    When a profile is requested by patient identifier
    Then the patient can not be deleted

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a Morbidity Report
    When a profile is requested by patient identifier
    Then the patient can not be deleted

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is vaccinated
    When a profile is requested by patient identifier
    Then the patient can not be deleted

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is a subject of an investigation
    And the patient is a subject of a Treatment
    When a profile is requested by patient identifier
    Then the patient can not be deleted

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a Case Report
    When a profile is requested by patient identifier
    Then the patient can not be deleted

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is a subject of an investigation
    And the patient names a contact
    When a profile is requested by patient identifier
    Then the patient can not be deleted

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is named as a contact
    When a profile is requested by patient identifier
    Then the patient can not be deleted

  Scenario: I can not delete a patient that is already deleted
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has been deleted
    When a profile is requested by patient identifier
    Then the patient can not be deleted