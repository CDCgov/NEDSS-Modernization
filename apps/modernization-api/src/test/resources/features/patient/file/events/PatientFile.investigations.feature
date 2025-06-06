@patient-file-investigations
Feature: Patient File Investigations

  Background:
    Given there is a program area named "Tyrant Virus"
    And there is a jurisdiction named "Racoon City"
    And I am logged into NBS
    And I can "View" any "Investigation" for Tyrant Virus in Racoon City
    And I have a patient
    And the patient is a subject of an investigation for Tyrant Virus within Racoon City

  Scenario: I can retrieve investigations for a patient
    Given the investigation was started on 04/05/1974
    And the investigation is for the Mumps condition
    When I view the investigations for a patient
    Then the 1st investigation is Open
    And the 1st investigation was started on 04/05/1974
    And the 1st investigation is for the "Mumps" condition
    And the 1st investigation is within the Racoon City jurisdiction

  Scenario: I can retrieve closed investigations for a patient
    Given the investigation has been closed
    And there is a provider named "Nancy" "Wheeler"
    And the investigation was investigated by the provider
    When I view the investigations for a patient
    Then the 1st investigation is Closed
    And the 1st investigation was investigated by the provider

  Scenario: I can retrieve open investigations for a patient
    Given the investigation was started on 04/05/1974
    And the investigation is for the Mumps condition
    When I view the open investigations for a patient
    Then the 1st investigation is Open
    And the 1st investigation was started on 04/05/1974
    And the 1st investigation is for the "Mumps" condition
    And the 1st investigation is within the Racoon City jurisdiction

  Scenario: I cannot retrieve investigations for a patient not the subject of an investigation
    Given I have another patient
    When I view the investigations for a patient
    Then no values are returned
