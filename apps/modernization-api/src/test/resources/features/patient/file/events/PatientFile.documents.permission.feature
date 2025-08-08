@patient-file-document
Feature: Patient File documents Permissions

  Background:
    Given I am logged in
    And there is a program area named "Spattergroit"
    And there is a jurisdiction named "Wizarding World"
    And there is a program area named "Gray Brittle Death"
    And there is a jurisdiction named "Arkham"
    And I have a patient
    And the patient has a Case Report

  Scenario: I cannot retrieve Documents for a patient without proper permission
    When I view the documents for the patient
    Then I am not allowed due to insufficient permissions

  Scenario: I can only view Documents for a patient within Program areas that I have access to
    Given I can "view" any "document" for Spattergroit in Wizarding World
    And  the case report is for Spattergroit within Wizarding World
    When I view the documents for the patient
    Then the patient file has the case report

  Scenario: I can not view Documents for a patient for Program areas that I have access to
    Given I can "view" any "document" for Spattergroit in Wizarding World
    And  the case report is for Gray Brittle Death within Wizarding World
    When I view the documents for the patient
    Then no values are returned

  Scenario: I can only view Documents for a patient for Jurisdictions that I have access to
    Given I can "view" any "document" for Gray Brittle Death in Arkham
    And  the case report is for Gray Brittle Death within Arkham
    When I view the documents for the patient
    Then the patient file has the case report

  Scenario: I can not view Documents for a patient for Jurisdictions that I have access to
    Given I can "view" any "document" for Gray Brittle Death in Arkham
    And  the case report is for Spattergroit within Arkham
    When I view the documents for the patient
    Then no values are returned
