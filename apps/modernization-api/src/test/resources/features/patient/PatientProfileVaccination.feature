@patient-profile-vaccination
Feature: Patient Profile Vaccinations

  Background:
    Given I have a patient

  Scenario: A Vaccination is viewed from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I can "VIEW" any "INTERVENTIONVACCINERECORD"
    When the patient is vaccinated
    And the Vaccination is viewed from the Patient Profile
    And I am redirected to Classic NBS to view a Vaccination

  Scenario: A Vaccination is viewed from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    When the patient is vaccinated
    And the Vaccination is viewed from the Patient Profile
    Then I am not allowed to view a Classic NBS Vaccination

  Scenario: A vaccination is added from the Patient Profile
    Given I am logged into NBS and a security log entry exists
    And I can "ADD" any "INTERVENTIONVACCINERECORD"
    When a Vaccination is added from a Patient Profile
    Then the classic profile is prepared to add a Vaccination
    And I am redirected to Classic NBS to add a Vaccination

  Scenario: A vaccination is added from the Patient Profile without required permissions
    Given I am logged into NBS and a security log entry exists
    When a Vaccination is added from a Patient Profile
    Then I am not allowed to add a Classic NBS Vaccination
