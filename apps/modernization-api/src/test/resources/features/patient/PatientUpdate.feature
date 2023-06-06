@patient_update
Feature: Patient Demographics Update

  Scenario: I can update a patient
    Given I am logged into NBS
    And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    And I have a patient
    When a patient's ethnicity is changed
    Then the patient has the changed ethnicity

  Scenario Outline: I can send a patient update requests
    Given I have the authorities: "FIND-PATIENT,EDIT-PATIENT,VIEW-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When I send a "<updateType>" update request
    Then the "<updateType>" update request is posted to kafka

    Examples:
      | updateType     |
      | general info   |
      | sex and birth  |
      | mortality      |
      | administrative |
      | name           |
      | address        |
      | email          |
      | identification |
      | phone          |
      | race           |

  @patient_update_permissions
  Scenario Outline: I cant send update requests without the proper permissions
    Given I have the authorities: "<authorities>" for the jurisdiction: "ALL" and program area: "STD"
    When I send a "<updateType>" update request
    Then I get an access denied exception for patient update

    Examples:
      | updateType     | authorities               |
      | general info   | FIND-PATIENT,EDIT-PATIENT |
      | general info   | FIND-PATIENT,VIEW-PATIENT |
      | general info   | EDIT-PATIENT,VIEW-PATIENT |
      | sex and birth  | FIND-PATIENT,EDIT-PATIENT |
      | sex and birth  | FIND-PATIENT,VIEW-PATIENT |
      | sex and birth  | EDIT-PATIENT,VIEW-PATIENT |
      | administrative | FIND-PATIENT,EDIT-PATIENT |
      | administrative | FIND-PATIENT,VIEW-PATIENT |
      | administrative | EDIT-PATIENT,VIEW-PATIENT |
      | name           | FIND-PATIENT,EDIT-PATIENT |
      | name           | FIND-PATIENT,VIEW-PATIENT |
      | name           | EDIT-PATIENT,VIEW-PATIENT |
      | address        | FIND-PATIENT,EDIT-PATIENT |
      | address        | FIND-PATIENT,VIEW-PATIENT |
      | address        | EDIT-PATIENT,VIEW-PATIENT |
      | race           | FIND-PATIENT,EDIT-PATIENT |
      | race           | FIND-PATIENT,VIEW-PATIENT |
      | race           | EDIT-PATIENT,VIEW-PATIENT |
      | mortality      | FIND-PATIENT,EDIT-PATIENT |
      | mortality      | FIND-PATIENT,VIEW-PATIENT |
      | mortality      | EDIT-PATIENT,VIEW-PATIENT |
      | phone          | FIND-PATIENT,EDIT-PATIENT |
      | phone          | FIND-PATIENT,VIEW-PATIENT |
      | phone          | EDIT-PATIENT,VIEW-PATIENT |
      | email          | FIND-PATIENT,EDIT-PATIENT |
      | email          | FIND-PATIENT,VIEW-PATIENT |
      | email          | EDIT-PATIENT,VIEW-PATIENT |
      | identification | FIND-PATIENT,EDIT-PATIENT |
      | identification | FIND-PATIENT,VIEW-PATIENT |
      | identification | EDIT-PATIENT,VIEW-PATIENT |
