@patient-file-birth-record
@patient-file-birth-record-redirect
Feature: Patient File Birth record redirect

  Background:
    Given I have a patient
    And I am logged in

  Scenario: A birth record is added from the Patient file
    Given I can "ADD" any "BirthRecord"
    When a birth record is added from the Patient file
    Then NBS is prepared to add a birth record
    And I am redirected to Classic NBS to add a birth record

  Scenario: A birth record is added from the Patient file without required permissions
    When a birth record is added from the Patient file
    Then I am not allowed due to insufficient permissions
