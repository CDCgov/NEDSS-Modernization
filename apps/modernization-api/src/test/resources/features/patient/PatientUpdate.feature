@patient_update
Feature: I can send a patient update request

  Scenario: I can send a patient update requests
    Given I have the authorities: "FIND-PATIENT,EDIT-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When I send a "<updateType>" update request
    Then the "<updateType>" update request is posted to kafka

    Examples: 
      | updateType    |
      | general info  |
      | sex and birth |
      | mortality     |

  @patient_update_permissions
  Scenario: I cant send update requests without the proper permissions
    Given I have the authorities: "<authorities>" for the jurisdiction: "ALL" and program area: "STD"
    When I send a "<updateType>" update request
    Then I get an access denied exception for patient update

    Examples: 
      | updateType    | authorities               |
      | general info  | FIND-PATIENT,EDIT-PATIENT |
      | general info  | FIND-PATIENT,VIEW-PATIENT |
      | general info  | EDIT-PATIENT,VIEW-PATIENT |
      | sex and birth | FIND-PATIENT,EDIT-PATIENT |
      | sex and birth | FIND-PATIENT,VIEW-PATIENT |
      | sex and birth | EDIT-PATIENT,VIEW-PATIENT |
      | mortality     | FIND-PATIENT,EDIT-PATIENT |
      | mortality     | FIND-PATIENT,VIEW-PATIENT |
      | mortality     | EDIT-PATIENT,VIEW-PATIENT |
