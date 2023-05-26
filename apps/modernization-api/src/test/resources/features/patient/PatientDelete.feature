@patient-delete
Feature: I can send a patient delete request

  Scenario: I can send a patient delete request
    Given I am logged into NBS
    And I have the authorities: "DELETE-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I have a patient
    When I send a patient delete request
    Then the delete request is posted to kafka

  Scenario Outline: I cant send delete requests without the proper permissions
    Given I am logged into NBS
    And I have the authorities: "<authorities>" for the jurisdiction: "ALL" and program area: "STD"
    And I have a patient
    When I send a patient delete request
    Then I get an access denied exception for patient delete

    Examples:
      | authorities    |
      | FIND-PATIENT   |
      | VIEW-PATIENT   |
      | DELETE-PATIENT |
