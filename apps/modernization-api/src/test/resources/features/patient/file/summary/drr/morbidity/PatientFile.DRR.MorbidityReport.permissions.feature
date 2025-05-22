@documents-requiring-review @morbidity-report
Feature: Patient File Documents Requiring Review: Morbidity Report Security Assignment

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And there is a program area named "Greyscale"
    And there is a jurisdiction named "Old Valria"
    And there is a program area named "Krippin"
    And there is a jurisdiction named "Greenwich Village"
    And I have a patient
    And the patient has a Morbidity Report
    
  Scenario: I can only view Morbidity Reports requiring review for a patient for Program areas that I have access to
    Given I can "view" any "ObservationMorbidityReport" for Krippin in Greenwich Village
    And the morbidity report has not been processed
    And  the morbidity report is for Krippin within Greenwich Village
    When I view the documents requiring review for the patient
    Then the patient file has the Morbidity Report requiring review

  Scenario: I can not view Morbidity Reports requiring review for a patient for Program areas that I have access to
    Given I can "view" any "ObservationMorbidityReport" for Krippin in Greenwich Village
    And the morbidity report has not been processed
    And  the morbidity report is for Greyscale within Greenwich Village
    When I view the documents requiring review for the patient
    Then the patient file has no Morbidity Reports requiring review

  Scenario: I can only view Morbidity Reports requiring review for a patient for Jurisdictions that I have access to
    Given I can "view" any "ObservationMorbidityReport" for Greyscale in Old Valria
    And the morbidity report has not been processed
    And  the morbidity report is for Greyscale within Old Valria
    When I view the documents requiring review for the patient
    Then the patient file has the Morbidity Report requiring review

  Scenario: I can not view Morbidity Reports requiring review for a patient for Jurisdictions that I have access to
    Given I can "view" any "ObservationMorbidityReport" for Greyscale in Old Valria
    And the morbidity report has not been processed
    And  the morbidity report is for Krippin within Old Valria
    When I view the documents requiring review for the patient
    Then the patient file has no Morbidity Reports requiring review

