@patient-search @patient-search-results
Feature: Patient Search Result Addresses

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient

  Scenario: I can view address type and use from patient search results
    Given the patient has a House - Home address at "6151 Richmond Street" "Miami" "33266"
    And patients are available for search
    And I would like to search for a patient using a short ID
    When I search for patients
    Then the search results have a patient with an "address type" equal to "House"
    And the search results have a patient with an "address use" equal to "Home"
