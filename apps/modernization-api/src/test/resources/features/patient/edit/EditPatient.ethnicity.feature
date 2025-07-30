@patient-edit
Feature: Editing of Patient ethnicity demographics

  Background:
    Given I am logged into NBS
    And I can "edit" any "patient"
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can edit a patient with ethnicity information
    Given the patient has the Hispanic or Latino ethnicity as of 05/11/2023
    And I am entering the ethnicity as of date 05/29/2023
    And I enter the ethnicity ethnic group Not Hispanic or Latino
    When I edit the patient with entered demographics
    And I view the patient's ethnicity demographics
    Then the patient file ethnicity demographics are as of 05/29/2023
    And the patient file ethnicity demographics has the ethnicity Not Hispanic or Latino

  Scenario: I can edit a patient with unknown ethnicity information
    Given the patient has the Hispanic or Latino ethnicity as of 05/11/2023
    And the patient ethnicity includes a Spanish origin of Spaniard
    And the patient ethnicity includes a Spanish origin of Cuban
    And I am entering the ethnicity as of date 05/29/2023
    And I enter the ethnicity ethnic group Unknown
    And I enter the ethnicity unknown reason Refused to answer
    When I edit the patient with entered demographics
    And I view the patient's ethnicity demographics
    And the patient file ethnicity demographics are as of 05/29/2023
    And the patient file ethnicity demographics is unknown with the reason being Refused to answer
    And the patient file ethnicity demographics does not include Spanish origins
    And the patient history contains the previous version

  Scenario: I can edit a patient's ethnicity by adding detailed ethnicities
    Given I am entering the ethnicity as of date 05/11/2023
    And I enter the ethnicity ethnic group Hispanic or Latino
    And I enter the ethnicity detailed Spaniard
    And I enter the ethnicity detailed Cuban
    When I edit the patient with entered demographics
    And I view the patient's ethnicity demographics
    Then the patient file ethnicity demographics are as of 05/11/2023
    And the patient file ethnicity demographics has the ethnicity Hispanic or Latino
    And the patient file ethnicity demographics includes the Spanish origin Spaniard
    And the patient file ethnicity demographics includes the Spanish origin Cuban
    And the patient ethnicity history is not recorded

  Scenario: I can edit a patient's ethnicity by removing detailed ethnicities
    Given the patient has the Hispanic or Latino ethnicity as of 05/11/2023
    And the patient ethnicity includes a Spanish origin of Spaniard
    And the patient ethnicity includes a Spanish origin of Cuban
    And I am entering the ethnicity as of date 08/17/2024
    And I enter the ethnicity ethnic group Hispanic or Latino
    And I enter the ethnicity detailed Cuban
    When I edit the patient with entered demographics
    And I view the patient's ethnicity demographics
    Then the patient file ethnicity demographics are as of 08/17/2024
    And the patient file ethnicity demographics has the ethnicity Hispanic or Latino
    And the patient file ethnicity demographics does not include the Spanish origin Spaniard
    And the patient file ethnicity demographics includes the Spanish origin Cuban
    And the patient ethnicity history contains the previous version
