@patient-profile-vaccination
Feature: Patient Profile Vaccinations

  Background:
    Given I have a patient

  Scenario: I can retrieve vaccinations for a patient
    Given I have the authorities: "FIND-PATIENT,VIEW-INTERVENTIONVACCINERECORD" for the jurisdiction: "ALL" and program area: "STD"
    When the patient is vaccinated
    Then the profile has an associated vaccination

  Scenario: I cannot retrieve vaccinations for a patient without vaccinations
    Given I have the authorities: "FIND-PATIENT,VIEW-INTERVENTIONVACCINERECORD" for the jurisdiction: "ALL" and program area: "STD"
    Then the profile has no associated vaccination

    Scenario: I cannot retrieve vaccinations without proper authorities
      Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
      Then the profile vaccinations are not accessible

  Scenario: A Vaccination is viewed from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I have the authorities: "VIEW-INTERVENTIONVACCINERECORD" for the jurisdiction: "ALL" and program area: "STD"
    When the patient is vaccinated
    And the Vaccination is viewed from the Patient Profile
    And I am redirected to Classic NBS to view a Vaccination

  Scenario: A Vaccination is viewed from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    And I have the authorities: "OTHER" for the jurisdiction: "ALL" and program area: "STD"
    When the patient is vaccinated
    And the Vaccination is viewed from the Patient Profile
    Then I am not allowed to view a Classic NBS Vaccination

  Scenario: A vaccination is added from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I have the authorities: "ADD-INTERVENTIONVACCINERECORD" for the jurisdiction: "ALL" and program area: "STD"
    When a Vaccination is added from a Patient Profile
    Then the classic profile is prepared to add a Vaccination
    And I am redirected to Classic NBS to add a Vaccination

  Scenario: A vaccination is added from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    And I have the authorities: "OTHER" for the jurisdiction: "ALL" and program area: "STD"
    When a Vaccination is added from a Patient Profile
    Then I am not allowed to add a Classic NBS Vaccination
