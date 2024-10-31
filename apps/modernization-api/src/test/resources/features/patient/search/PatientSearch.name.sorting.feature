@patient-name-sorting
Feature: Patient Search Name Sorting

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I have a patient

  Scenario: I can find the patient that are ordered by last name, that get sorted by last name ascending
    Given the patient has the legal name "John" "Smith"
    And I have another patient
    And the patient has the legal name "John" "Smoth"
    And patients are available for search
    And I want patients sorted by "patientname" "asc"
    When I search for patients
    Then search result 1 has an "first name" of "John"
    And search result 1 has an "last name" of "Smith"
    And search result 2 has an "first name" of "John"
    And search result 2 has an "last name" of "Smoth"

  Scenario: I can find the patient that are ordered by last name, that get sorted by last name descending
    Given the patient has the legal name "John" "Smith"
    And I have another patient
    And the patient has the legal name "John" "Smoth"
    And patients are available for search
    And I want patients sorted by "patientname" "desc"
    When I search for patients
    And search result 1 has an "first name" of "John"
    And search result 1 has an "last name" of "Smoth"
    Then search result 2 has an "first name" of "John"
    And search result 2 has an "last name" of "Smith"

  Scenario: I can find the patient that are ordered by legal name, that get sorted by first name ascending
    Given the patient has the legal name "John" "Smith"
    And I have another patient
    And the patient has the legal name "Michael" "Smith"
    And I have another patient
    And the patient has the legal name "" "Smith"
    And patients are available for search
    And I want patients sorted by "patientname" "asc"
    When I search for patients
    And search result 1 has an "last name" of "Smith"
    And search result 2 has an "first name" of "John"
    And search result 2 has an "last name" of "Smith"
    And search result 3 has an "first name" of "Michael"
    And search result 3 has an "last name" of "Smith"

  Scenario: I can find the patient that are ordered by legal name, that get sorted by first name descending
    Given the patient has the legal name "John" "Smith"
    And I have another patient
    And the patient has the legal name "Michael" "Smith"
    And I have another patient
    And the patient has the legal name "" "Smith"
    And patients are available for search
    And I want patients sorted by "patientname" "desc"
    When I search for patients
    And search result 1 has an "first name" of "Michael"
    And search result 1 has an "last name" of "Smith"
    And search result 2 has an "first name" of "John"
    And search result 2 has an "last name" of "Smith"
    And search result 3 has an "last name" of "Smith"

  Scenario: I can find the patient that are ordered by legal name, that get sorted by suffix ascending
    Given the patient has the legal name "John" "Jacob" "Smith", Jr. as of 01/01/2000
    And I have another patient
    And the patient has the legal name "John" "Jacob" "Smith", "" as of 01/01/2000
    And I have another patient
    And the patient has the legal name "John" "Jacob" "Smith", Sr. as of 01/01/2000
    And patients are available for search
    And I want patients sorted by "patientname" "asc"
    When I search for patients
    And search result 1 has an "suffix" of "Jr."
    And search result 2 has an "suffix" of "Sr."
    And search result 3 has an "suffix" of ""

  Scenario: I can find the patient that are ordered by legal name, that get sorted by suffix descending
    Given the patient has the legal name "John" "Jacob" "Smith", Jr. as of 01/01/2000
    And I have another patient
    And the patient has the legal name "John" "Jacob" "Smith", "" as of 01/01/2000
    And I have another patient
    And the patient has the legal name "John" "Jacob" "Smith", Sr. as of 01/01/2000
    And patients are available for search
    And I want patients sorted by "patientname" "desc"
    When I search for patients
    And search result 1 has an "suffix" of ""
    And search result 2 has an "suffix" of "Sr."
    And search result 3 has an "suffix" of "Jr."


  Scenario: I can find the patient that are ordered by legal name, that get sorted by birth date ascending
    Given the patient has the legal name "John" "Smith"
    And the patient has a "birthday" of "1990-01-01"
    And I have another patient
    Given the patient has the legal name "John" "Smith"
    And the patient has a "birthday" of "2012-01-01"
    And patients are available for search
    And I want patients sorted by "patientname" "asc"
    When I search for patients
    And search result 1 has an "birthday" of "1990-01-01"
    And search result 2 has an "birthday" of "2012-01-01"

  Scenario: I can find the patient that are ordered by legal name, that get sorted by birth date descending
    Given the patient has the legal name "John" "Smith"
    And the patient has a "birthday" of "1990-01-01"
    And I have another patient
    Given the patient has the legal name "John" "Smith"
    And the patient has a "birthday" of "2012-01-01"
    And patients are available for search
    And I want patients sorted by "patientname" "desc"
    When I search for patients
    And search result 1 has an "birthday" of "2012-01-01"
    And search result 2 has an "birthday" of "1990-01-01"

  Scenario: I can find the patient that are ordered by legal name, that get sorted by patient id ascending
    Given the patient has the legal name "John" "Jacob" "Smith", Jr. as of 01/01/2000
    And the patient has a "email" of "shouldbefirst@test.com"
    And I have another patient
    Given the patient has the legal name "John" "Jacob" "Smith", Jr. as of 01/01/2000
    And the patient has a "email" of "shouldbesecond@test.com"
    And patients are available for search
    And I want patients sorted by "patientname" "asc"
    When I search for patients
    And search result 1 has a "email" of "shouldbefirst@test.com"
    And search result 2 has a "email" of "shouldbesecond@test.com"

  Scenario: I can find the patient that are ordered by legal name, that get sorted by patient id descending
    Given the patient has the legal name "John" "Jacob" "Smith", Jr. as of 01/01/2000
    And the patient has a "email" of "shouldbefirst@test.com"
    And I have another patient
    Given the patient has the legal name "John" "Jacob" "Smith", Jr. as of 01/01/2000
    And the patient has a "email" of "shouldbesecond@test.com"
    And patients are available for search
    And I want patients sorted by "patientname" "desc"
    When I search for patients
    And search result 1 has a "email" of "shouldbesecond@test.com"
    And search result 2 has a "email" of "shouldbefirst@test.com"