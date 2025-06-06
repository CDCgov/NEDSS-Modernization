@patient-profile
Feature: Patient Profile

  Background:
    Given I have a patient

  Scenario: I can retrieve a profile by patient identifier
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a profile is requested by patient identifier
    Then the profile is found

  Scenario: I can retrieve a profile by short identifier
    Given I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
    When a profile is requested by short identifier
    Then the profile is found

  Scenario: I cannot retrieve a profile without proper authorities
    Given I have the authorities: "NOTHING" for the jurisdiction: "ALL" and program area: "STD"
    When a profile is requested by patient identifier
    Then the profile is not accessible

  Scenario: I cannot retrieve a profile without proper authorities
    Given I have the authorities: "NOTHING" for the jurisdiction: "ALL" and program area: "STD"
    When a profile is requested by short identifier
    Then the profile is not accessible
    
