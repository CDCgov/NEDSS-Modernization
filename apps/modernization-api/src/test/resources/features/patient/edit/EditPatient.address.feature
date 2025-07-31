@patient-edit
Feature: Editing of Patient address demographics

  Background:
    Given I am logged into NBS
    And I can "edit" any "patient"
    And I can "viewworkup" any "patient"
    And I have a patient

  Scenario: I can edit a patient to add an address demographic
    Given I am entering the House - Home address at "1640 Riverside Drive" "Hill Valley" "33266" as of 11/01/1955
    When I edit the patient with entered demographics
    And I view the patient's address demographics
    Then the patient file address demographics includes a House - Home address at "1640 Riverside Drive" "Hill Valley" "33266" as of 11/01/1955

  Scenario: I can edit a patient to add an address demographic
    Given I enter the address as of 11/01/1955
    And I enter the address type Postal/Mailing
    And I enter the address use Temporary
    And I enter the address street address "4102 Raintree Boulevard"
    And I enter the address street address2 "Suite 43"
    And I enter the address city "Annandale"
    And I enter the address state Minnesota
    And I enter the address zipcode "55302"
    And I enter the address county Wright County
    And I enter the address country United States
    And I enter the address census tract "1013.00"
    And I enter the address comment "Added for development testing"
    When I edit the patient with entered demographics
    And I view the patient's address demographics
    Then the 1st address demographic on the patient file is as of 11/01/1955
    And the 1st address demographic on the patient file has the address type Postal/Mailing
    And the 1st address demographic on the patient file has the address use Temporary
    And the 1st address demographic on the patient file has the street address "4102 Raintree Boulevard"
    And the 1st address demographic on the patient file has the street address 2 "Suite 43"
    And the 1st address demographic on the patient file has the city "Annandale"
    And the 1st address demographic on the patient file has the state Minnesota
    And the 1st address demographic on the patient file has the zipcode "55302"
    And the 1st address demographic on the patient file has the county Wright County
    And the 1st address demographic on the patient file has the country United States
    And the 1st address demographic on the patient file has the census tract "1013.00"
    And the 1st address demographic on the patient file has the comment "Added for development testing"

  Scenario: I can edit a patient to update an existing address demographic
    Given the patient has a House - Home address at "47526 Rudolph Divide" "Edmundton" "77747" as of 10/17/1978
    And the patient has an Office - Primary Business address at "27449 Hand Ranch" "West Elmira" "19882" as of 05/11/2021
    And I want to change the patient's addresses
    And I select the entered address that is as of 10/17/1978
    And I enter the address use Primary Business
    And I enter the address as of 04/11/2017
    When I edit the patient with entered demographics
    And I view the patient's address demographics
    Then the patient file address demographics includes a House - Primary Business address at "47526 Rudolph Divide" "Edmundton" "77747" as of 04/11/2017
    And the patient file address demographics includes an Office - Primary Business address at "27449 Hand Ranch" "West Elmira" "19882" as of 05/11/2021

  Scenario: I can edit a patient to remove an existing address demographic
    Given  the patient has a House - Home address at "47526 Rudolph Divide" "Edmundton" "77747" as of 10/17/1978
    And the patient has a Office - Primary Business address at "27449 Hand Ranch" "West Elmira" "19882" as of 05/11/2021
    And I want to change the patient's addresses
    And I remove the entered address as of 05/11/2021
    When I edit the patient with entered demographics
    And I view the patient's address demographics
    Then the patient file address demographics includes a House - Home address at "47526 Rudolph Divide" "Edmundton" "77747" as of 10/17/1978
    And the patient file address demographics does not include an Office - Primary Business address at "27449 Hand Ranch" "West Elmira" "19882" as of 05/11/2021

