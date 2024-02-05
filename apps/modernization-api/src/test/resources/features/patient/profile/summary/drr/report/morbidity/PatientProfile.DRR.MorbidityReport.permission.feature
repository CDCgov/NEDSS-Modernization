@patient-profile @web-interaction @documents-requiring-review @morbidity-report
Feature: Patient Profile Documents Requiring Review: Morbidity Report Permission

  Background:
    Given I am logged in
    And I have a patient
    And I can "find" any "patient"

  Scenario: I cannot retrieve morbidity reports requiring review for a patient without proper authorities
    When I view the Patient Profile Documents Requiring Review
    Then I am not allowed due to insufficient permissions

  @morbidity-report
  Scenario: I can not find Morbidity Reports requiring review without the proper permission
    Given I can "View" any "Document"
    And I can "View" any "ObservationLabReport"
    And the patient has a morbidity report
    And the morbidity report has not been processed
    And the patient has a morbidity report
    And the morbidity report has not been processed
    And the patient has a Case Report
    And the case report has not been processed
    When I search for documents requiring review for the patient
    Then no morbidity reports are returned
