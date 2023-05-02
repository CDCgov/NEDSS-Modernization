@patient-profile-birth
  Feature: Patient Profile Birth

    Background:
      Given I have a patient

    Scenario: I cannot retrieve patient birth for a patient without birth demographics
      Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
      Then the profile has no associated birth

    Scenario: I cannot retrieve patient birth without proper authorities
      Given I have the authorities: "OTHER" for the jurisdiction: "ALL" and program area: "STD"
      Then the profile birth is not accessible
