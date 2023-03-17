@patient_update
Feature: I can send a patient update request

  Scenario: I can send a general info update
    Given I have the authorities: "FIND-PATIENT,EDIT-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When I send a "<updateType>" update request
    Then the "<updateType>" update request is posted to kafka

    Examples: 
      | updateType    |
      | general info  |
      | sex and birth |
      | mortality     |
