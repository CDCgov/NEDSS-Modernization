@patient @patient-search
Feature: Patient Search by Last name operator

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient
    And the patient has the legal name "JoeEqual" "Smith"
    And I have another patient
    And the patient has the legal name "JoeSoundsLike" "Smyth"
    And I have another patient
    And the patient has the legal name "JoeStartsWith" "Smiths"
    And I have another patient
    And the patient has the legal name "JoeContains" "Aerosmithy"
    And patients are available for search

  Scenario: I can find the most relevant patient when searching by last name with operator equals
    And I add the patient criteria for a "last name" equal to "Smith"
    And I add the patient criteria for a "last name operator" equal to "EQUAL"
    When I search for patients
    Then search result 1 has a "first name" of "JoeEqual"
    And search result 1 has a "last name" of "Smith"
    And there are 1 patient search results

  Scenario: I can find the most relevant patient when searching by last name with operator starts with
    And I add the patient criteria for a "last name" equal to "Smith"
    And I add the patient criteria for a "last name operator" equal to "STARTS_WITH"
    When I search for patients
    Then search result 1 has a "first name" of "JoeEqual"
    And search result 1 has a "last name" of "Smith"
    Then search result 2 has a "first name" of "JoeStartsWith"
    And search result 2 has a "last name" of "Smiths"
    And there are 2 patient search results    

  Scenario: I can find the most relevant patient when searching by last name with operator sounds like
    And I add the patient criteria for a "last name" equal to "Smith"
    And I add the patient criteria for a "last name operator" equal to "SOUNDS_LIKE"
    When I search for patients
    Then search result 1 has a "first name" of "JoeEqual"
    Then search result 2 has a "first name" of "JoeSoundsLike"
    And there are 2 patient search results   

  Scenario: I can find the most relevant patient when searching by last name with operator not equal
    And I add the patient criteria for a "last name" equal to "Smith"
    And I add the patient criteria for a "last name operator" equal to "NOT_EQUAL"
    When I search for patients
    Then search result 1 has a "last name" of "Smyth"
    Then search result 2 has a "last name" of "Smiths"
    Then search result 3 has a "last name" of "Aerosmithy"
    And there are 3 patient search results   

  Scenario: I can find the most relevant patient when searching by last name with operator contains
    And I add the patient criteria for a "last name" equal to "Smith"
    And I add the patient criteria for a "last name operator" equal to "CONTAINS"
    When I search for patients
    Then search result 1 has a "last name" of "Smith"
    Then search result 2 has a "last name" of "Smiths"
    Then search result 3 has a "last name" of "Aerosmithy"
    And there are 3 patient search results   
