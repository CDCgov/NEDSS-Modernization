@patient-profile-races  @web-interaction
Feature: Patient Profile Races Permission

  Background:
    Given I am logged in
    And I have a patient
    And the patient has a race

  Scenario: I cannot retrieve patient races without proper authorities
    When I view the Patient Profile Races
    Then I am not allowed due to insufficient permissions
