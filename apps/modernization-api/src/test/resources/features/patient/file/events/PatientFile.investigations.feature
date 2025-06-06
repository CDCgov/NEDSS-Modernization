@patient-investigations
Feature: Patient File Investigations

  Background:
    Given I am logged into NBS
    And I can "View" any "Investigation"
    And I have a patient
    And the patient is a subject of an investigation

  Scenario: I can retrieve all investigations for a patient
    Given the patient is a subject of an investigation
    When I view the investigations for a patient

  Scenario: I can retrieve closed investigations for a patient
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    And the patient is a subject of an investigation
    And the investigation has been closed
    When I view the investigations for a patient
    Then the 1st investigation has a "status" of "Closed"

  Scenario: I can retrieve open investigations for a patient
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    And the patient is a subject of an investigation
    And the investigation was started on 04/05/1974
    And the investigation is for the Mumps condition
    And the investigation is for STD within Gwinnett County
    When I view the open investigations for a patient
    Then the 1st investigation has a "status" of "Open"
    Then the 1st investigation has a "start date" of "1974-04-05"
    Then the 1st investigation has a "condition" of "Mumps"
    Then the 1st investigation has a "jurisdiction" of "Gwinnett County"

  Scenario: I cannot retrieve investigations for a patient not the subject of an investigation
    Given I have another patient
    When I view the investigations for a patient
    Then no values are returned
