@patient-delete
Feature: Patient Delete

  Background:
    Given I have a patient
    And I am logged into NBS

  Scenario: I can delete a patient that is not associated with events
    Given I have the authorities: "DELETE-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When I delete the patient
    Then the patient is deleted

  Scenario Outline: I delete a patient without the proper permissions
    Given  I have the authorities: "<authorities>" for the jurisdiction: "ALL" and program area: "STD"
    When I delete the patient
    Then I am not allowed to delete the patient

    Examples:
      | authorities    |
      | FIND-PATIENT   |
      | VIEW-PATIENT   |
      | DELETE-PATIENT |

  Scenario: I can not delete a patient that does not exist
    Given I have the authorities: "DELETE-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When I delete an unknown patient
    Then there is no patient to delete

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "DELETE-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is a subject of an investigation
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "DELETE-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a lab Report
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "DELETE-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a Morbidity Report
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "DELETE-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is vaccinated
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "DELETE-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is a subject of an investigation
    And the patient is a subject of a Treatment
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "DELETE-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient has a Case Report
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "DELETE-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is a subject of an investigation
    And the patient names a contact
    When I delete the patient
    Then the patient is not deleted because of an association with an event

  Scenario: I can not delete a patient that is associated with events
    Given I have the authorities: "DELETE-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And the patient is named as a contact
    When I delete the patient
    Then the patient is not deleted because of an association with an event
