@patient_extended_create
Feature: Creation of Patients with extended ethnicity data

  Background:
    Given I am logged into NBS
    And I can "add" any "patient"
    And I can "viewworkup" any "patient"

  Scenario: I can create a patient with ethnicity information
    Given I am entering the ethnicity as of date 05/29/2023
    And I enter the ethnicity ethnic group Unknown
    And I enter the ethnicity unknown reason Refused to answer
    And the ethnicity is included in the extended patient data
    When I create a patient with extended data
    Then I view the patient's ethnicity demographics
    And the patient file ethnicity demographics are as of 05/29/2023
    And the patient file ethnicity demographics is unknown with the reason being Refused to answer

  Scenario: I can create a patient with detailed ethnicity information
    Given I am entering the ethnicity as of date 05/11/2023
    And I enter the ethnicity ethnic group Hispanic or Latino
    And I enter the ethnicity detailed Spaniard
    And I enter the ethnicity detailed Cuban
    And the ethnicity is included in the extended patient data
    When I create a patient with extended data
    Then I view the patient's ethnicity demographics
    And the patient file ethnicity demographics are as of 05/11/2023
    And the patient file ethnicity demographics has the ethnicity Hispanic or Latino
    And the patient file ethnicity demographics includes the Spanish origin Spaniard
    And the patient file ethnicity demographics includes the Spanish origin Cuban

  @CNFT1-3340
  Scenario: When adding a patient, the ethnic group is required for the ethnicity demographics to be saved
    Given I am entering the ethnicity as of date 05/11/2023
    And the ethnicity is included in the extended patient data
    When I create a patient with extended data
    And I view the patient's ethnicity demographics
    Then no value is returned

