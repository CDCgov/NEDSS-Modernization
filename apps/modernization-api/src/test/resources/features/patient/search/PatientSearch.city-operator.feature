@patient-search @patient-search-results
Feature: Searching patient's by street

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I have a patient

  Scenario: I can find the a patient with a city address that equals a value
    And the patient has a "city" of "McLean"
    Given I have another patient
    And the patient has a "city" of "Chicago"
    And patients are available for search
    And I add the patient criteria for a city address that equals "McLean"
    When I search for patients
    Then search result 1 has a "city" of "McLean"
    And there are 1 patient search results

  Scenario: I can find the a patient with a city address that does not equal a value
    And the patient has a "city" of "McLean"
    Given I have another patient
    And the patient has a "city" of "Chicago"
    And patients are available for search
    And I add the patient criteria for a city address that does not equal "McLean"
    When I search for patients
    Then search result 1 has a "city" of "Chicago"
    And there are 1 patient search results

  Scenario: I can find the a patient with a city address that contains a value
    And the patient has a "city" of "McLean"
    Given I have another patient
    And the patient has a "city" of "Chicago"
    And patients are available for search
    And I add the patient criteria for a city address that contains "lea"
    When I search for patients
    Then search result 1 has a "city" of "McLean"
    And there are 1 patient search results

  Scenario: I can find the a patient with a city address and street address that equals a value
    And the patient has a "city" of "McLean"
    And the patient has an "address" of "124Street"
    Given I have another patient
    And the patient has a "city" of "Chicago"
    And the patient has an "address" of "124Street"
    And patients are available for search
    And I add the patient criteria for a city address that equals "McLean"
    And I add the patient criteria for a street address that equals "124Street"
    When I search for patients
    Then search result 1 has a "city" of "McLean"
    And there are 1 patient search results
