@patient @patient-search
Feature: Patient Search Sorting

  Background:
    Given I am logged into NBS
    And I can "find" any "patient"
    And I have a patient

  Scenario: I can find the patients ordered by birthday ascending
    Given the patient has a "birthday" of "1987-01-15"
    And I have another patient
    And the patient has a "birthday" of "1999-11-19"
    And I have another patient
    And the patient has a "birthday" of "1974-05-29"
    And patients are available for search
    And I want patients sorted by "birthday" "asc"
    When I search for patients
    And search result 1 has a "birthday" of "1974-05-29"
    And search result 2 has a "birthday" of "1987-01-15"
    And search result 3 has a "birthday" of "1999-11-19"

  Scenario: I can find the patients ordered by birthday descending
    Given the patient has a "birthday" of "1987-01-15"
    And I have another patient
    And the patient has a "birthday" of "1999-11-19"
    And I have another patient
    And the patient has a "birthday" of "1974-05-29"
    And patients are available for search
    And I want patients sorted by "birthday" "desc"
    When I search for patients
    And search result 1 has a "birthday" of "1999-11-19"
    And search result 2 has a "birthday" of "1987-01-15"
    And search result 3 has a "birthday" of "1974-05-29"

  Scenario: I can find the most relevant patient when sorting by legal name  ascending
    Given the patient has the legal name "Wanda" "Maximoff"
    And I have another patient
    And the patient has the legal name "Helen" "Cho"
    And I have another patient
    And the patient has the legal name "Pietro" "Maximoff"
    And patients are available for search
    And I want patients sorted by "patientname" "asc"
    When I search for patients
    Then search result 1 has a "first name" of "Helen"
    And search result 1 has a "last name" of "Cho"
    And search result 2 has a "first name" of "Pietro"
    And search result 2 has a "last name" of "Maximoff"
    And search result 3 has a "first name" of "Wanda"
    And search result 3 has a "last name" of "Maximoff"

  Scenario: I can find the most relevant patient when sorting by legal name descending
    Given the patient has the legal name "Wanda" "Maximoff"
    And I have another patient
    And the patient has the legal name "Helen" "Cho"
    And I have another patient
    And the patient has the legal name "Pietro" "Maximoff"
    And patients are available for search
    And I want patients sorted by "last name" "desc"
    When I search for patients
    Then search result 1 has a "first name" of "Wanda"
    And search result 1 has a "last name" of "Maximoff"
    And search result 2 has a "first name" of "Pietro"
    And search result 2 has a "last name" of "Maximoff"
    And search result 3 has a "first name" of "Helen"
    And search result 3 has a "last name" of "Cho"

  Scenario: I can find the most relevant patient when sorting by last name  ascending
    Given the patient has the legal name "Timothy" "Jackson"
    And I have another patient
    And the patient has the legal name "Jason" "Todd"
    And I have another patient
    And the patient has the legal name "Stephanie" "Brown"
    And patients are available for search
    And I want patients sorted by "last name" "asc"
    When I search for patients
    Then search result 1 has a "first name" of "Stephanie"
    And search result 1 has a "last name" of "Brown"
    And search result 2 has a "first name" of "Timothy"
    And search result 2 has a "last name" of "Jackson"
    And search result 3 has a "first name" of "Jason"
    And search result 3 has a "last name" of "Todd"

  Scenario: I can find the most relevant patient when sorting by last name descending
    Given the patient has the legal name "Timothy" "Jackson"
    And I have another patient
    And the patient has the legal name "Jason" "Todd"
    And I have another patient
    And the patient has the legal name "Stephanie" "Brown"
    And patients are available for search
    And I want patients sorted by "last name" "desc"
    When I search for patients
    Then search result 1 has a "first name" of "Jason"
    And search result 1 has a "last name" of "Todd"
    And search result 2 has a "first name" of "Timothy"
    And search result 2 has a "last name" of "Jackson"
    And search result 3 has a "first name" of "Stephanie"
    And search result 3 has a "last name" of "Brown"

  Scenario: I can find the most relevant patient when sorting by first name  ascending
    Given the patient has the legal name "Timothy" "Jackson"
    And I have another patient
    And the patient has the legal name "Jason" "Todd"
    And I have another patient
    And the patient has the legal name "Stephanie" "Brown"
    And patients are available for search
    And I want patients sorted by "first name" "asc"
    When I search for patients
    Then search result 1 has a "first name" of "Jason"
    And search result 2 has a "first name" of "Stephanie"
    And search result 3 has a "first name" of "Timothy"

  Scenario: I can find the most relevant patient when sorting by first name descending
    Given the patient has the legal name "Timothy" "Jackson"
    And I have another patient
    And the patient has the legal name "Jason" "Todd"
    And I have another patient
    And the patient has the legal name "Stephanie" "Brown"
    And patients are available for search
    And I want patients sorted by "first name" "desc"
    When I search for patients
    Then search result 1 has a "first name" of "Timothy"
    And search result 2 has a "first name" of "Stephanie"
    And search result 3 has a "first name" of "Jason"

  Scenario: I can find the most relevant patient when sorting by identification ascending
    Given the patient can be identified with an "SS" of "888-88-8888"
    And I have another patient
    And the patient can be identified with an "DL" of "123"
    And I have another patient
    And the patient can be identified with an "Other" of "456"
    And patients are available for search
    And I want patients sorted by "identification" "asc"
    When I search for patients
    Then search result 1 has an "identification value" of "123"
    And search result 2 has an "identification value" of "456"
    And search result 3 has an "identification value" of "888-88-8888"

  Scenario: I can find the most relevant patient when sorting by identification descending
    Given the patient can be identified with an "SS" of "888-88-8888"
    And I have another patient
    And the patient can be identified with an "DL" of "123"
    And I have another patient
    And the patient can be identified with an "Other" of "456"
    And patients are available for search
    And I want patients sorted by "identification" "desc"
    When I search for patients
    Then search result 1 has an "identification value" of "888-88-8888"
    And search result 2 has an "identification value" of "456"
    And search result 3 has an "identification value" of "123"

  Scenario: I can find the most relevant patient when sorting by city  ascending
    Given the patient has a "city" of "acity"
    And I have another patient
    And the patient has a "city" of "bcity"
    And I have another patient
    And the patient has a "city" of "ccity"
    And patients are available for search
    And I want patients sorted by "city" "asc"
    When I search for patients
    Then search result 1 has a "city" of "acity"
    And search result 2 has a "city" of "bcity"
    And search result 3 has a "city" of "ccity"

  Scenario: I can find the most relevant patient when sorting by city descending
    Given the patient has a "city" of "acity"
    And I have another patient
    And the patient has a "city" of "bcity"
    And I have another patient
    And the patient has a "city" of "ccity"
    And patients are available for search
    And I want patients sorted by "city" "desc"
    When I search for patients
    Then search result 1 has a "city" of "ccity"
    And search result 2 has a "city" of "bcity"
    And search result 3 has a "city" of "acity"

  Scenario: I can find the most relevant patient when sorting by email ascending
    Given the patient has the Email Address - Home email address of "xyz@test.com" as of 11/07/2023
    And the patient has the Email Address - Home email address of "abc@test.com" as of 11/07/2024
    And I have another patient
    And the patient has a "email address" of "cba@test.com"
    And I have another patient
    And the patient has a "email address" of "bac@test.com"
    And patients are available for search
    And I want patients sorted by "email" "asc"
    When I search for patients
    Then search result 1 has a "email address" of "abc@test.com"
    And search result 2 has a "email address" of "bac@test.com"
    And search result 3 has a "email address" of "cba@test.com"

  Scenario: I can find the most relevant patient when sorting by email descending
    Given the patient has a "email address" of "abc@test.com"
    And I have another patient
    And the patient has the Email Address - Home email address of "xyz@test.com" as of 11/07/2023
    And the patient has the Email Address - Home email address of "cba@test.com" as of 11/07/2024
    And I have another patient
    And the patient has a "email address" of "bac@test.com"
    And patients are available for search
    And I want patients sorted by "email" "desc"
    When I search for patients
    Then search result 1 has a "email address" of "cba@test.com"
    And search result 2 has a "email address" of "bac@test.com"
    And search result 3 has a "email address" of "abc@test.com"

  Scenario: I can find the most relevant patient when sorting by address ascending
    Given the patient has a "address" of "123 One St"
    And I have another patient
    And the patient has a "address" of "345 One St"
    And I have another patient
    And the patient has a "address" of "234 One St"
    And patients are available for search
    And I want patients sorted by "address" "asc"
    When I search for patients
    Then search result 1 has a "address" of "123 One St"
    And search result 2 has a "address" of "234 One St"
    And search result 3 has a "address" of "345 One St"

  Scenario: I can find the most relevant patient when sorting by address descending
    Given the patient has a "address" of "123 One St"
    And I have another patient
    And the patient has a "address" of "345 One St"
    And I have another patient
    And the patient has a "address" of "234 One St"
    And patients are available for search
    And I want patients sorted by "address" "desc"
    When I search for patients
    Then search result 1 has a "address" of "345 One St"
    And search result 2 has a "address" of "234 One St"
    And search result 3 has a "address" of "123 One St"

  Scenario: I can find the most relevant patient when sorting by zip ascending
    Given the patient has a "zip" of "12345"
    And I have another patient
    And the patient has a "zip" of "34567"
    And I have another patient
    And the patient has a "zip" of "23456"
    And patients are available for search
    And I want patients sorted by "zip" "asc"
    When I search for patients
    Then search result 1 has a "zip" of "12345"
    And search result 2 has a "zip" of "23456"
    And search result 3 has a "zip" of "34567"

  Scenario: I can find the most relevant patient when sorting by zip descending
    Given the patient has a "zip" of "12345"
    And I have another patient
    And the patient has a "zip" of "34567"
    And I have another patient
    And the patient has a "zip" of "23456"
    And patients are available for search
    And I want patients sorted by "zip" "desc"
    When I search for patients
    Then search result 1 has a "zip" of "34567"
    And search result 2 has a "zip" of "23456"
    And search result 3 has a "zip" of "12345"

  Scenario: I can find the most relevant patient when sorting by phone ascending
    Given the patient has a "phone number" of "123-456-7890"
    And I have another patient
    And the patient has a "phone number" of "321-456-7890"
    And I have another patient
    And the patient has a "phone number" of "213-456-7890"
    And patients are available for search
    And I want patients sorted by "phone" "asc"
    When I search for patients
    Then search result 1 has a "phone number" of "123-456-7890"
    And search result 2 has a "phone number" of "213-456-7890"
    And search result 3 has a "phone number" of "321-456-7890"

  Scenario: I can find the most relevant patient when sorting by phone descending
    Given the patient has a "phone number" of "123-456-7890"
    And I have another patient
    And the patient has a "phone number" of "321-456-7890"
    And I have another patient
    And the patient has a "phone number" of "213-456-7890"
    And patients are available for search
    And I want patients sorted by "phone" "desc"
    When I search for patients
    Then search result 1 has a "phone number" of "321-456-7890"
    And search result 2 has a "phone number" of "213-456-7890"
    And search result 3 has a "phone number" of "123-456-7890"

  Scenario: I can find the most relevant patient when sorting by gender ascending
    Given the patient has a "sex" of "F"
    And I have another patient
    And the patient has a "sex" of "U"
    And I have another patient
    And the patient has a "sex" of "M"
    And patients are available for search
    And I want patients sorted by "sex" "asc"
    When I search for patients
    Then search result 1 has a "sex" of "Female"
    And search result 2 has a "sex" of "Male"
    And search result 3 has a "sex" of "Unknown"

  Scenario: I can find the most relevant patient when sorting by gender descending
    Given the patient has a "sex" of "F"
    And I have another patient
    And the patient has a "sex" of "U"
    And I have another patient
    And the patient has a "sex" of "M"
    And patients are available for search
    And I want patients sorted by "sex" "desc"
    When I search for patients
    Then search result 1 has a "sex" of "Unknown"
    And search result 2 has a "sex" of "Male"
    And search result 3 has a "sex" of "Female"

  Scenario: I can find the most relevant patient when sorting by local id ascending
    Given the patient has an "local id" of "PSN10000120GA01"
    And I have another patient
    And the patient has an "local id" of "PSN10000320GA01"
    And I have another patient
    And the patient has an "local id" of "PSN10000220GA01"
    And patients are available for search
    And I want patients sorted by "local_id" "asc"
    When I search for patients
    Then search result 1 has a "patient id" of "120"
    And search result 2 has a "patient id" of "220"
    And search result 3 has a "patient id" of "320"

  Scenario: I can find the most relevant patient when sorting by local id descending
    Given the patient has an "local id" of "PSN10000120GA01"
    And I have another patient
    And the patient has an "local id" of "PSN10000320GA01"
    And I have another patient
    And the patient has an "local id" of "PSN10000220GA01"
    And patients are available for search
    And I want patients sorted by "patient id" "desc"
    When I search for patients
    Then search result 1 has a "patient id" of "320"
    And search result 2 has a "patient id" of "220"
    And search result 3 has a "patient id" of "120"

  Scenario: I can find the most relevant patient when sorting by state ascending
    Given the patient has a "state" of "01"
    And I have another patient
    And the patient has a "state" of "04"
    And I have another patient
    And the patient has a "state" of "02"
    And patients are available for search
    And I want patients sorted by "state" "asc"
    When I search for patients
    Then search result 1 has a "state" of "AK"
    And search result 2 has a "state" of "AL"
    And search result 3 has a "state" of "AZ"

  Scenario: I can find the most relevant patient when sorting by state descending
    Given the patient has a "state" of "01"
    And I have another patient
    And the patient has a "state" of "04"
    And I have another patient
    And the patient has a "state" of "02"
    And patients are available for search
    And I want patients sorted by "state" "desc"
    When I search for patients
    Then search result 1 has a "state" of "AZ"
    And search result 2 has a "state" of "AL"
    And search result 3 has a "state" of "AK"

  Scenario: I can find the most relevant patient when sorting by county ascending
    Given the patient has a "county" of "01001"
    And I have another patient
    And the patient has a "county" of "01005"
    And I have another patient
    And the patient has a "county" of "01003"
    And patients are available for search
    And I want patients sorted by "county" "asc"
    When I search for patients
    Then search result 1 has a "county" of "Autauga County"
    And search result 2 has a "county" of "Baldwin County"
    And search result 3 has a "county" of "Barbour County"

  Scenario: I can find the most relevant patient when sorting by county descending
    Given the patient has a "county" of "01001"
    And I have another patient
    And the patient has a "county" of "01005"
    And I have another patient
    And the patient has a "county" of "01003"
    And patients are available for search
    And I want patients sorted by "county" "desc"
    When I search for patients
    Then search result 1 has a "county" of "Barbour County"
    And search result 2 has a "county" of "Baldwin County"
    And search result 3 has a "county" of "Autauga County"

  Scenario: I can find the most relevant patient when sorting by country ascending
    Given the patient has a "country" of "840"
    And I have another patient
    And the patient has a "country" of "858"
    And I have another patient
    And the patient has a "country" of "850"
    And patients are available for search
    And I want patients sorted by "country" "asc"
    When I search for patients
    Then there are 3 patient search results

  Scenario: I can find the most relevant patient when sorting by country descending
    Given the patient has a "country" of "840"
    And I have another patient
    And the patient has a "country" of "858"
    And I have another patient
    And the patient has a "country" of "850"
    And patients are available for search
    And I want patients sorted by "country" "desc"
    When I search for patients
    Then there are 3 patient search results
