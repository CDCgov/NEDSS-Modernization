@patient_extended_create
  Feature: Creation of Patients with extended data

    Background:
      Given I am logged into NBS
      And I can "add" any "patient"
      And I can "find" any "patient"

    Scenario: I can create a patient with minimal data
      When I create a patient with extended data
      Then a new patient is created
      And the new patient's identifier is returned
      And the new patient's local identifier is returned
      And the new patient's short identifier is returned

    Scenario: I can create a patient and receive the legal name
      Given I am entering a legal name as of 07/19/2017
      And I enter the first name "Mary" on the current name
      And I enter the last name "Walker" on the current name
      And the name is included with the extended patient data
      When I create a patient with extended data
      Then a new patient is created
      And the new patient's identifier is returned
      And the new patient's local identifier is returned
      And the new patient's short identifier is returned
      And the new patient's name "Mary" "Walker" is returned
