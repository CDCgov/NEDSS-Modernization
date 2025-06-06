@patient-investigations
Feature: Patient File Investigations permissions

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And there is a program area named "Gray Brittle Death"
    And there is a jurisdiction named "Arkham"
    And there is a program area named "Spattergroit"
    And there is a jurisdiction named "Wizarding World"
    And I have a patient
    And the patient is a subject of an investigation for Spattergroit within Wizarding World
    And the patient is a subject of an investigation for Spattergroit within Arkham
    And the patient is a subject of an investigation for Gray Brittle Death within Wizarding World
    And the patient is a subject of an investigation for Gray Brittle Death within Arkham

  Scenario: I cannot retrieve investigations for a patient without proper authorities
    When I view the investigations for a patient
    Then I am not allowed due to insufficient permissions


  Scenario: I can only view Investigations for a patient for Program areas that I have access to
    Given I can "view" any "investigation" for Spattergroit in Wizarding World
    When I view the investigations for a patient
    Then the patient file has 1 investigation

