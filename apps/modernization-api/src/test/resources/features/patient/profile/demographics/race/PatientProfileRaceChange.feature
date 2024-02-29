@patient-profile-race @patient-profile-race-change @web-interaction
Feature: Patient Profile Race Changes

  Background:
    Given I am logged in
    And I can "find" any "patient"
    And I can "edit" any "patient"
    And I have a patient

  Scenario: I can change a patient with a new race category
    Given I want to set the patient's race category to Black or African American
    When I add a patient's race as of 04/10/2017
    And I view the Patient Profile Races
    Then the patient's race as of 04/10/2017 includes the category Black or African American

  Scenario: I cannot change a patient with the same race category
    Given the patient has the race category Black or African American
    And I want to set the patient's race category to Black or African American
    When I add a patient's race as of 04/10/2017
    Then the patient's race cannot be added because the category exists
    
  Scenario: I can change a patient with a new detailed race
    Given I want to set the patient's race category to Black or African American
    And I want to set the patient's detailed race to Bahamian
    And I want to set the patient's detailed race to Haitian
    When I add a patient's race as of 04/10/2017
    And I view the Patient Profile Races
    Then the patient's race includes Bahamian within the category Black or African American
    And the patient's race includes Haitian within the category Black or African American

  Scenario: I can change a patient's existing race
    Given the patient has the race category Asian
    And the patient has the race category Other
    And I want to set the patient's detailed race to Bangladeshi
    And I want to set the patient's detailed race to Taiwanese
    When I update a patient's race category of Asian as of 04/17/2020
    And I view the Patient Profile Races
    Then the patient's race as of 04/17/2020 includes the category Asian

  Scenario: I can change a patient's existing race with detailed races
    Given the patient has the race category Asian
    And the patient has the race category Other
    And I want to set the patient's detailed race to Bangladeshi
    And I want to set the patient's detailed race to Taiwanese
    When I update a patient's race category of Asian as of 04/17/2020
    And I view the Patient Profile Races
    Then the patient's race includes Bangladeshi within the category Asian
    And the patient's race includes Taiwanese within the category Asian

  Scenario: I can remove a patient's existing race
    Given the patient has the race category Asian
    And the patient has the race category Other
    And I want to remove the patient's Other race entry
    When I remove a patient's race
    And I view the Patient Profile Races
    Then the patient's race does not include the category Other
    And the patient's race includes the category Asian
    Then the patient race history contains the previous version

