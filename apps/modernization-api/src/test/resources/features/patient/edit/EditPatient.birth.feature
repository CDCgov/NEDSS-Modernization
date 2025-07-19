@patient-edit
Feature: Editing of Patient birth demographics

  Background:
    Given I am logged into NBS
    And I can "edit" any "patient"
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can edit a patient with birth demographics information
    Given I enter the birth demographics as of date 07/03/1990
    And I enter the birth demographics with the patient born on 04/31/1990
    And I enter the birth demographics with the patient born as Unknown
    When I edit a patient with entered demographics
    And I view the patient's birth demographics
    Then the patient file birth demographics are as of 07/03/1990
    And the patient file birth demographics has the birth date of 04/31/1990
    And the patient file birth demographics has the birth sex of Unknown
    And the patient history contains the previous version

  Scenario: I can edit a patient with multiple birth demographics information
    Given I enter the birth demographics with the patient multiple as Yes
    And I enter the birth demographics with the patient born 17th
    When I edit a patient with entered demographics
    And I view the patient's birth demographics
    Then the patient file birth demographics has the patient multiple as Yes
    And the patient file birth demographics has the patient born 17th
    And the patient history contains the previous version

  Scenario: I can edit a patient with birth location demographics
    Given I enter the birth demographics with the patient born in the city of "Salem Center"
    And I enter the birth demographics with the patient born in the county of Westchester County
    And I enter the birth demographics with the patient born in the state of New York
    And I enter the birth demographics with the patient born in the country of United States
    When I edit a patient with entered demographics
    And I view the patient's birth demographics
    Then the patient file birth demographics has the patient born in the city of "Salem Center"
    And the patient file birth demographics has the patient born in the county of Westchester County
    And the patient file birth demographics has the patient born in the state of New York
    And the patient file birth demographics has the patient born in the country of United States
    And the patient history contains the previous version
