@patient_extended_create
  Feature: Creation of Patients with extended administrative data

    Background:
      Given I am logged into NBS
      And I can "add" any "patient"
      And I can "view" any "patient"

    Scenario: I can create a patient with administrative demographics information
      Given I enter the patient administrative as of date 05/29/2023
      And I enter the administrative comment "testing extended patient data"
      And the administrative is included in the extended patient data
      When I create a patient with extended data
      Then I view the patient's Administrative information
      And the patient file administrative information has the as of date 05/29/2023
      And the patient file administrative information has the comment "testing extended patient data"
