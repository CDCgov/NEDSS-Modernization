@patient-edit @patient-mortality-demographics
Feature: Editing of Patient mortality demographics

  Background:
    Given I am logged into NBS
    And I can "edit" any "patient"
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can edit a patient with mortality information
    Given I am entering the mortality as of date 06/29/2023
    And I enter the mortality deceased on date of 05/29/2023
    And I enter the mortality deceased option as Yes
    And I enter the mortality city "Salem Center"
    And I enter the mortality country United States
    And I enter the mortality county Westchester County
    And I enter the mortality state New York
    When I edit a patient with entered demographics
    And I view the patient's mortality demographics
    Then the patient file mortality demographics are as of 06/29/2023
    And the patient file mortality demographics has the patient deceased on 05/29/2023
    And the patient file mortality demographics shows that the patient is deceased
    And the patient file mortality demographics has the patient dying in the city of "Salem Center"
    And the patient file mortality demographics has the patient dying in the country of United States
    And the patient file mortality demographics has the patient dying in the county of Westchester County
    And the patient file mortality demographics has the patient dying in the state of New York
    And the patient history contains the previous version

  Scenario: I can edit a patient with mortality information when the patient is not deceased
    Given the patient died on 02/03/2005 as of 07/11/2013
    And the patient died in the city of "Salem Center"
    And the patient died in the county of Westchester County
    And the patient died in the state of New York
    And the patient died in the country of United States
    And I am entering the mortality as of date 07/14/2013
    And I enter the mortality deceased option as No
    When I edit a patient with entered demographics
    And I view the patient's mortality demographics
    Then the patient file mortality demographics are as of 07/14/2013
    And the patient file mortality demographics shows that the patient is not deceased
    And the patient file mortality demographics does not include details of death
