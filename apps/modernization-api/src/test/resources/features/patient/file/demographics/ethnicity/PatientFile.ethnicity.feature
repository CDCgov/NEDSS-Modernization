@patient-file @patient-ethnicity-demographics
Feature: Viewing the ethnicity demographics of a patient

  Background:
    Given I am logged into NBS
    And I can "view" any "patient"
    And I have a patient

  Scenario: I can view a Patient's ethnicity demographics
    Given the patient has the Not Hispanic or Latino ethnicity as of 11/14/2022
    When I view the patient's ethnicity demographics
    Then the patient file ethnicity demographics are as of 11/14/2022
    And the patient file ethnicity demographics has the ethnicity Not Hispanic or Latino
    And the patient file ethnicity demographics does not include Spanish origins

  Scenario: I can view a Patient's detailed ethnicity demographics
    Given the patient has the ethnicity Hispanic or Latino
    And the patient ethnicity includes a Spanish origin of Cuban
    And the patient ethnicity includes a Spanish origin of Central American
    When I view the patient's ethnicity demographics
    And the patient file ethnicity demographics has the ethnicity Hispanic or Latino
    And the patient file ethnicity demographics includes the Spanish origin Cuban
    And the patient file ethnicity demographics includes the Spanish origin Central American

  Scenario: I can view a Patient's unknown ethnicity demographics
    Given the patient's ethnicity is unknown with the reason being Not asked
    When I view the patient's ethnicity demographics
    Then the patient file ethnicity demographics is unknown with the reason being Not asked

  Scenario: I can not view a Patient's ethnicity demographics if there aren't any
    When I view the patient's ethnicity demographics
    Then no value is returned
