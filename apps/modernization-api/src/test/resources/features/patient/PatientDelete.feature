@patient-delete
Feature: Patient Delete

  Background:
    Given I am logged into NBS
    And I can "viewworkup" any "patient"
    And I can "delete" any "patient"
    And I have a patient

  Scenario: I can delete a patient that is not associated with events
    When I delete the patient
    Then the patient is deleted

  Scenario: I can not delete a patient that does not exist
    When I delete an unknown patient
    Then I am unable to delete the patient because it was not found

  Scenario: I can not delete a patient that is associated with an investigation
    Given the patient is a subject of an investigation
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that is associated with a Lab Report
    Given the patient has a lab Report
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that is associated with a Morbidity Report
    Given the patient has a Morbidity Report
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that is associated with a vaccination
    Given the patient is vaccinated
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that is associated with a treatment
    Given the patient is a subject of an investigation
    And the patient is a subject of a Treatment
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that is associated with a Case Report
    Given the patient has a Case Report
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that named a contact
    Given the patient is a subject of an investigation
    And the patient names a contact
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that was named as a contact
    Given the patient is named as a contact
    When I delete the patient
    Then the patient is not deleted because of an association with an event
