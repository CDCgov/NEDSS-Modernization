@patient-profile @documents-requiring-review @lab-report @web-interaction
Feature: Patient Profile Documents Requiring Review: Lab Report

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And I can "View" any "ObservationLabReport"
    And there is an organization named "Darkplace Hospital"
    And I have a patient

  Scenario: I can find Lab Reports requiring review for a patient
    Given the patient has a lab report reported by Darkplace Hospital
    And the lab report has not been processed
    And there is a provider named "Dean" "Learner"
    And the lab report was ordered by the provider
    When I search for documents requiring review for the patient
    Then the lab report requiring review is returned
    And the patient lab report requiring review was reported by the "Darkplace Hospital" facility
    And the patient lab report requiring review was ordered by "Dean Learner"
    And the patient lab report requiring review is not electronic
    And the patient lab report requiring review has the event of the Lab Report

  Scenario: I can find electronic Lab Reports requiring review for a patient
    Given the patient has a lab report
    And the lab report has not been processed
    And the lab report is electronic
    When I search for documents requiring review for the patient
    Then the lab report requiring review is returned
    And the patient lab report requiring review is electronic

  Scenario: Unprocessed Lab Reports for a patient do not require review
    Given the patient has a lab report
    When I search for documents requiring review for the patient
    Then the patient has no documents requiring review
