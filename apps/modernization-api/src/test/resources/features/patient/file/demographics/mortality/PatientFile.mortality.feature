@patient-file @patient-mortality-demographics
Feature: Viewing the mortality demographics of a patient

  Background:
    Given I am logged into NBS
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can view a Patient's mortality demographics
    Given the patient died on 02/03/2005 as of 07/11/2013
    When I view the patient's mortality demographics
    Then the patient file mortality demographics are as of 07/11/2013
    And the patient file mortality demographics has the patient deceased on 02/03/2005

  Scenario Outline:
    Given the patient <indicator> deceased
    When I view the patient's mortality demographics
    Then the patient file mortality demographics shows that the patient <indicator> deceased

    Examples:
      | indicator          |
      | is                 |
      | is not             |
      | is not known to be |


  Scenario: I can view a Patent's mortality location demographics
    Given the patient died in the city of "Salem Center"
    And the patient died in the county of Westchester County
    And the patient died in the state of New York
    And the patient died in the country of United States
    When I view the patient's mortality demographics
    Then the patient file mortality demographics has the patient dying in the city of "Salem Center"
    And the patient file mortality demographics has the patient dying in the county of Westchester County
    And the patient file mortality demographics has the patient dying in the state of New York
    And the patient file mortality demographics has the patient dying in the country of United States

  Scenario: I can not view a Patient's mortality demographics if there aren't any
    When I view the patient's mortality demographics
    Then no value is returned
