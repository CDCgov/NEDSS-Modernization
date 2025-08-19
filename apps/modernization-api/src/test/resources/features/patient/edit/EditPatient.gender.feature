@patient-edit
Feature: Editing of Patient gender demographics

  Background:
    Given I am logged into NBS
    And I can "edit" any "patient"
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can edit a patient with gender demographics information
    Given I enter the gender demographics as of date 07/03/1990
    And I enter the gender demographics with the current gender of Female
    And I enter the gender demographics with the additional gender "another gender"
    And I enter the gender demographics with a preferred gender of FTM
    When I edit the patient with entered demographics
    And I view the patient's gender demographics
    Then the patient file gender demographics are as of 07/03/1990
    And the patient file gender demographics has the current gender of Female
    And the patient file gender demographics has the "another gender" additional gender
    And the patient file gender demographics has a preferred gender of FTM
    And the patient history contains the previous version

  Scenario: I can edit a patient with unknown gender demographics
    Given I enter the gender demographics as of date 04/06/2010
    And I enter the gender demographics with the unknown reason of did not ask
    When I edit the patient with entered demographics
    And I view the patient's gender demographics
    Then the patient's current gender is unknown with the reason being did not ask
    And the patient history contains the previous version

  Scenario: I can clear the gender demographics of a patient
    Given the patient's gender as of 07/11/2013 is Female
    And  the patient has the "additional" additional gender
    And the patient has a preferred gender of Transgender Unspecified
    When I edit the patient with entered demographics
    And I view the patient's gender demographics
    Then no value is returned
