@patient-search @patient-search-results
Feature: Patient Search Result Sorting by Identifications

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I want patients sorted by "relevance" "desc"
    And I have a patient
    And the patient can be identified with an Other of "773" as of 11/07/2023
    And the patient can be identified with an Social Security of "888-88-8888" as of 11/07/2023
    And the patient can be identified with an Other of "999-11-1111" as of 10/07/2004
    And I have another patient
    And the patient can be identified with an Driver's license number of "00123" as of 05/13/1997
    And the patient can be identified with an Driver's license number of "123" as of 10/17/2001
    And I have another patient
    And the patient can be identified with an Other of "456" as of 07/11/2023
    And patients are available for search

  Scenario: I can find the most relevant patient when sorting by identification ascending
    Given I want patients sorted by "identification" "asc"
    When I search for patients
    Then search result 1 has an "identification value" of "123"
    And search result 2 has an "identification value" of "456"
    And search result 3 has an "identification value" of "888-88-8888"

  Scenario: I can find the most relevant patient when sorting by identification descending
    Given I want patients sorted by "identification" "desc"
    When I search for patients
    Then search result 1 has an "identification value" of "888-88-8888"
    And search result 2 has an "identification value" of "456"
    And search result 3 has an "identification value" of "123"
