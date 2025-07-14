@documents-requiring-review @laboratory-report
Feature: Patient File Documents Requiring Review: Laboratory Report

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And I can "view" any "ObservationLabReport"
    And I have a patient
    And the patient has a laboratory report
    And the patient has another laboratory report
    And the laboratory report has not been processed

  Scenario: I can view Laboratory Reports requiring review for a patient
    Given the laboratory report was received on 03/29/2010 at 02:51:07
    And the laboratory report was collected on 07/11/1989
    When I view the documents requiring review for the patient
    Then the patient file has the laboratory report requiring review
    And the laboratory report requiring review was received on 03/29/2010 at 02:51:07
    And the laboratory report requiring review has the event date 07/11/1989
    And there is one laboratory report requiring review
    And the laboratory report requiring review is not electronic
    And the laboratory report requiring review does not contain resulted tests

  Scenario: I can view Laboratory Reports requiring review for a patient that were ordered by a Provider
    Given there is a provider named Doctor/Dr. "Lucien" "Sanchez"
    And the laboratory report was ordered by the provider
    When I view the documents requiring review for the patient
    Then the patient file has the laboratory report requiring review
    And the laboratory report requiring review was ordered by "Doctor/Dr." "Lucien" "Sanchez"

  Scenario: I can view Laboratory Reports requiring review for a patient that were reported by a facility
    Given there is an organization named "Darkplace Hospital"
    And the patient has a laboratory report reported by Darkplace Hospital
    And the laboratory report has not been processed
    When I view the documents requiring review for the patient
    Then the patient file has the laboratory report requiring review
    And the laboratory report requiring review was reported by "Darkplace Hospital"

  Scenario: I can view Laboratory Reports requiring review for a patient that were ordered by a facility
    Given there is an organization named "Darkplace Hospital"
    And the laboratory report was ordered by the Darkplace Hospital facility
    And the laboratory report has not been processed
    When I view the documents requiring review for the patient
    Then the patient file has the laboratory report requiring review
    And the laboratory report requiring review was ordered by the "Darkplace Hospital" facility

  Scenario: I can view electronic Laboratory Reports requiring review for a patient
    Given  the laboratory report is electronic
    When I view the documents requiring review for the patient
    Then the patient file has the laboratory report requiring review
    And the laboratory report requiring review is electronic

  Scenario: I can retrieve Laboratory Reports requiring review with tests ordered for a patient
    And the laboratory report has an ordered test with a specimen from the Cornea
    When I view the documents requiring review for the patient
    Then the laboratory report requiring review contains specimen site of Cornea

  Scenario:I can view Resulted Tests of Laboratory Reports requiring review for a patient
    Given the laboratory report has an Aldolase test with a coded result of above threshold
    And the laboratory report has a Digoxin test with a numeric result of "1013" (drop)
    And the laboratory report has a Paracentesis test with a text result of "yikes!"
    When I view the documents requiring review for the patient
    Then the patient file has the laboratory report requiring review
    And the laboratory report requiring review contains a "Aldolase" test with the result "above threshold"
    And the laboratory report requiring review contains a "Digoxin" test with the result "=1013 (drop)"
    And the laboratory report requiring review contains a "Paracentesis" test with the result "yikes!"
