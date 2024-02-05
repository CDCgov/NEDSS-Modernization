@patient-profile @web-interaction @documents-requiring-review @documents
Feature: Patient Profile Documents Requiring Review: Case Report

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And I can "View" any "document"
    And I have a patient
    And the patient has a Case Report


  Scenario: I can find Cases Reports requiring review for a patient
    Given the case report has not been processed
    And the case report was sent by "Weyland-Yutani Corporation"
    And the case report was received on 07/11/1989
    And the case report has been updated
    And the case report is for the Diphtheria condition
    When I search for documents requiring review for the patient
    Then the case report requiring review is returned
    And the patient case report requiring review is not electronic
    And the patient case report requiring review has the event of the Case Report
    And the patient case report requiring review was sent by "Weyland-Yutani Corporation"
    And the patient case report requiring review has the description title "Diphtheria"
    And the patient case report requiring review was received on 07/11/1989

  Scenario: Unprocessed Cases Reports for a patient do not require review
    When I search for documents requiring review for the patient
    Then the patient has no documents requiring review
