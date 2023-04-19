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
