@patient_extended_create
Feature: Creation of Patients with gender demographics

  Background:
    Given I am logged into NBS
    And I can "add" any "patient"
    And I can "view" any "patient"

  Scenario: I can create a patient with gender demographics information
    Given I enter the gender demographics as of date 07/03/1990
    And I enter the gender demographics with the current gender of Female
    And I enter the gender demographics with the additional gender "another gender"
    And I enter the gender demographics with a preferred gender of FTM
    And the gender demographics are included in the extended patient data
    When I create a patient with extended data
    And I view the patient's gender demographics
    Then the patient file gender demographics are as of 07/03/1990
    And the patient file gender demographics has the current gender of Female
    And the patient file gender demographics has the "another gender" additional gender
    And the patient file gender demographics has a preferred gender of FTM

  Scenario: I can create a patient with unknown gender demographics
    Given I enter the gender demographics as of date 04/06/2010
    And I enter the gender demographics with the unknown reason of did not ask
    And the gender demographics are included in the extended patient data
    When I create a patient with extended data
    And I view the patient's gender demographics
    Then the patient's current gender is unknown with the reason being did not ask
