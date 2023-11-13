@patient-profile-investigation
Feature: Patient Profile Investigations

  Background:
    Given I am logged in
    And I have a patient

  Scenario: I can retrieve all investigations for a patient
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    When the patient is a subject of an investigation
    Then the profile has an associated investigation

  Scenario: I can retrieve closed investigations for a patient
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    When the patient is a subject of an investigation
    And the investigation has been closed
    Then the profile has an associated investigation
    And the profile has no associated open investigation

  Scenario: I can retrieve open investigations for a patient
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    When the patient is a subject of an investigation
    Then the profile has an associated open investigation

  Scenario: I cannot retrieve investigations for a patient not the subject of an investigation
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    Then the profile has no associated investigation

  Scenario: I cannot retrieve patient investigations without required permissions
    Given I can "Find" any "Patient" for "STD" within all jurisdictions
    Then the profile investigations are not accessible

  @web-interaction
  Scenario: An investigation is viewed from the Patient Profile
    Given I am logged into NBS
    And I can "Find" any "Patient" for "STD" within all jurisdictions
    And I can "VIEW" any "INVESTIGATION" for "STD" within all jurisdictions
    And the patient is a subject of an investigation
    When the investigation is viewed from the Patient Profile
    Then the classic profile is prepared to view an investigation
    And I am redirected to Classic NBS to view an Investigation

  @web-interaction
  Scenario: An investigation is viewed from the Patient Profile without required permissions
    Given the patient is a subject of an investigation
    When the investigation is viewed from the Patient Profile
    Then I am not allowed to view a Classic NBS Investigation

  @web-interaction
  Scenario: An investigation is added from the Patient Profile
    Given I can "Add" any "INVESTIGATION" for "STD" within all jurisdictions
    When an investigation is added from a Patient Profile
    Then the classic profile is prepared to add an investigation
    And I am redirected to Classic NBS to add an Investigation

  @web-interaction
  Scenario: An investigation is added from the Patient Profile without required permissions
    When an investigation is added from a Patient Profile
    Then I am not allowed to add a Classic NBS Investigation

  @web-interaction
  Scenario: Investigations are compared from the Patient Profile
    Given I can "MERGEINVESTIGATION" any "INVESTIGATION" for "STD" within all jurisdictions
    And the patient is a subject of 2 investigations
    When the investigations are compared from a Patient Profile
    Then the classic profile is prepared to compare investigations
    And I am redirected to Classic NBS to compare Investigation

  @web-interaction
  Scenario: Investigations are compared from the Patient Profile without required permissions
    Given the patient is a subject of 2 investigations
    When the investigations are compared from a Patient Profile
    Then I am not allowed to compare Classic NBS Investigations
