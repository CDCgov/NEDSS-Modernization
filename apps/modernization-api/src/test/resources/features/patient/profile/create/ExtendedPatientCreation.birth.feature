@patient_extended_create
Feature: Creation of Patients with birth demographics

  Background:
    Given I am logged into NBS
    And I can "add" any "patient"
    And I can "find" any "patient"

  Scenario: I can create a patient with birth demographics information
    Given I enter the birth demographics as of date 07/03/1990
    And I enter the birth demographics with the patient born on 04/31/1990
    And I enter the birth demographics with the patient born as Unknown
    And the birth demographics are included in the extended patient data
    When I create a patient with extended data
    And I view the Patient Profile Birth demographics
    Then the patient profile birth demographics has the as of date 07/03/1990
    And the patient profile birth demographics has the patient born on 04/31/1990
    And the patient profile birth demographics has patient born as Unknown

  Scenario: I can create a patient with multiple birth demographics information
    Given I enter the birth demographics with the patient multiple as Yes
    And I enter the birth demographics with the patient born 17th
    And the birth demographics are included in the extended patient data
    When I create a patient with extended data
    And I view the Patient Profile Birth demographics
    Then the patient profile birth demographics has Yes for multiple birth
    And the patient profile birth demographics has the patient born 17th

  Scenario: I can create a patient with birth location demographics
    Given I enter the birth demographics with the patient born in the city of "Salem Center"
    And I enter the birth demographics with the patient born in the county of Westchester County
    And I enter the birth demographics with the patient born in the state of New York
    And I enter the birth demographics with the patient born in the country of United States
    And the birth demographics are included in the extended patient data
    When I create a patient with extended data
    And I view the Patient Profile Birth demographics
    Then the patient profile birth demographics has the patient born in the city of "Salem Center"
    And the patient profile birth demographics has the patient born in the county of Westchester County
    And the patient profile birth demographics has the patient born in the state of New York
    And the patient profile birth demographics has the patient born in the country of United States
