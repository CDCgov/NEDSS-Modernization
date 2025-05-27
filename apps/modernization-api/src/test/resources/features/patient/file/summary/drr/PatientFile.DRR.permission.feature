@documents-requiring-review @documents
Feature: Patient Profile Documents Requiring Review: Case Report Permission

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And I have a patient
    And the patient has a lab report
    And the lab report has not been processed
    And the patient has a morbidity report
    And the morbidity report has not been processed
    And the patient has a Case Report
    And the case report has not been processed

  Scenario: I cannot retrieve Case Reports requiring review for a patient without proper authorities
    When I view the documents requiring review for the patient
    Then I am not allowed due to insufficient permissions

  Scenario: I can not find Case Reports requiring review without the proper permission
    Given I can "View" any "ObservationLabReport"
    And I can "View" any "ObservationMorbidityReport"
    When I view the documents requiring review for the patient
    Then the patient file has no case reports requiring review
    And there is one morbidity report requiring review
#    And there is one laboratory report requiring review

  Scenario: I can not find Morbidity Reports requiring review without the proper permission
    Given I can "View" any "ObservationLabReport"
    And I can "View" any "document"
    When I view the documents requiring review for the patient
    Then the patient file has no morbidity reports requiring review
    And there is one case report requiring review
#    And there is one laboratory report requiring review

  Scenario: I can not find Laboratory Reports requiring review without the proper permission
    Given I can "View" any "document"
    And I can "View" any "ObservationMorbidityReport"
    When I view the documents requiring review for the patient
    Then the patient file has no laboratory reports requiring review
    And there is one morbidity report requiring review
    And there is one case report requiring review

  Scenario: I can view all documents requiring review for a patient
    Given I can "View" any "document"
    And  I can "View" any "ObservationLabReport"
    And I can "View" any "ObservationMorbidityReport"
    When I view the documents requiring review for the patient
    Then there is one case report requiring review
#    And there is one laboratory report requiring review
    And there is one morbidity report requiring review

