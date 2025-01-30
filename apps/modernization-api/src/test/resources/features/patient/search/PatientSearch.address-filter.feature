@patient-search @patient-search-results
Feature: Searching patient's by street

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I have a patient

  Scenario: I can find the a patient with a street address that equals a value and a matching street filter
    And the patient has an "address" of "123Street"
    Given I have another patient
    And the patient has an "address" of "124Street"
    And patients are available for search
    And I add the patient criteria for a street address that equals "123Street"
    And I would like to filter search results with address "123Street"
    When I search for patients
    Then search result 1 has a "address" of "123Street"
    And there are 1 patient search results

  Scenario: I can't find the a patient with a street address that equals a value and a non-matching filter
    And the patient has an "address" of "123Street"
    Given I have another patient
    And the patient has an "address" of "124Street"
    And patients are available for search
    And I add the patient criteria for a street address that equals "123Street"
    And I would like to filter search results with address "124Street"
    When I search for patients
    Then there are 0 patient search results

  Scenario: I can find the a patient with a street address and city address that equals a value and matching city filter
    And the patient has a "city" of "McLean"
    And the patient has an "address" of "124Street"
    Given I have another patient
    And the patient has a "city" of "Chicago"
    And the patient has an "address" of "124Street"
    And patients are available for search
    And I add the patient criteria for a street address that equals "124Street"
    And I add the patient criteria for a city address that equals "McLean"
    And I would like to filter search results with address "Lean"
    When I search for patients
    And there are 1 patient search results

  Scenario: I can't find the a patient with a street address and city address that equals a value and non-matching filter
    And the patient has a "city" of "McLean"
    And the patient has an "address" of "124Street"
    Given I have another patient
    And the patient has a "city" of "Chicago"
    And the patient has an "address" of "124Street"
    And patients are available for search
    And I add the patient criteria for a street address that equals "124Street"
    And I add the patient criteria for a city address that equals "McLean"
    And I would like to filter search results with address "Long"
    When I search for patients
    And there are 0 patient search results

  Scenario: I can find the a patient with a matching zip filter
    And the patient has a "zip" of "34567"
    And the patient has an "address" of "124Street"
    Given I have another patient
    And the patient has an "address" of "124Street"
    And the patient has a "zip" of "34568"
    And patients are available for search
    And I add the patient criteria for a street address that equals "124Street"
    And I would like to filter search results with address "34567"
    When I search for patients
    And there are 1 patient search results

  Scenario: I can't find the a patient with a non-matching zip filter
    And the patient has a "zip" of "34567"
    And the patient has an "address" of "124Street"
    Given I have another patient
    And the patient has an "address" of "124Street"
    And the patient has a "zip" of "34568"
    And patients are available for search
    And I add the patient criteria for a street address that equals "124Street"
    And I would like to filter search results with address "12345"
    When I search for patients
    And there are 0 patient search results

  Scenario: I can find the a patient with a zip that equals a value and matching state filter
    And the patient has an "address" of "124Street"
    And the patient has a "state" of "01"
    Given I have another patient
    And the patient has an "address" of "124Street"
    And the patient has a "state" of "02"
    And patients are available for search
    And I add the patient criteria for a street address that equals "124Street"
    And I would like to filter search results with address "AL"
    When I search for patients
    And there are 1 patient search results

  Scenario: I can't find the a patient with a zip that equals a value and non-matching state filter
    And the patient has an "address" of "124Street"
    And the patient has a "state" of "01"
    Given I have another patient
    And the patient has an "address" of "124Street"
    And the patient has a "state" of "02"
    And patients are available for search
    And I add the patient criteria for a street address that equals "124Street"
    And I would like to filter search results with address "QQ"
    When I search for patients
    And there are 0 patient search results

  Scenario: I can find the a patient with a street address that equals a value and two filters
    And the patient has an "address" of "123Street"
    And the patient has a "first name" of "John"
    And patients are available for search
    And I add the patient criteria for a street address that equals "123Street"
    And I would like to filter search results with name "oh"
    And I would like to filter search results with address "123Street"
    When I search for patients
    Then search result 1 has a "address" of "123Street"
    And there are 1 patient search results
