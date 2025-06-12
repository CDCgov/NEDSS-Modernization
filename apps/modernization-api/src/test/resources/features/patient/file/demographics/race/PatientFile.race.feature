@patient-file @patient-race-demographics
Feature: Viewing the race demographics of a patient

  Background:
    Given I am logged into NBS
    And I can "view" any "patient"
    And I have a patient

  Scenario: I can view a Patient's race demographics
    Given the patient's race is Asian as of 01/19/1987
    And the patient's race is Other as of 03/11/2019
    When I view the patient's race demographics
    Then the 1st patient file race demographic has the category Other
    And the 1st patient file race demographic is as of 03/11/2019
    And the patient file race demographics does not include details for the Other race
    And the 2nd patient file race demographic has the category Asian
    And the 2nd patient file race demographic is as of 01/19/1987
    And the patient file race demographics does not include details for the Asian race


  Scenario: I can view a Patient's detailed race demographics
    Given the patient has the race category Asian
    And the patient race of Asian includes Bangladeshi
    And the patient race of Asian includes Taiwanese
    When I view the patient's race demographics
    Then the patient file race demographics includes Taiwanese within the Asian race
    And the patient file race demographics includes Bangladeshi within the Asian race
