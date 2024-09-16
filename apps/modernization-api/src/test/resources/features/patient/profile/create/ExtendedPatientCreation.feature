@patient_extended_create
  Feature: Creation of Patients with extended data

    Background:
      Given I am logged into NBS
      And I can "add" any "patient"
      And I can "find" any "patient"

    Scenario: I can create a patient with minimal data
      Given I am entering extended data for a new patient as of 05/11/2023
      When I create a patient with extended data
      Then a new patient is created
      And the new patient's identifier is returned
      And the new patient's local identifier is returned
      And the new patient's short identifier is returned
