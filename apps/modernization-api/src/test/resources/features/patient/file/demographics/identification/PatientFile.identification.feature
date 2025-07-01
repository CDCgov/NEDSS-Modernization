@patient-file @patient-identification-demographics
Feature: Viewing the identification demographics of a patient

  Background:
    Given I am logged into NBS
    And I can "view" any "patient"
    And I have a patient

  Scenario: I can view the patient's identification demographics
    Given the patient can be identified with a Medicare number of "1051" as of 02/11/2020
    When I view the patient's identification demographics
    Then the patient file identification demographics includes a Medicare number of "1051" as of 02/11/2020

  Scenario: I can view the patient's identification demographics ordered by as of date with the newer entries being first
    Given the patient can be identified with a Medicare number of "163" as of 02/11/2020
    And the patient can be identified with a Person number of "349" as of 08/17/2019
    And the patient can be identified with a Other of "1033" as of 02/11/2020
    When I view the patient's identification demographics
    Then the 1st identification demographics on the patient file includes a Other of "1033" as of 02/11/2020
    And the 2nd identification demographics on the patient file includes a Medicare number of "163" as of 02/11/2020
    And the 3rd identification demographics on the patient file includes a Person number of "349" as of 08/17/2019

  Scenario: No identification demographics are return when a patient does not have any Identification demographics
    When I view the patient's identification demographics
    Then no values are returned
