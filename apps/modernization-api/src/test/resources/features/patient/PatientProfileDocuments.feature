@patient-profile-documents @documents
Feature: Patient Profile Documents

  Background:
    Given I am logged in
    And I can "FIND" any "PATIENT"
    And I can "VIEW" any "DOCUMENT" for "STD" within all jurisdictions
    And I have a patient

  Scenario: I can retrieve documents for a patient
    When the patient has a Case Report
    Then the profile has an associated document

  Scenario: I cannot retrieve documents for a patient without documents
    Then the profile has no associated document

  Scenario: I cannot view documents that do not have a program area and jurisdiction assigned
    When the patient has a Case report
    And the case report requires security assignment
    Then the profile has no associated document

  @web-interaction
  Scenario: A Document is viewed from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I can "VIEW" any "DOCUMENT"
    And the patient has a Case Report
    When the Document is viewed from the Patient Profile
    Then the classic profile is prepared to view a Document
    And I am redirected to Classic NBS to view a Document

  @web-interaction
  Scenario: A Document is viewed from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    And the patient has a Case Report
    When the Document is viewed from the Patient Profile
    Then I am not allowed to view a Classic NBS Document
