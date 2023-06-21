@patient-profile-general-information-change
Feature: Patient Profile General Information Changes

  Background:
    Given I have a patient

Scenario: I can update a patient's general information
Given I am logged into NBS
And I have the authorities: "FIND-PATIENT,EDIT-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
When a patient's general information is changed
Then the patient has the changed general information

Scenario: I cannot update a patient's general information without proper permission
Given I am logged into NBS
And I have the authorities: "FIND-PATIENT" for the jurisdiction: "ALL" and program area: "STD"
Then I am unable to change a patient's general information
And a patient event is not emitted
