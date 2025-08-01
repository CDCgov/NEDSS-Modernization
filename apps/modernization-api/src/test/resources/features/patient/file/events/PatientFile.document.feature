@patient-file-document
Feature: Patient File documents

  Background:
    Given I am logged in
    And I can "View" any "document"
    And I have a patient

  Scenario: I cannot retrieve documents for a patient not the subject of any documents
    When I view the documents for the patient
    Then no values are returned

  Scenario: I can retrieve document for a patient
    Given the patient has a Case Report for Diphtheria
    And the case report was sent by "Weyland-Yutani Corporation"
    And the case report was received on 07/11/1989 at 10:17:19
    And the case report has been updated
    When I view the documents for the patient
    Then the patient file has the case report received on 07/11/1989 at 10:17:19
    And the patient file has the case report reported on 07/11/1989
    And the patient file has the case report for the condition "Diphtheria"

  Scenario: I can retrieve Documents with associated investigations for a patient
    Given the patient has a Case Report
    And there is a program area named "Geostigma"
    And there is a jurisdiction named "Midgar"
    And the patient is a subject of an investigation for Geostigma within Midgar
    And I can "view" any "investigation" for Geostigma in Midgar
    And the case report is associated with the investigation
    When I view the documents for the patient
    Then the patient file has the case report associated with the investigation

  Scenario: I cannot view associated investigations when retrieving Documents for a patient when I do not have permission
    Given the patient has a Case Report
    And there is a program area named "Geostigma"
    And there is a jurisdiction named "Midgar"
    And the patient is a subject of an investigation for Geostigma within Midgar
    And the case report is associated with the investigation
    When I view the documents for the patient
    Then the patient file has the case report not associated with any investigations
