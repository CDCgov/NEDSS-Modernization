@patient-edit
Feature: Editing of Patient address demographics

  Background:
    Given I am logged into NBS
    And I can "edit" any "patient"
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can edit a patient to add an address demographic
    Given I am entering the House - Home address at "1640 Riverside Drive" "Hill Valley" "33266" as of 11/01/1955
    When I edit a patient with entered demographics
    And I view the patient's address demographics
    Then the patient file address demographics includes a House - Home address at "1640 Riverside Drive" "Hill Valley" "33266" as of 11/01/1955

  Scenario: I can edit a patient to update an existing address demographic
    Given the patient has a House - Home address at "47526 Rudolph Divide" "Edmundton" "77747" as of 10/17/1978
    And the patient has an Office - Primary Business address at "27449 Hand Ranch" "West Elmira" "19882" as of 05/11/2021
    And I want to change the patient's addresses
    And I select the entered address that is as of 10/17/1978
    And I enter the address use Primary Business
    And I enter the address as of 04/11/2017
    When I edit a patient with entered demographics
    And I view the patient's address demographics
    Then the patient file address demographics includes a House - Primary Business address at "47526 Rudolph Divide" "Edmundton" "77747" as of 04/11/2017
    And the patient file address demographics includes an Office - Primary Business address at "27449 Hand Ranch" "West Elmira" "19882" as of 05/11/2021

  Scenario: I can edit a patient to remove an existing address demographic
    Given  the patient has a House - Home address at "47526 Rudolph Divide" "Edmundton" "77747" as of 10/17/1978
    And the patient has a Office - Primary Business address at "27449 Hand Ranch" "West Elmira" "19882" as of 05/11/2021
    And I want to change the patient's addresses
    And I remove the entered address as of 05/11/2021
    When I edit a patient with entered demographics
    And I view the patient's address demographics
    Then the patient file address demographics includes a House - Home address at "47526 Rudolph Divide" "Edmundton" "77747" as of 10/17/1978
    And the patient file address demographics does not include an Office - Primary Business address at "27449 Hand Ranch" "West Elmira" "19882" as of 05/11/2021

