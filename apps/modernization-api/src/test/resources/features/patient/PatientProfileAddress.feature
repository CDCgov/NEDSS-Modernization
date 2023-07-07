@patient-profile-addresses
  Feature: Patient Profile Addresses

    Background:
      Given I have a patient

    Scenario: I can retrieve patient addresses for a patient
      Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
      And the patient has an address
      Then the profile has associated addresses

    Scenario: I cannot retrieve patient addresses for a patient
      Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
      Then the profile has no associated addresses

    Scenario: I cannot retrieve patient addresses without proper authorities
      Given I have the authorities: "OTHER" for the jurisdiction: "ALL" and program area: "STD"
      Then the profile addresses are not accessible
