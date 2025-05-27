@documents-requiring-review @morbidity-report
Feature: Patient File Documents Requiring Review: Morbidity Report

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And I can "view" any "ObservationMorbidityReport"
    And I have a patient
    And the patient has a morbidity report
    And the patient has another Morbidity Report
    And the morbidity report has not been processed

  Scenario: I can view Morbidity Reports requiring review for a patient
    Given the morbidity report was received on 03/29/2010 at 02:51:07
    And the morbidity report was reported on 07/11/1989 at 10:17:19
    And the morbidity report is for the condition Diphtheria
    When I view the documents requiring review for the patient
    Then the patient file has the morbidity report requiring review
    And the morbidity report requiring review was received on 03/29/2010 at 02:51:07
    And the morbidity report requiring review has the event date 07/11/1989 at 10:17:19
    And there is one morbidity report requiring review
    And the morbidity report requiring review is not electronic
    And the morbidity report requiring review has the condition "Diphtheria"
    And the morbidity report requiring review does not contain treatments
    And the morbidity report requiring review does not contain resulted tests

  Scenario: I can view Morbidity Reports requiring review for a patient that were ordered by a Provider
    Given there is a provider named Doctor/Dr. "Lucien" "Sanchez"
    And the morbidity report was ordered by the provider
    When I view the documents requiring review for the patient
    Then the patient file has the morbidity report requiring review
    And the morbidity report requiring review was ordered by "Doctor/Dr." "Lucien" "Sanchez"

  Scenario: I can view Morbidity Reports requiring review for a patient that were reported by a facility
    Given there is an organization named "Darkplace Hospital"
    And the patient has a morbidity report reported by Darkplace Hospital
    And the morbidity report has not been processed
    When I view the documents requiring review for the patient
    Then the patient file has the morbidity report requiring review
    And the morbidity report requiring review was reported by "Darkplace Hospital"

  Scenario: I can view electronic Morbidity Reports requiring review for a patient
    Given  the morbidity report is electronic
    When I view the documents requiring review for the patient
    Then the patient file has the morbidity report requiring review
    And the morbidity report requiring review is electronic

  Scenario: I can view treatments of Morbidity Reports requiring review for a patient
    Given I can "view" any "treatment"
    And the morbidity report includes a Laser Surgery treatment
    And the morbidity report includes the custom "Chicken Soup" treatment
    When I view the documents requiring review for the patient
    Then the patient file has the morbidity report requiring review
    And the morbidity report requiring review contains the "Laser Surgery" treatment
    And the morbidity report requiring review contains the "Chicken Soup" treatment

  Scenario: I can only view treatments of Morbidity Reports requiring review for a patient if I can View Treatments
    Given the morbidity report includes a Laser Surgery treatment
    And the morbidity report includes the custom "Chicken Soup" treatment
    When I view the documents requiring review for the patient
    Then the patient file has the morbidity report requiring review
    And the morbidity report requiring review does not contain treatments

  Scenario:I can view Resulted Tests of Morbidity Reports requiring review for a patient
    Given the morbidity report has an Aldolase test with a coded result of above threshold
    And the morbidity report has a Digoxin test with a numeric result of "1013" (drop)
    And the morbidity report has a Paracentesis test with a text result of "yikes!"
    When I view the documents requiring review for the patient
    Then the patient file has the morbidity report requiring review
    And the morbidity report requiring review contains a "Aldolase" test with a result containing "above threshold"
    And the morbidity report requiring review contains a "Digoxin" test with a result containing "=1013 (drop)"
    And the morbidity report requiring review contains a "Paracentesis" test with a result containing "yikes!"
