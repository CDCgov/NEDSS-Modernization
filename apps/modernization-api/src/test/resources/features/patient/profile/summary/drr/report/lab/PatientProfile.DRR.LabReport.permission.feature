@patient-profile @web-interaction @documents-requiring-review @lab-report
Feature: Patient Profile Documents Requiring Review: Lab Report Permission

  Background:
    Given I am logged in
    And I have a patient
    And I can "find" any "patient"

  Scenario: I cannot retrieve lab reports requiring review for a patient without proper authorities
    When I view the Patient Profile Documents Requiring Review
    Then I am not allowed due to insufficient permissions

  Scenario: I can not find Lab Reports requiring review without the proper permission
    Given I can "View" any "Document"
    And I can "View" any "ObservationMorbidityReport"
    And the patient has a lab report
    And the patient has an unprocessed morbidity report
    And the patient has an unprocessed document
    When I search for documents requiring review for the patient
    Then no lab reports are returned
