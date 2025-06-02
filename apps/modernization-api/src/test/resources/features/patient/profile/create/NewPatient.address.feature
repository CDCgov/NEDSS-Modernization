@patient-create
Feature: Creation of Patients with address demographics

  Background:
    Given I am logged into NBS
    And I can "add" any "patient"
    And I can "view" any "patient"

  Scenario: I can create a patient with an address demographic
    Given I am entering the  House - Home address at "6 Richmond Street" "Miami" "12345" as of 01/01/1999
    And the address is included with the extended patient data
    When I create a patient with extended data
