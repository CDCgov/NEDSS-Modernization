@patient-profile-identifications @web-interaction
  Feature: Patient Profile Identification Permissions

    Background:
      Given I am logged in
      And I have a patient

    Scenario: I cannot retrieve patient identifications without proper authorities
      When I view the Patient Profile Identification
      Then I am not allowed due to insufficient permissions
