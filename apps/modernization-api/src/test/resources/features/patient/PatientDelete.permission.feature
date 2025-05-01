@patient-delete
Feature: Patient Delete

  Background:
    Given I have a patient


  Scenario: I cannot delete a patient without logging in
    Given I am not logged in at all
    When I delete the patient
    Then I am not allowed due to insufficient permissions

  Scenario: I cannot delete a patient without the proper permissions
    When I delete the patient
    Then I am not allowed due to insufficient permissions




