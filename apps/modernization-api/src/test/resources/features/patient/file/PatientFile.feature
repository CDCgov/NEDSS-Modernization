@patient-file
Feature: Patient File

  Background:
    Given I am logged into NBS
    And I can "view" any "patient"
    And I have a patient

  Scenario: I cannot view Patient file of the patient that does not exist
    When I view the Patient File for patient 947
    Then it was not found

  Scenario: I can retrieve the patient file header for a patient
    Given the patient was born on 06/19/1977
    And the patient has the gender Unknown
    And the patient died on 08/07/2015
    When I view the Patient File
    Then the patient file has patient unique identifier
    And the patient file has patient local identifier
    And the patient file has a status of "ACTIVE"
    And the patient file has the patient born on "06/19/1977"
    And the patient file has the patient deceased on "08/07/2015"
    And the patient file does not contain a name
    And the patient file can be deleted

  Scenario: The Patient file of a patient contains the most recent legal name
    Given the patient has the legal name "Joe" "Jacob" "Smith", Jr. as of 01/01/2020
    And the patient has the Alias name "Natalie" "Caine"
    And the patient has the legal name "Verity" "Green" as of 08/17/2017
    When I view the Patient File
    Then the patient file has a first name of "Joe"
    And the patient file has a middle name of "Jacob"
    And the patient file has a last name of "Smith"
    And the patient file has a name suffix of "Jr."


  Scenario: I get a deletable patient when no events are associated
    When I view the Patient File
    Then the patient file can be deleted

  Scenario: A deleted Patient file should show as Inactive
    Given the patient is inactive
    When I view the Patient File
    Then the patient file has a status of "INACTIVE"
    And the patient file cannot be deleted because the patient is inactive

  Scenario: The Patient file of a patient that subject of an Investigation should not be deletable
    Given the patient is a subject of an investigation
    When I view the Patient File
    Then the patient file cannot be deleted because the patient has associations

  Scenario: The Patient file of a patient that subject of a Lab Report should not be deletable
    Given the patient has a lab Report
    When I view the Patient File
    Then the patient file cannot be deleted because the patient has associations

  Scenario: The Patient file of a patient that subject of a Morbidity Report should not be deletable
    Given the patient has a Morbidity Report
    When I view the Patient File
    Then the patient file cannot be deleted because the patient has associations

  Scenario: The Patient file of a patient that is vaccinated should not be deletable
    Given the patient is vaccinated
    When I view the Patient File
    Then the patient file cannot be deleted because the patient has associations

  Scenario: The Patient file of a patient that subject of a treatment should not be deletable
    Given the patient is a subject of an investigation
    And the patient is a subject of a Treatment
    When I view the Patient File
    Then the patient file cannot be deleted because the patient has associations

  Scenario: The Patient file of a patient associated with a Case Report should not be deletable
    Given the patient has a Case Report
    When I view the Patient File
    Then the patient file cannot be deleted because the patient has associations

  Scenario: The Patient file of a patient that names a contact should not be deletable
    Given the patient is a subject of an investigation
    And the patient names a contact
    When I view the Patient File
    Then the patient file cannot be deleted because the patient has associations

  Scenario: The Patient file of a patient that is named as a contact should not be deletable
    Given the patient is named as a contact
    When I view the Patient File
    Then the patient file cannot be deleted because the patient has associations
