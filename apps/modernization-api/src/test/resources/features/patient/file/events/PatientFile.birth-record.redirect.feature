@patient-file-birth-record
@patient-file-birth-record-redirect
Feature: Patient File Birth record redirect

  Background:
    Given I am logged in
    And I have a patient
    And the patient has a birth record

  Scenario: A Birth record is added from the Patient file
    Given I can "ADD" any "BirthRecord"
    When a birth record is added from the Patient file
    Then NBS is prepared to add a birth record
    And I am redirected to Classic NBS to add a birth record

  Scenario: A Birth record is added from the Patient file without required permissions
    When a birth record is added from the Patient file
    Then I am not allowed due to insufficient permissions

  Scenario: A Birth record is viewed from the Patient file
    Given I can "VIEW" any "BirthRecord"
    When the birth record is viewed from the Patient file
    Then NBS is prepared to view a birth record
    And I am redirected to Classic NBS to view a birth record

  Scenario: A Birth record is viewed from the Patient Profile without required permissions
    When the birth record is viewed from the Patient file
    Then I am not allowed due to insufficient permissions
