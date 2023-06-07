@patient-profile-races
  Feature: Patient Profile Races

    Background:
      Given I have a patient

    Scenario: I can retrieve patient races for a patient
      Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
      And the patient has a race
      Then the profile has associated races

    Scenario: I cannot retrieve patient races for a patient
      Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
      Then the profile has no associated races

    Scenario: I cannot retrieve patient races without proper authorities
      Given I have the authorities: "OTHER" for the jurisdiction: "ALL" and program area: "STD"
      Then the profile races are not accessible
