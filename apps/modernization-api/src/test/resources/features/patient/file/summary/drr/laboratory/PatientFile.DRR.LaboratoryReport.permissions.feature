@documents-requiring-review @laboratory-report
Feature: Patient File Documents Requiring Review: Laboratory Report Security Assignment

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And there is a program area named "Gray Brittle Death"
    And there is a jurisdiction named "Arkham"
    And there is a program area named "Spattergroit"
    And there is a jurisdiction named "Wizarding World"
    And I have a patient
    And the patient has a Lab Report
    
  Scenario: I can only view Laboratory Reports requiring review for a patient for Program areas that I have access to
    Given I can "view" any "ObservationLabReport" for Spattergroit in Wizarding World
    And the lab report has not been processed
    And  the lab report is for Spattergroit within Wizarding World
    When I view the documents requiring review for the patient
    Then the patient file has the Laboratory Report requiring review

  Scenario: I can not view Laboratory Reports requiring review for a patient for Program areas that I have access to
    Given I can "view" any "ObservationLabReport" for Spattergroit in Wizarding World
    And the lab report has not been processed
    And  the lab report is for Gray Brittle Death within Wizarding World
    When I view the documents requiring review for the patient
    Then the patient file has no Laboratory Reports requiring review

  Scenario: I can only view Laboratory Reports requiring review for a patient for Jurisdictions that I have access to
    Given I can "view" any "ObservationLabReport" for Gray Brittle Death in Arkham
    And the lab report has not been processed
    And  the lab report is for Gray Brittle Death within Arkham
    When I view the documents requiring review for the patient
    Then the patient file has the Laboratory Report requiring review

  Scenario: I can not view Laboratory Reports requiring review for a patient for Jurisdictions that I have access to
    Given I can "view" any "ObservationLabReport" for Gray Brittle Death in Arkham
    And the lab report has not been processed
    And  the lab report is for Spattergroit within Arkham
    When I view the documents requiring review for the patient
    Then the patient file has no Laboratory Reports requiring review

