@patient-profile-general-information-change
Feature: Patient Profile General Information Changes

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I can "edit" any "patient"
    And I have a patient

  Scenario: I can update a patient's general information to be as of a certain date
    When the patient profile general information changes are submitted as of 03/19/2021
    And I view the patient's general information
    Then the patient's general information is as of 03/19/2021

  Scenario: I can update a patient's general information to include marital status
    Given I want to change the patient's general information to include the marital status of Interlocutory
    When the patient profile general information changes are submitted as of 04/17/2013
    And I view the patient's general information
    Then the patient's general information includes the marital status Interlocutory

  Scenario: I can update a patient's general information to include mother's maiden name
    Given I want to change the patient's general information to include the mother's maiden name of "Maiden Name"
    When the patient profile general information changes are submitted as of 04/17/2013
    And I view the patient's general information
    Then the patient's general information includes a mother's maiden name of "Maiden Name"

  Scenario: I can update a patient's general information to include adults in house
    Given I want to change the patient's general information to include 11 adults in the house
    When the patient profile general information changes are submitted as of 04/17/2013
    And I view the patient's general information
    Then the patient's general information includes 11 adults in the house

  Scenario: I can update a patient's general information to include children in house
    Given I want to change the patient's general information to include 67 children in the house
    When the patient profile general information changes are submitted as of 04/17/2013
    And I view the patient's general information
    Then the patient's general information includes 67 children in the house

  Scenario: I can update a patient's general information to include occupation
    Given I want to change the patient's general information to include the occupation of Envelope Manufacturing
    When the patient profile general information changes are submitted as of 04/17/2013
    And I view the patient's general information
    Then the patient's general information includes the occupation Envelope Manufacturing

  Scenario: I can update a patient's general information to include education level
    Given I want to change the patient's general information to include an education level of Occupational/Vocational degree
    When the patient profile general information changes are submitted as of 04/17/2013
    And I view the patient's general information
    Then the patient's general information includes an education level of Occupational/Vocational degree

  Scenario: I can update a patient's general information to include primary language
    Given I want to change the patient's general information to include a primary language of Miscellaneous languages
    When the patient profile general information changes are submitted as of 04/17/2013
    And I view the patient's general information
    Then the patient's general information includes a primary language of Miscellaneous languages

  Scenario: I can update a patient's general information to include if the patient speaks english
    Given I want to change the patient's general information to include that the patient does speak English
    When the patient profile general information changes are submitted as of 04/17/2013
    And I view the patient's general information
    Then the patient's general information includes that the patient does speak English
