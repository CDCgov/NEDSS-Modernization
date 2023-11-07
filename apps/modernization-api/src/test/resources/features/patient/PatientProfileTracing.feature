@patient-profile-tracing
Feature: Patient Profile Contact Tracing

  Background:
    Given I have a patient

  Scenario: I can retrieve contact named by a patient
    Given I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is a subject of an investigation
    When the patient names a contact
    Then the profile has a contact named by the patient

  Scenario: I can retrieve contact named by a patient
    Given I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile has no associated contacts named by the patient

  Scenario: I cannot retrieve contacts without proper authorities
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile contacts named by the patient are not returned

  Scenario: I can retrieve contacts that named the patient
    Given I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "STD"
    When the patient is named as a contact
    Then the profile has a contact that named the patient

  Scenario: I can retrieve contacts that named the patient
    Given I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "STD"
    When the patient is named as a contact
    Then the profile has a contact that named the patient

  Scenario: I can retrieve contacts that named the patient
    Given I have the authorities: "FIND-PATIENT,VIEW-INVESTIGATION" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile has no associated contacts that named the patient

  Scenario: I cannot retrieve contacts that named the patient without proper authorities
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile contacts that named the patient are not returned

  @web-interaction
  Scenario: A Contact is viewed from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I can "VIEW" any "INVESTIGATION"
    And the patient is a subject of an investigation
    When the patient names a contact
    And the Contact is viewed from the Patient Profile
    Then the classic profile is prepared to view a Contact
    And I am redirected to Classic NBS to view a Contact

  @web-interaction
  Scenario: A Contact is viewed from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    And the patient is a subject of an investigation
    When the patient names a contact
    And the Contact is viewed from the Patient Profile
    When the Contact is viewed from the Patient Profile
    Then I am not allowed to view a Classic NBS Contact
