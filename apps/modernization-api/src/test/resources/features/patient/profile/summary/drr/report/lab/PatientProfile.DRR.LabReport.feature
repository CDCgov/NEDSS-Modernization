@patient-profile @web-interaction @documents-requiring-review @lab-report
Feature: Patient Profile Documents Requiring Review: Lab Report

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And I can "View" any "ObservationLabReport"
    And I have a patient

  Scenario: I can find Lab Reports requiring review for a patient
    Given the patient has an unprocessed lab report
    When I search for documents requiring review for the patient
    Then the lab report requiring review is returned
