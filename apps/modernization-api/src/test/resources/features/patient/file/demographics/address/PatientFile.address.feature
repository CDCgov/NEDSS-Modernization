@patient-file @patient-address-demographics
Feature: Viewing the address demographics of a patient

  Background:
    Given I am logged into NBS
    And I can "view" any "patient"
    And I have a patient

  Scenario: I can view the patient's address demographics
    Given the patient has a House - Home address at "1640 Riverside Drive" "Hill Valley" "33266" as of 11/01/1955
    When I view the patient's address demographics
    Then the patient file address demographics includes a House - Home address at "1640 Riverside Drive" "Hill Valley" "33266" as of 11/01/1955


  Scenario: I can view the patient's address demographics ordered by as of date with the newer entries being first
    Given the patient has a Living with Friend - Temporary address at "9336 Giovanni View" "New Giles" "00599" as of 05/11/2021
    And the patient has a House - Home address at "47526 Rudolph Divide" "Edmundton" "77747" as of 10/17/1978
    And the patient has a Office - Primary Business address at "27449 Hand Ranch" "West Elmira" "19882" as of 05/11/2021
    When I view the patient's address demographics
    Then the 1st address demographics on the patient file includes a Office - Primary Business address at "27449 Hand Ranch" "West Elmira" "19882" as of 05/11/2021
    And the 2nd address demographics on the patient file includes a Living with Friend - Temporary address at "9336 Giovanni View" "New Giles" "00599" as of 05/11/2021
    And the 3rd address demographics on the patient file includes a House - Home address at "47526 Rudolph Divide" "Edmundton" "77747" as of 10/17/1978

  Scenario: No address demographics are returned when a patient does not have any address demographics
    When I view the patient's address demographics
    Then no values are returned
