@patient-edit
Feature: Editing of Patient race demographics

  Background:
    Given I am logged into NBS
    And I can "edit" any "patient"
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can edit a patient to add a race demographic
    Given I am entering the Other race as of 04/11/2019
    When I edit the patient with entered demographics
    And I view the patient's race demographics
    Then the patient file race demographics as of 04/11/2019 includes the category Other

  Scenario: I can edit a patient to add a race demographic with a detailed race
    Given the patient's race is Other as of 03/11/2019
    And I want to change the patient's race
    And I am entering the Asian race as of 09/07/2024
    And I am entering the detailed race Taiwanese
    And I am entering the detailed race Bangladeshi
    When I edit the patient with entered demographics
    And I view the patient's race demographics
    Then the patient file race demographics as of 09/07/2024 includes the category Asian
    And the patient file race demographics includes Taiwanese within the Asian race
    And the patient file race demographics includes Bangladeshi within the Asian race
    And the patient file race demographics as of 03/11/2019 includes the category Other

  Scenario: I cannot edit a patient to add a duplicate race category
    Given the patient's race is Other as of 03/11/2019
    And I want to change the patient's race
    And I am entering the Other race as of 04/11/2019
    When I edit the patient with entered demographics
    Then I am unable to edit the patient

  Scenario: I can edit a patient to update an existing race demographic
    Given the patient's race is Asian as of 01/19/1987
    And the patient's race is Other as of 03/11/2019
    And the patient race of Asian includes Bangladeshi
    And the patient race of Asian includes Taiwanese
    And I want to change the patient's race
    And I select the entered race that is as of 03/11/2019
    And I enter the race as of 04/11/2017
    And I enter the race category Unknown
    When I edit the patient with entered demographics
    And I view the patient's race demographics
    Then the patient file race demographics as of 04/11/2017 includes the category Unknown
    And the patient file race demographics as of 01/19/1987 includes the category Asian
    And the patient file race demographics includes Taiwanese within the Asian race
    And the patient file race demographics includes Bangladeshi within the Asian race
    And the patient race history contains the previous version

  Scenario: I can edit a patient to update an existing race demographic and remove the previous details
    Given the patient's race is Asian as of 01/19/1987
    And the patient race of Asian includes Bangladeshi
    And the patient race of Asian includes Taiwanese
    And I want to change the patient's race
    And I select the entered race that is as of 01/19/1987
    And I enter the race category Native Hawaiian or Other Pacific Islander
    When I edit the patient with entered demographics
    And I view the patient's race demographics
    Then the patient file race demographics as of 01/19/1987 includes the category Native Hawaiian or Other Pacific Islander
    And the patient file race demographics does not include details for the Native Hawaiian or Other Pacific Islander race

  Scenario: I can edit a patient to remove an existing race demographic
    Given the patient's race is Asian as of 01/19/1987
    And the patient's race is Other as of 03/11/2019
    And the patient race of Asian includes Bangladeshi
    And the patient race of Asian includes Taiwanese
    And I want to change the patient's race
    And I remove the entered race as of 03/11/2019
    When I edit the patient with entered demographics
    And I view the patient's race demographics
    And the patient file race demographics does not include the category Other
    And the patient race history contains the previous version
