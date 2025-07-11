@patient_extended_create
Feature: Creation of Patients with mortality data

  Background:
    Given I am logged into NBS
    And I can "add" any "patient"
    And I can "viewworkup" any "patient"

  Scenario: I can create a patient with mortality information
    Given I am entering the mortality as of date 06/29/2023
    And I enter the mortality deceased on date of 05/29/2023
    And I enter the mortality deceased option as Yes
    And I enter the mortality city "Salem Center"
    And I enter the mortality country United States
    And I enter the mortality county Westchester County
    And I enter the mortality state New York
    And the mortality demographics are included in the extended patient data
    When I create a patient with extended data
    Then I view the patient's mortality demographics
    And the patient file mortality demographics are as of 06/29/2023
    And the patient file mortality demographics has the patient deceased on 05/29/2023
    And the patient file mortality demographics shows that the patient is deceased
    And the patient file mortality demographics has the patient dying in the city of "Salem Center"
    And the patient file mortality demographics has the patient dying in the country of United States
    And the patient file mortality demographics has the patient dying in the county of Westchester County
    And the patient file mortality demographics has the patient dying in the state of New York
