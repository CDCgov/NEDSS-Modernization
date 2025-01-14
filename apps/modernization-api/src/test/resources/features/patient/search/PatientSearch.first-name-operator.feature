@patient-search @patient-search-results
Feature: Searching patient's by name

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I have a patient

  Scenario: I can find the a patient with a first name that equals a value
    Given I have another patient
    And the patient has the legal name "Joe" "Smith"
    And patients are available for search
    And I add the patient criteria for a first name that equals "Joe"
    When I search for patients
    Then search result 1 has a "first name" of "Joe"
    And search result 1 has a "last name" of "Smith"
    And there are 1 patient search results

  Scenario: I can find the a patient with a first name that does not equal a value
    Given the patient has the legal name "Joe" "Smith"
    And I have another patient
    And the patient has the legal name "Jose" "Smith"
    And patients are available for search
    And I add the patient criteria for a first name that does not equal "Jose"
    When I search for patients
    Then there are 1 patient search results
    And the patient is not in the search results

  Scenario: I can find the a patient with a first name that contains a value
    Given the patient has the Alias Name name "Anjoel" "Smith"
    And I have another patient
    And the patient has the legal name "Jo" "Smith"
    And patients are available for search
    And I add the patient criteria for a first name that contains "joe"
    When I search for patients
    Then search result 1 has a "first name" of "Anjoel"
    And search result 1 has a "last name" of "Smith"
    And there are 1 patient search results

  Scenario: I can find the a patient with a first name that starts with a value
    Given the patient has the Alias Name name "Joel" "Smith"
    And I have another patient
    And the patient has the legal name "Sam" "Smith"
    And patients are available for search
    And I add the patient criteria for a first name that starts with "joe"
    When I search for patients
    Then search result 1 has a "first name" of "Joel"
    And search result 1 has a "last name" of "Smith"
    And there are 1 patient search results

  Scenario: I can find the a patient with a first name that sounds like a value
    Given I have another patient
    And the patient has the legal name "Jo" "Smith"
    And patients are available for search
    And I add the patient criteria for a first name that sounds like "Joe"
    When I search for patients
    Then search result 1 has a "first name" of "Jo"
    And search result 1 has a "last name" of "Smith"
    And there are 1 patient search results

  Scenario: I can find the a patient with a first name and a last name
    Given I have another patient
    And the patient has the legal name "Joe" "Smith"
    And patients are available for search
    And I add the patient criteria for a first name that equals "Joe"
    And I add the patient criteria for a last name that equals "Smith"
    When I search for patients
    Then search result 1 has a "first name" of "Joe"
    And search result 1 has a "last name" of "Smith"
    And there are 1 patient search results
