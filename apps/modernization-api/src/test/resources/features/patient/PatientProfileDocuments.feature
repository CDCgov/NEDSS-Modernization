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


  Scenario: I cannot view documents that do not have a program area and jurisdiction assigned
    Given I have the authorities: "FIND-PATIENT,VIEW-DOCUMENT" for the jurisdiction: "ALL" and program area: "STD"
    When the patient only has a Case Report with no program area or jurisdiction
    Then the profile has no associated document

  Scenario: I cannot retrieve documents without proper authorities
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile documents are not returned


  Scenario: A Document is viewed from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I have the authorities: "VIEW-DOCUMENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a Case Report
    When the Document is viewed from the Patient Profile
    Then the classic profile is prepared to view a Document
    And I am redirected to Classic NBS to view a Document

  Scenario: A Document is viewed from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    And I have the authorities: "OTHER" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a Case Report
    When the Document is viewed from the Patient Profile
    Then I am not allowed to view a Classic NBS Document
