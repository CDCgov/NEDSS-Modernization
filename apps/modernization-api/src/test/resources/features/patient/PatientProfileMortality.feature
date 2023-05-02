@patient-profile-mortality
  Feature: Patient Profile Mortality

    Background:
      Given I have a patient

    Scenario: I cannot retrieve patient mortality for a patient without mortality demographics
      Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
      Then the profile has no associated mortality

    Scenario: I cannot retrieve patient mortality without proper authorities
      Given I have the authorities: "OTHER" for the jurisdiction: "ALL" and program area: "STD"
      Then the profile mortality is not accessible
