@patient-profile-treatments
Feature: Patient Profile Treatments

  Background:
    Given I have a patient
    And the patient is a subject of an investigation

  Scenario: I can retrieve treatments for a patient
    Given I have the authorities: "FIND-PATIENT,VIEW-TREATMENT" for the jurisdiction: "ALL" and program area: "STD"
    When the patient is a subject of a Treatment
    Then the profile has an associated Treatment

  Scenario: I cannot retrieve Treatments for a patient without Treatments
    Given I have the authorities: "FIND-PATIENT,VIEW-TREATMENT" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile has no associated Treatments

  Scenario: I cannot retrieve Treatments without proper authorities
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile Treatments are not returned

  Scenario: A Treatment is viewed from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I have the authorities: "VIEW-TREATMENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is a subject of a Treatment
    When the Treatment is viewed from the Patient Profile
    Then the classic profile is prepared to view a Treatment
    And I am redirected to Classic NBS to view a Treatment

  Scenario: A Treatment is viewed from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    And I have the authorities: "OTHER" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is a subject of a Treatment
    When the Treatment is viewed from the Patient Profile
    Then I am not allowed to view a Classic NBS Treatment
