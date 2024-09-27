@patient_extended_create
  Feature: Creation of Patients with mortality data

    Background:
      Given I am logged into NBS
      And I can "add" any "patient"
      And I can "find" any "patient"

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
      Then I view the Patient Profile Mortality demographics
      And the patient profile mortality has the as of date 06/29/2023
      And the patient profile mortality has the deceased on date 05/29/2023
      And the patient profile mortality has the deceased option as Yes
      And the patient profile mortality has the city "Salem Center"
      And the patient profile mortality has the country United States
      And the patient profile mortality has the county Westchester County
      And the patient profile mortality has the state New York
