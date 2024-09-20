@patient_extended_create
  Feature: Creation of Patients with extended ethnicity data

    Background:
      Given I am logged into NBS
      And I can "add" any "patient"
      And I can "find" any "patient"

    Scenario: I can create a patient with ethnicity information
      Given I am entering the ethnicity as of date 05/29/2023
      And I enter the ethnicity ethnic group "ethnic1"
      And I enter the ethnicity unknown reason "reason1"
      And the ethnicity is included in the extended patient data
      When I create a patient with extended data
      Then I view the Patient Profile Ethnicity
      And the patient profile ethnicity has the as of date 05/29/2023
      And the patient profile ethnicity has the ethnic group "ethnic1"
      And the patient profile ethnicity has the unknown reason "reason1"
