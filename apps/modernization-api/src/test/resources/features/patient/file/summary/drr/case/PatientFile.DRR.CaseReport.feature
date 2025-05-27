@documents-requiring-review @case-report
Feature: Patient File Documents Requiring Review: Case Report

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And I can "View" any "document"
    And I have a patient
    And the patient has a Case Report


  Scenario: I can view Cases Reports requiring review for a patient
    Given the case report has not been processed
    And the case report was sent by "Weyland-Yutani Corporation"
    And the case report was received on 07/11/1989 at 10:17:19
    And the case report has been updated
    And the case report is for the condition Diphtheria
    When I view the documents requiring review for the patient
    Then the patient file has the case report requiring review
    And the case report requiring review is not electronic
    And the case report requiring review was sent by "Weyland-Yutani Corporation"
    And the case report requiring review has the condition "Diphtheria"
    And the case report requiring review was received on 07/11/1989 at 10:17:19

  Scenario: Unprocessed Cases Reports for a patient do not require review
    When I search for documents requiring review for the patient
    Then the patient file has no case reports requiring review
