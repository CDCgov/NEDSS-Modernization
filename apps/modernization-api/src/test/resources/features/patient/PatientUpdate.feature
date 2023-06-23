@patient_update
Feature: Patient Demographics Update
  Background:
    Given I have a patient
    And the patient has an address

  Scenario: I can update a patient's ethnicity
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a patient's ethnicity is changed
    Then the patient has the changed ethnicity
    And the patient ethnicity changed event is emitted

  Scenario: I cannot update a patient's ethnicity without proper permission
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    Then I am unable to change a patient's ethnicity
    And a patient event is not emitted

  Scenario Outline: I can send a patient update requests
    Given I have the authorities: "FIND-PATIENT,EDIT-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When I send a "<updateType>" update request
    Then the request has a response

    Examples:
      | updateType     |
      | administrative |

  @patient_update_permissions
  Scenario Outline: I cant send update requests without the proper permissions
    Given I have the authorities: "<authorities>" for the jurisdiction: "ALL" and program area: "STD"
    When I send a "<updateType>" update request
    Then I get an access denied exception for patient update

    Examples:
      | updateType     | authorities               |
      | administrative | FIND-PATIENT,EDIT-PATIENT |
      | administrative | FIND-PATIENT,VIEW-PATIENT |
      | administrative | EDIT-PATIENT,VIEW-PATIENT |
