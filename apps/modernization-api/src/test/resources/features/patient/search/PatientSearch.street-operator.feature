@patient-search @patient-search-results
Feature: Searching patient's by street

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I have a patient

  Scenario: I can find the a patient with a street address that equals a value
    And the patient has an "address" of "123Street"
    Given I have another patient
    And the patient has an "address" of "124Street"
    And patients are available for search
    And I add the patient criteria for a street address that equals "123Street"
    When I search for patients
    Then search result 1 has a "address" of "123Street"
    And there are 1 patient search results

  Scenario: I can find the a patient with a street address that does not equal a value
    And the patient has an "address" of "123Street"
    Given I have another patient
    And the patient has an "address" of "124Street"
    And patients are available for search
    And I add the patient criteria for a street address that does not equal "123Street"
    When I search for patients
    Then search result 1 has a "address" of "124Street"
    And there are 1 patient search results

  Scenario: I can find the a patient with a street address that contains a value
    And the patient has an "address" of "123Street"
    Given I have another patient
    And the patient has an "address" of "124Street"
    And patients are available for search
    And I add the patient criteria for a street address that contains "23"
    When I search for patients
    Then search result 1 has a "address" of "123Street"
    And there are 1 patient search results
