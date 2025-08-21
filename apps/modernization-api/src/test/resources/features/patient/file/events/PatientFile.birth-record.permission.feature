@patient-file-birth-record
Feature: Patient File Birth records Permissions

  Background:
    Given I am logged in
    And I have a patient
    And the patient has a birth record

  Scenario: I cannot retrieve Birth records for a patient without proper permission
    When I view the birth records for the patient
    Then I am not allowed due to insufficient permissions

  Scenario: I can only view Birth records for a patient with the proper permissions
    Given I can "view" any "BirthRecord"
    When I view the birth records for the patient
    Then the patient file has the birth record
