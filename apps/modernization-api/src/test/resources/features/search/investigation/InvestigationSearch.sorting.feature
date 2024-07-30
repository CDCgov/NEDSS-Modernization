@investigation_search
Feature: Investigation Search Sorting

  Background:
    Given I am logged in
    And I can "Find" any "Patient"
    And I can "View" any "Investigation" for "STD" within all jurisdictions
    And I can "View" any "Investigation" for "ARBO" within all jurisdictions

  Scenario: I can find Investigations ordered by the patient's last name ascending
    Given I have a patient
    And the patient has the "legal" name "Ben" "Hanscom"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Beverly" "Marsh"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Bill" "Denbrough"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "last name" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "last name" of "Denbrough"
    And the 2nd investigation search result has a "last name" of "Hanscom"
    And the 3rd investigation search result has a "last name" of "Marsh"

  Scenario: I can find Investigations ordered by the patient's last name Descending
    Given I have a patient
    And the patient has the "legal" name "Ben" "Hanscom"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Beverly" "Marsh"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Bill" "Denbrough"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "last name" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "last name" of "Marsh"
    And the 2nd investigation search result has a "last name" of "Hanscom"
    And the 3rd investigation search result has a "last name" of "Denbrough"

  Scenario: I can find Investigations ordered by the patient's birthday ascending
    Given I have a patient
    And the patient has a "birthday" of "1980-09-29"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "birthday" of "2013-09-24"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "birthday" of "1974-04-05"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "birthday" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "birthday" of "1974-04-05"
    And the 2nd investigation search result has a "birthday" of "1980-09-29"
    And the 3rd investigation search result has a "birthday" of "2013-09-24"

  Scenario: I can find Investigations ordered by the patient's birthday descending
    Given I have a patient
    And the patient has a "birthday" of "1980-09-29"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "birthday" of "2013-09-24"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "birthday" of "1974-04-05"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "birthday" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "birthday" of "2013-09-24"
    And the 2nd investigation search result has a "birthday" of "1980-09-29"
    And the 3rd investigation search result has a "birthday" of "1974-04-05"

  Scenario: I can find Investigations ordered by the patient's first name ascending
    Given I have a patient
    And the patient has the "legal" name "Beverly" "Hanscom"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Ben" "Marsh"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Bill" "Denbrough"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "first name" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "first name" of "Ben"
    And the 2nd investigation search result has a "first name" of "Beverly"
    And the 3rd investigation search result has a "first name" of "Bill"

  Scenario: I can find Investigations ordered by the patient's first name Descending
    Given I have a patient
    And the patient has the "legal" name "Beverly" "Hanscom"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Ben" "Marsh"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has the "legal" name "Bill" "Denbrough"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "first name" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "first name" of "Bill"
    And the 2nd investigation search result has a "first name" of "Beverly"
    And the 3rd investigation search result has a "first name" of "Ben"

  Scenario: I can find Investigations ordered by the patient's sex ascending
    Given I have a patient
    And the patient has a "sex" of "F"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "sex" of "U"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "sex" of "M"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "sex" "asc"
    When I search for investigations
    Then the 1st investigation search result has a "sex" of "F"
    And the 2nd investigation search result has a "sex" of "M"
    And the 3rd investigation search result has a "sex" of "U"

  Scenario: I can find Investigations ordered by the patient's sex descending
    Given I have a patient
    And the patient has a "sex" of "F"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "sex" of "U"
    And the patient is a subject of an investigation
    And I have another patient
    And the patient has a "sex" of "M"
    And the patient is a subject of an investigation
    And investigations are available for search
    And I want search results sorted by "sex" "desc"
    When I search for investigations
    Then the 1st investigation search result has a "sex" of "U"
    And the 2nd investigation search result has a "sex" of "M"
