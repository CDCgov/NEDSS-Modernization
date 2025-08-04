@patient-profile-documents @documents
Feature: Patient Profile Documents

  Background:
    Given I am logged in
    And I have a patient
    And the patient has a Case Report

  @web-interaction
  Scenario: A Document is viewed from the Patient Profile
    Given I can "view" any "document"
    When the Document is viewed from the Patient Profile
    Then the classic profile is prepared to view a Document
    And I am redirected to Classic NBS to view a Document

  @web-interaction
  Scenario: A Document is viewed from the Patient Profile without required permissions
    When the Document is viewed from the Patient Profile
    Then I am not allowed to view a Classic NBS Document
