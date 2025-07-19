@patient-edit
Feature: Editing of Patient administrative information

    Background:
      Given I am logged into NBS
      And I can "edit" any "patient"
      And I can "viewworkup" any "patient"
      And I have a patient

    Scenario: I can edit a patient with administrative demographics information
      Given I enter the patient administrative as of date 05/29/2023
      And I enter the administrative comment "testing extended patient data"
      When I edit a patient with entered demographics
      And I view the patient's Administrative information
      Then the patient file administrative information has the as of date 05/29/2023
      And the patient file administrative information has the comment "testing extended patient data"
      And the patient history contains the previous version
