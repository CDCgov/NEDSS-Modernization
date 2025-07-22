@patient-file-vaccination
Feature: Patient File Vaccinations Permissions

  Background:
    Given I am logged in
    And I have a patient
    And the patient is vaccinated

  Scenario: I cannot retrieve Vaccinations for a patient without proper permission
    When I view the vaccinations for the patient
    Then I am not allowed due to insufficient permissions

  Scenario: I can only view Vaccinations for a patient
    Given I can "view" any "INTERVENTIONVACCINERECORD"
    When I view the vaccinations for the patient
    Then the patient file has the vaccination

