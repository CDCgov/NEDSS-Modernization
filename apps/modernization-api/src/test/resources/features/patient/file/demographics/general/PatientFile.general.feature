@patient-file @patient-general-demographics
Feature: Viewing the general demographics of a patient

  Background:
    Given I am logged into NBS
    And I can "view" any "patient"
    And I have a patient

  Scenario: I can view a Patient's general demographics
    Given the patient's martial status as of 02/17/1947 is Widowed
    And the patient's maternal maiden name is "Havisham"
    And the patient lives with 3 adults
    And the patient lives with 17 children
    And the patient's primary occupation is Wholesale Trade
    And the patient's education level is Some college credit, but less than 1 year
    And the patient's primary language is Welsh
    And the patient does not speak English
    When I view the patient's general information demographics
    Then the patient file general information is as of 02/17/1947
    And the patient file general information includes the marital status Widowed
    And the patient file general information includes a maternal maiden name of "Havisham"
    And the patient file general information includes 3 adults in the residence
    And the patient file general information includes 17 children in the residence
    And the patient file general information includes the primary occupation Wholesale Trade
    And the patient file general information includes an education level of Some college credit, but less than 1 year
    And the patient file general information includes a primary language of Welsh
    And the patient file general information includes that the patient does not speak English
