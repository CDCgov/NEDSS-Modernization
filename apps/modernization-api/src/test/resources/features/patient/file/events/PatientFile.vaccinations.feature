@patient-file-vaccination
Feature: Patient File Vaccinations

  Background:
    Given I am logged in
    And I can "view" any "INTERVENTIONVACCINERECORD"
    And I have a patient

  Scenario: I cannot retrieve vaccinations for a patient not the subject of any vaccinations
    When I view the vaccinations for the patient
    Then no values are returned

  Scenario: I can retrieve Vaccinations for a patient
    Given the patient is vaccinated with plague
    And the vaccination was administered on 02/03/2015
    And the vaccination was created on 04/25/2010 at 11:15:17
    When I view the vaccinations for the patient
    And the patient file has the vaccine created on 04/25/2010 at 11:15:17
    And the patient file has the "plague" vaccination administered
    And the patient file has the vaccination administered on 02/03/2015
    And the patient file has the vaccination is not associated with any investigations

  Scenario: I can retrieve performed Vaccinations for a patient
    Given there is an organization named "Eastman Medical Center"
    And there is a provider named "Douglas" "Howser"
    And the patient is vaccinated
    And the vaccination was performed at Eastman Medical Center
    And the vaccination was performed by the provider
    When I view the vaccinations for the patient
    Then the patient file has the vaccination performed at "Eastman Medical Center"
    And the patient file has the vaccination performed by "Douglas" "Howser"

  Scenario: I can retrieve Vaccinations with associated investigations for a patient
    Given there is a program area named "Geostigma"
    And there is a jurisdiction named "Midgar"
    And the patient is a subject of an investigation for Geostigma within Midgar
    And I can "view" any "investigation" for Geostigma in Midgar
    And the patient is vaccinated
    And the vaccination is associated with the investigation
    When I view the vaccinations for the patient
    Then the patient file has the vaccination associated with the investigation

  Scenario: I cannot view associated investigations when retrieving Vaccinations for a patient when I do not have permission
    Given there is a program area named "Geostigma"
    And there is a jurisdiction named "Midgar"
    And the patient is a subject of an investigation for Geostigma within Midgar
    And the patient is vaccinated
    And the vaccination is associated with the investigation
    When I view the vaccinations for the patient
    Then the patient file has the vaccination is not associated with any investigations
