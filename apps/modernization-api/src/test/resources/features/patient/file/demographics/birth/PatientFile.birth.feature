@patient-file @patient-birth-demographics
Feature: Viewing the birth demographics of a patient

  Background:
    Given I am logged into NBS
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can view a Patient's birth demographics
    Given the patient was born on 02/03/2005 as of 07/11/2013
    And the patient was born a Male
    And the patient was the 2nd to be born
    When I view the patient's birth demographics
    Then the patient file birth demographics are as of 07/11/2013
    And the patient file birth demographics has the birth date of 02/03/2005
    And the patient file birth demographics has the birth sex of Male
    And the patient file birth demographics has the patient multiple as Yes
    And the patient file birth demographics has the patient born 2nd

  Scenario: I can view a Patent's birth location demographics
    Given the patient was born in the city of "Salem Center"
    And the patient was born in the county of Westchester County
    And the patient was born in the state of New York
    And the patient was born in the country of United States
    When I view the patient's birth demographics
    Then the patient file birth demographics has the patient born in the city of "Salem Center"
    And the patient file birth demographics has the patient born in the county of Westchester County
    And the patient file birth demographics has the patient born in the state of New York
    And the patient file birth demographics has the patient born in the country of United States

  Scenario: I can not view a Patient's birth demographics if there aren't any
    When I view the patient's birth demographics
    Then no value is returned
