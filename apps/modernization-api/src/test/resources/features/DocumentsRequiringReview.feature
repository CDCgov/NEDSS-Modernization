@documents_requiring_review
Feature: Documents Requiring Review

  Background: 
    Given I have the authorities: "VIEWWORKUP-PATIENT,VIEW-OBSERVATIONLABREPORT" for the jurisdiction: "ALL" and program area: "STD"

  Scenario: I can retrieve documents requiring review for a particular patient
    Given a patient has documents requiring review
    When I search for documents requiring review for a patient
    Then I receive a list of documents requiring review

  Scenario: If a patient has no documents requiring review, none are returned
    Given a patient does not have documents requiring review
    When I search for documents requiring review for a patient
    Then none are returned
