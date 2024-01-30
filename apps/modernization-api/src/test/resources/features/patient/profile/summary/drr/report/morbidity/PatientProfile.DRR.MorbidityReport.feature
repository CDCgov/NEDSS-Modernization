@patient-profile @web-interaction @documents-requiring-review @morbidity-report
Feature: Patient Profile Documents Requiring Review: Case Report

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And I can "View" any "ObservationMorbidityReport"
    And I have a patient
    And the patient has a Morbidity Report


  Scenario: I can find Morbidity Reports requiring review for a patient
    Given the morbidity report has not been processed
    And there is a provider named "Rupert" "Marmalade"
    And the morbidity report was ordered by the provider
    When I search for documents requiring review for the patient
    Then the Morbidity Report requiring review is returned
    When I search for documents requiring review for the patient
    Then the morbidity report requiring review is returned
    And the patient morbidity report requiring review was ordered by "Rupert Marmalade"
    And the patient morbidity report requiring review is not electronic

  Scenario: Unprocessed Morbidity Reports for a patient do not require review
    When I search for documents requiring review for the patient
    Then the patient has no documents requiring review
