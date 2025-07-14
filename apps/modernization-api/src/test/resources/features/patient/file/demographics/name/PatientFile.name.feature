@patient-file @patient-name-demographics
Feature: Viewing the name demographics of a patient

  Background: I am logged in and can find patient
    Given I am logged into NBS
    And I have a patient
    And I can "viewworkup" any "patient"

  Scenario: I can find patient names
    Given the patient has the legal name "Joe" "Jacob" "Smith", Jr. as of 01/01/2000
    When I view the patient's name demographics
    Then the patient file name demographics includes a legal name "Joe" "Jacob" "Smith", Jr. as of 01/01/2000

  Scenario: Patient file name demographics are order by as of date, and then the order in which they were added
    Given the patient has the legal name "Jessica" "Cruz" as of 01/01/2020
    And the patient has the legal name "Sojourner" "Mullein" as of 01/01/2020
    And the patient has the legal name "Alan" "Scott" as of 07/19/1940
    And the patient has the legal name "Hal" "Jordan" as of 09/17/1959
    And the patient has the legal name "John" "Stewart" as of 12/11/1971
    When I view the patient's name demographics
    Then the 1st patient file name demographic has the as of date 01/01/2020
    And the 1st patient file name demographic has the first name "Sojourner"
    And the 2nd patient file name demographic has the as of date 01/01/2020
    And the 2st patient file name demographic has the first name "Jessica"
    And the 3rd patient file name demographic has the as of date 12/11/1971
    And the 4th patient file name demographic has the as of date 09/17/1959
    And the 5th patient file name demographic has the as of date 07/19/1940
