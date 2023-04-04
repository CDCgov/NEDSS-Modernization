@patient_delete
Feature: I can send a patient delete request

  Scenario: I can send a patient delete request
    Given I have the authorities: "DELETE-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When I send a patient delete request
    Then the delete request is posted to kafka

  @patient_delete_permissions
  Scenario: I cant send delete requests without the proper permissions
    Given I have the authorities: "<authorities>" for the jurisdiction: "ALL" and program area: "STD"
    When I send a patient delete request
    Then I get an access denied exception for patient delete

    Examples: 
      | authorities    |
      | FIND-PATIENT   |
      | VIEW-PATIENT   |
      | DELETE-PATIENT |
