@patient-file-patient-named-by-contact
Feature: Patient File Patient named by contact permission

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And there is a program area named "Gray Brittle Death"
    And there is a jurisdiction named "Arkham"
    And there is a program area named "Spattergroit"
    And there is a jurisdiction named "Wizarding World"
    And I have a patient

  Scenario: I cannot retrieve Contacts named by a patient without proper permission
    When I view the contacts that named the patient
    Then I am not allowed due to insufficient permissions

  Scenario: I can only view Contacts named by a patient a patient within Program areas that I have access to
    Given I can "view" any "ct_contact" for Spattergroit in Wizarding World
    And the patient is a subject of an investigation for Spattergroit within Wizarding World
    And I have another patient
    And the patient was named as a contact in the investigation
    When I view the contacts that named the patient
    Then the patient file has the patient named by the contact

  Scenario: I can not view Contacts named by a patient a patient for Program areas that I have access to
    Given I can "view" any "ct_contact" for Spattergroit in Wizarding World
    And the patient is a subject of an investigation for Gray Brittle Death within Wizarding World
    And I have another patient
    And the patient was named as a contact in the investigation
    When I view the contacts that named the patient
    Then no values are returned

  Scenario: I can only view Contacts named by a patient a patient for Jurisdictions that I have access to
    Given I can "view" any "ct_contact" for Gray Brittle Death in Arkham
    And the patient is a subject of an investigation for Gray Brittle Death within Arkham
    And I have another patient
    And the patient was named as a contact in the investigation
    When I view the contacts that named the patient
    Then the patient file has the patient named by the contact

  Scenario: I can not view Contacts named by a patient a patient for Jurisdictions that I have access to
    Given I can "view" any "ct_contact" for Gray Brittle Death in Arkham
    And the patient is a subject of an investigation for Spattergroit within Arkham
    And I have another patient
    And the patient was named as a contact in the investigation
    When I view the contacts that named the patient
    Then no values are returned
