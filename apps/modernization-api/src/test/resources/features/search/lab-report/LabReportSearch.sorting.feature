@lab_report_search
Feature: Lab Report Search Sorting

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I can "view" any "ObservationLabReport" for "STD" within all jurisdictions
    And I can "view" any "ObservationLabReport" for "ARBO" within all jurisdictions

  Scenario: I can find Lab Reports ordered by the patient's last name ascending
    Given I have a patient
    And the patient has the "legal" name "Ryu" "Jose"
    And the patient has a lab report
    And I have another patient
    And the patient has the "legal" name "Tem" "Ray"
    And the patient has a lab report
    And I have another patient
    And the patient has the "legal" name "Fraw" "Bow"
    And the patient has a lab report
    And lab reports are available for search
    And I want search results sorted by "last name" "asc"
    When I search for lab reports
    Then the 1st lab report search result has a "last name" of "Bow"
    And the 2nd lab report search result has a "last name" of "Jose"
    And the 3rd lab report search result has a "last name" of "Ray"

  Scenario: I can find Lab Reports ordered by the patient's last name descending
    Given I have a patient
    And the patient has the "legal" name "Ryu" "Jose"
    And the patient has a lab report
    And I have another patient
    And the patient has the "legal" name "Tem" "Ray"
    And the patient has a lab report
    And I have another patient
    And the patient has the "legal" name "Fraw" "Bow"
    And the patient has a lab report
    And lab reports are available for search
    And I want search results sorted by "last name" "desc"
    When I search for lab reports
    Then the 1st lab report search result has a "last name" of "Ray"
    And the 2nd lab report search result has a "last name" of "Jose"
    And the 3rd lab report search result has a "last name" of "Bow"

  Scenario: I can find Lab Reports ordered by the patient's birthday ascending
    Given I have a patient
    And the patient has a "birthday" of "1987-01-15"
    And the patient has a lab report
    And I have another patient
    And the patient has a "birthday" of "1999-11-19"
    And the patient has a lab report
    And I have another patient
    And the patient has a "birthday" of "1974-05-29"
    And the patient has a lab report
    And lab reports are available for search
    And I want search results sorted by "birthday" "asc"
    When I search for lab reports
    Then the 1st lab report search result has a "birthday" of "1974-05-29"
    And the 2nd lab report search result has a "birthday" of "1987-01-15"
    And the 3rd lab report search result has a "birthday" of "1999-11-19"

  Scenario: I can find Lab Reports ordered by the patient's birthday descending
    Given I have a patient
    And the patient has a "birthday" of "1987-01-15"
    And the patient has a lab report
    And I have another patient
    And the patient has a "birthday" of "1999-11-19"
    And the patient has a lab report
    And I have another patient
    And the patient has a "birthday" of "1974-05-29"
    And the patient has a lab report
    And lab reports are available for search
    And I want search results sorted by "birthday" "desc"
    When I search for lab reports
    Then the 1st lab report search result has a "birthday" of "1999-11-19"
    And the 2nd lab report search result has a "birthday" of "1987-01-15"
    And the 3rd lab report search result has a "birthday" of "1974-05-29"
