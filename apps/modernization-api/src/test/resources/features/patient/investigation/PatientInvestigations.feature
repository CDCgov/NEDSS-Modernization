@patient-investigations
Feature: Patient Investigations

  Background:
    Given I am logged into NBS
    And I have a patient

  Scenario: I can retrieve all investigations for a patient
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    And the patient is a subject of an investigation
    When I view the patient investigations
    Then the profile has an associated investigation

  Scenario: I can retrieve closed investigations for a patient
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    And the patient is a subject of an investigation
    And the investigation has been closed
    When I view the patient investigations
    Then investigations are returned
    Then the 1st investigation has a "status" of "Closed"

  Scenario: I can retrieve open investigations for a patient
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    And the patient is a subject of an investigation
    And the investigation was started on 04/05/1974
    And the investigation is for the Mumps condition
    And the investigation is for ARBO within Gwinnett County
    When I view the open patient investigations
    Then investigations are returned
    Then the 1st investigation has a "status" of "Open"
    Then the 1st investigation has a "start date" of "1974-04-05"
    Then the 1st investigation has a "condition" of "Mumps"
    Then the 1st investigation has a "jurisdiction" of "Gwinnett County"

  Scenario: I cannot retrieve investigations for a patient not the subject of an investigation
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    When I view the patient investigations
    Then an empty array is returned

  Scenario: I cannot retrieve patient investigations without required permissions
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    And the patient is a subject of an investigation
    When I view the patient investigations
    Then the packet is blank

