@patient-profile @web-interaction @documents-requiring-review @documents
Feature: Patient Profile Documents Requiring Review: Case Report Permission

  Background:
    Given I am logged in
    And I have a patient
    And I can "find" any "patient"

  Scenario: I cannot retrieve Case Reports requiring review for a patient without proper authorities
    When I view the Patient Profile Documents Requiring Review
    Then I am not allowed due to insufficient permissions

  @morbidity-report @lab-report
  Scenario: I can not find Case Reports requiring review without the proper permission
    Given I can "View" any "ObservationLabReport"
    And I can "View" any "ObservationMorbidityReport"
    And the patient has a lab report
    And the lab report has not been processed
    And the patient has a morbidity report
    And the morbidity report has not been processed
    And the patient has a Case Report
    And the case report has not been processed
    When I search for documents requiring review for the patient
    Then no case reports are returned
